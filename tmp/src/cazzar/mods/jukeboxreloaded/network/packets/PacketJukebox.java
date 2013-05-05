package cazzar.mods.jukeboxreloaded.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

import cazzar.mods.jukeboxreloaded.lib.Reference;
import static cazzar.mods.jukeboxreloaded.lib.Reference.Packets.*;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public abstract class PacketJukebox {
    private static final BiMap<Integer, Class<? extends PacketJukebox>> idMap;

    static
    {
        ImmutableBiMap.Builder<Integer, Class<? extends PacketJukebox>> builder = ImmutableBiMap.builder();

        builder.put(TILEJUKEBOX_DATA, PacketJukeboxDescription.class);
        builder.put(CLIENT_UPDATE_TILEJUKEBOX, PacketUpdateClientTile.class);
        builder.put(SERVER_NEXT_SHUFFLEDDISK, PacketShuffleDisk.class);

        idMap = builder.build();
    }

    public static PacketJukebox constructPacket(int packetId) throws ProtocolException, InstantiationException, IllegalAccessException
    {
        Class<? extends PacketJukebox> clazz = idMap.get(Integer.valueOf(packetId));
        if (clazz == null)
        {
            throw new ProtocolException("Unknown Packet Id!");
        }
        else
        {
            return clazz.newInstance();
        }
    }

    public static class ProtocolException extends Exception {

        public ProtocolException()
        {
        }

        public ProtocolException(String message, Throwable cause)
        {
            super(message, cause);
        }

        public ProtocolException(String message)
        {
            super(message);
        }

        public ProtocolException(Throwable cause)
        {
            super(cause);
        }
    }

    public final int getPacketId()
    {
        if (idMap.inverse().containsKey(getClass()))
        {
            return idMap.inverse().get(getClass()).intValue();
        }
        else
        {
            throw new RuntimeException("Packet " + getClass().getSimpleName() + " is missing a mapping!");
        }
    }

    public final Packet makePacket()
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeByte(getPacketId());
        write(out);
        return PacketDispatcher.getPacket(Reference.CHANNEL_NAME, out.toByteArray());
    }

    public abstract void write(ByteArrayDataOutput out);

    public abstract void read(ByteArrayDataInput in);

    public abstract void execute(EntityPlayer player, Side side) throws ProtocolException;
}
