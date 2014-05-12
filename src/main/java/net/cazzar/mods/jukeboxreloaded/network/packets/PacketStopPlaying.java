/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.cazzar.mods.jukeboxreloaded.network.packets;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.network.packets.IPacket;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Cayde
 */
public class PacketStopPlaying implements IPacket {

    public int x;
    public int y;
    public int z;
    private String identifier;

    @SuppressWarnings("UnusedDeclaration")
    public PacketStopPlaying() {
    }

    public PacketStopPlaying(String identifier, int x, int y, int z) {
        this.identifier = identifier;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void handleClient(EntityPlayer player) {
        final TileEntity te = player.worldObj.getTileEntity(x, y, z);
        if (te instanceof TileJukebox) {
            ((TileJukebox) te).setPlaying(false);
            ((TileJukebox) te).waitTicks = 20;
            ((TileJukebox) te).markForUpdate();
        }

        SoundSystemHelper.stop(identifier);
    }

    @Override
    public void handleServer(EntityPlayer player) {
        handleClient(player);

        JukeboxReloaded.proxy().channel.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        JukeboxReloaded.proxy().channel.get(Side.SERVER).writeAndFlush(this);// just reuse me.
    }

    @Override
    public void read(ByteBuf in) {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
        identifier = ByteBufUtils.readUTF8String(in);
    }

    @Override
    public void write(ByteBuf out) {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
        ByteBufUtils.writeUTF8String(out, identifier);
    }

}
