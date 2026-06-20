package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntitySpellcasterIllager;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityPig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityRabbit;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityLargeFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntitySmallFireballOther;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EntityEvoker extends EntitySpellcasterIllager implements IRangedAttackMob {
  public EntityEvoker(World worldIn) {
    super(worldIn);
    this.experienceValue = 50;
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(0, new EntityAIFollowLeader(this, 1.2D, 32.0F, 6.0F));
    this.tasks.addTask(1, new AICastingSpell());
    this.tasks.addTask(2, new AIConvertingSpell());
    this.tasks.addTask(2, new AIReinforcingSpell());
    this.tasks.addTask(2, new AIPolymorphSpell());
    this.tasks.addTask(2, new AIWololoSpell());
    this.tasks.addTask(2, new AISummonSpell());
    this.tasks.addTask(3, new AISummonMeteorStormSpell());
    this.tasks.addTask(3, new AIDisintigrationRaySpell());
    this.tasks.addTask(3, new AILightningBoltSpell());
    this.tasks.addTask(3, new AIPoisonSpraySpell());
    this.tasks.addTask(4, new AISmallFireballSpell());
    this.tasks.addTask(4, new AIFireballSpell());
    this.tasks.addTask(4, new AIFrostRaySpell());
    this.tasks.addTask(4, new AIMagicMissileSpell());
    this.tasks.addTask(5, new AIAttackSpell());
    this.tasks.addTask(6, new EntityAIFriendlyAttackMelee(this, 1.0D, false));
    this.tasks.addTask(7, new EntityAIWander(this, 0.8D, 80));
    this.tasks.addTask(8, new EntityAILookIdle(this));
  }
  
  public String getDescName() {
    return "evoker";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityEvoker(this.world);
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 3.0F;
  }
  
  public void createChild() {
    super.createChild();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (int i = 0; i < 1; i++) {
        EntityEvoker baby = new EntityEvoker(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-100000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity(baby);
      }  
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString(this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.4F) : super.getSoundPitch();
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_EVOCATION_ILLAGER;
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
    double d1 = 1.25D;
    Vec3d vec3d = getLook(1.0F);
    double d2 = target.posX - this.posX + vec3d.x * d1;
    double d3 = (target.getEntityBoundingBox()).minY + (target.height / 2.0F) - 0.5D + this.posY + (this.height / 2.0F);
    double d4 = target.posZ - this.posZ + vec3d.z * d1;
    this.world.playEvent(null, 1016, new BlockPos(this), 0);
    EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(this.world, this, d2, d3, d4);
    entitylargefireball.explosionPower = 4;
    entitylargefireball.posX = this.posX + vec3d.x * d1;
    entitylargefireball.posY = this.posY + (this.height / 2.0F) + 0.5D;
    entitylargefireball.posZ = this.posZ + vec3d.z * d1;
    this.world.spawnEntity(entitylargefireball);
    swingArm(EnumHand.MAIN_HAND);
  }
  
  public void attackEntityWithRangedAttack2(EntityLivingBase target, float p_82196_2_) {
    double d1 = 1.25D;
    Vec3d vec3d = getLook(1.0F);
    double d2 = target.posX - this.posX + vec3d.x * d1;
    double d3 = (target.getEntityBoundingBox()).minY + (target.height / 2.0F) - 0.5D + this.posY + (this.height / 2.0F);
    double d4 = target.posZ - this.posZ + vec3d.z * d1;
    this.world.playEvent(null, 1016, new BlockPos(this), 0);
    EntitySmallFireballOther entitylargefireball = new EntitySmallFireballOther(this.world, this, d2, d3, d4);
    entitylargefireball.posX = this.posX + vec3d.x * d1;
    entitylargefireball.posY = this.posY + (this.height / 2.0F) + 0.5D;
    entitylargefireball.posZ = this.posZ + vec3d.z * d1;
    this.world.spawnEntity(entitylargefireball);
    swingArm(EnumHand.MAIN_HAND);
  }
  
  public void attackEntityWithSpray(EntityLivingBase target, float p_82196_2_) {
    double d = 1.25D;
    Vec3d vec3d = getLook(1.0F);
    EntityPoisonSpray entitysnowball = new EntityPoisonSpray(this.world, this);
    double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
    double d1 = target.posX - this.posX + vec3d.x * d;
    double d2 = d0 - entitysnowball.posY;
    double d3 = target.posZ - this.posZ + vec3d.z * d;
    float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.1F;
    entitysnowball.shoot(d1, d2 + f, d3, 1.2F, 1.0F);
    playSound(SoundEvents.BLOCK_SLIME_STEP, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
    this.world.spawnEntity(entitysnowball);
    swingArm(EnumHand.MAIN_HAND);
  }
  
  public void fireRay(Entity entity, double x, double y, double z) {
    if (entity != null && entity.isEntityAlive()) {
      double d3 = x;
      double d4 = y;
      double d5 = z;
      double d6 = entity.posX - d3;
      double d7 = entity.posY - d4;
      double d8 = entity.posZ - d5;
      EntityDisintigrationRay entitywitherskull = new EntityDisintigrationRay(this.world, entity, this, d6, d7, d8);
      entitywitherskull.posY = d4;
      entitywitherskull.posX = d3;
      entitywitherskull.posZ = d5;
      entitywitherskull.accelerationY = d4;
      entitywitherskull.accelerationX = d3;
      entitywitherskull.accelerationZ = d5;
      entitywitherskull.targetEntity = entity;
      this.world.spawnEntity(entitywitherskull);
    } 
  }
  
  public void fireCone(Entity entity, double x, double y, double z) {
    if (entity != null && entity.isEntityAlive()) {
      double d3 = x;
      double d4 = y;
      double d5 = z;
      double d6 = entity.posX - d3;
      double d7 = entity.posY - d4;
      double d8 = entity.posZ - d5;
      EntityFrostRay entitywitherskull = new EntityFrostRay(this.world, entity, this, d6, d7, d8);
      entitywitherskull.posY = d4;
      entitywitherskull.posX = d3;
      entitywitherskull.posZ = d5;
      entitywitherskull.accelerationY = d4;
      entitywitherskull.accelerationX = d3;
      entitywitherskull.accelerationZ = d5;
      entitywitherskull.targetEntity = entity;
      this.world.spawnEntity(entitywitherskull);
    } 
  }
  
  public void performSpecialAttack() {
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(64.0D, 64.0D, 64.0D), Predicates.and(EntitySelectors.IS_ALIVE));
    if (list != null && !list.isEmpty())
        for (EntityLivingBase entity : list) {
            if (entity != null && !false)
                if (!false) {
                    try {
                        ReflectionHelper.findField(entity.getClass(), new String[]{"shieldTime"}).setInt(entity, 0);
                    } catch (Exception exception) {
                    }
                    double d1 = entity.posX + (this.rand.nextFloat() * 16.0F - 8.0F);
                    double d2 = entity.posY + 20.0D + (this.rand.nextFloat() * 20.0F - 10.0F);
                    double d3 = entity.posZ + (this.rand.nextFloat() * 16.0F - 8.0F);
                    fireLightning(entity, d1, d2, d3);
                    this.world.addWeatherEffect(new EntityLightningBolt(this.world, entity.posX, entity.posY, entity.posZ, true));
                }
        }
    setSpecialAttackTimer(1800);
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (getConvertingTarget() != null && getDistanceSq(getConvertingTarget()) > 256.0D)
      getNavigator().tryMoveToEntityLiving(getConvertingTarget(), 1.0D);
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) > 512.0D)
      getNavigator().tryMoveToEntityLiving(getAttackTarget(), 1.0D);
    if (getAllyTarget() != null && !getAllyTarget().isEntityAlive())
      setConvertingTarget(null);
    if (getConvertingTarget() != null && !getConvertingTarget().isEntityAlive())
      setConvertingTarget(null);
    if (getWololoTarget() != null && !getWololoTarget().isEntityAlive())
      setWololoTarget(null);
    if (getAttackTarget() != null && !getAttackTarget().isEntityAlive())
      setAttackTarget(null);
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getJukeboxToDanceTo() != null) {
      if (this.ticksExisted % 10 == 0)
        playSound(SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON, isSneaking() ? 0.1F : 1.0F, this.rand.nextFloat() * 2.0F); 
      float f = this.renderYawOffset * 0.017453292F + MathHelper.cos(this.ticksExisted * 0.6662F) * 0.5F;
      float f1 = MathHelper.cos(f);
      float f2 = MathHelper.sin(f);
      this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + f1 * (isChild() ? 0.3D : 0.6D) * getFittness(), this.posY + (isChild() ? 0.9D : 1.8D) * getFittness(), this.posZ + f2 * (isChild() ? 0.3D : 0.6D) * getFittness(), this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
      this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX - f1 * (isChild() ? 0.3D : 0.6D) * getFittness(), this.posY + (isChild() ? 0.9D : 1.8D) * getFittness(), this.posZ - f2 * (isChild() ? 0.3D : 0.6D) * getFittness(), this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
    } 
    if (!getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty()) {
      setItemStackToSlot(EntityEquipmentSlot.OFFHAND, getItemStackFromSlot(EntityEquipmentSlot.HEAD));
      setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
    } 
    if (!getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty()) {
      setItemStackToSlot(EntityEquipmentSlot.OFFHAND, getItemStackFromSlot(EntityEquipmentSlot.CHEST));
      setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
    } 
    if (!getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty()) {
      setItemStackToSlot(EntityEquipmentSlot.OFFHAND, getItemStackFromSlot(EntityEquipmentSlot.LEGS));
      setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
    } 
    if (!getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty()) {
      setItemStackToSlot(EntityEquipmentSlot.OFFHAND, getItemStackFromSlot(EntityEquipmentSlot.LEGS));
      setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
    } 
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    setEquipmentBasedOnDifficulty(difficulty);
    setEnchantmentBasedOnDifficulty(difficulty);
    return super.onInitialSpawn(difficulty, livingdata);
  }
  
  protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.TOTEM_OF_UNDYING));
    setDropChance(EntityEquipmentSlot.HEAD, 0.0F);
    setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.TOTEM_OF_UNDYING));
    setDropChance(EntityEquipmentSlot.CHEST, 0.0F);
    setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(Items.TOTEM_OF_UNDYING));
    setDropChance(EntityEquipmentSlot.LEGS, 0.0F);
    setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(Items.TOTEM_OF_UNDYING));
    setDropChance(EntityEquipmentSlot.FEET, 0.0F);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() - 0.3F); 
    return SoundEvents.EVOCATION_ILLAGER_DEATH;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() - 0.3F); 
    return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
  }
  
  protected float applyPotionDamageCalculations(DamageSource p_70672_1_, float p_70672_2_) {
    p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
    if (p_70672_1_.getTrueSource() instanceof EntityLivingBase && false)
      p_70672_2_ = 0.0F; 
    if (p_70672_1_.isMagicDamage() && p_70672_1_.getDamageType() != "antimatter")
      p_70672_2_ = (float)(p_70672_2_ * 0.05D); 
    return p_70672_2_;
  }
  
  protected SoundEvent getSpellSound() {
    return SoundEvents.EVOCATION_ILLAGER_CAST_SPELL;
  }
  
  class AIAttackSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIAttackSpell() {
      super();
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 100;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      double d0 = EntityEvoker.this.posY;
      double d1 = EntityEvoker.this.posY;
      float f = (float)MathHelper.atan2(entitylivingbase.posZ - EntityEvoker.this.posZ, entitylivingbase.posX - EntityEvoker.this.posX);
      if (EntityEvoker.this.getDistance(entitylivingbase) < 4.0D && entitylivingbase.posY <= d0 + 1.0D) {
        d0 = EntityEvoker.this.posY;
        d1 = EntityEvoker.this.posY;
        int i;
        for (i = 0; i < 5; i++) {
          float f1 = f + i * 3.1415927F * 0.4F;
          spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f1) * 1.0D, EntityEvoker.this.posZ + MathHelper.sin(f1) * 1.0D, d0, d1, f1, 0);
        } 
        for (i = 0; i < 10; i++) {
          float f1 = f + i * 3.1415927F * 0.2F;
          spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f1) * 2.0D, EntityEvoker.this.posZ + MathHelper.sin(f1) * 2.0D, d0, d1, f1, 10);
        } 
        if (EntityEvoker.this.getLevel() >= 5)
          for (i = 0; i < 15; i++) {
            float f1 = f + i * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f1) * 3.0D, EntityEvoker.this.posZ + MathHelper.sin(f1) * 3.0D, d0, d1, f1, 20);
          }  
        if (EntityEvoker.this.getLevel() >= 10)
          for (int k = 0; k < 20; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f2) * 4.0D, EntityEvoker.this.posZ + MathHelper.sin(f2) * 4.0D, d0, d1, f2, 30);
          }  
        if (EntityEvoker.this.getLevel() >= 15)
          for (int k = 0; k < 25; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f2) * 5.0D, EntityEvoker.this.posZ + MathHelper.sin(f2) * 5.0D, d0, d1, f2, 40);
          }  
        if (EntityEvoker.this.getLevel() >= 20)
          for (int k = 0; k < 30; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f2) * 6.0D, EntityEvoker.this.posZ + MathHelper.sin(f2) * 6.0D, d0, d1, f2, 50);
          }  
        if (EntityEvoker.this.getLevel() >= 25)
          for (int k = 0; k < 35; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f2) * 7.0D, EntityEvoker.this.posZ + MathHelper.sin(f2) * 7.0D, d0, d1, f2, 60);
          }  
        if (EntityEvoker.this.getLevel() >= 30)
          for (int k = 0; k < 40; k++) {
            float f2 = f + k * 3.1415927F * 0.1F;
            spawnFangs(EntityEvoker.this.posX + MathHelper.cos(f2) * 8.0D, EntityEvoker.this.posZ + MathHelper.sin(f2) * 8.0D, d0, d1, f2, 70);
          }  
      } else if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive()) {
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(EntityEvoker.this.world)) {
          float j = EntityEvoker.this.renderYawOffset * 0.017453292F;
          float f1 = MathHelper.cos(j);
          float f2 = MathHelper.sin(j);
          EntityInvisibleFangsProjectile entitymagicmissiles = new EntityInvisibleFangsProjectile(EntityEvoker.this.world, entitylivingbase, EntityEvoker.this, EntityEvoker.this.posX, EntityEvoker.this.posY, EntityEvoker.this.posZ);
          EntityEvoker.this.world.spawnEntity(entitymagicmissiles);
        } 
      } 
    }
    
    private void spawnFangs(double p_190876_1_, double p_190876_3_, double p_190876_5_, double p_190876_7_, float p_190876_9_, int p_190876_10_) {
      BlockPos blockpos = new BlockPos(p_190876_1_, p_190876_7_, p_190876_3_);
      boolean flag = true;
      double d0 = 0.0D;
      if (flag && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(EntityEvoker.this.world)) {
        EntityEvokerFangOther entityevokerfangs = new EntityEvokerFangOther(EntityEvoker.this.world, p_190876_1_, blockpos.getY() + d0, p_190876_3_, p_190876_9_, p_190876_10_, EntityEvoker.this);
        EntityEvoker.this.world.spawnEntity(entityevokerfangs);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FANGS;
    }
  }
  
  class AIFireballSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIFireballSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityEvoker.this.getAttackTarget() != null && EntityEvoker.this.getDistance(EntityEvoker.this.getAttackTarget()) > 45.72D && EntityEvoker.this.getDistanceSq(EntityEvoker.this.getAttackTarget()) < 45.0D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 3);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 160;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive())
        EntityEvoker.this.attackEntityWithRangedAttack(entitylivingbase, 1.0F); 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FIREBALL;
    }
  }
  
  class AISmallFireballSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AISmallFireballSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityEvoker.this.getAttackTarget() != null && EntityEvoker.this.getDistance(EntityEvoker.this.getAttackTarget()) > 36.576D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 1);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 80;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive())
        EntityEvoker.this.attackEntityWithRangedAttack2(entitylivingbase, 1.0F); 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FIREBOLT;
    }
  }
  
  class AIPoisonSpraySpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIPoisonSpraySpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityEvoker.this.getAttackTarget() != null && EntityEvoker.this.getDistance(EntityEvoker.this.getAttackTarget()) > 9.144D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 2);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 120;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive())
        EntityEvoker.this.attackEntityWithSpray(entitylivingbase, 1.0F); 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.POISON_SPRAY;
    }
  }
  
  class AIMagicMissileSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIMagicMissileSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityEvoker.this.getAttackTarget() != null && EntityEvoker.this.getDistance(EntityEvoker.this.getAttackTarget()) > 36.576D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 5);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 160;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive())
        for (int i = 0; i < (EntityEvoker.this.isHero() ? 18 : 9); i++) {
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(EntityEvoker.this.world)) {
            EntityMagicMissile entitymagicmissiles = new EntityMagicMissile(EntityEvoker.this.world, entitylivingbase, EntityEvoker.this, EntityEvoker.this.posX, EntityEvoker.this.posY + 2.0D, EntityEvoker.this.posZ);
            entitymagicmissiles.posY = EntityEvoker.this.posY + 2.0D;
            Random random = new Random();
            entitymagicmissiles.motionX += random.nextDouble() * 2.0D - 1.0D;
            entitymagicmissiles.motionY += random.nextDouble() * 2.0D;
            entitymagicmissiles.motionZ += random.nextDouble() * 2.0D - 1.0D;
            EntityEvoker.this.world.spawnEntity(entitymagicmissiles);
          } 
        }  
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.MAGIC_MISSLE;
    }
  }
  
  class AIDisintigrationRaySpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIDisintigrationRaySpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityEvoker.this.getAttackTarget() != null && EntityEvoker.this.getDistance(EntityEvoker.this.getAttackTarget()) > 18.288D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 20);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 360;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive()) {
        try {
          ReflectionHelper.findField(entitylivingbase.getClass(), new String[] { "shieldTime" }).setInt(entitylivingbase, 0);
        } catch (Exception exception) {}
        float f = EntityEvoker.this.renderYawOffset * 0.017453292F;
        float f1 = MathHelper.cos(f);
        float f2 = MathHelper.sin(f);
        double d1 = EntityEvoker.this.posX - (f2 * 0.4F);
        double d2 = EntityEvoker.this.posY + 1.25D;
        double d3 = EntityEvoker.this.posZ + (f1 * 0.4F);
        EntityEvoker.this.fireRay(entitylivingbase, d1, d2, d3);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.DISINTIGRATION_RAY;
    }
  }
  
  class AIFrostRaySpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIFrostRaySpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityEvoker.this.getAttackTarget() != null && EntityEvoker.this.getDistance(EntityEvoker.this.getAttackTarget()) > 18.288D)
        return false; 
      return (EntityEvoker.this.getLevel() >= 15);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 200;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive()) {
        try {
          ReflectionHelper.findField(entitylivingbase.getClass(), new String[] { "shieldTime" }).setInt(entitylivingbase, 0);
        } catch (Exception exception) {}
        float f = EntityEvoker.this.renderYawOffset * 0.017453292F;
        float f1 = MathHelper.cos(f);
        float f2 = MathHelper.sin(f);
        double d1 = EntityEvoker.this.posX - (f2 * 0.4F);
        double d2 = EntityEvoker.this.posY + 1.25D;
        double d3 = EntityEvoker.this.posZ + (f1 * 0.4F);
        EntityEvoker.this.fireCone(entitylivingbase, d1, d2, d3);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FROST_RAY;
    }
  }
  
  class AILightningBoltSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AILightningBoltSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityEvoker.this.getAttackTarget() != null && EntityEvoker.this.getDistance(EntityEvoker.this.getAttackTarget()) > ((EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) ? 64.0D : 30.48D))
        return false; 
      return (EntityEvoker.this.getLevel() >= 10);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return (EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) ? 1200 : 240;
    }
    
    protected void castSpell() {
      EntityLivingBase entitylivingbase = EntityEvoker.this.getAttackTarget();
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase)) && entitylivingbase != null && entitylivingbase.isEntityAlive()) {
        try {
          ReflectionHelper.findField(entitylivingbase.getClass(), new String[] { "shieldTime" }).setInt(entitylivingbase, 0);
        } catch (Exception exception) {}
        if (EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) {
          EntityEvoker.this.performSpecialAttack();
        } else {
          float f = EntityEvoker.this.renderYawOffset * 0.017453292F;
          float f1 = MathHelper.cos(f);
          float f2 = MathHelper.sin(f);
          double d1 = EntityEvoker.this.posX - (f2 * 0.4F);
          double d2 = EntityEvoker.this.posY + 1.25D;
          double d3 = EntityEvoker.this.posZ + (f1 * 0.4F);
          EntityEvoker.this.fireLightning(entitylivingbase, d1, d2, d3);
        } 
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return (EntityEvoker.this.isHero() && EntityEvoker.this.getSpecialAttackTimer() <= 0) ? SoundEvents.ENTITY_LIGHTNING_THUNDER : SoundEvents.EVOCATION_ILLAGER_PREPARE_ATTACK;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.LIGHTNING_BOLT;
    }
  }
  
  class AISummonSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AISummonSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      int count = 0;
      List<EntityTameBase> list = EntityEvoker.this.world.getEntitiesWithinAABB(EntityTameBase.class, EntityEvoker.this.getEntityBoundingBox().grow(32.0D), EntitySelectors.IS_ALIVE);
      if (!list.isEmpty()) {
        EntityTameBase entity = list.get(EntityEvoker.this.rand.nextInt(list.size()));
        if (!entity.hasLimitedLife()) {
          list.remove(entity);
        } else {
          count++;
          if (count > 10)
            return false; 
        } 
      } 
      return true;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 60;
    }
    
    protected int getCastingInterval() {
      return 400;
    }
    
    protected void castSpell() {
      switch (EntityEvoker.this.rand.nextInt(10)) {
        case 1:
          if (EntityEvoker.this.getLevel() >= 10) {
            for (int j = 0; j < (4 + EntityEvoker.this.rand.nextInt(6)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
              EntityZombie entityvex = new EntityZombie(EntityEvoker.this.world);
              entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
              entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
              EntityEvoker.this.world.spawnEntity(entityvex);
            } 
            return;
          } 
        case 2:
          if (EntityEvoker.this.getLevel() >= 20) {
            for (int j = 0; j < (2 + EntityEvoker.this.rand.nextInt(6)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
              EntitySkeleton entityvex = new EntitySkeleton(EntityEvoker.this.world);
              entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
              entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
              EntityEvoker.this.world.spawnEntity(entityvex);
            } 
            return;
          } 
        case 3:
          if (EntityEvoker.this.getLevel() >= 50) {
            for (int j = 0; j < (2 + EntityEvoker.this.rand.nextInt(4)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
              EntityBlaze entityvex = new EntityBlaze(EntityEvoker.this.world);
              entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
              entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
              EntityEvoker.this.world.spawnEntity(entityvex);
            } 
            return;
          } 
        case 4:
          if (EntityEvoker.this.getLevel() >= 100) {
            for (int j = 0; j < (2 + EntityEvoker.this.rand.nextInt(2)) * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
              EntityEnderman entityvex = new EntityEnderman(EntityEvoker.this.world);
              entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
              entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
              EntityEvoker.this.world.spawnEntity(entityvex);
            } 
            return;
          } 
        case 5:
          if (EntityEvoker.this.getLevel() >= 150) {
            for (int j = 0; j < 2 * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
              EntityIceGolem entityvex = new EntityIceGolem(EntityEvoker.this.world);
              entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
              entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
              EntityEvoker.this.world.spawnEntity(entityvex);
            } 
            return;
          } 
        case 6:
          if (EntityEvoker.this.getLevel() >= 200) {
            for (int j = 0; j < 2 * (EntityEvoker.this.isHero() ? 2 : 1); j++) {
              BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
              EntityMagmaGolem entityvex = new EntityMagmaGolem(EntityEvoker.this.world);
              entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
              entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
              if (!EntityEvoker.this.isWild())
                entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
              entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
              entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
              EntityEvoker.this.world.spawnEntity(entityvex);
            } 
            return;
          } 
        case 7:
          if (EntityEvoker.this.getLevel() >= 300) {
            BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
            EntityWither entityvex = new EntityWither(EntityEvoker.this.world);
            entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
            entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
            if (!EntityEvoker.this.isWild())
              entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
            entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
            entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
            entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
            EntityEvoker.this.world.spawnEntity(entityvex);
            return;
          } 
          break;
      } 
      for (int i = 0; i < (4 + EntityEvoker.this.rand.nextInt(8)) * (EntityEvoker.this.isHero() ? 2 : 1); i++) {
        BlockPos blockpos = (new BlockPos(EntityEvoker.this)).add(-3 + EntityEvoker.this.rand.nextInt(6), EntityEvoker.this.rand.nextInt(4), -3 + EntityEvoker.this.rand.nextInt(6));
        EntityVex entityvex = new EntityVex(EntityEvoker.this.world);
        entityvex.moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
        entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(blockpos), null);
        if (!EntityEvoker.this.isWild())
          entityvex.setOwnerId(EntityEvoker.this.getOwnerId()); 
        entityvex.setBoundOrigin(blockpos);
        entityvex.setLimitedLife((int)(EntityEvoker.this.getIntelligence() * (Math.max(EntityEvoker.this.getLevel(), 10))));
        entityvex.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 4000));
        entityvex.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 4000));
        EntityEvoker.this.world.spawnEntity(entityvex);
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.SUMMON_VEX;
    }
  }
  
  class AISummonMeteorStormSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AISummonMeteorStormSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      return (EntityEvoker.this.getLevel() >= 50);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 1200;
    }
    
    protected void castSpell() {
      List<Entity> list = EntityEvoker.this.world.getEntitiesWithinAABBExcludingEntity(EntityEvoker.this, EntityEvoker.this.getEntityBoundingBox().grow(64.0D));
      if (list != null && !list.isEmpty())
          for (Entity entity : list) {
              if (entity != null && entity instanceof EntityLivingBase && !false) {
                  double d1 = entity.posX + EntityEvoker.this.rand.nextDouble() * 50.0D - 25.0D;
                  double d2 = entity.posY + 100.0D + EntityEvoker.this.rand.nextDouble() * 50.0D - 25.0D;
                  double d3 = entity.posZ + EntityEvoker.this.rand.nextDouble() * 50.0D - 25.0D;
                  double d4 = entity.posX - d1;
                  double d5 = entity.posY - d2;
                  double d6 = entity.posZ - d3;
                  EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(EntityEvoker.this.world, EntityEvoker.this, d4, d5, d6);
                  EntityEvoker.this.world.playEvent(null, 1016, new BlockPos(entitylargefireball), 0);
                  entitylargefireball.explosionPower = 8;
                  entitylargefireball.posX = d1;
                  entitylargefireball.posY = d2;
                  entitylargefireball.posZ = d3;
                  if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(EntityEvoker.this.world))
                      EntityEvoker.this.world.spawnEntity(entitylargefireball);
              }
          }
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.METEOR_STORM;
    }
  }
  
  public class AIWololoSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntitySheep> wololoSelector = p_apply_1_ -> (EntityEvoker.this.getOwner() != null) ? ((p_apply_1_.getFleeceColor() == EnumDyeColor.RED)) : ((p_apply_1_.getFleeceColor() == EnumDyeColor.BLUE));
    
    public AIWololoSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (EntityEvoker.this.getAttackTarget() != null)
        return false; 
      if (EntityEvoker.this.isSpellcasting())
        return false; 
      if (EntityEvoker.this.ticksExisted < this.spellCooldown)
        return false; 
      List<EntitySheep> list = EntityEvoker.this.world.getEntitiesWithinAABB(EntitySheep.class, EntityEvoker.this.getEntityBoundingBox().grow(16.0D, 4.0D, 16.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityEvoker.this.setWololoTarget(list.get(EntityEvoker.this.rand.nextInt(list.size())));
      return true;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getWololoTarget() != null && this.spellWarmup > 0);
    }
    
    public void resetTask() {
      super.resetTask();
      EntityEvoker.this.setWololoTarget(null);
    }
    
    protected void castSpell() {
      EntitySheep entitysheep = EntityEvoker.this.getWololoTarget();
      if (entitysheep != null && entitysheep.isEntityAlive()) {
        EntityEvoker.this.playSound(ESound.converted, 1.0F, 1.0F);
        if (EntityEvoker.this.isWild()) {
          entitysheep.setFleeceColor(EnumDyeColor.RED);
        } else {
          entitysheep.setFleeceColor(EnumDyeColor.BLUE);
        } 
      } 
    }
    
    protected int getCastWarmupTime() {
      return 40;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 40;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_WOLOLO;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.WOLOLO;
    }
  }
  
  public class AIConvertingSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = p_apply_1_ -> (p_apply_1_.isWild() && p_apply_1_.isEntityAlive() && !p_apply_1_.isABoss() && p_apply_1_.getTier() != EnumTier.TIER6);
    
    public AIConvertingSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (EntityEvoker.this.isWild())
        return false; 
      if (EntityEvoker.this.isSpellcasting())
        return false; 
      if (EntityEvoker.this.ticksExisted < this.spellCooldown)
        return false; 
      List<EntityTameBase> list = EntityEvoker.this.world.getEntitiesWithinAABB(EntityTameBase.class, EntityEvoker.this.getEntityBoundingBox().grow(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityEvoker.this.rand.nextInt(list.size()));
      if (false || entity.isABoss() || entity.isHero() || entity.hasLimitedLife()) {
        list.remove(entity);
        return false;
      } 
      EntityEvoker.this.setConvertingTarget(entity);
      return true;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getConvertingTarget() != null && EntityEvoker.this.getConvertingTarget().isEntityAlive() && EntityEvoker.this.getConvertingTarget().isWild() && this.spellWarmup > 0);
    }
    
    public void resetTask() {
      super.resetTask();
      if (EntityEvoker.this.getConvertingTarget() != null && !EntityEvoker.this.getConvertingTarget().isWild())
        EntityEvoker.this.setConvertingTarget(null);
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityEvoker.this.getConvertingTarget();
      if (entitysheep != null && entitysheep.isEntityAlive()) {
        entitysheep.spawnExplosionParticle();
        entitysheep.getNavigator().tryMoveToEntityLiving(EntityEvoker.this, 1.2D);
        entitysheep.incrementConversion((EntityPlayer)EntityEvoker.this.getOwner());
        for (int i1 = 0; i1 < EntityEvoker.this.getLevel() / 10 + 1; i1++)
          entitysheep.incrementConversion((EntityPlayer)EntityEvoker.this.getOwner()); 
      } 
    }
    
    protected int getCastWarmupTime() {
      return 10;
    }
    
    protected int getCastingTime() {
      return 20;
    }
    
    protected int getCastingInterval() {
      return 10;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_WOLOLO;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.CONVERT;
    }
  }
  
  public class AIReinforcingSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = p_apply_1_ -> (!p_apply_1_.isWild() && p_apply_1_.isEntityAlive());
    
    public AIReinforcingSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (EntityEvoker.this.isWild())
        return false; 
      if (EntityEvoker.this.isSpellcasting())
        return false; 
      if (EntityEvoker.this.ticksExisted < this.spellCooldown)
        return false; 
      List<EntityTameBase> list = EntityEvoker.this.world.getEntitiesWithinAABB(EntityTameBase.class, EntityEvoker.this.getEntityBoundingBox().grow(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityEvoker.this.rand.nextInt(list.size()));
      if ((!entity.isPotionActive(MobEffects.WEAKNESS) && !entity.isPotionActive(MobEffects.SLOWNESS) && !entity.isPotionActive(MobEffects.NAUSEA) && !entity.isPotionActive(MobEffects.LEVITATION) && !entity.isPotionActive(MobEffects.BLINDNESS) && !entity.isPotionActive(MobEffects.POISON) && !entity.isPotionActive(MobEffects.WITHER) && !entity.isBurning()) || entity.hasLimitedLife()) {
        list.remove(entity);
        return false;
      } 
      EntityEvoker.this.setAllyTarget(entity);
      return true;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityEvoker.this.getAllyTarget() != null && EntityEvoker.this.getAllyTarget().isEntityAlive() && false && (EntityEvoker.this.getAllyTarget().isPotionActive(MobEffects.NAUSEA) || EntityEvoker.this.getAllyTarget().isPotionActive(MobEffects.LEVITATION) || EntityEvoker.this.getAllyTarget().isPotionActive(MobEffects.WEAKNESS) || EntityEvoker.this.getAllyTarget().isPotionActive(MobEffects.BLINDNESS) || EntityEvoker.this.getAllyTarget().isPotionActive(MobEffects.SLOWNESS) || EntityEvoker.this.getAllyTarget().isPotionActive(MobEffects.WITHER) || EntityEvoker.this.getAllyTarget().isPotionActive(MobEffects.POISON) || EntityEvoker.this.getAllyTarget().isBurning()) && this.spellWarmup > 0);
    }
    
    public void resetTask() {
      super.resetTask();
      EntityEvoker.this.setAllyTarget(null);
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityEvoker.this.getAllyTarget();
      if (entitysheep != null && entitysheep.isEntityAlive() && false)
        if (entitysheep.isBurning() || entitysheep.isPotionActive(MobEffects.LEVITATION) || entitysheep.isPotionActive(MobEffects.WEAKNESS) || entitysheep.isPotionActive(MobEffects.BLINDNESS) || entitysheep.isPotionActive(MobEffects.LEVITATION) || entitysheep.isPotionActive(MobEffects.NAUSEA) || entitysheep.isPotionActive(MobEffects.SLOWNESS) || entitysheep.isPotionActive(MobEffects.POISON) || entitysheep.isPotionActive(MobEffects.WITHER)) {
          if (entitysheep instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntitySheep)
            entitysheep.setCustomNameTag("jeb_"); 
          entitysheep.spawnExplosionParticle();
          entitysheep.extinguish();
          entitysheep.removeActivePotionEffect(MobEffects.POISON);
          entitysheep.removeActivePotionEffect(MobEffects.WITHER);
          entitysheep.removeActivePotionEffect(MobEffects.SLOWNESS);
          entitysheep.removeActivePotionEffect(MobEffects.WEAKNESS);
          entitysheep.removeActivePotionEffect(MobEffects.BLINDNESS);
          entitysheep.removeActivePotionEffect(MobEffects.NAUSEA);
          entitysheep.removeActivePotionEffect(MobEffects.LEVITATION);
          entitysheep.removeActivePotionEffect(MobEffects.HUNGER);
          entitysheep.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1000 * EntityEvoker.this.getLevel()));
          entitysheep.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1000 * EntityEvoker.this.getLevel()));
          EntityEvoker.this.getOwner().extinguish();
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.POISON);
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.WITHER);
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.SLOWNESS);
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.WEAKNESS);
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.NAUSEA);
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.BLINDNESS);
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.LEVITATION);
          EntityEvoker.this.getOwner().removeActivePotionEffect(MobEffects.HUNGER);
          EntityEvoker.this.getOwner().addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1000 * EntityEvoker.this.getLevel()));
          EntityEvoker.this.getOwner().addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1));
          EntityEvoker.this.getOwner().addPotionEffect(new PotionEffect(MobEffects.SATURATION, 20));
          EntityEvoker.this.getOwner().addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 1000 * EntityEvoker.this.getLevel()));
          if ((entitysheep instanceof EntityPig && !((EntityPig)entitysheep).getSaddled()) || (entitysheep instanceof EntityCreeper && !((EntityCreeper)entitysheep).getPowered())) {
            entitysheep.onStruckByLightning(null);
            net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitysheep).addWeatherEffect(new EntityLightningBolt(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitysheep), entitysheep.posX - 0.5D, entitysheep.posY, entitysheep.posZ - 0.5D, true));
          } 
          if (entitysheep instanceof EntityRabbit && ((EntityRabbit)entitysheep).getRabbitType() != 99) {
            ((EntityRabbit)entitysheep).setRabbitType(99);
            entitysheep.ticksExisted = 1;
            net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitysheep).addWeatherEffect(new EntityLightningBolt(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitysheep), entitysheep.posX - 0.5D, entitysheep.posY, entitysheep.posZ - 0.5D, true));
          } 
          if (entitysheep instanceof EntitySlime && ((EntitySlime)entitysheep).getSlimeSize() <= 1) {
            ((EntitySlime)entitysheep).setSlimeSize((EntityEvoker.this.rand.nextInt(4) == 0) ? 4 : 2);
            entitysheep.ticksExisted = 1;
            entitysheep.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 2.0F, 1.0F);
            net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitysheep).addWeatherEffect(new EntityLightningBolt(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitysheep), entitysheep.posX - 0.5D, entitysheep.posY, entitysheep.posZ - 0.5D, true));
          } 
          resetTask();
        }  
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 0;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.BUFFER_EVOKER;
    }
  }
  
  public class AIPolymorphSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIPolymorphSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      return (EntityEvoker.this.getPolymorphTime() <= 0 && EntityEvoker.this.getLevel() >= 50);
    }
    
    protected int getCastingTime() {
      return 60;
    }
    
    protected int getCastingInterval() {
      return 2400;
    }
    
    protected void castSpell() {
      if (EntityEvoker.this.getLevel() >= 300) {
        EntityEnderDragon entityvex = new EntityEnderDragon(EntityEvoker.this.world);
        entityvex.copyLocationAndAnglesFrom(EntityEvoker.this);
        entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(EntityEvoker.this.getPosition()), null);
        entityvex.setOwnerId(EntityEvoker.this.getOwnerId());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.playSound(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.playSound(ESound.blast, 10.0F, 1.0F);
        entityvex.spawnExplosionParticle();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.setCustomNameTag(EntityEvoker.this.getName());
        entityvex.renderYawOffset = entityvex.rotationYaw = entityvex.rotationYawHead + 180.0F;
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.world.spawnEntity(entityvex);
        EntityEvoker.this.world.removeEntity(EntityEvoker.this);
      } else if (EntityEvoker.this.getLevel() < 300 && EntityEvoker.this.getLevel() >= 200) {
        EntityWither entityvex = new EntityWither(EntityEvoker.this.world);
        entityvex.copyLocationAndAnglesFrom(EntityEvoker.this);
        entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(EntityEvoker.this.getPosition()), null);
        entityvex.setOwnerId(EntityEvoker.this.getOwnerId());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.playSound(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.playSound(ESound.blast, 10.0F, 1.0F);
        entityvex.spawnExplosionParticle();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.setCustomNameTag(EntityEvoker.this.getName());
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.world.spawnEntity(entityvex);
        EntityEvoker.this.world.removeEntity(EntityEvoker.this);
      } else if (EntityEvoker.this.getLevel() < 200 && EntityEvoker.this.getLevel() >= 100) {
        EntityGiant entityvex = new EntityGiant(EntityEvoker.this.world);
        entityvex.copyLocationAndAnglesFrom(EntityEvoker.this);
        entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(EntityEvoker.this.getPosition()), null);
        entityvex.setOwnerId(EntityEvoker.this.getOwnerId());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.playSound(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.playSound(ESound.blast, 10.0F, 1.0F);
        entityvex.spawnExplosionParticle();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.setCustomNameTag(EntityEvoker.this.getName());
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.world.spawnEntity(entityvex);
        EntityEvoker.this.world.removeEntity(EntityEvoker.this);
      } else {
        EntityIronGolem entityvex = new EntityIronGolem(EntityEvoker.this.world);
        entityvex.copyLocationAndAnglesFrom(EntityEvoker.this);
        entityvex.onInitialSpawn(EntityEvoker.this.world.getDifficultyForLocation(EntityEvoker.this.getPosition()), null);
        entityvex.setOwnerId(EntityEvoker.this.getOwnerId());
        entityvex.setIsHero(EntityEvoker.this.isHero());
        entityvex.setLastChance(EntityEvoker.this.hasLastChance());
        entityvex.setLevel(EntityEvoker.this.getLevel());
        entityvex.setGrowingAge(EntityEvoker.this.getGrowingAge());
        entityvex.playSound(ESound.bugSpecial, 10.0F, 0.5F);
        entityvex.playSound(ESound.blast, 10.0F, 1.0F);
        entityvex.spawnExplosionParticle();
        entityvex.setPolymorphTime(getCastingInterval());
        entityvex.setCustomNameTag(EntityEvoker.this.getName());
        NBTTagCompound tag = EntityEvoker.this.serializeNBT();
        entityvex.polymorpherData = tag;
        EntityEvoker.this.world.spawnEntity(entityvex);
        EntityEvoker.this.world.removeEntity(EntityEvoker.this);
      } 
    }
    
    @Nullable
    protected SoundEvent getSpellPrepareSound() {
      EntityEvoker.this.playSound(SoundEvents.EVOCATION_ILLAGER_PREPARE_SUMMON, 1.0F, 1.0F);
      return SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.POLYMORPH;
    }
  }
}


