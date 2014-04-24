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

package net.cazzar.mods.jukeboxreloaded.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    public static final int JUKEBOX = 0;
    public static final int PORTABLE_JUKEBOX = 1;

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        switch (ID) {
            case JUKEBOX:
                if (!(tile instanceof TileJukebox)) return null;
                return new GUIJukebox(player, (TileJukebox) tile);
            case PORTABLE_JUKEBOX:
                return new GuiPortableJukebox(player);
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        final TileEntity tile = world.getTileEntity(x, y, z);

        switch (ID) {
            case JUKEBOX:
                if (!(tile instanceof TileJukebox)) return null;
                return new ContainerJukebox(player.inventory, (TileJukebox) tile);
            case PORTABLE_JUKEBOX:
                return new ContainerPortableJukebox(player.inventory);
            default:
                return null;
        }
    }

}
