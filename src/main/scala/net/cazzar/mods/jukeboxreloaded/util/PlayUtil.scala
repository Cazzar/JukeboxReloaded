package net.cazzar.mods.jukeboxreloaded.util

import net.cazzar.corelib.lib.SoundSystemHelper
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.Util._
import net.cazzar.mods.jukeboxreloaded.api.IJukeboxPlayable
import net.minecraft.item.{ItemRecord, ItemStack}
import net.minecraft.util.BlockPos

object PlayUtil {
  def canBePlayed(item: ItemStack, pos: BlockPos): Boolean = Option(item).map(_.getItem).orNull match {
    case _: ItemRecord => true
    case playable: IJukeboxPlayable => playable.canPlay(item)
    case _ => false
  }

  def stop(item: ItemStack, pos: BlockPos) = Option(item).map(_.getItem).orNull match {
    case record: ItemRecord => SoundSystemHelper.stop(record.identifier(pos))
    case playable: IJukeboxPlayable => playable.stop(item, pos)
    case _ => if (item != null) JukeboxReloaded.logger.error("Attempted to stop an item of {} class", item.getClass)
  }

  def play(item: ItemStack, pos: BlockPos) = Option(item).map(_.getItem).orNull match {
    case record: ItemRecord => SoundSystemHelper.playRecord(record, pos.x, pos.y, pos.z, record.identifier(pos))
    case playable: IJukeboxPlayable => playable.play(item, pos)
    case _ => if (item != null) JukeboxReloaded.logger.error("Attempted to play an item of {} class", item.getClass)
  }

  def isPlaying(item: ItemStack, pos: BlockPos) = Option(item).map(_.getItem).orNull match {
    case record: ItemRecord => SoundSystemHelper.isPlaying(record.identifier(pos))
    case playable: IJukeboxPlayable => playable.isPlaying(item, pos);
    case _ =>
      if (item != null) JukeboxReloaded.logger.error("Incorrect type to play: {}", item.getClass)
      false
  }
}
