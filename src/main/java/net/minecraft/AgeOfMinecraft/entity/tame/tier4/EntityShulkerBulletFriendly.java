package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShulkerBulletFriendly extends EntityShulkerBullet {
  private EntityTameBase owner;
  
  private Entity target;
  
  public EntityShulkerBulletFriendly(World worldIn) {
    super(worldIn);
  }
  
  public SoundCategory getSoundCategory() {
    return SoundCategory.MASTER;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityShulkerBulletFriendly(World worldIn, double x, double y, double z, double motionXIn, double motionYIn, double motionZIn) {
    super(worldIn, x, y, z, motionXIn, motionYIn, motionZIn);
  }
  
  public EntityShulkerBulletFriendly(World worldIn, EntityTameBase p_i46772_2_, Entity p_i46772_3_, EnumFacing.Axis p_i46772_4_) {
    super(worldIn, (EntityLivingBase)p_i46772_2_, p_i46772_3_, p_i46772_4_);
    this.owner = p_i46772_2_;
    this.target = p_i46772_3_;
  }
  
  public void setDead() {
    if (!this.owner.getHeldItemMainhand().isEmpty()) {
      EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(this.world, this.posX, this.posY, this.posZ, this.owner.getHeldItemMainhand());
      ((WorldServer)this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 2, 0.2D, 0.2D, 0.2D, 0.0D);
      playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
      if (!this.world.isRemote)
        this.world.spawnEntity((Entity)entityfireworkrocket); 
      if (this.target != null && this.target instanceof EntityLivingBase && ((EntityLivingBase)this.target).getHealth() <= 5.0F)
        this.target.startRiding((Entity)entityfireworkrocket); 
    } 
    super.setDead();
  }
  
  public boolean isImmuneToExplosions() {
    return true;
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (this.owner != null && this.owner.moralRaisedTimer > 200) {
      this.motionX *= 1.100000023841858D;
      this.motionY *= 1.100000023841858D;
      this.motionZ *= 1.100000023841858D;
    } 
    if (this.target == null) {
      ((WorldServer)this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 2, 0.2D, 0.2D, 0.2D, 0.0D);
      playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
      if (!this.world.isRemote)
        setDead(); 
    } else {
      if (!this.target.isEntityAlive())
        setDead(); 
      if (this.target instanceof net.minecraft.entity.IEntityMultiPart && this.target instanceof EntityLivingBase) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
        if (!list.isEmpty())
          for (EntityLivingBase entity1 : list) {
            if (entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive()) {
              this.owner.attackEntityAsMob(this.target);
              applyEnchantments((EntityLivingBase)this.owner, this.target);
              playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
              if (!this.world.isRemote)
                setDead(); 
            } 
          }  
      } 
    } 
  }
  
  protected void bulletHit(RayTraceResult result) {
    if (result.entityHit != null && this.target != null && result.entityHit == this.target && result.entityHit instanceof EntityLivingBase)
      if ((!false || (this.owner instanceof EntityShulker && ((EntityShulker)this.owner).isPositiveShulker() && false)) && this.owner != null) {
        this.owner.inflictEngenderMobDamage((EntityLivingBase)result.entityHit, " was shot by ", DamageSource.causeThrownDamage((Entity)this, (Entity)this.owner), (float)this.owner.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
        ((EntityShulker)this.owner).inflictShulkerEffects((EntityLivingBase)result.entityHit);
        applyEnchantments((EntityLivingBase)this.owner, result.entityHit);
        ((WorldServer)this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 2, 0.2D, 0.2D, 0.2D, 0.0D);
        playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
        if (!this.world.isRemote)
          setDead(); 
      }  
  }
}


