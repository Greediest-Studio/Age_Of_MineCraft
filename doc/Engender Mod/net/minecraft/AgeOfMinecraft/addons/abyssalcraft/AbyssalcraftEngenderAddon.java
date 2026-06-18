package net.minecraft.AgeOfMinecraft.addons.abyssalcraft;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "ageofabyssalcraft", name = "Engender - Abyssalcraft Addon", version = "0.7 Pre Release 2", acceptedMinecraftVersions = "[1.12.2]", dependencies = "before:abyssalcraft;")
public class AbyssalcraftEngenderAddon {
  public static final String MODNAME = "Engender - Abyssalcraft Addon";
  
  public static final String MODID = "ageofabyssalcraft";
  
  public static final String VERSION = "0.7 Pre Release 2";
  
  public static final String CLIENT = "net.minecraft.AgeOfMinecraft.addons.abyssalcraft.ClientProxy";
  
  public static final String SERVER = "net.minecraft.AgeOfMinecraft.addons.abyssalcraft.CommonProxy";
  
  @SidedProxy(clientSide = "net.minecraft.AgeOfMinecraft.addons.abyssalcraft.ClientProxy", serverSide = "net.minecraft.AgeOfMinecraft.addons.abyssalcraft.CommonProxy")
  public static CommonProxy proxy;
  
  @Instance
  public static AbyssalcraftEngenderAddon instance;
  
  public static Logger logger;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    logger = e.getModLog();
    if (Loader.isModLoaded("abyssalcraft")) {
      logger.info("Abyssalcraft detected!");
      logger.info("Loading Abyssalcraft Addon...");
      proxy.preInit(e);
      logger.debug("Pre Initialization started!");
      logger.debug("Finished pre-init for Abyssalcraft Addon!");
    } else {
      logger.info("Warning! Abyssalcraft is not installed. You will not have access to the Eldritch Horrors within until you install it.");
    } 
  }
  
  @EventHandler
  public void init(FMLInitializationEvent e) {
    if (Loader.isModLoaded("abyssalcraft")) {
      proxy.init(e);
      logger.debug("Initialization started!");
      proxy.registerRenders();
      logger.debug("Finished init for Abyssalcraft Addon!");
    } 
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    if (Loader.isModLoaded("abyssalcraft")) {
      proxy.postInit(e);
      logger.debug("Post Initialization started!");
      proxy.renderEntitiesAbyssalcraft();
      logger.debug("Finished post-init for Abyssalcraft Addon!");
      logger.info("Finished Abyssalcraft Addon!");
    } 
  }
}
