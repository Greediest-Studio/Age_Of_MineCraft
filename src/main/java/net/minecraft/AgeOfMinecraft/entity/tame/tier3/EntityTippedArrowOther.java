package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityTippedArrowOther extends EntityTippedArrow {
  EntityLivingBase shooter;
  
  public EntityTippedArrowOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityTippedArrowOther(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  public EntityTippedArrowOther(World worldIn, EntityLivingBase shooter) {
    super(worldIn, shooter);
    this.shooter = shooter;
  }
  
  protected void onHit(RayTraceResult p_184549_1_) {
    Entity entity = p_184549_1_.entityHit;
    if (!this.world.isRemote && this.shooter != null && entity != null && entity instanceof EntityLivingBase && entity != ((EntityTameBase)this.shooter).getOwner()) {
      DamageSource damagesource;
      float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
      int i = MathHelper.ceil(f * getDamage());
      if (getIsCritical())
        i += this.rand.nextInt(i / 2 + 2); 
      if (this.shootingEntity == null) {
        damagesource = DamageSource.causeArrowDamage((EntityArrow)this, (Entity)this);
      } else {
        damagesource = DamageSource.causeArrowDamage((EntityArrow)this, (Entity)this.shooter);
      } 
      if (entity instanceof EntityLivingBase && this.shooter != null && entity.isEntityAlive() && this.shooter instanceof EntityTameBase) {
        ((EntityTameBase)this.shooter).inflictEngenderMobDamage((EntityLivingBase)entity, " was shot by ", damagesource, i);
        arrowHit((EntityLivingBase)entity);
        if (this.shooter instanceof EntitySkeleton && (entity instanceof net.minecraft.entity.monster.EntityCreeper || entity instanceof EntityCreeper) && !entity.isEntityAlive()) {
          int i1 = Item.getIdFromItem(Items.RECORD_13);
          int j = Item.getIdFromItem(Items.RECORD_WAIT);
          int k = i1 + this.rand.nextInt(j - i1 + 1);
          entity.dropItem(Item.getItemById(k), 1);
        } 
        if (((EntityTameBase)this.shooter).isHero() && getDamage() > 0.0D) {
          setDamage(getDamage() - 0.5D);
        } else {
          setDead();
        } 
      } 
    } else if (!this.world.isRemote && entity == null && isEntityInsideOpaqueBlock()) {
      playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
      setDead();
    } 
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (isEntityInsideOpaqueBlock())
      for (int u = 0; u < 3.0D + getDamage(); u++) {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY - 0.20000000298023224D);
        int k = MathHelper.floor(this.posZ);
        IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
        if (iblockstate.getMaterial() != Material.AIR)
          this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, (getEntityBoundingBox()).minY + 0.3D, this.posZ, -this.motionX, 2.0D, -this.motionZ, Block.getStateId(iblockstate));
      }  
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(2.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.shooter != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.isEntityAlive())
          onHit(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
