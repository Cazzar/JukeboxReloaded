package net.cazzar.mods.jukeboxreloaded.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import static cpw.mods.fml.common.network.FMLOutboundHandler.FML_MESSAGETARGET;
import static cpw.mods.fml.common.network.FMLOutboundHandler.FML_MESSAGETARGETARGS;
import static cpw.mods.fml.common.network.FMLOutboundHandler.OutboundTarget.*;
import static net.cazzar.mods.jukeboxreloaded.JukeboxReloaded.proxy;

public abstract class PacketJukebox {
    public static class ProtocolException extends Exception {
        private static final long serialVersionUID = 4758559873161416283L;

        public ProtocolException() {
        }

        public ProtocolException(String message) {
            super(message);
        }

        public ProtocolException(String message, Throwable cause) {
            super(message, cause);
        }

        public ProtocolException(Throwable cause) {
            super(cause);
        }
    }

    public EntityPlayer player;

    public abstract void execute(EntityPlayer player, Side side)
            throws ProtocolException;

    public void read(ByteBuf in) {
        player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(readString(in));
    }

    public String readString(ByteBuf bytes) {
        return ByteBufUtils.readUTF8String(bytes);
    }

    public void writeString(ByteBuf bytes, String s) {
        ByteBufUtils.writeUTF8String(bytes, s);
    }

    public boolean sendToAllInDimension(int dimID) {
        proxy().getChannel().attr(FML_MESSAGETARGET).set(DIMENSION);
        proxy().getChannel().attr(FML_MESSAGETARGETARGS).set(dimID);
        return proxy().getChannel().writeOutbound(this);
    }

    public boolean sendToPlayer(EntityPlayer player) {
        proxy().getChannel().attr(FML_MESSAGETARGET).set(PLAYER);
        proxy().getChannel().attr(FML_MESSAGETARGETARGS).set(player);
        return proxy().getChannel().writeOutbound(this);
    }

    public boolean sendToAllPlayers() {
        proxy().getChannel().attr(FML_MESSAGETARGET).set(ALL);
        return proxy().getChannel().writeOutbound(this);
    }

    public boolean sendToServer() {
        proxy().getChannel().attr(FML_MESSAGETARGET).set(TOSERVER);
        return proxy().getChannel().writeOutbound(this);
    }

    public void write(ByteBuf out){
        writeString(out, player.func_146103_bH().getName());
    }
}
