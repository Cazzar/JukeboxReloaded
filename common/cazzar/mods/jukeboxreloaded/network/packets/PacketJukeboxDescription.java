package cazzar.mods.jukeboxreloaded.network.packets;

import net.minecraft.entity.player.EntityPlayer;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketJukeboxDescription extends PacketJukebox {
	int		x, y, z;
	int		recordNumber;
	boolean	playingRecord;
	int		repeatMode;
	boolean	shuffle;
	short	facing;
	
	public PacketJukeboxDescription() {}
	
	public PacketJukeboxDescription(TileJukeBox tile) {
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
		recordNumber = tile.getCurrentRecordNumer();
		playingRecord = tile.playing;
		repeatMode = tile.getReplayMode();
		shuffle = tile.shuffleEnabled();
		facing = tile.getFacing();
	}
	
	@Override
	public void execute(EntityPlayer player, Side side)
			throws ProtocolException {
		final TileJukeBox tile = (TileJukeBox) player.worldObj
				.getBlockTileEntity(x, y, z);
		
		tile.setRecordPlaying(recordNumber);
		tile.setPlaying(playingRecord);
		//tile.setPlayingID(playingRecord);
		tile.setRepeatMode(repeatMode);
		tile.setShuffle(shuffle);
		tile.setFacing(facing);
		
		// if (side.isServer())
		// tile.markForUpdate();
	}
	
	@Override
	public void read(ByteArrayDataInput in) {
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		recordNumber = in.readInt();
		playingRecord = in.readBoolean();
		repeatMode = in.readInt();
		shuffle = in.readBoolean();
		facing = in.readShort();
	}
	
	@Override
	public void write(ByteArrayDataOutput out) {
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeInt(recordNumber);
		out.writeBoolean(playingRecord);
		out.writeInt(repeatMode);
		out.writeBoolean(shuffle);
		out.writeShort(facing);
	}
	
}
