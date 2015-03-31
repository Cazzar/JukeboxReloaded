package net.cazzar.mods.jukeboxreloaded.proxy

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.cazzar.mods.jukeboxreloaded.network.gui.GuiHandler
import net.minecraft.world.World
import net.minecraftforge.fml.common.event.{FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry

/**
 * Created by Cayde on 15/12/2014.
 */
trait IProxy {
  def preInit(e: FMLPreInitializationEvent) = {
    registerBlocks()
    registerTileEntity()
    registerNetwork()
  }

  def postInit(e: FMLPostInitializationEvent): Unit = {}

  def registerBlocks(): Unit = {
    GameRegistry.registerBlock(BlockJukebox, "jukebox")
  }

  def registerTileEntity(): Unit = {
    GameRegistry.registerTileEntity(classOf[TileJukebox], "tileJukebox")
  }

  def registerNetwork(): Unit = {
    NetworkHandler.init()
    NetworkRegistry.INSTANCE.registerGuiHandler(JukeboxReloaded, GuiHandler)
  }

  def getWorld: Option[World] = None
}
