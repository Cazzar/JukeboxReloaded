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

import io.netty.buffer.ByteBuf;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.cazzar.corelib.network.packets.IPacket;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;

public class PacketStopAllSounds implements IPacket {

    public PacketStopAllSounds() {
    }

    @Override
    public void handleClient(EntityPlayer player) {
        SoundSystemHelper.getSoundManager().stopAllSounds();
    }

    @Override
    public void handleServer(EntityPlayer player) {
        //quietly ignore it.
        JukeboxReloaded.logger.debug(PacketHandler.marker, "Recieved a packet I should not have!");
    }

    @Override
    public void read(ByteBuf in) {
    }

    @Override
    public void write(ByteBuf out) {
    }

}
