package net.cazzar.mods.jukeboxreloaded.network.gui.client

import net.cazzar.corelib.client.gui.TexturedButton
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded.JUKEBOX_GUI_TEXTURE
import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.cazzar.mods.jukeboxreloaded.network.gui.server.ContainerJukebox
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.entity.player.EntityPlayer
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
      .setOwner(this))

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
}
