package cazzar.mods.jukeboxreloaded;

import net.minecraft.block.Block;
import cazzar.mods.jukeboxreloaded.blocks.BlockJukeBox;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import cazzar.mods.jukeboxreloaded.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = true)
public class JukeboxReloaded {
    @Instance(Reference.MOD_ID)
    private static JukeboxReloaded instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static JukeboxReloaded instance()
    {
        return instance;
    }

    public BlockJukeBox jukeBox;

    @Init
    public void Initialization(FMLInitializationEvent event)
    {
        proxy.Init();
        Block.blocksList[Block.jukebox.blockID] = null;
        jukeBox = new BlockJukeBox(Block.jukebox.blockID);
        GameRegistry.registerBlock(jukeBox, "blockJukeBox");
        GameRegistry.registerTileEntity(TileJukeBox.class, "tileJukeBox");
        LanguageRegistry.addName(jukeBox, "JukeBox");
    }

    public CommonProxy proxy()
    {
        return proxy;
    }
}
