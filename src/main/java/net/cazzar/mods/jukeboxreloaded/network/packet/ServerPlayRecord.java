package net.cazzar.mods.jukeboxreloaded.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.item.ItemRecord;

/**
 * @author Cayde
 */
public class ServerPlayRecord implements IMessage, IMessageHandler<ServerPlayRecord, IMessage> {
    int slot, x, y, z;

    public ServerPlayRecord() {
    }

    public ServerPlayRecord(TileJukebox tileJukebox) {
        this(tileJukebox.getCurrentRecordNumber(), tileJukebox.xCoord, tileJukebox.yCoord, tileJukebox.zCoord);
    }

    public ServerPlayRecord(int slot, int x, int y, int z) {
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
    public IMessage onMessage(ServerPlayRecord message, MessageContext ctx) {
        TileJukebox jukebox = Util.getTileEntity(ctx.getServerHandler().playerEntity.worldObj, x, y, z, TileJukebox.class);
        if (jukebox == null) {
            //TODO: log;
            return null;
        }

        if (jukebox.getStackInSlot(slot) == null) return null;

        jukebox.setPlaying(true);
        SoundSystemHelper.playRecord((ItemRecord) jukebox.getStackInSlot(slot).getItem(), x, y, z, jukebox.getIdentifier());
        return null;
    }

    @Override
    public String toString() {
        return "ServerPlayRecord{" +
                       "slot=" + slot +
                       ", x=" + x +
                       ", y=" + y +
                       ", z=" + z +
                       '}';
    }
}
