package net.minecraft.AgeOfMinecraft.events;

import com.google.common.base.Predicate;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCreeder;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.util.ClientCompat;
import net.minecraft.AgeOfMinecraft.util.EntityAICompat;
import net.minecraft.AgeOfMinecraft.util.EntityCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EngenderGeneralEvent {
  public static final EngenderGeneralEvent INSTANCE = new EngenderGeneralEvent();
  
  public static EngenderMusicEvent musicTicker;
  
  @SubscribeEvent
  public void onClientTick(TickEvent.ClientTickEvent event) {
    Minecraft mc = Minecraft.getMinecraft();
    if (musicTicker != null && ClientCompat.world(mc) != null)
      if (EngenderConfig.general.useMusic) {
        musicTicker.update();
      } else {
        musicTicker.setNoMusic();
      }  
  }
  
  @SubscribeEvent
  public static void renderTick(TickEvent.RenderTickEvent event) {}
  
  @SubscribeEvent
  public static void healTicker(LivingHealEvent event) {
    if (event.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      NBTTagCompound karma = mob.getEntityData();
      if (mob.attackable() && karma.getInteger("KR") > 0)
        mob.setHealth(mob.getHealth() - event.getAmount());
    } 
  }
  
  @SubscribeEvent
  public void lootLoad(LootTableLoadEvent event) {
    if (event != null)
      return; 
    if (event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry(new LootEntryItem(EItem.convertingStaff, 5, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:convertingstaff"));
    } 
    if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry(new LootEntryItem(EItem.zombieItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:zombie"));
        main.addEntry(new LootEntryItem(EItem.skeletonItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:skeleton"));
        main.addEntry(new LootEntryItem(EItem.spiderItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 3.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:spider"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry(new LootEntryItem(EItem.huskItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:husk"));
    } 
    if (event.getName().equals(LootTableList.CHESTS_IGLOO_CHEST)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry(new LootEntryItem(EItem.strayItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:stray"));
    } 
    if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry(new LootEntryItem(EItem.cavespiderItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:cavespider"));
    } 
    if (event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry(new LootEntryItem(EItem.villagerItem, 10, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 4.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:villager"));
        main.addEntry(new LootEntryItem(EItem.villagergolemItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:irongolem"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR) || event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) || event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry(new LootEntryItem(EItem.silverfishItem, 10, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 4.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:silverfish"));
        main.addEntry(new LootEntryItem(EItem.spiderItem, 10, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:spider"));
        main.addEntry(new LootEntryItem(EItem.endermanItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:endermanItem"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry(new LootEntryItem(EItem.zombieItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:zombie"));
        main.addEntry(new LootEntryItem(EItem.ozelotItem, 1, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:ozelot"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_WOODLAND_MANSION)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry(new LootEntryItem(EItem.vindicatorItem, 5, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:vindicator"));
        main.addEntry(new LootEntryItem(EItem.vexItem, 10, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:vex"));
        main.addEntry(new LootEntryItem(EItem.evokerItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:evoker"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry(new LootEntryItem(EItem.shulkerItem, 5, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:shulker"));
        main.addEntry(new LootEntryItem(EItem.endermiteItem, 10, 0, new LootFunction[] {new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F))}, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:endermite"));
        main.addEntry(new LootEntryItem(EItem.endermanItem, 5, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:enderman"));
      } 
    } 
  }
  
  @SubscribeEvent
  public void onLivingEvent(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof EntityDragon) {
      EntityDragon mob = (EntityDragon)event.getEntity();
      World world = EntityCompat.world(mob);
      if (!EntityCompat.isRemote(world) && mob.getFightManager() != null && mob.getFightManager().hasPreviouslyKilledDragon() && EngenderConfig.general.dragonEgg) {
        ReflectionHelper.setPrivateValue(DragonFightManager.class, mob.getFightManager(), Boolean.FALSE, new String[] { "previouslyKilled", "previouslyKilled" });
        for (EntityPlayer entityplayer : EntityCompat.playerEntities(world)) {
          if (EngenderConfig.general.useMessage)
            entityplayer.sendStatusMessage(new TextComponentTranslation(TextFormatting.BOLD + "The respawned dragon will drop another egg now."), true);
        } 
      } 
    } 
    if (event.getEntity() instanceof EntityPlayer)
      EntityCompat.setXpCooldown((EntityPlayer)event.getEntity(), 0); 
    if (event.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      int ticksExisted = EntityCompat.ticksExisted(mob);
      if (mob.attackable() && ticksExisted > 0) {
        NBTTagCompound karma = mob.getEntityData();
        if (!karma.hasKey("KR", 99) || EntityCompat.isDead(mob))
          karma.setInteger("KR", 0); 
        if (karma.getInteger("KR") > 0 && mob.getHealth() > 1.0F && ticksExisted % 5 - ((karma.getInteger("KR") >= 40) ? 4 : (karma.getInteger("KR") / 10)) == 0) {
          mob.setHealth(mob.getHealth() - 1.0F);
          karma.setInteger("KR", karma.getInteger("KR") - 1);
        } 
      }
    } 
    if (event.getEntity() instanceof net.minecraft.entity.monster.EntityMagmaCube && 
      event.getEntity().isWet())
      event.getEntity().attackEntityFrom((new DamageSource("cooler")).setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled(), 1.0F); 
  }

  @SubscribeEvent
  public void onMobDeathEvent(LivingDeathEvent event) {
      if (event.getEntity() instanceof EntityPlayer) {
        musicTicker.setNoMusic();
        musicTicker = null;
    } 
    if (event.getEntity() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLivingBase)
      event.getSource().getTrueSource().onKillEntity((EntityLivingBase)event.getEntity());
    if (event.getSource().getTrueSource() instanceof EntityTameBase && !((EntityTameBase)event.getSource().getTrueSource()).isWild())
      if (((EntityTameBase)event.getSource().getTrueSource()).getOwner() instanceof EntityPlayerMP)
        ((EntityTameBase)event.getSource().getTrueSource()).getOwner().awardKillScore(event.getEntity(), (int)((EntityLivingBase)event.getEntity()).getMaxHealth(), event.getSource());
    if ((event.getSource().getTrueSource() instanceof net.minecraft.entity.monster.EntityCreeper && ((net.minecraft.entity.monster.EntityCreeper)event.getSource().getTrueSource()).getPowered()) || (event.getSource().getTrueSource() instanceof EntityCreeder && ((EntityCreeder)event.getSource().getTrueSource()).getPowered())) {
      if (event.getEntity() instanceof net.minecraft.entity.monster.EntitySkeleton)
        event.getEntity().entityDropItem(new ItemStack(Items.SKULL, 1, 0), 0.0F); 
      if (event.getEntity() instanceof net.minecraft.entity.monster.EntityWitherSkeleton)
        event.getEntity().entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0.0F); 
      if (event.getEntity() instanceof net.minecraft.entity.monster.EntityZombie && !(event.getEntity() instanceof net.minecraft.entity.monster.EntityPigZombie))
        event.getEntity().entityDropItem(new ItemStack(Items.SKULL, 1, 2), 0.0F); 
      if (event.getEntity() instanceof net.minecraft.entity.monster.EntityCreeper)
        event.getEntity().entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F); 
      if (event.getEntity() instanceof EntityDragon)
        event.getEntity().entityDropItem(new ItemStack(Items.SKULL, 1, 5), 0.0F); 
    }
  }

  @SubscribeEvent
  public void onMobSpawnEvent(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof EntityPlayer)
      musicTicker = new EngenderMusicEvent(Minecraft.getMinecraft());
    World world = EntityCompat.world(event.getEntity());
    if (!EntityCompat.isRemote(world) && !world.getGameRules().hasRule("disableExpItemDrops"))
      world.getGameRules().addGameRule("disableExpItemDrops", "false", GameRules.ValueType.BOOLEAN_VALUE); 
    if (!EntityCompat.isRemote(world) && !world.getGameRules().hasRule("disableCorpses"))
      world.getGameRules().addGameRule("disableCorpses", "false", GameRules.ValueType.BOOLEAN_VALUE); 
    if (event.getEntity() instanceof EntityLiving) {
      final EntityLiving mob = (EntityLiving)event.getEntity();
      if (mob instanceof EntityCreature) {
        final EntityCreature cri = (EntityCreature)event.getEntity();
      } 
    } 
    if (event.getEntity() instanceof EntityLiving && event.getEntity() instanceof IMob) {
      final EntityLiving mob = (EntityLiving)event.getEntity();
      if (mob instanceof EntityCreature) {
        final EntityCreature cri = (EntityCreature)event.getEntity();
        EntityAICompat.addTargetTask(cri, 3, new EntityAINearestAttackableTarget(cri, net.minecraft.entity.passive.EntityVillager.class, false));
      } else {
        EntityAICompat.addTargetTask(mob, 3, new EntityAIFindEntityNearest(mob, net.minecraft.entity.passive.EntityVillager.class));
      } 
    } 
    if (event.getEntity() instanceof EntityMob) {
      final EntityMob mob = (EntityMob)event.getEntity();
      EntityAICompat.addTargetTask(mob, 3, new EntityAINearestAttackableTarget(mob, EntityTameBase.class, 0, false, false, (Predicate<EntityTameBase>) engendermob -> (engendermob != null && engendermob.isEntityAlive() && !false)));
      EntityAICompat.addTargetTask(mob, 3, new EntityAINearestAttackableTarget(mob, net.minecraft.entity.passive.EntityVillager.class, true));
    } 
    if (event.getEntity() instanceof net.minecraft.entity.monster.EntityIronGolem) {
      net.minecraft.entity.monster.EntityIronGolem golems = (net.minecraft.entity.monster.EntityIronGolem)event.getEntity();
      EntityAICompat.addTargetTask(golems, 3, new EntityAINearestAttackableTarget(golems, EntityLivingBase.class, 0, false, false, (Predicate<EntityLivingBase>) p_apply_1_ -> (p_apply_1_ != null && IMob.MOB_SELECTOR.apply(p_apply_1_))));
    } 
    if (event.getEntity() instanceof net.minecraft.entity.passive.EntityVillager) {
      net.minecraft.entity.passive.EntityVillager testificate = (net.minecraft.entity.passive.EntityVillager)event.getEntity();
      EntityAICompat.addTask(testificate, 1, new EntityAIAvoidEntity(testificate, EntityLivingBase.class, (Predicate<EntityLivingBase>) mob -> (mob.isEntityAlive() && mob instanceof IMob),  8.0F, 0.6D, 0.6D));
    } 
    if (event.getEntity() instanceof EntityItem) {
      EntityItem item = (EntityItem)event.getEntity();
      if (item.getItem().getItem() instanceof net.minecraft.AgeOfMinecraft.items.ItemTierItem || item.getItem().getItem() instanceof net.minecraft.AgeOfMinecraft.items.ItemFusion)
        item.setNoDespawn(); 
      if (item
        .getItem().getItem() == EItem.witheredNetherStar || item
        .getItem().getItem() == EItem.witherStormItem || item.getItem().getItem() == EItem.fusionItemWitherStorm || item
        .getItem().getItem() == EItem.jzaharItem || item.getItem().getItem() == EItem.fusionItemJzahar || item.getItem().getItem() == EItem.chaosGuardianItem || item.getItem().getItem() == EItem.fusionItemChaosGuardian || item.getItem().getItem() == Item.getItemFromBlock(Blocks.COMMAND_BLOCK) || item.getItem().getItem() == Item.getItemFromBlock(Blocks.CHAIN_COMMAND_BLOCK) || item.getItem().getItem() == Item.getItemFromBlock(Blocks.REPEATING_COMMAND_BLOCK) || item.getItem().getItem() == Item.getItemFromBlock(Blocks.BARRIER) || item.getItem().getItem() == Item.getItemFromBlock(Blocks.BEDROCK) || item.getItem().getItem() == Item.getItemFromBlock(Blocks.STRUCTURE_BLOCK) || item.getItem().getItem() == Item.getItemFromBlock(Blocks.STRUCTURE_VOID) || item.getItem().getItem() == Item.getItemFromBlock(Blocks.DRAGON_EGG))
        item.setEntityInvulnerable(true); 
    } 
  }
}


