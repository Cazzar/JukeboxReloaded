package net.cazzar.mods.jukeboxreloaded.events;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IPlayerTracker;
import net.cazzar.corelib.lib.SoundSystemHelper;
import net.minecraft.entity.player.EntityPlayer;

public class EventHandler implements IPlayerTracker {

    @Override
    public void onPlayerLogin(EntityPlayer player) {
    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient())
            SoundSystemHelper.getSoundManager().stopAllSounds();
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) {
    }
}
