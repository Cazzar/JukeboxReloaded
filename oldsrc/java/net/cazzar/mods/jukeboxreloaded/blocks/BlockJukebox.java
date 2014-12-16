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

import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.cazzar.mods.jukeboxreloaded.network.packet.ClientAction;
import net.cazzar.mods.jukeboxreloaded.network.packet.ServerAction;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class BlockJukebox extends Block implements ITileEntityProvider {
    static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
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
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        dropInventory(worldIn, pos.getX(), pos.getY(), pos.getZ());

        TileJukebox tileJukebox = worldIn.getTileEntity(pos) instanceof TileJukebox ? ((TileJukebox) worldIn.getTileEntity(pos)) : null;

        if (tileJukebox != null)
            if (ClientUtil.isClient())
                PacketHandler.INSTANCE.sendToServer(new ClientAction(ClientAction.Action.STOP, pos));
            else PacketHandler.INSTANCE.sendToAll(new ServerAction(ClientAction.Action.STOP, pos));
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileJukebox();
    }

    private void dropInventory(World world, int x, int y, int z) {
        final TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

        if (!(tileEntity instanceof IInventory)) return;

        final IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {

            final ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                final float dX = world.rand.nextFloat() * 0.8F + 0.1F;
                final float dY = world.rand.nextFloat() * 0.8F + 0.1F;
                final float dZ = world.rand.nextFloat() * 0.8F + 0.1F;


                JukeboxReloaded.logger.debug(marker, "Dropping {}", itemStack);
                final EntityItem entityItem = new EntityItem(world, x + dX, y
                        + dY, z + dZ, new ItemStack(itemStack.getItem(),
                        itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound())
                    entityItem.getEntityItem().setTagCompound(
                            (NBTTagCompound) itemStack.getTagCompound().copy());

                final float factor = 0.05F;
                entityItem.motionX = world.rand.nextGaussian() * factor;
                entityItem.motionY = world.rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = world.rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            TileJukebox tile = Util.getTileEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), TileJukebox.class);
            if (tile != null)
                playerIn.openGui(JukeboxReloaded.instance(), GuiHandler.JUKEBOX, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     *//*
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block id) {
        if (!world.isRemote) {
            if (!world.isBlockIndirectlyGettingPowered(x, y, z)) return;

            final TileEntity tile = world.getTileEntity(x, y, z);
            if (tile instanceof TileJukebox)
                ((TileJukebox) tile).playSelectedRecord();
        }
    }*/
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return createTileEntity(worldIn, null);
    }

    protected BlockState createBlockState() {
        return new BlockState(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }
}
