package net.cazzar.mods.jukeboxreloaded.proxy

import cpw.mods.fml.common.registry.GameRegistry
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import cpw.mods.fml.common.network.NetworkRegistry
import net.cazzar.corelib.network.DynamicPacketHandler
import net.cazzar.mods.jukeboxreloaded.packets.PacketPlayRecord
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.common.gui.GuiHandler
import net.cazzar.mods.jukeboxreloaded.common.Reference

class CommonProxy {
    def initConfig() = {}
    def initBlocks() = {
        GameRegistry registerBlock(new BlockJukebox, "jukebox")
        GameRegistry registerTileEntity(classOf[TileJukebox], "tileJukebox")
    }
    def initOther() = {}
    def initNetworking() = {
        NetworkRegistry.INSTANCE.newChannel("JukeboxReloaded", new DynamicPacketHandler(classOf[PacketPlayRecord]))
        NetworkRegistry.INSTANCE.registerGuiHandler(JukeboxReloaded, GuiHandler)
    }
}
