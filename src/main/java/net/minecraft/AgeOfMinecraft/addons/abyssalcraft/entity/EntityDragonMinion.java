package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDragonMinion extends EntityTameBase implements IEntityMultiPart, Light, Flying, Undead {
  public static final float innerRotation = 0.0F;
  
  public double targetX;
  
  public double targetY;
  
  public double targetZ;
  
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
  
  public boolean forceNewTarget;
  
  public Entity target;
  
  public EntityDragonBoss healingcircle;
  
  public EntityDragonMinion(World par1World) {
    super(par1World);
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 3.0F, 3.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 3.0F, 3.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 4.0F, 4.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 2.0F, 2.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 2.0F, 2.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 2.0F, 2.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 2.0F, 2.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 2.0F, 2.0F) };
    setHealth(getMaxHealth());
    setSize(8.0F, 3.0F);
    this.noClip = true;
    this.targetY = 100.0D;
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityDragonMinion(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    } 
  }
  
  public int getNextLevelRequirement() {
    return 300;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 2.0F;
  }
  
  protected void entityInit() {
    super.entityInit();
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
  
  protected Item getDropItem() {
    return ACItems.coralium_plagued_flesh;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_SPECTRAL_DRAGON;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.2F : 2.0F;
  }
  
  public void onLivingUpdate() {
    if (this.ticksExisted <= 20) {
      if (this.ticksExisted == 3)
        spawnExplosionParticle(); 
      this.motionX *= 0.0D;
      this.motionZ *= 0.0D;
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
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
      if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false)
        if (getDistanceSq(getAttackTarget()) < (this.reachWidth * this.reachWidth + ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width) * ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width)) + 9.0D && (this.ticksExisted + getEntityId()) % 10 == 0)
          attackEntityAsMob(getAttackTarget());
    } 
    setSilent(isAIDisabled());
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      float f = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
      float f1 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
      if (f1 <= -0.3F && f >= -0.3F)
        this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), getSoundVolume(), getSoundPitch() + 0.1F, false); 
    } 
    this.prevAnimTime = this.animTime;
    if (getHealth() <= 0.0F) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity)
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
    } else if (!isAIDisabled()) {
      if (!isWild() && getDistanceSq(getOwner()) > 4069.0D) {
        this.target = getOwner();
        setAttackTarget(null);
      } 
      if (getAttackTarget() != null && this.target == null && this.ticksExisted + getEntityId() % 40 == 0)
        this.target = getAttackTarget();
      updateHealingCircle();
      float f = 0.2F / (MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
      f *= (float)Math.pow(2.0D, this.motionY);
      if (this.ticksExisted > 20)
        this.animTime += f; 
      if (isChild()) {
        this.animTime += f;
        this.animTime += f;
      } 
      if (isBeingRidden() && ((EntityLivingBase)getControllingPassenger()).moveForward == 0.0F)
        this.animTime += 0.1F; 
      this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
      if (this.ringBufferIndex < 0)
        for (int i = 0; i < this.ringBuffer.length; i++) {
          this.ringBuffer[i][0] = this.rotationYaw;
          this.ringBuffer[i][1] = this.posY;
        }  
      if (++this.ringBufferIndex == this.ringBuffer.length)
        this.ringBufferIndex = 0; 
      this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
      this.ringBuffer[this.ringBufferIndex][1] = this.posY;
      this.renderYawOffset = this.rotationYaw;
      this.dragonPartHead.width = this.dragonPartHead.height = 1.5F;
      this.dragonPartNeck.width = this.dragonPartNeck.height = 1.5F;
      this.dragonPartTail1.width = this.dragonPartTail1.height = 1.0F;
      this.dragonPartTail2.width = this.dragonPartTail2.height = 1.0F;
      this.dragonPartTail3.width = this.dragonPartTail3.height = 1.0F;
      this.dragonPartBody.height = 1.5F;
      this.dragonPartBody.width = 2.5F;
      this.dragonPartWing1.height = 1.5F;
      this.dragonPartWing1.width = 2.0F;
      this.dragonPartWing2.height = 1.5F;
      this.dragonPartWing2.width = 1.5F;
      float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
      float f16 = MathHelper.cos(f14);
      float f18 = MathHelper.sin(f14);
      float f21 = this.rotationYaw * 0.017453292F;
      float f19 = MathHelper.sin(f21);
      float f31 = MathHelper.cos(f21);
      double[] adouble = getMovementOffsets(5, 1.0F);
      double[] adouble1 = getMovementOffsets(14, 1.0F);
      double[] adouble2 = getMovementOffsets(16, 1.0F);
      this.dragonPartBody.onUpdate();
      this.dragonPartBody.setLocationAndAngles(this.posX + (f19 * 0.25F), this.posY, this.posZ - (f31 * 0.25F), 0.0F, 0.0F);
      this.dragonPartWing1.onUpdate();
      this.dragonPartWing1.setLocationAndAngles(this.posX + (f31 * 2.25F), this.posY + (MathHelper.sin(this.animTime * 3.0F) * 1.5F), this.posZ + (f19 * 2.25F), 0.0F, 0.0F);
      this.dragonPartWing2.onUpdate();
      this.dragonPartWing2.setLocationAndAngles(this.posX - (f31 * 2.25F), this.posY + (MathHelper.sin(this.animTime * 3.0F) * 1.5F), this.posZ - (f19 * 2.25F), 0.0F, 0.0F);
      this.dragonPartNeck.onUpdate();
      this.dragonPartNeck.setLocationAndAngles(this.posX + (f19 * 2.0F), this.posY, this.posZ - (f31 * 2.0F), 0.0F, 0.0F);
      this.dragonPartHead.onUpdate();
      this.dragonPartHead.setLocationAndAngles(this.posX + (f19 * 3.5F), this.posY, this.posZ - (f31 * 3.5F), 0.0F, 0.0F);
      for (int j = 0; j < 3; j++) {
        ExtendMultiPartEntityPart MultiPartEntityPart = null;
        if (j == 0)
          MultiPartEntityPart = this.dragonPartTail1; 
        if (j == 1)
          MultiPartEntityPart = this.dragonPartTail2; 
        if (j == 2)
          MultiPartEntityPart = this.dragonPartTail3; 
        adouble1 = getMovementOffsets(12 + j * 2, 1.0F);
        float f211 = this.rotationYaw * 3.1415927F / 180.0F + simplifyAngle(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
        float f22 = MathHelper.sin(f211);
        float f7 = MathHelper.cos(f211);
        float f23 = 0.75F;
        float f24 = (j + 1) * 1.0F;
        MultiPartEntityPart.onUpdate();
        MultiPartEntityPart.setLocationAndAngles(this.posX - ((f19 * f23 + f22 * f24) * f16), this.posY + adouble1[1] - adouble[1] - ((f24 + f23) * f18) + 0.75D, this.posZ + ((f31 * f23 + f7 * f24) * f16), 0.0F, 0.0F);
      } 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartBody.getEntityBoundingBox().grow(1.0D)));
        collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.getEntityBoundingBox().grow(1.0D).offset(0.0D, -1.0D, 0.0D)));
        collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.getEntityBoundingBox().grow(1.0D).offset(0.0D, -1.0D, 0.0D)));
        collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail1.getEntityBoundingBox().grow(1.0D)));
        collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail2.getEntityBoundingBox().grow(1.0D)));
        collideWithEntities(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartTail3.getEntityBoundingBox().grow(1.0D)));
        attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.getEntityBoundingBox().grow(3.0D).offset(0.0D, -1.0D, 0.0D)));
        attackEntitiesInList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartNeck.getEntityBoundingBox().grow(3.0D).offset(0.0D, -1.0D, 0.0D)));
      } 
    } 
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (this.newPosRotationIncrements > 0) {
        double d3 = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
        double d0 = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
        double d1 = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
        double d2 = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
        this.rotationYaw = (float)(this.rotationYaw + d2 / this.newPosRotationIncrements);
        this.rotationPitch = (float)(this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
        this.newPosRotationIncrements--;
        setPosition(d3, d0, d1);
        setRotation(this.rotationYaw, this.rotationPitch);
      } 
    } else {
      double d3 = this.targetX - this.posX;
      double d0 = this.targetY - this.posY;
      double d1 = this.targetZ - this.posZ;
      double d2 = d3 * d3 + d0 * d0 + d1 * d1;
      if (this.target != null) {
        this.targetX = this.target.posX;
        this.targetZ = this.target.posZ;
        double d4 = this.targetX - this.posX;
        double d5 = this.targetZ - this.posZ;
        double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        double d7 = 0.4000000059604645D + d6 / 80.0D - 1.0D;
        if (d7 > 10.0D)
          d7 = 10.0D; 
        this.targetY = Flying.clampFlightY((this.target.getEntityBoundingBox()).minY + d7);
      } else {
        this.targetX += this.rand.nextGaussian() * 2.0D;
        this.targetZ += this.rand.nextGaussian() * 2.0D;
      } 
      if (this.forceNewTarget || d2 < 100.0D || d2 > 22500.0D || this.collidedHorizontally || this.collidedVertically)
        setNewTarget(); 
      d0 /= MathHelper.sqrt(d3 * d3 + d1 * d1);
      float f3 = 0.6F;
      if (d0 < -f3)
        d0 = -f3; 
      if (d0 > f3)
        d0 = f3; 
      this.motionY += d0 * 0.10000000149011612D;
      this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
      double d8 = 180.0D - Math.atan2(d3, d1) * 180.0D / Math.PI;
      double d9 = MathHelper.wrapDegrees(d8 - this.rotationYaw);
      if (d9 > 50.0D)
        d9 = 50.0D; 
      if (d9 < -50.0D)
        d9 = -50.0D; 
      Vec3d vec3 = (new Vec3d(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ)).normalize();
      Vec3d vec31 = (new Vec3d(MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), this.motionY, -MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F))).normalize();
      float f4 = (float)(vec31.dotProduct(vec3) + 0.5D) / 1.5F;
      if (f4 < 0.0F)
        f4 = 0.0F; 
      this.randomYawVelocity *= 0.8F;
      float f5 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
      double d10 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;
      if (d10 > 40.0D)
        d10 = 40.0D; 
      this.randomYawVelocity = (float)(this.randomYawVelocity + d9 * 0.699999988079071D / d10 / f5);
      this.rotationYaw += this.randomYawVelocity * 0.1F;
      float f6 = (float)(2.0D / (d10 + 1.0D));
      float f7 = 0.06F;
      moveRelative(0.0F, 0.0F, -1.0F, f7 * (f4 * f6 + 1.0F - f6));
      move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      Vec3d vec32 = (new Vec3d(this.motionX, this.motionY, this.motionZ)).normalize();
      float f8 = (float)(vec32.dotProduct(vec31) + 1.0D) / 2.5F;
      f8 = 0.8F + 0.15F * f8;
      this.motionX *= f8;
      this.motionZ *= f8;
      this.motionY *= 0.9100000262260437D;
    } 
    if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)getControllingPassenger();
      passenger.fallDistance *= 0.0F;
      passenger.hurtResistantTime = 40;
      passenger.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 4));
      this.renderYawOffset = this.rotationYaw = passenger.rotationYawHead + 180.0F;
      this.rotationPitch = 0.0F;
      for (int i = 0; i < 256; i++) {
        double g = 0.005D;
        if (this.moralRaisedTimer > 200)
          g *= 2.0D; 
        if (isSprinting())
          g *= 2.0D; 
        Vec3d vec3 = passenger.getLook(1.0F);
        if (passenger.moveForward > 0.0F) {
          setPosition(this.posX + vec3.x * g, this.posY + vec3.y * g, this.posZ + vec3.z * g);
          Entity[] aentity = getParts();
          if (aentity != null)
            for (Entity entity : aentity)
              entity.setLocationAndAngles(entity.posX + vec3.x * g, entity.posY + vec3.y * g, entity.posZ + vec3.z * g, 0.0F, 0.0F);  
        } 
        if (passenger.moveForward < 0.0F)
          setPosition(this.posX - vec3.x * g, this.posY - vec3.y * g, this.posZ - vec3.z * g); 
      } 
    } 
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
  
  public void move(MoverType type, double x, double y, double z) {
    setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
    resetPositionToBB();
    try {
      doBlockCollisions();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
      CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
      addEntityCrashInfo(crashreportcategory);
      throw new ReportedException(crashreport);
    } 
  }
  
  protected void doBlockCollisions() {
    AxisAlignedBB axisalignedbb = getEntityBoundingBox();
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001D, axisalignedbb.minY + 0.001D, axisalignedbb.minZ + 0.001D);
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001D, axisalignedbb.maxY - 0.001D, axisalignedbb.maxZ - 0.001D);
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain();
    if (this.world.isAreaLoaded(blockpos$pooledmutableblockpos, blockpos$pooledmutableblockpos1))
      for (int i = blockpos$pooledmutableblockpos.getX(); i <= blockpos$pooledmutableblockpos1.getX(); i++) {
        for (int j = blockpos$pooledmutableblockpos.getY(); j <= blockpos$pooledmutableblockpos1.getY(); j++) {
          for (int k = blockpos$pooledmutableblockpos.getZ(); k <= blockpos$pooledmutableblockpos1.getZ(); k++) {
            blockpos$pooledmutableblockpos2.setPos(i, j, k);
            IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos2);
            if (iblockstate.getMaterial() == Material.PORTAL)
              addVelocity((this.motionX > 0.0D) ? -3.0D : 3.0D, (this.motionY > 0.0D) ? -3.0D : 3.0D, (this.motionZ > 0.0D) ? -3.0D : 3.0D); 
          } 
        } 
      }  
    blockpos$pooledmutableblockpos.release();
    blockpos$pooledmutableblockpos1.release();
    blockpos$pooledmutableblockpos2.release();
  }
  
  private void updateHealingCircle() {
    if (this.healingcircle != null)
      if (this.healingcircle.isDead) {
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          attackEntityFromPart(this.dragonPartHead, DamageSource.causeExplosionDamage((Explosion)null), 1000.0F);
        this.healingcircle = null;
      } else if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.ticksExisted % 10 == 0 && getHealth() <= getMaxHealth() && this.healingcircle.getHealth() < this.healingcircle.getMaxHealth()) {
        attackEntityFromPart(this.dragonPartHead, (new DamageSource("starve")).setDamageBypassesArmor().setDamageIsAbsolute(), 2.0F);
        this.healingcircle.heal(4.0F);
      }  
    if (this.rand.nextInt(10) == 0) {
      float f = 64.0F;
      List<?> list = this.world.getEntitiesWithinAABB(EntityDragonBoss.class, getEntityBoundingBox().grow(f, f, f));
      EntityDragonBoss entitydragonboss = null;
      double d0 = Double.MAX_VALUE;
        for (Object o : list) {
            EntityDragonBoss entitydragonboss1 = (EntityDragonBoss) o;
            double d1 = entitydragonboss1.getDistanceSq(this);
            if (d1 < d0) {
                d0 = d1;
                entitydragonboss = entitydragonboss1;
            }
        }
      this.healingcircle = entitydragonboss;
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
              entity.addVelocity(d2 / d4 * 1.25D, 0.1D, d3 / d4 * 1.25D);
              entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F);
              if (entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase) entity))
                  ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));
          }
      }
  }
  
  private void attackEntitiesInList(List<?> par1List) {
      for (Object o : par1List) {
          Entity entity = (Entity) o;
          if (entity.ticksExisted + entity.getEntityId() % 10 == 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && entity instanceof EntityLivingBase && !false) {
              attackEntityAsMob(entity);
              playSound(SoundEvents.BLOCK_NOTE_HAT, 5.0F, 0.75F);
              if (entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase) entity))
                  ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 400));
              if (ACConfig.hardcoreMode && entity instanceof EntityPlayer)
                  entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.0F);
          }
      }
  }
  
  private void setNewTarget() {
    this.forceNewTarget = false;
    if (this.rand.nextBoolean() && getAttackTarget() != null) {
      this.target = getAttackTarget();
    } else {
      boolean flag = false;
      do {
        if (getAttackTarget() != null) {
          this.targetX = (getAttackTarget()).posX;
          this.targetY = Flying.clampFlightY((getAttackTarget()).posY + 20.0D + (this.rand.nextFloat() * 20.0F));
          this.targetZ = (getAttackTarget()).posZ;
          this.targetX += (this.rand.nextFloat() * 40.0F - 20.0F);
          this.targetZ += (this.rand.nextFloat() * 40.0F - 20.0F);
          double d0 = this.posX - this.targetX;
          double d1 = this.posY - this.targetY;
          double d2 = this.posZ - this.targetZ;
          flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
        } else if (!isWild()) {
          this.targetX = (getOwner()).posX;
          this.targetY = Flying.clampFlightY((getOwner()).posY + 20.0D + (this.rand.nextFloat() * 20.0F));
          this.targetZ = (getOwner()).posZ;
          this.targetX += (this.rand.nextFloat() * 60.0F - 30.0F);
          this.targetZ += (this.rand.nextFloat() * 60.0F - 30.0F);
          double d0 = this.posX - this.targetX;
          double d1 = this.posY - this.targetY;
          double d2 = this.posZ - this.targetZ;
          flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
        } else {
          this.targetX = 0.0D;
          this.targetY = Flying.clampFlightY(70.0F + this.rand.nextFloat() * 50.0F);
          this.targetZ = 0.0D;
          this.targetX += (this.rand.nextFloat() * 120.0F - 60.0F);
          this.targetZ += (this.rand.nextFloat() * 120.0F - 60.0F);
          double d0 = this.posX - this.targetX;
          double d1 = this.posY - this.targetY;
          double d2 = this.posZ - this.targetZ;
          flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
        } 
      } while (!flag);
      this.target = null;
    } 
  }
  
  private float simplifyAngle(double par1) {
    return (float)MathHelper.wrapDegrees(par1);
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart par1MultiPartEntityPart, DamageSource source, float damage) {
    if (par1MultiPartEntityPart != this.dragonPartHead)
      damage = damage / 2.0F + 1.0F; 
    if (damage < 0.01F || source.isProjectile() || source == DamageSource.WITHER || source == DamageSource.IN_WALL || source == DamageSource.DROWN || source == DamageSource.CRAMMING || source == DamageSource.CACTUS)
      return false; 
    float f = getHealth();
    if (source.isProjectile()) {
      playSound(getPierceHurtSound(), 3.0F, 1.0F);
    } else if (damage >= 7.0F || source.isExplosion() || source == DamageSource.ANVIL || source.canHarmInCreative() || source.isDamageAbsolute() || source.isMagicDamage() || source == DamageSource.FALL || source == DamageSource.LAVA) {
      playSound(getCrushHurtSound(), 3.0F, 1.0F);
    } else {
      playSound(getRegularHurtSound(), 3.0F, 1.0F);
    } 
    attackEntityFrom(source, damage);
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    return super.attackEntityFrom(par1DamageSource, par2);
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
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
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
    return SoundEvents.ENTITY_VEX_DEATH;
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
}

