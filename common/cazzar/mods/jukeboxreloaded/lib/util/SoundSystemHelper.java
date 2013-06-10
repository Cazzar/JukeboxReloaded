package cazzar.mods.jukeboxreloaded.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundManager;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import de.cuina.fireandfuel.CodecJLayerMP3;

public class SoundSystemHelper {
	private static boolean	registeredCodecs	= false;
	
	public static SoundManager getSoundManager() {
		return Minecraft.getMinecraft().sndManager;
	}
	
	public static SoundSystem getSoundSystem() {
		return SoundManager.sndSystem;
	}
	
	public static void registerCodecs() {
		if (registeredCodecs)
			throw new RuntimeException(
					"We cannot register the codecs more than once!");
		SoundSystemConfig.setCodec("mp3", CodecJLayerMP3.class);
	}
}
