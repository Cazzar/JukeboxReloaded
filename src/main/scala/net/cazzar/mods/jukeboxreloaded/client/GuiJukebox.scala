package net.cazzar.mods.jukeboxreloaded.client

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.gui.inventory.GuiContainer
import net.cazzar.mods.jukeboxreloaded.common.gui.ContainerJukebox
import net.minecraft.client.gui.GuiButton
import org.lwjgl.opengl.GL11._
import net.cazzar.mods.jukeboxreloaded.common.Reference

class GuiJukebox(player: EntityPlayer, tile: TileJukebox) extends GuiContainer(new ContainerJukebox(player.inventory, tile)) {
    field_146999_f = 176
    field_147000_g = 176

    def func_146976_a(p1: Float, p2: Int, p3: Int) = {
        //updateButtonStates
        glColor4f(1F, 1F, 1F, 1F)
        field_146297_k.renderEngine.bindTexture(Reference.JUKEBOX_GUI_TEXTURE)
        val xStart = (this.field_146294_l - this.field_146999_f) / 2
        val yStart = (this.field_146295_m - this.field_147000_g) / 2

        drawTexturedModalRect(xStart, yStart, 0, 0, field_146999_f, field_147000_g)
    }

    //actionPerformed
    override def func_146284_a(p_146284_1_ : GuiButton) = {

    }
}
