package net.minecraft.AgeOfMinecraft;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import java.util.List;

import net.minecraft.AgeOfMinecraft.ageofminecraft.Tags;
import net.minecraft.AgeOfMinecraft.commands.CommandKillEngenderMobs;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.events.EngenderGeneralEvent;
import net.minecraft.AgeOfMinecraft.util.PrivateHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION)
public class EngenderMod {

  public static final String CLIENT = "net.minecraft.AgeOfMinecraft.ClientProxy";
  
  @SidedProxy(clientSide = "net.minecraft.AgeOfMinecraft.ClientProxy",
              serverSide = "net.minecraft.AgeOfMinecraft.CommonProxy")

  public static CommonProxy proxy;
  
  @Instance
  public static EngenderMod instance;
  
  public static Logger logger;

  @EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    logger = e.getModLog();
    logger.info("Loading The Engender Mod...");
    logger.debug("Pre-Initialization started");
    NetworkRegistry.INSTANCE.registerGuiHandler(instance, new CommonProxy());
    PrivateHelper.set(RangedAttribute.class, (RangedAttribute)SharedMonsterAttributes.MAX_HEALTH, 2147483647.0D, "maximumValue", "field_111118_b", "b");
    PrivateHelper.set(RangedAttribute.class, (RangedAttribute)SharedMonsterAttributes.ATTACK_DAMAGE, 2147483647.0D, "maximumValue", "field_111118_b", "b");
    proxy.preInit(e);
    logger.debug("Pre-Initialization finished");
  }
  
  @EventHandler
  public void init(FMLInitializationEvent e) {
    logger.debug("Initialization started");
    MinecraftForge.EVENT_BUS.register(this);
    MinecraftForge.EVENT_BUS.register(new EngenderGeneralEvent());
    MinecraftForge.EVENT_BUS.register(EngenderGeneralEvent.musicTicker);
    ConfigManager.sync("ageofminecraft", Config.Type.INSTANCE);
    proxy.init(e);
    logger.debug("Initialization finished");
  }
  
  @EventHandler
  public void postInit(FMLPostInitializationEvent e) {
    logger.debug("Post-Initialization started!");
    proxy.postInit(e);
    logger.debug("Post-Initialization finished");
    logger.info("Finished The Engender Mod!");
  }
  
  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equals("ageofminecraft")) {
      console("Config was updated!");
      ConfigManager.sync("ageofminecraft", Config.Type.INSTANCE);
    } 
  }
  
  @EventHandler
  public void onServerStart(FMLServerStartingEvent e) {
    e.registerServerCommand(new CommandKillEngenderMobs());
  }
  
  public static boolean sensorsShowJzahars(World world) {
    if (Loader.isModLoaded("abyssalcraft")) {
      List<?> list = world.loadedEntityList;
      if (!list.isEmpty())
          for (Object o : list) {
              Entity entity = (Entity) o;
              if (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityJzahar)
                  return true;
              if (entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
                  return true;
          }
    } 
    return false;
  }

  public static boolean canBeTurned(Entity entity) {
    return (entity instanceof net.minecraft.entity.player.EntityPlayer || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid || entity instanceof net.minecraft.entity.monster.EntityZombie || entity instanceof net.minecraft.entity.passive.EntitySquid || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton || entity instanceof net.minecraft.entity.monster.AbstractSkeleton || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper || entity instanceof net.minecraft.entity.monster.EntityCreeper || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entity instanceof net.minecraft.entity.passive.EntityVillager || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumSquid || (
      
      Loader.isModLoaded("abyssalcraft") && (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadling || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid)));
  }
  
  public static boolean doesntHaveTimeToBleed(Entity entity) {
    return ((entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isEntityUndead()) || entity instanceof net.minecraft.entity.monster.EntityBlaze || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityGolem || (entity instanceof EntityTameBase && ((EntityTameBase)entity)
      
      .isNotALivingThing()) || (
      Loader.isModLoaded("abyssalcraft") && entity instanceof EntityLiving && ((EntityLiving)entity).getCreatureAttribute() == AbyssalCraftAPI.SHADOW));
  }
  
  public static void console(String string) {
    console(string, true);
  }
  
  public static void console(String string, boolean onlyDebug) {
    if (onlyDebug)
      if (EngenderConfig.debugMode) {
        logger.info(string);
      } else {
        logger.info(string);
      }  
  }
}
