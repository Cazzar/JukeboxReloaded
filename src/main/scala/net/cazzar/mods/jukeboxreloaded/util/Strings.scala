package net.cazzar.mods.jukeboxreloaded.util

import net.minecraft.util.StatCollector

object Strings extends Enumeration {
  protected case class Val(key: String) {
    override def toString = StatCollector.translateToLocal(key)
  }

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
}
