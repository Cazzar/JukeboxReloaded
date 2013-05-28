package cazzar.mods.jukeboxreloaded.proxy;

import net.minecraft.entity.Entity;

public class ClientProxy extends CommonProxy {    
    @Override
    public void SetCape(Entity ent, String capeURL) 
    {
    	ent.cloakUrl = capeURL;
		ent.updateCloak();
    }
}
