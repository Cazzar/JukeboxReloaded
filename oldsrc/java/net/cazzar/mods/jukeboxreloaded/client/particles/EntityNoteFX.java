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

package net.cazzar.mods.jukeboxreloaded.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityNoteFX extends EntityFX {
    public EntityNoteFX(World world, double x, double y, double z) {
        super(world, x, y, z);

        this.particleRed = rand.nextFloat();
        this.particleGreen = rand.nextFloat();
        this.particleBlue = rand.nextFloat();

        //this.particleAlpha = rand.nextFloat() * 0.5F + 0.5F;

        this.particleMaxAge = 10;

        //this.motionX = (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        this.motionY = Math.random() * 0.15D;
        //this.motionZ = (double)((float)(Math.random() * 2.0D - 1.0D) * 0.4F);
        this.particleScale = 2 + rand.nextFloat() - 0.5F;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.motionX = Math.sin(this.particleAge) * 0.075 * rand.nextDouble();
        this.motionZ = Math.cos(this.particleAge) * 0.075 * rand.nextDouble();
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    protected void setParticleIcon(ResourceLocation location) {} //noop
}
