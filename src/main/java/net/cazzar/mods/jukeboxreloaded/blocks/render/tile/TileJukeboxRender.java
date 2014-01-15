package net.cazzar.mods.jukeboxreloaded.blocks.render.tile;

import net.cazzar.mods.jukeboxreloaded.blocks.TileJukebox;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;
import net.minecraftforge.common.util.ForgeDirection;

import static net.cazzar.corelib.util.ClientUtil.mc;
import static org.lwjgl.opengl.GL11.*;

public class TileJukeboxRender extends TileEntitySpecialRenderer {
    WavefrontObject model;
    final ResourceLocation textureLocation = new ResourceLocation("cazzar", "textures/map.png");

    public TileJukeboxRender() {
        model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation("cazzar", "model/Jukebox.obj"));
    }

    @Override
    public void func_147500_a(TileEntity tile, double x, double y, double z, float tick) {
        if (tile instanceof TileJukebox) {
            TileJukebox tileJukebox = (TileJukebox) tile;

            glPushMatrix();
//            glDisable(GL_LIGHTING);
//
            glTranslated(x + 0.5, y, z + 0.5);
            glScaled(0.4F, 0.4F, 0.4F);
            switch (ForgeDirection.getOrientation(tileJukebox.getFacing())) {
                case NORTH:
                    glRotatef(180, 0, 1, 0);
                    break;
                case SOUTH:
                    glRotatef(0, 0, 1, 0);
                    break;
                case WEST:
                    glRotatef(-90, 0, 1, 0);
                    break;
                case EAST:
                    glRotatef(90, 0, 1, 0);
                    break;
            }
            mc().renderEngine.bindTexture(textureLocation);

//            Tessellator.instance.addTranslation((float)x, (float)y, (float)z);
            model.renderAll();

//            glEnable(GL_LIGHTING);
            glPopMatrix();
        }
    }
}
