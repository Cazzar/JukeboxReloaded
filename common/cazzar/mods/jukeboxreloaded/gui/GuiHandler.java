package cazzar.mods.jukeboxreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    public static final int JUKEBOX = 0;

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        switch (ID)
        {
            case JUKEBOX:
                if (!(tile instanceof TileJukeBox)) return null;
                return new GUIJukeBox(player, (TileJukeBox) tile);
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
            int x, int y, int z)
    {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        switch (ID)
        {
            case JUKEBOX:
                if (!(tile instanceof TileJukeBox)) return null;
                return new ContainerJukeBox(player.inventory,
                        (TileJukeBox) tile);
            default:
                return null;
        }
    }

}
