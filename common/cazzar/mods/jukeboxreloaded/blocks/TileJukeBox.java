package cazzar.mods.jukeboxreloaded.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import codechicken.core.inventory.InventoryUtils;
import codechicken.core.vec.BlockCoord;

public class TileJukeBox extends TileEntity implements IInventory {
    World world;
    int metadata;
    public ItemStack[] items;
    int recordNumber = 0;
    boolean playingRecord = false;
    String lastPlayingRecord = "";

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

    public BlockCoord getCoord()
    {
        return new BlockCoord(this);
    }

    public int getCurrentRecordNumer()
    {
        return recordNumber;
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
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null)
        {
            setInventorySlotContents(slot, null);
        }
        return stack;
    }

    @Override
    public boolean isInvNameLocalized()
    {
        return false;
    }

    public boolean isPlayingRecord()
    {
        return playingRecord;
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack)
    {
        return (itemstack.getItem() instanceof ItemRecord) || itemstack == null;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    public void nextRecord()
    {
        if (recordNumber++ >= getSizeInventory() - 1)
        {
            recordNumber = 0;
        }
    }

    @Override
    public void openChest()
    {
    }

    public void playSelectedRecord()
    {
        for (int i = recordNumber; i < getSizeInventory(); i++)
        {
            if (getStackInSlot(i) != null)
            {
                recordNumber = i;
                break;
            }
        }
        if (getStackInSlot(recordNumber) == null) return;

        // worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1005, xCoord,
        // yCoord,
        // zCoord, getStackInSlot(recordNumber).itemID);
        worldObj.playRecord(((ItemRecord) getStackInSlot(recordNumber)
                .getItem()).recordName, xCoord, yCoord, zCoord);
        playingRecord = true;
        lastPlayingRecord = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
    }

    public void previousRecord()
    {
        if (recordNumber == 0)
        {
            recordNumber = getSizeInventory() - 1;
        }
        else
        {
            recordNumber--;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        recordNumber = tag.getInteger("recordNumber");
        InventoryUtils.readItemStacksFromTag(items, tag.getTagList("items"));
    }

    public void resetPlayingRecord()
    {
        recordNumber = 0;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        items[slot] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public void setPlaying(boolean playing)
    {
        playingRecord = playing;
    }

    public void setRecordPlaying(int recordNumber)
    {
        this.recordNumber = recordNumber;
    }

    public void stopPlayingRecord()
    {
        playingRecord = false;

        worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1005, xCoord, yCoord,
                zCoord, 0);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        tag.setInteger("recordNumber", recordNumber);
        tag.setTag("items", InventoryUtils.writeItemStacksToTag(items));
    }
    
    public String getLastPlayedRecord() 
    {
        return lastPlayingRecord;
    }
}
