package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityFrostRay extends EntityFireball {
  public Entity targetEntity;
  
  public EntityFrostRay(World worldIn) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_189654_d(true);
  }
  
  public EntityFrostRay(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn);
    this.field_70235_a = shooter;
    func_70012_b(accelX, accelY, accelZ, shooter.field_70177_z, shooter.field_70125_A);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.field_70232_b = accelX;
    this.field_70233_c = accelY;
    this.field_70230_d = accelZ;
  }
  
  public EntityFrostRay(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn);
    this.field_70235_a = shooter;
    func_70012_b(accelX, accelY, accelZ, shooter.field_70177_z, shooter.field_70125_A);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.targetEntity = target;
    this.field_70232_b = accelX;
    this.field_70233_c = accelY;
    this.field_70230_d = accelZ;
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
  }
  
  public EntityFrostRay(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    this(worldIn);
    func_70012_b(accelX, accelY, accelZ, 0.0F, 0.0F);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.field_70232_b = accelX;
    this.field_70233_c = accelY;
    this.field_70230_d = accelZ;
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.END_ROD;
  }
  
  protected void func_70227_a(RayTraceResult result) {}
  
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
    return 0.99F;
  }
  
  public void func_70071_h_() {
    if (!this.field_70170_p.field_72995_K)
      func_70052_a(6, func_184202_aL()); 
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_189654_d(true);
    func_70030_z();
    if (this.targetEntity != null && !this.targetEntity.func_70089_S())
      this.targetEntity = null; 
    if (this.field_70173_aa == 1) {
      this.field_70232_b = this.field_70165_t;
      this.field_70233_c = this.field_70163_u;
      this.field_70230_d = this.field_70161_v;
    } 
    if (this.field_70173_aa > 3 && this.targetEntity != null)
      func_70012_b(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u + this.targetEntity.func_70047_e() * 0.2D, this.targetEntity.field_70161_v, this.targetEntity.field_70177_z, this.targetEntity.field_70125_A); 
    if (this.field_70173_aa > 4 && this.field_70235_a != null && this.targetEntity != null) {
      this.targetEntity.field_70172_ad = 0;
      if (!this.field_70170_p.field_72995_K && this.field_70235_a instanceof EntityTameBase) {
        ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)this.targetEntity, " was vaporized by ", (new EntityDamageSource("indirectMagic", (Entity)this.field_70235_a)).func_82726_p().func_76348_h().func_151518_m(), this.targetEntity.func_184222_aU() ? 80.0F : 20.0F);
        ((EntityTameBase)this.field_70235_a).inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)this.targetEntity, MobEffects.field_82731_v, 100, 1);
      } 
      func_70106_y();
    } 
    if (this.field_70173_aa > 2) {
      short short1 = (short)(int)func_70092_e(this.field_70232_b, this.field_70233_c, this.field_70230_d);
      for (int i = 0; i < short1; i++) {
        double d9 = i / (short1 - 1.0D);
        double d6 = this.field_70165_t + (this.field_70165_t - this.field_70232_b) * -d9;
        double d7 = this.field_70163_u + (this.field_70163_u - this.field_70233_c) * -d9;
        double d8 = this.field_70161_v + (this.field_70161_v - this.field_70230_d) * -d9;
        if (this.field_70170_p.field_72995_K)
          this.field_70170_p.func_175682_a(EnumParticleTypes.SNOWBALL, true, d6, d7, d8, 0.0D, 0.025D, 0.0D, new int[0]); 
      } 
      func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    } else {
      this.field_70159_w *= 0.0D;
      this.field_70181_x *= 0.0D;
      this.field_70179_y *= 0.0D;
    } 
    this.field_70170_p.func_175688_a(EnumParticleTypes.SNOWBALL, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.025D, 0.0D, new int[0]);
    if (this.field_70173_aa > 20 || (!this.field_70170_p.field_72995_K && this.targetEntity == null))
      func_70106_y(); 
  }
}
