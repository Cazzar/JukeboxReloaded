package net.cazzar.mods.jukeboxreloaded.network.packet;

import cpw.mods.fml.common.network.simpleimpl.*;
import io.netty.buffer.ByteBuf;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;

/**
 * @author Cayde
 */
public class ClientShuffle implements IMessage, IMessageHandler<ClientShuffle, IMessage> {
    public ClientShuffle() {
    } //Needed for Netty

    public ClientShuffle(TileJukebox tileJukebox) {
        this();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    @Override
    public IMessage onMessage(ClientShuffle message, MessageContext ctx) {
        return null;
    }
}
