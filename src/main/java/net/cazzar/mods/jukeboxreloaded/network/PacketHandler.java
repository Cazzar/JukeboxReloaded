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

package net.cazzar.mods.jukeboxreloaded.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.cazzar.mods.jukeboxreloaded.lib.Reference;
import net.cazzar.mods.jukeboxreloaded.network.packet.*;

import static cpw.mods.fml.relauncher.Side.CLIENT;
import static cpw.mods.fml.relauncher.Side.SERVER;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(ClientAction.Handler.class, ClientAction.class, 0, SERVER);
        INSTANCE.registerMessage(ServerAction.Handler.class, ServerAction.class, 1, CLIENT);
        INSTANCE.registerMessage(ClientPlayRecord.Handler.class, ClientPlayRecord.class, 2, SERVER);
        INSTANCE.registerMessage(ServerPlayRecord.Handler.class, ServerPlayRecord.class, 3, CLIENT);
        INSTANCE.registerMessage(ClientShuffle.Handler.class, ClientShuffle.class, 4, SERVER);
    }

    private PacketHandler() {

    }
}
