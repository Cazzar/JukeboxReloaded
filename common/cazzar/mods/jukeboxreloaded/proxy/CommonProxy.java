package cazzar.mods.jukeboxreloaded.proxy;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.blocks.BlockJukeBox;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.configuration.ConfigHelper;
import cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import cazzar.mods.jukeboxreloaded.item.ItemCustomRecord;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy {
	public BlockJukeBox		jukeBox;
	public ItemCustomRecord	kokoro, loveIsWar, shibuya, spica, sukiDaiSuki,
			weArePopcandy;
	private ConfigHelper	config;
	
	public void initBlocks() {
		jukeBox = new BlockJukeBox(config.blocks.Jukebox);
		GameRegistry.registerBlock(jukeBox, "blockJukeBox");
	}
	
	public void initConfig(File suggested) {
		final Configuration config = new Configuration(suggested);
		this.config = new ConfigHelper(config);
		if (config.hasChanged()) config.save();
	}
	
	public void initItems() {
		// String folder = "resources/mod/streaming/JukeboxReloaded/";
		GameRegistry.registerItem(kokoro = new ItemCustomRecord(
				config.items.record1, "kokoro", "ココロ",
				"Sung by Kagamine Rin", "writer トラボルタ feat. 鏡音リン"), "kokoro");
		GameRegistry
				.registerItem(loveIsWar = new ItemCustomRecord(
						config.items.record2, "love_is_war", "Love is War",
						"Sung by Hatsune Miku",
						"Writer - Supercell feat. 初音ミク"), "love_is_war");
		GameRegistry.registerItem(shibuya = new ItemCustomRecord(
				config.items.record3, "shibuya", "SHIBUYA (Original)",
				"by BECCA"), "shibuya");
		GameRegistry.registerItem(spica = new ItemCustomRecord(
				config.items.record4, "spica", "SPiCa", "by とく"), "spica");
		GameRegistry.registerItem(sukiDaiSuki = new ItemCustomRecord(
				config.items.record5, "suki_daisuki", "すすすす、すき、だあいすき",
				"Sung by Kagamine Rin", "Writer - かたほとりP"),
				"suki_daisuki");
		GameRegistry.registerItem(weArePopcandy = new ItemCustomRecord(
				config.items.record6, "we_are_popcandy", "We are POPCANDY!",
				"Sung by Hatsune Miku", "Writer RUNO"), "we_are_popcandy");
		
		ChestGenHooks
				.addItem(ChestGenHooks.DUNGEON_CHEST,
						new WeightedRandomChestContent(new ItemStack(kokoro),
								1, 1, 10));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
				new WeightedRandomChestContent(new ItemStack(loveIsWar), 1, 1,
						10));
		ChestGenHooks
				.addItem(ChestGenHooks.DUNGEON_CHEST,
						new WeightedRandomChestContent(new ItemStack(shibuya),
								1, 1, 10));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
				new WeightedRandomChestContent(new ItemStack(spica), 1, 1, 10));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
				new WeightedRandomChestContent(new ItemStack(sukiDaiSuki), 1,
						1, 10));
		ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST,
				new WeightedRandomChestContent(new ItemStack(weArePopcandy), 1,
						1, 10));
	}
	
	public void initLanguage() {
		LanguageRegistry.addName(jukeBox, "JukeBox");
		LanguageRegistry.addName(kokoro, "Record");
		LanguageRegistry.addName(loveIsWar, "Record");
		LanguageRegistry.addName(shibuya, "Record");
		LanguageRegistry.addName(sukiDaiSuki, "Record");
		LanguageRegistry.addName(weArePopcandy, "Record");
	}
	
	public void initNetwork() {
		NetworkRegistry.instance().registerGuiHandler(
				JukeboxReloaded.instance(), new GuiHandler());
	}
	
	public void initOther() {}
	
	public void initRecipe() {
		GameRegistry.addRecipe(new ItemStack(jukeBox), new Object[] { "WCW",
				"NJN", "WWW", 'W', new ItemStack(Block.planks), 'C',
				new ItemStack(Block.chest), 'J', new ItemStack(Block.jukebox),
				'N', new ItemStack(Block.music) });
	}
	
	public void initTileEntities() {
		GameRegistry.registerTileEntity(TileJukeBox.class, "tileJukeBox");
	}
	
	public Side getEffectiveSide() {
		return Side.SERVER;
	}
	
	public void SetCape(Entity ent, String capeURL) {}
}