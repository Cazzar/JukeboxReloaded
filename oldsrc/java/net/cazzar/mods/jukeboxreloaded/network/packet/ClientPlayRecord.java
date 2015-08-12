package net.cazzar.mods.jukeboxreloaded.network.packet;

import io.netty.buffer.ByteBuf;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author Cayde
 */
public class ClientPlayRecord implements IMessage {
    int slot, x, y, z;

    public ClientPlayRecord() {
    }

    public ClientPlayRecord(TileJukebox tileJukebox) {
        this(tileJukebox.getCurrentRecordNumber(), tileJukebox.getPos());
    }

    public ClientPlayRecord(int slot, BlockPos pos) {
        this();
        this.slot = slot;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        slot = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(slot);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<ClientPlayRecord, IMessage> {
        @Override
        public IMessage onMessage(ClientPlayRecord message, MessageContext ctx) {
            TileJukebox jukebox = Util.getTileEntity(ctx.getServerHandler().playerEntity.worldObj, message.x, message.y, message.z, TileJukebox.class);
            if (jukebox == null) {
                //TODO: log;
                return null;
            }

            jukebox.setRecordPlaying(message.slot);
            jukebox.playSelectedRecord();

            return null;
        }
    }

    @Override
    public String toString() {
        return "ClientPlayRecord{" +
                       "slot=" + slot +
                       ", x=" + x +
                       ", y=" + y +
                       ", z=" + z +
                       '}';
    }
}
