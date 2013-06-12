package cazzar.mods.jukeboxreloaded.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import cazzar.mods.jukeboxreloaded.lib.util.SoundSystemHelper;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketStopAllSounds extends PacketJukebox {
	
	public PacketStopAllSounds() {}
	
	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		if (side.isClient()) {
			SoundSystemHelper.getSoundManager().stopAllSounds();
		}
		else throw new ProtocolException(
				"Cannot send this packet to the server!");
	}
	
	@Override
	public void read(ByteArrayDataInput in) {}
	
	@Override
	public void write(ByteArrayDataOutput out) {}
	
}
