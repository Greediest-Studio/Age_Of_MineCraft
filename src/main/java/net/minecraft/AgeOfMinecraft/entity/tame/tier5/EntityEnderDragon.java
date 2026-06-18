package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityBodyHelperDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityDragonFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.IPhase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseFireballAndStrafe;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseList;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseManager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseRamAttack;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEnderDragon extends EntityTameBase implements IEntityMultiPart, Massive, Flying, Armored, Ender {
  private static final DataParameter<Boolean> CARRIES_CRYSTAL = EntityDataManager.createKey(EntityEnderDragon.class, DataSerializers.BOOLEAN);
  
  public static final DataParameter<Integer> PHASE = EntityDataManager.createKey(EntityEnderDragon.class, DataSerializers.VARINT);
  
  public int innerRotation;
  
  public double[][] ringBuffer = new double[64][3];
  
  public int ringBufferIndex = -1;
  
  public ExtendMultiPartEntityPart[] dragonPartArray;
  
  public ExtendMultiPartEntityPart dragonPartHead;
  
  public ExtendMultiPartEntityPart dragonPartNeck;
  
  public ExtendMultiPartEntityPart dragonPartBody;
  
  public ExtendMultiPartEntityPart dragonPartTail1;
  
  public ExtendMultiPartEntityPart dragonPartTail2;
  
  public ExtendMultiPartEntityPart dragonPartTail3;
  
  public ExtendMultiPartEntityPart dragonPartWing1;
  
  public ExtendMultiPartEntityPart dragonPartWing2;
  
  double sitPosX;
  
  double sitPosY;
  
  double sitPosZ;
  
  public float prevAnimTime;
  
  public float animTime;
  
  public boolean slowed;
  
  public boolean sitting;
  
  private final PhaseManager phaseManager;
  
  private int growlTime = 200;
  
  private int sittingDamageReceived;
  
  private final PathPoint[] pathPoints = new PathPoint[24];
  
  private final int[] neighbors = new int[24];
  
  private final PathHeap pathFindQueue = new PathHeap();
  
  public EntityEnderDragon(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 6.0F, 6.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 6.0F, 6.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F) };
    setHealth(getMaxHealth());
    setSize(16.0F, 3.6F);
    this.noClip = true;
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    this.phaseManager = new PhaseManager(this);
    getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
    this.forceSpawn = true;
    this.experienceValue = 500;
  }
  
  public String getDescName() {
    return "ender_dragon";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.LIGHT_PURPLE;
  }
  
  public int playMusic() {
    return 6;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public void onUpdate() {
    ItemStack charge = isCarryingCrystal() ? new ItemStack(Items.END_CRYSTAL) : ItemStack.EMPTY;
    charge.setStackDisplayName("Carrying Crystal");
    this.basicInventory.setInventorySlotContents(7, charge);
    super.onUpdate();
    this.innerRotation++;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setColor(BossInfo.Color.PINK);
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 3.0F;
  }
  
  public float getBonusVSMassive() {
    return 2.0F;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.ENDER;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(27.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(PHASE, Integer.valueOf(PhaseList.HOLDING_PATTERN.getId()));
    getDataManager().register(CARRIES_CRYSTAL, Boolean.valueOf(false));
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public boolean isCarryingCrystal() {
    return ((Boolean)getDataManager().get(CARRIES_CRYSTAL)).booleanValue();
  }
  
  public void setCarryingCrystal(boolean childZombie) {
    getDataManager().set(CARRIES_CRYSTAL, Boolean.valueOf(childZombie));
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public float getEyeHeight() {
    return 2.325F;
  }
  
  public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_) {
    if (getHealth() <= 0.0F)
      p_70974_2_ = 0.0F; 
    p_70974_2_ = 1.0F - p_70974_2_;
    int i = this.ringBufferIndex - p_70974_1_ & 0x3F;
    int j = this.ringBufferIndex - p_70974_1_ - 1 & 0x3F;
    double[] adouble = new double[3];
    double d0 = this.ringBuffer[i][0];
    double d1 = MathHelper.wrapDegrees(this.ringBuffer[j][0] - d0);
    adouble[0] = d0 + d1 * p_70974_2_;
    d0 = this.ringBuffer[i][1];
    d1 = this.ringBuffer[j][1] - d0;
    adouble[1] = d0 + d1 * p_70974_2_;
    adouble[2] = this.ringBuffer[i][2] + (this.ringBuffer[j][2] - this.ringBuffer[i][2]) * p_70974_2_;
    return adouble;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_ENDER_DRAGON;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() == Items.ENDER_EYE && (hasOwner(player) || false)) {
      playSound(getAmbientSound(), 0.25F, 0.75F);
      if (!isBeingRidden())
        if (this.sitting) {
          this.sitting = false;
        } else {
          this.sitting = true;
          this.sitPosX = this.posX;
          this.sitPosY = this.posY;
          this.sitPosZ = this.posZ;
        }  
      return true;
    } 
    if (!stack.isEmpty() && stack.getItem() == Items.END_CRYSTAL && (hasOwner(player) || false)) {
      if (!isCarryingCrystal()) {
        setCarryingCrystal(true);
        playLivingSound();
        this.world.playEvent(3000, getPosition(), 0);
        if (!player.capabilities.isCreativeMode)
          stack.shrink(1); 
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
  
  public double getMountedYOffset() {
    return 3.25D;
  }
  
  protected boolean canFitPassenger(Entity passenger) {
    return (getPassengers().size() < 5);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      int i = getPassengers().indexOf(passenger);
      float f3 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f11 = MathHelper.sin(f3);
      float f4 = MathHelper.cos(f3);
      if (i == 5)
        passenger.setPosition(this.dragonPartTail3.posX, this.dragonPartTail3.posY + 1.0D, this.dragonPartTail3.posZ); 
      if (i == 4)
        passenger.setPosition(this.dragonPartTail2.posX, this.dragonPartTail2.posY + 1.0D, this.dragonPartTail2.posZ); 
      if (i == 3)
        passenger.setPosition(this.dragonPartTail1.posX, this.dragonPartTail1.posY + 1.0D, this.dragonPartTail1.posZ); 
      if (i == 2)
        passenger.setPosition(this.posX + (f11 * 1.0F), this.posY + getEyeHeight() + 1.0D, this.posZ - (f4 * 1.0F)); 
      if (i == 1)
        passenger.setPosition(this.posX + (f11 * -1.0F), this.posY + getEyeHeight() + 1.0D, this.posZ - (f4 * -1.0F)); 
      if (i == 0)
        passenger.setPosition(this.dragonPartNeck.posX, this.dragonPartNeck.posY + 1.0D, this.dragonPartNeck.posZ); 
    } 
  }
  
  public void performSpecialAttack() {
    playLivingSound();
    playLivingSound();
    playLivingSound();
    playLivingSound();
    playLivingSound();
    playLivingSound();
    playLivingSound();
    playLivingSound();
    setSpecialAttackTimer(2000);
  }
  
  public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
    double d1, d0 = entityIn.posX - this.posX;
    double d2 = entityIn.posZ - this.posZ;
    if (entityIn instanceof EntityLivingBase) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
      d1 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - this.posY + getEyeHeight();
    } else {
      d1 = ((entityIn.getEntityBoundingBox()).minY + (entityIn.getEntityBoundingBox()).maxY) / 2.0D - this.posY + getEyeHeight();
    } 
    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
    float f = (float)(MathHelper.atan2(d2, d0) * 57.29577951308232D) - 90.0F;
    float f1 = (float)-(MathHelper.atan2(d1, d3) * 57.29577951308232D);
    this.rotationPitch = updateRotation(this.rotationPitch, f1, maxPitchIncrease);
    this.rotationYawHead = updateRotation(this.rotationYawHead, f, maxYawIncrease);
  }
  
  private float updateRotation(float angle, float targetAngle, float maxIncrease) {
    float f = MathHelper.wrapDegrees(targetAngle - angle);
    if (f > maxIncrease)
      f = maxIncrease; 
    if (f < -maxIncrease)
      f = -maxIncrease; 
    return angle + f;
  }
  
  public boolean canBeSteered() {
    return getControllingPassenger() instanceof EntityPlayer;
  }
  
  public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
    super.setAttackTarget(entitylivingbaseIn);
  }
  
  protected EntityBodyHelper createBodyHelper() {
    return (EntityBodyHelper)new EntityBodyHelperDragon((EntityLivingBase)this);
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    super.onKillEntity(entityLivingIn);
  }
  
  public void onLivingUpdate() {
    if (this.posX == 0.0D && this.posY == 0.0D && this.posZ == 0.0D) {
      EntityEnderDragon entityliving = new EntityEnderDragon(this.world);
      if (!isWild()) {
        entityliving.copyLocationAndAnglesFrom((Entity)getOwner());
      } else {
        entityliving.setPosition(0.0D, 64.0D, 0.0D);
      } 
      entityliving.setOwnerId(getOwnerId());
      entityliving.getPhaseManager().setPhase(PhaseList.LANDING_APPROACH);
      entityliving.setLevel(getLevel());
      entityliving.setStrength(getStrength());
      entityliving.setStamina(getStamina());
      entityliving.setIntelligence(getIntelligence());
      entityliving.setAgility(getAgility());
      entityliving.setDexterity(getDexterity());
      entityliving.setIsHero(isHero());
      entityliving.setLastChance(hasLastChance());
      this.world.removeEntity((Entity)this);
      this.world.spawnEntity((Entity)entityliving);
    } 
    if (this.convertionInt > 0) {
      this.phaseManager.setPhase(PhaseList.LANDING);
      this.motionX *= 0.75D;
      this.motionY *= 0.75D;
      this.motionZ *= 0.75D;
    } 
    if (getPhaseManager().getCurrentPhase() == PhaseList.LANDING_APPROACH) {
      setAttackTarget((EntityLivingBase)null);
      double d0 = (isWild() ? 0.0D : (getOwner()).posX) - this.posX;
      double d1 = (isWild() ? this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION).getY() : ((getOwner()).posY + 4.0D)) - this.posY;
      double d2 = (isWild() ? 0.0D : (getOwner()).posZ) - this.posZ;
      double d3 = d0 * d0 + d2 * d2;
      double d5 = MathHelper.sqrt(d3);
      move(MoverType.SELF, d0 / d5 * 0.99D - this.motionX, d1 / d5 * 0.99D - this.motionY, d2 / d5 * 0.99D - this.motionZ);
    } 
    if (!isEntityAlive())
      clearActivePotions(); 
    if (this.sitting) {
      this.phaseManager.setPhase(PhaseList.HOLDING_PATTERN);
      setLocationAndAngles(this.sitPosX, this.sitPosY, this.sitPosZ, this.rotationYaw, this.rotationPitch);
      this.motionX = this.motionY = this.motionZ = 0.0D;
      if (getAttackTarget() != null && this.phaseManager.getCurrentPhase() != PhaseList.LANDING_APPROACH && this.phaseManager.getCurrentPhase() != PhaseList.LANDING) {
        faceEntity((Entity)getAttackTarget(), 10.0F, 90.0F);
        this.renderYawOffset = this.rotationYaw = this.rotationYawHead + 180.0F;
      } 
      if (!isWild() && getDistanceSq((Entity)getOwner()) > 10000.0D) {
        this.sitPosX = (getOwner()).posX;
        this.sitPosY = (getOwner()).posY + 12.0D;
        this.sitPosZ = (getOwner()).posZ;
      } else {
        this.sitPosX = this.posX;
        this.sitPosY = this.posY;
        this.sitPosZ = this.posZ;
      } 
    } 
    if (this.dead)
      this.phaseManager.setPhase(PhaseList.DYING); 
    if (getHealth() <= getMaxHealth() / 5.0F && !this.phaseManager.getCurrentPhase().getIsStationary()) {
      this.phaseManager.setPhase(PhaseList.LANDING_APPROACH);
      setAttackTarget((EntityLivingBase)null);
    } 
    if (getJukeboxToDanceTo() != null) {
      Vec3d vec3d = getHeadLookVec(1.0F).normalize();
      vec3d.rotateYaw(-0.7853982F);
      double d0 = this.dragonPartHead.posX;
      double d1 = this.dragonPartHead.posY + (this.dragonPartHead.height / 2.0F);
      double d2 = this.dragonPartHead.posZ;
      for (int k = 0; k < 8; k++) {
        double d3 = d0 + getRNG().nextGaussian() / 2.0D;
        double d4 = d1 + getRNG().nextGaussian() / 2.0D;
        double d5 = d2 + getRNG().nextGaussian() / 2.0D;
        this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.x * 0.07999999821186066D + this.motionX, -vec3d.y * 0.30000001192092896D + this.motionY, -vec3d.z * 0.07999999821186066D + this.motionZ, new int[0]);
        vec3d.rotateYaw(0.19634955F);
      } 
    } 
    if (this.moralRaisedTimer <= 0)
      this.moralRaisedTimer = 0; 
    if (this.moralRaisedTimer > 0)
      this.moralRaisedTimer--; 
    if (isAIDisabled()) {
      setNoAI(isAIDisabled());
      this.hurtResistantTime = this.maxHurtResistantTime;
      if (this.ticksExisted > 21)
        this.ticksExisted--; 
    } else {
      if (!this.world.isRemote && getAttackTarget() == null) {
        this.targetTasks.onUpdateTasks();
        List<EntityTameBase> list = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow(getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()), Predicates.and(EntitySelectors.IS_ALIVE, EntitySelectors.NOT_SPECTATING));
        for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
          EntityTameBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
          if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase) && entitylivingbase.getOwnerId() == getOwnerId() && entitylivingbase.getFakeHealth() > 0.0F) {
            setAttackTarget((EntityLivingBase)entitylivingbase);
            break;
          } 
          list.remove(entitylivingbase);
        } 
      } 
      if (!this.world.isRemote) {
        if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false)
          if (getDistanceSq((Entity)getAttackTarget()) < (this.reachWidth * this.reachWidth + ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width) * ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width)) + 9.0D && (this.ticksExisted + getEntityId()) % 10 == 0)
            attackEntityAsMob((Entity)getAttackTarget());  
        if (isEntityAlive()) {
          ChunkLoadingEvent.updateLoaded((Entity)this);
        } else {
          ChunkLoadingEvent.stopLoading((Entity)this);
        } 
      } 
    } 
    setSilent(isAIDisabled());
    this.isJumping = false;
    this.isAirBorne = false;
    this.onGround = true;
    if (getJukeboxToDanceTo() != null) {
      getNavigator().clearPath();
      IBlockState iblockstate = this.world.getBlockState(getJukeboxToDanceTo());
      Block block = iblockstate.getBlock();
      if (this.ticksExisted > 100)
        this.ticksExisted = 20; 
      if (this.innerRotation > 500)
        this.innerRotation = 0; 
      this.sitting = true;
      setPositionAndUpdate(getJukeboxToDanceTo().getX(), getJukeboxToDanceTo().getY() + 12.0D, getJukeboxToDanceTo().getZ());
      if (block != Blocks.JUKEBOX || (block == Blocks.JUKEBOX && !((Boolean)iblockstate.getValue((IProperty)BlockJukebox.HAS_RECORD)).booleanValue()) || getDistanceSqToCenter(this.jukeBoxToDanceTo) > 10000.0D) {
        setJukeboxToDanceTo(null);
        getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
        this.sitting = false;
      } 
    } 
    if (getJukeboxToDanceTo() == null && this.ticksExisted % 60 == 0) {
      int i11 = (int)this.posY;
      int l1 = (int)this.posX;
      int i2 = (int)this.posZ;
      boolean flag = false;
      for (int k2 = -12 - (int)this.width; k2 <= 12 + (int)this.width; k2++) {
        for (int l2 = -12 - (int)this.width; l2 <= 12 + (int)this.width; l2++) {
          for (int k = -18 - (int)this.height; k <= 18 + (int)this.height; k++) {
            int i3 = l1 + k2;
            int m = i11 + k;
            int n = i2 + l2;
            BlockPos blockpos = new BlockPos(i3, m, n);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (block == Blocks.JUKEBOX && ((Boolean)iblockstate.getValue((IProperty)BlockJukebox.HAS_RECORD)).booleanValue()) {
              setJukeboxToDanceTo(blockpos);
              if (this.ticksExisted > 100)
                this.ticksExisted = 20; 
              if (this.innerRotation > 500)
                this.innerRotation = 0; 
              getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
            } 
          } 
        } 
      } 
    } 
    this.dragonPartHead.width = this.dragonPartHead.height = 2.5F * getFittness();
    this.dragonPartNeck.width = this.dragonPartNeck.height = 2.5F * getFittness();
    this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F * getFittness();
    this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F * getFittness();
    this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F * getFittness();
    this.dragonPartBody.height = 3.5F * getFittness();
    this.dragonPartBody.width = 5.0F * getFittness();
    this.dragonPartWing1.height = 3.0F * getFittness();
    this.dragonPartWing1.width = 4.0F * getFittness();
    this.dragonPartWing2.height = 3.0F * getFittness();
    this.dragonPartWing2.width = 4.0F * getFittness();
    Vec3d[] avec3d = new Vec3d[this.dragonPartArray.length];
    for (int j = 0; j < this.dragonPartArray.length; j++)
      avec3d[j] = new Vec3d((this.dragonPartArray[j]).posX, (this.dragonPartArray[j]).posY, (this.dragonPartArray[j]).posZ); 
    float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F * 0.017453292F;
    float f16 = MathHelper.cos(f14);
    float f18 = MathHelper.sin(f14);
    float f2 = this.rotationYaw * 0.017453292F;
    float f19 = MathHelper.sin(f2);
    float f3 = MathHelper.cos(f2);
    double[] adouble = getMovementOffsets(5, 1.0F);
    double[] adouble1 = getMovementOffsets(14, 1.0F);
    double[] adouble2 = getMovementOffsets(16, 1.0F);
    this.dragonPartBody.onUpdate();
    this.dragonPartBody.setLocationAndAngles(this.posX, this.posY - (MathHelper.sin(this.animTime * 6.2831855F - 0.5F) * 0.1F * getFittness()), this.posZ, 0.0F, 0.0F);
    this.dragonPartWing1.onUpdate();
    this.dragonPartWing1.setLocationAndAngles(this.posX + (f3 * 4.5F * getFittness()), this.posY + 1.0D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ + (f19 * 4.5F * getFittness()), 0.0F, 0.0F);
    this.dragonPartWing2.onUpdate();
    this.dragonPartWing2.setLocationAndAngles(this.posX - (f3 * 4.5F * getFittness()), this.posY + 1.0D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ - (f19 * 4.5F * getFittness()), 0.0F, 0.0F);
    this.dragonPartNeck.onUpdate();
    this.dragonPartNeck.setLocationAndAngles(this.posX + (f19 * 3.5F * getFittness()), this.posY + 1.0D - (MathHelper.sin(this.animTime * 6.2831855F + 1.0F) * 0.1F * getFittness()) + (f18 * 2.0F * getFittness()) - (this.rotationPitch / 90.0F) * Math.PI * 0.25D, this.posZ - (f3 * 3.5F * getFittness()), 0.0F, 0.0F);
    this.dragonPartHead.onUpdate();
    this.dragonPartHead.setLocationAndAngles(this.posX + (f19 * 6.0F * getFittness()), this.posY + 1.0D - (MathHelper.sin(this.animTime * 6.2831855F) * 0.1F * getFittness()) + (f18 * 4.0F * getFittness()) - (this.rotationPitch / 90.0F) * Math.PI * 1.0D, this.posZ - (f3 * 6.0F * getFittness()), 0.0F, 0.0F);
    for (int i = 0; i < 3; i++) {
      ExtendMultiPartEntityPart MultiPartEntityPart = null;
      if (i == 0)
        MultiPartEntityPart = this.dragonPartTail1; 
      if (i == 1)
        MultiPartEntityPart = this.dragonPartTail2; 
      if (i == 2)
        MultiPartEntityPart = this.dragonPartTail3; 
      adouble1 = getMovementOffsets(12 + i * 2, 1.0F);
      float f21 = this.rotationYaw * 0.017453292F + simplifyAngle(adouble1[0] - adouble[0]) * 0.017453292F;
      float f22 = MathHelper.sin(f21);
      float f7 = MathHelper.cos(f21);
      float f23 = 1.5F;
      float f24 = (i + 1) * 2.0F;
      MultiPartEntityPart.onUpdate();
      MultiPartEntityPart.setLocationAndAngles(this.posX - ((f19 * f23 + f22 * f24 * getFittness()) * f16), this.posY - (MathHelper.sin(this.animTime * 6.2831855F + i) * 0.2F * (i + 1) * getFittness()) - (f18 * (2.0F + (1 * i))) + f23, this.posZ + ((f3 * f23 + f7 * f24 * getFittness()) * f16), 0.0F, 0.0F);
    } 
    for (int l = 0; l < this.dragonPartArray.length; l++) {
      (this.dragonPartArray[l]).prevPosX = (avec3d[l]).x;
      (this.dragonPartArray[l]).prevPosY = (avec3d[l]).y;
      (this.dragonPartArray[l]).prevPosZ = (avec3d[l]).z;
    } 
    if (isWild() && getOwnerId() == null) {
      if (this.world.isRemote) {
        double d0 = this.rand.nextGaussian() * 0.02D;
        double d1 = this.rand.nextGaussian() * 0.02D;
        double d2 = this.rand.nextGaussian() * 0.02D;
        double d3 = 10.0D;
        this.world.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d0 * d3, this.posY + (this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d2 * d3, d0, d1, d2, new int[0]);
      } 
    } else {
      if (getAttackTarget() != null && getJukeboxToDanceTo() == null && this.phaseManager.getCurrentPhase() != PhaseList.LANDING_APPROACH && this.phaseManager.getCurrentPhase() != PhaseList.LANDING) {
        faceEntity((Entity)getAttackTarget(), 10.0F, 90.0F);
        if (this.sitting)
          this.renderYawOffset = this.rotationYaw = this.rotationYawHead + 180.0F; 
      } else {
        this.rotationYawHead = this.rotationYaw - 180.0F;
        this.rotationPitch = (getPhaseManager().getCurrentPhase().getIsStationary() || this.sitting) ? 20.0F : 0.0F;
      } 
      this.convertionInt = 0;
      if (isHero())
        for (int k = 0; k < this.dragonPartArray.length; k++) {
          avec3d[k] = new Vec3d((this.dragonPartArray[k]).posX, (this.dragonPartArray[k]).posY, (this.dragonPartArray[k]).posZ);
          double d0 = this.rand.nextGaussian() * 0.02D;
          double d1 = this.rand.nextGaussian() * 0.02D;
          double d2 = this.rand.nextGaussian() * 0.02D;
          double d3 = 10.0D;
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, (this.dragonPartArray[k]).posX + (this.rand.nextFloat() * (this.dragonPartArray[k]).width * 2.0F) - (this.dragonPartArray[k]).width - d0 * d3, (this.dragonPartArray[k]).posY + (this.rand.nextFloat() * (this.dragonPartArray[k]).height) - d1 * d3, (this.dragonPartArray[k]).posZ + (this.rand.nextFloat() * (this.dragonPartArray[k]).width * 2.0F) - (this.dragonPartArray[k]).width - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
        }  
    } 
    if (this.rand.nextInt(5) == 0 && !isWild() && getOwner().getRevengeTarget() != null && this.isOffensive)
      setAttackTarget(getOwner().getRevengeTarget()); 
    this.noClip = true;
    this.reachWidth = 8.0F;
    this.onGround = false;
    this.isAirBorne = true;
    if (this.ticksExisted % 5 == 0)
      this.slowed = false; 
    if (this.world.isRemote) {
      setHealth(getHealth());
      if (!isSilent()) {
        float f = MathHelper.cos(this.animTime * 6.2831855F);
        float f1 = MathHelper.cos(this.prevAnimTime * 6.2831855F);
        if (f1 <= -0.3F && f >= -0.3F)
          if (isSneaking()) {
            this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 1.0F, 0.6F + this.rand.nextFloat() * 0.3F, false);
          } else {
            this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
          }  
        if (!this.phaseManager.getCurrentPhase().getIsStationary() && --this.growlTime < 0) {
          if (isSneaking()) {
            this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 1.0F, 0.6F + this.rand.nextFloat() * 0.3F, false);
          } else {
            this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
          } 
          this.growlTime = 200 + this.rand.nextInt(200);
        } 
      } 
    } 
    this.prevAnimTime = this.animTime;
    if (isAIDisabled()) {
      this.animTime = 0.0F;
    } else if (isEntityAlive()) {
      if (this.ringBufferIndex < 0)
        for (int k = 0; k < this.ringBuffer.length; k++) {
          this.ringBuffer[k][0] = this.rotationYaw;
          this.ringBuffer[k][1] = this.posY;
        }  
      if (++this.ringBufferIndex == this.ringBuffer.length)
        this.ringBufferIndex = 0; 
      this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
      this.ringBuffer[this.ringBufferIndex][1] = this.posY;
      if (this.world.isRemote) {
        if (this.newPosRotationIncrements > 0) {
          double d5 = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
          double d0 = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
          double d1 = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
          double d2 = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
          this.rotationYaw = (float)(this.rotationYaw + d2 / this.newPosRotationIncrements);
          this.rotationPitch = (float)(this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
          this.newPosRotationIncrements--;
          setPosition(d5, d0, d1);
          setRotation(this.rotationYaw, this.rotationPitch);
        } 
        this.phaseManager.getCurrentPhase().doClientRenderEffects();
      } else {
        IPhase iphase = this.phaseManager.getCurrentPhase();
        iphase.doLocalUpdate();
        if (this.phaseManager.getCurrentPhase() != iphase) {
          iphase = this.phaseManager.getCurrentPhase();
          iphase.doLocalUpdate();
        } 
        Vec3d vec3d = iphase.getTargetLocation();
        if (vec3d != null) {
          double d6 = vec3d.x - this.posX;
          double d7 = vec3d.y - this.posY;
          double d8 = vec3d.z - this.posZ;
          double d3 = d6 * d6 + d7 * d7 + d8 * d8;
          float f5 = iphase.getMaxRiseOrFall();
          d7 = MathHelper.clamp(d7 / MathHelper.sqrt(d6 * d6 + d8 * d8), -f5, f5);
          this.motionY += d7 * 0.10000000149011612D;
          this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
          double d4 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d6, d8) * 57.29577951308232D - this.rotationYaw), -50.0D, 50.0D);
          Vec3d vec3d1 = (new Vec3d(vec3d.x - this.posX, vec3d.y - this.posY, vec3d.z - this.posZ)).normalize();
          Vec3d vec3d2 = (new Vec3d(MathHelper.sin(this.rotationYaw * 0.017453292F), this.motionY, -MathHelper.cos(this.rotationYaw * 0.017453292F))).normalize();
          float f7 = Math.max(((float)vec3d2.dotProduct(vec3d1) + 0.5F) / 1.5F, 0.0F);
          this.randomYawVelocity *= 0.8F;
          this.randomYawVelocity = (float)(this.randomYawVelocity + d4 * iphase.getYawFactor());
          this.rotationYaw += this.randomYawVelocity * 0.1F;
          float f8 = (float)(2.0D / (d3 + 1.0D));
          float f9 = 0.06F;
          if (this.ticksExisted > 20 && !this.sitting) {
            if (this.ticksExisted > 20 && !this.sitting)
              moveRelative(0.0F, 0.0F, -1.0F, 0.06F * (f7 * f8 + 1.0F - f8)); 
            if (getPhaseManager().getCurrentPhase() == PhaseList.CHARGING_PLAYER) {
              if (isSneaking()) {
                move(MoverType.SELF, this.motionX * (isBeingRidden() ? 0.375D : 0.25D), this.motionY * (isBeingRidden() ? 0.375D : 0.25D), this.motionZ * (isBeingRidden() ? 0.375D : 0.25D));
              } else if (this.moralRaisedTimer > 200) {
                move(MoverType.SELF, this.motionX * (isBeingRidden() ? 15.0D : 10.0D), this.motionY * (isBeingRidden() ? 15.0D : 10.0D), this.motionZ * (isBeingRidden() ? 15.0D : 10.0D));
              } else {
                move(MoverType.SELF, this.motionX * (isBeingRidden() ? 7.5D : 5.0D), this.motionY * (isBeingRidden() ? 7.5D : 5.0D), this.motionZ * (isBeingRidden() ? 7.5D : 5.0D));
              } 
            } else {
              if (isSneaking())
                move(MoverType.SELF, this.motionX * (isBeingRidden() ? 0.375D : 0.25D), this.motionY * (isBeingRidden() ? 0.375D : 0.25D), this.motionZ * (isBeingRidden() ? 0.375D : 0.25D)); 
              if (this.moralRaisedTimer > 200) {
                move(MoverType.SELF, this.motionX * (isBeingRidden() ? 3.0D : 2.0D), this.motionY * (isBeingRidden() ? 3.0D : 2.0D), this.motionZ * (isBeingRidden() ? 3.0D : 2.0D));
              } else {
                move(MoverType.SELF, this.motionX * (isBeingRidden() ? 1.5D : 1.0D), this.motionY * (isBeingRidden() ? 1.5D : 1.0D), this.motionZ * (isBeingRidden() ? 1.5D : 1.0D));
              } 
            } 
          } 
          Vec3d vec3d3 = (new Vec3d(this.motionX, this.motionY, this.motionZ)).normalize();
          float f10 = ((float)vec3d3.dotProduct(vec3d2) + 1.0F) / 2.0F;
          f10 = 0.8F + 0.15F * f10;
          this.motionX *= f10;
          this.motionZ *= f10;
          this.motionY *= 0.9100000262260437D;
        } 
      } 
      if (!this.world.isRemote) {
        collideWithEntities(this.dragonPartHead, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartHead.getEntityBoundingBox().grow(1.0D)));
        collideWithEntities(this.dragonPartNeck, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartNeck.getEntityBoundingBox().grow(1.0D)));
        collideWithEntities(this.dragonPartBody, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartBody.getEntityBoundingBox().grow(1.0D)));
        flingEntities(this.dragonPartWing1, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing1.getEntityBoundingBox().grow(4.0D).offset(0.0D, -2.0D, 0.0D)));
        flingEntities(this.dragonPartWing2, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartWing2.getEntityBoundingBox().grow(4.0D).offset(0.0D, -2.0D, 0.0D)));
        collideWithEntities(this.dragonPartTail1, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartTail1.getEntityBoundingBox().grow(1.0D)));
        collideWithEntities(this.dragonPartTail2, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartTail2.getEntityBoundingBox().grow(1.0D)));
        collideWithEntities(this.dragonPartTail3, this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartTail3.getEntityBoundingBox().grow(1.0D)));
        attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartHead.getEntityBoundingBox().grow(3.0D)));
        attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, this.dragonPartNeck.getEntityBoundingBox().grow(2.0D)));
      } 
      destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartNeck.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartWing1.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartWing2.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartTail1.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartTail2.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartTail3.getEntityBoundingBox());
    } 
    if (getSpecialAttackTimer() > 0)
      if (this.moralRaisedTimer > 200) {
        setSpecialAttackTimer(getSpecialAttackTimer() - 2);
      } else {
        setSpecialAttackTimer(getSpecialAttackTimer() - 1);
      }  
    if (getHealth() <= 0.0F && this.deathTicks > 0) {
      for (int k = 0; k < this.dragonPartArray.length; k++) {
        avec3d[k] = new Vec3d((this.dragonPartArray[k]).posX, (this.dragonPartArray[k]).posY, (this.dragonPartArray[k]).posZ);
        double d1 = (this.rand.nextFloat() * (this.dragonPartArray[k]).width - (this.dragonPartArray[k]).width / 2.0F);
        double d2 = (this.rand.nextFloat() * (this.dragonPartArray[k]).height - (this.dragonPartArray[k]).height / 2.0F);
        double d3 = (this.rand.nextFloat() * (this.dragonPartArray[k]).width - (this.dragonPartArray[k]).width / 2.0F);
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, (this.dragonPartArray[k]).posX + d1, (this.dragonPartArray[k]).posY + d2, (this.dragonPartArray[k]).posZ + d3, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
      } 
      float f13 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f15 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f17 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f13, this.posY + f15, this.posZ + f17, 0.0D, 0.0D, 0.0D, new int[0]);
    } else {
      if (getAttackTarget() != null && !getPhaseManager().getCurrentPhase().getIsStationary() && getPhaseManager().getCurrentPhase() != PhaseList.CHARGING_PLAYER && getPhaseManager().getCurrentPhase() != PhaseList.STRAFE_PLAYER && getAttackTarget() != null && this.ticksExisted % 60 == 0 && getRNG().nextInt(5) == 0 && !this.sitting)
        switch (this.rand.nextInt(2)) {
          case 0:
            getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
            ((PhaseRamAttack)getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).setTarget(new Vec3d((getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ));
            break;
          case 1:
            getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
            ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseList.STRAFE_PLAYER)).setTarget(getAttackTarget());
            break;
        }  
      if (isHero() && getSpecialAttackTimer() > 1995) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(256.0D, 256.0D, 256.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity = list.get(i1);
            if (entity != null)
              if (!false) {
                double d6 = this.posX + this.rand.nextDouble() * 32.0D - 16.0D;
                double d7 = this.posY + 64.0D + this.rand.nextDouble() * 32.0D - 16.0D;
                double d8 = this.posZ + this.rand.nextDouble() * 32.0D - 16.0D;
                double d9 = entity.posX - d6;
                double d10 = entity.posY + (entity.height / 2.0F) - d7 + (this.height / 2.0F);
                double d11 = entity.posZ - d8;
                this.world.playEvent((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
                EntityDragonFireballOther entitydragonfireball = new EntityDragonFireballOther(this.world, (EntityLivingBase)this, d9, d10, d11);
                entitydragonfireball.posX = d6;
                entitydragonfireball.posY = d7;
                entitydragonfireball.posZ = d8;
                this.world.spawnEntity((Entity)entitydragonfireball);
              }  
          }  
      } 
      if (getAttackTarget() != null && getAttackTarget().isEntityAlive() && getSpecialAttackTimer() <= 0 && isHero())
        performSpecialAttack(); 
      if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
        EntityPlayer passenger = (EntityPlayer)getControllingPassenger();
        passenger.fallDistance *= 0.0F;
        passenger.hurtResistantTime = 40;
        passenger.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 4));
        getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        this.renderYawOffset = this.rotationYaw = passenger.rotationYawHead + 180.0F;
        this.rotationPitch = 0.0F;
        for (int k = 0; k < 256; k++) {
          double d1 = 0.005D;
          if (this.moralRaisedTimer > 200)
            d1 *= 2.0D; 
          if (isSprinting())
            d1 *= 2.0D; 
          Vec3d vec3 = passenger.getLook(1.0F);
          if (passenger.moveForward > 0.0F) {
            setPosition(this.posX + vec3.x * d1, this.posY + vec3.y * d1, this.posZ + vec3.z * d1);
            Entity[] aentity = getParts();
            if (aentity != null)
              for (Entity entity : aentity)
                entity.setLocationAndAngles(entity.posX + vec3.x * d1, entity.posY + vec3.y * d1, entity.posZ + vec3.z * d1, 0.0F, 0.0F);  
          } 
          if (passenger.moveForward < 0.0F)
            setPosition(this.posX - vec3.x * d1, this.posY - vec3.y * d1, this.posZ - vec3.z * d1); 
        } 
      } 
      if (!isWild() && getOwner().isSprinting()) {
        setSprinting(true);
      } else {
        setSprinting(false);
      } 
      if (!isWild() && getOwner().isSneaking()) {
        setSneaking(true);
      } else {
        setSneaking(false);
      } 
      if (isSneaking() && !getPhaseManager().getCurrentPhase().getIsStationary() && this.rand.nextInt(100) == 0)
        getPhaseManager().setPhase(PhaseList.LANDING_APPROACH); 
      if (!getPhaseManager().getCurrentPhase().getIsStationary() && !isWild() && (getOwner().getHealth() <= 6.0F || (!getOwner().getHeldItemMainhand().isEmpty() && getOwner().getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE)) && getRNG().nextInt(20) == 0) {
        getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
        ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseList.STRAFE_PLAYER)).setTarget(getOwner());
      } 
      if (!isWild() && getDistanceSq((Entity)getOwner()) >= 48400.0D)
        setLocationAndAngles((getOwner()).posX, (getOwner()).posY, (getOwner()).posZ, this.rotationYaw, this.rotationPitch); 
      if (!this.world.isRemote && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen((Entity)this) && this.rand.nextInt(120) == 0) {
        double d1 = this.dragonPartHead.posX;
        double d2 = this.dragonPartHead.posY + 0.25D;
        double d3 = this.dragonPartHead.posZ;
        if (getPolymorphTime() > 0 && this.rand.nextBoolean()) {
          for (int k = 0; k < (isHero() ? 36 : 18); k++) {
            EntityMagicMissile entitymagicmissiles = new EntityMagicMissile(this.world, (Entity)getAttackTarget(), (EntityLivingBase)this, d1, d2, d3);
            Random random = new Random();
            entitymagicmissiles.motionX += random.nextDouble() * 2.0D - 1.0D + this.motionX;
            entitymagicmissiles.motionY += random.nextDouble() * 2.0D + this.motionY;
            entitymagicmissiles.motionZ += random.nextDouble() * 2.0D - 1.0D + this.motionZ;
            this.world.spawnEntity((Entity)entitymagicmissiles);
          } 
        } else {
          fireLightning((Entity)getAttackTarget(), d1, d2, d3);
        } 
      } 
      if (!this.world.isRemote && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen((Entity)this) && this.rand.nextInt(120) == 0) {
        double d6 = this.dragonPartHead.posX;
        double d7 = this.dragonPartHead.posY + 0.5D;
        double d8 = this.dragonPartHead.posZ;
        double d9 = (getAttackTarget()).posX - d6;
        double d10 = (getAttackTarget()).posY + 1.0D - d7;
        double d11 = (getAttackTarget()).posZ - d8;
        this.world.playEvent((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
        if (getPolymorphTime() > 0 && this.rand.nextBoolean()) {
          EntityInvisibleFangsProjectile entitydragonfireball = new EntityInvisibleFangsProjectile(this.world, (EntityLivingBase)this, d6, d7, d8);
          entitydragonfireball.posX = d6;
          entitydragonfireball.posY = d7;
          entitydragonfireball.posZ = d8;
          this.world.spawnEntity((Entity)entitydragonfireball);
        } else {
          EntityDragonFireballOther entitydragonfireball = new EntityDragonFireballOther(this.world, (EntityLivingBase)this, d9, d10, d11);
          entitydragonfireball.posX = d6;
          entitydragonfireball.posY = d7;
          entitydragonfireball.posZ = d8;
          this.world.spawnEntity((Entity)entitydragonfireball);
        } 
      } 
      if (this.rand.nextInt(2) == 0 && !isWild() && getOwner().getRevengeTarget() != null)
        setAttackTarget(getOwner().getRevengeTarget()); 
      if (getAttackTarget() != null && (!getAttackTarget().isEntityAlive() || !this.isOffensive || false))
        setAttackTarget((EntityLivingBase)null); 
      updateDragonEnderCrystal();
      float f12 = 0.2F / (MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
      f12 *= (float)Math.pow(2.0D, this.motionY);
      if (this.ticksExisted > 20)
        if (this.phaseManager.getCurrentPhase().getIsStationary() || (isBeingRidden() && ((EntityLivingBase)getControllingPassenger()).moveForward == 0.0F) || getPhaseManager().getCurrentPhase() == PhaseList.CHARGING_PLAYER || this.sitting) {
          if (isSneaking()) {
            this.animTime += 0.05F;
          } else {
            this.animTime += 0.1F;
          } 
        } else if (this.slowed) {
          this.animTime += f12 * 0.5F;
        } else {
          this.animTime += f12;
        }  
      this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw;
    } 
    if (this.ticksExisted % 20 == 0)
      heal(1.0F); 
  }
  
  private float getHeadYOffset(float p_184662_1_) {
    double d0 = 0.0D;
    if (this.phaseManager.getCurrentPhase().getIsStationary() || getPhaseManager().getCurrentPhase() == PhaseList.HOVER) {
      d0 = -1.0D;
    } else {
      double[] adouble = getMovementOffsets(5, 1.0F);
      double[] adouble1 = getMovementOffsets(0, 1.0F);
      d0 = adouble[1] - adouble1[0];
    } 
    return (float)d0;
  }
  
  private void updateDragonEnderCrystal() {
    if (this.ticksExisted % 5 == 0 && isEntityAlive() && isCarryingCrystal())
      setHealth(getHealth() + 1.0F); 
  }
  
  protected void collideWithEntities(ExtendMultiPartEntityPart part, List list) {
    double d0 = ((part.getEntityBoundingBox()).minX + (part.getEntityBoundingBox()).maxX) / 2.0D;
    double d1 = ((part.getEntityBoundingBox()).minZ + (part.getEntityBoundingBox()).maxZ) / 2.0D;
    Iterator<Entity> iterator = list.iterator();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();
      if (isEntityAlive() && entity instanceof EntityLivingBase && !false && !this.world.isRemote) {
        double d2 = entity.posX - d0;
        double d3 = entity.posZ - d1;
        double d4 = d2 * d2 + d3 * d3;
        entity.addVelocity(d2 / d4 * 4.0D, 0.75D, d3 / d4 * 4.0D);
        this.slowed = true;
        if (entity instanceof EntityLivingBase) {
          ((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).rotationYawHead = (float)MathHelper.atan2(entity.motionZ, entity.motionX) * 57.295776F - 90.0F;
          ((EntityLivingBase)entity).setRevengeTarget(null);
          if (entity instanceof EntityLiving)
            ((EntityLiving)entity).setAttackTarget(null); 
        } 
      } 
    } 
  }
  
  protected void flingEntities(ExtendMultiPartEntityPart part, List list) {
    double d0 = ((part.getEntityBoundingBox()).minX + (part.getEntityBoundingBox()).maxX) / 1.5D;
    double d1 = ((part.getEntityBoundingBox()).minZ + (part.getEntityBoundingBox()).maxZ) / 1.5D;
    Iterator<Entity> iterator = list.iterator();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();
      if (isEntityAlive() && entity instanceof EntityLivingBase && !false && !this.world.isRemote) {
        double d2 = entity.posX - d0;
        double d3 = entity.posZ - d1;
        double d4 = d2 * d2 + d3 * d3;
        entity.addVelocity(d2 / d4 * 32.0D, 1.5D, d3 / d4 * 32.0D);
        if (entity instanceof EntityLivingBase) {
          ((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).rotationYawHead = (float)MathHelper.atan2(entity.motionZ, entity.motionX) * 57.295776F - 90.0F;
          ((EntityLivingBase)entity).setRevengeTarget(null);
          if (entity instanceof EntityLiving)
            ((EntityLiving)entity).setAttackTarget(null); 
        } 
        entity.attackEntityFrom(DamageSource.FLY_INTO_WALL, 4.0F);
      } 
    } 
  }
  
  protected void attackEntitiesInList(List<Entity> p_70971_1_) {
    for (int i = 0; i < p_70971_1_.size(); i++) {
      Entity entity = p_70971_1_.get(i);
      if (isEntityAlive() && entity.ticksExisted + entity.getEntityId() % 10 == 0 && !this.world.isRemote && entity instanceof EntityLivingBase && !false) {
        attackEntityAsMob(entity);
        if (entity instanceof EntityLivingBase) {
          ((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).rotationYawHead = (float)MathHelper.atan2(entity.motionZ, entity.motionX) * 57.295776F - 90.0F;
          ((EntityLivingBase)entity).setRevengeTarget(null);
          if (entity instanceof EntityLiving)
            ((EntityLiving)entity).setAttackTarget(null); 
        } 
        playSound(SoundEvents.BLOCK_NOTE_HAT, 5.0F, 0.5F);
        this.slowed = true;
      } 
    } 
  }
  
  private float simplifyAngle(double p_70973_1_) {
    return (float)MathHelper.wrapDegrees(p_70973_1_);
  }
  
  protected boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_) {
    int i = MathHelper.floor(p_70972_1_.minX);
    int j = MathHelper.floor(p_70972_1_.minY);
    int k = MathHelper.floor(p_70972_1_.minZ);
    int l = MathHelper.floor(p_70972_1_.maxX);
    int i1 = MathHelper.floor(p_70972_1_.maxY);
    int j1 = MathHelper.floor(p_70972_1_.maxZ);
    boolean flag1 = false;
    for (int k1 = i; k1 <= l; k1++) {
      for (int l1 = j; l1 <= i1; l1++) {
        for (int i2 = k; i2 <= j1; i2++) {
          BlockPos blockpos = new BlockPos(k1, l1, i2);
          IBlockState iblockstate = this.world.getBlockState(blockpos);
          Block block = iblockstate.getBlock();
          if (EngenderConfig.mobs.grief && isEntityAlive() && !block.isAir(iblockstate, (IBlockAccess)this.world, blockpos))
            if (block.canEntityDestroy(iblockstate, (IBlockAccess)this.world, blockpos, (Entity)this))
              if (block != Blocks.END_PORTAL && block != Blocks.DRAGON_EGG && block != Blocks.BEDROCK && block != Blocks.END_STONE && block != Blocks.OBSIDIAN && block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.IRON_BARS && block != Blocks.END_GATEWAY) {
                if (!this.world.isRemote)
                  flag1 = (this.world.setBlockToAir(blockpos) || flag1); 
              } else {
                this.slowed = true;
              }   
        } 
      } 
    } 
    return EngenderConfig.mobs.grief;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean isEntityInvulnerable(DamageSource source) {
    return super.isEntityInvulnerable(source);
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    if (isEntityInvulnerable(source))
      damage = 0.0F; 
    if (getHealth() <= 0.0F)
      this.phaseManager.setPhase(PhaseList.DYING); 
    damage = this.phaseManager.getCurrentPhase().getAdjustedDamage(dragonPart, source, damage);
    if (dragonPart != this.dragonPartHead)
      damage = damage / 5.0F + Math.min(damage, 1.0F); 
    if (isEntityInvulnerable(source) || damage < 0.01F || source == DamageSource.WITHER || source == DamageSource.IN_WALL || source == DamageSource.DROWN || source == DamageSource.CRAMMING || source == DamageSource.CACTUS)
      return false; 
    float f = getHealth();
    super.attackEntityFrom(source, damage);
    if (getHealth() <= 0.0F && !this.phaseManager.getCurrentPhase().getIsStationary() && !isWild())
      this.phaseManager.setPhase(PhaseList.DYING); 
    if (this.phaseManager.getCurrentPhase().getIsStationary()) {
      this.sittingDamageReceived = (int)(this.sittingDamageReceived + f - getHealth());
      if (this.sittingDamageReceived > 50.0F) {
        this.sittingDamageReceived = 0;
        if (source.getTrueSource() instanceof EntityLivingBase)
          switch (this.rand.nextInt(2)) {
            case 0:
              getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
              ((PhaseRamAttack)getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).setTarget(new Vec3d(((EntityLivingBase)source.getTrueSource()).posX, ((EntityLivingBase)source.getTrueSource()).posY, ((EntityLivingBase)source.getTrueSource()).posZ));
              break;
            case 1:
              getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
              ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseList.STRAFE_PLAYER)).setTarget((EntityLivingBase)source.getTrueSource());
              break;
          }  
      } 
    } 
    if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase)
      if (!isWild() && source.getTrueSource() == getOwner()) {
        getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
      } else {
        setAttackTarget((EntityLivingBase)source.getTrueSource());
        if (this.rand.nextInt(2) == 0) {
          getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
          ((PhaseRamAttack)getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).setTarget(new Vec3d((source.getTrueSource()).posX, (source.getTrueSource()).posY, (source.getTrueSource()).posZ));
        } 
      }  
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return attackEntityFromPart((MultiPartEntityPart)this.dragonPartBody, source, amount);
  }
  
  protected void onDeathUpdate() {
    List<EntityLivingBase> list = Lists.newArrayList();
    List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, (new AxisAlignedBB(getPosition())).grow(256.0D));
    for (Entity entity : entities) {
      if (entity instanceof EntityEnderCrystal) {
        entity.attackEntityFrom(DamageSource.GENERIC, 1.0F);
        setHealth(50.0F);
        this.dead = false;
        getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        this.world.setEntityState((Entity)this, (byte)35);
        playSound(SoundEvents.ITEM_TOTEM_USE, 10.0F, 1.0F);
        this.deathTicks = 0;
        break;
      } 
    } 
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (!this.world.isRemote) {
      if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && this.world.getGameRules().getBoolean("doMobLoot")) {
        int i = 1000;
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY + 8.0D, this.posZ, j));
        } 
      } 
      if (this.deathTicks == 1) {
        if (Loader.isModLoaded("draconicevolution")) {
          Entity var8 = EntityList.createEntityByIDFromName(new ResourceLocation("draconicevolution:dragonheartitem"), this.world);
          this.world.spawnEntity(var8);
        } 
        this.world.playBroadcastSound(1028, new BlockPos((Entity)this), 0);
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.world.playerEntities) {
            this.world.playSound(null, entityplayer.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_DEATH, getSoundCategory(), getSoundVolume(), 1.0F);
            entityplayer.sendStatusMessage((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().getName() + "'s " + getName() + " has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).sendMessage((ITextComponent)new TextComponentTranslation("Your " + getName() + " has fallen in battle.", new Object[0]));
        } 
      } 
    } 
    double d0 = (isWild() ? 0.0D : (getOwner()).posX) - this.posX;
    double d1 = (isWild() ? this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION).getY() : ((getOwner()).posY + 4.0D)) - this.posY;
    double d2 = (isWild() ? 0.0D : (getOwner()).posZ) - this.posZ;
    double d3 = d0 * d0 + d2 * d2;
    if (this.ticksExisted > 20)
      if (d3 > 1.0D && this.deathTicks < 1) {
        double d5 = MathHelper.sqrt(d3);
        this.renderYawOffset = this.rotationYaw = this.rotationYawHead = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F + 180.0F;
        this.motionX += d0 / d5 * 0.75D - this.motionX;
        this.motionY += d1 / d5 * 0.75D - this.motionY;
        this.motionZ += d2 / d5 * 0.75D - this.motionZ;
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      } else {
        move(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
        this.renderYawOffset = this.rotationYawHead = this.rotationYaw += 10.0F;
        this.deathTicks++;
      }  
    if (this.deathTicks == 200 && !this.world.isRemote) {
      int i = 2000;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY + 8.0D, this.posZ, j));
      } 
      this.world.setBlockState(new BlockPos(this.posX, this.posY + 4.0D, this.posZ), Blocks.DRAGON_EGG.getDefaultState(), 3);
      entityDropItem(new ItemStack(Items.SKULL, 1, 5), 4.0F);
      dropItemWithOffset(Items.ELYTRA, 1, 4.0F);
      setDead();
    } 
  }
  
  private void dropExperience(int p_184668_1_) {
    while (p_184668_1_ > 0) {
      int i = EntityXPOrb.getXPSplit(p_184668_1_);
      p_184668_1_ -= i;
      this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, i));
    } 
  }
  
  public int initPathPoints() {
    if (this.pathPoints[0] == null) {
      for (int i = 0; i < 24; i++) {
        int l, i1, j = 5;
        if (i < 12) {
          l = (int)(60.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.2617994F * i)));
          i1 = (int)(60.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.2617994F * i)));
        } else if (i < 20) {
          int lvt_3_1_ = i - 12;
          l = (int)(40.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.3926991F * lvt_3_1_)));
          i1 = (int)(40.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.3926991F * lvt_3_1_)));
          j += 10;
        } else {
          int k1 = i - 20;
          l = (int)(20.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.7853982F * k1)));
          i1 = (int)(20.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.7853982F * k1)));
        } 
        int j1 = Math.max(this.world.getSeaLevel() + 10, this.world.getTopSolidOrLiquidBlock(new BlockPos(l, 0, i1)).getY() + j);
        this.pathPoints[i] = new PathPoint(l, j1, i1);
      } 
      this.neighbors[0] = 6146;
      this.neighbors[1] = 8197;
      this.neighbors[2] = 8202;
      this.neighbors[3] = 16404;
      this.neighbors[4] = 32808;
      this.neighbors[5] = 32848;
      this.neighbors[6] = 65696;
      this.neighbors[7] = 131392;
      this.neighbors[8] = 131712;
      this.neighbors[9] = 263424;
      this.neighbors[10] = 526848;
      this.neighbors[11] = 525313;
      this.neighbors[12] = 1581057;
      this.neighbors[13] = 3166214;
      this.neighbors[14] = 2138120;
      this.neighbors[15] = 6373424;
      this.neighbors[16] = 4358208;
      this.neighbors[17] = 12910976;
      this.neighbors[18] = 9044480;
      this.neighbors[19] = 9706496;
      this.neighbors[20] = 15216640;
      this.neighbors[21] = 13688832;
      this.neighbors[22] = 11763712;
      this.neighbors[23] = 8257536;
    } 
    return getNearestPpIdx(this.posX, this.posY, this.posZ);
  }
  
  public int getNearestPpIdx(double x, double y, double z) {
    float f = 10000.0F;
    int i = 0;
    PathPoint pathpoint = new PathPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    int j = 0;
    for (int k = j; k < 24; k++) {
      if (this.pathPoints[k] != null) {
        float f1 = this.pathPoints[k].distanceToSquared(pathpoint);
        if (f1 < f) {
          f = f1;
          i = k;
        } 
      } 
    } 
    return i;
  }
  
  @Nullable
  public Path findPath(int startIdx, int finishIdx, @Nullable PathPoint andThen) {
    for (int i = 0; i < 24; i++) {
      PathPoint pathpoint = this.pathPoints[i];
      pathpoint.visited = false;
      pathpoint.distanceToTarget = 0.0F;
      pathpoint.totalPathDistance = 0.0F;
      pathpoint.distanceToNext = 0.0F;
      pathpoint.previous = null;
      pathpoint.index = -1;
    } 
    PathPoint pathpoint4 = this.pathPoints[startIdx];
    PathPoint pathpoint5 = this.pathPoints[finishIdx];
    pathpoint4.totalPathDistance = 0.0F;
    pathpoint4.distanceToNext = pathpoint4.distanceTo(pathpoint5);
    pathpoint4.distanceToTarget = pathpoint4.distanceToNext;
    this.pathFindQueue.clearPath();
    this.pathFindQueue.addPoint(pathpoint4);
    PathPoint pathpoint1 = pathpoint4;
    int j = 0;
    while (!this.pathFindQueue.isPathEmpty()) {
      PathPoint pathpoint2 = this.pathFindQueue.dequeue();
      if (pathpoint2.equals(pathpoint5)) {
        if (andThen != null) {
          andThen.previous = pathpoint5;
          pathpoint5 = andThen;
        } 
        return makePath(pathpoint4, pathpoint5);
      } 
      if (pathpoint2.distanceTo(pathpoint5) < pathpoint1.distanceTo(pathpoint5))
        pathpoint1 = pathpoint2; 
      pathpoint2.visited = true;
      int k = 0;
      for (int l = 0; l < 24; l++) {
        if (this.pathPoints[l] == pathpoint2) {
          k = l;
          break;
        } 
      } 
      for (int i1 = j; i1 < 24; i1++) {
        int ran = this.rand.nextInt(23);
        if ((this.neighbors[k] & 1 << ran) > 0) {
          PathPoint pathpoint3 = this.pathPoints[ran];
          if (!pathpoint3.visited) {
            float f = pathpoint2.totalPathDistance + pathpoint2.distanceTo(pathpoint3);
            if (!pathpoint3.isAssigned() || f < pathpoint3.totalPathDistance) {
              pathpoint3.previous = pathpoint2;
              pathpoint3.totalPathDistance = f;
              pathpoint3.distanceToNext = pathpoint3.distanceTo(pathpoint5);
              if (pathpoint3.isAssigned()) {
                this.pathFindQueue.changeDistance(pathpoint3, pathpoint3.totalPathDistance + pathpoint3.distanceToNext);
              } else {
                pathpoint3.distanceToTarget = pathpoint3.totalPathDistance + pathpoint3.distanceToNext;
                this.pathFindQueue.addPoint(pathpoint3);
              } 
            } 
          } 
        } 
      } 
    } 
    if (pathpoint1 == pathpoint4)
      return null; 
    if (andThen != null) {
      andThen.previous = pathpoint1;
      pathpoint1 = andThen;
    } 
    return makePath(pathpoint4, pathpoint1);
  }
  
  private Path makePath(PathPoint start, PathPoint finish) {
    int i = 1;
    for (PathPoint pathpoint = finish; pathpoint.previous != null; pathpoint = pathpoint.previous)
      i++; 
    PathPoint[] apathpoint = new PathPoint[i];
    PathPoint pathpoint1 = finish;
    i--;
    for (apathpoint[i] = finish; pathpoint1.previous != null; apathpoint[i] = pathpoint1) {
      pathpoint1 = pathpoint1.previous;
      i--;
    } 
    return new Path(apathpoint);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("DragonPhase", this.phaseManager.getCurrentPhase().getPhaseList().getId());
    tagCompound.setBoolean("CarryingCrystal", isCarryingCrystal());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    if (tagCompund.hasKey("DragonPhase"))
      this.phaseManager.setPhase(PhaseList.getById(tagCompund.getInteger("DragonPhase"))); 
    setCarryingCrystal(tagCompund.getBoolean("CarryingCrystal"));
  }
  
  protected void despawnEntity() {}
  
  public Entity[] getParts() {
    return (Entity[])this.dragonPartArray;
  }
  
  public boolean canBeCollidedWith() {
    return (this.world.getClosestPlayerToEntity((Entity)this, this.width) != null && isEntityAlive());
  }
  
  public World getWorld() {
    return this.world;
  }
  
  protected void collideWithNearbyEntities() {}
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_ENDERDRAGON_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_ENDERDRAGON_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_ENDERMEN_STARE;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 10.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadPartYOffset(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
    BlockPos blockpos = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
    if (getOwner() != null)
      blockpos = new BlockPos((Entity)getOwner()); 
    float f = Math.max(MathHelper.sqrt(getDistanceSqToCenter(blockpos)) / 4.0F, 1.0F);
    return p_184667_1_ / f;
  }
  
  public Vec3d getHeadLookVec(float p_184665_1_) {
    Vec3d vec3d;
    IPhase iphase = this.phaseManager.getCurrentPhase();
    PhaseList<? extends IPhase> phaselist = iphase.getPhaseList();
    if (phaselist != PhaseList.LANDING && phaselist != PhaseList.TAKEOFF) {
      if (iphase.getIsStationary()) {
        float f4 = this.rotationPitch;
        float f5 = 1.5F;
        this.rotationPitch = -6.0F * f5 * 5.0F;
        vec3d = getLook(p_184665_1_);
        this.rotationPitch = f4;
      } else {
        vec3d = getLook(p_184665_1_);
      } 
    } else {
      BlockPos blockpos = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
      if (getOwner() != null)
        blockpos = new BlockPos((Entity)getOwner()); 
      float f = Math.max(MathHelper.sqrt(getDistanceSqToCenter(blockpos)) / 4.0F, 1.0F);
      float f1 = 6.0F / f;
      float f2 = this.rotationPitch;
      float f3 = 1.5F;
      this.rotationPitch = -f1 * f3 * 5.0F;
      vec3d = getLook(p_184665_1_);
      this.rotationPitch = f2;
    } 
    return vec3d;
  }
  
  public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc) {
    EntityPlayer entityplayer;
    if (dmgSrc.getTrueSource() instanceof EntityPlayer) {
      entityplayer = (EntityPlayer)dmgSrc.getTrueSource();
    } else {
      entityplayer = this.world.getNearestAttackablePlayer(pos, 64.0D, 64.0D);
    } 
    this.phaseManager.getCurrentPhase().onCrystalDestroyed(crystal, pos, dmgSrc, entityplayer);
  }
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    if (PHASE.equals(key) && this.world.isRemote)
      this.phaseManager.setPhase(PhaseList.getById(((Integer)getDataManager().get(PHASE)).intValue())); 
    super.notifyDataManagerChange(key);
  }
  
  public PhaseManager getPhaseManager() {
    return this.phaseManager;
  }
  
  public void addPotionEffect(PotionEffect potioneffectIn) {
    if (!potioneffectIn.getPotion().isBadEffect())
      super.addPotionEffect(potioneffectIn); 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}
}


