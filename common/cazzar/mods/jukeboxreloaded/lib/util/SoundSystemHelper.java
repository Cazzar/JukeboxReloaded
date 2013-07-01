package cazzar.mods.jukeboxreloaded.lib.util;

import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;

import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.item.ItemRecord;
import net.minecraft.world.World;

import net.minecraftforge.client.event.sound.PlayStreamingEvent;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.common.MinecraftForge;

import paulscode.sound.SoundSystem;

public class SoundSystemHelper {
	public static SoundManager getSoundManager() {
		return Minecraft.getMinecraft().sndManager;
	}
	
	public static SoundSystem getSoundSystem() {
		return SoundManager.sndSystem;
	}
	
	public static boolean isSoundEnabled() {
		return getSoundSystem() == null;
	}
	
	TileJukeBox	tile;
	Side		side;
	
	public SoundSystemHelper(TileJukeBox tile) {
		this.tile = tile;
		JukeboxReloaded.instance();
		side = JukeboxReloaded.proxy().getEffectiveSide();
	}
	
	public void playRecord(String record, World world, float x, float y,
			float z, float volume) {
		tile.waitTicks = 20;
		if (side.isServer()) return;
		if (getSoundSystem() == null) return;
		
		SoundSystem sndSystem = getSoundSystem();
		
		ItemRecord itemrecord = ItemRecord.getRecord(record);
		if (itemrecord == null) return;
		
		Minecraft.getMinecraft().ingameGUI.setRecordPlayingMessage(itemrecord
				.getRecordTitle());
		if (sndSystem.playing(tile.getIdentifier())) sndSystem.stop(tile.getIdentifier());
		
		SoundPoolEntry song = getSoundManager().soundPoolStreaming
				.getRandomSoundFromSoundPool(record);
		song = SoundEvent.getResult(new PlayStreamingEvent(getSoundManager(),
				song, tile.getIdentifier(), x, y, z));
		
		if (song == null) return;
		if (sndSystem.playing("BgMusic")) sndSystem.stop("BgMusic");
		
		float f3 = 16.0F;
		sndSystem.newStreamingSource(true, tile.getIdentifier(), song.soundUrl,
				song.soundName, false, x, y, z, 2, f3 * 4.0F);
		sndSystem.setVolume(tile.getIdentifier(), volume * Minecraft.getMinecraft().gameSettings.soundVolume);
		MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(
				getSoundManager(), tile.getIdentifier(), x, y, z));
		sndSystem.play(tile.getIdentifier());
	}
	
	public void pause() {
		if (side.isServer()) return;
		if (getSoundSystem() == null) return;
		
		getSoundSystem().pause(tile.getIdentifier());
	}
	
	public void resume() {
		if (side.isServer()) return;
		if (getSoundSystem() == null) return;
		
		getSoundSystem().play(tile.getIdentifier());
	}
	
	public void stop() {
		if (side.isServer()) return;
		if (getSoundSystem() == null) return;
		
		getSoundSystem().stop(tile.getIdentifier());
	}
	
	public static void stop(String identifier) {
		JukeboxReloaded.instance();
		if (JukeboxReloaded.proxy().getEffectiveSide().isServer()) return;
		if (getSoundSystem() == null) return;
		
		getSoundSystem().stop(identifier);
	}
	
	public boolean isPlaying() {
		if (side.isServer()) return false;
		if (getSoundSystem() == null) return false;
		
		return getSoundSystem().playing(tile.getIdentifier());
	}
}
