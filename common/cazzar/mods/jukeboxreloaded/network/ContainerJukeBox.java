package cazzar.mods.jukeboxreloaded.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import cazzar.mods.jukeboxreloaded.blocks.TileJukeBox;
import codechicken.core.inventory.SlotDummy;

public class ContainerJukeBox extends Container
{
    private final int PLAYER_INVENTORY_ROWS = 3;
    private final int PLAYER_INVENTORY_COLUMNS = 9;

    private final int INVENTORY_ROWS = 4;
    private final int INVENTORY_COLUMNS = 3;

    private final TileJukeBox tileJukeBox;

    public ContainerJukeBox(InventoryPlayer inventoryPlayer, TileJukeBox tile)
    {
        
        tileJukeBox = tile;

        int slot = 0;
        for ( int col = 0; col < INVENTORY_COLUMNS; ++col )
        {
            for ( int row = 0; row < INVENTORY_ROWS; ++row )
            {
                // IInventory inv, int slot, int x, int y
                addSlotToContainer(new SlotDummy(tile, slot++, 54 + row * 18,
                        17 + col * 18));
                // offset corner * index * width to same point of slot
            }
        }

        // Add the player's inventory slots to the container
        for ( int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex )
        {
            for ( int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex )
            {
                addSlotToContainer(new SlotDummy(inventoryPlayer,
                        inventoryColumnIndex + inventoryRowIndex * 9 + 9,
                        8 + inventoryColumnIndex * 18,
                        94 + inventoryRowIndex * 18));
            }
        }

        // Add the player's action bar slots to the container
        for ( int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex )
        {
            addSlotToContainer(new SlotDummy(inventoryPlayer,
                    actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 152));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotID)
    {
        
        //FIXME: fix up adding any item.
        ItemStack itemstack = null;
        final Slot slot = (Slot) inventorySlots.get(slotID);

        if ( slot != null && slot.getHasStack() )
        {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if ( !(itemstack.getItem() instanceof ItemRecord) ) return null;
            final int slots = tileJukeBox.getSizeInventory();
            if ( slotID < slots )
            {
                if ( !mergeItemStack(itemstack1, slots, inventorySlots.size(),
                        true) ) return null;
            }
            else if ( !mergeItemStack(itemstack1, 0, slots, false) )
                return null;
            if ( itemstack1.stackSize == 0 )
            {
                if ( !(itemstack1.getItem() instanceof ItemRecord) )
                    return null;
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}
