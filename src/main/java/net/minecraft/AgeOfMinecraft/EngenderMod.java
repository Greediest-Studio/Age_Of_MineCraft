package net.minecraft.AgeOfMinecraft;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.AgeOfMinecraft.ageofminecraft.Tags;
import net.minecraft.AgeOfMinecraft.commands.CommandKillEngenderMobs;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.events.EngenderGeneralEvent;
import net.minecraft.AgeOfMinecraft.util.EntityCompat;
import net.minecraft.AgeOfMinecraft.util.PrivateHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraft.server.MinecraftServer;
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

  private boolean selfTestRunning;
  private boolean selfTestStopWhenDone;
  private int selfTestTicks;
  private final List<SelfTestEntity> selfTestEntities = new ArrayList<>();

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
    MinecraftForge.EVENT_BUS.register(EngenderGeneralEvent.INSTANCE);
    if (EngenderGeneralEvent.musicTicker != null)
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

  @EventHandler
  public void onServerStarted(FMLServerStartedEvent e) {
    if (!"true".equalsIgnoreCase(System.getenv("AOM_SELF_TEST")))
      return;
    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
    if (server == null) {
      logger.error("AOM self-test skipped because MinecraftServer is unavailable");
      return;
    }
    this.selfTestStopWhenDone = "true".equalsIgnoreCase(System.getenv("AOM_SELF_TEST_STOP"));
    this.selfTestTicks = 0;
    this.selfTestEntities.clear();
    List<ResourceLocation> entityIds = collectSelfTestEntityIds();
    logger.info("AOM self-test starting with {} entity id(s), scope='{}'", Integer.valueOf(entityIds.size()), selfTestScope());
    runSelfTestCommands(server, entityIds);
    runSelfTestDirectSpawns(server, entityIds);
    runSelfTestSpawnEggSpawns(server, entityIds);
    this.selfTestRunning = true;
  }

  @SubscribeEvent
  public void onServerTick(TickEvent.ServerTickEvent event) {
    if (!this.selfTestRunning || event.phase != TickEvent.Phase.END)
      return;
    this.selfTestTicks++;
    if (this.selfTestTicks < selfTestTickLimit())
      return;
    int alive = 0;
    List<String> aliveLiving = new ArrayList<>();
    List<String> missingLiving = new ArrayList<>();
    for (SelfTestEntity selfTestEntity : this.selfTestEntities) {
      boolean isAlive = selfTestEntity.entity != null && selfTestEntity.entity.isEntityAlive();
      if (isAlive)
        alive++;
      if (selfTestEntity.entity instanceof EntityLivingBase) {
        if (isAlive)
          aliveLiving.add(selfTestEntity.id.toString());
        else
          missingLiving.add(selfTestEntity.id.toString());
      }
    }
    logger.info("AOM self-test tick observation completed after {} tick(s): {}/{} directly spawned entities still alive",
        Integer.valueOf(this.selfTestTicks), Integer.valueOf(alive), Integer.valueOf(this.selfTestEntities.size()));
    logger.info("AOM self-test living entities still alive after observation: {}", aliveLiving);
    logger.info("AOM self-test living entities missing after observation: {}", missingLiving);
    this.selfTestRunning = false;
    if (this.selfTestStopWhenDone) {
      MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
      if (server != null)
        server.initiateShutdown();
    }
  }

  private void runSelfTestCommands(MinecraftServer server, List<ResourceLocation> entityIds) {
    WorldServer world = server.getWorld(0);
    BlockPos origin = world == null ? new BlockPos(0, 80, 0) : world.getSpawnPoint();
    int index = 0;
    for (ResourceLocation id : entityIds) {
      BlockPos pos = selfTestPosition(world, origin, index, 0);
      runSelfTestCommand(server, "summon " + id.toString() + " " + pos.getX() + " " + pos.getY() + " " + pos.getZ());
      index++;
    }
  }

  private void runSelfTestCommand(MinecraftServer server, String command) {
    try {
      int result = server.getCommandManager().executeCommand(server, command);
      logger.info("AOM self-test command '{}' completed with result {}", command, Integer.valueOf(result));
    } catch (Throwable throwable) {
      logger.error("AOM self-test command '{}' failed", command, throwable);
    }
  }

  private void runSelfTestDirectSpawns(MinecraftServer server, List<ResourceLocation> entityIds) {
    WorldServer world = server.getWorld(0);
    if (world == null) {
      logger.error("AOM self-test direct spawn skipped because dimension 0 is unavailable");
      return;
    }
    BlockPos origin = world.getSpawnPoint();
    int index = 0;
    for (ResourceLocation id : entityIds) {
      BlockPos pos = selfTestPosition(world, origin, index, 288);
      try {
        Entity entity = EntityList.createEntityByIDFromName(id, world);
        if (entity == null) {
          logger.error("AOM self-test could not create entity '{}'", id);
        } else {
          entity.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
          if (entity instanceof EntityLiving) {
            EntityLiving living = (EntityLiving)entity;
            living.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), null);
          }
          boolean spawned = world.spawnEntity(entity);
          if (spawned)
            this.selfTestEntities.add(new SelfTestEntity(id, entity));
          logger.info("AOM self-test direct spawn '{}' completed with result {}", id, Boolean.valueOf(spawned));
        }
      } catch (Throwable throwable) {
        logger.error("AOM self-test direct spawn '{}' failed", id, throwable);
      }
      index++;
    }
  }

  private void runSelfTestSpawnEggSpawns(MinecraftServer server, List<ResourceLocation> entityIds) {
    WorldServer world = server.getWorld(0);
    if (world == null) {
      logger.error("AOM self-test spawn egg path skipped because dimension 0 is unavailable");
      return;
    }
    BlockPos origin = world.getSpawnPoint();
    int index = 0;
    for (ResourceLocation id : entityIds) {
      BlockPos pos = selfTestPosition(world, origin, index, 576);
      try {
        Entity entity = ItemMonsterPlacer.spawnCreature(world, id, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
        if (entity instanceof EntityLivingBase)
          this.selfTestEntities.add(new SelfTestEntity(id, entity));
        logger.info("AOM self-test spawn egg path '{}' completed with result {}", id, Boolean.valueOf(entity != null));
      } catch (Throwable throwable) {
        logger.error("AOM self-test spawn egg path '{}' failed", id, throwable);
      }
      index++;
    }
  }

  private BlockPos selfTestPosition(WorldServer world, BlockPos origin, int index, int xOffset) {
    int x = origin.getX() + xOffset + (index % 12) * 24;
    int z = origin.getZ() + (index / 12) * 24;
    if (world == null)
      return new BlockPos(x, Math.max(80, origin.getY() + 1), z);
    BlockPos surface = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));
    if (surface.getY() <= 0)
      return new BlockPos(x, Math.max(80, origin.getY() + 1), z);
    return surface.up();
  }

  private List<ResourceLocation> collectSelfTestEntityIds() {
    String scope = selfTestScope();
    List<ResourceLocation> ids = new ArrayList<>();
    if (!"all".equals(scope)) {
      addSelfTestId(ids, "ageofminecraft", "witherstormbosshelpful");
      addSelfTestId(ids, "ageofchaos", "chaosguardianhelpful");
      return ids;
    }
    for (ResourceLocation id : EntityList.getEntityNameList()) {
      if (isEngenderEntityNamespace(id.getNamespace()))
        ids.add(id);
    }
    Collections.sort(ids, new Comparator<ResourceLocation>() {
      public int compare(ResourceLocation first, ResourceLocation second) {
        return first.toString().compareTo(second.toString());
      }
    });
    return ids;
  }

  private void addSelfTestId(List<ResourceLocation> ids, String namespace, String path) {
    ResourceLocation id = new ResourceLocation(namespace, path);
    if (EntityList.isRegistered(id))
      ids.add(id);
    else
      logger.error("AOM self-test expected entity id '{}' is not registered", id);
  }

  private boolean isEngenderEntityNamespace(String namespace) {
    return "ageofminecraft".equals(namespace) || "ageofabyssalcraft".equals(namespace) ||
        "ageofchaos".equals(namespace) || "ageofmutants".equals(namespace);
  }

  private String selfTestScope() {
    String scope = System.getenv("AOM_SELF_TEST_SCOPE");
    return scope == null ? "bosses" : scope.toLowerCase();
  }

  private int selfTestTickLimit() {
    String value = System.getenv("AOM_SELF_TEST_TICKS");
    if (value == null)
      return 120;
    try {
      return Math.max(1, Integer.parseInt(value));
    } catch (NumberFormatException e) {
      logger.error("Invalid AOM_SELF_TEST_TICKS='{}', falling back to 120", value);
      return 120;
    }
  }

  private static class SelfTestEntity {
    private final ResourceLocation id;
    private final Entity entity;

    private SelfTestEntity(ResourceLocation id, Entity entity) {
      this.id = id;
      this.entity = entity;
    }
  }
  
  public static boolean sensorsShowJzahars(World world) {
    if (Loader.isModLoaded("abyssalcraft")) {
      List<Entity> list = EntityCompat.loadedEntityList(world);
      if (!list.isEmpty())
          for (Entity entity : list) {
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
