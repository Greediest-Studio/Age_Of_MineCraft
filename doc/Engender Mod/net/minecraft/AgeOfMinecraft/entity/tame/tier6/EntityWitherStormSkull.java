package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherStormSkull extends EntityFireball {
  private static final DataParameter<Boolean> INVULNERABLE = EntityDataManager.func_187226_a(EntityWitherStormSkull.class, DataSerializers.field_187198_h);
  
  public float damage = 20.0F;
  
  public EntityWitherStormSkull(World worldIn) {
    super(worldIn);
    func_70105_a(1.0F, 1.0F);
  }
  
  public EntityWitherStormSkull(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  protected EnumParticleTypes func_184563_j() {
    return (this.field_70146_Z.nextInt(2) == 0) ? EnumParticleTypes.LAVA : EnumParticleTypes.SMOKE_LARGE;
  }
  
  protected float func_82341_c() {
    return 0.95F;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherStormSkull(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  public boolean func_70027_ad() {
    return false;
  }
  
  public float func_180428_a(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
    float f = super.func_180428_a(explosionIn, worldIn, pos, blockStateIn);
    Block block = blockStateIn.func_177230_c();
    if (EntityWither.func_181033_a(block))
      f = Math.min(0.8F, f); 
    return f;
  }
  
  protected void func_70227_a(RayTraceResult movingObject) {
    if (!this.field_70170_p.field_72995_K)
      if (this.field_70235_a != null) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity1 = list.get(i1);
            if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart) {
              if (this.field_70235_a != null)
                EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0F, false, false); 
              this.field_70235_a.func_70652_k((Entity)entity1);
              func_174815_a(this.field_70235_a, (Entity)entity1);
              func_70106_y();
            } 
          }  
      }  
    if (movingObject.field_72308_g != null) {
      func_82149_j(movingObject.field_72308_g);
      movingObject.field_72308_g.field_70172_ad = 0;
      byte b0 = 20;
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
        b0 = 40;
      } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
        b0 = 80;
      } 
      if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && movingObject.field_72308_g instanceof EntityLivingBase && !((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g))
        if (this.field_70235_a.func_70652_k(movingObject.field_72308_g)) {
          if (b0 > 0 && movingObject.field_72308_g instanceof EntityLivingBase) {
            ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 20 * b0));
            ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76419_f, 20 * b0, this.field_70170_p.func_175659_aa().func_151525_a()));
            ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 20 * b0, this.field_70170_p.func_175659_aa().func_151525_a()));
            ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_82731_v, 2147483647, 3));
          } 
          List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(6.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
          if (list1 != null && !list1.isEmpty())
            for (int i1 = 0; i1 < list1.size(); i1++) {
              EntityLivingBase entity1 = list1.get(i1);
              if (!((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entity1)) {
                this.field_70235_a.func_70652_k((Entity)entity1);
                if (b0 > 0 && movingObject.field_72308_g instanceof EntityLivingBase) {
                  ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 20 * b0));
                  ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76419_f, 20 * b0, this.field_70170_p.func_175659_aa().func_151525_a()));
                  ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 20 * b0, this.field_70170_p.func_175659_aa().func_151525_a()));
                  ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_82731_v, 2147483647, 3));
                } 
              } 
            }  
          if (this.field_70235_a != null) {
            List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g((this.field_70235_a instanceof EntityWitherStormHead) ? 9.0D : 3.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
            if (list != null && !list.isEmpty())
              for (int i1 = 0; i1 < list.size(); i1++) {
                EntityLivingBase entity1 = list.get(i1);
                if (!((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entity1))
                  ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was destroyed by ", DamageSource.func_76358_a(this.field_70235_a), this.damage); 
              }  
            if (movingObject.field_72308_g != null)
              if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && movingObject.field_72308_g instanceof EntityLivingBase && !((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g)) {
                movingObject.field_72308_g.field_70172_ad = 0;
                ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was destroyed by ", DamageSource.func_76358_a(this.field_70235_a), this.damage);
              }  
            EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
            if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase)
              entityareaeffectcloud.setOwner((EntityTameBase)this.field_70235_a); 
            entityareaeffectcloud.setParticle(EnumParticleTypes.SMOKE_NORMAL);
            entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.field_82731_v, 40 * b0, 1 + this.field_70170_p.func_175659_aa().func_151525_a()));
            entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.field_76440_q, 20 * b0));
            entityareaeffectcloud.setRadius(4.0F);
            entityareaeffectcloud.setDuration(120);
            this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
            EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, (this.field_70235_a instanceof EntityWitherStormHead) ? 6.0F : 2.0F, EngenderConfig.mobs.grief, EngenderConfig.mobs.grief);
            func_70106_y();
          } 
          func_70106_y();
          if (!movingObject.field_72308_g.func_70089_S()) {
            this.field_70235_a.func_70691_i(25.0F);
          } else {
            func_174815_a(this.field_70235_a, movingObject.field_72308_g);
          } 
        }  
    } else {
      if (this.field_70235_a != null)
        EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, (this.field_70235_a instanceof EntityWitherStormHead) ? 6.0F : 2.0F, EngenderConfig.mobs.grief, EngenderConfig.mobs.grief); 
      func_70106_y();
    } 
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  protected void func_70088_a() {
    this.field_70180_af.func_187214_a(INVULNERABLE, Boolean.valueOf(false));
  }
  
  public boolean isInvulnerable() {
    return ((Boolean)this.field_70180_af.func_187225_a(INVULNERABLE)).booleanValue();
  }
  
  public void setInvulnerable(boolean invulnerable) {
    this.field_70180_af.func_187227_b(INVULNERABLE, Boolean.valueOf(invulnerable));
  }
  
  protected boolean func_184564_k() {
    return false;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.field_70235_a != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S() && !((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entity1))
          func_70227_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
