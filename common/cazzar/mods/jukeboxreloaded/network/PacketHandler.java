package cazzar.mods.jukeboxreloaded.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

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
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
            
            int x,y,z;
            Action action; 
            
            try {
                    x = inputStream.readInt();
                    y = inputStream.readInt();
                    z = inputStream.readInt();
                    action = Action.fromInt(inputStream.readInt());
            } catch (IOException e) {
                    e.printStackTrace();
                    return;
            }
            
            
        }
    }
}
