package net.cazzar.mods.jukeboxreloaded.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

/**
 * @Author: Cayde
 */
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
}
