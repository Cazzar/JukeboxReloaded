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

package net.cazzar.mods.jukeboxreloaded;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy;

@Mod(modid = "JukeboxReloaded")
public class JukeboxReloaded {
    @Mod.Instance
    public static JukeboxReloaded instance;

    @SidedProxy(clientSide = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy", serverSide = "net.cazzar.mods.jukeboxreloaded.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = getVersionFromJar();
        proxy.initBlocks();
    }

    private static String getVersionFromJar() {
        final String version = JukeboxReloaded.class.getPackage().getImplementationVersion();
        return (version == null || version.isEmpty()) ? "Unknown" : version;
    }
}
