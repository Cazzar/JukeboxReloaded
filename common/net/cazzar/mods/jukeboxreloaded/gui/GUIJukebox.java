/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.mods.jukeboxreloaded.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.client.gui.TexturedButton;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.Strings;
import net.cazzar.mods.jukeboxreloaded.network.packets.PacketJukeboxDescription;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Random;

import static java.lang.Math.floor;
import static net.cazzar.mods.jukeboxreloaded.lib.Reference.JUKEBOX_GUI_TEXTURE;
import static net.cazzar.mods.jukeboxreloaded.lib.Reference.JukeboxGUIActions.*;

@SideOnly(Side.CLIENT)
public class GUIJukebox extends GuiContainer {
    TileJukebox tileJukebox;
    private TexturedButton btnPlay, btnStop, btnShuffleOn, btnShuffleOff, btnRepeatAll, btnRepeatOne, btnRepeatOff, btnNext, btnPrev;
    private GuiButton volUp, volDown;

    public GUIJukebox(EntityPlayer player, TileJukebox tileJukebox) {
        super(new ContainerJukebox(player.inventory, tileJukebox));
        this.tileJukebox = tileJukebox;
        xSize = 176;
        ySize = 176;
    }

    @Override
    protected void actionPerformed(GuiButton btn) {
        final boolean wasPlaying = tileJukebox.isPlayingRecord();

        switch (btn.id) {
            case PLAY:
                tileJukebox.playSelectedRecord();
                break;
            case STOP:
                tileJukebox.stopPlayingRecord();
                break;
            case NEXT:
                if (wasPlaying) tileJukebox.stopPlayingRecord();
                if (tileJukebox.shuffleEnabled()) {
                    final Random random = new Random();
                    if (tileJukebox.getLastSlotWithItem() <= 0) return;

                    final int nextDisk = random.nextInt(tileJukebox.getLastSlotWithItem());
                    if (tileJukebox.getCurrentRecordNumer() != nextDisk)
                        tileJukebox.setRecordPlaying(nextDisk);
                }
                tileJukebox.nextRecord();
                if (wasPlaying) tileJukebox.playSelectedRecord();
                break;
            case PREVIOUS:
                if (wasPlaying) tileJukebox.stopPlayingRecord();
                tileJukebox.previousRecord();
                if (wasPlaying) tileJukebox.playSelectedRecord();
                break;
            case REPEAT_ALL:
                tileJukebox.setRepeatMode(1);
                break;
            case REPEAT_ONE:
                tileJukebox.setRepeatMode(2);
                break;
            case REPEAT_OFF:
                tileJukebox.setRepeatMode(0);
                break;
            case SHUFFLE:
                tileJukebox.setShuffle(true);
                break;
            case SHUFFLE_OFF:
                tileJukebox.setShuffle(false);
                break;
            case VOLUME_UP:
                if (tileJukebox.volume >= 1F) {
                    tileJukebox.volume = 1F;
                    volUp.enabled = false;
                    volDown.enabled = true;
                    break;
                }

                SoundSystemHelper.getSoundSystem().setVolume(tileJukebox.getIdentifier(), tileJukebox.volume * ClientUtil.mc().gameSettings.soundVolume);

                tileJukebox.volume += 0.05F;
                break;
            case VOLUME_DOWN:
                if (tileJukebox.volume <= 0F) {
                    tileJukebox.volume = 0F;
                    volDown.enabled = false;
                    volUp.enabled = true;
                    break;
                }

                SoundSystemHelper.getSoundSystem().setVolume(tileJukebox.getIdentifier(), tileJukebox.volume * ClientUtil.mc().gameSettings.soundVolume);

                tileJukebox.volume -= 0.05F;
                break;
        }

        if (tileJukebox.volume <= 0F) {
            tileJukebox.volume = 0F;
            volDown.enabled = false;
            volUp.enabled = true;
        } else if (tileJukebox.volume >= 1F) {
            tileJukebox.volume = 1F;
            volUp.enabled = false;
            volDown.enabled = true;
        } else {
            volUp.enabled = true;
            volDown.enabled = true;
        }


        btnPlay.enabled = !(btnStop.enabled = tileJukebox.isPlayingRecord());
        btnShuffleOn.enabled = !tileJukebox.shuffleEnabled();
        btnShuffleOff.enabled = tileJukebox.shuffleEnabled();
        switch (tileJukebox.getReplayMode()) {
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

        new PacketJukeboxDescription(tileJukebox).sendToServer();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
                                                   int var3) {

        btnPlay.enabled = !(btnStop.enabled = tileJukebox.isPlayingRecord());
        btnShuffleOn.enabled = !tileJukebox.shuffleEnabled();
        btnShuffleOff.enabled = tileJukebox.shuffleEnabled();

        switch (tileJukebox.getReplayMode()) {
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
        mc.renderEngine.func_110577_a(JUKEBOX_GUI_TEXTURE);
        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {

        final String containerName = Strings.GUI_JUKEBOX_NAME.toString();
        fontRenderer.drawString(containerName,
                xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6,
                4210752);
        fontRenderer.drawString(
                Strings.GUI_INVENTORY.toString(), 8,
                ySize - 93, 4210752);

        String str = (tileJukebox.volume == 1.0F) ? "10" : String.format("%.1f", tileJukebox.volume * 10);
        fontRenderer.drawString(str, 21, 68, 4210752);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_110577_a(JUKEBOX_GUI_TEXTURE);

        final int xOffset = 53;
        final int yOffset = 16;
        final int size = 18;

        final int index = tileJukebox.getCurrentRecordNumer();
        final int column = (int) floor(index / 4D);
        final int row = index % 4;

        // System.out.println(column + ":" + row + ":" + index);
        // Args: x, y, xOffset, yOffset, Width, Height
        // xOffset + (size * row), yOffset + (size * column),
        drawTexturedModalRect(xOffset + size * row, yOffset + size
                * column, 176, 0, 18, 18);

        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;

        for (GuiButton button : (List<GuiButton>) buttonList) {
            if (!(button instanceof TexturedButton))
                return;

            TexturedButton btn = (TexturedButton) button;

            if (
                    (x >= btn.xPosition && x <= btn.xPosition + btn.getHeight()) &&
                            (y >= btn.yPosition && y <= btn.yPosition + btn.getWidth())
                    )

                if (!btn.getTooltip().trim().isEmpty() && btn.enabled)
                    btn.drawToolTip(x - xStart, y - yStart);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();

        initButtons();
        initTooltips();
    }

    private void initTooltips() {
        btnPlay.setTooltip(Strings.TOOLTIP_PLAY.toString());
        btnStop.setTooltip(Strings.TOOLTIP_STOP.toString());
        btnNext.setTooltip(Strings.TOOLTIP_NEXT.toString());
        btnPrev.setTooltip(Strings.TOOLTIP_PREV.toString());
        btnRepeatAll.setTooltip(Strings.TOOLTIP_REPEAT_ALL.toString());
        btnRepeatOff.setTooltip(Strings.TOOLTIP_REPEAT_NONE.toString());
        btnRepeatOne.setTooltip(Strings.TOOLTIP_REPEAT_ONE.toString());
        btnShuffleOn.setTooltip(Strings.TOOLTIP_SHUFFLE_ON.toString());
        btnShuffleOff.setTooltip(Strings.TOOLTIP_SHUFFLE_OFF.toString());
    }

    @SuppressWarnings("unchecked")
    private void initButtons() {
        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;

        buttonList.add(btnPlay = new TexturedButton(this, PLAY, xStart + 7,
                yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 176, 38, 176, 18,
                176, 58));
        buttonList.add(btnStop = new TexturedButton(this, STOP, xStart + 29,
                yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 176, 98, 176, 78,
                176, 118));

        buttonList.add(btnNext = new TexturedButton(this, NEXT, xStart + 29, yStart + 39, 20,
                20, JUKEBOX_GUI_TEXTURE, 216, 38, 216, 18, 216, 58));
        buttonList.add(btnPrev = new TexturedButton(this, PREVIOUS, xStart + 7, yStart + 39,
                20, 20, JUKEBOX_GUI_TEXTURE, 236, 38, 236, 18, 236, 58));

        buttonList.add(btnRepeatOne = new TexturedButton(this, REPEAT_ONE,
                xStart + 150, yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE,
                196, 98, 196, 78, 196, 118));
        buttonList.add(btnRepeatAll = new TexturedButton(this, REPEAT_ALL,
                xStart + 150, yStart + 40, 20, 20, JUKEBOX_GUI_TEXTURE,
                216, 98, 216, 78, 216, 118));
        buttonList.add(btnRepeatOff = new TexturedButton(this, REPEAT_OFF,
                xStart + 150, yStart + 63, 20, 20, JUKEBOX_GUI_TEXTURE,
                196, 158, 196, 138, 196, 178));

        buttonList.add(btnShuffleOn = new TexturedButton(this, SHUFFLE, xStart + 128,
                yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 236, 98, 236, 78,
                236, 118));
        buttonList.add(btnShuffleOff = new TexturedButton(this, SHUFFLE_OFF,
                xStart + 128, yStart + 40, 20, 20, JUKEBOX_GUI_TEXTURE,
                176, 158, 176, 138, 176, 178));

        buttonList.add(volDown = new GuiButton(VOLUME_DOWN, xStart + 7, yStart + 61, 12, 20, "-"));
        buttonList.add(volUp = new GuiButton(VOLUME_UP, xStart + 37, yStart + 61, 12, 20, "+"));
    }
}
