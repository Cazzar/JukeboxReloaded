package cazzar.mods.jukeboxreloaded.blocks;

import static cazzar.mods.jukeboxreloaded.lib.Reference.*;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import codechicken.core.inventory.InventoryUtils;
import codechicken.core.packet.PacketCustom;
import codechicken.core.vec.BlockCoord;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileJukeBox extends TileEntity implements IInventory
{
    World world;
    int metadata;
    public ItemStack[] items;
    int recordNumber = 0;
    boolean playingRecord = false;
    String lastPlayingRecord = "";
    boolean repeat = true;
    boolean repeatAll = false;
    boolean shuffle = false;
    int tick = 0;

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
        if ( stack != null )
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
        if ( recordNumber++ >= getSizeInventory() - 1 )
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
        for ( int i = recordNumber; i < getSizeInventory(); i++ )
        {
            if ( getStackInSlot(i) != null )
            {
                recordNumber = i;
                break;
            }
        }
        if ( getStackInSlot(recordNumber) == null ) return;

        // worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1005, xCoord,
        // yCoord,
        // zCoord, getStackInSlot(recordNumber).itemID);
        worldObj.playRecord(((ItemRecord) getStackInSlot(recordNumber)
                .getItem()).recordName, xCoord, yCoord, zCoord);
        playingRecord = true;
        lastPlayingRecord = ((ItemRecord) getStackInSlot(recordNumber)
                .getItem()).recordName;
    }

    public void previousRecord()
    {
        if ( recordNumber == 0 )
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
        //InventoryUtils.readItemStacksFromTag(items, tag.getTagList("items"));
        NBTTagList tagList = tag.getTagList("items");
        
        for(int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound tag2 = (NBTTagCompound) tagList.tagAt(i);
            items[tag2.getShort("Slot")] = ItemStack.loadItemStackFromNBT(tag);
        }
    }

    public void resetPlayingRecord()
    {
        recordNumber = 0;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack)
    {
        items[slot] = itemstack;
        //if ( itemstack != null
        //        && itemstack.stackSize > getInventoryStackLimit() )
        //{
        //    itemstack.stackSize = getInventoryStackLimit();
        //}
        
        if (recordNumber == slot && playingRecord && itemstack == null)
        {
        	playSelectedRecord();
        }
    }

    public void setPlaying(boolean playing)
    {
        if (!isPlayingRecord() && playing)
        	playSelectedRecord();
        else if (isPlayingRecord() && !playing)
        	stopPlayingRecord();
    }

    public void setRecordPlaying(int recordNumber)
    {
        int oldRecord = this.recordNumber;
        this.recordNumber = recordNumber;
        if ( playingRecord && oldRecord != recordNumber ) playSelectedRecord();
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
        NBTTagList tagList = new NBTTagList();
        for(int i = 0; i < items.length; i++)
        {
            if (items[i] != null)
            {
                NBTTagCompound tag2 = new NBTTagCompound();
                tag2.setShort("Slot", (short) i);
                items[i].writeToNBT(tag2);
                tagList.appendTag(tag2);
            }
        }
        tag.setTag("items", tagList);
    }

    public String getLastPlayedRecord()
    {
        return lastPlayingRecord;
    }

    public int getLastSlotWithItem()
    {
        int i = 0;
        for ( ItemStack itemStack : items )
        {
            if ( itemStack == null )
            {
                break;
            }
            i++;
        }
        return i-1;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void updateEntity()
    {
        if ( ++tick != 10 )
        {
            return;
        }
        tick = 0;
        // streaming is only used on the client for playing in the jukebox..
        boolean playing = SoundManager.sndSystem.playing("streaming");
        if ( !playing  )
        {
            final boolean wasPlaying = this.isPlayingRecord();
            if ( !wasPlaying ) return;
            // if repeating
            if ( repeat )
            {
                playSelectedRecord();
            }
            // if repeating everything
            else if ( repeatAll )
            {
                nextRecord();
                if ( recordNumber == getLastSlotWithItem() + 1 )
                {
                    resetPlayingRecord();
                }
                playSelectedRecord();
            }
           else
            {
                stopPlayingRecord();
                resetPlayingRecord();
            }
            // send tile information to the server to update the other clients
            if (shuffle && !repeat){
            	PacketCustom packet = new PacketCustom(CHANNEL_NAME, Packets.SERVER_NEXT_SHUFFLEDDISK);
            	packet.writeCoord(getCoord());
            	packet.writeString(lastPlayingRecord);
            	packet.writeBoolean(shuffle);
            	packet.sendToServer();
            }
            final PacketCustom packet = new PacketCustom(
                    CHANNEL_NAME, Packets.CLIENT_UPDATE_TILEJUKEBOX);
            packet.writeCoord(this.getCoord());
            packet.writeBoolean(this.isPlayingRecord());
            packet.writeInt(this.getCurrentRecordNumer());
            packet.sendToServer();
        }
    }
    
    public void markForUpdate() 
    {
    	worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        PacketCustom pkt = new PacketCustom(CHANNEL_NAME, Packets.CLIENT_TILEJUKEBOX_DATA);
        pkt.writeCoord(getCoord());
        pkt.writeBoolean(isPlayingRecord());
        pkt.writeInt(getCurrentRecordNumer());
        pkt.writeInt(getReplayMode());
        pkt.writeBoolean(shuffleEnabled());
        return pkt.toPacket();
    }
    /**
     * @return 
     * 0: none <br/>
     * 1: all <br/>
     * 2: one
     */
    public int getReplayMode()
    {
    	if (repeat) return 2;
    	else if (repeatAll) return 1;
    	else return 0;
    }
    public boolean shuffleEnabled() {
    	return shuffle;
    }
    public void setShuffle(boolean shuffle)
    {
    	this.shuffle = shuffle;
    }
    /**
     * @param mode
     * 0: none <br/>
     * 1: all <br/>
     * 2: one
     */
    public void setRepeatMode(int mode)
    {
    	switch (mode)
    	{
    		case 0:
    			this.repeat = this.repeatAll = false;
    			break;
    		case 1:
    			this.repeatAll = true;
    			this.repeat = false;
    			break;
    		case 2:
    			this.repeatAll = false;
    			this.repeat = true;
    			break;
    		default:
    			this.repeat = this.repeatAll = false;
    			break;	
    	}
    }
}
