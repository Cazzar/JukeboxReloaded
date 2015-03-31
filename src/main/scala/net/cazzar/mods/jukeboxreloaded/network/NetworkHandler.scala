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

package net.cazzar.mods.jukeboxreloaded.network

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.network.message._
import net.minecraftforge.fml.relauncher.Side

object NetworkHandler {
  val INSTANCE = net.minecraftforge.fml.common.network.NetworkRegistry.INSTANCE.newSimpleChannel(JukeboxReloaded.MOD_ID)
  private var initialised = false

  def init(): Unit = {
    if (initialised) return

    initialised = true
    INSTANCE.registerMessage(classOf[ClientActionMessage.Handler], classOf[ClientActionMessage], 0, Side.SERVER)
    INSTANCE.registerMessage(classOf[ServerActionMessage.Handler], classOf[ServerActionMessage], 1, Side.CLIENT)
    INSTANCE.registerMessage(classOf[SetRecordMessage.Handler], classOf[SetRecordMessage], 2, Side.CLIENT)
    INSTANCE.registerMessage(classOf[SetRecordMessage.Handler], classOf[SetRecordMessage], 3, Side.SERVER)

    //    INSTANCE.registerMessage(classOf[ClientPlay.Handler], classOf[ClientPlay], 0, CLIENT)
    //    INSTANCE.registerMessage(classOf[ServerPlay.Handler], classOf[ServerPlay], 1, SERVER)
  }
}
