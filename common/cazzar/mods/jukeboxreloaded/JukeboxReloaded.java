package cazzar.mods.jukeboxreloaded;

import net.minecraft.block.Block;
import cazzar.mods.jukeboxreloaded.blocks.BlockJukeBox;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import cazzar.mods.jukeboxreloaded.network.PacketHandler;
import cazzar.mods.jukeboxreloaded.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
@NetworkMod(channels = { Reference.CHANNEL_NAME }, packetHandler = PacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class JukeboxReloaded
{
    @Instance(Reference.MOD_ID)
    private static JukeboxReloaded instance;
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    private static CommonProxy proxy;

    public static JukeboxReloaded instance()
    {
        return instance;
    }

    BlockJukeBox jukeBox;

    @Init
    public void Initialization(FMLInitializationEvent event)
    {
        System.out.println("Init of the proxy");
        proxy.Init();
        Block.blocksList[Block.jukebox.blockID] = null;
        jukeBox = new BlockJukeBox(Block.jukebox.blockID);
        GameRegistry.registerBlock(jukeBox, "blockJukeBox");
        GameRegistry.registerTileEntity(TileJukeBox.class, "tileJukeBox");
    }
}
