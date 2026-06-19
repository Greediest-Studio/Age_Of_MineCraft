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

  public static Item heromaker;
  
  public static Item lastchance;
  
  public static Item trainingstick;
  
  public static Item blowhorn;
  
  public static Item blowhorn2;
  
  public static Item convertingStaff;
  
  public static Item summoningStaff;
  
  public static Item commandingStaff;
  
  public static Item portalStaff;
  
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
    witheredNetherStar = (new ItemSimpleFoiled()).setCreativeTab(ETab.engender).setTranslationKey("withered_nether_star").setRegistryName("withered_nether_star");
    statChecker = new ItemEngenderStatChecker();
    carrier = new ItemCarrier();
    heromaker = new ItemHeroMaker();
    lastchance = new ItemLastChance();
    trainingstick = new ItemTrainingStick();
    blowhorn = new ItemMoralHorn();
    blowhorn2 = new ItemDragonsHorn();
    convertingStaff = new ItemConvertingStaff();
    summoningStaff = new ItemSummoningStaff();
    commandingStaff = new ItemCommandingStaff();
    portalStaff = new ItemPortalStaff();
    batItem = new ItemBatItem();
    chickenItem = new ItemChickenItem();
    cowItem = new ItemCowItem();
    mooshroomItem = new ItemMooshroomItem();
    ozelotItem = new ItemOcelotItem();
    parrotItem = new ItemParrotItem();
    pigItem = new ItemPigItem();
    rabbitItem = new ItemRabbitItem();
    sheepItem = new ItemSheepItem();
    endermiteItem = new ItemEndermiteItem();
    llamaItem = new ItemLlamaItem();
    silverfishItem = new ItemSilverfishItem();
    snowmanItem = new ItemSnowmanItem();
    squidItem = new ItemSquidItem();
    villagerItem = new ItemVillagerItem();
    wolfItem = new ItemWolfItem();
    chickenjockeyItem = new ItemChickenJockeyItem();
    creeperItem = new ItemCreeperItem();
    magmacubeItem = new ItemMagmaCubeItem();
    polarBearItem = new ItemPolarBearItem();
    prisonSlimeItem = new ItemPrisonSlimeItem();
    skeletonItem = new ItemSkeletonItem();
    slimeItem = new ItemSlimeItem();
    spiderItem = new ItemSpiderItem();
    spiderjockeyItem = new ItemSpiderJockeyItem();
    vexItem = new ItemVexItem();
    zombieItem = new ItemZombieItem();
    blazeItem = new ItemBlazeItem();
    cavespiderItem = new ItemCaveSpiderItem();
    creederItem = new ItemCreederItem();
    endermanItem = new ItemEndermanItem();
    ghastItem = new ItemGhastItem();
    guardianItem = new ItemGuardianItem();
    huskItem = new ItemHuskItem();
    iceSpiderItem = new ItemIceSpiderItem();
    icyEnderCreeperItem = new ItemIcyEnderCreeperItem();
    killerrabbitItem = new ItemKillerBunnyItem();
    prisonSpiderItem = new ItemPrisonSpiderItem();
    prisonZombieItem = new ItemPrisonZombieItem();
    shulkerItem = new ItemShulkerItem();
    strayItem = new ItemStrayItem();
    vindicatorItem = new ItemVindicatorItem();
    witchItem = new ItemWitchItem();
    witherskeletonItem = new ItemWitherSkeletonItem();
    pigzombieItem = new ItemPigZombieItem();
    abomniableSnowmanItem = new ItemAbomniableSnowmanItem();
    elderguardianItem = new ItemElderGuardianItem();
    enderdragonItem = new ItemEnderDragonItem();
    eversourceItem = new ItemEversourceItem();
    evokerItem = new ItemEvokerItem();
    ghastherItem = new ItemGhastherItem();
    giantItem = new ItemGiantItem();
    iceGolemItem = new ItemIceGolemItem();
    illusionerItem = new ItemIllusionerItem();
    villagergolemItem = new ItemIronGolemItem();
    magmaGolemItem = new ItemMagmaGolemItem();
    prisonGolemItem = new ItemPrisonGolemItem();
    the4horsemenItem = new ItemSkeletonTrapItem();
    witherItem = new ItemWitherItem();
    witherStormItem = new ItemWitherStormItem();
    fusionItemBat = new ItemFusionVanilla(batItem, "fusionbat", 1, 0);
    fusionItemChicken = new ItemFusionVanilla(chickenItem, "fusionchicken", 1, 0);
    fusionItemCow = new ItemFusionVanilla(cowItem, "fusioncow", 2, 0);
    fusionItemMooshroom = new ItemFusionVanilla(mooshroomItem, "fusionmooshroom", 4, 0);
    fusionItemOzelot = new ItemFusionVanilla(ozelotItem, "fusionozelot", 2, 0);
    fusionItemParrot = new ItemFusionVanilla(parrotItem, "fusionparrot", 1, 0);
    fusionItemPig = new ItemFusionVanilla(pigItem, "fusionpig", 2, 0);
    fusionItemRabbit = new ItemFusionVanilla(rabbitItem, "fusionrabbit", 1, 0);
    fusionItemSheep = new ItemFusionVanilla(sheepItem, "fusionsheep", 3, 0);
    fusionItemEndermite = new ItemFusionVanilla(endermiteItem, "fusionendermite", 5, 0);
    fusionItemLlama = new ItemFusionVanilla(llamaItem, "fusionllama", 4, 0);
    fusionItemSilverfish = new ItemFusionVanilla(silverfishItem, "fusionsilverfish", 5, 0);
    fusionItemSnowman = new ItemFusionVanilla(snowmanItem, "fusionsnowman", 4, 0);
    fusionItemSquid = new ItemFusionVanilla(squidItem, "fusionsquid", 6, 0);
    fusionItemVillager = new ItemFusionVanilla(villagerItem, "fusionvillager", 15, 0);
    fusionItemWolf = new ItemFusionVanilla(wolfItem, "fusionwolf", 6, 0);
    fusionItemChickenJockey = new ItemFusionVanilla(chickenjockeyItem, "fusionchickenjockey", 25, 0);
    fusionItemCreeper = new ItemFusionVanilla(creeperItem, "fusioncreeper", 25, 0);
    fusionItemMagmaCube = new ItemFusionVanilla(magmacubeItem, "fusionmagmacube", 10, 0);
    fusionItemPolarBear = new ItemFusionVanilla(polarBearItem, "fusionpolarbear", 30, 0);
    fusionItemPrisonSlime = new ItemFusionMCSM(prisonSlimeItem, "fusionprisonslime", 12, 0);
    fusionItemSkeleton = new ItemFusionVanilla(skeletonItem, "fusionskeleton", 20, 0);
    fusionItemSlime = new ItemFusionVanilla(slimeItem, "fusionslime", 8, 0);
    fusionItemSpider = new ItemFusionVanilla(spiderItem, "fusionspider", 8, 0);
    fusionItemSpiderJockey = new ItemFusionVanilla(spiderjockeyItem, "fusionspiderjockey", 32, 0);
    fusionItemVex = new ItemFusionVanilla(vexItem, "fusionvex", 15, 0);
    fusionItemZombie = new ItemFusionVanilla(zombieItem, "fusionzombie", 20, 0);
    fusionItemBlaze = new ItemFusionVanilla(blazeItem, "fusionblaze", 75, 0);
    fusionItemCaveSpider = new ItemFusionVanilla(cavespiderItem, "fusioncavespider", 40, 0);
    fusionItemCreeder = new ItemFusionMCSM(creederItem, "fusioncreeder", 100, 0);
    fusionItemEnderman = new ItemFusionVanilla(endermanItem, "fusionenderman", 150, 0);
    fusionItemGhast = new ItemFusionVanilla(ghastItem, "fusionghast", 250, 0);
    fusionItemGuardian = new ItemFusionVanilla(guardianItem, "fusionguardian", 120, 0);
    fusionItemHusk = new ItemFusionVanilla(huskItem, "fusionhusk", 40, 0);
    fusionItemIceSpider = new ItemFusionMCSM(iceSpiderItem, "fusionicespider", 30, 0);
    fusionItemIcyEnderCreeper = new ItemFusionMCSM(icyEnderCreeperItem, "fusionicyendercreeper", 100, 0);
    fusionItemKillerRabbit = new ItemFusionVanilla(killerrabbitItem, "fusionkillerbunny", 200, 0);
    fusionItemPrisonSpider = new ItemFusionMCSM(prisonSpiderItem, "fusionprisonspider", 50, 0);
    fusionItemPrisonZombie = new ItemFusionMCSM(prisonZombieItem, "fusionprisonzombie", 60, 0);
    fusionItemShulker = new ItemFusionVanilla(shulkerItem, "fusionshulker", 200, 0);
    fusionItemStray = new ItemFusionVanilla(strayItem, "fusionstray", 80, 0);
    fusionItemVindicator = new ItemFusionVanilla(vindicatorItem, "fusionvindicator", 150, 0);
    fusionItemWitch = new ItemFusionVanilla(witchItem, "fusionwitch", 100, 0);
    fusionItemWitherSkeleton = new ItemFusionVanilla(witherskeletonItem, "fusionwitherskeleton", 125, 0);
    fusionItemPigZombie = new ItemFusionVanilla(pigzombieItem, "fusionpigzombie", 80, 0);
    fusionItemAbomniableSnowman = new ItemFusionVanilla(abomniableSnowmanItem, "fusionabomniablesnowman", 2500, 50);
    fusionItemElderGuardian = new ItemFusionVanilla(elderguardianItem, "fusionelderguardian", 1500, 20);
    fusionItemEnderDragon = new ItemFusionVanilla(enderdragonItem, "fusionenderdragon", 24000, 500);
    fusionItemEversource = new ItemFusionMCSM(eversourceItem, "fusioneversource", 5000, 1000);
    fusionItemEvoker = new ItemFusionVanilla(evokerItem, "fusionevoker", 6000, 350);
    fusionItemGhasther = new ItemFusionVanilla(ghastherItem, "fusionghasther", 4000, 150);
    fusionItemGiant = new ItemFusionVanilla(giantItem, "fusiongiant", 2000, 30);
    fusionItemMagmaGolem = new ItemFusionMCSM(magmaGolemItem, "fusionmagmagolem", 1000, 10);
    fusionItemIceGolem = new ItemFusionMCSM(iceGolemItem, "fusionicegolem", 800, 10);
    fusionItemIllusioner = new ItemFusionVanilla(illusionerItem, "fusionillusioner", 6000, 350);
    fusionItemVillagerGolem = new ItemFusionVanilla(villagergolemItem, "fusionirongolem", 1500, 20);
    fusionItemPrisonGolem = new ItemFusionMCSM(prisonGolemItem, "fusionprisongolem", 1600, 20);
    fusionItemSkeletonTrap = new ItemFusionVanilla(the4horsemenItem, "fusionskeletontrap", 2400, 80);
    fusionItemWither = new ItemFusionVanilla(witherItem, "fusionwither", 12000, 750);
    fusionItemWitherStorm = new ItemFusionMCSM(witherStormItem, "fusionwitherstorm", 100000, 5000);
  }
  
  public static void initAby() {
    if (Loader.isModLoaded("abyssalcraft")) {
      peGunLevel1 = new ItemPEGun("pegun");
      peGunLevel2 = new ItemPEGun("pegun1");
      peGunLevel3 = new ItemPEGun("pegun2");
      peGunLevel4 = new ItemPEGun("pegun3");
      peGunLevel5 = new ItemPEGun("pegun4");
      abyssalPortalStaff = new ItemAbyssalPortalStaff();
      abyssalZombieItem = new ItemAbyssalZombieItem();
      abyssalniteGolemItem = new ItemAbygolemItem();
      dreadAbyssalniteGolemItem = new ItemDreadgolemItem();
      dreadlingItem = new ItemDreadlingItem();
      dreadSpawnItem = new ItemDreadSpawnItem();
      chagarothSpawnItem = new ItemChagarothSpawnItem();
      chagarothFistItem = new ItemChagarothFistItem();
      coraliumSquidItem = new ItemCoraliumSquidItem();
      depthsGhoulItem = new ItemDepthsGhoulItem();
      shadowCreatureItem = new ItemShadowCreatureItem();
      greaterDreadSpawnItem = new ItemGreaterDreadSpawnItem();
      omotholGhoulItem = new ItemOmotholGhoulItem();
      remnantItem = new ItemRemnantItem();
      shadowMonsterItem = new ItemShadowMonsterItem();
      shoggothItem = new ItemLesserShoggothItem();
      spectralDragonItem = new ItemSpectralDragonItem();
      asorahItem = new ItemAsorahItem();
      chagarothItem = new ItemChagarothItem();
      dreadguardItem = new ItemDreadguardItem();
      gatekeeperminionItem = new ItemGatekeeperMinionItem();
      lesserDreadbeastItem = new ItemLesserDreadbeastItem();
      shadowBeastItem = new ItemShadowBeastItem();
      skeletonGoliathItem = new ItemSkeletonGoliathItem();
      sacthothItem = new ItemSacthothItem();
      jzaharItem = new ItemJzaharItem();
      fusionItemAbyssalZombie = new ItemFusionAby(abyssalZombieItem, "fusionabyssalzombie", 40, 0);
      fusionItemAbyssalniteGolem = new ItemFusionAby(abyssalniteGolemItem, "fusionabygolem", 30, 0);
      fusionItemChagarothSpawn = new ItemFusionAby(chagarothSpawnItem, "fusionchagarothspawn", 20, 0);
      fusionItemChagarothFist = new ItemFusionAby(chagarothFistItem, "fusionchagarothfist", 60, 0);
      fusionItemCoraliumSquid = new ItemFusionAby(coraliumSquidItem, "fusioncoraliumsquid", 40, 0);
      fusionItemDreadAbyssalniteGolem = new ItemFusionAby(dreadAbyssalniteGolemItem, "fusiondreadgolem", 40, 0);
      fusionItemDreadling = new ItemFusionAby(dreadlingItem, "fusiondreadling", 20, 0);
      fusionItemDreadSpawn = new ItemFusionAby(dreadSpawnItem, "fusiondreadspawn", 10, 0);
      fusionItemDepthsGhoul = new ItemFusionAby(depthsGhoulItem, "fusiondepthsghoul", 20, 0);
      fusionItemShadowCreature = new ItemFusionAby(shadowCreatureItem, "fusionshadowcreature", 30, 0);
      fusionItemGreaterDreadSpawn = new ItemFusionAby(greaterDreadSpawnItem, "fusiongreaterdreadspawn", 100, 0);
      fusionItemOmotholGhoul = new ItemFusionAby(omotholGhoulItem, "fusionomotholghoul", 400, 0);
      fusionItemShadowMonster = new ItemFusionAby(shadowMonsterItem, "fusionshadowmonster", 100, 0);
      fusionItemShoggoth = new ItemFusionAby(shoggothItem, "fusionshoggoth", 800, 0);
      fusionItemRemnant = new ItemFusionAby(remnantItem, "fusionremnant", 300, 0);
      fusionItemSpectralDragon = new ItemFusionAby(spectralDragonItem, "fusionspectraldragon", 500, 0);
      fusionItemAsorah = new ItemFusionAby(asorahItem, "fusionasorah", 24000, 500);
      fusionItemChagaroth = new ItemFusionAby(chagarothItem, "fusionchagaroth", 20000, 2000);
      fusionItemDreadguard = new ItemFusionAby(dreadguardItem, "fusiondreadguard", 2000, 50);
      fusionItemGatekeeperMinion = new ItemFusionAby(gatekeeperminionItem, "fusiongatekeeperminion", 5000, 100);
      fusionItemLesserDreadbeast = new ItemFusionAby(lesserDreadbeastItem, "fusionlesserdreadbeast", 6000, 50);
      fusionItemShadowBeast = new ItemFusionAby(shadowBeastItem, "fusionshadowbeast", 2000, 20);
      fusionItemSkeletonGoliath = new ItemFusionAby(skeletonGoliathItem, "fusiongskeleton", 1000, 10);
      fusionItemSacthoth = new ItemFusionAby(sacthothItem, "fusionsacthoth", 16000, 200);
      fusionItemJzahar = new ItemFusionAby(jzaharItem, "fusionjzahar", 125000, 7500);
    } 
  }
  
  public static void initDracEvo() {
    if (Loader.isModLoaded("draconicevolution")) {
      draconicPortalStaff = new ItemDraconicPortalStaff();
      chaosGuardianItem = new ItemChaosGuardianItem();
      fusionItemChaosGuardian = new ItemFusionDrac(chaosGuardianItem, "fusionchaosguardian", 200000, 10000);
    } 
  }
  
  public static void initMutant() {
    if (Loader.isModLoaded("mutantbeasts")) {
      pigSpiderItem = new ItemSpiderPigItem();
      mutantSnowGolemItem = new ItemMutantSnowGolemItem();
      mutantCreeperItem = new ItemMutantCreeperItem();
      mutantSkeletonItem = new ItemMutantSkeletonItem();
      mutantZombieItem = new ItemMutantZombieItem();
      mutantEndermanItem = new ItemMutantEndermanItem();
      fusionPigSpider = new ItemFusionMutant(pigSpiderItem, "fusionspiderpig", 100, 0);
      fusionMutantSnowGolem = new ItemFusionMutant(mutantSnowGolemItem, "fusionmutantsnowgolem", 500, 0);
      fusionMutantCreeper = new ItemFusionMutant(mutantCreeperItem, "fusionmutantcreeper", 8000, 400);
      fusionMutantSkeleton = new ItemFusionMutant(mutantSkeletonItem, "fusionmutantskeleton", 12000, 600);
      fusionMutantZombie = new ItemFusionMutant(mutantZombieItem, "fusionmutantzombie", 16000, 800);
      fusionMutantEnderman = new ItemFusionMutant(mutantEndermanItem, "fusionmutantenderman", 20000, 1000);
    } 
  }
  
  public static void register() {
    GameData.register_impl((IForgeRegistryEntry)manaContainer);
    GameData.register_impl((IForgeRegistryEntry)entropyContainer);
    GameData.register_impl((IForgeRegistryEntry)artifact1);
    GameData.register_impl((IForgeRegistryEntry)witheredNetherStar);
    GameData.register_impl((IForgeRegistryEntry)statChecker);
    GameData.register_impl((IForgeRegistryEntry)carrier);
    GameData.register_impl((IForgeRegistryEntry)heromaker);
    GameData.register_impl((IForgeRegistryEntry)lastchance);
    GameData.register_impl((IForgeRegistryEntry)trainingstick);
    GameData.register_impl((IForgeRegistryEntry)blowhorn);
    GameData.register_impl((IForgeRegistryEntry)blowhorn2);
    GameData.register_impl((IForgeRegistryEntry)convertingStaff);
    GameData.register_impl((IForgeRegistryEntry)summoningStaff);
    GameData.register_impl((IForgeRegistryEntry)commandingStaff);
    GameData.register_impl((IForgeRegistryEntry)portalStaff);
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
      registerItemSubbed(renderItem, manaContainer, i);
    for (i = 0; i <= 9; i++)
      registerItemSubbed(renderItem, entropyContainer, i);
    registerItem(renderItem, artifact1);
    registerItem(renderItem, witheredNetherStar);
    registerItem(renderItem, statChecker);
    registerItem(renderItem, carrier);
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
    registerItem(renderItem, fusionItemBat);
    registerItem(renderItem, fusionItemChicken);
    registerItem(renderItem, fusionItemCow);
    registerItem(renderItem, fusionItemMooshroom);
    registerItem(renderItem, fusionItemOzelot);
    registerItem(renderItem, fusionItemParrot);
    registerItem(renderItem, fusionItemPig);
    registerItem(renderItem, fusionItemRabbit);
    registerItem(renderItem, fusionItemSheep);
    registerItem(renderItem, fusionItemSquid);
    registerItem(renderItem, fusionItemEndermite);
    registerItem(renderItem, fusionItemLlama);
    registerItem(renderItem, fusionItemSilverfish);
    registerItem(renderItem, fusionItemSnowman);
    registerItem(renderItem, fusionItemVillager);
    registerItem(renderItem, fusionItemWolf);
    registerItem(renderItem, fusionItemCreeper);
    registerItem(renderItem, fusionItemMagmaCube);
    registerItem(renderItem, fusionItemPolarBear);
    registerItem(renderItem, fusionItemPrisonSlime);
    registerItem(renderItem, fusionItemSkeleton);
    registerItem(renderItem, fusionItemSlime);
    registerItem(renderItem, fusionItemSpider);
    registerItem(renderItem, fusionItemZombie);
    registerItem(renderItem, fusionItemVex);
    registerItem(renderItem, fusionItemSpiderJockey);
    registerItem(renderItem, fusionItemChickenJockey);
    registerItem(renderItem, fusionItemBlaze);
    registerItem(renderItem, fusionItemCaveSpider);
    registerItem(renderItem, fusionItemCreeder);
    registerItem(renderItem, fusionItemEnderman);
    registerItem(renderItem, fusionItemGhast);
    registerItem(renderItem, fusionItemGuardian);
    registerItem(renderItem, fusionItemHusk);
    registerItem(renderItem, fusionItemIceSpider);
    registerItem(renderItem, fusionItemIcyEnderCreeper);
    registerItem(renderItem, fusionItemKillerRabbit);
    registerItem(renderItem, fusionItemPrisonSpider);
    registerItem(renderItem, fusionItemPrisonZombie);
    registerItem(renderItem, fusionItemShulker);
    registerItem(renderItem, fusionItemStray);
    registerItem(renderItem, fusionItemVindicator);
    registerItem(renderItem, fusionItemWitch);
    registerItem(renderItem, fusionItemWitherSkeleton);
    registerItem(renderItem, fusionItemPigZombie);
    registerItem(renderItem, fusionItemAbomniableSnowman);
    registerItem(renderItem, fusionItemElderGuardian);
    registerItem(renderItem, fusionItemEnderDragon);
    registerItem(renderItem, fusionItemEversource);
    registerItem(renderItem, fusionItemEvoker);
    registerItem(renderItem, fusionItemGhasther);
    registerItem(renderItem, fusionItemGiant);
    registerItem(renderItem, fusionItemIceGolem);
    registerItem(renderItem, fusionItemIllusioner);
    registerItem(renderItem, fusionItemVillagerGolem);
    registerItem(renderItem, fusionItemMagmaGolem);
    registerItem(renderItem, fusionItemPrisonGolem);
    registerItem(renderItem, fusionItemSkeletonTrap);
    registerItem(renderItem, fusionItemWither);
    registerItem(renderItem, fusionItemWitherStorm);
    registerItem(renderItem, batItem);
    registerItem(renderItem, chickenItem);
    registerItem(renderItem, cowItem);
    registerItem(renderItem, mooshroomItem);
    registerItem(renderItem, ozelotItem);
    registerItem(renderItem, parrotItem);
    registerItem(renderItem, pigItem);
    registerItem(renderItem, rabbitItem);
    registerItem(renderItem, sheepItem);
    registerItem(renderItem, endermiteItem);
    registerItem(renderItem, llamaItem);
    registerItem(renderItem, silverfishItem);
    registerItem(renderItem, snowmanItem);
    registerItem(renderItem, squidItem);
    registerItem(renderItem, villagerItem);
    registerItem(renderItem, wolfItem);
    registerItem(renderItem, chickenjockeyItem);
    registerItem(renderItem, creeperItem);
    registerItem(renderItem, magmacubeItem);
    registerItem(renderItem, polarBearItem);
    registerItem(renderItem, prisonSlimeItem);
    registerItem(renderItem, skeletonItem);
    registerItem(renderItem, slimeItem);
    registerItem(renderItem, spiderItem);
    registerItem(renderItem, spiderjockeyItem);
    registerItem(renderItem, vexItem);
    registerItem(renderItem, zombieItem);
    registerItem(renderItem, batItem);
    registerItem(renderItem, blazeItem);
    registerItem(renderItem, cavespiderItem);
    registerItem(renderItem, creederItem);
    registerItem(renderItem, endermanItem);
    registerItem(renderItem, ghastItem);
    registerItem(renderItem, guardianItem);
    registerItem(renderItem, huskItem);
    registerItem(renderItem, iceSpiderItem);
    registerItem(renderItem, icyEnderCreeperItem);
    registerItem(renderItem, killerrabbitItem);
    registerItem(renderItem, prisonSpiderItem);
    registerItem(renderItem, prisonZombieItem);
    registerItem(renderItem, shulkerItem);
    registerItem(renderItem, strayItem);
    registerItem(renderItem, vindicatorItem);
    registerItem(renderItem, witchItem);
    registerItem(renderItem, witherskeletonItem);
    registerItem(renderItem, pigzombieItem);
    registerItem(renderItem, abomniableSnowmanItem);
    registerItem(renderItem, elderguardianItem);
    registerItem(renderItem, enderdragonItem);
    registerItem(renderItem, eversourceItem);
    registerItem(renderItem, evokerItem);
    registerItem(renderItem, ghastherItem);
    registerItem(renderItem, giantItem);
    registerItem(renderItem, iceGolemItem);
    registerItem(renderItem, illusionerItem);
    registerItem(renderItem, villagergolemItem);
    registerItem(renderItem, magmaGolemItem);
    registerItem(renderItem, prisonGolemItem);
    registerItem(renderItem, the4horsemenItem);
    registerItem(renderItem, witherItem);
    registerItem(renderItem, witherStormItem);
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
      registerItem(renderItem, fusionItemAbyssalZombie);
      registerItem(renderItem, fusionItemAbyssalniteGolem);
      registerItem(renderItem, fusionItemChagarothSpawn);
      registerItem(renderItem, fusionItemChagarothFist);
      registerItem(renderItem, fusionItemCoraliumSquid);
      registerItem(renderItem, fusionItemDreadAbyssalniteGolem);
      registerItem(renderItem, fusionItemDreadling);
      registerItem(renderItem, fusionItemDreadSpawn);
      registerItem(renderItem, fusionItemDepthsGhoul);
      registerItem(renderItem, fusionItemShadowCreature);
      registerItem(renderItem, fusionItemGreaterDreadSpawn);
      registerItem(renderItem, fusionItemOmotholGhoul);
      registerItem(renderItem, fusionItemShadowMonster);
      registerItem(renderItem, fusionItemShoggoth);
      registerItem(renderItem, fusionItemRemnant);
      registerItem(renderItem, fusionItemSpectralDragon);
      registerItem(renderItem, fusionItemAsorah);
      registerItem(renderItem, fusionItemChagaroth);
      registerItem(renderItem, fusionItemDreadguard);
      registerItem(renderItem, fusionItemGatekeeperMinion);
      registerItem(renderItem, fusionItemLesserDreadbeast);
      registerItem(renderItem, fusionItemShadowBeast);
      registerItem(renderItem, fusionItemSkeletonGoliath);
      registerItem(renderItem, fusionItemSacthoth);
      registerItem(renderItem, fusionItemJzahar);
      registerItem(renderItem, abyssalZombieItem);
      registerItem(renderItem, abyssalniteGolemItem);
      registerItem(renderItem, chagarothSpawnItem);
      registerItem(renderItem, chagarothFistItem);
      registerItem(renderItem, coraliumSquidItem);
      registerItem(renderItem, dreadAbyssalniteGolemItem);
      registerItem(renderItem, depthsGhoulItem);
      registerItem(renderItem, dreadlingItem);
      registerItem(renderItem, dreadSpawnItem);
      registerItem(renderItem, shadowCreatureItem);
      registerItem(renderItem, greaterDreadSpawnItem);
      registerItem(renderItem, omotholGhoulItem);
      registerItem(renderItem, remnantItem);
      registerItem(renderItem, shadowMonsterItem);
      registerItem(renderItem, shoggothItem);
      registerItem(renderItem, spectralDragonItem);
      registerItem(renderItem, asorahItem);
      registerItem(renderItem, chagarothItem);
      registerItem(renderItem, dreadguardItem);
      registerItem(renderItem, gatekeeperminionItem);
      registerItem(renderItem, lesserDreadbeastItem);
      registerItem(renderItem, shadowBeastItem);
      registerItem(renderItem, skeletonGoliathItem);
      registerItem(renderItem, sacthothItem);
      registerItem(renderItem, jzaharItem);
    } 
  }
  
  public static void registerDracRenders() {
    if (Loader.isModLoaded("draconicevolution")) {
      ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
      for (int i = 0; i <= 4; i++)
        registerItemSubbed(renderItem, draconicPortalStaff, i); 
      registerItem(renderItem, fusionItemChaosGuardian);
      registerItem(renderItem, chaosGuardianItem);
    } 
  }
  
  public static void registerMutantRenders() {
    if (Loader.isModLoaded("mutantbeasts")) {
      ItemModelMesher renderItem = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
      registerItem(renderItem, fusionPigSpider);
      registerItem(renderItem, fusionMutantSnowGolem);
      registerItem(renderItem, fusionMutantCreeper);
      registerItem(renderItem, fusionMutantEnderman);
      registerItem(renderItem, fusionMutantSkeleton);
      registerItem(renderItem, fusionMutantZombie);
      registerItem(renderItem, pigSpiderItem);
      registerItem(renderItem, mutantSnowGolemItem);
      registerItem(renderItem, mutantCreeperItem);
      registerItem(renderItem, mutantEndermanItem);
      registerItem(renderItem, mutantSkeletonItem);
      registerItem(renderItem, mutantZombieItem);
    } 
  }
  
  public static void registerItem(ItemModelMesher renderItem, Item item) {
    registerItemSubbed(renderItem, item, 0);
  }
  
  public static void registerItemSubbed(ItemModelMesher renderItem, Item item, int meta) {
    renderItem.register(item, meta, new ModelResourceLocation("ageofminecraft:" + item.getTranslationKey().substring(5), "inventory"));
  }
}
