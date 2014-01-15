package net.cazzar.mods.jukeboxreloaded;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.cazzar.corelib.lib.LogHelper;
import net.cazzar.mods.jukeboxreloaded.lib.Reference;
import net.cazzar.mods.jukeboxreloaded.lib.util.VersionHelper;
import net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME)
//@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {Reference.CHANNEL_NAME}, packetHandler = PacketHandler.class)
public class JukeboxReloaded {
    public static Logger logger = LogHelper.getLogger(Reference.MOD_ID);

    @Instance(Reference.MOD_ID)
    private static JukeboxReloaded instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static JukeboxReloaded instance() {
        return instance;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = getVersionFromJar();
        //LogHelper.init();
        proxy.initNetwork();
        proxy.initConfig(event.getSuggestedConfigurationFile());

        VersionHelper.execute();

        proxy.initBlocks();
        proxy.initItems();
        proxy.initTileEntities();
        proxy.initRecipe();
        proxy.initRendering();

        proxy.initOther();
    }
    @EventHandler
    public void init(FMLInitializationEvent evt) {
         proxy.initVillagers();
    }

    public static CommonProxy proxy() {
        return proxy;
    }

    public String getVersionFromJar() {
        String s = getClass().getPackage().getImplementationVersion();
        return s == null || s.isEmpty() ? "UNKNOWN" : s;
    }
}
