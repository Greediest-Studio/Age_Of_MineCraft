package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityMagicMissile extends EntityFireball {
  public Entity targetEntity;
  
  public EntityMagicMissile(World worldIn) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
  }
  
  public EntityMagicMissile(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
  }
  
  public EntityMagicMissile(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn, shooter, accelX, accelY, accelZ);
    this.targetEntity = target;
    func_184185_a(ESound.magicMissileFire, 1.0F, 1.0F);
    this.field_70232_b = 0.0D;
    this.field_70233_c = 0.0D;
    this.field_70230_d = 0.0D;
  }
  
  public EntityMagicMissile(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.SUSPENDED_DEPTH;
  }
  
  protected void func_70227_a(RayTraceResult result) {
    if (this.field_70173_aa > 40 && !this.field_70170_p.field_72995_K && this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && result.field_72308_g != null && result.field_72308_g.field_70172_ad <= 0 && result.field_72308_g instanceof EntityLivingBase)
      if (!((EntityTameBase)this.field_70235_a).func_184191_r(result.field_72308_g)) {
        ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)this.targetEntity, " was shot by ", (new EntityDamageSourceIndirect("arrow", (Entity)this, (Entity)this.field_70235_a)).func_82726_p().func_76349_b(), 2.0F);
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
        func_184185_a(SoundEvents.field_190021_aL, 1.0F, 1.5F);
        func_70106_y();
        ((EntityLivingBase)this.targetEntity).field_70172_ad = 0;
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
    return (this.field_70173_aa > 90) ? 0.9F : (this.field_70173_aa / 100.0F);
  }
  
  public void func_70071_h_() {
    this.field_70165_t += this.field_70159_w;
    this.field_70163_u += this.field_70181_x;
    this.field_70161_v += this.field_70179_y;
    if (this.field_70235_a != null) {
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(0.5D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity1 = list.get(i1);
          if (entity1 != null && entity1.func_70089_S() && this.targetEntity != null && entity1 == this.targetEntity) {
            entity1.field_70172_ad = 0;
            func_70227_a(new RayTraceResult((Entity)entity1));
          } 
        }  
    } 
    if (this.targetEntity != null && !this.targetEntity.func_70089_S())
      this.targetEntity = null; 
    this.field_70170_p.func_175688_a(EnumParticleTypes.END_ROD, this.field_70165_t, this.field_70163_u + 0.125D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
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
    if (this.field_70173_aa > 20 && this.targetEntity != null) {
      double d0 = this.targetEntity.field_70165_t - this.field_70165_t;
      double d1 = this.targetEntity.field_70163_u - this.field_70163_u;
      double d2 = this.targetEntity.field_70161_v - this.field_70161_v;
      float f2 = MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
      this.field_70159_w = d0 / f2 * (func_82341_c() * func_82341_c()) * (func_82341_c() * func_82341_c()) + this.field_70159_w * (func_82341_c() * func_82341_c());
      this.field_70181_x = d1 / f2 * (func_82341_c() * func_82341_c()) * (func_82341_c() * func_82341_c()) + this.field_70181_x * (func_82341_c() * func_82341_c());
      this.field_70179_y = d2 / f2 * (func_82341_c() * func_82341_c()) * (func_82341_c() * func_82341_c()) + this.field_70179_y * (func_82341_c() * func_82341_c());
    } 
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    if (!this.field_70170_p.field_72995_K && this.field_70173_aa > 20 && this.targetEntity == null)
      if ((this.targetEntity == null || (this.targetEntity != null && !this.targetEntity.func_70089_S())) && this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase) {
        List<EntityLivingBase> entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(100.0D));
        if (entities != null && !entities.isEmpty()) {
          for (int i = 0; i < entities.size(); i++) {
            EntityLivingBase entity = entities.get(this.field_70146_Z.nextInt(entities.size()));
            if (entity.func_70089_S() && entity instanceof EntityLivingBase && !((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entity))
              this.targetEntity = (Entity)entity; 
          } 
        } else {
          func_184185_a(SoundEvents.field_190021_aL, 1.0F, 1.5F);
          func_70106_y();
        } 
      }  
  }
}
