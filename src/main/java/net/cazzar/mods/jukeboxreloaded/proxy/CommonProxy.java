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
import net.cazzar.mods.jukeboxreloaded.gui.GuiHandler;
import net.cazzar.mods.jukeboxreloaded.items.ItemPortableJukebox;
import net.cazzar.mods.jukeboxreloaded.network.PacketHandler;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.io.File;
import java.util.EnumMap;
import java.util.Random;

public class CommonProxy {
    public BlockJukebox jukeBox;
    public ItemCustomRecord kokoro, loveIsWar, shibuya, spica, sukiDaiSuki, weArePopcandy;
    public ItemPortableJukebox portableJukebox;
    public CreativeTabJukeboxReloaded creativeTab;
    private ConfigHelper config;

    public EnumMap<Side, FMLEmbeddedChannel> getChannelMap() {
        return channelMap;
    }

    private EnumMap<Side,FMLEmbeddedChannel> channelMap;

    public void initBlocks() {
        creativeTab = new CreativeTabJukeboxReloaded();
        jukeBox = new BlockJukebox();
        GameRegistry.registerBlock(jukeBox, "jukebox");
    }

    public void initConfig(File suggested) {
        final Configuration config = new Configuration(suggested);
        this.config = new ConfigHelper(config);
        if (config.hasChanged())
            config.save();
    }

    @SuppressWarnings("RedundantCast")
    public void initItems() {
        GameRegistry.registerItem(kokoro = new ItemCustomRecord("cazzar:kokoro", "ogg", "\u30b3\u30b3\u30ed", "Sung by Kagamine Rin", "writer \u30c8\u30e9\u30dc\u30eb\u30bf feat. \u93e1\u97f3\u30ea\u30f3"), "kokoro");
        GameRegistry.registerItem(loveIsWar = new ItemCustomRecord("cazzar:love_is_war", "ogg", "Love is War", "Sung by Hatsune Miku", "Writer - Supercell feat. \u521d\u97f3\u30df\u30af"), "love_is_war");
        GameRegistry.registerItem(shibuya = new ItemCustomRecord("cazzar:shibuya", "ogg", "SHIBUYA (Original)", "by BECCA"), "shibuya");
        GameRegistry.registerItem(spica = new ItemCustomRecord("cazzar:spica", "ogg", "SPiCa", "by \u3068\u304f"), "spica");
        GameRegistry.registerItem(sukiDaiSuki = new ItemCustomRecord("cazzar:suki_daisuki", "ogg", "\u3059\u3059\u3059\u3059\u3001\u3059\u304d\u3001\u3060\u3042\u3044\u3059\u304d", "Sung by Kagamine Rin", "Writer - \u304b\u305f\u307b\u3068\u308aP"), "suki_daisuki");
        GameRegistry.registerItem(weArePopcandy = new ItemCustomRecord("cazzar:we_are_popcandy", "ogg", "We are POPCANDY!", "Sung by Hatsune Miku", "Writer RUNO"), "we_are_popcandy");
//        GameRegistry.registerItem(portableJukebox = new ItemPortableJukebox(config.items.portableJukeboxId), "Portable Jukebox");

        ((Item)kokoro).setCreativeTab(creativeTab);
        ((Item)loveIsWar).setCreativeTab(creativeTab);
        ((Item)shibuya).setCreativeTab(creativeTab);
        ((Item)spica).setCreativeTab(creativeTab);
        ((Item)sukiDaiSuki).setCreativeTab(creativeTab);
        ((Item)weArePopcandy).setCreativeTab(creativeTab);
    }

    public void initNetwork() {
        NetworkRegistry.INSTANCE.registerGuiHandler(JukeboxReloaded.instance(), new GuiHandler());
        channelMap = NetworkRegistry.INSTANCE.newChannel("JukeboxReloaded", new PacketHandler());
    }

    public void initOther() {
//        MinecraftForge.EVENT_BUS.register(new EventHandler());
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
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), kokoro));
                        break;
                    case 2:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), loveIsWar));
                        break;
                    case 3:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), shibuya));
                        break;
                    case 4:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), spica));
                        break;
                    case 5:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), sukiDaiSuki));
                        break;
                    case 6:
                        recipeList.addToListWithCheck(new MerchantRecipe(new ItemStack(Items.emerald), weArePopcandy));
                        break;
                }
            }
        });
    }

    public FMLEmbeddedChannel getChannel() {
        return channelMap.get(Side.SERVER);
    }
}