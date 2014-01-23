package net.cazzar.mods.jukeboxreloaded.common.gui.impl

import net.minecraft.inventory.{Slot, IInventory}
import net.minecraft.item.{ItemRecord, ItemStack}

class RecordSlot(inventory: IInventory, index: Int, xPosition: Int, yPosition: Int) extends Slot(inventory, index, xPosition, yPosition) {


    override def isItemValid(par1ItemStack: ItemStack): Boolean = par1ItemStack.getItem.isInstanceOf[ItemRecord]
}
