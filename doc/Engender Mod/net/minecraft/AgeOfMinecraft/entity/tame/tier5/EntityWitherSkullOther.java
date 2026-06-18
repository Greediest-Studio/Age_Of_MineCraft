package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherSkullOther extends EntityWitherSkull {
  public float damage = 8.0F;
  
  public EntityWitherSkullOther(World worldIn) {
    super(worldIn);
    func_70105_a(0.3125F, 0.3125F);
  }
  
  public EntityWitherSkullOther(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_) {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    func_70105_a(0.3125F, 0.3125F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherSkullOther(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    func_70105_a(0.3125F, 0.3125F);
  }
  
  protected void func_70227_a(RayTraceResult movingObject) {
    byte b0 = 10;
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
      b0 = 20;
    } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
      b0 = 60;
    } 
    if (!this.field_70170_p.field_72995_K && movingObject.field_72308_g != this.field_70235_a && movingObject.field_72308_g != null)
      if ((movingObject.field_72308_g instanceof EntityLivingBase && this.field_70235_a instanceof EntityTameBase && !((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g)) || (movingObject.field_72308_g instanceof EntityTameBase && ((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g) && ((EntityTameBase)this.field_70235_a).getFakeHealth() > 0.0F)) {
        func_82149_j(movingObject.field_72308_g);
        List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (!list1.isEmpty())
          for (EntityLivingBase entitylivingbase : list1) {
            if (!((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entitylivingbase))
              ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage(entitylivingbase, " was shot by ", DamageSource.func_76358_a(this.field_70235_a), this.damage); 
          }  
        ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was shot by ", DamageSource.func_76358_a(this.field_70235_a), this.damage);
        ((EntityTameBase)this.field_70235_a).inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)movingObject.field_72308_g, MobEffects.field_82731_v, 10, 1);
        func_70106_y();
        EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase)
          entityareaeffectcloud.setOwner((EntityTameBase)this.field_70235_a); 
        entityareaeffectcloud.setParticle(EnumParticleTypes.SMOKE_NORMAL);
        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.field_82731_v, 20 * b0, 1 + this.field_70170_p.func_175659_aa().func_151525_a()));
        entityareaeffectcloud.setDuration(40 + this.field_70146_Z.nextInt(60));
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusPerTick((0.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
        this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
        EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0F, false, (EngenderConfig.mobs.grief && func_82342_d()));
        func_70106_y();
      }  
    if (!this.field_70170_p.field_72995_K && movingObject.field_72308_g == null) {
      List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (!list1.isEmpty())
        for (EntityLivingBase entitylivingbase : list1) {
          if (!((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entitylivingbase))
            ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage(entitylivingbase, " was shot by ", DamageSource.func_76358_a(this.field_70235_a), this.damage); 
        }  
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      entityareaeffectcloud.setOwner((EntityTameBase)this.field_70235_a);
      entityareaeffectcloud.setParticle(EnumParticleTypes.SMOKE_NORMAL);
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.field_82731_v, 20 * b0, 1 + this.field_70170_p.func_175659_aa().func_151525_a()));
      entityareaeffectcloud.setDuration(40 + this.field_70146_Z.nextInt(60));
      entityareaeffectcloud.setRadius(3.0F);
      entityareaeffectcloud.setRadiusPerTick((0.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
      EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0F, false, (EngenderConfig.mobs.grief && func_82342_d()));
      func_70106_y();
    } 
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
