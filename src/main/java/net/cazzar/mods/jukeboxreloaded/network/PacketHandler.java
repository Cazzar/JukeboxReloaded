package net.cazzar.mods.jukeboxreloaded.network;

import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.cazzar.mods.jukeboxreloaded.network.packets.*;

import static net.cazzar.mods.jukeboxreloaded.lib.Reference.PacketsIDs.*;

public class PacketHandler extends FMLIndexedMessageToMessageCodec<PacketJukebox> {
    public PacketHandler() {
        addDiscriminator(JUKEBOX_DATA, PacketJukeboxDescription.class);
        addDiscriminator(CLIENT_UPDATE_TILE_JUKEBOX, PacketUpdateClientTile.class);
        addDiscriminator(SERVER_SHUFFLE_DISK, PacketShuffleDisk.class);
        addDiscriminator(PLAY_RECORD, PacketPlayRecord.class);
        addDiscriminator(STOP_RECORD, PacketStopPlaying.class);
//        addDiscriminator(STOP_ALL, PacketStopAllSounds.class);
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, PacketJukebox packet, ByteBuf data) throws Exception {
        packet.write(data);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf data, PacketJukebox packet) {
        packet.read(data);
        try {
            packet.execute(packet.getSender(), ctx.attr(NetworkRegistry.CHANNEL_SOURCE).get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
