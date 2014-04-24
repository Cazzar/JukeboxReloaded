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

package net.cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox;

public class CommonProxy {
    BlockJukebox block;
    public void initBlocks() {
        GameRegistry.registerBlock(new BlockJukebox(), "jukebox");
    }
}
