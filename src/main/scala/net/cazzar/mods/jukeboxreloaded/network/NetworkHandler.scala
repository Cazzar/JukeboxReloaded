package net.cazzar.mods.jukeboxreloaded.network

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.network.message._
import net.minecraftforge.fml.relauncher.Side

//import net.cazzar.mods.jukeboxreloaded.network.message.{ServerPlay, ClientPlay}

object NetworkHandler {
  val INSTANCE = net.minecraftforge.fml.common.network.NetworkRegistry.INSTANCE.newSimpleChannel(JukeboxReloaded.MOD_ID)
  private var initialised = false

  def init(): Unit = {
    if (initialised) return

    initialised = true
    INSTANCE.registerMessage(classOf[ClientActionMessage.Handler], classOf[ClientActionMessage], 0, Side.SERVER)
    INSTANCE.registerMessage(classOf[ServerActionMessage.Handler], classOf[ServerActionMessage], 1, Side.CLIENT)

//    INSTANCE.registerMessage(classOf[ClientPlay.Handler], classOf[ClientPlay], 0, CLIENT)
//    INSTANCE.registerMessage(classOf[ServerPlay.Handler], classOf[ServerPlay], 1, SERVER)
  }
}
