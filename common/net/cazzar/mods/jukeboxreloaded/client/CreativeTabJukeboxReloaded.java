package net.cazzar.mods.jukeboxreloaded.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author Cayde
 */
public class CreativeTabJukeboxReloaded extends CreativeTabs {
    public CreativeTabJukeboxReloaded() {
        super(CreativeTabs.getNextID(), "Jukebox Reloaded");
    }

    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex() {
        return JukeboxReloaded.proxy().jukeBox.blockID;
    }
}
