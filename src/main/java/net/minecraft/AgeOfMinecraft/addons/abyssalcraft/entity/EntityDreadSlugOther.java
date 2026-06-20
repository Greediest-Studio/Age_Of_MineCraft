package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDreadSlugOther extends EntityThrowable {
  public EntityDreadSlugOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityDreadSlugOther(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntityDreadSlugOther(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  protected void onImpact(RayTraceResult result) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && result.entityHit != null)
      if (result.entityHit instanceof EntityLivingBase && getThrower() != null && getThrower() instanceof EntityTameBase && !false) {
        ((EntityTameBase)getThrower()).inflictEngenderMobDamage((EntityLivingBase)result.entityHit, " was pummeled by ", DamageSource.causeThrownDamage(this, getThrower()), 4.0F);
        if (!EntityUtil.isEntityDread((EntityLivingBase)result.entityHit))
          ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 200)); 
        setDead();
      }  
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && result.entityHit == null) {
      if (isBeingRidden())
        for (Entity entity : getPassengers()) {
          entity.playSound(ESound.amalgamate, 2.0F, 1.5F);
          entity.copyLocationAndAnglesFrom(this);
          entity.posY += 3.0D;
        }  
      setDead();
    } 
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


