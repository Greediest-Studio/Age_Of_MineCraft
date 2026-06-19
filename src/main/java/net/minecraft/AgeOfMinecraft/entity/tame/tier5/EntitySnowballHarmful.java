package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySnowballHarmful extends EntitySnowball {
  public float damage = 0.0F;
  
  public EntitySnowballHarmful(World worldIn) {
    super(worldIn);
  }
  
  public EntitySnowballHarmful(World worldIn, EntityLivingBase throwerIn) {
    super(worldIn, throwerIn);
  }
  
  public EntitySnowballHarmful(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 3)
      for (int i = 0; i < 16; i++)
        this.world.spawnParticle(EnumParticleTypes.SNOWBALL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
  }
  
  protected void onImpact(RayTraceResult result) {
    if (!this.world.isRemote && getThrower() == null) {
      setDead();
      return;
    } 
    if (!this.world.isRemote && result.entityHit != null)
      if ((result.entityHit instanceof EntityLivingBase && getThrower() != null && getThrower() instanceof EntityTameBase && !false) || (result.entityHit instanceof EntityTameBase && false && ((EntityTameBase)getThrower()).getFakeHealth() > 0.0F)) {
        result.entityHit.hurtResistantTime = 0;
        ((EntityTameBase)getThrower()).inflictEngenderMobDamage((EntityLivingBase)result.entityHit, " was pummeled by ", DamageSource.causeThrownDamage(this, getThrower()), this.damage);
        this.world.setEntityState(this, (byte)3);
        setDead();
      }  
    if (!this.world.isRemote && result.entityHit == null) {
      this.world.setEntityState(this, (byte)3);
      setDead();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (getThrower() != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive() && !false)
          onImpact(new RayTraceResult(entity1));
      }  
  }
}


