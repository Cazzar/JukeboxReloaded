/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.mods.jukeboxreloaded.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @Author: Cayde
 */
public class PortableJukeboxHelper {
    EntityPlayer ent;
    ItemStack stack;

    public PortableJukeboxHelper(EntityPlayer player, ItemStack item) {
        ent = player;
        stack = item;
    }

    public int getSelectedRecord() {
        if (stack == null) return 9;
        NBTTagCompound tags = stack.getTagCompound();
        if (tags == null) {
            stack.setTagCompound(tags = new NBTTagCompound());
        }
        int i = tags.getInteger("selectedrecord");
        if (i < 9 || i > 35) i = 9;

        return i;
    }

    public void setSelectedRecord(int record) {

        NBTTagCompound tags = stack.getTagCompound();
        if (tags == null) {
            stack.setTagCompound(tags = new NBTTagCompound());
        }
        tags.setInteger("selectedrecord", record);
    }

    public float getVolume() {
        NBTTagCompound tags = stack.getTagCompound();
        if (tags == null) {
            stack.setTagCompound(tags = new NBTTagCompound());
        }
        return tags.getFloat("volume");
    }

    /*public void play() {
        if (SoundSystemHelper.isPlaying(SoundSystemHelper.getEntityChannel(ent)))
            SoundSystemHelper.stop(SoundSystemHelper.getEntityChannel(ent));

        ItemStack item;
        item = ent.inventory.getStackInSlot(getSelectedRecord());

        if (item == null) return;
        if (!(item.getItem() instanceof ItemRecord)) {
            return;
        }
        String record = ((ItemRecord) item.getItem()).getRecordTitle();

        SoundSystemHelper.playRecordAtEntity(ent, record, getVolume());
    }

    public void stop() {
        SoundSystemHelper.stop(SoundSystemHelper.getEntityChannel(ent));
    }*/

    public void next() {
        for (int ii = 0; ii < 2; ii++) {
            for (int i = getSelectedRecord(); i <= 35; i++) {
                if (i == 35) setSelectedRecord(0);
                ItemStack itemStack = ent.inventory.getStackInSlot(i);

                if (itemStack == null) continue;

                if (!(itemStack.getItem() instanceof ItemRecord)) continue;

                setSelectedRecord(i);
//                stop();
//                play();
                return;
            }
        }
    }

    public void prev() {
        for (int ii = 0; ii < 2; ii++) {
            for (int i = getSelectedRecord(); i <= 9; i--) {
                ItemStack itemStack = ent.inventory.getStackInSlot(i);
                if (i == 9) setSelectedRecord(0);
                if (itemStack == null) continue;

                if (!(itemStack.getItem() instanceof ItemRecord)) continue;

                setSelectedRecord(i);
//                stop();
//                play();
                return;
            }
        }
    }
}
