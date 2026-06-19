package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCoraliumChargeOther extends EntityFireball {
  public EntityCoraliumChargeOther(World worldIn) {
    super(worldIn);
    setSize(1.0F, 1.0F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityCoraliumChargeOther(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public EntityCoraliumChargeOther(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.VILLAGER_HAPPY;
  }
  
  protected boolean isFireballFiery() {
    return false;
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (!this.world.isRemote && movingObject.entityHit != null && this.shootingEntity != null && movingObject.entityHit != this.shootingEntity) {
      if ((movingObject.entityHit instanceof EntityLivingBase && this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && !false) || (movingObject.entityHit instanceof EntityTameBase && false && ((EntityTameBase)this.shootingEntity).getFakeHealth() > 0.0F)) {
        ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was destroyed by ", DamageSource.causeFireballDamage(this, (Entity)this.shootingEntity), (float)((EntityTameBase)this.shootingEntity).getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
        EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false);
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
      entityareaeffectcloud.setParticle(EnumParticleTypes.VILLAGER_HAPPY);
      entityareaeffectcloud.addEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 400));
      entityareaeffectcloud.setRadius(1.0F);
      entityareaeffectcloud.setDuration(100 + this.rand.nextInt(100));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          if (this.shootingEntity != null && getDistanceSq((Entity)entitylivingbase) < 64.0D)
            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ); 
        }  
      this.world.playEvent(2006, new BlockPos(this.posX, this.posY, this.posZ), 0);
      this.world.spawnEntity((Entity)entityareaeffectcloud);
      if (this.shootingEntity != null)
        EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false); 
      setDead();
    } 
    if (!this.world.isRemote && movingObject.entityHit == null) {
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(8.0D));
      EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
      if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase)
        entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity); 
      entityareaeffectcloud.setParticle(EnumParticleTypes.VILLAGER_HAPPY);
      entityareaeffectcloud.addEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 400));
      entityareaeffectcloud.setRadius(1.0F);
      entityareaeffectcloud.setDuration(100 + this.rand.nextInt(100));
      entityareaeffectcloud.setRadiusPerTick((3.0F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());
      if (!list.isEmpty())
        for (EntityLivingBase entitylivingbase : list) {
          if (this.shootingEntity != null && getDistanceSq((Entity)entitylivingbase) < 64.0D)
            entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ); 
        }  
      this.world.playSound((EntityPlayer)null, getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FIREBALL_EPLD, SoundCategory.MASTER, 1.0F, this.world.rand.nextFloat() * 0.1F + 0.9F);
      this.world.spawnEntity((Entity)entityareaeffectcloud);
      if (this.shootingEntity != null)
        EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY + 1.0D, this.posZ, 7.0F, false, false); 
      setDead();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive())
          onImpact(new RayTraceResult((Entity)entity1)); 
      }  
  }
}


