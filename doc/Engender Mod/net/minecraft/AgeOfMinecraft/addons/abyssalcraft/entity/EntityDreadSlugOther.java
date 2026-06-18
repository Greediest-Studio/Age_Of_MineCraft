package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDreadSlugOther extends EntityThrowable {
  public EntityDreadSlugOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityDreadSlugOther(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntityDreadSlugOther(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  protected void func_70184_a(RayTraceResult result) {
    if (!this.field_70170_p.field_72995_K && result.field_72308_g != null)
      if (result.field_72308_g instanceof EntityLivingBase && func_85052_h() != null && func_85052_h() instanceof EntityTameBase && !((EntityTameBase)func_85052_h()).func_184191_r(result.field_72308_g)) {
        ((EntityTameBase)func_85052_h()).inflictEngenderMobDamage((EntityLivingBase)result.field_72308_g, " was pummeled by ", DamageSource.func_76356_a((Entity)this, (Entity)func_85052_h()), 4.0F);
        if (!EntityUtil.isEntityDread((EntityLivingBase)result.field_72308_g))
          ((EntityLivingBase)result.field_72308_g).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 200)); 
        func_70106_y();
      }  
    if (!this.field_70170_p.field_72995_K && result.field_72308_g == null) {
      if (func_184207_aI())
        for (Entity entity : func_184188_bt()) {
          entity.func_184185_a(ESound.amalgamate, 2.0F, 1.5F);
          entity.func_82149_j((Entity)this);
          entity.field_70163_u += 3.0D;
        }  
      func_70106_y();
    } 
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
