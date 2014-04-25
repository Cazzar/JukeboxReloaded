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

package net.cazzar.mods.jukeboxreloaded.network;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.cazzar.corelib.network.packets.IPacket;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.corelib.util.CommonUtil;
import net.cazzar.mods.jukeboxreloaded.network.packets.*;
import net.minecraft.network.NetHandlerPlayServer;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
    public static Marker marker = MarkerManager.getMarker("JukeboxReloaded-Packets");

    public PacketHandler() {
        addDiscriminator(0, PacketPlayRecord.class);
        addDiscriminator(1, PacketShuffleDisk.class);
        addDiscriminator(2, PacketStopAllSounds.class);
        addDiscriminator(3, PacketStopPlaying.class);
        addDiscriminator(4, PacketUpdateClientTile.class);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, IPacket packet, ByteBuf bytes) throws Exception {
        packet.write(bytes);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf bytes, IPacket packet) {
        packet.read(bytes);
        switch (CommonUtil.getSide()) {
            case CLIENT:
                packet.handleClient(ClientUtil.mc().thePlayer);
                break;
            case SERVER:
                packet.handleServer(((NetHandlerPlayServer) ctx.channel().attr(NetworkRegistry.NET_HANDLER).get()).playerEntity);
                break;
        }
    }
}
