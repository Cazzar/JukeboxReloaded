package net.cazzar.mods.jukeboxreloaded.blocks.tileentity

import net.cazzar.corelib.tile.SyncedTileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection
import net.cazzar.corelib.util.ClientUtil
import net.cazzar.corelib.lib.{InventoryUtils, SoundSystemHelper}
import net.minecraft.util.ChunkCoordinates
import net.minecraft.item.{Item, ItemRecord, ItemStack}
import net.minecraft.inventory.IInventory
import net.minecraft.entity.player.EntityPlayer
import net.cazzar.mods.jukeboxreloaded.common.{Strings, ReplayMode}
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded

class TileJukebox(metadata: Int) extends SyncedTileEntity with IInventory {
    private var isPlayingLocal = false
    var shuffle = false
    def playing = {
        if (ClientUtil.isClient) SoundSystemHelper.isPlaying(ClientUtil.mc.renderGlobal, identifier)
        else isPlayingLocal
    }
    var facing = ForgeDirection.NORTH.ordinal.asInstanceOf[Short]
    var items = new Array[ItemStack](getSizeInventory)
    var lastRecord = ""
    var current = 0
    var replayMode = ReplayMode.ALL

    def this() = this(0)

    def nextRecord() = {
        current += 1
        if (current >= getSizeInventory) current = 0
    }

    def previousRecord() = {
        current -= 1
        if (current < 0) current = getSizeInventory - 1
    }

    //write
    override def writeToNBT(tag: NBTTagCompound) = {
        super.writeToNBT(tag)
        tag.setShort("facing", facing)
        tag.setInteger("current", current)
        tag.setInteger("replayMode", replayMode.id)
        tag.setBoolean("shuffle", shuffle)
        tag.setTag("items", InventoryUtils.writeItemStacksToTag(items))
    }

    //save
    override def readFromNBT(tag: NBTTagCompound) = {
        super.readFromNBT(tag)
        facing = tag.getShort("facing")
        current = tag.getInteger("current")
        replayMode = ReplayMode(tag.getInteger("replayMode"))
        shuffle = tag.getBoolean("shuffle")
        identifier = new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord)
        InventoryUtils.readItemStacksFromTag(items, tag.getTagList("items", 10))
    }

    def addExtraNBTToPacket(tag: NBTTagCompound) = {
        tag.setBoolean("playing", playing)
    }

    def readExtraNBTFromPacket(tag: NBTTagCompound) = {
        setPlaying(tag.getBoolean("playing"))
    }

    def setPlaying(playing: Boolean): Boolean = {
        if (playing) {
            stopPlayingRecord()
            false
        }
        else {
            playSelectedRecord()
            true
        }
    }

    def playSelectedRecord() = {
        if (getStackInSlot(current) != null) {
            isPlayingLocal = true
//            worldObj.playRecord(getStackInSlot(current).getItem.asInstanceOf[ItemRecord].recordName, this.xCoord, this.yCoord, this.zCoord)
            worldObj.playAuxSFXAtEntity(null, 1005, xCoord, yCoord, zCoord, Item.getIdFromItem(getStackInSlot(current).getItem))
        }
    }

    def stopPlayingRecord() = {
        if (worldObj.isRemote) {
            //send packet?
        }

        isPlayingLocal = false
        worldObj.playRecord(null, this.xCoord, this.yCoord, this.zCoord)
    }

    var identifier: ChunkCoordinates = null

    override def getSizeInventory: Int = 12


    def getStackInSlot(i: Int): ItemStack = {
        if (i >= getSizeInventory) null
        else items(i)
    }

    def decrStackSize(slot: Int, amount: Int): ItemStack = {
        items(slot).stackSize -= amount
        items(slot)
    }

    def getStackInSlotOnClosing(i: Int): ItemStack = {
        val stack: ItemStack = getStackInSlotOnClosing(i)
        if (stack != null) setInventorySlotContents(i, null)
        getStackInSlot(i)
    }

    def setInventorySlotContents(slot: Int, stack: ItemStack) = {
        items(slot) = stack
    }

    def func_145825_b(): String = "Jukebox"

    def func_145818_k_(): Boolean = false

    def getInventoryStackLimit: Int = 1

    def isUseableByPlayer(p1: EntityPlayer): Boolean = true

    def openChest() = {}

    def closeChest() = {}

    def isItemValidForSlot(p1: Int, p2: ItemStack): Boolean = p2.getItem.isInstanceOf[ItemRecord]

    def markForUpdate() = {
        worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord)
    }

    override def closeInventory(): Unit = {}

    override def openInventory(): Unit = {}

    override def hasCustomInventoryName: Boolean = true

    override def getInventoryName: String = Strings.GUI_JUKEBOX_NAME
}
