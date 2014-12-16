package net.cazzar.mods.jukeboxreloaded.network.message

import io.netty.buffer.ByteBuf
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.Util._
import net.cazzar.mods.jukeboxreloaded.api.IPlayMethod
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.{MessageContext, IMessageHandler, IMessage}

class ClientPlayMessage(var item: Item, var pos: BlockPos) extends IMessage {
  def this() = this(null, null)
  
  override def fromBytes(buf: ByteBuf): Unit = {
    item = buf.readItemStack()
    pos = buf.readPos()
  }

  override def toBytes(buf: ByteBuf): Unit = {
    buf.writeItemStack(item)
    buf.writePos(pos)
  }
}

object ClientPlayMessage {
  def apply(is: Item, pos: BlockPos) = new ClientPlayMessage(is, pos)

  class Handler extends IMessageHandler[ClientPlayMessage, IMessage] {
    override def onMessage(message: ClientPlayMessage, ctx: MessageContext): IMessage = {
      if (!JukeboxReloaded.canBePlayed(message.item)) return null

      val player = JukeboxReloaded.getPlayerFor(message.item).asInstanceOf[IPlayMethod[Item]]
      player.play(message.item, message.pos)
      null
    }
  }
}
