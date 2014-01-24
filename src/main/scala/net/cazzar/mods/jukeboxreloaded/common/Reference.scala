package net.cazzar.mods.jukeboxreloaded.common

import net.minecraft.util.ResourceLocation

object Reference {

    //-1 for the loops.
    val PLAYER_INVENTORY_ROWS = 3 - 1
    val PLAYER_INVENTORY_COLUMNS = 9 - 1
    val JUKEBOX_INVENTORY_ROWS = 3 - 1
    val JUKEBOX_INVENTORY_COLUMNS = 4 - 1
    val JUKEBOX_GUI_TEXTURE = new ResourceLocation("cazzar", "textures/gui/jukebox.png")

    object GuiActions {
        val PLAY = 0
        val STOP = 1
        val NEXT = 2
        val PREVIOUS = 3
        val REPEAT_OFF = 4
        val REPEAT_ALL = 5
        val REPEAT_ONE = 6
        val SHUFFLE_OFF = 7
        val SHUFFLE = 8
        val VOLUME_UP = 9
        val VOLUME_DOWN = 10
    }
}
