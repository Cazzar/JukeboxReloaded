package net.cazzar.mods.jukeboxreloaded.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    public static final int JUKEBOX = 0;
    public static final int PORTABLE_JUKEBOX = 1;

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
        final TileEntity tile = world.getBlockTileEntity(x, y, z);

        switch (ID) {
            case JUKEBOX:
                if (!(tile instanceof TileJukebox)) return null;
                return new GUIJukebox(player, (TileJukebox) tile);
            case PORTABLE_JUKEBOX:
                return new GuiPortableJukebox(player);
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
                return new ContainerJukebox(player.inventory, (TileJukebox) tile);
            case PORTABLE_JUKEBOX:
                return new ContainerPortableJukebox(player.inventory);
            default:
                return null;
        }
    }

}
