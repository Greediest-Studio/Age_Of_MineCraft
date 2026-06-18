package net.minecraft.AgeOfMinecraft.entity.tame.other;

import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPortalLightning extends EntityFireball {
  public Entity targetEntity;
  
  public EntityPortalLightning(World worldIn) {
    super(worldIn);
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setNoGravity(true);
  }
  
  public EntityPortalLightning(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn);
    this.shootingEntity = shooter;
    setLocationAndAngles(accelX, accelY, accelZ, shooter.rotationYaw, shooter.rotationPitch);
    setPosition(this.posX, this.posY, this.posZ);
    this.accelerationX = accelX;
    this.accelerationY = accelY;
    this.accelerationZ = accelZ;
  }
  
  public EntityPortalLightning(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
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
  
  public EntityPortalLightning(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
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
    if (!this.world.isRemote) {
      setFlag(6, isGlowing());
      if (!this.isDead) {
        ChunkLoadingEvent.updateLoaded((Entity)this);
      } else {
        ChunkLoadingEvent.stopLoading((Entity)this);
      } 
    } 
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setNoGravity(true);
    onEntityUpdate();
    if (this.targetEntity != null && !this.targetEntity.isEntityAlive())
      this.targetEntity = null; 
    this.motionX *= 0.0D;
    this.motionY *= 0.0D;
    this.motionZ *= 0.0D;
    if (this.ticksExisted == 1) {
      this.accelerationX = this.posX;
      this.accelerationY = this.posY;
      this.accelerationZ = this.posZ;
      setPosition(this.posX, this.posY, this.posZ);
    } 
    if (this.ticksExisted > 1 && this.targetEntity != null)
      setLocationAndAngles(this.targetEntity.posX, this.targetEntity.posY + this.targetEntity.getEyeHeight() * 0.2D, this.targetEntity.posZ, this.targetEntity.rotationYaw, this.targetEntity.rotationPitch); 
    if (this.ticksExisted > 2) {
      short short1 = (short)(int)getDistanceSq(this.accelerationX, this.accelerationY, this.accelerationZ);
      for (int i = 0; i < short1; i++) {
        double d9 = i / (short1 - 1.0D);
        double d6 = this.posX + (this.posX - this.accelerationX) * -d9;
        double d7 = this.posY + (this.posY - this.accelerationY) * -d9;
        double d8 = this.posZ + (this.posZ - this.accelerationZ) * -d9;
        if (this.world.isRemote)
          this.world.spawnParticle(EnumParticleTypes.END_ROD, true, d6, d7, d8, 0.0D, 0.01D, 0.0D, new int[0]); 
      } 
    } 
    if (this.ticksExisted > 5 && this.shootingEntity != null && this.targetEntity != null && getDistance(this.targetEntity) <= 1.0D) {
      this.targetEntity.playSound(ESound.lightningShot, 10000.0F, 1.0F);
      this.targetEntity.onStruckByLightning(null);
      this.targetEntity.hurtResistantTime = 0;
      if (this.shootingEntity instanceof EntityTameBase) {
        this.world.addWeatherEffect((Entity)new EntityLightningBolt(this.world, this.accelerationX - 0.5D, this.accelerationY, this.accelerationZ - 0.5D, true));
        List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, (new AxisAlignedBB(getPosition())).grow(2.0D));
        for (Entity entity : entities) {
          if (entity instanceof EntityLivingBase) {
            ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)this.targetEntity, " was electrocuted by ", (new EntityDamageSource("indirectMagic", (Entity)this.shootingEntity)).setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute(), 10.0F);
            if (entity.isEntityAlive())
              ((EntityTameBase)this.shootingEntity).attackEntityAsMob(this.targetEntity); 
          } 
        } 
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_THUNDER, SoundCategory.WEATHER, 10000.0F, 0.8F + this.rand.nextFloat() * 0.2F);
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.WEATHER, 2.0F, 0.5F + this.rand.nextFloat() * 0.2F);
        if (this.targetEntity instanceof EntityLivingBase && !(this.targetEntity instanceof EntityPlayer) && !(this.targetEntity instanceof net.minecraft.entity.EntityLiving)) {
          this.targetEntity.motionY++;
          if (this.targetEntity instanceof EntityLivingBase)
            if (!this.targetEntity.isEntityAlive() && !this.targetEntity.isDead)
              this.shootingEntity.onKillEntity((EntityLivingBase)this.targetEntity);  
          this.targetEntity.setFire(100);
        } 
      } 
      setDead();
    } 
    if (this.ticksExisted > 20 || (!this.world.isRemote && this.targetEntity == null))
      setDead(); 
  }
}
