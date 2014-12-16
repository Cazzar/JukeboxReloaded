package net.cazzar.mods.jukeboxreloaded.proxy

import net.cazzar.mods.jukeboxreloaded.blocks.BlockJukebox
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.item.Item
import net.cazzar.mods.jukeboxreloaded.Util._
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent

/**
 * Created by Cayde on 15/12/2014.
 */
class ClientProxy extends IProxy {
  override def postInit(e: FMLPostInitializationEvent): Unit = {
    super.postInit(e)

    Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(BlockJukebox, 0, new ModelResourceLocation("jukeboxreloaded:jukebox", "inventory"))
  }
}
