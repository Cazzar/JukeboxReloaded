package net.cazzar.mods.jukeboxreloaded

import cpw.mods.fml.common.{SidedProxy, Mod}
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy
import net.cazzar.corelib.lib.LogHelper

@Mod(modid = "JukeboxReloaded", modLanguage = "scala")
object JukeboxReloaded {
    val logger = LogHelper.getLogger(getClass)
    @SidedProxy(clientSide = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy", serverSide = "net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy")
    var proxy: CommonProxy = null

    @Mod.EventHandler
    def preInit(e: FMLPreInitializationEvent) = {
        proxy.initBlocks()
        proxy.initNetworking()
    }
}
