package net.cazzar.mods.jukeboxreloaded.lib;

import net.minecraft.util.ResourceLocation;

public class Reference {
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

    public static final String MOD_ID = "JukeboxReloaded";
    public static final String MOD_NAME = "JukeBox Reloaded";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String CHANNEL_NAME = "JUKEBOXRELOADED";
    public static final String SERVER_PROXY_CLASS = "net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy";
    public static final ResourceLocation JUKEBOX_GUI_TEXTURE = new ResourceLocation(
            "cazzar",
            "textures/gui/jukebox.png");

    public static final int VERSION_CHECK_ATTEMPTS = 3;
}
