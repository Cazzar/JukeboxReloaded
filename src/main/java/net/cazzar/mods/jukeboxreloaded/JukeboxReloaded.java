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

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, dependencies = "required-after:CazzarCoreLib", certificateFingerprint = "b69d7336fbe4c3e97279eb3ee3199f009a903475")
//@NetworkMod(clientSideRequired = true, serverSideRequired = true, channels = {Reference.CHANNEL_NAME}, packetHandler = PacketHandler.class)
public class JukeboxReloaded {
    public static Logger logger = LogHelper.getLogger(Reference.MOD_ID);
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
    @Instance(Reference.MOD_ID)
    private static JukeboxReloaded instance;
    public int render;

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
