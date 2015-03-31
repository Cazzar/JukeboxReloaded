package net.cazzar.mods.jukeboxreloaded

import io.netty.buffer.ByteBuf
import net.cazzar.corelib.lib.SoundSystemHelper
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemRecord, Item, ItemStack}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, ChatComponentText}
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.{IMessage, SimpleNetworkWrapper}

object Util {
  @inline implicit def blockToItem(block: Block): Item = Item.getItemFromBlock(block)

  implicit class RichWorld(val world: World) {
    @inline def getTile[T >: Null <: TileEntity](pos: BlockPos): Option[T] = Option(world.getTileEntity(pos)).filter(_.isInstanceOf[T]).asInstanceOf[Option[T]]
  }

  implicit class RichPlayer(val player: EntityPlayer) {
    @inline def sendMessage(message: String) = player.addChatComponentMessage(message) // abuse of my own functions much?
  }

  implicit class RichItemStack(val is: ItemStack) {
    @inline def ==(other: ItemStack): Boolean = is.getItem == other.getItem && is.getItemDamage == other.getItemDamage
  }

  implicit class RichByteBuf(val buf: ByteBuf) {
    @inline def writeString(str: String) = ByteBufUtils.writeUTF8String(buf, str)
    @inline def readString() = ByteBufUtils.readUTF8String(buf)

    @inline def writeItemStack(is: ItemStack) = ByteBufUtils.writeItemStack(buf, is)
    @inline def readItemStack() = ByteBufUtils.readItemStack(buf)

    @inline def writePos(pos: BlockPos): Unit = {
      buf.writeInt(pos.getX)
      buf.writeByte(pos.getY)
      buf.writeInt(pos.getZ)
    }
    @inline def readPos() = {
      val x = buf.readInt()
      val y = buf.readByte()
      val z = buf.readInt()

      new BlockPos(x, y, z)
    }
  }

  implicit class RichSimpleNetworkWrapper(val wrapper: SimpleNetworkWrapper) {
    @inline def sendToWorld(message: IMessage, world: World) = wrapper.sendToDimension(message, world.provider.getDimensionId)
    @inline def sendToServerOrWorld(message: IMessage, world: World) = if (world.isRemote) wrapper.sendToServer(message) else wrapper.sendToWorld(message, world)

  }

  implicit class RichItemRecord(val record: ItemRecord) {
    @inline def identifier(pos: BlockPos) = SoundSystemHelper.getIdentifierForRecord(record, pos.x, pos.y, pos.z);
  }

  implicit class RichBlockPos(val pos: BlockPos) {
    @inline def x = pos.getX
    @inline def y = pos.getY
    @inline def z = pos.getZ
    @inline def getTileEntity(world: World) = world.getTileEntity(pos)
    @inline def getTileEntityChecked[T >: Null <: TileEntity](world: World) = world.getTile[T](pos)
    @inline def getBlockState(world: World) = world.getBlockState(pos)
  }

  @inline implicit def stringToChatComponent(str: String): ChatComponentText = new ChatComponentText(str)

  implicit def unitToRunnable(unit: () => Unit): Runnable = new Runnable {
    override def run() = unit()
  }
  
  implicit def toItemStack(item: Item): ItemStack = new ItemStack(item)
  implicit def toItemStack(block: Block): ItemStack = new ItemStack(block)

  implicit def itemStackToItem(is: ItemStack): Item = if(is == null) null else is.getItem

}
