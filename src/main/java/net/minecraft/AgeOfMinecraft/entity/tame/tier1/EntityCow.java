package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityCow extends EntityTameBase implements IJumpingMount, Light, Animal {
  protected float jumpPower;
  
  public EntityCow(World worldIn) {
    super(worldIn);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.0D, 15.0F, 4.0F));
    this.tasks.addTask(2, new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.5D, 80));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.experienceValue = 3;
    setSize(0.9F, 1.4F);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
  }
  
  public String getDescName() {
    return "cow";
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSFlying() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public float getBonusVSTiny() {
    return 1.25F;
  }
  
  public float getBonusVSStructure() {
    return 0.1F;
  }
  
  public float getBonusVSElemental() {
    return 1.0F;
  }
  
  public float getBonusVSUndead() {
    return 0.5F;
  }
  
  public float getBonusVSEnder() {
    return 0.25F;
  }
  
  public float getBonusVSAnimal() {
    return 1.5F;
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityCow(this.world);
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock) ? 10.0F : (this.world.getLightBrightness(pos) - 0.5F);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_COW_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_COW_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_COW_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F / getFittness());
  }
  
  protected float getSoundVolume() {
    return 0.4F;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_COW;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() == Items.BUCKET && hasOwner(player)) {
      player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
      stack.shrink(1);
      if (stack.isEmpty()) {
        player.setHeldItem(hand, new ItemStack(Items.MILK_BUCKET));
      } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.MILK_BUCKET))) {
        player.dropItem(new ItemStack(Items.MILK_BUCKET), false);
      } 
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void setJumpPower(int jumpPowerIn) {
    if (isBeingRidden()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  public boolean canJump() {
    return true;
  }
  
  public void handleStartJump(int p_184775_1_) {
    playLivingSound();
  }
  
  public void handleStopJump() {}
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
  }
  
  public double getMountedYOffset() {
    return this.height * 0.825D;
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      this.stepHeight = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.rotationYawHead = entitylivingbase.rotationYawHead;
      this.rotationPitch = 90.0F;
      setRotation(this.rotationYaw, this.rotationPitch);
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (forward != 0.0F) {
        this.rotationYaw = this.renderYawOffset = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      } 
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = 0.8D * this.jumpPower * getFittness();
        if (isPotionActive(MobEffects.JUMP_BOOST))
          this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
        this.isAirBorne = true;
        if (forward > 0.0F) {
          float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
          float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
          this.motionX += (-0.4F * f * this.jumpPower);
          this.motionZ += (0.4F * f1 * this.jumpPower);
        } 
        this.jumpPower = 0.0F;
      } 
      if (forward > 0.0F) {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(2.0D, 1.0D, 2.0D));
          for (Entity entity : list) {
              if (entity instanceof EntityLivingBase && !false) {
                  attackEntityAsMob(entity);
                  double d01 = ((getEntityBoundingBox()).minX + (getEntityBoundingBox()).maxX) / 2.0D;
                  double d11 = ((getEntityBoundingBox()).minZ + (getEntityBoundingBox()).maxZ) / 2.0D;
                  double d2 = entity.posX - d01;
                  double d3 = entity.posZ - d11;
                  double d4 = d2 * d2 + d3 * d3;
                  entity.addVelocity(d2 / d4 * 3.0D, 0.1D, d3 / d4 * 3.0D);
                  if (getDistanceSq(entity) < 9.0D || !entity.isEntityAlive())
                      entity.addVelocity(d2 / d4 * 3.0D, 0.1D, d3 / d4 * 3.0D);
              }
          }
        if (this.moralRaisedTimer > 100) {
          List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D));
            for (Entity entity : list1) {
                if (entity instanceof EntityLivingBase && !false) {
                    attackEntityAsMob(entity);
                    double d01 = ((getEntityBoundingBox()).minX + (getEntityBoundingBox()).maxX) / 2.0D;
                    double d11 = ((getEntityBoundingBox()).minZ + (getEntityBoundingBox()).maxZ) / 2.0D;
                    double d2 = entity.posX - d01;
                    double d3 = entity.posZ - d11;
                    double d4 = d2 * d2 + d3 * d3;
                    entity.addVelocity(d2 / d4 * 3.0D * getFittness(), 0.15D, d3 / d4 * 3.0D * getFittness());
                    if (getDistanceSq(entity) < 9.0D || !entity.isEntityAlive())
                        entity.addVelocity(d2 / d4 * 3.0D * getFittness(), 0.15D, d3 / d4 * 3.0D * getFittness());
                }
            }
        } 
      } 
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double d5 = this.posX - this.prevPosX;
      double d7 = this.posZ - this.prevPosZ;
      float f10 = MathHelper.sqrt(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.limbSwingAmount += (f10 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public float getEyeHeight() {
    return this.height;
  }
}

