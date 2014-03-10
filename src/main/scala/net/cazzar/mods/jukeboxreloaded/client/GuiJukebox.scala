package net.cazzar.mods.jukeboxreloaded.client

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.client.gui.inventory.GuiContainer
import net.cazzar.mods.jukeboxreloaded.common.gui.ContainerJukebox
import net.minecraft.client.gui.GuiButton
import org.lwjgl.opengl.GL11._
import net.cazzar.mods.jukeboxreloaded.common.{Strings, Reference}
import net.cazzar.mods.jukeboxreloaded.common.ReplayMode._
import Reference.JUKEBOX_GUI_TEXTURE
import Reference.GuiActions._
import net.cazzar.corelib.client.gui.TexturedButton
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded
import cpw.mods.fml.relauncher.Side
import cpw.mods.fml.common.network.FMLOutboundHandler
import cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget
import net.cazzar.mods.jukeboxreloaded.networking.packets.PacketJukeboxGuiAction

class GuiJukebox(player: EntityPlayer, tile: TileJukebox) extends GuiContainer(new ContainerJukebox(player.inventory, tile)) {
    xSize = 176
    ySize = 176

    //(width - xSize) / 2
    def xStart = (this.width - this.xSize) / 2

    //(height - ySize) / 2
    def yStart = (this.height - this.ySize) / 2

    var btnPlay: TexturedButton = null
    var btnStop: TexturedButton = null
    var btnNext: TexturedButton = null
    var btnPrev: TexturedButton = null
    var btnRepeatOne: TexturedButton = null
    var btnRepeatAll: TexturedButton = null
    var btnRepeatOff: TexturedButton = null
    var btnShuffleOn: TexturedButton = null
    var btnShuffleOff: TexturedButton = null

    override def func_146979_b(x: Int, y: Int) = {
        val name = Strings.GUI_JUKEBOX_NAME
        fontRendererObj.drawString(name, width / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 4210752)
        fontRendererObj.drawString(Strings.GUI_INVENTORY, 8, field_147000_g - 93, 4210752)

        glColor4f(1.0F, 1.0F, 1.0F, 1.0F)
        field_146297_k.renderEngine.bindTexture(JUKEBOX_GUI_TEXTURE)
        val xOffset = 53
        val yOffset = 16
        val size = 18

        val index = tile.current
        val column = Math.floor(index / 4D).asInstanceOf[Int]
        val row = index % 4

        drawTexturedModalRect(xOffset + size * row, yOffset + size * column, 176, 0, 18, 18)

        import scala.collection.JavaConversions._
        for (button <- field_146292_n.asInstanceOf[java.util.List[GuiButton]]) {
            button match {
                case btn: TexturedButton =>
                    //if ((x >= btn.xPosition && x <= btn.xPosition + btn.getHeight()) && (y >= btn.yPosition && y <= btn.yPosition + btn.getWidth()))
                    if ((x >= btn.field_146128_h && x <= btn.field_146128_h + btn.getHeight) && (y >= btn.field_146129_i && y <= btn.field_146129_i + btn.getWidth))
                        if (!btn.getTooltip.trim().isEmpty && btn.field_146124_l) {
                            btn.drawToolTip(x - xStart, y - yStart)
                        }
                case _ =>
            }
        }
    }

    def func_146976_a(p1: Float, p2: Int, p3: Int) = {
        updateButtonStates()
        glColor4f(1F, 1F, 1F, 1F)
        field_146297_k.renderEngine.bindTexture(Reference.JUKEBOX_GUI_TEXTURE)
        val xStart = (this.field_146294_l - this.field_146999_f) / 2
        val yStart = (this.field_146295_m - this.field_147000_g) / 2

        drawTexturedModalRect(xStart, yStart, 0, 0, field_146999_f, field_147000_g)
    }

    //actionPerformed
    override def func_146284_a(button: GuiButton) = {
        //this is only server -> client so we have to hack backwards.
        //tile.markForUpdate()

        JukeboxReloaded.proxy.channel.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(OutboundTarget.TOSERVER)
        JukeboxReloaded.proxy.channel.get(Side.CLIENT).writeOutbound(new PacketJukeboxGuiAction(tile, button.field_146127_k))
    }

    def initButtons() = {
        btnPlay = new TexturedButton(this, PLAY, xStart + 7, yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 176, 38, 176, 18, 176, 58)
        btnStop = new TexturedButton(this, STOP, xStart + 29, yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 176, 98, 176, 78, 176, 118)
        btnNext = new TexturedButton(this, NEXT, xStart + 29, yStart + 39, 20, 20, JUKEBOX_GUI_TEXTURE, 216, 38, 216, 18, 216, 58)
        btnPrev = new TexturedButton(this, PREVIOUS, xStart + 7, yStart + 39, 20, 20, JUKEBOX_GUI_TEXTURE, 236, 38, 236, 18, 236, 58)
        btnRepeatOne = new TexturedButton(this, REPEAT_ONE, xStart + 150, yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 196, 98, 196, 78, 196, 118)
        btnRepeatAll = new TexturedButton(this, REPEAT_ALL, xStart + 150, yStart + 40, 20, 20, JUKEBOX_GUI_TEXTURE, 216, 98, 216, 78, 216, 118)
        btnRepeatOff = new TexturedButton(this, REPEAT_OFF, xStart + 150, yStart + 63, 20, 20, JUKEBOX_GUI_TEXTURE, 196, 158, 196, 138, 196, 178)
        btnShuffleOn = new TexturedButton(this, SHUFFLE, xStart + 128, yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 236, 98, 236, 78, 236, 118)
        btnShuffleOff = new TexturedButton(this, SHUFFLE_OFF, xStart + 128, yStart + 40, 20, 20, JUKEBOX_GUI_TEXTURE, 176, 158, 176, 138, 176, 178)

        val list: java.util.List[GuiButton] = field_146292_n.asInstanceOf[java.util.List[GuiButton]]
        list.add(btnPlay)
        list.add(btnStop)
        list.add(btnNext)
        list.add(btnPrev)
        list.add(btnRepeatOne)
        list.add(btnRepeatAll)
        list.add(btnRepeatOff)
        list.add(btnShuffleOn)
        list.add(btnShuffleOff)
    }

    override def initGui() = {
        super.initGui()

        initButtons()
        initTooltips()
        updateButtonStates()
    }

    def initTooltips() = {
        btnPlay.setTooltip(Strings.TOOLTIP_PLAY)
        btnStop.setTooltip(Strings.TOOLTIP_STOP)
        btnNext.setTooltip(Strings.TOOLTIP_NEXT)
        btnPrev.setTooltip(Strings.TOOLTIP_PREV)
        btnRepeatAll.setTooltip(Strings.TOOLTIP_REPEAT_ALL)
        btnRepeatOff.setTooltip(Strings.TOOLTIP_REPEAT_NONE)
        btnRepeatOne.setTooltip(Strings.TOOLTIP_REPEAT_ONE)
        btnShuffleOn.setTooltip(Strings.TOOLTIP_SHUFFLE_ON)
        btnShuffleOff.setTooltip(Strings.TOOLTIP_SHUFFLE_OFF)
    }


    def updateButtonStates() = {
        btnStop.field_146124_l = tile.playing
        btnPlay.field_146124_l = !tile.playing
        btnShuffleOn.field_146124_l = !tile.shuffle
        btnShuffleOff.field_146124_l = tile.shuffle

        tile.replayMode match {
            case ALL =>
                btnRepeatAll.field_146124_l = false
                btnRepeatOne.field_146124_l = true
                btnRepeatOff.field_146124_l = true
            case ONE =>
                btnRepeatAll.field_146124_l = true
                btnRepeatOne.field_146124_l = false
                btnRepeatOff.field_146124_l = true
            case OFF =>
                btnRepeatAll.field_146124_l = true
                btnRepeatOne.field_146124_l = true
                btnRepeatOff.field_146124_l = false
        }
    }
}
