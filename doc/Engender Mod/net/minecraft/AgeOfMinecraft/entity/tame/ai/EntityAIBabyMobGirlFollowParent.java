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
  
  public boolean func_75250_a() {
    if (this.childAnimal.func_70638_az() != null)
      return false; 
    if (this.childAnimal.isWild())
      return false; 
    if (this.childAnimal.field_70170_p.field_73011_w != (this.childAnimal.getOwner()).field_70170_p.field_73011_w)
      return false; 
    this.parentAnimal = (EntityPlayer)this.childAnimal.getOwner();
    double d0 = this.childAnimal.func_70068_e((Entity)this.parentAnimal);
    return ((d0 >= 64.0D || !this.childAnimal.func_70685_l((Entity)this.parentAnimal)) && (this.childAnimal.func_70631_g_() || this.childAnimal.isMarried()));
  }
  
  public boolean func_75253_b() {
    if (!this.parentAnimal.func_70089_S())
      return false; 
    double d0 = this.childAnimal.func_70068_e((Entity)this.parentAnimal);
    return (d0 > 9.0D && (this.childAnimal.func_70631_g_() || this.childAnimal.isMarried()));
  }
  
  public void func_75249_e() {
    this.delayCounter = 0;
  }
  
  public void func_75251_c() {
    this.parentAnimal = null;
  }
  
  public void func_75246_d() {
    if (--this.delayCounter <= 0) {
      this.delayCounter = 10;
      this.childAnimal.func_70671_ap().func_75651_a((Entity)this.parentAnimal, 10.0F, 40.0F);
      this.childAnimal.func_70661_as().func_75497_a((Entity)this.parentAnimal, this.moveSpeed);
    } 
  }
}
