package net.cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.cazzar.corelib.items.ItemCustomRecord;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.client.CreativeTabJukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.configuration.ConfigHelper;
import net.cazzar.mods.jukeboxreloaded.events.EventHandler;
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;

public class CommonProxy {
    public BlockJukebox jukeBox;
    public ItemCustomRecord kokoro, loveIsWar, shibuya, spica, sukiDaiSuki, weArePopcandy;
    public CreativeTabJukeboxReloaded creativeTab;
    private ConfigHelper config;

    public void initBlocks() {
        creativeTab = new CreativeTabJukeboxReloaded();
        jukeBox = new BlockJukebox(config.blocks.Jukebox);
        GameRegistry.registerBlock(jukeBox, "blockJukebox");
    }

    public void initConfig(File suggested) {
        final Configuration config = new Configuration(suggested);
        this.config = new ConfigHelper(config);
        if (config.hasChanged())
            config.save();
    }

    public void initItems() {
        GameRegistry.registerItem(kokoro = new ItemCustomRecord(config.items.record1, "cazzar:kokoro.ogg", "ココロ", new  String[]{"Sung by Kagamine Rin", "writer トラボルタ feat. 鏡音リン"}), "kokoro");
        GameRegistry.registerItem(loveIsWar = new ItemCustomRecord(config.items.record2, "cazzar:love_is_war.ogg", "Love is War", new String[]{"Sung by Hatsune Miku", "Writer - Supercell feat. 初音ミク"}), "love_is_war");
        GameRegistry.registerItem(shibuya = new ItemCustomRecord(config.items.record3, "cazzar:shibuya.ogg", "SHIBUYA (Original)", new String[]{"by BECCA"}), "shibuya");
        GameRegistry.registerItem(spica = new ItemCustomRecord(config.items.record4, "cazzar:spica.ogg", "SPiCa", new String[]{"by とく"}), "spica");
        GameRegistry.registerItem(sukiDaiSuki = new ItemCustomRecord(config.items.record5, "cazzar:suki_daisuki.ogg", "すすすす、すき、だあいすき", new String[]{"Sung by Kagamine Rin", "Writer - かたほとりP"}), "suki_daisuki");
        GameRegistry.registerItem(weArePopcandy = new ItemCustomRecord(config.items.record6, "cazzar:we_are_popcandy.ogg", "We are POPCANDY!", new String[]{"Sung by Hatsune Miku", "Writer RUNO"}), "we_are_popcandy");

        kokoro.setCreativeTab(creativeTab);
        loveIsWar.setCreativeTab(creativeTab);
        shibuya.setCreativeTab(creativeTab);
        spica.setCreativeTab(creativeTab);
        sukiDaiSuki.setCreativeTab(creativeTab);
        weArePopcandy.setCreativeTab(creativeTab);

        int weight = 5;
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(kokoro), 1, 1, weight));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(loveIsWar), 1, 1, weight));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(shibuya), 1, 1, weight));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(spica), 1, 1, weight));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(sukiDaiSuki), 1, 1, weight));
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(weArePopcandy), 1, 1, weight));
    }

    public void initNetwork() {
        NetworkRegistry.instance().registerGuiHandler(JukeboxReloaded.instance(), new GuiHandler());
    }

    public void initOther() {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void initRecipe() {
        GameRegistry.addRecipe(new ShapedOreRecipe(jukeBox,
                "WCW",
                "NJN",
                "WWW",
                'W', "plankWood",
                'C', new ItemStack(Block.chest),
                'J', new ItemStack(Block.jukebox),
                'N', new ItemStack(Block.music)));
    }

    public void initTileEntities() {
        GameRegistry.registerTileEntity(TileJukebox.class, "tileJukebox");
    }

    public ConfigHelper getConfig() {
        return config;
    }
}