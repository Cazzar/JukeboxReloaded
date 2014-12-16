package net.cazzar.mods.jukeboxreloaded

import io.netty.buffer.ByteBuf
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, ChatComponentText}
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.ByteBufUtils

object Util {
  implicit def blockToItem(block: Block): Item = Item.getItemFromBlock(block)

  implicit class RichWorld(val world: World) {
    def getTile[T >: Null <: TileEntity](pos: BlockPos): T = world.getTileEntity(pos) match {
      case t: T => t
      case _ => null
    }
  }

  implicit class RichPlayer(val player: EntityPlayer) {
    def sendMessage(message: String) = player.addChatComponentMessage(message) // abuse of my own functions much?
  }

  implicit class RichItemStack(val is: ItemStack) {
    def ==(other: ItemStack): Boolean = is.getItem == other.getItem && is.getItemDamage == other.getItemDamage
  }

  implicit class RichByteBuf(val buf: ByteBuf) {
    def writeString(str: String) = ByteBufUtils.writeUTF8String(buf, str)
    def readString() = ByteBufUtils.readUTF8String(buf)

    def writeItemStack(is: ItemStack) = ByteBufUtils.writeItemStack(buf, is)
    def readItemStack() = ByteBufUtils.readItemStack(buf)

    def writePos(pos: BlockPos): Unit = {
      buf.writeInt(pos.getX)
      buf.writeByte(pos.getY)
      buf.writeInt(pos.getZ)
    }
    def readPos() = {
      val x = buf.readInt()
      val y = buf.readByte()
      val z = buf.readInt()

      new BlockPos(x, y, z)
    }
  }

  implicit class RichBlockPos(pos: BlockPos) {
    def getTileEntity(world: World) = world.getTileEntity(pos)
    def getTileEntityChecked[T >: Null <: TileEntity](world: World) = world.getTile[T](pos)
    def getBlockState(world: World) = world.getBlockState(pos)
  }

  implicit def stringToChatComponent(str: String): ChatComponentText = new ChatComponentText(str)

  implicit def unitToRunnable(unit: () => Unit): Runnable = new Runnable {
    override def run() = unit()
  }
  
  implicit def toItemStack(item: Item): ItemStack = new ItemStack(item)
  implicit def toItemStack(block: Block): ItemStack = new ItemStack(block)

  implicit def itemStackToItem(is: ItemStack): Item = is.getItem
}
