/*
package net.cazzar.mods.jukeboxreloaded.client;

import com.google.common.collect.Lists;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import java.util.List;

*/
/**
 * Created by Cayde on 10/11/2014.
 *//*

public class WaliaCompat implements IWailaDataProvider {

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return new ItemStack(JukeboxReloaded.proxy.jukeBox);
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (accessor.getBlock() != JukeboxReloaded.proxy.jukeBox) return null;
        TileJukebox jukebox = (TileJukebox) accessor.getTileEntity();

        List<String> data = Lists.newArrayList();

        if (jukebox.isPlayingRecord()) {
            data.add(I18n.format("cazzar:walia.jukebox.playing"));

            data.add(I18n.format("cazzar:walia.jukebox.currentlyPlaying", jukebox.getRecordName()));
        }

        return data;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }
}
*/
