package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;

public class EntityAIAttackRangedBowAlly<T extends EntityTameBase & IRangedAttackMob> extends EntityAIBase {
  private final T entity;
  
  private final double moveSpeedAmp;
  
  private final int attackCooldown;
  
  private final float maxAttackDistance;
  
  private int attackTime = -1;
  
  private int seeTime;
  
  private boolean strafingClockwise;
  
  private boolean strafingBackwards;
  
  private int strafingTime = -1;
  
  public EntityAIAttackRangedBowAlly(T p_i46805_1_, double p_i46805_2_, int p_i46805_4_, float p_i46805_5_) {
    this.entity = p_i46805_1_;
    this.moveSpeedAmp = p_i46805_2_;
    this.attackCooldown = p_i46805_4_;
    this.maxAttackDistance = p_i46805_5_ * p_i46805_5_;
    setMutexBits(3);
  }
  
  public boolean shouldExecute() {
    return (this.entity.getAttackTarget() == null) ? false : isBowInMainhand();
  }
  
  protected boolean isBowInMainhand() {
    return (this.entity.getHeldItemMainhand() != null && this.entity.getHeldItemMainhand().getItem() instanceof ItemBow);
  }
  
  public boolean shouldContinueExecuting() {
    return ((shouldExecute() || !this.entity.getNavigator().noPath()) && isBowInMainhand());
  }
  
  public void startExecuting() {
    super.startExecuting();
    this.entity.setArmsRaised(true);
    this.entity.setSitResting(false);
  }
  
  public void resetTask() {
    super.startExecuting();
    this.entity.setArmsRaised(false);
    this.seeTime = 0;
    this.attackTime = -1;
    this.entity.resetActiveHand();
    this.entity.getMoveHelper().strafe(0.0F, 0.0F);
  }
  
  public void updateTask() {
    EntityLivingBase entitylivingbase = this.entity.getAttackTarget();
    if (entitylivingbase != null) {
      this.entity.setSitResting(false);
      double d0 = this.entity.getDistanceSq(entitylivingbase.posX, (entitylivingbase.getEntityBoundingBox()).minY, entitylivingbase.posZ);
      boolean flag = this.entity.getEntitySenses().canSee((Entity)entitylivingbase);
      boolean flag1 = (this.seeTime > 0);
      this.seeTime++;
      if (((EntityTameBase)this.entity).moralRaisedTimer > 200)
        this.seeTime++; 
      if (!flag) {
        this.seeTime++;
        this.seeTime++;
        this.seeTime++;
        this.seeTime++;
      } 
      if ((d0 <= (this.maxAttackDistance + entitylivingbase.width) && this.seeTime >= 20) || !((EntityTameBase)this.entity).onGround || this.moveSpeedAmp == 0.0D) {
        this.entity.getNavigator().clearPath();
        this.strafingTime++;
      } else {
        this.entity.getNavigator().tryMoveToEntityLiving((Entity)entitylivingbase, this.moveSpeedAmp);
        this.strafingTime = -1;
      } 
      if (this.strafingTime >= 20) {
        if (this.entity.getRNG().nextFloat() < 0.3D)
          this.strafingClockwise = !this.strafingClockwise; 
        if (this.entity.getRNG().nextFloat() < 0.3D)
          this.strafingBackwards = !this.strafingBackwards; 
        this.strafingTime = 0;
      } 
      if (this.strafingTime > -1) {
        if (d0 > (this.maxAttackDistance * 0.75F)) {
          this.strafingBackwards = false;
        } else if (d0 < (this.maxAttackDistance * 0.25F)) {
          this.strafingBackwards = true;
        } 
        if (this.entity.getRidingEntity() != null && this.entity.getRidingEntity() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)
          this.entity.getNavigator().setPath(((EntityLiving)this.entity.getRidingEntity()).getNavigator().getPathToEntityLiving((Entity)this.entity.getAttackTarget()), 1.2D); 
        if (((EntityTameBase)this.entity).hurtResistantTime > 0 || this.entity.getDistance((Entity)entitylivingbase) <= this.maxAttackDistance / 2.0F)
          this.entity.getMoveHelper().strafe(-1.0F, (entitylivingbase instanceof net.minecraft.AgeOfMinecraft.entity.tame.Flying || entitylivingbase instanceof IRangedAttackMob || entitylivingbase instanceof net.minecraft.entity.player.EntityPlayer) ? (this.strafingClockwise ? 1.0F : -1.0F) : 0.0F); 
        this.entity.faceEntity((Entity)entitylivingbase, 30.0F, 30.0F);
      } else {
        this.entity.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 30.0F, 30.0F);
      } 
      if (this.entity.isHandActive()) {
        if (!flag && this.seeTime < -60) {
          this.entity.resetActiveHand();
        } else if (flag) {
          int i = this.entity.getItemInUseMaxCount();
          if (i >= 20) {
            this.entity.getMoveHelper().strafe(0.0F, 0.0F);
            this.entity.getNavigator().clearPath();
            this.entity.resetActiveHand();
            if (d0 < (this.maxAttackDistance * 0.1F)) {
              this.entity.attackEntityAsMob((Entity)entitylivingbase);
            } else {
              ((IRangedAttackMob)this.entity).attackEntityWithRangedAttack(entitylivingbase, ItemBow.getArrowVelocity(i));
            } 
            this.attackTime = this.attackCooldown;
          } 
        } 
      } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
        this.entity.setActiveHand(EnumHand.MAIN_HAND);
      } 
    } 
  }
}
