package net.cazzar.mods.jukeboxreloaded.api;

import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;

/**
 * Created by Cayde on 16/12/2014.
 */
public interface IPlayMethod<T extends Item> {
    void play(T item, BlockPos pos);
    void stop(T item, BlockPos pos);
    boolean isPlaying(T item, BlockPos pos);

    /**
     * NEVER ASSUME that you will only get your item to play.
     * @param item the item to play
     * @return if you can play it via this interface implementation.
     */
    boolean canPlay(Item item);
}
