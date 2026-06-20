package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityMagicMissile extends EntityFireball {
  public Entity targetEntity;
  
  public EntityMagicMissile(World worldIn) {
    super(worldIn);
    setSize(0.25F, 0.25F);
    this.noClip = true;
  }
  
  public EntityMagicMissile(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(0.25F, 0.25F);
    this.noClip = true;
  }
  
  public EntityMagicMissile(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn, shooter, accelX, accelY, accelZ);
    this.targetEntity = target;
    playSound(ESound.magicMissileFire, 1.0F, 1.0F);
    this.accelerationX = 0.0D;
    this.accelerationY = 0.0D;
    this.accelerationZ = 0.0D;
  }
  
  public EntityMagicMissile(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(0.25F, 0.25F);
    this.noClip = true;
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SUSPENDED_DEPTH;
  }
  
  protected void onImpact(RayTraceResult result) {
    if (this.ticksExisted > 40 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && result.entityHit != null && result.entityHit.hurtResistantTime <= 0 && result.entityHit instanceof EntityLivingBase)
      if (!false) {
        ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)this.targetEntity, " was shot by ", (new EntityDamageSourceIndirect("arrow", this, this.shootingEntity)).setMagicDamage().setProjectile(), 2.0F);
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.5F);
        setDead();
        this.targetEntity.hurtResistantTime = 0;
      }  
  }
  
  protected boolean isFireballFiery() {
    return false;
  }
  
  public boolean isBurning() {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  protected float getMotionFactor() {
    return (this.ticksExisted > 90) ? 0.9F : (this.ticksExisted / 100.0F);
  }
  
  public void onUpdate() {
    this.posX += this.motionX;
    this.posY += this.motionY;
    this.posZ += this.motionZ;
    if (this.shootingEntity != null) {
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(0.5D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list != null && !list.isEmpty())
          for (EntityLivingBase entity1 : list) {
              if (entity1 != null && entity1.isEntityAlive() && this.targetEntity != null && entity1 == this.targetEntity) {
                  entity1.hurtResistantTime = 0;
                  onImpact(new RayTraceResult(entity1));
              }
          }
    } 
    if (this.targetEntity != null && !this.targetEntity.isEntityAlive())
      this.targetEntity = null; 
    this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX, this.posY + 0.125D, this.posZ, 0.0D, 0.0D, 0.0D);
    if (this.motionX > getMotionFactor())
      this.motionX = getMotionFactor(); 
    if (this.motionY > getMotionFactor())
      this.motionY = getMotionFactor(); 
    if (this.motionZ > getMotionFactor())
      this.motionZ = getMotionFactor(); 
    if (this.motionX < -getMotionFactor())
      this.motionX = -getMotionFactor(); 
    if (this.motionY < -getMotionFactor())
      this.motionY = -getMotionFactor(); 
    if (this.motionZ < -getMotionFactor())
      this.motionZ = -getMotionFactor(); 
    if (this.ticksExisted > 20 && this.targetEntity != null) {
      double d0 = this.targetEntity.posX - this.posX;
      double d1 = this.targetEntity.posY - this.posY;
      double d2 = this.targetEntity.posZ - this.posZ;
      float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      this.motionX = d0 / f2 * (getMotionFactor() * getMotionFactor()) * (getMotionFactor() * getMotionFactor()) + this.motionX * (getMotionFactor() * getMotionFactor());
      this.motionY = d1 / f2 * (getMotionFactor() * getMotionFactor()) * (getMotionFactor() * getMotionFactor()) + this.motionY * (getMotionFactor() * getMotionFactor());
      this.motionZ = d2 / f2 * (getMotionFactor() * getMotionFactor()) * (getMotionFactor() * getMotionFactor()) + this.motionZ * (getMotionFactor() * getMotionFactor());
    } 
    setPosition(this.posX, this.posY, this.posZ);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.ticksExisted > 20 && this.targetEntity == null)
      if ((this.targetEntity == null || (this.targetEntity != null && !this.targetEntity.isEntityAlive())) && this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase) {
        List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(100.0D));
        if (entities != null && !entities.isEmpty()) {
          for (int i = 0; i < entities.size(); i++) {
            EntityLivingBase entity = entities.get(this.rand.nextInt(entities.size()));
            if (entity.isEntityAlive() && entity instanceof EntityLivingBase && !false)
              this.targetEntity = entity;
          } 
        } else {
          playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 1.0F, 1.5F);
          setDead();
        } 
      }  
  }
}


