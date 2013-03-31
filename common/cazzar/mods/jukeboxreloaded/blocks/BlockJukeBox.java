package cazzar.mods.jukeboxreloaded.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.network.GuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockJukeBox extends Block
{

    @SideOnly(Side.CLIENT)
    private final Icon[] iconBuffer;

    private Random rand = new Random();

    public BlockJukeBox(int ID)
    {
        super(ID, Material.rock);
        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName("JukeBox");
        iconBuffer = new Icon[3];
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
        return new TileJukeBox(world, metadata);
        // return super.createTileEntity(world, metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    // this one does in-world rendering
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z,
            int blockSide)
    {
        if ( blockSide == ForgeDirection.UP.ordinal() ) return iconBuffer[2];
        if ( blockSide == ForgeDirection.DOWN.ordinal() ) return iconBuffer[0];
        return iconBuffer[1];
    }

    @Override
    // this one does inventory rendering
    public Icon getBlockTextureFromSideAndMetadata(int blockSide, int blockMeta)
    {
        if ( blockSide == ForgeDirection.UP.ordinal() ) return iconBuffer[2];
        if ( blockSide == ForgeDirection.DOWN.ordinal() ) return iconBuffer[0];
        return iconBuffer[1];
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
            EntityPlayer player, int par6, float par7, float par8, float par9)
    {

        if ( player.isSneaking() )
            return false;
        else
        {
            if ( !world.isRemote )
            {
                final TileJukeBox tileJukeBox = (TileJukeBox) world
                        .getBlockTileEntity(x, y, z);

                if ( tileJukeBox != null )
                {
                    System.out.println("trying to open the GUI");
                    player.openGui(JukeboxReloaded.instance(),
                            GuiHandler.JUKEBOX, world, x, y, z);
                }
                else
                {
                    System.out.println("Tile is null");
                }
            }
            return true;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister iconRegister)
    {
        iconBuffer[0] = iconRegister.registerIcon("cazzar:jukeboxbottom");
        iconBuffer[1] = iconRegister.registerIcon("cazzar:jukeboxside");
        iconBuffer[2] = iconRegister.registerIcon("cazzar:jukeboxtop");
        // super.registerIcons(par1IconRegister);
    }

    private void dropInventory(World world, int x, int y, int z)
    {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if ( !(tileEntity instanceof IInventory) ) return;

        IInventory inventory = (IInventory) tileEntity;

        for ( int i = 0; i < inventory.getSizeInventory(); i++ )
        {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if ( itemStack != null && itemStack.stackSize > 0 )
            {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z
                        + dZ, new ItemStack(itemStack.itemID,
                        itemStack.stackSize, itemStack.getItemDamage()));

                if ( itemStack.hasTagCompound() )
                {
                    entityItem.getEntityItem().setTagCompound(
                            (NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id,
            int meta)
    {
        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }
}
