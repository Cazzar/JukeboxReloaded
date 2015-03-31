package net.cazzar.mods.jukeboxreloaded.network.message

import io.netty.buffer.ByteBuf
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
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
      val tile = ctx.getServerHandler.playerEntity.worldObj.getTile[TileJukebox](message.pos)
      message.action match {
        case Action.PLAY => tile.foreach(r => {
          r.record = message.currentRecord
          r.playRecord(fromServer = true)
        })
        case Action.STOP => tile.foreach(_.stopPlayingRecord(serious = true))
        case Action.NEXT => tile.foreach(_.nextRecord())
        case Action.PREVIOUS => tile.foreach(_.prevRecord())
        case Action.SHUFFLE_OFF => tile.foreach(_.shuffle = true)
        case Action.SHUFFLE_ON => tile.foreach(_.shuffle = false)
        case Action.REPEAT_ALL => tile.foreach(_.repeatMode = TileJukebox.RepeatMode.ALL)
        case Action.REPEAT_NONE => tile.foreach(_.repeatMode = TileJukebox.RepeatMode.NONE)
        case Action.REPEAT_ONE => tile.foreach(_.repeatMode = TileJukebox.RepeatMode.ONE)
      }
      
      null
    }
  }
}
