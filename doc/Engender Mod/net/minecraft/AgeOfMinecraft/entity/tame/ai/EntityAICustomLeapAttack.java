package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;

public class EntityAICustomLeapAttack extends EntityAIBase {
  EntityLiving leaper;
  
  EntityLivingBase leapTarget;
  
  float leapMotionX;
  
  float leapMotionY;
  
  float leapMotionZ;
  
  float leapMotionXGeneralPercent;
  
  float leapMotionZGeneralPercent;
  
  float leapMotionXPercent;
  
  float leapMotionZPercent;
  
  double minDistance;
  
  double maxDistance;
  
  int randomChance;
  
  public EntityAICustomLeapAttack(EntityLiving leapingEntity, float leapMotionYIn, float leapMotionHorizontal, float leapMotionHorizontalGeneralPercent, float leapMotionHorizontalPercent, double minDis, double maxDis, int chance) {
    this.leaper = leapingEntity;
    this.leapMotionY = leapMotionYIn;
    this.leapMotionX = leapMotionHorizontal;
    this.leapMotionZ = leapMotionHorizontal;
    this.leapMotionXGeneralPercent = leapMotionHorizontalGeneralPercent;
    this.leapMotionZGeneralPercent = leapMotionHorizontalGeneralPercent;
    this.leapMotionXPercent = leapMotionHorizontalPercent;
    this.leapMotionZPercent = leapMotionHorizontalPercent;
    this.minDistance = minDis;
    this.maxDistance = maxDis;
    this.randomChance = chance;
    func_75248_a(5);
  }
  
  public boolean func_75250_a() {
    this.leapTarget = this.leaper.func_70638_az();
    if (this.leapTarget == null)
      return false; 
    double d0 = this.leaper.func_70068_e((Entity)this.leapTarget);
    return (d0 >= this.minDistance && d0 <= this.maxDistance) ? (!this.leaper.field_70122_E ? false : ((this.leaper.func_70681_au().nextInt(2) == 0))) : false;
  }
  
  public boolean func_75253_b() {
    return !this.leaper.field_70122_E;
  }
  
  public void func_75249_e() {
    double d0 = this.leapTarget.field_70165_t - this.leaper.field_70165_t;
    double d1 = this.leapTarget.field_70161_v - this.leaper.field_70161_v;
    float f = MathHelper.func_76133_a(d0 * d0 + d1 * d1);
    this.leaper.field_70159_w += d0 / f * this.leapMotionX * this.leapMotionXGeneralPercent + this.leaper.field_70159_w * this.leapMotionXPercent;
    this.leaper.field_70179_y += d1 / f * this.leapMotionZ * this.leapMotionZGeneralPercent + this.leaper.field_70179_y * this.leapMotionZPercent;
    this.leaper.field_70181_x = this.leapMotionY;
    ForgeHooks.onLivingJump((EntityLivingBase)this.leaper);
    double dou = this.leaper.func_70068_e((Entity)this.leapTarget);
    if (dou <= this.minDistance + 16.0D)
      this.leaper.func_70652_k((Entity)this.leapTarget); 
  }
}
