package net.cazzar.mods.jukeboxreloaded.packets

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.corelib.network.packets.IPacket
import io.netty.buffer.ByteBuf
import cpw.mods.fml.common.network.ByteBufUtils._
import net.cazzar.corelib.util.ClientUtil.mc
import net.cazzar.corelib.util.CommonUtil.getSide
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer

class PacketPlayRecord(var tile: TileJukebox, var record: String) extends IPacket {
    def this() = this(null, null)

    def read(in: ByteBuf) = {
        var x, y, z: Int = 0
        x = in.readInt()
        y = in.readInt()
        z = in.readInt()
        record = readUTF8String(in)

        if (getSide.isServer) {
            val player: EntityPlayer = MinecraftServer.getServer.getConfigurationManager.getPlayerForUsername(readUTF8String(in))
            player.worldObj.playRecord(record, x, y, z)
        } else {
            throw new RuntimeException("Cannot send this packet to the client")
        }
    }

    def write(out: ByteBuf) = {
        out.writeInt(tile.identifier().posX)
        out.writeInt(tile.identifier().posY)
        out.writeInt(tile.identifier().posZ)
        writeUTF8String(out, record)
        writeUTF8String(out, mc.thePlayer.func_146103_bH.getName)
    }
}
