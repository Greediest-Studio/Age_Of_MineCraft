package net.minecraft.AgeOfMinecraft;

import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import java.util.List;
import net.daveyx0.primitivemobs.entity.monster.EntityTrollager;
import net.minecraft.AgeOfMinecraft.commands.CommandKillEngenderMobs;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.events.EngenderGeneralEvent;
import net.minecraft.AgeOfMinecraft.util.PrivateHelper;
import net.minecraft.command.ICommand;
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

@Mod(modid = "ageofminecraft", name = "Engender - The Age of Minecraft", version = "0.7 Pre Release 2", acceptedMinecraftVersions = "[1.12.2]")
public class EngenderMod {
  public static final String MODNAME = "Engender - The Age of Minecraft";
  
  public static final String MODID = "ageofminecraft";
  
  public static final String VERSION = "0.7 Pre Release 2";
  
  public static final String CLIENT = "net.minecraft.AgeOfMinecraft.ClientProxy";
  
  public static final String SERVER = "net.minecraft.AgeOfMinecraft.CommonProxy";
  
  @SidedProxy(clientSide = "net.minecraft.AgeOfMinecraft.ClientProxy", serverSide = "net.minecraft.AgeOfMinecraft.CommonProxy")
  public static CommonProxy proxy;
  
  @Instance
  public static EngenderMod instance;
  
  public static Logger logger;
  
  public static final int statCheckerGUIID = 100;
  
  public static final int engenderfuserGUIID = 101;
  
  @EventHandler
  public void preInit(FMLPreInitializationEvent e) {
    logger = e.getModLog();
    logger.info("Loading The Engender Mod...");
    logger.debug("Pre-Initialization started");
    NetworkRegistry.INSTANCE.registerGuiHandler(instance, new CommonProxy());
    PrivateHelper.set(RangedAttribute.class, SharedMonsterAttributes.field_111267_a, Integer.valueOf(2147483647), new String[] { "maximumValue", "field_111118_b" });
    PrivateHelper.set(RangedAttribute.class, SharedMonsterAttributes.field_111264_e, Integer.valueOf(2147483647), new String[] { "maximumValue", "field_111118_b" });
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
    e.registerServerCommand((ICommand)new CommandKillEngenderMobs());
  }
  
  public static boolean sensorsShowJzahars(World world) {
    if (Loader.isModLoaded("abyssalcraft")) {
      List<?> list = world.field_72996_f;
      if (!list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          Entity entity = (Entity)list.get(i1);
          if (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityJzahar)
            return true; 
          if (entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
            return true; 
        }  
    } 
    return false;
  }
  
  public static boolean sensorsShowWithers(World world) {
    List<?> list = world.field_72996_f;
    if (!list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        Entity entity = (Entity)list.get(i1);
        if (entity instanceof net.minecraft.entity.boss.EntityWither)
          return true; 
      }  
    return false;
  }
  
  public static boolean isWoodLikeMob(Entity entity) {
    return (entity instanceof net.minecraft.entity.boss.EntityWither || entity instanceof net.minecraft.entity.monster.AbstractSkeleton || entity instanceof net.minecraft.entity.monster.EntityShulker || entity instanceof net.minecraft.entity.passive.EntityMooshroom || (entity instanceof EntityTameBase && ((EntityTameBase)entity)
      
      .isWoodLike()) || (
      Loader.isModLoaded("primitivemobs") && (entity instanceof net.daveyx0.primitivemobs.entity.monster.EntityHauntedTool || entity instanceof net.daveyx0.primitivemobs.entity.monster.EntityMimic || entity instanceof net.daveyx0.primitivemobs.entity.monster.EntityEnchantedBook || entity instanceof net.daveyx0.primitivemobs.entity.monster.EntityBlazingJuggernaut || (entity instanceof EntityTrollager && ((EntityTrollager)entity)
      
      .isStone()))) || (
      Loader.isModLoaded("abyssalcraft") && (entity instanceof com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityAbygolem || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonBoss || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonMinion || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadgolem || entity instanceof com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid)) || (
      
      Loader.isModLoaded("twilightforest") && (entity instanceof twilightforest.entity.boss.EntityTFLich || entity instanceof twilightforest.entity.boss.EntityTFMinoshroom || entity instanceof twilightforest.entity.boss.EntityTFSnowQueen || entity instanceof twilightforest.entity.boss.EntityTFIceCrystal || entity instanceof twilightforest.entity.EntityTFSkeletonDruid || entity instanceof twilightforest.entity.EntityTFSnowGuardian || entity instanceof twilightforest.entity.EntityTFIceExploder || entity instanceof twilightforest.entity.EntityTFIceShooter || entity instanceof twilightforest.entity.EntityTFTowerGolem || entity instanceof twilightforest.entity.EntityTFMazeSlime)) || (
      
      Loader.isModLoaded("scp") && (entity instanceof alexiy.secure.contain.protect.entity.EntityAllosaurus || entity instanceof alexiy.secure.contain.protect.entity.EntityHeavyGolem || entity instanceof alexiy.secure.contain.protect.entity.EntitySculpture || entity instanceof alexiy.secure.contain.protect.entity.redpool.Entity_354_15 || entity instanceof alexiy.secure.contain.protect.entity.redpool.Entity_354_16)) || (
      
      Loader.isModLoaded("mutantbeasts") && (entity instanceof chumbanotz.mutantbeasts.entity.mutant.MutantSkeletonEntity || entity instanceof chumbanotz.mutantbeasts.entity.mutant.MutantSnowGolemEntity)) || (
      
      Loader.isModLoaded("iceandfire") && entity instanceof EntityLivingBase && 
      EntityGorgon.isStoneMob((EntityLivingBase)entity)) || (
      Loader.isModLoaded("draconicevolution") && entity instanceof com.brandon3055.draconicevolution.entity.EntityGuardianCrystal));
  }
  
  public static boolean isMetalLikeMob(Entity entity) {
    return (entity instanceof net.minecraft.entity.monster.EntityBlaze || entity instanceof net.minecraft.entity.monster.EntityIronGolem || (entity instanceof EntityTameBase && ((EntityTameBase)entity).isMetalLike()) || (Loader.isModLoaded("abyssalcraft") && (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadguard || entity instanceof com.shinoow.abyssalcraft.common.entity.EntitySacthoth)) || (
      
      Loader.isModLoaded("scp") && (entity instanceof alexiy.secure.contain.protect.entity.redpool.Entity_354_3 || entity instanceof alexiy.secure.contain.protect.entity.redpool.Entity_354_18 || entity instanceof alexiy.secure.contain.protect.entity.EntityAutonomousArmor)) || (
      
      Loader.isModLoaded("twilightforest") && (entity instanceof twilightforest.entity.EntityTFArmoredGiant || entity instanceof twilightforest.entity.EntityTFBlockGoblin || entity instanceof twilightforest.entity.EntityTFHelmetCrab || entity instanceof twilightforest.entity.EntityTFGoblinKnightLower || entity instanceof twilightforest.entity.EntityTFGoblinKnightUpper)));
  }
  
  public static boolean canBeTurned(Entity entity) {
    return (entity instanceof net.minecraft.entity.player.EntityPlayer || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid || entity instanceof net.minecraft.entity.monster.EntityZombie || entity instanceof net.minecraft.entity.passive.EntitySquid || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton || entity instanceof net.minecraft.entity.monster.AbstractSkeleton || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper || entity instanceof net.minecraft.entity.monster.EntityCreeper || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager || entity instanceof net.minecraft.entity.passive.EntityVillager || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumSquid || (
      
      Loader.isModLoaded("abyssalcraft") && (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadling || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid)));
  }
  
  public static boolean doesntHaveTimeToBleed(Entity entity) {
    return ((entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70662_br()) || entity instanceof net.minecraft.entity.monster.EntityBlaze || entity instanceof net.minecraft.entity.monster.EntitySlime || entity instanceof net.minecraft.entity.monster.EntityGolem || (entity instanceof EntityTameBase && ((EntityTameBase)entity)
      
      .isNotALivingThing()) || (
      Loader.isModLoaded("abyssalcraft") && entity instanceof EntityLiving && ((EntityLiving)entity).func_70668_bt() == AbyssalCraftAPI.SHADOW));
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
  
  public static void debug(String string) {
    debug(string, true);
  }
  
  public static void debug(String string, boolean onlyDebug) {
    if (onlyDebug)
      if (EngenderConfig.debugMode) {
        logger.debug(string);
      } else {
        logger.debug(string);
      }  
  }
  
  public static void warn(String string) {
    warn(string, true);
  }
  
  public static void warn(String string, boolean onlyDebug) {
    if (onlyDebug)
      if (EngenderConfig.debugMode) {
        logger.warn(string);
      } else {
        logger.warn(string);
      }  
  }
}
