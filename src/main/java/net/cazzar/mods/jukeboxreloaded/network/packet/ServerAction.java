package net.cazzar.mods.jukeboxreloaded.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.util.ClientUtil;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.lib.RepeatMode;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;

/**
 * @author Cayde
 */
public class ServerAction implements IMessage, IMessageHandler<ServerAction, IMessage> {
    ClientAction.Action action;
    int x, y, z;

    public ServerAction(ClientAction.Action action, TileJukebox jukebox) {
        this(action, jukebox.xCoord, jukebox.yCoord, jukebox.zCoord);
    }

    public ServerAction(ClientAction.Action action, int x, int y, int z) {
        this.action = action;
        this.x = x;
        this.y = y;
        this.z = z;
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

    @Override
    public IMessage onMessage(ServerAction message, MessageContext ctx) {
        TileJukebox jukebox = Util.getTileEntity(ClientUtil.mc().thePlayer.worldObj, x, y, z, TileJukebox.class);
        if (jukebox == null) {
            //TODO: log;
            return null;
        }

        switch (action) {
            case STOP:
                jukebox.stopPlayingRecord();
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
        return null;
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
