package cazzar.mods.jukeboxreloaded.lib;

public class Reference
{
    public static final String MOD_ID = "jukeboxReloaded";
    public static final String MOD_NAME = "JukeBox Reloaded";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String CHANNEL_NAME = "JUKEBOXRELOADED";
    public static final int SHIFTED_ID_RANGE_CORRECTION = 256;
    public static final String SERVER_PROXY_CLASS = "cazzar.mods.jukeboxreloaded.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "cazzar.mods.jukeboxreloaded.proxy.ClientProxy";
    public static final String GUIJUKEBOX_TEXTURE_FILE = "/mods/cazzar/textures/gui/jukebox.png";
    public static final Integer MOD_BUILD = 3;    

    public static class Packets
    {
        public static final int SERVER_UPDATE_TILEJUKEBOX = 1;
        public static final int CLIENT_UPDATE_TILEJUKEBOX = 2;
        public static final int CLIENT_TILEJUKEBOX_DATA   = 4;
        public static final int SERVER_NEXT_SHUFFLEDDISK  = 5;
    }
    public static class GUIJukeBoxActions
    {
    	public static final int PLAY = 0;
    	public static final int STOP = 1;
    	public static final int NEXT = 2;
    	public static final int PREVIOUS = 3;
    	public static final int REPEAT_OFF = 4;
    	public static final int REPEAT_ALL = 5;
    	public static final int REPEAT_ONE = 6;
    	public static final int SHUFFLE_OFF = 7;
    	public static final int SHUFFLE = 8;
    }
}
