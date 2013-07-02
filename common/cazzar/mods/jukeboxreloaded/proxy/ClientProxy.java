package cazzar.mods.jukeboxreloaded.proxy;

import cazzar.mods.jukeboxreloaded.network.ThreadDownloadSongs;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
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
