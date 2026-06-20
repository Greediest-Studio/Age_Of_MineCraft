package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.brandon3055.brandonscore.utils.Utils;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityDragonFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseList;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class EntityChaosGuardian extends EntityEnderDragon {
  public int homeX = 0;
  
  public int homeY = -1;
  
  public int homeZ = 0;
  
  public boolean homeSet = false;
  
  public double targetX;
  
  public double targetY;
  
  public double targetZ;
  
  public boolean forceNewTarget;
  
  private int nextAttackTimer = 100;
  
  private int attackInProgress = -1;
  
  private int attackTimer = 0;
  
  private EnumBehaviour previousBehaviour = EnumBehaviour.ROAMING;
  
  private int ignitionChargeTimer = 0;
  
  private static final int ATTACK_FIREBALL_CHARGE = 0;
  
  private static final int ATTACK_FIREBALL_CHASER = 1;
  
  private static final int ATTACK_ENERGY_CHASER = 2;
  
  private static final int ATTACK_CHAOS_CHASER = 3;
  
  private static final int ATTACK_TELEPORT = 4;
  
  private static final int ATTACK_LIGHTNING_BOLT = 5;
  
  private static final int ATTACK_ENDER_CHARGE = 6;
  
  private static final int ATTACK_WILD_CARD = 7;
  
  private static final int ATTACK_HEAVY_WIND = 8;
  
  public float circlePosition = 0.0F;
  
  public float circleDirection = 1.0F;
  
  public EnumBehaviour behaviour = EnumBehaviour.ROAMING;
  
  private static final List<WeightedAttack> weightedAttacks = Lists.newArrayList(new WeightedAttack(16, 0), new WeightedAttack(14, 1), new WeightedAttack(12, 2), new WeightedAttack(10, 3), new WeightedAttack(5, 5), new WeightedAttack(10, 6));
  
  private static final List<WeightedAttack> weightedLowHealthAttaxks = Lists.newArrayList(new WeightedAttack(5, 1), new WeightedAttack(5, 4), new WeightedAttack(10, 2), new WeightedAttack(15, 3), new WeightedAttack(15, 5), new WeightedAttack(105, 6));
  
  private static final List<WeightedBehaviour> weightedBehaviours = Lists.newArrayList(new WeightedBehaviour(1, EnumBehaviour.LOW_HEALTH_STRATEGY), new WeightedBehaviour(10, EnumBehaviour.GUARDING), new WeightedBehaviour(20, EnumBehaviour.CHARGING), new WeightedBehaviour(12, EnumBehaviour.FIREBOMB), new WeightedBehaviour(20, EnumBehaviour.CIRCLE_PLAYER));
  
  public EntityChaosGuardian(World par1World) {
    super(par1World);
    this.bossInfo.setColor(BossInfo.Color.RED);
    this.bossInfo.setDarkenSky(true);
    this.bossInfo.setOverlay(BossInfo.Overlay.NOTCHED_20);
    setLevel(300);
    this.homeX = (int)this.posX;
    this.homeY = (int)this.posY;
    this.homeZ = (int)this.posZ;
    this.targetX = this.homeX;
    this.targetY = this.homeY;
    this.targetZ = this.homeZ;
    playLivingSound();
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "chaos_guardian";
  }
  
  public int playMusic() {
    return 10;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setDarkenSky(true);
    if (getHealth() > getMaxHealth() * 0.75D && this.bossInfo.getColor() != BossInfo.Color.RED)
      this.bossInfo.setColor(BossInfo.Color.RED); 
    if (getHealth() < getMaxHealth() * 0.75D && getHealth() > getMaxHealth() / 2.0F && this.bossInfo.getColor() != BossInfo.Color.PURPLE)
      this.bossInfo.setColor(BossInfo.Color.PURPLE); 
    if (getHealth() < getMaxHealth() / 2.0F && getHealth() > getMaxHealth() / 4.0F && this.bossInfo.getColor() != BossInfo.Color.PINK)
      this.bossInfo.setColor(BossInfo.Color.PINK); 
    if (getHealth() < getMaxHealth() / 4.0F && getHealth() > 0.0F && this.bossInfo.getColor() != BossInfo.Color.WHITE)
      this.bossInfo.setColor(BossInfo.Color.WHITE); 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2000.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(50.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20.0D);
  }
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 100.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 100.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 100.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public void onLivingUpdate() {
    if (getJukeboxToDanceTo() == null)
      clearPerchState();
    if (this.ticksExisted % 10 == 0)
      heal(1.0F); 
    if (this.motionY >= 2.0D || this.motionY <= -2.0D)
      this.motionY = 0.0D; 
    if (isEntityAlive() && isCarryingCrystal())
      setHealth(getHealth() + 0.1F); 
    if (this.innerRotation > 500)
      this.innerRotation = 0; 
    if (getAttackTarget() != null && getJukeboxToDanceTo() == null)
      faceEntity(getAttackTarget(), 10.0F, 90.0F);
    if (getAttackTarget() != null && (!getAttackTarget().isEntityAlive() || false))
      setAttackTarget(null); 
    if (!hasActiveCombatTarget() && this.behaviour != EnumBehaviour.DEAD && this.behaviour != EnumBehaviour.GUARDING && this.behaviour != EnumBehaviour.ROAMING && this.behaviour != EnumBehaviour.GO_HOME)
      this.behaviour = (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) > 240.0D) ? EnumBehaviour.GO_HOME : EnumBehaviour.ROAMING; 
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
    this.isJumping = false;
    this.isAirBorne = true;
    this.onGround = false;
    if (getJukeboxToDanceTo() != null) {
      getNavigator().clearPath();
      IBlockState iblockstate = this.world.getBlockState(getJukeboxToDanceTo());
      Block block = iblockstate.getBlock();
      if (this.ticksExisted > 100)
        this.ticksExisted = 20; 
      if (this.innerRotation > 500)
        this.innerRotation = 0; 
      this.sitting = true;
      this.motionX = this.motionY = this.motionZ *= 0.0D;
      setPositionAndUpdate(getJukeboxToDanceTo().getX(), getJukeboxToDanceTo().getY() + 12.0D, getJukeboxToDanceTo().getZ());
      if (block != Blocks.JUKEBOX || (block == Blocks.JUKEBOX && !(Boolean) iblockstate.getValue((IProperty) BlockJukebox.HAS_RECORD)) || getDistanceSqToCenter(this.jukeBoxToDanceTo) > 10000.0D) {
        setJukeboxToDanceTo(null);
        this.sitting = false;
      } 
    } 
    if (getJukeboxToDanceTo() == null && this.ticksExisted % 60 == 0) {
      int i11 = MathHelper.floor(this.posY);
      int l1 = MathHelper.floor(this.posX);
      int i2 = MathHelper.floor(this.posZ);
      for (int k2 = -12 - (int)this.width; k2 <= 12 + (int)this.width; k2++) {
        for (int l2 = -12 - (int)this.width; l2 <= 12 + (int)this.width; l2++) {
          for (int k = -18 - (int)this.height; k <= 18 + (int)this.height; k++) {
            int i3 = l1 + k2;
            int m = i11 + k;
            int n = i2 + l2;
            BlockPos blockpos = new BlockPos(i3, m, n);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (block == Blocks.JUKEBOX && (Boolean) iblockstate.getValue((IProperty) BlockJukebox.HAS_RECORD)) {
              setJukeboxToDanceTo(blockpos);
              if (this.ticksExisted > 100)
                this.ticksExisted = 20; 
              if (this.innerRotation > 500)
                this.innerRotation = 0; 
            } 
          } 
        } 
      } 
    } 
    if (!this.homeSet) {
      this.homeX = (int)this.posX;
      this.homeY = (int)this.posY;
      this.homeZ = (int)this.posZ;
      this.targetX = this.homeX;
      this.targetY = this.homeY;
      this.targetZ = this.homeZ;
      this.homeSet = true;
    }
    if (!isWild()) {
      this.homeX = (int)(getOwner()).posX;
      this.homeY = (int)(getOwner()).posY + 5;
      this.homeZ = (int)(getOwner()).posZ;
    } 
    float moveSpeedMultiplier = this.behaviour.dragonSpeed;
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      float f = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
      float f1 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
      if (f1 <= -0.3F && f >= -0.3F)
        if (this.deathTicks <= 0)
          if (isSneaking()) {
            this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 1.0F, 0.6F + this.rand.nextFloat() * 0.3F, false);
          } else {
            this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.HOSTILE, 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
          }   
    } 
    this.prevAnimTime = this.animTime;
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      updateTarget();
      customAIUpdate();
      if (this.behaviour == EnumBehaviour.FIREBOMB && Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, this.homeX, (this.homeY + 30), this.homeZ) <= 3.0D)
        moveSpeedMultiplier = 0.0F; 
    } 
    if (getHealth() <= 0.0F) {
      this.behaviour = EnumBehaviour.DEAD;
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
    } 
    this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
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
      float f = 0.2F / (MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
      f *= (moveSpeedMultiplier == 0.0F) ? 1.0F : moveSpeedMultiplier;
      f *= (float)Math.pow(2.0D, this.motionY);
      if (isBeingRidden() || getJukeboxToDanceTo() != null) {
        this.animTime += 0.1F;
      } else if (this.slowed) {
        this.animTime += f * 0.5F;
      } else {
        this.animTime += f;
      } 
      if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        if (this.newPosRotationIncrements > 0) {
          double d10 = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
          double d0 = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
          double d1 = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
          double d2 = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
          this.rotationYaw = (float)(this.rotationYaw + d2 / this.newPosRotationIncrements);
          this.rotationPitch = (float)(this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
          this.newPosRotationIncrements--;
          setPosition(d10, d0, d1);
          setRotation(this.rotationYaw, this.rotationPitch);
        } 
      } else {
        double d10 = this.targetX - this.posX;
        double d0 = this.targetY - this.posY;
        double d1 = this.targetZ - this.posZ;
        double d2 = d10 * d10 + d0 * d0 + d1 * d1;
        if (isSneaking() && getAttackTarget() == null) {
          this.targetX = this.homeX;
          this.targetY = this.homeY;
          this.targetZ = this.homeZ;
        } 
        if (getAttackTarget() != null) {
          if (this.behaviour == EnumBehaviour.CIRCLE_PLAYER) {
            this.targetX = (getAttackTarget()).posX + (int)(Math.cos(this.circlePosition) * 60.0D);
            this.targetZ = (getAttackTarget()).posZ + (int)(Math.sin(this.circlePosition) * 60.0D);
            moveSpeedMultiplier = 1.0F + Math.min((float)Utils.getDistanceAtoB(this.targetX, this.targetZ, this.posX, this.posZ) / 50.0F * 3.0F, 3.0F);
          } else {
            this.targetX = (getAttackTarget()).posX;
            this.targetZ = (getAttackTarget()).posZ;
          } 
          double d3 = this.targetX - this.posX;
          double d5 = this.targetZ - this.posZ;
          double d7 = Math.sqrt(d3 * d3 + d5 * d5);
          double d8 = 0.4000000059604645D + d7 / 80.0D - 1.0D;
          if (d8 > 10.0D)
            d8 = 10.0D; 
          this.targetY = Flying.clampFlightY((getAttackTarget().getEntityBoundingBox()).minY + d8 + ((this.behaviour == EnumBehaviour.CIRCLE_PLAYER) ? 50 : 10));
        } else if (this.behaviour != EnumBehaviour.FIREBOMB) {
          this.targetX += this.rand.nextGaussian() * 2.0D;
          this.targetZ += this.rand.nextGaussian() * 2.0D;
        } 
        if (this.forceNewTarget || d2 < 100.0D || d2 > 22500.0D || this.collidedHorizontally || this.collidedVertically)
          setNewTarget(); 
        d0 /= MathHelper.sqrt(d10 * d10 + d1 * d1);
        float f12 = 0.6F;
        if (d0 < -f12)
          d0 = -f12; 
        if (d0 > f12)
          d0 = f12; 
        this.motionY += d0 * 0.10000000149011612D;
        this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
        double d4 = 180.0D - Math.atan2(d10, d1) * 180.0D / Math.PI;
        double d6 = MathHelper.wrapDegrees(d4 - this.rotationYaw);
        if (d6 > 50.0D)
          d6 = 50.0D; 
        if (d6 < -50.0D)
          d6 = -50.0D; 
        Vec3d vec3 = (new Vec3d(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ)).normalize();
        Vec3d vec32 = (new Vec3d(MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), this.motionY, -MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F))).normalize();
        float f5 = (float)(vec32.dotProduct(vec3) + 0.5D) / 1.5F;
        if (f5 < 0.0F)
          f5 = 0.0F; 
        this.randomYawVelocity *= 0.8F;
        float f6 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
        double d9 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;
        if (d9 > 40.0D)
          d9 = 40.0D; 
        this.randomYawVelocity = (float)(this.randomYawVelocity + d6 * 0.699999988079071D / d9 / f6);
        this.rotationYaw += this.randomYawVelocity * 0.1F;
        float f1 = (float)(2.0D / (d9 + 1.0D));
        float f8 = 0.06F;
        moveRelative(0.0F, 0.0F, -1.0F, 0.06F * (f1 * f8 + 1.0F - f8));
        if (isSneaking()) {
          move(MoverType.SELF, this.motionX * (isBeingRidden() ? 0.375D : 0.25D), this.motionY * (isBeingRidden() ? 0.375D : 0.25D), this.motionZ * (isBeingRidden() ? 0.375D : 0.25D));
        } else if (this.slowed) {
          move(MoverType.SELF, this.motionX * 0.800000011920929D * moveSpeedMultiplier, this.motionY * 0.800000011920929D * moveSpeedMultiplier, this.motionZ * 0.800000011920929D * moveSpeedMultiplier);
        } else if (this.moralRaisedTimer > 200) {
          move(MoverType.SELF, this.motionX * (isBeingRidden() ? 3.0D : 2.0D), this.motionY * (isBeingRidden() ? 3.0D : 2.0D), this.motionZ * (isBeingRidden() ? 3.0D : 2.0D));
        } else {
          move(MoverType.SELF, this.motionX * (isBeingRidden() ? 1.5D : 1.0D), this.motionY * (isBeingRidden() ? 1.5D : 1.0D), this.motionZ * (isBeingRidden() ? 1.5D : 1.0D));
        } 
        Vec3d vec31 = (new Vec3d(this.motionX, this.motionY, this.motionZ)).normalize();
        float f9 = (float)(vec31.dotProduct(vec32) + 1.0D) / 2.0F;
        f9 = 0.8F + 0.15F * f9;
        this.motionX *= f9;
        this.motionZ *= f9;
        this.motionY *= 0.9100000262260437D;
      } 
    } 
    this.dragonPartHead.width = this.dragonPartHead.height = 2.5F;
    this.dragonPartNeck.width = this.dragonPartNeck.height = 2.5F;
    this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
    this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
    this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
    this.dragonPartBody.height = 3.5F;
    this.dragonPartBody.width = 5.0F;
    this.dragonPartWing1.height = 3.0F;
    this.dragonPartWing1.width = 4.0F;
    this.dragonPartWing2.height = 3.0F;
    this.dragonPartWing2.width = 4.0F;
    Vec3d[] avec3d = new Vec3d[this.dragonPartArray.length];
    for (int j = 0; j < this.dragonPartArray.length; j++)
      avec3d[j] = new Vec3d((this.dragonPartArray[j]).posX, (this.dragonPartArray[j]).posY, (this.dragonPartArray[j]).posZ); 
    float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F * 0.017453292F;
    float f16 = MathHelper.cos(f14);
    float f18 = MathHelper.sin(f14);
    float f7 = this.rotationYaw * 0.017453292F;
    float f19 = MathHelper.sin(f7);
    float f3 = MathHelper.cos(f7);
    double[] adouble = getMovementOffsets(5, 1.0F);
    double[] adouble1 = getMovementOffsets(14, 1.0F);
    this.dragonPartBody.onUpdate();
    this.dragonPartBody.setLocationAndAngles(this.posX, this.posY - (MathHelper.sin(this.animTime * 6.2831855F - 0.5F) * 0.1F), this.posZ, 0.0F, 0.0F);
    this.dragonPartWing1.onUpdate();
    this.dragonPartWing1.setLocationAndAngles(this.posX + (f3 * 4.5F), this.posY + 1.0D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ + (f19 * 4.5F), 0.0F, 0.0F);
    this.dragonPartWing2.onUpdate();
    this.dragonPartWing2.setLocationAndAngles(this.posX - (f3 * 4.5F), this.posY + 1.0D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ - (f19 * 4.5F), 0.0F, 0.0F);
    this.dragonPartNeck.onUpdate();
    this.dragonPartNeck.setLocationAndAngles(this.posX + (f19 * 3.5F), this.posY + 1.0D - (MathHelper.sin(this.animTime * 6.2831855F + 1.0F) * 0.1F) + (f18 * 2.0F) - (this.rotationPitch / 90.0F) * Math.PI * 0.25D, this.posZ - (f3 * 3.5F), 0.0F, 0.0F);
    this.dragonPartHead.onUpdate();
    this.dragonPartHead.setLocationAndAngles(this.posX + (f19 * 6.0F), this.posY + 1.0D - (MathHelper.sin(this.animTime * 6.2831855F) * 0.1F) + (f18 * 4.0F) - (this.rotationPitch / 90.0F) * Math.PI * 1.0D, this.posZ - (f3 * 6.0F), 0.0F, 0.0F);
    for (int i = 0; i < 3; i++) {
      ExtendMultiPartEntityPart extendMultiPartEntityPart = this.dragonPartTail1;
      MultiPartEntityPart MultiPartEntityPart = null;
      if (i == 0)
        extendMultiPartEntityPart = this.dragonPartTail1; 
      if (i == 1)
        extendMultiPartEntityPart = this.dragonPartTail2; 
      if (i == 2)
        extendMultiPartEntityPart = this.dragonPartTail3; 
      adouble1 = getMovementOffsets(12 + i * 2, 1.0F);
      float f21 = this.rotationYaw * 0.017453292F + simplifyAngle(adouble1[0] - adouble[0]) * 0.017453292F;
      float f22 = MathHelper.sin(f21);
      float f71 = MathHelper.cos(f21);
      float f23 = 1.5F;
      float f24 = (i + 1) * 2.0F;
      extendMultiPartEntityPart.onUpdate();
      extendMultiPartEntityPart.setLocationAndAngles(this.posX - ((f19 * f23 + f22 * f24) * f16), this.posY - (MathHelper.sin(this.animTime * 6.2831855F + i) * 0.2F * (i + 1)) - (f18 * (2.0F + (1 * i))) + f23, this.posZ + ((f3 * f23 + f71 * f24) * f16), 0.0F, 0.0F);
    } 
    for (int l = 0; l < this.dragonPartArray.length; l++) {
      (this.dragonPartArray[l]).prevPosX = (avec3d[l]).x;
      (this.dragonPartArray[l]).prevPosY = (avec3d[l]).y;
      (this.dragonPartArray[l]).prevPosZ = (avec3d[l]).z;
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      collideWithEntities(this.dragonPartHead, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.getEntityBoundingBox().grow(1.0D)));
      collideWithEntities(this.dragonPartNeck, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartNeck.getEntityBoundingBox().grow(1.0D)));
      collideWithEntities(this.dragonPartBody, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartBody.getEntityBoundingBox().grow(1.0D)));
      flingEntities(this.dragonPartWing1, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.getEntityBoundingBox().grow(4.0D).offset(0.0D, -2.0D, 0.0D)));
      flingEntities(this.dragonPartWing2, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.getEntityBoundingBox().grow(4.0D).offset(0.0D, -2.0D, 0.0D)));
      collideWithEntities(this.dragonPartTail1, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail1.getEntityBoundingBox().grow(1.0D)));
      collideWithEntities(this.dragonPartTail2, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail2.getEntityBoundingBox().grow(1.0D)));
      collideWithEntities(this.dragonPartTail3, this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail3.getEntityBoundingBox().grow(1.0D)));
      attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.getEntityBoundingBox().grow(3.0D)));
      attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartNeck.getEntityBoundingBox().grow(2.0D)));
    } 
    destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox());
    destroyBlocksInAABB(this.dragonPartNeck.getEntityBoundingBox());
    destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
    destroyBlocksInAABB(this.dragonPartWing1.getEntityBoundingBox());
    destroyBlocksInAABB(this.dragonPartWing2.getEntityBoundingBox());
    destroyBlocksInAABB(this.dragonPartTail1.getEntityBoundingBox());
    destroyBlocksInAABB(this.dragonPartTail2.getEntityBoundingBox());
    destroyBlocksInAABB(this.dragonPartTail3.getEntityBoundingBox());
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive() && getHealth() < getMaxHealth() && this.hurtTime <= 5)
      heal((getAttackTarget() != null || isPotionActive(MobEffects.HUNGER)) ? 0.02F : 0.1F); 
    if (!isWild() && getDistanceSq(getOwner()) >= 48400.0D) {
      this.behaviour = EnumBehaviour.GUARDING;
      setPositionAndUpdate((getOwner()).posX + 60.0D, (getOwner()).posY + 40.0D, (getOwner()).posZ + 60.0D);
      this.targetX = this.posX;
      this.targetY = this.posY;
      this.targetZ = this.posZ;
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen(this) && this.rand.nextInt(40) == 0) {
      List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getAttackTarget().getEntityBoundingBox().grow(3.0D));
      for (EntityLivingBase entityLivingBase : entities) {
        if (!false)
          fireLightning(entityLivingBase, this.dragonPartHead.posX, this.dragonPartHead.posY + 0.25D, this.dragonPartHead.posZ);
      } 
    } 
    if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)getControllingPassenger();
      passenger.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 4));
      this.renderYawOffset = this.rotationYaw = passenger.rotationYawHead + 180.0F;
      this.rotationPitch = 0.0F;
      for (int k = 0; k < 256; k++) {
        double d1 = 0.0075D;
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
    setSneaking(false);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen(this) && this.rand.nextInt(120) == 0) {
      EntityGuardianProjectile projectile;
      double distance;
      switch (this.rand.nextInt(4)) {
        case 0:
          projectile = new EntityGuardianProjectile(this.world, 1, getAttackTarget(), 5.0F + this.rand.nextFloat() * 8.0F, this);
          projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
          projectile.shooter = this;
          projectile.target = getAttackTarget();
          this.world.spawnEntity(projectile);
          distance = Utils.getDistanceAtoB((getAttackTarget()).posX, (getAttackTarget()).posZ, this.dragonPartHead.posX, this.dragonPartHead.posZ);
          this.rotationPitch = (float)Math.toDegrees(Math.atan2((getAttackTarget()).posY - this.dragonPartHead.posY, distance)) * -1.0F;
          break;
        case 1:
          projectile = new EntityGuardianProjectile(this.world, 3, getAttackTarget(), 5.0F + this.rand.nextFloat() * 2.0F, this);
          projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
          projectile.shooter = this;
          projectile.target = getAttackTarget();
          this.world.spawnEntity(projectile);
          break;
        case 2:
          projectile = new EntityGuardianProjectile(this.world, 4, getAttackTarget(), 5.0F + this.rand.nextFloat() * 10.0F, this);
          projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
          projectile.shooter = this;
          projectile.target = getAttackTarget();
          this.world.spawnEntity(projectile);
          break;
        case 3:
          projectile = new EntityGuardianProjectile(this.world, 5, getAttackTarget(), 5.0F + this.rand.nextFloat() * 10.0F, this);
          projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
          projectile.shooter = this;
          projectile.target = getAttackTarget();
          this.world.spawnEntity(projectile);
          break;
      } 
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.rand.nextInt(200) == 0 && getDistance(getAttackTarget()) >= 100.0D) {
      this.behaviour = EnumBehaviour.CHARGING;
      this.targetX = (getAttackTarget()).posX;
      this.targetY = Flying.clampFlightY((getAttackTarget()).posY);
      this.targetZ = (getAttackTarget()).posZ;
    } 
    if (this.attackInProgress != -1 && this.nextAttackTimer <= 0)
      this.attackInProgress = -1; 
  }
  
  private void customAIUpdate() {
    if (getHealth() > 0.0F && getHealth() < getMaxHealth() * 0.2F)
      this.behaviour = EnumBehaviour.LOW_HEALTH_STRATEGY; 
    switch (this.behaviour) {
      case ROAMING:
        if (!hasActiveCombatTarget()) {
          if (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) > 240.0D) {
            this.behaviour = EnumBehaviour.GO_HOME;
          } else if (this.forceNewTarget || Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, this.targetX, this.targetY, this.targetZ) < 25.0D) {
            setNewTarget();
          } 
          break;
        } 
        if (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) < 200.0D)
          selectNewBehaviour(); 
        break;
      case GO_HOME:
        if (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) < 70.0D) {
          if (hasActiveCombatTarget()) {
            selectNewBehaviour();
          } else {
            this.behaviour = EnumBehaviour.ROAMING;
            setNewTarget();
          } 
        } 
        break;
      case GUARDING:
        if (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) > 120.0D) {
          this.behaviour = EnumBehaviour.GO_HOME;
        } else if (this.forceNewTarget || Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, this.targetX, this.targetY, this.targetZ) < 25.0D) {
          setNewTarget();
        } 
        break;
      case CHARGING:
        if (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) > 300.0D)
          this.behaviour = EnumBehaviour.GO_HOME; 
        break;
      case CIRCLE_PLAYER:
        this.circlePosition += 0.02F * this.circleDirection;
        if (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) > 300.0D || this.posY > 250.0D)
          this.behaviour = EnumBehaviour.GO_HOME; 
        break;
      case LOW_HEALTH_STRATEGY:
        if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 60.0D && this.attackInProgress != 4) {
          int escape = 0;
          while (escape < 50) {
            this.targetX = this.homeX + (this.rand.nextDouble() - 0.5D) * 220.0D;
            this.targetY = Flying.clampFlightY((this.homeY + 30) + this.rand.nextDouble() * 20.0D);
            this.targetZ = this.homeZ + (this.rand.nextDouble() - 0.5D) * 220.0D;
            escape++;
          } 
          setAttackTarget(null);
        } 
        break;
      default:
        setAttackTarget(null);
        this.targetX = this.homeX;
        this.targetY = this.homeY;
        this.targetZ = this.homeZ;
        break;
    } 
    if (this.behaviour == EnumBehaviour.DEAD)
      return; 
    if (this.ticksExisted % 1000 == 0 && this.rand.nextBoolean())
      this.ignitionChargeTimer = 0; 
    if (this.ticksExisted % ((this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) ? 1000 : 2000) == 0)
      selectNewBehaviour(); 
    if (this.ignitionChargeTimer > 1 || (this.ignitionChargeTimer == 1 && this.ticksExisted % 20 == 0))
      this.ignitionChargeTimer--; 
    if (this.ignitionChargeTimer <= 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if ((this.ticksExisted - 19) % 20 == 0)
        this.ignitionChargeTimer = ((this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) ? 1000 : 2000) + this.rand.nextInt(600);  
    updateAttack();
  }
  
  private void updateAttack() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) || this.behaviour == EnumBehaviour.DEAD)
      return; 
    if (this.behaviour == EnumBehaviour.FIREBOMB && Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, this.homeX, (this.homeY + 30), this.homeZ) <= 3.0D) {
      if (getAttackTarget() == null || this.ticksExisted % 100 == 0)
        setNewTarget(); 
      if (getAttackTarget() != null) {
        double distance = Utils.getDistanceAtoB((getAttackTarget()).posX, (getAttackTarget()).posZ, this.dragonPartHead.posX, this.dragonPartHead.posZ);
        if (Utils.getDistanceAtoB((getAttackTarget()).posX, (getAttackTarget()).posZ, this.posX, this.posZ) < 5.0D)
          distance *= -1.0D; 
        float anglePitch = (float)Math.toDegrees(Math.atan2((getAttackTarget()).posY - this.dragonPartHead.posY, distance)) * -1.0F;
        float angleYaw = (float)Math.toDegrees(Math.atan2((getAttackTarget()).posX - this.dragonPartHead.posX, (getAttackTarget()).posZ - this.posZ)) * -1.0F;
        this.rotationPitch = anglePitch;
        if (Utils.getDistanceAtoB((getAttackTarget()).posX, (getAttackTarget()).posZ, this.posX, this.posZ) > 8.0D)
          this.rotationYaw = angleYaw + 180.0F; 
        if (this.ticksExisted % 2 == 0) {
          EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 1, getAttackTarget(), 5.0F + this.rand.nextFloat() * 8.0F, this);
          projectile.setPosition(this.dragonPartHead.posX + Math.cos(((this.rotationYaw - 90.0F) / 180.0F * 3.1415927F)) * 2.0D, this.dragonPartHead.posY + 1.5D, this.dragonPartHead.posZ + Math.sin(((this.rotationYaw - 90.0F) / 180.0F * 3.1415927F)) * 2.0D);
          this.world.spawnEntity(projectile);
        } 
      } 
    } else if (this.nextAttackTimer > 0 && getAttackTarget() != null) {
      this.nextAttackTimer--;
    } else if (this.nextAttackTimer == 0) {
      if (this.attackInProgress == -1) {
        selectNewAttack();
        switch (this.attackInProgress) {
          case 0:
            this.attackTimer = 100 + this.rand.nextInt(100);
            this.previousBehaviour = this.behaviour;
            this.behaviour = EnumBehaviour.CHARGING;
            break;
          case 1:
            this.attackTimer = 60 + this.rand.nextInt(60);
            break;
          case 2:
            this.attackTimer = 60 + this.rand.nextInt(60);
            break;
          case 3:
            this.attackTimer = 60 + this.rand.nextInt(60);
            break;
          case 4:
            this.attackTimer = 10 + this.rand.nextInt(10);
            break;
          case 5:
            this.attackTimer = 120 + this.rand.nextInt(60);
            break;
          case 6:
            this.attackTimer = 120 + this.rand.nextInt(60);
            break;
          case 7:
            this.attackTimer = 80 + this.rand.nextInt(40);
            break;
          case 8:
            this.attackTimer = 80;
            this.previousBehaviour = this.behaviour;
            this.behaviour = EnumBehaviour.CHARGING;
            break;
        } 
      } 
      switch (this.attackInProgress) {
        case 0:
          if (getAttackTarget() != null && Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, (getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ) > 10.0D) {
            if (this.attackTimer % 2 == 0) {
              EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 1, getAttackTarget(), 5.0F + this.rand.nextFloat() * 8.0F, this);
              projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
              projectile.shooter = this;
              projectile.target = getAttackTarget();
              this.world.spawnEntity(projectile);
            } 
            double distance = Utils.getDistanceAtoB((getAttackTarget()).posX, (getAttackTarget()).posZ, this.dragonPartHead.posX, this.dragonPartHead.posZ);
            this.rotationPitch = (float)Math.toDegrees(Math.atan2((getAttackTarget()).posY - this.dragonPartHead.posY, distance)) * -1.0F;
            break;
          } 
          this.attackTimer = 0;
          break;
        case 1:
          if (getAttackTarget() != null && this.attackTimer % 10 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 3, getAttackTarget(), 5.0F + this.rand.nextFloat() * 2.0F, this);
            projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
            projectile.shooter = this;
            projectile.target = getAttackTarget();
            this.world.spawnEntity(projectile);
          } 
          break;
        case 2:
          if (getAttackTarget() != null && this.attackTimer % 10 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 4, getAttackTarget(), 5.0F + this.rand.nextFloat() * 10.0F, this);
            projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
            projectile.shooter = this;
            projectile.target = getAttackTarget();
            this.world.spawnEntity(projectile);
          } 
          break;
        case 3:
          if (getAttackTarget() != null && this.attackTimer % 10 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 5, getAttackTarget(), 5.0F + this.rand.nextFloat() * 10.0F, this);
            projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
            projectile.shooter = this;
            projectile.target = getAttackTarget();
            this.world.spawnEntity(projectile);
          } 
          break;
        case 4:
          if (getAttackTarget() == null) {
            this.attackInProgress = -1;
            return;
          } 
          if (getAttackTarget() != null && Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, (getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ) > 15.0D) {
            if (this.attackTimer % 2 == 0) {
              EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 2, getAttackTarget(), 5.0F + this.rand.nextFloat() * 8.0F, this);
              projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
              projectile.shooter = this;
              projectile.target = getAttackTarget();
              this.world.spawnEntity(projectile);
            } 
            double distance = Utils.getDistanceAtoB((getAttackTarget()).posX, (getAttackTarget()).posZ, this.dragonPartHead.posX, this.dragonPartHead.posZ);
            this.rotationPitch = (float)Math.toDegrees(Math.atan2((getAttackTarget()).posY - this.dragonPartHead.posY, distance)) * -1.0F;
            break;
          } 
          this.attackTimer = 0;
          break;
        case 5:
          if (getAttackTarget() != null && this.attackTimer % 20 == 0) {
            List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getAttackTarget().getEntityBoundingBox().grow(3.0D));
            for (EntityLivingBase entityLivingBase : entities) {
              if (!false)
                fireLightning(entityLivingBase, this.dragonPartHead.posX, this.dragonPartHead.posY + 0.25D, this.dragonPartHead.posZ);
            } 
          } 
          break;
        case 6:
          if (getAttackTarget() != null && Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, (getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ) > 15.0D)
            if (this.attackTimer % 5 == 0) {
              double d6 = this.dragonPartHead.posX;
              double d7 = this.dragonPartHead.posY + 0.5D;
              double d8 = this.dragonPartHead.posZ;
              double d9 = (getAttackTarget()).posX - d6;
              double d10 = (getAttackTarget()).posY + 1.0D - d7;
              double d11 = (getAttackTarget()).posZ - d8;
              this.world.playEvent(null, 1016, new BlockPos(this), 0);
              EntityDragonFireballOther entitydragonfireball = new EntityDragonFireballOther(this.world, this, d9, d10, d11);
              entitydragonfireball.posX = d6;
              entitydragonfireball.posY = d7;
              entitydragonfireball.posZ = d8;
              this.world.spawnEntity(entitydragonfireball);
            }  
          break;
        case 7:
          if (getAttackTarget() != null && this.attackTimer % 2 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.world, 1 + this.rand.nextInt(5), getAttackTarget(), 5.0F + this.rand.nextFloat() * 10.0F, this);
            projectile.setPosition(this.dragonPartHead.posX, this.dragonPartHead.posY, this.dragonPartHead.posZ);
            projectile.shooter = this;
            projectile.target = getAttackTarget();
            this.world.spawnEntity(projectile);
          } 
          break;
        case 8:
          if (getAttackTarget() != null && Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, (getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ) > 10.0D) {
            this.animTime += 0.25F;
            if (this.attackTimer % 20 == 0) {
              playSound(SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundVolume(), getSoundPitch() - 0.25F);
              List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(16.0D));
              for (EntityLivingBase entityLivingBase : entities) {
                if (!false) {
                  double d0 = entityLivingBase.posX - this.posX;
                  double d1 = entityLivingBase.posZ - this.posZ;
                  double d2 = MathHelper.absMax(d0, d1);
                  d2 = MathHelper.sqrt(d2);
                  d0 /= d2;
                  d1 /= d2;
                  double d3 = 1.0D / d2;
                  if (d3 > 1.0D)
                    d3 = 1.0D; 
                  d0 *= d3;
                  d1 *= d3;
                  d0 *= 1.750000000745058D;
                  d1 *= 1.750000000745058D;
                  if (!entityLivingBase.isBeingRidden())
                    entityLivingBase.addVelocity(d0, 1.0D, d1); 
                } 
              } 
            } 
            double distance = Utils.getDistanceAtoB((getAttackTarget()).posX, (getAttackTarget()).posZ, this.dragonPartHead.posX, this.dragonPartHead.posZ);
            this.rotationPitch = (float)Math.toDegrees(Math.atan2((getAttackTarget()).posY - this.dragonPartHead.posY, distance)) * -1.0F;
          } 
          break;
      } 
      this.attackTimer--;
      if (this.attackTimer <= -1) {
        if (this.attackInProgress == 0)
          this.behaviour = this.previousBehaviour; 
        this.attackInProgress = -1;
        this.nextAttackTimer = -1;
      } 
    } else {
      this.nextAttackTimer = (this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) ? (10 + this.rand.nextInt(50)) : (200 + this.rand.nextInt(400));
    } 
  }
  
  private void selectNewAttack() {
    if (this.behaviour == EnumBehaviour.DEAD)
      return; 
    if (this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) {
      this.attackInProgress = WeightedRandom.getRandomItem(this.rand, weightedLowHealthAttaxks).attack;
    } else if (this.behaviour != EnumBehaviour.FIREBOMB) {
      this.attackInProgress = WeightedRandom.getRandomItem(this.rand, weightedAttacks).attack;
    } else {
      this.attackInProgress = 2;
    } 
  }
  
  private void selectNewBehaviour() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) || this.behaviour == EnumBehaviour.DEAD)
      return; 
    if (!hasActiveCombatTarget()) {
      this.behaviour = (Utils.getDistanceAtoB(this.posX, this.posZ, this.homeX, this.homeZ) > 240.0D) ? EnumBehaviour.GO_HOME : EnumBehaviour.ROAMING;
      this.previousBehaviour = this.behaviour;
      setNewTarget();
      return;
    }
    EnumBehaviour newBehaviour = this.behaviour;
    for (; newBehaviour == this.behaviour; newBehaviour = WeightedRandom.getRandomItem(this.rand, weightedBehaviours).randomBehaviour);
    this.behaviour = newBehaviour;
    this.previousBehaviour = this.behaviour;
  }
  
  private void updateTarget() {
    switch (this.behaviour) {
      case FIREBOMB:
        if (Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, this.homeX, (this.homeY + 30), this.homeZ) > 3.0D) {
          this.targetX = this.homeX;
          this.targetY = Flying.clampFlightY(this.homeY + 30);
          this.targetZ = this.homeZ;
        } 
        break;
      case DEAD:
        this.targetX = this.homeX;
        this.targetY = this.homeY;
        this.targetZ = this.homeZ;
        setAttackTarget(null);
        break;
    } 
  }
  
  private void setNewTarget() {
    if (this.behaviour == EnumBehaviour.DEAD)
      return; 
    this.forceNewTarget = false;
    switch (this.behaviour) {
      case ROAMING:
        this.targetX = (this.homeX + this.rand.nextFloat() * 120.0F - 60.0F);
        this.targetY = Flying.clampFlightY((this.homeY + 60) + this.rand.nextDouble() * 40.0D);
        this.targetZ = (this.homeZ + this.rand.nextFloat() * 120.0F - 60.0F);
        break;
      case GO_HOME:
        this.targetX = this.homeX;
        this.targetY = Flying.clampFlightY((this.homeY + 30) + this.rand.nextDouble() * 30.0D);
        this.targetZ = this.homeZ;
        break;
      case GUARDING:
        this.targetX = (this.homeX + this.rand.nextFloat() * 80.0F - 40.0F);
        this.targetY = Flying.clampFlightY((this.homeY + 25) + this.rand.nextDouble() * 25.0D);
        this.targetZ = (this.homeZ + this.rand.nextFloat() * 80.0F - 40.0F);
        setAttackTarget(null);
        break;
      case CHARGING:
        if (getAttackTarget() != null) {
          this.targetX = (getAttackTarget()).posX + (this.rand.nextFloat() * 60.0F) - 30.0D;
          this.targetY = Flying.clampFlightY((getAttackTarget()).posY + 10.0D + this.rand.nextDouble() * 10.0D);
          this.targetZ = (getAttackTarget()).posZ + (this.rand.nextFloat() * 60.0F) - 30.0D;
        } 
        break;
      case CIRCLE_PLAYER:
        if (getAttackTarget() != null) {
          this.targetX = (getAttackTarget()).posX + (this.rand.nextFloat() * 120.0F) - 60.0D;
          this.targetY = Flying.clampFlightY((getAttackTarget()).posY + 30.0D + this.rand.nextDouble() * 30.0D);
          this.targetZ = (getAttackTarget()).posZ + (this.rand.nextFloat() * 120.0F) - 60.0D;
        } 
        break;
      case LOW_HEALTH_STRATEGY:
        this.targetX = (this.homeX + this.rand.nextFloat() * 60.0F - 30.0F);
        this.targetY = Flying.clampFlightY((this.homeY + 30) + this.rand.nextDouble() * 20.0D);
        this.targetZ = (this.homeZ + this.rand.nextFloat() * 60.0F - 30.0F);
        break;
      case FIREBOMB:
        if (getAttackTarget() != null) {
          this.targetX = (getAttackTarget()).posX + (this.rand.nextFloat() * 120.0F) - 60.0D;
          this.targetY = Flying.clampFlightY((getAttackTarget()).posY + 20.0D + this.rand.nextDouble() * 20.0D);
          this.targetZ = (getAttackTarget()).posZ + (this.rand.nextFloat() * 120.0F) - 60.0D;
        } 
        break;
      case DEAD:
        this.targetX = this.homeX;
        this.targetY = this.homeY;
        this.targetZ = this.homeZ;
        break;
    } 
  }

  private boolean hasActiveCombatTarget() {
    EntityLivingBase target = getAttackTarget();
    return target != null && target.isEntityAlive();
  }

  private void clearPerchState() {
    this.sitting = false;
    if (getPhaseManager().getCurrentPhase() != PhaseList.HOLDING_PATTERN && getPhaseManager().getCurrentPhase() != PhaseList.DYING)
      getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
  }

  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() == Items.ENDER_EYE && (hasOwner(player) || false)) {
      clearPerchState();
      setNewTarget();
      return true;
    }
    return super.interact(player, hand);
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource damageSource, float dmg) {
    if (this.behaviour == EnumBehaviour.DEAD)
      return false; 
    boolean wildGuardian = isWild();
    if (damageSource.getDamageType() == "chaosImplosion" || damageSource.getDamageType() == "de.GuardianFireball" || damageSource.getDamageType() == "de.GuardianEnergyBall" || damageSource.getDamageType() == "de.GuardianChaosBall")
      dmg *= 0.15F; 
    switch (this.behaviour) {
      case GUARDING:
        if (this.rand.nextInt(5) == 0)
          selectNewBehaviour(); 
        break;
      case CHARGING:
        if (this.rand.nextInt(6) == 0)
          selectNewBehaviour(); 
        break;
      case CIRCLE_PLAYER:
        if (this.rand.nextInt(6) == 0) {
          selectNewBehaviour();
          break;
        } 
        if (this.rand.nextInt(4) == 0)
          this.circleDirection *= -1.0F; 
        break;
      case LOW_HEALTH_STRATEGY:
        if (this.rand.nextInt(6) == 0 && getHealth() >= getMaxHealth() * 0.2F)
          selectNewBehaviour(); 
        if (this.attackInProgress != 4) {
          int escape = 0;
          while (escape < 50) {
            this.targetX = this.homeX + (this.rand.nextDouble() - 0.5D) * 260.0D;
            this.targetY = Flying.clampFlightY((this.homeY + 20) + (this.rand.nextDouble() - 0.5D) * 50.0D);
            this.targetZ = this.homeZ + (this.rand.nextDouble() - 0.5D) * 260.0D;
            escape++;
          } 
          setAttackTarget(null);
        } 
        break;
      case FIREBOMB:
        if ((getAttackTarget() == null && Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, this.homeX, (this.homeY + 30), this.homeZ) <= 3.0D) || this.rand.nextInt(5) == 0)
          selectNewBehaviour(); 
        if (damageSource.getTrueSource() instanceof EntityLivingBase && damageSource.getTrueSource() != getAttackTarget() && this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY, this.posZ), new Vec3d((damageSource.getTrueSource()).posX, (damageSource.getTrueSource()).posY, (damageSource.getTrueSource()).posZ)) == null)
          setAttackTarget((EntityLivingBase)damageSource.getTrueSource()); 
        break;
    } 
    boolean attacked = super.attackEntityFromPart(part, damageSource, dmg);
    if (wildGuardian) {
      setAttackTarget(null);
      getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
      this.behaviour = EnumBehaviour.GO_HOME;
      this.targetX = this.homeX;
      this.targetY = Flying.clampFlightY(this.homeY + 30);
      this.targetZ = this.homeZ;
      this.forceNewTarget = false;
      this.attackInProgress = -1;
      this.nextAttackTimer = 100;
    }
    return attacked;
  }
  
  public void setToGuard() {
    this.behaviour = EnumBehaviour.GUARDING;
    clearPerchState();
    setNewTarget();
  }
  
  private enum EnumBehaviour {
    ROAMING(1.0F),
    GO_HOME(1.3F),
    GUARDING(0.8F),
    CHARGING(2.0F),
    FIREBOMB(1.5F),
    CIRCLE_PLAYER(1.2F),
    LOW_HEALTH_STRATEGY(2.0F),
    DEAD(0.5F);
    
    public float dragonSpeed;
    
    EnumBehaviour(float dragonSpeed) {
      this.dragonSpeed = dragonSpeed;
    }
  }
  
  private static class WeightedAttack extends WeightedRandom.Item {
    public int attack;
    
    public WeightedAttack(int weight, int attack) {
      super(weight);
      this.attack = attack;
    }
  }
  
  private static class WeightedBehaviour extends WeightedRandom.Item {
    public EntityChaosGuardian.EnumBehaviour randomBehaviour;
    
    public WeightedBehaviour(int weight, EntityChaosGuardian.EnumBehaviour randomBehaviour) {
      super(weight);
      this.randomBehaviour = randomBehaviour;
    }
  }
  
  protected void onDeathUpdate() {
    if (usesVanillaDeathUpdate()) {
      super.onDeathUpdate();
      return;
    }
    if (!isWild()) {
      this.homeX = (int)(getOwner()).posX;
      this.homeY = (int)(getOwner()).posY + 5;
      this.homeZ = (int)(getOwner()).posZ;
    } 
    double d0 = this.homeX - this.posX;
    double d1 = this.homeY - this.posY;
    double d2 = this.homeZ - this.posZ;
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
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.deathTicks == 1) {
      if (Loader.isModLoaded("draconicevolution")) {
        Entity var8 = EntityList.createEntityByIDFromName(new ResourceLocation("draconicevolution:dragonheartitem"), this.world);
        this.world.spawnEntity(var8);
      } 
      if (Loader.isModLoaded("draconicevolution")) {
        Entity var8 = EntityList.createEntityByIDFromName(new ResourceLocation("draconicevolution:dragonheartitem"), this.world);
        this.world.spawnEntity(var8);
      } 
      this.world.playBroadcastSound(1028, new BlockPos(this), 0);
      if (getOwner() != null) {
        for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world)) {
          this.world.playSound(null, entityplayer.getPosition(), SoundEvents.ENTITY_ENDERDRAGON_DEATH, getSoundCategory(), getSoundVolume(), 1.0F);
          entityplayer.sendStatusMessage(new TextComponentTranslation("§4" + getOwner().getName() + "'s " + getName() + " has been killed!!!", new Object[0]), true);
        } 
        getOwner().sendMessage(new TextComponentTranslation("Your Chaos Guardian has fallen in battle.", new Object[0]));
      } 
    } 
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      EntityLightningBolt bolt = new EntityLightningBolt(this.world, this.homeX, (this.homeY + 1), this.homeZ, true);
      bolt.ignoreFrustumCheck = true;
      this.world.addWeatherEffect(bolt);
    } 
    if (this.deathTicks == 200 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      int i = 200000;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
      } 
      playSound(ESound.blast, 100.0F, 0.75F);
      setDead();
      List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(200.0D));
      for (EntityLivingBase entity : entities) {
        if (!entity.isImmuneToExplosions() && !false)
          entity.attackEntityFrom((new EntityDamageSource("explosion.player", this)).setExplosion().setDamageBypassesArmor().setDamageIsAbsolute(), 10000.0F);
      } 
    } 
  }
  
  private float simplifyAngle(double par1) {
    return (float)MathHelper.wrapDegrees(par1);
  }
  
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    compound.setInteger("HomeXCoord", this.homeX);
    compound.setInteger("HomeYCoord", this.homeY);
    compound.setInteger("HomeZCoord", this.homeZ);
    compound.setString("Behaviour", this.behaviour.name());
    compound.setBoolean("HomeSet", this.homeSet);
    return compound;
  }
  
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    this.homeX = compound.getInteger("HomeXCoord");
    this.homeY = compound.getInteger("HomeYCoord");
    this.homeZ = compound.getInteger("HomeZCoord");
    if (compound.hasKey("Behaviour"))
      this.behaviour = EnumBehaviour.valueOf(compound.getString("Behaviour")); 
    this.homeSet = compound.getBoolean("HomeSet");
    this.targetX = this.homeX;
    this.targetZ = this.homeZ;
  }
}

