/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
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

package net.cazzar.mods.jukeboxreloaded.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.client.gui.TexturedButton;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.Strings;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.cazzar.mods.jukeboxreloaded.network.packet.ClientAction;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import java.util.List;

import static java.lang.Math.floor;
import static net.cazzar.mods.jukeboxreloaded.lib.Reference.JUKEBOX_GUI_TEXTURE;

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
        ClientAction.Action action = null;

        if (btn == btnNext) action = ClientAction.Action.NEXT;
        else if (btn == btnPrev) action = ClientAction.Action.PREVIOUS;
        else if (btn == btnRepeatAll) action = ClientAction.Action.REPEAT_ALL;
        else if (btn == btnRepeatOne) action = ClientAction.Action.REPEAT_ONE;
        else if (btn == btnRepeatOff) action = ClientAction.Action.REPEAT_OFF;
        else if (btn == btnShuffleOn) action = ClientAction.Action.SHUFFLE_ON;
        else if (btn == btnShuffleOff) action = ClientAction.Action.SHUFFLE_OFF;


/*        switch (btn.id) {
            case PLAY:
                tileJukebox.playSelectedRecord();
                break;
            case STOP:
                //no need to set action due to the function sends it.
                tileJukebox.stopPlayingRecord();
                break;
            case NEXT:
                //logic for next and also looping.
                action = ClientAction.Action.NEXT;
                if (wasPlaying) tileJukebox.stopPlayingRecord();
                if (tileJukebox.shuffleEnabled()) {
                    final Random random = new Random();
                    if (tileJukebox.getLastSlotWithItem() <= 0) return;

                    final int nextDisk = random.nextInt(tileJukebox.getLastSlotWithItem());
                    if (tileJukebox.getCurrentRecordNumber() != nextDisk)
                        tileJukebox.setRecordPlaying(nextDisk);
                }
                tileJukebox.nextRecord();
                if (wasPlaying) tileJukebox.playSelectedRecord();
                break;
            case PREVIOUS:
                action = ClientAction.Action.PREVIOUS;
                if (wasPlaying) tileJukebox.stopPlayingRecord();
                tileJukebox.previousRecord();
                if (wasPlaying) tileJukebox.playSelectedRecord();
                break;

            case REPEAT_ALL:
                //repeat
                action = ClientAction.Action.REPEAT_ALL;
                tileJukebox.setRepeatMode(RepeatMode.ALL);
                break;
            case REPEAT_ONE:
                action = ClientAction.Action.REPEAT_ONE;
                tileJukebox.setRepeatMode(RepeatMode.ONE);
                break;
            case REPEAT_OFF:
                action = ClientAction.Action.REPEAT_OFF;
                tileJukebox.setRepeatMode(RepeatMode.OFF);
                break;

            case SHUFFLE:
                //shuffle
                action = ClientAction.Action.SHUFFLE_ON;
                tileJukebox.setShuffle(true);
                break;
            case SHUFFLE_OFF:
                action = ClientAction.Action.SHUFFLE_OFF;
                tileJukebox.setShuffle(false);
                break;
            case VOLUME_UP:
                if (tileJukebox.volume >= 1F) {
                    tileJukebox.volume = 1F;
                    break;
                }

                tileJukebox.volume += 0.05F;
                SoundSystemHelper.getSoundSystem().setVolume(tileJukebox.getIdentifier(), tileJukebox.volume * ClientUtil.mc().gameSettings.getSoundLevel(SoundCategory.RECORDS));
                break;
            case VOLUME_DOWN:
                if (tileJukebox.volume <= 0F) {
                    tileJukebox.volume = 0F;
                    break;
                }

                tileJukebox.volume -= 0.05F;
                SoundSystemHelper.getSoundSystem().setVolume(tileJukebox.getIdentifier(), tileJukebox.volume * ClientUtil.mc().gameSettings.getSoundLevel(SoundCategory.RECORDS));
                break;
        }*/

        updateButtonStates();
        if (action != null) PacketHandler.INSTANCE.sendToServer(new ClientAction(action, tileJukebox));

        tileJukebox.markForUpdate();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        updateButtonStates();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(JUKEBOX_GUI_TEXTURE);
        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {

        final String containerName = Strings.GUI_JUKEBOX_NAME.toString();
        this.fontRendererObj.drawString(containerName,
                                               xSize / 2 - fontRendererObj.getStringWidth(containerName) / 2, 6,
                                               4210752);
        fontRendererObj.drawString(
                                          Strings.GUI_INVENTORY.toString(), 8,
                                          ySize - 93, 4210752);

//        String str = (tileJukebox.volume == 1.0F) ? "10" : String.format("%.1f", tileJukebox.volume * 10);
//        fontRendererObj.drawString(str, 21, 68, 4210752);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(JUKEBOX_GUI_TEXTURE);

        final int xOffset = 53;
        final int yOffset = 16;
        final int size = 18;

        final int index = tileJukebox.getCurrentRecordNumber();
        final int column = (int) floor(index / 4D);
        final int row = index % 4;

        drawTexturedModalRect(xOffset + size * row, yOffset + size * column, 176, 0, 18, 18);

        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;

        for (GuiButton button : (List<GuiButton>) buttonList) {
            if (!(button instanceof TexturedButton))
                return;
            TexturedButton btn = (TexturedButton) button;
            if ((x >= btn.xPosition && x <= btn.xPosition + btn.getHeight()) && (y >= btn.yPosition && y <= btn.yPosition + btn.getWidth()))
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

       /*buttonList.add(btnShuffleOn = new  TexturedButton(this, SHUFFLE, xStart + 128, yStart + 17, 20, 20, JUKEBOX_GUI_TEXTURE, 236, 98, 236, 78, 236, 118));*/

        buttonList.add(btnPlay = (TexturedButton) new TexturedButton()
                                                          .setTexture(JUKEBOX_GUI_TEXTURE)
                                                          .setOffsets(176, 38)
                                                          .setDisabledOffsets(176, 18)
                                                          .setHoveredOffsets(176, 58)
                                                          .setPosition(xStart + 7, yStart + 17)
                                                          .setSize(20, 20)
                                                          .setOwner(this));

        buttonList.add(btnStop = (TexturedButton) new TexturedButton()
                                                          .setTexture(JUKEBOX_GUI_TEXTURE)
                                                          .setOffsets(176, 98)
                                                          .setDisabledOffsets(176, 78)
                                                          .setHoveredOffsets(176, 118)
                                                          .setPosition(xStart + 29, yStart + 17)
                                                          .setSize(20, 20)
                                                          .setOwner(this));

        buttonList.add(btnNext = (TexturedButton) new TexturedButton()
                                                          .setTexture(JUKEBOX_GUI_TEXTURE)
                                                          .setOffsets(216, 38)
                                                          .setDisabledOffsets(216, 18)
                                                          .setHoveredOffsets(216, 58)
                                                          .setPosition(xStart + 29, yStart + 39)
                                                          .setSize(20, 20)
                                                          .setOwner(this));

        buttonList.add(btnPrev = (TexturedButton) new TexturedButton()
                                                          .setTexture(JUKEBOX_GUI_TEXTURE)
                                                          .setOffsets(236, 38)
                                                          .setDisabledOffsets(236, 18)
                                                          .setHoveredOffsets(236, 58)
                                                          .setPosition(xStart + 7, yStart + 39)
                                                          .setSize(20, 20)
                                                          .setOwner(this));

        buttonList.add(btnRepeatOne = (TexturedButton) new TexturedButton()
                                                               .setTexture(JUKEBOX_GUI_TEXTURE)
                                                               .setOffsets(236, 38)
                                                               .setDisabledOffsets(236, 18)
                                                               .setHoveredOffsets(236, 58)
                                                               .setPosition(xStart + 150, yStart + 17)
                                                               .setSize(20, 20)
                                                               .setOwner(this));

        buttonList.add(btnRepeatAll = (TexturedButton) new TexturedButton()
                                                               .setTexture(JUKEBOX_GUI_TEXTURE)
                                                               .setOffsets(236, 38)
                                                               .setDisabledOffsets(236, 18)
                                                               .setHoveredOffsets(236, 58)
                                                               .setPosition(xStart + 150, yStart + 40)
                                                               .setSize(20, 20)
                                                               .setOwner(this));

        buttonList.add(btnRepeatOff = (TexturedButton) new TexturedButton()
                                                               .setTexture(JUKEBOX_GUI_TEXTURE)
                                                               .setOffsets(196, 158)
                                                               .setDisabledOffsets(196, 138)
                                                               .setHoveredOffsets(196, 178)
                                                               .setPosition(xStart + 150, yStart + 63)
                                                               .setSize(20, 20)
                                                               .setOwner(this));


        buttonList.add(btnShuffleOn = (TexturedButton) new TexturedButton()
                                                               .setTexture(JUKEBOX_GUI_TEXTURE)
                                                               .setOffsets(236, 98)
                                                               .setDisabledOffsets(236, 78)
                                                               .setHoveredOffsets(236, 118)
                                                               .setPosition(xStart + 128, yStart + 17)
                                                               .setSize(20, 20)
                                                               .setOwner(this));

        buttonList.add(btnShuffleOff = (TexturedButton) new TexturedButton()
                                                                .setTexture(JUKEBOX_GUI_TEXTURE)
                                                                .setOffsets(216, 98)
                                                                .setDisabledOffsets(216, 78)
                                                                .setHoveredOffsets(216, 118)
                                                                .setPosition(xStart + 128, yStart + 40)
                                                                .setSize(20, 20)
                                                                .setOwner(this));

//        buttonList.add(volDown = new GuiButton(VOLUME_DOWN, xStart + 7, yStart + 61, 12, 20, "-"));
//        buttonList.add(volUp = new GuiButton(VOLUME_UP, xStart + 37, yStart + 61, 12, 20, "+"));
    }

    @SuppressWarnings("RedundantCast")
    public void updateButtonStates() {

        ((GuiButton) btnPlay).enabled = !(((GuiButton) btnStop).enabled = tileJukebox.isPlayingRecord());
        ((GuiButton) btnShuffleOn).enabled = !tileJukebox.shuffleEnabled();
        ((GuiButton) btnShuffleOff).enabled = tileJukebox.shuffleEnabled();

        switch (tileJukebox.getReplayMode()) {
            case OFF:
                ((GuiButton) btnRepeatAll).enabled = true;
                ((GuiButton) btnRepeatOne).enabled = true;
                ((GuiButton) btnRepeatOff).enabled = false;
                break;
            case ONE:
                ((GuiButton) btnRepeatAll).enabled = true;
                ((GuiButton) btnRepeatOne).enabled = false;
                ((GuiButton) btnRepeatOff).enabled = true;
                break;
            case ALL:
                ((GuiButton) btnRepeatAll).enabled = false;
                ((GuiButton) btnRepeatOne).enabled = true;
                ((GuiButton) btnRepeatOff).enabled = true;
        }
    }
}
