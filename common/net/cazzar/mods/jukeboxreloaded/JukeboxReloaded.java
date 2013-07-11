package net.cazzar.mods.jukeboxreloaded;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.cazzar.corelib.lib.LogHelper;
import net.cazzar.mods.jukeboxreloaded.lib.Reference;
import net.cazzar.mods.jukeboxreloaded.lib.util.VersionHelper;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {Reference.CHANNEL_NAME}, packetHandler = PacketHandler.class)
public class JukeboxReloaded {
    public static LogHelper logger = new LogHelper(Reference.MOD_ID);

    @Instance(Reference.MOD_ID)
    private static JukeboxReloaded instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static JukeboxReloaded instance() {
        return instance;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        //LogHelper.init();
        proxy.initNetwork();
        proxy.initConfig(event.getSuggestedConfigurationFile());

        VersionHelper.execute();

        proxy.initBlocks();
        proxy.initItems();
        proxy.initTileEntities();
        proxy.initRecipe();

        proxy.initOther();
    }

    public static CommonProxy proxy() {
        return proxy;
    }
}
