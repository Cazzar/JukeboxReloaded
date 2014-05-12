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

package net.cazzar.mods.jukeboxreloaded.client.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.cazzar.mods.jukeboxreloaded.blocks.BlockGlowTest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import static org.lwjgl.opengl.GL11.*;

public class RenderGlowBlock implements ISimpleBlockRenderingHandler {
    protected static void renderAllSides(IBlockAccess world, int x, int y, int z, Block block, RenderBlocks renderer, IIcon tex, boolean allsides) {
        if ((allsides) || (block.shouldSideBeRendered(world, x + 1, y, z, 6)))
            renderer.renderFaceXPos(block, x, y, z, tex);
        if ((allsides) || (block.shouldSideBeRendered(world, x - 1, y, z, 6)))
            renderer.renderFaceXNeg(block, x, y, z, tex);
        if ((allsides) || (block.shouldSideBeRendered(world, x, y, z + 1, 6)))
            renderer.renderFaceZPos(block, x, y, z, tex);
        if ((allsides) || (block.shouldSideBeRendered(world, x, y, z - 1, 6)))
            renderer.renderFaceZNeg(block, x, y, z, tex);
        if ((allsides) || (block.shouldSideBeRendered(world, x, y + 1, z, 6)))
            renderer.renderFaceYPos(block, x, y, z, tex);
        if ((allsides) || (block.shouldSideBeRendered(world, x, y - 1, z, 6)))
            renderer.renderFaceYNeg(block, x, y, z, tex);
    }

    public static void drawFaces(RenderBlocks renderer, Block block, IIcon yNeg, IIcon yPos, IIcon xNeg, IIcon xPos, IIcon zNeg, IIcon zPos, boolean solidtop) {
        Tessellator tessellator = Tessellator.instance;
        glTranslated(-0.5, -0.5, -0.5);

        tessellator.startDrawingQuads();
        tessellator.setNormal(0f, -1f, 0f);
        renderer.renderFaceYNeg(block, 0, 0, 0, yNeg);
        tessellator.draw();

        if (solidtop) glDisable(GL_ALPHA_TEST);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0f, 1f, 0f);
        renderer.renderFaceYNeg(block, 0, 0, 0, yPos);
        tessellator.draw();
        if (solidtop) glEnable(GL_ALPHA_TEST);

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1f, 0f, 0f);
        renderer.renderFaceXNeg(block, 0, 0, 0, xNeg);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(1f, 0f, 0f);
        renderer.renderFaceXPos(block, 0, 0, 0, xPos);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(-1f, 0f, 0f);
        renderer.renderFaceXNeg(block, 0, 0, 0, zNeg);
        tessellator.draw();

        tessellator.startDrawingQuads();
        tessellator.setNormal(0f, 0f, 1f);
        renderer.renderFaceZPos(block, 0, 0, 0, zPos);
        tessellator.draw();

        glTranslated(0.5, 0.5, 0.5);
    }

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        final Tessellator tesr = Tessellator.instance;
        block.setBlockBounds(0, 0, 0, 1, 1, 1);
        renderer.setRenderBoundsFromBlock(block);
        renderer.renderStandardBlock(block, x, y, z);

        tesr.setColorOpaque(0, 0, 255);
        tesr.setBrightness(200);

        renderAllSides(world, x, y, z, block, renderer, ((BlockGlowTest) block).icons[0], false);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return 0;
    }
}
