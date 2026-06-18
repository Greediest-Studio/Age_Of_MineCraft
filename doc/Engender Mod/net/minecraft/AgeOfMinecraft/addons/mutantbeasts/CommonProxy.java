package net.minecraft.AgeOfMinecraft.addons.mutantbeasts;

import net.minecraft.AgeOfMinecraft.registry.EEntity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
  public void preInit(FMLPreInitializationEvent e) {}
  
  public void init(FMLInitializationEvent e) {
    if (Loader.isModLoaded("mutantbeasts"))
      EEntity.registerMutantEntity(); 
  }
  
  public void postInit(FMLPostInitializationEvent e) {}
}
