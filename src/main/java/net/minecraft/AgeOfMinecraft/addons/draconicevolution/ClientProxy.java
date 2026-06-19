package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
  public void preInit(FMLPreInitializationEvent e) {
    super.preInit(e);
  }
  
  public void init(FMLInitializationEvent e) {
    super.init(e);
  }
  
  public void postInit(FMLPostInitializationEvent e) {
    super.postInit(e);
  }
  
  public void renderEntitiesDraconicEvolution() {
    if (Loader.isModLoaded("draconicevolution")) {
      RenderingRegistry.registerEntityRenderingHandler(EntityChaosGuardian.class, RenderChaosGuardian::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityGuardianProjectile.class, RenderGuardianProjectile::new);
      RenderingRegistry.registerEntityRenderingHandler(EntityDraconicPortal.class, RenderDraconicPortal::new);
    } 
  }
}
