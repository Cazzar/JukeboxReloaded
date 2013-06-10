/**
 * 
 */
package cazzar.mods.jukeboxreloaded.network;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import cazzar.mods.jukeboxreloaded.lib.util.SoundSystemHelper;
import cazzar.mods.jukeboxreloaded.lib.util.Util;


/**
 * @author Cayde
 * Download the songs for the jukebox records.
 */
public class ThreadDownloadSongs implements Runnable {
	String[] songs = {"kokoro.mp3", "love_is_war.mp3", "shibuya.mp3", "spica.mp3", "suki_daisuki.mp3", "we_are_popcandy.mp3"};
	String baseUrl = "http://download.cazzar.net/JukeboxReloaded/songs/";
	String folder = "resources/mod/streaming/JukeboxReloaded";
	
	@Override
	public void run() {
		new File(folder).mkdirs();
		for (String song : songs) {
			try {
				if (!new File(folder, song).exists()) 
					Util.saveUrl(folder + song, new URL(baseUrl + song));
				//Minecraft.getMinecraft().installResource("resources/mod/streaming/JukeboxReloaded/", new File(folder, song));
				//Minecraft.getMinecraft().installResource(folder, new File(folder + song));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
