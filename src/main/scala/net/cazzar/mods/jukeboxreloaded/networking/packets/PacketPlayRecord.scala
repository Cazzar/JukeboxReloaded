package net.cazzar.mods.jukeboxreloaded.networking.packets

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import io.netty.buffer.ByteBuf
import cpw.mods.fml.common.network.ByteBufUtils._
import net.cazzar.corelib.util.ClientUtil.mc
import net.cazzar.corelib.util.CommonUtil.getSide
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer

class PacketPlayRecord(var tile: TileJukebox, var record: String) extends IPacket {
    var x, y, z: Int = 0

    def this() = this(null, null)

    def read(in: ByteBuf) = {

        x = in.readInt()
        y = in.readInt()
        z = in.readInt()
        record = readUTF8String(in)
    }

    def write(out: ByteBuf) = {
        out.writeInt(tile.xCoord)
        out.writeInt(tile.yCoord)
        out.writeInt(tile.zCoord)
    }

    override def executeServer(player: EntityPlayer): Unit = player.worldObj.playRecord(record, x, y, z)

    override def executeClient(player: EntityPlayer): Unit = throw new RuntimeException("Cannot send this packet to the client")
}
