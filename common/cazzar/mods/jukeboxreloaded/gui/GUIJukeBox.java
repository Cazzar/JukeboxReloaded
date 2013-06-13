package cazzar.mods.jukeboxreloaded.gui;

import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJUKEBOX_TEXTURE_FILE;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.NEXT;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.PLAY;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.PREVIOUS;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.REPEAT_ALL;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.REPEAT_OFF;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.REPEAT_ONE;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.SHUFFLE;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.SHUFFLE_OFF;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.STOP;
import static java.lang.Math.floor;

import java.util.Random;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.opengl.GL11;

import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Strings;
import cazzar.mods.jukeboxreloaded.network.packets.PacketJukeboxDescription;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIJukeBox extends GuiContainer {
	TileJukeBox	tileJukeBox;
	private TexturedButton	btnPlay, btnStop, btnShuffle, btnShuffleOff,
			btnRepeatAll, btnRepeatOne, btnRepeatOff;
	
	public GUIJukeBox(EntityPlayer player, TileJukeBox tileJukeBox) {
		super(new ContainerJukeBox(player.inventory, tileJukeBox));
		this.tileJukeBox = tileJukeBox;
		xSize = 176;
		ySize = 176;
	}
	
	@Override
	protected void actionPerformed(GuiButton btn) {
		final boolean wasPlaying = tileJukeBox.isPlayingRecord();
		
		switch (btn.id) {
			case PLAY:
				tileJukeBox.playSelectedRecord();
				break;
			case STOP:
				tileJukeBox.stopPlayingRecord();
				break;
			case NEXT:
				if (wasPlaying) tileJukeBox.stopPlayingRecord();
				if (tileJukeBox.shuffleEnabled()) {
					final Random random = new Random();
					if (tileJukeBox.getLastSlotWithItem() <= 0) return;
					final int nextDisk = random.nextInt(tileJukeBox
							.getLastSlotWithItem());
					if (tileJukeBox.getCurrentRecordNumer() != nextDisk)
						tileJukeBox.setRecordPlaying(nextDisk);
				}
				tileJukeBox.nextRecord();
				if (wasPlaying) tileJukeBox.playSelectedRecord();
				break;
			case PREVIOUS:
				if (wasPlaying) tileJukeBox.stopPlayingRecord();
				tileJukeBox.previousRecord();
				if (wasPlaying) tileJukeBox.playSelectedRecord();
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
		
		PacketDispatcher.sendPacketToServer((new PacketJukeboxDescription(
				tileJukeBox)).makePacket());
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		
		
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
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(GUIJUKEBOX_TEXTURE_FILE);
		final int xStart = (width - xSize) / 2;
		final int yStart = (height - ySize) / 2;
		drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		
		final String containerName = Strings.GUI_JUKEBOX_NAME.toString();
		fontRenderer.drawString(containerName,
				xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6,
				4210752);
		fontRenderer.drawString(
				Strings.GUI_INVENTORY.toString(), 8,
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
		final int xStart = (width - xSize) / 2;
		final int yStart = (height - ySize) / 2;
		
		buttonList.add(btnPlay = new TexturedButton(PLAY, xStart + 7,
				yStart + 17, 20, 20, GUIJUKEBOX_TEXTURE_FILE, 176, 38, 176, 18,
				176, 58));
		buttonList.add(btnStop = new TexturedButton(STOP, xStart + 29,
				yStart + 17, 20, 20, GUIJUKEBOX_TEXTURE_FILE, 176, 98, 176, 78,
				176, 118));
		
		buttonList.add(new TexturedButton(NEXT, xStart + 29, yStart + 39, 20,
				20, GUIJUKEBOX_TEXTURE_FILE, 216, 38, 216, 18, 216, 58));
		buttonList.add(new TexturedButton(PREVIOUS, xStart + 7, yStart + 39,
				20, 20, GUIJUKEBOX_TEXTURE_FILE, 236, 38, 236, 18, 236, 58));
		
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
	}
}
