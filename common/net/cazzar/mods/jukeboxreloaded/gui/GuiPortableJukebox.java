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

import net.cazzar.corelib.client.gui.TexturedButton;
import net.cazzar.mods.jukeboxreloaded.items.PortableJukeboxHelper;
import net.cazzar.mods.jukeboxreloaded.lib.Strings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.lwjgl.opengl.GL11;

import static net.cazzar.mods.jukeboxreloaded.lib.Reference.JUKEBOX_GUI_TEXTURE;
import static net.cazzar.mods.jukeboxreloaded.lib.Reference.JukeboxGUIActions.*;
import static net.cazzar.mods.jukeboxreloaded.lib.Reference.PORTABLE_JUKEBOX_GUI_TEXTURE;

public class GuiPortableJukebox extends GuiContainer {
    private TexturedButton btnPlay, btnStop, /*btnShuffleOn, btnShuffleOff, btnRepeatAll, btnRepeatOne, btnRepeatOff,*/
            btnNext, btnPrev;
    private GuiButton volUp, volDown;
    private PortableJukeboxHelper helper;

    public GuiPortableJukebox(EntityPlayer player) {
        this(new ContainerPortableJukebox(player.inventory), player);
    }

    public GuiPortableJukebox(Container container, EntityPlayer player) {
        super(container);

        helper = new PortableJukeboxHelper(player, player.getHeldItem());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        //updateButtonStates();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;
        mc.renderEngine.func_110577_a(PORTABLE_JUKEBOX_GUI_TEXTURE);
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case PLAY:
                helper.play();
                break;
            case STOP:
                helper.stop();
                break;
            case NEXT:
                helper.next();
                break;
            case PREVIOUS:
                helper.prev();
                break;
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        initButtons();
        initTooltips();
    }

    @SuppressWarnings("unchecked")
    public void initButtons() {
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

        /*buttonList.add(btnRepeatOne = new TexturedButton(this, REPEAT_ONE,
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
        */
        buttonList.add(volDown = new GuiButton(VOLUME_DOWN, xStart + 7, yStart + 61, 12, 20, "-"));
        buttonList.add(volUp = new GuiButton(VOLUME_UP, xStart + 37, yStart + 61, 12, 20, "+"));
    }

    private void initTooltips() {
        btnPlay.setTooltip(Strings.TOOLTIP_PLAY.toString());
        btnStop.setTooltip(Strings.TOOLTIP_STOP.toString());
        btnNext.setTooltip(Strings.TOOLTIP_NEXT.toString());
        btnPrev.setTooltip(Strings.TOOLTIP_PREV.toString());
        //btnRepeatAll.setTooltip(Strings.TOOLTIP_REPEAT_ALL.toString());
        //btnRepeatOff.setTooltip(Strings.TOOLTIP_REPEAT_NONE.toString());
        //btnRepeatOne.setTooltip(Strings.TOOLTIP_REPEAT_ONE.toString());
        //btnShuffleOn.setTooltip(Strings.TOOLTIP_SHUFFLE_ON.toString());
        //btnShuffleOff.setTooltip(Strings.TOOLTIP_SHUFFLE_OFF.toString());
    }
}
