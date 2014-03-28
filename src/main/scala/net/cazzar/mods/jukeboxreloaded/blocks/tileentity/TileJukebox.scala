package net.cazzar.mods.jukeboxreloaded.blocks.tileentity

import net.cazzar.corelib.tile.SyncedTileEntity
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection
import net.cazzar.corelib.util.ClientUtil
import net.cazzar.corelib.lib.{InventoryUtils, SoundSystemHelper}
import net.minecraft.util.{MathHelper, ChunkCoordinates}
import net.minecraft.item.{ItemRecord, ItemStack}
import net.minecraft.inventory.IInventory
import net.minecraft.entity.player.EntityPlayer
import net.cazzar.mods.jukeboxreloaded.common.{Strings, ReplayMode}
import net.cazzar.corelib.util.ClientUtil._
import cpw.mods.fml.relauncher.{Side, SideOnly}
import li.cil.oc.api.network.{Callback, Arguments, Context, SimpleComponent}
import cpw.mods.fml.common.Optional

@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")
class TileJukebox(metadata: Int) extends SyncedTileEntity with IInventory with SimpleComponent {
    private var isPlayingLocal = false
    var shuffle = false

    def playing = {
        if (ClientUtil.isClient) {
            SoundSystemHelper.isPlaying(identifier.toString)
        }
        else isPlayingLocal
    }

    var facing = ForgeDirection.NORTH.ordinal.asInstanceOf[Short]
    var items = new Array[ItemStack](getSizeInventory)
    var lastRecord = ""
    var current = 0
    var replayMode = ReplayMode.ALL

    def this() = this(0)

    def nextRecord() {
        if (playing) {
            var set = false
            for (i <- current to getSizeInventory - 1) {
                if (getStackInSlot(i) != null && !set) {
                    current = i
                    set = true
                }
            }
            if (!set) {
                current = -1
                nextRecord()
                return
            }
        }
        else if (shuffle) {
            val c = current
            current = worldObj.rand.nextInt(getSizeInventory)

            while (current == c && getStackInSlot(current) != null) current = worldObj.rand.nextInt(getSizeInventory)
        }
        else {
            current += 1
            if (current >= getSizeInventory) current = 0
        }

        stopPlayingRecord()
        if (playing)
            playSelectedRecord()
    }

    def previousRecord() = {
        current -= 1
        if (current < 0) current = getSizeInventory - 1

        stopPlayingRecord()
        playSelectedRecord()
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
            playSelectedRecord()
            false
        }
        else {
            stopPlayingRecord()
            true
        }
    }

    def record = getStackInSlot(current).getItem.asInstanceOf[ItemRecord]

    def playSelectedRecord() = {
        if (getStackInSlot(current) != null && !playing) {
            isPlayingLocal = true
            if (ClientUtil.isClient)
                SoundSystemHelper.playRecord(getWorldObj, record, xCoord, yCoord, zCoord, identifier.toString)
            lastRecord = record.recordName
            markForUpdate()
        }
    }

    def stopPlayingRecord() = {
        if (playing) {
            isPlayingLocal = false
            if (ClientUtil.isClient)
                SoundSystemHelper.stop(identifier.toString)
            markForUpdate()
        }
    }

    override def invalidate() {
        super.invalidate()
        stopPlayingRecord()
    }

    def identifier: ChunkCoordinates = new ChunkCoordinates(xCoord, yCoord, zCoord)

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

    @SideOnly(Side.CLIENT)
    override def updateEntity() {
        if (isPlayingLocal) {
            if (!playing) nextRecord()
        }

        if (getStackInSlot(current) == null) {
            stopPlayingRecord()
            return
        }

        val sound = SoundSystemHelper.getSoundHandler.getSound(record.getRecordResource("records." + record.recordName))
        val entry = sound.func_148720_g
        val category = sound.getSoundCategory
        val volume = MathHelper.clamp_double(16 * entry.getVolume * mc.gameSettings.getSoundLevel(category).asInstanceOf[Double], 0.0D, 1.0D).asInstanceOf[Float]
        val pitch = MathHelper.clamp_double(16 * entry.getVolume * mc.gameSettings.getSoundLevel(category).asInstanceOf[Double], 0.0D, 1.0D).asInstanceOf[Float]

        SoundSystemHelper.getSoundSystem.setPitch(identifier.toString, pitch)
        SoundSystemHelper.getSoundSystem.setVolume(identifier.toString, volume)
    }

    override def getComponentName: String = "jukebox"

    @Optional.Method(modid = "OpenComputers")
    @Callback def play(ctx: Context, args: Arguments): Array[Object] = {
        playSelectedRecord()
        null
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback def stop(ctx: Context, args: Arguments): Array[Object] = {
        stopPlayingRecord()
        null
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback def next(ctx: Context, args: Arguments): Array[Object] = {
        nextRecord()
        null
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback def previous(ctx: Context, args: Arguments): Array[Object] = {
        previousRecord()
        null
    }

    @Optional.Method(modid = "OpenComputers")
    @Callback def isPlaying(ctx: Context, args: Arguments): Array[Object] = Array(Boolean.box(playing))

    @Optional.Method(modid = "OpenComputers")
    @Callback def getRecord(ctx: Context, args: Arguments): Array[Object] = Array(Int.box(current))

}
