/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

package net.cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import net.cazzar.corelib.items.ItemCustomRecord;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.client.CreativeTabJukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.configuration.ConfigHelper;
import net.cazzar.mods.jukeboxreloaded.events.EventHandler;
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.cazzar.mods.jukeboxreloaded.items.ItemPortableJukebox;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;
import java.util.Random;

public class CommonProxy {
    public BlockJukebox jukeBox;
    public ItemCustomRecord kokoro, loveIsWar, shibuya, spica, sukiDaiSuki, weArePopcandy;
    public ItemPortableJukebox portableJukebox;
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

    @SuppressWarnings("RedundantCast")
    public void initItems() {
        GameRegistry.registerItem(kokoro = new ItemCustomRecord(config.items.record1, "cazzar:kokoro.ogg", "ココロ", new String[]{"Sung by Kagamine Rin", "writer トラボルタ feat. 鏡音リン"}), "kokoro");
        GameRegistry.registerItem(loveIsWar = new ItemCustomRecord(config.items.record2, "cazzar:love_is_war.ogg", "Love is War", new String[]{"Sung by Hatsune Miku", "Writer - Supercell feat. 初音ミク"}), "love_is_war");
        GameRegistry.registerItem(shibuya = new ItemCustomRecord(config.items.record3, "cazzar:shibuya.ogg", "SHIBUYA (Original)", new String[]{"by BECCA"}), "shibuya");
        GameRegistry.registerItem(spica = new ItemCustomRecord(config.items.record4, "cazzar:spica.ogg", "SPiCa", new String[]{"by とく"}), "spica");
        GameRegistry.registerItem(sukiDaiSuki = new ItemCustomRecord(config.items.record5, "cazzar:suki_daisuki.ogg", "すすすす、すき、だあいすき", new String[]{"Sung by Kagamine Rin", "Writer - かたほとりP"}), "suki_daisuki");
        GameRegistry.registerItem(weArePopcandy = new ItemCustomRecord(config.items.record6, "cazzar:we_are_popcandy.ogg", "We are POPCANDY!", new String[]{"Sung by Hatsune Miku", "Writer RUNO"}), "we_are_popcandy");
        GameRegistry.registerItem(portableJukebox = new ItemPortableJukebox(config.items.portableJukeboxId), "Portable Jukebox");

        ((Item)kokoro).setCreativeTab(creativeTab);
        ((Item)loveIsWar).setCreativeTab(creativeTab);
        ((Item)shibuya).setCreativeTab(creativeTab);
        ((Item)spica).setCreativeTab(creativeTab);
        ((Item)sukiDaiSuki).setCreativeTab(creativeTab);
        ((Item)weArePopcandy).setCreativeTab(creativeTab);
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

    public void initVillagers() {
        VillagerRegistry.instance().registerVillagerId(3000);
        VillagerRegistry.instance().registerVillageTradeHandler(3000, new VillagerRegistry.IVillageTradeHandler() {
            @Override
            public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
                switch (random.nextInt(6) + 1){
                    case 1:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald), kokoro));
                        break;
                    case 2:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald), loveIsWar));
                        break;
                    case 3:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald), shibuya));
                        break;
                    case 4:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald), spica));
                        break;
                    case 5:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald), sukiDaiSuki));
                        break;
                    case 6:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Item.emerald), weArePopcandy));
                        break;
                }
            }
        });
    }
}