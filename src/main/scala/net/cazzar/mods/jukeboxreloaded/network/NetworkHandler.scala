package net.cazzar.mods.jukeboxreloaded.network

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.network.message.ClientPlayMessage
import net.minecraftforge.fml.relauncher.Side

object NetworkHandler {
  val INSTANCE = net.minecraftforge.fml.common.network.NetworkRegistry.INSTANCE.newSimpleChannel(JukeboxReloaded.MOD_ID)
  private var initalized = false

  def init(): Unit = {
    if (initalized) return

    initalized = true
    INSTANCE.registerMessage(classOf[ClientPlayMessage.Handler], classOf[ClientPlayMessage], 0, Side.CLIENT)
  }
}
