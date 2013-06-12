package cazzar.mods.jukeboxreloaded.lib;

import net.minecraft.util.StatCollector;

public enum Strings {
	GUI_JUKEBOX_NAME("gui.jukebox.name"),
	GUI_INVENTORY("container.inventory")
	
	;
	String name;
	private Strings(String name) {
		this.name = name;
	}
	
	public String toString() {
		return StatCollector.translateToLocal(name);
	}
}
