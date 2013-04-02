package cazzar.mods.jukeboxreloaded.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import codechicken.core.packet.PacketCustom;
import codechicken.core.packet.PacketCustom.ICustomPacketHandler.IClientPacketHandler;
import codechicken.core.vec.BlockCoord;

public class JukeBoxCPH implements IClientPacketHandler {
    public static final String channel = Reference.CHANNEL_NAME;

    @Override
    public void handlePacket(PacketCustom packet, NetClientHandler nethandler,
            Minecraft mc)
    {
        switch (packet.getType())
        {
            case 2:
                handleUpdatePacket(mc.theWorld, packet.readCoord(),
                        packet.readInt(), packet.readInt());
                break;

            case 4:
                handleTileEntityData(mc.theWorld, packet.readCoord(),
                        packet.readBoolean(), packet.readInt());
                break;
            default:
                break;
        }
    }

    private void handleTileEntityData(WorldClient world, BlockCoord pos,
            boolean isPlaying, int currentRecord)
    {
        final TileEntity tile = world.getBlockTileEntity(pos.x, pos.y, pos.z);
        if (tile instanceof TileJukeBox)
        {
            ((TileJukeBox) tile).setPlaying(isPlaying);
            ((TileJukeBox) tile).setRecordPlaying(currentRecord);
        }
    }

    private void handleUpdatePacket(World world, BlockCoord pos, int action,
            int currentRecord)
    {
        final TileEntity tile = world.getBlockTileEntity(pos.x, pos.y, pos.z);
        if (tile instanceof TileJukeBox)
        {
            switch (action)
            {
                case 0:
                    ((TileJukeBox) tile).setPlaying(true);
                    break;

                case 1:
                    ((TileJukeBox) tile).setPlaying(false);
                    break;

                case 2:
                case 3:
                    ((TileJukeBox) tile).setRecordPlaying(currentRecord);
                    break;
            }
        }
    }
}
