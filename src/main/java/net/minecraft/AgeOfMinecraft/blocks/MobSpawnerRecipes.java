package net.minecraft.AgeOfMinecraft.blocks;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

public class MobSpawnerRecipes {
  private static final MobSpawnerRecipes smeltingBase = new MobSpawnerRecipes();
  
  private Map<ItemStack, ItemStack> smeltingList = Maps.newHashMap();
  
  private Map<ItemStack, Float> experienceList = Maps.newHashMap();
  
  public static MobSpawnerRecipes instance() {
    return smeltingBase;
  }
  
  private MobSpawnerRecipes() {
    addSmelting((Item)EItem.fusionItemBat, new ItemStack((Item)EItem.batItem), 5.0F);
    addSmelting((Item)EItem.fusionItemChicken, new ItemStack((Item)EItem.chickenItem), 5.0F);
    addSmelting((Item)EItem.fusionItemCow, new ItemStack((Item)EItem.cowItem), 5.0F);
    addSmelting((Item)EItem.fusionItemMooshroom, new ItemStack((Item)EItem.mooshroomItem), 5.0F);
    addSmelting((Item)EItem.fusionItemParrot, new ItemStack((Item)EItem.parrotItem), 5.0F);
    addSmelting((Item)EItem.fusionItemPig, new ItemStack((Item)EItem.pigItem), 5.0F);
    addSmelting((Item)EItem.fusionItemRabbit, new ItemStack((Item)EItem.rabbitItem), 5.0F);
    addSmelting((Item)EItem.fusionItemSheep, new ItemStack((Item)EItem.sheepItem), 5.0F);
    addSmelting((Item)EItem.fusionItemOzelot, new ItemStack((Item)EItem.ozelotItem), 5.0F);
    addSmelting((Item)EItem.fusionItemSquid, new ItemStack((Item)EItem.squidItem), 5.0F);
    addSmelting((Item)EItem.fusionItemVillager, new ItemStack((Item)EItem.villagerItem), 5.0F);
    addSmelting((Item)EItem.fusionItemSnowman, new ItemStack((Item)EItem.snowmanItem), 5.0F);
    addSmelting((Item)EItem.fusionItemSilverfish, new ItemStack((Item)EItem.silverfishItem), 15.0F);
    addSmelting((Item)EItem.fusionItemEndermite, new ItemStack((Item)EItem.endermiteItem), 18.0F);
    addSmelting((Item)EItem.fusionItemWolf, new ItemStack((Item)EItem.wolfItem), 18.0F);
    addSmelting((Item)EItem.fusionItemSpider, new ItemStack((Item)EItem.spiderItem), 20.0F);
    addSmelting((Item)EItem.fusionItemZombie, new ItemStack((Item)EItem.zombieItem), 22.0F);
    addSmelting((Item)EItem.fusionItemSkeleton, new ItemStack((Item)EItem.skeletonItem), 30.0F);
    addSmelting((Item)EItem.fusionItemCreeper, new ItemStack((Item)EItem.creeperItem), 50.0F);
    addSmelting((Item)EItem.fusionItemSlime, new ItemStack((Item)EItem.slimeItem), 50.0F);
    addSmelting((Item)EItem.fusionItemMagmaCube, new ItemStack((Item)EItem.magmacubeItem), 50.0F);
    addSmelting((Item)EItem.fusionItemSpiderJockey, new ItemStack((Item)EItem.spiderjockeyItem), 50.0F);
    addSmelting((Item)EItem.fusionItemChickenJockey, new ItemStack((Item)EItem.chickenjockeyItem), 50.0F);
    addSmelting((Item)EItem.fusionItemBlaze, new ItemStack((Item)EItem.blazeItem), 500.0F);
    addSmelting((Item)EItem.fusionItemEnderman, new ItemStack((Item)EItem.endermanItem), 400.0F);
    addSmelting((Item)EItem.fusionItemCaveSpider, new ItemStack((Item)EItem.cavespiderItem), 100.0F);
    addSmelting((Item)EItem.fusionItemPigZombie, new ItemStack((Item)EItem.pigzombieItem), 200.0F);
    addSmelting((Item)EItem.fusionItemGuardian, new ItemStack((Item)EItem.guardianItem), 50.0F);
    addSmelting((Item)EItem.fusionItemGhast, new ItemStack((Item)EItem.ghastItem), 700.0F);
    addSmelting((Item)EItem.fusionItemWitch, new ItemStack((Item)EItem.witchItem), 600.0F);
    addSmelting((Item)EItem.fusionItemWitherSkeleton, new ItemStack((Item)EItem.witherskeletonItem), 180.0F);
    addSmelting((Item)EItem.fusionItemKillerRabbit, new ItemStack((Item)EItem.killerrabbitItem), 50.0F);
    addSmelting((Item)EItem.fusionItemElderGuardian, new ItemStack((Item)EItem.elderguardianItem), 10000.0F);
    addSmelting((Item)EItem.fusionItemGiant, new ItemStack((Item)EItem.giantItem), 8000.0F);
    addSmelting((Item)EItem.fusionItemVillagerGolem, new ItemStack((Item)EItem.villagergolemItem), 8000.0F);
    addSmelting((Item)EItem.fusionItemEnderDragon, new ItemStack((Item)EItem.enderdragonItem), 160000.0F);
    addSmelting((Item)EItem.fusionItemWither, new ItemStack((Item)EItem.witherItem), 160000.0F);
    addSmelting((Item)EItem.fusionItemShulker, new ItemStack((Item)EItem.shulkerItem), 300.0F);
    addSmelting((Item)EItem.fusionItemSkeletonTrap, new ItemStack((Item)EItem.the4horsemenItem), 12000.0F);
    addSmelting((Item)EItem.fusionItemStray, new ItemStack((Item)EItem.strayItem), 150.0F);
    addSmelting((Item)EItem.fusionItemHusk, new ItemStack((Item)EItem.huskItem), 150.0F);
    addSmelting((Item)EItem.fusionItemPolarBear, new ItemStack((Item)EItem.polarBearItem), 75.0F);
    addSmelting((Item)EItem.fusionItemVex, new ItemStack((Item)EItem.vexItem), 45.0F);
    addSmelting((Item)EItem.fusionItemVindicator, new ItemStack((Item)EItem.vindicatorItem), 300.0F);
    addSmelting((Item)EItem.fusionItemLlama, new ItemStack((Item)EItem.llamaItem), 10.0F);
    addSmelting((Item)EItem.fusionItemEvoker, new ItemStack((Item)EItem.evokerItem), 18000.0F);
    addSmelting((Item)EItem.fusionItemIllusioner, new ItemStack((Item)EItem.illusionerItem), 18000.0F);
    addSmelting((Item)EItem.fusionItemEversource, new ItemStack((Item)EItem.eversourceItem), 18000.0F);
    addSmelting((Item)EItem.fusionItemIceSpider, new ItemStack((Item)EItem.iceSpiderItem), 50.0F);
    addSmelting((Item)EItem.fusionItemPrisonSlime, new ItemStack((Item)EItem.prisonSlimeItem), 50.0F);
    addSmelting((Item)EItem.fusionItemPrisonZombie, new ItemStack((Item)EItem.prisonZombieItem), 50.0F);
    addSmelting((Item)EItem.fusionItemPrisonSpider, new ItemStack((Item)EItem.prisonSpiderItem), 50.0F);
    addSmelting((Item)EItem.fusionItemCreeder, new ItemStack((Item)EItem.creederItem), 100.0F);
    addSmelting((Item)EItem.fusionItemIcyEnderCreeper, new ItemStack((Item)EItem.icyEnderCreeperItem), 400.0F);
    addSmelting((Item)EItem.fusionItemIceGolem, new ItemStack((Item)EItem.iceGolemItem), 8000.0F);
    addSmelting((Item)EItem.fusionItemMagmaGolem, new ItemStack((Item)EItem.magmaGolemItem), 8000.0F);
    addSmelting((Item)EItem.fusionItemPrisonGolem, new ItemStack((Item)EItem.prisonGolemItem), 8000.0F);
    addSmelting((Item)EItem.fusionItemGhasther, new ItemStack((Item)EItem.ghastherItem), 160000.0F);
    addSmelting((Item)EItem.fusionItemAbomniableSnowman, new ItemStack((Item)EItem.abomniableSnowmanItem), 160000.0F);
    addSmelting((Item)EItem.fusionItemWitherStorm, new ItemStack((Item)EItem.witherStormItem), 6000000.0F);
    if (Loader.isModLoaded("abyssalcraft")) {
      addSmelting((Item)EItem.fusionItemAbyssalZombie, new ItemStack((Item)EItem.abyssalZombieItem), 22.0F);
      addSmelting((Item)EItem.fusionItemAbyssalniteGolem, new ItemStack((Item)EItem.abyssalniteGolemItem), 22.0F);
      addSmelting((Item)EItem.fusionItemChagarothSpawn, new ItemStack((Item)EItem.chagarothSpawnItem), 22.0F);
      addSmelting((Item)EItem.fusionItemChagarothFist, new ItemStack((Item)EItem.chagarothFistItem), 22.0F);
      addSmelting((Item)EItem.fusionItemCoraliumSquid, new ItemStack((Item)EItem.coraliumSquidItem), 22.0F);
      addSmelting((Item)EItem.fusionItemDreadAbyssalniteGolem, new ItemStack((Item)EItem.dreadAbyssalniteGolemItem), 22.0F);
      addSmelting((Item)EItem.fusionItemDreadling, new ItemStack((Item)EItem.dreadlingItem), 22.0F);
      addSmelting((Item)EItem.fusionItemDreadSpawn, new ItemStack((Item)EItem.dreadSpawnItem), 22.0F);
      addSmelting((Item)EItem.fusionItemDepthsGhoul, new ItemStack((Item)EItem.depthsGhoulItem), 22.0F);
      addSmelting((Item)EItem.fusionItemShadowCreature, new ItemStack((Item)EItem.shadowCreatureItem), 50.0F);
      addSmelting((Item)EItem.fusionItemGreaterDreadSpawn, new ItemStack((Item)EItem.greaterDreadSpawnItem), 50.0F);
      addSmelting((Item)EItem.fusionItemOmotholGhoul, new ItemStack((Item)EItem.omotholGhoulItem), 150.0F);
      addSmelting((Item)EItem.fusionItemShadowMonster, new ItemStack((Item)EItem.shadowMonsterItem), 150.0F);
      addSmelting((Item)EItem.fusionItemShoggoth, new ItemStack((Item)EItem.shoggothItem), 300.0F);
      addSmelting((Item)EItem.fusionItemRemnant, new ItemStack((Item)EItem.remnantItem), 600.0F);
      addSmelting((Item)EItem.fusionItemSpectralDragon, new ItemStack((Item)EItem.spectralDragonItem), 700.0F);
      addSmelting((Item)EItem.fusionItemAsorah, new ItemStack((Item)EItem.asorahItem), 160000.0F);
      addSmelting((Item)EItem.fusionItemChagaroth, new ItemStack((Item)EItem.chagarothItem), 160000.0F);
      addSmelting((Item)EItem.fusionItemDreadguard, new ItemStack((Item)EItem.dreadguardItem), 8000.0F);
      addSmelting((Item)EItem.fusionItemGatekeeperMinion, new ItemStack((Item)EItem.gatekeeperminionItem), 8000.0F);
      addSmelting((Item)EItem.fusionItemLesserDreadbeast, new ItemStack((Item)EItem.lesserDreadbeastItem), 8000.0F);
      addSmelting((Item)EItem.fusionItemShadowBeast, new ItemStack((Item)EItem.shadowBeastItem), 8000.0F);
      addSmelting((Item)EItem.fusionItemSkeletonGoliath, new ItemStack((Item)EItem.skeletonGoliathItem), 500.0F);
      addSmelting((Item)EItem.fusionItemSacthoth, new ItemStack((Item)EItem.sacthothItem), 160000.0F);
      addSmelting((Item)EItem.fusionItemJzahar, new ItemStack((Item)EItem.jzaharItem), 6000000.0F);
    } 
    if (Loader.isModLoaded("draconicevolution"))
      addSmelting((Item)EItem.fusionItemChaosGuardian, new ItemStack((Item)EItem.chaosGuardianItem), 6000000.0F); 
    if (Loader.isModLoaded("mutantbeasts")) {
      addSmelting((Item)EItem.fusionPigSpider, new ItemStack((Item)EItem.pigSpiderItem), 200.0F);
      addSmelting((Item)EItem.fusionMutantSnowGolem, new ItemStack((Item)EItem.mutantSnowGolemItem), 800.0F);
      addSmelting((Item)EItem.fusionMutantCreeper, new ItemStack((Item)EItem.mutantCreeperItem), 14000.0F);
      addSmelting((Item)EItem.fusionMutantSkeleton, new ItemStack((Item)EItem.mutantSkeletonItem), 16000.0F);
      addSmelting((Item)EItem.fusionMutantZombie, new ItemStack((Item)EItem.mutantZombieItem), 18000.0F);
      addSmelting((Item)EItem.fusionMutantEnderman, new ItemStack((Item)EItem.mutantEndermanItem), 20000.0F);
    } 
  }
  
  public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience) {
    addSmelting(Item.getItemFromBlock(input), stack, experience);
  }
  
  public void addSmelting(Item input, ItemStack stack, float experience) {
    addSmeltingRecipe(new ItemStack(input), stack, experience);
  }
  
  public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience) {
    this.smeltingList.put(input, stack);
    this.experienceList.put(stack, Float.valueOf(experience));
  }
  
  public ItemStack getSmeltingResult(ItemStack stack) {
    Iterator<?> iterator = this.smeltingList.entrySet().iterator();
    while (true) {
      if (!iterator.hasNext())
        return null; 
      Map.Entry entry = (Map.Entry)iterator.next();
      if (compareItemStacks(stack, (ItemStack)entry.getKey()))
        return (ItemStack)entry.getValue(); 
    } 
  }
  
  private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
    return (stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata()));
  }
  
  public Map<ItemStack, ItemStack> getSmeltingList() {
    return this.smeltingList;
  }
  
  public float getSmeltingExperience(ItemStack stack) {
    float ret = stack.getItem().getSmeltingExperience(stack);
    if (ret != -1.0F)
      return ret; 
    Iterator<?> iterator = this.experienceList.entrySet().iterator();
    while (true) {
      if (!iterator.hasNext())
        return 0.0F; 
      Map.Entry entry = (Map.Entry)iterator.next();
      if (compareItemStacks(stack, (ItemStack)entry.getKey()))
        return ((Float)entry.getValue()).floatValue(); 
    } 
  }
}
