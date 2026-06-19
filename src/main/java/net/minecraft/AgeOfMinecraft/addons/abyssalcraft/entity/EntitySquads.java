package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySquads extends EntityThrowable {
  public EntitySquads(World worldIn) {
    super(worldIn);
    setSize(0.75F, 0.75F);
  }
  
  public EntitySquads(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
    setSize(0.75F, 0.75F);
  }
  
  public EntitySquads(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
    setSize(0.75F, 0.75F);
  }
  
  protected void onImpact(RayTraceResult result) {
    if (getThrower() != null) {
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list != null && !list.isEmpty())
          for (EntityLivingBase entity1 : list) {
              if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart) {
                  getThrower().attackEntityAsMob(entity1);
                  if (this.rand.nextBoolean() && !EntityUtil.isEntityCoralium(entity1))
                      entity1.addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 200));
                  if (this.rand.nextInt(2) == 0)
                      entity1.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100));
                  if (this.rand.nextInt(2) == 0)
                      entity1.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100));
                  applyEnchantments(getThrower(), entity1);
                  setDead();
              }
          }
    } 
    if (!this.world.isRemote && result.entityHit != null)
      if (result.entityHit instanceof EntityLivingBase && !this.world.isRemote && getThrower() != null && getThrower() instanceof EntityTameBase && !false) {
        if (ACConfig.hardcoreMode && result.entityHit instanceof net.minecraft.entity.player.EntityPlayer)
          result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()).setDamageBypassesArmor().setDamageIsAbsolute(), 2.0F);
        getThrower().attackEntityAsMob(result.entityHit);
        if (this.rand.nextBoolean() && !EntityUtil.isEntityCoralium((EntityLivingBase)result.entityHit))
          ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 200)); 
        if (this.rand.nextInt(2) == 0)
          ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100)); 
        if (this.rand.nextInt(2) == 0)
          ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100)); 
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


