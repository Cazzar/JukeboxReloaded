package net.cazzar.mods.jukeboxreloaded.networking.packets

import io.netty.buffer.ByteBuf
import net.minecraft.entity.player.EntityPlayer

trait IPacket {
    def write(bytes: ByteBuf)
    def read(bytes: ByteBuf)
    def executeClient(player: EntityPlayer)
    def executeServer(player: EntityPlayer)
}
