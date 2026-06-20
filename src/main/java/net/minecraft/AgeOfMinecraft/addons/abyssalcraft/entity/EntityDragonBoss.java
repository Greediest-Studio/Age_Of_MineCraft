package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import java.util.List;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.IPhaseAsorah;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseFireballAndStrafe;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseListAsorah;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseManagerAsorah;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseRamAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDragonBoss extends EntityTameBase implements IEntityMultiPart, Massive, Armored, Flying, Undead {
  public static final DataParameter<Integer> PHASE = EntityDataManager.createKey(EntityDragonBoss.class, DataSerializers.VARINT);
  
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
  
  public float prevAnimTime;
  
  public float animTime;
  
  public boolean slowed;
  
  private final PhaseManagerAsorah phaseManager;
  
  private int growlTime = 200;
  
  private int sittingDamageReceived;
  
  private final PathPoint[] pathPoints = new PathPoint[24];
  
  private final int[] neighbors = new int[24];
  
  private final PathHeap pathFindQueue = new PathHeap();
  
  public EntityDragonMinion healingcircle;
  
  public EntityDragonBoss(World par1World) {
    super(par1World);
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 9.0F, 9.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 9.0F, 9.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 12.0F, 12.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 6.0F, 6.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 6.0F, 6.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 6.0F, 6.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 6.0F, 6.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 6.0F, 6.0F) };
    setHealth(getMaxHealth());
    setSize(24.0F, 5.4F);
    this.noClip = true;
    this.phaseManager = new PhaseManagerAsorah(this);
    getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    this.ignoreFrustumCheck = true;
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public int playMusic() {
    return 6;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public PhaseManagerAsorah getPhaseManager() {
    return this.phaseManager;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setDarkenSky(true);
    if (getHealth() > getMaxHealth() * 0.75D && this.bossInfo.getColor() != BossInfo.Color.BLUE)
      this.bossInfo.setColor(BossInfo.Color.BLUE); 
    if (getHealth() < getMaxHealth() * 0.75D && getHealth() > getMaxHealth() / 2.0F && this.bossInfo.getColor() != BossInfo.Color.GREEN)
      this.bossInfo.setColor(BossInfo.Color.GREEN); 
    if (getHealth() < getMaxHealth() / 2.0F && getHealth() > getMaxHealth() / 4.0F && this.bossInfo.getColor() != BossInfo.Color.YELLOW)
      this.bossInfo.setColor(BossInfo.Color.YELLOW); 
    if (getHealth() < getMaxHealth() / 4.0F && getHealth() > 0.0F && this.bossInfo.getColor() != BossInfo.Color.RED)
      this.bossInfo.setColor(BossInfo.Color.RED); 
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean canUseGuardBlock() {
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
  
  public float getEyeHeight() {
    return 3.4875F;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultFittnessStat() {
    return 1.0F;
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
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public String getName() {
    return TextFormatting.AQUA + super.getName();
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(800.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    } 
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(25.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(PHASE, PhaseListAsorah.HOLDING_PATTERN.getId());
  }
  
  public double[] getMovementOffsets(int par1, float par2) {
    if (getHealth() <= 0.0F)
      par2 = 0.0F; 
    par2 = 1.0F - par2;
    int j = this.ringBufferIndex - par1 * 1 & 0x3F;
    int k = this.ringBufferIndex - par1 * 1 - 1 & 0x3F;
    double[] adouble = new double[3];
    double d0 = this.ringBuffer[j][0];
    double d1 = MathHelper.wrapDegrees(this.ringBuffer[k][0] - d0);
    adouble[0] = d0 + d1 * par2;
    d0 = this.ringBuffer[j][1];
    d1 = this.ringBuffer[k][1] - d0;
    adouble[1] = d0 + d1 * par2;
    adouble[2] = this.ringBuffer[j][2] + (this.ringBuffer[k][2] - this.ringBuffer[j][2]) * par2;
    return adouble;
  }
  
  protected boolean canDespawn() {
    return false;
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
  
  public void onLivingUpdate() {
    if (this.convertionInt > 0) {
      this.phaseManager.setPhase(PhaseListAsorah.LANDING);
      this.motionX *= 0.75D;
      this.motionY *= 0.75D;
      this.motionZ *= 0.75D;
    } 
    if (this.convertionInt > 0 && this.ticksExisted % 10 == 0) {
      EntityPlayer player = this.world.getClosestPlayerToEntity(this, -1.0D);
      if (player != null)
        for (int i1 = 0; i1 < this.convertionInt; i1++) {
          float f1 = i1 * 3.1415927F / timesToConvert() * 0.5F;
          this.world.spawnParticle(EnumParticleTypes.END_ROD, true, this.posX + MathHelper.cos(f1) * 12.0D, this.posY + this.height + 1.0D, this.posZ + MathHelper.sin(f1) * 12.0D, this.motionX, this.motionY, this.motionZ);
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
      if (getHealth() > getMaxHealth() / 5.0F)
        this.targetTasks.onUpdateTasks(); 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false)
          if (getDistanceSq(getAttackTarget()) < (this.reachWidth * this.reachWidth + ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width) * ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width)) + 9.0D && (this.ticksExisted + getEntityId()) % 10 == 0)
            attackEntityAsMob(getAttackTarget());
        if (isEntityAlive()) {
          ChunkLoadingEvent.updateLoaded(this);
        } else {
          ChunkLoadingEvent.stopLoading(this);
        } 
      } 
    } 
    setSilent(isAIDisabled());
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      float f = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
      float f1 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
      if (f1 <= -0.3F && f >= -0.3F)
        this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), getSoundVolume(), 0.8F + this.rand.nextFloat() * 0.3F, false); 
    } 
    this.isJumping = false;
    this.isAirBorne = false;
    this.onGround = true;
    if (getJukeboxToDanceTo() != null) {
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
      getNavigator().clearPath();
      IBlockState iblockstate = this.world.getBlockState(getJukeboxToDanceTo());
      Block block = iblockstate.getBlock();
      if (this.ticksExisted > 40)
        this.ticksExisted = 0; 
      if (block != Blocks.JUKEBOX || (block == Blocks.JUKEBOX && !(Boolean) iblockstate.getValue((IProperty) BlockJukebox.HAS_RECORD)) || getDistanceSqToCenter(this.jukeBoxToDanceTo) > 10000.0D) {
        setJukeboxToDanceTo(null);
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
      } 
    } 
    if (getJukeboxToDanceTo() == null && this.ticksExisted % 60 == 0) {
      int i11 = MathHelper.floor(this.posY);
      int l1 = MathHelper.floor(this.posX);
      int i2 = MathHelper.floor(this.posZ);
      boolean flag = false;
      for (int k2 = -12 - (int)this.width; k2 <= 12 + (int)this.width; k2++) {
        for (int l2 = -12 - (int)this.width; l2 <= 12 + (int)this.width; l2++) {
          for (int k = -12 - (int)this.height; k <= 12 + (int)this.height; k++) {
            int i3 = l1 + k2;
            int m = i11 + k;
            int n = i2 + l2;
            BlockPos blockpos = new BlockPos(i3, m, n);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (block == Blocks.JUKEBOX && (Boolean) iblockstate.getValue((IProperty) BlockJukebox.HAS_RECORD)) {
              setJukeboxToDanceTo(blockpos);
              getPhaseManager().setPhase(PhaseListAsorah.SITTING_SCANNING);
            } 
          } 
        } 
      } 
    } 
    this.dragonPartHead.width = this.dragonPartHead.height = 3.75F;
    this.dragonPartNeck.width = this.dragonPartNeck.height = 3.75F;
    this.dragonPartTail1.width = this.dragonPartTail1.height = 3.0F;
    this.dragonPartTail2.width = this.dragonPartTail2.height = 3.0F;
    this.dragonPartTail3.width = this.dragonPartTail3.height = 3.0F;
    this.dragonPartBody.height = 5.25F;
    this.dragonPartBody.width = 7.5F;
    this.dragonPartWing1.height = 4.5F;
    this.dragonPartWing1.width = 6.0F;
    this.dragonPartWing2.height = 4.5F;
    this.dragonPartWing2.width = 6.0F;
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
    this.dragonPartBody.setLocationAndAngles(this.posX, this.posY - (MathHelper.sin(this.animTime * 6.2831855F - 0.5F) * 0.15F), this.posZ, 0.0F, 0.0F);
    this.dragonPartWing1.onUpdate();
    this.dragonPartWing1.setLocationAndAngles(this.posX + (f3 * 6.75F), this.posY + 1.5D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ + (f19 * 6.75F), 0.0F, 0.0F);
    this.dragonPartWing2.onUpdate();
    this.dragonPartWing2.setLocationAndAngles(this.posX - (f3 * 6.75F), this.posY + 1.5D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ - (f19 * 6.75F), 0.0F, 0.0F);
    this.dragonPartNeck.onUpdate();
    this.dragonPartNeck.setLocationAndAngles(this.posX + (f19 * 5.25F), this.posY + 1.5D - (MathHelper.sin(this.animTime * 6.2831855F + 1.0F) * 0.1F) + (f18 * 3.0F) - (this.rotationPitch / 90.0F) * Math.PI * 0.375D, this.posZ - (f3 * 5.25F), 0.0F, 0.0F);
    this.dragonPartHead.onUpdate();
    this.dragonPartHead.setLocationAndAngles(this.posX + (f19 * 9.0F), this.posY + 1.5D - (MathHelper.sin(this.animTime * 6.2831855F) * 0.1F) + (f18 * 6.0F) - (this.rotationPitch / 90.0F) * Math.PI * 1.5D, this.posZ - (f3 * 9.0F), 0.0F, 0.0F);
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
      float f23 = 2.25F;
      float f24 = (i + 1) * 3.0F;
      MultiPartEntityPart.onUpdate();
      MultiPartEntityPart.setLocationAndAngles(this.posX - ((f19 * f23 + f22 * f24) * f16), this.posY - (MathHelper.sin(this.animTime * 6.2831855F + i) * 0.3F * (i + 1)) - (f18 * (2.0F + (1 * i))) + f23, this.posZ + ((f3 * f23 + f7 * f24) * f16), 0.0F, 0.0F);
    } 
    for (int l = 0; l < this.dragonPartArray.length; l++) {
      (this.dragonPartArray[l]).prevPosX = (avec3d[l]).x;
      (this.dragonPartArray[l]).prevPosY = (avec3d[l]).y;
      (this.dragonPartArray[l]).prevPosZ = (avec3d[l]).z;
    } 
    if (isWild() && getOwnerId() == null) {
      if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        double d0 = this.rand.nextGaussian() * 0.02D;
        double d1 = this.rand.nextGaussian() * 0.02D;
        double d2 = this.rand.nextGaussian() * 0.02D;
        double d3 = 10.0D;
        this.world.spawnParticle(EnumParticleTypes.TOWN_AURA, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d0 * d3, this.posY + (this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d2 * d3, d0, d1, d2);
      } 
    } else {
      if (getAttackTarget() != null && getJukeboxToDanceTo() == null && !getPhaseManager().getCurrentPhase().getIsStationary()) {
        faceEntity(getAttackTarget(), 10.0F, 60.0F);
      } else if (!isWild()) {
        this.rotationYawHead = this.rotationYaw;
        this.rotationPitch = getPhaseManager().getCurrentPhase().getIsStationary() ? 20.0F : 0.0F;
      } 
      this.convertionInt = 0;
      if (isHero())
        if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
          double d0 = this.rand.nextGaussian() * 0.02D;
          double d1 = this.rand.nextGaussian() * 0.02D;
          double d2 = this.rand.nextGaussian() * 0.02D;
          double d3 = 10.0D;
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartBody.posX + (this.rand.nextFloat() * this.dragonPartBody.width * 2.0F) - this.dragonPartBody.width - d0 * d3, this.dragonPartBody.posY + (this.rand.nextFloat() * this.dragonPartBody.height) - d1 * d3, this.dragonPartBody.posZ + (this.rand.nextFloat() * this.dragonPartBody.width * 2.0F) - this.dragonPartBody.width - d2 * d3, d0, 0.10000000149011612D, d2);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartHead.posX + (this.rand.nextFloat() * this.dragonPartHead.width * 2.0F) - this.dragonPartHead.width - d0 * d3, this.dragonPartHead.posY + (this.rand.nextFloat() * this.dragonPartHead.height) - d1 * d3, this.dragonPartHead.posZ + (this.rand.nextFloat() * this.dragonPartHead.width * 2.0F) - this.dragonPartHead.width - d2 * d3, d0, 0.10000000149011612D, d2);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartNeck.posX + (this.rand.nextFloat() * this.dragonPartNeck.width * 2.0F) - this.dragonPartNeck.width - d0 * d3, this.dragonPartNeck.posY + (this.rand.nextFloat() * this.dragonPartNeck.height) - d1 * d3, this.dragonPartNeck.posZ + (this.rand.nextFloat() * this.dragonPartNeck.width * 2.0F) - this.dragonPartNeck.width - d2 * d3, d0, 0.10000000149011612D, d2);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartWing1.posX + (this.rand.nextFloat() * this.dragonPartWing1.width * 2.0F) - this.dragonPartWing1.width - d0 * d3, this.dragonPartWing1.posY + (this.rand.nextFloat() * this.dragonPartWing1.height) - d1 * d3, this.dragonPartWing1.posZ + (this.rand.nextFloat() * this.dragonPartWing1.width * 2.0F) - this.dragonPartWing1.width - d2 * d3, d0, 0.10000000149011612D, d2);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartWing2.posX + (this.rand.nextFloat() * this.dragonPartWing2.width * 2.0F) - this.dragonPartWing2.width - d0 * d3, this.dragonPartWing2.posY + (this.rand.nextFloat() * this.dragonPartWing2.height) - d1 * d3, this.dragonPartWing2.posZ + (this.rand.nextFloat() * this.dragonPartWing2.width * 2.0F) - this.dragonPartWing2.width - d2 * d3, d0, 0.10000000149011612D, d2);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartTail1.posX + (this.rand.nextFloat() * this.dragonPartTail1.width * 2.0F) - this.dragonPartTail1.width - d0 * d3, this.dragonPartTail1.posY + (this.rand.nextFloat() * this.dragonPartTail1.height) - d1 * d3, this.dragonPartTail1.posZ + (this.rand.nextFloat() * this.dragonPartTail1.width * 2.0F) - this.dragonPartTail1.width - d2 * d3, d0, 0.10000000149011612D, d2);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartTail2.posX + (this.rand.nextFloat() * this.dragonPartTail2.width * 2.0F) - this.dragonPartTail2.width - d0 * d3, this.dragonPartTail2.posY + (this.rand.nextFloat() * this.dragonPartTail2.height) - d1 * d3, this.dragonPartTail2.posZ + (this.rand.nextFloat() * this.dragonPartTail2.width * 2.0F) - this.dragonPartTail2.width - d2 * d3, d0, 0.10000000149011612D, d2);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartTail3.posX + (this.rand.nextFloat() * this.dragonPartTail3.width * 2.0F) - this.dragonPartTail3.width - d0 * d3, this.dragonPartTail3.posY + (this.rand.nextFloat() * this.dragonPartTail3.height) - d1 * d3, this.dragonPartTail3.posZ + (this.rand.nextFloat() * this.dragonPartTail3.width * 2.0F) - this.dragonPartTail3.width - d2 * d3, d0, 0.10000000149011612D, d2);
        }  
    } 
    if (this.rand.nextInt(5) == 0 && !isWild() && getOwner().getRevengeTarget() != null && this.isOffensive)
      setAttackTarget(getOwner().getRevengeTarget()); 
    this.noClip = true;
    if (isSneaking()) {
      this.bossInfo.setVisible(false);
    } else if (isEntityAlive()) {
      this.bossInfo.setVisible(true);
    } 
    this.onGround = false;
    this.isAirBorne = true;
    this.prevAnimTime = this.animTime;
    if (getSpecialAttackTimer() > 0)
      if (this.moralRaisedTimer > 200) {
        setSpecialAttackTimer(getSpecialAttackTimer() - 2);
      } else {
        setSpecialAttackTimer(getSpecialAttackTimer() - 1);
      }  
    if (getHealth() <= 0.0F) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f21 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity)
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f21, 0.0D, 0.0D, 0.0D);
    } else {
      if (!getPhaseManager().getCurrentPhase().getIsStationary() && getPhaseManager().getCurrentPhase() != PhaseListAsorah.CHARGING_PLAYER && getPhaseManager().getCurrentPhase() != PhaseListAsorah.STRAFE_PLAYER && getAttackTarget() != null && this.ticksExisted % 60 == 0 && getRNG().nextInt(5) == 0)
        switch (this.rand.nextInt(2)) {
          case 0:
            getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
            getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER).setTarget(new Vec3d((getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ));
            break;
          case 1:
            getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
            getPhaseManager().getPhase(PhaseListAsorah.STRAFE_PLAYER).setTarget(getAttackTarget());
            break;
        }  
      if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityLivingBase && !(getControllingPassenger() instanceof EntityPlayer)) {
        EntityLivingBase passenger = (EntityLivingBase)getControllingPassenger();
        passenger.renderYawOffset = passenger.rotationYaw = passenger.rotationYawHead = this.rotationYaw;
      } 
      if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
        EntityPlayer passenger = (EntityPlayer)getControllingPassenger();
        passenger.fallDistance *= 0.0F;
        passenger.hurtResistantTime = 40;
        passenger.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 4));
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
        this.rotationYaw = passenger.rotationYaw - 180.0F;
        this.rotationPitch = passenger.rotationPitch;
        for (int m = 0; m < 256; m++) {
          double d1 = 0.005D;
          if (this.moralRaisedTimer > 200)
            d1 *= 2.0D; 
          Vec3d vec3 = passenger.getLook(1.0F);
          if (passenger.moveForward > 0.0F)
            setPosition(this.posX + vec3.x * d1, this.posY + vec3.y * d1, this.posZ + vec3.z * d1); 
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
        getPhaseManager().setPhase(PhaseListAsorah.LANDING_APPROACH); 
      if (!getPhaseManager().getCurrentPhase().getIsStationary() && !isWild() && (getOwner().getHealth() <= 6.0F || (!getOwner().getHeldItemMainhand().isEmpty() && getOwner().getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE)) && getRNG().nextInt(20) == 0) {
        getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
        getPhaseManager().getPhase(PhaseListAsorah.STRAFE_PLAYER).setTarget(getOwner());
      } 
      if (isEntityAlive() && getHealth() < getMaxHealth() && this.ticksExisted % ((getRevengeTarget() == null) ? 8 : 40) == 0) {
        heal(1.0F);
        spawnHeartParticle();
      } 
      if (!isWild() && getDistanceSq(getOwner()) >= 48400.0D)
        setLocationAndAngles((getOwner()).posX, (getOwner()).posY, (getOwner()).posZ, this.rotationYaw, this.rotationPitch); 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen(this) && this.rand.nextInt(160) == 0) {
        double d1 = this.dragonPartHead.posX;
        double d2 = this.dragonPartHead.posY + 0.25D;
        double d3 = this.dragonPartHead.posZ;
        fireLightning(getAttackTarget(), d1, d2, d3);
      } 
      if (getAttackTarget() != null && getAttackTarget().isEntityAlive() && getSpecialAttackTimer() <= 0 && isHero()) {
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
      if (getSpecialAttackTimer() > 1000)
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN); 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen(this) && ((this.ticksExisted % 6 == 0 && getSpecialAttackTimer() > 1000) || this.rand.nextInt(100) == 0)) {
        double d6 = this.dragonPartHead.posX;
        double d7 = this.dragonPartHead.posY + 2.0D;
        double d8 = this.dragonPartHead.posZ;
        double d9 = (getAttackTarget()).posX - d6;
        double d10 = (getAttackTarget()).posY + 1.0D - d7;
        double d11 = (getAttackTarget()).posZ - d8;
        this.world.playEvent(null, 1016, new BlockPos(this), 0);
        EntityCoraliumChargeOther entitydragonfireball = new EntityCoraliumChargeOther(this.world, this, d9, d10, d11);
        entitydragonfireball.posX = d6;
        entitydragonfireball.posY = d7;
        entitydragonfireball.posZ = d8;
        this.world.spawnEntity(entitydragonfireball);
      } 
      if (this.rand.nextInt(2) == 0 && !isWild() && getOwner().getRevengeTarget() != null)
        setAttackTarget(getOwner().getRevengeTarget()); 
      if (getAttackTarget() != null && (!getAttackTarget().isEntityAlive() || !this.isOffensive || false))
        setAttackTarget(null); 
      updateHealingCircle();
      float f12 = 0.2F / (MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
      f12 *= (float)Math.pow(2.0D, this.motionY);
      if (this.phaseManager.getCurrentPhase().getIsStationary() || getPhaseManager().getCurrentPhase() == PhaseListAsorah.CHARGING_PLAYER) {
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
      int k;
      for (k = 0; k < 2; k++) {
        if (ACConfig.particleEntity)
          AbyssalCraft.proxy.spawnParticle("CorBlood", this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D); 
      } 
      this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
      if (isAIDisabled()) {
        this.animTime = 0.5F;
      } else if (isEntityAlive()) {
        if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
          EntityPlayer passenger = (EntityPlayer)getControllingPassenger();
          passenger.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 4));
          getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
          this.renderYawOffset = this.rotationYaw = passenger.rotationYawHead + 180.0F;
          this.rotationPitch = 0.0F;
          for (int m = 0; m < 256; m++) {
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
        if (this.ringBufferIndex < 0)
          for (k = 0; k < this.ringBuffer.length; k++) {
            this.ringBuffer[k][0] = this.rotationYaw;
            this.ringBuffer[k][1] = this.posY;
          }  
        if (++this.ringBufferIndex == this.ringBuffer.length)
          this.ringBufferIndex = 0; 
        this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
        this.ringBuffer[this.ringBufferIndex][1] = this.posY;
        if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
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
          IPhaseAsorah iphase = this.phaseManager.getCurrentPhase();
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
            if (isBeingRidden()) {
              double look = 2.0D;
              Vec3d vec3 = getLook(1.0F);
              d6 = this.posX + vec3d.x * look;
              d7 = this.posY + vec3d.y * look;
              d8 = this.posX + vec3d.z * look;
            } 
            double d3 = d6 * d6 + d7 * d7 + d8 * d8;
            float f6 = iphase.getMaxRiseOrFall();
            d7 = MathHelper.clamp(d7 / MathHelper.sqrt(d6 * d6 + d8 * d8), -f6, f6);
            this.motionY += d7 * 0.10000000149011612D;
            this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
            double d4 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d6, d8) * 57.29577951308232D - this.rotationYaw), -50.0D, 50.0D);
            Vec3d vec3d1 = (new Vec3d(vec3d.x - this.posX, vec3d.y - this.posY, vec3d.z - this.posZ)).normalize();
            Vec3d vec3d2 = (new Vec3d(MathHelper.sin(this.rotationYaw * 0.017453292F), this.motionY, -MathHelper.cos(this.rotationYaw * 0.017453292F))).normalize();
            float f8 = Math.max(((float)vec3d2.dotProduct(vec3d1) + 0.5F) / 1.5F, 0.0F);
            this.randomYawVelocity *= 0.8F;
            this.randomYawVelocity = (float)(this.randomYawVelocity + d4 * iphase.getYawFactor());
            this.rotationYaw += this.randomYawVelocity * 0.1F;
            float f9 = (float)(2.0D / (d3 + 1.0D));
            float f10 = 0.06F;
            moveRelative(0.0F, 0.0F, -1.0F, f10 * (f8 * f9 + 1.0F - f9));
            if (getPhaseManager().getCurrentPhase() == PhaseListAsorah.CHARGING_PLAYER) {
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
            Vec3d vec3d3 = (new Vec3d(this.motionX, this.motionY, this.motionZ)).normalize();
            float f11 = ((float)vec3d3.dotProduct(vec3d2) + 1.0F) / 2.0F;
            f11 = 0.8F + 0.15F * f11;
            if (isBeingRidden())
              f11 = 0.91F; 
            this.motionX *= f11;
            this.motionZ *= f11;
            this.motionY *= 0.9100000262260437D;
          } 
        } 
      } 
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartBody.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D)));
      collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.getEntityBoundingBox().grow(6.0D, 4.0D, 6.0D).offset(0.0D, -2.0D, 0.0D)));
      collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.getEntityBoundingBox().grow(6.0D, 4.0D, 6.0D).offset(0.0D, -2.0D, 0.0D)));
      collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail1.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D)));
      collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail2.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D)));
      collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail3.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D)));
      attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.getEntityBoundingBox().grow(3.0D, 3.0D, 3.0D).offset(0.0D, -2.0D, 0.0D)));
      attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartNeck.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D).offset(0.0D, -2.0D, 0.0D)));
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    if (this.deathTicks > 0)
      par1NBTTagCompound.setInteger("DeathTicks", this.deathTicks); 
    par1NBTTagCompound.setInteger("DragonPhase", this.phaseManager.getCurrentPhase().getPhaseList().getId());
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    this.deathTicks = par1NBTTagCompound.getInteger("DeathTicks");
    if (par1NBTTagCompound.hasKey("DragonPhase"))
      this.phaseManager.setPhase(PhaseListAsorah.getById(par1NBTTagCompound.getInteger("DragonPhase"))); 
  }
  
  private void updateHealingCircle() {
    if (this.healingcircle != null)
      if (this.healingcircle.isDead) {
        this.healingcircle = null;
      } else if ((this.ticksExisted + this.healingcircle.getEntityId()) % 5 == 0) {
        heal(1.0F);
      }  
    if (this.rand.nextInt(10) == 0) {
      float f = 64.0F;
      List<?> list = this.world.getEntitiesWithinAABB(EntityDragonMinion.class, getEntityBoundingBox().grow(f, f, f));
      EntityDragonMinion entitydragonminion = null;
      double d0 = Double.MAX_VALUE;
        for (Object o : list) {
            EntityDragonMinion entitydragonminion1 = (EntityDragonMinion) o;
            double d1 = entitydragonminion1.getDistanceSq(this);
            if (d1 < d0) {
                d0 = d1;
                entitydragonminion = entitydragonminion1;
            }
        }
      this.healingcircle = entitydragonminion;
    } 
  }
  
  private void collideWithEntities(List<?> par1List) {
    double d0 = ((this.dragonPartBody.getEntityBoundingBox()).minX + (this.dragonPartBody.getEntityBoundingBox()).maxX) / 2.0D;
    double d1 = ((this.dragonPartBody.getEntityBoundingBox()).minZ + (this.dragonPartBody.getEntityBoundingBox()).maxZ) / 2.0D;
      for (Object o : par1List) {
          Entity entity = (Entity) o;
          if (entity instanceof EntityLivingBase && !false) {
              double d2 = entity.posX - d0;
              double d3 = entity.posZ - d1;
              double d4 = d2 * d2 + d3 * d3;
              entity.addVelocity(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
              if (entity instanceof EntityLivingBase) {
                  ((EntityLivingBase) entity).renderYawOffset = entity.rotationYaw = ((EntityLivingBase) entity).rotationYawHead = (float) MathHelper.atan2(entity.motionZ, entity.motionX) * 57.295776F - 90.0F;
                  ((EntityLivingBase) entity).setRevengeTarget(null);
                  if (entity instanceof EntityLiving)
                      ((EntityLiving) entity).setAttackTarget(null);
              }
          }
      }
  }
  
  private void attackEntitiesInList(List<?> par1List) {
      for (Object o : par1List) {
          Entity entity = (Entity) o;
          if (entity.ticksExisted + entity.getEntityId() % 10 == 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && entity instanceof EntityLivingBase && !false) {
              attackEntityAsMob(entity);
              playSound(SoundEvents.BLOCK_NOTE_HAT, 5.0F, 0.5F);
              if (ACConfig.hardcoreMode && entity instanceof EntityPlayer)
                  entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.0F * (float) (Math.max(ACConfig.damageAmpl, 1.0D)));
          }
      }
  }
  
  private float simplifyAngle(double par1) {
    return (float)MathHelper.wrapDegrees(par1);
  }
  
  public int initPathPoints() {
    if (this.pathPoints[0] == null) {
      int i = 0;
      int j = 0;
      int k = 0;
      int l = 0;
      for (int i1 = 0; i1 < 24; i1++) {
        int j1 = 5;
        if (i1 < 12) {
          i = (int)(60.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.2617994F * i1)));
          k = (int)(60.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.2617994F * i1)));
        } else if (i1 < 20) {
          l = i1 - 12;
          i = (int)(40.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.3926991F * l)));
          k = (int)(40.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.3926991F * l)));
          j1 += 10;
        } else {
          l = i1 - 20;
          i = (int)(20.0F * MathHelper.cos(2.0F * (-3.1415927F + 0.7853982F * l)));
          k = (int)(20.0F * MathHelper.sin(2.0F * (-3.1415927F + 0.7853982F * l)));
        } 
        j = Math.max(this.world.getSeaLevel() + 10, this.world.getTopSolidOrLiquidBlock(new BlockPos(i, 0, k)).getY() + j1);
        this.pathPoints[i1] = new PathPoint(i, j, k);
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
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 10.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadPartYOffset(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
    double d0;
    IPhaseAsorah iphase = this.phaseManager.getCurrentPhase();
    PhaseListAsorah<? extends IPhaseAsorah> phaselist = iphase.getPhaseList();
    if (phaselist != PhaseListAsorah.LANDING && phaselist != PhaseListAsorah.TAKEOFF) {
      if (iphase.getIsStationary()) {
        d0 = p_184667_1_;
      } else if (p_184667_1_ == 6) {
        d0 = 0.0D;
      } else {
        d0 = p_184667_3_[1] - p_184667_2_[1];
      } 
    } else {
      BlockPos blockpos = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
      if (getOwner() != null)
        blockpos = new BlockPos(getOwner());
      float f = Math.max(MathHelper.sqrt(getDistanceSqToCenter(blockpos)) / 4.0F, 1.0F);
      d0 = (p_184667_1_ / f);
    } 
    return (float)d0;
  }
  
  public Vec3d getHeadLookVec(float p_184665_1_) {
    Vec3d vec3d;
    IPhaseAsorah iphase = this.phaseManager.getCurrentPhase();
    PhaseListAsorah<? extends IPhaseAsorah> phaselist = iphase.getPhaseList();
    if (phaselist != PhaseListAsorah.LANDING && phaselist != PhaseListAsorah.TAKEOFF) {
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
        blockpos = new BlockPos(getOwner());
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
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    if (PHASE.equals(key) && net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      this.phaseManager.setPhase(PhaseListAsorah.getById(getDataManager().get(PHASE)));
    super.notifyDataManagerChange(key);
  }
  
  public int getNearestPpIdx(double x, double y, double z) {
    float f = 10000.0F;
    int i = 0;
    PathPoint pathpoint = new PathPoint(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    int j = 12;
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
  
  public Path findPath(int startIdx, int finishIdx, PathPoint andThen) {
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
    int j = 12;
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
        if ((this.neighbors[k] & 1 << i1) > 0) {
          PathPoint pathpoint3 = this.pathPoints[i1];
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
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    if (getHealth() <= 0.0F)
      this.phaseManager.setPhase(PhaseListAsorah.DYING); 
    damage = this.phaseManager.getCurrentPhase().getAdjustedDamage(dragonPart, source, damage);
    if (dragonPart != this.dragonPartHead)
      damage = damage / 5.0F + Math.min(damage, 1.0F); 
    if (isEntityInvulnerable(source) || damage < 0.01F || source == DamageSource.WITHER || source == DamageSource.IN_WALL || source == DamageSource.DROWN || source == DamageSource.CRAMMING || source == DamageSource.CACTUS)
      return false; 
    float f = getHealth();
    if (getHealth() <= 0.0F && !this.phaseManager.getCurrentPhase().getIsStationary() && !isWild())
      this.phaseManager.setPhase(PhaseListAsorah.DYING); 
    if (this.phaseManager.getCurrentPhase().getIsStationary()) {
      this.sittingDamageReceived = (int)(this.sittingDamageReceived + f - getHealth());
      if (this.sittingDamageReceived > 50.0F) {
        this.sittingDamageReceived = 0;
        if (source.getTrueSource() instanceof EntityLivingBase)
          switch (this.rand.nextInt(2)) {
            case 0:
              getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
              getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER).setTarget(new Vec3d(source.getTrueSource().posX, ((EntityLivingBase)source.getTrueSource()).posY, ((EntityLivingBase)source.getTrueSource()).posZ));
              break;
            case 1:
              getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
              getPhaseManager().getPhase(PhaseListAsorah.STRAFE_PLAYER).setTarget((EntityLivingBase)source.getTrueSource());
              break;
          }  
      } 
    } 
    if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase)
      if (!isWild() && source.getTrueSource() == getOwner()) {
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
      } else {
        setAttackTarget((EntityLivingBase)source.getTrueSource());
        if (this.rand.nextInt(2) == 0) {
          getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
          getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER).setTarget(new Vec3d((source.getTrueSource()).posX, (source.getTrueSource()).posY, (source.getTrueSource()).posZ));
        } 
      }  
    return super.attackEntityFrom(source, damage);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return attackEntityFromPart(this.dragonPartBody, source, amount);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  public double getMountedYOffset() {
    return 4.825D;
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
        passenger.setPosition(this.dragonPartTail3.posX, this.dragonPartTail3.posY + 1.5D, this.dragonPartTail3.posZ); 
      if (i == 4)
        passenger.setPosition(this.dragonPartTail2.posX, this.dragonPartTail2.posY + 1.5D, this.dragonPartTail2.posZ); 
      if (i == 3)
        passenger.setPosition(this.dragonPartTail1.posX, this.dragonPartTail1.posY + 1.5D, this.dragonPartTail1.posZ); 
      if (i == 2)
        passenger.setPosition(this.posX + (f11 * 1.5F), this.posY + getEyeHeight() + 1.5D, this.posZ - (f4 * 1.5F)); 
      if (i == 1)
        passenger.setPosition(this.posX + (f11 * -1.5F), this.posY + getEyeHeight() + 1.5D, this.posZ - (f4 * -1.5F)); 
      if (i == 0)
        passenger.setPosition(this.dragonPartNeck.posX, this.dragonPartNeck.posY + 1.5D, this.dragonPartNeck.posZ); 
    } 
  }
  
  public void addPotionEffect(PotionEffect potioneffectIn) {
    if (!potioneffectIn.getPotion().isBadEffect())
      super.addPotionEffect(potioneffectIn); 
  }
  
  protected void onDeathUpdate() {
    if (usesVanillaDeathUpdate()) {
      super.onDeathUpdate();
      return;
    }
    this.deathTicks++;
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity)
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (this.deathTicks > 150 && this.deathTicks % 5 == 0) {
        int i = 1500;
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
          this.world.spawnEntity(new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.chunk_of_coralium)));
          this.world.spawnEntity(new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.refined_coralium_ingot)));
          this.world.spawnEntity(new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.coralium_plagued_flesh)));
        } 
      } 
      if (this.deathTicks == 1)
        this.world.playBroadcastSound(1028, new BlockPos(this), 0);
    } 
    move(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
    this.renderYawOffset = this.rotationYaw += 20.0F;
    if (this.deathTicks == 20 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      SpecialTextUtil.OblivionaireGroup(this.world, I18n.translateToLocal("message.asorah.death.1"));
    if (this.deathTicks == 80 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      SpecialTextUtil.OblivionaireGroup(this.world, I18n.translateToLocal("message.asorah.death.2"));
    if (this.deathTicks == 140 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      SpecialTextUtil.OblivionaireGroup(this.world, I18n.translateToLocal("message.asorah.death.3"));
    if (this.deathTicks >= 200 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      List<Entity> list = net.minecraft.AgeOfMinecraft.util.EntityCompat.loadedEntityList(this.world);
      if (list != null)
          for (Entity entity : list) {
              if (entity instanceof EntityJzahar && entity.isEntityAlive())
                  SpecialTextUtil.JzaharGroup(this.world, false ? I18n.translateToLocal("message.jzaharhelpful.snidecomment.asorah") : I18n.translateToLocal("message.jzahar.snidecomment.asorah"));
          }
      setDead();
      this.world.spawnEntity(new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(ACItems.eye_of_the_abyss)));
    } 
  }
  
  private int posneg(int num) {
    return this.rand.nextBoolean() ? this.rand.nextInt(num) : (-1 * this.rand.nextInt(num));
  }
  
  public Entity[] getParts() {
    return this.dragonPartArray;
  }
  
  public boolean canBeCollidedWith() {
    return (this.world.getClosestPlayerToEntity(this, this.width) != null && isEntityAlive());
  }
  
  public World getWorld() {
    return this.world;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_ENDERDRAGON_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_ENDERDRAGON_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_WITHER_DEATH;
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
  
  public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}
}


