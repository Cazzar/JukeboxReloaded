package net.cazzar.mods.jukeboxreloaded.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import org.lwjgl.opengl.GL11;

import static net.cazzar.mods.jukeboxreloaded.lib.Reference.PORTABLE_JUKEBOX_GUI_TEXTURE;

public class GuiPortableJukebox extends GuiContainer {
    public GuiPortableJukebox(EntityPlayer player) {
        this(new ContainerPortableJukebox(player.inventory));
    }

    public GuiPortableJukebox(Container container) {
        super(container);

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;
        mc.renderEngine.func_110577_a(PORTABLE_JUKEBOX_GUI_TEXTURE);
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }
}
