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
import net.cazzar.corelib.network.packets.IPacket;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class PacketShuffleDisk implements IPacket {

    int x, y, z;

    public PacketShuffleDisk() {
    }

    public PacketShuffleDisk(TileJukebox tile) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }

    @Override
    public void handleServer(EntityPlayer player) {
        final TileEntity tile = player.worldObj.getTileEntity(x, y, z);
            if (tile instanceof TileJukebox) {
                final TileJukebox jukeBox = (TileJukebox) tile;
                final Random random = new Random();
                if (jukeBox.getLastSlotWithItem() <= 0) return;
                final int nextDisk = random.nextInt(jukeBox
                        .getLastSlotWithItem());
                if (jukeBox.getCurrentRecordNumber() != nextDisk)
                    jukeBox.setRecordPlaying(nextDisk);
                ((TileJukebox) tile).markForUpdate();
            }
    }

    @Override
    public void handleClient(EntityPlayer player) {
        //noop
    }

    @Override
    public void read(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    @Override
    public void write(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }
}
