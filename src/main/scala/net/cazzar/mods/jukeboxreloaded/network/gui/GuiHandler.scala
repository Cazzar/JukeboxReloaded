package net.cazzar.mods.jukeboxreloaded.network.gui

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.gui.client.GuiJukebox
import net.cazzar.mods.jukeboxreloaded.network.gui.server.ContainerJukebox
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler
import net.cazzar.mods.jukeboxreloaded.Util._

object GuiHandler extends IGuiHandler{
  final val JUKEBOX = 0

  override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = ID match {
    case JUKEBOX => new ContainerJukebox(player.inventory, new BlockPos(x, y, z).getTileEntityChecked[TileJukebox](world).get)
  }

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = ID match {
    case JUKEBOX => new GuiJukebox(player, new BlockPos(x, y, z).getTileEntityChecked[TileJukebox](world).get)
  }
}
