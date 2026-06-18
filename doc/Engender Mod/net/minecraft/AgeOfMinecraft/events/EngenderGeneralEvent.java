package net.minecraft.AgeOfMinecraft.events;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import java.util.List;
import java.util.Random;
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
import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityManaOrb;
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
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
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
import net.tangotek.tektopia.entities.EntityVillageNavigator;

public class EngenderGeneralEvent {
  public static final EngenderGeneralEvent INSTANCE = new EngenderGeneralEvent();
  
  public static EngenderMusicEvent musicTicker;
  
  @SubscribeEvent
  public void onClientTick(TickEvent.ClientTickEvent event) {
    Minecraft mc = Minecraft.func_71410_x();
    if (musicTicker != null && mc.field_71441_e != null)
      if (EngenderConfig.general.useMusic) {
        musicTicker.func_73660_a();
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
      if (mob.func_190631_cK() && karma.func_74762_e("KR") > 0)
        mob.func_70606_j(mob.func_110143_aJ() - event.getAmount()); 
      if (mob.func_70644_a(EEffect.BLEEDING))
        event.setAmount(event.getAmount() / (mob.func_70660_b(EEffect.BLEEDING).func_76458_c() + 2)); 
    } 
  }
  
  @SubscribeEvent
  public void lootLoad(LootTableLoadEvent event) {
    if (event.getName().equals(LootTableList.field_186420_b)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem(EItem.convertingStaff, 5, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:convertingstaff")); 
    } 
    if (event.getName().equals(LootTableList.field_186422_d)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.zombieItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:zombie"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.skeletonItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:skeleton"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.spiderItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 3.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:spider"));
      } 
    } 
    if (event.getName().equals(LootTableList.field_186429_k)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.huskItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:husk")); 
    } 
    if (event.getName().equals(LootTableList.field_186431_m)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.strayItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:stray")); 
    } 
    if (event.getName().equals(LootTableList.field_186424_f)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null)
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.cavespiderItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:cavespider")); 
    } 
    if (event.getName().equals(LootTableList.field_186423_e)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.villagerItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 4.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:villager"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.villagergolemItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:irongolem"));
      } 
    } 
    if (event.getName().equals(LootTableList.field_186428_j) || event.getName().equals(LootTableList.field_186427_i) || event.getName().equals(LootTableList.field_186426_h)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.silverfishItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 4.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:silverfish"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.spiderItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:spider"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.endermanItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:endermanItem"));
      } 
    } 
    if (event.getName().equals(LootTableList.field_186430_l)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.zombieItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:zombie"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.ozelotItem, 1, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:ozelot"));
      } 
    } 
    if (event.getName().equals(LootTableList.field_191192_o)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.vindicatorItem, 5, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:vindicator"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.vexItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:vex"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.evokerItem, 1, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:evoker"));
      } 
    } 
    if (event.getName().equals(LootTableList.field_186421_c)) {
      LootPool main = event.getTable().getPool("main");
      if (main != null) {
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.shulkerItem, 5, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(1.0F, 2.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:shulker"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.endermiteItem, 10, 0, new LootFunction[] { (LootFunction)new SetCount(new net.minecraft.world.storage.loot.conditions.LootCondition[0], new RandomValueRange(2.0F, 8.0F)) }new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:endermite"));
        main.addEntry((LootEntry)new LootEntryItem((Item)EItem.endermanItem, 5, 0, new LootFunction[0], new net.minecraft.world.storage.loot.conditions.LootCondition[0], "ageofminecraft:enderman"));
      } 
    } 
  }
  
  @SubscribeEvent
  public void onPotionAdded(PotionEvent.PotionAddedEvent event) {
    if (event.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      if (EngenderMod.doesntHaveTimeToBleed((Entity)mob))
        if (event.getPotionEffect().func_188419_a() == EEffect.BLEEDING)
          mob.func_184596_c(event.getPotionEffect().func_188419_a());  
    } 
  }
  
  @SubscribeEvent
  public void onLivingEvent(LivingEvent.LivingUpdateEvent event) {
    if (event.getEntity() instanceof EntityDragon) {
      EntityDragon mob = (EntityDragon)event.getEntity();
      if (!mob.field_70170_p.field_72995_K && mob.func_184664_cU() != null && mob.func_184664_cU().func_186102_d() && EngenderConfig.general.dragonEgg) {
        ReflectionHelper.setPrivateValue(DragonFightManager.class, mob.func_184664_cU(), Boolean.valueOf(false), new String[] { "field_186118_l", "previouslyKilled" });
        for (EntityPlayer entityplayer : mob.field_70170_p.field_73010_i) {
          if (EngenderConfig.general.useMessage)
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation(TextFormatting.BOLD + "The respawned dragon will drop another egg now.", new Object[0]), true); 
        } 
      } 
    } 
    if (event.getEntity() instanceof EntityPlayer)
      ((EntityPlayer)event.getEntity()).field_71090_bL = 0; 
    if (event.getEntity() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      if (mob.func_190631_cK() && mob.field_70173_aa > 0) {
        NBTTagCompound karma = mob.getEntityData();
        if (!karma.func_150297_b("KR", 99) || mob.field_70128_L)
          karma.func_74768_a("KR", 0); 
        if (karma.func_74762_e("KR") > 0 && mob.func_110143_aJ() > 1.0F && mob.field_70173_aa % 5 - ((karma.func_74762_e("KR") >= 40) ? 4 : (karma.func_74762_e("KR") / 10)) == 0) {
          mob.func_70606_j(mob.func_110143_aJ() - 1.0F);
          karma.func_74768_a("KR", karma.func_74762_e("KR") - 1);
        } 
      } 
      if (mob.func_70027_ad() || mob.func_180799_ab() || mob.func_70094_T())
        mob.func_184596_c(EEffect.BLEEDING); 
    } 
    if (event.getEntity() instanceof EntityMagmaCube && 
      event.getEntity().func_70026_G())
      event.getEntity().func_70097_a((new DamageSource("cooler")).func_76348_h().func_151518_m().func_76351_m(), 1.0F); 
  }
  
  public static void playOnHitSound(DamageSource attacktype, Entity entity, float damage) {
    if (attacktype.func_76355_l() != "yell")
      if (EngenderMod.isWoodLikeMob(entity)) {
        if (attacktype.func_76352_a() && attacktype.func_76355_l() != "fireball") {
          entity.func_184185_a(ESound.woodHitPierce, 2.0F, 1.0F);
        } else if (damage >= 6.0F || attacktype.func_94541_c() || attacktype.func_151517_h() || attacktype.func_76363_c() || attacktype == DamageSource.field_82728_o || attacktype.func_76357_e() || attacktype.func_82725_o() || attacktype == DamageSource.field_76371_c) {
          entity.func_184185_a(ESound.woodHitCrush, 2.0F, 1.0F);
        } else {
          entity.func_184185_a(ESound.woodHit, 2.0F, 1.0F);
        } 
      } else if (EngenderMod.isMetalLikeMob(entity)) {
        if (attacktype.func_76352_a() && attacktype.func_76355_l() != "fireball") {
          entity.func_184185_a(ESound.metalHitPierce, 2.0F, 1.0F);
        } else if (damage >= 6.0F || attacktype.func_94541_c() || attacktype.func_151517_h() || attacktype.func_76363_c() || attacktype == DamageSource.field_82728_o || attacktype.func_76357_e() || attacktype.func_82725_o() || attacktype == DamageSource.field_76371_c) {
          entity.func_184185_a(ESound.metalHitCrush, 2.0F, 1.0F);
        } else {
          entity.func_184185_a(ESound.metalHit, 2.0F, 1.0F);
        } 
      } else if (attacktype.func_76352_a() && attacktype.func_76355_l() != "fireball") {
        entity.func_184185_a(ESound.fleshHitPierce, 2.0F, 1.0F);
      } else if (damage >= 6.0F || attacktype.func_94541_c() || attacktype.func_151517_h() || attacktype.func_76363_c() || attacktype == DamageSource.field_82728_o || attacktype.func_76357_e() || attacktype.func_82725_o() || attacktype == DamageSource.field_76371_c) {
        if (entity.field_70131_O >= 5.0F) {
          entity.func_184185_a(ESound.fleshHitCrushHeavy, 2.0F, 1.0F);
        } else {
          entity.func_184185_a(ESound.fleshHitCrush, 2.0F, 1.0F);
        } 
      } else {
        entity.func_184185_a(ESound.fleshHit, 2.0F, 1.0F);
      }  
  }
  
  @SubscribeEvent
  public void onMobHitEvent(LivingHurtEvent event) {
    EntityLivingBase entity = (EntityLivingBase)event.getEntity();
    float f = event.getAmount();
    DamageSource attacktype = event.getSource();
    if (Loader.isModLoaded("tektopia") && event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof net.tangotek.tektopia.entities.EntityGuard)
      (event.getEntity()).field_70172_ad = 0; 
    if (event.getEntity() instanceof EntityLivingBase && !attacktype.func_82725_o() && !attacktype.func_76347_k()) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      if ((f > 1.5F || attacktype.func_76352_a() || attacktype.func_94541_c()) && mob.func_70681_au().nextFloat() * f != 1.0F && !EngenderMod.doesntHaveTimeToBleed((Entity)mob)) {
        int level = 0;
        if (attacktype.func_76352_a() || attacktype.func_94541_c()) {
          if (f >= 3.0F)
            level++; 
          if (f >= 20.0F)
            level++; 
          if (f >= 80.0F)
            level++; 
        } else {
          if (f >= 7.0F)
            level++; 
          if (f >= 10.0F)
            level++; 
          if (f >= 25.0F)
            level++; 
        } 
        mob.func_70690_d(new PotionEffect(EEffect.BLEEDING, 600, level, false, false));
      } 
    } 
    if (attacktype == DamageSource.field_180137_b && entity instanceof EntityCreeper)
      event.setAmount(0.0F); 
    if (attacktype == DamageSource.field_180137_b)
      entity.field_70172_ad = 0; 
    playOnHitSound(attacktype, (Entity)entity, f);
    if (event.getEntity() instanceof EntityLivingBase && event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityLivingBase) {
      EntityLivingBase mob = (EntityLivingBase)event.getEntity();
      if (mob.func_70631_g_())
        event.setAmount(event.getAmount() * 2.0F); 
      if (((EntityLivingBase)event.getSource().func_76346_g()).func_70631_g_())
        event.setAmount(event.getAmount() / 2.0F); 
      if (event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityTameBase && ((EntityTameBase)event.getSource().func_76346_g()).getOwner() != null) {
        mob.func_70097_a(DamageSource.func_76365_a((EntityPlayer)((EntityTameBase)event.getSource().func_76346_g()).getOwner()), 1.0F);
        try {
          ReflectionHelper.findField(EntityLivingBase.class, new String[] { "recentlyHit", "field_70718_bc" }).setInt(mob, 100);
        } catch (Exception exception) {}
      } 
    } 
    if (!event.getEntity().func_70089_S())
      event.setCanceled(true); 
    if (event.getEntity() instanceof EntityPlayer && event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityTameBase) {
      EntityPlayer player = (EntityPlayer)event.getEntity();
      EntityTameBase mob = (EntityTameBase)event.getSource().func_76346_g();
      if (mob != null && mob.func_184191_r((Entity)player) && !mob.field_70170_p.func_82736_K().func_82766_b("friendlyFire") && mob.func_184191_r((Entity)player)) {
        event.setAmount(0.0F);
        event.setCanceled(true);
      } 
    } 
    if (event.getEntity() instanceof EntityTameBase && event.getSource().func_76346_g() != null && event.getSource().func_76346_g() instanceof EntityTameBase) {
      EntityTameBase player = (EntityTameBase)event.getEntity();
      EntityTameBase mob = (EntityTameBase)event.getSource().func_76346_g();
      if (mob != null && mob.func_184191_r((Entity)player) && !mob.field_70170_p.func_82736_K().func_82766_b("friendlyFire") && mob.func_184191_r((Entity)player)) {
        event.setAmount(0.0F);
        event.setCanceled(true);
      } 
    } 
  }
  
  @SubscribeEvent
  public void onMobDeathEvent(LivingDeathEvent event) {
    if (event.getEntity() instanceof EntityPlayer) {
      musicTicker.setNoMusic();
      musicTicker = null;
    } 
    if (event.getEntity() instanceof EntityPlayer && event.getSource().func_76346_g() instanceof EntityLivingBase)
      ((EntityLivingBase)event.getSource().func_76346_g()).func_70074_a((EntityLivingBase)event.getEntity()); 
    if (event.getSource().func_76346_g() instanceof EntityTameBase && !((EntityTameBase)event.getSource().func_76346_g()).isWild())
      if (((EntityTameBase)event.getSource().func_76346_g()).getOwner() instanceof EntityPlayerMP)
        ((EntityPlayerMP)((EntityTameBase)event.getSource().func_76346_g()).getOwner()).func_191956_a(event.getEntity(), (int)((EntityLivingBase)event.getEntity()).func_110138_aP(), event.getSource());  
    if ((event.getSource().func_76346_g() instanceof EntityCreeper && ((EntityCreeper)event.getSource().func_76346_g()).getPowered()) || (event.getSource().func_76346_g() instanceof EntityCreeder && ((EntityCreeder)event.getSource().func_76346_g()).getPowered())) {
      if (event.getEntity() instanceof net.minecraft.entity.monster.EntitySkeleton)
        event.getEntity().func_70099_a(new ItemStack(Items.field_151144_bL, 1, 0), 0.0F); 
      if (event.getEntity() instanceof net.minecraft.entity.monster.EntityWitherSkeleton)
        event.getEntity().func_70099_a(new ItemStack(Items.field_151144_bL, 1, 1), 0.0F); 
      if (event.getEntity() instanceof net.minecraft.entity.monster.EntityZombie && !(event.getEntity() instanceof EntityPigZombie))
        event.getEntity().func_70099_a(new ItemStack(Items.field_151144_bL, 1, 2), 0.0F); 
      if (event.getEntity() instanceof EntityCreeper)
        event.getEntity().func_70099_a(new ItemStack(Items.field_151144_bL, 1, 4), 0.0F); 
      if (event.getEntity() instanceof EntityDragon)
        event.getEntity().func_70099_a(new ItemStack(Items.field_151144_bL, 1, 5), 0.0F); 
    } 
    EntityLivingBase critter = (EntityLivingBase)event.getEntity();
    EntityPlayer player = critter.field_70170_p.func_72890_a((Entity)critter, EngenderConfig.general.manaDistance);
    boolean stink = false;
    if (player != null)
      stink = true; 
    if (EngenderConfig.general.mana && stink && !critter.field_70170_p.field_72995_K && critter.field_70170_p.func_82736_K().func_82766_b("doMobLoot") && (!(critter instanceof EntityTameBase) || (critter instanceof EntityTameBase && ((EntityTameBase)critter).isWild()))) {
      int i = (critter instanceof net.minecraft.entity.EntityAgeable) ? ((int)critter.func_110138_aP() / 4) : (int)critter.func_110138_aP();
      while (i > 0) {
        int j = EntityXPOrb.func_70527_a(i);
        i -= j;
        critter.field_70170_p.func_72838_d((Entity)new EntityManaOrb(critter.field_70170_p, critter.field_70165_t, critter.field_70163_u + critter.func_70047_e(), critter.field_70161_v, j, false));
      } 
      if (critter.func_110138_aP() >= 100.0F)
        for (int a = 0; a < 1 + (new Random()).nextInt(2); a++)
          critter.field_70170_p.func_72838_d((Entity)new EntityManaOrb(critter.field_70170_p, critter.field_70165_t, critter.field_70163_u + critter.func_70047_e(), critter.field_70161_v, (new Random()).nextInt(1 + (int)critter.func_110138_aP() / 10), true));  
    } 
    if (event.getEntity() instanceof EntityWither) {
      EntityWither mob = (EntityWither)event.getEntity();
      if (!mob.field_70170_p.field_72995_K && mob.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
        int i = 200;
        i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)mob, mob.field_70170_p.func_72890_a((Entity)mob, -1.0D), i);
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          mob.field_70170_p.func_72838_d((Entity)new EntityXPOrb(mob.field_70170_p, mob.field_70165_t, mob.field_70163_u, mob.field_70161_v, j));
        } 
      } 
    } 
    if (!(event.getEntity()).field_70170_p.field_72995_K && event.getEntity() instanceof EntityDragon) {
      List<EntityEnderDragon> list = Lists.newArrayList();
      List<Entity> entities = (event.getEntity()).field_70170_p.func_72839_b(event.getEntity(), (new AxisAlignedBB(event.getEntity().func_180425_c())).func_186662_g(256.0D));
      for (Entity entity : entities) {
        if (entity instanceof EntityEnderDragon && entity.func_70089_S()) {
          list.add((EntityEnderDragon)entity);
          break;
        } 
      } 
      if (list.isEmpty() && Maths.chance(10)) {
        EntityDarkness mob = new EntityDarkness((event.getEntity()).field_70170_p);
        event.getEntity().func_70107_b(0.0D, (event.getEntity()).field_70170_p.func_175672_r(new BlockPos(0, 256, 0)).func_177956_o(), 0.0D);
        mob.func_70107_b(0.0D, 250.0D, 100.0D);
        mob.field_70761_aq = mob.field_70761_aq;
        mob.field_70177_z = mob.field_70177_z;
        mob.field_70759_as = mob.field_70759_as;
        mob.field_70125_A = mob.field_70125_A;
        mob.func_180482_a((event.getEntity()).field_70170_p.func_175649_E(event.getEntity().func_180425_c()), null);
        (event.getEntity()).field_70170_p.func_72838_d((Entity)mob);
      } 
    } 
  }
  
  public boolean shouldMobAutoAttack(EntityMob entity) {
    if (Loader.isModLoaded("mutantbeasts") && entity instanceof chumbanotz.mutantbeasts.entity.mutant.MutantEndermanEntity)
      return false; 
    if (Loader.isModLoaded("abyssalcraft") && entity instanceof com.shinoow.abyssalcraft.common.entity.EntityAbygolem)
      return false; 
    if (Loader.isModLoaded("abyssalcraft") && entity instanceof EntityRemnant)
      return false; 
    if (entity instanceof EntityEnderman)
      return false; 
    if (entity instanceof EntityPigZombie && !((EntityPigZombie)entity).func_175457_ck())
      return false; 
    return entity instanceof EntityMob;
  }
  
  @SubscribeEvent
  public void onMobSpawnEvent(EntityJoinWorldEvent event) {
    if (event.getEntity() instanceof EntityPlayer)
      musicTicker = new EngenderMusicEvent(Minecraft.func_71410_x()); 
    if (event.getEntity() instanceof EntityLivingBase && !(event.getEntity()).field_70170_p.field_72995_K && EngenderConfig.mobs.naturalSpawns && ((EntityLivingBase)event.getEntity()).func_70681_au().nextInt(((EntityLivingBase)event.getEntity()).func_184222_aU() ? 15 : 2) == 0)
      changeMob((event.getEntity()).field_70170_p, event.getEntity().func_180425_c(), (EntityLivingBase)event.getEntity()); 
    if (!(event.getEntity()).field_70170_p.field_72995_K && !(event.getEntity()).field_70170_p.func_82736_K().func_82765_e("friendlyFire"))
      (event.getEntity()).field_70170_p.func_82736_K().func_180262_a("friendlyFire", "false", GameRules.ValueType.BOOLEAN_VALUE); 
    if (!(event.getEntity()).field_70170_p.field_72995_K && !(event.getEntity()).field_70170_p.func_82736_K().func_82765_e("disableExpItemDrops"))
      (event.getEntity()).field_70170_p.func_82736_K().func_180262_a("disableExpItemDrops", "false", GameRules.ValueType.BOOLEAN_VALUE); 
    if (!(event.getEntity()).field_70170_p.field_72995_K && !(event.getEntity()).field_70170_p.func_82736_K().func_82765_e("disableCorpses"))
      (event.getEntity()).field_70170_p.func_82736_K().func_180262_a("disableCorpses", "false", GameRules.ValueType.BOOLEAN_VALUE); 
    if (event.getEntity() instanceof EntityLiving) {
      final EntityLiving mob = (EntityLiving)event.getEntity();
      if (mob instanceof EntityCreature) {
        final EntityCreature cri = (EntityCreature)event.getEntity();
        if (Loader.isModLoaded("tektopia") && cri instanceof net.tangotek.tektopia.entities.EntityGuard)
          cri.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget(cri, EntityLivingBase.class, 0, false, false, new Predicate<EntityLivingBase>() {
                  public boolean apply(@Nullable EntityLivingBase engendermob) {
                    return (engendermob != null && engendermob.func_70089_S() && !engendermob.func_184191_r((Entity)cri) && engendermob instanceof IMob);
                  }
                })); 
      } 
    } 
    if (event.getEntity() instanceof EntityLiving && event.getEntity() instanceof IMob) {
      final EntityLiving mob = (EntityLiving)event.getEntity();
      if (mob instanceof EntityCreature) {
        final EntityCreature cri = (EntityCreature)event.getEntity();
        cri.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget(cri, EntityVillager.class, false));
      } else {
        if (Loader.isModLoaded("tektopia"))
          mob.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAIFindEntityNearest(mob, EntityVillageNavigator.class)); 
        mob.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAIFindEntityNearest(mob, EntityVillager.class));
      } 
    } 
    if (event.getEntity() instanceof EntityMob) {
      final EntityMob mob = (EntityMob)event.getEntity();
      mob.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)mob, EntityTameBase.class, 0, false, false, new Predicate<EntityTameBase>() {
              public boolean apply(@Nullable EntityTameBase engendermob) {
                return (engendermob != null && engendermob.func_70089_S() && !engendermob.func_184191_r((Entity)mob));
              }
            }));
      mob.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)mob, EntityVillager.class, true));
    } 
    if (event.getEntity() instanceof EntityIronGolem) {
      EntityIronGolem golems = (EntityIronGolem)event.getEntity();
      golems.field_70715_bh.func_75776_a(3, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)golems, EntityLivingBase.class, 0, false, false, new Predicate<EntityLivingBase>() {
              public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
                return (p_apply_1_ != null && IMob.field_82192_a.apply(p_apply_1_));
              }
            }));
    } 
    if (event.getEntity() instanceof EntityVillager) {
      EntityVillager testificate = (EntityVillager)event.getEntity();
      testificate.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)testificate, EntityLivingBase.class, new Predicate<EntityLivingBase>() {
              public boolean apply(EntityLivingBase mob) {
                return (mob.func_70089_S() && mob instanceof IMob);
              }
            },  8.0F, 0.6D, 0.6D));
    } 
    if (event.getEntity() instanceof EntityItem) {
      EntityItem item = (EntityItem)event.getEntity();
      if (item.func_92059_d().func_77973_b() instanceof net.minecraft.AgeOfMinecraft.items.ItemTierItem || item.func_92059_d().func_77973_b() instanceof net.minecraft.AgeOfMinecraft.items.ItemFusion)
        item.func_174873_u(); 
      if (item
        .func_92059_d().func_77973_b() == EItem.witheredNetherStar || item
        .func_92059_d().func_77973_b() == EItem.witherStormItem || item.func_92059_d().func_77973_b() == EItem.fusionItemWitherStorm || item
        .func_92059_d().func_77973_b() == EItem.jzaharItem || item.func_92059_d().func_77973_b() == EItem.fusionItemJzahar || item.func_92059_d().func_77973_b() == EItem.chaosGuardianItem || item.func_92059_d().func_77973_b() == EItem.fusionItemChaosGuardian || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_150483_bI) || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_185777_dd) || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_185776_dc) || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_180401_cv) || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_150357_h) || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_185779_df) || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_189881_dj) || item.func_92059_d().func_77973_b() == Item.func_150898_a(Blocks.field_150380_bt))
        item.func_184224_h(true); 
    } 
  }
  
  public static void changeMob(World world, BlockPos pos, EntityLivingBase mob) {
    if (Loader.isModLoaded("mutantbeasts")) {
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantCreeperEntity) {
        EntityMutantCreeper newmob = new EntityMutantCreeper(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantZombieEntity) {
        EntityMutantZombie newmob = new EntityMutantZombie(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantSkeletonEntity) {
        EntityMutantSkeleton newmob = new EntityMutantSkeleton(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof chumbanotz.mutantbeasts.entity.mutant.MutantEndermanEntity) {
        EntityMutantEnderman newmob = new EntityMutantEnderman(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityBat) {
      EntityBat newmob = new EntityBat(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityChicken) {
      EntityChicken newmob = new EntityChicken(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityCow)
      if (mob instanceof net.minecraft.entity.passive.EntityMooshroom) {
        EntityMooshroom newmob = new EntityMooshroom(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } else {
        EntityCow newmob = new EntityCow(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      }  
    if (mob instanceof net.minecraft.entity.passive.EntityOcelot) {
      EntityOcelot newmob = new EntityOcelot(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntityRabbit) {
      EntityRabbit newmob = new EntityRabbit(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setRabbitType(((EntityRabbit)mob).func_175531_cl());
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntitySheep) {
      EntitySheep newmob = new EntitySheep(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setFleeceColor(((EntitySheep)mob).func_175509_cj());
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityEndermite) {
      EntityEndermite newmob = new EntityEndermite(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntityLlama) {
      EntityLlama newmob = new EntityLlama(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setVariant(((EntityLlama)mob).func_190719_dM());
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntitySilverfish) {
      EntitySilverfish newmob = new EntitySilverfish(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntitySnowman) {
      EntitySnowman newmob = new EntitySnowman(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntitySquid) {
      EntitySquid newmob = new EntitySquid(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntityVillager) {
      EntityVillager newmob = new EntityVillager(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setProfession(((EntityVillager)mob).func_70946_n());
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.passive.EntityWolf) {
      EntityWolf newmob = new EntityWolf(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntityCreeper) {
      EntityCreeper newmob = new EntityCreeper(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setPowered(((EntityCreeper)mob).func_70830_n());
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntitySlime)
      if (mob instanceof EntityMagmaCube) {
        EntityMagmaCube newmob = new EntityMagmaCube(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setSlimeSize(((EntityMagmaCube)mob).func_70809_q());
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } else {
        EntitySlime newmob = new EntitySlime(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setSlimeSize(((EntitySlime)mob).func_70809_q());
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      }  
    if (mob instanceof net.minecraft.entity.monster.EntitySkeleton) {
      EntitySkeleton newmob = new EntitySkeleton(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setSkeletonType(0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityWitherSkeleton) {
      EntitySkeleton newmob = new EntitySkeleton(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setSkeletonType(1);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityStray) {
      EntitySkeleton newmob = new EntitySkeleton(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      newmob.setSkeletonType(2);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntitySpider)
      if (mob instanceof net.minecraft.entity.monster.EntityCaveSpider) {
        EntityCaveSpider newmob = new EntityCaveSpider(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } else {
        EntitySpider newmob = new EntitySpider(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      }  
    if (mob instanceof net.minecraft.entity.monster.EntityVex) {
      EntityVex newmob = new EntityVex(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityZombie)
      if (mob instanceof EntityPigZombie) {
        EntityPigZombie newmob = new EntityPigZombie(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } else if (mob instanceof EntityZombieVillager) {
        EntityZombie newmob = new EntityZombie(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setVillagerType(((EntityZombieVillager)mob).func_190736_dl());
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } else {
        EntityZombie newmob = new EntityZombie(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setChild(mob.func_70631_g_());
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        newmob.setZombieType((mob instanceof net.minecraft.entity.monster.EntityHusk) ? 1 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      }  
    if (mob instanceof net.minecraft.entity.monster.EntityBlaze) {
      EntityBlaze newmob = new EntityBlaze(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntityEnderman) {
      EntityEnderman newmob = new EntityEnderman(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setHeldBlockState(((EntityEnderman)mob).func_175489_ck());
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityGhast) {
      EntityGhast newmob = new EntityGhast(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityGuardian) {
      EntityGuardian newmob = new EntityGuardian(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityShulker) {
      EntityShulker newmob = new EntityShulker(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityVindicator) {
      EntityVindicator newmob = new EntityVindicator(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityWitch) {
      EntityWitch newmob = new EntityWitch(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityElderGuardian) {
      EntityElderGuardian newmob = new EntityElderGuardian(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityEvoker) {
      EntityEvoker newmob = new EntityEvoker(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof net.minecraft.entity.monster.EntityGiantZombie) {
      EntityGiant newmob = new EntityGiant(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntityIronGolem) {
      EntityIronGolem newmob = new EntityIronGolem(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setChild(mob.func_70631_g_());
      newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (mob instanceof EntityWither) {
      EntityWither newmob = new EntityWither(world);
      newmob.func_82149_j((Entity)mob);
      newmob.field_70761_aq = mob.field_70761_aq;
      newmob.field_70177_z = mob.field_70177_z;
      newmob.field_70759_as = mob.field_70759_as;
      newmob.field_70125_A = mob.field_70125_A;
      newmob.func_180482_a(world.func_175649_E(pos), null);
      newmob.setInvulTime(((EntityWither)mob).func_82212_n());
      world.func_72900_e((Entity)mob);
      world.func_72838_d((Entity)newmob);
      world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
    } 
    if (Loader.isModLoaded("abyssalcraft")) {
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityAbygolem) {
        EntityAbygolem newmob = new EntityAbygolem(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie) {
        EntityAbyssalZombie newmob = new EntityAbyssalZombie(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityChagaroth) {
        EntityChagaroth newmob = new EntityChagaroth(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityChagarothFist) {
        EntityChagarothFist newmob = new EntityChagarothFist(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityChagarothSpawn) {
        EntityChagarothSpawn newmob = new EntityChagarothSpawn(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid) {
        EntityCoraliumSquid newmob = new EntityCoraliumSquid(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof EntityDepthsGhoul) {
        EntityDepthsGhoul newmob = new EntityDepthsGhoul(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        newmob.setGhoulType(((EntityDepthsGhoul)mob).getGhoulType());
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonBoss) {
        EntityDragonBoss newmob = new EntityDragonBoss(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonMinion) {
        EntityDragonMinion newmob = new EntityDragonMinion(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadgolem) {
        EntityDreadgolem newmob = new EntityDreadgolem(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadguard) {
        EntityDreadguard newmob = new EntityDreadguard(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadling) {
        EntityDreadling newmob = new EntityDreadling(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn) {
        EntityDreadSpawn newmob = new EntityDreadSpawn(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion) {
        EntityGatekeeperMinion newmob = new EntityGatekeeperMinion(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn) {
        EntityGreaterDreadSpawn newmob = new EntityGreaterDreadSpawn(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast) {
        EntityLesserDreadbeast newmob = new EntityLesserDreadbeast(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth) {
        EntityLesserShoggoth newmob = new EntityLesserShoggoth(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul) {
        EntityOmotholGhoul newmob = new EntityOmotholGhoul(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof EntityRemnant) {
        EntityRemnant newmob = new EntityRemnant(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        newmob.setProfession(((EntityRemnant)mob).getProfession());
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntitySacthoth) {
        EntitySacthoth newmob = new EntitySacthoth(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityShadowBeast) {
        EntityShadowBeast newmob = new EntityShadowBeast(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityShadowCreature) {
        EntityShadowCreature newmob = new EntityShadowCreature(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntityShadowMonster) {
        EntityShadowMonster newmob = new EntityShadowMonster(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
      if (mob instanceof com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath) {
        EntitySkeletonGoliath newmob = new EntitySkeletonGoliath(world);
        newmob.func_82149_j((Entity)mob);
        newmob.field_70761_aq = mob.field_70761_aq;
        newmob.field_70177_z = mob.field_70177_z;
        newmob.field_70759_as = mob.field_70759_as;
        newmob.field_70125_A = mob.field_70125_A;
        newmob.func_180482_a(world.func_175649_E(pos), null);
        newmob.setGrowingAge(mob.func_70631_g_() ? -60000 : 0);
        world.func_72900_e((Entity)mob);
        world.func_72838_d((Entity)newmob);
        world.func_180498_a((EntityPlayer)null, 1027, newmob.func_180425_c(), 0);
      } 
    } 
  }
}
