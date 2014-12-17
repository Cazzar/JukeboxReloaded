package net.cazzar.mods.jukeboxreloaded.network.gui.client

import java.lang.Math._

import net.cazzar.corelib.client.gui.TexturedButton
import net.cazzar.corelib.client.gui.handler.IGUIAction
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded.JUKEBOX_GUI_TEXTURE
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.gui.server.ContainerJukebox
import net.cazzar.mods.jukeboxreloaded.util.Strings
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.StatCollector
import org.lwjgl.opengl.GL11

class GuiJukebox(player: EntityPlayer, tile: TileJukebox) extends GuiContainer(ContainerJukebox(player.inventory, tile)) {
  val btnPlay = new TexturedButton()
  val btnStop = new TexturedButton()
  val btnShuffleOn = new TexturedButton()
  val btnShuffleOff = new TexturedButton()
  val btnRepeatAll = new TexturedButton()
  val btnRepeatOne = new TexturedButton()
  val btnRepeatOff = new TexturedButton()
  val btnNext = new TexturedButton()
  val btnPrev = new TexturedButton()

  xSize = 176
  ySize = 176

  def add[T](list: java.util.List[T], value: Any) = list.add(value.asInstanceOf[T])

  override def drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int) = {
    updateButtonStates()
    GL11.glColor4f(1, 1, 1, 1)
    mc.renderEngine.bindTexture(JUKEBOX_GUI_TEXTURE)
    drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize)
  }


  override def mouseClicked(mouseX: Int, mouseY: Int, mouseButton: Int): Unit = {
    super.mouseClicked(mouseX, mouseY, mouseButton)
  }

  override def drawGuiContainerForegroundLayer(mouseX: Int, mouseY: Int): Unit = {
    val name = if (tile.hasCustomName) StatCollector.translateToLocal(tile.name) else Strings.GUI_JUKEBOX_NAME.toString
    fontRendererObj.drawString(name, xSize / 2 - fontRendererObj.getStringWidth(name) / 2, 6, 0x404040)
    fontRendererObj.drawString(Strings.GUI_INVENTORY.toString, 8, ySize - 93, 0x404040)

    GL11.glColor4f(1, 1, 1, 1)
    mc.renderEngine.bindTexture(JUKEBOX_GUI_TEXTURE)

    val xOffset = 53
    val yOffset = 16
    val size = 18

    val index = tile.record
    val column = floor(index / 4D).toInt
    val row = index % 4

    drawTexturedModalRect(xOffset + size * row, yOffset + size * column, 176, 0, 18, 18)

    for (button <- buttonList.asInstanceOf[List[GuiButton]]) {
      if (!button.isInstanceOf[TexturedButton]) return
      val btn: TexturedButton = button.asInstanceOf[TexturedButton]
      if ((mouseX >= btn.xPosition && mouseX <= btn.xPosition + btn.getHeight) && (mouseY >= btn.yPosition && mouseY <= btn.yPosition + btn.getWidth))
        if (btn.getTooltipList.size() != 0 && btn.enabled)
          btn.drawToolTip(mouseX - guiLeft, mouseY - guiTop)
    }
  }

  def updateButtonStates() = {

  }

  override def initGui(): Unit = {
    super.initGui()

    add(buttonList, btnPlay.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(176, 38)
      .setDisabledOffsets(176, 18)
      .setHoveredOffsets(176, 58)
      .setPosition(guiLeft + 7, guiTop + 17)
      .setSize(20, 20)
      .setOwner(this)
      .addListener(() => tile.playRecord()))

    add(buttonList, btnStop.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(176, 98)
      .setDisabledOffsets(176, 78)
      .setHoveredOffsets(176, 118)
      .setPosition(guiLeft + 29, guiTop + 17)
      .setSize(20, 20)
      .setOwner(this))

    add(buttonList, btnNext.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(216, 38)
      .setDisabledOffsets(216, 18)
      .setHoveredOffsets(216, 58)
      .setPosition(guiLeft + 29, guiTop + 39)
      .setSize(20, 20)
      .setOwner(this))

    add(buttonList, btnPrev.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(236, 38)
      .setDisabledOffsets(236, 18)
      .setHoveredOffsets(236, 58)
      .setPosition(guiLeft + 7, guiTop + 39)
      .setSize(20, 20)
      .setOwner(this))

    add(buttonList, btnRepeatOne.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(196, 98)
      .setDisabledOffsets(196, 78)
      .setHoveredOffsets(196, 118)
      .setPosition(guiLeft + 150, guiTop + 17)
      .setSize(20, 20)
      .setOwner(this))

    add(buttonList, btnRepeatAll.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(216, 98)
      .setDisabledOffsets(216, 78)
      .setHoveredOffsets(216, 118)
      .setPosition(guiLeft + 150, guiTop + 40)
      .setSize(20, 20)
      .setOwner(this))

    add(buttonList, btnRepeatOff.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(196, 158)
      .setDisabledOffsets(196, 138)
      .setHoveredOffsets(196, 178)
      .setPosition(guiLeft + 150, guiTop + 63)
      .setSize(20, 20)
      .setOwner(this))

    add(buttonList, btnShuffleOn.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(236, 98)
      .setDisabledOffsets(236, 78)
      .setHoveredOffsets(236, 118)
      .setPosition(guiLeft + 128, guiTop + 17)
      .setSize(20, 20)
      .setOwner(this))

    add(buttonList, btnShuffleOff.setTexture(JUKEBOX_GUI_TEXTURE)
      .setOffsets(176, 158)
      .setDisabledOffsets(176, 138)
      .setHoveredOffsets(176, 178)
      .setPosition(guiLeft + 128, guiTop + 40)
      .setSize(20, 20)
      .setOwner(this))
  }

  implicit def unitToAction(unit: () => Unit): IGUIAction = new IGUIAction {
    override def click(): Unit = unit()
  }
}
