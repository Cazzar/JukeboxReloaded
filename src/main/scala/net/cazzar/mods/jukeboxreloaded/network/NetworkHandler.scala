package net.cazzar.mods.jukeboxreloaded.network

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.network.message.{ServerPlayMessage, ClientPlayMessage}
import net.minecraftforge.fml.relauncher.Side.{CLIENT, SERVER}

object NetworkHandler {
  val INSTANCE = net.minecraftforge.fml.common.network.NetworkRegistry.INSTANCE.newSimpleChannel(JukeboxReloaded.MOD_ID)
  private var initalized = false

  def init(): Unit = {
    if (initalized) return

    initalized = true
    INSTANCE.registerMessage(classOf[ClientPlayMessage.Handler], classOf[ClientPlayMessage], 0, CLIENT)
    INSTANCE.registerMessage(classOf[ServerPlayMessage.Handler], classOf[ServerPlayMessage], 1, SERVER)
  }
}
