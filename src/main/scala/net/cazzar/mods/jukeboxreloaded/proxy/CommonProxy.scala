package net.cazzar.mods.jukeboxreloaded.proxy

import cpw.mods.fml.common.registry.GameRegistry
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import cpw.mods.fml.common.network.{FMLEmbeddedChannel, NetworkRegistry}
import net.cazzar.corelib.network.DynamicPacketHandler
import net.cazzar.mods.jukeboxreloaded.packets.{PacketJukeboxGuiAction, PacketPlayRecord}
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.common.gui.GuiHandler
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.cazzar.corelib.creative.GenericCreativeTab
import net.minecraft.init.Blocks
import java.util
import cpw.mods.fml.relauncher.Side

class CommonProxy {
    var jukebox: BlockJukebox = null
    var channel: util.EnumMap[Side, FMLEmbeddedChannel] = null
    var tab: CreativeTabs = null

    def initConfig() = {}

    def initBlocks() = {
        jukebox = new BlockJukebox
        GameRegistry registerBlock(jukebox, "jukebox")
        GameRegistry registerTileEntity(classOf[TileJukebox], "tileJukebox")

        tab = new GenericCreativeTab("Jukebox Reloaded", Item.func_150898_a(jukebox))
        jukebox.func_149647_a(tab)
    }

    def initOther() = {}

    def initNetworking() = {
        channel = NetworkRegistry.INSTANCE.newChannel("JukeboxReloaded", new DynamicPacketHandler(classOf[PacketPlayRecord], classOf[PacketJukeboxGuiAction]))
        NetworkRegistry.INSTANCE.registerGuiHandler(JukeboxReloaded, GuiHandler)
    }
}
