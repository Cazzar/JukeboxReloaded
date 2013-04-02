package cazzar.mods.jukeboxreloaded.proxy;

import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import cazzar.mods.jukeboxreloaded.network.JukeBoxSPH;
import codechicken.core.packet.PacketCustom;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void Init()
    {
        NetworkRegistry.instance().registerGuiHandler(
                JukeboxReloaded.instance(), new GuiHandler());
        PacketCustom.assignHandler(Reference.CHANNEL_NAME, 0, 255,
                new JukeBoxSPH());
    }
}