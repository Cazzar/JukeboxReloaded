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

import net.cazzar.corelib.lib.InventoryUtils;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.tile.SyncedTileEntity;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.corelib.util.CommonUtil;
import net.cazzar.mods.jukeboxreloaded.client.particles.Particles;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.cazzar.mods.jukeboxreloaded.network.packet.*;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.IChatComponent;

import java.util.Random;

//@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
public class TileJukebox extends SyncedTileEntity implements IInventory, IUpdatePlayerListBox {//, SimpleComponent {
    public ItemStack[] items;
    public boolean playing = false;
    public int waitTicks = 0;
    public float volume = 0.5F;
    int metadata;
    int recordNumber = 0;
    String lastPlayingRecord = "";
    //boolean repeat = true;
    //boolean repeatAll = false;
    boolean shuffle = false;
    RepeatMode repeatMode = RepeatMode.ALL;
    int tick = 0;
    private short facing;

    public TileJukebox() {
        items = new ItemStack[getSizeInventory()];
    }

    public TileJukebox(int metadata) {
        this();
        this.metadata = metadata;
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemRecord;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        items[slot].stackSize -= amount;
        return items[slot];
    }

    public int getCurrentRecordNumber() {
        return recordNumber;
    }

//    @Override
//    public void getDescriptionPacket() {
//        return (new PacketJukeboxDescription(this)).makePacket();
//    }


    public short getFacing() {
        return facing;
    }

    public void setFacing(short direction) {
        facing = direction;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
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
     * @return 0: none <br> 1: all <br> 2: one
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

    public boolean isPlayingRecord() {
        if (!worldObj.isRemote && CommonUtil.isServer()) return playing;
        return SoundSystemHelper.isPlaying(getIdentifier());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    public void markForUpdate() {
        worldObj.markBlockForUpdate(pos);
        markDirty();
    }

    public void nextRecord() {
        if (recordNumber++ >= getSizeInventory() - 1) recordNumber = 0;
    }

    public void playSelectedRecord() {
        if (getStackInSlot(recordNumber) == null) return;

        if (worldObj.isRemote) {
            PacketHandler.INSTANCE.sendToServer(new ClientPlayRecord(this));
            return;
        }

        lastPlayingRecord = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
        playing = true;

        waitTicks = 20;

        PacketHandler.INSTANCE.sendToAll(new ServerPlayRecord(this));
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
        setRepeatMode(RepeatMode.get(tag.getInteger("rptMode")));
        volume = tag.getFloat("volume");

        InventoryUtils.readItemStacksFromTag(items, tag.getTagList("inventory", 10));
    }

    public void resetPlayingRecord() {
        recordNumber = 0;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemstack) {
        items[slot] = itemstack;

        if (recordNumber == slot && isPlayingRecord() && itemstack == null)
            playSelectedRecord();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return null;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setRecordPlaying(int recordNumber) {
        final int oldRecord = this.recordNumber;
        this.recordNumber = recordNumber;
        if (isPlayingRecord() && oldRecord != recordNumber)
            playSelectedRecord();

        markForUpdate();
    }

    /**
     * @param mode 0: none <br> 1: all <br> 2: one
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
            PacketHandler.INSTANCE.sendToAll(new ServerAction(ClientAction.Action.STOP, this));
        else PacketHandler.INSTANCE.sendToServer(new ClientAction(ClientAction.Action.STOP, this));
    }

    @Override
    public void update() {
        if (!ClientUtil.isClient()) return;

        tick++;
        final Random random = worldObj.rand;

        if (tick % 5 == 0 && random.nextBoolean())
            if (isPlayingRecord()) {
                SoundSystemHelper.getSoundSystem().setVolume(getIdentifier(), volume * ClientUtil.mc().gameSettings.getSoundLevel(SoundCategory.RECORDS));
                SoundSystemHelper.stop("bgMusic");

                Particles particles = Particles.values()[random.nextInt(Particles.values().length)];
                particles.spawn(pos.getX() + random.nextDouble(), pos.getY() + 1.2D, pos.getZ() + random.nextDouble());
            }

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
            if (repeatMode == RepeatMode.ONE && !shuffle) playSelectedRecord();
            else if (repeatMode == RepeatMode.ALL && !shuffle) {
                nextRecord();
                if (recordNumber == getLastSlotWithItem() + 1)
                    resetPlayingRecord();
                playSelectedRecord();
            } else if (!shuffle) {
                stopPlayingRecord();
                resetPlayingRecord();
            }

            // send tile information to the server to update the other clients
            if (shuffle && repeatMode != RepeatMode.ONE) {
                PacketHandler.INSTANCE.sendToServer(new ClientShuffle(this));
            }
            markForUpdate();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("recordNumber", recordNumber);
        tag.setShort("facing", facing);
        tag.setInteger("rptMode", getReplayMode().ordinal());
        tag.setBoolean("shuffle", shuffle);
        tag.setFloat("volume", volume);
        tag.setTag("inventory", InventoryUtils.writeItemStacksToTag(items));
    }

    public String getIdentifier() {
        if (getStackInSlot(getCurrentRecordNumber()) == null) return "";

        return SoundSystemHelper.getIdentifierForRecord(((ItemRecord) getStackInSlot(getCurrentRecordNumber()).getItem()), pos.getX(), pos.getY(), pos.getZ());
    }

    /*@Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] getRecordName(Context context, Arguments args) {
        String s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
        s = s.replace("cazzar:kokoro", "Kagamine Rin - Kokoro")
                .replace("cazzar:love_is_war", "Hatsune Miku - Love Is war")
                .replace("cazzar:shibuya", "BECCA - SHIBUYA (Original)")
                .replace("cazzar:spica", "Hatsune Miku - SPiCa")
                .replace("cazzar:suki_daisuki", "Kagamine Rin - I Like You, I Love You")
                .replace("cazzar:we_are_popcandy", "Hatsune Miku - We are POPCANDY!");
        return new String[]{s};
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] isPlaying(Context context, Arguments args) {
        return new Object[]{isPlayingRecord()};
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] play(Context context, Arguments args) {
        playSelectedRecord();
        markForUpdate();
        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] stop(Context context, Arguments args) {
        stopPlayingRecord();
        markForUpdate();

        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] next(Context context, Arguments args) {
        boolean wasPlaying = isPlayingRecord();
        if (wasPlaying) stopPlayingRecord();
        if (shuffleEnabled()) {
            final Random random = new Random();
            if (getLastSlotWithItem() <= 0) return null;

            final int nextDisk = random.nextInt(getLastSlotWithItem());
            if (getCurrentRecordNumber() != nextDisk)
                setRecordPlaying(nextDisk);
        }
        nextRecord();
        if (wasPlaying) playSelectedRecord();
        markForUpdate();

        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] previous(Context context, Arguments args) {
        boolean wasPlaying = isPlayingRecord();

        if (wasPlaying) stopPlayingRecord();
        previousRecord();
        if (wasPlaying) playSelectedRecord();
        markForUpdate();

        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] setRepeatOne(Context context, Arguments args) {
        repeatMode = RepeatMode.ONE;
        markForUpdate();
        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] setRepeatNone(Context context, Arguments args) {
        repeatMode = RepeatMode.OFF;
        markForUpdate();
        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] setRepeatAll(Context context, Arguments args) {
        repeatMode = RepeatMode.ALL;
        markForUpdate();
        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] enableShuffle(Context context, Arguments args) {
        shuffle = true;
        markForUpdate();
        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] disableShuffle(Context context, Arguments args) {
        shuffle = false;
        markForUpdate();
        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] toggleShuffle(Context context, Arguments args) {
        shuffle = !shuffle;
        markForUpdate();
        return null;
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback
    public Object[] setVolume(Context context, Arguments args) {
        int vol = args.checkInteger(1);

        if (vol >= 1f)
            volume = 1f;
        else if (vol <= 0f) volume = 0f;
        else volume = vol;

        markForUpdate();
        return new Object[]{volume};
    }*/

    @Override
    public void addExtraNBTToPacket(NBTTagCompound tag) {
        tag.setBoolean("playing", playing);
    }

    @Override
    public void readExtraNBTFromPacket(NBTTagCompound tag) {
        if (tag.getBoolean("playing") && !playing)
            playSelectedRecord();
    }

    /*@Optional.Method(modid = "OpenComputers")
    @Override
    public String getComponentName() {
        return "jukebox";
    }*/
}
