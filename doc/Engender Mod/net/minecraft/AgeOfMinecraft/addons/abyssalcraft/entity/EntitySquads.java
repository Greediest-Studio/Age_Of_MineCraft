package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySquads extends EntityThrowable {
  public EntitySquads(World worldIn) {
    super(worldIn);
    func_70105_a(0.75F, 0.75F);
  }
  
  public EntitySquads(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
    func_70105_a(0.75F, 0.75F);
  }
  
  public EntitySquads(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
    func_70105_a(0.75F, 0.75F);
  }
  
  protected void func_70184_a(RayTraceResult result) {
    if (func_85052_h() != null) {
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity1 = list.get(i1);
          if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart) {
            func_85052_h().func_70652_k((Entity)entity1);
            if (this.field_70146_Z.nextBoolean() && !EntityUtil.isEntityCoralium(entity1))
              entity1.func_70690_d(new PotionEffect(AbyssalCraftAPI.coralium_plague, 200)); 
            if (this.field_70146_Z.nextInt(2) == 0)
              entity1.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 100)); 
            if (this.field_70146_Z.nextInt(2) == 0)
              entity1.func_70690_d(new PotionEffect(MobEffects.field_76419_f, 100)); 
            func_174815_a(func_85052_h(), (Entity)entity1);
            func_70106_y();
          } 
        }  
    } 
    if (!this.field_70170_p.field_72995_K && result.field_72308_g != null)
      if (result.field_72308_g instanceof EntityLivingBase && !this.field_70170_p.field_72995_K && func_85052_h() != null && func_85052_h() instanceof EntityTameBase && !((EntityTameBase)func_85052_h()).func_184191_r(result.field_72308_g)) {
        if (ACConfig.hardcoreMode && result.field_72308_g instanceof net.minecraft.entity.player.EntityPlayer)
          result.field_72308_g.func_70097_a(DamageSource.func_76356_a((Entity)this, (Entity)func_85052_h()).func_76348_h().func_151518_m(), 2.0F); 
        ((EntityTameBase)func_85052_h()).func_70652_k(result.field_72308_g);
        if (this.field_70146_Z.nextBoolean() && !EntityUtil.isEntityCoralium((EntityLivingBase)result.field_72308_g))
          ((EntityLivingBase)result.field_72308_g).func_70690_d(new PotionEffect(AbyssalCraftAPI.coralium_plague, 200)); 
        if (this.field_70146_Z.nextInt(2) == 0)
          ((EntityLivingBase)result.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 100)); 
        if (this.field_70146_Z.nextInt(2) == 0)
          ((EntityLivingBase)result.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76419_f, 100)); 
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
