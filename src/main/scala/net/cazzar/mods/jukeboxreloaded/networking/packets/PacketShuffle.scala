package net.cazzar.mods.jukeboxreloaded.networking.packets

import io.netty.buffer.ByteBuf
import net.minecraft.entity.player.EntityPlayer
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox

class PacketShuffle(var tile: TileJukebox) extends IPacket {
    var x, y, z: Int = 0
    override def executeServer(player: EntityPlayer) {
        tile = player.worldObj.getTileEntity(x, y, z).asInstanceOf[TileJukebox]
        tile.nextRecord()
    }
    override def executeClient(player: EntityPlayer) {}
    override def read(bytes: ByteBuf) {
        x = bytes.readInt()
        y = bytes.readInt()
        z = bytes.readInt()
    }
    override def write(bytes: ByteBuf) {
        bytes.writeInt(tile.identifier.posX)
        bytes.writeInt(tile.identifier.posY)
        bytes.writeInt(tile.identifier.posZ)
    }
}
