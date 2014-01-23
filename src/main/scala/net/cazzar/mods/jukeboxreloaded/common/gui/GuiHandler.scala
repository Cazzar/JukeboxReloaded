package net.cazzar.mods.jukeboxreloaded.common.gui

import cpw.mods.fml.common.network.IGuiHandler
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.cazzar.mods.jukeboxreloaded.client.GuiJukebox
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox

object GuiHandler extends IGuiHandler{
    val JUKEBOX: Int = 0

    def getServerGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = id match {
        case JUKEBOX => {
            val tile: TileJukebox = world.func_147438_o(x, y, z).asInstanceOf[TileJukebox]
            new ContainerJukebox(player.inventory, tile)
        }
    }

    def getClientGuiElement(id: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef = id match {
        case JUKEBOX => {
            val tile: TileJukebox = world.func_147438_o(x, y, z).asInstanceOf[TileJukebox]
            new GuiJukebox(player, tile)
        }
    }
}
