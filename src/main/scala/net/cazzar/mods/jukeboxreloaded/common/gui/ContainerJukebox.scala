package net.cazzar.mods.jukeboxreloaded.common.gui

import net.minecraft.inventory.{Slot, Container}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.common.Reference._
import net.minecraft.item.{ItemStack, ItemRecord}
import net.cazzar.mods.jukeboxreloaded.common.gui.impl.RecordSlot

class ContainerJukebox(inventory: InventoryPlayer, tile: TileJukebox) extends Container {
    var slot = 0

    for (col <- 0 to JUKEBOX_INVENTORY_ROWS)
        for (row <- 0 to JUKEBOX_INVENTORY_COLUMNS) {
            addSlotToContainer(new RecordSlot(tile, slot, 54 + row * 18, 17 + col * 18))
            slot += 1
        }


    for (inventoryRowIndex <- 0 to PLAYER_INVENTORY_ROWS)
        for (inventoryColumnIndex <- 0 to PLAYER_INVENTORY_COLUMNS) {
            addSlotToContainer(new Slot(inventory, inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 94 + inventoryRowIndex * 18))
        }

    for (actionBarSlotIndex <- 0 to PLAYER_INVENTORY_COLUMNS) {
        addSlotToContainer(new Slot(inventory, actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 152))
    }

    override def canInteractWith(player: EntityPlayer) = true

    override def transferStackInSlot(player: EntityPlayer, slotNumber: Int): ItemStack = {
        var stack: ItemStack = null
        val slot = inventorySlots.get(slotNumber).asInstanceOf[Slot]

        if (slot != null && slot.getHasStack) {
            val slotStack = slot.getStack
            stack = slotStack.copy()

            val chestSlots: Int = tile.getSizeInventory
            if (slotNumber < chestSlots) {
                if (!mergeItemStack(slotStack, chestSlots, inventorySlots.size(), force = true)) return null
            } else if (!mergeItemStack(slotStack, 0, chestSlots, force = false)) return null

            if (slotStack.stackSize == 0) slot.putStack(null)
            else slot.onSlotChanged()
        }

        stack
    }

    override def mergeItemStack(p1: ItemStack, p2: Int, p3: Int, force: Boolean): Boolean = {
        if (!p1.getItem.isInstanceOf[ItemRecord]) false
        super.mergeItemStack(p1, p2, p3, force)
    }
}
