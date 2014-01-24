package net.cazzar.mods.jukeboxreloaded.blocks.tileentity

import net.cazzar.corelib.tile.SyncedTileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection
import net.cazzar.corelib.util.ClientUtil
import net.cazzar.corelib.lib.SoundSystemHelper
import net.minecraft.util.ChunkCoordinates
import net.minecraft.item.{ItemRecord, ItemStack}
import net.minecraft.inventory.IInventory
import net.minecraft.entity.player.EntityPlayer
import net.cazzar.mods.jukeboxreloaded.common.ReplayMode

class TileJukebox(metadata: Int) extends SyncedTileEntity with IInventory {
    private var isPlayingLocal = false
    var shuffle = false
    var playing = {
        if (ClientUtil.isClient) SoundSystemHelper.isPlaying(ClientUtil.mc.renderGlobal, identifier())
        else isPlayingLocal
    }
    var facing = ForgeDirection.NORTH.ordinal.asInstanceOf[Short]
    var items = new Array[ItemStack](getSizeInventory)
    var lastRecord = ""
    var current: Short = 0
    var replayMode = ReplayMode.ALL

    def this() = this(0)

    //write
    override def func_145841_b(tag: NBTTagCompound) = {
        super.func_145841_b(tag)
        facing = tag.getShort("facing")
    }

    //save
    override def func_145839_a(tag: NBTTagCompound) = {
        super.func_145839_a(tag)
        tag.setShort("facing", facing)
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
        if (field_145850_b.isRemote) {
            //send packet

        }
        else {
            isPlayingLocal = true
            field_145850_b.playRecord(getStackInSlot(current).getItem.asInstanceOf[ItemRecord].field_150929_a, this.field_145851_c, this.field_145848_d, this.field_145849_e)
        }
    }

    def stopPlayingRecord() = {
        if (field_145850_b.isRemote) {
            //send packet?
        }

        isPlayingLocal = false
        field_145850_b.playRecord(null, this.field_145851_c, this.field_145848_d, this.field_145849_e)
    }

    def identifier(): ChunkCoordinates = new ChunkCoordinates(this.field_145851_c, this.field_145848_d, this.field_145849_e)

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
        field_145850_b.func_147471_g(this.field_145851_c, this.field_145848_d, this.field_145849_e)
    }
}
