package net.cazzar.mods.jukeboxreloaded.blocks.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

import static net.cazzar.corelib.util.ClientUtil.mc;
import static org.lwjgl.opengl.GL11.*;

public class JukeboxRenderingHandler implements ISimpleBlockRenderingHandler {
    WavefrontObject model;
    final ResourceLocation textureLocation = new ResourceLocation("cazzar", "textures/map.png");

    public JukeboxRenderingHandler() {
        model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation("cazzar:model/Jukebox.obj"));
    }

    @Override
    public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderBlocks) {
        glPushMatrix();
        glScalef(0.35F, 0.35F, 0.35F);
        glTranslatef(0F, -1.8F, 0F);
        glRotatef(-90, 0, 1, 0);
        glBindTexture(GL_TEXTURE_2D, mc().renderEngine.getTexture(textureLocation).getGlTextureId());
        model.renderAll();
        glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess iBlockAccess, int x, int y, int z, Block block, int meta, RenderBlocks renderBlocks) {
//        glPushMatrix();
        final Tessellator tess = Tessellator.instance;
        tess.addTranslation(x, y, z);

//        glScaled(0.5, 0.5, 0.5);
//        int tessTexId = tess.textureID;
        tess.textureID = mc().renderEngine.getTexture(textureLocation).getGlTextureId();
//        tess.defaultTexture = false;

        model.tessellateAll(tess);
//        tess.textureID = tessTexId;
//        glPopMatrix();
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int i) {
        return true;
    }

    @Override
    public int getRenderId() {
//        return JukeboxReloaded.proxy().getJukeboxRenderID();
        return 0;
    }
}
