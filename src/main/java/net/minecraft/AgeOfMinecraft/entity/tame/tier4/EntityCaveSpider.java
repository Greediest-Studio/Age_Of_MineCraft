package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCaveSpider extends EntitySpider {
  public EntityCaveSpider(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.35F, 1.365F);
    } else {
      setSize(0.8F, 0.475F);
    } 
    this.experienceValue = 2;
  }
  
  public String getDescName() {
    return "spider_cave";
  }
  
  public int getNextLevelRequirement() {
    return 30;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.1F) : super.getSoundPitch();
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityCaveSpider(this.world);
  }
  
  public int timesToConvert() {
    return 17;
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    if (super.attackEntityAsMob(p_70652_1_)) {
      if (p_70652_1_ instanceof EntityLivingBase) {
        byte b0 = 7;
        if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
          b0 = 15;
        } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
          b0 = 21;
        } 
        if (b0 > 0)
          ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(MobEffects.POISON, b0 * 20, 1)); 
      } 
      return true;
    } 
    return false;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean isNotColliding() {
    if (this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox())) {
      BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
      if (this.world.canBlockSeeSky(blockpos.up()))
        return false; 
      IBlockState iblockstate = this.world.getBlockState(blockpos.down());
      Block block = iblockstate.getBlock();
      if (block == Blocks.PLANKS)
        return true; 
    } 
    return false;
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.84F) : (this.height * 0.74F);
  }
  
  public void updateRiderPosition() {
    Entity entity = getControllingPassenger();
    if (entity != null) {
      double d8 = -0.13D;
      Vec3d vec3 = getLook(1.0F);
      double dx = vec3.x * d8;
      double dz = vec3.z * d8;
      entity.setPosition(this.posX + dx, this.posY + getMountedYOffset() + entity.getYOffset(), this.posZ + dz);
    } 
  }
  
  public double getMountedYOffset() {
    return this.height * 0.5D;
  }
  
  public void travel(float strafe, float vertical, float forward) {
    this.moveForward *= 1.5F;
    this.moveStrafing *= 1.5F;
    super.travel(strafe, vertical, forward);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_CAVE_SPIDER;
  }
}
