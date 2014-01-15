package net.cazzar.mods.jukeboxreloaded.network.packets;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class PacketShuffleDisk extends PacketJukebox {

    int x, y, z;
    EntityPlayer sender = ClientUtil.mc().thePlayer;

    public PacketShuffleDisk() {
    }

    public PacketShuffleDisk(TileJukebox tile) {
        x = tile.field_145851_c;
        y = tile.field_145848_d;
        z = tile.field_145849_e;
    }

    @Override
    public PacketShuffleDisk setSender(EntityPlayer player) {
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
        if (side.isServer()) {
            final TileEntity tile = player.worldObj.func_147438_o(x, y, z);
            if (tile instanceof TileJukebox) {
                final TileJukebox jukebox = (TileJukebox) tile;
                final Random random = new Random();
                if (jukebox.getLastSlotWithItem() <= 0) return;
                final int nextDisk = random.nextInt(jukebox
                        .getLastSlotWithItem());
                if (jukebox.getCurrentRecordNumber() != nextDisk)
                    jukebox.setRecordPlaying(nextDisk);
                ((TileJukebox) tile).markForUpdate();
            }
        }
    }

    @Override
    public void read(ByteBuf in) {
        sender = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(readString(in));
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    @Override
    public void write(ByteBuf out) {
        writeString(out, sender.func_146103_bH().getName());
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }
}
