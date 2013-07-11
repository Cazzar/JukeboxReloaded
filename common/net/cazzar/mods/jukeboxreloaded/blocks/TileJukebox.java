package net.cazzar.mods.jukeboxreloaded.blocks;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.util.CommonUtil;
import net.cazzar.corelib.util.InventoryUtils;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketJukeboxDescription;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketPlayRecord;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketShuffleDisk;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketStopPlaying;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class TileJukebox extends TileEntity implements IInventory, IPeripheral {
    int metadata;
    public ItemStack[] items;
    int recordNumber = 0;
    String lastPlayingRecord = "";
    boolean repeat = true;
    boolean repeatAll = false;
    boolean shuffle = false;
    int tick = 0;
    public boolean playing = false;
    public int waitTicks = 0;
    public float volume = 0.5F;
    private short facing;
    private boolean pageUpgrade;

    public TileJukebox() {
        items = new ItemStack[getSizeInventory()];
    }

    public TileJukebox(int metadata) {
        this();
        this.metadata = metadata;
    }

    @Override
    public void closeChest() {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemRecord;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        items[slot].stackSize -= amount;
        return items[slot];
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

    public int getLastSlotWithItem() {
        int i = 0;
        for (final ItemStack itemStack : items) {
            if (itemStack == null) break;
            i++;
        }
        return i - 1;
    }

    /**
     * @return 0: none <br/> 1: all <br/> 2: one
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
        return SoundSystemHelper.isPlaying(getIdentifier());
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
    public void openChest() {
    }

    public void playSelectedRecord() {
        if (worldObj.isRemote) {
            if (getStackInSlot(recordNumber) == null) return;
            new PacketPlayRecord(((ItemRecord) getStackInSlot(recordNumber)
                    .getItem()).recordName, xCoord, yCoord, zCoord)
                    .sendToServer();
            return;
        }

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

        if (recordNumber == slot && isPlayingRecord() && itemstack == null)
            playSelectedRecord();
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setRecordPlaying(int recordNumber) {
        final int oldRecord = this.recordNumber;
        this.recordNumber = recordNumber;
        if (isPlayingRecord() && oldRecord != recordNumber)
            playSelectedRecord();
    }

    /**
     * @param mode 0: none <br/> 1: all <br/> 2: one
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
        if (CommonUtil.isServer()) new PacketStopPlaying(
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
                worldObj.spawnParticle("note", xCoord + random.nextDouble(), yCoord + 1.2D, zCoord + random.nextDouble(), random.nextDouble(), random.nextDouble(), random.nextDouble());

        if (tick % 10 != 0) return;
        if (waitTicks-- >= 0) return;

        waitTicks = 0;
        if (!SoundSystemHelper.isSoundEnabled()) return; // Thanks to
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
            } else {
                stopPlayingRecord();
                resetPlayingRecord();
            }
            // send tile information to the server to update the other clients
            if (shuffle && !repeat) new PacketShuffleDisk(this).sendToServer();
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

    // ComputerCraft API functions

    @Override
    public String getType() {
        return "jukebox";
    }

    @Override
    public String[] getMethodNames() {
        return new String[]{"isPlaying", "next", "prev", "play", "stop",
                "setShuffle", "getShuffle", "setRepeatAll", "setRepeatNone",
                "setRepeatOne", "selectRecord", "getRecordInfo"};
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, int method,
                               Object[] args) throws Exception {
        boolean wasPlaying = playing;
        switch (method) {
            case 0:
                return new Object[]{playing};
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
                boolean newShuffle;
                try {
                    newShuffle = (Boolean) args[0];
                    this.setShuffle(newShuffle);
                } catch (Exception e) {
                    throw new Exception("Error parsing: " + args[0]);
                }
                break;
            case 6:
                return new Object[]{shuffle};
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
                this.setRecordPlaying(((Double) args[0]).intValue() - 1);
                break;
            case 11:
                String s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
                if (FMLCommonHandler.instance().getEffectiveSide().isClient())
                    s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
                s = s.replace("cazzar:kokoro", "Kagamine Rin - Kokoro")
                        .replace("cazzar:love_is_war", "Hatsune Miku - Love Is war")
                        .replace("cazzar:shibuya", "BECCA - SHIBUYA (Original)")
                        .replace("cazzar:spica", "Hatsune Miku - SPiCa")
                        .replace("cazzar:suki_daisuki", "Kagamine Rin - I Like You, I Love You")
                        .replace("cazzar:we_are_popcandy",
                                "Hatsune Miku - We are POPCANDY!");
                return new String[]{s};
            default:
                return null;
        }
        markForUpdate();
        return null;
    }

    @Override
    public boolean canAttachToSide(int side) {
        return true;
    }

    @Override
    public void attach(IComputerAccess computer) {
    }

    @Override
    public void detach(IComputerAccess computer) {
    }

    public void activate(EntityPlayer player) {
        if (!player.isSneaking()) return;

        ItemStack held = player.getHeldItem();

        if (held == null) {
            if (this.pageUpgrade)
                dropItemInWorld(new ItemStack(Item.paper));
        } else if (held.getItem() == Item.paper) {
            if (this.pageUpgrade) return;
            this.pageUpgrade = true;

            if (!player.capabilities.isCreativeMode)
                held.stackSize--;
        }
    }

    private void dropItemInWorld(ItemStack itemStack) {
        Random rand = new Random();

        if (itemStack != null && itemStack.stackSize > 0) {
            final float dX = rand.nextFloat() * 0.8F + 0.1F;
            final float dY = rand.nextFloat() * 0.8F + 0.1F;
            final float dZ = rand.nextFloat() * 0.8F + 0.1F;

            final EntityItem entityItem = new EntityItem(worldObj, xCoord + dX, yCoord
                    + dY, zCoord + dZ, new ItemStack(itemStack.itemID,
                    itemStack.stackSize, itemStack.getItemDamage()));

            if (itemStack.hasTagCompound())
                entityItem.getEntityItem().setTagCompound(
                        (NBTTagCompound) itemStack.getTagCompound().copy());

            final float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * factor;
            worldObj.spawnEntityInWorld(entityItem);
            itemStack.stackSize = 0;
        }
    }
}
