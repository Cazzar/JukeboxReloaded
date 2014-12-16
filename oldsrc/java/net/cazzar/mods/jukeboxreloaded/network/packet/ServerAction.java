package net.cazzar.mods.jukeboxreloaded.network.packet;

import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**
 * @author Cayde
 */
public class ServerAction implements IMessage {
    ClientAction.Action action;
    int x, y, z;

    public ServerAction(ClientAction.Action action, TileJukebox jukebox) {
        this(action, jukebox.getPos());
    }

    public ServerAction(ClientAction.Action action, BlockPos pos) {
        this.action = action;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }
    public ServerAction() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        action = ClientAction.Action.VALUES[buf.readInt()];
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(action.ordinal());
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    public static class Handler implements IMessageHandler<ServerAction, IMessage> {

        @Override
        public IMessage onMessage(ServerAction message, MessageContext ctx) {
            TileJukebox jukebox = Util.getTileEntity(ClientUtil.mc().thePlayer.worldObj, message.x, message.y, message.z, TileJukebox.class);
            if (jukebox == null) {
                //TODO: log;
                return null;
            }

            switch (message.action) {
                case STOP:
                    jukebox.setPlaying(false);
                    SoundSystemHelper.stop(jukebox.getIdentifier());
                    break;
                case NEXT:
                    jukebox.nextRecord();
                    break;
                case PREVIOUS:
                    jukebox.previousRecord();
                    break;
                case SHUFFLE_ON:
                    jukebox.setShuffle(true);
                    break;
                case SHUFFLE_OFF:
                    jukebox.setShuffle(false);
                    break;
                case REPEAT_ALL:
                    jukebox.setRepeatMode(RepeatMode.ALL);
                    break;
                case REPEAT_OFF:
                    jukebox.setRepeatMode(RepeatMode.OFF);
                    break;
                case REPEAT_ONE:
                    jukebox.setRepeatMode(RepeatMode.ONE);
                    break;
            }

            jukebox.markForUpdate();
            return null;
        }
    }

    @Override
    public String toString() {
        return "ServerAction{" +
                       "action=" + action +
                       ", x=" + x +
                       ", y=" + y +
                       ", z=" + z +
                       '}';
    }
}
