package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import java.util.Iterator;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAIFriendlyHurtByTarget extends EntityAITarget {
  private boolean entityCallsForHelp;
  
  private int revengeTimerOld;
  
  private final Class[] targetClasses;
  
  public EntityAIFriendlyHurtByTarget(EntityCreature p_i45885_1_, boolean p_i45885_2_, Class... p_i45885_3_) {
    super(p_i45885_1_, false);
    this.entityCallsForHelp = p_i45885_2_;
    this.targetClasses = p_i45885_3_;
    func_75248_a(1);
  }
  
  public boolean func_75250_a() {
    return (this.field_75299_d.func_70643_av() == null && func_75296_a(this.field_75299_d.func_70643_av(), false));
  }
  
  public void func_75249_e() {
    this.field_75299_d.func_70624_b(this.field_75299_d.func_70643_av());
    if (this.entityCallsForHelp) {
      List list = this.field_75299_d.field_70170_p.func_72872_a(this.field_75299_d.getClass(), (new AxisAlignedBB(this.field_75299_d.field_70165_t, this.field_75299_d.field_70163_u, this.field_75299_d.field_70161_v, this.field_75299_d.field_70165_t + 1.0D, this.field_75299_d.field_70163_u + 1.0D, this.field_75299_d.field_70161_v + 1.0D)).func_72314_b(32.0D, 32.0D, 32.0D));
      Iterator<EntityTameBase> iterator = list.iterator();
      while (iterator.hasNext()) {
        EntityTameBase entitycreature = iterator.next();
        if (this.field_75299_d != entitycreature && !entitycreature.func_184191_r((Entity)this.field_75299_d.func_70643_av()) && entitycreature.func_184191_r((Entity)this.field_75299_d))
          setEntityAttackTarget((EntityCreature)entitycreature, this.field_75299_d.func_70643_av()); 
      } 
    } 
    super.func_75249_e();
  }
  
  protected void setEntityAttackTarget(EntityCreature p_179446_1_, EntityLivingBase p_179446_2_) {
    p_179446_1_.func_70624_b(p_179446_2_);
  }
}
