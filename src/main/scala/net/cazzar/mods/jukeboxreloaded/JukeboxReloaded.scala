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
  logger.info("Like my mod? Consider supporting me on Patreon! http://www.patreon.com/cazzar") //Hey, at least this isn't ingame constantly.

  @SidedProxy(clientSide = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy", serverSide = "net.cazzar.mods.jukeboxreloaded.proxy.ServerProxy") // since I only have client for now.
  var proxy: IProxy = null

  @Mod.EventHandler def preInit(e: FMLPreInitializationEvent) = proxy.preInit(e)

  @Mod.EventHandler def postInit(e: FMLPostInitializationEvent) = proxy.postInit(e)
}
