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

package net.cazzar.mods.jukeboxreloaded.util

import net.minecraft.util.StatCollector

object Strings extends Enumeration {

  val GUI_JUKEBOX_NAME = Val("gui.jukebox.name")
  val GUI_INVENTORY = Val("container.inventory")
  val TOOLTIP_PLAY = Val("gui.jukebox.play")
  val TOOLTIP_STOP = Val("gui.jukebox.stop")
  val TOOLTIP_NEXT = Val("gui.jukebox.next")
  val TOOLTIP_PREV = Val("gui.jukebox.prev")
  val TOOLTIP_REPEAT_ONE = Val("gui.jukebox.repeat.one")
  val TOOLTIP_REPEAT_ALL = Val("gui.jukebox.repeat.all")
  val TOOLTIP_REPEAT_NONE = Val("gui.jukebox.repeat.none")
  val TOOLTIP_SHUFFLE_ON = Val("gui.jukebox.shuffle.on")
  val TOOLTIP_SHUFFLE_OFF = Val("gui.jukebox.shuffle.off")
  val GUI_PORTABLE_JUKEBOX_NAME = Val("gui.portableJukebox.name")

  implicit def string(value: Val): String = value.toString

  protected case class Val(key: String) {
    implicit def string: String = toString

    override def toString = StatCollector.translateToLocal(key)
  }
}
