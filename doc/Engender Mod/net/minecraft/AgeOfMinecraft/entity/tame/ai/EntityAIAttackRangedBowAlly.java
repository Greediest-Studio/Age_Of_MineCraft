package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;

public class EntityAIAttackRangedBowAlly<T extends EntityTameBase & IRangedAttackMob> extends EntityAIBase {
  private final T entity;
  
  private final double moveSpeedAmp;
  
  private final int field_188501_c;
  
  private final float maxAttackDistance;
  
  private int field_188503_e = -1;
  
  private int field_188504_f;
  
  private boolean field_188505_g;
  
  private boolean field_188506_h;
  
  private int field_188507_i = -1;
  
  public EntityAIAttackRangedBowAlly(T p_i46805_1_, double p_i46805_2_, int p_i46805_4_, float p_i46805_5_) {
    this.entity = p_i46805_1_;
    this.moveSpeedAmp = p_i46805_2_;
    this.field_188501_c = p_i46805_4_;
    this.maxAttackDistance = p_i46805_5_ * p_i46805_5_;
    func_75248_a(3);
  }
  
  public boolean func_75250_a() {
    return (this.entity.func_70638_az() == null) ? false : func_188498_f();
  }
  
  protected boolean func_188498_f() {
    return (this.entity.func_184614_ca() != null && this.entity.func_184614_ca().func_77973_b() instanceof ItemBow);
  }
  
  public boolean func_75253_b() {
    return ((func_75250_a() || !this.entity.func_70661_as().func_75500_f()) && func_188498_f());
  }
  
  public void func_75249_e() {
    super.func_75249_e();
    this.entity.setArmsRaised(true);
    this.entity.setSitResting(false);
  }
  
  public void func_75251_c() {
    super.func_75249_e();
    this.entity.setArmsRaised(false);
    this.field_188504_f = 0;
    this.field_188503_e = -1;
    this.entity.func_184602_cy();
    this.entity.func_70605_aq().func_188488_a(0.0F, 0.0F);
  }
  
  public void func_75246_d() {
    EntityLivingBase entitylivingbase = this.entity.func_70638_az();
    if (entitylivingbase != null) {
      this.entity.setSitResting(false);
      double d0 = this.entity.func_70092_e(entitylivingbase.field_70165_t, (entitylivingbase.func_174813_aQ()).field_72338_b, entitylivingbase.field_70161_v);
      boolean flag = this.entity.func_70635_at().func_75522_a((Entity)entitylivingbase);
      boolean flag1 = (this.field_188504_f > 0);
      this.field_188504_f++;
      if (((EntityTameBase)this.entity).moralRaisedTimer > 200)
        this.field_188504_f++; 
      if (!flag) {
        this.field_188504_f++;
        this.field_188504_f++;
        this.field_188504_f++;
        this.field_188504_f++;
      } 
      if ((d0 <= (this.maxAttackDistance + entitylivingbase.field_70130_N) && this.field_188504_f >= 20) || !((EntityTameBase)this.entity).field_70122_E || this.moveSpeedAmp == 0.0D) {
        this.entity.func_70661_as().func_75499_g();
        this.field_188507_i++;
      } else {
        this.entity.func_70661_as().func_75497_a((Entity)entitylivingbase, this.moveSpeedAmp);
        this.field_188507_i = -1;
      } 
      if (this.field_188507_i >= 20) {
        if (this.entity.func_70681_au().nextFloat() < 0.3D)
          this.field_188505_g = !this.field_188505_g; 
        if (this.entity.func_70681_au().nextFloat() < 0.3D)
          this.field_188506_h = !this.field_188506_h; 
        this.field_188507_i = 0;
      } 
      if (this.field_188507_i > -1) {
        if (d0 > (this.maxAttackDistance * 0.75F)) {
          this.field_188506_h = false;
        } else if (d0 < (this.maxAttackDistance * 0.25F)) {
          this.field_188506_h = true;
        } 
        if (this.entity.func_184187_bx() != null && this.entity.func_184187_bx() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)
          this.entity.func_70661_as().func_75484_a(((EntityLiving)this.entity.func_184187_bx()).func_70661_as().func_75494_a((Entity)this.entity.func_70638_az()), 1.2D); 
        if (((EntityTameBase)this.entity).field_70172_ad > 0 || this.entity.func_70032_d((Entity)entitylivingbase) <= this.maxAttackDistance / 2.0F)
          this.entity.func_70605_aq().func_188488_a(-1.0F, (entitylivingbase instanceof net.minecraft.AgeOfMinecraft.entity.tame.Flying || entitylivingbase instanceof IRangedAttackMob || entitylivingbase instanceof net.minecraft.entity.player.EntityPlayer) ? (this.field_188505_g ? 1.0F : -1.0F) : 0.0F); 
        this.entity.func_70625_a((Entity)entitylivingbase, 30.0F, 30.0F);
      } else {
        this.entity.func_70671_ap().func_75651_a((Entity)entitylivingbase, 30.0F, 30.0F);
      } 
      if (this.entity.func_184587_cr()) {
        if (!flag && this.field_188504_f < -60) {
          this.entity.func_184602_cy();
        } else if (flag) {
          int i = this.entity.func_184612_cw();
          if (i >= 20) {
            this.entity.func_70605_aq().func_188488_a(0.0F, 0.0F);
            this.entity.func_70661_as().func_75499_g();
            this.entity.func_184602_cy();
            if (d0 < (this.maxAttackDistance * 0.1F)) {
              this.entity.func_70652_k((Entity)entitylivingbase);
            } else {
              ((IRangedAttackMob)this.entity).func_82196_d(entitylivingbase, ItemBow.func_185059_b(i));
            } 
            this.field_188503_e = this.field_188501_c;
          } 
        } 
      } else if (--this.field_188503_e <= 0 && this.field_188504_f >= -60) {
        this.entity.func_184598_c(EnumHand.MAIN_HAND);
      } 
    } 
  }
}
