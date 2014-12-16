package net.cazzar.mods.jukeboxreloaded.network.packet;


import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author Cayde
 */
public class ServerPlayRecord implements IMessage {
    int slot, x, y, z;

    public ServerPlayRecord() {
    }

    public ServerPlayRecord(TileJukebox tileJukebox) {
        this(tileJukebox.getCurrentRecordNumber(), tileJukebox.getPos());
    }

    public ServerPlayRecord(int slot, BlockPos pos) {
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

    public static class Handler implements IMessageHandler<ServerPlayRecord, IMessage> {
        @Override
        public IMessage onMessage(ServerPlayRecord message, MessageContext ctx) {
            TileJukebox jukebox = Util.getTileEntity(ClientUtil.mc().thePlayer.worldObj, message.x, message.y, message.z, TileJukebox.class);
            if (jukebox == null) {
                //TODO: log;
                return null;
            }

            if (jukebox.getStackInSlot(message.slot) == null) return null;

            jukebox.setPlaying(true);
            SoundSystemHelper.playRecord((ItemRecord) jukebox.getStackInSlot(message.slot).getItem(), message.x, message.y, message.z, jukebox.getIdentifier());
            return null;
        }
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
