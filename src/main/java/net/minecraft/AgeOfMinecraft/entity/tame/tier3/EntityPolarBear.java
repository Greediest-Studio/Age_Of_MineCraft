package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPolarBear extends EntityTameBase implements Light, Animal {
  private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.createKey(EntityPolarBear.class, DataSerializers.BOOLEAN);
  
  private float clientSideStandAnimation0;
  
  private float clientSideStandAnimation;
  
  private int warningSoundTicks;
  
  public EntityPolarBear(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    setSize(1.3F, 1.4F);
    this.experienceValue = 5;
  }
  
  public String getDescName() {
    return "polar_bear";
  }
  
  protected void initEntityAI() {
    super.initEntityAI();
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new AIMeleeAttack());
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 9.0F));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityPolarBear(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.clientSideStandAnimation > 0.0F)
      setSize(1.3F, 1.4F + this.clientSideStandAnimation * 0.275F); 
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  protected SoundEvent getAmbientSound() {
    return isChild() ? SoundEvents.ENTITY_POLAR_BEAR_BABY_AMBIENT : SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_POLAR_BEAR_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F / getFittness());
  }
  
  protected void playWarningSound() {
    if (this.warningSoundTicks <= 0) {
      playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1.0F, 1.0F);
      this.warningSoundTicks = 20;
    } 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_POLAR_BEAR;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(IS_STANDING, Boolean.valueOf(false));
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getJukeboxToDanceTo() != null)
      if (this.ticksExisted % 10 == 0 || this.ticksExisted % 10 == 1 || this.ticksExisted % 10 == 2 || this.ticksExisted % 10 == 3 || this.ticksExisted % 10 == 4) {
        setStanding(false);
      } else {
        setStanding(true);
      }  
    if (this.world.isRemote) {
      this.clientSideStandAnimation0 = this.clientSideStandAnimation;
      if (isStanding()) {
        this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
      } else {
        this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
      } 
    } 
    if (this.warningSoundTicks > 0)
      this.warningSoundTicks--; 
    if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false)
      if (getDistanceSq((Entity)getAttackTarget()) < (this.reachWidth * this.reachWidth + ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width) * ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width)) + 9.0D && (this.ticksExisted + getEntityId()) % 20 == 0) {
        attackEntityAsMob((Entity)getAttackTarget());
        swingArm(EnumHand.MAIN_HAND);
        if (!getHeldItemOffhand().isEmpty())
          swingArm(EnumHand.OFF_HAND); 
      }  
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    boolean flag = super.attackEntityAsMob(entityIn);
    if (flag) {
      applyEnchantments((EntityLivingBase)this, entityIn);
      playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1.0F, 1.25F);
    } 
    return flag;
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
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      this.stepHeight = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.rotationYawHead = entitylivingbase.rotationYawHead;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      strafe = entitylivingbase.moveStrafing * (isInWater() ? 1.0F : 0.5F);
      forward = entitylivingbase.moveForward * (isInWater() ? 1.0F : 0.5F);
      if (forward != 0.0F) {
        this.rotationYaw = this.renderYawOffset = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      } 
      if (isInWater() || isInLava()) {
        this.motionY += 0.025D;
        if (entitylivingbase.moveForward == 0.0F) {
          this.motionX *= 0.9D;
          this.motionZ *= 0.9D;
        } else {
          if (this.motionX > 3.0D)
            this.motionX = 3.0D; 
          if (this.motionZ > 3.0D)
            this.motionZ = 3.0D; 
          if (this.motionX < -3.0D)
            this.motionX = -3.0D; 
          if (this.motionZ < -3.0D)
            this.motionZ = -3.0D; 
          this.motionX *= isHero() ? 1.5D : 1.125D;
          this.motionZ *= isHero() ? 1.5D : 1.125D;
        } 
      } 
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * ((isInWater() || isInLava()) ? 20.0F : 1.0F));
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (entitylivingbase.moveForward > 0.0F && this.ticksExisted % 7 == 0)
        playStepSound(new BlockPos((Entity)this), this.world.getBlockState(new BlockPos((Entity)this)).getBlock()); 
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
  
  public boolean isStanding() {
    return (((Boolean)this.dataManager.get(IS_STANDING)).booleanValue() || !isEntityAlive());
  }
  
  public void setStanding(boolean standing) {
    this.dataManager.set(IS_STANDING, Boolean.valueOf(standing));
  }
  
  @SideOnly(Side.CLIENT)
  public float getStandingAnimationScale(float p_189795_1_) {
    return (this.clientSideStandAnimation0 + (this.clientSideStandAnimation - this.clientSideStandAnimation0) * p_189795_1_) / 6.0F;
  }
  
  protected float getWaterSlowDown() {
    return 0.99F;
  }
  
  class AIHurtByTarget extends EntityAIHurtByTarget {
    public AIHurtByTarget() {
      super((EntityCreature)EntityPolarBear.this, false, new Class[0]);
    }
    
    public void startExecuting() {
      super.startExecuting();
      if (EntityPolarBear.this.isChild()) {
        alertOthers();
        resetTask();
      } 
    }
    
    protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
      if (creatureIn instanceof EntityPolarBear && !((EntityPolarBear)creatureIn).isChild())
        super.setEntityAttackTarget(creatureIn, entityLivingBaseIn); 
    }
  }
  
  class AIMeleeAttack extends EntityAIFriendlyAttackMelee {
    public AIMeleeAttack() {
      super(EntityPolarBear.this, 1.25D, true);
    }
    
    protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
      double d0 = getAttackReachSqr(p_190102_1_);
      if (p_190102_2_ <= d0 && this.attackTick <= 0.0D) {
        this.attackTick = 20.0D;
        EntityPolarBear.this.attackEntityAsMob((Entity)p_190102_1_);
        EntityPolarBear.this.setStanding(false);
        List<EntityLivingBase> list1 = EntityPolarBear.this.world.getEntitiesWithinAABB(EntityLivingBase.class, p_190102_1_.getEntityBoundingBox().grow(2.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
        if (list1 != null && !list1.isEmpty())
          for (int i1 = 0; i1 < list1.size(); i1++) {
            EntityLivingBase entity1 = list1.get(i1);
            if (!false)
              EntityPolarBear.this.attackEntityAsMob((Entity)entity1); 
          }  
      } else if (p_190102_2_ <= d0 * 2.0D) {
        if (this.attackTick <= 0.0D) {
          EntityPolarBear.this.setStanding(false);
          this.attackTick = 20.0D;
        } 
        if (this.attackTick <= 10.0D) {
          EntityPolarBear.this.setStanding(true);
          EntityPolarBear.this.playWarningSound();
        } 
      } else {
        this.attackTick = 20.0D;
        EntityPolarBear.this.setStanding(false);
      } 
    }
    
    public void resetTask() {
      EntityPolarBear.this.setStanding(false);
      super.resetTask();
    }
    
    protected double getAttackReachSqr(EntityLivingBase attackTarget) {
      return (this.attacker.width * this.attacker.width + attackTarget.width * attackTarget.width) + 9.0D;
    }
  }
  
  class AIPanic extends EntityAIPanic {
    public AIPanic() {
      super((EntityCreature)EntityPolarBear.this, 2.0D);
    }
    
    public boolean shouldExecute() {
      return (!EntityPolarBear.this.isChild() && !EntityPolarBear.this.isBurning()) ? false : super.shouldExecute();
    }
  }
  
  static class GroupData implements IEntityLivingData {
    public boolean madeParent;
  }
}


