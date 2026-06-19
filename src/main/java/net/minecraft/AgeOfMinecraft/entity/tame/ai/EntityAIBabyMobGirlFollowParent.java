package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIBabyMobGirlFollowParent extends EntityAIBase {
  EntityTameBase childAnimal;
  
  EntityPlayer parentAnimal;
  
  double moveSpeed;
  
  private int delayCounter;
  
  public EntityAIBabyMobGirlFollowParent(EntityTameBase animal, double speed) {
    this.childAnimal = animal;
    this.moveSpeed = speed;
  }
  
  public boolean shouldExecute() {
    if (this.childAnimal.getAttackTarget() != null)
      return false; 
    if (this.childAnimal.isWild())
      return false; 
    if (this.childAnimal.world.provider != (this.childAnimal.getOwner()).world.provider)
      return false; 
    this.parentAnimal = (EntityPlayer)this.childAnimal.getOwner();
    double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
    return ((d0 >= 64.0D || !this.childAnimal.canEntityBeSeen(this.parentAnimal)) && (this.childAnimal.isChild() || this.childAnimal.isMarried()));
  }
  
  public boolean shouldContinueExecuting() {
    if (!this.parentAnimal.isEntityAlive())
      return false; 
    double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
    return (d0 > 9.0D && (this.childAnimal.isChild() || this.childAnimal.isMarried()));
  }
  
  public void startExecuting() {
    this.delayCounter = 0;
  }
  
  public void resetTask() {
    this.parentAnimal = null;
  }
  
  public void updateTask() {
    if (--this.delayCounter <= 0) {
      this.delayCounter = 10;
      this.childAnimal.getLookHelper().setLookPositionWithEntity(this.parentAnimal, 10.0F, 40.0F);
      this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
    } 
  }
}
