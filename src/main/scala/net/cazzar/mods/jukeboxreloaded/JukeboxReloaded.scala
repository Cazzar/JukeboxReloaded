package net.cazzar.mods.jukeboxreloaded

import net.cazzar.corelib.lib.LogHelper
import net.cazzar.mods.jukeboxreloaded.api.IPlayMethod
import net.cazzar.mods.jukeboxreloaded.proxy.IProxy
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.event.{FMLPostInitializationEvent, FMLInterModComms, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}

import scala.collection.mutable


@Mod(modid = JukeboxReloaded.MOD_ID, name = "Jukebox Reloaded", version = "@VERSION@", modLanguage = "scala")
object JukeboxReloaded {
  final val MOD_ID = "jukeboxreloaded"
  final val JUKEBOX_GUI_TEXTURE: ResourceLocation = new ResourceLocation(MOD_ID.toLowerCase, "textures/gui/jukebox.png")
  val logger = LogHelper.getLogger("JukeboxReloaded")
  private val players = new mutable.MutableList[IPlayMethod[_ <: Item]]

  @SidedProxy(clientSide = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy", serverSide = "net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy") // since I only have client for now.
  var proxy: IProxy = null

  @Mod.EventHandler
  def preInit(e: FMLPreInitializationEvent): Unit = {
    players += RecordPlayMethod
    //    players.put(classOf[ItemCustomRecord], RecordPlayMethod)

    proxy.preInit(e)
  }

  @Mod.EventHandler
  def postInit(e: FMLPostInitializationEvent) = proxy.postInit(e)

  def interComms(e: FMLInterModComms.IMCMessage): Unit = {
    /*
    {
      "players": [
        {
          "item": is
          "player": clazz
        },
        ...
      ]
    }
     */

    if (!e.isNBTMessage) return

    val nbt = e.getNBTValue

    val players = nbt.getTagList("players", 10)

    for (idx <- 0 to players.tagCount()) {
      this.players += Class.forName(players.getStringTagAt(idx)).newInstance().asInstanceOf[IPlayMethod[_ <: Item]]
  }
  }

  def getPlayerFor(item: Item): IPlayMethod[Item] = {
    for (player <- players) {
      if (player.canPlay(item)) return player.asInstanceOf[IPlayMethod[Item]]
    }

    null
  }

  def canBePlayed(item: Item): Boolean = {
    for (player <- players) if (player.canPlay(item)) return true
    false
  }
}
