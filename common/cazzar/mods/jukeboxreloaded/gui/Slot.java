/**
 * 
 */
package cazzar.mods.jukeboxreloaded.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

/**
 * @author Cayde
 *
 */
public class Slot extends net.minecraft.inventory.Slot {

    public Slot(IInventory inv, int slot, int x, int y)
    {
        super(inv, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack item)
    {
       return (item.getItem() instanceof ItemRecord);
    }
    
    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
    
}
