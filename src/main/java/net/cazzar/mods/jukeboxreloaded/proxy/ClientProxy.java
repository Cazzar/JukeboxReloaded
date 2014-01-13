package net.cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy {
    @Override
    public void initVillagers() {
        super.initVillagers();
        VillagerRegistry.instance().registerVillagerSkin(3000, new ResourceLocation("cazzar", "textures/mob/villager.png"));
    }

    @Override
    public FMLEmbeddedChannel getChannel() {
        return getChannelMap().get(Side.CLIENT);
    }
}
