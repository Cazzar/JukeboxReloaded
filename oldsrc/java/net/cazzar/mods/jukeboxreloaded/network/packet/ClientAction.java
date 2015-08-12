package net.cazzar.mods.jukeboxreloaded.network.packet;

import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.LogHelper;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.apache.logging.log4j.Logger;

/**
 * @author Cayde
 */
public class ClientAction implements IMessage {


    Action action;
    int x, y, z;

    public ClientAction(Action action, TileJukebox jukebox) {
        this(action, jukebox.getPos());
    }

    public ClientAction(Action action, BlockPos pos) {
        this.action = action;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
    }

    public ClientAction() {
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        action = Action.VALUES[buf.readInt()];
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


    public static class Handler implements IMessageHandler<ClientAction, IMessage> {
        public static final Logger log = LogHelper.getLogger(Handler.class);

        @Override
        public IMessage onMessage(ClientAction message, MessageContext ctx) {
            TileJukebox jukebox = Util.getTileEntity(ctx.getServerHandler().playerEntity.worldObj, message.x, message.y, message.z, TileJukebox.class);
            if (jukebox == null) {
                //TODO: log;
                return null;
            }
            final boolean wasPlaying = jukebox.isPlayingRecord();

            switch (message.action) {
                case STOP:
                    if (ClientUtil.isClient()) {
                        jukebox.setPlaying(false);
                        SoundSystemHelper.stop(jukebox.getIdentifier());
                    } else jukebox.stopPlayingRecord();
                    break;
                case NEXT:
                    if (wasPlaying)
                        jukebox.stopPlayingRecord();

                    jukebox.nextRecord();

                    if (wasPlaying)
                        jukebox.playSelectedRecord();
                    break;
                case PREVIOUS:
                    if (wasPlaying)
                        jukebox.stopPlayingRecord();

                    jukebox.previousRecord();

                    if (wasPlaying)
                        jukebox.playSelectedRecord();
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
        return "ClientAction{" +
                       "action=" + action +
                       ", x=" + x +
                       ", y=" + y +
                       ", z=" + z +
                       '}';
    }

    public static enum Action {
        STOP, NEXT, PREVIOUS, SHUFFLE_ON, SHUFFLE_OFF, REPEAT_ALL, REPEAT_OFF, REPEAT_ONE;
        public static final Action[] VALUES = values();
    }
}
