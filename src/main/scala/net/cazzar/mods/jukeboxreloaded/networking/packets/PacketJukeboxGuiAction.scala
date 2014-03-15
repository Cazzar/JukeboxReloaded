package net.cazzar.mods.jukeboxreloaded.networking.packets

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import io.netty.buffer.ByteBuf
import net.cazzar.mods.jukeboxreloaded.common.Reference.GuiActions._
import net.cazzar.mods.jukeboxreloaded.common.ReplayMode._
import net.minecraft.entity.player.EntityPlayer

class PacketJukeboxGuiAction(var tile: TileJukebox, var action: Int) extends IPacket {
    var x, y, z = 0

    def this() = this(null, 0)

    def read(in: ByteBuf) = {
        action = in.readInt()
        //        val player = MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(readUTF8String(in))

        //        tile = player.worldObj.func_147438_o(in.readInt(), in.readInt(), in.readInt()).asInstanceOf[TileJukebox]

        x = in.readInt()
        y = in.readInt()
        z = in.readInt()
    }

    def write(out: ByteBuf) = {
        out.writeInt(action)
        //        writeUTF8String(out, ClientUtil.mc().thePlayer.func_146103_bH.getName)
        //x
        //this.field_145851_c
        out.writeInt(tile.xCoord)
        //y
        //this.field_145848_d
        out.writeInt(tile.yCoord)
        out.writeInt(tile.zCoord) //z
    }

    override def executeServer(player: EntityPlayer): Unit = {
        tile = player.worldObj.getTileEntity(x, y, z).asInstanceOf[TileJukebox]

        action match {
            case PLAY => tile.playSelectedRecord()
            case STOP => tile.stopPlayingRecord()
            case NEXT => tile.nextRecord()
            case PREVIOUS => tile.previousRecord()
            case REPEAT_ONE => tile.replayMode = ONE
            case REPEAT_ALL => tile.replayMode = ALL
            case REPEAT_OFF => tile.replayMode = OFF
            case SHUFFLE => tile.shuffle = true
            case SHUFFLE_OFF => tile.shuffle = false

            case _ => null
        }

        tile.markForUpdate()
    }

    override def executeClient(player: EntityPlayer): Unit = {
        executeServer(player)
    }
}
