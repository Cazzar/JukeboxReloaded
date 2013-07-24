package net.cazzar.mods.jukeboxreloaded.lib;

import net.minecraft.util.StatCollector;

public enum Strings {
    GUI_JUKEBOX_NAME("gui.jukebox.name"),
    GUI_INVENTORY("container.inventory"),
    TOOLTIP_PLAY("gui.jukebox.play"),
    TOOLTIP_STOP("gui.jukebox.stop"),
    TOOLTIP_NEXT("gui.jukebox.next"),
    TOOLTIP_PREV("gui.jukebox.prev"),
    TOOLTIP_REPEAT_ONE("gui.jukebox.repeat.one"),
    TOOLTIP_REPEAT_ALL("gui.jukebox.repeat.all"),
    TOOLTIP_REPEAT_NONE("gui.jukebox.repeat.none"),
    TOOLTIP_SHUFFLE_ON("gui.jukebox.shuffle.on"),
    TOOLTIP_SHUFFLE_OFF("gui.jukebox.shuffle.off"),
    GUI_PORTABLE_JUKEBOX_NAME("gui.portableJukebox.name");

    private final String name;

    private Strings(String name) {
        this.name = name;
    }

    public String toString() {
        return StatCollector.translateToLocal(name);
    }
}
