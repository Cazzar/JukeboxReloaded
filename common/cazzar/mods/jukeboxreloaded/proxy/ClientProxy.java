package cazzar.mods.jukeboxreloaded.proxy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.minecraft.client.Minecraft;
import cazzar.mods.jukeboxreloaded.network.ThreadDownloadSongs;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void extractTextures() {
	}
	
	@Override
	public void initOther() {
		final Thread downloader = new Thread(new ThreadDownloadSongs());
		downloader.setDaemon(true);
		downloader.run();
	}
	
	public Side getEffectiveSide() {
		return Side.CLIENT;
	}
}
