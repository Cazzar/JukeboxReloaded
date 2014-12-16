package net.cazzar.mods.jukeboxreloaded.blocks

import com.google.common.base.Predicate
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.NetworkHandler
import net.cazzar.mods.jukeboxreloaded.network.gui.GuiHandler
import net.cazzar.mods.jukeboxreloaded.network.message.ClientPlayMessage
import net.minecraft.block.{ITileEntityProvider, Block}
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.{IBlockState, BlockState}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, EnumFacing}
import net.minecraft.world.World
import net.cazzar.mods.jukeboxreloaded.Util._

/**
 * Created by Cayde on 15/12/2014.
 */
object BlockJukebox extends {
  val FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL.asInstanceOf[Predicate[EnumFacing]])
} with Block(Material.wood) with ITileEntityProvider {

  setDefaultState(blockState.getBaseState.withProperty(FACING, EnumFacing.NORTH))

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileJukebox

  protected override def createBlockState: BlockState = new BlockState(this, FACING)


  override def onBlockDestroyedByPlayer(worldIn: World, pos: BlockPos, state: IBlockState): Unit = {
    val tile = worldIn.getTile[TileJukebox](pos)

  }

  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (!worldIn.isRemote) {
      val tile = pos.getTileEntityChecked[TileJukebox](worldIn)
      if (tile == null) return false

      playerIn.openGui(JukeboxReloaded, GuiHandler.JUKEBOX, worldIn, pos.getX, pos.getY, pos.getZ)
    }
    true
  }

  override def getStateFromMeta(meta: Int): IBlockState = {
    var enumfacing: EnumFacing = EnumFacing.getFront(meta)
    if (enumfacing.getAxis eq EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH
    }
    this.getDefaultState.withProperty(FACING, enumfacing)
  }

  override def getMetaFromState(state: IBlockState): Int = {
    state.getValue(FACING).asInstanceOf[EnumFacing].getIndex
  }

  override def onBlockPlacedBy(worldIn: World, pos: BlockPos, state: IBlockState, placer: EntityLivingBase, stack: ItemStack) {
    worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing.getOpposite), 2)
  }
}
