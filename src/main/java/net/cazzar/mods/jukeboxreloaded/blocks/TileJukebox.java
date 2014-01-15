package net.cazzar.mods.jukeboxreloaded.blocks;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computer.api.IComputerAccess;
import dan200.computer.api.IPeripheral;
import net.cazzar.corelib.lib.InventoryUtils;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.corelib.util.CommonUtil;
import net.cazzar.mods.jukeboxreloaded.client.particles.Particles;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketJukeboxDescription;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketPlayRecord;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketShuffleDisk;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketStopPlaying;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.Random;

import static net.cazzar.mods.jukeboxreloaded.JukeboxReloaded.proxy;

public class TileJukebox extends TileEntity implements IInventory, IPeripheral {
    int metadata;
    public ItemStack[] items;
    int recordNumber = 0;
    String lastPlayingRecord = "";
    //boolean repeat = true;
    //boolean repeatAll = false;
    boolean shuffle = false;
    RepeatMode repeatMode = RepeatMode.ALL;
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

    public int getCurrentRecordNumber() {
        return recordNumber;
    }

    @Override
    public Packet func_145844_m() {
//        return (new PacketJukeboxDescription(this)).makePacket();
        return proxy().getChannel().generatePacketFrom(new PacketJukeboxDescription(this));
    }

    public short getFacing() {
        return facing;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public String func_145825_b() {
        return "Jukebox";
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
    public RepeatMode getReplayMode() {
        //if (repeat) return 2;
        //else if (repeatAll) return 1;
        //else return 0;
        return repeatMode;
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
    public boolean func_145818_k_() {
        return false;
    }

    public boolean isPlayingRecord() {
//        return SoundSystemHelper.isPlaying(this.field_145850_b, getIdentifier());
        return playing;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    public void markForUpdate() {
        field_145850_b.func_147438_o(field_145851_c, field_145848_d, field_145849_e);
    }

    public void nextRecord() {
        if (recordNumber++ >= getSizeInventory() - 1) recordNumber = 0;
    }

    @Override
    public void openChest() {
    }

    public void playSelectedRecord() {
        if (field_145850_b.isRemote) {
            if (getStackInSlot(recordNumber) == null) return;
            new PacketPlayRecord(((ItemRecord) getStackInSlot(recordNumber).getItem()).field_150929_a, field_145851_c, field_145848_d, field_145849_e).setSender(ClientUtil.mc().thePlayer).sendToServer();
            return;
        }

        if (getStackInSlot(recordNumber) == null) return;

        // if (!(getStackInSlot(recordNumber).getItem() instanceof ItemRecord))
        // return; // no I will not play.

        // worldObj.playRecord(((ItemRecord) getStackInSlot(recordNumber)
        // .getItem()).recordName, xCoord, yCoord, zCoord);

        lastPlayingRecord = ((ItemRecord) getStackInSlot(recordNumber).getItem()).field_150929_a;
        playing = true;

        new PacketPlayRecord(((ItemRecord) getStackInSlot(recordNumber).getItem()).field_150929_a, field_145851_c, field_145848_d, field_145849_e).sendToAllPlayers();
    }

    public void previousRecord() {
        if (recordNumber == 0) recordNumber = getSizeInventory() - 1;
        else recordNumber--;
    }

    @Override
    public void func_145839_a(NBTTagCompound tag) {
        super.func_145839_a(tag);
        recordNumber = tag.getInteger("recordNumber");
        facing = tag.getShort("facing");
        shuffle = tag.getBoolean("shuffle");
        setRepeatMode(RepeatMode.get(tag.getInteger("rptMode")));
        volume = tag.getFloat("volume");

        InventoryUtils.readItemStacksFromTag(items, tag.func_150295_c("inventory", items.length));
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
    public void setRepeatMode(RepeatMode mode) {
        repeatMode = mode;
    }

    public void setShuffle(boolean shuffle) {
        this.shuffle = shuffle;
    }

    public boolean shuffleEnabled() {
        return shuffle;
    }

    public void stopPlayingRecord() {
        playing = false;
        if (CommonUtil.isServer())
            new PacketStopPlaying(field_145851_c, field_145848_d, field_145849_e).sendToAllPlayers();
        else new PacketStopPlaying(field_145851_c, field_145848_d, field_145849_e).sendToServer();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void func_145845_h() {
        tick++;
        final Random random = new Random();

        if (tick % 5 == 0 && random.nextBoolean())
            if (isPlayingRecord()) {
                Particles particles = Particles.values()[random.nextInt(Particles.values().length)];
                particles.spawn(field_145851_c + random.nextDouble(), field_145848_d + 1.2D, field_145849_e + random.nextDouble());
            }

        if (tick % 10 != 0) return;
        if (waitTicks-- >= 0) return;

        waitTicks = 0;
        // streaming is only used on the client for playing in the jukebox..
        if (!isPlayingRecord()) {
            final boolean wasPlaying = playing;
            if (!wasPlaying) return;
            // if repeating
            if (repeatMode == RepeatMode.ONE) playSelectedRecord();
            else if (repeatMode == RepeatMode.ALL) {
                nextRecord();
                if (recordNumber == getLastSlotWithItem() + 1)
                    resetPlayingRecord();
                playSelectedRecord();
            } else {
                stopPlayingRecord();
                resetPlayingRecord();
            }
            // send tile information to the server to update the other clients
            if (shuffle && repeatMode != RepeatMode.ONE) new PacketShuffleDisk(this).sendToServer();
            new PacketJukeboxDescription(this).sendToServer();
        }
    }

    @Override
    public void func_145841_b(NBTTagCompound tag) {
        super.func_145841_b(tag);
        tag.setInteger("recordNumber", recordNumber);
        tag.setShort("facing", facing);
        tag.setInteger("rptMode", getReplayMode().ordinal());
        tag.setBoolean("shuffle", shuffle);
        tag.setFloat("volume", volume);
        tag.setTag("inventory", InventoryUtils.writeItemStacksToTag(items));
    }

    public ChunkCoordinates getIdentifier() {
        //x, y, z
        return new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e);
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
                    if (getCurrentRecordNumber() != nextDisk)
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
                this.setRepeatMode(RepeatMode.ALL);
                break;
            case 8:
                this.setRepeatMode(RepeatMode.OFF);
                break;
            case 9:
                this.setRepeatMode(RepeatMode.ONE);
                break;
            case 10:
                this.setRecordPlaying(((Double) args[0]).intValue() - 1);
                break;
            case 11:
                String s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).field_150929_a;
                if (FMLCommonHandler.instance().getEffectiveSide().isClient())
                    s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).field_150929_a;
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

    private void dropItemInWorld(ItemStack itemStack) {
        Random rand = new Random();

        if (itemStack != null && itemStack.stackSize > 0) {
            final float dX = rand.nextFloat() * 0.8F + 0.1F;
            final float dY = rand.nextFloat() * 0.8F + 0.1F;
            final float dZ = rand.nextFloat() * 0.8F + 0.1F;

            final EntityItem entityItem = new EntityItem(field_145850_b, field_145851_c + dX, field_145848_d + dY, field_145849_e + dZ, new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

            if (itemStack.hasTagCompound())
                entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());

            final float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * factor;
            field_145850_b.spawnEntityInWorld(entityItem);
            itemStack.stackSize = 0;
        }
    }
}
