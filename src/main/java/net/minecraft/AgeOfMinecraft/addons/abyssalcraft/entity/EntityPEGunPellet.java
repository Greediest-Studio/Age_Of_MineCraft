package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPEGunPellet extends EntityFireball {
  public float damage = 2.0F;
  
  public int texture = 1;
  
  public EntityPEGunPellet(World worldIn) {
    super(worldIn);
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setNoGravity(true);
  }
  
  public EntityPEGunPellet(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(0.25F, 0.25F);
    this.noClip = true;
    setNoGravity(true);
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.ENCHANTMENT_TABLE;
  }
  
  protected void onImpact(RayTraceResult result) {
    if (!this.world.isRemote) {
      if (this.shootingEntity != null && result.entityHit != null && result.entityHit instanceof net.minecraft.entity.monster.EntityEnderman)
        result.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("blaster", this, this.shootingEntity)).setDamageBypassesArmor(), 0.0F);
      if (this.shootingEntity != null && result.entityHit != null && result.entityHit instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman)
        result.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("blaster", this, this.shootingEntity)).setDamageBypassesArmor(), 0.0F);
      if (this.shootingEntity != null && result.entityHit != null && result.entityHit.isEntityAlive() && !(result.entityHit instanceof net.minecraft.entity.monster.EntityEnderman) && !(result.entityHit instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman)) {
        result.entityHit.hurtResistantTime = 0;
        if (this.shootingEntity instanceof EntityTameBase && result.entityHit instanceof EntityLivingBase) {
          if (!false) {
            result.entityHit.setFire((int)this.damage);
            if (this.damage >= 10.0F) {
              result.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("blaster", this, this.shootingEntity)).setDamageBypassesArmor().setDamageIsAbsolute(), this.damage);
            } else {
              result.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("blaster", this, this.shootingEntity)).setDamageBypassesArmor(), this.damage);
            } 
          } 
        } else {
          result.entityHit.setFire((int)this.damage);
          playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5F, 2.0F);
          if (this.damage >= 10.0F) {
            result.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("blaster", this, this.shootingEntity)).setDamageBypassesArmor().setDamageIsAbsolute(), this.damage);
          } else {
            result.entityHit.attackEntityFrom((new EntityDamageSourceIndirect("blaster", this, this.shootingEntity)).setDamageBypassesArmor(), this.damage);
          } 
        } 
        applyEnchantments(this.shootingEntity, result.entityHit);
        setDead();
      } 
    } 
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
    return 1.0F;
  }
  
  public void onUpdate() {
    if (this.shootingEntity == null) {
      setDead();
    } else {
      super.onUpdate();
      for (int i = 0; i < this.texture - 1; i++) {
        this.motionX += this.accelerationX;
        this.motionY += this.accelerationY;
        this.motionZ += this.accelerationZ;
      } 
      if (this.damage >= 20.0F) {
        this.texture = 10;
      } else if (this.damage < 20.0F && this.damage >= 16.0F) {
        this.texture = 8;
      } else if (this.damage < 16.0F && this.damage >= 12.0F) {
        this.texture = 6;
      } else if (this.damage < 12.0F && this.damage >= 8.0F) {
        this.texture = 4;
      } else if (this.damage < 8.0F && this.damage >= 0.0F) {
        this.texture = 2;
      } 
      setSize(0.125F, 0.125F);
      this.noClip = true;
      setNoGravity(true);
      AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.world, this.posX, this.posY + 0.2D, this.posZ, 0.0D, 0.0D, 0.0D);
    } 
  }
}


