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
