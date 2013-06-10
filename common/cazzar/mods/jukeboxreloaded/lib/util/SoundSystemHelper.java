package cazzar.mods.jukeboxreloaded.lib.util;

import java.net.MalformedURLException;
import java.net.URL;

import de.cuina.fireandfuel.CodecJLayerMP3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.common.MinecraftForge;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

public class SoundSystemHelper {
	private static boolean		registeredCodecs	= false;
	
	public static SoundSystem getSoundSystem() {
		return SoundManager.sndSystem;
	}
	
	public static SoundManager getSoundManager() {
		return Minecraft.getMinecraft().sndManager;
	}
	
	public static void registerCodecs() {
		if (registeredCodecs)
			throw new RuntimeException(
					"We cannot register the codecs more than once!");
		SoundSystemConfig.setCodec("mp3", CodecJLayerMP3.class);
	}
}
