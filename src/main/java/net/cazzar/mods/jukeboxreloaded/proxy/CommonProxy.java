/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.client.CreativeTabJukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.configuration.ConfigHelper;
import net.cazzar.mods.jukeboxreloaded.events.EventHandler;
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;
import java.util.Map;

public class CommonProxy {
    public BlockJukebox jukeBox;
    //    public ItemPortableJukebox portableJukebox;
    public CreativeTabJukeboxReloaded creativeTab;
    private ConfigHelper config;

    public void initBlocks() {
        creativeTab = new CreativeTabJukeboxReloaded();
        jukeBox = new BlockJukebox();
        GameRegistry.registerBlock(jukeBox, "blockJukebox");
    }

    public void initConfig(File suggested) {
        final Configuration config = new Configuration(suggested);
        this.config = new ConfigHelper(config);
        if (config.hasChanged())
            config.save();
    }

    @SuppressWarnings("RedundantCast")
    public void initItems() {
/*        GameRegistry.registerItem(kokoro = new ItemCustomRecord("kokoro", "ココロ", new String[]{"Sung by Kagamine Rin", "writer トラボルタ feat. 鏡音リン"}).setDomain("cazzar"), "kokoro");
        GameRegistry.registerItem(loveIsWar = new ItemCustomRecord("love_is_war", "Love is War", new String[]{"Sung by Hatsune Miku", "Writer - Supercell feat. 初音ミク"}).setDomain("cazzar"), "love_is_war");
        GameRegistry.registerItem(shibuya = new ItemCustomRecord("shibuya", "SHIBUYA (Original)", new String[]{"by BECCA"}).setDomain("cazzar"), "shibuya");
        GameRegistry.registerItem(spica = new ItemCustomRecord("spica", "SPiCa", new String[]{"by とく"}).setDomain("cazzar"), "spica");
        GameRegistry.registerItem(sukiDaiSuki = new ItemCustomRecord("suki_daisuki", "すすすす、すき、だあいすき", new String[]{"Sung by Kagamine Rin", "Writer - かたほとりP"}).setDomain("cazzar"), "suki_daisuki");
        GameRegistry.registerItem(weArePopcandy = new ItemCustomRecord("we_are_popcandy", "We are POPCANDY!", new String[]{"Sung by Hatsune Miku", "Writer RUNO"}).setDomain("cazzar"), "we_are_popcandy");
//        GameRegistry.registerItem(portableJukebox = new ItemPortableJukebox(config.items.portableJukeboxId), "Portable Jukebox");

        ((Item) kokoro).setCreativeTab(creativeTab);
        ((Item) loveIsWar).setCreativeTab(creativeTab);
        ((Item) shibuya).setCreativeTab(creativeTab);
        ((Item) spica).setCreativeTab(creativeTab);
        ((Item) sukiDaiSuki).setCreativeTab(creativeTab);
        ((Item) weArePopcandy).setCreativeTab(creativeTab);*/
    }

    public void initNetwork() {
        NetworkRegistry.INSTANCE.registerGuiHandler(JukeboxReloaded.instance(), new GuiHandler());
        //noinspection unchecked
//        channel = NetworkRegistry.INSTANCE.newChannel(Reference.MOD_ID, new PacketHandler());
        PacketHandler.init();
    }

    public void initOther() {
        EventHandler target = new EventHandler();
        MinecraftForge.EVENT_BUS.register(target);
        FMLCommonHandler.instance().bus().register(target);
    }

    public void initRecipe() {
        GameRegistry.addRecipe(new ShapedOreRecipe(jukeBox,
                "WCW",
                "NJN",
                "WWW",
                'W', "plankWood",
                'C', new ItemStack(Blocks.chest),
                'J', new ItemStack(Blocks.jukebox),
                'N', new ItemStack(Blocks.noteblock)));
    }

    public void initTileEntities() {
        GameRegistry.registerTileEntity(TileJukebox.class, "tileJukebox");
    }

    @SuppressWarnings("UnusedDeclaration")
    public ConfigHelper getConfig() {
        return config;
    }

    public void initVillagers() {
        VillagerRegistry.instance().registerVillagerId(getConfig().main.villagerID);
        VillagerRegistry.instance().registerVillageTradeHandler(getConfig().main.villagerID, (villager, recipeList, random) -> {
            Map<String, ItemRecord> value = ObfuscationReflectionHelper.getPrivateValue(ItemRecord.class, null, "field_150928_b"); //This here is alright due to to the
            if (random.nextInt(3) == 2) {
                ItemRecord record = (ItemRecord) value.values().toArray()[random.nextInt(value.size())];
                ItemRecord result = (ItemRecord) value.values().toArray()[random.nextInt(value.size())];

                while (result == record) {// ensure no buyback for the same item
                    result = (ItemRecord) value.values().toArray()[random.nextInt(value.size())];
                }

                recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(record), new ItemStack(Items.emerald, random.nextInt(3) + 1), new ItemStack(result)));
            } else {
                ItemRecord record = (ItemRecord) value.values().toArray()[random.nextInt(value.size())];
                recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald, 15 + random.nextInt(5)), record));
            }
        });
    }
}
