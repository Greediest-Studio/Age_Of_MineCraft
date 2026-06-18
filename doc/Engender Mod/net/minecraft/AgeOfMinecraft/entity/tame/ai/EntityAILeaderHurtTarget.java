package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAILeaderHurtTarget extends EntityAITarget {
  EntityTameBase theEntityTameable;
  
  EntityLivingBase theTarget;
  
  private int field_142050_e;
  
  public EntityAILeaderHurtTarget(EntityTameBase p_i1668_1_) {
    super((EntityCreature)p_i1668_1_, false);
    this.theEntityTameable = p_i1668_1_;
    func_75248_a(1);
  }
  
  public boolean func_75250_a() {
    if (this.theEntityTameable.getOwner() == null)
      return false; 
    if (this.theEntityTameable.func_70631_g_())
      return false; 
    EntityLivingBase entitylivingbase = this.theEntityTameable.getOwner();
    if (entitylivingbase == null)
      return !this.theEntityTameable.func_70631_g_(); 
    this.theTarget = entitylivingbase.func_110144_aD();
    return (func_75296_a(this.theTarget, false) && this.theEntityTameable.shouldAttackEntity(this.theTarget, entitylivingbase));
  }
  
  public void func_75249_e() {
    this.theEntityTameable.func_70624_b(this.theTarget);
    EntityLivingBase entitylivingbase = this.theEntityTameable.getOwner();
    if (entitylivingbase != null)
      this.field_142050_e = entitylivingbase.func_142013_aG(); 
    if (this.theEntityTameable instanceof EntityPigZombie)
      ((EntityPigZombie)this.theEntityTameable).becomeAngryAt((Entity)this.theTarget); 
    super.func_75249_e();
  }
}
