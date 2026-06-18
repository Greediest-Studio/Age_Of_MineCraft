package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWither extends EntityTameBase implements IEntityMultiPart, IRangedAttackMob, IJumpingMount, Flying, Armored, Undead {
  private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer>[] HEAD_TARGETS = new DataParameter[] { FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET };
  
  private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
  
  private float[] xRotationHeads = new float[2];
  
  private float[] yRotationHeads = new float[2];
  
  private float[] xRotOHeads = new float[2];
  
  private float[] yRotOHeads = new float[2];
  
  private int[] nextHeadUpdate = new int[2];
  
  private int[] idleHeadUpdates = new int[2];
  
  private float[] headRandomTurn = new float[2];
  
  private static final DataParameter<Integer> HOVERTIMER = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> RAMTIMER = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
  
  private static final DataParameter<Boolean> SPAWNEDSKELETONS = EntityDataManager.createKey(EntityWither.class, DataSerializers.BOOLEAN);
  
  private double hoverX;
  
  private double hoverZ;
  
  public ExtendMultiPartEntityPart[] partArray;
  
  public ExtendMultiPartEntityPart mainHead = new ExtendMultiPartEntityPart(this, "mainHead", 1.0F, 1.0F);
  
  public ExtendMultiPartEntityPart rightHead = new ExtendMultiPartEntityPart(this, "rightHead", 0.75F, 0.75F);
  
  public ExtendMultiPartEntityPart leftHead = new ExtendMultiPartEntityPart(this, "leftHead", 0.75F, 0.75F);
  
  public ExtendMultiPartEntityPart spine = new ExtendMultiPartEntityPart(this, "spine", 0.4F, 1.6F);
  
  public ExtendMultiPartEntityPart lowerspine = new ExtendMultiPartEntityPart(this, "lowerspine", 0.4F, 0.6F);
  
  public ExtendMultiPartEntityPart rightRibs = new ExtendMultiPartEntityPart(this, "rightRibs", 0.5F, 0.8F);
  
  public ExtendMultiPartEntityPart leftRibs = new ExtendMultiPartEntityPart(this, "leftRibs", 0.5F, 0.8F);
  
  public ExtendMultiPartEntityPart rightsupport = new ExtendMultiPartEntityPart(this, "rightsupport", 0.4F, 0.4F);
  
  public ExtendMultiPartEntityPart leftsupport = new ExtendMultiPartEntityPart(this, "leftsupport", 0.4F, 0.4F);
  
  private int blockBreakCounter;
  
  private int lastActiveTime;
  
  private int timeSinceIgnited;
  
  protected float jumpPower;
  
  public EntityWither(World worldIn) {
    super(worldIn);
    this.partArray = new ExtendMultiPartEntityPart[] { this.mainHead, this.rightHead, this.leftHead, this.spine, this.lowerspine, this.rightRibs, this.leftRibs, this.rightsupport, this.leftsupport };
    setHealth(getMaxHealth());
    setSize(0.9F, 3.3F);
    this.isOffensive = true;
    this.isImmuneToFire = true;
    ((PathNavigateGround)getNavigator()).setCanSwim(true);
    this.tasks.addTask(0, new AIDoNothing());
    this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 4.0D, 100.0F, 16.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 4.0D, 50, 20.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIWanderAvoidWaterFlying((EntityCreature)this, 2.0D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.ignoreFrustumCheck = true;
    this.experienceValue = 500;
    this.moveHelper = (EntityMoveHelper)new EntityFlyHelper((EntityLiving)this);
  }
  
  public String getDescName() {
    return "wither";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.LIGHT_PURPLE;
  }
  
  public int playMusic() {
    return 2;
  }
  
  public boolean attackable() {
    return (getInvulTime() <= 0 && super.attackable());
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    EntityTameBase.PathNavigateFlying pathnavigateflying = new EntityTameBase.PathNavigateFlying((EntityLiving)this, worldIn);
    pathnavigateflying.setCanOpenDoors(true);
    pathnavigateflying.setCanFloat(true);
    pathnavigateflying.setCanEnterDoors(true);
    return (PathNavigate)pathnavigateflying;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public boolean hasSpawnedSkeletons() {
    return ((Boolean)getDataManager().get(SPAWNEDSKELETONS)).booleanValue();
  }
  
  public void setCanSpawnSkeletons(boolean childZombie) {
    getDataManager().set(SPAWNEDSKELETONS, Boolean.valueOf(childZombie));
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setColor(BossInfo.Color.PURPLE);
    this.bossInfo.setDarkenSky(true);
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float p_70831_1_) {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / 30.0F;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getSpawnTimer() {
    return 1;
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 2.0F;
  }
  
  public float getBonusVSFlying() {
    return 4.0F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
    getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(3.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(FIRST_HEAD_TARGET, Integer.valueOf(0));
    this.dataManager.register(SECOND_HEAD_TARGET, Integer.valueOf(0));
    this.dataManager.register(THIRD_HEAD_TARGET, Integer.valueOf(0));
    this.dataManager.register(INVULNERABILITY_TIME, Integer.valueOf(0));
    this.dataManager.register(HOVERTIMER, Integer.valueOf(0));
    this.dataManager.register(RAMTIMER, Integer.valueOf(0));
    getDataManager().register(SPAWNEDSKELETONS, Boolean.valueOf(false));
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("SpawnSkeletons", hasSpawnedSkeletons());
    tagCompound.setInteger("Invul", getInvulTime());
    tagCompound.setInteger("Hover", getHoverTime());
    tagCompound.setInteger("Ram", getRamTime());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setCanSpawnSkeletons(tagCompund.getBoolean("SpawnSkeletons"));
    setInvulTime(tagCompund.getInteger("Invul"));
    setHoverTime(tagCompund.getInteger("Hover"));
    setRamTime(tagCompund.getInteger("Ram"));
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_WITHER_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_WITHER_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_WITHER_DEATH;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public float getEyeHeight() {
    return isSneaking() ? (this.height * 0.83F - 0.1F) : (this.height * 0.83F);
  }
  
  public double getMountedYOffset() {
    return this.height * 0.945D;
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
  
  public void handleStartJump(int p_184775_1_) {}
  
  public void handleStopJump() {}
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden() && canBeSteered()) {
      if (getInvulTime() > 0) {
        int j = getInvulTime() - 1;
        if (j <= 0) {
          createEngenderModExplosionFireless((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, isHero() ? 35.0F : 7.0F, EngenderConfig.mobs.grief);
          this.world.playBroadcastSound(1023, new BlockPos((Entity)this), 0);
        } 
        setInvulTime(j);
        if (this.ticksExisted % 1 == 0)
          heal(1.0F); 
      } 
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      entitylivingbase.fallDistance *= 0.0F;
      entitylivingbase.hurtResistantTime = 40;
      entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 4));
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      this.jumpMovementFactor = 0.2F;
      if (((EntityLivingBase)getControllingPassenger()).moveStrafing != 0.0F && this.ticksExisted % 10 == 0 && !this.world.isRemote) {
        Vec3d vec3 = entitylivingbase.getLook(1.0F);
        double d0 = entitylivingbase.posX + vec3.x * 50.0D;
        double d1 = entitylivingbase.posY + entitylivingbase.getEyeHeight() + vec3.y * 50.0D;
        double d2 = entitylivingbase.posZ + vec3.z * 50.0D;
        launchWitherSkullToCoords(0, d0, d1, d2, (this.rand.nextFloat() < 0.001F));
      } 
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } 
      int i;
      for (i = 0; i < 256; i++) {
        int in = MathHelper.floor(this.posX - 1.5D + this.rand.nextDouble() * 3.0D);
        int j = MathHelper.floor(this.posY + 3.0D - this.rand.nextDouble() * 8.0D);
        int k = MathHelper.floor(this.posZ - 1.5D + this.rand.nextDouble() * 3.0D);
        BlockPos blockpos = new BlockPos(in, j, k);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        if (block != Blocks.AIR)
          setPosition(this.posX, this.posY + 0.01D, this.posZ); 
      } 
      if (this.jumpPower > 0.0F) {
        this.motionY += this.jumpPower;
        if (isPotionActive(MobEffects.JUMP_BOOST))
          this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
        this.jumpPower = 0.0F;
      } 
      for (i = 1; i < 3; i++) {
        if (!this.world.isRemote && this.ticksExisted >= this.nextHeadUpdate[i - 1]) {
          this.nextHeadUpdate[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
          if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) {
            int k2 = i - 1;
            int l2 = this.idleHeadUpdates[i - 1];
            this.idleHeadUpdates[k2] = this.idleHeadUpdates[i - 1] + 1;
            if (l2 > 15) {
              float f = 10.0F;
              float f1 = 5.0F;
              double d0 = MathHelper.nextDouble(this.rand, this.posX - f, this.posX + f);
              double d1 = MathHelper.nextDouble(this.rand, this.posY - f1, this.posY + f1);
              double d2 = MathHelper.nextDouble(this.rand, this.posZ - f, this.posZ + f);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              this.idleHeadUpdates[i - 1] = 0;
            } 
          } 
          int i1 = getWatchedTargetId(i);
          if (i1 > 0) {
            Entity entity = this.world.getEntityByID(i1);
            if (!isSneaking() && entity != null && entity.isEntityAlive() && getDistanceSq(entity) <= 900.0D && canEntityBeSeen(entity)) {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.idleHeadUpdates[i - 1] = 0;
              if (this.moralRaisedTimer > 200) {
                this.nextHeadUpdate[i - 1] = this.ticksExisted + 20 + this.rand.nextInt(10);
              } else {
                this.nextHeadUpdate[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
              } 
            } else {
              updateWatchedTargetId(i, 0);
            } 
          } else {
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(20.0D, 8.0D, 20.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
            for (int k1 = 0; k1 < 10 && !list.isEmpty(); k1++) {
              EntityLivingBase entitylivingbase1 = list.get(this.rand.nextInt(list.size()));
              if (entitylivingbase1 != this && entitylivingbase1.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase1) && (!false || (entitylivingbase1 instanceof EntityTameBase && false && entitylivingbase1 != this && ((EntityTameBase)entitylivingbase1).getFakeHealth() > 0.0F && getFakeHealth() > 0.0F)) && entitylivingbase1 != getOwner())
                if (entitylivingbase1 instanceof EntityPlayer) {
                  if (!((EntityPlayer)entitylivingbase1).capabilities.disableDamage)
                    updateWatchedTargetId(i, entitylivingbase1.getEntityId()); 
                } else {
                  updateWatchedTargetId(i, entitylivingbase1.getEntityId());
                }  
              if (entitylivingbase1 == this || !entitylivingbase1.isEntityAlive() || !canEntityBeSeen((Entity)entitylivingbase1) || false || entitylivingbase1 == getOwner())
                list.remove(entitylivingbase1); 
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
    } else if (getAttackTarget() != null) {
      if (isInWater()) {
        moveRelative(strafe, vertical, forward, 0.02F);
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.800000011920929D;
        this.motionY *= 0.800000011920929D;
        this.motionZ *= 0.800000011920929D;
      } else if (isInLava()) {
        moveRelative(strafe, vertical, forward, 0.02F);
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.5D;
        this.motionY *= 0.5D;
        this.motionZ *= 0.5D;
      } else {
        float f = 0.95F;
        float f1 = 0.16277136F / f * f * f;
        moveRelative(strafe, vertical, forward, 0.02F);
        f = 0.95F;
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= f;
        this.motionY *= f;
        this.motionZ *= f;
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
  
  public void performSpecialAttack() {
    playLivingSound();
    playSound(ESound.witherSpecial, 10.0F, 1.0F);
    setSpecialAttackTimer(1600);
  }
  
  public boolean isImmuneToExplosions() {
    return (getInvulTime() > 1 || super.isImmuneToExplosions());
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public void becomeAHero() {
    super.becomeAHero();
    ignite();
  }
  
  public void onLivingUpdate() {
    if (!getCurrentBook().isEmpty()) {
      this.idleHeadUpdates[0] = 0;
      this.idleHeadUpdates[1] = 0;
    } 
    if (!isAIDisabled() && !this.world.isRemote) {
      if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false)
        if (getDistanceSq((Entity)getAttackTarget()) < (this.reachWidth * this.reachWidth + ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width) * ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width)) + 9.0D && (this.ticksExisted + getEntityId()) % 10 == 0)
          attackEntityAsMob((Entity)getAttackTarget());  
      if (isEntityAlive()) {
        ChunkLoadingEvent.updateLoaded((Entity)this);
      } else {
        ChunkLoadingEvent.stopLoading((Entity)this);
      } 
    } 
    setRamTime(getRamTime() - 1);
    setHoverTime(getHoverTime() - 1);
    if (this.hoverX == 0.0D)
      this.hoverX = this.posX; 
    if (this.hoverZ == 0.0D)
      this.hoverZ = this.posZ; 
    for (int i = 0; i < this.nextHeadUpdate.length; i++) {
      if (this.nextHeadUpdate[i] > 90)
        this.xRotationHeads[i] = rotlerp(this.xRotationHeads[i], 90.0F, 15.0F); 
    } 
    float f2 = this.renderYawOffset * 0.017453292F;
    float f19 = MathHelper.sin(f2);
    float f3 = MathHelper.cos(f2);
    double ex = (!this.onGround && !isBeingRidden() && !isInvisible()) ? (MathHelper.cos(this.ticksExisted * 0.2F) * 0.2F) : 0.0D;
    this.mainHead.onUpdate();
    this.mainHead.setLocationAndAngles(this.posX, this.posY + getEyeHeight() - 0.4D + ex, this.posZ, 0.0F, 0.0F);
    this.rightHead.onUpdate();
    this.rightHead.setLocationAndAngles(this.posX - (f3 * 1.15F), this.posY + getEyeHeight() - 0.7D + ex, this.posZ - (f19 * 1.15F), 0.0F, 0.0F);
    this.leftHead.onUpdate();
    this.leftHead.setLocationAndAngles(this.posX + (f3 * 1.15F), this.posY + getEyeHeight() - 0.7D + ex, this.posZ + (f19 * 1.15F), 0.0F, 0.0F);
    this.rightsupport.onUpdate();
    this.rightsupport.setLocationAndAngles(this.posX - (f3 * 0.5F), this.posY + getEyeHeight() - 0.8D + ex, this.posZ - (f19 * 0.5F), 0.0F, 0.0F);
    this.leftsupport.onUpdate();
    this.leftsupport.setLocationAndAngles(this.posX + (f3 * 0.5F), this.posY + getEyeHeight() - 0.8D + ex, this.posZ + (f19 * 0.5F), 0.0F, 0.0F);
    this.spine.onUpdate();
    this.spine.setLocationAndAngles(this.posX - (f19 * (-0.25F + MathHelper.cos(this.ticksExisted * 0.1F + 3.1415927F) * 0.1F)), this.posY + 0.75D + ex, this.posZ - (f3 * (0.25F - MathHelper.cos(this.ticksExisted * 0.1F + 3.1415927F) * 0.1F)), 0.0F, 0.0F);
    this.lowerspine.onUpdate();
    this.lowerspine.setLocationAndAngles(this.posX - (f19 * (-0.75F + MathHelper.cos(this.ticksExisted * 0.1F - 1.0F + 3.1415927F) * 0.2F)), this.posY + 0.25D + ex, this.posZ - (f3 * (0.75F - MathHelper.cos(this.ticksExisted * 0.1F - 1.0F + 3.1415927F) * 0.2F)), 0.0F, 0.0F);
    this.rightRibs.onUpdate();
    this.rightRibs.setLocationAndAngles(this.spine.posX + (f3 * 0.375F), this.posY + 0.95D + ex, this.spine.posZ + (f19 * 0.375F), 0.0F, 0.0F);
    this.leftRibs.onUpdate();
    this.leftRibs.setLocationAndAngles(this.spine.posX - (f3 * 0.375F), this.posY + 0.95D + ex, this.spine.posZ - (f19 * 0.375F), 0.0F, 0.0F);
    if (!this.world.isRemote && getInvulTime() <= 1) {
      if (isArmored() && !hasSpawnedSkeletons() && getFakeHealth() <= 0.0F) {
        this.motionY = -(0.9D + this.motionY);
      } else if (getWatchedTargetId(0) > 0 && getControllingPassenger() == null && !(getControllingPassenger() instanceof EntityPlayer)) {
        Entity entity = this.world.getEntityByID(getWatchedTargetId(0));
        if (entity != null) {
          if (entity instanceof EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) {
            for (int m = 0; m < 256; m++) {
              int in = MathHelper.floor(this.posX - 1.5D + this.rand.nextDouble() * 3.0D);
              int n = MathHelper.floor(this.posY + 3.0D - this.rand.nextDouble() * 8.0D);
              int i1 = MathHelper.floor(this.posZ - 1.5D + this.rand.nextDouble() * 3.0D);
              BlockPos blockpos = new BlockPos(in, n, i1);
              IBlockState iblockstate = this.world.getBlockState(blockpos);
              Block block = iblockstate.getBlock();
              if (block != Blocks.AIR)
                setPosition(this.posX, this.posY + 0.01D, this.posZ); 
            } 
            if (getDistance(entity) > 4.0D && getRamTime() <= 0) {
              this.hoverX = entity.posX;
              this.hoverZ = entity.posZ;
              if (!(entity instanceof EntityWither) && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) && this.posY < entity.posY) {
                this.onGround = false;
                this.isAirBorne = true;
                this.motionY += 0.5D - this.motionY;
              } 
              if (!(entity instanceof EntityWither) && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) && this.posY > entity.posY) {
                this.onGround = false;
                this.isAirBorne = true;
                this.motionY -= 0.5D + this.motionY;
              } 
            } 
          } 
          if ((entity instanceof EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) && ((EntityLiving)entity).getAttackTarget() != this) {
            if (this.posY < entity.posY - 1.0D) {
              this.onGround = false;
              this.isAirBorne = true;
              this.motionY += 0.5D - this.motionY;
            } 
            if (this.posY > entity.posY + 1.0D) {
              this.onGround = false;
              this.isAirBorne = true;
              this.motionY -= 0.5D + this.motionY;
            } 
          } else {
            if (this.posY < (isArmored() ? (entity.posY - ((hasSpawnedSkeletons() && getFakeHealth() <= 0.0F) ? 8.0D : 1.0D)) : (entity.posY + 4.0D + entity.getEyeHeight()))) {
              this.onGround = false;
              this.isAirBorne = true;
              this.motionY += 0.5D - this.motionY;
            } 
            if (this.posY > entity.posY + (isArmored() ? (entity.posY - ((hasSpawnedSkeletons() && getFakeHealth() <= 0.0F) ? 8.0D : 1.0D)) : (entity.posY + 5.0D + entity.getEyeHeight()))) {
              this.onGround = false;
              this.isAirBorne = true;
              this.motionY -= 0.5D + this.motionY;
            } 
          } 
          faceEntity(entity, 180.0F, 40.0F);
          if ((getHoverTime() > 40 && !isArmored()) || (isArmored() && getRamTime() < 140 && getRamTime() > 100)) {
            double d0 = this.hoverX - this.posX;
            double d1 = this.hoverZ - this.posZ;
            double d3 = d0 * d0 + d1 * d1;
            if (d3 > 36.0D) {
              double d5 = MathHelper.sqrt(d3);
              if (this.moralRaisedTimer > 200 || isArmored() || (getRamTime() < 140 && getRamTime() > 100)) {
                this.motionX += d0 / d5 * 1.8D - this.motionX;
                this.motionZ += d1 / d5 * 1.8D - this.motionZ;
              } else {
                this.motionX += d0 / d5 * 0.6D - this.motionX;
                this.motionZ += d1 / d5 * 0.6D - this.motionZ;
              } 
            } 
          } 
        } 
      } 
      if (isArmored()) {
        if (!hasSpawnedSkeletons() && getFakeHealth() <= 0.0F && isArmored() && this.onGround) {
          createEngenderModExplosionFireless((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, isHero() ? 35.0F : 7.0F, EngenderConfig.mobs.grief);
          this.world.playBroadcastSound(1023, new BlockPos((Entity)this), 0);
          for (int a = 0; a < 4; a++) {
            EntitySkeleton entityliving = new EntitySkeleton(this.world);
            entityliving.copyLocationAndAnglesFrom((Entity)this);
            entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            entityliving.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityliving)), null);
            entityliving.setSkeletonType(1);
            entityliving.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
            if (entityliving.getRNG().nextInt(2) > 0)
              entityliving.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.STONE_SWORD)); 
            this.world.spawnEntity((Entity)entityliving);
            entityliving.setOwnerId(getOwnerId());
          } 
          setCanSpawnSkeletons(true);
        } 
        if (getRamTime() <= 0 && getAttackTarget() != null) {
          setRamTime(200);
          faceEntity((Entity)getAttackTarget(), 180.0F, 0.0F);
        } 
        if (getRamTime() == 120) {
          double d1 = (getAttackTarget() != null && getDistance((Entity)getAttackTarget()) > 24.0D) ? getDistance((Entity)getAttackTarget()) : 24.0D;
          Vec3d vec3d = getLook(1.0F);
          this.hoverX = this.posX + vec3d.x * d1;
          this.hoverZ = this.posZ + vec3d.z * d1;
        } 
        if (getRamTime() < 140 && getRamTime() > 100) {
          this.motionY = -0.1D;
          double d0 = ((getEntityBoundingBox()).minX + (getEntityBoundingBox()).maxX) * 5.0D;
          double d1 = ((getEntityBoundingBox()).minY + (getEntityBoundingBox()).maxY) * 2.0D;
          double d2 = ((getEntityBoundingBox()).minZ + (getEntityBoundingBox()).maxZ) * 5.0D;
          List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(5.0D));
          if (!list.isEmpty())
            for (int i1 = 0; i1 < list.size(); i1++) {
              Entity e = list.get(i1);
              if (e.isEntityAlive() && e instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase)e;
                if (!false) {
                  double d3 = entity.posX - d0;
                  double d4 = entity.posZ - d1;
                  double d5 = entity.posZ - d2;
                  double d6 = d3 * d3 + d4 * d4 + d5 * d5;
                  inflictEngenderMobDamage(entity, " was rammed into by ", DamageSource.causeMobDamage((EntityLivingBase)this), (float)getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() * 2.0F);
                  entity.renderYawOffset = entity.rotationYaw = entity.rotationYawHead = (float)MathHelper.atan2(entity.motionZ, entity.motionX) * 57.295776F - 90.0F;
                  entity.setRevengeTarget(null);
                  if (entity instanceof EntityLiving)
                    ((EntityLiving)entity).setAttackTarget(null); 
                  entity.addVelocity(d3 / d6 * 5.0D, d4 / d6 * 1.0D, d5 / d6 * 5.0D);
                } 
              } 
            }  
          if (!this.world.isRemote && EngenderConfig.mobs.grief) {
            int i11 = MathHelper.floor(this.posY);
            int l1 = MathHelper.floor(this.posX);
            int i2 = MathHelper.floor(this.posZ);
            boolean bool = false;
            for (int k2 = -2; k2 <= 2; k2++) {
              for (int l2 = -2; l2 <= 2; l2++) {
                for (int m = -1; m <= 3; m++) {
                  int i3 = l1 + k2;
                  int n = i11 + m;
                  int l = i2 + l2;
                  BlockPos blockpos = new BlockPos(i3, n, l);
                  IBlockState iblockstate = this.world.getBlockState(blockpos);
                  Block block = iblockstate.getBlock();
                  if (!block.isAir(iblockstate, (IBlockAccess)this.world, blockpos)) {
                    if (canDestroyBlock(block))
                      bool = (this.world.destroyBlock(blockpos, true) || bool); 
                  } 
                } 
              } 
            } 
            if (bool)
              this.world.playEvent((EntityPlayer)null, 1022, new BlockPos((Entity)this), 0); 
          } 
        } 
      } else {
        if (!this.world.isRemote && getAttackTarget() != null && getHoverTime() <= 120 && getHoverTime() >= 60 && this.ticksExisted % 20 == 0)
          attackEntityWithRangedAttack(getAttackTarget(), 1.0F); 
        if (getAttackTarget() != null && getHoverTime() <= 0) {
          setHoverTime(120);
          this.hoverX = (getAttackTarget()).posX + this.rand.nextGaussian() * 10.0D;
          this.hoverZ = (getAttackTarget()).posZ + this.rand.nextGaussian() * 10.0D;
        } 
      } 
    } 
    if (isSneaking() || (getRamTime() < 180 && getRamTime() > 100 && this.height != 2.3F)) {
      setSize(0.9F, 2.3F);
    } else if (this.height != 3.3F) {
      setSize(0.9F, 3.3F);
    } 
    if (this.motionY < 0.0D || (getAttackTarget() != null && !isBeingRidden()))
      this.motionY *= 0.6D; 
    if (isSneaking() || getInvulTime() > 0)
      setAttackTarget(null); 
    if (this.posY < 0.0D)
      this.motionY += (0.5D - this.motionY) * 0.6000000238418579D; 
    super.onLivingUpdate();
    int k;
    for (k = 0; k < 2; k++) {
      this.yRotOHeads[k] = this.yRotationHeads[k];
      this.xRotOHeads[k] = this.xRotationHeads[k];
    } 
    for (k = 0; k < 2; k++) {
      if (getAttackTarget() != null)
        this.headRandomTurn[k] = 0.0F; 
      int m = getWatchedTargetId(k + 1);
      Entity entity1 = null;
      if (m > 0)
        entity1 = this.world.getEntityByID(m); 
      if (entity1 != null) {
        double d1 = getHeadX(k + 1);
        double d3 = getHeadY(k + 1);
        double d5 = getHeadZ(k + 1);
        double d6 = entity1.posX - d1;
        double d7 = entity1.posY + entity1.getEyeHeight() - d3;
        double d8 = entity1.posZ - d5;
        double d9 = MathHelper.sqrt(d6 * d6 + d8 * d8);
        float f = (float)(Math.atan2(d8, d6) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)-(Math.atan2(d7, d9) * 180.0D / Math.PI);
        this.xRotationHeads[k] = rotlerp(this.xRotationHeads[k], f1, 40.0F);
        this.yRotationHeads[k] = rotlerp(this.yRotationHeads[k], f, 10.0F);
      } else {
        if ((this.ticksExisted + getEntityId()) % 400 == 0)
          this.headRandomTurn[0] = this.rand.nextFloat() * 360.0F - 180.0F; 
        if ((this.ticksExisted + getEntityId()) % 400 == 0)
          this.headRandomTurn[1] = this.rand.nextFloat() * 360.0F - 180.0F; 
        this.yRotationHeads[k] = rotlerp(this.yRotationHeads[k], this.prevRenderYawOffset + this.renderYawOffset - this.prevRenderYawOffset + this.headRandomTurn[k], 2.0F);
      } 
    } 
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (isHero() && getSpecialAttackTimer() == 1400) {
      createEngenderModExplosionFireless((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false);
      this.world.playBroadcastSound(1023, new BlockPos((Entity)this), 0);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(128.0D, 128.0D, 128.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!false && entity.isNonBoss() && !(entity instanceof EntityTameBase)) {
              entity.hurtResistantTime = 0;
              entity.attackEntityFrom(DamageSource.GENERIC, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.ANVIL, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.CACTUS, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.DRAGON_BREATH, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.DROWN, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.FALL, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.FALLING_BLOCK, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.IN_FIRE, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.IN_WALL, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.LAVA, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.MAGIC, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.ON_FIRE, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.STARVE, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.WITHER, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.HOT_FLOOR, Float.MAX_VALUE);
              entity.attackEntityFrom(DamageSource.FIREWORKS, Float.MAX_VALUE);
              entity.attackEntityFrom((new DamageSource("generic")).setDamageBypassesArmor().setDamageIsAbsolute(), Float.MAX_VALUE);
              if (EngenderConfig.general.useMessage && !entity.isEntityAlive() && !isWild()) {
                entity.setDead();
                getOwner().sendMessage((ITextComponent)new TextComponentTranslation(entity.getName() + " doesn't exist anymore...", new Object[0]));
              } 
            } else {
              list.remove(entity);
            }  
        }  
    } 
    if (this.world.isRemote) {
      for (k = 0; k < 2; k++)
        this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]); 
      if (isHero() && getSpecialAttackTimer() >= 1400)
        for (int i1 = 0; i1 < 128; i1++)
          this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 1.75D, this.posZ, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D, new int[0]);  
    } 
    boolean flag = isArmored();
    int j;
    for (j = 0; j < 3; j++) {
      double d10 = getHeadX(j);
      double d2 = getHeadY(j);
      double d4 = getHeadZ(j);
      this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      if (flag && this.world.rand.nextInt(4) == 0)
        this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]); 
    } 
    if (isInWater())
      for (j = 0; j < this.partArray.length; j++)
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (this.partArray[j]).posX + this.rand.nextGaussian() * ((this.partArray[j]).width / 2.0F), (this.partArray[j]).posY + this.rand.nextDouble() * ((this.partArray[j]).width / 2.0F), (this.partArray[j]).posZ + this.rand.nextGaussian() * ((this.partArray[j]).width / 2.0F), 0.0D, 0.0D, 0.0D, new int[0]);  
    if (getInvulTime() > 0)
      for (j = 0; j < 3; j++)
        this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + (this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);  
  }
  
  protected void updateAITasks() {
    if (getInvulTime() > 0) {
      int i = getInvulTime() - 1;
      if (i <= 0) {
        createEngenderModExplosionFireless((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, isHero() ? 35.0F : 7.0F, EngenderConfig.mobs.grief);
        this.world.playBroadcastSound(1023, new BlockPos((Entity)this), 0);
      } 
      setInvulTime(i);
      if (this.ticksExisted % 1 == 0)
        heal(1.0F); 
    } else {
      super.updateAITasks();
      if (getAttackTarget() == null && isArmored() && this.rand.nextInt(400) == 0) {
        setInvulTime(220);
        this.ticksExisted = 0;
        playLivingSound();
        playSound(ESound.createMob, 10.0F, 0.75F);
        playSound(ESound.createBossMob, 1.0E7F, 1.0F);
      } 
      for (int i = 1; i < 3; i++) {
        if (this.ticksExisted >= this.nextHeadUpdate[i - 1]) {
          this.nextHeadUpdate[i - 1] = this.ticksExisted + 20 + this.rand.nextInt(20);
          if (this.world.getDifficulty() == EnumDifficulty.HARD) {
            int k2 = i - 1;
            int l2 = this.idleHeadUpdates[i - 1];
            this.idleHeadUpdates[k2] = this.idleHeadUpdates[i - 1] + 1;
            if (l2 > 15) {
              float f = 10.0F;
              float f1 = 5.0F;
              double d0 = MathHelper.nextDouble(this.rand, this.posX - f, this.posX + f);
              double d1 = MathHelper.nextDouble(this.rand, this.posY - f1, this.posY + f1);
              double d2 = MathHelper.nextDouble(this.rand, this.posZ - f, this.posZ + f);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              this.idleHeadUpdates[i - 1] = 0;
            } 
          } 
          int i1 = getWatchedTargetId(i);
          if (i1 > 0) {
            Entity entity = this.world.getEntityByID(i1);
            if (!isSneaking() && entity != null && entity.isEntityAlive() && getDistanceSq(entity) <= 2000.0D && canEntityBeSeen(entity)) {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.idleHeadUpdates[i - 1] = 0;
              if (this.moralRaisedTimer > 200) {
                this.nextHeadUpdate[i - 1] = this.ticksExisted + 20 + this.rand.nextInt(10) - (int)(getEntityAttribute(DEXTERITY).getBaseValue() * 0.1D);
              } else {
                this.nextHeadUpdate[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20) - (int)(getEntityAttribute(DEXTERITY).getBaseValue() * 0.2D);
              } 
            } else {
              updateWatchedTargetId(i, 0);
            } 
          } else {
            List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(32.0D, 32.0D, 32.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
            for (int k1 = 0; k1 < 10 && !list.isEmpty(); k1++) {
              EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
              if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase) && (!false || (getAttackTarget() != null && this.rand.nextInt(120) == 0))) {
                updateWatchedTargetId(i, entitylivingbase.getEntityId());
              } else if (!entitylivingbase.isEntityAlive() || !canEntityBeSeen((Entity)entitylivingbase) || (false && this.rand.nextInt(80) == 0)) {
                list.remove(entitylivingbase);
              } 
            } 
          } 
        } 
      } 
      if (getAttackTarget() != null) {
        updateWatchedTargetId(0, getAttackTarget().getEntityId());
      } else {
        updateWatchedTargetId(0, 0);
      } 
      if (this.blockBreakCounter > 0) {
        this.blockBreakCounter--;
        if (this.blockBreakCounter == 0 && EngenderConfig.mobs.grief) {
          int i11 = MathHelper.floor(this.posY);
          int l1 = MathHelper.floor(this.posX);
          int i2 = MathHelper.floor(this.posZ);
          boolean flag = false;
          for (int k2 = -1; k2 <= 1; k2++) {
            for (int l2 = -1; l2 <= 1; l2++) {
              for (int j = 0; j <= 3; j++) {
                int i3 = l1 + k2;
                int k = i11 + j;
                int l = i2 + l2;
                BlockPos blockpos = new BlockPos(i3, k, l);
                IBlockState iblockstate = this.world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();
                if (!block.isAir(iblockstate, (IBlockAccess)this.world, blockpos) && canDestroyBlock(block))
                  flag = (this.world.destroyBlock(blockpos, true) || flag); 
              } 
            } 
          } 
          if (flag)
            this.world.playEvent((EntityPlayer)null, 1022, new BlockPos((Entity)this), 0); 
        } 
      } 
      if (this.ticksExisted % 20 == 0)
        heal(1.0F); 
    } 
  }
  
  public static boolean canDestroyBlock(Block p_181033_0_) {
    return (p_181033_0_ != Blocks.BEDROCK && p_181033_0_ != Blocks.END_PORTAL && p_181033_0_ != Blocks.END_PORTAL_FRAME && p_181033_0_ != Blocks.COMMAND_BLOCK && p_181033_0_ != Blocks.REPEATING_COMMAND_BLOCK && p_181033_0_ != Blocks.CHAIN_COMMAND_BLOCK && p_181033_0_ != Blocks.BARRIER);
  }
  
  public void ignite() {
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    if (super.attackEntityAsMob(p_70652_1_)) {
      if (p_70652_1_ instanceof EntityLivingBase)
        inflictCustomStatusEffect(this.world.getDifficulty(), (EntityLivingBase)p_70652_1_, MobEffects.WITHER, 10, 1); 
      if (!p_70652_1_.isEntityAlive()) {
        heal(10.0F);
      } else {
        applyEnchantments((EntityLivingBase)this, p_70652_1_);
      } 
      return true;
    } 
    return false;
  }
  
  private double getHeadX(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.posX; 
    float f = (this.renderYawOffset + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.25D * getEntityAttribute(FITTNESS).getAttributeValue();
  }
  
  private double getHeadY(int p_82208_1_) {
    return isSneaking() ? (this.posY + 1.25D * getEntityAttribute(FITTNESS).getAttributeValue()) : (this.posY + 2.25D * getEntityAttribute(FITTNESS).getAttributeValue());
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
  
  public void setInWeb() {}
  
  private double getHeadZ(int p_82213_1_) {
    if (p_82213_1_ <= 0)
      return this.posZ; 
    float f = (this.renderYawOffset + (180 * (p_82213_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.25D * getEntityAttribute(FITTNESS).getAttributeValue();
  }
  
  private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
    float f3 = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);
    if (f3 > p_82204_3_)
      f3 = p_82204_3_; 
    if (f3 < -p_82204_3_)
      f3 = -p_82204_3_; 
    return p_82204_1_ + f3;
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
    if (!this.world.isRemote && getPolymorphTime() > 0 && this.rand.nextBoolean()) {
      float j = this.renderYawOffset * 0.017453292F;
      float f1 = MathHelper.cos(j);
      float f2 = MathHelper.sin(j);
      double d3 = getHeadX(p_82216_1_);
      double d4 = getHeadY(p_82216_1_);
      double d5 = getHeadZ(p_82216_1_);
      EntityInvisibleFangsProjectile entitymagicmissiles = new EntityInvisibleFangsProjectile(this.world, (Entity)p_82216_2_, (EntityLivingBase)this, d3, d4, d5);
      this.world.spawnEntity((Entity)entitymagicmissiles);
    } else {
      launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX + p_82216_2_.motionX * p_82216_2_.motionX, p_82216_2_.posY + ((p_82216_2_.height > 8.0F) ? 7.0D : (p_82216_2_.height * 0.5D)), p_82216_2_.posZ + p_82216_2_.motionZ * p_82216_2_.motionZ, (p_82216_1_ == 0 && !p_82216_2_.canEntityBeSeen((Entity)this)));
    } 
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_) {
    playSound(SoundEvents.ENTITY_WITHER_SHOOT, getSoundVolume(), 1.0F);
    double d3 = getHeadX(p_82209_1_);
    double d4 = getHeadY(p_82209_1_);
    double d5 = getHeadZ(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityWitherSkullOther entitywitherskull = new EntityWitherSkullOther(this.world, (EntityLivingBase)this, d6, d7, d8);
    if (p_82209_8_)
      entitywitherskull.setInvulnerable(true); 
    float f = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    entitywitherskull.damage = f;
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    if (!this.world.isRemote)
      this.world.spawnEntity((Entity)entitywitherskull); 
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
    launchWitherSkullToEntity(0, p_82196_1_);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_WITHER;
  }
  
  protected void despawnEntity() {
    this.idleTime = 0;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 2.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }
  
  public void addPotionEffect(PotionEffect potioneffectIn) {
    if (!potioneffectIn.getPotion().isBadEffect())
      super.addPotionEffect(potioneffectIn); 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadYRotation(int p_82207_1_) {
    return this.yRotationHeads[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadXRotation(int p_82210_1_) {
    return this.xRotationHeads[p_82210_1_];
  }
  
  public int getHoverTime() {
    return ((Integer)this.dataManager.get(HOVERTIMER)).intValue();
  }
  
  public void setHoverTime(int p_82215_1_) {
    this.dataManager.set(HOVERTIMER, Integer.valueOf(p_82215_1_));
  }
  
  public int getRamTime() {
    return ((Integer)this.dataManager.get(RAMTIMER)).intValue();
  }
  
  public void setRamTime(int p_82215_1_) {
    this.dataManager.set(RAMTIMER, Integer.valueOf(p_82215_1_));
  }
  
  public int getInvulTime() {
    return ((Integer)this.dataManager.get(INVULNERABILITY_TIME)).intValue();
  }
  
  public void setInvulTime(int p_82215_1_) {
    this.dataManager.set(INVULNERABILITY_TIME, Integer.valueOf(p_82215_1_));
  }
  
  public int getWatchedTargetId(int p_82203_1_) {
    return ((Integer)this.dataManager.get(HEAD_TARGETS[p_82203_1_])).intValue();
  }
  
  public void updateWatchedTargetId(int targetOffset, int newId) {
    this.dataManager.set(HEAD_TARGETS[targetOffset], Integer.valueOf(newId));
  }
  
  public boolean isArmored() {
    return (getHealth() <= getMaxHealth() / 2.0F || (getFakeHealth() <= getMaxHealth() && getFakeHealth() > 0.0F));
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
  
  protected void onDeathUpdate() {
    this.lastActiveTime = this.timeSinceIgnited;
    this.timeSinceIgnited = ++this.deathTicks;
    setRamTime(0);
    setHoverTime(0);
    if (!this.world.isRemote && this.deathTicks == 120) {
      playSound(ESound.blast, 10.0F, 1.0F);
      createEngenderModExplosionFireless((Entity)this, this.posX, this.posY + 1.0D, this.posZ, 14.0F, EngenderConfig.mobs.grief);
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(128.0D));
      this.world.playEvent(3000, getPosition(), 0);
      playSound(ESound.blast, 10.0F, 1.0F);
      if (list != null && !list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          Entity entity = list.get(i);
          double scale = (128.0D - entity.getDistance(this.posX, this.posY, this.posZ)) / 128.0D;
          Vec3d dir = new Vec3d(entity.posX - this.posX, entity.posY - this.posY, entity.posZ - this.posZ);
          dir = dir.normalize();
          if (entity instanceof EntityLivingBase)
            if (entity.isEntityAlive()) {
              if (entity.getDistance((Entity)this) <= 16.0D) {
                entity.hurtResistantTime = 0;
                inflictEngenderMobDamage((EntityLivingBase)entity, " was blown up by ", DamageSource.causeExplosionDamage((EntityLivingBase)this), 96.0F);
              } 
              entity.addVelocity(dir.x * 2.5D * scale, 1.5D + this.rand.nextDouble(), dir.z * 2.5D * scale);
            } else {
              entity.addVelocity(dir.x * 2.5D * scale, 1.5D + this.rand.nextDouble(), dir.z * 2.5D * scale);
            }  
        }  
      if (!this.world.isRemote && canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")) {
        int i = getExperiencePoints(this.attackingPlayer);
        i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.attackingPlayer, i);
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY + 8.0D, this.posZ, j));
        } 
      } 
      for (int k = 0; k < 20; k++) {
        double d2 = this.rand.nextGaussian() * 0.02D;
        double d0 = this.rand.nextGaussian() * 0.02D;
        double d1 = this.rand.nextGaussian() * 0.02D;
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, d0, d1, new int[0]);
      } 
      setDead();
    } 
    if (!this.world.isRemote)
      if (this.deathTicks == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.world.playerEntities) {
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F);
            entityplayer.sendStatusMessage((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().getName() + "'s " + getName() + " has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).sendMessage((ITextComponent)new TextComponentTranslation("Your " + getName() + " has been destroyed!", new Object[0]));
        }   
  }
  
  public boolean canBeCollidedWith() {
    return true;
  }
  
  public World getWorld() {
    return this.world;
  }
  
  public Entity[] getParts() {
    return (Entity[])this.partArray;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return attackEntityFromPart((MultiPartEntityPart)this.mainHead, source, amount);
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart witherPart, DamageSource source, float damage) {
    if (isArmored())
      damage = damage / 2.0F + Math.min(damage, 1.0F); 
    if (witherPart == this.spine || witherPart == this.rightRibs || witherPart == this.leftRibs)
      damage = damage / 10.0F + Math.min(damage, 1.0F); 
    if (witherPart == this.lowerspine || witherPart == this.rightsupport || witherPart == this.leftsupport)
      damage = damage / 2.0F + Math.min(damage, 1.0F); 
    if (witherPart == this.rightHead && !source.isExplosion())
      this.nextHeadUpdate[0] = this.nextHeadUpdate[0] + 200; 
    if (witherPart == this.leftHead && !source.isExplosion())
      this.nextHeadUpdate[1] = this.nextHeadUpdate[1] + 200; 
    if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase)
      if (!isWild() && source.getTrueSource() == getOwner()) {
        updateWatchedTargetId(1, 0);
        updateWatchedTargetId(2, 0);
      }  
    if (isEntityInvulnerable(source) || getInvulTime() > 0 || damage < 0.01F || source == DamageSource.WITHER || source == DamageSource.IN_WALL || source == DamageSource.DROWN || source == DamageSource.CRAMMING || source == DamageSource.CACTUS)
      return false; 
    if (isArmored() && source.isProjectile())
      return false; 
    super.attackEntityFrom(source, damage);
    if (this.blockBreakCounter <= 0)
      this.blockBreakCounter = 20; 
    int i;
    for (i = 0; i < this.idleHeadUpdates.length; i++)
      this.idleHeadUpdates[i] = this.idleHeadUpdates[i] + 3; 
    if (!source.isMagicDamage())
      for (i = 0; i < this.nextHeadUpdate.length; i++) {
        if (this.nextHeadUpdate[i] <= 80)
          this.nextHeadUpdate[i] = this.nextHeadUpdate[i] - 20; 
      }  
    return true;
  }
  
  class AIDoNothing extends EntityAIBase {
    public AIDoNothing() {
      setMutexBits(7);
    }
    
    public boolean shouldExecute() {
      return (EntityWither.this.getInvulTime() > 0);
    }
  }
}

