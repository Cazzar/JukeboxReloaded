package cazzar.mods.jukeboxreloaded;

import cazzar.mods.jukeboxreloaded.lib.Reference;
import cazzar.mods.jukeboxreloaded.lib.util.LogHelper;
import cazzar.mods.jukeboxreloaded.lib.util.VersionHelper;
import cazzar.mods.jukeboxreloaded.network.PacketHandler;
import cazzar.mods.jukeboxreloaded.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME,
		version = Reference.MOD_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true,
		channels = { Reference.CHANNEL_NAME },
		packetHandler = PacketHandler.class)
public class JukeboxReloaded {
	
	@Instance(Reference.MOD_ID)
	private static JukeboxReloaded	instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS,
			serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy		proxy;
	
	public static JukeboxReloaded instance() {
		return instance;
	}
	
	@PreInit
	public void Initialization(FMLPreInitializationEvent event) {
		LogHelper.init();
		proxy.initNetwork();
		proxy.initConfig(event.getSuggestedConfigurationFile());
		
		VersionHelper.execute();
		
		proxy.initBlocks();
		proxy.initItems();
		proxy.initTileEntities();
		proxy.initLanguage();
		proxy.initRecipe();
		
		proxy.initOther();
	}
	
	public CommonProxy proxy() {
		return proxy;
	}
}
