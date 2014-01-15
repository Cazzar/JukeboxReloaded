package net.cazzar.mods.jukeboxreloaded.network.packets;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

@SuppressWarnings("UnusedDeclaration") //Due to us using it reflectively
public class PacketJukeboxDescription extends PacketJukebox {
    int x, y, z;
    int recordNumber;
    boolean playingRecord;
    int repeatMode;
    boolean shuffle;
    short facing;
    float volume;
    EntityPlayer sender = ClientUtil.mc().thePlayer;

    public PacketJukeboxDescription() {
    }

    public PacketJukeboxDescription(TileJukebox tile) {
        x = tile.field_145851_c;
        y = tile.field_145848_d;
        z = tile.field_145849_e;
        recordNumber = tile.getCurrentRecordNumber();
        playingRecord = tile.playing;
        repeatMode = tile.getReplayMode().ordinal();
        shuffle = tile.shuffleEnabled();
        facing = tile.getFacing();
        volume = tile.volume;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PacketJukeboxDescription setSender(EntityPlayer player) {
        this.sender = player;
        return this;
    }

    @Override
    public EntityPlayer getSender() {
        return ClientUtil.mc().thePlayer;
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException {
        final TileJukebox tile = (TileJukebox) player.worldObj.func_147438_o(x, y, z);

        tile.setPlaying(playingRecord);
        tile.setRecordPlaying(recordNumber);
        tile.setRepeatMode(RepeatMode.get(repeatMode));
        tile.setShuffle(shuffle);
        tile.setFacing(facing);
        tile.volume = volume;
    }

    @Override
    public void read(ByteBuf in) {
        sender = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(readString(in));
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        recordNumber = in.readInt();
        playingRecord = in.readBoolean();
        repeatMode = in.readInt();
        shuffle = in.readBoolean();
        facing = in.readShort();
        volume = in.readFloat();
    }

    @Override
    public void write(ByteBuf out) {
        writeString(out, sender.func_146103_bH().getName());
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeInt(recordNumber);
        out.writeBoolean(playingRecord);
        out.writeInt(repeatMode);
        out.writeBoolean(shuffle);
        out.writeShort(facing);
        out.writeFloat(volume);
    }
}
