package cazzar.mods.jukeboxreloaded.network;

import java.io.File;
import java.net.URL;

import cazzar.mods.jukeboxreloaded.lib.util.SoundSystemHelper;
import cazzar.mods.jukeboxreloaded.lib.util.Util;

/**
 * @author Cayde
 *         Download the songs for the jukebox records.
 */
public class ThreadDownloadSongs implements Runnable {
	String[]	songs	= { "kokoro.ogg", "love_is_war.ogg", "shibuya.ogg",
			"spica.ogg", "suki_daisuki.ogg", "we_are_popcandy.ogg" };
	String		baseUrl	= "http://download.cazzar.net/JukeboxReloaded/songs/";
	String		folder	= "resources/mod/streaming/JukeboxReloaded/";
	
	@Override
	public void run() {
		new File(folder).mkdirs();
		for (final String song : songs)
			try {
				if (!new File(folder, song).exists())
					Util.saveUrl(folder + song, new URL(baseUrl + song));
				
				SoundSystemHelper.getSoundManager().soundPoolStreaming.addSound(song, new File(folder, song).toURI().toURL());
			}
			catch (final Exception e) {
				e.printStackTrace();
			}
	}
}
