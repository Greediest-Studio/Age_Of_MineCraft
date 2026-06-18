package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPotionOther extends EntityPotion {
  public EntityPotionOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityPotionOther(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
    super(worldIn, throwerIn, potionDamageIn);
  }
  
  public EntityPotionOther(World worldIn, double x, double y, double z, ItemStack potionDamageIn) {
    super(worldIn, x, y, z, potionDamageIn);
  }
  
  protected void onImpact(RayTraceResult result) {
    if (!this.world.isRemote && getThrower() == null) {
      setDead();
      return;
    } 
    if (!this.world.isRemote && getThrower() != null && getThrower() instanceof EntityTameBase) {
      ItemStack itemstack = getPotion();
      PotionType potiontype = PotionUtils.getPotionFromItem(itemstack);
      List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemstack);
      boolean flag = (potiontype == PotionTypes.WATER && list.isEmpty());
      if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
        BlockPos blockpos = result.getBlockPos().offset(result.sideHit);
        extinguishFires(blockpos);
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
          extinguishFires(blockpos.offset(enumfacing)); 
      } 
      applyWater();
      if (result.entityHit != null) {
        if (result.entityHit instanceof EntityLivingBase && !false) {
          if (!list.isEmpty())
            if (isLingering()) {
              makeAreaOfEffectCloud(itemstack, potiontype);
            } else {
              applySplash(result, list);
            }  
          int i = potiontype.hasInstantEffect() ? 2007 : 2002;
          this.world.playEvent(i, new BlockPos((Entity)this), PotionUtils.getColor(itemstack));
          setDead();
        } 
      } else if (result.entityHit == null) {
        if (!list.isEmpty())
          if (isLingering()) {
            makeAreaOfEffectCloud(itemstack, potiontype);
          } else {
            applySplash(result, list);
          }  
        int i = potiontype.hasInstantEffect() ? 2007 : 2002;
        this.world.playEvent(i, new BlockPos((Entity)this), PotionUtils.getColor(itemstack));
        setDead();
      } 
    } 
  }
  
  private void applyWater() {
    AxisAlignedBB axisalignedbb = getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, WATER_SENSITIVE);
    if (!list.isEmpty())
      for (EntityLivingBase entitylivingbase : list) {
        double d0 = getDistanceSq((Entity)entitylivingbase);
        if (d0 < 16.0D && isWaterSensitiveEntity(entitylivingbase))
          entitylivingbase.attackEntityFrom(DamageSource.DROWN, 1.0F); 
      }  
  }
  
  private void applySplash(RayTraceResult p_190543_1_, List<PotionEffect> p_190543_2_) {
    AxisAlignedBB axisalignedbb = getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
    if (!list.isEmpty())
      for (EntityLivingBase entitylivingbase : list) {
        if (entitylivingbase.canBeHitWithPotion()) {
          double d0 = getDistanceSq((Entity)entitylivingbase);
          if (d0 < 16.0D) {
            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
            if (entitylivingbase == p_190543_1_.entityHit) {
              copyLocationAndAnglesFrom((Entity)entitylivingbase);
              d1 = 1.0D;
            } 
            if (false) {
              for (PotionEffect potioneffect : p_190543_2_) {
                if (potioneffect.getPotion().isInstant()) {
                  potioneffect.getPotion().affectEntity((Entity)this, (Entity)getThrower(), entitylivingbase, potioneffect.getAmplifier(), 0.5D);
                  continue;
                } 
                if (!potioneffect.getPotion().isBadEffect())
                  entitylivingbase.addPotionEffect(new PotionEffect(potioneffect)); 
              } 
              continue;
            } 
            for (PotionEffect potioneffect : p_190543_2_) {
              if (potioneffect.getPotion().isInstant()) {
                ((EntityTameBase)getThrower()).inflictEngenderMobDamage(entitylivingbase, " was killed by magic created by ", (new EntityDamageSource("indirectMagic", (Entity)getThrower())).setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute(), 8.0F);
                continue;
              } 
              if (potioneffect.getPotion().isBadEffect() && entitylivingbase.isPotionApplicable(potioneffect)) {
                entitylivingbase.addPotionEffect(new PotionEffect(potioneffect));
                continue;
              } 
              ((EntityTameBase)getThrower()).inflictEngenderMobDamage(entitylivingbase, " was killed by magic created by ", (new EntityDamageSource("indirectMagic", (Entity)getThrower())).setMagicDamage().setDamageBypassesArmor().setDamageIsAbsolute(), 8.0F);
            } 
          } 
        } 
      }  
  }
  
  private void makeAreaOfEffectCloud(ItemStack p_190542_1_, PotionType p_190542_2_) {
    EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.world, this.posX, this.posY, this.posZ);
    if (getThrower() != null && getThrower() instanceof EntityTameBase)
      entityareaeffectcloud.setOwner((EntityTameBase)getThrower()); 
    entityareaeffectcloud.setRadius(3.0F);
    entityareaeffectcloud.setRadiusOnUse(-0.5F);
    entityareaeffectcloud.setWaitTime(10);
    entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
    entityareaeffectcloud.setPotion(p_190542_2_);
    for (PotionEffect potioneffect : PotionUtils.getFullEffectsFromItem(p_190542_1_))
      entityareaeffectcloud.addEffect(new PotionEffect(potioneffect)); 
    NBTTagCompound nbttagcompound = p_190542_1_.getTagCompound();
    if (nbttagcompound != null && nbttagcompound.hasKey("CustomPotionColor", 99))
      entityareaeffectcloud.setColor(nbttagcompound.getInteger("CustomPotionColor")); 
    this.world.spawnEntity((Entity)entityareaeffectcloud);
  }
  
  private boolean isLingering() {
    return (getPotion().getItem() == Items.LINGERING_POTION);
  }
  
  private void extinguishFires(BlockPos pos) {
    if (this.world.getBlockState(pos).getBlock() == Blocks.FIRE)
      this.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2); 
  }
  
  private static boolean isWaterSensitiveEntity(EntityLivingBase p_190544_0_) {
    return (p_190544_0_ instanceof net.minecraft.entity.monster.EntityEnderman || p_190544_0_ instanceof net.minecraft.entity.monster.EntityBlaze || p_190544_0_ instanceof EntityBlaze || p_190544_0_ instanceof EntityEnderman);
  }
}


