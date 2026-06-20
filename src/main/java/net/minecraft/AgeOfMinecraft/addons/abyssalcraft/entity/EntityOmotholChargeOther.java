package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityOmotholChargeOther extends EntityFireball {
  public EntityOmotholChargeOther(World worldIn) {
    super(worldIn);
    setSize(1.0F, 1.0F);
  }
  
  public EntityOmotholChargeOther(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public EntityOmotholChargeOther(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SMOKE_LARGE;
  }
  
  protected boolean isFireballFiery() {
    return false;
  }
  
  protected void onImpact(RayTraceResult result) {
    byte b0 = 5;
    if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
      b0 = 10;
    } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
      b0 = 20;
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (this.shootingEntity != null) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
        if (list != null && !list.isEmpty())
            for (EntityLivingBase entity1 : list) {
                if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart) {
                    this.shootingEntity.attackEntityAsMob(entity1);
                    applyEnchantments(this.shootingEntity, entity1);
                    setDead();
                }
            }
      } 
      if (result.entityHit != null) {
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && result.entityHit instanceof EntityLivingBase) {
          if (!result.entityHit.isImmuneToFire() && result.entityHit instanceof net.minecraft.entity.passive.EntityAnimal)
            result.entityHit.setFire(20); 
          if (!false) {
            this.shootingEntity.attackEntityAsMob(result.entityHit);
            applyEnchantments(this.shootingEntity, result.entityHit);
            result.entityHit.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity).setDamageBypassesArmor(), 10.0F);
            if (!result.entityHit.isImmuneToFire())
              result.entityHit.setFire(20); 
            if (b0 > 0 && result.entityHit instanceof EntityLivingBase) {
              ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 5 * b0, 0));
              ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * b0, this.world.getDifficulty().getId()));
              ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 5 * b0, 0));
            } 
            setDead();
          } 
        } 
        if (this.shootingEntity != null)
          EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 3.0F, false, false);
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(8.0D));
        EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase)
          entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity); 
        entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * b0, this.world.getDifficulty().getId()));
        entityareaeffectcloud.setRadius(2.0F);
        entityareaeffectcloud.setDuration(60);
        this.world.spawnEntity(entityareaeffectcloud);
      } 
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive())
          onImpact(new RayTraceResult(entity1));
      }  
  }
}


