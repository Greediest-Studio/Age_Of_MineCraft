package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhast extends EntityTameBase implements Massive, Flying, Light {
  private static final DataParameter<Boolean> SHOULD_FLY = EntityDataManager.createKey(EntityGhast.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EntityGhast.class, DataSerializers.BOOLEAN);
  
  public boolean eleanor;
  
  private int explosionStrength = 1;
  
  public EntityGhast(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.5F, 1.95F);
    } else {
      setSize(4.5F, 4.5F);
    } 
    this.isImmuneToFire = true;
    this.isOffensive = true;
    this.moveHelper = new GhastMoveHelper(this);
    this.tasks.addTask(0, new AIFireballAttack(this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, EngenderConfig.mobs.useMobTalkerModels ? 64.0F : 100.0F, EngenderConfig.mobs.useMobTalkerModels ? 9.0F : 16.0F));
    this.tasks.addTask(2, new AIRandomFly(this));
    this.tasks.addTask(3, new AILookAround());
    this.tasks.addTask(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.experienceValue = 20;
  }
  
  public String getDescName() {
    return "ghast";
  }
  
  public int getNextLevelRequirement() {
    return 250;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGhast(this.world);
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 0.5F;
  }
  
  public float getBonusVSArmored() {
    return 3.0F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 2.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isAttacking() {
    return ((Boolean)this.dataManager.get(ATTACKING)).booleanValue();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public void setAttacking(boolean attacking) {
    this.dataManager.set(ATTACKING, Boolean.valueOf(attacking));
  }
  
  public void setFlying(boolean attacking) {
    this.dataManager.set(SHOULD_FLY, Boolean.valueOf(attacking));
  }
  
  public boolean isFlying() {
    return ((Boolean)this.dataManager.get(SHOULD_FLY)).booleanValue();
  }
  
  public void performSpecialAttack() {
    playSound(getHurtSound((DamageSource)null), getSoundVolume(), getSoundPitch());
    setSpecialAttackTimer(1200);
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.25F) : super.getSoundPitch();
  }
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 1; i++) {
        EntityGhast baby = new EntityGhast(this.world);
        baby.copyLocationAndAnglesFrom((Entity)this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-60000);
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
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)getControllingPassenger();
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = passenger.rotationYaw;
      this.rotationPitch = EngenderConfig.mobs.useMobTalkerModels ? passenger.rotationPitch : 0.0F;
    } 
    if (isHero() && getSpecialAttackTimer() > 1100) {
      playSound(getHurtSound((DamageSource)null), getSoundVolume(), getSoundPitch());
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(64.0D, 64.0D, 64.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!false) {
              if (getSpecialAttackTimer() > 1190 && entity instanceof EntityCreature && !(entity instanceof EntityTameBase))
                ((EntityCreature)entity).tasks.addTask(0, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)entity, EntityGhast.class, 128.0F, 1.5D, 1.5D)); 
              entity.hurtResistantTime = 0;
              inflictEngenderMobDamage(entity, "'s ears exploded thanks to ", DamageSource.WITHER, 0.25F);
              entity.addVelocity(this.rand.nextGaussian() * 0.05D, this.rand.nextGaussian() * 0.05D, this.rand.nextGaussian() * 0.05D);
            }  
        }  
    } 
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) < 2048.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (!EngenderConfig.mobs.useMobTalkerModels && this.eleanor)
      this.eleanor = false; 
    if (!EngenderConfig.mobs.useMobTalkerModels) {
      setFlying(true);
    } else {
      setFlying((getAttackTarget() != null && !isChild()));
    } 
    if (!isFlying() && !isBeingRidden() && isEntityAlive())
      this.motionY *= 0.6D; 
    if (!isFlying() && (isInWater() || isInLava()) && getRNG().nextFloat() < 0.8F && isEntityAlive())
      getJumpHelper().setJumping(); 
    if (getAttackTarget() != null && getOwner() != null && !canEntityBeSeen((Entity)getAttackTarget()) && this.rand.nextInt(80) == 0)
      playSound(getHurtSound((DamageSource)null), getSoundVolume(), getSoundPitch() + 0.25F); 
    if (getOwner() != null)
      if (getAttackTarget() == null && this.ticksExisted % 10 == 0) {
        double d0 = getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        List<Entity> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(d0, d0, d0), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            Entity entity = list.get(i1);
            if (entity != null && entity.isEntityAlive() && canEntityBeSeen(entity) && !false && entity.getDistanceSq((Entity)getOwner()) <= 256.0D) {
              setAttackTarget((EntityLivingBase)entity);
            } else {
              list.remove(entity);
            } 
          }  
      }  
  }
  
  public int getFireballStrength() {
    return this.explosionStrength;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public void travel(float strafe, float vertical, float forward) {
    if ((isFlying() || isBeingRidden()) && isEntityAlive()) {
      if (isInWater()) {
        moveRelative(strafe, vertical, forward, 0.02F);
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.800000011920929D;
        this.motionY *= 0.800000011920929D;
        this.motionZ *= 0.800000011920929D;
      } else if (isInLava()) {
        moveRelative(strafe, vertical, forward, 0.02F);
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.5D;
        this.motionY *= 0.5D;
        this.motionZ *= 0.5D;
      } else {
        float f = 0.95F;
        float f1 = 0.16277136F / f * f * f;
        moveRelative(strafe, vertical, forward, 0.02F);
        f = 0.95F;
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= f;
        this.motionY *= f;
        this.motionZ *= f;
      } 
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public boolean isOnLadder() {
    return false;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (this.eleanor && source instanceof net.minecraft.util.EntityDamageSourceIndirect)
      return false; 
    Entity entity = source.getTrueSource();
    if (entity instanceof EntityLivingBase && EngenderConfig.mobs.useMobTalkerModels && this.eleanor && amount < 50.0F) {
      EntityLivingBase creature = (EntityLivingBase)entity;
      creature.attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute(), amount);
      creature.knockBack((Entity)this, amount * 0.1F, -MathHelper.sin(creature.rotationYawHead * 0.017453292F), MathHelper.cos(creature.rotationYawHead * 0.017453292F));
    } 
    if (this.eleanor && (source.isFireDamage() || source.isExplosion() || source.isProjectile() || source.isMagicDamage() || amount < 50.0F))
      return false; 
    if (EngenderConfig.mobs.useMobTalkerModels && amount >= 1.0F && this.eleanor)
      amount *= 1.0E-4F; 
    if (isEntityInvulnerable(source))
      return false; 
    if (source.getImmediateSource() instanceof net.minecraft.entity.projectile.EntityLargeFireball && source.getTrueSource() instanceof EntityPlayer) {
      super.attackEntityFrom(source, 1000.0F);
      return true;
    } 
    return super.attackEntityFrom(source, amount);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(ATTACKING, Boolean.valueOf(false));
    this.dataManager.register(SHOULD_FLY, Boolean.valueOf(false));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(17.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(40.0D);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_GHAST_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_GHAST_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_GHAST_DEATH;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_GHAST;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 10.0F;
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("ExplosionPower", this.explosionStrength);
    if (this.eleanor)
      tagCompound.setBoolean("Eleanor", true); 
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    if (tagCompund.hasKey("ExplosionPower", 99))
      this.explosionStrength = tagCompund.getInteger("ExplosionPower"); 
    if (tagCompund.hasKey("Eleanor", 99))
      this.eleanor = tagCompund.getBoolean("Eleanor"); 
  }
  
  public void setCustomNameTag(String name) {
    super.setCustomNameTag(name);
    if (!EngenderConfig.loreFriendlyMode && EngenderConfig.mobs.useMobTalkerModels && "Eleanor".equals(name) && isHero()) {
      this.ticksExisted = 0;
      becomeAHero();
      this.eleanor = true;
    } else {
      this.eleanor = false;
    } 
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.84F) : (this.height * 0.66F);
  }
  
  public double getMountedYOffset() {
    return this.height * (EngenderConfig.mobs.useMobTalkerModels ? 0.75D : 0.95D);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.fleshHitCrushHeavy;
  }
  
  static class AIFireballAttack extends EntityAIBase {
    private EntityGhast ghast;
    
    public int attackTimer;
    
    public AIFireballAttack(EntityGhast ghast) {
      this.ghast = ghast;
    }
    
    public boolean shouldExecute() {
      return (this.ghast.getAttackTarget() != null && !this.ghast.isSneaking());
    }
    
    public void startExecuting() {
      this.attackTimer = 0;
      this.ghast.setArmsRaised(true);
    }
    
    public void resetTask() {
      this.ghast.setAttacking(false);
      this.ghast.setArmsRaised(false);
    }
    
    public void updateTask() {
      EntityLivingBase entitylivingbase = this.ghast.getAttackTarget();
      double d0 = 100.0D;
      if (entitylivingbase != null && entitylivingbase.getDistanceSq((Entity)this.ghast) < d0 * d0) {
        World world = this.ghast.world;
        this.attackTimer++;
        if (this.ghast.moralRaisedTimer > 200)
          this.attackTimer++; 
        if (this.attackTimer == 10)
          this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.8F + this.ghast.getRNG().nextFloat() * 0.4F + (this.ghast.isChild() ? 0.5F : 0.0F) + (EngenderConfig.mobs.useMobTalkerModels ? 0.25F : 0.0F)); 
        if (this.attackTimer == 20) {
          double d1 = EngenderConfig.mobs.useMobTalkerModels ? (this.ghast.isChild() ? 0.25D : 0.5D) : (this.ghast.isChild() ? 2.0D : 4.0D);
          Vec3d vec3 = this.ghast.getLook(1.0F);
          double d2 = entitylivingbase.posX + entitylivingbase.motionX * 10.0D - this.ghast.posX + vec3.x * d1;
          double d3 = entitylivingbase.posY + ((entitylivingbase.height > 8.0F) ? 7.0D : (entitylivingbase.height * 0.5D)) - this.ghast.posY + 1.0D;
          double d4 = entitylivingbase.posZ + entitylivingbase.motionZ * 10.0D - this.ghast.posZ + vec3.z * d1;
          if (this.ghast.isChild()) {
            EntitySmallFireballOther entitylargefireball = new EntitySmallFireballOther(world, (EntityLivingBase)this.ghast, d2, d3, d4);
            entitylargefireball.posX = this.ghast.posX + vec3.x * d1;
            entitylargefireball.posY = this.ghast.posY + 1.0D;
            entitylargefireball.posZ = this.ghast.posZ + vec3.z * d1;
            float dm = (float)this.ghast.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            entitylargefireball.damage = dm;
            this.ghast.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 10.0F, 1.5F);
            world.spawnEntity((Entity)entitylargefireball);
            if (this.ghast.moralRaisedTimer > 200) {
              this.attackTimer = -15;
            } else {
              this.attackTimer = -30;
            } 
          } else {
            EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(world, (EntityLivingBase)this.ghast, d2, d3, d4);
            entitylargefireball.explosionPower = this.ghast.getFireballStrength() * (this.ghast.isHero() ? 2 : 1);
            entitylargefireball.posX = this.ghast.posX + vec3.x * d1;
            entitylargefireball.posY = this.ghast.posY + 1.0D;
            entitylargefireball.posZ = this.ghast.posZ + vec3.z * d1;
            float dm = (float)this.ghast.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            entitylargefireball.damage = dm;
            this.ghast.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 10.0F, 1.0F);
            world.spawnEntity((Entity)entitylargefireball);
            if (this.ghast.moralRaisedTimer > 200) {
              this.attackTimer = -20;
            } else {
              this.attackTimer = -40;
            } 
          } 
        } 
      } else if (this.attackTimer > 0) {
        this.attackTimer--;
      } 
      this.ghast.setAttacking((this.attackTimer > 10 || this.ghast.getSpecialAttackTimer() > 1100));
    }
  }
  
  class AILookAround extends EntityAIBase {
    private EntityGhast parentEntity = EntityGhast.this;
    
    public AILookAround() {
      setMutexBits(2);
    }
    
    public boolean shouldExecute() {
      return this.parentEntity.isFlying();
    }
    
    public void updateTask() {
      if (this.parentEntity.getControllingPassenger() != null) {
        this.parentEntity.prevRotationYaw = this.parentEntity.rotationYaw = (this.parentEntity.getControllingPassenger()).rotationYaw;
        this.parentEntity.rotationPitch = 0.0F;
        this.parentEntity.setRotation(this.parentEntity.rotationYaw, this.parentEntity.rotationPitch);
        this.parentEntity.rotationYawHead = this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
      } else if (this.parentEntity.getAttackTarget() == null) {
        this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = this.parentEntity.rotationYawHead = -((float)Math.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * 180.0F / 3.1415927F;
      } else {
        EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
        this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = this.parentEntity.rotationYawHead;
        this.parentEntity.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 180.0F, EngenderConfig.mobs.useMobTalkerModels ? 40.0F : 180.0F);
      } 
    }
  }
  
  static class AIRandomFly extends EntityAIBase {
    private EntityGhast ghast;
    
    public AIRandomFly(EntityGhast ghast) {
      this.ghast = ghast;
      setMutexBits(1);
    }
    
    public boolean shouldExecute() {
      EntityMoveHelper entitymovehelper = this.ghast.getMoveHelper();
      if (!entitymovehelper.isUpdating())
        return true; 
      double d0 = entitymovehelper.getX() - this.ghast.posX;
      double d1 = entitymovehelper.getY() - this.ghast.posY;
      double d2 = entitymovehelper.getZ() - this.ghast.posZ;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return ((d3 < 1.0D || d3 > 3600.0D) && !this.ghast.isBeingRidden());
    }
    
    public boolean shouldContinueExecuting() {
      return (!this.ghast.isFlying() && !this.ghast.isBeingRidden());
    }
    
    public void startExecuting() {
      if (this.ghast.isBeingRidden())
        resetTask(); 
      Random random = this.ghast.getRNG();
      if (this.ghast.getOwner() != null) {
        if (this.ghast.getOwner().isSneaking() || !this.ghast.getCurrentBook().isEmpty()) {
          double d0 = (this.ghast.getOwner()).posX + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d1 = (this.ghast.getOwner()).posY + 8.0D + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d2 = (this.ghast.getOwner()).posZ + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } else if (!this.ghast.getCurrentBook().isEmpty()) {
          double d0 = (this.ghast.getOwner()).posX;
          double d1 = (this.ghast.getOwner()).posY + 4.0D;
          double d2 = (this.ghast.getOwner()).posZ;
          this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } else {
          double d0 = (this.ghast.getOwner()).posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = (this.ghast.getOwner()).posY + ((EngenderConfig.mobs.useMobTalkerModels || this.ghast.isChild()) ? 16.0D : 32.0D) + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = (this.ghast.getOwner()).posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } 
      } else {
        double d0 = this.ghast.posX + random.nextGaussian() * 32.0D;
        double d1 = this.ghast.posY + random.nextGaussian() * 32.0D;
        double d2 = this.ghast.posZ + random.nextGaussian() * 32.0D;
        this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
      } 
    }
  }
  
  static class GhastMoveHelper extends EntityMoveHelper {
    private EntityGhast parentEntity;
    
    private int courseChangeCooldown;
    
    public GhastMoveHelper(EntityGhast ghast) {
      super((EntityLiving)ghast);
      this.parentEntity = ghast;
    }
    
    public void onUpdateMoveHelper() {
      if (this.parentEntity.getControllingPassenger() != null && this.parentEntity.getControllingPassenger() instanceof EntityPlayer && this.parentEntity.isBeingRidden()) {
        EntityPlayer passenger = (EntityPlayer)this.parentEntity.getControllingPassenger();
        this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = this.parentEntity.rotationYawHead = passenger.rotationYaw;
        this.parentEntity.rotationPitch = EngenderConfig.mobs.useMobTalkerModels ? passenger.rotationPitch : 0.0F;
        Vec3d vec3 = passenger.getLook(1.0F);
        double d0 = this.parentEntity.posX - this.parentEntity.posX + vec3.x * 50.0D;
        double d1 = this.parentEntity.posY - this.parentEntity.posY + vec3.y * 50.0D;
        double d2 = this.parentEntity.posZ - this.parentEntity.posZ + vec3.z * 50.0D;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        d3 = MathHelper.sqrt(d3);
        if (passenger.moveForward > 0.0F) {
          this.parentEntity.motionX -= d0 / d3 * ((this.parentEntity.moralRaisedTimer > 0) ? 0.2D : 0.1D);
          this.parentEntity.motionY -= d1 / d3 * ((this.parentEntity.moralRaisedTimer > 0) ? 0.2D : 0.1D);
          this.parentEntity.motionZ -= d2 / d3 * ((this.parentEntity.moralRaisedTimer > 0) ? 0.2D : 0.1D);
          if (this.parentEntity.motionX > 0.5D)
            this.parentEntity.motionX = 0.5D; 
          if (this.parentEntity.motionY > 0.5D)
            this.parentEntity.motionY = 0.5D; 
          if (this.parentEntity.motionZ > 0.5D)
            this.parentEntity.motionZ = 0.5D; 
          if (this.parentEntity.motionX < -0.5D)
            this.parentEntity.motionX = -0.5D; 
          if (this.parentEntity.motionY < -0.5D)
            this.parentEntity.motionY = -0.5D; 
          if (this.parentEntity.motionZ < -0.5D)
            this.parentEntity.motionZ = -0.5D; 
        } 
      } 
      if (this.parentEntity.isFlying() && !this.parentEntity.isBeingRidden()) {
        if (this.action == EntityMoveHelper.Action.MOVE_TO && this.parentEntity.getJukeboxToDanceTo() == null) {
          double d0 = this.posX - this.parentEntity.posX;
          double d1 = this.posY - this.parentEntity.posY;
          double d2 = this.posZ - this.parentEntity.posZ;
          double d3 = d0 * d0 + d1 * d1 + d2 * d2;
          if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
            d3 = MathHelper.sqrt(d3);
            if (isNotColliding(this.posX, this.posY, this.posZ, d3)) {
              this.parentEntity.motionX += d0 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.2D : 0.1D);
              this.parentEntity.motionY += d1 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.2D : 0.1D);
              this.parentEntity.motionZ += d2 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.2D : 0.1D);
            } else {
              this.action = EntityMoveHelper.Action.WAIT;
            } 
          } 
        } 
      } else {
        super.onUpdateMoveHelper();
      } 
    }
    
    private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
      double d0 = (x - this.parentEntity.posX) / p_179926_7_;
      double d1 = (y - this.parentEntity.posY) / p_179926_7_;
      double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
      AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();
      for (int i = 1; i < p_179926_7_; i++) {
        axisalignedbb = axisalignedbb.offset(d0, d1, d2);
        if (!this.parentEntity.world.getCollisionBoxes((Entity)this.parentEntity, axisalignedbb).isEmpty())
          return false; 
      } 
      return true;
    }
  }
}

