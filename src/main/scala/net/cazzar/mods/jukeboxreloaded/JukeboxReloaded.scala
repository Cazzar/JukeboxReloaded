package net.cazzar.mods.jukeboxreloaded

import cpw.mods.fml.common.{SidedProxy, Mod}
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy

@Mod(modid = "JukeboxReloaded", modLanguage = "scala")
object JukeboxReloaded {
    @SidedProxy(clientSide = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy", serverSide = "net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy")
    var proxy: CommonProxy = null

    @Mod.EventHandler
    def preInit(e: FMLPreInitializationEvent) = {
        proxy.initBlocks()
        proxy.initNetworking()
    }
}
