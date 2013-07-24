package net.cazzar.mods.jukeboxreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

import static net.cazzar.mods.jukeboxreloaded.gui.ContainerJukebox.PLAYER_INVENTORY_COLUMNS;
import static net.cazzar.mods.jukeboxreloaded.gui.ContainerJukebox.PLAYER_INVENTORY_ROWS;

public class ContainerPortableJukebox extends Container {

    public ContainerPortableJukebox(InventoryPlayer playerInventory) {
        // Add the player's inventory slots to the container
        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex)
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex)
                addSlotToContainer(new net.minecraft.inventory.Slot(
                        playerInventory, inventoryColumnIndex
                        + inventoryRowIndex * 9 + 9,
                        8 + inventoryColumnIndex * 18,
                        84 + inventoryRowIndex * 18));

        // Add the player's action bar slots to the container
        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex)
            addSlotToContainer(new net.minecraft.inventory.Slot(
                    playerInventory, actionBarSlotIndex,
                    8 + actionBarSlotIndex * 18, 142));
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
}
