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

package net.cazzar.mods.jukeboxreloaded.blocks.tileentity

import net.cazzar.corelib.lib.InventoryUtils
import net.cazzar.corelib.tile.SyncedTileEntity
import net.cazzar.corelib.util.CommonUtil
import net.cazzar.mods.jukeboxreloaded.Util._
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox.RepeatMode
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.cazzar.mods.jukeboxreloaded.network.message._
import net.cazzar.mods.jukeboxreloaded.util.PlayUtil
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.{ItemRecord, ItemStack}
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.IChatComponent

class TileJukebox extends SyncedTileEntity with IInventory {
  var items: Array[ItemStack] = new Array[ItemStack](12)
  var name: String = null
  var repeatMode = RepeatMode.ALL
  var shuffle = false
  private var _playing = false
  private var _record = 0

  def setServerPlayingStatus(playing: Boolean) = _playing = playing

  def isSlotPlaying: Boolean = {
    if (worldObj.isRemote) return _playing

    PlayUtil.isPlaying(selectedRecord, pos)
  }

  def selectedRecord = items(record)

  override def decrStackSize(index: Int, count: Int): ItemStack = {
    val item = items(index)
    item.stackSize -= count

    if (item.stackSize <= 0) items(index) = null
    items(index)
  }

  override def closeInventory(player: EntityPlayer): Unit = {}

  override def getInventoryStackLimit: Int = 1

  override def clear(): Unit = items = new Array[ItemStack](getSizeInventory)

  override def getSizeInventory: Int = items.length

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

  override def markDirty() = {
    super.markDirty()
    if (hasWorldObj) worldObj.markBlockForUpdate(getPos)
  }

  override def writeToNBT(compound: NBTTagCompound): Unit = {
    super.writeToNBT(compound)
    compound.setTag("inventory", InventoryUtils.writeItemStacksToTag(items))
    compound.setInteger("record", record)
    compound.setInteger("repeat", repeatMode.id)
    compound.setBoolean("shuffle", shuffle)
  }

  override def readFromNBT(compound: NBTTagCompound): Unit = {
    super.readFromNBT(compound)
    if (compound.hasKey("inventory")) InventoryUtils.readItemStacksFromTag(items, compound.getTag("inventory").asInstanceOf[NBTTagList])
    record_=(compound.getInteger("record"))
    repeatMode = RepeatMode(compound.getInteger("repeat"))
    shuffle = compound.getBoolean("shuffle")
  }

  def playRecord(fromServer: Boolean = false): Unit = {
    if (selectedRecord == null || !hasWorldObj) return
    if (worldObj.isRemote && !fromServer) {
      val packet = new ClientActionMessage(Action.PLAY, pos)
      packet.currentRecord = record
      NetworkHandler.INSTANCE.sendToServer(packet)
      _playing = true
    } else if (fromServer) {
      PlayUtil.play(selectedRecord, pos)
    }
    else {
      val packet = new ClientActionMessage(Action.PLAY, pos)
      packet.currentRecord = record
      NetworkHandler.INSTANCE.sendToWorld(packet, worldObj)
    }
  }

  def seriouslyStahp() = {
    for (i <- items) {
      PlayUtil.stop(i, pos)
    }
  }

  def stopPlayingRecord(serious: Boolean = false) = {
    if (serious) seriouslyStahp()
    else PlayUtil.stop(selectedRecord, pos)
  }

  def nextRecord() = {
    record = _record + 1
    NetworkHandler.INSTANCE.sendToServerOrWorld(new SetRecordMessage(pos, _record), worldObj)
  }

  def record = _record

  def record_=(index: Int) = {
    val wasPlaying = playing
    if (wasPlaying) stopPlayingRecord()

    _record = index % getSizeInventory
    markDirty()

    if (wasPlaying) playRecord()
  }

  def prevRecord() = {
    record = if (_record == 0) getSizeInventory - 1 else _record - 1

    NetworkHandler.INSTANCE.sendToServerOrWorld(new SetRecordMessage(pos, _record), worldObj)
  }

  override def addExtraNBTToPacket(tag: NBTTagCompound) = {
    tag.setBoolean("playing", playing)
  }

  def playing: Boolean = {
    if (CommonUtil.isServer) return _playing

    for (item <- items) {
      if (PlayUtil.isPlaying(item, pos))
        return true
    }
    false
  }

  override def readExtraNBTFromPacket(tag: NBTTagCompound) = {
    val tagPlaying = tag.getBoolean("playing")
    if (tagPlaying && !playing) playRecord() else if (!tagPlaying && playing) stopPlayingRecord()
  }
}

object TileJukebox {

  object RepeatMode extends Enumeration {
    val ONE, NONE, ALL = Value
  }

}