package net.minecraft.AgeOfMinecraft.events;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbygolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothFist;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagarothSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumSquid;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDepthsGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadgolem;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadguard;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGatekeeperMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityGreaterDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserDreadbeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserShoggoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityOmotholGhoul;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityRemnant;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowBeast;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowCreature;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowMonster;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySkeletonGoliath;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantCreeper;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeleton;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityBat;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityMooshroom;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
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
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCaveSpider;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCreeder;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityElderGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.AgeOfMinecraft.entity.untame.tier5.EntityDarkness;
import net.minecraft.AgeOfMinecraft.registry.EEffect;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.util.Maths;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
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
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EngenderGeneralEvent {
  public static final EngenderGeneralEvent INSTANCE = new EngenderGeneralEvent();
  
  public static EngenderMusicEvent musicTicker;
  
  @SubscribeEvent
  public void onClientTick(TickEvent.ClientTickEvent event) {
    Minecraft mc = Minecraft.getMinecraft();
    if (musicTicker != null && mc.world != null)
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
      if (mob.isPotionActive(EEffect.BLEEDING))
        event.setAmount(event.getAmount() / (mob.getActivePotionEffect(EEffect.BLEEDING).getAmplifier() + 2)); 
    } 
  }
  
  @SubscribeEvent
  public void lootLoad(LootTableLoadEvent event) {
    if (event != null)
      return; 
    if (event.getName().equals(LootTableList.CHESTS_SPAWN_BONUS_CHEST)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem(EItem.convertingStaff, 5, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:convertingstaff")); 
    } 
    if (event.getName().equals(LootTableList.CHESTS_SIMPLE_DUNGEON)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.zombieItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:zombie"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.skeletonItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:skeleton"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.spiderItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 3.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:spider"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.huskItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:husk")); 
    } 
    if (event.getName().equals(LootTableList.CHESTS_IGLOO_CHEST)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.strayItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:stray")); 
    } 
    if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.cavespiderItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:cavespider")); 
    } 
    if (event.getName().equals(LootTableList.CHESTS_VILLAGE_BLACKSMITH)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.villagerItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 4.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:villager"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.villagergolemItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:irongolem"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR) || event.getName().equals(LootTableList.CHESTS_STRONGHOLD_CROSSING) || event.getName().equals(LootTableList.CHESTS_STRONGHOLD_LIBRARY)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.silverfishItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 4.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:silverfish"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.spiderItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:spider"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.endermanItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:endermanItem"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_JUNGLE_TEMPLE)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.zombieItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:zombie"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.ozelotItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:ozelot"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_WOODLAND_MANSION)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.vindicatorItem, 5, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:vindicator"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.vexItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:vex"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.evokerItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:evoker"));
      } 
    } 
    if (event.getName().equals(LootTableList.CHESTS_END_CITY_TREASURE)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.shulkerItem, 5, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:shulker"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.endermiteItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }, new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:endermite"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.endermanItem, 5, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:enderman"));
      } 
    } 
  }
  
  @SubscribeEvent
  public void onPotionAdded(PotionEvent.PotionAddedEvent event) {
    if (event.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      if (EngenderMod.doesntHaveTimeToBleed((Entity)mob))
        if (event.getPotionEffect().getPotion() == EEffect.BLEEDING)
          mob.removeActivePotionEffect(event.getPotionEffect().getPotion());  
    } 
  }
  
  @SubscribeEvent
  public void onLivingEvent(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof EntityDragon) {
      EntityDragon mob = (EntityDragon)event.getEntity();
      if (!mob.world.isRemote && mob.getFightManager() != null && mob.getFightManager().hasPreviouslyKilledDragon() && EngenderConfig.general.dragonEgg) {
        ReflectionHelper.setPrivateValue(DragonFightManager.class, mob.getFightManager(), Boolean.valueOf(false), new String[] { "previouslyKilled", "previouslyKilled" });
        for (EntityPlayer entityplayer : mob.world.playerEntities) {
          if (EngenderConfig.general.useMessage)
            entityplayer.sendStatusMessage((ITextComponent)new TextComponentTranslation(TextFormatting.BOLD + "The respawned dragon will drop another egg now.", new Object[0]), true); 
        } 
      } 
    } 
    if (event.getEntity() instanceof EntityPlayer)
      ((EntityPlayer)event.getEntity()).xpCooldown = 0; 
    if (event.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      if (mob.attackable() && mob.ticksExisted > 0) {
        NBTTagCompound karma = mob.getEntityData();
        if (!karma.hasKey("KR", 99) || mob.isDead)
          karma.setInteger("KR", 0); 
        if (karma.getInteger("KR") > 0 && mob.getHealth() > 1.0F && mob.ticksExisted % 5 - ((karma.getInteger("KR") >= 40) ? 4 : (karma.getInteger("KR") / 10)) == 0) {
          mob.setHealth(mob.getHealth() - 1.0F);
          karma.setInteger("KR", karma.getInteger("KR") - 1);
        } 
      } 
      if (mob.isBurning() || mob.isInLava() || mob.isEntityInsideOpaqueBlock())
        mob.removeActivePotionEffect(EEffect.BLEEDING); 
    } 
    if (event.getEntity() instanceof net.minecraft.entity.monster.EntityMagmaCube && 
      event.getEntity().isWet())
      event.getEntity().attackEntityFrom((new DamageSource("cooler")).setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled(), 1.0F); 
  }
  
  public static void playOnHitSound(DamageSource attacktype, Entity entity, float damage) {
    if (attacktype.getDamageType() != "yell")
      if (EngenderMod.isWoodLikeMob(entity)) {
        if (attacktype.isProjectile() && attacktype.getDamageType() != "fireball") {
          entity.playSound(ESound.woodHitPierce, 2.0F, 1.0F);
        } else if (damage >= 6.0F || attacktype.isExplosion() || attacktype.isDamageAbsolute() || attacktype.isUnblockable() || attacktype == DamageSource.ANVIL || attacktype.canHarmInCreative() || attacktype.isMagicDamage() || attacktype == DamageSource.LAVA) {
          entity.playSound(ESound.woodHitCrush, 2.0F, 1.0F);
        } else {
          entity.playSound(ESound.woodHit, 2.0F, 1.0F);
        } 
      } else if (EngenderMod.isMetalLikeMob(entity)) {
        if (attacktype.isProjectile() && attacktype.getDamageType() != "fireball") {
          entity.playSound(ESound.metalHitPierce, 2.0F, 1.0F);
        } else if (damage >= 6.0F || attacktype.isExplosion() || attacktype.isDamageAbsolute() || attacktype.isUnblockable() || attacktype == DamageSource.ANVIL || attacktype.canHarmInCreative() || attacktype.isMagicDamage() || attacktype == DamageSource.LAVA) {
          entity.playSound(ESound.metalHitCrush, 2.0F, 1.0F);
        } else {
          entity.playSound(ESound.metalHit, 2.0F, 1.0F);
        } 
      } else if (attacktype.isProjectile() && attacktype.getDamageType() != "fireball") {
        entity.playSound(ESound.fleshHitPierce, 2.0F, 1.0F);
      } else if (damage >= 6.0F || attacktype.isExplosion() || attacktype.isDamageAbsolute() || attacktype.isUnblockable() || attacktype == DamageSource.ANVIL || attacktype.canHarmInCreative() || attacktype.isMagicDamage() || attacktype == DamageSource.LAVA) {
        if (entity.height >= 5.0F) {
          entity.playSound(ESound.fleshHitCrushHeavy, 2.0F, 1.0F);
        } else {
          entity.playSound(ESound.fleshHitCrush, 2.0F, 1.0F);
        } 
      } else {
        entity.playSound(ESound.fleshHit, 2.0F, 1.0F);
      }  
  }
  
  @SubscribeEvent
  public void onMobHitEvent(LivingHurtEvent event) {
    EntityLivingBase entity = (EntityLivingBase)event.getEntity();
    float f = event.getAmount();
    DamageSource attacktype = event.getSource();
    if (attacktype == DamageSource.LIGHTNING_BOLT && entity instanceof net.minecraft.entity.monster.EntityCreeper)
      event.setAmount(0.0F); 
    if (attacktype == DamageSource.LIGHTNING_BOLT)
      entity.hurtResistantTime = 0; 
    playOnHitSound(attacktype, (Entity)entity, f);
    if (event.getEntity() instanceof EntityLivingBase && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      if (mob.isChild())
        event.setAmount(event.getAmount() * 2.0F); 
      if (((EntityLivingBase)event.getSource().getTrueSource()).isChild())
        event.setAmount(event.getAmount() / 2.0F); 
      if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof EntityTameBase && ((EntityTameBase)event.getSource().getTrueSource()).getOwner() != null) {
        mob.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)((EntityTameBase)event.getSource().getTrueSource()).getOwner()), 1.0F);
        try {
          ReflectionHelper.findField(EntityLivingBase.class, new String[] { "recentlyHit", "recentlyHit" }).setInt(mob, 100);
        } catch (Exception exception) {}
      } 
    } 
    if (!event.getEntity().isEntityAlive())
      event.setCanceled(true); 
  }
  
  @SubscribeEvent
  public void onMobDeathEvent(LivingDeathEvent event) {
    if (event.getEntity() instanceof EntityDragon || event.getEntity() instanceof EntityPlayer)
      return; 
    if (event.getEntity() instanceof EntityPlayer) {
      musicTicker.setNoMusic();
      musicTicker = null;
    } 
    if (event.getEntity() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLivingBase)
      ((EntityLivingBase)event.getSource().getTrueSource()).onKillEntity((EntityLivingBase)event.getEntity()); 
    if (event.getSource().getTrueSource() instanceof EntityTameBase && !((EntityTameBase)event.getSource().getTrueSource()).isWild())
      if (((EntityTameBase)event.getSource().getTrueSource()).getOwner() instanceof EntityPlayerMP)
        ((EntityPlayerMP)((EntityTameBase)event.getSource().getTrueSource()).getOwner()).awardKillScore(event.getEntity(), (int)((EntityLivingBase)event.getEntity()).getMaxHealth(), event.getSource());  
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
    EntityLivingBase critter = (EntityLivingBase)event.getEntity();
    if (event.getEntity() instanceof net.minecraft.entity.boss.EntityWither) {
      net.minecraft.entity.boss.EntityWither mob = (net.minecraft.entity.boss.EntityWither)event.getEntity();
      if (!mob.world.isRemote && mob.world.getGameRules().getBoolean("doMobLoot")) {
        int i = 200;
        i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)mob, mob.world.getClosestPlayerToEntity((Entity)mob, -1.0D), i);
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          mob.world.spawnEntity((Entity)new EntityXPOrb(mob.world, mob.posX, mob.posY, mob.posZ, j));
        } 
      } 
    } 
    if (!(event.getEntity()).world.isRemote && event.getEntity() instanceof EntityDragon) {
      List<EntityEnderDragon> list = Lists.newArrayList();
      List<Entity> entities = (event.getEntity()).world.getEntitiesWithinAABBExcludingEntity(event.getEntity(), (new AxisAlignedBB(event.getEntity().getPosition())).grow(256.0D));
      for (Entity entity : entities) {
        if (entity instanceof EntityEnderDragon && entity.isEntityAlive()) {
          list.add((EntityEnderDragon)entity);
          break;
        } 
      } 
      if (list.isEmpty() && Maths.chance(10)) {
        EntityDarkness mob = new EntityDarkness((event.getEntity()).world);
        event.getEntity().setPosition(0.0D, (event.getEntity()).world.getTopSolidOrLiquidBlock(new BlockPos(0, 256, 0)).getY(), 0.0D);
        mob.setPosition(0.0D, 250.0D, 100.0D);
        mob.renderYawOffset = mob.renderYawOffset;
        mob.rotationYaw = mob.rotationYaw;
        mob.rotationYawHead = mob.rotationYawHead;
        mob.rotationPitch = mob.rotationPitch;
        mob.onInitialSpawn((event.getEntity()).world.getDifficultyForLocation(event.getEntity().getPosition()), null);
        (event.getEntity()).world.spawnEntity((Entity)mob);
      } 
    } 
  }
  
  public boolean shouldMobAutoAttack(EntityMob entity) {
    if (Loader.isModLoaded("mutantbeasts") && entity instanceof chumbanotz.mutantbeasts.entity.mutant.MutantEndermanEntity)
      return false; 
    if (Loader.isModLoaded("abyssalcraft") && entity instanceof com.shinoow.abyssalcraft.common.entity.EntityAbygolem)
      return false; 
    if (Loader.isModLoaded("abyssalcraft") && entity instanceof com.shinoow.abyssalcraft.common.entity.EntityRemnant)
      return false; 
    if (entity instanceof net.minecraft.entity.monster.EntityEnderman)
      return false; 
    if (entity instanceof net.minecraft.entity.monster.EntityPigZombie && !((net.minecraft.entity.monster.EntityPigZombie)entity).isAngry())
      return false; 
    return entity instanceof EntityMob;
  }
  
  @SubscribeEvent
  public void onMobSpawnEvent(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof EntityPlayer)
      musicTicker = new EngenderMusicEvent(Minecraft.getMinecraft()); 
    if (event.getEntity() instanceof EntityLivingBase && !(event.getEntity()).world.isRemote && EngenderConfig.mobs.naturalSpawns && ((EntityLivingBase)event.getEntity()).getRNG().nextInt(((EntityLivingBase)event.getEntity()).isNonBoss() ? 15 : 2) == 0)
      changeMob((event.getEntity()).world, event.getEntity().getPosition(), (EntityLivingBase)event.getEntity()); 
    if (!(event.getEntity()).world.isRemote && !(event.getEntity()).world.getGameRules().hasRule("disableExpItemDrops"))
      (event.getEntity()).world.getGameRules().addGameRule("disableExpItemDrops", "false", GameRules.ValueType.BOOLEAN_VALUE); 
    if (!(event.getEntity()).world.isRemote && !(event.getEntity()).world.getGameRules().hasRule("disableCorpses"))
      (event.getEntity()).world.getGameRules().addGameRule("disableCorpses", "false", GameRules.ValueType.BOOLEAN_VALUE); 
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
        cri.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(cri, net.minecraft.entity.passive.EntityVillager.class, false));
      } else {
        mob.targetTasks.addTask(3, (EntityAIBase)new EntityAIFindEntityNearest(mob, net.minecraft.entity.passive.EntityVillager.class));
      } 
    } 
    if (event.getEntity() instanceof EntityMob) {
      final EntityMob mob = (EntityMob)event.getEntity();
      mob.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)mob, EntityTameBase.class, 0, false, false, new Predicate<EntityTameBase>() {
              public boolean apply(@Nullable EntityTameBase engendermob) {
                return (engendermob != null && engendermob.isEntityAlive() && !false);
              }
            }));
      mob.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)mob, net.minecraft.entity.passive.EntityVillager.class, true));
    } 
    if (event.getEntity() instanceof net.minecraft.entity.monster.EntityIronGolem) {
      net.minecraft.entity.monster.EntityIronGolem golems = (net.minecraft.entity.monster.EntityIronGolem)event.getEntity();
      golems.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)golems, EntityLivingBase.class, 0, false, false, new Predicate<EntityLivingBase>() {
              public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
                return (p_apply_1_ != null && IMob.MOB_SELECTOR.apply(p_apply_1_));
              }
            }));
    } 
    if (event.getEntity() instanceof net.minecraft.entity.passive.EntityVillager) {
      net.minecraft.entity.passive.EntityVillager testificate = (net.minecraft.entity.passive.EntityVillager)event.getEntity();
      testificate.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)testificate, EntityLivingBase.class, new Predicate<EntityLivingBase>() {
              public boolean apply(EntityLivingBase mob) {
                return (mob.isEntityAlive() && mob instanceof IMob);
              }
            },  8.0F, 0.6D, 0.6D));
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
  
  public static void changeMob(World world, BlockPos pos, EntityLivingBase mob) {
    if (Loader.isModLoaded("mutantbeasts")) {
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantCreeperEntity) {
        EntityMutantCreeper newmob = new EntityMutantCreeper(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantZombieEntity) {
        EntityMutantZombie newmob = new EntityMutantZombie(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantSkeletonEntity) {
        EntityMutantSkeleton newmob = new EntityMutantSkeleton(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantEndermanEntity) {
        EntityMutantEnderman newmob = new EntityMutantEnderman(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityBat) {
      EntityBat newmob = new EntityBat(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityChicken) {
      EntityChicken newmob = new EntityChicken(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityCow)
      if (mob instanceof net.minecraft.entity.passive.EntityMooshroom) {
        EntityMooshroom newmob = new EntityMooshroom(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } else {
        EntityCow newmob = new EntityCow(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      }  
    if (mob instanceof net.minecraft.entity.passive.EntityOcelot) {
      EntityOcelot newmob = new EntityOcelot(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityRabbit) {
      EntityRabbit newmob = new EntityRabbit(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setRabbitType(((net.minecraft.entity.passive.EntityRabbit)mob).getRabbitType());
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntitySheep) {
      EntitySheep newmob = new EntitySheep(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setFleeceColor(((net.minecraft.entity.passive.EntitySheep)mob).getFleeceColor());
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityEndermite) {
      EntityEndermite newmob = new EntityEndermite(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityLlama) {
      EntityLlama newmob = new EntityLlama(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setVariant(((net.minecraft.entity.passive.EntityLlama)mob).getVariant());
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntitySilverfish) {
      EntitySilverfish newmob = new EntitySilverfish(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntitySnowman) {
      EntitySnowman newmob = new EntitySnowman(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntitySquid) {
      EntitySquid newmob = new EntitySquid(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityVillager) {
      EntityVillager newmob = new EntityVillager(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setProfession(((net.minecraft.entity.passive.EntityVillager)mob).getProfession());
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityWolf) {
      EntityWolf newmob = new EntityWolf(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityCreeper) {
      EntityCreeper newmob = new EntityCreeper(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setPowered(((net.minecraft.entity.monster.EntityCreeper)mob).getPowered());
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntitySlime)
      if (mob instanceof net.minecraft.entity.monster.EntityMagmaCube) {
        EntityMagmaCube newmob = new EntityMagmaCube(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setSlimeSize(((net.minecraft.entity.monster.EntityMagmaCube)mob).getSlimeSize());
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } else {
        EntitySlime newmob = new EntitySlime(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setSlimeSize(((net.minecraft.entity.monster.EntitySlime)mob).getSlimeSize());
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      }  
    if (mob instanceof net.minecraft.entity.monster.EntitySkeleton) {
      EntitySkeleton newmob = new EntitySkeleton(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setSkeletonType(0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityWitherSkeleton) {
      EntitySkeleton newmob = new EntitySkeleton(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setSkeletonType(1);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityStray) {
      EntitySkeleton newmob = new EntitySkeleton(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      newmob.setSkeletonType(2);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntitySpider)
      if (mob instanceof net.minecraft.entity.monster.EntityCaveSpider) {
        EntityCaveSpider newmob = new EntityCaveSpider(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } else {
        EntitySpider newmob = new EntitySpider(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      }  
    if (mob instanceof net.minecraft.entity.monster.EntityVex) {
      EntityVex newmob = new EntityVex(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityZombie)
      if (mob instanceof net.minecraft.entity.monster.EntityPigZombie) {
        EntityPigZombie newmob = new EntityPigZombie(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } else if (mob instanceof EntityZombieVillager) {
        EntityZombie newmob = new EntityZombie(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setVillagerType(((EntityZombieVillager)mob).getProfession());
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } else {
        EntityZombie newmob = new EntityZombie(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setChild(mob.isChild());
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        newmob.setZombieType((mob instanceof net.minecraft.entity.monster.EntityHusk) ? 1 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      }  
    if (mob instanceof net.minecraft.entity.monster.EntityBlaze) {
      EntityBlaze newmob = new EntityBlaze(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityEnderman) {
      EntityEnderman newmob = new EntityEnderman(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setHeldBlockState(((net.minecraft.entity.monster.EntityEnderman)mob).getHeldBlockState());
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityGhast) {
      EntityGhast newmob = new EntityGhast(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityGuardian) {
      EntityGuardian newmob = new EntityGuardian(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityShulker) {
      EntityShulker newmob = new EntityShulker(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityVindicator) {
      EntityVindicator newmob = new EntityVindicator(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityWitch) {
      EntityWitch newmob = new EntityWitch(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityElderGuardian) {
      EntityElderGuardian newmob = new EntityElderGuardian(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityEvoker) {
      EntityEvoker newmob = new EntityEvoker(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityGiantZombie) {
      EntityGiant newmob = new EntityGiant(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityIronGolem) {
      EntityIronGolem newmob = new EntityIronGolem(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setChild(mob.isChild());
      newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (mob instanceof net.minecraft.entity.boss.EntityWither) {
      EntityWither newmob = new EntityWither(world);
      newmob.copyLocationAndAnglesFrom((Entity)mob);
      newmob.renderYawOffset = mob.renderYawOffset;
      newmob.rotationYaw = mob.rotationYaw;
      newmob.rotationYawHead = mob.rotationYawHead;
      newmob.rotationPitch = mob.rotationPitch;
      newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
      newmob.setInvulTime(((net.minecraft.entity.boss.EntityWither)mob).getInvulTime());
      world.removeEntity((Entity)mob);
      world.spawnEntity((Entity)newmob);
      world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
    } 
    if (Loader.isModLoaded("abyssalcraft")) {
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityAbygolem) {
        EntityAbygolem newmob = new EntityAbygolem(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie) {
        EntityAbyssalZombie newmob = new EntityAbyssalZombie(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityChagaroth) {
        EntityChagaroth newmob = new EntityChagaroth(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityChagarothFist) {
        EntityChagarothFist newmob = new EntityChagarothFist(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityChagarothSpawn) {
        EntityChagarothSpawn newmob = new EntityChagarothSpawn(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid) {
        EntityCoraliumSquid newmob = new EntityCoraliumSquid(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul) {
        EntityDepthsGhoul newmob = new EntityDepthsGhoul(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        newmob.setGhoulType(((com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul)mob).getGhoulType());
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonBoss) {
        EntityDragonBoss newmob = new EntityDragonBoss(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonMinion) {
        EntityDragonMinion newmob = new EntityDragonMinion(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadgolem) {
        EntityDreadgolem newmob = new EntityDreadgolem(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadguard) {
        EntityDreadguard newmob = new EntityDreadguard(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadling) {
        EntityDreadling newmob = new EntityDreadling(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn) {
        EntityDreadSpawn newmob = new EntityDreadSpawn(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion) {
        EntityGatekeeperMinion newmob = new EntityGatekeeperMinion(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn) {
        EntityGreaterDreadSpawn newmob = new EntityGreaterDreadSpawn(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast) {
        EntityLesserDreadbeast newmob = new EntityLesserDreadbeast(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth) {
        EntityLesserShoggoth newmob = new EntityLesserShoggoth(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul) {
        EntityOmotholGhoul newmob = new EntityOmotholGhoul(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityRemnant) {
        EntityRemnant newmob = new EntityRemnant(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        newmob.setProfession(((com.shinoow.abyssalcraft.common.entity.EntityRemnant)mob).getProfession());
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntitySacthoth) {
        EntitySacthoth newmob = new EntitySacthoth(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityShadowBeast) {
        EntityShadowBeast newmob = new EntityShadowBeast(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityShadowCreature) {
        EntityShadowCreature newmob = new EntityShadowCreature(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityShadowMonster) {
        EntityShadowMonster newmob = new EntityShadowMonster(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath) {
        EntitySkeletonGoliath newmob = new EntitySkeletonGoliath(world);
        newmob.copyLocationAndAnglesFrom((Entity)mob);
        newmob.renderYawOffset = mob.renderYawOffset;
        newmob.rotationYaw = mob.rotationYaw;
        newmob.rotationYawHead = mob.rotationYawHead;
        newmob.rotationPitch = mob.rotationPitch;
        newmob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
        newmob.setGrowingAge(mob.isChild() ? -60000 : 0);
        world.removeEntity((Entity)mob);
        world.spawnEntity((Entity)newmob);
        world.playEvent((EntityPlayer)null, 1027, newmob.getPosition(), 0);
      } 
    } 
  }
}


