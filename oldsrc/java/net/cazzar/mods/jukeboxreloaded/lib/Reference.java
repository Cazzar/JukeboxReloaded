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

package net.cazzar.mods.jukeboxreloaded.lib;

import net.minecraft.util.ResourceLocation;

public class Reference {
    public static final String MOD_ID = "JukeboxReloaded";
    public static final String MOD_NAME = "Jukebox Reloaded";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String CHANNEL_NAME = "JUKEBOXRELOADED";
    public static final String SERVER_PROXY_CLASS = "net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy";
    public static final ResourceLocation JUKEBOX_GUI_TEXTURE = new ResourceLocation(
            MOD_ID.toLowerCase(),
            "textures/gui/jukebox.png");
    public static final ResourceLocation PORTABLE_JUKEBOX_GUI_TEXTURE = new ResourceLocation(MOD_ID.toLowerCase(), "textures/gui/portableJukebox.png");
    public static final int VERSION_CHECK_ATTEMPTS = 3;

    public static class JukeboxGUIActions {
        public static final int PLAY = 0;
        public static final int STOP = 1;
        public static final int NEXT = 2;
        public static final int PREVIOUS = 3;
        public static final int REPEAT_OFF = 4;
        public static final int REPEAT_ALL = 5;
        public static final int REPEAT_ONE = 6;
        public static final int SHUFFLE_OFF = 7;
        public static final int SHUFFLE = 8;
        public static final int VOLUME_UP = 9;
        public static final int VOLUME_DOWN = 10;
    }

    public static class PacketsIDs {
        public static final int CLIENT_UPDATE_TILE_JUKEBOX = 1;
        public static final int JUKEBOX_DATA = 2;
        public static final int SERVER_SHUFFLE_DISK = 3;
        public static final int PLAY_RECORD = 4;
        public static final int STOP_RECORD = 5;
        public static final int STOP_ALL = 6;
    }
}
