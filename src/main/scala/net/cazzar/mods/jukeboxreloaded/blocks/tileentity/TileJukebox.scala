package net.cazzar.mods.jukeboxreloaded.blocks.tileentity

import net.cazzar.corelib.lib.{SoundSystemHelper, InventoryUtils}
import net.cazzar.corelib.tile.SyncedTileEntity
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.api.IPlayMethod
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox.RepeatMode
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.cazzar.mods.jukeboxreloaded.util.PlayUtil
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.{Item, ItemRecord, ItemStack}
import net.minecraft.nbt.{NBTTagList, NBTTagCompound}
import net.minecraft.network.Packet
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IChatComponent
import net.cazzar.mods.jukeboxreloaded.network.message._
import net.cazzar.mods.jukeboxreloaded.Util._
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
 * Created by Cayde on 16/12/2014.
 */
class TileJukebox extends SyncedTileEntity with IInventory {
  var items: Array[ItemStack] = new Array[ItemStack](12)
  private var _playing = false
  var name: String = null
  var repeatMode = RepeatMode.ALL
  var shuffle = false
  private var _record = 0

  def record = _record

  @SideOnly(Side.SERVER)
  def setServerPlayingStatus(playing: Boolean) = _playing = playing

  def playing: Boolean = {
    if (worldObj == null) return false
    if (worldObj.isRemote) return _playing

    for (item <- items) {
      if (PlayUtil.isPlaying(item, pos))
        return true
    }
    false
  }

  def isSlotPlaying: Boolean = {
    if (worldObj.isRemote) return _playing

    PlayUtil.isPlaying(selectedRecord, pos)
  }

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

  def selectedRecord = items(record)

  def playRecord(fromServer: Boolean = false): Unit = {
    if (selectedRecord == null) return
    if (worldObj.isRemote){
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

  def nextRecord() = record = _record + 1

  def record_=(index: Int) = {
    val wasPlaying = playing
    if (wasPlaying)
      stopPlayingRecord()

    _record = index % getSizeInventory

    playRecord()
  }

  def prevRecord() = record = if (_record == 0) getSizeInventory - 1 else _record - 1

  override def addExtraNBTToPacket(tag: NBTTagCompound) = {
    tag.setBoolean("playing", playing)
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