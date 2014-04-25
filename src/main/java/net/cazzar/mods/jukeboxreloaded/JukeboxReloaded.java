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
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.cazzar.corelib.lib.LogHelper;
import net.cazzar.mods.jukeboxreloaded.lib.Reference;
import net.cazzar.mods.jukeboxreloaded.proxy.CommonProxy;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME)
//@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {Reference.CHANNEL_NAME}, packetHandler = PacketHandler.class)
public class JukeboxReloaded {
    public static Logger logger = LogHelper.getLogger(Reference.MOD_ID);
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    @Instance(Reference.MOD_ID)
    private static JukeboxReloaded instance;

    public static JukeboxReloaded instance() {
        return instance;
    }

    public static CommonProxy proxy() {
        return proxy;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        event.getModMetadata().version = getVersionFromJar();
        //LogHelper.init();
        proxy.initNetwork();
        proxy.initConfig(event.getSuggestedConfigurationFile());

        proxy.initBlocks();
        proxy.initItems();
        proxy.initTileEntities();
        proxy.initRecipe();

        proxy.initOther();
    }

    @EventHandler
    public void init(FMLInitializationEvent evt) {
        proxy.initVillagers();
    }

    public String getVersionFromJar() {
        String s = getClass().getPackage().getImplementationVersion();
        return s == null || s.isEmpty() ? "UNKNOWN" : s;
    }
}
