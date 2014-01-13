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

package net.cazzar.mods.jukeboxreloaded.network.packets;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketPlayRecord extends PacketJukebox {
    int x, y, z;
    String record;

    public PacketPlayRecord() {
    }

    public PacketPlayRecord(String record, int x, int y, int z) {
        this.record = record;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException {
        final TileEntity te = player.worldObj.func_147438_o(x, y, z);
        if (te instanceof TileJukebox) {
            TileJukebox t = ((TileJukebox) te);
            t.setPlaying(true);
            SoundSystemHelper.playRecord(player.worldObj, record, x, y, z);
        }
    }

    @Override
    public void read(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        record = readString(in);
    }

    @Override
    public void write(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        writeString(out, record);
    }
}
