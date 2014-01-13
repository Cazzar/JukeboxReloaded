package net.cazzar.mods.jukeboxreloaded.blocks.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.cazzar.mods.jukeboxreloaded.JukeboxReloaded;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class JukeboxRenderingHandler implements ISimpleBlockRenderingHandler {
    IModelCustom model;

    public JukeboxRenderingHandler() {
        model = AdvancedModelLoader.loadModel(new ResourceLocation("cazzar:model/Jukebox.obj"));
    }

    @Override
    public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderBlocks) {
        model.renderAll();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int meta, RenderBlocks renderBlocks) {
        model.renderAll();
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return true;
    }

    @Override
    public int getRenderId() {
        return JukeboxReloaded.proxy().getJukeboxRenderID();
    }
}
