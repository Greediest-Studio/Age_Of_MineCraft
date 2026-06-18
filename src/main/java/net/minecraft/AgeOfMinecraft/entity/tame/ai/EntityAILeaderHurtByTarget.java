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
  
  private int timestamp;
  
  public EntityAILeaderHurtByTarget(EntityTameBase p_i1667_1_) {
    super((EntityCreature)p_i1667_1_, false);
    this.theDefendingTameable = p_i1667_1_;
    setMutexBits(1);
  }
  
  public boolean shouldExecute() {
    if (this.theDefendingTameable.getOwner() == null)
      return false; 
    if (this.theDefendingTameable.isChild())
      return false; 
    EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
    if (entitylivingbase == null)
      return !this.theDefendingTameable.isChild(); 
    this.theOwnerAttacker = entitylivingbase.getRevengeTarget();
    return (isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, entitylivingbase));
  }
  
  public void startExecuting() {
    this.theDefendingTameable.setAttackTarget(this.theOwnerAttacker); 
    EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
    if (this.theDefendingTameable instanceof EntityPigZombie)
      ((EntityPigZombie)this.theDefendingTameable).becomeAngryAt((Entity)this.theOwnerAttacker); 
    if (this.theDefendingTameable instanceof EntityEnderDragon) {
      ((EntityEnderDragon)this.theDefendingTameable).getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
      ((PhaseRamAttack)((EntityEnderDragon)this.theDefendingTameable).getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).setTarget(new Vec3d(this.theOwnerAttacker.posX, this.theOwnerAttacker.posY, this.theOwnerAttacker.posZ));
    } 
    super.startExecuting();
  }
}
