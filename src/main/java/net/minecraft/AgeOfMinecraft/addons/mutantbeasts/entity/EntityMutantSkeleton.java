package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.client.animationapi.IAnimatedEntity;
import chumbanotz.mutantbeasts.entity.BodyPartEntity;
import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "chumbanotz.mutantbeasts.client.animationapi.IAnimatedEntity", modid = "mutantbeasts")
public class EntityMutantSkeleton extends EntityTameBase implements IRangedAttackMob, IJumpingMount, Undead, Massive, Armored, IAnimatedEntity {
  public static final byte MELEE_ATTACK = 4;
  
  public static final byte SHOOT_ATTACK = 5;
  
  public static final byte MULTI_SHOT_ATTACK = 6;
  
  public static final byte CONSTRICT_RIBS_ATTACK = 7;
  
  private int attackID;
  
  private int attackTick;
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 0.0D, 10, 96.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.1D, true);
  
  protected float jumpPower;
  
  public EntityMutantSkeleton(World worldIn) {
    super(worldIn);
    this.experienceValue = 600;
    this.ignoreFrustumCheck = true;
    setSize(0.9F, 3.6F);
    this.tasks.addTask(1, new MeleeGoal());
    this.tasks.addTask(1, new SnipeGoal());
    this.tasks.addTask(1, new ShootGoal());
    this.tasks.addTask(1, new MultiShotGoal());
    this.tasks.addTask(1, new ConstrictRibsAttackGoal());
    this.tasks.addTask(3, new EntityAIFollowLeader(this, 1.1D, 64.0F, 12.0F));
    this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.75D));
    this.tasks.addTask(6, new EntityAILookIdle(this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_skeleton";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(160.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(96.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
    getEntityAttribute(SWIM_SPEED).setBaseValue(5.0D);
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantSkeleton(this.world);
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !player.isSneaking() && !this.world.isRemote)
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return new MBGroundPathNavigator(this, worldIn);
  }
  
  public float getEyeHeight() {
    return this.height * 0.9027F;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 0 || (id >= 4 && id <= 8)) {
      this.attackID = id;
      this.attackTick = 0;
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    setPathPriority(PathNodeType.WATER, isInWater() ? 16.0F : -1.0F);
    if (this.attackID != 0)
      this.attackTick++; 
    removeActivePotionEffect(MobEffects.BLINDNESS);
    if (this.attackID == 0 && isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && !false && getDistanceSq(getAttackTarget()) < (this.width * this.width + (getAttackTarget()).width * (getAttackTarget()).width) + 9.0D)
      attackEntityAsMob(getAttackTarget());
    if (!this.world.isDaytime() && this.ticksExisted % 50 == 0 && getHealth() < getMaxHealth())
      heal(1.0F); 
    if (getAttackTarget() != null) {
      if (getDistance(getAttackTarget()) > 8.0D || getAttackTarget() instanceof net.minecraft.entity.EntityFlying || (getAttackTarget()).posY > this.posY + 6.0D) {
        this.tasks.addTask(2, this.aiArrowAttack);
        this.tasks.removeTask(this.aiAttackOnCollide);
      } else {
        this.tasks.addTask(2, this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
      } 
    } else {
      this.tasks.removeTask(this.aiAttackOnCollide);
      this.tasks.removeTask(this.aiArrowAttack);
    } 
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    if (!this.world.isRemote && this.attackID == 0)
      if (getHealth() <= getMaxHealth() * 0.375F && this.rand.nextInt(2) == 0) {
        setAttackID(6);
      } else if (this.rand.nextInt(4) != 0) {
        setAttackID(4);
      } else if (this.onGround || isInWater() || isInLava()) {
        setAttackID(7);
      }  
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return super.attackEntityFrom(source, amount);
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected boolean canBeRidden(Entity entityIn) {
    return false;
  }
  
  public boolean isPushedByWater() {
    return false;
  }
  
  public int getAnimationID() {
    return this.attackID;
  }
  
  public int getAnimationTick() {
    return this.attackTick;
  }
  
  protected void onDeathUpdate() {
    super.onDeathUpdate();
    if (this.deathTime > 2) {
      if (!this.world.isRemote) {
        for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(3.0D, 2.0D, 3.0D))) {
          if (entity instanceof EntityLivingBase)
            inflictEngenderMobDamage((EntityLivingBase)entity, "'s body was mutilated by a flying body part from ", DamageSource.causeMobDamage(this).setDamageBypassesArmor(), 7.0F);
        } 
        for (int i = 0; i < 18; i++) {
          int j = i;
          if (i >= 3)
            j = i + 1; 
          if (j >= 4)
            j++; 
          if (j >= 5)
            j++; 
          if (j >= 6)
            j++; 
          if (j >= 9)
            j++; 
          if (j >= 10)
            j++; 
          if (j >= 11)
            j++; 
          if (j >= 12)
            j++; 
          if (j >= 15)
            j++; 
          if (j >= 16)
            j++; 
          if (j >= 17)
            j++; 
          if (j >= 18)
            j++; 
          if (j >= 20)
            j++; 
          BodyPartEntity part = new BodyPartEntity(this.world, this, j);
          part.motionX += (this.rand.nextFloat() * 0.8F * 2.0F - 0.8F);
          part.motionY += (this.rand.nextFloat() * 0.25F + 0.1F);
          part.motionZ += (this.rand.nextFloat() * 0.8F * 2.0F - 0.8F);
          this.world.spawnEntity(part);
        } 
      } 
      onKillCommand();
    } 
  }
  
  protected void handleJumpWater() {
    this.motionY += 0.03999999910593033D;
  }
  
  protected void handleJumpLava() {
    handleJumpWater();
  }
  
  public boolean getCanSpawnHere() {
    return (super.getCanSpawnHere() && this.world.canSeeSky(getPosition()));
  }
  
  protected SoundEvent getAmbientSound() {
    return MBSoundEvents.ENTITY_MUTANT_SKELETON_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_SKELETON_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return MBSoundEvents.ENTITY_MUTANT_SKELETON_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(MBSoundEvents.ENTITY_MUTANT_SKELETON_STEP, 0.15F, 1.0F);
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 0.15F, getSoundPitch());
  }
  
  protected ResourceLocation getLootTable() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_skeleton");
  }
  
  private void setAttackID(int id) {
    this.attackID = id;
    this.attackTick = 0;
    this.world.setEntityState(this, (byte)id);
  }
  
  class MeleeGoal extends EntityAIBase {
    public MeleeGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      return (EntityMutantSkeleton.this.getAttackTarget() != null && EntityMutantSkeleton.this.attackID == 4);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantSkeleton.this.attackTick < 14);
    }
    
    public void startExecuting() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public void updateTask() {
      if (EntityMutantSkeleton.this.getAttackTarget() != null && EntityMutantSkeleton.this.getAttackTarget().isEntityAlive())
        EntityMutantSkeleton.this.getLookHelper().setLookPositionWithEntity(EntityMutantSkeleton.this.getAttackTarget(), 30.0F, 30.0F);
      if (EntityMutantSkeleton.this.attackTick == 3) {
        for (Entity entity : EntityMutantSkeleton.this.world.getEntitiesInAABBexcluding(EntityMutantSkeleton.this, EntityMutantSkeleton.this.getEntityBoundingBox().grow(4.0D), EntitySelectors.CAN_AI_TARGET)) {
          double dist = EntityMutantSkeleton.this.getDistance(entity);
          double x = EntityMutantSkeleton.this.posX - entity.posX;
          double z = EntityMutantSkeleton.this.posZ - entity.posZ;
          if (entity instanceof EntityLivingBase && dist <= (2.3F + EntityMutantSkeleton.this.rand.nextFloat() * 0.3F) && EntityUtil.getHeadAngle(EntityMutantSkeleton.this, x, z) <= 60.0F) {
            float power = 1.8F + EntityMutantSkeleton.this.rand.nextInt(5) * 0.15F;
            entity.dismountRidingEntity();
            EntityMutantSkeleton.this.inflictEngenderMobDamage((EntityLivingBase)entity, " was crushed by ", DamageSource.causeMobDamage(EntityMutantSkeleton.this), (float)EntityMutantSkeleton.this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + 2.0F);
            entity.motionX = -x / dist * power;
            entity.motionY = Math.max(0.2800000011920929D, entity.motionY);
            entity.motionZ = -z / dist * power;
            EntityUtil.knockBackBlockingPlayer(EntityMutantSkeleton.this);
          } 
        } 
        EntityMutantSkeleton.this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.0F, 1.0F / (EntityMutantSkeleton.this.rand.nextFloat() * 0.4F + 1.2F));
      } 
    }
    
    public void resetTask() {
      EntityMutantSkeleton.this.setAttackID(0);
    }
  }
  
  class ConstrictRibsAttackGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public ConstrictRibsAttackGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantSkeleton.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantSkeleton.this.attackID == 7);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantSkeleton.this.attackTick < 20);
    }
    
    public void startExecuting() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public void updateTask() {
      if (EntityMutantSkeleton.this.attackTick == 5)
        this.attackTarget.dismountRidingEntity(); 
      if (EntityMutantSkeleton.this.attackTick == 6) {
        this.attackTarget.dismountRidingEntity();
        EntityMutantSkeleton.this.inflictEngenderMobDamage(this.attackTarget, " was crushed by ", DamageSource.causeMobDamage(EntityMutantSkeleton.this).setDamageIsAbsolute(), (float)EntityMutantSkeleton.this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() + 6.0F);
        this.attackTarget.motionX = ((1.0F + EntityMutantSkeleton.this.getRNG().nextFloat() * 0.4F) * (EntityMutantSkeleton.this.getRNG().nextBoolean() ? 1.0D : -1.0D));
        this.attackTarget.motionY = (0.4F + EntityMutantSkeleton.this.getRNG().nextFloat() * 0.8F);
        this.attackTarget.motionZ = ((1.0F + EntityMutantSkeleton.this.getRNG().nextFloat() * 0.4F) * (EntityMutantSkeleton.this.getRNG().nextBoolean() ? 1.0D : -1.0D));
        EntityMutantSkeleton.this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5F, 0.8F + EntityMutantSkeleton.this.rand.nextFloat() * 0.4F);
        this.attackTarget.velocityChanged = true;
        EntityUtil.disableShield(this.attackTarget, 100);
      } 
    }
    
    public void resetTask() {
      EntityMutantSkeleton.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  class ShootGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public ShootGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantSkeleton.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantSkeleton.this.attackID == 5);
    }
    
    public void startExecuting() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantSkeleton.this.attackTick < 32);
    }
    
    public void updateTask() {
      EntityMutantSkeleton.this.navigator.clearPath();
      EntityMutantSkeleton.this.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
      EntityMutantSkeleton.this.renderYawOffset = EntityMutantSkeleton.this.rotationYaw = EntityMutantSkeleton.this.rotationYawHead;
      if (EntityMutantSkeleton.this.attackTick == 26) {
        EntityMutantSkeletonArrow arrowEntity = new EntityMutantSkeletonArrow(EntityMutantSkeleton.this.world, EntityMutantSkeleton.this, this.attackTarget);
        if (EntityMutantSkeleton.this.hurtTime > 0)
          arrowEntity.randomize(EntityMutantSkeleton.this.hurtTime / 2.0F); 
        if (EntityMutantSkeleton.this.rand.nextInt(6) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.WITHER, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.rand.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.UNLUCK, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.rand.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.rand.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.POISON, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.rand.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 120 + EntityMutantSkeleton.this.rand.nextInt(60), 1)); 
        if (EntityMutantSkeleton.this.rand.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.HUNGER, 120 + EntityMutantSkeleton.this.rand.nextInt(60), 1)); 
        if (EntityMutantSkeleton.this.rand.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120 + EntityMutantSkeleton.this.rand.nextInt(60), 1)); 
        EntityMutantSkeleton.this.world.spawnEntity(arrowEntity);
        EntityMutantSkeleton.this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (EntityMutantSkeleton.this.rand.nextFloat() * 0.4F + 1.2F) + 0.25F);
      } 
    }
    
    public void resetTask() {
      EntityMutantSkeleton.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  class SnipeGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public SnipeGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantSkeleton.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantSkeleton.this.attackID == 8);
    }
    
    public void startExecuting() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantSkeleton.this.attackTick < 128);
    }
    
    public void updateTask() {
      EntityMutantSkeleton.this.navigator.clearPath();
      EntityMutantSkeleton.this.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
      EntityMutantSkeleton.this.renderYawOffset = EntityMutantSkeleton.this.rotationYaw = EntityMutantSkeleton.this.rotationYawHead;
      if (EntityMutantSkeleton.this.attackTick == 26)
        EntityMutantSkeleton.this.playSound(SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, 1.0F, 0.75F); 
      if (EntityMutantSkeleton.this.attackTick == 104) {
        EntityMutantSkeletonArrow arrowEntity = new EntityMutantSkeletonArrow(EntityMutantSkeleton.this.world, EntityMutantSkeleton.this, this.attackTarget);
        arrowEntity.setDamage(50);
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.WITHER, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.UNLUCK, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.NAUSEA, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.POISON, 80 + EntityMutantSkeleton.this.rand.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 120 + EntityMutantSkeleton.this.rand.nextInt(60), 1));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.HUNGER, 120 + EntityMutantSkeleton.this.rand.nextInt(60), 1));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120 + EntityMutantSkeleton.this.rand.nextInt(60), 1));
        EntityMutantSkeleton.this.world.spawnEntity(arrowEntity);
        EntityMutantSkeleton.this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.0F / (EntityMutantSkeleton.this.rand.nextFloat() * 0.4F + 1.2F) + 0.25F);
        EntityMutantSkeleton.this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (EntityMutantSkeleton.this.rand.nextFloat() * 0.4F + 1.2F) + 0.25F);
      } 
    }
    
    public void resetTask() {
      EntityMutantSkeleton.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  class MultiShotGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    private final List<EntityMutantSkeletonArrow> shots;
    
    public MultiShotGoal() {
      this.shots = new ArrayList<>();
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantSkeleton.this.getAttackTarget();
      return (this.attackTarget != null && (EntityMutantSkeleton.this.onGround || EntityMutantSkeleton.this.isInWater() || EntityMutantSkeleton.this.isInLava()) && EntityMutantSkeleton.this.attackID == 6);
    }
    
    public void startExecuting() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantSkeleton.this.attackTick < 30);
    }
    
    public void updateTask() {
      EntityMutantSkeleton.this.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
      EntityMutantSkeleton.this.renderYawOffset = EntityMutantSkeleton.this.rotationYaw = EntityMutantSkeleton.this.rotationYawHead;
      if (EntityMutantSkeleton.this.attackTick == 10) {
        double x = this.attackTarget.posX - EntityMutantSkeleton.this.posX;
        double z = this.attackTarget.posZ - EntityMutantSkeleton.this.posZ;
        float scale = 0.06F + EntityMutantSkeleton.this.rand.nextFloat() * 0.03F;
        if (EntityMutantSkeleton.this.getDistanceSq(this.attackTarget) < 16.0D) {
          x *= -1.0D;
          z *= -1.0D;
          scale *= 5.0F;
        } 
        EntityMutantSkeleton.this.motionX = x * scale;
        EntityMutantSkeleton.this.motionY = 1.5D;
        EntityMutantSkeleton.this.motionZ = z * scale;
      } 
      if (EntityMutantSkeleton.this.attackTick >= 24 && EntityMutantSkeleton.this.attackTick < 28) {
        if (!this.shots.isEmpty()) {
          for (EntityMutantSkeletonArrow arrowEntity : this.shots)
            EntityMutantSkeleton.this.world.spawnEntity(arrowEntity); 
          this.shots.clear();
        } 
        for (int i = 0; i < 6 + EntityMutantSkeleton.this.rand.nextInt(3); i++) {
          EntityMutantSkeletonArrow shot = new EntityMutantSkeletonArrow(EntityMutantSkeleton.this.world, EntityMutantSkeleton.this, this.attackTarget);
          shot.setSpeed(1.2F - EntityMutantSkeleton.this.rand.nextFloat() * 0.1F);
          shot.setClones(2);
          shot.randomize(3.0F);
          shot.setDamage(5 + EntityMutantSkeleton.this.rand.nextInt(5));
          shot.setPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120 + EntityMutantSkeleton.this.rand.nextInt(60), 1));
          this.shots.add(shot);
        } 
        EntityMutantSkeleton.this.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 1.0F, 1.0F / (EntityMutantSkeleton.this.rand.nextFloat() * 0.4F + 1.2F) + 0.25F);
      } 
    }
    
    public void resetTask() {
      this.shots.clear();
      if (this.attackTarget.isEntityAlive() && EntityMutantSkeleton.this.getDistance(this.attackTarget.posX, EntityMutantSkeleton.this.posY, this.attackTarget.posZ) <= 12.0D) {
        EntityMutantSkeleton.this.setAttackID(7);
        double x = this.attackTarget.posX - EntityMutantSkeleton.this.posX;
        double z = this.attackTarget.posZ - EntityMutantSkeleton.this.posZ;
        float scale = 0.1F;
        EntityMutantSkeleton.this.motionX = x * scale;
        EntityMutantSkeleton.this.motionY = -1.5D;
        EntityMutantSkeleton.this.motionZ = z * scale;
        this.attackTarget.hurtResistantTime = 0;
        this.attackTarget = null;
      } else {
        EntityMutantSkeleton.this.setAttackID(0);
        this.attackTarget = null;
      } 
    }
  }
  
  public void setJumpPower(int jumpPowerIn) {
    if (isBeingRidden()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  public boolean canJump() {
    return true;
  }
  
  public void handleStartJump(int p_184775_1_) {}
  
  public void handleStopJump() {}
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing / 3.0F;
      forward = entitylivingbase.moveForward / 3.0F;
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = 2.0D * this.jumpPower * getFittness();
        if (isPotionActive(MobEffects.JUMP_BOOST))
          this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
        this.isAirBorne = true;
        if (forward > 0.0F) {
          float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
          float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
          this.motionX += (-3.0F * f * this.jumpPower);
          this.motionZ += (3.0F * f1 * this.jumpPower);
        } 
        this.jumpPower = 0.0F;
      } 
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double d5 = this.posX - this.prevPosX;
      double d7 = this.posZ - this.prevPosZ;
      float f10 = MathHelper.sqrt(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.limbSwingAmount += (f10 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    if (!this.world.isRemote && this.attackID == 0)
      if (getDistance(target.posX, this.posY, target.posZ) <= 12.0D) {
        setAttackID(6);
      } else if (this.onGround || isInWater() || isInLava()) {
        if (this.rand.nextInt(5) == 0) {
          setAttackID(8);
        } else {
          setAttackID(5);
        } 
      }  
  }
  
  public void setAnimationID(int paramInt) {}
  
  public void setAnimationTick(int paramInt) {}
}

