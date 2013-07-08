package cazzar.mods.jukeboxreloaded.lib;

import net.minecraft.client.resources.ResourceLocation;

public class Reference {
	public static class GUIJukeBoxActions {
		public static final int	PLAY		= 0;
		public static final int	STOP		= 1;
		public static final int	NEXT		= 2;
		public static final int	PREVIOUS	= 3;
		public static final int	REPEAT_OFF	= 4;
		public static final int	REPEAT_ALL	= 5;
		public static final int	REPEAT_ONE	= 6;
		public static final int	SHUFFLE_OFF	= 7;
		public static final int	SHUFFLE		= 8;
	}
	
	public static class Packets {
		public static final int	SERVER_UPDATE_TILEJUKEBOX	= 1;
		public static final int	CLIENT_UPDATE_TILEJUKEBOX	= 2;
		public static final int	TILEJUKEBOX_DATA			= 4;
		public static final int	SERVER_NEXT_SHUFFLEDDISK	= 5;
		public static final int	PLAY_RECORD					= 6;
		public static final int	STOP_RECORD					= 7;
		public static final int	STOP_ALL					= 8;
	}
	
	public static final String				MOD_ID						= "JukeboxReloaded";
	public static final String				MOD_NAME					= "JukeBox Reloaded";
	public static final String				MOD_VERSION					= "@VERSION@";
	public static final String				CHANNEL_NAME				= "JUKEBOXRELOADED";
	public static final int					SHIFTED_ID_RANGE_CORRECTION	= 256;
	public static final String				SERVER_PROXY_CLASS			= "cazzar.mods.jukeboxreloaded.proxy.CommonProxy";
	public static final String				CLIENT_PROXY_CLASS			= "cazzar.mods.jukeboxreloaded.proxy.ClientProxy";
	public static final ResourceLocation	GUIJUKEBOX_TEXTURE_FILE		= new ResourceLocation(
																				"cazzar",
																				"textures/gui/jukebox.png");
	// public static final String GUIJUKEBOX_TEXTURE_FILE =
	// "/mods/cazzar/textures/gui/jukebox.png";
	
	public static final String				MOD_BUILD					= "@BUILD_NUMBER@";
	public static final int					VERSION_CHECK_ATTEMPTS		= 3;
}
