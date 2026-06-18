package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCoraliumChargeOther extends EntityFireball {
  public EntityCoraliumChargeOther(World worldIn) {
    super(worldIn);
    func_70105_a(1.0F, 1.0F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityCoraliumChargeOther(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  public EntityCoraliumChargeOther(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    func_70105_a(1.0F, 1.0F);
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.VILLAGER_HAPPY;
  }
  
  protected boolean func_184564_k() {
    return false;
  }
  
  protected void func_70227_a(RayTraceResult movingObject) {
    if (!this.field_70170_p.field_72995_K && movingObject.field_72308_g != null && this.field_70235_a != null && movingObject.field_72308_g != this.field_70235_a) {
      if ((movingObject.field_72308_g instanceof EntityLivingBase && this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && !((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g)) || (movingObject.field_72308_g instanceof EntityTameBase && ((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g) && ((EntityTameBase)this.field_70235_a).getFakeHealth() > 0.0F)) {
        ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was destroyed by ", DamageSource.func_76362_a(this, (Entity)this.field_70235_a), (float)((EntityTameBase)this.field_70235_a).func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111264_e).func_111125_b());
        EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 7.0F, false, false);
      } 
      if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && movingObject.field_72308_g instanceof EntityLivingBase && ((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g)) {
        ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76428_l, 400, 3));
        ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76429_m, 6000, 0));
        ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76426_n, 6000, 0));
        ((EntityLivingBase)movingObject.field_72308_g).func_70690_d(new PotionEffect(MobEffects.field_76444_x, 2400, 3));
      } 
      List<EntityLivingBase> list = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(8.0D));
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase)
        entityareaeffectcloud.setOwner((EntityTameBase)this.field_70235_a); 
      entityareaeffectcloud.setParticle(EnumParticleTypes.VILLAGER_HAPPY);
      entityareaeffectcloud.addEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 400));
      entityareaeffectcloud.setRadius(1.0F);
      entityareaeffectcloud.setDuration(100 + this.field_70146_Z.nextInt(100));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          if (this.field_70235_a != null && func_70068_e((Entity)entitylivingbase) < 64.0D)
            entityareaeffectcloud.func_70107_b(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v); 
        }  
      this.field_70170_p.func_175718_b(2006, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
      this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
      if (this.field_70235_a != null)
        EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 7.0F, false, false); 
      func_70106_y();
    } 
    if (!this.field_70170_p.field_72995_K && movingObject.field_72308_g == null) {
      List<EntityLivingBase> list = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(8.0D));
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
      if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase)
        entityareaeffectcloud.setOwner((EntityTameBase)this.field_70235_a); 
      entityareaeffectcloud.setParticle(EnumParticleTypes.VILLAGER_HAPPY);
      entityareaeffectcloud.addEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 400));
      entityareaeffectcloud.setRadius(1.0F);
      entityareaeffectcloud.setDuration(100 + this.field_70146_Z.nextInt(100));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          if (this.field_70235_a != null && func_70068_e((Entity)entitylivingbase) < 64.0D)
            entityareaeffectcloud.func_70107_b(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v); 
        }  
      this.field_70170_p.func_184133_a((EntityPlayer)null, func_180425_c(), SoundEvents.field_187523_aM, SoundCategory.MASTER, 1.0F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
      this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
      if (this.field_70235_a != null)
        EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 7.0F, false, false); 
      func_70106_y();
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
