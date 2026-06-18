package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySnowballHarmful extends EntitySnowball {
  public float damage = 0.0F;
  
  public EntitySnowballHarmful(World worldIn) {
    super(worldIn);
  }
  
  public EntitySnowballHarmful(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntitySnowballHarmful(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 3)
      for (int i = 0; i < 16; i++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SNOWBALL, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);  
  }
  
  protected void func_70184_a(RayTraceResult result) {
    if (!this.field_70170_p.field_72995_K && func_85052_h() == null) {
      func_70106_y();
      return;
    } 
    if (!this.field_70170_p.field_72995_K && result.field_72308_g != null)
      if ((result.field_72308_g instanceof EntityLivingBase && func_85052_h() != null && func_85052_h() instanceof EntityTameBase && !((EntityTameBase)func_85052_h()).func_184191_r(result.field_72308_g)) || (result.field_72308_g instanceof EntityTameBase && ((EntityTameBase)func_85052_h()).func_184191_r(result.field_72308_g) && ((EntityTameBase)func_85052_h()).getFakeHealth() > 0.0F)) {
        ((EntityLivingBase)result.field_72308_g).field_70172_ad = 0;
        ((EntityTameBase)func_85052_h()).inflictEngenderMobDamage((EntityLivingBase)result.field_72308_g, " was pummeled by ", DamageSource.func_76356_a((Entity)this, (Entity)func_85052_h()), this.damage);
        this.field_70170_p.func_72960_a((Entity)this, (byte)3);
        func_70106_y();
      }  
    if (!this.field_70170_p.field_72995_K && result.field_72308_g == null) {
      this.field_70170_p.func_72960_a((Entity)this, (byte)3);
      func_70106_y();
    } 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (func_85052_h() != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S() && !((EntityTameBase)func_85052_h()).func_184191_r((Entity)entity1))
          func_70184_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
