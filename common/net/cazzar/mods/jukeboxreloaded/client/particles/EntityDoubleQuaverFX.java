package net.cazzar.mods.jukeboxreloaded.client.particles;

import net.minecraft.world.World;

/**
 * @Author: Cayde
 */
public class EntityDoubleQuaverFX extends EntityNoteFX {
    public EntityDoubleQuaverFX(World world, double x, double y, double z) {
        super(world, x, y, z);
        setParticleIcon(ParticleIcons.DOUBLE_QUAVER);
    }
}
