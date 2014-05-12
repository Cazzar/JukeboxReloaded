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
