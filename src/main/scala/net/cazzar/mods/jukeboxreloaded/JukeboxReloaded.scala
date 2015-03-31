package net.cazzar.mods.jukeboxreloaded

import net.cazzar.corelib.lib.LogHelper
import net.cazzar.mods.jukeboxreloaded.proxy.IProxy
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.event.{FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}


@Mod(modid = JukeboxReloaded.MOD_ID, name = "Jukebox Reloaded", version = "@VERSION@", modLanguage = "scala")
object JukeboxReloaded {
  final val MOD_ID = "jukeboxreloaded"
  final val JUKEBOX_GUI_TEXTURE: ResourceLocation = new ResourceLocation(MOD_ID.toLowerCase, "textures/gui/jukebox.png")
  val logger = LogHelper.getLogger("JukeboxReloaded")

  @SidedProxy(clientSide = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy", serverSide = "net.cazzar.mods.jukeboxreloaded.proxy.ServerProxy") // since I only have client for now.
  var proxy: IProxy = null

  @Mod.EventHandler def preInit(e: FMLPreInitializationEvent) = proxy.preInit(e)

  @Mod.EventHandler def postInit(e: FMLPostInitializationEvent) = proxy.postInit(e)
}
