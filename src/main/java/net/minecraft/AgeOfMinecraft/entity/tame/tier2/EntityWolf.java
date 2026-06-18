package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICustomLeapAttack;
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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWolf extends EntityTameBase implements IJumpingMount, Light, Animal {
  protected static final DataParameter<Byte> ANGRY = EntityDataManager.createKey(EntityWolf.class, DataSerializers.BYTE);
  
  private float headRotationCourse;
  
  private float headRotationCourseOld;
  
  private boolean isWet;
  
  private boolean isShaking;
  
  private float timeWolfIsShaking;
  
  private float prevTimeWolfIsShaking;
  
  protected float jumpPower;
  
  public EntityWolf(World worldIn) {
    super(worldIn);
    setSize(0.6F, 0.8F);
    this.isOffensive = true;
    this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 15.0F, 4.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAICustomLeapAttack((EntityLiving)this, 0.4F, 0.75F, 0.8F, 0.5F, 4.0D, 24.0D, 3));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.experienceValue = 3;
  }
  
  public String getDescName() {
    return "wolf";
  }
  
  public int getNextLevelRequirement() {
    return 15;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityWolf(this.world);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock) ? 10.0F : (this.world.getLightBrightness(pos) - 0.5F);
  }
  
  public void setAttackTarget(EntityLivingBase p_70624_1_) {
    super.setAttackTarget(p_70624_1_);
    setAngry(true);
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (getAttackTarget() != null) {
      setAngry(true);
    } else {
      setAngry(false);
    } 
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F / getFittness());
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(ANGRY, Byte.valueOf((byte)0));
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("Angry", isAngry());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setAngry(tagCompund.getBoolean("Angry"));
  }
  
  protected SoundEvent getAmbientSound() {
    return isAngry() ? SoundEvents.ENTITY_WOLF_GROWL : SoundEvents.ENTITY_WOLF_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_WOLF_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_WOLF_DEATH;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_WOLF;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (!this.world.isRemote && this.isWet && !this.isShaking && !hasPath() && this.onGround) {
      this.isShaking = true;
      this.timeWolfIsShaking = 0.0F;
      this.prevTimeWolfIsShaking = 0.0F;
      this.world.setEntityState((Entity)this, (byte)8);
    } 
    if (!this.world.isRemote && getAttackTarget() == null && isAngry())
      setAngry(false); 
  }
  
  public void onUpdate() {
    super.onUpdate();
    this.headRotationCourseOld = this.headRotationCourse;
    this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
    if (isWet()) {
      this.isWet = true;
      this.isShaking = false;
      this.timeWolfIsShaking = 0.0F;
      this.prevTimeWolfIsShaking = 0.0F;
    } else if ((this.isWet || this.isShaking) && this.isShaking) {
      if (this.timeWolfIsShaking == 0.0F)
        playSound(SoundEvents.ENTITY_WOLF_SHAKE, getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F); 
      this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
      this.timeWolfIsShaking += 0.05F;
      if (this.prevTimeWolfIsShaking >= 2.0F) {
        this.isWet = false;
        this.isShaking = false;
        this.prevTimeWolfIsShaking = 0.0F;
        this.timeWolfIsShaking = 0.0F;
      } 
      if (this.timeWolfIsShaking > 0.4F) {
        float f = (float)(getEntityBoundingBox()).minY;
        int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);
        for (int j = 0; j < i; j++) {
          float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
          float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
          this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f1, (f + 0.8F), this.posZ + f2, this.motionX, this.motionY, this.motionZ, new int[0]);
        } 
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isWolfWet() {
    return this.isWet;
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadingWhileWet(float p_70915_1_) {
    return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
    float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;
    if (f2 < 0.0F) {
      f2 = 0.0F;
    } else if (f2 > 1.0F) {
      f2 = 1.0F;
    } 
    return MathHelper.sin(f2 * 3.1415927F) * MathHelper.sin(f2 * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getInterestedAngle(float p_70917_1_) {
    return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * 3.1415927F;
  }
  
  public float getEyeHeight() {
    return this.height * 0.8F;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() instanceof ItemFood) {
      ItemFood itemfood = (ItemFood)stack.getItem();
      if (itemfood.isWolfsFavoriteMeat() && getHealth() < getMaxHealth()) {
        stack.shrink(1);
        heal(itemfood.getHealAmount(stack));
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
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 8) {
      this.isShaking = true;
      this.timeWolfIsShaking = 0.0F;
      this.prevTimeWolfIsShaking = 0.0F;
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public float getTailRotation() {
    return isAngry() ? 1.5393804F : 0.62831855F;
  }
  
  public boolean isAngry() {
    return ((((Byte)this.dataManager.get(ANGRY)).byteValue() & 0x2) != 0);
  }
  
  public void setAngry(boolean angry) {
    byte b0 = ((Byte)this.dataManager.get(ANGRY)).byteValue();
    if (angry) {
      this.dataManager.set(ANGRY, Byte.valueOf((byte)(b0 | 0x2)));
    } else {
      this.dataManager.set(ANGRY, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
    } 
  }
  
  public void setJumpPower(int jumpPowerIn) {
    if (isBeingRidden()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 1.5F);
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
    EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
    if (isBeingRidden() && canBeSteered()) {
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      this.jumpMovementFactor = 0.05F;
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = 0.6D * this.jumpPower;
        if (isPotionActive(MobEffects.JUMP_BOOST))
          this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
        this.isAirBorne = true;
        float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
        float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
        this.motionX = (-2.0F * f * this.jumpPower);
        this.motionZ = (2.0F * f1 * this.jumpPower);
        this.jumpPower = 0.0F;
        ForgeHooks.onLivingJump((EntityLivingBase)this);
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
      this.jumpMovementFactor = 0.02F;
      super.travel(strafe, vertical, forward);
    } 
  }
}

