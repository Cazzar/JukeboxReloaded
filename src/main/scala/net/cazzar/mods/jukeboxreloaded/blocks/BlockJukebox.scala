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

package net.cazzar.mods.jukeboxreloaded.blocks

import com.google.common.base.Predicate
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import net.cazzar.mods.jukeboxreloaded.Util._
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.gui.GuiHandler
import net.minecraft.block.material.Material
import net.minecraft.block.properties.PropertyDirection
import net.minecraft.block.state.{BlockState, IBlockState}
import net.minecraft.block.{Block, ITileEntityProvider}
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{BlockPos, EnumFacing}
import net.minecraft.world.World

object BlockJukebox extends {
  val FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL.asInstanceOf[Predicate[EnumFacing]])
} with Block(Material.wood) with ITileEntityProvider {

  setDefaultState(blockState.getBaseState.withProperty(FACING, EnumFacing.NORTH))
  setUnlocalizedName("Jukebox")

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileJukebox

  override def onBlockDestroyedByPlayer(worldIn: World, pos: BlockPos, state: IBlockState): Unit = {
    val tile = worldIn.getTile[TileJukebox](pos)
    //TODO: Drop items
  }

  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (!worldIn.isRemote) {
      val tile = pos.getTileEntityChecked[TileJukebox](worldIn)
      if (!tile.isDefined) return false
      playerIn.openGui(JukeboxReloaded, GuiHandler.JUKEBOX, worldIn, pos.x, pos.y, pos.z)
    }
    true
  }

  override def getStateFromMeta(meta: Int): IBlockState = {
    var enumfacing = EnumFacing.getFront(meta)
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

  protected override def createBlockState: BlockState = new BlockState(this, FACING)
}
