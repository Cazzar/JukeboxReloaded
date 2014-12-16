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
