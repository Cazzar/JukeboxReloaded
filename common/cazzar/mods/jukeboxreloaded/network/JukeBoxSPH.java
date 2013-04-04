package cazzar.mods.jukeboxreloaded.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetServerHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import static cazzar.mods.jukeboxreloaded.lib.Reference.Packets.*;
import java.util.Random;
import codechicken.core.packet.PacketCustom;
import codechicken.core.packet.PacketCustom.ICustomPacketHandler.IServerPacketHandler;
import codechicken.core.vec.BlockCoord;

public class JukeBoxSPH implements IServerPacketHandler {

	@Override
	public void handlePacket(PacketCustom packet, NetServerHandler nethandler,
			EntityPlayerMP sender) {
		switch (packet.getType()) {
		case SERVER_UPDATE_TILEJUKEBOX:
			// handleUpdatePacket(sender.worldObj, packet.readCoord(),
			// packet.readInt());
			updateTileEntity(sender.worldObj, packet.readCoord(),
					packet.readBoolean(), packet.readInt(), packet.readInt(),
					packet.readBoolean());
			break;
		case SERVER_NEXT_SHUFFLEDDISK:
			shuffleDisk(sender.worldObj, packet.readCoord(),
					packet.readString(), packet.readBoolean());
		default:
			break;
		}
	}

	private void updateTileEntity(World world, BlockCoord pos,
			boolean isPlaying, int currentRecord, int repeatMode,
			boolean shuffle) {
		final TileEntity tile = world.getBlockTileEntity(pos.x, pos.y, pos.z);
		if (tile instanceof TileJukeBox) {
			final TileJukeBox jukeBox = (TileJukeBox) tile;

			jukeBox.setRecordPlaying(currentRecord);
			jukeBox.setPlaying(isPlaying);
			jukeBox.setRepeatMode(repeatMode);
			jukeBox.setShuffle(shuffle);
			jukeBox.markForUpdate();
		}
	}

	private void shuffleDisk(World world, BlockCoord pos,
			String lastPlayingRecord, boolean shuffle) {
		final TileEntity tile = world.getBlockTileEntity(pos.x, pos.y, pos.z);
		if (tile instanceof TileJukeBox) {
			final TileJukeBox jukeBox = (TileJukeBox) tile;
			Random random = new Random();
			if (jukeBox.getLastSlotWithItem() < 0)
				return;
			int nextDisk = random.nextInt(jukeBox.getLastSlotWithItem());
			if (jukeBox.getCurrentRecordNumer() != nextDisk) {
				jukeBox.setRecordPlaying(nextDisk);
			}
		}
	}
}