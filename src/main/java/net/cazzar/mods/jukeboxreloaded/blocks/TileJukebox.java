/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.mods.jukeboxreloaded.blocks;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.lib.InventoryUtils;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.tile.SyncedTileEntity;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.corelib.util.CommonUtil;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.client.particles.Particles;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketPlayRecord;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketShuffleDisk;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketStopPlaying;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

import static cpw.mods.fml.common.network.FMLOutboundHandler.FML_MESSAGETARGET;
import static cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget.ALL;
import static cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget.TOSERVER;

public class TileJukebox extends SyncedTileEntity implements IInventory {
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

    public boolean isPlayingRecord() {
        return SoundSystemHelper.isPlaying(getIdentifier());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    public void markForUpdate() {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    public void nextRecord() {
        if (recordNumber++ >= getSizeInventory() - 1) recordNumber = 0;
    }

    public void playSelectedRecord() {
        if (worldObj.isRemote) {
            if (getStackInSlot(recordNumber) == null) return;
            FMLEmbeddedChannel channel = JukeboxReloaded.proxy().channel.get(Side.CLIENT);
            channel.attr(FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
            channel.writeAndFlush(new PacketPlayRecord(getStackInSlot(recordNumber), xCoord, yCoord, zCoord));
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

        FMLEmbeddedChannel channel = JukeboxReloaded.proxy().channel.get(Side.SERVER);
        channel.attr(FML_MESSAGETARGET).set(ALL);
        channel.writeAndFlush(new PacketPlayRecord(getStackInSlot(recordNumber), xCoord, yCoord, zCoord));
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
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
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
        FMLEmbeddedChannel channel = JukeboxReloaded.proxy().channel.get(CommonUtil.getSide());
        if (CommonUtil.isServer()) {
            channel.attr(FML_MESSAGETARGET).set(ALL);
            channel.writeAndFlush(new PacketStopPlaying(getIdentifier(), xCoord, yCoord, zCoord));
        } else {
            channel.attr(FML_MESSAGETARGET).set(TOSERVER);
            channel.writeAndFlush(new PacketStopPlaying(getIdentifier(), xCoord, yCoord, zCoord));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateEntity() {
        tick++;
        final Random random = new Random();

        if (tick % 5 == 0 && random.nextBoolean())
            if (isPlayingRecord()) {
                SoundSystemHelper.getSoundSystem().setVolume(getIdentifier(), volume * ClientUtil.mc().gameSettings.getSoundLevel(SoundCategory.RECORDS));
                SoundSystemHelper.stop("bgMusic");

                Particles particles = Particles.values()[random.nextInt(Particles.values().length)];
                particles.spawn(xCoord + random.nextDouble(), yCoord + 1.2D, zCoord + random.nextDouble());
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
            if (shuffle && repeatMode != RepeatMode.ONE) {
                FMLEmbeddedChannel channel = JukeboxReloaded.proxy().channel.get(Side.CLIENT);
                channel.attr(FML_MESSAGETARGET).set(TOSERVER);
                channel.writeAndFlush(new PacketShuffleDisk(this));
            }
            markForUpdate();
            markDirty();
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
        return SoundSystemHelper.getIdentifierForRecord(((ItemRecord) getStackInSlot(getCurrentRecordNumber()).getItem()), xCoord, yCoord, zCoord);
//        return xCoord + ":" + yCoord + ":" + zCoord;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getServerSideRecordName() {
        String s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            s = ((ItemRecord) getStackInSlot(recordNumber).getItem()).recordName;
        s = s.replace("cazzar:kokoro", "Kagamine Rin - Kokoro")
                .replace("cazzar:love_is_war", "Hatsune Miku - Love Is war")
                .replace("cazzar:shibuya", "BECCA - SHIBUYA (Original)")
                .replace("cazzar:spica", "Hatsune Miku - SPiCa")
                .replace("cazzar:suki_daisuki", "Kagamine Rin - I Like You, I Love You")
                .replace("cazzar:we_are_popcandy", "Hatsune Miku - We are POPCANDY!");
        return s;
    }


    @SuppressWarnings("UnusedDeclaration")
    private void dropItemInWorld(ItemStack itemStack) {
        Random rand = new Random();

        if (itemStack != null && itemStack.stackSize > 0) {
            final float dX = rand.nextFloat() * 0.8F + 0.1F;
            final float dY = rand.nextFloat() * 0.8F + 0.1F;
            final float dZ = rand.nextFloat() * 0.8F + 0.1F;

            final EntityItem entityItem = new EntityItem(worldObj, xCoord + dX, yCoord + dY, zCoord + dZ, new ItemStack(itemStack.getItem(), itemStack.stackSize, itemStack.getItemDamage()));

            if (itemStack.hasTagCompound())
                entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());

            final float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * factor;
            worldObj.spawnEntityInWorld(entityItem);
            itemStack.stackSize = 0;
        }
    }

    @Override
    public void addExtraNBTToPacket(NBTTagCompound tag) {
        tag.setBoolean("playing", playing);
    }

    @Override
    public void readExtraNBTFromPacket(NBTTagCompound tag) {
        if (tag.getBoolean("playing") && !playing)
            playSelectedRecord();
    }
}
