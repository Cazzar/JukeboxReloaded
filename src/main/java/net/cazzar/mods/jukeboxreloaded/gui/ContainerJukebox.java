/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.mods.jukeboxreloaded.gui;

import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

public class ContainerJukebox extends Container {
    protected static final int PLAYER_INVENTORY_ROWS = 3;
    protected static final int PLAYER_INVENTORY_COLUMNS = 9;

    protected static final int INVENTORY_ROWS = 3;
    protected static final int INVENTORY_COLUMNS = 4;

    TileJukebox tileJukebox;

    public ContainerJukebox(InventoryPlayer inventoryPlayer, TileJukebox tile) {
        tileJukebox = tile;
        int slot = 0;
        for (int col = 0; col < INVENTORY_ROWS; ++col)
            for (int row = 0; row < INVENTORY_COLUMNS; ++row)
                // IInventory inv, int slot, int x, int y
                addSlotToContainer(new Slot(tile, slot++, 54 + row * 18,
                        17 + col * 18));
        // offset corner * index * width to same point of slot

        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
                addSlotToContainer(new net.minecraft.inventory.Slot(
                        inventoryPlayer, inventoryColumnIndex
                        + inventoryRowIndex * 9 + 9,
                        8 + inventoryColumnIndex * 18,
                        94 + inventoryRowIndex * 18
                ));

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
            addSlotToContainer(new net.minecraft.inventory.Slot(
                    inventoryPlayer, actionBarSlotIndex,
                    8 + actionBarSlotIndex * 18, 152));
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int i) {
        ItemStack itemstack = null;
        final net.minecraft.inventory.Slot slot = (net.minecraft.inventory.Slot) inventorySlots
                .get(i);

        if (slot != null && slot.getHasStack()) {
            final ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            final int chestSlots = tileJukebox.getSizeInventory();
            if (i < chestSlots) {
                if (!mergeItemStack(itemstack1, chestSlots,
                        inventorySlots.size(), true)) return null;
            } else if (!mergeItemStack(itemstack1, 0, chestSlots, false))
                return null;
            if (itemstack1.stackSize == 0) slot.putStack(null);
            else slot.onSlotChanged();
        }
        return itemstack;
    }

    @Override
    protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4) {
        return par1ItemStack.getItem() instanceof ItemRecord && super.mergeItemStack(par1ItemStack, par2, par3, par4);
    }
}
