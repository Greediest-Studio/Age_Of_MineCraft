package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAICreeperSwell extends EntityAIBase {
  EntityCreeper swellingCreeper;
  
  EntityLivingBase creeperAttackTarget;
  
  public EntityAICreeperSwell(EntityCreeper p_i1655_1_) {
    this.swellingCreeper = p_i1655_1_;
    func_75248_a(1);
  }
  
  public boolean func_75250_a() {
    EntityLivingBase entitylivingbase = this.swellingCreeper.func_70638_az();
    return ((this.swellingCreeper.getCreeperState() > 0 || (entitylivingbase != null && this.swellingCreeper.func_70068_e((Entity)entitylivingbase) < 9.0D)) && this.swellingCreeper.func_110143_aJ() <= this.swellingCreeper.func_110138_aP() / 2.0F);
  }
  
  public void func_75249_e() {
    this.swellingCreeper.func_70661_as().func_75499_g();
    this.creeperAttackTarget = this.swellingCreeper.func_70638_az();
  }
  
  public void func_75251_c() {
    this.creeperAttackTarget = null;
  }
  
  public void func_75246_d() {
    if (this.creeperAttackTarget == null || (this.creeperAttackTarget instanceof EntityTameBase && ((EntityTameBase)this.creeperAttackTarget).func_184753_b() == this.swellingCreeper.func_184753_b())) {
      this.swellingCreeper.setCreeperState(-1);
    } else if (this.swellingCreeper.func_70068_e((Entity)this.creeperAttackTarget) > 49.0D) {
      this.swellingCreeper.setCreeperState(-1);
    } else if (!this.swellingCreeper.func_70635_at().func_75522_a((Entity)this.creeperAttackTarget)) {
      this.swellingCreeper.setCreeperState(-1);
    } else {
      this.swellingCreeper.setCreeperState(1);
    } 
  }
}
