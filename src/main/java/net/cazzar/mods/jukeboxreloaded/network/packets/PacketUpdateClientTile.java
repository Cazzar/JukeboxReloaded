package net.cazzar.mods.jukeboxreloaded.network.packets;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import static net.cazzar.mods.jukeboxreloaded.lib.Reference.JukeboxGUIActions.*;

public class PacketUpdateClientTile extends PacketJukebox {

    // packet.writeCoord(this.getCoord());
    // packet.writeBoolean(this.isPlayingRecord());
    // packet.writeInt(this.getCurrentRecordNumber());

    int x, y, z;
    int action;
    int currentRecord;
    EntityPlayer sender = ClientUtil.mc().thePlayer;

    public PacketUpdateClientTile() {
    }

    public PacketUpdateClientTile(TileJukebox tile, int action) {
        x = tile.field_145851_c;
        y = tile.field_145848_d;
        z = tile.field_145849_e;
        this.action = action;
        currentRecord = tile.getCurrentRecordNumber();
    }

    @Override
    public PacketUpdateClientTile setSender(EntityPlayer player) {
        sender = player;
        return this;
    }

    @Override
    public EntityPlayer getSender() {
        return sender;
    }

    @Override
    public void execute(EntityPlayer player, Side side)
            throws ProtocolException {
        if (side.isClient()) {
            final TileJukebox tile = (TileJukebox) player.worldObj.func_147438_o(x, y, z);

            switch (action) {
                case PLAY:
                    tile.setPlaying(true);
                    break;

                case STOP:
                    tile.setPlaying(false);
                    break;

                case NEXT:
                case PREVIOUS:
                    tile.setRecordPlaying(currentRecord);
                    break;
            }
        } else throw new ProtocolException("Cannot send this packet to the server!");
    }

    @Override
    public void read(ByteBuf in) {
        sender = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(readString(in));
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        action = in.readInt();
        currentRecord = in.readInt();
    }

    @Override
    public void write(ByteBuf out) {
        writeString(out, sender.func_146103_bH().getName());
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        out.writeInt(action);
        out.writeInt(currentRecord);
    }

}
