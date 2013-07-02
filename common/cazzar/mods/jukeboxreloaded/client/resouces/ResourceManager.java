package cazzar.mods.jukeboxreloaded.client.resouces;

import java.io.IOException;

import net.minecraft.client.resources.Resource;
import net.minecraft.client.resources.ResourceLocation;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.MetadataSerializer;

public class ResourceManager extends SimpleReloadableResourceManager {

	
	public ResourceManager(MetadataSerializer par1MetadataSerializer) {
		super(par1MetadataSerializer);
	}

	/**
	 * Get Resource.
	 */
	@Override
	public Resource func_110536_a(ResourceLocation location)
			throws IOException {
		return null;
	}
}
