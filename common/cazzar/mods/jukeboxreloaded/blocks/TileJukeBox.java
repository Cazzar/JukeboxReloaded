package cazzar.mods.jukeboxreloaded.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import codechicken.core.inventory.InventoryUtils;

public class TileJukeBox extends TileEntity implements IInventory
{
    World world;
    int metadata;
    public ItemStack[] items;

    public TileJukeBox()
    {
        items = new ItemStack[getSizeInventory()];
    }

    public TileJukeBox(World world, int metadata)
    {
        this.metadata = metadata;
        this.world = world;
        items = new ItemStack[getSizeInventory()];
    }

    @Override
    public void closeChest()
    {
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        items[slot].stackSize -= amount;
        return items[slot];
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public String getInvName()
    {
        return "JukeBox";
    }

    @Override
    public int getSizeInventory()
    {
        return 12;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return items[slot];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return items[i];
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack)
    {
        return itemstack.getItem() instanceof ItemRecord;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public void openChest()
    {
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        InventoryUtils.readItemStacksFromTag(items, tag.getTagList("items"));
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
            items[i] = itemstack;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setTag("items", InventoryUtils.writeItemStacksToTag(items));
    }
}
