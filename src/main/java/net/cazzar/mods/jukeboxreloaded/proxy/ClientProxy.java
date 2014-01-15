package net.cazzar.mods.jukeboxreloaded.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.cazzar.mods.jukeboxreloaded.blocks.render.tile.TileJukeboxRender;
import net.cazzar.mods.jukeboxreloaded.blocks.render.item.ItemJukeboxRender;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
    @Override
    public void initVillagers() {
        super.initVillagers();
        VillagerRegistry.instance().registerVillagerSkin(3000, new ResourceLocation("cazzar", "textures/mob/villager.png"));
    }

    @Override
    public void initRendering() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileJukebox.class, new TileJukeboxRender());
        MinecraftForgeClient.registerItemRenderer(ItemBlock.func_150898_a(jukeBox), new ItemJukeboxRender());
    }

    @Override
    public FMLEmbeddedChannel getChannel() {
        return getChannelMap().get(Side.CLIENT);
    }
}
