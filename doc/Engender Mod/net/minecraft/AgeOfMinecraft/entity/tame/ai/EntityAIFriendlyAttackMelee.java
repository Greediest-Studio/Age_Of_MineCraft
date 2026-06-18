package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.World;

public class EntityAIFriendlyAttackMelee extends EntityAIBase {
  World world;
  
  protected EntityTameBase attacker;
  
  protected double attackTick;
  
  double speedTowardsTarget;
  
  boolean longMemory;
  
  Path entityPathEntity;
  
  private int delayCounter;
  
  private double targetX;
  
  private double targetY;
  
  private double targetZ;
  
  protected final int attackInterval = 20;
  
  private int failedPathFindingPenalty = 0;
  
  private boolean canPenalize = false;
  
  public EntityAIFriendlyAttackMelee(EntityTameBase creature, double speedIn, boolean useLongMemory) {
    this.attacker = creature;
    this.world = creature.field_70170_p;
    this.speedTowardsTarget = speedIn;
    this.longMemory = useLongMemory;
    func_75248_a(1);
  }
  
  public boolean func_75250_a() {
    EntityLivingBase entitylivingbase = this.attacker.func_70638_az();
    if (entitylivingbase == null)
      return false; 
    if (!entitylivingbase.func_70089_S())
      return false; 
    return !this.attacker.shouldFleeAtLowHealth();
  }
  
  public boolean func_75253_b() {
    EntityLivingBase entitylivingbase = this.attacker.func_70638_az();
    if (entitylivingbase == null)
      return false; 
    if (!entitylivingbase.func_70089_S())
      return false; 
    return !this.attacker.shouldFleeAtLowHealth();
  }
  
  public void func_75249_e() {
    this.attacker.func_70661_as().func_75484_a(this.entityPathEntity, this.speedTowardsTarget);
    this.attacker.setArmsRaised(true);
    this.attacker.setSitResting(false);
    this.delayCounter = 0;
  }
  
  public void func_75251_c() {
    EntityLivingBase entitylivingbase = this.attacker.func_70638_az();
    if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).func_175149_v() || ((EntityPlayer)entitylivingbase).func_184812_l_()))
      this.attacker.func_70624_b((EntityLivingBase)null); 
    this.attacker.func_70661_as().func_75499_g();
    this.attacker.setArmsRaised(false);
  }
  
  public void func_75246_d() {
    this.attacker.setSitResting(false);
    EntityLivingBase entitylivingbase = this.attacker.func_70638_az();
    if (entitylivingbase != null) {
      this.attacker.func_70671_ap().func_75651_a((Entity)entitylivingbase, this.attacker.func_184649_cE(), this.attacker.func_70646_bf());
      double d0 = this.attacker.func_70068_e((Entity)entitylivingbase);
      this.delayCounter--;
      if ((this.longMemory || this.attacker.func_70635_at().func_75522_a((Entity)entitylivingbase)) && this.delayCounter <= 0) {
        this.targetX = entitylivingbase.field_70165_t;
        this.targetY = (entitylivingbase.func_174813_aQ()).field_72338_b;
        this.targetZ = entitylivingbase.field_70161_v;
        this.delayCounter = 4 + this.attacker.func_70681_au().nextInt(7);
        if (this.canPenalize) {
          this.delayCounter += this.failedPathFindingPenalty;
          if (this.attacker.func_70661_as().func_75505_d() != null) {
            this.failedPathFindingPenalty = 0;
          } else {
            this.failedPathFindingPenalty += 10;
          } 
        } 
        if (d0 > 1024.0D) {
          this.delayCounter += 10;
        } else if (d0 > 128.0D) {
          this.delayCounter += 5;
        } 
      } 
      checkAndPerformAttack(entitylivingbase, d0);
      this.attackTick = Math.max(this.attackTick - 1.0D, 0.0D);
      if (this.attacker.getAttackState() == 1 && this.attacker.func_174818_b(this.attacker.getGuardBlock()) >= 576.0D) {
        func_75251_c();
        this.attacker.func_70661_as().func_75492_a(this.attacker.getGuardBlock().func_177958_n(), this.attacker.getGuardBlock().func_177956_o(), this.attacker.getGuardBlock().func_177952_p(), this.speedTowardsTarget);
      } 
      if (this.speedTowardsTarget != 0.0D && !(this.attacker instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker) && d0 > getAttackReachSqr(entitylivingbase)) {
        this.attacker.func_70661_as().func_75497_a((Entity)entitylivingbase, this.speedTowardsTarget);
      } else {
        this.attacker.field_70761_aq = this.attacker.field_70177_z = this.attacker.field_70759_as;
      } 
      if (this.attacker.func_70661_as().func_75500_f()) {
        this.attacker.setArmsRaised(false);
      } else {
        this.attacker.setArmsRaised(true);
      } 
    } 
  }
  
  protected void checkAndPerformAttack(EntityLivingBase attackTarget, double p_190102_2_) {
    double d0 = getAttackReachSqr(attackTarget);
    if (p_190102_2_ <= d0 && this.attackTick <= 0.0D && this.attacker.field_70163_u + this.attacker.field_70131_O > attackTarget.field_70163_u) {
      this.attackTick = 20.0D - this.attacker.func_110148_a(EntityTameBase.DEXTERITY).func_111125_b() * 0.1D;
      this.attacker.func_70652_k((Entity)attackTarget);
      this.attacker.attackWithAdditionalEffects((Entity)attackTarget);
    } 
  }
  
  protected double getAttackReachSqr(EntityLivingBase attackTarget) {
    return (this.attacker.reachWidth * this.attacker.reachWidth + ((attackTarget instanceof EntityTameBase) ? ((EntityTameBase)attackTarget).reachWidth : attackTarget.field_70130_N) * ((attackTarget instanceof EntityTameBase) ? ((EntityTameBase)attackTarget).reachWidth : attackTarget.field_70130_N)) + 4.0D;
  }
}
