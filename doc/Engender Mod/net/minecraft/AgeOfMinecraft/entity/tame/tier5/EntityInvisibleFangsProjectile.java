package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityInvisibleFangsProjectile extends EntitySmallFireball {
  public Entity targetEntity;
  
  public EntityInvisibleFangsProjectile(World worldIn) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_82142_c(true);
  }
  
  public EntityInvisibleFangsProjectile(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_82142_c(true);
  }
  
  public EntityInvisibleFangsProjectile(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn, shooter, accelX, accelY, accelZ);
    this.targetEntity = target;
    this.field_70232_b = 0.0D;
    this.field_70233_c = 0.0D;
    this.field_70230_d = 0.0D;
    func_82142_c(true);
  }
  
  public EntityInvisibleFangsProjectile(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_82142_c(true);
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.SUSPENDED_DEPTH;
  }
  
  protected void func_70227_a(RayTraceResult result) {
    if (this.field_70173_aa > 40 && !this.field_70170_p.field_72995_K && this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && result.field_72308_g != null && result.field_72308_g instanceof EntityLivingBase)
      if (!((EntityTameBase)this.field_70235_a).func_184191_r(result.field_72308_g)) {
        ((EntityTameBase)this.field_70235_a).func_70652_k(this.targetEntity);
        func_70106_y();
      }  
  }
  
  protected boolean func_184564_k() {
    return false;
  }
  
  public boolean func_70027_ad() {
    return false;
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  protected float func_82341_c() {
    return 0.85F;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    func_82142_c(true);
    if (!this.field_70170_p.field_72995_K && this.field_70235_a != null && this.targetEntity != null && this.field_70173_aa > 2) {
      EntityEvokerFangOther entityevokerfangs = new EntityEvokerFangOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, (float)MathHelper.func_181159_b(this.targetEntity.field_70161_v - this.field_70161_v, this.targetEntity.field_70165_t - this.field_70165_t), 5, (this.field_70235_a != null) ? this.field_70235_a : null);
      this.field_70170_p.func_72838_d((Entity)entityevokerfangs);
    } 
    if (this.field_70235_a != null) {
      List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ(), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list1 != null && !list1.isEmpty())
        for (int i1 = 0; i1 < list1.size(); i1++) {
          EntityLivingBase entity1 = list1.get(i1);
          if (func_70068_e((Entity)entity1) < 0.1D && entity1 != null && entity1.func_70089_S() && entity1 == this.targetEntity)
            func_70106_y(); 
        }  
    } 
    if (this.targetEntity != null && !this.targetEntity.func_70089_S())
      this.targetEntity = null; 
    if (this.field_70159_w > func_82341_c())
      this.field_70159_w = func_82341_c(); 
    if (this.field_70181_x > func_82341_c())
      this.field_70181_x = func_82341_c(); 
    if (this.field_70179_y > func_82341_c())
      this.field_70179_y = func_82341_c(); 
    if (this.field_70159_w < -func_82341_c())
      this.field_70159_w = -func_82341_c(); 
    if (this.field_70181_x < -func_82341_c())
      this.field_70181_x = -func_82341_c(); 
    if (this.field_70179_y < -func_82341_c())
      this.field_70179_y = -func_82341_c(); 
    if (this.targetEntity != null) {
      double d0 = this.targetEntity.field_70165_t - this.field_70165_t;
      double d1 = this.targetEntity.field_70163_u - this.field_70163_u;
      double d2 = this.targetEntity.field_70161_v - this.field_70161_v;
      float f2 = MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
      this.field_70159_w = d0 / f2 * (func_82341_c() * func_82341_c()) * (func_82341_c() * func_82341_c()) + this.field_70159_w * (func_82341_c() * func_82341_c());
      this.field_70181_x = d1 / f2 * (func_82341_c() * func_82341_c()) * (func_82341_c() * func_82341_c()) + this.field_70181_x * (func_82341_c() * func_82341_c());
      this.field_70179_y = d2 / f2 * (func_82341_c() * func_82341_c()) * (func_82341_c() * func_82341_c()) + this.field_70179_y * (func_82341_c() * func_82341_c());
    } 
    if (!this.field_70170_p.field_72995_K && this.targetEntity == null)
      func_70106_y(); 
  }
}
