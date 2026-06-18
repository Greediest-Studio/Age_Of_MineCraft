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
    setSize(0.3125F, 0.3125F);
  }
  
  public EntityWitherSkullOther(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_) {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.3125F, 0.3125F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherSkullOther(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_) {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(0.3125F, 0.3125F);
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    byte b0 = 10;
    if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
      b0 = 20;
    } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
      b0 = 60;
    } 
    if (!this.world.isRemote && movingObject.entityHit != this.shootingEntity && movingObject.entityHit != null)
      if ((movingObject.entityHit instanceof EntityLivingBase && this.shootingEntity instanceof EntityTameBase && !false) || (movingObject.entityHit instanceof EntityTameBase && false && ((EntityTameBase)this.shootingEntity).getFakeHealth() > 0.0F)) {
        copyLocationAndAnglesFrom(movingObject.entityHit);
        List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
        if (!list1.isEmpty())
          for (EntityLivingBase entitylivingbase : list1) {
            if (!false)
              ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage(entitylivingbase, " was shot by ", DamageSource.causeMobDamage(this.shootingEntity), this.damage); 
          }  
        ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was shot by ", DamageSource.causeMobDamage(this.shootingEntity), this.damage);
        ((EntityTameBase)this.shootingEntity).inflictCustomStatusEffect(this.world.getDifficulty(), (EntityLivingBase)movingObject.entityHit, MobEffects.WITHER, 10, 1);
        setDead();
        EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase)
          entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity); 
        entityareaeffectcloud.setParticle(EnumParticleTypes.SMOKE_NORMAL);
        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WITHER, 20 * b0, 1 + this.world.getDifficulty().getId()));
        entityareaeffectcloud.setDuration(40 + this.rand.nextInt(60));
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusPerTick((0.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
        this.world.spawnEntity((Entity)entityareaeffectcloud);
        EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY, this.posZ, 2.0F, false, (EngenderConfig.mobs.grief && isInvulnerable()));
        setDead();
      }  
    if (!this.world.isRemote && movingObject.entityHit == null) {
      List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
      if (!list1.isEmpty())
        for (EntityLivingBase entitylivingbase : list1) {
          if (!false)
            ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage(entitylivingbase, " was shot by ", DamageSource.causeMobDamage(this.shootingEntity), this.damage); 
        }  
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
      entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity);
      entityareaeffectcloud.setParticle(EnumParticleTypes.SMOKE_NORMAL);
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WITHER, 20 * b0, 1 + this.world.getDifficulty().getId()));
      entityareaeffectcloud.setDuration(40 + this.rand.nextInt(60));
      entityareaeffectcloud.setRadius(3.0F);
      entityareaeffectcloud.setRadiusPerTick((0.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      this.world.spawnEntity((Entity)entityareaeffectcloud);
      EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY, this.posZ, 2.0F, false, (EngenderConfig.mobs.grief && isInvulnerable()));
      setDead();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.shootingEntity != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive() && !false)
          onImpact(new RayTraceResult((Entity)entity1)); 
      }  
  }
}


