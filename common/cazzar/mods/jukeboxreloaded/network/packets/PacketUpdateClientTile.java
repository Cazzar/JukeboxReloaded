package cazzar.mods.jukeboxreloaded.network.packets;

import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.NEXT;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.PLAY;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.PREVIOUS;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.STOP;
import net.minecraft.entity.player.EntityPlayer;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketUpdateClientTile extends PacketJukebox {
	
	// packet.writeCoord(this.getCoord());
	// packet.writeBoolean(this.isPlayingRecord());
	// packet.writeInt(this.getCurrentRecordNumer());
	
	int	x, y, z;
	int	action;
	int	currentRecord;
	
	public PacketUpdateClientTile() {}
	
	public PacketUpdateClientTile(TileJukeBox tile, int action) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
		this.action = action;
		currentRecord = tile.getCurrentRecordNumer();
	}
	
	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		if (side.isClient()) {
			final TileJukeBox tile = (TileJukeBox) player.worldObj
					.getBlockTileEntity(x, y, z);
			
			switch (action) {
				case PLAY:
					tile.setPlaying(true);
					break;
				
				case STOP:
					tile.setPlaying(false);
					break;
				
				case NEXT:
				case PREVIOUS:
					tile.setRecordPlaying(currentRecord);
					break;
			}
		}
		else throw new ProtocolException(
				"Cannot send this packet to the server!");
	}
	
	@Override
	public void read(ByteArrayDataInput in) {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		action = in.readInt();
		currentRecord = in.readInt();
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeInt(action);
		out.writeInt(currentRecord);
	}
	
}
