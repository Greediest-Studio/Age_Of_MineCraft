package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.common.entity.EntityInkProjectile;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityInkProjectileOther extends EntityInkProjectile {
  public EntityInkProjectileOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityInkProjectileOther(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntityInkProjectileOther(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  protected void func_70184_a(RayTraceResult result) {
    if (!this.field_70170_p.field_72995_K && result.field_72308_g != null)
      if ((result.field_72308_g instanceof EntityLivingBase && func_85052_h() instanceof EntityTameBase && !((EntityTameBase)func_85052_h()).func_184191_r(result.field_72308_g)) || (result.field_72308_g instanceof EntityTameBase && ((EntityTameBase)func_85052_h()).func_184191_r(result.field_72308_g) && ((EntityTameBase)func_85052_h()).getFakeHealth() > 0.0F)) {
        ((EntityTameBase)func_85052_h()).inflictEngenderMobDamage((EntityLivingBase)result.field_72308_g, " was pummeled by ", DamageSource.func_76356_a((Entity)this, (Entity)func_85052_h()), 1.0F);
        func_70106_y();
      }  
    if (!this.field_70170_p.field_72995_K && result.field_72308_g == null)
      func_70106_y(); 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S())
          func_70184_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
