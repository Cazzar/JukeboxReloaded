package cazzar.mods.jukeboxreloaded.blocks;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.lib.InventoryUtils;
import cazzar.mods.jukeboxreloaded.lib.util.SoundSystemHelper;
import cazzar.mods.jukeboxreloaded.network.packets.PacketJukeboxDescription;
import cazzar.mods.jukeboxreloaded.network.packets.PacketPlayRecord;
import cazzar.mods.jukeboxreloaded.network.packets.PacketShuffleDisk;
import cazzar.mods.jukeboxreloaded.network.packets.PacketStopPlaying;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;

public class TileJukeBox extends TileEntity implements IInventory, IPeripheral {
	int					metadata;
	public ItemStack[]	items;
	int					recordNumber		= 0;
	// boolean playingRecord = false;
	String				lastPlayingRecord	= "";
	boolean				repeat				= true;
	boolean				repeatAll			= false;
	boolean				shuffle				= false;
	int					tick				= 0;
	public boolean		playing				= false;
	public int			waitTicks			= 0;
	public float		volume				= 0.5F;
	private short		facing;
	SoundSystemHelper	sndSystem;
	
	public TileJukeBox() {
		items = new ItemStack[getSizeInventory()];
		sndSystem = new SoundSystemHelper(this);
	}
	
	public TileJukeBox(int metadata) {
		this();
		this.metadata = metadata;
	}
	
	@Override
	public void closeChest() {}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		items[slot].stackSize -= amount;
		return items[slot];
	}
	
	public void forcePlayRecord(String record) {
		// worldObj.playRecord(record, xCoord, yCoord, zCoord);
		// playingRecord = true;
		lastPlayingRecord = record;
		markForUpdate();
	}
	
	public int getCurrentRecordNumer() {
		return recordNumber;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		return (new PacketJukeboxDescription(this)).makePacket();
	}
	
	public short getFacing() {
		return facing;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public String getInvName() {
		return "JukeBox";
	}
	
	public String getLastPlayedRecord() {
		return lastPlayingRecord;
	}
	
	public int getLastSlotWithItem() {
		int i = 0;
		for (final ItemStack itemStack : items) {
			if (itemStack == null) break;
			i++;
		}
		return i - 1;
	}
	
	/**
	 * @return 0: none <br/>
	 *         1: all <br/>
	 *         2: one
	 */
	public int getReplayMode() {
		if (repeat) return 2;
		else if (repeatAll) return 1;
		else return 0;
	}
	
	@Override
	public int getSizeInventory() {
		return 12;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return items[slot];
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		final ItemStack stack = getStackInSlot(slot);
		if (stack != null) setInventorySlotContents(slot, null);
		return stack;
	}
	
	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
	
	public boolean isPlayingRecord() {
		if (sndSystem == null) sndSystem = new SoundSystemHelper(this);
		return sndSystem.isPlaying();
	}
	
	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) {
		return (itemstack.getItem() instanceof ItemRecord) || itemstack == null;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}
	
	public void markForUpdate() {
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public void nextRecord() {
		if (recordNumber++ >= getSizeInventory() - 1) recordNumber = 0;
	}
	
	@Override
	public void openChest() {}
	
	public void playSelectedRecord() {
		if (worldObj.isRemote) {
			if (getStackInSlot(recordNumber) == null) return;
			new PacketPlayRecord(((ItemRecord) getStackInSlot(recordNumber)
					.getItem()).recordName, xCoord, yCoord, zCoord)
					.sendToServer();
			return;
		}
		
		// for (int i = recordNumber; i < getSizeInventory(); i++)
		// if (getStackInSlot(i) != null) {
		// recordNumber = i;
		// break;
		// }
		if (getStackInSlot(recordNumber) == null) return;
		
		// if (!(getStackInSlot(recordNumber).getItem() instanceof ItemRecord))
		// return; // no I will not play.
		
		// worldObj.playRecord(((ItemRecord) getStackInSlot(recordNumber)
		// .getItem()).recordName, xCoord, yCoord, zCoord);
		
		lastPlayingRecord = ((ItemRecord) getStackInSlot(recordNumber)
				.getItem()).recordName;
		playing = true;
		
		new PacketPlayRecord(((ItemRecord) getStackInSlot(recordNumber)
				.getItem()).recordName, xCoord, yCoord, zCoord)
				.sendToAllPlayers();
	}
	
	public void previousRecord() {
		if (recordNumber == 0) recordNumber = getSizeInventory() - 1;
		else recordNumber--;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		recordNumber = tag.getInteger("recordNumber");
		facing = tag.getShort("facing");
		shuffle = tag.getBoolean("shuffle");
		setRepeatMode(tag.getInteger("rptMode"));
		
		InventoryUtils
				.readItemStacksFromTag(items, tag.getTagList("inventory"));
	}
	
	public void resetPlayingRecord() {
		recordNumber = 0;
	}
	
	public void setFacing(short direction) {
		facing = direction;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		items[slot] = itemstack;
		// if ( itemstack != null
		// && itemstack.stackSize > getInventoryStackLimit() )
		// {
		// itemstack.stackSize = getInventoryStackLimit();
		// }
		
		if (recordNumber == slot && sndSystem.isPlaying() && itemstack == null)
			playSelectedRecord();
	}
	
	public void setPlaying(boolean playing) {
		this.playing = playing;
		// if (!isPlayingRecord() && playing) playSelectedRecord();
		// else if (isPlayingRecord() && !playing) stopPlayingRecord();
	}
	
	public void setRecordPlaying(int recordNumber) {
		final int oldRecord = this.recordNumber;
		this.recordNumber = recordNumber;
		if (sndSystem.isPlaying() && oldRecord != recordNumber)
			playSelectedRecord();
	}
	
	/**
	 * @param mode
	 *            0: none <br/>
	 *            1: all <br/>
	 *            2: one
	 */
	public void setRepeatMode(int mode) {
		switch (mode) {
			case 0:
				repeat = repeatAll = false;
				break;
			case 1:
				repeatAll = true;
				repeat = false;
				break;
			case 2:
				repeatAll = false;
				repeat = true;
				break;
			default:
				repeat = repeatAll = false;
				break;
		}
	}
	
	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
	}
	
	public boolean shuffleEnabled() {
		return shuffle;
	}
	
	public void stopPlayingRecord() {
		playing = false;
		if (JukeboxReloaded.proxy.getEffectiveSide().isServer()) new PacketStopPlaying(
				xCoord, yCoord, zCoord).sendToAllPlayers();
		else new PacketStopPlaying(xCoord, yCoord, zCoord).sendToServer();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateEntity() {
		tick++;
		final Random random = new Random();
		
		if (tick % 5 == 0 && random.nextBoolean())
			if (isPlayingRecord())
				worldObj.spawnParticle("note", xCoord + random.nextDouble(),
						yCoord + 1.2D, zCoord + random.nextDouble(),
						random.nextDouble(), random.nextDouble(),
						random.nextDouble());
		// worldObj.spawnParticle("note", xCoord + random.nextDouble(),
		// yCoord +1, zCoord + random.nextDouble(), 0, 100, 0);
		
		if (tick % 10 != 0) return;
		if (waitTicks-- >= 0) return;
		
		waitTicks = 0;
		if (SoundSystemHelper.getSoundSystem() == null) return; // Thanks to
																// alex
		// streaming is only used on the client for playing in the jukebox..
		if (!isPlayingRecord()) {
			final boolean wasPlaying = playing;
			if (!wasPlaying) return;
			// if repeating
			if (repeat) playSelectedRecord();
			else if (repeatAll) {
				nextRecord();
				if (recordNumber == getLastSlotWithItem() + 1)
					resetPlayingRecord();
				playSelectedRecord();
			}
			else {
				stopPlayingRecord();
				resetPlayingRecord();
			}
			// send tile information to the server to update the other clients
			if (shuffle && !repeat)
				new PacketShuffleDisk(this).sendToServer();
			new PacketJukeboxDescription(this).sendToServer();
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("recordNumber", recordNumber);
		tag.setShort("facing", facing);
		tag.setInteger("rptMode", getReplayMode());
		tag.setBoolean("shuffle", shuffle);
		tag.setTag("inventory", InventoryUtils.writeItemStacksToTag(items));
	}
	
	public String getIdentifier() {
		return xCoord + ":" + yCoord + ":" + zCoord;
	}
	
	/**
	 * @return the Sound System Helper
	 */
	public SoundSystemHelper getSoundSystem() {
		return sndSystem;
	}
	
	// ComputerCraft API functions
	
	/**
	 * @see dan200.computer.api.IPeripheral#getType()
	 */
	@Override
	public String getType() {
		return "jukebox";
	}
	
	/**
	 * @see dan200.computer.api.IPeripheral#getMethodNames()
	 */
	@Override
	public String[] getMethodNames() {
		return new String[] { "isPlaying", "next", "prev", "play", "stop",
				"setShuffle", "getShuffle", "setRepeatAll", "setRepeatNone",
				"setRepeatOne", "selectRecord", "getRecordInfo" };
	}
	
	/**
	 * @see dan200.computer.api.IPeripheral#callMethod(dan200.computer.api.IComputerAccess,
	 *      int, java.lang.Object[])
	 */
	@Override
	public Object[] callMethod(IComputerAccess computer, int method,
			Object[] args) throws Exception {
		boolean wasPlaying = playing;
		switch (method) {
			case 0:
				return new Object[] { playing };
			case 1:
				if (wasPlaying) stopPlayingRecord();
				if (shuffleEnabled()) {
					final Random random = new Random();
					if (getLastSlotWithItem() <= 0) break;
					final int nextDisk = random.nextInt(getLastSlotWithItem());
					if (getCurrentRecordNumer() != nextDisk)
						setRecordPlaying(nextDisk);
				}
				nextRecord();
				if (wasPlaying) playSelectedRecord();
				break;
			case 2:
				if (wasPlaying) stopPlayingRecord();
				previousRecord();
				if (wasPlaying) playSelectedRecord();
				break;
			case 3:
				playSelectedRecord();
				break;
			case 4:
				stopPlayingRecord();
				break;
			case 5:
				boolean newshuffle;
				try {
					newshuffle = Boolean.valueOf((Boolean) args[0]);
					this.setShuffle(newshuffle);
				}
				catch (Exception e) {
					throw new Exception("Error parsing: " + args[0]);
				}
				break;
			case 6:
				return new Object[] { shuffle };
			case 7:
				this.setRepeatMode(1);
				break;
			case 8:
				this.setRepeatMode(0);
				break;
			case 9:
				this.setRepeatMode(2);
				break;
			case 10:
				this.setRecordPlaying(((Double)args[0]).intValue() - 1);
				break;
			case 11:
				String s = ((ItemRecord) getStackInSlot(recordNumber).getItem())
						.getRecordTitle().trim();
				return new String[] { s };
			default:
				return null;
		}
		markForUpdate();
		return null;
	}
	
	/**
	 * @see dan200.computer.api.IPeripheral#canAttachToSide(int)
	 */
	@Override
	public boolean canAttachToSide(int side) {
		return true;
	}
	
	/**
	 * @see dan200.computer.api.IPeripheral#attach(dan200.computer.api.IComputerAccess)
	 */
	@Override
	public void attach(IComputerAccess computer) {}
	
	/**
	 * @see dan200.computer.api.IPeripheral#detach(dan200.computer.api.IComputerAccess)
	 */
	@Override
	public void detach(IComputerAccess computer) {}
}
