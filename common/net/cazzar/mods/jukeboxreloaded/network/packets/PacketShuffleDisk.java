package net.cazzar.mods.jukeboxreloaded.network.packets;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class PacketShuffleDisk extends PacketJukebox {

    int x, y, z;

    public PacketShuffleDisk() {
    }

    public PacketShuffleDisk(TileJukebox tile) {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }

    @Override
    public void execute(EntityPlayer player, Side side)
            throws ProtocolException {
        if (side.isServer()) {
            final TileEntity tile = player.worldObj.getBlockTileEntity(x, y, z);
            if (tile instanceof TileJukebox) {
                final TileJukebox jukeBox = (TileJukebox) tile;
                final Random random = new Random();
                if (jukeBox.getLastSlotWithItem() <= 0) return;
                final int nextDisk = random.nextInt(jukeBox
                        .getLastSlotWithItem());
                if (jukeBox.getCurrentRecordNumer() != nextDisk)
                    jukeBox.setRecordPlaying(nextDisk);
                ((TileJukebox) tile).markForUpdate();
            }
        }
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
