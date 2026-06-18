package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIAttackRangedAlly extends EntityAIBase {
  private final EntityTameBase entityHost;
  
  private final IRangedAttackMob rangedAttackEntityHost;
  
  private EntityLivingBase attackTarget;
  
  public EntityAIAttackRangedAlly(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
    this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
  }
  
  private int rangedAttackTime = -1;
  
  private final double entityMoveSpeed;
  
  private int seeTime;
  
  private final int attackIntervalMin;
  
  private final int maxRangedAttackTime;
  
  private final float attackRadius;
  
  private final float maxAttackDistance;
  
  public EntityAIAttackRangedAlly(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
    if (!(attacker instanceof EntityLivingBase))
      throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob"); 
    this.rangedAttackEntityHost = attacker;
    this.entityHost = (EntityTameBase)attacker;
    this.entityMoveSpeed = movespeed;
    this.attackIntervalMin = p_i1650_4_;
    this.maxRangedAttackTime = maxAttackTime;
    this.attackRadius = maxAttackDistanceIn;
    this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
    setMutexBits(3);
  }
  
  public boolean shouldExecute() {
    EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();
    if (entitylivingbase == null)
      return false; 
    this.attackTarget = entitylivingbase;
    return true;
  }
  
  public boolean shouldContinueExecuting() {
    return (shouldExecute() || !this.entityHost.getNavigator().noPath());
  }
  
  public void startExecuting() {
    super.startExecuting();
    this.entityHost.setArmsRaised(true);
    this.entityHost.setSitResting(false);
  }
  
  public void resetTask() {
    super.startExecuting();
    this.entityHost.setArmsRaised(false);
    this.entityHost.resetActiveHand();
    this.attackTarget = null;
    this.seeTime = 0;
    this.rangedAttackTime = -1;
  }
  
  public void updateTask() {
    double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, (this.attackTarget.getEntityBoundingBox()).minY, this.attackTarget.posZ);
    boolean flag = this.entityHost.getEntitySenses().canSee((Entity)this.attackTarget);
    if (flag) {
      this.seeTime++;
    } else {
      this.seeTime = 0;
    } 
    if ((d0 <= this.maxAttackDistance + this.attackTarget.width && this.seeTime >= 20) || !this.entityHost.onGround || this.entityMoveSpeed == 0.0D) {
      this.entityHost.getNavigator().clearPath();
    } else {
      this.entityHost.getNavigator().tryMoveToEntityLiving((Entity)this.attackTarget, this.entityMoveSpeed);
    } 
    this.entityHost.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0F, 30.0F);
    if (--this.rangedAttackTime == 0) {
      if (!flag)
        return; 
      float f = MathHelper.sqrt(d0) / this.attackRadius;
      float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
      this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
      if (this.entityHost instanceof EntitySans) {
        this.rangedAttackTime = ((EntitySans)this.entityHost).attackInterval;
      } else {
        this.rangedAttackTime = MathHelper.floor(f * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin - (float)(this.entityHost.getEntityAttribute(EntityTameBase.DEXTERITY).getBaseValue() * 0.2D));
      } 
    } else if (this.rangedAttackTime < 0) {
      float f2 = MathHelper.sqrt(d0) / this.attackRadius;
      if (this.entityHost instanceof EntitySans) {
        this.rangedAttackTime = ((EntitySans)this.entityHost).attackInterval;
      } else {
        this.rangedAttackTime = MathHelper.floor(f2 * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin - (float)(this.entityHost.getEntityAttribute(EntityTameBase.DEXTERITY).getBaseValue() * 0.2D));
      } 
    } 
  }
}
