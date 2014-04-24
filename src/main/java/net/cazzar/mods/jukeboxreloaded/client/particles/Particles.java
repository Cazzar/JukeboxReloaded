/*
 * Copyright (C) 2014 Cayde Dixon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.cazzar.mods.jukeboxreloaded.client.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.cazzar.corelib.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;

/**
 * @Author: Cayde
 */
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
