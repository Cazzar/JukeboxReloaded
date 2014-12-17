package net.cazzar.mods.jukeboxreloaded.blocks.tileentity

import net.cazzar.corelib.lib.InventoryUtils
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.{ItemRecord, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.Packet
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IChatComponent
import net.cazzar.mods.jukeboxreloaded.network.message._
import net.cazzar.mods.jukeboxreloaded.Util._

/**
 * Created by Cayde on 16/12/2014.
 */
class TileJukebox extends TileEntity with IInventory {
  var items: Array[ItemStack] = new Array[ItemStack](12)
  var isPlaying = false
  var name: String = null
  private var currRecord = 0

  def record = currRecord

  override def getSizeInventory: Int = items.length

  override def decrStackSize(index: Int, count: Int): ItemStack = {
    val item = items(index)
    item.stackSize -= count

    if (item.stackSize <= 0) items(index) = null
    items(index)
  }

  override def closeInventory(player: EntityPlayer): Unit = {}

  override def getInventoryStackLimit: Int = 1

  override def clear(): Unit = items = new Array[ItemStack](getSizeInventory)

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

  override def getDescriptionPacket: Packet = {
    null
  }


  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
    compound.setTag("inventory", InventoryUtils.writeItemStacksToTag(items))
    compound.setInteger("record", record)
  }

  def selectedRecord() = items(record)

  def playRecord() = {
    if (worldObj.isRemote) NetworkHandler.INSTANCE.sendToServer(ServerPlayMessage(selectedRecord(), pos))
    else NetworkHandler.INSTANCE.sendToWorld(ClientPlayMessage(selectedRecord(), pos), worldObj)
  }

  def nextRecord() = setRecord(currRecord + 1)

  def setRecord(index: Int) = currRecord = index % getSizeInventory

  def prevRecord() = setRecord(if (currRecord == 0) getSizeInventory - 1 else currRecord - 1)
}