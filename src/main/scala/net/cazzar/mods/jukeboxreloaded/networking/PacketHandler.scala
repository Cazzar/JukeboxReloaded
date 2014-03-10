package net.cazzar.mods.jukeboxreloaded.networking

import cpw.mods.fml.common.network.{NetworkRegistry, FMLIndexedMessageToMessageCodec}
import net.cazzar.mods.jukeboxreloaded.networking.packets.IPacket
import io.netty.channel.ChannelHandlerContext
import io.netty.buffer.ByteBuf
import net.cazzar.corelib.util.{ClientUtil, CommonUtil}
import cpw.mods.fml.relauncher.Side
import net.minecraft.network.NetHandlerPlayServer

class PacketHandler extends FMLIndexedMessageToMessageCodec[IPacket]{
    override def decodeInto(ctx: ChannelHandlerContext, source: ByteBuf, msg: IPacket) = msg.write(source)

    override def encodeInto(ctx: ChannelHandlerContext, msg: IPacket, target: ByteBuf) = {
        msg.read(target)
        CommonUtil.getSide match {
            case Side.CLIENT => msg.executeClient(ClientUtil.mc().thePlayer)
            case Side.SERVER => msg.executeServer(ctx.channel.attr(NetworkRegistry.NET_HANDLER).get().asInstanceOf[NetHandlerPlayServer].playerEntity)
        }
    }
}
