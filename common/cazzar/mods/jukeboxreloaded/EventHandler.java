package cazzar.mods.jukeboxreloaded;

import net.minecraft.entity.player.EntityPlayer;
import cazzar.mods.jukeboxreloaded.lib.util.SoundSystemHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;

public class EventHandler implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		if (FMLCommonHandler.instance().getEffectiveSide().isClient())
			SoundSystemHelper.getSoundManager().stopAllSounds();
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {}
	@Override
	public void onPlayerRespawn(EntityPlayer player) {}
}
