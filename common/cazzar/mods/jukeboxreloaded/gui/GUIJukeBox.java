package cazzar.mods.jukeboxreloaded.gui;

import static java.lang.Math.floor;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJUKEBOX_TEXTURE_FILE;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.*;
import codechicken.core.packet.PacketCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIJukeBox extends GuiContainer {
	TileJukeBox tileJukeBox;
	private TexturedButton btnPlay, btnStop, btnShuffle, btnShuffleOff,
			btnRepeatAll, btnRepeatOne, btnRepeatOff;

	public GUIJukeBox(EntityPlayer player, TileJukeBox tileJukeBox) {
		super(new ContainerJukeBox(player.inventory, tileJukeBox));
		this.tileJukeBox = tileJukeBox;
		xSize = 175;
		ySize = 177;
	}

	@Override
	protected void actionPerformed(GuiButton btn) {
		final boolean wasPlaying = tileJukeBox.isPlayingRecord();
		// final PacketCustom packet = new PacketCustom(Reference.CHANNEL_NAME,
		// 1);
		// packet.writeCoord(tileJukeBox.getCoord());
		// packet.writeInt(btn.id);
		// packet.sendToServer();

		switch (btn.id) {
		case PLAY:
			// btnPlayplayRecord(
			// playRecord(((ItemRecord)tileJukeBox.getStackInSlot(recordNumber).getItem()).getRecordTitle(),
			// getRecordInfo(tileJukeBox.getStackInSlot(recordNumber)),tileJukeBox.worldObj,
			// tileJukeBox.xCoord, tileJukeBox.yCoord, tileJukeBox.zCoord);
			tileJukeBox.playSelectedRecord();
			// tileJukeBox.nextRecord();
			break;
		case STOP:
			tileJukeBox.stopPlayingRecord();
			// tileJukeBox.resetPlayingRecord();
			break;
		case NEXT:
			if (wasPlaying) {
				tileJukeBox.stopPlayingRecord();
			}
			tileJukeBox.nextRecord();
			if (wasPlaying) {
				tileJukeBox.playSelectedRecord();
			}
			break;
		case PREVIOUS:
			if (wasPlaying) {
				tileJukeBox.stopPlayingRecord();
			}
			tileJukeBox.previousRecord();
			if (wasPlaying) {
				tileJukeBox.playSelectedRecord();
			}
			break;
		case REPEAT_ALL:
			tileJukeBox.setRepeatMode(1);
			break;
		case REPEAT_ONE:
			tileJukeBox.setRepeatMode(2);
			break;
		case REPEAT_OFF:
			tileJukeBox.setRepeatMode(0);
			break;
		case SHUFFLE:
			tileJukeBox.setShuffle(true);
			break;
		case SHUFFLE_OFF:
			tileJukeBox.setShuffle(false);
			break;
		}
		btnPlay.enabled = !(btnStop.enabled = tileJukeBox.isPlayingRecord());
		btnShuffle.enabled = !tileJukeBox.shuffleEnabled();
		btnShuffleOff.enabled = tileJukeBox.shuffleEnabled();
		switch (tileJukeBox.getReplayMode()) {
		case 0:
			btnRepeatOff.enabled = false;
			btnRepeatOne.enabled = true;
			btnRepeatAll.enabled = true;
			break;
		case 1:
			btnRepeatOff.enabled = true;
			btnRepeatOne.enabled = true;
			btnRepeatAll.enabled = false;
			break;
		case 2:
			btnRepeatOff.enabled = true;
			btnRepeatOne.enabled = false;
			btnRepeatAll.enabled = true;
			break;
		}
	
		// send tile information to the server to update the other clients
		final PacketCustom packet = new PacketCustom(Reference.CHANNEL_NAME, 1);
		packet.writeCoord(tileJukeBox.getCoord());
		packet.writeBoolean(tileJukeBox.isPlayingRecord());
		packet.writeInt(tileJukeBox.getCurrentRecordNumer());
		packet.writeInt(tileJukeBox.getReplayMode());
		packet.writeBoolean(tileJukeBox.shuffleEnabled());
		packet.sendToServer();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUIJUKEBOX_TEXTURE_FILE);
		final int xStart = (width - xSize) / 2;
		final int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {

		final String containerName = "JukeBox";
		fontRenderer.drawString(containerName,
				xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6,
				4210752);
		fontRenderer.drawString(
				StatCollector.translateToLocal("container.inventory"), 8,
				ySize - 93, 4210752);

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUIJUKEBOX_TEXTURE_FILE);

		final int xOffset = 53;
		final int yOffset = 16;
		final int size = 18;

		final int index = tileJukeBox.getCurrentRecordNumer();
		final int column = (int) floor(index / 4D);
		final int row = index % 4;

		// System.out.println(column + ":" + row + ":" + index);
		// Args: x, y, xOffset, yOffset, Width, Height
		// xOffset + (size * row), yOffset + (size * column),
		drawTexturedModalRect(0 + xOffset + size * row, 0 + yOffset + size
				* column, 176, 0, 18, 18);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		// id, x, y, W, H
		// this.buttonList.add(this.button = new GuiCCButton(0, 28, 17, 40, 10,
		// "Play"));
		final int xStart = (width - xSize) / 2;
		final int yStart = (height - ySize) / 2;

		// buttonList.add(btnPlay = new GuiButton(0, xStart + 10, yStart + 17,
		// 40, 20, "Play"));
		// buttonList.add(btnStop = new GuiButton(1, xStart + 10, yStart + 40,
		// 40, 20, "Stop"));

		// buttonList.add(new GuiButton(2, xStart + 128, yStart + 17, 40, 20,
		// "Next"));
		// buttonList.add(new GuiButton(3, xStart + 128, yStart + 40, 40, 20,
		// "Prev"));

		buttonList.add(btnPlay = new TexturedButton(PLAY, xStart + 10,
				yStart + 17, 20, 20, GUIJUKEBOX_TEXTURE_FILE, 176, 38, 176, 18,
				176, 58));
		buttonList.add(btnStop = new TexturedButton(STOP, xStart + 32,
				yStart + 17, 20, 20, GUIJUKEBOX_TEXTURE_FILE, 176, 98, 176, 78,
				176, 118));

		buttonList.add(new TexturedButton(NEXT, xStart + 32, yStart + 39, 20,
				20, GUIJUKEBOX_TEXTURE_FILE, 216, 38, 216, 18, 216, 58));
		buttonList.add(new TexturedButton(PREVIOUS, xStart + 10, yStart + 39,
				20, 20, GUIJUKEBOX_TEXTURE_FILE, 236, 38, 236, 18, 236, 58));

		// buttonList.add(btnShuffle = new TexturedButton(SHUFFLE, xStart + 128,
		// yStart + 17, 20, 20, GUIJUKEBOX_TEXTURE_FILE, 233, 98, 233, 78, 233,
		// 118));
		buttonList.add(btnRepeatOne = new TexturedButton(REPEAT_ONE,
				xStart + 150, yStart + 17, 20, 20, GUIJUKEBOX_TEXTURE_FILE,
				196, 98, 196, 78, 196, 118));
		buttonList.add(btnRepeatAll = new TexturedButton(REPEAT_ALL,
				xStart + 150, yStart + 40, 20, 20, GUIJUKEBOX_TEXTURE_FILE,
				216, 98, 216, 78, 216, 118));
		buttonList.add(btnRepeatOff = new TexturedButton(REPEAT_OFF,
				xStart + 150, yStart + 63, 20, 20, GUIJUKEBOX_TEXTURE_FILE,
				196, 158, 196, 138, 196, 178));

		buttonList.add(btnShuffle = new TexturedButton(SHUFFLE, xStart + 128,
				yStart + 17, 20, 20, GUIJUKEBOX_TEXTURE_FILE, 236, 98, 236, 78,
				236, 118));
		buttonList.add(btnShuffleOff = new TexturedButton(SHUFFLE_OFF,
				xStart + 128, yStart + 40, 20, 20, GUIJUKEBOX_TEXTURE_FILE,
				176, 158, 176, 138, 176, 178));

		// buttonList.add(new TexturedButton(4, xStart + 128, yStart + 57, 20,
		// 20, GUIJUKEBOX_TEXTURE_FILE, 176, 38, 176, 18, 176, 58));
		btnPlay.enabled = !(btnStop.enabled = tileJukeBox.isPlayingRecord());
		btnShuffle.enabled = !tileJukeBox.shuffleEnabled();
		btnShuffleOff.enabled = tileJukeBox.shuffleEnabled();
		switch (tileJukeBox.getReplayMode()) {
		case 0:
			btnRepeatOff.enabled = false;
			btnRepeatOne.enabled = true;
			btnRepeatAll.enabled = true;
			break;
		case 1:
			btnRepeatOff.enabled = true;
			btnRepeatOne.enabled = true;
			btnRepeatAll.enabled = false;
			break;
		case 2:
			btnRepeatOff.enabled = true;
			btnRepeatOne.enabled = false;
			btnRepeatAll.enabled = true;
			break;
		}
	}
}
