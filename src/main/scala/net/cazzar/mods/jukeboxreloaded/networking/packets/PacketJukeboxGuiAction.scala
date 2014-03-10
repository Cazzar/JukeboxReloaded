package net.cazzar.mods.jukeboxreloaded.networking.packets

import net.cazzar.corelib.network.packets.IPacket
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import io.netty.buffer.ByteBuf
import cpw.mods.fml.common.network.ByteBufUtils._
import net.cazzar.corelib.util.ClientUtil
import net.minecraft.server.MinecraftServer
import net.cazzar.mods.jukeboxreloaded.common.Reference.GuiActions._
import net.cazzar.mods.jukeboxreloaded.common.ReplayMode._

class PacketJukeboxGuiAction(var tile: TileJukebox, var action: Int) extends IPacket {
    def this() = this(null, 0)

    def read(in: ByteBuf) = {
        action = in.readInt()
        val player = MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(readUTF8String(in))

        tile = player.worldObj.func_147438_o(in.readInt(), in.readInt(), in.readInt()).asInstanceOf[TileJukebox]

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

    def write(out: ByteBuf) = {
        out.writeInt(action)
        writeUTF8String(out, ClientUtil.mc().thePlayer.func_146103_bH.getName)
        //x
        //this.field_145851_c
        out.writeInt(tile.field_145851_c)
        //y
        //this.field_145848_d
        out.writeInt(tile.field_145848_d)
        out.writeInt(tile.field_145849_e)  //z
    }
}
