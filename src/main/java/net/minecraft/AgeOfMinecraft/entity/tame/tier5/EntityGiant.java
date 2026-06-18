package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGiant extends EntityTameBase implements Massive, Armored {
  public EntityGiant(World worldIn) {
    super(worldIn);
    setSize(3.0F, 12.0F);
    this.stepHeight = 3.0F;
    this.isOffensive = true;
    this.isImmuneToFire = true;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 48.0F, 8.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.experienceValue = 250;
    this.ignoreFrustumCheck = true;
    this.experienceValue = 50;
  }
  
  public String getDescName() {
    return "giant";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  public int getNextLevelRequirement() {
    return 1000;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGiant(this.world);
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
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
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  protected boolean canFitPassenger(Entity passenger) {
    return (getPassengers().size() < 3);
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
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      int i = getPassengers().indexOf(passenger);
      float f3 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f11 = MathHelper.sin(f3);
      float f4 = MathHelper.cos(f3);
      if (EngenderConfig.mobs.useMobTalkerModels) {
        if (i == 2)
          passenger.setPosition(this.posX - (f4 * 2.1F * getFittness()), this.posY + (isHero() ? (8.0D * getFittness()) : (7.5D * getFittness())), this.posZ - (f11 * 2.1F * getFittness())); 
        if (i == 1)
          passenger.setPosition(this.posX + (f4 * 2.1F * getFittness()), this.posY + (isHero() ? (8.0D * getFittness()) : (7.5D * getFittness())), this.posZ + (f11 * 2.1F * getFittness())); 
        if (i == 0)
          passenger.setPosition(this.posX, this.posY + (isHero() ? (12.0D * getFittness()) : (11.35D * getFittness())), this.posZ); 
      } else {
        if (i == 2)
          passenger.setPosition(this.posX - (f4 * 2.25F), this.posY + (isHero() ? 9.0D : 8.5D), this.posZ - (f11 * 2.25F)); 
        if (i == 1)
          passenger.setPosition(this.posX + (f4 * 2.25F), this.posY + (isHero() ? 9.0D : 8.5D), this.posZ + (f11 * 2.25F)); 
        if (i == 0)
          passenger.setPosition(this.posX, this.posY + (isHero() ? (12.0D * getFittness()) : (11.35D * getFittness())), this.posZ); 
      } 
    } 
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    double amount = 0.6D;
    if (this.world.getDifficulty() == EnumDifficulty.EASY)
      amount *= 0.75D; 
    if (this.world.getDifficulty() == EnumDifficulty.HARD)
      amount *= 1.5D; 
    ((EntityLivingBase)entity).knockBack((Entity)this, 1.0F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
    if (!entity.isEntityAlive() && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).prevRenderYawOffset = ((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).prevRotationYaw = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).prevRotationYawHead = ((EntityLivingBase)entity).rotationYawHead = this.rotationYawHead;
      float xRatio = MathHelper.sin(this.rotationYawHead * 0.017453292F);
      float zRatio = -MathHelper.cos(this.rotationYawHead * 0.017453292F);
      entity.isAirBorne = true;
      float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
      entity.motionX /= 2.0D;
      entity.motionZ /= 2.0D;
      entity.motionX -= xRatio / f * 3.0D;
      entity.motionZ -= zRatio / f * 3.0D;
      entity.motionY /= 2.0D;
      entity.motionY += amount;
      if (entity instanceof EntityPlayerMP)
        ((EntityPlayerMP)entity).connection.sendPacket((Packet)new SPacketEntityVelocity(entity)); 
    } 
    entity.motionY += amount;
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.84F) : (this.height * 0.87F);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(50.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(24.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = 10.0F;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(2.0D, 0.0D, 2.0D));
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        if (entity instanceof EntityLivingBase && !false && !this.world.isRemote && this.ticksExisted % 10 == 0) {
          attackEntityAsMob(entity);
          double d01 = ((getEntityBoundingBox()).minX + (getEntityBoundingBox()).maxX) / 2.0D;
          double d11 = ((getEntityBoundingBox()).minZ + (getEntityBoundingBox()).maxZ) / 2.0D;
          double d2 = entity.posX - d01;
          double d3 = entity.posZ - d11;
          double d4 = d2 * d2 + d3 * d3;
          entity.addVelocity(d2 / d4 * 6.0D, 0.25D, d3 / d4 * 6.0D);
          if (entity.height >= 1.0F)
            addVelocity(-(d2 / d4 * 1.25D), 0.25D, -(d3 / d4 * 1.25D)); 
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
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public void onLivingUpdate() {
    this.reachWidth = 6.0F;
    if (isHero() && !isWild())
      if (this.world.isRemote) {
        double d0 = this.rand.nextGaussian() * 0.02D;
        double d1 = this.rand.nextGaussian() * 0.02D;
        double d2 = this.rand.nextGaussian() * 0.02D;
        double d3 = 10.0D;
        this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + (this.rand.nextFloat() * 3.0F * 2.0F) - 3.0D - d0 * d3, this.posY + (this.rand.nextFloat() * 12.0F) - d1 * d3, this.posZ + (this.rand.nextFloat() * 3.0F * 2.0F) - 3.0D - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
      }  
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) < 128.0D && getSpecialAttackTimer() <= 0 && this.onGround && isHero())
      performSpecialAttack(); 
    this.stepHeight = 4.0F;
    super.onLivingUpdate();
    for (int t = 0; t < 8; t++) {
      if (this.motionX != 0.0D && this.motionZ != 0.0D) {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY - 0.25D);
        int k = MathHelper.floor(this.posZ);
        IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
        if (iblockstate.getMaterial() != Material.AIR)
          this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockstate) }); 
      } 
    } 
  }
  
  public void performSpecialAttack() {
    this.motionY = 2.0D;
    playSound(ESound.golemSpecial, 10.0F, 0.75F);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void fall(float distance, float damageMultiplier) {
    if (getSpecialAttackTimer() <= 0 && isHero() && getAttackTarget() != null) {
      setSpecialAttackTimer(400);
      playSound(ESound.golemSmash, 10.0F, 0.9F);
      createEngenderModExplosionFireless((Entity)this, this.posX, this.posY - 2.0D, this.posZ, 3.0F, false);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(48.0D, 3.0D, 48.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null && !false) {
            inflictEngenderMobDamage(entity, " was smashed by ", DamageSource.causeExplosionDamage((EntityLivingBase)this), 50.0F);
            entity.isAirBorne = true;
            float f = MathHelper.sqrt(MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.sin(this.rotationYaw * 0.017453292F) + -MathHelper.cos(this.rotationYaw * 0.017453292F) * -MathHelper.cos(this.rotationYaw * 0.017453292F));
            entity.motionX /= 2.0D;
            entity.motionZ /= 2.0D;
            entity.motionX -= (MathHelper.sin(this.rotationYaw * 0.017453292F) / f) * 1.0D;
            entity.motionZ -= (-MathHelper.cos(this.rotationYaw * 0.017453292F) / f) * 1.0D;
            entity.motionY += (this.rand.nextInt(30) == 0) ? 30.0D : 3.0D;
          } 
        }  
    } 
    super.fall(distance, damageMultiplier);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.guyHurt, getSoundVolume(), getSoundPitch() + 0.4F); 
    return SoundEvents.ENTITY_ZOMBIE_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.guyDeath, getSoundVolume(), getSoundPitch() + 0.4F); 
    return SoundEvents.ENTITY_ZOMBIE_DEATH;
  }
  
  protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) {
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 3.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 0.8F) / getFittness());
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 5.0F;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_GIANT;
  }
  
  protected float getSoundPitch() {
    return isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 0.5F);
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  public boolean canBeCollidedWith() {
    return true;
  }
  
  public World getWorld() {
    return this.world;
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    return attackEntityFrom(source, damage);
  }
}

