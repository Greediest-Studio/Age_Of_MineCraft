package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.pathfinding.PathNodeType;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EntityIcyEnderCreeper extends EntityTameBase implements IJumpingMount, Armored, Ender {
  private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
  
  private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.1D, 0)).setSaved(false);
  
  private int lastCreepySound = 0;
  
  private int targetChangeTime = 0;
  
  protected float jumpPower;
  
  public EntityIcyEnderCreeper(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.5F, 2.75F);
    } else {
      setSize(0.5F, 2.875F);
    } 
    this.stepHeight = 1.0F;
    this.isOffensive = true;
    setPathPriority(PathNodeType.WATER, -1.0F);
    setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
    setPathPriority(PathNodeType.DANGER_CACTUS, -1.0F);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.5D, 48.0F, 12.0F));
    this.tasks.addTask(3, new EntityAIFriendlyAttackMelee(this, 1.5D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.8D, 80));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    this.experienceValue = 10;
  }
  
  public String getDescName() {
    return "icy_ender_creeper";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityIcyEnderCreeper(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
  }
  
  public int timesToConvert() {
    return 27;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSMassive() {
    return 2.5F;
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
  
  public void setAttackTarget(EntityLivingBase entitylivingbaseIn) {
    super.setAttackTarget(entitylivingbaseIn);
    IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
    if (entitylivingbaseIn == null) {
      this.targetChangeTime = 0;
      iattributeinstance.removeModifier(attackingSpeedBoostModifier);
    } else {
      this.targetChangeTime = this.ticksExisted;
      if (!iattributeinstance.hasModifier(attackingSpeedBoostModifier))
        iattributeinstance.applyModifier(attackingSpeedBoostModifier); 
    } 
  }
  
  protected void entityInit() {
    super.entityInit();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.ENDER;
  }
  
  public void createChild() {
    super.createChild();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (int i = 0; i < 1 + this.rand.nextInt(10); i++) {
        EntityIcyEnderCreeper baby = new EntityIcyEnderCreeper(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-100000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity(baby);
      }  
  }
  
  public void playEndermanSound() {
    if (this.ticksExisted >= this.lastCreepySound + 400) {
      this.lastCreepySound = this.ticksExisted;
      if (!isSilent())
        this.world.playSound(this.posX, this.posY + getEyeHeight(), this.posZ, SoundEvents.ENTITY_ENDERMEN_STARE, getSoundCategory(), isSneaking() ? 1.0F : 2.5F, EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F, false); 
    } 
  }
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    if (isArmsRaised() && net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      playEndermanSound(); 
    super.notifyDataManagerChange(key);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.25F) : super.getSoundPitch();
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.88F) : (this.height * 0.89F);
  }
  
  public void performSpecialAttack() {
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(128.0D, 128.0D, 128.0D), Predicates.and(EntitySelectors.IS_ALIVE));
    if (list != null && !list.isEmpty())
        for (EntityLivingBase entity : list) {
            if (entity != null)
                if (!false) {
                    teleportToEntity(entity);
                    attackEntityAsMob(entity);
                }
        }
    setSpecialAttackTimer(1200);
  }
  
  public void onLivingUpdate() {
    if (getAttackTarget() != null && getAttackTarget().isEntityAlive() && getDistanceSq(getAttackTarget()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (isWet() && this.hurtResistantTime <= 10)
      attackEntityFrom((new DamageSource("onFire")).setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled(), 2.0F); 
    if (getAttackTarget() != null) {
      if (this.ticksExisted % 400 == 0)
        playSound(SoundEvents.ENTITY_ENDERMEN_STARE, isSneaking() ? 1.0F : 2.5F, EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F); 
      if ((getAttackTarget()).height <= 2.5F && getAttackTarget().isNonBoss() && getAttackTarget() instanceof EntityLiving && !(getAttackTarget() instanceof EntityTameBase)) {
        getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 9));
        getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60, 0));
        ((EntityLiving)getAttackTarget()).getLookHelper().setLookPosition((getAttackTarget()).posX + this.rand.nextDouble() * 60.0D - 30.0D, (getAttackTarget()).posY + this.rand.nextDouble() * 60.0D - 30.0D, (getAttackTarget()).posZ + this.rand.nextDouble() * 60.0D - 30.0D, 180.0F, 180.0F);
        ((EntityLiving)getAttackTarget()).setAttackTarget(null);
        (getAttackTarget()).renderYawOffset = (getAttackTarget()).rotationYaw = (getAttackTarget()).rotationYawHead;
      } 
    } 
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
      this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
      for (int i = 0; i < 2; i++) {
        double d0 = (float)this.posX - 0.5D + this.rand.nextFloat();
        double d1 = ((float)this.posY + this.rand.nextFloat());
        double d2 = (float)this.posZ - 0.5D + this.rand.nextFloat();
        double d3 = (this.rand.nextDouble() - 0.5D) * 2.0D;
        double d4 = -this.rand.nextDouble();
        double d5 = (this.rand.nextDouble() - 0.5D) * 2.0D;
        d1 += this.rand.nextDouble() * this.height;
        this.world.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
      } 
    } 
    if (!isBeingRidden() && getGuardBlock() == null && getOwner() != null && (getDistanceSq(getOwner()) > 4096.0D || !canEntityBeSeen(getOwner())) && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      setAttackTarget(null);
      getNavigator().clearPath();
      teleportTo((getOwner()).posX, (getOwner()).posY, (getOwner()).posZ);
    } 
    if (!isBeingRidden() && getRevengeTarget() != null && getRNG().nextInt(20) == 0) {
      if (getRevengeTarget().getDistanceSq(this) < 2.0D && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        teleportRandomly(); 
      if (getRevengeTarget().getDistanceSq(this) > 128.0D && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        teleportToEntity(getRevengeTarget());
    } 
    super.onLivingUpdate();
  }
  
  protected void updateAITasks() {
    if (isWet()) {
      setFire(10);
      teleportRandomly();
    } 
    super.updateAITasks();
  }
  
  protected boolean teleportRandomly() {
    if (this.world.isDaytime() || isWet())
      playLivingSound(); 
    double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 32.0D;
    double d1 = this.posY + (this.rand.nextInt(64) - 32);
    double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportToEntity(Entity p_70816_1_) {
    Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, (getEntityBoundingBox()).minY + (this.height / 2.0F) - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
    vec3d = vec3d.normalize();
    double d0 = 16.0D;
    double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.x * d0;
    double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3d.y * d0;
    double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.z * d0;
    return teleportTo(d1, d2, d3);
  }
  
  protected boolean teleportTo(double x, double y, double z) {
    EnderTeleportEvent event = new EnderTeleportEvent(this, x, y, z, 0.0F);
    if (MinecraftForge.EVENT_BUS.post(event))
      return false; 
    boolean flag = (attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ()) && !isInLove() && !isRiding());
    if (flag) {
      this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F);
      playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.rand.nextFloat() < 0.01F) {
        EntityEndermite entityendermite = new EntityEndermite(this.world);
        entityendermite.setOwnerId(getOwnerId());
        entityendermite.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        this.world.spawnEntity(entityendermite);
      } 
    } 
    return flag;
  }
  
  public boolean attemptTeleport(double x, double y, double z) {
    double d0 = this.posX;
    double d1 = this.posY;
    double d2 = this.posZ;
    this.posX = x;
    this.posY = y;
    this.posZ = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos(this);
    World world = this.world;
    Random random = getRNG();
    if (world.isBlockLoaded(blockpos)) {
      boolean flag1 = false;
      while (!flag1 && blockpos.getY() > 0) {
        BlockPos blockpos1 = blockpos.down();
        IBlockState iblockstate = world.getBlockState(blockpos1);
        if (iblockstate.getMaterial().blocksMovement()) {
          flag1 = true;
          continue;
        } 
        this.posY--;
        blockpos = blockpos1;
      } 
      if (flag1) {
        setPositionAndUpdate(this.posX, this.posY, this.posZ);
        if (isBeingRidden())
          getControllingPassenger().setPositionAndUpdate(this.posX, this.posY, this.posZ); 
        if (world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(getEntityBoundingBox()))
          flag = true; 
      } 
    } 
    if (!flag) {
      setPositionAndUpdate(d0, d1, d2);
      return false;
    } 
    int i = 128;
    for (int j = 0; j < 128; j++) {
      double d6 = j / 127.0D;
      float f = (random.nextFloat() - 0.5F) * 0.2F;
      float f1 = (random.nextFloat() - 0.5F) * 0.2F;
      float f2 = (random.nextFloat() - 0.5F) * 0.2F;
      double d3 = d0 + (this.posX - d0) * d6 + (random.nextDouble() - 0.5D) * this.width * 2.0D;
      double d4 = d1 + (this.posY - d1) * d6 + random.nextDouble() * this.height;
      double d5 = d2 + (this.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * this.width * 2.0D;
      world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, f, f1, f2);
    } 
    if (this instanceof EntityCreature)
      getNavigator().clearPath(); 
    return true;
  }
  
  protected SoundEvent getAmbientSound() {
    return isArmsRaised() ? SoundEvents.ENTITY_ENDERMEN_SCREAM : SoundEvents.ENTITY_ENDERMEN_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch()); 
    return SoundEvents.ENTITY_ENDERMEN_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch()); 
    return SoundEvents.ENTITY_ENDERMEN_DEATH;
  }
  
  protected Item getDropItem() {
    return Items.ENDER_PEARL;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null && !isWild() && false && !isChild() && !player.isSneaking() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      player.startRiding(this);
      return true;
    } 
    if (!stack.isEmpty() && stack.getItem() == Items.ENDER_EYE && (hasOwner(player) || false)) {
      List<EntityTameBase> list = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow(256.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty() && !isBeingRidden())
          for (EntityTameBase entity : list) {
              if (entity != null)
                  if (false) {
                      this.world.playSound(null, entity.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F);
                      entity.changeDimension(1);
                  }
          }
      this.world.playSound(null, getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F);
      this.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F);
      changeDimension(1);
      player.changeDimension(1);
      return true;
    } 
    return false;
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      double d8 = 0.5D;
      Vec3d vec3 = getLook(1.0F);
      double dx = vec3.x * d8;
      double dz = vec3.z * d8;
      passenger.setPosition(this.posX + dx, this.posY + 0.25D, this.posZ + dz);
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
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = this.rotationYawHead = entitylivingbase.rotationYaw;
      this.rotationPitch = 0.0F;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 1.5F);
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      this.jumpMovementFactor = 0.02F;
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = this.jumpPower * getFittness();
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
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_ICY_ENDER_CREEPER;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    Entity entity = source.getTrueSource();
    setSitResting(false);
    if (isEntityAlive() && (isEntityInvulnerable(source) || source.getTrueSource() instanceof net.minecraft.entity.monster.EntityCreeper || source instanceof net.minecraft.util.EntityDamageSourceIndirect || source.isExplosion() || source.isProjectile())) {
      if (teleportRandomly())
        return true; 
      return false;
    } 
    boolean flag = super.attackEntityFrom(source, amount);
    if (source.isUnblockable())
      teleportRandomly(); 
    return flag;
  }
}


