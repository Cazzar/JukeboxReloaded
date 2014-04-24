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

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.network.packets.IPacket;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class PacketPlayRecord implements IPacket {
    int x, y, z;
    ItemStack record;

    public PacketPlayRecord() {
    }

    public PacketPlayRecord(ItemStack record, int x, int y, int z) {
        this.record = record;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void handleServer(EntityPlayer player) {
        JukeboxReloaded.proxy().channel.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        JukeboxReloaded.proxy().channel.get(Side.SERVER).writeAndFlush(this);// just reuse me.
    }

    @Override
    public void handleClient(EntityPlayer player) {
        final TileEntity te = player.worldObj.getTileEntity(x, y, z);
        if (te instanceof TileJukebox) {
            TileJukebox t = ((TileJukebox) te);
            t.setPlaying(true);
            SoundSystemHelper.playRecord((ItemRecord) record.getItem(), x, y, z, t.getIdentifier());
        }
    }

    @Override
    public void read(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        record = ByteBufUtils.readItemStack(in);
    }

    @Override
    public void write(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        ByteBufUtils.writeItemStack(out, record);
    }
}
