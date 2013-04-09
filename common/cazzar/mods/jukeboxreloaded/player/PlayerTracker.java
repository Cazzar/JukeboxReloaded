package cazzar.mods.jukeboxreloaded.player;

import cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		if (player.username.equals("cazzar"))
		{
			player.cloakUrl = "http://www.minecraftcapes.com/userskins/BUDDER__by_Shane_Lisbon.png";
			player.updateCloak();
		}
		
		String update = JukeboxReloaded.getUpdateDetailIfExists();
		if (update != null)
		{
			player.sendChatToPlayer("There is an update for JukeboxReloaded out");
			player.sendChatToPlayer(update);
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

}
