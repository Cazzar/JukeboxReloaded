package cazzar.mods.jukeboxreloaded.proxy;

import net.minecraft.entity.Entity;
import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void Init()
    {
        NetworkRegistry.instance().registerGuiHandler(
                JukeboxReloaded.instance(), new GuiHandler());
    }
    
    public void SetCape(Entity ent, String capeURL) {}
}