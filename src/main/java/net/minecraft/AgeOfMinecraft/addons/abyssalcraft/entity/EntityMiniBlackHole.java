package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityMiniBlackHole extends EntityFireball {
  public EntityTameBase shootingEntity;
  
  public EntityMiniBlackHole(World worldIn) {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntityMiniBlackHole(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    this.shootingEntity = (EntityTameBase)shooter;
    setSize(0.5F, 0.5F);
  }
  
  public EntityMiniBlackHole(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(0.5F, 0.5F);
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
  
  protected float getMotionFactor() {
    return 0.8F;
  }
  
  protected void onImpact(RayTraceResult result) {
    if (!this.world.isRemote && this.shootingEntity != null && result.entityHit != null && (result.entityHit instanceof EntityJzahar || result.entityHit instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm || result.entityHit instanceof net.minecraft.entity.IEntityMultiPart)) {
      this.shootingEntity.attackEntityAsMob(result.entityHit);
      EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 3.0F, false, false);
      setDead();
    } 
    if (!this.world.isRemote && this.shootingEntity != null && result.entityHit == null) {
      EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 3.0F, false, false);
      setDead();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (this.ticksExisted > 200 && !this.world.isRemote && this.shootingEntity != null) {
      EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 3.0F, false, false);
      setDead();
    } 
    this.world.spawnParticle(getParticleType(), this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(8.0D));
    if (list != null && !list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        if (entity.isEntityAlive() && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) && !(entity instanceof EntityJzahar) && this.shootingEntity != null && entity instanceof EntityLivingBase && !false) {
          if (entity.posY < this.posY)
            entity.motionY += 0.02500000037252903D; 
          double d1 = 1.0D;
          float x = MathHelper.cos((this.ticksExisted + i)) * (1.0F + (float)(this.rand.nextGaussian() * 2.0D));
          float z = MathHelper.sin((this.ticksExisted + i)) * (1.0F + (float)(this.rand.nextGaussian() * 2.0D));
          double d2 = this.posX + x - entity.posX;
          double d3 = this.posY - entity.posY;
          double d4 = this.posZ + z - entity.posZ;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          if (entity.getDistanceSq(this) <= 9.0D && this.shootingEntity != null && entity instanceof EntityLivingBase && !false) {
            entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.isEntityAlive() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " touched a Mini Black Hole's event horizon thanks to " + this.shootingEntity.getName() + " (" + this.shootingEntity.getOwner().getName() + ")", new Object[0]));
          } 
          if (entity.getDistanceSq(this) <= 2.0D && this.shootingEntity != null && entity instanceof EntityLivingBase && !false) {
            if (entity instanceof EntityLivingBase)
              ((EntityLivingBase)entity).setHealth(((EntityLivingBase)entity).getHealth() - 0.1F); 
            entity.attackEntityFrom(DamageSource.CRAMMING.setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute(), 4.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.isEntityAlive() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was sucked into a Mini Black Hole thanks to " + this.shootingEntity.getName() + " (" + this.shootingEntity.getOwner().getName() + ")", new Object[0]));
            if (!entity.isEntityAlive() && entity.isNonBoss() && !(entity instanceof EntityTameBase)) {
              if (entity instanceof EntityLiving)
                ((EntityLiving)entity).spawnExplosionParticle(); 
              entity.setDead();
            } 
          } 
        } 
        if (entity != null && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityMiniBlackHole) && !(entity instanceof EntityBlackHole) && !(entity instanceof EntityImplosion)) {
          double d1 = 1.0D;
          double d2 = this.posX - entity.posX;
          double d3 = this.posY - entity.posY;
          double d4 = this.posZ - entity.posZ;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.setPosition(entity.posX, entity.posY, entity.posZ);
          entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          if (entity instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityGuardianProjectile && getDistance(entity) <= 1.0D && this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase) {
            EntityTameBase.createEngenderModExplosion(this.shootingEntity, entity.posX, entity.posY, entity.posZ, 2.0F, true, false);
            entity.setDead();
          } 
        } 
      }  
  }
}


