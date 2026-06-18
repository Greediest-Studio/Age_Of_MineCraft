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
  
  public boolean func_75250_a() {
    if (!this.childAnimal.isWild())
      return false; 
    if (this.childAnimal.func_184753_b() != null)
      return false; 
    if (this.childAnimal.getGrowingAge() >= 0)
      return false; 
    List<EntityTameBase> list = this.childAnimal.field_70170_p.func_72872_a(this.childAnimal.getClass(), this.childAnimal.func_174813_aQ().func_186662_g(16.0D));
    EntityTameBase entityanimal = null;
    double d0 = Double.MAX_VALUE;
    for (EntityTameBase entityanimal1 : list) {
      if (entityanimal1.getGrowingAge() >= 0 && this.childAnimal.getClass() == entityanimal1.getClass() && entityanimal1.isWild() && entityanimal1.func_184753_b() == null) {
        double d1 = this.childAnimal.func_70068_e((Entity)entityanimal1);
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
  
  public boolean func_75253_b() {
    if (!this.childAnimal.isWild())
      return false; 
    if (this.childAnimal.func_184753_b() != null)
      return false; 
    if (this.childAnimal.getGrowingAge() >= 0)
      return false; 
    if (!this.parentAnimal.func_70089_S())
      return false; 
    double d0 = this.childAnimal.func_70068_e((Entity)this.parentAnimal);
    return (d0 >= 16.0D && d0 <= 256.0D);
  }
  
  public void func_75249_e() {
    this.delayCounter = 0;
  }
  
  public void func_75251_c() {
    this.parentAnimal = null;
  }
  
  public void func_75246_d() {
    if (--this.delayCounter <= 0) {
      this.delayCounter = 20;
      this.childAnimal.func_70661_as().func_75497_a((Entity)this.parentAnimal, this.moveSpeed);
    } 
  }
}
