package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShulkerBulletFriendly extends EntityShulkerBullet {
  private EntityTameBase field_184570_a;
  
  private Entity target;
  
  public EntityShulkerBulletFriendly(World worldIn) {
    super(worldIn);
  }
  
  public SoundCategory func_184176_by() {
    return SoundCategory.MASTER;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityShulkerBulletFriendly(World worldIn, double x, double y, double z, double motionXIn, double motionYIn, double motionZIn) {
    super(worldIn, x, y, z, motionXIn, motionYIn, motionZIn);
  }
  
  public EntityShulkerBulletFriendly(World worldIn, EntityTameBase p_i46772_2_, Entity p_i46772_3_, EnumFacing.Axis p_i46772_4_) {
    super(worldIn, (EntityLivingBase)p_i46772_2_, p_i46772_3_, p_i46772_4_);
    this.field_184570_a = p_i46772_2_;
    this.target = p_i46772_3_;
  }
  
  public void func_70106_y() {
    if (!this.field_184570_a.func_184614_ca().func_190926_b()) {
      EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_184570_a.func_184614_ca());
      ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2, 0.2D, 0.2D, 0.2D, 0.0D, new int[0]);
      func_184185_a(SoundEvents.field_187775_eP, 1.0F, 1.0F);
      if (!this.field_70170_p.field_72995_K)
        this.field_70170_p.func_72838_d((Entity)entityfireworkrocket); 
      if (this.target != null && this.target instanceof EntityLivingBase && ((EntityLivingBase)this.target).func_110143_aJ() <= 5.0F)
        this.target.func_184220_m((Entity)entityfireworkrocket); 
    } 
    super.func_70106_y();
  }
  
  public boolean func_180427_aV() {
    return true;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (this.field_184570_a != null && this.field_184570_a.moralRaisedTimer > 200) {
      this.field_70159_w *= 1.100000023841858D;
      this.field_70181_x *= 1.100000023841858D;
      this.field_70179_y *= 1.100000023841858D;
    } 
    if (this.target == null) {
      ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2, 0.2D, 0.2D, 0.2D, 0.0D, new int[0]);
      func_184185_a(SoundEvents.field_187775_eP, 1.0F, 1.0F);
      if (!this.field_70170_p.field_72995_K)
        func_70106_y(); 
    } else {
      if (!this.target.func_70089_S())
        func_70106_y(); 
      if (this.target instanceof net.minecraft.entity.IEntityMultiPart && this.target instanceof EntityLivingBase) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (!list.isEmpty())
          for (EntityLivingBase entity1 : list) {
            if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S()) {
              this.field_184570_a.func_70652_k(this.target);
              func_174815_a((EntityLivingBase)this.field_184570_a, this.target);
              func_184185_a(SoundEvents.field_187775_eP, 1.0F, 1.0F);
              if (!this.field_70170_p.field_72995_K)
                func_70106_y(); 
            } 
          }  
      } 
    } 
  }
  
  protected void func_184567_a(RayTraceResult result) {
    if (result.field_72308_g != null && this.target != null && result.field_72308_g == this.target && result.field_72308_g instanceof EntityLivingBase)
      if ((!this.field_184570_a.func_184191_r(result.field_72308_g) || (this.field_184570_a instanceof EntityShulker && ((EntityShulker)this.field_184570_a).isPositiveShulker() && this.field_184570_a.func_184191_r(result.field_72308_g))) && this.field_184570_a != null) {
        this.field_184570_a.inflictEngenderMobDamage((EntityLivingBase)result.field_72308_g, " was shot by ", DamageSource.func_76356_a((Entity)this, (Entity)this.field_184570_a), (float)this.field_184570_a.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111264_e).func_111125_b());
        ((EntityShulker)this.field_184570_a).inflictShulkerEffects((EntityLivingBase)result.field_72308_g);
        func_174815_a((EntityLivingBase)this.field_184570_a, result.field_72308_g);
        ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2, 0.2D, 0.2D, 0.2D, 0.0D, new int[0]);
        func_184185_a(SoundEvents.field_187775_eP, 1.0F, 1.0F);
        if (!this.field_70170_p.field_72995_K)
          func_70106_y(); 
      }  
  }
}
