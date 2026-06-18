package net.minecraft.AgeOfMinecraft.registry;

import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.AbyssalcraftEngenderAddon;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbygolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalPortal;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityBlackHole;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothFist;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumChargeOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumSquid;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDepthsGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSlugOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadedChargeOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadgolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadguard;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGatekeeperMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGreaterDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityImplosion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityInkProjectileOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserDreadbeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserShoggoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityMiniBlackHole;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholChargeOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityRemnant;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowBeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowCreature;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowMonster;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySkeletonGoliath;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySquads;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.DraconicEvolutionAddon;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityDraconicPortal;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityGuardianProjectile;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.MutantBeastsAddon;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityEndersoulFragment;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantCreeper;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeleton;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeletonArrow;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSnowGolem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntitySpiderPig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityThrowingBlock;
import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntityBoneAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntityGasterBlaster;
import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans;
import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityManaOrb;
import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortal;
import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortalLightning;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityBat;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityMooshroom;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityParrot;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityRabbit;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityLlama;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySilverfish;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySnowman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityWolf;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityMagmaCube;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityPolarBear;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityPrisonSlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityTippedArrowOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCaveSpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCreeder;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIceSpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIcyEnderCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPrisonSpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityAbomniableSnowman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityDisintigrationRay;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityElderGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEversource;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityFrostRay;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGhasther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIceGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIllusioner;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityInvisibleFangsProjectile;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityMagicMissile;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityMagmaGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityPrisonGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntitySnowballHarmful;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormSkull;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer;
import net.minecraft.AgeOfMinecraft.entity.untame.tier5.EntityDarkness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class EEntity {
  private static int id = 0;
  
  private static int idaby = 0;
  
  private static int iddrac = 0;
  
  private static int idmut = 0;
  
  public static void registerEntity() {
    createEntityWithEgg((Class)EntityBat.class, "BatHelpful", 4996656, 986895, 64);
    createEntityWithEgg((Class)EntityChicken.class, "ChickenHelpful", 10592673, 16711680, 64);
    createEntityWithEgg((Class)EntityCow.class, "CowHelpful", 4470310, 10592673, 64);
    createEntityWithEgg((Class)EntityMooshroom.class, "MushroomcowHelpful", 10489616, 12040119, 64);
    createEntityWithEgg((Class)EntityParrot.class, "ParrotHelpful", 894731, 16711680, 64);
    createEntityWithEgg((Class)EntityPig.class, "PigHelpful", 15771042, 14377823, 64);
    createEntityWithEgg((Class)EntityRabbit.class, "RabbitHelpful", 10051392, 7555121, 64);
    createEntityWithEgg((Class)EntitySheep.class, "SheepHelpful", 15198183, 16758197, 64);
    createEntityWithEgg((Class)EntityOcelot.class, "OzelotHelpful", 15720061, 5653556, 64);
    createEntityWithEgg((Class)EntitySquid.class, "SquidHelpful", 2243405, 7375001, 64);
    createEntityWithEgg((Class)EntityLlama.class, "LlamaHelpful", 12623485, 10051392, 64);
    createEntityWithEgg((Class)EntityVillager.class, "VillagerHelpful", 5651507, 12422002, 64);
    createEntityWithEgg((Class)EntitySnowman.class, "SnowmanHelpful", 16382457, 14543594, 64);
    createEntityWithEgg((Class)EntitySilverfish.class, "SilverfishHelpful", 7237230, 3158064, 64);
    createEntityWithEgg((Class)EntityEndermite.class, "EndermiteHelpful", 1447446, 7237230, 64);
    createEntityWithEgg((Class)EntityWolf.class, "WolfHelpful", 14144467, 13545366, 64);
    createEntityWithEgg((Class)EntitySpider.class, "SpiderHelpful", 3419431, 11013646, 64);
    createEntityWithEgg((Class)EntityZombie.class, "ZombieHelpful", 44975, 7969893, 64);
    createEntityWithEgg((Class)EntitySkeleton.class, "SkeletonHelpful", 12698049, 4802889, 64);
    createEntityWithEgg((Class)EntityCreeper.class, "CreeperHelpful", 894731, 0, 64);
    createEntityWithEgg((Class)EntityPolarBear.class, "PolarBearHelpful", 15921906, 9803152, 64);
    createEntityWithEgg((Class)EntitySlime.class, "SlimeHelpful", 5349438, 8306542, 64);
    createEntityWithEgg((Class)EntityMagmaCube.class, "LavaSlimeHelpful", 3407872, 16579584, 64);
    createEntityWithEgg((Class)EntityPrisonSlime.class, "PrisonSlimeHelpful", 11874332, 0, 64);
    createEntityWithEgg((Class)EntityVex.class, "VexHelpful", 8032420, 15265265, 64);
    createEntityWithEgg((Class)EntityBlaze.class, "BlazeHelpful", 16167425, 16775294, 64);
    createEntityWithEgg((Class)EntityCaveSpider.class, "CaveSpiderHelpful", 803406, 11013646, 64);
    createEntityWithEgg((Class)EntityCreeder.class, "CreederHelpful", 4928820, 2892064, 64);
    createEntityWithEgg((Class)EntityEnderman.class, "EndermanHelpful", 1447446, 0, 64);
    createEntityWithEgg((Class)EntityGhast.class, "GhastHelpful", 16382457, 12369084, 64);
    createEntityWithEgg((Class)EntityGuardian.class, "GuardianHelpful", 5931634, 15826224, 64);
    createEntityWithEgg((Class)EntityIceSpider.class, "IceSpiderHelpful", 1433817, 57591, 64);
    createEntityWithEgg((Class)EntityIcyEnderCreeper.class, "IcyEnderCreeperHelpful", 16382457, 0, 64);
    createEntityWithEgg((Class)EntityPigZombie.class, "PigZombieHelpful", 15373203, 5009705, 64);
    createEntityWithEgg((Class)EntityPrisonSpider.class, "PrisonSpiderHelpful", 3419431, 11013646, 64);
    createEntityWithEgg((Class)EntityShulker.class, "ShulkerHelpful", 9725844, 5060690, 64);
    createEntityWithEgg((Class)EntityVindicator.class, "VindicatorHelpful", 9804699, 2580065, 64);
    createEntityWithEgg((Class)EntityWitch.class, "WitchHelpful", 3407872, 5349438, 64);
    createEntityWithEgg((Class)EntityAbomniableSnowman.class, "AbomniableSnowmanHelpful", 16382457, 14543594, 256);
    createEntityWithEgg((Class)EntityElderGuardian.class, "ElderGuardianHelpful", 13552826, 7632531, 256);
    createEntityWithEgg((Class)EntityEnderDragon.class, "EnderDragonHelpful", 1513239, 13369594, 2048);
    createEntityWithEgg((Class)EntityEversource.class, "EversourceHelpful", 10592673, 10489616, 256);
    createEntityWithEgg((Class)EntityEvoker.class, "EvokerHelpful", 9804699, 1973274, 256);
    createEntityWithEgg((Class)EntityGhasther.class, "GhastherHelpful", 3285791, 2430226, 256);
    createEntityWithEgg((Class)EntityGiant.class, "GiantHelpful", 44975, 7969893, 256);
    createEntityWithEgg((Class)EntityIceGolem.class, "IceGolemHelpful", 14144467, 14543594, 256);
    createEntityWithEgg((Class)EntityIllusioner.class, "IllusionerHelpful", 1267859, 9804699, 256);
    createEntityWithEgg((Class)EntityIronGolem.class, "VillagerGolemHelpful", 14144467, 14377823, 256);
    createEntityWithEgg((Class)EntityMagmaGolem.class, "MagmaGolemHelpful", 3407872, 16167425, 256);
    createEntityWithEgg((Class)EntityPrisonGolem.class, "PrisonGolemHelpful", 2697256, 394758, 256);
    createEntityWithEgg((Class)EntityWither.class, "WitherBossHelpful", 1710618, 5526612, 2048);
    createEntityWithEgg((Class)EntityPortal.class, "Portal", 5723991, 4602464, 2048);
    createEntityWithEgg((Class)EntityCommandBlockWither.class, "WitherBossCommandBlockHelpful", 1710618, 5526612, 2048);
    createEntityWithEgg((Class)EntityWitherStorm.class, "WitherStormBossHelpful", 986135, 1838892, 2048);
    createEntityWithEgg((Class)EntityDarkness.class, "untame_darkness", 1052688, 4198416, 2048);
    createEntityWithEgg((Class)EntitySans.class, "sans", 16382457, 1052688, 2048);
    createEntity((Class)EntityWitherStormHead.class, "WitherStormBossHeadHelpful", 2048);
    createEntity((Class)EntityWitherStormTentacle.class, "WitherStormBossTentacleHelpful", 2048);
    createEntity((Class)EntityWitherStormTentacleDevourer.class, "WitherStormBossTentacleDevourerHelpful", 2048);
    createEntity((Class)EntityMagicMissile.class, "MagicMissile", 256);
    createEntity((Class)EntityWitherStormSkull.class, "WitherStormSkull", 2048);
    createEntity((Class)EntityPortalLightning.class, "PortalLightning", 256);
    createEntity((Class)EntityDisintigrationRay.class, "DisintigrationRay", 64);
    createEntity((Class)EntityFrostRay.class, "FrostRay", 64);
    createEntity((Class)EntitySnowballHarmful.class, "SnowballHarmful", 256);
    createEntity((Class)EntityAreaEffectCloudOther.class, "AreaEffectCloudOther", 64);
    createEntity((Class)EntityTippedArrowOther.class, "TippedArrowOther", 256);
    createEntity((Class)EntityInvisibleFangsProjectile.class, "InvisibleFangsProjectile", 512);
    createEntity((Class)EntityManaOrb.class, "ManaOrb", 64);
    createEntity((Class)EntityBoneAttack.class, "BoneAttack", 256);
    createEntity((Class)EntityGasterBlaster.class, "GasterBlaster", 512);
    EntitySpawnPlacementRegistry.setPlacementType(EntitySquid.class, EntityLiving.SpawnPlacementType.IN_WATER);
    EntitySpawnPlacementRegistry.setPlacementType(EntityGuardian.class, EntityLiving.SpawnPlacementType.IN_WATER);
    EntityRegistry.addSpawn(net.minecraft.entity.monster.EntityEndermite.class, 20, 1, 4, EnumCreatureType.MONSTER, new Biome[] { Biomes.SKY });
    EntityRegistry.addSpawn(net.minecraft.entity.monster.EntityShulker.class, 1, 1, 1, EnumCreatureType.MONSTER, new Biome[] { Biomes.SKY });
    EntityRegistry.addSpawn(net.minecraft.entity.monster.EntityBlaze.class, 20, 1, 4, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
    EntityRegistry.addSpawn(EntityWitherSkeleton.class, 10, 1, 4, EnumCreatureType.MONSTER, new Biome[] { Biomes.HELL });
  }
  
  public static void registerAbyEntity() {
    if (Loader.isModLoaded("abyssalcraft")) {
      createEntityWithEggForAbyssalcraft((Class)EntityAbygolem.class, "AbygolemHelpful", 9044198, 6357153, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityDreadgolem.class, "DreadgolemHelpful", 31850496, 13369344, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityCoraliumSquid.class, "CoraliumSquidHelpful", 85571, 1347454, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityAbyssalZombie.class, "AbyssalZombieHelpful", 3582080, 75302, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityDepthsGhoul.class, "DepthsGhoulHelpful", 3582080, 75302, 64);
      createEntityWithEggForAbyssalcraft((Class)EntitySkeletonGoliath.class, "SkeletonGoliathHelpful", 14079689, 13027245, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityDragonMinion.class, "DragonMinionHelpful", 4404276, 3425092, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityDragonBoss.class, "DragonBossHelpful", 4679527, 7768115, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityDreadSpawn.class, "DreadSpawnHelpful", 15073280, 13369344, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityChagarothSpawn.class, "ChagarothSpawnHelpful", 15073280, 13369344, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityDreadling.class, "DreadlingHelpful", 15073280, 13369344, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityChagarothFist.class, "ChagarothFistHelpful", 15073280, 13369344, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityGreaterDreadSpawn.class, "GreaterDreadSpawnHelpful", 15073280, 13369344, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityDreadguard.class, "DreadguardHelpful", 15073280, 13369344, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityLesserDreadbeast.class, "LesserDreadbeastHelpful", 15073280, 13369344, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityChagaroth.class, "ChagarothHelpful", 15073280, 13369344, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityOmotholGhoul.class, "OmotholGhoulHelpful", 1257779, 3416354, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityLesserShoggoth.class, "LesserShoggothHelpful", 1257779, 3416354, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityShadowCreature.class, "ShadowCreatureHelpful", 0, 16777215, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityShadowMonster.class, "ShadowMonsterHelpful", 0, 16777215, 64);
      createEntityWithEggForAbyssalcraft((Class)EntityShadowBeast.class, "ShadowBeastHelpful", 0, 16777215, 256);
      createEntityWithEggForAbyssalcraft((Class)EntitySacthoth.class, "SacthothHelpful", 0, 16777215, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityRemnant.class, "RemnantHelpful", 1257779, 3416354, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityGatekeeperMinion.class, "GatekeeperMinionHelpful", 1257779, 3416354, 256);
      createEntityWithEggForAbyssalcraft((Class)EntityJzahar.class, "JzaharHelpful", 1257779, 3416354, 2048);
      createEntityForAbyssalcraft((Class)EntitySquads.class, "Squads", 128);
      createEntityForAbyssalcraft((Class)EntityDreadSlugOther.class, "Slugs", 128);
      createEntityForAbyssalcraft((Class)EntityInkProjectileOther.class, "Inks", 128);
      createEntityForAbyssalcraft((Class)EntityPEGunPellet.class, "PEBolt", 128);
      createEntityForAbyssalcraft((Class)EntityCoraliumChargeOther.class, "CoraliumCharge", 256);
      createEntityForAbyssalcraft((Class)EntityDreadedChargeOther.class, "DreadedCharge", 256);
      createEntityForAbyssalcraft((Class)EntityOmotholChargeOther.class, "OmotholCharge", 256);
      createEntityForAbyssalcraft((Class)EntityMiniBlackHole.class, "MiniBlackHole", 256);
      createEntityForAbyssalcraft((Class)EntityBlackHole.class, "BlackHole", 2048);
      createEntityForAbyssalcraft((Class)EntityImplosion.class, "Implosion", 2048);
      createEntityWithEggForAbyssalcraft((Class)EntityAbyssalPortal.class, "AbyssalPortal", 10658709, 70162, 2048);
      EntitySpawnPlacementRegistry.setPlacementType(EntityCoraliumSquid.class, EntityLiving.SpawnPlacementType.IN_WATER);
    } 
  }
  
  public static void registerDracEvoEntity() {
    if (Loader.isModLoaded("draconicevolution")) {
      createEntityWithEggForDraconicEvolution((Class)EntityChaosGuardian.class, "ChaosGuardianHelpful", 13585163, 986909, 2048);
      createEntityForDraconicEvolution((Class)EntityGuardianProjectile.class, "GuardianProjectileOther", 2048);
      createEntityWithEggForDraconicEvolution((Class)EntityDraconicPortal.class, "DraconicPortal", 13585163, 986909, 2048);
    } 
  }
  
  public static void registerMutantEntity() {
    if (Loader.isModLoaded("mutantbeasts")) {
      createEntityWithEggForMutantCreatures((Class)EntitySpiderPig.class, "SpiderPigHelpful", 3419431, 15771042, 128);
      createEntityWithEggForMutantCreatures((Class)EntityMutantSnowGolem.class, "MutantSnowGolemHelpful", 15073279, 16753434, 256);
      createEntityWithEggForMutantCreatures((Class)EntityMutantZombie.class, "MutantZombieHelpful", 7969893, 44975, 256);
      createEntityWithEggForMutantCreatures((Class)EntityMutantCreeper.class, "MutantCreeperHelpful", 5349438, 11013646, 256);
      createEntityWithEggForMutantCreatures((Class)EntityMutantSkeleton.class, "MutantSkeletonHelpful", 12698049, 6310217, 256);
      createEntityWithEggForMutantCreatures((Class)EntityMutantEnderman.class, "MutantEndermanHelpful", 1447446, 8860812, 256);
      createEntityForMutantCreatures((Class)EntityMutantSkeletonArrow.class, "MutantSkeletonArrowOther", 256);
      createEntityForMutantCreatures((Class)EntityThrowingBlock.class, "ThrowingBlockOther", 256);
      createEntityForMutantCreatures((Class)EntityEndersoulFragment.class, "EndersoulFragmentOther", 256);
    } 
  }
  
  public static void createEntityWithEgg(Class<? extends Entity> entityClass, String entityName, int primary, int secondary, int updateDistance) {
    createEntity(entityClass, entityName, updateDistance);
    EntityRegistry.registerEgg(new ResourceLocation("ageofminecraft", entityName), primary, secondary);
  }
  
  public static void createEntity(Class<? extends Entity> entityClass, String entityName, int updateDistance) {
    EntityRegistry.registerModEntity(new ResourceLocation("ageofminecraft", entityName), entityClass, entityName, ++id, EngenderMod.instance, updateDistance, 1, true);
  }
  
  public static void createEntityWithEggForAbyssalcraft(Class<? extends Entity> entityClass, String entityName, int primary, int secondary, int updateDistance) {
    createEntityForAbyssalcraft(entityClass, entityName, updateDistance);
    EntityRegistry.registerEgg(new ResourceLocation("ageofabyssalcraft", entityName), primary, secondary);
  }
  
  public static void createEntityForAbyssalcraft(Class<? extends Entity> entityClass, String entityName, int updateDistance) {
    EntityRegistry.registerModEntity(new ResourceLocation("ageofabyssalcraft", entityName), entityClass, entityName, ++idaby, AbyssalcraftEngenderAddon.instance, updateDistance, 1, true);
  }
  
  public static void createEntityForDraconicEvolution(Class<? extends Entity> entityClass, String entityName, int updateDistance) {
    EntityRegistry.registerModEntity(new ResourceLocation("ageofchaos", entityName), entityClass, entityName, ++iddrac, DraconicEvolutionAddon.instance, updateDistance, 1, true);
  }
  
  public static void createEntityWithEggForDraconicEvolution(Class<? extends Entity> entityClass, String entityName, int primary, int secondary, int updateDistance) {
    createEntityForDraconicEvolution(entityClass, entityName, updateDistance);
    EntityRegistry.registerEgg(new ResourceLocation("ageofchaos", entityName), primary, secondary);
  }
  
  public static void createEntityForMutantCreatures(Class<? extends Entity> entityClass, String entityName, int updateDistance) {
    EntityRegistry.registerModEntity(new ResourceLocation("ageofmutants", entityName), entityClass, entityName, ++idmut, MutantBeastsAddon.instance, updateDistance, 1, true);
  }
  
  public static void createEntityWithEggForMutantCreatures(Class<? extends Entity> entityClass, String entityName, int primary, int secondary, int updateDistance) {
    createEntityForMutantCreatures(entityClass, entityName, updateDistance);
    EntityRegistry.registerEgg(new ResourceLocation("ageofmutants", entityName), primary, secondary);
  }
}
