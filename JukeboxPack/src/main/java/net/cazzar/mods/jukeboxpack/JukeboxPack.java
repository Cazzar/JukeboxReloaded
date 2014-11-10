package net.cazzar.mods.jukeboxpack;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.cazzar.corelib.items.ItemCustomRecord;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author Cayde
 */
@Mod(modid = JukeboxPack.MODID, version = "@VERSION@", dependencies = "after:JukeboxReloaded")
public class JukeboxPack {
    static final String MODID = "jukeboxpack";
    public ItemCustomRecord kokoro, loveIsWar, shibuya, spica, sukiDaiSuki, weArePopcandy;

    @Mod.Instance
    public static JukeboxPack instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        CreativeTabs creativeTab = CreativeTabs.tabMisc;
        if (Loader.isModLoaded("JukeboxReloaded")) {
            creativeTab = JukeboxReloaded.proxy.creativeTab;
        }

        GameRegistry.registerItem(kokoro = new ItemCustomRecord("kokoro", "\u30b3\u30b3\u30ed", new String[]{"Sung by Kagamine Rin", "writer \u30c8\u30e9\u30dc\u30eb\u30bf feat. \u93e1\u97f3\u30ea\u30f3"}).setDomain(MODID), "kokoro");
        GameRegistry.registerItem(loveIsWar = new ItemCustomRecord("love_is_war", "Love is War", new String[]{"Sung by Hatsune Miku", "Writer - Supercell feat. \u521d\u97f3\u30df\u30af"}).setDomain(MODID), "love_is_war");
        GameRegistry.registerItem(shibuya = new ItemCustomRecord("shibuya", "SHIBUYA (Original)", new String[]{"by BECCA"}).setDomain(MODID), "shibuya");
        GameRegistry.registerItem(spica = new ItemCustomRecord("spica", "SPiCa", new String[]{"by \u3068\u304f"}).setDomain(MODID), "spica");
        GameRegistry.registerItem(sukiDaiSuki = new ItemCustomRecord("suki_daisuki", "\u3059\u3059\u3059\u3059\u3001\u3059\u304d\u3001\u3060\u3042\u3044\u3059\u304d", new String[]{"Sung by Kagamine Rin", "Writer - かたほとりP"}).setDomain(MODID), "suki_daisuki");
        GameRegistry.registerItem(weArePopcandy = new ItemCustomRecord("we_are_popcandy", "We are POPCANDY!", new String[]{"Sung by Hatsune Miku", "Writer RUNO"}).setDomain(MODID), "we_are_popcandy");

        ((Item) kokoro).setCreativeTab(creativeTab);
        ((Item) loveIsWar).setCreativeTab(creativeTab);
        ((Item) shibuya).setCreativeTab(creativeTab);
        ((Item) spica).setCreativeTab(creativeTab);
        ((Item) sukiDaiSuki).setCreativeTab(creativeTab);
        ((Item) weArePopcandy).setCreativeTab(creativeTab);
    }
}
