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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;

public enum Particles {
    QUAVER(EntityQuaverFX.class),
    CROTCHET(EntityCrotchetFX.class),
    DOUBLE_QUAVER(EntityDoubleQuaverFX.class);

    Class<? extends EntityFX> clazz;

    private Particles(Class<? extends EntityFX> clazz) {
        this.clazz = clazz;
    }

    @SideOnly(Side.CLIENT)
    public void spawn(double x, double y, double z) {
        Minecraft mc = ClientUtil.mc();

        if (mc == null) return;
        if (mc.renderViewEntity == null || mc.effectRenderer == null) return;

        int setting = mc.gameSettings.particleSetting;
        if (setting == 2 || (setting == 1 && mc.theWorld.rand.nextInt(3) == 0))
            return;

        double plPosX = mc.renderViewEntity.posX - x;
        double plPosY = mc.renderViewEntity.posY - y;
        double plPosZ = mc.renderViewEntity.posZ - z;

        double maxOffset = 16.0D; //vanilla default

        if (plPosX * plPosX + plPosY * plPosY + plPosZ * plPosZ > maxOffset * maxOffset) { //yay, pythagoras!
            return;
        }

        EntityFX entityFX;

        try {
            Constructor constructor = clazz.getDeclaredConstructor(World.class, double.class, double.class, double.class);
            entityFX = (EntityFX) constructor.newInstance(mc.theWorld, x, y, z);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        mc.effectRenderer.addEffect(entityFX);
    }
}
