package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPoisonSpray extends EntitySnowball {
  public EntityPoisonSpray(World worldIn) {
    super(worldIn);
  }
  
  public EntityPoisonSpray(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntityPoisonSpray(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    byte b0 = 10;
    if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
      b0 = 20;
    } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
      b0 = 60;
    } 
    if (movingObject.entityHit != null) {
      if (getThrower() != null && getThrower() instanceof EntityTameBase && movingObject.entityHit instanceof EntityLivingBase && !false) {
        movingObject.entityHit.hurtResistantTime = 0;
        if (getThrower().attackEntityAsMob(movingObject.entityHit)) {
          if (b0 > 0 && movingObject.entityHit instanceof EntityLivingBase)
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * b0)); 
          setDead();
          applyEnchantments(getThrower(), movingObject.entityHit);
          EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
          if (getThrower() != null && getThrower() instanceof EntityTameBase)
            entityareaeffectcloud.setOwner((EntityTameBase)getThrower()); 
          entityareaeffectcloud.setParticle(EnumParticleTypes.SLIME);
          entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.POISON, 20 * b0));
          entityareaeffectcloud.setRadius(2.0F);
          entityareaeffectcloud.setDuration(100);
          playSound(SoundEvents.ENTITY_SMALL_SLIME_SQUISH, 1.0F, 1.0F);
          this.world.spawnEntity((Entity)entityareaeffectcloud);
        } 
      } 
    } else {
      setDead();
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
      entityareaeffectcloud.setOwner((EntityTameBase)getThrower());
      entityareaeffectcloud.setParticle(EnumParticleTypes.SLIME);
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.POISON, 20 * b0));
      entityareaeffectcloud.setRadius(2.0F);
      entityareaeffectcloud.setDuration(100);
      playSound(SoundEvents.ENTITY_SMALL_SLIME_SQUISH, 1.0F, 1.0F);
      this.world.spawnEntity((Entity)entityareaeffectcloud);
    } 
    List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (list1 != null && !list1.isEmpty())
        for (EntityLivingBase entity1 : list1) {
            if (!false) {
                getThrower().attackEntityAsMob((Entity) entity1);
                if (b0 > 0 && entity1 instanceof EntityLivingBase)
                    entity1.addPotionEffect(new PotionEffect(MobEffects.POISON, 20 * b0));
            }
        }
  }
}


