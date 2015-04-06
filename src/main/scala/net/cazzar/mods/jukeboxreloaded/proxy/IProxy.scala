/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.cazzar.mods.jukeboxreloaded.proxy

import net.cazzar.corelib.creative.GenericCreativeTab
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.Util._
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.cazzar.mods.jukeboxreloaded.network.gui.GuiHandler
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.common.event.{FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.ShapedOreRecipe

trait IProxy {
  def preInit(e: FMLPreInitializationEvent) = {
    registerBlocks()

    val tab = new GenericCreativeTab("Jukebox Reloaded", BlockJukebox)
    BlockJukebox.setCreativeTab(tab)

    registerTileEntity()
    registerNetwork()

    GameRegistry.addRecipe(new ShapedOreRecipe(BlockJukebox,
      "WCW",
      "NJN",
      "WWW",
      Char.box('W'), "plankWood",
      Char.box('C'), new ItemStack(Blocks.chest),
      Char.box('J'), new ItemStack(Blocks.jukebox),
      Char.box('N'), new ItemStack(Blocks.noteblock)
    ))


  }

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

  def postInit(e: FMLPostInitializationEvent): Unit = {}

  def getWorld: Option[World] = None
}
