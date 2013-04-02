package cazzar.mods.jukeboxreloaded.gui;

import static java.lang.Math.floor;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import codechicken.core.packet.PacketCustom;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUIJukeBox extends GuiContainer {
    TileJukeBox tileJukeBox;
    private GuiButton btnPlay;
    private GuiButton btnStop;

    public GUIJukeBox(EntityPlayer player, TileJukeBox tileJukeBox)
    {
        super(new ContainerJukeBox(player.inventory, tileJukeBox));
        this.tileJukeBox = tileJukeBox;
        xSize = 175;
        ySize = 177;
    }

    @Override
    protected void actionPerformed(GuiButton btn)
    {
        final boolean wasPlaying = tileJukeBox.isPlayingRecord();
        // final PacketCustom packet = new PacketCustom(Reference.CHANNEL_NAME,
        // 1);
        // packet.writeCoord(tileJukeBox.getCoord());
        // packet.writeInt(btn.id);
        // packet.sendToServer();

        switch (btn.id)
        {
            case 0:
                // btnPlayplayRecord(
                // playRecord(((ItemRecord)tileJukeBox.getStackInSlot(recordNumber).getItem()).getRecordTitle(),
                // getRecordInfo(tileJukeBox.getStackInSlot(recordNumber)),tileJukeBox.worldObj,
                // tileJukeBox.xCoord, tileJukeBox.yCoord, tileJukeBox.zCoord);
                tileJukeBox.playSelectedRecord();
                // tileJukeBox.nextRecord();
                break;
            case 1:
                tileJukeBox.stopPlayingRecord();
                // tileJukeBox.resetPlayingRecord();
                break;
            case 2:
                if (wasPlaying)
                {
                    tileJukeBox.stopPlayingRecord();
                }
                tileJukeBox.nextRecord();
                if (wasPlaying)
                {
                    tileJukeBox.playSelectedRecord();
                }
                break;
            case 3:
                if (wasPlaying)
                {
                    tileJukeBox.stopPlayingRecord();
                }
                tileJukeBox.previousRecord();
                if (wasPlaying)
                {
                    tileJukeBox.playSelectedRecord();
                }
                break;
        }
        btnPlay.enabled = !(btnStop.enabled = tileJukeBox.isPlayingRecord());

        // send tile information to the server to update the other clients
        final PacketCustom packet = new PacketCustom(Reference.CHANNEL_NAME, 1);
        packet.writeCoord(tileJukeBox.getCoord());
        packet.writeBoolean(tileJukeBox.isPlayingRecord());
        packet.writeInt(tileJukeBox.getCurrentRecordNumer());
        packet.sendToServer();
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
        
        //TileEntity tile = world.getBlockTileEntity(x, y, z);
        //if (tile instanceof TileJukeBox) {
            boolean playing = SoundManager.sndSystem.playing(((TileJukeBox) tileJukeBox).getLastPlayedRecord());
        //}

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

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture("/mods/cazzar/textures/gui/jukebox.png");

        final int xOffset = 53;
        final int yOffset = 16;
        final int size = 18;

        final int index = tileJukeBox.getCurrentRecordNumer();
        final int column = (int) floor(index / 4D);
        final int row = index % 4;

        // System.out.println(column + ":" + row + ":" + index);
        // Args: x1, y1, x2, y2, color
        // xOffset + (size * row), yOffset + (size * column),
        drawTexturedModalRect(0 + xOffset + size * row, 0 + yOffset + size
                * column, 176, 0, 18, 18);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui()
    {
        super.initGui();
        // id, x, y, W, H
        // this.buttonList.add(this.button = new GuiCCButton(0, 28, 17, 40, 10,
        // "Play"));
        final int xStart = (width - xSize) / 2;
        final int yStart = (height - ySize) / 2;

        buttonList.add(btnPlay = new GuiButton(0, xStart + 10, yStart + 17, 40,
                20, "Play"));
        buttonList.add(btnStop = new GuiButton(1, xStart + 10, yStart + 40, 40,
                20, "Stop"));

        buttonList.add(new GuiButton(2, xStart + 128, yStart + 17, 40, 20,
                "Next"));
        buttonList.add(new GuiButton(3, xStart + 128, yStart + 40, 40, 20,
                "Prev"));
        btnPlay.enabled = !(btnStop.enabled = tileJukeBox.isPlayingRecord());
    }
}
