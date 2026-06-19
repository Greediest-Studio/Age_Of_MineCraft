package net.minecraft.AgeOfMinecraft.registry;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemAbygolemItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemAbyssalPortalStaff;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemAbyssalZombieItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemAsorahItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemChagarothFistItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemChagarothItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemChagarothSpawnItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemCoraliumSquidItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemDepthsGhoulItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemDreadSpawnItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemDreadgolemItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemDreadguardItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemDreadlingItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemFusionAby;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemGatekeeperMinionItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemGreaterDreadSpawnItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemJzaharItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemLesserDreadbeastItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemLesserShoggothItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemOmotholGhoulItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemPEGun;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemRemnantItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemSacthothItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemShadowBeastItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemShadowCreatureItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemShadowMonsterItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemSkeletonGoliathItem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemSpectralDragonItem;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.ItemChaosGuardianItem;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.ItemDraconicPortalStaff;
import net.minecraft.AgeOfMinecraft.addons.draconicevolution.ItemFusionDrac;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items.ItemFusionMutant;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items.ItemMutantCreeperItem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items.ItemMutantEndermanItem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items.ItemMutantSkeletonItem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items.ItemMutantSnowGolemItem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items.ItemMutantZombieItem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.items.ItemSpiderPigItem;
import net.minecraft.AgeOfMinecraft.items.ItemAbomniableSnowmanItem;
import net.minecraft.AgeOfMinecraft.items.ItemBatItem;
import net.minecraft.AgeOfMinecraft.items.ItemBlazeItem;
import net.minecraft.AgeOfMinecraft.items.ItemCarrier;
import net.minecraft.AgeOfMinecraft.items.ItemCaveSpiderItem;
import net.minecraft.AgeOfMinecraft.items.ItemChickenItem;
import net.minecraft.AgeOfMinecraft.items.ItemChickenJockeyItem;
import net.minecraft.AgeOfMinecraft.items.ItemCommandingStaff;
import net.minecraft.AgeOfMinecraft.items.ItemConvertingStaff;
import net.minecraft.AgeOfMinecraft.items.ItemCowItem;
import net.minecraft.AgeOfMinecraft.items.ItemCreederItem;
import net.minecraft.AgeOfMinecraft.items.ItemCreeperItem;
import net.minecraft.AgeOfMinecraft.items.ItemDragonsHorn;
import net.minecraft.AgeOfMinecraft.items.ItemElderGuardianItem;
import net.minecraft.AgeOfMinecraft.items.ItemEnderDragonItem;
import net.minecraft.AgeOfMinecraft.items.ItemEndermanItem;
import net.minecraft.AgeOfMinecraft.items.ItemEndermiteItem;
import net.minecraft.AgeOfMinecraft.items.ItemEngenderStatChecker;
import net.minecraft.AgeOfMinecraft.items.ItemEversourceItem;
import net.minecraft.AgeOfMinecraft.items.ItemEvokerItem;
import net.minecraft.AgeOfMinecraft.items.ItemFusion;
import net.minecraft.AgeOfMinecraft.items.ItemFusionMCSM;
import net.minecraft.AgeOfMinecraft.items.ItemFusionVanilla;
import net.minecraft.AgeOfMinecraft.items.ItemGhastItem;
import net.minecraft.AgeOfMinecraft.items.ItemGhastherItem;
import net.minecraft.AgeOfMinecraft.items.ItemGiantItem;
import net.minecraft.AgeOfMinecraft.items.ItemGuardianItem;
import net.minecraft.AgeOfMinecraft.items.ItemHeroMaker;
import net.minecraft.AgeOfMinecraft.items.ItemHuskItem;
import net.minecraft.AgeOfMinecraft.items.ItemIceGolemItem;
import net.minecraft.AgeOfMinecraft.items.ItemIceSpiderItem;
import net.minecraft.AgeOfMinecraft.items.ItemIcyEnderCreeperItem;
import net.minecraft.AgeOfMinecraft.items.ItemIllusionerItem;
import net.minecraft.AgeOfMinecraft.items.ItemIronGolemItem;
import net.minecraft.AgeOfMinecraft.items.ItemKillerBunnyItem;
import net.minecraft.AgeOfMinecraft.items.ItemLastChance;
import net.minecraft.AgeOfMinecraft.items.ItemLearningBook;
import net.minecraft.AgeOfMinecraft.items.ItemLlamaItem;
import net.minecraft.AgeOfMinecraft.items.ItemMagmaCubeItem;
import net.minecraft.AgeOfMinecraft.items.ItemMagmaGolemItem;
import net.minecraft.AgeOfMinecraft.items.ItemManaCollector;
import net.minecraft.AgeOfMinecraft.items.ItemMooshroomItem;
import net.minecraft.AgeOfMinecraft.items.ItemMoralHorn;
import net.minecraft.AgeOfMinecraft.items.ItemOcelotItem;
import net.minecraft.AgeOfMinecraft.items.ItemParrotItem;
import net.minecraft.AgeOfMinecraft.items.ItemPigItem;
import net.minecraft.AgeOfMinecraft.items.ItemPigZombieItem;
import net.minecraft.AgeOfMinecraft.items.ItemPolarBearItem;
import net.minecraft.AgeOfMinecraft.items.ItemPortalStaff;
import net.minecraft.AgeOfMinecraft.items.ItemPrisonGolemItem;
import net.minecraft.AgeOfMinecraft.items.ItemPrisonSlimeItem;
import net.minecraft.AgeOfMinecraft.items.ItemPrisonSpiderItem;
import net.minecraft.AgeOfMinecraft.items.ItemPrisonZombieItem;
import net.minecraft.AgeOfMinecraft.items.ItemRabbitItem;
import net.minecraft.AgeOfMinecraft.items.ItemSheepItem;
import net.minecraft.AgeOfMinecraft.items.ItemShulkerItem;
import net.minecraft.AgeOfMinecraft.items.ItemSilverfishItem;
import net.minecraft.AgeOfMinecraft.items.ItemSkeletonItem;
import net.minecraft.AgeOfMinecraft.items.ItemSkeletonTrapItem;
import net.minecraft.AgeOfMinecraft.items.ItemSlimeItem;
import net.minecraft.AgeOfMinecraft.items.ItemSnowmanItem;
import net.minecraft.AgeOfMinecraft.items.ItemSpiderItem;
import net.minecraft.AgeOfMinecraft.items.ItemSpiderJockeyItem;
import net.minecraft.AgeOfMinecraft.items.ItemSquidItem;
import net.minecraft.AgeOfMinecraft.items.ItemStrayItem;
import net.minecraft.AgeOfMinecraft.items.ItemSummoningStaff;
import net.minecraft.AgeOfMinecraft.items.ItemTierItem;
import net.minecraft.AgeOfMinecraft.items.ItemTrainingStick;
import net.minecraft.AgeOfMinecraft.items.ItemVexItem;
import net.minecraft.AgeOfMinecraft.items.ItemVillagerItem;
import net.minecraft.AgeOfMinecraft.items.ItemVindicatorItem;
import net.minecraft.AgeOfMinecraft.items.ItemWeddingRing;
import net.minecraft.AgeOfMinecraft.items.ItemWitchItem;
import net.minecraft.AgeOfMinecraft.items.ItemWitherItem;
import net.minecraft.AgeOfMinecraft.items.ItemWitherSkeletonItem;
import net.minecraft.AgeOfMinecraft.items.ItemWitherStormItem;
import net.minecraft.AgeOfMinecraft.items.ItemWolfItem;
import net.minecraft.AgeOfMinecraft.items.ItemZombieItem;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class EItem {
  public static ItemManaCollector manaContainer;
  
  public static ItemManaCollector entropyContainer;
  
  public static ItemManaCollector artifact1;
  
  public static Item witheredNetherStar;
  
  public static Item statChecker;
  
  public static Item carrier;
  
  public static ItemWeddingRing weddingring;
  
  public static Item heromaker;
  
  public static Item lastchance;
  
  public static Item trainingstick;
  
  public static Item blowhorn;
  
  public static Item blowhorn2;
  
  public static Item convertingStaff;
  
  public static Item summoningStaff;
  
  public static Item commandingStaff;
  
  public static Item portalStaff;
  
  public static ItemLearningBook learningBookBasic;
  
  public static ItemLearningBook learningBookBasicCombat;
  
  public static ItemLearningBook learningBookBasicCooking;
  
  public static ItemLearningBook learningBookBasicExercise;
  
  public static ItemLearningBook learningBookBasicKnowledge;
  
  public static ItemLearningBook learningBookModern;
  
  public static ItemLearningBook learningBookModernCombat;
  
  public static ItemLearningBook learningBookModernBrute;
  
  public static ItemLearningBook learningBookModernCooking;
  
  public static ItemLearningBook learningBookModernEating;
  
  public static ItemLearningBook learningBookModernExercise;
  
  public static ItemLearningBook learningBookModernRunning;
  
  public static ItemLearningBook learningBookModernKnowledge;
  
  public static ItemLearningBook learningBookModernPacifist;
  
  public static ItemLearningBook learningBookAdvanced;
  
  public static ItemLearningBook learningBookAdvancedCombat;
  
  public static ItemLearningBook learningBookAdvancedBrute;
  
  public static ItemLearningBook learningBookAdvancedWarrior;
  
  public static ItemLearningBook learningBookAdvancedCooking;
  
  public static ItemLearningBook learningBookAdvancedEating;
  
  public static ItemLearningBook learningBookAdvancedDieting;
  
  public static ItemLearningBook learningBookAdvancedExercise;
  
  public static ItemLearningBook learningBookAdvancedRunning;
  
  public static ItemLearningBook learningBookAdvancedLifting;
  
  public static ItemLearningBook learningBookAdvancedKnowledge;
  
  public static ItemLearningBook learningBookAdvancedPacifist;
  
  public static ItemLearningBook learningBookAdvancedWisdom;
  
  public static ItemLearningBook learningBookComplex;
  
  public static ItemLearningBook learningBookComplexCombat;
  
  public static ItemLearningBook learningBookComplexBrute;
  
  public static ItemLearningBook learningBookComplexWarrior;
  
  public static ItemLearningBook learningBookComplexCooking;
  
  public static ItemLearningBook learningBookComplexEating;
  
  public static ItemLearningBook learningBookComplexDieting;
  
  public static ItemLearningBook learningBookComplexExercise;
  
  public static ItemLearningBook learningBookComplexRunning;
  
  public static ItemLearningBook learningBookComplexLifting;
  
  public static ItemLearningBook learningBookComplexKnowledge;
  
  public static ItemLearningBook learningBookComplexPacifist;
  
  public static ItemLearningBook learningBookComplexWisdom;
  
  public static ItemLearningBook learningBookMaster;
  
  public static ItemLearningBook learningBookMasterCombat;
  
  public static ItemLearningBook learningBookMasterBrute;
  
  public static ItemLearningBook learningBookMasterWarrior;
  
  public static ItemLearningBook learningBookMasterCooking;
  
  public static ItemLearningBook learningBookMasterEating;
  
  public static ItemLearningBook learningBookMasterDieting;
  
  public static ItemLearningBook learningBookMasterExercise;
  
  public static ItemLearningBook learningBookMasterRunning;
  
  public static ItemLearningBook learningBookMasterLifting;
  
  public static ItemLearningBook learningBookMasterKnowledge;
  
  public static ItemLearningBook learningBookMasterPacifist;
  
  public static ItemLearningBook learningBookMasterWisdom;
  
  public static ItemLearningBook learningBookArtifactStrength;
  
  public static ItemLearningBook learningBookArtifactStamina;
  
  public static ItemLearningBook learningBookArtifactSpeed;
  
  public static ItemLearningBook learningBookArtifactIntellegence;
  
  public static ItemLearningBook learningBookArtifact;
  
  public static ItemFusion fusionItemBat;
  
  public static ItemFusion fusionItemChicken;
  
  public static ItemFusion fusionItemCow;
  
  public static ItemFusion fusionItemMooshroom;
  
  public static ItemFusion fusionItemParrot;
  
  public static ItemFusion fusionItemPig;
  
  public static ItemFusion fusionItemRabbit;
  
  public static ItemFusion fusionItemSheep;
  
  public static ItemFusion fusionItemOzelot;
  
  public static ItemFusion fusionItemSquid;
  
  public static ItemFusion fusionItemVillager;
  
  public static ItemFusion fusionItemSnowman;
  
  public static ItemFusion fusionItemSilverfish;
  
  public static ItemFusion fusionItemEndermite;
  
  public static ItemFusion fusionItemWolf;
  
  public static ItemFusion fusionItemSpider;
  
  public static ItemFusion fusionItemZombie;
  
  public static ItemFusion fusionItemSkeleton;
  
  public static ItemFusion fusionItemCreeper;
  
  public static ItemFusion fusionItemSlime;
  
  public static ItemFusion fusionItemMagmaCube;
  
  public static ItemFusion fusionItemSpiderJockey;
  
  public static ItemFusion fusionItemChickenJockey;
  
  public static ItemFusion fusionItemBlaze;
  
  public static ItemFusion fusionItemEnderman;
  
  public static ItemFusion fusionItemCaveSpider;
  
  public static ItemFusion fusionItemPigZombie;
  
  public static ItemFusion fusionItemGuardian;
  
  public static ItemFusion fusionItemGhast;
  
  public static ItemFusion fusionItemWitch;
  
  public static ItemFusion fusionItemWitherSkeleton;
  
  public static ItemFusion fusionItemKillerRabbit;
  
  public static ItemFusion fusionItemElderGuardian;
  
  public static ItemFusion fusionItemGiant;
  
  public static ItemFusion fusionItemVillagerGolem;
  
  public static ItemFusion fusionItemEnderDragon;
  
  public static ItemFusion fusionItemWither;
  
  public static ItemFusion fusionItemShulker;
  
  public static ItemFusion fusionItemSkeletonTrap;
  
  public static ItemFusion fusionItemStray;
  
  public static ItemFusion fusionItemHusk;
  
  public static ItemFusion fusionItemPolarBear;
  
  public static ItemFusion fusionItemVex;
  
  public static ItemFusion fusionItemVindicator;
  
  public static ItemFusion fusionItemLlama;
  
  public static ItemFusion fusionItemEvoker;
  
  public static ItemFusion fusionItemEversource;
  
  public static ItemFusion fusionItemIceSpider;
  
  public static ItemFusion fusionItemCreeder;
  
  public static ItemFusion fusionItemIcyEnderCreeper;
  
  public static ItemFusion fusionItemIceGolem;
  
  public static ItemFusion fusionItemMagmaGolem;
  
  public static ItemFusion fusionItemPrisonSlime;
  
  public static ItemFusion fusionItemPrisonZombie;
  
  public static ItemFusion fusionItemPrisonSpider;
  
  public static ItemFusion fusionItemPrisonGolem;
  
  public static ItemFusion fusionItemGhasther;
  
  public static ItemFusion fusionItemAbomniableSnowman;
  
  public static ItemFusion fusionItemIllusioner;
  
  public static ItemFusion fusionItemWitherStorm;
  
  public static ItemTierItem batItem;
  
  public static ItemTierItem chickenItem;
  
  public static ItemTierItem cowItem;
  
  public static ItemTierItem mooshroomItem;
  
  public static ItemTierItem parrotItem;
  
  public static ItemTierItem pigItem;
  
  public static ItemTierItem rabbitItem;
  
  public static ItemTierItem sheepItem;
  
  public static ItemTierItem ozelotItem;
  
  public static ItemTierItem squidItem;
  
  public static ItemTierItem llamaItem;
  
  public static ItemTierItem villagerItem;
  
  public static ItemTierItem snowmanItem;
  
  public static ItemTierItem silverfishItem;
  
  public static ItemTierItem endermiteItem;
  
  public static ItemTierItem wolfItem;
  
  public static ItemTierItem spiderItem;
  
  public static ItemTierItem zombieItem;
  
  public static ItemTierItem skeletonItem;
  
  public static ItemTierItem creeperItem;
  
  public static ItemTierItem polarBearItem;
  
  public static ItemTierItem slimeItem;
  
  public static ItemTierItem magmacubeItem;
  
  public static ItemTierItem vexItem;
  
  public static ItemTierItem spiderjockeyItem;
  
  public static ItemTierItem chickenjockeyItem;
  
  public static ItemTierItem blazeItem;
  
  public static ItemTierItem endermanItem;
  
  public static ItemTierItem cavespiderItem;
  
  public static ItemTierItem pigzombieItem;
  
  public static ItemTierItem guardianItem;
  
  public static ItemTierItem ghastItem;
  
  public static ItemTierItem huskItem;
  
  public static ItemTierItem shulkerItem;
  
  public static ItemTierItem strayItem;
  
  public static ItemTierItem witchItem;
  
  public static ItemTierItem vindicatorItem;
  
  public static ItemTierItem witherskeletonItem;
  
  public static ItemTierItem killerrabbitItem;
  
  public static ItemTierItem elderguardianItem;
  
  public static ItemTierItem the4horsemenItem;
  
  public static ItemTierItem evokerItem;
  
  public static ItemTierItem giantItem;
  
  public static ItemTierItem villagergolemItem;
  
  public static ItemTierItem enderdragonItem;
  
  public static ItemTierItem witherItem;
  
  public static ItemTierItem eversourceItem;
  
  public static ItemTierItem iceSpiderItem;
  
  public static ItemTierItem creederItem;
  
  public static ItemTierItem icyEnderCreeperItem;
  
  public static ItemTierItem iceGolemItem;
  
  public static ItemTierItem magmaGolemItem;
  
  public static ItemTierItem prisonSlimeItem;
  
  public static ItemTierItem prisonZombieItem;
  
  public static ItemTierItem prisonSpiderItem;
  
  public static ItemTierItem prisonGolemItem;
  
  public static ItemTierItem ghastherItem;
  
  public static ItemTierItem abomniableSnowmanItem;
  
  public static ItemTierItem illusionerItem;
  
  public static ItemTierItem witherStormItem;
  
  public static Item peGunLevel1;
  
  public static Item peGunLevel2;
  
  public static Item peGunLevel3;
  
  public static Item peGunLevel4;
  
  public static Item peGunLevel5;
  
  public static Item abyssalPortalStaff;
  
  public static ItemFusion fusionItemAbyssalZombie;
  
  public static ItemFusion fusionItemAbyssalniteGolem;
  
  public static ItemFusion fusionItemChagarothSpawn;
  
  public static ItemFusion fusionItemChagarothFist;
  
  public static ItemFusion fusionItemCoraliumSquid;
  
  public static ItemFusion fusionItemDreadAbyssalniteGolem;
  
  public static ItemFusion fusionItemDreadling;
  
  public static ItemFusion fusionItemDreadSpawn;
  
  public static ItemFusion fusionItemDepthsGhoul;
  
  public static ItemFusion fusionItemShadowCreature;
  
  public static ItemFusion fusionItemGreaterDreadSpawn;
  
  public static ItemFusion fusionItemOmotholGhoul;
  
  public static ItemFusion fusionItemShadowMonster;
  
  public static ItemFusion fusionItemShoggoth;
  
  public static ItemFusion fusionItemRemnant;
  
  public static ItemFusion fusionItemSpectralDragon;
  
  public static ItemFusion fusionItemAsorah;
  
  public static ItemFusion fusionItemChagaroth;
  
  public static ItemFusion fusionItemDreadguard;
  
  public static ItemFusion fusionItemGatekeeperMinion;
  
  public static ItemFusion fusionItemLesserDreadbeast;
  
  public static ItemFusion fusionItemShadowBeast;
  
  public static ItemFusion fusionItemSkeletonGoliath;
  
  public static ItemFusion fusionItemSacthoth;
  
  public static ItemTierItem shadowCreatureItem;
  
  public static ItemTierItem shadowMonsterItem;
  
  public static ItemTierItem shadowBeastItem;
  
  public static ItemTierItem sacthothItem;
  
  public static ItemTierItem skeletonGoliathItem;
  
  public static ItemTierItem dreadguardItem;
  
  public static ItemTierItem depthsGhoulItem;
  
  public static ItemTierItem omotholGhoulItem;
  
  public static ItemTierItem chagarothSpawnItem;
  
  public static ItemTierItem chagarothFistItem;
  
  public static ItemTierItem dreadSpawnItem;
  
  public static ItemTierItem greaterDreadSpawnItem;
  
  public static ItemTierItem lesserDreadbeastItem;
  
  public static ItemTierItem chagarothItem;
  
  public static ItemTierItem abyssalniteGolemItem;
  
  public static ItemTierItem dreadAbyssalniteGolemItem;
  
  public static ItemTierItem remnantItem;
  
  public static ItemTierItem abyssalZombieItem;
  
  public static ItemTierItem dreadlingItem;
  
  public static ItemTierItem shoggothItem;
  
  public static ItemTierItem gatekeeperminionItem;
  
  public static ItemTierItem coraliumSquidItem;
  
  public static ItemTierItem spectralDragonItem;
  
  public static ItemTierItem asorahItem;
  
  public static ItemFusion fusionItemJzahar;
  
  public static ItemTierItem jzaharItem;
  
  public static Item draconicPortalStaff;
  
  public static ItemFusion fusionItemChaosGuardian;
  
  public static ItemTierItem chaosGuardianItem;
  
  public static ItemFusion fusionPigSpider;
  
  public static ItemFusion fusionMutantSnowGolem;
  
  public static ItemFusion fusionMutantZombie;
  
  public static ItemFusion fusionMutantCreeper;
  
  public static ItemFusion fusionMutantSkeleton;
  
  public static ItemFusion fusionMutantEnderman;
  
  public static ItemTierItem pigSpiderItem;
  
  public static ItemTierItem mutantSnowGolemItem;
  
  public static ItemTierItem mutantCreeperItem;
  
  public static ItemTierItem mutantSkeletonItem;
  
  public static ItemTierItem mutantZombieItem;
  
  public static ItemTierItem mutantEndermanItem;
  
  public static void ENMO() {
    init();
    register();
    BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(carrier, new BehaviorDefaultDispenseItem() {
          public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
            EnumFacing enumfacing = (EnumFacing)source.getBlockState().getValue((IProperty)BlockDispenser.FACING);
            double d0 = source.getX() + enumfacing.getXOffset();
            double d1 = ((source.getBlockPos().getY() + enumfacing.getYOffset()) + 0.2F);
            double d2 = source.getZ() + enumfacing.getZOffset();
            Entity entity = ItemCarrier.spawnMob(source.getWorld(), stack, d0, d1, d2);
            super.dispenseStack(source, stack);
            return stack;
          }
        });
  }
  
  public static void ENMOA() {
    initAby();
    registerAby();
  }
  
  public static void ENMODE() {
    initDracEvo();
    registerDracEvo();
  }
  
  public static void ENMOMB() {
    initMutant();
    registerMutant();
  }
  
  public static void init() {
    manaContainer = new ItemManaCollector("mana_collector", 0);
    entropyContainer = new ItemManaCollector("entropy_collector", 1);
    artifact1 = new ItemManaCollector("infinite_well_spring", 2);
    witheredNetherStar = (Item)(new ItemSimpleFoiled()).setCreativeTab(ETab.engender).setTranslationKey("withered_nether_star").setRegistryName("withered_nether_star");
    statChecker = (Item)new ItemEngenderStatChecker();
    carrier = (Item)new ItemCarrier();
    weddingring = new ItemWeddingRing();
    heromaker = (Item)new ItemHeroMaker();
    lastchance = (Item)new ItemLastChance();
    trainingstick = (Item)new ItemTrainingStick();
    blowhorn = (Item)new ItemMoralHorn();
    blowhorn2 = (Item)new ItemDragonsHorn();
    convertingStaff = (Item)new ItemConvertingStaff();
    summoningStaff = (Item)new ItemSummoningStaff();
    commandingStaff = (Item)new ItemCommandingStaff();
    portalStaff = (Item)new ItemPortalStaff();
    learningBookBasic = new ItemLearningBook(0, "learningbook_basic", "Teach your mobs the basics of themselves.", 150, 10, 0.1F, 0.1F, 0.1F, 0.1F, 0.1F);
    learningBookBasicCombat = new ItemLearningBook(0, "learningbook_basic_combat", "Teach your mobs the basics of combat.", 100, 1, 0.5F, 0.0F, 0.1F, 0.1F, 0.0F);
    learningBookBasicCooking = new ItemLearningBook(0, "learningbook_basic_cooking", "Teach your mobs the basics of cooking.", 100, 1, 0.0F, 0.5F, 0.1F, 0.0F, 0.0F);
    learningBookBasicExercise = new ItemLearningBook(0, "learningbook_basic_exercise", "Teach your mobs the basics of exercise.", 100, 1, 0.0F, 0.0F, 0.1F, 0.1F, 0.5F);
    learningBookBasicKnowledge = new ItemLearningBook(0, "learningbook_basic_knowledge", "Teach your mobs simple knowledge.", 100, 15, 0.0F, 0.0F, 0.5F, 0.0F, 0.0F);
    learningBookModern = new ItemLearningBook(1, "learningbook_modern", "The history of all engendered mobs as we know it.", 75, 25, 0.2F, 0.2F, 0.2F, 0.2F, 0.2F);
    learningBookModernCombat = new ItemLearningBook(1, "learningbook_modern_combat", "How to properly wield your sword", 70, 10, 0.75F, 0.0F, 0.0F, 0.1F, 0.0F);
    learningBookModernBrute = new ItemLearningBook(1, "learningbook_modern_brute", "Learn about the history of Barbarians.", 50, 7, 1.25F, 0.1F, -0.1F, 0.0F, 0.0F);
    learningBookModernCooking = new ItemLearningBook(1, "learningbook_modern_cooking", "A guide to never burning your food again", 70, 10, 0.1F, 0.75F, 0.1F, 0.0F, 0.0F);
    learningBookModernEating = new ItemLearningBook(1, "learningbook_modern_eating", "Create good eating habbits", 50, 7, 0.0F, 0.75F, 0.1F, -0.1F, -0.1F);
    learningBookModernExercise = new ItemLearningBook(1, "learningbook_modern_exercise", "Go outside and exercise.", 70, 10, 0.0F, 0.1F, 0.0F, 0.3F, 0.75F);
    learningBookModernRunning = new ItemLearningBook(1, "learningbook_modern_running", "You can't win a battle if you can't escape from bad decisions.", 50, 7, -0.3F, 0.1F, 0.0F, 0.0F, 0.1F);
    learningBookModernKnowledge = new ItemLearningBook(1, "learningbook_modern_knowledge", "Learn how to calculate your opponent's next move.", 70, 50, 0.0F, 0.0F, 0.75F, 0.0F, 0.0F);
    learningBookModernPacifist = new ItemLearningBook(1, "learningbook_modern_pacifist", "Learn about the gigantic entity that rules the void.", 50, 20, -0.5F, 0.0F, 2.25F, -0.5F, -0.3F);
    learningBookAdvanced = new ItemLearningBook(2, "learningbook_advanced", "It's time to look into the future of Engender!", 50, 50, 0.5F, 0.5F, 0.5F, 0.5F, 0.5F);
    learningBookAdvancedCombat = new ItemLearningBook(2, "learningbook_advanced_combat", "Knight armor and weaponry", 30, 30, 2.5F, 0.1F, 0.1F, 0.1F, -1.0F);
    learningBookAdvancedBrute = new ItemLearningBook(2, "learningbook_advanced_brute", "The history of Spartans", 20, 20, 8.0F, -0.75F, -1.0F, -5.0F, -5.0F);
    learningBookAdvancedWarrior = new ItemLearningBook(2, "learningbook_advanced_warrior", "The legend of an unknown warrior...", 15, 20, -4.0F, 0.0F, 1.0F, 1.5F, 0.0F);
    learningBookAdvancedCooking = new ItemLearningBook(2, "learningbook_advanced_cooking", "Now things are heating up!", 30, 30, -0.2F, 2.5F, 0.1F, 0.1F, -0.2F);
    learningBookAdvancedEating = new ItemLearningBook(2, "learningbook_advanced_eating", "Learn how to eat three times a day", 20, 20, 0.1F, 4.0F, 0.1F, -5.0F, -3.0F);
    learningBookAdvancedDieting = new ItemLearningBook(2, "learningbook_advanced_dieting", "When you get too fat, lose weight!", 15, 20, -7.5F, 7.5F, -0.75F, 0.5F, -0.25F);
    learningBookAdvancedExercise = new ItemLearningBook(2, "learningbook_advanced_exercise", "Time to get into motion!", 30, 30, 0.0F, 0.5F, -0.1F, 0.5F, 2.0F);
    learningBookAdvancedRunning = new ItemLearningBook(2, "learningbook_advanced_running", "Running faster is key to run faster!Wait wha?...", 20, 20, -0.5F, -0.5F, 0.0F, 0.0F, 4.0F);
    learningBookAdvancedLifting = new ItemLearningBook(2, "learningbook_advanced_lifting", "Time to use those arms for something!", 15, 20, 0.1F, -0.5F, -1.0F, 2.5F, 0.0F);
    learningBookAdvancedKnowledge = new ItemLearningBook(2, "learningbook_advanced_knowledge", "Learn how to form tactics.", 30, 100, -0.2F, 0.0F, 2.0F, -0.1F, -0.1F);
    learningBookAdvancedPacifist = new ItemLearningBook(2, "learningbook_advanced_pacifist", "Learn how to protest against an evil society.", 20, 30, -3.0F, 0.0F, 5.0F, -5.0F, -0.2F);
    learningBookAdvancedWisdom = new ItemLearningBook(2, "learningbook_advanced_wisdom", "\"Being wise takes a man to learn how to grow...\"", 15, 200, -0.2F, -0.5F, -2.0F, 0.0F, 0.0F);
    learningBookComplex = new ItemLearningBook(3, "learningbook_complex", "Staying together as one army.", 35, 100, 1.0F, 1.0F, 1.5F, 1.0F, 1.0F);
    learningBookComplexCombat = new ItemLearningBook(3, "learningbook_complex_combat", "How to keep yourself on guard", 15, 50, 8.5F, 0.5F, 1.0F, -0.5F, -4.0F);
    learningBookComplexBrute = new ItemLearningBook(3, "learningbook_complex_brute", "Heavy armor and weaponry", 10, 25, 15.5F, -10.0F, -10.0F, -1.0F, -1.0F);
    learningBookComplexWarrior = new ItemLearningBook(3, "learningbook_complex_warrior", "The historical ultimate blade...", 5, 25, -10.5F, 0.0F, 2.0F, 8.0F, 5.0F);
    learningBookComplexCooking = new ItemLearningBook(3, "learningbook_complex_cooking", "Chef Ramsay has joined the chapter.", 15, 50, 2.5F, 10.0F, 1.0F, -2.0F, -5.0F);
    learningBookComplexEating = new ItemLearningBook(3, "learningbook_complex_eating", "Eating healthy foods come a long way!", 10, 25, -15.0F, 15.0F, 0.2F, 2.0F, 1.0F);
    learningBookComplexDieting = new ItemLearningBook(3, "learningbook_complex_dieting", "Learn how to lose weight to feel better!", 5, 25, -10.0F, 20.5F, -1.0F, -2.0F, 1.0F);
    learningBookComplexExercise = new ItemLearningBook(3, "learningbook_complex_exercise", "Outpreforming your dog", 15, 50, -5.5F, 0.0F, -1.0F, 2.0F, 10.5F);
    learningBookComplexRunning = new ItemLearningBook(3, "learningbook_complex_running", "Running faster than the speed of the wind", 10, 25, -10.0F, 0.1F, -8.0F, -0.2F, 15.0F);
    learningBookComplexLifting = new ItemLearningBook(3, "learningbook_complex_lifting", "A guide to perfect hand-eye coordination", 5, 25, -5.5F, -5.5F, 0.5F, 15.5F, -0.5F);
    learningBookComplexKnowledge = new ItemLearningBook(3, "learningbook_complex_knowledge", "Learn about the world as a whole.", 15, 350, -2.0F, -2.0F, 20.0F, -2.0F, -2.0F);
    learningBookComplexPacifist = new ItemLearningBook(3, "learningbook_complex_pacifist", "How to run a peaceful society", 10, 175, -10.0F, 0.0F, 30.0F, -10.0F, 0.0F);
    learningBookComplexWisdom = new ItemLearningBook(3, "learningbook_complex_wisdom", "\"Never bring a sword to a ranged fight.\"", 5, 1000, -10.0F, -10.0F, 0.5F, -10.0F, 0.0F);
    learningBookMaster = new ItemLearningBook(4, "learningbook_master", "Now that everything is together, let's begin Engender!", 15, 250, 5.0F, 5.0F, 10.0F, 5.0F, 5.0F);
    learningBookMasterCombat = new ItemLearningBook(4, "learningbook_master_combat", "A master's guide to combat", 120, 1, 30.0F, -15.0F, 5.0F, -5.0F, -5.0F);
    learningBookMasterBrute = new ItemLearningBook(4, "learningbook_master_brute", "The legends of Gundyr", 7, 75, 50.0F, -30.0F, -5.0F, -5.0F, -5.0F);
    learningBookMasterWarrior = new ItemLearningBook(4, "learningbook_master_warrior", "Mastery of swordcraft", 5, 75, -20.0F, 10.0F, 5.0F, 10.0F, -5.0F);
    learningBookMasterCooking = new ItemLearningBook(4, "learningbook_master_cooking", "Never make anything raw again!", 10, 120, 0.0F, 30.0F, 1.0F, -5.0F, -5.0F);
    learningBookMasterEating = new ItemLearningBook(4, "learningbook_master_eating", "Making the perfect meal!", 7, 75, -10.0F, 45.0F, 2.0F, -10.0F, -5.0F);
    learningBookMasterDieting = new ItemLearningBook(4, "learningbook_master_dieting", "Best ways to lose unnecissary weight", 5, 75, -25.5F, 40.0F, -5.0F, 5.0F, 5.0F);
    learningBookMasterExercise = new ItemLearningBook(4, "learningbook_master_exercise", "Mastering Ultra-Instinct", 10, 120, -10.0F, -5.0F, 1.0F, 15.5F, 15.0F);
    learningBookMasterRunning = new ItemLearningBook(4, "learningbook_master_running", "Go faster than the speed of sound!", 7, 75, -5.0F, -10.0F, -2.0F, 1.0F, 30.0F);
    learningBookMasterLifting = new ItemLearningBook(4, "learningbook_master_lifting", "Win the match with faster blows!", 5, 75, -5.0F, 0.0F, 2.0F, 20.0F, -5.0F);
    learningBookMasterKnowledge = new ItemLearningBook(4, "learningbook_master_knowledge", "Know your opponent", 10, 3000, -5.0F, -5.0F, 35.0F, 0.0F, 0.0F);
    learningBookMasterPacifist = new ItemLearningBook(4, "learningbook_master_pacifist", "How to end world hunger", 7, 500, -15.5F, -5.0F, 45.0F, -15.5F, 0.5F);
    learningBookMasterWisdom = new ItemLearningBook(4, "learningbook_master_wisdom", "\"In war, everybody loses something...\"", 5, 25000, -10.0F, -5.0F, -30.1F, -5.0F, -1.0F);
    learningBookArtifactStrength = new ItemLearningBook(5, "learningbook_artifact_strength", "Learn how to wield a real weapon crafted by the gods.", 2, 100000, 100.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    learningBookArtifactStamina = new ItemLearningBook(5, "learningbook_artifact_stamina", "There is a way to never feel exhausted.The power is yours.", 2, 100000, 0.0F, 100.0F, 0.0F, 0.0F, 0.0F);
    learningBookArtifactSpeed = new ItemLearningBook(5, "learningbook_artifact_speed", "Let's see if you can use your instincts...", 2, 100000, 0.0F, 0.0F, 0.0F, 100.0F, 100.0F);
    learningBookArtifactIntellegence = new ItemLearningBook(5, "learningbook_artifact_intelegence", "A god can never go without knowledge of the universe.", 2, 250000, 0.0F, 0.0F, 100.0F, 0.0F, 0.0F);
    learningBookArtifact = new ItemLearningBook(6, "learningbook_artifact", "Gain the knowledge of a god with a glance.", 1, 1000000, 100.0F, 100.0F, 100.0F, 100.0F, 100.0F);
    batItem = (ItemTierItem)new ItemBatItem();
    chickenItem = (ItemTierItem)new ItemChickenItem();
    cowItem = (ItemTierItem)new ItemCowItem();
    mooshroomItem = (ItemTierItem)new ItemMooshroomItem();
    ozelotItem = (ItemTierItem)new ItemOcelotItem();
    parrotItem = (ItemTierItem)new ItemParrotItem();
    pigItem = (ItemTierItem)new ItemPigItem();
    rabbitItem = (ItemTierItem)new ItemRabbitItem();
    sheepItem = (ItemTierItem)new ItemSheepItem();
    endermiteItem = (ItemTierItem)new ItemEndermiteItem();
    llamaItem = (ItemTierItem)new ItemLlamaItem();
    silverfishItem = (ItemTierItem)new ItemSilverfishItem();
    snowmanItem = (ItemTierItem)new ItemSnowmanItem();
    squidItem = (ItemTierItem)new ItemSquidItem();
    villagerItem = (ItemTierItem)new ItemVillagerItem();
    wolfItem = (ItemTierItem)new ItemWolfItem();
    chickenjockeyItem = (ItemTierItem)new ItemChickenJockeyItem();
    creeperItem = (ItemTierItem)new ItemCreeperItem();
    magmacubeItem = (ItemTierItem)new ItemMagmaCubeItem();
    polarBearItem = (ItemTierItem)new ItemPolarBearItem();
    prisonSlimeItem = (ItemTierItem)new ItemPrisonSlimeItem();
    skeletonItem = (ItemTierItem)new ItemSkeletonItem();
    slimeItem = (ItemTierItem)new ItemSlimeItem();
    spiderItem = (ItemTierItem)new ItemSpiderItem();
    spiderjockeyItem = (ItemTierItem)new ItemSpiderJockeyItem();
    vexItem = (ItemTierItem)new ItemVexItem();
    zombieItem = (ItemTierItem)new ItemZombieItem();
    blazeItem = (ItemTierItem)new ItemBlazeItem();
    cavespiderItem = (ItemTierItem)new ItemCaveSpiderItem();
    creederItem = (ItemTierItem)new ItemCreederItem();
    endermanItem = (ItemTierItem)new ItemEndermanItem();
    ghastItem = (ItemTierItem)new ItemGhastItem();
    guardianItem = (ItemTierItem)new ItemGuardianItem();
    huskItem = (ItemTierItem)new ItemHuskItem();
    iceSpiderItem = (ItemTierItem)new ItemIceSpiderItem();
    icyEnderCreeperItem = (ItemTierItem)new ItemIcyEnderCreeperItem();
    killerrabbitItem = (ItemTierItem)new ItemKillerBunnyItem();
    prisonSpiderItem = (ItemTierItem)new ItemPrisonSpiderItem();
    prisonZombieItem = (ItemTierItem)new ItemPrisonZombieItem();
    shulkerItem = (ItemTierItem)new ItemShulkerItem();
    strayItem = (ItemTierItem)new ItemStrayItem();
    vindicatorItem = (ItemTierItem)new ItemVindicatorItem();
    witchItem = (ItemTierItem)new ItemWitchItem();
    witherskeletonItem = (ItemTierItem)new ItemWitherSkeletonItem();
    pigzombieItem = (ItemTierItem)new ItemPigZombieItem();
    abomniableSnowmanItem = (ItemTierItem)new ItemAbomniableSnowmanItem();
    elderguardianItem = (ItemTierItem)new ItemElderGuardianItem();
    enderdragonItem = (ItemTierItem)new ItemEnderDragonItem();
    eversourceItem = (ItemTierItem)new ItemEversourceItem();
    evokerItem = (ItemTierItem)new ItemEvokerItem();
    ghastherItem = (ItemTierItem)new ItemGhastherItem();
    giantItem = (ItemTierItem)new ItemGiantItem();
    iceGolemItem = (ItemTierItem)new ItemIceGolemItem();
    illusionerItem = (ItemTierItem)new ItemIllusionerItem();
    villagergolemItem = (ItemTierItem)new ItemIronGolemItem();
    magmaGolemItem = (ItemTierItem)new ItemMagmaGolemItem();
    prisonGolemItem = (ItemTierItem)new ItemPrisonGolemItem();
    the4horsemenItem = (ItemTierItem)new ItemSkeletonTrapItem();
    witherItem = (ItemTierItem)new ItemWitherItem();
    witherStormItem = (ItemTierItem)new ItemWitherStormItem();
    fusionItemBat = (ItemFusion)new ItemFusionVanilla(batItem, "fusionbat", 1, 0);
    fusionItemChicken = (ItemFusion)new ItemFusionVanilla(chickenItem, "fusionchicken", 1, 0);
    fusionItemCow = (ItemFusion)new ItemFusionVanilla(cowItem, "fusioncow", 2, 0);
    fusionItemMooshroom = (ItemFusion)new ItemFusionVanilla(mooshroomItem, "fusionmooshroom", 4, 0);
    fusionItemOzelot = (ItemFusion)new ItemFusionVanilla(ozelotItem, "fusionozelot", 2, 0);
    fusionItemParrot = (ItemFusion)new ItemFusionVanilla(parrotItem, "fusionparrot", 1, 0);
    fusionItemPig = (ItemFusion)new ItemFusionVanilla(pigItem, "fusionpig", 2, 0);
    fusionItemRabbit = (ItemFusion)new ItemFusionVanilla(rabbitItem, "fusionrabbit", 1, 0);
    fusionItemSheep = (ItemFusion)new ItemFusionVanilla(sheepItem, "fusionsheep", 3, 0);
    fusionItemEndermite = (ItemFusion)new ItemFusionVanilla(endermiteItem, "fusionendermite", 5, 0);
    fusionItemLlama = (ItemFusion)new ItemFusionVanilla(llamaItem, "fusionllama", 4, 0);
    fusionItemSilverfish = (ItemFusion)new ItemFusionVanilla(silverfishItem, "fusionsilverfish", 5, 0);
    fusionItemSnowman = (ItemFusion)new ItemFusionVanilla(snowmanItem, "fusionsnowman", 4, 0);
    fusionItemSquid = (ItemFusion)new ItemFusionVanilla(squidItem, "fusionsquid", 6, 0);
    fusionItemVillager = (ItemFusion)new ItemFusionVanilla(villagerItem, "fusionvillager", 15, 0);
    fusionItemWolf = (ItemFusion)new ItemFusionVanilla(wolfItem, "fusionwolf", 6, 0);
    fusionItemChickenJockey = (ItemFusion)new ItemFusionVanilla(chickenjockeyItem, "fusionchickenjockey", 25, 0);
    fusionItemCreeper = (ItemFusion)new ItemFusionVanilla(creeperItem, "fusioncreeper", 25, 0);
    fusionItemMagmaCube = (ItemFusion)new ItemFusionVanilla(magmacubeItem, "fusionmagmacube", 10, 0);
    fusionItemPolarBear = (ItemFusion)new ItemFusionVanilla(polarBearItem, "fusionpolarbear", 30, 0);
    fusionItemPrisonSlime = (ItemFusion)new ItemFusionMCSM(prisonSlimeItem, "fusionprisonslime", 12, 0);
    fusionItemSkeleton = (ItemFusion)new ItemFusionVanilla(skeletonItem, "fusionskeleton", 20, 0);
    fusionItemSlime = (ItemFusion)new ItemFusionVanilla(slimeItem, "fusionslime", 8, 0);
    fusionItemSpider = (ItemFusion)new ItemFusionVanilla(spiderItem, "fusionspider", 8, 0);
    fusionItemSpiderJockey = (ItemFusion)new ItemFusionVanilla(spiderjockeyItem, "fusionspiderjockey", 32, 0);
    fusionItemVex = (ItemFusion)new ItemFusionVanilla(vexItem, "fusionvex", 15, 0);
    fusionItemZombie = (ItemFusion)new ItemFusionVanilla(zombieItem, "fusionzombie", 20, 0);
    fusionItemBlaze = (ItemFusion)new ItemFusionVanilla(blazeItem, "fusionblaze", 75, 0);
    fusionItemCaveSpider = (ItemFusion)new ItemFusionVanilla(cavespiderItem, "fusioncavespider", 40, 0);
    fusionItemCreeder = (ItemFusion)new ItemFusionMCSM(creederItem, "fusioncreeder", 100, 0);
    fusionItemEnderman = (ItemFusion)new ItemFusionVanilla(endermanItem, "fusionenderman", 150, 0);
    fusionItemGhast = (ItemFusion)new ItemFusionVanilla(ghastItem, "fusionghast", 250, 0);
    fusionItemGuardian = (ItemFusion)new ItemFusionVanilla(guardianItem, "fusionguardian", 120, 0);
    fusionItemHusk = (ItemFusion)new ItemFusionVanilla(huskItem, "fusionhusk", 40, 0);
    fusionItemIceSpider = (ItemFusion)new ItemFusionMCSM(iceSpiderItem, "fusionicespider", 30, 0);
    fusionItemIcyEnderCreeper = (ItemFusion)new ItemFusionMCSM(icyEnderCreeperItem, "fusionicyendercreeper", 100, 0);
    fusionItemKillerRabbit = (ItemFusion)new ItemFusionVanilla(killerrabbitItem, "fusionkillerbunny", 200, 0);
    fusionItemPrisonSpider = (ItemFusion)new ItemFusionMCSM(prisonSpiderItem, "fusionprisonspider", 50, 0);
    fusionItemPrisonZombie = (ItemFusion)new ItemFusionMCSM(prisonZombieItem, "fusionprisonzombie", 60, 0);
    fusionItemShulker = (ItemFusion)new ItemFusionVanilla(shulkerItem, "fusionshulker", 200, 0);
    fusionItemStray = (ItemFusion)new ItemFusionVanilla(strayItem, "fusionstray", 80, 0);
    fusionItemVindicator = (ItemFusion)new ItemFusionVanilla(vindicatorItem, "fusionvindicator", 150, 0);
    fusionItemWitch = (ItemFusion)new ItemFusionVanilla(witchItem, "fusionwitch", 100, 0);
    fusionItemWitherSkeleton = (ItemFusion)new ItemFusionVanilla(witherskeletonItem, "fusionwitherskeleton", 125, 0);
    fusionItemPigZombie = (ItemFusion)new ItemFusionVanilla(pigzombieItem, "fusionpigzombie", 80, 0);
    fusionItemAbomniableSnowman = (ItemFusion)new ItemFusionVanilla(abomniableSnowmanItem, "fusionabomniablesnowman", 2500, 50);
    fusionItemElderGuardian = (ItemFusion)new ItemFusionVanilla(elderguardianItem, "fusionelderguardian", 1500, 20);
    fusionItemEnderDragon = (ItemFusion)new ItemFusionVanilla(enderdragonItem, "fusionenderdragon", 24000, 500);
    fusionItemEversource = (ItemFusion)new ItemFusionMCSM(eversourceItem, "fusioneversource", 5000, 1000);
    fusionItemEvoker = (ItemFusion)new ItemFusionVanilla(evokerItem, "fusionevoker", 6000, 350);
    fusionItemGhasther = (ItemFusion)new ItemFusionVanilla(ghastherItem, "fusionghasther", 4000, 150);
    fusionItemGiant = (ItemFusion)new ItemFusionVanilla(giantItem, "fusiongiant", 2000, 30);
    fusionItemMagmaGolem = (ItemFusion)new ItemFusionMCSM(magmaGolemItem, "fusionmagmagolem", 1000, 10);
    fusionItemIceGolem = (ItemFusion)new ItemFusionMCSM(iceGolemItem, "fusionicegolem", 800, 10);
    fusionItemIllusioner = (ItemFusion)new ItemFusionVanilla(illusionerItem, "fusionillusioner", 6000, 350);
    fusionItemVillagerGolem = (ItemFusion)new ItemFusionVanilla(villagergolemItem, "fusionirongolem", 1500, 20);
    fusionItemPrisonGolem = (ItemFusion)new ItemFusionMCSM(prisonGolemItem, "fusionprisongolem", 1600, 20);
    fusionItemSkeletonTrap = (ItemFusion)new ItemFusionVanilla(the4horsemenItem, "fusionskeletontrap", 2400, 80);
    fusionItemWither = (ItemFusion)new ItemFusionVanilla(witherItem, "fusionwither", 12000, 750);
    fusionItemWitherStorm = (ItemFusion)new ItemFusionMCSM(witherStormItem, "fusionwitherstorm", 100000, 5000);
  }
  
  public static void initAby() {
    if (Loader.isModLoaded("abyssalcraft")) {
      peGunLevel1 = (Item)new ItemPEGun("pegun");
      peGunLevel2 = (Item)new ItemPEGun("pegun1");
      peGunLevel3 = (Item)new ItemPEGun("pegun2");
      peGunLevel4 = (Item)new ItemPEGun("pegun3");
      peGunLevel5 = (Item)new ItemPEGun("pegun4");
      abyssalPortalStaff = (Item)new ItemAbyssalPortalStaff();
      abyssalZombieItem = (ItemTierItem)new ItemAbyssalZombieItem();
      abyssalniteGolemItem = (ItemTierItem)new ItemAbygolemItem();
      dreadAbyssalniteGolemItem = (ItemTierItem)new ItemDreadgolemItem();
      dreadlingItem = (ItemTierItem)new ItemDreadlingItem();
      dreadSpawnItem = (ItemTierItem)new ItemDreadSpawnItem();
      chagarothSpawnItem = (ItemTierItem)new ItemChagarothSpawnItem();
      chagarothFistItem = (ItemTierItem)new ItemChagarothFistItem();
      coraliumSquidItem = (ItemTierItem)new ItemCoraliumSquidItem();
      depthsGhoulItem = (ItemTierItem)new ItemDepthsGhoulItem();
      shadowCreatureItem = (ItemTierItem)new ItemShadowCreatureItem();
      greaterDreadSpawnItem = (ItemTierItem)new ItemGreaterDreadSpawnItem();
      omotholGhoulItem = (ItemTierItem)new ItemOmotholGhoulItem();
      remnantItem = (ItemTierItem)new ItemRemnantItem();
      shadowMonsterItem = (ItemTierItem)new ItemShadowMonsterItem();
      shoggothItem = (ItemTierItem)new ItemLesserShoggothItem();
      spectralDragonItem = (ItemTierItem)new ItemSpectralDragonItem();
      asorahItem = (ItemTierItem)new ItemAsorahItem();
      chagarothItem = (ItemTierItem)new ItemChagarothItem();
      dreadguardItem = (ItemTierItem)new ItemDreadguardItem();
      gatekeeperminionItem = (ItemTierItem)new ItemGatekeeperMinionItem();
      lesserDreadbeastItem = (ItemTierItem)new ItemLesserDreadbeastItem();
      shadowBeastItem = (ItemTierItem)new ItemShadowBeastItem();
      skeletonGoliathItem = (ItemTierItem)new ItemSkeletonGoliathItem();
      sacthothItem = (ItemTierItem)new ItemSacthothItem();
      jzaharItem = (ItemTierItem)new ItemJzaharItem();
      fusionItemAbyssalZombie = (ItemFusion)new ItemFusionAby(abyssalZombieItem, "fusionabyssalzombie", 40, 0);
      fusionItemAbyssalniteGolem = (ItemFusion)new ItemFusionAby(abyssalniteGolemItem, "fusionabygolem", 30, 0);
      fusionItemChagarothSpawn = (ItemFusion)new ItemFusionAby(chagarothSpawnItem, "fusionchagarothspawn", 20, 0);
      fusionItemChagarothFist = (ItemFusion)new ItemFusionAby(chagarothFistItem, "fusionchagarothfist", 60, 0);
      fusionItemCoraliumSquid = (ItemFusion)new ItemFusionAby(coraliumSquidItem, "fusioncoraliumsquid", 40, 0);
      fusionItemDreadAbyssalniteGolem = (ItemFusion)new ItemFusionAby(dreadAbyssalniteGolemItem, "fusiondreadgolem", 40, 0);
      fusionItemDreadling = (ItemFusion)new ItemFusionAby(dreadlingItem, "fusiondreadling", 20, 0);
      fusionItemDreadSpawn = (ItemFusion)new ItemFusionAby(dreadSpawnItem, "fusiondreadspawn", 10, 0);
      fusionItemDepthsGhoul = (ItemFusion)new ItemFusionAby(depthsGhoulItem, "fusiondepthsghoul", 20, 0);
      fusionItemShadowCreature = (ItemFusion)new ItemFusionAby(shadowCreatureItem, "fusionshadowcreature", 30, 0);
      fusionItemGreaterDreadSpawn = (ItemFusion)new ItemFusionAby(greaterDreadSpawnItem, "fusiongreaterdreadspawn", 100, 0);
      fusionItemOmotholGhoul = (ItemFusion)new ItemFusionAby(omotholGhoulItem, "fusionomotholghoul", 400, 0);
      fusionItemShadowMonster = (ItemFusion)new ItemFusionAby(shadowMonsterItem, "fusionshadowmonster", 100, 0);
      fusionItemShoggoth = (ItemFusion)new ItemFusionAby(shoggothItem, "fusionshoggoth", 800, 0);
      fusionItemRemnant = (ItemFusion)new ItemFusionAby(remnantItem, "fusionremnant", 300, 0);
      fusionItemSpectralDragon = (ItemFusion)new ItemFusionAby(spectralDragonItem, "fusionspectraldragon", 500, 0);
      fusionItemAsorah = (ItemFusion)new ItemFusionAby(asorahItem, "fusionasorah", 24000, 500);
      fusionItemChagaroth = (ItemFusion)new ItemFusionAby(chagarothItem, "fusionchagaroth", 20000, 2000);
      fusionItemDreadguard = (ItemFusion)new ItemFusionAby(dreadguardItem, "fusiondreadguard", 2000, 50);
      fusionItemGatekeeperMinion = (ItemFusion)new ItemFusionAby(gatekeeperminionItem, "fusiongatekeeperminion", 5000, 100);
      fusionItemLesserDreadbeast = (ItemFusion)new ItemFusionAby(lesserDreadbeastItem, "fusionlesserdreadbeast", 6000, 50);
      fusionItemShadowBeast = (ItemFusion)new ItemFusionAby(shadowBeastItem, "fusionshadowbeast", 2000, 20);
      fusionItemSkeletonGoliath = (ItemFusion)new ItemFusionAby(skeletonGoliathItem, "fusiongskeleton", 1000, 10);
      fusionItemSacthoth = (ItemFusion)new ItemFusionAby(sacthothItem, "fusionsacthoth", 16000, 200);
      fusionItemJzahar = (ItemFusion)new ItemFusionAby(jzaharItem, "fusionjzahar", 125000, 7500);
    } 
  }
  
  public static void initDracEvo() {
    if (Loader.isModLoaded("draconicevolution")) {
      draconicPortalStaff = (Item)new ItemDraconicPortalStaff();
      chaosGuardianItem = (ItemTierItem)new ItemChaosGuardianItem();
      fusionItemChaosGuardian = (ItemFusion)new ItemFusionDrac(chaosGuardianItem, "fusionchaosguardian", 200000, 10000);
    } 
  }
  
  public static void initMutant() {
    if (Loader.isModLoaded("mutantbeasts")) {
      pigSpiderItem = (ItemTierItem)new ItemSpiderPigItem();
      mutantSnowGolemItem = (ItemTierItem)new ItemMutantSnowGolemItem();
      mutantCreeperItem = (ItemTierItem)new ItemMutantCreeperItem();
      mutantSkeletonItem = (ItemTierItem)new ItemMutantSkeletonItem();
      mutantZombieItem = (ItemTierItem)new ItemMutantZombieItem();
      mutantEndermanItem = (ItemTierItem)new ItemMutantEndermanItem();
      fusionPigSpider = (ItemFusion)new ItemFusionMutant(pigSpiderItem, "fusionspiderpig", 100, 0);
      fusionMutantSnowGolem = (ItemFusion)new ItemFusionMutant(mutantSnowGolemItem, "fusionmutantsnowgolem", 500, 0);
      fusionMutantCreeper = (ItemFusion)new ItemFusionMutant(mutantCreeperItem, "fusionmutantcreeper", 8000, 400);
      fusionMutantSkeleton = (ItemFusion)new ItemFusionMutant(mutantSkeletonItem, "fusionmutantskeleton", 12000, 600);
      fusionMutantZombie = (ItemFusion)new ItemFusionMutant(mutantZombieItem, "fusionmutantzombie", 16000, 800);
      fusionMutantEnderman = (ItemFusion)new ItemFusionMutant(mutantEndermanItem, "fusionmutantenderman", 20000, 1000);
    } 
  }
  
  public static void register() {
    GameData.register_impl((IForgeRegistryEntry)manaContainer);
    GameData.register_impl((IForgeRegistryEntry)entropyContainer);
    GameData.register_impl((IForgeRegistryEntry)artifact1);
    GameData.register_impl((IForgeRegistryEntry)witheredNetherStar);
    GameData.register_impl((IForgeRegistryEntry)statChecker);
    GameData.register_impl((IForgeRegistryEntry)carrier);
    GameData.register_impl((IForgeRegistryEntry)weddingring);
    GameData.register_impl((IForgeRegistryEntry)heromaker);
    GameData.register_impl((IForgeRegistryEntry)lastchance);
    GameData.register_impl((IForgeRegistryEntry)trainingstick);
    GameData.register_impl((IForgeRegistryEntry)blowhorn);
    GameData.register_impl((IForgeRegistryEntry)blowhorn2);
    GameData.register_impl((IForgeRegistryEntry)convertingStaff);
    GameData.register_impl((IForgeRegistryEntry)summoningStaff);
    GameData.register_impl((IForgeRegistryEntry)commandingStaff);
    GameData.register_impl((IForgeRegistryEntry)portalStaff);
    GameData.register_impl((IForgeRegistryEntry)learningBookBasic);
    GameData.register_impl((IForgeRegistryEntry)learningBookBasicCombat);
    GameData.register_impl((IForgeRegistryEntry)learningBookBasicCooking);
    GameData.register_impl((IForgeRegistryEntry)learningBookBasicExercise);
    GameData.register_impl((IForgeRegistryEntry)learningBookBasicKnowledge);
    GameData.register_impl((IForgeRegistryEntry)learningBookModern);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernCombat);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernBrute);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernCooking);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernEating);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernExercise);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernRunning);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernKnowledge);
    GameData.register_impl((IForgeRegistryEntry)learningBookModernPacifist);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvanced);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedCombat);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedBrute);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedWarrior);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedCooking);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedEating);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedDieting);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedExercise);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedRunning);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedLifting);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedKnowledge);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedPacifist);
    GameData.register_impl((IForgeRegistryEntry)learningBookAdvancedWisdom);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplex);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexCombat);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexBrute);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexWarrior);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexCooking);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexEating);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexDieting);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexExercise);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexRunning);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexLifting);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexKnowledge);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexPacifist);
    GameData.register_impl((IForgeRegistryEntry)learningBookComplexWisdom);
    GameData.register_impl((IForgeRegistryEntry)learningBookMaster);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterCombat);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterBrute);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterWarrior);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterCooking);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterEating);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterDieting);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterExercise);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterRunning);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterLifting);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterKnowledge);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterPacifist);
    GameData.register_impl((IForgeRegistryEntry)learningBookMasterWisdom);
    GameData.register_impl((IForgeRegistryEntry)learningBookArtifactStrength);
    GameData.register_impl((IForgeRegistryEntry)learningBookArtifactStamina);
    GameData.register_impl((IForgeRegistryEntry)learningBookArtifactSpeed);
    GameData.register_impl((IForgeRegistryEntry)learningBookArtifactIntellegence);
    GameData.register_impl((IForgeRegistryEntry)learningBookArtifact);
    GameData.register_impl((IForgeRegistryEntry)fusionItemBat);
    GameData.register_impl((IForgeRegistryEntry)fusionItemChicken);
    GameData.register_impl((IForgeRegistryEntry)fusionItemCow);
    GameData.register_impl((IForgeRegistryEntry)fusionItemMooshroom);
    GameData.register_impl((IForgeRegistryEntry)fusionItemOzelot);
    GameData.register_impl((IForgeRegistryEntry)fusionItemParrot);
    GameData.register_impl((IForgeRegistryEntry)fusionItemPig);
    GameData.register_impl((IForgeRegistryEntry)fusionItemRabbit);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSheep);
    GameData.register_impl((IForgeRegistryEntry)fusionItemEndermite);
    GameData.register_impl((IForgeRegistryEntry)fusionItemLlama);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSilverfish);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSnowman);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSquid);
    GameData.register_impl((IForgeRegistryEntry)fusionItemVillager);
    GameData.register_impl((IForgeRegistryEntry)fusionItemWolf);
    GameData.register_impl((IForgeRegistryEntry)fusionItemChickenJockey);
    GameData.register_impl((IForgeRegistryEntry)fusionItemCreeper);
    GameData.register_impl((IForgeRegistryEntry)fusionItemMagmaCube);
    GameData.register_impl((IForgeRegistryEntry)fusionItemPolarBear);
    GameData.register_impl((IForgeRegistryEntry)fusionItemPrisonSlime);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSkeleton);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSlime);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSpider);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSpiderJockey);
    GameData.register_impl((IForgeRegistryEntry)fusionItemVex);
    GameData.register_impl((IForgeRegistryEntry)fusionItemZombie);
    GameData.register_impl((IForgeRegistryEntry)fusionItemBlaze);
    GameData.register_impl((IForgeRegistryEntry)fusionItemCaveSpider);
    GameData.register_impl((IForgeRegistryEntry)fusionItemCreeder);
    GameData.register_impl((IForgeRegistryEntry)fusionItemEnderman);
    GameData.register_impl((IForgeRegistryEntry)fusionItemGhast);
    GameData.register_impl((IForgeRegistryEntry)fusionItemGuardian);
    GameData.register_impl((IForgeRegistryEntry)fusionItemHusk);
    GameData.register_impl((IForgeRegistryEntry)fusionItemIceSpider);
    GameData.register_impl((IForgeRegistryEntry)fusionItemIcyEnderCreeper);
    GameData.register_impl((IForgeRegistryEntry)fusionItemKillerRabbit);
    GameData.register_impl((IForgeRegistryEntry)fusionItemPrisonSpider);
    GameData.register_impl((IForgeRegistryEntry)fusionItemPrisonZombie);
    GameData.register_impl((IForgeRegistryEntry)fusionItemShulker);
    GameData.register_impl((IForgeRegistryEntry)fusionItemStray);
    GameData.register_impl((IForgeRegistryEntry)fusionItemVindicator);
    GameData.register_impl((IForgeRegistryEntry)fusionItemWitch);
    GameData.register_impl((IForgeRegistryEntry)fusionItemWitherSkeleton);
    GameData.register_impl((IForgeRegistryEntry)fusionItemPigZombie);
    GameData.register_impl((IForgeRegistryEntry)fusionItemAbomniableSnowman);
    GameData.register_impl((IForgeRegistryEntry)fusionItemElderGuardian);
    GameData.register_impl((IForgeRegistryEntry)fusionItemEnderDragon);
    GameData.register_impl((IForgeRegistryEntry)fusionItemEversource);
    GameData.register_impl((IForgeRegistryEntry)fusionItemEvoker);
    GameData.register_impl((IForgeRegistryEntry)fusionItemGhasther);
    GameData.register_impl((IForgeRegistryEntry)fusionItemGiant);
    GameData.register_impl((IForgeRegistryEntry)fusionItemIceGolem);
    GameData.register_impl((IForgeRegistryEntry)fusionItemIllusioner);
    GameData.register_impl((IForgeRegistryEntry)fusionItemVillagerGolem);
    GameData.register_impl((IForgeRegistryEntry)fusionItemMagmaGolem);
    GameData.register_impl((IForgeRegistryEntry)fusionItemPrisonGolem);
    GameData.register_impl((IForgeRegistryEntry)fusionItemSkeletonTrap);
    GameData.register_impl((IForgeRegistryEntry)fusionItemWither);
    GameData.register_impl((IForgeRegistryEntry)fusionItemWitherStorm);
    GameData.register_impl((IForgeRegistryEntry)batItem);
    GameData.register_impl((IForgeRegistryEntry)chickenItem);
    GameData.register_impl((IForgeRegistryEntry)cowItem);
    GameData.register_impl((IForgeRegistryEntry)mooshroomItem);
    GameData.register_impl((IForgeRegistryEntry)ozelotItem);
    GameData.register_impl((IForgeRegistryEntry)parrotItem);
    GameData.register_impl((IForgeRegistryEntry)pigItem);
    GameData.register_impl((IForgeRegistryEntry)rabbitItem);
    GameData.register_impl((IForgeRegistryEntry)sheepItem);
    GameData.register_impl((IForgeRegistryEntry)endermiteItem);
    GameData.register_impl((IForgeRegistryEntry)llamaItem);
    GameData.register_impl((IForgeRegistryEntry)silverfishItem);
    GameData.register_impl((IForgeRegistryEntry)snowmanItem);
    GameData.register_impl((IForgeRegistryEntry)squidItem);
    GameData.register_impl((IForgeRegistryEntry)villagerItem);
    GameData.register_impl((IForgeRegistryEntry)wolfItem);
    GameData.register_impl((IForgeRegistryEntry)chickenjockeyItem);
    GameData.register_impl((IForgeRegistryEntry)creeperItem);
    GameData.register_impl((IForgeRegistryEntry)magmacubeItem);
    GameData.register_impl((IForgeRegistryEntry)polarBearItem);
    GameData.register_impl((IForgeRegistryEntry)prisonSlimeItem);
    GameData.register_impl((IForgeRegistryEntry)skeletonItem);
    GameData.register_impl((IForgeRegistryEntry)slimeItem);
    GameData.register_impl((IForgeRegistryEntry)spiderItem);
    GameData.register_impl((IForgeRegistryEntry)spiderjockeyItem);
    GameData.register_impl((IForgeRegistryEntry)vexItem);
    GameData.register_impl((IForgeRegistryEntry)zombieItem);
    GameData.register_impl((IForgeRegistryEntry)blazeItem);
    GameData.register_impl((IForgeRegistryEntry)cavespiderItem);
    GameData.register_impl((IForgeRegistryEntry)creederItem);
    GameData.register_impl((IForgeRegistryEntry)endermanItem);
    GameData.register_impl((IForgeRegistryEntry)ghastItem);
    GameData.register_impl((IForgeRegistryEntry)guardianItem);
    GameData.register_impl((IForgeRegistryEntry)huskItem);
    GameData.register_impl((IForgeRegistryEntry)iceSpiderItem);
    GameData.register_impl((IForgeRegistryEntry)icyEnderCreeperItem);
    GameData.register_impl((IForgeRegistryEntry)killerrabbitItem);
    GameData.register_impl((IForgeRegistryEntry)prisonSpiderItem);
    GameData.register_impl((IForgeRegistryEntry)prisonZombieItem);
    GameData.register_impl((IForgeRegistryEntry)shulkerItem);
    GameData.register_impl((IForgeRegistryEntry)strayItem);
    GameData.register_impl((IForgeRegistryEntry)vindicatorItem);
    GameData.register_impl((IForgeRegistryEntry)witchItem);
    GameData.register_impl((IForgeRegistryEntry)witherskeletonItem);
    GameData.register_impl((IForgeRegistryEntry)pigzombieItem);
    GameData.register_impl((IForgeRegistryEntry)abomniableSnowmanItem);
    GameData.register_impl((IForgeRegistryEntry)elderguardianItem);
    GameData.register_impl((IForgeRegistryEntry)enderdragonItem);
    GameData.register_impl((IForgeRegistryEntry)eversourceItem);
    GameData.register_impl((IForgeRegistryEntry)evokerItem);
    GameData.register_impl((IForgeRegistryEntry)ghastherItem);
    GameData.register_impl((IForgeRegistryEntry)giantItem);
    GameData.register_impl((IForgeRegistryEntry)iceGolemItem);
    GameData.register_impl((IForgeRegistryEntry)illusionerItem);
    GameData.register_impl((IForgeRegistryEntry)villagergolemItem);
    GameData.register_impl((IForgeRegistryEntry)magmaGolemItem);
    GameData.register_impl((IForgeRegistryEntry)prisonGolemItem);
    GameData.register_impl((IForgeRegistryEntry)the4horsemenItem);
    GameData.register_impl((IForgeRegistryEntry)witherItem);
    GameData.register_impl((IForgeRegistryEntry)witherStormItem);
  }
  
  public static void registerAby() {
    if (Loader.isModLoaded("abyssalcraft")) {
      GameData.register_impl((IForgeRegistryEntry)peGunLevel1);
      GameData.register_impl((IForgeRegistryEntry)peGunLevel2);
      GameData.register_impl((IForgeRegistryEntry)peGunLevel3);
      GameData.register_impl((IForgeRegistryEntry)peGunLevel4);
      GameData.register_impl((IForgeRegistryEntry)peGunLevel5);
      GameData.register_impl((IForgeRegistryEntry)abyssalPortalStaff);
      GameData.register_impl((IForgeRegistryEntry)fusionItemAbyssalZombie);
      GameData.register_impl((IForgeRegistryEntry)fusionItemAbyssalniteGolem);
      GameData.register_impl((IForgeRegistryEntry)fusionItemChagarothSpawn);
      GameData.register_impl((IForgeRegistryEntry)fusionItemChagarothFist);
      GameData.register_impl((IForgeRegistryEntry)fusionItemCoraliumSquid);
      GameData.register_impl((IForgeRegistryEntry)fusionItemDreadAbyssalniteGolem);
      GameData.register_impl((IForgeRegistryEntry)fusionItemDreadling);
      GameData.register_impl((IForgeRegistryEntry)fusionItemDreadSpawn);
      GameData.register_impl((IForgeRegistryEntry)fusionItemDepthsGhoul);
      GameData.register_impl((IForgeRegistryEntry)fusionItemShadowCreature);
      GameData.register_impl((IForgeRegistryEntry)fusionItemGreaterDreadSpawn);
      GameData.register_impl((IForgeRegistryEntry)fusionItemOmotholGhoul);
      GameData.register_impl((IForgeRegistryEntry)fusionItemShadowMonster);
      GameData.register_impl((IForgeRegistryEntry)fusionItemShoggoth);
      GameData.register_impl((IForgeRegistryEntry)fusionItemRemnant);
      GameData.register_impl((IForgeRegistryEntry)fusionItemSpectralDragon);
      GameData.register_impl((IForgeRegistryEntry)fusionItemAsorah);
      GameData.register_impl((IForgeRegistryEntry)fusionItemChagaroth);
      GameData.register_impl((IForgeRegistryEntry)fusionItemDreadguard);
      GameData.register_impl((IForgeRegistryEntry)fusionItemGatekeeperMinion);
      GameData.register_impl((IForgeRegistryEntry)fusionItemLesserDreadbeast);
      GameData.register_impl((IForgeRegistryEntry)fusionItemShadowBeast);
      GameData.register_impl((IForgeRegistryEntry)fusionItemSkeletonGoliath);
      GameData.register_impl((IForgeRegistryEntry)fusionItemSacthoth);
      GameData.register_impl((IForgeRegistryEntry)fusionItemJzahar);
      GameData.register_impl((IForgeRegistryEntry)abyssalZombieItem);
      GameData.register_impl((IForgeRegistryEntry)abyssalniteGolemItem);
      GameData.register_impl((IForgeRegistryEntry)chagarothSpawnItem);
      GameData.register_impl((IForgeRegistryEntry)chagarothFistItem);
      GameData.register_impl((IForgeRegistryEntry)coraliumSquidItem);
      GameData.register_impl((IForgeRegistryEntry)dreadAbyssalniteGolemItem);
      GameData.register_impl((IForgeRegistryEntry)dreadlingItem);
      GameData.register_impl((IForgeRegistryEntry)dreadSpawnItem);
      GameData.register_impl((IForgeRegistryEntry)depthsGhoulItem);
      GameData.register_impl((IForgeRegistryEntry)shadowCreatureItem);
      GameData.register_impl((IForgeRegistryEntry)greaterDreadSpawnItem);
      GameData.register_impl((IForgeRegistryEntry)omotholGhoulItem);
      GameData.register_impl((IForgeRegistryEntry)remnantItem);
      GameData.register_impl((IForgeRegistryEntry)shadowMonsterItem);
      GameData.register_impl((IForgeRegistryEntry)shoggothItem);
      GameData.register_impl((IForgeRegistryEntry)spectralDragonItem);
      GameData.register_impl((IForgeRegistryEntry)asorahItem);
      GameData.register_impl((IForgeRegistryEntry)chagarothItem);
      GameData.register_impl((IForgeRegistryEntry)dreadguardItem);
      GameData.register_impl((IForgeRegistryEntry)gatekeeperminionItem);
      GameData.register_impl((IForgeRegistryEntry)lesserDreadbeastItem);
      GameData.register_impl((IForgeRegistryEntry)skeletonGoliathItem);
      GameData.register_impl((IForgeRegistryEntry)shadowBeastItem);
      GameData.register_impl((IForgeRegistryEntry)sacthothItem);
      GameData.register_impl((IForgeRegistryEntry)jzaharItem);
    } 
  }
  
  public static void registerDracEvo() {
    if (Loader.isModLoaded("draconicevolution")) {
      GameData.register_impl((IForgeRegistryEntry)draconicPortalStaff);
      GameData.register_impl((IForgeRegistryEntry)fusionItemChaosGuardian);
      GameData.register_impl((IForgeRegistryEntry)chaosGuardianItem);
    } 
  }
  
  public static void registerMutant() {
    if (Loader.isModLoaded("mutantbeasts")) {
      GameData.register_impl((IForgeRegistryEntry)fusionPigSpider);
      GameData.register_impl((IForgeRegistryEntry)fusionMutantSnowGolem);
      GameData.register_impl((IForgeRegistryEntry)fusionMutantCreeper);
      GameData.register_impl((IForgeRegistryEntry)fusionMutantEnderman);
      GameData.register_impl((IForgeRegistryEntry)fusionMutantSkeleton);
      GameData.register_impl((IForgeRegistryEntry)fusionMutantZombie);
      GameData.register_impl((IForgeRegistryEntry)pigSpiderItem);
      GameData.register_impl((IForgeRegistryEntry)mutantSnowGolemItem);
      GameData.register_impl((IForgeRegistryEntry)mutantCreeperItem);
      GameData.register_impl((IForgeRegistryEntry)mutantEndermanItem);
      GameData.register_impl((IForgeRegistryEntry)mutantSkeletonItem);
      GameData.register_impl((IForgeRegistryEntry)mutantZombieItem);
    } 
  }
  
  public static void registerRenders() {
    ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
    int i;
    for (i = 0; i <= 9; i++)
      registerItemSubbed(renderItem, (Item)manaContainer, i); 
    for (i = 0; i <= 9; i++)
      registerItemSubbed(renderItem, entropyContainer, i);
    registerItem(renderItem, artifact1);
    registerItem(renderItem, witheredNetherStar);
    registerItem(renderItem, statChecker);
    registerItem(renderItem, carrier);
    registerItem(renderItem, weddingring);
    registerItem(renderItem, heromaker);
    registerItem(renderItem, lastchance);
    registerItem(renderItem, trainingstick);
    registerItem(renderItem, blowhorn);
    registerItem(renderItem, blowhorn2);
    for (i = 0; i <= 4; i++)
      registerItemSubbed(renderItem, convertingStaff, i); 
    for (i = 0; i <= 4; i++)
      registerItemSubbed(renderItem, summoningStaff, i); 
    for (i = 0; i <= 4; i++)
      registerItemSubbed(renderItem, commandingStaff, i); 
    for (i = 0; i <= 4; i++)
      registerItemSubbed(renderItem, portalStaff, i); 
    registerItem(renderItem, (Item)learningBookBasic);
    registerItem(renderItem, (Item)learningBookBasicCombat);
    registerItem(renderItem, (Item)learningBookBasicCooking);
    registerItem(renderItem, (Item)learningBookBasicExercise);
    registerItem(renderItem, (Item)learningBookBasicKnowledge);
    registerItem(renderItem, (Item)learningBookModern);
    registerItem(renderItem, (Item)learningBookModernCombat);
    registerItem(renderItem, (Item)learningBookModernBrute);
    registerItem(renderItem, (Item)learningBookModernCooking);
    registerItem(renderItem, (Item)learningBookModernEating);
    registerItem(renderItem, (Item)learningBookModernExercise);
    registerItem(renderItem, (Item)learningBookModernRunning);
    registerItem(renderItem, (Item)learningBookModernKnowledge);
    registerItem(renderItem, (Item)learningBookModernPacifist);
    registerItem(renderItem, (Item)learningBookAdvanced);
    registerItem(renderItem, (Item)learningBookAdvancedCombat);
    registerItem(renderItem, (Item)learningBookAdvancedBrute);
    registerItem(renderItem, (Item)learningBookAdvancedWarrior);
    registerItem(renderItem, (Item)learningBookAdvancedCooking);
    registerItem(renderItem, (Item)learningBookAdvancedEating);
    registerItem(renderItem, (Item)learningBookAdvancedDieting);
    registerItem(renderItem, (Item)learningBookAdvancedExercise);
    registerItem(renderItem, (Item)learningBookAdvancedRunning);
    registerItem(renderItem, (Item)learningBookAdvancedLifting);
    registerItem(renderItem, (Item)learningBookAdvancedKnowledge);
    registerItem(renderItem, (Item)learningBookAdvancedPacifist);
    registerItem(renderItem, (Item)learningBookAdvancedWisdom);
    registerItem(renderItem, (Item)learningBookComplex);
    registerItem(renderItem, (Item)learningBookComplexCombat);
    registerItem(renderItem, (Item)learningBookComplexBrute);
    registerItem(renderItem, (Item)learningBookComplexWarrior);
    registerItem(renderItem, (Item)learningBookComplexCooking);
    registerItem(renderItem, (Item)learningBookComplexEating);
    registerItem(renderItem, (Item)learningBookComplexDieting);
    registerItem(renderItem, (Item)learningBookComplexExercise);
    registerItem(renderItem, (Item)learningBookComplexRunning);
    registerItem(renderItem, (Item)learningBookComplexLifting);
    registerItem(renderItem, (Item)learningBookComplexKnowledge);
    registerItem(renderItem, (Item)learningBookComplexPacifist);
    registerItem(renderItem, (Item)learningBookComplexWisdom);
    registerItem(renderItem, (Item)learningBookMaster);
    registerItem(renderItem, (Item)learningBookMasterCombat);
    registerItem(renderItem, (Item)learningBookMasterBrute);
    registerItem(renderItem, (Item)learningBookMasterWarrior);
    registerItem(renderItem, (Item)learningBookMasterCooking);
    registerItem(renderItem, (Item)learningBookMasterEating);
    registerItem(renderItem, (Item)learningBookMasterDieting);
    registerItem(renderItem, (Item)learningBookMasterExercise);
    registerItem(renderItem, (Item)learningBookMasterRunning);
    registerItem(renderItem, (Item)learningBookMasterLifting);
    registerItem(renderItem, (Item)learningBookMasterKnowledge);
    registerItem(renderItem, (Item)learningBookMasterPacifist);
    registerItem(renderItem, (Item)learningBookMasterWisdom);
    registerItem(renderItem, (Item)learningBookArtifactStrength);
    registerItem(renderItem, (Item)learningBookArtifactStamina);
    registerItem(renderItem, (Item)learningBookArtifactSpeed);
    registerItem(renderItem, (Item)learningBookArtifactIntellegence);
    registerItem(renderItem, (Item)learningBookArtifact);
    registerItem(renderItem, (Item)fusionItemBat);
    registerItem(renderItem, (Item)fusionItemChicken);
    registerItem(renderItem, (Item)fusionItemCow);
    registerItem(renderItem, (Item)fusionItemMooshroom);
    registerItem(renderItem, (Item)fusionItemOzelot);
    registerItem(renderItem, (Item)fusionItemParrot);
    registerItem(renderItem, (Item)fusionItemPig);
    registerItem(renderItem, (Item)fusionItemRabbit);
    registerItem(renderItem, (Item)fusionItemSheep);
    registerItem(renderItem, (Item)fusionItemSquid);
    registerItem(renderItem, (Item)fusionItemEndermite);
    registerItem(renderItem, (Item)fusionItemLlama);
    registerItem(renderItem, (Item)fusionItemSilverfish);
    registerItem(renderItem, (Item)fusionItemSnowman);
    registerItem(renderItem, (Item)fusionItemVillager);
    registerItem(renderItem, (Item)fusionItemWolf);
    registerItem(renderItem, (Item)fusionItemCreeper);
    registerItem(renderItem, (Item)fusionItemMagmaCube);
    registerItem(renderItem, (Item)fusionItemPolarBear);
    registerItem(renderItem, (Item)fusionItemPrisonSlime);
    registerItem(renderItem, (Item)fusionItemSkeleton);
    registerItem(renderItem, (Item)fusionItemSlime);
    registerItem(renderItem, (Item)fusionItemSpider);
    registerItem(renderItem, (Item)fusionItemZombie);
    registerItem(renderItem, (Item)fusionItemVex);
    registerItem(renderItem, (Item)fusionItemSpiderJockey);
    registerItem(renderItem, (Item)fusionItemChickenJockey);
    registerItem(renderItem, (Item)fusionItemBlaze);
    registerItem(renderItem, (Item)fusionItemCaveSpider);
    registerItem(renderItem, (Item)fusionItemCreeder);
    registerItem(renderItem, (Item)fusionItemEnderman);
    registerItem(renderItem, (Item)fusionItemGhast);
    registerItem(renderItem, (Item)fusionItemGuardian);
    registerItem(renderItem, (Item)fusionItemHusk);
    registerItem(renderItem, (Item)fusionItemIceSpider);
    registerItem(renderItem, (Item)fusionItemIcyEnderCreeper);
    registerItem(renderItem, (Item)fusionItemKillerRabbit);
    registerItem(renderItem, (Item)fusionItemPrisonSpider);
    registerItem(renderItem, (Item)fusionItemPrisonZombie);
    registerItem(renderItem, (Item)fusionItemShulker);
    registerItem(renderItem, (Item)fusionItemStray);
    registerItem(renderItem, (Item)fusionItemVindicator);
    registerItem(renderItem, (Item)fusionItemWitch);
    registerItem(renderItem, (Item)fusionItemWitherSkeleton);
    registerItem(renderItem, (Item)fusionItemPigZombie);
    registerItem(renderItem, (Item)fusionItemAbomniableSnowman);
    registerItem(renderItem, (Item)fusionItemElderGuardian);
    registerItem(renderItem, (Item)fusionItemEnderDragon);
    registerItem(renderItem, (Item)fusionItemEversource);
    registerItem(renderItem, (Item)fusionItemEvoker);
    registerItem(renderItem, (Item)fusionItemGhasther);
    registerItem(renderItem, (Item)fusionItemGiant);
    registerItem(renderItem, (Item)fusionItemIceGolem);
    registerItem(renderItem, (Item)fusionItemIllusioner);
    registerItem(renderItem, (Item)fusionItemVillagerGolem);
    registerItem(renderItem, (Item)fusionItemMagmaGolem);
    registerItem(renderItem, (Item)fusionItemPrisonGolem);
    registerItem(renderItem, (Item)fusionItemSkeletonTrap);
    registerItem(renderItem, (Item)fusionItemWither);
    registerItem(renderItem, (Item)fusionItemWitherStorm);
    registerItem(renderItem, (Item)batItem);
    registerItem(renderItem, (Item)chickenItem);
    registerItem(renderItem, (Item)cowItem);
    registerItem(renderItem, (Item)mooshroomItem);
    registerItem(renderItem, (Item)ozelotItem);
    registerItem(renderItem, (Item)parrotItem);
    registerItem(renderItem, (Item)pigItem);
    registerItem(renderItem, (Item)rabbitItem);
    registerItem(renderItem, (Item)sheepItem);
    registerItem(renderItem, (Item)endermiteItem);
    registerItem(renderItem, (Item)llamaItem);
    registerItem(renderItem, (Item)silverfishItem);
    registerItem(renderItem, (Item)snowmanItem);
    registerItem(renderItem, (Item)squidItem);
    registerItem(renderItem, (Item)villagerItem);
    registerItem(renderItem, (Item)wolfItem);
    registerItem(renderItem, (Item)chickenjockeyItem);
    registerItem(renderItem, (Item)creeperItem);
    registerItem(renderItem, (Item)magmacubeItem);
    registerItem(renderItem, (Item)polarBearItem);
    registerItem(renderItem, (Item)prisonSlimeItem);
    registerItem(renderItem, (Item)skeletonItem);
    registerItem(renderItem, (Item)slimeItem);
    registerItem(renderItem, (Item)spiderItem);
    registerItem(renderItem, (Item)spiderjockeyItem);
    registerItem(renderItem, (Item)vexItem);
    registerItem(renderItem, (Item)zombieItem);
    registerItem(renderItem, (Item)batItem);
    registerItem(renderItem, (Item)blazeItem);
    registerItem(renderItem, (Item)cavespiderItem);
    registerItem(renderItem, (Item)creederItem);
    registerItem(renderItem, (Item)endermanItem);
    registerItem(renderItem, (Item)ghastItem);
    registerItem(renderItem, (Item)guardianItem);
    registerItem(renderItem, (Item)huskItem);
    registerItem(renderItem, (Item)iceSpiderItem);
    registerItem(renderItem, (Item)icyEnderCreeperItem);
    registerItem(renderItem, (Item)killerrabbitItem);
    registerItem(renderItem, (Item)prisonSpiderItem);
    registerItem(renderItem, (Item)prisonZombieItem);
    registerItem(renderItem, (Item)shulkerItem);
    registerItem(renderItem, (Item)strayItem);
    registerItem(renderItem, (Item)vindicatorItem);
    registerItem(renderItem, (Item)witchItem);
    registerItem(renderItem, (Item)witherskeletonItem);
    registerItem(renderItem, (Item)pigzombieItem);
    registerItem(renderItem, (Item)abomniableSnowmanItem);
    registerItem(renderItem, (Item)elderguardianItem);
    registerItem(renderItem, (Item)enderdragonItem);
    registerItem(renderItem, (Item)eversourceItem);
    registerItem(renderItem, (Item)evokerItem);
    registerItem(renderItem, (Item)ghastherItem);
    registerItem(renderItem, (Item)giantItem);
    registerItem(renderItem, (Item)iceGolemItem);
    registerItem(renderItem, (Item)illusionerItem);
    registerItem(renderItem, (Item)villagergolemItem);
    registerItem(renderItem, (Item)magmaGolemItem);
    registerItem(renderItem, (Item)prisonGolemItem);
    registerItem(renderItem, (Item)the4horsemenItem);
    registerItem(renderItem, (Item)witherItem);
    registerItem(renderItem, (Item)witherStormItem);
  }
  
  public static void registerAbyRenders() {
    if (Loader.isModLoaded("abyssalcraft")) {
      ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
      registerItem(renderItem, peGunLevel1);
      registerItem(renderItem, peGunLevel2);
      registerItem(renderItem, peGunLevel3);
      registerItem(renderItem, peGunLevel4);
      registerItem(renderItem, peGunLevel5);
      for (int i = 0; i <= 4; i++)
        registerItemSubbed(renderItem, abyssalPortalStaff, i); 
      registerItem(renderItem, (Item)fusionItemAbyssalZombie);
      registerItem(renderItem, (Item)fusionItemAbyssalniteGolem);
      registerItem(renderItem, (Item)fusionItemChagarothSpawn);
      registerItem(renderItem, (Item)fusionItemChagarothFist);
      registerItem(renderItem, (Item)fusionItemCoraliumSquid);
      registerItem(renderItem, (Item)fusionItemDreadAbyssalniteGolem);
      registerItem(renderItem, (Item)fusionItemDreadling);
      registerItem(renderItem, (Item)fusionItemDreadSpawn);
      registerItem(renderItem, (Item)fusionItemDepthsGhoul);
      registerItem(renderItem, (Item)fusionItemShadowCreature);
      registerItem(renderItem, (Item)fusionItemGreaterDreadSpawn);
      registerItem(renderItem, (Item)fusionItemOmotholGhoul);
      registerItem(renderItem, (Item)fusionItemShadowMonster);
      registerItem(renderItem, (Item)fusionItemShoggoth);
      registerItem(renderItem, (Item)fusionItemRemnant);
      registerItem(renderItem, (Item)fusionItemSpectralDragon);
      registerItem(renderItem, (Item)fusionItemAsorah);
      registerItem(renderItem, (Item)fusionItemChagaroth);
      registerItem(renderItem, (Item)fusionItemDreadguard);
      registerItem(renderItem, (Item)fusionItemGatekeeperMinion);
      registerItem(renderItem, (Item)fusionItemLesserDreadbeast);
      registerItem(renderItem, (Item)fusionItemShadowBeast);
      registerItem(renderItem, (Item)fusionItemSkeletonGoliath);
      registerItem(renderItem, (Item)fusionItemSacthoth);
      registerItem(renderItem, (Item)fusionItemJzahar);
      registerItem(renderItem, (Item)abyssalZombieItem);
      registerItem(renderItem, (Item)abyssalniteGolemItem);
      registerItem(renderItem, (Item)chagarothSpawnItem);
      registerItem(renderItem, (Item)chagarothFistItem);
      registerItem(renderItem, (Item)coraliumSquidItem);
      registerItem(renderItem, (Item)dreadAbyssalniteGolemItem);
      registerItem(renderItem, (Item)depthsGhoulItem);
      registerItem(renderItem, (Item)dreadlingItem);
      registerItem(renderItem, (Item)dreadSpawnItem);
      registerItem(renderItem, (Item)shadowCreatureItem);
      registerItem(renderItem, (Item)greaterDreadSpawnItem);
      registerItem(renderItem, (Item)omotholGhoulItem);
      registerItem(renderItem, (Item)remnantItem);
      registerItem(renderItem, (Item)shadowMonsterItem);
      registerItem(renderItem, (Item)shoggothItem);
      registerItem(renderItem, (Item)spectralDragonItem);
      registerItem(renderItem, (Item)asorahItem);
      registerItem(renderItem, (Item)chagarothItem);
      registerItem(renderItem, (Item)dreadguardItem);
      registerItem(renderItem, (Item)gatekeeperminionItem);
      registerItem(renderItem, (Item)lesserDreadbeastItem);
      registerItem(renderItem, (Item)shadowBeastItem);
      registerItem(renderItem, (Item)skeletonGoliathItem);
      registerItem(renderItem, (Item)sacthothItem);
      registerItem(renderItem, (Item)jzaharItem);
    } 
  }
  
  public static void registerDracRenders() {
    if (Loader.isModLoaded("draconicevolution")) {
      ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
      for (int i = 0; i <= 4; i++)
        registerItemSubbed(renderItem, draconicPortalStaff, i); 
      registerItem(renderItem, (Item)fusionItemChaosGuardian);
      registerItem(renderItem, (Item)chaosGuardianItem);
    } 
  }
  
  public static void registerMutantRenders() {
    if (Loader.isModLoaded("mutantbeasts")) {
      ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
      registerItem(renderItem, (Item)fusionPigSpider);
      registerItem(renderItem, (Item)fusionMutantSnowGolem);
      registerItem(renderItem, (Item)fusionMutantCreeper);
      registerItem(renderItem, (Item)fusionMutantEnderman);
      registerItem(renderItem, (Item)fusionMutantSkeleton);
      registerItem(renderItem, (Item)fusionMutantZombie);
      registerItem(renderItem, (Item)pigSpiderItem);
      registerItem(renderItem, (Item)mutantSnowGolemItem);
      registerItem(renderItem, (Item)mutantCreeperItem);
      registerItem(renderItem, (Item)mutantEndermanItem);
      registerItem(renderItem, (Item)mutantSkeletonItem);
      registerItem(renderItem, (Item)mutantZombieItem);
    } 
  }
  
  public static void createLearningBook(Item book, int tier, String name, String description, int durability, int experience, float strength, float stamina, float intelegence, float dexterity, float agility) {
    ItemLearningBook itemLearningBook = new ItemLearningBook(tier, name, description, durability, experience, strength, stamina, intelegence, dexterity, agility);
  }
  
  public static void registerItem(ItemModelMesher renderItem, Item item) {
    registerItemSubbed(renderItem, item, 0);
  }
  
  public static void registerItemSubbed(ItemModelMesher renderItem, Item item, int meta) {
    renderItem.register(item, meta, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
  }
}
