package cazzar.mods.jukeboxreloaded.proxy;

import net.minecraft.entity.Entity;
import cazzar.mods.jukeboxreloaded.network.ThreadDownloadSongs;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	@Override
	public void initOther() {
		final Thread downloader = new Thread(new ThreadDownloadSongs());
		downloader.setDaemon(true);
		downloader.run();
	}
	
	@Override
	public void SetCape(Entity ent, String capeURL) {
		ent.cloakUrl = capeURL;
		ent.updateCloak();
	}
	
	public Side getEffectiveSide() {
		return Side.CLIENT;
	}
}
