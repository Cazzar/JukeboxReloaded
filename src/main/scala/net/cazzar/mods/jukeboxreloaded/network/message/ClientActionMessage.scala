package net.cazzar.mods.jukeboxreloaded.network.message

import io.netty.buffer.ByteBuf
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.{MessageContext, IMessageHandler, IMessage}
import net.cazzar.mods.jukeboxreloaded.Util._

class ClientActionMessage(var action: Action.Value, var pos: BlockPos) extends IMessage {
  var currentRecord = 0

  def this() = this(null, null)

  override def fromBytes(buf: ByteBuf): Unit = {
    pos = buf.readPos()
    action = Action(buf.readByte())
  }

  override def toBytes(buf: ByteBuf): Unit = {
    buf.writePos(pos)
    buf.writeByte(action.id)
  }
}

object ClientActionMessage {
  class Handler extends IMessageHandler[ClientActionMessage, IMessage] {
    override def onMessage(message: ClientActionMessage, ctx: MessageContext): IMessage = {
      val newMessage = new ServerActionMessage(message.action, message.pos)
      newMessage.currentRecord = message.currentRecord

      val world = ctx.getServerHandler.playerEntity.worldObj
      NetworkHandler.INSTANCE.sendToWorld(newMessage, world)

      val maybeJukebox = world.getTile[TileJukebox](message.pos)
      if (message.action == Action.PLAY) maybeJukebox.foreach(_.setServerPlayingStatus(true))
      if (message.action == Action.STOP) maybeJukebox.foreach(_.setServerPlayingStatus(false))

      null
    }
  }
}
