package cazzar.mods.jukeboxreloaded.network;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIJukeBox extends GuiContainer
{
    TileJukeBox tileJukeBox;

    public GUIJukeBox(EntityPlayer player, TileJukeBox tileJukeBox)
    {
        super(new ContainerJukeBox(player.inventory, tileJukeBox));
        this.tileJukeBox = tileJukeBox;
        xSize = 175;
        ySize = 177;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2,
            int var3)
    {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/cazzar/textures/gui/jukebox.png");
        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y)
    {

        final String containerName = "JukeBox";
        fontRenderer.drawString(containerName,
                xSize / 2 - fontRenderer.getStringWidth(containerName) / 2, 6,
                4210752);
        fontRenderer.drawString(
                StatCollector.translateToLocal("container.inventory"), 8,
                ySize - 93, 4210752);
    }

}
