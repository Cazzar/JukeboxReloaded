/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.cazzar.mods.jukeboxreloaded.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.client.particles.ParticleIcons;
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.cazzar.mods.jukeboxreloaded.network.packet.ClientAction;
import net.cazzar.mods.jukeboxreloaded.network.packet.ServerAction;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.Random;

public class BlockJukebox extends Block {
    private final IIcon[] iconBuffer = new IIcon[4];
    private final Random rand = new Random();
    Marker marker = MarkerManager.getMarker("JukeboxReloaded-Block");

    public BlockJukebox() {
        super(Material.rock);
        setCreativeTab(JukeboxReloaded.proxy.creativeTab);
        setUnlocalizedName("Jukebox");
        setHardness(1.0F);
        setStepSound(soundTypeWood);
        setTickRandomly(true);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block id, int meta) {
        dropInventory(world, x, y, z);

        TileJukebox tileJukebox = world.getTileEntity(x, y, z) instanceof TileJukebox ? ((TileJukebox) world.getTileEntity(x, y, z)) : null;

        if (tileJukebox != null)
            if (ClientUtil.isClient())
                PacketHandler.INSTANCE.sendToServer(new ClientAction(ClientAction.Action.STOP, x, y, z));
            else PacketHandler.INSTANCE.sendToAll(new ServerAction(ClientAction.Action.STOP, x, y, z));
        super.breakBlock(world, x, y, z, id, meta);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileJukebox(metadata);
        // return super.createTileEntity(world, metadata);
    }

    private void dropInventory(World world, int x, int y, int z) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory)) return;

        final IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {

            final ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                final float dX = rand.nextFloat() * 0.8F + 0.1F;
                final float dY = rand.nextFloat() * 0.8F + 0.1F;
                final float dZ = rand.nextFloat() * 0.8F + 0.1F;


                JukeboxReloaded.logger.debug(marker, "Dropping {}", itemStack);
                final EntityItem entityItem = new EntityItem(world, x + dX, y
                        + dY, z + dZ, new ItemStack(itemStack.getItem(),
                        itemStack.stackSize, itemStack.getCurrentDurability()));

                if (itemStack.hasTagCompound())
                    entityItem.getEntityItem().setTagCompound(
                            (NBTTagCompound) itemStack.getTagCompound().copy());

                final float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
        if (blockSide == ForgeDirection.UP.ordinal()) return iconBuffer[2];
        if (blockSide == ForgeDirection.DOWN.ordinal()) return iconBuffer[0];
        final TileJukebox te = (TileJukebox) world.getTileEntity(x, y, z);
        // if (blockSide == Integer.valueOf(te.getFacing())) return
        // iconBuffer[1];

        ForgeDirection front, left, right;
        left = right = ForgeDirection.UNKNOWN;
        front = ForgeDirection.getOrientation(te.getFacing());

        switch (front.ordinal()) {
            case 2:
            case 3:
                right = (left = ForgeDirection.getOrientation(4)).getOpposite();
                break;
            case 4:
            case 5:
                right = (left = ForgeDirection.getOrientation(2)).getOpposite();
                break;
            default:
                break;
        }

        if (blockSide == left.ordinal() || blockSide == right.ordinal())
            return iconBuffer[1];
        if (blockSide == front.ordinal()) return iconBuffer[3];
        return iconBuffer[0];
    }


    /*// this one does inventory rendering
    public Icon agetBlockTextureFromSideAndMetadata(int blockSide, int blockMeta) {
        if (blockSide == ForgeDirection.UP.ordinal()) return iconBuffer[2];
        if (blockSide == ForgeDirection.DOWN.ordinal()) return iconBuffer[0];
        return iconBuffer[1];
    } */

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int blockSide, int blockMeta) {
        // return super.getIcon(par1, par2);
        if (blockSide == ForgeDirection.UP.ordinal()) return iconBuffer[2];
        if (blockSide == ForgeDirection.DOWN.ordinal()) return iconBuffer[0];
        return iconBuffer[1];
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if (!world.isRemote) {
            final TileJukebox tileJukebox = (TileJukebox) world.getTileEntity(x, y, z);

            if (tileJukebox != null) player.openGui(JukeboxReloaded.instance(), GuiHandler.JUKEBOX, world, x, y, z);
            else JukeboxReloaded.logger.error(marker, "Tile is null");
        }

        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        final int heading = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        final TileJukebox te = (TileJukebox) world.getTileEntity(x, y, z);
        switch (heading) {
            case 0:
                te.setFacing((short) 2);
                break;
            case 1:
                te.setFacing((short) 5);
                break;
            case 2:
                te.setFacing((short) 3);
                break;
            case 3:
                te.setFacing((short) 4);
                break;
            default:
                break;
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block id) {
        if (!world.isRemote) {
            if (!world.isBlockIndirectlyGettingPowered(x, y, z)) return;

            final TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileJukebox)
                ((TileJukebox) tile).playSelectedRecord();
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        iconBuffer[0] = iconRegister.registerIcon("cazzar:jukeboxbottom");
        iconBuffer[1] = iconRegister.registerIcon("cazzar:jukeboxside");
        iconBuffer[2] = iconRegister.registerIcon("cazzar:jukeboxtop");
        iconBuffer[3] = iconRegister.registerIcon("cazzar:jukeboxfront");
        ParticleIcons.CROTCHET = iconRegister.registerIcon("cazzar:crotchet");
        ParticleIcons.QUAVER = iconRegister.registerIcon("cazzar:quaver");
        ParticleIcons.DOUBLE_QUAVER = iconRegister.registerIcon("cazzar:double-quaver");
    }
}
