package net.cazzar.mods.jukeboxreloaded.client;

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

/**
 * @author Cayde
 */
public class CreativeTabJukeboxReloaded extends CreativeTabs {
    public CreativeTabJukeboxReloaded() {
        super(CreativeTabs.getNextID(), "Jukebox Reloaded");
    }

    @Override
    public Item getTabIconItem() {
        return ItemBlock.func_150898_a(JukeboxReloaded.proxy().jukeBox);
    }

//    @SideOnly(Side.CLIENT)
//    public int getTabIconItemIndex() {
//        return JukeboxReloaded.proxy().jukeBox.blockID;
//    }
}
