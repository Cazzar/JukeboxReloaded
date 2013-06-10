package cazzar.mods.jukeboxreloaded.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

/**
 * @author Cayde
 */
public class PacketStopPlaying extends PacketJukebox {

	public int x, y, z;
	
	public PacketStopPlaying() {}
	
	public PacketStopPlaying(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		if (side.isServer()) {
			PacketDispatcher.sendPacketToAllPlayers(this.makePacket());
		}
		TileEntity te = player.worldObj.getBlockTileEntity(x, y, z);
		if (te instanceof TileJukeBox) {
			((TileJukeBox) te).setForcedPlaying(false);
		}
		
		player.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1005, x, y, z, 0);
		
	}

	@Override
	public void read(ByteArrayDataInput in) {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();		
	}

	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
	}
	
	
	
}
