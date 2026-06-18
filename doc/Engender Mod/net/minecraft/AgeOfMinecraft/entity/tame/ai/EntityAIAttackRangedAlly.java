package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIAttackRangedAlly extends EntityAIBase {
  private final EntityTameBase entityHost;
  
  private final IRangedAttackMob rangedAttackEntityHost;
  
  private EntityLivingBase attackTarget;
  
  public EntityAIAttackRangedAlly(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
    this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
  }
  
  private int rangedAttackTime = -1;
  
  private final double entityMoveSpeed;
  
  private int seeTime;
  
  private final int attackIntervalMin;
  
  private final int maxRangedAttackTime;
  
  private final float attackRadius;
  
  private final float maxAttackDistance;
  
  public EntityAIAttackRangedAlly(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
    if (!(attacker instanceof EntityLivingBase))
      throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob"); 
    this.rangedAttackEntityHost = attacker;
    this.entityHost = (EntityTameBase)attacker;
    this.entityMoveSpeed = movespeed;
    this.attackIntervalMin = p_i1650_4_;
    this.maxRangedAttackTime = maxAttackTime;
    this.attackRadius = maxAttackDistanceIn;
    this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
    func_75248_a(3);
  }
  
  public boolean func_75250_a() {
    EntityLivingBase entitylivingbase = this.entityHost.func_70638_az();
    if (entitylivingbase == null)
      return false; 
    this.attackTarget = entitylivingbase;
    return true;
  }
  
  public boolean func_75253_b() {
    return (func_75250_a() || !this.entityHost.func_70661_as().func_75500_f());
  }
  
  public void func_75249_e() {
    super.func_75249_e();
    this.entityHost.setArmsRaised(true);
    this.entityHost.setSitResting(false);
  }
  
  public void func_75251_c() {
    super.func_75249_e();
    this.entityHost.setArmsRaised(false);
    this.entityHost.func_184602_cy();
    this.attackTarget = null;
    this.seeTime = 0;
    this.rangedAttackTime = -1;
  }
  
  public void func_75246_d() {
    double d0 = this.entityHost.func_70092_e(this.attackTarget.field_70165_t, (this.attackTarget.func_174813_aQ()).field_72338_b, this.attackTarget.field_70161_v);
    boolean flag = this.entityHost.func_70635_at().func_75522_a((Entity)this.attackTarget);
    if (flag) {
      this.seeTime++;
    } else {
      this.seeTime = 0;
    } 
    if ((d0 <= this.maxAttackDistance + this.attackTarget.field_70130_N && this.seeTime >= 20) || !this.entityHost.field_70122_E || this.entityMoveSpeed == 0.0D) {
      this.entityHost.func_70661_as().func_75499_g();
    } else {
      this.entityHost.func_70661_as().func_75497_a((Entity)this.attackTarget, this.entityMoveSpeed);
    } 
    this.entityHost.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F);
    if (--this.rangedAttackTime == 0) {
      if (!flag)
        return; 
      float f = MathHelper.func_76133_a(d0) / this.attackRadius;
      float lvt_5_1_ = MathHelper.func_76131_a(f, 0.1F, 1.0F);
      this.rangedAttackEntityHost.func_82196_d(this.attackTarget, lvt_5_1_);
      if (this.entityHost instanceof EntitySans) {
        this.rangedAttackTime = ((EntitySans)this.entityHost).attackInterval;
      } else {
        this.rangedAttackTime = MathHelper.func_76141_d(f * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin - (float)(this.entityHost.func_110148_a(EntityTameBase.DEXTERITY).func_111125_b() * 0.2D));
      } 
    } else if (this.rangedAttackTime < 0) {
      float f2 = MathHelper.func_76133_a(d0) / this.attackRadius;
      if (this.entityHost instanceof EntitySans) {
        this.rangedAttackTime = ((EntitySans)this.entityHost).attackInterval;
      } else {
        this.rangedAttackTime = MathHelper.func_76141_d(f2 * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin - (float)(this.entityHost.func_110148_a(EntityTameBase.DEXTERITY).func_111125_b() * 0.2D));
      } 
    } 
  }
}
