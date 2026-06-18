package net.minecraft.AgeOfMinecraft.addons.mutantbeasts;

import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = "ageofmutants", name = "Engender - Mutant Beasts (Creatures) Addon", version = "0.7 Pre Release 2", acceptedMinecraftVersions = "[1.12.2]")
public class MutantBeastsAddon {
  public static final String MODNAME = "Engender - Mutant Beasts (Creatures) Addon";
  
  public static final String MODID = "ageofmutants";
  
  public static final String VERSION = "0.7 Pre Release 2";
  
  public static final String CLIENT = "net.minecraft.AgeOfMinecraft.addons.mutantbeasts.ClientProxy";
  
  public static final String SERVER = "net.minecraft.AgeOfMinecraft.addons.mutantbeasts.CommonProxy";
  
  @SidedProxy(clientSide = "net.minecraft.AgeOfMinecraft.addons.mutantbeasts.ClientProxy", serverSide = "net.minecraft.AgeOfMinecraft.addons.mutantbeasts.CommonProxy")
  public static CommonProxy proxy;
  
  @Instance
  public static MutantBeastsAddon instance;
  
  public static Logger logger;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    logger = e.getModLog();
    if (Loader.isModLoaded("mutantbeasts")) {
      logger.info("Mutant Beasts detected!");
      logger.info("Loading Mutant Beasts Addon...");
      proxy.preInit(e);
      logger.debug("Pre Initialization started!");
      logger.debug("Finished pre-init for Mutant Beasts Addon!");
    } else {
      logger.info("Warning! Mutant Beasts is not installed. You will not have access to the Chaos Guardian until you install it.");
    } 
  }
  
  @EventHandler
  public void init(FMLInitializationEvent e) {
    if (Loader.isModLoaded("mutantbeasts")) {
      proxy.init(e);
      logger.debug("Initialization started!");
      logger.debug("Finished init for Mutant Beasts Addon!");
    } 
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    if (Loader.isModLoaded("mutantbeasts")) {
      proxy.postInit(e);
      logger.debug("Post Initialization started!");
      EItem.fusionPigSpider.setCreativeTab(ETab.mutant);
      EItem.fusionMutantSnowGolem.setCreativeTab(ETab.mutant);
      EItem.fusionMutantCreeper.setCreativeTab(ETab.mutant);
      EItem.fusionMutantEnderman.setCreativeTab(ETab.mutant);
      EItem.fusionMutantSkeleton.setCreativeTab(ETab.mutant);
      EItem.fusionMutantZombie.setCreativeTab(ETab.mutant);
      EItem.pigSpiderItem.setCreativeTab(ETab.mutant);
      EItem.mutantSnowGolemItem.setCreativeTab(ETab.mutant);
      EItem.mutantCreeperItem.setCreativeTab(ETab.mutant);
      EItem.mutantEndermanItem.setCreativeTab(ETab.mutant);
      EItem.mutantSkeletonItem.setCreativeTab(ETab.mutant);
      EItem.mutantZombieItem.setCreativeTab(ETab.mutant);
      logger.debug("Finished post-init for Mutant Beasts Addon!");
      logger.info("Finished Mutant Beasts Addon!");
    } 
  }
}
