package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityOmotholChargeOther extends EntityFireball {
  public EntityOmotholChargeOther(World worldIn) {
    super(worldIn);
    func_70105_a(1.0F, 1.0F);
  }
  
  public EntityOmotholChargeOther(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  public EntityOmotholChargeOther(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.SMOKE_LARGE;
  }
  
  protected boolean func_184564_k() {
    return false;
  }
  
  protected void func_70227_a(RayTraceResult result) {
    byte b0 = 5;
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
      b0 = 10;
    } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
      b0 = 20;
    } 
    if (!this.field_70170_p.field_72995_K) {
      if (this.field_70235_a != null) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity1 = list.get(i1);
            if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart) {
              this.field_70235_a.func_70652_k((Entity)entity1);
              func_174815_a(this.field_70235_a, (Entity)entity1);
              func_70106_y();
            } 
          }  
      } 
      if (result.field_72308_g != null) {
        if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && result.field_72308_g instanceof EntityLivingBase) {
          if (!result.field_72308_g.func_70045_F() && result.field_72308_g instanceof net.minecraft.entity.passive.EntityAnimal)
            result.field_72308_g.func_70015_d(20); 
          if (!((EntityTameBase)this.field_70235_a).func_184191_r(result.field_72308_g)) {
            ((EntityTameBase)this.field_70235_a).func_70652_k(result.field_72308_g);
            func_174815_a(this.field_70235_a, result.field_72308_g);
            ((EntityLivingBase)result.field_72308_g).func_70097_a(DamageSource.func_76362_a(this, (Entity)this.field_70235_a).func_76348_h(), 10.0F);
            if (!result.field_72308_g.func_70045_F())
              result.field_72308_g.func_70015_d(20); 
            if (b0 > 0 && result.field_72308_g instanceof EntityLivingBase) {
              ((EntityLivingBase)result.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 5 * b0, 0));
              ((EntityLivingBase)result.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 20 * b0, this.field_70170_p.func_175659_aa().func_151525_a()));
              ((EntityLivingBase)result.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 5 * b0, 0));
            } 
            func_70106_y();
          } 
        } 
        if (this.field_70235_a != null)
          EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 3.0F, false, false); 
        List<EntityLivingBase> list = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(8.0D));
        EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase)
          entityareaeffectcloud.setOwner((EntityTameBase)this.field_70235_a); 
        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.field_76421_d, 20 * b0, this.field_70170_p.func_175659_aa().func_151525_a()));
        entityareaeffectcloud.setRadius(2.0F);
        entityareaeffectcloud.setDuration(60);
        this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
      } 
    } 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S())
          func_70227_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
