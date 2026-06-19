package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFollowWildAdult extends EntityAIBase {
  EntityTameBase childAnimal;
  
  EntityTameBase parentAnimal;
  
  double moveSpeed;
  
  private int delayCounter;
  
  public EntityAIFollowWildAdult(EntityTameBase animal, double speed) {
    this.childAnimal = animal;
    this.moveSpeed = speed;
  }
  
  public boolean shouldExecute() {
    if (!this.childAnimal.isWild())
      return false; 
    if (this.childAnimal.getOwnerId() != null)
      return false; 
    if (this.childAnimal.getGrowingAge() >= 0)
      return false; 
    List<EntityTameBase> list = this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(), this.childAnimal.getEntityBoundingBox().grow(16.0D));
    EntityTameBase entityanimal = null;
    double d0 = Double.MAX_VALUE;
    for (EntityTameBase entityanimal1 : list) {
      if (entityanimal1.getGrowingAge() >= 0 && this.childAnimal.getClass() == entityanimal1.getClass() && entityanimal1.isWild() && entityanimal1.getOwnerId() == null) {
        double d1 = this.childAnimal.getDistanceSq(entityanimal1);
        if (d1 <= d0) {
          d0 = d1;
          entityanimal = entityanimal1;
        } 
      } 
    } 
    if (entityanimal == null)
      return false; 
    if (d0 < 16.0D)
      return false; 
    this.parentAnimal = entityanimal;
    return true;
  }
  
  public boolean shouldContinueExecuting() {
    if (!this.childAnimal.isWild())
      return false; 
    if (this.childAnimal.getOwnerId() != null)
      return false; 
    if (this.childAnimal.getGrowingAge() >= 0)
      return false; 
    if (!this.parentAnimal.isEntityAlive())
      return false; 
    double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
    return (d0 >= 16.0D && d0 <= 256.0D);
  }
  
  public void startExecuting() {
    this.delayCounter = 0;
  }
  
  public void resetTask() {
    this.parentAnimal = null;
  }
  
  public void updateTask() {
    if (--this.delayCounter <= 0) {
      this.delayCounter = 20;
      this.childAnimal.getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
    } 
  }
}
