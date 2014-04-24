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

package net.cazzar.mods.jukeboxreloaded.network.packets;

import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.network.packets.IPacket;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.minecraft.entity.player.EntityPlayer;

public class PacketStopAllSounds implements IPacket {

    public PacketStopAllSounds() {
    }

    @Override
    public void handleClient(EntityPlayer player) {
            SoundSystemHelper.getSoundManager().stopAllSounds();
    }

    @Override
    public void handleServer(EntityPlayer player) {
        //quietly ignore it.
        JukeboxReloaded.logger.debug("Recieved a packet I should not have!");
    }

    @Override
    public void read(ByteBuf in) {
    }

    @Override
    public void write(ByteBuf out) {
    }

}
