package cazzar.mods.jukeboxreloaded.proxy;

import cazzar.mods.jukeboxreloaded.lib.util.SoundSystemHelper;
import cazzar.mods.jukeboxreloaded.network.ThreadDownloadSongs;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.Entity;

public class ClientProxy extends CommonProxy {
	@Override
	public void initOther() {
		SoundSystemHelper.registerCodecs();
		
		Thread downloader = new Thread(new ThreadDownloadSongs());
		downloader.setDaemon(true);
		downloader.run();
	}
	
	@Override
	public void SetCape(Entity ent, String capeURL) {
		ent.cloakUrl = capeURL;
		ent.updateCloak();
	}
}
