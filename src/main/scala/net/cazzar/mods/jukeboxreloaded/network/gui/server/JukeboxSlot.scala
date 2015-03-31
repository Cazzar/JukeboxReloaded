package net.cazzar.mods.jukeboxreloaded.network.gui.server

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.util.PlayUtil
import net.minecraft.inventory.{IInventory, Slot}
import net.minecraft.item.ItemStack

class JukeboxSlot(jukebox: TileJukebox, index: Int, xPosition: Int, yPosition: Int) extends Slot(jukebox: IInventory, index: Int, xPosition: Int, yPosition: Int) {
  override def isItemValid(stack: ItemStack): Boolean = PlayUtil.canBePlayed(stack, jukebox.getPos)

  override def getItemStackLimit(stack: ItemStack): Int = 1

  override def getSlotStackLimit: Int = 1
}
