package net.cazzar.mods.jukeboxreloaded

import net.cazzar.corelib.lib.SoundSystemHelper
import net.cazzar.mods.jukeboxreloaded.api.IPlayMethod
import net.minecraft.item.{Item, ItemRecord}
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.FMLCommonHandler

object RecordPlayMethod extends IPlayMethod[ItemRecord] {
  override def play(item: ItemRecord, pos: BlockPos): Unit = {
    val identifier = SoundSystemHelper.getIdentifierForRecord(item, pos.getX, pos.getY, pos.getZ)

    println(FMLCommonHandler.instance().getEffectiveSide)
    println(pos)

    SoundSystemHelper.playRecord(item, pos.getX, pos.getY, pos.getZ, identifier)
  }

  override def isPlaying(item: ItemRecord, pos: BlockPos): Boolean = {
    val identifier = SoundSystemHelper.getIdentifierForRecord(item, pos.getX, pos.getY, pos.getZ)

    SoundSystemHelper.isPlaying(identifier)
  }

  override def stop(item: ItemRecord, pos: BlockPos): Unit = {
    val identifier = SoundSystemHelper.getIdentifierForRecord(item, pos.getX, pos.getY, pos.getZ)

    SoundSystemHelper.stop(identifier)
  }

  override def canPlay(item: Item): Boolean = item.isInstanceOf[ItemRecord]
}
