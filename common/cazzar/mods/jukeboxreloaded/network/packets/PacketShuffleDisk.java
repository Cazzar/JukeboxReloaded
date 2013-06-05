package cazzar.mods.jukeboxreloaded.network.packets;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

import cpw.mods.fml.relauncher.Side;

public class PacketShuffleDisk extends PacketJukebox {

    int x, y, z;

    public PacketShuffleDisk(TileJukeBox tile)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }
    
    public PacketShuffleDisk() {}
    
    @Override
    public void write(ByteArrayDataOutput out)
    {
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(z);
    }

    @Override
    public void read(ByteArrayDataInput in)
    {
        x = in.readInt();
        y = in.readInt();
        z = in.readInt();
    }

    @Override
    public void execute(EntityPlayer player, Side side) throws ProtocolException
    {
        if (side.isServer())
        {
            final TileEntity tile = player.worldObj.getBlockTileEntity(x, y, z);
            if (tile instanceof TileJukeBox)
            {
                final TileJukeBox jukeBox = (TileJukeBox) tile;
                Random random = new Random();
                if (jukeBox.getLastSlotWithItem() <= 0) return;
                int nextDisk = random.nextInt(jukeBox.getLastSlotWithItem());
                if (jukeBox.getCurrentRecordNumer() != nextDisk)
                {
                    jukeBox.setRecordPlaying(nextDisk);
                }
                ((TileJukeBox) tile).markForUpdate();
            }
        }
    }
}
