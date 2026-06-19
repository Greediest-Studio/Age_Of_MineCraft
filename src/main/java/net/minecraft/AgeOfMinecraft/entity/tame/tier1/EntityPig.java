package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
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
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityPig extends EntityTameBase implements IJumpingMount, Light, Animal {
  private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(EntityPig.class, DataSerializers.BOOLEAN);
  
  protected float jumpPower;
  
  public EntityPig(World worldIn) {
    super(worldIn);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.2D, 16.0F, 4.0F));
    this.tasks.addTask(2, new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.8D, 80));
    this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    this.experienceValue = 1;
    setSize(0.9F, 0.9F);
  }
  
  public String getDescName() {
    return getSaddled() ? "pig_saddled" : "pig";
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityPig(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
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
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock) ? 10.0F : (this.world.getLightBrightness(pos) - 0.5F);
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(400);
    playSound(ESound.pigSpecial, 5.0F, getSoundPitch());
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(32.0D, 32.0D, 32.0D), Predicates.and(EntitySelectors.IS_ALIVE));
    if (list != null && !list.isEmpty())
        for (EntityLivingBase entity : list) {
            if (entity != null)
                if (!false) {
                    entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
                    entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 200, 0));
                    entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 0));
                }
        }
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(SADDLED, Boolean.FALSE);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("Saddle", getSaddled());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setSaddled(tagCompund.getBoolean("Saddle"));
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_PIG_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_PIG_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_PIG_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F / getFittness());
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() == Items.SADDLE && hasOwner(player)) {
      setSaddled(true);
      playSound(SoundEvents.ENTITY_PIG_SADDLE, 0.5F, 1.0F);
      stack.shrink(1);
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  public void onDeath(DamageSource cause) {
    super.onDeath(cause);
    if (!this.world.isRemote)
      if (getSaddled()) {
        dropItem(Items.SADDLE, 1);
        setSaddled(false);
      }  
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_PIG;
  }
  
  public boolean getSaddled() {
    return this.dataManager.get(SADDLED);
  }
  
  public void setSaddled(boolean saddled) {
    if (saddled) {
      this.dataManager.set(SADDLED, Boolean.TRUE);
    } else {
      this.dataManager.set(SADDLED, Boolean.FALSE);
    } 
  }
  
  public void onStruckByLightning(EntityLightningBolt lightningBolt) {
    if (!this.world.isRemote && !this.isDead) {
      EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
      entitypigzombie.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
      entitypigzombie.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      entitypigzombie.setNoAI(isAIDisabled());
      if (hasCustomName())
        entitypigzombie.setCustomNameTag(getCustomNameTag()); 
      if (!isWild())
        entitypigzombie.setOwnerId(getOwnerId()); 
      this.world.spawnEntity(entitypigzombie);
      if (isBeingRidden())
        getControllingPassenger().startRiding(entitypigzombie);
      setDead();
    } 
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
      this.rotationYawHead = entitylivingbase.rotationYawHead;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (forward != 0.0F) {
        this.rotationYaw = this.renderYawOffset = this.rotationYawHead;
        this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      } 
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (getSaddled() ? 1.5F : 1.0F));
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = 0.7D * this.jumpPower * getFittness();
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
}

