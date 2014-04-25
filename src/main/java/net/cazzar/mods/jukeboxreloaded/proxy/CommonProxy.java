/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import net.cazzar.corelib.items.ItemCustomRecord;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.client.CreativeTabJukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.configuration.ConfigHelper;
import net.cazzar.mods.jukeboxreloaded.events.EventHandler;
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.cazzar.mods.jukeboxreloaded.lib.Reference;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;
import java.util.EnumMap;
import java.util.Random;

public class CommonProxy {
    public BlockJukebox jukeBox;
    public ItemCustomRecord kokoro, loveIsWar, shibuya, spica, sukiDaiSuki, weArePopcandy;
    //    public ItemPortableJukebox portableJukebox;
    public CreativeTabJukeboxReloaded creativeTab;
    public EnumMap<Side, FMLEmbeddedChannel> channel;
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
        GameRegistry.registerItem(kokoro = new ItemCustomRecord("kokoro", "ココロ", new String[]{"Sung by Kagamine Rin", "writer トラボルタ feat. 鏡音リン"}).setDomain("cazzar"), "kokoro");
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
        ((Item) weArePopcandy).setCreativeTab(creativeTab);
    }

    public void initNetwork() {
        NetworkRegistry.INSTANCE.registerGuiHandler(JukeboxReloaded.instance(), new GuiHandler());
        //noinspection unchecked
        channel = NetworkRegistry.INSTANCE.newChannel(Reference.MOD_ID, new PacketHandler());
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
        VillagerRegistry.instance().registerVillagerId(3000);
        VillagerRegistry.instance().registerVillageTradeHandler(3000, new VillagerRegistry.IVillageTradeHandler() {
            @Override
            public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random) {
                switch (random.nextInt(6)) {
                    case 0:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), kokoro));
                        break;
                    case 1:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), loveIsWar));
                        break;
                    case 2:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), shibuya));
                        break;
                    case 3:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), spica));
                        break;
                    case 4:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), sukiDaiSuki));
                        break;
                    case 5:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), weArePopcandy));
                        break;
                }
            }
        });
    }
}