package cazzar.mods.jukeboxreloaded;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cazzar.mods.jukeboxreloaded.blocks.BlockJukeBox;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import cazzar.mods.jukeboxreloaded.player.PlayerTracker;
import cazzar.mods.jukeboxreloaded.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
	private int JukeboxID;

    @PreInit
    public void Initialization(FMLPreInitializationEvent event)
    {
        proxy.Init();
        //Block.blocksList[Block.jukebox.blockID] = null;
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        JukeboxID = config.get("default", "jukeboxID", 3000, "The ID number for the Jukebox").getInt();
        jukeBox = new BlockJukeBox(JukeboxID);
        GameRegistry.registerBlock(jukeBox, "blockJukeBox");
        GameRegistry.registerTileEntity(TileJukeBox.class, "tileJukeBox");
        LanguageRegistry.addName(jukeBox, "JukeBox");
        
       
        GameRegistry.addRecipe(new ItemStack(jukeBox), new Object[] {"WCW", "NJN", "WWW", 
        	'W', new ItemStack(Block.planks),
        	'C', new ItemStack(Block.chest), 
        	'J', new ItemStack(Block.jukebox), 
        	'N', new ItemStack(Block.music)});
        
        GameRegistry.registerPlayerTracker(new PlayerTracker());
        //update checker
        
        
    }

    public CommonProxy proxy()
    {
        return proxy;
    }
    
    public static String getUpdateDetailIfExists()
    {
    	URL url;
		try {
			url = new URL("https://bitbucket.org/cazzar/mod-updates/raw/master/JukeboxReloaded.cfg");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

        	String s, description = null, release = null;
        	
        	while ((s = reader.readLine()) != null)
        	{
        		if (s.startsWith("release: "))
        			release = s.replace("release: ", "");
        		else if (s.startsWith("description: "))
        			description = s.replace("description: ", "");
        	}
        	
        	int build = Integer.parseInt(release);
        	
        	if (Reference.MOD_BUILD != build)
        		if (release != null && description != null)
        			return description;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
