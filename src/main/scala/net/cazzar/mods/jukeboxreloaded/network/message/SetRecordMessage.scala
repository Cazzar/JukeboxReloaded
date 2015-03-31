package net.cazzar.mods.jukeboxreloaded.network.message

import io.netty.buffer.ByteBuf
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl._
import net.cazzar.mods.jukeboxreloaded.Util._
import net.minecraftforge.fml.relauncher.Side

class SetRecordMessage(var pos: BlockPos, var record: Int) extends IMessage{
  def this() = this(null, 0)

  override def toBytes(buf: ByteBuf): Unit = {
    buf.writePos(pos)
    buf.writeInt(record)
  }

  override def fromBytes(buf: ByteBuf): Unit = {
    pos = buf.readPos()
    record = buf.readInt()
  }
}

object SetRecordMessage {
  class Handler extends IMessageHandler[SetRecordMessage, IMessage] {
    override def onMessage(message: SetRecordMessage, ctx: MessageContext): IMessage = {
      ctx.side match {
        case Side.SERVER => {
          ctx.getServerHandler.playerEntity.worldObj.getTile[TileJukebox](message.pos).foreach(_.record = message.record)
          NetworkHandler.INSTANCE.sendToWorld(message, ctx.getServerHandler.playerEntity.worldObj)
        }
        case Side.CLIENT => JukeboxReloaded.proxy.getWorld.foreach(_.getTile[TileJukebox](message.pos).foreach(_.record = message.record))
      }
      null
    }


  }
}
