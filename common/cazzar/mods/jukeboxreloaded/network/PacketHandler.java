package cazzar.mods.jukeboxreloaded.network;

import cazzar.mods.jukeboxreloaded.lib.Reference;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

    @Override
    public void onPacketData(INetworkManager manager,
            Packet250CustomPayload packet, Player player)
    {
        if (packet.channel.equals(Reference.CHANNEL_NAME)) {
            
        }
    }
}
