package net.cazzar.mods.jukeboxreloaded.network.message

import io.netty.buffer.ByteBuf
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.minecraft.item.Item
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.{MessageContext, IMessageHandler, IMessage}
import net.cazzar.mods.jukeboxreloaded.Util._

class ServerPlayMessage(var item: Item, var pos: BlockPos) extends IMessage {
  override def fromBytes(buf: ByteBuf): Unit = {
    item = buf.readItemStack()
    pos = buf.readPos()
  }

  override def toBytes(buf: ByteBuf): Unit = {
    buf.writeItemStack(item)
    buf.writePos(pos)
  }
}

object ServerPlayMessage {
  def apply(item: Item, pos: BlockPos) = new ServerPlayMessage(item, pos)

  class Handler extends IMessageHandler[ServerPlayMessage, IMessage] {
    override def onMessage(message: ServerPlayMessage, ctx: MessageContext): IMessage = {
      val world = ctx.getServerHandler.playerEntity.worldObj
      NetworkHandler.INSTANCE.sendToWorld(ClientPlayMessage(message.item, message.pos), world)

      null
    }
  }
}
