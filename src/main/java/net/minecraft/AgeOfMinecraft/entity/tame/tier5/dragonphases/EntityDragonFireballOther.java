package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.util.AttributeCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDragonFireballOther extends EntityDragonFireball {
  public EntityDragonFireballOther(World worldIn) {
    super(worldIn);
    setSize(1.0F, 1.0F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityDragonFireballOther(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public EntityDragonFireballOther(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && movingObject.entityHit != null && this.shootingEntity != null && movingObject.entityHit != this.shootingEntity) {
      if (movingObject.entityHit instanceof EntityLivingBase && this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && !false) {
        copyLocationAndAnglesFrom(movingObject.entityHit);
        ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was destroyed by ", DamageSource.causeFireballDamage(this, this.shootingEntity), (float)AttributeCompat.getBaseValue(this.shootingEntity, SharedMonsterAttributes.ATTACK_DAMAGE, 0.0D));
        EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false);
      } 
      if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && movingObject.entityHit instanceof EntityLivingBase && false) {
        ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 3));
        ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
        ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
        ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
      } 
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(8.0D));
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
      if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase)
        entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity); 
      entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WITHER, 100, 2));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.GLOWING, 400, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.HUNGER, 200, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 600, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.REGENERATION, 200, 9));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.SPEED, 2000, 1));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.STRENGTH, 2000, 1));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.HASTE, 2000, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2000, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.RESISTANCE, 2000, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.ABSORPTION, 2000, 4));
      entityareaeffectcloud.setRadius(1.0F);
      entityareaeffectcloud.setDuration(100 + this.rand.nextInt(100));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          if (this.shootingEntity != null && getDistanceSq(entitylivingbase) < 64.0D)
            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ); 
        }  
      this.world.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
      this.world.spawnEntity(entityareaeffectcloud);
      if (this.shootingEntity != null)
        EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false);
      setDead();
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && movingObject.entityHit == null) {
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(8.0D));
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
      entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity);
      entityareaeffectcloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WITHER, 100, 2));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.GLOWING, 400, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.HUNGER, 200, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 600, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.SLOWNESS, 600, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 1, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.REGENERATION, 200, 9));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.SPEED, 2000, 1));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.STRENGTH, 2000, 1));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.HASTE, 2000, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2000, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.REGENERATION, 600, 3));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.RESISTANCE, 2000, 0));
      entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.ABSORPTION, 2000, 4));
      entityareaeffectcloud.setRadius(1.0F);
      entityareaeffectcloud.setDuration(100 + this.rand.nextInt(100));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          if (this.shootingEntity != null && getDistanceSq(entitylivingbase) < 64.0D)
            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ); 
        }  
      this.world.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
      this.world.spawnEntity(entityareaeffectcloud);
      if (this.shootingEntity != null)
        EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false);
      setDead();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.shootingEntity != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive() && !false)
          onImpact(new RayTraceResult(entity1));
      }  
  }
}


