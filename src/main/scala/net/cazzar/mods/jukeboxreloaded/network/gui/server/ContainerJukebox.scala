package net.cazzar.mods.jukeboxreloaded.network.gui.server

import net.cazzar.mods.jukeboxreloaded.blocks.tileentity.TileJukebox
import net.minecraft.entity.player.{InventoryPlayer, EntityPlayer}
import net.minecraft.inventory.{Slot, Container}

class ContainerJukebox(inv: InventoryPlayer, tile: TileJukebox) extends Container {
  var slot = 0
  
  //Jukebox
  for (row <- 0 until ContainerJukebox.INVENTORY_ROWS)
    for (col <- 0 until ContainerJukebox.INVENTORY_COLUMNS) {
      println(slot)
      addSlotToContainer(new JukeboxSlot(tile, slot, 54 + col * 18, 17 + row * 18))
      slot += 1
    }
  
  //Player main inventory
  for (row <- 0 until ContainerJukebox.PLAYER_INVENTORY_ROWS)
    for (col <- 0 until ContainerJukebox.PLAYER_INVENTORY_COLUMNS)
      addSlotToContainer(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 94 + row * 18))

  println("hi")
  for (action <- 0 until InventoryPlayer.getHotbarSize)
    addSlotToContainer(new Slot(inv, action, 8 + action * 18, 152))

  override def canInteractWith(playerIn: EntityPlayer): Boolean = true
}

object ContainerJukebox {
  final val PLAYER_INVENTORY_ROWS = 3
  final val PLAYER_INVENTORY_COLUMNS = 9

  final val INVENTORY_ROWS = 3
  final val INVENTORY_COLUMNS = 4

  def apply(inv: InventoryPlayer, tile: TileJukebox) = new ContainerJukebox(inv, tile)
}
