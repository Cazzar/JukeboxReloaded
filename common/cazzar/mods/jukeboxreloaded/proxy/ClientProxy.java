package cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void extractTextures() {
	}
	
	@Override
	public void initOther() {
		super.initOther();
		
		//final Thread downloader = new Thread(new ThreadDownloadSongs());
		//downloader.setDaemon(true);
		//downloader.run();
	}
	
	public Side getEffectiveSide() {
		return Side.CLIENT;
	}
}
