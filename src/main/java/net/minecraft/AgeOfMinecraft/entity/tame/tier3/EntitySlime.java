package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.ForgeHooks;

public class EntitySlime extends EntityTameBase implements IJumpingMount {
  private static final DataParameter<Integer> SLIME_SIZE = EntityDataManager.createKey(EntitySlime.class, DataSerializers.VARINT);
  
  public float squishAmount;
  
  public float squishFactor;
  
  public float prevSquishFactor;
  
  private boolean wasOnGround;
  
  private int jumpDelay;
  
  protected float jumpPower;
  
  public EntitySlime(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
      this.tasks.addTask(6, new EntityAIFriendlyAttackMelee(this, 1.2D, true));
      this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.8D));
      this.tasks.addTask(8, new EntityAILookIdle(this));
    } else {
      this.moveHelper = new SlimeMoveHelper(this);
      this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
      this.tasks.addTask(2, new AISlimeFloat(this));
      this.tasks.addTask(3, new AISlimeAttack(this));
      this.tasks.addTask(4, new AISlimeFaceRandom(this));
      this.tasks.addTask(5, new AISlimeHop(this));
    } 
  }
  
  public String getDescName() {
    return "slime";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public int getNextLevelRequirement() {
    return 10 * getSlimeSize();
  }
  
  public float getBonusVSLight() {
    return 1.25F;
  }
  
  public float getBonusVSArmored() {
    return 2.0F;
  }
  
  public float getBonusVSMassive() {
    return 0.5F;
  }
  
  public boolean isChild() {
    return (getSlimeSize() == 0);
  }
  
  public void setChild(boolean childZombie) {}
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(SLIME_SIZE, 1);
  }
  
  public int timesToConvert() {
    return 4 * getSlimeSize();
  }
  
  public void setSlimeSize(int size) {
    this.getDataManager().set(SLIME_SIZE, size);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(size * 0.125F, size * 0.5F - 0.05F);
      if (size <= 1)
        setSize(0.2F, 0.85F); 
      if (size == 2 || size == 3)
        setSize(0.35F, 1.5F); 
    } else {
      setSize(0.5F * size, 0.5F * size);
    } 
    setPosition(this.posX, this.posY, this.posZ);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((size * size));
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(EngenderConfig.mobs.useMobTalkerModels ? 0.25D : (0.35F + 0.15F * size));
    setHealth(getMaxHealth());
    this.experienceValue = size;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public int getSlimeSize() {
    return this.getDataManager().get(SLIME_SIZE);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Size", getSlimeSize() - 1);
    tagCompound.setBoolean("wasOnGround", this.wasOnGround);
  }
  
  public boolean isSmallSlime() {
    return (getSlimeSize() <= 1);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    int i = tagCompund.getInteger("Size");
    if (i < 0)
      i = 0; 
    setSlimeSize(i + 1);
    this.wasOnGround = tagCompund.getBoolean("wasOnGround");
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SLIME;
  }
  
  protected SoundEvent getJumpSound() {
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_JUMP : SoundEvents.ENTITY_SLIME_JUMP;
  }
  
  public void createChild() {
    super.createChild();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (int i = 0; i < 2 + this.rand.nextInt(3); i++) {
        EntitySlime baby = createInstance();
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        baby.setSlimeSize(getSlimeSize() / 2);
        this.world.spawnEntity(baby);
      }  
  }
  
  public void onUpdate() {
    ItemStack size = new ItemStack((this instanceof EntityMagmaCube) ? Items.MAGMA_CREAM : Items.SLIME_BALL, getSlimeSize());
    size.setStackDisplayName("Size: " + getSlimeSize());
    this.basicInventory.setInventorySlotContents(7, size);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getAttackStrength());
    this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
    this.prevSquishFactor = this.squishFactor;
    super.onUpdate();
    if (this.onGround && !this.wasOnGround) {
      int i = getSlimeSize();
      if (spawnCustomParticles())
        i = 0; 
      for (int j = 0; j < i * 8; j++) {
        float f = this.rand.nextFloat() * 6.2831855F;
        float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
        float f2 = MathHelper.sin(f) * this.width * f1;
        float f3 = MathHelper.cos(f) * this.width * f1;
        World world = this.world;
        EnumParticleTypes enumparticletypes = getParticleType();
        double d0 = this.posX + f2;
        double d1 = this.posZ + f3;
        world.spawnParticle(enumparticletypes, d0, (getEntityBoundingBox()).minY, d1, 0.0D, 0.0D, 0.0D);
      } 
      playSound(getSquishSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.05F : 0.8F));
      this.squishAmount = -0.5F;
    } else if (!this.onGround && this.wasOnGround) {
      this.squishAmount = 1.0F;
    } 
    this.wasOnGround = this.onGround;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      this.squishAmount *= 0.3F;
    } else {
      alterSquishAmount();
    } 
  }
  
  protected void alterSquishAmount() {
    this.squishAmount *= 0.6F;
  }
  
  protected int getJumpDelay() {
    return (this.moralRaisedTimer > 200) ? (this.rand.nextInt(10) + 5) : (this.rand.nextInt(20) + 10);
  }
  
  protected EntitySlime createInstance() {
    return new EntitySlime(this.world);
  }
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    if (SLIME_SIZE.equals(key)) {
      int size = getSlimeSize();
      if (EngenderConfig.mobs.useMobTalkerModels) {
        setSize(size * 0.125F, size * 0.5F - 0.05F);
        if (getSlimeSize() <= 1)
          setSize(0.2F, 0.85F); 
        if (getSlimeSize() == 2 || size == 3)
          setSize(0.35F, 1.5F); 
      } else {
        setSize(0.5F * size, 0.5F * size);
      } 
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    } 
    super.notifyDataManagerChange(key);
  }
  
  public void setDead() {
    int i = getSlimeSize();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && i > 1 && getHealth() <= 0.0F) {
      int j = 2 + this.rand.nextInt(3);
      for (int k = 0; k < j; k++) {
        float f = ((k % 2) - 0.5F) * i / 4.0F;
        float f1 = ((k / 2) - 0.5F) * i / 4.0F;
        EntitySlime entityslime = createInstance();
        entityslime.writeEntityToNBT(getEntityData());
        entityslime.setOwnerId(getOwnerId());
        entityslime.setSlimeSize(i / 2);
        entityslime.setLocationAndAngles(this.posX + f, this.posY, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
        this.world.spawnEntity(entityslime);
      } 
    } 
    super.setDead();
  }
  
  public void performSpecialAttack() {
    jump();
    this.motionY++;
    playSound(getJumpSound(), getSoundVolume(), ((getRNG().nextFloat() - getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void fall(float distance, float damageMultiplier) {
    this.moveStrafing = this.moveForward = 0.0F;
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 64.0D * getSlimeSize() && getSpecialAttackTimer() <= 0 && isHero()) {
      setSpecialAttackTimer(100 * getSlimeSize());
      playSound(ESound.golemSmash, 10.0F, 2.0F - getSlimeSize() * 0.25F);
      createEngenderModExplosionFireless(this, this.posX, this.posY - 0.5D, this.posZ, getSlimeSize(), false);
      if (getAttackTarget() != null && !false) {
        double d01 = (getAttackTarget()).posX - this.posX;
        double d11 = (getAttackTarget()).posZ - this.posZ;
        float f21 = MathHelper.sqrt(d01 * d01 + d11 * d11);
        double hor = (f21 / 16.0F) * 1.25D;
        this.motionX = d01 / f21 * hor * hor + this.motionX * hor;
        this.motionZ = d11 / f21 * hor * hor + this.motionZ * hor;
        this.motionY = 0.6000000238418579D;
        double dou = getDistanceSq(getAttackTarget());
        if (dou <= 16.0D)
          attackEntityAsMob(getAttackTarget());
      } 
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(16.0D * getSlimeSize()), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty())
          for (EntityLivingBase entity : list) {
              if (entity != null)
                  if (!false) {
                      entity.motionY += 0.75D;
                      attackEntityAsMob(entity);
                  }
          }
    } 
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    collideWithNearbyEntities();
    if (isRiding() && getRidingEntity() instanceof EntityLivingBase)
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = ((EntityLivingBase)getRidingEntity()).rotationYawHead; 
    if (getAttackTarget() != null && !this.onGround && EngenderConfig.mobs.useMobTalkerModels && getDistanceSq(getAttackTarget()) > ((getAttackTarget()).width * (getAttackTarget()).width) + 16.0D) {
      double d0 = (getAttackTarget()).posX - this.posX;
      double d1 = (getAttackTarget()).posZ - this.posZ;
      double d3 = d0 * d0 + d1 * d1;
      double d5 = MathHelper.sqrt(d3);
      this.motionX += d0 / d5 * 0.5D - this.motionX;
      this.motionZ += d1 / d5 * 0.5D - this.motionZ;
    } 
    if (getAttackTarget() != null && this.onGround && EngenderConfig.mobs.useMobTalkerModels)
      if (this.jumpDelay-- <= 0) {
        this.jumpDelay = getJumpDelay();
        if (getAttackTarget() != null)
          this.jumpDelay /= 3; 
        if (isBurning())
          this.jumpDelay /= 3; 
        if (!isSneaking())
          getJumpHelper().setJumping(); 
      }  
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 32.0D * getSlimeSize() && getSpecialAttackTimer() <= 0 && this.onGround && isHero())
      performSpecialAttack(); 
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString(this);
      if (str == null)
        str = "generic"; 
      return (getSlimeSize() >= 4) ? I18n.translateToLocal("entity.SlimeHelpful.cmm4.name") : (isSmallSlime() ? I18n.translateToLocal("entity.SlimeHelpful.cmm1.name") : I18n.translateToLocal("entity.SlimeHelpful.cmm2.name"));
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public void applyEntityCollision(Entity entityIn) {
    super.applyEntityCollision(entityIn);
    if (entityIn instanceof EntityLivingBase) {
      EntityLivingBase entity = (EntityLivingBase)entityIn;
      if (this.ticksExisted > 10 && canEntityBeSeen(entity) && getDistanceSq(entity) < this.width * 1.5D * this.width * 1.5D && attackEntityAsMob(entity))
        playSound(SoundEvents.ENTITY_SLIME_ATTACK, getSoundVolume(), getSoundPitch()); 
    } 
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden() && canBeSteered()) {
      if (isInWater() || isInLava())
        if (getRNG().nextFloat() < 0.8F)
          this.motionY += 0.1D - this.motionY;  
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      this.jumpMovementFactor = 0.05F;
      if (canPassengerSteer()) {
        setAIMoveSpeed(EngenderConfig.mobs.useMobTalkerModels ? (float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() : 0.0F);
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        jump();
        this.motionY += this.jumpPower * ((this instanceof EntityMagmaCube) ? 1.0D : 0.25D);
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
      this.jumpMovementFactor = 0.02F;
      super.travel(strafe, vertical, forward);
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
    playSound(getJumpSound(), getSoundVolume(), ((getRNG().nextFloat() - getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
  }
  
  public void handleStopJump() {}
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && false && getSlimeSize() < (isHero() ? 16 : 4) && stack.getItem() == ((this instanceof EntityMagmaCube) ? Items.MAGMA_CREAM : Items.SLIME_BALL)) {
      if (!player.capabilities.isCreativeMode)
        stack.shrink(1); 
      playSound(getSquishSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.05F : 0.8F));
      setSlimeSize(getSlimeSize() + 1);
      playSound(getSquishSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.05F : 0.8F));
      int i = getSlimeSize();
      if (spawnCustomParticles())
        i = 0; 
      for (int j = 0; j < i * 8; j++) {
        float f = this.rand.nextFloat() * 6.2831855F;
        float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
        float f2 = MathHelper.sin(f) * i * 0.5F * f1;
        float f3 = MathHelper.cos(f) * i * 0.5F * f1;
        World world = this.world;
        EnumParticleTypes enumparticletypes = getParticleType();
        double d0 = this.posX + f2;
        double d1 = this.posZ + f3;
        world.spawnParticle(enumparticletypes, d0, (getEntityBoundingBox()).minY, d1, 0.0D, 0.0D, 0.0D);
      } 
      return true;
    } 
    if (isSmallSlime()) {
      if (hasOwner(player) && EngenderConfig.mobs.useMobTalkerModels) {
        player.swingArm(EnumHand.MAIN_HAND);
        if (getRidingEntity() == null) {
          startRiding(player, true);
        } else {
          dismountRidingEntity();
        } 
      } 
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null && !isSmallSlime()) {
      if (!isWild() && false && !isChild() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.8F) : (0.625F * this.height);
  }
  
  protected boolean canDamagePlayer() {
    return true;
  }
  
  protected int getAttackStrength() {
    return (this.moralRaisedTimer > 200) ? (getSlimeSize() * 2) : getSlimeSize();
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + ((getSlimeSize() > 6) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_HURT : SoundEvents.ENTITY_SLIME_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + ((getSlimeSize() > 6) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_DEATH : SoundEvents.ENTITY_SLIME_DEATH;
  }
  
  protected SoundEvent getSquishSound() {
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_HURT : SoundEvents.ENTITY_SLIME_SQUISH;
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.25F) : super.getSoundPitch();
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return isSmallSlime() ? ELoot.ENTITIES_SLIME : LootTableList.EMPTY;
  }
  
  protected float getSoundVolume() {
    return (isSneaking() ? 0.025F : 0.25F) * getSlimeSize();
  }
  
  public int getVerticalFaceSpeed() {
    return 40;
  }
  
  protected boolean makesSoundOnJump() {
    return false;
  }
  
  protected boolean makesSoundOnLand() {
    return (getSlimeSize() > 2);
  }
  
  protected void jump() {
    playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.35F : 1.1F));
    this.motionY += 0.42D;
    this.isAirBorne = true;
    ForgeHooks.onLivingJump(this);
  }
  
  public double getMountedYOffset() {
    return this.height - 0.25D + this.squishFactor * getSlimeSize() * 0.25D * ((this instanceof EntityMagmaCube) ? 3.0D : 1.5D);
  }
  
  public double getYOffset() {
    return EngenderConfig.mobs.useMobTalkerModels ? 0.0D : 0.5D;
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    int i = this.rand.nextInt(3);
    if (i < 2 && this.rand.nextFloat() < 0.5F * difficulty.getClampedAdditionalDifficulty())
      i++; 
    int j = 1 << i;
    if (this.rand.nextInt(200) == 0) {
      setSlimeSize(8);
    } else {
      setSlimeSize(j);
    } 
    return super.onInitialSpawn(difficulty, livingdata);
  }
  
  protected boolean spawnCustomParticles() {
    return false;
  }
  
  static class AISlimeAttack extends EntityAIBase {
    private final EntitySlime slime;
    
    private int growTieredTimer;
    
    public AISlimeAttack(EntitySlime slimeIn) {
      this.slime = slimeIn;
      setMutexBits(2);
    }
    
    public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
      return (entitylivingbase == null) ? false : (!entitylivingbase.isEntityAlive() ? false : ((!(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage)));
    }
    
    public void startExecuting() {
      this.growTieredTimer = 300;
      super.startExecuting();
    }
    
    public boolean shouldContinueExecuting() {
      EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
      return (entitylivingbase == null) ? false : (!entitylivingbase.isEntityAlive() ? false : ((entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage) ? false : ((--this.growTieredTimer > 0))));
    }
    
    public void updateTask() {
      this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
      ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, this.slime.canDamagePlayer());
      if (this.slime.ticksExisted % 10 == 0 && this.slime.canEntityBeSeen(this.slime.getAttackTarget()) && this.slime.getDistanceSq(this.slime.getAttackTarget()) < this.slime.width * 1.5D * this.slime.width * 1.5D && this.slime.getAttackTarget() != null)
        this.slime.attackEntityAsMob(this.slime.getAttackTarget());
    }
  }
  
  static class AISlimeFaceRandom extends EntityAIBase {
    private final EntitySlime slime;
    
    private float chosenDegrees;
    
    private int nextRandomizeTime;
    
    public AISlimeFaceRandom(EntitySlime slimeIn) {
      this.slime = slimeIn;
      setMutexBits(2);
    }
    
    public boolean shouldExecute() {
      return (this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(MobEffects.LEVITATION)));
    }
    
    public void updateTask() {
      if (--this.nextRandomizeTime <= 0) {
        this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
        this.chosenDegrees = this.slime.getRNG().nextInt(360);
      } 
      ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
    }
  }
  
  static class AISlimeFloat extends EntityAIBase {
    private final EntitySlime slime;
    
    public AISlimeFloat(EntitySlime slimeIn) {
      this.slime = slimeIn;
      setMutexBits(5);
      ((PathNavigateGround)slimeIn.getNavigator()).setCanSwim(true);
    }
    
    public boolean shouldExecute() {
      return (this.slime.isInWater() || this.slime.isInLava());
    }
    
    public void updateTask() {
      if (this.slime.getRNG().nextFloat() < 0.8F)
        this.slime.getJumpHelper().setJumping(); 
      ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2D);
    }
  }
  
  static class AISlimeHop extends EntityAIBase {
    private final EntitySlime slime;
    
    public AISlimeHop(EntitySlime slimeIn) {
      this.slime = slimeIn;
      setMutexBits(5);
    }
    
    public boolean shouldExecute() {
      return true;
    }
    
    public void updateTask() {
      ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0D);
    }
  }
  
  public static class SlimeMoveHelper extends EntityMoveHelper {
    private float yRot;
    
    private int jumpDelay;
    
    private final EntitySlime slime;
    
    private boolean isAggressive;
    
    public SlimeMoveHelper(EntitySlime slimeIn) {
      super(slimeIn);
      this.slime = slimeIn;
      this.yRot = 180.0F * slimeIn.rotationYaw / 3.1415927F;
    }
    
    public void setDirection(float p_179920_1_, boolean p_179920_2_) {
      this.yRot = p_179920_1_;
      this.isAggressive = p_179920_2_;
    }
    
    public void setSpeed(double speedIn) {
      this.speed = speedIn;
      this.action = EntityMoveHelper.Action.MOVE_TO;
    }
    
    public void onUpdateMoveHelper() {
      this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
      this.entity.rotationYawHead = this.entity.rotationYaw;
      this.entity.renderYawOffset = this.entity.rotationYaw;
      if (this.action != EntityMoveHelper.Action.MOVE_TO || this.slime.getJukeboxToDanceTo() != null || !((EntityTameBase)this.entity).getCurrentBook().isEmpty()) {
        this.entity.setMoveForward(0.0F);
      } else {
        this.action = EntityMoveHelper.Action.WAIT;
        if (this.entity.onGround && !this.entity.isBeingRidden() && !this.entity.isRiding()) {
          this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
          if (this.jumpDelay-- <= 0) {
            this.jumpDelay = this.slime.getJumpDelay();
            if (this.isAggressive)
              this.jumpDelay /= 3; 
            if (this.entity.isBurning())
              this.jumpDelay /= 3; 
            this.slime.getJumpHelper().setJumping();
          } else {
            this.slime.moveStrafing = 0.0F;
            this.slime.moveForward = 0.0F;
            this.entity.setAIMoveSpeed(0.0F);
          } 
        } else {
          this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
        } 
      } 
    }
  }
}

