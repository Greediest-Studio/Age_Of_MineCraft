package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherStormSkull extends EntityFireball {
  private static final DataParameter<Boolean> INVULNERABLE = EntityDataManager.createKey(EntityWitherStormSkull.class, DataSerializers.BOOLEAN);
  
  public float damage = 20.0F;
  
  public EntityWitherStormSkull(World worldIn) {
    super(worldIn);
    setSize(1.0F, 1.0F);
  }
  
  public EntityWitherStormSkull(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  protected EnumParticleTypes getParticleType() {
    return (this.rand.nextInt(2) == 0) ? EnumParticleTypes.LAVA : EnumParticleTypes.SMOKE_LARGE;
  }
  
  protected float getMotionFactor() {
    return 0.95F;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherStormSkull(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    setSize(1.0F, 1.0F);
  }
  
  public boolean isBurning() {
    return false;
  }
  
  public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
    float f = super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
    Block block = blockStateIn.getBlock();
    if (EntityWither.canDestroyBlock(block))
      f = Math.min(0.8F, f); 
    return f;
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (this.shootingEntity != null) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
        if (list != null && !list.isEmpty())
            for (EntityLivingBase entity1 : list) {
                if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && !EntityWitherStorm.shouldIgnoreStormTarget(entity1)) {
                    if (this.shootingEntity != null)
                        EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 1.0F, false, false);
                    this.shootingEntity.attackEntityAsMob(entity1);
                    applyEnchantments(this.shootingEntity, entity1);
                    setDead();
                }
            }
      }  
    if (movingObject.entityHit != null) {
      copyLocationAndAnglesFrom(movingObject.entityHit);
      movingObject.entityHit.hurtResistantTime = 0;
      byte b0 = 20;
      if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
        b0 = 40;
      } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
        b0 = 80;
      } 
      if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && movingObject.entityHit instanceof EntityLivingBase && !EntityWitherStorm.shouldIgnoreStormTarget(movingObject.entityHit))
        if (this.shootingEntity.attackEntityAsMob(movingObject.entityHit)) {
          if (b0 > 0 && movingObject.entityHit instanceof EntityLivingBase) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * b0));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * b0, this.world.getDifficulty().getId()));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * b0, this.world.getDifficulty().getId()));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 2147483647, 3));
          } 
          List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(6.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
          if (list1 != null && !list1.isEmpty())
              for (EntityLivingBase entity1 : list1) {
                  if (!EntityWitherStorm.shouldIgnoreStormTarget(entity1)) {
                      this.shootingEntity.attackEntityAsMob(entity1);
                      if (b0 > 0 && movingObject.entityHit instanceof EntityLivingBase) {
                          ((EntityLivingBase) movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20 * b0));
                          ((EntityLivingBase) movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 20 * b0, this.world.getDifficulty().getId()));
                          ((EntityLivingBase) movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20 * b0, this.world.getDifficulty().getId()));
                          ((EntityLivingBase) movingObject.entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER, 2147483647, 3));
                      }
                  }
              }
          if (this.shootingEntity != null) {
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow((this.shootingEntity instanceof EntityWitherStormHead) ? 9.0D : 3.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
            if (list != null && !list.isEmpty())
                for (EntityLivingBase entity1 : list) {
                    if (!EntityWitherStorm.shouldIgnoreStormTarget(entity1))
                        ((EntityTameBase) this.shootingEntity).inflictEngenderMobDamage(entity1, " was destroyed by ", DamageSource.causeMobDamage(this.shootingEntity), this.damage);
                }
            if (movingObject.entityHit != null)
              if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && movingObject.entityHit instanceof EntityLivingBase && !EntityWitherStorm.shouldIgnoreStormTarget(movingObject.entityHit)) {
                movingObject.entityHit.hurtResistantTime = 0;
                ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was destroyed by ", DamageSource.causeMobDamage(this.shootingEntity), this.damage);
              }  
            EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
            if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase)
              entityareaeffectcloud.setOwner((EntityTameBase)this.shootingEntity); 
            entityareaeffectcloud.setParticle(EnumParticleTypes.SMOKE_NORMAL);
            entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.WITHER, 40 * b0, 1 + this.world.getDifficulty().getId()));
            entityareaeffectcloud.addEffect(new PotionEffect(MobEffects.BLINDNESS, 20 * b0));
            entityareaeffectcloud.setRadius(4.0F);
            entityareaeffectcloud.setDuration(120);
            this.world.spawnEntity(entityareaeffectcloud);
            EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, (this.shootingEntity instanceof EntityWitherStormHead) ? 6.0F : 2.0F, EngenderConfig.mobs.grief, EngenderConfig.mobs.grief);
            setDead();
          } 
          setDead();
          if (!movingObject.entityHit.isEntityAlive()) {
            this.shootingEntity.heal(25.0F);
          } else {
            applyEnchantments(this.shootingEntity, movingObject.entityHit);
          } 
        }  
    } else {
      if (this.shootingEntity != null)
        EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, (this.shootingEntity instanceof EntityWitherStormHead) ? 6.0F : 2.0F, EngenderConfig.mobs.grief, EngenderConfig.mobs.grief);
      setDead();
    } 
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  protected void entityInit() {
    this.getDataManager().register(INVULNERABLE, Boolean.FALSE);
  }
  
  public boolean isInvulnerable() {
    return this.getDataManager().get(INVULNERABLE);
  }
  
  public void setInvulnerable(boolean invulnerable) {
    this.getDataManager().set(INVULNERABLE, invulnerable);
  }
  
  protected boolean isFireballFiery() {
    return false;
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.shootingEntity != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive() && !EntityWitherStorm.shouldIgnoreStormTarget(entity1))
          onImpact(new RayTraceResult(entity1));
      }  
  }
}


