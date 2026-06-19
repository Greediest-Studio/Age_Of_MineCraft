package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLargeFireballOther extends EntityLargeFireball {
  public float damage = 17.0F;
  
  public EntityLargeFireballOther(World worldIn) {
    super(worldIn);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityLargeFireballOther(World worldIn, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
    super(worldIn, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
  }
  
  public EntityLargeFireballOther(World worldIn, EntityLivingBase p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_) {
    super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
  }
  
  public boolean isImmuneToExplosions() {
    return true;
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (!this.world.isRemote)
      if (movingObject.entityHit != null && !(movingObject.entityHit instanceof EntityLargeFireballOther)) {
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase && movingObject.entityHit instanceof EntityLivingBase) {
          if (!movingObject.entityHit.isImmuneToFire() && movingObject.entityHit instanceof net.minecraft.entity.passive.EntityAnimal)
            movingObject.entityHit.setFire(30); 
          if (!((EntityTameBase)this.shootingEntity).isWild() && movingObject.entityHit instanceof net.minecraft.entity.monster.EntityGhast)
            this.damage = 1000.0F; 
          copyLocationAndAnglesFrom(movingObject.entityHit);
          ((EntityTameBase)this.shootingEntity).inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was fireballed by ", DamageSource.causeFireballDamage((EntityFireball)this, (Entity)this.shootingEntity), this.damage);
          applyEnchantments(this.shootingEntity, movingObject.entityHit);
          boolean flag = EngenderConfig.mobs.grief;
          EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY, this.posZ, this.explosionPower, flag, flag);
          if (!movingObject.entityHit.isImmuneToFire())
            movingObject.entityHit.setFire(30); 
          setDead();
        } 
      } else if (movingObject.entityHit == null) {
        if (this.shootingEntity != null && this.shootingEntity instanceof EntityTameBase)
          EntityTameBase.createEngenderModExplosion((Entity)this.shootingEntity, this.posX, this.posY, this.posZ, this.explosionPower, false, false); 
        setDead();
      }  
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(2.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.shootingEntity != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive())
          onImpact(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
