package cazzar.mods.jukeboxreloaded.lib.util;

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
import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoundSystemHelper {
	public static SoundManager getSoundManager() {
		return Minecraft.getMinecraft().sndManager;
	}
	
	public static SoundSystem getSoundSystem() {
		return getSoundManager().sndSystem;
	}
	
	public static boolean isSoundEnabled() {
		return getSoundSystem() == null;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRecord(String filename) {
		//Minecraft.getMinecraft().sndManager.addStreaming(filename);
		getSoundManager().soundPoolStreaming.addSound(filename);
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
		sndSystem.newStreamingSource(true, tile.getIdentifier(), song.func_110457_b(),
				song.func_110458_a(), false, x, y, z, 2, f3 * 4.0F);
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
