package net.cazzar.mods.jukeboxreloaded.blocks.tileentity

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.{ItemRecord, ItemStack}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IChatComponent
import net.cazzar.mods.jukeboxreloaded.Util._

/**
 * Created by Cayde on 16/12/2014.
 */
class TileJukebox extends TileEntity with IInventory {
  var items: Array[ItemStack] = new Array[ItemStack](12)
  var isPlaying = false
  var name: String = null

  override def getSizeInventory: Int = items.length

  override def decrStackSize(index: Int, count: Int): ItemStack = {
    val item = items(index)
    item.stackSize -= count

    if (item.stackSize <= 0) items(index) = null
    items(index)
  }

  override def closeInventory(player: EntityPlayer): Unit = {}

  override def getInventoryStackLimit: Int = 1

  override def clear(): Unit = items = new Array[ItemStack](9)

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = stack.getItem.isInstanceOf[ItemRecord]

  override def getStackInSlotOnClosing(index: Int): ItemStack = items(index)

  override def openInventory(player: EntityPlayer): Unit = {}

  override def getFieldCount: Int = 0

  override def getField(id: Int): Int = 0

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = items(index) = stack

  override def isUseableByPlayer(player: EntityPlayer): Boolean = true

  override def getStackInSlot(index: Int): ItemStack = items(index)

  override def setField(id: Int, value: Int): Unit = {}

  override def getDisplayName: IChatComponent = getName

  override def getName: String = if (hasCustomName) name else "Jukebox"

  override def hasCustomName: Boolean = name != null

  override def markDirty() =  {
    super.markDirty()
    worldObj.markBlockForUpdate(getPos)
  }
}