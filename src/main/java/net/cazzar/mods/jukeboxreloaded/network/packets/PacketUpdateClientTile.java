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

import static net.cazzar.mods.jukeboxreloaded.lib.Reference.JukeboxGUIActions.*;

public class PacketUpdateClientTile implements IPacket {

    // packet.writeCoord(this.getCoord());
    // packet.writeBoolean(this.isPlayingRecord());
    // packet.writeInt(this.getCurrentRecordNumber());

    int x, y, z;
    int action;
    int currentRecord;

    public PacketUpdateClientTile() {
    }

    public PacketUpdateClientTile(TileJukebox tile, int action) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
        this.action = action;
        currentRecord = tile.getCurrentRecordNumber();
    }

    @Override
    public void handleClient(EntityPlayer player) {
            final TileJukebox tile = (TileJukebox) player.worldObj
                    .getTileEntity(x, y, z);

            switch (action) {
                case PLAY:
                    tile.setPlaying(true);
                    break;

                case STOP:
                    tile.setPlaying(false);
                    break;

                case NEXT:
                case PREVIOUS:
                    tile.setRecordPlaying(currentRecord);
                    break;
            }
    }

    @Override
    public void handleServer(EntityPlayer player) {
        //quietly die
    }

    @Override
    public void read(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        action = in.readInt();
        currentRecord = in.readInt();
    }

    @Override
    public void write(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeInt(action);
        out.writeInt(currentRecord);
    }

}
