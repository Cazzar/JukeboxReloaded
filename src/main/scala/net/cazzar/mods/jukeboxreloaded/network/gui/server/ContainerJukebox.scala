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

package net.cazzar.mods.jukeboxreloaded.network.gui.server

import net.cazzar.mods.jukeboxreloaded.api.IJukeboxPlayable
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{Container, Slot}
import net.minecraft.item.{ItemRecord, ItemStack}

class ContainerJukebox(inv: InventoryPlayer, tile: TileJukebox) extends Container {
  var slot = 0

  //Jukebox
  for (row <- 0 until ContainerJukebox.INVENTORY_ROWS)
    for (col <- 0 until ContainerJukebox.INVENTORY_COLUMNS) {
      addSlotToContainer(new JukeboxSlot(tile, slot, 54 + col * 18, 17 + row * 18))
      slot += 1
    }

  //Player main inventory
  for (row <- 0 until ContainerJukebox.PLAYER_INVENTORY_ROWS)
    for (col <- 0 until ContainerJukebox.PLAYER_INVENTORY_COLUMNS)
      addSlotToContainer(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 94 + row * 18))

  for (action <- 0 until InventoryPlayer.getHotbarSize)
    addSlotToContainer(new Slot(inv, action, 8 + action * 18, 152))

  override def canInteractWith(playerIn: EntityPlayer): Boolean = true

  override def transferStackInSlot(entity: EntityPlayer, idx: Int): ItemStack = {
    var stack: ItemStack = null
    val slot: Slot = inventorySlots.get(idx).asInstanceOf[Slot]

    if (slot != null && slot.getHasStack) {
      val slotStack = slot.getStack
      stack = slotStack.copy()

      val recordSlots = tile.getSizeInventory

      if (idx < recordSlots) {
        if (!mergeItemStack(slotStack, recordSlots, inventorySlots.size(), useEndIndex = true)) return null
      } else if (!mergeItemStack(slotStack, 0, recordSlots, useEndIndex = false))
        return null

      if (slotStack.stackSize == 0) slot.putStack(null)
      else slot.onSlotChanged()
    }

    stack
  }

  override def mergeItemStack(stack: ItemStack, startIndex: Int, endIndex: Int, useEndIndex: Boolean): Boolean = {
    (stack.getItem.isInstanceOf[ItemRecord] || stack.getItem.isInstanceOf[IJukeboxPlayable]) && super.mergeItemStack(stack, startIndex, endIndex, useEndIndex)
  }
}

object ContainerJukebox {
  final val PLAYER_INVENTORY_ROWS = 3
  final val PLAYER_INVENTORY_COLUMNS = 9

  final val INVENTORY_ROWS = 3
  final val INVENTORY_COLUMNS = 4

  def apply(inv: InventoryPlayer, tile: TileJukebox) = new ContainerJukebox(inv, tile)
}
