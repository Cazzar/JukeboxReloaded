package cazzar.mods.jukeboxreloaded;

import cazzar.mods.jukeboxreloaded.network.packets.PacketStopAllSounds;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.Player;

public class EventHandler implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		new PacketStopAllSounds().sendToAllInDimension((Player)player);
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {}
	@Override
	public void onPlayerRespawn(EntityPlayer player) {}
}
