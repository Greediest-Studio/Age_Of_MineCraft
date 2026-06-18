package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "ageofchaos", name = "Engender - Draconic Evolution Addon", version = "0.7 Pre Release 2", acceptedMinecraftVersions = "[1.12.2]", dependencies = "before:draconicevolution;")
public class DraconicEvolutionAddon {
  public static final String MODNAME = "Engender - Draconic Evolution Addon";
  
  public static final String MODID = "ageofchaos";
  
  public static final String VERSION = "0.7 Pre Release 2";
  
  public static final String CLIENT = "net.minecraft.AgeOfMinecraft.addons.draconicevolution.ClientProxy";
  
  public static final String SERVER = "net.minecraft.AgeOfMinecraft.addons.draconicevolution.CommonProxy";
  
  @SidedProxy(clientSide = "net.minecraft.AgeOfMinecraft.addons.draconicevolution.ClientProxy", serverSide = "net.minecraft.AgeOfMinecraft.addons.draconicevolution.CommonProxy")
  public static CommonProxy proxy;
  
  @Instance
  public static DraconicEvolutionAddon instance;
  
  public static Logger logger;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    logger = e.getModLog();
    if (Loader.isModLoaded("draconicevolution")) {
      logger.info("Draconic Evolution detected!");
      logger.info("Loading Draconic Evolution Addon...");
      proxy.preInit(e);
      logger.debug("Pre Initialization started!");
      logger.debug("Finished pre-init for Draconic Evolution Addon!");
    } else {
      logger.info("Warning! Draconic Evolution is not installed. You will not have access to the Chaos Guardian until you install it.");
    } 
  }
  
  @EventHandler
  public void init(FMLInitializationEvent e) {
    if (Loader.isModLoaded("draconicevolution")) {
      proxy.init(e);
      logger.debug("Initialization started!");
      proxy.registerRenders();
      logger.debug("Finished init for Draconic Evolution Addon!");
    } 
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    if (Loader.isModLoaded("draconicevolution")) {
      proxy.postInit(e);
      logger.debug("Post Initialization started!");
      proxy.renderEntitiesDraconicEvolution();
      logger.debug("Finished post-init for Draconic Evolution Addon!");
      logger.info("Finished Draconic Evolution Addon!");
    } 
  }
}
