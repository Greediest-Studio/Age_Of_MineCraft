package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPigZombie extends EntityZombie implements IJumpingMount {
  private static final DataParameter<Boolean> OLDPEPIGMAN = EntityDataManager.createKey(EntityPigZombie.class, DataSerializers.BOOLEAN);
  
  private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
  
  private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.075D, 0)).setSaved(false);
  
  private int angerLevel;
  
  private int randomSoundDelay = 40;
  
  private UUID angerTargetUUID;
  
  protected float jumpPower;
  
  public EntityPigZombie(World worldIn) {
    super(worldIn);
    this.isImmuneToFire = true;
    this.randomSoundDelay = 0;
    setToNotVillager();
  }
  
  public String getDescName() {
    return isOldPEPigman() ? "zombie_pigman_old" : "zombie_pigman";
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(OLDPEPIGMAN, Boolean.FALSE);
  }
  
  public boolean isOldPEPigman() {
    return getDataManager().get(OLDPEPIGMAN);
  }
  
  public void setOldPEPigman(boolean childZombie) {
    getDataManager().set(OLDPEPIGMAN, childZombie);
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityPigZombie(this.world);
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString(this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public boolean canBeMatedWith() {
    return (EngenderConfig.mobs.useMobTalkerModels && this.height >= 1.365F && !isChild());
  }
  
  public void createChild() {
    playSound(ESound.girlMoan, 1.0F, getSoundPitch() + 0.1F);
    int i;
    for (i = 0; i < 10; i++)
      spawnHeartParticle(); 
    if (!this.world.isRemote)
      for (i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntityPigZombie baby = new EntityPigZombie(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-60000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        baby.setToNotVillager();
        this.world.spawnEntity(baby);
      }  
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.2F) : super.getSoundPitch();
  }
  
  public void performSpecialAttack() {
    getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 5.0F);
    playSound(ESound.pigmanSpecial, getSoundVolume(), 1.0F);
    setSpecialAttackTimer(500);
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 4.0D && this.ticksExisted % 1 == 0 && this.moralRaisedTimer > 200)
      attackEntityAsMob(getAttackTarget());
    if (getAttackTarget() != null && getAttackTarget().isEntityAlive() && getDistanceSq(getAttackTarget()) < (getAttackTarget()).width + 9.0D && getSpecialAttackTimer() <= 0 && isHero()) {
      jump();
      (getAttackTarget()).motionY += 2.0D;
      playSound(ESound.pigmanSpecial, getSoundVolume(), 1.0F);
      performSpecialAttack();
    } 
  }
  
  protected void updateAITasks() {
    IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
    if (isAngry()) {
      if (!iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
        setSprinting(true);
        iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
      } 
      this.angerLevel--;
    } else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
      iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
    } 
    if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0)
      playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, 10000.0F, getSoundPitch() + 1.8F); 
    if (this.moralRaisedTimer > 200) {
      addPotionEffect(new PotionEffect(MobEffects.SPEED, 20, 9));
      this.angerLevel = 600;
      playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, 1.0F, getSoundPitch() + 1.8F);
      this.hurtResistantTime = 0;
      this.limbSwingAmount *= 6.0F;
    } 
    super.updateAITasks();
  }
  
  public boolean isNotColliding() {
    return (this.world.checkNoEntityCollision(getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox()));
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setShort("Anger", (short)this.angerLevel);
    tagCompound.setBoolean("OldPEPigman", isOldPEPigman());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    this.angerLevel = tagCompund.getShort("Anger");
    setOldPEPigman(tagCompund.getBoolean("OldPEPigman"));
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (isEntityInvulnerable(source))
      return false; 
    Entity entity = source.getTrueSource();
    if (entity instanceof EntityLivingBase)
      becomeAngryAt(entity); 
    return super.attackEntityFrom(source, amount);
  }
  
  public void becomeAngryAt(Entity p_70835_1_) {
    this.angerLevel = 600;
    this.randomSoundDelay = this.rand.nextInt(150);
    if (p_70835_1_ instanceof EntityLivingBase && !false)
      setAttackTarget((EntityLivingBase)p_70835_1_); 
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
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof net.minecraft.entity.player.EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = 0.8D * this.jumpPower;
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
  
  public boolean isAngry() {
    return (this.angerLevel > 0);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_ZOMBIE_PIG_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_ZOMBIE_PIGMAN;
  }
  
  protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    if (getRNG().nextInt(3) == 0) {
      int i = this.rand.nextInt(3);
      if (i == 0) {
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
      } else {
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.GOLDEN_SWORD));
      } 
    } 
    if (this.rand.nextFloat() < 0.25F * difficulty.getClampedAdditionalDifficulty()) {
      int i = this.rand.nextInt(2);
      float f = (this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.325F : 0.25F;
      if (this.rand.nextFloat() < 0.1F)
        i++; 
      if (this.rand.nextFloat() < 0.15F)
        i++; 
      if (this.rand.nextFloat() < 0.2F)
        i++; 
      boolean flag = true;
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
          ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
          if (!flag && this.rand.nextFloat() < f)
            break; 
          flag = false;
          if (itemstack.isEmpty()) {
            Item item = getArmorByChance(entityequipmentslot, i);
            if (item != null)
              setItemStackToSlot(entityequipmentslot, new ItemStack(item)); 
          } 
        } 
      } 
    } 
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    super.onInitialSpawn(difficulty, livingdata);
    setToNotVillager();
    if (!this.world.isRemote && this.world.rand.nextInt(4) == 0)
      setOldPEPigman(true); 
    return livingdata;
  }
}

