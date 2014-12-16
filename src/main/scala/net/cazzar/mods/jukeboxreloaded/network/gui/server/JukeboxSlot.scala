package net.cazzar.mods.jukeboxreloaded.network.gui.server

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.minecraft.inventory.{IInventory, Slot}
import net.minecraft.item.ItemStack
import net.cazzar.mods.jukeboxreloaded.Util._
class JukeboxSlot(inventoryIn: IInventory, index: Int, xPosition: Int, yPosition: Int) extends Slot(inventoryIn: IInventory, index: Int, xPosition: Int, yPosition: Int) {
  override def isItemValid(stack: ItemStack): Boolean = JukeboxReloaded.canBePlayed(stack)

  override def getItemStackLimit(stack: ItemStack): Int = 1

  override def getSlotStackLimit: Int = 1
}
