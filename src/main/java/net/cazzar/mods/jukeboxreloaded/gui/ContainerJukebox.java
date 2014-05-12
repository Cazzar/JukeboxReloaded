/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
