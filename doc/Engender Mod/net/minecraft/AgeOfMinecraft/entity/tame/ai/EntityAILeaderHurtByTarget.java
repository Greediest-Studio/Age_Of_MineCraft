package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseList;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseRamAttack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.math.Vec3d;

public class EntityAILeaderHurtByTarget extends EntityAITarget {
  EntityTameBase theDefendingTameable;
  
  EntityLivingBase theOwnerAttacker;
  
  private int field_142051_e;
  
  public EntityAILeaderHurtByTarget(EntityTameBase p_i1667_1_) {
    super((EntityCreature)p_i1667_1_, false);
    this.theDefendingTameable = p_i1667_1_;
    func_75248_a(1);
  }
  
  public boolean func_75250_a() {
    if (this.theDefendingTameable.getOwner() == null)
      return false; 
    if (this.theDefendingTameable.func_70631_g_())
      return false; 
    EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
    if (entitylivingbase == null)
      return !this.theDefendingTameable.func_70631_g_(); 
    this.theOwnerAttacker = entitylivingbase.func_70643_av();
    return (func_75296_a(this.theOwnerAttacker, false) && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, entitylivingbase) && !this.theDefendingTameable.func_184191_r((Entity)this.theOwnerAttacker));
  }
  
  public void func_75249_e() {
    if (!this.theDefendingTameable.func_184191_r((Entity)this.theOwnerAttacker))
      this.theDefendingTameable.func_70624_b(this.theOwnerAttacker); 
    EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
    if (this.theDefendingTameable instanceof EntityPigZombie)
      ((EntityPigZombie)this.theDefendingTameable).becomeAngryAt((Entity)this.theOwnerAttacker); 
    if (this.theDefendingTameable instanceof EntityEnderDragon) {
      ((EntityEnderDragon)this.theDefendingTameable).getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
      ((PhaseRamAttack)((EntityEnderDragon)this.theDefendingTameable).getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).func_188668_a(new Vec3d(this.theOwnerAttacker.field_70165_t, this.theOwnerAttacker.field_70163_u, this.theOwnerAttacker.field_70161_v));
    } 
    super.func_75249_e();
  }
}
