package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDisintigrationRay extends EntityFireball {
  public Entity targetEntity;
  
  public EntityDisintigrationRay(World worldIn) {
    super(worldIn);
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setNoGravity(true);
  }
  
  public EntityDisintigrationRay(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn);
    this.shootingEntity = shooter;
    setLocationAndAngles(accelX, accelY, accelZ, shooter.rotationYaw, shooter.rotationPitch);
    setPosition(this.posX, this.posY, this.posZ);
    this.accelerationX = accelX;
    this.accelerationY = accelY;
    this.accelerationZ = accelZ;
  }
  
  public EntityDisintigrationRay(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn);
    this.shootingEntity = shooter;
    setLocationAndAngles(accelX, accelY, accelZ, shooter.rotationYaw, shooter.rotationPitch);
    setPosition(this.posX, this.posY, this.posZ);
    this.targetEntity = target;
    this.accelerationX = accelX;
    this.accelerationY = accelY;
    this.accelerationZ = accelZ;
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
  }
  
  public EntityDisintigrationRay(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    this(worldIn);
    setLocationAndAngles(accelX, accelY, accelZ, 0.0F, 0.0F);
    setPosition(this.posX, this.posY, this.posZ);
    this.accelerationX = accelX;
    this.accelerationY = accelY;
    this.accelerationZ = accelZ;
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.END_ROD;
  }
  
  protected void onImpact(RayTraceResult result) {}
  
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
    return 0.99F;
  }
  
  public void onUpdate() {
    if (!this.world.isRemote)
      setFlag(6, isGlowing()); 
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setNoGravity(true);
    onEntityUpdate();
    if (this.targetEntity != null && !this.targetEntity.isEntityAlive())
      this.targetEntity = null; 
    if (this.ticksExisted == 1) {
      this.accelerationX = this.posX;
      this.accelerationY = this.posY;
      this.accelerationZ = this.posZ;
    } 
    if (this.ticksExisted > 3 && this.targetEntity != null)
      setLocationAndAngles(this.targetEntity.posX, this.targetEntity.posY + this.targetEntity.getEyeHeight() * 0.2D, this.targetEntity.posZ, this.targetEntity.rotationYaw, this.targetEntity.rotationPitch); 
    if (this.ticksExisted > 4 && this.shootingEntity != null && this.targetEntity != null) {
      this.targetEntity.hurtResistantTime = 0;
      if (!this.world.isRemote)
        if (this.targetEntity instanceof EntityLiving && !(this.targetEntity instanceof EntityTameBase)) {
          ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)this.targetEntity, " was vaporized by ", (new EntityDamageSource("indirectMagic", (Entity)this.shootingEntity)).setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute(), 20.0F);
          if (((EntityLiving)this.targetEntity).getHealth() <= 80.0F && this.targetEntity.isNonBoss())
            this.targetEntity.setDead(); 
          this.targetEntity.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2.0F, 2.0F);
          this.targetEntity.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 2.0F, 2.0F);
          this.targetEntity.playSound(SoundEvents.BLOCK_LAVA_POP, 2.0F, 2.0F);
          this.targetEntity.world.setEntityState(this.targetEntity, (byte)20);
        } else {
          ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)this.targetEntity, " was vaporized by ", (new EntityDamageSource("indirectMagic", (Entity)this.shootingEntity)).setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute(), 20.0F);
        }  
      setDead();
    } 
    if (this.ticksExisted > 2) {
      short short1 = (short)(int)getDistanceSq(this.accelerationX, this.accelerationY, this.accelerationZ);
      for (int i = 0; i < short1; i++) {
        double d9 = i / (short1 - 1.0D);
        double d6 = this.posX + (this.posX - this.accelerationX) * -d9;
        double d7 = this.posY + (this.posY - this.accelerationY) * -d9;
        double d8 = this.posZ + (this.posZ - this.accelerationZ) * -d9;
        if (this.world.isRemote)
          this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, true, d6, d7, d8, 0.0D, 0.01D, 0.0D, new int[0]); 
      } 
      setPosition(this.posX, this.posY, this.posZ);
    } else {
      this.motionX *= 0.0D;
      this.motionY *= 0.0D;
      this.motionZ *= 0.0D;
    } 
    this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
    if (this.ticksExisted > 20 || (!this.world.isRemote && this.targetEntity == null))
      setDead(); 
  }
}
