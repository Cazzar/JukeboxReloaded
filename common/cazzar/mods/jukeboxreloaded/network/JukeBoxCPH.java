package cazzar.mods.jukeboxreloaded.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import cazzar.mods.jukeboxreloaded.lib.Reference;
import static cazzar.mods.jukeboxreloaded.lib.Reference.Packets.*;
import static cazzar.mods.jukeboxreloaded.lib.Reference.GUIJukeBoxActions.*;
import codechicken.core.packet.PacketCustom;
import codechicken.core.packet.PacketCustom.ICustomPacketHandler.IClientPacketHandler;
import codechicken.core.vec.BlockCoord;

public class JukeBoxCPH implements IClientPacketHandler {
	public static final String channel = Reference.CHANNEL_NAME;

	@Override
	public void handlePacket(PacketCustom packet, NetClientHandler nethandler,
			Minecraft mc) {
		switch (packet.getType()) {
		case CLIENT_UPDATE_TILEJUKEBOX:
			handleUpdatePacket(mc.theWorld, packet.readCoord(),
					packet.readInt(), packet.readInt());
			break;

		case CLIENT_TILEJUKEBOX_DATA:
			handleTileEntityData(mc.theWorld, packet.readCoord(),
					packet.readBoolean(), packet.readInt(), packet.readInt(),
					packet.readBoolean());
			break;
		default:
			break;
		}
	}

	private void handleTileEntityData(WorldClient world, BlockCoord pos,
			boolean isPlaying, int currentRecord, int repeatMode,
			boolean shuffle) {
		final TileEntity tile = world.getBlockTileEntity(pos.x, pos.y, pos.z);
		if (tile instanceof TileJukeBox) {
			((TileJukeBox) tile).setPlaying(isPlaying);
			((TileJukeBox) tile).setRecordPlaying(currentRecord);
		}
	}

	private void handleUpdatePacket(World world, BlockCoord pos, int action,
			int currentRecord) {
		final TileEntity tile = world.getBlockTileEntity(pos.x, pos.y, pos.z);
		if (tile instanceof TileJukeBox) {
			switch (action) {
			case PLAY:
				((TileJukeBox) tile).setPlaying(true);
				break;

			case STOP:
				((TileJukeBox) tile).setPlaying(false);
				break;

			case NEXT:
			case PREVIOUS:
				((TileJukeBox) tile).setRecordPlaying(currentRecord);
				break;
			}
		}
	}
}
