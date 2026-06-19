package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLlama extends EntityTameBase implements IRangedAttackMob, Light, Animal {
  private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.createKey(EntityLlama.class, DataSerializers.VARINT);
  
  public EntityLlama(World worldIn) {
    super(worldIn);
    setSize(0.9F, 1.87F);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 6.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 1.2D, 20, 50, 12.0F));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.experienceValue = 3;
  }
  
  public String getDescName() {
    return "llama";
  }
  
  public int getNextLevelRequirement() {
    return 10;
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock) ? 10.0F : (this.world.getLightBrightness(pos) - 0.5F);
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityLlama(this.world);
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setInteger("Variant", getVariant());
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    setStrength(compound.getInteger("Strength"));
    super.readEntityFromNBT(compound);
    setVariant(compound.getInteger("Variant"));
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((15.0F + this.rand.nextInt(8) + this.rand.nextInt(9)));
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(DATA_COLOR_ID, -1);
    this.dataManager.register(DATA_VARIANT_ID, 0);
  }
  
  public int getVariant() {
    return MathHelper.clamp((Integer) this.dataManager.get(DATA_VARIANT_ID), 0, 3);
  }
  
  public void setVariant(int variantIn) {
    this.dataManager.set(DATA_VARIANT_ID, variantIn);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      float f = MathHelper.cos(this.renderYawOffset * 0.017453292F);
      float f1 = MathHelper.sin(this.renderYawOffset * 0.017453292F);
      float f2 = 0.3F;
      passenger.setPosition(this.posX + (0.3F * f1), this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ - (0.3F * f));
    } 
  }
  
  public double getMountedYOffset() {
    return this.height * 0.67D;
  }
  
  protected boolean handleEating(EntityPlayer player, ItemStack stack) {
    int i = 0;
    int j = 0;
    float f = 0.0F;
    boolean flag = false;
    Item item = stack.getItem();
    if (item == Items.WHEAT) {
      i = 10;
      j = 3;
      f = 2.0F;
    } 
    if (getHealth() < getMaxHealth() && f > 0.0F) {
      heal(f);
      flag = true;
    } 
    if (flag && !isSilent())
      this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_EAT, getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F); 
    return flag;
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    int i;
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    if (livingdata instanceof GroupData) {
      i = ((GroupData)livingdata).variant;
    } else {
      i = this.rand.nextInt(4);
      livingdata = new GroupData(i);
    } 
    setVariant(i);
    return livingdata;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean hasColor() {
    return (getColor() != null);
  }
  
  protected SoundEvent getAngrySound() {
    return SoundEvents.ENTITY_LLAMA_ANGRY;
  }
  
  protected SoundEvent getAmbientSound() {
    return (getAttackTarget() != null || this.rand.nextInt(5) == 0) ? SoundEvents.ENTITY_LLAMA_ANGRY : SoundEvents.ENTITY_LLAMA_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_LLAMA_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_LLAMA_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F / getFittness());
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_LLAMA;
  }
  
  public void makeMad() {
    SoundEvent soundevent = getAngrySound();
    if (soundevent != null)
      playSound(soundevent, getSoundVolume(), getSoundPitch()); 
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
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.rotationYawHead = entitylivingbase.rotationYawHead;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (forward != 0.0F) {
        this.rotationYaw = this.renderYawOffset = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      } 
      if (isInWater() || isInLava())
        this.motionY += 0.05D; 
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * ((isInWater() || isInLava()) ? 20.0F : 1.0F));
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (entitylivingbase.moveForward > 0.0F && this.ticksExisted % 7 == 0)
        playStepSound(new BlockPos((Entity)this), this.world.getBlockState(new BlockPos((Entity)this)).getBlock()); 
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double d1 = this.posX - this.prevPosX;
      double d0 = this.posZ - this.prevPosZ;
      float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 2.0F;
      if (f2 > 1.0F)
        f2 = 1.0F; 
      this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.6F;
      this.limbSwing += this.limbSwingAmount * 0.25F;
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
  
  private void setColor(@Nullable EnumDyeColor color) {
    this.dataManager.set(DATA_COLOR_ID, (color == null) ? -1 : color.getMetadata());
  }
  
  @Nullable
  public EnumDyeColor getColor() {
    int i = (Integer) this.dataManager.get(DATA_COLOR_ID);
    return (i == -1) ? null : EnumDyeColor.byMetadata(i);
  }
  
  public int getMaxTemper() {
    return 30;
  }
  
  private void spit(EntityLivingBase target) {
    EntityLlamaSpitOther entityllamaspit = new EntityLlamaSpitOther(this.world, this);
    double d0 = target.posX - this.posX;
    double d1 = (target.getEntityBoundingBox()).minY - 1.25D - entityllamaspit.posY;
    double d2 = target.posZ - this.posZ;
    float f = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.25F;
    entityllamaspit.shoot(d0, d1 + f, d2, 2.0F, 4.0F);
    this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_LLAMA_SPIT, getSoundCategory(), getSoundVolume(), getSoundPitch());
    this.world.spawnEntity((Entity)entityllamaspit);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    if (getDistance((Entity)target) <= (target.width + this.width)) {
      attackEntityAsMob((Entity)target);
      getNavigator().tryMoveToEntityLiving((Entity)target, 1.2D);
    } else {
      spit(target);
    } 
  }
  
  static class GroupData implements IEntityLivingData {
    public int variant;
    
    private GroupData(int variantIn) {
      this.variant = variantIn;
    }
  }
}

