package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityElderGuardian extends EntityGuardian implements Massive {
  public EntityElderGuardian(World worldIn) {
    super(worldIn);
    enablePersistence();
    this.experienceValue = 50;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.6F, 2.35F);
    } else {
      setSize(2.35F, 2.35F);
    } 
    if (this.wander != null)
      this.wander.setExecutionChance(400); 
  }
  
  public String getDescName() {
    return "elder_guardian";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_ELDER_GUARDIAN;
  }
  
  public int getAttackDuration() {
    return 60;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_ELDER_GUARDIAN_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    setAttackTarget((EntityLivingBase)null);
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    if (!isInWater())
      playSound(SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND, getSoundVolume(), getSoundPitch()); 
    return SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH;
  }
  
  public double getMountedYOffset() {
    return (!EngenderConfig.mobs.useMobTalkerModels && this.onGround && !isInWater()) ? (this.height * 1.1D) : (this.height * 0.85D);
  }
  
  protected void jump() {
    this.hurtTime = 0;
    if (EngenderConfig.mobs.useMobTalkerModels && !isChild()) {
      playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, 1.0F, 1.75F + this.rand.nextFloat() * 0.25F);
    } else {
      playSound(SoundEvents.ENTITY_GUARDIAN_FLOP, 1.0F, isChild() ? 1.5F : 1.0F);
    } 
    this.motionY += (isChild() ? 0.5D : 0.6D) - this.motionY;
    this.onGround = false;
    this.isAirBorne = true;
  }
  
  public float getRenderSizeModifier() {
    return EngenderConfig.mobs.useMobTalkerModels ? 1.35F : 2.35F;
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.9F + ((isRiding() || isInWater() || isInLava() || isSitResting() || !isChild()) ? (MathHelper.cos(this.ticksExisted * 0.2F) * 0.1F) : 0.0F)) : ((this.onGround && !isSneaking() && !isInWater()) ? (this.height * 0.75F) : (this.height * 0.5F));
  }
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntityGuardian baby = new EntityGuardian(this.world);
        baby.copyLocationAndAnglesFrom((Entity)this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        if (this.rand.nextFloat() < 0.25F) {
          baby = new EntityElderGuardian(this.world);
          baby.setGrowingAge(-100000);
          baby.setElder();
        } else {
          baby.setGrowingAge(-24000);
        } 
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity((Entity)baby);
      }  
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.1F) : super.getSoundPitch();
  }
  
  public int getVerticalFaceSpeed() {
    return EngenderConfig.mobs.useMobTalkerModels ? 40 : ((this.onGround && getAttackTarget() == null) ? 5 : 180);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    this.fallDistance = 0.0F;
    if (EngenderConfig.mobs.useMobTalkerModels && !isChild() && this.onGround) {
      this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + this.rand.nextDouble() - 0.5D, this.posY + 0.75D + (MathHelper.cos(this.ticksExisted * 0.2F) * 0.1F), this.posZ + this.rand.nextDouble() - 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
      if (this.ticksExisted % 30 == 0)
        playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, 0.1F, 0.75F); 
    } 
  }
  
  public boolean isElder() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityElderGuardian(this.world);
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if ((this.ticksExisted + getEntityId()) % 200 == 0) {
      List<EntityLivingBase> list = this.world.getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
              return (EntityElderGuardian.this.getDistanceSq((Entity)p_apply_1_) < 2500.0D && EntityElderGuardian.this.getRNG().nextInt(20) == 0);
            }
          });
      int j = 1;
      int k = 6000;
      int l = 1200;
      for (EntityLivingBase entityplayermp : list) {
        Potion potion;
        if (!false) {
          potion = MobEffects.SLOWNESS;
        } else {
          potion = MobEffects.SPEED;
        } 
        if (!entityplayermp.isPotionActive(potion) || entityplayermp.getActivePotionEffect(potion).getAmplifier() < j || entityplayermp.getActivePotionEffect(potion).getDuration() < l) {
          this.world.playSound(null, entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, getSoundCategory(), getSoundVolume(), isChild() ? 1.5F : 1.0F);
          entityplayermp.addPotionEffect(new PotionEffect(potion, k, j, true, false));
          entityplayermp.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0, true, false));
        } 
      } 
    } 
    if (this.ticksExisted % 20 == 0) {
      List<EntityPlayerMP> list = this.world.getPlayers(EntityPlayerMP.class, new Predicate<EntityPlayerMP>() {
            public boolean apply(@Nullable EntityPlayerMP p_apply_1_) {
              return (EntityElderGuardian.this.getDistanceSq((Entity)p_apply_1_) < 2500.0D);
            }
          });
      for (EntityPlayerMP entityplayermp : list) {
        Potion potion;
        if (false) {
          potion = MobEffects.HASTE;
        } else {
          potion = MobEffects.MINING_FATIGUE;
        } 
        if (this.ticksExisted == 40 || !entityplayermp.isPotionActive(potion) || entityplayermp.getActivePotionEffect(potion).getAmplifier() < 2 || entityplayermp.getActivePotionEffect(potion).getDuration() < 1200) {
          entityplayermp.connection.sendPacket((Packet)new SPacketChangeGameState(10, 0.0F));
          entityplayermp.addPotionEffect(new PotionEffect(potion, 6000, 2, true, false));
          entityplayermp.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0, true, false));
          faceEntity((Entity)entityplayermp, 180.0F, getVerticalFaceSpeed());
        } 
      } 
    } 
  }
}

