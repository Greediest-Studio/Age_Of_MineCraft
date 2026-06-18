package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPoisonSpray extends EntitySnowball {
  public EntityPoisonSpray(World worldIn) {
    super(worldIn);
  }
  
  public EntityPoisonSpray(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntityPoisonSpray(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  protected void func_70184_a(RayTraceResult movingObject) {
    byte b0 = 10;
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
      b0 = 20;
    } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
      b0 = 60;
    } 
    if (movingObject.field_72308_g != null) {
      if (func_85052_h() != null && func_85052_h() instanceof EntityTameBase && movingObject.field_72308_g instanceof EntityLivingBase && !((EntityTameBase)func_85052_h()).func_184191_r(movingObject.field_72308_g)) {
        movingObject.field_72308_g.field_70172_ad = 0;
        if (func_85052_h().func_70652_k(movingObject.field_72308_g)) {
          if (b0 > 0 && movingObject.field_72308_g instanceof EntityLivingBase)
            ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76436_u, 20 * b0)); 
          func_70106_y();
          func_174815_a(func_85052_h(), movingObject.field_72308_g);
          EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
          if (func_85052_h() != null && func_85052_h() instanceof EntityTameBase)
            entityareaeffectcloud.setOwner((EntityTameBase)func_85052_h()); 
          entityareaeffectcloud.setParticle(EnumParticleTypes.SLIME);
          entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.field_76436_u, 20 * b0));
          entityareaeffectcloud.setRadius(2.0F);
          entityareaeffectcloud.setDuration(100);
          func_184185_a(SoundEvents.field_187900_fz, 1.0F, 1.0F);
          this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
        } 
      } 
    } else {
      func_70106_y();
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      entityareaeffectcloud.setOwner((EntityTameBase)func_85052_h());
      entityareaeffectcloud.setParticle(EnumParticleTypes.SLIME);
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.field_76436_u, 20 * b0));
      entityareaeffectcloud.setRadius(2.0F);
      entityareaeffectcloud.setDuration(100);
      func_184185_a(SoundEvents.field_187900_fz, 1.0F, 1.0F);
      this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
    } 
    List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (list1 != null && !list1.isEmpty())
      for (int i1 = 0; i1 < list1.size(); i1++) {
        EntityLivingBase entity1 = list1.get(i1);
        if (!((EntityTameBase)func_85052_h()).func_184191_r((Entity)entity1)) {
          func_85052_h().func_70652_k((Entity)entity1);
          if (b0 > 0 && entity1 instanceof EntityLivingBase)
            entity1.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 20 * b0)); 
        } 
      }  
  }
}
