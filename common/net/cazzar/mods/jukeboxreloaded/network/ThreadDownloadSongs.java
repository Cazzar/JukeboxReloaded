package net.cazzar.mods.jukeboxreloaded.network;

import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.cazzar.mods.jukeboxreloaded.lib.util.Util;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.net.URL;

/**
 * @author Cayde Download the songs for the jukebox records.
 */
public class ThreadDownloadSongs implements Runnable {
    final String[] songs = {"kokoro.ogg", "love_is_war.ogg", "shibuya.ogg",
            "spica.ogg", "suki_daisuki.ogg", "we_are_popcandy.ogg"};
    final String baseUrl = "http://s3.amazonaws.com/CazzarMods/JukeboxReloaded/songs/";
    final File folder = new File(Minecraft.getMinecraft().mcDataDir,
            "resources/mod/streaming/JukeboxReloaded/");

    @Override
    public void run() {
        // System.out.println(Minecraft.getMinecraftDir().getAbsolutePath());
        folder.mkdirs();
        JukeboxReloaded.logger
                .info("Downloading records, this may take a while if this is your first startup");
        int i = 1;

        for (final String song : songs)
            try {
                JukeboxReloaded.logger.info("Downloading and registering %s of %s", i,
                        songs.length);

                if (!new File(folder, song).exists())
                    Util.saveUrl(new File(folder, song).getAbsolutePath(),
                            new URL(baseUrl + song));

                i++;

                // SoundSystemHelper.getSoundManager().soun
                // .addSound(song, new File(folder, song).toURI().toURL());
            } catch (final Exception e) {
                e.printStackTrace();
            }
    }
}
