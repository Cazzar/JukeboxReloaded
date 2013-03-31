package cazzar.mods.jukeboxreloaded.proxy;

import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.network.GuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy
{
    public void Init()
    {
        NetworkRegistry.instance().registerGuiHandler(
                JukeboxReloaded.instance(), new GuiHandler());
        System.out.println("registered GUI handler");
    }
}
