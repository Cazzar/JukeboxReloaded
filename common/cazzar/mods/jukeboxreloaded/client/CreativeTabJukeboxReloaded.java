package cazzar.mods.jukeboxreloaded.client;

import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @author Cayde
 *
 */
public class CreativeTabJukeboxReloaded extends CreativeTabs {
	public CreativeTabJukeboxReloaded() {
		super(CreativeTabs.getNextID(), "Jukebox Reloaded");
	}
	
    @SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        return JukeboxReloaded.instance().proxy().jukeBox.blockID;
    }
}
