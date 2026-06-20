package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityInvisibleFangsProjectile extends EntitySmallFireball {
  public Entity targetEntity;
  
  public EntityInvisibleFangsProjectile(World worldIn) {
    super(worldIn);
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setInvisible(true);
  }
  
  public EntityInvisibleFangsProjectile(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setInvisible(true);
  }
  
  public EntityInvisibleFangsProjectile(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn, shooter, accelX, accelY, accelZ);
    this.targetEntity = target;
    this.accelerationX = 0.0D;
    this.accelerationY = 0.0D;
    this.accelerationZ = 0.0D;
    setInvisible(true);
  }
  
  public EntityInvisibleFangsProjectile(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setInvisible(true);
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SUSPENDED_DEPTH;
  }
  
  protected void onImpact(RayTraceResult result) {
    if (this.ticksExisted > 40 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && result.entityHit != null && result.entityHit instanceof EntityLivingBase)
      if (!false) {
        this.shootingEntity.attackEntityAsMob(this.targetEntity);
        setDead();
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
    return 0.85F;
  }
  
  public void onUpdate() {
    super.onUpdate();
    setInvisible(true);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.shootingEntity != null && this.targetEntity != null && this.ticksExisted > 2) {
      EntityEvokerFangOther entityevokerfangs = new EntityEvokerFangOther(this.world, this.posX, this.posY, this.posZ, (float)MathHelper.atan2(this.targetEntity.posZ - this.posZ, this.targetEntity.posX - this.posX), 5, (this.shootingEntity != null) ? this.shootingEntity : null);
      this.world.spawnEntity(entityevokerfangs);
    } 
    if (this.shootingEntity != null) {
      List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox(), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list1 != null && !list1.isEmpty())
          for (EntityLivingBase entity1 : list1) {
              if (getDistanceSq(entity1) < 0.1D && entity1 != null && entity1.isEntityAlive() && entity1 == this.targetEntity)
                  setDead();
          }
    } 
    if (this.targetEntity != null && !this.targetEntity.isEntityAlive())
      this.targetEntity = null; 
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
    if (this.targetEntity != null) {
      double d0 = this.targetEntity.posX - this.posX;
      double d1 = this.targetEntity.posY - this.posY;
      double d2 = this.targetEntity.posZ - this.posZ;
      float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      this.motionX = d0 / f2 * (getMotionFactor() * getMotionFactor()) * (getMotionFactor() * getMotionFactor()) + this.motionX * (getMotionFactor() * getMotionFactor());
      this.motionY = d1 / f2 * (getMotionFactor() * getMotionFactor()) * (getMotionFactor() * getMotionFactor()) + this.motionY * (getMotionFactor() * getMotionFactor());
      this.motionZ = d2 / f2 * (getMotionFactor() * getMotionFactor()) * (getMotionFactor() * getMotionFactor()) + this.motionZ * (getMotionFactor() * getMotionFactor());
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.targetEntity == null)
      setDead(); 
  }
}


