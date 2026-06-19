package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.World;

public class EntityAIFriendlyAttackMelee extends EntityAIBase {
  World world;
  
  protected EntityTameBase attacker;
  
  protected double attackTick;
  
  double speedTowardsTarget;
  
  boolean longMemory;
  
  Path entityPathEntity;
  
  private int delayCounter;
  
  private double targetX;
  
  private double targetY;
  
  private double targetZ;
  
  protected final int attackInterval = 20;
  
  private int failedPathFindingPenalty = 0;
  
  private boolean canPenalize = false;
  
  public EntityAIFriendlyAttackMelee(EntityTameBase creature, double speedIn, boolean useLongMemory) {
    this.attacker = creature;
    this.world = creature.world;
    this.speedTowardsTarget = speedIn;
    this.longMemory = useLongMemory;
    setMutexBits(1);
  }
  
  public boolean shouldExecute() {
    EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
    if (entitylivingbase == null)
      return false; 
    if (!entitylivingbase.isEntityAlive())
      return false; 
    return !this.attacker.shouldFleeAtLowHealth();
  }
  
  public boolean shouldContinueExecuting() {
    EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
    if (entitylivingbase == null)
      return false; 
    if (!entitylivingbase.isEntityAlive())
      return false; 
    return !this.attacker.shouldFleeAtLowHealth();
  }
  
  public void startExecuting() {
    this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
    this.attacker.setArmsRaised(true);
    this.attacker.setSitResting(false);
    this.delayCounter = 0;
  }
  
  public void resetTask() {
    EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
    if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()))
      this.attacker.setAttackTarget(null);
    this.attacker.getNavigator().clearPath();
    this.attacker.setArmsRaised(false);
  }
  
  public void updateTask() {
    this.attacker.setSitResting(false);
    EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
    if (entitylivingbase != null) {
      this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, this.attacker.getHorizontalFaceSpeed(), this.attacker.getVerticalFaceSpeed());
      double d0 = this.attacker.getDistanceSq(entitylivingbase);
      this.delayCounter--;
      if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0) {
        this.targetX = entitylivingbase.posX;
        this.targetY = (entitylivingbase.getEntityBoundingBox()).minY;
        this.targetZ = entitylivingbase.posZ;
        this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
        if (this.canPenalize) {
          this.delayCounter += this.failedPathFindingPenalty;
          if (this.attacker.getNavigator().getPath() != null) {
            this.failedPathFindingPenalty = 0;
          } else {
            this.failedPathFindingPenalty += 10;
          } 
        } 
        if (d0 > 1024.0D) {
          this.delayCounter += 10;
        } else if (d0 > 128.0D) {
          this.delayCounter += 5;
        } 
      } 
      checkAndPerformAttack(entitylivingbase, d0);
      this.attackTick = Math.max(this.attackTick - 1.0D, 0.0D);
      if (this.attacker.getAttackState() == 1 && this.attacker.getDistanceSq(this.attacker.getGuardBlock()) >= 576.0D) {
        resetTask();
        this.attacker.getNavigator().tryMoveToXYZ(this.attacker.getGuardBlock().getX(), this.attacker.getGuardBlock().getY(), this.attacker.getGuardBlock().getZ(), this.speedTowardsTarget);
      } 
      if (this.speedTowardsTarget != 0.0D && !(this.attacker instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker) && d0 > getAttackReachSqr(entitylivingbase)) {
        this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget);
      } else {
        this.attacker.renderYawOffset = this.attacker.rotationYaw = this.attacker.rotationYawHead;
      } 
      if (this.attacker.getNavigator().noPath()) {
        this.attacker.setArmsRaised(false);
      } else {
        this.attacker.setArmsRaised(true);
      } 
    } 
  }
  
  protected void checkAndPerformAttack(EntityLivingBase attackTarget, double p_190102_2_) {
    double d0 = getAttackReachSqr(attackTarget);
    if (p_190102_2_ <= d0 && this.attackTick <= 0.0D && this.attacker.posY + this.attacker.height > attackTarget.posY) {
      this.attackTick = 20.0D - this.attacker.getEntityAttribute(EntityTameBase.DEXTERITY).getBaseValue() * 0.1D;
      this.attacker.attackEntityAsMob(attackTarget);
      this.attacker.attackWithAdditionalEffects(attackTarget);
    } 
  }
  
  protected double getAttackReachSqr(EntityLivingBase attackTarget) {
    return (this.attacker.reachWidth * this.attacker.reachWidth + ((attackTarget instanceof EntityTameBase) ? ((EntityTameBase)attackTarget).reachWidth : attackTarget.width) * ((attackTarget instanceof EntityTameBase) ? ((EntityTameBase)attackTarget).reachWidth : attackTarget.width)) + 4.0D;
  }
}
