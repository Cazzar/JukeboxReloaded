package net.cazzar.mods.jukeboxreloaded.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;

/**
 * @author Cayde
 */
public class ClientPlayRecord implements IMessage, IMessageHandler<ClientPlayRecord, IMessage> {
    int slot, x, y, z;

    public ClientPlayRecord() {
    }

    public ClientPlayRecord(TileJukebox tileJukebox) {
        this(tileJukebox.getCurrentRecordNumber(), tileJukebox.xCoord, tileJukebox.yCoord, tileJukebox.zCoord);
    }

    public ClientPlayRecord(int slot, int x, int y, int z) {
        this();
        this.slot = slot;
        this.x = x;
        this.y = y;
        this.z = z;
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

    @Override
    public IMessage onMessage(ClientPlayRecord message, MessageContext ctx) {
        TileJukebox jukebox = Util.getTileEntity(ctx.getServerHandler().playerEntity.worldObj, x, y, z, TileJukebox.class);
        if (jukebox == null) {
            //TODO: log;
            return null;
        }

        jukebox.setRecordPlaying(slot);
        jukebox.playSelectedRecord();

        return null;
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
