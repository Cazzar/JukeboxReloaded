package net.cazzar.mods.jukeboxreloaded.common.gui

import net.minecraft.inventory.{Slot, Container}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.common.Reference._
import net.minecraft.item.{ItemStack, ItemRecord}
import com.google.common.collect.Lists
import net.cazzar.mods.jukeboxreloaded.common.gui.impl.RecordSlot

class ContainerJukebox(inventory: InventoryPlayer, tile: TileJukebox) extends Container {
//    val items = Lists.newArrayList(classOf[ItemRecord])
    var slot = 0
    //
    0.to(JUKEBOX_INVENTORY_ROWS - 1).foreach({
        (row) => {
            0.to(JUKEBOX_INVENTORY_COLUMNS - 1).foreach({
                (col) => {
                    addSlotToContainer(new RecordSlot(tile, slot, 54 + col * 18, 17 + row * 18))
                    slot += 1
                }
            })
        }
    })

    0.to(PLAYER_INVENTORY_ROWS - 1).foreach({
        (col) => {
            0.to(PLAYER_INVENTORY_COLUMNS - 1).foreach({
                (row) => {
                    addSlotToContainer(new Slot(inventory, col + row * 9 + 9, 8 + row * 18, 94 + row * 18))
                    slot += 1
                }
            })
        }
    })

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
