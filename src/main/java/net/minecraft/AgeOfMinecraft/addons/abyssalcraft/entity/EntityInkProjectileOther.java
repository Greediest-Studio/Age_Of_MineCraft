package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.common.entity.EntityInkProjectile;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityInkProjectileOther extends EntityInkProjectile {
  public EntityInkProjectileOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityInkProjectileOther(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntityInkProjectileOther(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  protected void onImpact(RayTraceResult result) {
    if (!this.world.isRemote && result.entityHit != null)
      if ((result.entityHit instanceof EntityLivingBase && getThrower() instanceof EntityTameBase && !false) || (result.entityHit instanceof EntityTameBase && false && ((EntityTameBase)getThrower()).getFakeHealth() > 0.0F)) {
        ((EntityTameBase)getThrower()).inflictEngenderMobDamage((EntityLivingBase)result.entityHit, " was pummeled by ", DamageSource.causeThrownDamage(this, getThrower()), 1.0F);
        setDead();
      }  
    if (!this.world.isRemote && result.entityHit == null)
      setDead(); 
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive())
          onImpact(new RayTraceResult(entity1));
      }  
  }
}


