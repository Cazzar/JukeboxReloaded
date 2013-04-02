package cazzar.mods.jukeboxreloaded.proxy;

import cazzar.mods.jukeboxreloaded.lib.Reference;
import cazzar.mods.jukeboxreloaded.network.JukeBoxCPH;
import codechicken.core.packet.PacketCustom;

public class ClientProxy extends CommonProxy {
    @Override
    public void Init()
    {
        super.Init();
        PacketCustom.assignHandler(Reference.CHANNEL_NAME, 0, 255,
                new JukeBoxCPH());
    }
}
