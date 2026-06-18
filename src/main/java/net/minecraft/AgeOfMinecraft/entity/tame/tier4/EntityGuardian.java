package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityElderGuardian;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGuardian extends EntityTameBase implements Armored {
  private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(EntityGuardian.class, DataSerializers.BYTE);
  
  private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.createKey(EntityGuardian.class, DataSerializers.VARINT);
  
  private float clientSideTailAnimation;
  
  private float clientSideTailAnimationO;
  
  private float tailSwiping;
  
  private float spineExtention;
  
  private float clientSideSpikesAnimationO;
  
  private EntityLivingBase targetedEntity;
  
  private int clientSideAttackTime;
  
  private boolean clientSideTouchedGround;
  
  protected EntityAIWander wander;
  
  public EntityGuardian(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.experienceValue = 10;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.45F, 1.75F);
    } else {
      setSize(1.0F, 1.0F);
    } 
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 48.0F, 8.0F));
    this.tasks.addTask(4, new AIGuardianAttack());
    EntityAIMoveTowardsRestriction entityaimovetowardsrestriction;
    this.tasks.addTask(5, (EntityAIBase)(entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D)));
    this.tasks.addTask(7, (EntityAIBase)(this.wander = new EntityAIWander((EntityCreature)this, 1.0D, 80)));
    this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.wander.setMutexBits(3);
    entityaimovetowardsrestriction.setMutexBits(3);
    this.moveHelper = new GuardianMoveHelper(this);
    this.clientSideTailAnimationO = this.clientSideTailAnimation = this.rand.nextFloat();
  }
  
  public String getDescName() {
    return "guardian";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGuardian(this.world);
  }
  
  public float getBonusVSLight() {
    return 3.0F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
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
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntityGuardian baby = new EntityGuardian(this.world);
        EntityGuardian entityElderGuardian = baby;
        baby.copyLocationAndAnglesFrom((Entity)this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        if (this.rand.nextFloat() < 0.01F) {
          entityElderGuardian = new EntityElderGuardian(this.world);
          entityElderGuardian.setGrowingAge(-100000);
          entityElderGuardian.setElder();
        } else {
          entityElderGuardian.setGrowingAge(-24000);
        } 
        entityElderGuardian.setChild(true);
        entityElderGuardian.setIsAntiMob(isAntiMob());
        entityElderGuardian.setIsHero(isHero());
        entityElderGuardian.setOwnerId(getOwnerId());
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            entityElderGuardian.levelUp();  
        this.world.spawnEntity((Entity)entityElderGuardian);
      }  
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.3F) : super.getSoundPitch();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public float getRenderSizeModifier() {
    return EngenderConfig.mobs.useMobTalkerModels ? 1.0F : 1.0F;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
  }
  
  public float getAttackAnimationScale(float p_175477_1_) {
    return (this.clientSideAttackTime + p_175477_1_) / getAttackDuration();
  }
  
  protected PathNavigate getNewNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateSwimmer((EntityLiving)this, worldIn);
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos).getMaterial() == Material.WATER) ? (10.0F + this.world.getLightBrightness(pos) - 0.5F) : super.getBlockPathWeight(pos);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(STATUS, Byte.valueOf((byte)0));
    this.dataManager.register(TARGET_ENTITY, Integer.valueOf(0));
  }
  
  private boolean isSyncedFlagSet(int flagId) {
    return ((((Byte)this.dataManager.get(STATUS)).byteValue() & flagId) != 0);
  }
  
  private void setSyncedFlag(int flagId, boolean state) {
    byte b0 = ((Byte)this.dataManager.get(STATUS)).byteValue();
    if (state) {
      this.dataManager.set(STATUS, Byte.valueOf((byte)(b0 | flagId)));
    } else {
      this.dataManager.set(STATUS, Byte.valueOf((byte)(b0 & (flagId ^ 0xFFFFFFFF))));
    } 
  }
  
  public boolean isMoving() {
    return isSyncedFlagSet(2);
  }
  
  private void setMoving(boolean p_175476_1_) {
    setSyncedFlag(2, p_175476_1_);
  }
  
  public boolean isPushedByWater() {
    return false;
  }
  
  public int getAttackDuration() {
    return 80;
  }
  
  public boolean isElder() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public void setElder() {
    this.clientSideSpikesAnimationO = this.spineExtention = 1.0F;
  }
  
  private void setTargetedEntity(int entityId) {
    this.dataManager.set(TARGET_ENTITY, Integer.valueOf(entityId));
  }
  
  public boolean hasTargetedEntity() {
    return (((Integer)this.dataManager.get(TARGET_ENTITY)).intValue() != 0 && isEntityAlive());
  }
  
  public EntityLivingBase getTargetedEntity() {
    if (!hasTargetedEntity())
      return null; 
    if (this.world.isRemote) {
      if (this.targetedEntity != null)
        return this.targetedEntity; 
      Entity entity = this.world.getEntityByID(((Integer)this.dataManager.get(TARGET_ENTITY)).intValue());
      if (entity instanceof EntityLivingBase) {
        this.targetedEntity = (EntityLivingBase)entity;
        return this.targetedEntity;
      } 
      return null;
    } 
    return getAttackTarget();
  }
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    super.notifyDataManagerChange(key);
    if (TARGET_ENTITY.equals(key)) {
      this.clientSideAttackTime = 0;
      this.targetedEntity = null;
    } 
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_GUARDIAN_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_GUARDIAN_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    setAttackTarget((EntityLivingBase)null);
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    if (!isInWater())
      playSound(SoundEvents.ENTITY_GUARDIAN_DEATH_LAND, getSoundVolume(), getSoundPitch()); 
    return SoundEvents.ENTITY_GUARDIAN_DEATH;
  }
  
  protected boolean canTriggerWalking() {
    return (!isInWater() && EngenderConfig.mobs.useMobTalkerModels);
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.85F + ((isRiding() || isInWater() || isInLava() || isSitResting()) ? (MathHelper.cos(this.ticksExisted * 0.2F) * 0.1F) : 0.0F)) : (this.height * 0.5F);
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() == Items.TOTEM_OF_UNDYING && !(this instanceof EntityElderGuardian)) {
      EntityElderGuardian baby = new EntityElderGuardian(this.world);
      baby.copyLocationAndAnglesFrom((Entity)this);
      baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      baby.setOwnerId(getOwnerId());
      this.world.spawnEntity((Entity)baby);
      this.world.removeEntity((Entity)this);
      stack.shrink(1);
      (Minecraft.getMinecraft()).effectRenderer.emitParticleAtEntity((Entity)this, EnumParticleTypes.TOTEM, 30);
      playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
      this.ticksExisted = 0;
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void onLivingUpdate() {
    this.moveHelper = (!isInWater() && EngenderConfig.mobs.useMobTalkerModels) ? new EntityMoveHelper((EntityLiving)this) : new GuardianMoveHelper(this);
    if (!EngenderConfig.mobs.useMobTalkerModels)
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead; 
    if (this.deathTime > 0)
      this.motionY = -0.6D; 
    setSprinting(false);
    if (this.world.isRemote) {
      this.clientSideTailAnimationO = this.clientSideTailAnimation;
      if (!isInWater() && !isElder() && !isPotionActive(MobEffects.LEVITATION)) {
        this.tailSwiping = 1.0F;
        this.clientSideTouchedGround = (this.motionY < 0.0D && this.world.isBlockNormalCube((new BlockPos((Entity)this)).down(), false));
      } else if (isMoving()) {
        if (this.tailSwiping < 0.5F) {
          this.tailSwiping = 4.0F;
        } else {
          this.tailSwiping += (0.5F - this.tailSwiping) * 0.1F;
        } 
      } else if (isChild()) {
        this.tailSwiping = 1.0F;
      } else {
        this.tailSwiping += (0.125F - this.tailSwiping) * 0.2F;
      } 
      if (!isAIDisabled()) {
        this.clientSideTailAnimation += this.tailSwiping;
        this.clientSideSpikesAnimationO = this.spineExtention;
      } 
      if (!isInWater() && !isRiding() && !isPotionActive(MobEffects.LEVITATION)) {
        if (isElder()) {
          if (this.onGround && !isSneaking()) {
            this.spineExtention += (0.7F - this.spineExtention) * 0.05F;
          } else {
            this.spineExtention += (-0.7F - this.spineExtention) * 0.25F;
          } 
        } else {
          this.spineExtention = this.rand.nextFloat() - 0.5F;
        } 
      } else if (isMoving() || isRiding() || isPotionActive(MobEffects.LEVITATION)) {
        this.spineExtention += (-0.7F - this.spineExtention) * 0.25F;
      } else {
        this.spineExtention += (0.25F - this.spineExtention) * 0.06F;
      } 
      if (isAIDisabled())
        this.spineExtention = 0.0F; 
      if (isMoving() && isInWater()) {
        Vec3d vec3 = getLook(0.0F);
        for (int i = 0; i < 2; i++)
          this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width - vec3.x * 1.5D, this.posY + this.rand.nextDouble() * this.height - vec3.y * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width - vec3.z * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]); 
      } 
      if (hasTargetedEntity()) {
        if (this.clientSideAttackTime < getAttackDuration())
          this.clientSideAttackTime++; 
        EntityLivingBase entitylivingbase = getTargetedEntity();
        if (entitylivingbase != null) {
          getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 180.0F, getVerticalFaceSpeed());
          getLookHelper().onUpdateLook();
          double d5 = getAttackAnimationScale(0.0F);
          double d0 = entitylivingbase.posX - this.posX;
          double d1 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - this.posY + getEyeHeight();
          double d2 = entitylivingbase.posZ - this.posZ;
          double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
          d0 /= d3;
          d1 /= d3;
          d2 /= d3;
          double d4 = this.rand.nextDouble();
          while (d4 < d3) {
            d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
            this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4, this.posY + d1 * d4 + getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
          } 
        } 
      } 
    } 
    if (this.inWater) {
      setAir(300);
    } else if (!isBeingRidden() && isEntityAlive() && this.onGround && !isBeingRidden() && !isInLove() && !isSneaking() && !isElder() && !EngenderConfig.mobs.useMobTalkerModels) {
      this.motionX += ((this.rand.nextFloat() * 2.0F - 1.0F) * (isChild() ? 0.2F : 0.4F));
      this.motionZ += ((this.rand.nextFloat() * 2.0F - 1.0F) * (isChild() ? 0.2F : 0.4F));
      this.rotationYaw = this.rand.nextFloat() * 360.0F;
      jump();
    } 
    if (hasTargetedEntity())
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead; 
    if (this.ticksExisted % 20 == 0)
      setMoving(false); 
    super.onLivingUpdate();
  }
  
  public double getMountedYOffset() {
    return this.height * 0.85D;
  }
  
  public boolean shouldDismountInWater(Entity rider) {
    return false;
  }
  
  protected void jump() {
    this.hurtTime = 0;
    playSound(SoundEvents.ENTITY_GUARDIAN_FLOP, 1.0F, isChild() ? 1.5F : 1.0F);
    this.motionY += (isChild() ? 0.5D : 0.6D) - this.motionY;
    this.onGround = false;
    this.isAirBorne = true;
  }
  
  @SideOnly(Side.CLIENT)
  public float getTailAnimation(float p_175471_1_) {
    return this.clientSideTailAnimationO + (this.clientSideTailAnimation - this.clientSideTailAnimationO) * p_175471_1_;
  }
  
  @SideOnly(Side.CLIENT)
  public float getSpikesAnimation(float p_175469_1_) {
    return this.clientSideSpikesAnimationO + (this.spineExtention - this.clientSideSpikesAnimationO) * p_175469_1_;
  }
  
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_GUARDIAN;
  }
  
  public boolean isNotColliding() {
    return (this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty());
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (super.attackEntityFrom(source, amount)) {
      if (!isMoving() && !source.isMagicDamage() && source.getTrueSource() instanceof EntityLivingBase) {
        EntityLivingBase entitylivingbase = (EntityLivingBase)source.getTrueSource();
        if (!source.isExplosion() && (!false || (getOwner() != null && entitylivingbase == getOwner()))) {
          inflictEngenderMobDamage(entitylivingbase, " died trying to attack ", DamageSource.causeThornsDamage((Entity)this), 2.0F);
          playSound(SoundEvents.ENCHANT_THORNS_HIT, getSoundVolume(), getSoundPitch());
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public int getVerticalFaceSpeed() {
    return EngenderConfig.mobs.useMobTalkerModels ? 40 : 180;
  }
  
  public void travel(float strafe, float vertical, float forward) {
    EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
    if (isBeingRidden() && canBeSteered()) {
      entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 200, 0, true, true));
      entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 200, 0, true, true));
      if (getAttackTarget() == null) {
        this.renderYawOffset = this.rotationYaw = this.rotationYawHead = entitylivingbase.rotationYaw;
        this.rotationPitch = 0.0F;
        setRotation(this.rotationYaw, this.rotationPitch);
        this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      } 
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      this.jumpMovementFactor = 0.1F;
      if (canPassengerSteer()) {
        Vec3d vec3 = entitylivingbase.getLook(1.0F);
        double d0 = this.posX - this.posX + vec3.x * 50.0D;
        double d1 = this.posY - this.posY + vec3.y * 50.0D;
        double d2 = this.posZ - this.posZ + vec3.z * 50.0D;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        d3 = MathHelper.sqrt(d3);
        if ((forward != 0.0F || strafe != 0.0F) && isInWater()) {
          this.motionX -= d0 / d3 * ((this.moralRaisedTimer > 0) ? 0.1D : 0.05D);
          this.motionY -= d1 / d3 * ((this.moralRaisedTimer > 0) ? 0.1D : 0.05D);
          this.motionZ -= d2 / d3 * ((this.moralRaisedTimer > 0) ? 0.1D : 0.05D);
          if (this.motionX > (isElder() ? 0.3D : 0.6D))
            this.motionX = isElder() ? 0.3D : 0.6D; 
          if (this.motionY > (isElder() ? 0.3D : 0.6D))
            this.motionY = isElder() ? 0.3D : 0.6D; 
          if (this.motionZ > (isElder() ? 0.3D : 0.6D))
            this.motionZ = isElder() ? 0.3D : 0.6D; 
          if (this.motionX < -(isElder() ? 0.3D : 0.6D))
            this.motionX = -(isElder() ? 0.3D : 0.6D); 
          if (this.motionY < -(isElder() ? 0.3D : 0.6D))
            this.motionY = -(isElder() ? 0.3D : 0.6D); 
          if (this.motionZ < -(isElder() ? 0.3D : 0.6D))
            this.motionZ = -(isElder() ? 0.3D : 0.6D); 
          setMoving(true);
        } 
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        if (isInWater() && this.deathTime <= 0) {
          moveRelative(strafe, vertical, forward, 0.1F);
          move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
          this.motionX *= 0.8999999761581421D;
          this.motionY *= 0.8999999761581421D;
          this.motionZ *= 0.8999999761581421D;
          if (isInWater() && !isMoving() && getAttackTarget() == null && !isBeingRidden())
            this.motionY -= 0.005D; 
        } else {
          if ((forward != 0.0F || strafe != 0.0F) && this.onGround && !EngenderConfig.mobs.useMobTalkerModels)
            jump(); 
          super.travel(strafe, vertical, forward);
        } 
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
    } else if (isServerWorld()) {
      if (isInWater() && this.deathTime <= 0) {
        moveRelative(strafe, vertical, forward, 0.1F);
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8999999761581421D;
        this.motionY *= 0.8999999761581421D;
        this.motionZ *= 0.8999999761581421D;
        if (isInWater() && !isMoving() && getAttackTarget() == null && !isBeingRidden())
          this.motionY -= 0.005D; 
      } else {
        super.travel(strafe, vertical, forward);
      } 
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  class AIGuardianAttack extends EntityAIBase {
    private EntityGuardian guardian = EntityGuardian.this;
    
    private int tickCounter;
    
    public AIGuardianAttack() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
      return (entitylivingbase != null && entitylivingbase.isEntityAlive() && !this.guardian.isChild());
    }
    
    public boolean shouldContinueExecuting() {
      return (super.shouldContinueExecuting() && this.guardian.getAttackTarget() != null && this.tickCounter < this.guardian.getAttackDuration());
    }
    
    public void startExecuting() {
      this.tickCounter = -10;
      this.guardian.getNavigator().clearPath();
      this.guardian.getLookHelper().setLookPositionWithEntity((Entity)this.guardian.getAttackTarget(), 180.0F, this.guardian.getVerticalFaceSpeed());
      this.guardian.isAirBorne = true;
      this.guardian.setSitResting(false);
    }
    
    public void resetTask() {
      this.guardian.setTargetedEntity(0);
      this.guardian.setAttackTarget((EntityLivingBase)null);
      this.guardian.wander.makeUpdate();
    }
    
    public void updateTask() {
      this.guardian.setSitResting(false);
      EntityLivingBase entitylivingbase = this.guardian.getAttackTarget();
      this.guardian.getNavigator().clearPath();
      this.guardian.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 180.0F, this.guardian.getVerticalFaceSpeed());
      if (!this.guardian.canEntityBeSeen((Entity)entitylivingbase)) {
        this.guardian.setAttackTarget((EntityLivingBase)null);
      } else {
        this.tickCounter++;
        if (this.guardian.moralRaisedTimer > 200)
          this.tickCounter++; 
        if (this.tickCounter > 0)
          this.guardian.playSound(SoundEvents.ENTITY_GUARDIAN_ATTACK, 1.0F, 0.5F + (float)(this.tickCounter * 0.025D)); 
        if (this.tickCounter > 0 && this.guardian.isHero() && this.guardian.getSpecialAttackTimer() <= 0)
          this.guardian.playSound(SoundEvents.ENTITY_GUARDIAN_ATTACK, 10.0F, 0.5F + (float)(this.tickCounter * 0.025D)); 
        if (this.tickCounter == 0) {
          this.guardian.setTargetedEntity(this.guardian.getAttackTarget().getEntityId());
        } else if (this.tickCounter >= this.guardian.getAttackDuration() && (entitylivingbase.hurtResistantTime == 0 || this.guardian.isElder())) {
          float f = 1.0F;
          if (this.guardian.world.getDifficulty() == EnumDifficulty.HARD)
            f += 2.0F; 
          if (this.guardian.isElder()) {
            f += 2.0F;
            entitylivingbase.prevRenderYawOffset = entitylivingbase.renderYawOffset = entitylivingbase.prevRotationYaw = entitylivingbase.rotationYaw = entitylivingbase.prevRotationYawHead = entitylivingbase.rotationYawHead = this.guardian.rotationYawHead;
            float xRatio = MathHelper.sin(entitylivingbase.rotationYawHead * 0.017453292F);
            float zRatio = -MathHelper.cos(entitylivingbase.rotationYawHead * 0.017453292F);
            float f1 = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
            entitylivingbase.motionX -= xRatio / f1 / this.guardian.getDistance((Entity)entitylivingbase) * 4.0D;
            entitylivingbase.motionZ -= zRatio / f1 / this.guardian.getDistance((Entity)entitylivingbase) * 4.0D;
            entitylivingbase.motionY++;
            if (entitylivingbase instanceof EntityPlayerMP)
              ((EntityPlayerMP)entitylivingbase).connection.sendPacket((Packet)new SPacketEntityVelocity((Entity)entitylivingbase)); 
          } 
          this.guardian.inflictEngenderMobDamage(entitylivingbase, " was laser beamed by ", DamageSource.causeMobDamage((EntityLivingBase)this.guardian).setMagicDamage(), (float)this.guardian.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() + f);
          if (this.guardian.isHero() && this.guardian.getSpecialAttackTimer() <= 0) {
            this.guardian.playSound(SoundEvents.ENTITY_WITHER_SPAWN, 10.0F, 1.25F);
            entitylivingbase.hurtResistantTime = 0;
            EntityTameBase.createEngenderModExplosion((Entity)this.guardian, entitylivingbase.posX, entitylivingbase.posY + (entitylivingbase.height / 2.0F), entitylivingbase.posZ, 7.0F, true, false);
            entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.guardian), (float)this.guardian.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 3.0F);
            this.guardian.setSpecialAttackTimer(600);
          } 
        } 
        super.updateTask();
      } 
    }
  }
  
  class GuardianMoveHelper extends EntityMoveHelper {
    private EntityGuardian entityGuardian;
    
    public GuardianMoveHelper(EntityGuardian guardian) {
      super((EntityLiving)guardian);
      this.entityGuardian = guardian;
    }
    
    public void onUpdateMoveHelper() {
      if ((this.entityGuardian.isInWater() || this.entityGuardian.isPotionActive(MobEffects.LEVITATION)) && this.action == EntityMoveHelper.Action.MOVE_TO && !this.entityGuardian.getNavigator().noPath()) {
        double d0 = this.posX - this.entityGuardian.posX;
        double d1 = this.posY - this.entityGuardian.posY;
        double d2 = this.posZ - this.entityGuardian.posZ;
        double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
        d1 /= d3;
        float f = (float)(MathHelper.atan2(d2, d0) * 57.29577951308232D) - 90.0F;
        this.entityGuardian.rotationYaw = limitAngle(this.entityGuardian.rotationYaw, f, 90.0F);
        this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
        float f1 = (float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + (f1 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
        double d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5D) * 0.05D;
        double d5 = Math.cos((this.entityGuardian.rotationYaw * 0.017453292F));
        double d6 = Math.sin((this.entityGuardian.rotationYaw * 0.017453292F));
        this.entityGuardian.motionX += d4 * d5;
        this.entityGuardian.motionZ += d4 * d6;
        d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75D) * 0.05D;
        this.entityGuardian.motionY += d4 * (d6 + d5) * 0.25D;
        this.entityGuardian.motionY += this.entityGuardian.getAIMoveSpeed() * d1 * 0.1D;
        EntityLookHelper entitylookhelper = this.entityGuardian.getLookHelper();
        double d7 = this.entityGuardian.posX + d0 / d3 * 2.0D;
        double d8 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + d1 / d3;
        double d9 = this.entityGuardian.posZ + d2 / d3 * 2.0D;
        double d10 = entitylookhelper.getLookPosX();
        double d11 = entitylookhelper.getLookPosY();
        double d12 = entitylookhelper.getLookPosZ();
        if (!entitylookhelper.getIsLooking()) {
          d10 = d7;
          d11 = d8;
          d12 = d9;
        } 
        this.entityGuardian.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
        this.entityGuardian.setMoving(true);
      } else {
        if ((this.entityGuardian.isInWater() || this.entityGuardian.isPotionActive(MobEffects.LEVITATION)) && this.entityGuardian.onGround && (this.entityGuardian.moveForward != 0.0F || this.entityGuardian.moveStrafing != 0.0F))
          this.entityGuardian.jump(); 
        this.entityGuardian.setAIMoveSpeed(0.0F);
        this.entityGuardian.setMoving(false);
      } 
    }
  }
}


