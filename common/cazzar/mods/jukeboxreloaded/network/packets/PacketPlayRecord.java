package cazzar.mods.jukeboxreloaded.network.packets;

import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.util.LogHelper;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;

public class PacketPlayRecord extends PacketJukebox {
	int	x, y, z;
	String record;
	
	public PacketPlayRecord() {}
	
	public PacketPlayRecord(String record, int x, int y, int z) {
		this.record = record;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		TileEntity te = player.worldObj.getBlockTileEntity(x, y, z);
		if (te instanceof TileJukeBox) {
			((TileJukeBox) te).forcePlayRecord(record);
			((TileJukeBox) te).setForcedPlaying(true);
		}
		else return;
		if (side.isServer()) {
			((TileJukeBox) te).markForUpdate();
			PacketDispatcher.sendPacketToAllPlayers(this.makePacket());
		}
	}
	
	@Override
	public void read(ByteArrayDataInput in) {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		record = in.readUTF();
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeUTF(record);
	}
}
