package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySmallFireballOther extends EntitySmallFireball {
  public float damage = 6.0F;
  
  public EntitySmallFireballOther(World worldIn) {
    super(worldIn);
    setSize(0.3125F, 0.3125F);
  }
  
  public EntitySmallFireballOther(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_) {
    super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
    setSize(0.3125F, 0.3125F);
  }
  
  public EntitySmallFireballOther(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_) {
    super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
    setSize(0.3125F, 0.3125F);
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (this.shootingEntity != null) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
        if (list != null && !list.isEmpty())
            for (EntityLivingBase entity1 : list) {
                if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart) {
                    this.shootingEntity.attackEntityAsMob(entity1);
                    applyEnchantments(this.shootingEntity, entity1);
                    setDead();
                }
            }
      } 
      if (movingObject.entityHit != null && movingObject.entityHit.hurtResistantTime <= 5 && movingObject.entityHit.isEntityAlive()) {
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && movingObject.entityHit instanceof EntityLivingBase) {
          if (!movingObject.entityHit.isImmuneToFire() && movingObject.entityHit instanceof net.minecraft.entity.passive.EntityAnimal)
            movingObject.entityHit.setFire(10); 
          copyLocationAndAnglesFrom(movingObject.entityHit);
          ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was fireballed by ", DamageSource.causeFireballDamage(this, this.shootingEntity), this.damage);
          applyEnchantments(this.shootingEntity, movingObject.entityHit);
          if (!movingObject.entityHit.isImmuneToFire())
            movingObject.entityHit.setFire(10); 
          if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && ((EntityTameBase)this.shootingEntity).isHero())
            EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 1.0F, EngenderConfig.mobs.grief, false);
          setDead();
        } 
      } else if (movingObject.entityHit == null) {
        boolean flag1 = true;
        if (this.shootingEntity != null && this.shootingEntity instanceof net.minecraft.entity.EntityLiving)
          flag1 = EngenderConfig.mobs.grief; 
        if (flag1) {
          BlockPos blockpos = movingObject.getBlockPos().offset(movingObject.sideHit);
          if (this.world.isAirBlock(blockpos)) {
            this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
            if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && ((EntityTameBase)this.shootingEntity).isHero())
              EntityTameBase.createEngenderModExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 1.0F, EngenderConfig.mobs.grief, false);
            setDead();
          } 
        } 
      } 
    } 
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public boolean isImmuneToExplosions() {
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.shootingEntity != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive())
          onImpact(new RayTraceResult(entity1));
      }  
  }
}
