package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityEversource extends EntityTameBase implements IJumpingMount, Light {
  public float wingRotation;
  
  public float destPos;
  
  public float oFlapSpeed;
  
  public float oFlap;
  
  public float wingRotDelta = 1.0F;
  
  public int timeUntilNextEgg;
  
  public boolean chickenJockey;
  
  protected float jumpPower;
  
  public EntityEversource(World worldIn) {
    super(worldIn);
    this.timeUntilNextEgg = 200 + this.rand.nextInt(600);
    setPathPriority(PathNodeType.WATER, 0.0F);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 16.0F, 8.0F));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.5D, 80));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.experienceValue = 50;
    setSize(0.4F, 0.7F);
  }
  
  public String getDescName() {
    return "eversource";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityEversource(this.world);
  }
  
  public float getEyeHeight() {
    return this.height * 0.95F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
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
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (!this.world.isRemote && isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && !false && getDistanceSq((Entity)getAttackTarget()) < (this.width * this.width + (getAttackTarget()).width * (getAttackTarget()).width) + 9.0D)
      attackEntityAsMob((Entity)getAttackTarget()); 
    if (getControllingPassenger() != null && getControllingPassenger() instanceof EntityZombie) {
      EntityZombie passenger = (EntityZombie)getControllingPassenger();
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = passenger.rotationYawHead;
      this.rotationPitch = passenger.rotationPitch;
      if (passenger.getAttackTarget() != null)
        getNavigator().tryMoveToEntityLiving((Entity)passenger.getAttackTarget(), 1.0D); 
    } 
    this.oFlap = this.wingRotation;
    this.oFlapSpeed = this.destPos;
    this.destPos = (float)(this.destPos + (this.onGround ? -1 : 4) * 0.3D);
    this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);
    if (!this.onGround && this.wingRotDelta < 1.0F)
      this.wingRotDelta = 1.0F; 
    this.wingRotDelta = (float)(this.wingRotDelta * 0.9D);
    if (!this.onGround && this.motionY < 0.0D && isEntityAlive())
      this.motionY *= 0.6D; 
    this.wingRotation += this.wingRotDelta * 2.0F;
    if (!this.world.isRemote && !isChild() && !isChickenJockey() && --this.timeUntilNextEgg <= 0) {
      playSound(ESound.createMob, getSoundVolume(), 1.0F);
      playSound(SoundEvents.ENTITY_CHICKEN_EGG, getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      dropItem(randomSpawnItem(), 1);
      this.timeUntilNextEgg = isHero() ? (100 + this.rand.nextInt(100)) : (200 + this.rand.nextInt(600));
    } 
  }
  
  private Item randomSpawnItem() {
    switch (this.rand.nextInt(42)) {
      default:
        return (Item)EItem.chickenItem;
      case 1:
        return (Item)EItem.batItem;
      case 2:
        return (Item)EItem.cowItem;
      case 3:
        return (Item)EItem.mooshroomItem;
      case 4:
        return (Item)EItem.pigItem;
      case 5:
        return (Item)EItem.rabbitItem;
      case 6:
        return (Item)EItem.sheepItem;
      case 7:
        return (Item)EItem.ozelotItem;
      case 8:
        return (Item)EItem.squidItem;
      case 9:
        return (Item)EItem.llamaItem;
      case 10:
        return (Item)EItem.villagerItem;
      case 11:
        return (Item)EItem.snowmanItem;
      case 12:
        return (Item)EItem.silverfishItem;
      case 13:
        return (Item)EItem.endermiteItem;
      case 14:
        return (Item)EItem.wolfItem;
      case 15:
        return (Item)EItem.spiderItem;
      case 16:
        return (Item)EItem.zombieItem;
      case 17:
        return (Item)EItem.skeletonItem;
      case 18:
        return (Item)EItem.polarBearItem;
      case 19:
        return (Item)EItem.slimeItem;
      case 20:
        return (Item)EItem.magmacubeItem;
      case 21:
        return (Item)EItem.vexItem;
      case 22:
        return (Item)EItem.spiderjockeyItem;
      case 23:
        return (Item)EItem.chickenjockeyItem;
      case 24:
        return (Item)EItem.blazeItem;
      case 25:
        return (Item)EItem.endermanItem;
      case 26:
        return (Item)EItem.cavespiderItem;
      case 27:
        return (Item)EItem.pigzombieItem;
      case 28:
        return (Item)EItem.guardianItem;
      case 29:
        return (Item)EItem.ghastItem;
      case 30:
        return (Item)EItem.huskItem;
      case 31:
        return (Item)EItem.shulkerItem;
      case 32:
        return (Item)EItem.strayItem;
      case 33:
        return (Item)EItem.witchItem;
      case 34:
        return (Item)EItem.vindicatorItem;
      case 35:
        return (Item)EItem.witherskeletonItem;
      case 36:
        return (Item)EItem.killerrabbitItem;
      case 37:
        return (Item)EItem.elderguardianItem;
      case 38:
        return (Item)EItem.the4horsemenItem;
      case 39:
        return (Item)EItem.evokerItem;
      case 40:
        return (Item)EItem.giantItem;
      case 41:
        break;
    } 
    return (Item)EItem.villagergolemItem;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_CHICKEN_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_CHICKEN_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_CHICKEN_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F / getFittness());
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_CHICKEN;
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    this.chickenJockey = tagCompund.getBoolean("IsChickenJockey");
    if (tagCompund.hasKey("EggLayTime"))
      this.timeUntilNextEgg = tagCompund.getInteger("EggLayTime"); 
  }
  
  protected int getExperiencePoints(EntityPlayer player) {
    return isChickenJockey() ? 10 : super.getExperiencePoints(player);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("IsChickenJockey", this.chickenJockey);
    tagCompound.setInteger("EggLayTime", this.timeUntilNextEgg);
  }
  
  public double getMountedYOffset() {
    return this.height * 0.65D;
  }
  
  public void updatePassenger(Entity passenger) {
    super.updatePassenger(passenger);
    float f = MathHelper.sin(this.renderYawOffset * 0.017453292F);
    float f1 = MathHelper.cos(this.renderYawOffset * 0.017453292F);
    float f2 = 0.1F;
    float f3 = 0.0F;
    passenger.setPosition(this.posX + (f2 * f), this.posY + getMountedYOffset() + passenger.getYOffset() + f3, this.posZ - (f2 * f1));
    if (passenger instanceof EntityLivingBase)
      ((EntityLivingBase)passenger).renderYawOffset = this.renderYawOffset; 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
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
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      this.stepHeight = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = this.jumpPower * getFittness();
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
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
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
  
  public boolean isChickenJockey() {
    return this.chickenJockey;
  }
  
  public void setChickenJockey(boolean jockey) {
    this.chickenJockey = jockey;
  }
}

