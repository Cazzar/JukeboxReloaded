package net.cazzar.mods.jukeboxreloaded.blocks.render.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.obj.WavefrontObject;

import static net.cazzar.corelib.util.ClientUtil.mc;
import static org.lwjgl.opengl.GL11.*;

public class ItemJukeboxRender implements IItemRenderer {
    WavefrontObject model;
    final ResourceLocation textureLocation = new ResourceLocation("cazzar", "textures/map.png");

    public ItemJukeboxRender() {
        model = (WavefrontObject) AdvancedModelLoader.loadModel(new ResourceLocation("cazzar", "model/Jukebox.obj"));
    }
    /**
     * Checks if this renderer should handle a specific item's render type
     *
     * @param item The item we are trying to render
     * @param type A render type to check if this renderer handles
     *
     * @return true if this renderer should handle the given render type, otherwise false
     */
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    /**
     * Checks if certain helper functionality should be executed for this renderer. See ItemRendererHelper for more
     * info
     *
     * @param type   The render type
     * @param item   The ItemStack being rendered
     * @param helper The type of helper functionality to be ran
     *
     * @return True to run the helper functionality, false to not.
     */
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    /**
     * Called to do the actual rendering, see ItemRenderType for details on when specific types are run, and what extra
     * data is passed into the data parameter.
     *
     * @param type The render type
     * @param item The ItemStack being rendered
     * @param data Extra Type specific data
     */
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        glPushMatrix();

        glScaled(0.35F, 0.35F, 0.35F);
        glTranslatef(0, -1.7F, 0);
//        glRotatef(45, 0, 1, 0);
        mc().renderEngine.bindTexture(textureLocation);
        model.renderAll();

        glPopMatrix();
    }
}
