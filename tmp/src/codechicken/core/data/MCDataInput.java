package codechicken.core.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.liquids.LiquidStack;
import codechicken.core.vec.BlockCoord;

public interface MCDataInput
{
    public long readLong();
    public int readInt();
    public short readShort();
    public int readUnsignedShort();
    public byte readByte();
    public int readUnsignedByte();
    public double readDouble();
    public float readFloat();
    public boolean readBoolean();
    public char readChar();
    public byte[] readByteArray(int length);
    public String readString();
    public BlockCoord readCoord();
    public NBTTagCompound readNBTTagCompound();
    public ItemStack readItemStack();
    public LiquidStack readLiquidStack();
}
