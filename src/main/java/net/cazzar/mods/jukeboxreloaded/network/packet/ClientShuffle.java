package net.cazzar.mods.jukeboxreloaded.network.packet;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;

import java.util.Random;

/**
 * @author Cayde
 */
public class ClientShuffle implements IMessage {
    int x;
    int y;
    int z;

    public ClientShuffle(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ClientShuffle() {
    } //Needed for Netty

    public ClientShuffle(TileJukebox tile) {
        this(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class Handler implements IMessageHandler<ClientShuffle, IMessage> {
        @Override
        public IMessage onMessage(ClientShuffle message, MessageContext ctx) {
            TileJukebox jukebox = Util.getTileEntity(ctx.getServerHandler().playerEntity.worldObj, message.x, message.y, message.z, TileJukebox.class);
            if (jukebox == null) {
                //TODO: log;
                return null;
            }

            final Random random = jukebox.getWorldObj().rand;
            if (jukebox.getLastSlotWithItem() <= 0) return null;
            final int next = random.nextInt(jukebox.getLastSlotWithItem());

            if (jukebox.getCurrentRecordNumber() != next)
                jukebox.setRecordPlaying(next);

            jukebox.markForUpdate();

            return null;
        }
    }
}
