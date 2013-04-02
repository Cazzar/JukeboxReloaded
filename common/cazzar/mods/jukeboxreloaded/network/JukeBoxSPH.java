package cazzar.mods.jukeboxreloaded.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import codechicken.core.packet.PacketCustom;
import codechicken.core.packet.PacketCustom.ICustomPacketHandler.IServerPacketHandler;
import codechicken.core.vec.BlockCoord;

public class JukeBoxSPH implements IServerPacketHandler {

    @Override
    public void handlePacket(PacketCustom packet, NetServerHandler nethandler,
            EntityPlayerMP sender)
    {
        switch (packet.getType())
        {
            case 1:
                // handleUpdatePacket(sender.worldObj, packet.readCoord(),
                // packet.readInt());
                updateTileEntity(sender.worldObj, packet.readCoord(),
                        packet.readBoolean(), packet.readInt());
                break;
            case 3:
                final BlockCoord pos = packet.readCoord();
                final TileEntity tile = sender.worldObj.getBlockTileEntity(
                        pos.x, pos.y, pos.z);
                if (tile instanceof TileJukeBox)
                {
                    final PacketCustom packet2 = new PacketCustom(
                            Reference.CHANNEL_NAME, 4);
                    packet2.writeCoord(((TileJukeBox) tile).getCoord());
                    packet2.writeBoolean(((TileJukeBox) tile).isPlayingRecord());
                    packet2.writeInt(((TileJukeBox) tile)
                            .getCurrentRecordNumer());
                    packet2.sendToPlayer(sender);
                }
                break;
            default:
                break;
        }
    }

    private void updateTileEntity(World world, BlockCoord pos,
            boolean isPlaying, int currentRecord)
    {
        final TileEntity tile = world.getBlockTileEntity(pos.x, pos.y, pos.z);
        if (tile instanceof TileJukeBox)
        {
            final TileJukeBox jukeBox = (TileJukeBox) tile;

            System.out.println(isPlaying + ":" + currentRecord);

            jukeBox.setPlaying(isPlaying);
            jukeBox.setRecordPlaying(currentRecord);
        }
    }
}