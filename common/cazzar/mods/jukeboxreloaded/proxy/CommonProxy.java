package cazzar.mods.jukeboxreloaded.proxy;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.blocks.BlockJukeBox;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.configuration.Config;
import cazzar.mods.jukeboxreloaded.configuration.ConfigHelper;
import cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class CommonProxy {
	public BlockJukeBox		jukeBox;
	private ConfigHelper	config;
	
	public void initBlocks() {
		jukeBox = new BlockJukeBox(config.JukeboxID);
		GameRegistry.registerBlock(jukeBox, "blockJukeBox");
	}
	
	public void initConfig(File suggested) {
		final Configuration config = new Configuration(suggested);
		Config.Parse(this.config = new ConfigHelper(), config);
		if (config.hasChanged()) config.save();
	}
	
	public void initLanguage() {
		LanguageRegistry.addName(jukeBox, "JukeBox");
	}
	
	public void initNetwork() {
		NetworkRegistry.instance().registerGuiHandler(
				JukeboxReloaded.instance(), new GuiHandler());
	}
	
	public void initRecipe() {
		GameRegistry.addRecipe(new ItemStack(jukeBox), new Object[] { "WCW",
				"NJN", "WWW", 'W', new ItemStack(Block.planks), 'C',
				new ItemStack(Block.chest), 'J', new ItemStack(Block.jukebox),
				'N', new ItemStack(Block.music) });
	}
	
	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileJukeBox.class, "tileJukeBox");
	}
	
	public void SetCape(Entity ent, String capeURL) {}
}