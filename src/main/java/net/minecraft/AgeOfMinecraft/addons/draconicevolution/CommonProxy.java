package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import net.minecraft.AgeOfMinecraft.registry.EEntity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
  public void registerRenders() {}
  
  public void preInit(FMLPreInitializationEvent e) {
    if (Loader.isModLoaded("draconicevolution"))
      renderEntitiesDraconicEvolution(); 
  }
  
  public void init(FMLInitializationEvent e) {
    if (Loader.isModLoaded("draconicevolution"))
      EEntity.registerDracEvoEntity(); 
  }
  
  public void postInit(FMLPostInitializationEvent e) {
  }
  
  public void renderEntitiesDraconicEvolution() {}
}
