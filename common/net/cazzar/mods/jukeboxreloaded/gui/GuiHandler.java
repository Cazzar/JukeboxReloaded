package net.cazzar.mods.jukeboxreloaded.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    public static final int JUKEBOX = 0;

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        switch (ID) {
            case JUKEBOX:
                if (!(tile instanceof TileJukebox)) return null;
                return new GUIJukebox(player, (TileJukebox) tile);
            default:
                return null;
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        switch (ID) {
            case JUKEBOX:
                if (!(tile instanceof TileJukebox)) return null;
                return new ContainerJukebox(player.inventory,
                        (TileJukebox) tile);
            default:
                return null;
        }
    }

}
