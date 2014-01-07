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

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @Author: Cayde
 */
public class ItemPortableJukebox extends Item {
    public ItemPortableJukebox() {
        super();
        setUnlocalizedName("PortableJukebox");
        setCreativeTab(JukeboxReloaded.proxy.creativeTab);

        setNoRepair();
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int worldX, int worldY, int worldZ, int side, float playerX, float playerY, float playerZ) {
        if (!world.isRemote) {
            player.openGui(JukeboxReloaded.instance(), GuiHandler.PORTABLE_JUKEBOX, world, worldX, worldY, worldZ);
        }
        return false;
    }

    public RepeatMode getRepeatMode() {
        return RepeatMode.get(RepeatMode.ALL.ordinal());
    }
}
