package net.cazzar.mods.jukeboxreloaded.network.packet;

import io.netty.buffer.ByteBuf;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.Random;

/**
 * @author Cayde
 */
public class ClientShuffle implements IMessage {
    int x;
    int y;
    int z;

    public ClientShuffle(BlockPos pos) {
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public ClientShuffle() {
    } //Needed for Netty

    public ClientShuffle(TileJukebox tile) {
        this(tile.getPos());
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

            final Random random = jukebox.getWorld().rand;
            if (jukebox.getLastSlotWithItem() <= 0) return null;
            final int next = random.nextInt(jukebox.getLastSlotWithItem());

            if (jukebox.getCurrentRecordNumber() != next)
                jukebox.setRecordPlaying(next);

            jukebox.markForUpdate();

            return null;
        }
    }
}
