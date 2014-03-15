package net.cazzar.mods.jukeboxreloaded.networking

import cpw.mods.fml.common.network.{NetworkRegistry, FMLIndexedMessageToMessageCodec}
import net.cazzar.mods.jukeboxreloaded.networking.packets.{PacketPlayRecord, PacketJukeboxGuiAction, IPacket}
import io.netty.channel.ChannelHandlerContext
import io.netty.buffer.ByteBuf
import net.cazzar.corelib.util.{ClientUtil, CommonUtil}
import cpw.mods.fml.relauncher.Side
import net.minecraft.network.NetHandlerPlayServer

class PacketHandler extends FMLIndexedMessageToMessageCodec[IPacket]{
    addDiscriminator(0, classOf[PacketJukeboxGuiAction])
    addDiscriminator(1, classOf[PacketPlayRecord])

    override def decodeInto(ctx: ChannelHandlerContext, source: ByteBuf, msg: IPacket) = {
        msg.read(source)
        val player = ctx.channel.attr(NetworkRegistry.NET_HANDLER).get().asInstanceOf[NetHandlerPlayServer].playerEntity
        if (player.worldObj.isRemote) msg.executeServer(player)
        else msg.executeClient(player)
    }

    override def encodeInto(ctx: ChannelHandlerContext, msg: IPacket, target: ByteBuf) = {
        msg.write(target)
    }
}
