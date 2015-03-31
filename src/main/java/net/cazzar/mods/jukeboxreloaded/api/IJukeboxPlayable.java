package net.cazzar.mods.jukeboxreloaded.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

/**
 * Created by Cayde on 16/12/2014.
 */
public interface IJukeboxPlayable {
    void play(ItemStack item, BlockPos pos);
    void stop(ItemStack item, BlockPos pos);
    boolean isPlaying(ItemStack item, BlockPos pos);

    /**
     * NEVER ASSUME that you will only get your item to play.
     * @param item the item to play
     * @return if you can play it via this interface implementation.
     */
    boolean canPlay(ItemStack item);
}
