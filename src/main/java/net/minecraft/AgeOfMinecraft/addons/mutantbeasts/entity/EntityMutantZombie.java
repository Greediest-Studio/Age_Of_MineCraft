package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.FriendlyZombieSummon;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.Shockwave;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantZombie extends EntityTameBase implements IJumpingMount, Undead, Massive, Armored {
  private static final DataParameter<Integer> ZOMBIE_VARIANT = EntityDataManager.createKey(EntityMutantZombie.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> LIVES = EntityDataManager.createKey(EntityMutantZombie.class, DataSerializers.VARINT);
  
  private static final DataParameter<Byte> THROW_ATTACK_STATE = EntityDataManager.createKey(EntityMutantZombie.class, DataSerializers.BYTE);
  
  public static final int MAX_DEATH_TIME = 140;
  
  public static final int MAX_VANISH_TIME = 100;
  
  public static final byte MELEE_ATTACK = 4;
  
  public static final byte THROW_ATTACK = 5;
  
  public static final byte ROAR_ATTACK = 6;
  
  private int attackID;
  
  private int attackTick;
  
  public int throwHitTick = -1;
  
  public int throwFinishTick = -1;
  
  public int vanishTime;
  
  private final List<Shockwave> seismicWavesList = new ArrayList<>();
  
  private final List<FriendlyZombieSummon> resurrections = new ArrayList<>();
  
  private EntityLivingBase lastAttacker;
  
  private DamageSource deathCause = DamageSource.GENERIC;
  
  protected float jumpPower;
  
  public EntityMutantZombie(World worldIn) {
    super(worldIn);
    this.experienceValue = 600;
    this.ignoreFrustumCheck = true;
    setSize(1.8F, 3.2F);
    this.tasks.addTask(1, new MeleeGoal());
    this.tasks.addTask(1, new RoarGoal());
    this.tasks.addTask(1, new ThrowAttackGoal());
    this.tasks.addTask(1, new TossAttackGoal());
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 64.0F, 12.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_zombie";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(96.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(22.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
    getEntityAttribute(SWIM_SPEED).setBaseValue(4.0D);
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void performSpecialAttack() {
    setAttackID(6);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() == Items.TOTEM_OF_UNDYING) {
      setLives(getLives() + 2);
      stack.shrink(1);
      (Minecraft.getMinecraft()).effectRenderer.emitParticleAtEntity((Entity)this, EnumParticleTypes.TOTEM, 30);
      playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
      performSpecialAttack();
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !player.isSneaking() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected boolean canFitPassenger(Entity passenger) {
    return (getPassengers().size() < 3);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      int i = getPassengers().indexOf(passenger);
      float f3 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f11 = MathHelper.sin(f3);
      float f4 = MathHelper.cos(f3);
      if (i == 2)
        passenger.setPosition(this.posX - (f4 * 1.25F), this.posY + (isHero() ? 2.25D : 2.0D), this.posZ - (f11 * 1.25F)); 
      if (i == 1)
        passenger.setPosition(this.posX + (f4 * 1.25F), this.posY + (isHero() ? 2.25D : 2.0D), this.posZ + (f11 * 1.25F)); 
      if (i == 0)
        passenger.setPosition(this.posX, this.posY + (isHero() ? (2.5D * getFittness()) : (2.25D * getFittness())), this.posZ); 
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
    playSound(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_GRUNT, 0.3F, 0.8F + this.rand.nextFloat() * 0.4F);
  }
  
  public void handleStopJump() {}
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing / 3.0F;
      forward = entitylivingbase.moveForward / 3.0F;
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = 3.0D * this.jumpPower * getFittness();
        if (isPotionActive(MobEffects.JUMP_BOOST))
          this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
        this.isAirBorne = true;
        if (forward > 0.0F) {
          float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
          float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
          this.motionX += (-1.6F * f * this.jumpPower);
          this.motionZ += (1.6F * f1 * this.jumpPower);
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
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(ZOMBIE_VARIANT, Integer.valueOf(0));
    getDataManager().register(THROW_ATTACK_STATE, Byte.valueOf((byte)0));
    getDataManager().register(LIVES, Integer.valueOf(0));
  }
  
  public int getZombieType() {
    return ((Integer)getDataManager().get(ZOMBIE_VARIANT)).intValue() - 1;
  }
  
  public void setZombieType(int villagerType) {
    if (villagerType == 2)
      this.isImmuneToFire = true; 
    if (villagerType == 3) {
      if (Loader.isModLoaded("abyssalcraft") && ACConfig.hardcoreMode) {
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(375.0D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(48.0D);
      } else {
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(187.5D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(24.0D);
      } 
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    } 
    if (isAntiMob())
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() * 2.0D); 
    getDataManager().set(ZOMBIE_VARIANT, Integer.valueOf(villagerType + 1));
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return (PathNavigate)new MBGroundPathNavigator((EntityLiving)this, worldIn);
  }
  
  protected float updateDistance(float renderYawOffset, float distance) {
    return !isEntityAlive() ? distance : super.updateDistance(renderYawOffset, distance);
  }
  
  public int getLives() {
    return ((Integer)this.dataManager.get(LIVES)).intValue();
  }
  
  private void setLives(int lives) {
    this.dataManager.set(LIVES, Integer.valueOf(lives));
  }
  
  public boolean hasThrowAttackHit() {
    return (((Byte)this.dataManager.get(THROW_ATTACK_STATE)).byteValue() != 0);
  }
  
  public void setThrowAttackHit(boolean hit) {
    byte b0 = ((Byte)this.dataManager.get(THROW_ATTACK_STATE)).byteValue();
    this.dataManager.set(THROW_ATTACK_STATE, Byte.valueOf(hit ? 1 : (byte)(b0 & 0xFFFFFFFE)));
  }
  
  public boolean isThrowAttackFinished() {
    return ((((Byte)this.dataManager.get(THROW_ATTACK_STATE)).byteValue() & 0x2) != 0);
  }
  
  public void setThrowAttackFinished(boolean finished) {
    byte b0 = ((Byte)this.dataManager.get(THROW_ATTACK_STATE)).byteValue();
    this.dataManager.set(THROW_ATTACK_STATE, Byte.valueOf(finished ? (byte)(b0 | 0x2) : (byte)(b0 & 0xFFFFFFFD)));
  }
  
  public int getAttackID() {
    return this.attackID;
  }
  
  public int getAttackTick() {
    return this.attackTick;
  }
  
  public float getEyeHeight() {
    return this.height * 0.875F;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public int getMaxFallHeight() {
    return 16;
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
    if (player.getHeldItem(hand).getItem() == Items.FLINT_AND_STEEL && this.deathTime > 0 && !isBurning() && !isWet()) {
      setFire(8);
      player.swingArm(hand);
      player.getHeldItem(hand).damageItem(1, (EntityLivingBase)player);
      this.world.playSound(player, getPosition(), SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
      return EnumActionResult.SUCCESS;
    } 
    return EnumActionResult.PASS;
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    if (!this.world.isRemote) {
      if (this.attackID == 0 && !(entityIn instanceof net.minecraft.entity.monster.EntityGolem) && !(entityIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.Flying) && !(entityIn instanceof net.minecraft.entity.passive.EntityFlying) && !(entityIn instanceof net.minecraft.entity.EntityFlying) && (this.rand.nextInt(4) == 0 || (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getHealth() <= 4.0F)) && (this.onGround || isInWater() || isInLava()))
        setAttackID(7); 
      if (this.attackID == 0 && (this.rand.nextInt(2) == 0 || entityIn instanceof net.minecraft.entity.monster.EntityCreeper) && (this.onGround || isInWater() || isInLava()))
        setAttackID(5); 
      if (this.attackID == 0 && (this.onGround || isInWater() || isInLava()))
        setAttackID(4); 
    } 
    return (getAttackID() != 0) ? true : super.attackEntityAsMob(entityIn);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.isProjectile() && source.getDamageLocation() != null)
      amount = (float)(amount * 0.5D); 
    if (this.attackID == 5)
      return false; 
    if (this.attackID == 6 && source != DamageSource.OUT_OF_WORLD) {
      if (this.attackTick < 10)
        return false; 
      amount *= 0.15F;
    } 
    return super.attackEntityFrom(source, amount);
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 0 || (id >= 4 && id <= 7)) {
      this.attackID = id;
      this.attackTick = 0;
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (getAttackTarget() != null) {
      EntityLivingBase entityLivingBase = getAttackTarget();
      if (!entityLivingBase.isEntityAlive()) {
        setAttackTarget(null);
      } else if (getDistanceSq((Entity)entityLivingBase) < 49.0D) {
        int chance = (!canEntityBeSeen((Entity)entityLivingBase) || (getLastDamageSource() != null && getLastDamageSource().isProjectile())) ? 5 : 20;
        if (this.attackID == 0 && (this.onGround || this.world.containsAnyLiquid(getEntityBoundingBox())) && this.rand.nextInt(chance) == 0)
          setAttackID(4); 
        if (this.attackID == 0 && getDistanceSq((Entity)entityLivingBase) < 1.0D && this.rand.nextInt(125) == 0 && canEntityBeSeen((Entity)entityLivingBase))
          setAttackID(5); 
      } 
    } 
    if (ForgeEventFactory.getMobGriefingEvent(this.world, (Entity)this)) {
      int x = MathHelper.floor(this.posX);
      int y = MathHelper.floor(this.posY);
      int z = MathHelper.floor(this.posZ);
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          for (int k = 0; k <= 3; k++) {
            BlockPos blockpos = new BlockPos(x + i, y + k, z + j);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            if ((iblockstate.getBlock() instanceof net.minecraft.block.BlockLeaves || iblockstate.getBlock() instanceof net.minecraft.block.BlockGlass) && ForgeEventFactory.onEntityDestroyBlock((EntityLivingBase)this, blockpos, iblockstate))
              this.world.destroyBlock(blockpos, false); 
          } 
        } 
      } 
    } 
  }
  
  public boolean isEntityImmuneToCoralium() {
    return (getZombieType() == 3 || super.isEntityImmuneToCoralium());
  }
  
  public boolean passesCoraliumPlague() {
    return (getZombieType() == 3);
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public void onUpdate() {
    super.onUpdate();
    fixRotation();
    updateAnimation();
    updateMeleeGrounds();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    if (this.isImmuneToFire)
      extinguish(); 
    if (isEntityAlive() && !this.world.isDaytime() && this.ticksExisted % 50 == 0)
      heal(1.0F); 
    for (int i = this.resurrections.size() - 1; i >= 0; i--) {
      FriendlyZombieSummon zr = this.resurrections.get(i);
      if (!zr.update(this))
        this.resurrections.remove(zr); 
    } 
    if (getHealth() > 0.0F) {
      this.deathTime = 0;
      this.vanishTime = 0;
    } 
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.attackID == 0 && isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && !false && getDistanceSq((Entity)getAttackTarget()) < (this.width * this.width + (getAttackTarget()).width * (getAttackTarget()).width) + 36.0D)
      attackEntityAsMob((Entity)getAttackTarget()); 
    if (!this.world.isRemote && getAttackTarget() != null && this.attackID == 0 && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D && this.rand.nextInt(5) == 0) {
      int i = MathHelper.floor(this.posX);
      int j = MathHelper.floor(this.posY - 0.20000000298023224D);
      int k = MathHelper.floor(this.posZ);
      IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
      if (iblockstate.getMaterial() != Material.AIR)
        this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockstate) }); 
    } 
  }
  
  private void fixRotation() {
    float yaw;
    for (yaw = this.rotationYawHead - this.renderYawOffset; yaw < -180.0F; yaw += 360.0F);
    while (yaw >= 180.0F)
      yaw -= 360.0F; 
    float offset = 0.1F;
    if (this.attackID == 4)
      offset = 0.2F; 
    this.renderYawOffset += yaw * offset;
  }
  
  protected void updateAnimation() {
    if (this.attackID != 0)
      this.attackTick++; 
    if (this.attackID == 5) {
      if (hasThrowAttackHit()) {
        if (this.throwHitTick == -1)
          this.throwHitTick = 0; 
        this.throwHitTick++;
      } 
      if (isThrowAttackFinished()) {
        if (this.throwFinishTick == -1)
          this.throwFinishTick = 0; 
        this.throwFinishTick++;
      } 
    } else {
      this.throwHitTick = -1;
      this.throwFinishTick = -1;
    } 
  }
  
  protected void updateMeleeGrounds() {
    if (!this.seismicWavesList.isEmpty()) {
      Shockwave wave = this.seismicWavesList.remove(0);
      wave.affectBlocks(this.world, (EntityLivingBase)this);
      AxisAlignedBB box = new AxisAlignedBB(wave.getX(), (wave.getY() + 1), wave.getZ(), (wave.getX() + 3), (wave.getY() + 2), (wave.getZ() + 3));
      if (wave.isFirst()) {
        double addScale = this.rand.nextDouble() * 0.75D;
        box.grow(0.25D + addScale, 0.25D + addScale * 0.5D, 0.25D + addScale);
      } 
      for (Entity entity : this.world.getEntitiesInAABBexcluding((Entity)this, box, EntitySelectors.CAN_AI_TARGET)) {
        if (entity instanceof EntityLivingBase && !false) {
          DamageSource source = DamageSource.causeMobDamage((EntityLivingBase)this).setDamageIsAbsolute();
          inflictEngenderMobDamage((EntityLivingBase)entity, " was smashed by ", source, wave.isFirst() ? (12 + this.rand.nextInt(12)) : (6 + this.rand.nextInt(6)));
          double x = entity.posX - this.posX;
          double z = entity.posZ - this.posZ;
          double d = Math.sqrt(x * x + z * z);
          entity.motionX = x / d * 0.4D;
          entity.motionY = 0.25D;
          entity.motionZ = z / d * 0.4D;
          if (entity instanceof EntityLivingBase) {
            EntityUtil.sendPlayerVelocityPacket(entity);
            EntityUtil.knockBackBlockingPlayer((Entity)this);
            if (this.rand.nextInt(5) == 0)
              inflictCustomStatusEffect(this.world.getDifficulty(), (EntityLivingBase)entity, MobEffects.HUNGER, 160, 1); 
          } 
        } 
      } 
    } 
  }
  
  public boolean isPushedByWater() {
    return false;
  }
  
  public void onDeath(DamageSource cause) {
    if (!this.world.isRemote) {
      this.deathCause = cause;
      if (getLastDamageSource() != null && getLastDamageSource().getTrueSource() instanceof EntityLivingBase) {
        this.lastAttacker = (EntityLivingBase)getLastDamageSource().getTrueSource();
      } else if (cause.getTrueSource() instanceof EntityLivingBase) {
        this.lastAttacker = (EntityLivingBase)cause.getTrueSource();
      } 
      if (this.recentlyHit > 0)
        this.recentlyHit += 140; 
    } 
  }
  
  public boolean leavesNoCorpse() {
    return false;
  }
  
  protected void onDeathUpdate() {
    if (isBurning()) {
      this.vanishTime++;
    } else if (this.vanishTime > 0) {
      this.vanishTime--;
    } 
    if (getLives() <= 0) {
      super.onDeathUpdate();
      if (this.deathTicks == 40)
        super.onDeath(this.deathCause); 
    } else if (this.onGround) {
      if (this.deathCause.getDamageType() == "antimatter") {
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() * 2.0D);
        setHealth(getMaxHealth());
        this.deathTime = 0;
        this.dead = false;
        setIsAntiMob(true);
        this.ticksExisted = 0;
        this.renderYawOffset = this.rotationYaw = this.rotationYawHead = 0.0F;
        this.rotationPitch = 0.0F;
        int i = this.experienceValue;
        while (i > 0 && !this.world.isRemote) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
        } 
      } else {
        this.deathTicks++;
        if (this.deathTicks <= 80) {
          this.deathTime++;
        } else if (this.deathTicks >= 100) {
          this.deathTime--;
        } 
        if (this.deathTime <= -1) {
          this.deathTime = 0;
          this.deathTicks = 0;
          this.vanishTime = 0;
          if (!this.world.isRemote) {
            setLives(getLives() - 1);
            if (this.lastAttacker != null) {
              setRevengeTarget(this.lastAttacker);
              this.lastAttacker.setRevengeTarget((EntityLivingBase)this);
            } 
            setHealth(Math.round(getMaxHealth() / 3.75F));
          } 
        } 
      } 
    } 
    if (!this.onGround) {
      this.deathTicks = 0;
      this.deathTime = 0;
    } 
    if (this.vanishTime >= 100 && getLives() > 0) {
      if (!this.world.isRemote) {
        if (EngenderConfig.general.useMessage && !isWild() && getOwner() instanceof EntityPlayerMP) {
          ((EntityPlayerMP)getOwner()).sendMessage(getCombatTracker().getDeathMessage());
          this.world.playSound((EntityPlayer)getOwner(), getOwner().getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), getSoundPitch());
        } 
        setLives(0);
      } 
      this.deathTime = 0;
    } 
  }
  
  public int getSpawnTimer() {
    return 2;
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    float f = this.world.getDifficultyForLocation(new BlockPos((Entity)this)).getAdditionalDifficulty();
    if (isBurning() && this.rand.nextFloat() < f * 0.5F)
      entity.setFire(5 * (int)f); 
    if (getZombieType() == 1 && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).motionY += 0.2D;
      inflictCustomStatusEffect((this.world.getDifficulty() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.HUNGER, 200 * (int)f, 0);
      if (this.world.getDifficulty() == EnumDifficulty.HARD)
        inflictCustomStatusEffect((this.world.getDifficulty() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.WEAKNESS, 200, 0); 
    } 
    if (getZombieType() == 2 && entity instanceof EntityLivingBase) {
      entity.motionX = 0.0D;
      entity.motionY = 0.0D;
      entity.motionZ = 0.0D;
      inflictCustomStatusEffect(this.world.getDifficulty(), (EntityLivingBase)entity, MobEffects.SLOWNESS, 5, 1);
      if (entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != null && this.world.getDifficulty() == EnumDifficulty.HARD && this.rand.nextInt(3) == 0)
        ((EntityLiving)entity).setAttackTarget(null); 
    } 
    if (Loader.isModLoaded("abyssalcraft"))
      if (getZombieType() == 3 && entity instanceof EntityLivingBase) {
        entity.motionX = 0.0D;
        entity.motionY = 0.0D;
        entity.motionZ = 0.0D;
        inflictCustomStatusEffect(this.world.getDifficulty(), (EntityLivingBase)entity, MobEffects.SLOWNESS, 5, 1);
        if (entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != null && this.world.getDifficulty() == EnumDifficulty.HARD && this.rand.nextInt(3) == 0)
          ((EntityLiving)entity).setAttackTarget(null); 
      }  
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    setAttackID(6);
    setLives(3);
    Biome biome = this.world.getBiome(new BlockPos((Entity)this));
    if (biome instanceof net.minecraft.world.biome.BiomeDesert && this.rand.nextInt(2) != 0) {
      setZombieType(1);
      this.isImmuneToFire = true;
    } 
    if (biome instanceof net.minecraft.world.biome.BiomeSavanna && this.rand.nextInt(2) != 0)
      setZombieType(2); 
    if (Loader.isModLoaded("abyssalcraft"))
      if (biome instanceof com.shinoow.abyssalcraft.common.world.biome.BiomeGenAbywasteland || (biome instanceof com.shinoow.abyssalcraft.common.world.biome.BiomeGenCorSwamp && this.rand.nextInt(2) != 0) || this.dimension == ACLib.abyssal_wasteland_id)
        setZombieType(3);  
    if (getZombieType() == 3)
      if (Loader.isModLoaded("abyssalcraft") && ACConfig.hardcoreMode) {
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(375.0D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(48.0D);
      } else {
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(187.5D);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(24.0D);
      }  
    return livingdata;
  }
  
  public void becomeAHero() {
    setIsHero(true);
    playSound(ESound.hero, 100.0F, 1.0F);
    this.ticksExisted = 0;
    setAttackID(6);
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    super.onKillEntity(entityLivingIn);
    if ((this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof net.minecraft.entity.passive.EntityVillager) {
      net.minecraft.entity.passive.EntityVillager entityvillager = (net.minecraft.entity.passive.EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.world);
      entityzombie.rotationPitch = entityvillager.rotationPitch;
      entityzombie.renderYawOffset = entityzombie.rotationYaw = entityzombie.rotationYawHead = entityvillager.rotationYawHead;
      entityzombie.copyLocationAndAnglesFrom((Entity)entityLivingIn);
      this.world.removeEntity((Entity)entityLivingIn);
      entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), null);
      entityzombie.setVillagerType(entityvillager.getProfession());
      entityzombie.setNoAI(entityvillager.isAIDisabled());
      if (!isWild())
        entityzombie.setOwnerId(getOwnerId()); 
      if (entityvillager.hasCustomName())
        entityzombie.setCustomNameTag(entityvillager.getCustomNameTag()); 
      this.world.spawnEntity((Entity)entityzombie);
      this.world.playEvent((EntityPlayer)null, 1026, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
    } 
    if (entityLivingIn instanceof net.minecraft.entity.passive.EntityVillager) {
      net.minecraft.entity.passive.EntityVillager entityvillager = (net.minecraft.entity.passive.EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.world);
      entityzombie.rotationPitch = entityvillager.rotationPitch;
      entityzombie.renderYawOffset = entityzombie.rotationYaw = entityzombie.rotationYawHead = entityvillager.rotationYawHead;
      entityzombie.copyLocationAndAnglesFrom((Entity)entityLivingIn);
      this.world.removeEntity((Entity)entityLivingIn);
      entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), null);
      entityzombie.setVillagerType(entityvillager.getProfession());
      entityzombie.setGrowingAge(((net.minecraft.entity.passive.EntityVillager)entityLivingIn).getGrowingAge());
      entityzombie.setNoAI(entityvillager.isAIDisabled());
      if (!isWild())
        entityzombie.setOwnerId(getOwnerId()); 
      if (entityvillager.hasCustomName())
        entityzombie.setCustomNameTag(entityvillager.getCustomNameTag()); 
      this.world.spawnEntity((Entity)entityzombie);
      this.world.playEvent((EntityPlayer)null, 1026, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
    } 
  }
  
  protected void handleJumpWater() {
    this.motionY += 0.03999999910593033D;
  }
  
  protected void handleJumpLava() {
    handleJumpWater();
  }
  
  private void setAttackID(int id) {
    this.attackID = id;
    this.attackTick = 0;
    this.world.setEntityState((Entity)this, (byte)id);
  }
  
  public boolean getCanSpawnHere() {
    return (super.getCanSpawnHere() && this.world.canSeeSky(getPosition()));
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    String s = EntityList.getEntityString((Entity)this);
    if (s == null)
      s = "generic"; 
    switch (getZombieType()) {
      case 1:
        return I18n.translateToLocal("entity.MutantHuskHelpful.name");
      case 2:
        return I18n.translateToLocal("entity.MutantPrisonZombieHelpful.name");
      case 3:
        return I18n.translateToLocal("entity.MutantAbyssalZombieHelpful.name");
    } 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setInteger("ZombieType", getZombieType());
    compound.setInteger("Lives", getLives());
    compound.setShort("VanishTime", (short)this.vanishTime);
    if (!this.resurrections.isEmpty()) {
      NBTTagList nbtTagList = new NBTTagList();
      for (FriendlyZombieSummon resurrect : this.resurrections) {
        NBTTagCompound compound1 = NBTUtil.createPosTag(resurrect.getPosition());
        compound1.setInteger("Tick", resurrect.getTick());
        nbtTagList.appendTag((NBTBase)compound1);
      } 
      compound.setTag("Resurrections", (NBTBase)nbtTagList);
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    setLives(compound.getInteger("Lives"));
    setZombieType(compound.getInteger("ZombieType"));
    this.vanishTime = compound.getShort("VanishTime");
    NBTTagList nbtTagList = compound.getTagList("Resurrections", 10);
    for (int i = 0; i < nbtTagList.tagCount(); i++) {
      NBTTagCompound compound1 = nbtTagList.getCompoundTagAt(i);
      this.resurrections.add(i, new FriendlyZombieSummon(this.world, NBTUtil.getPosFromTag(compound1), compound1.getInteger("Tick")));
    } 
  }
  
  protected SoundEvent getAmbientSound() {
    return MBSoundEvents.ENTITY_MUTANT_ZOMBIE_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_ZOMBIE_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return MBSoundEvents.ENTITY_MUTANT_ZOMBIE_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.5F, 0.8F);
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 0.5F, getSoundPitch());
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantZombie(this.world);
  }
  
  protected ResourceLocation getLootTable() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_zombie");
  }
  
  class MeleeGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    private double dirX;
    
    private double dirZ;
    
    public MeleeGoal() {
      this.dirX = -1.0D;
      this.dirZ = -1.0D;
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantZombie.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantZombie.this.attackID == 4);
    }
    
    public void startExecuting() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.navigator.clearPath();
      EntityMutantZombie.this.livingSoundTime = -EntityMutantZombie.this.getTalkInterval();
      EntityMutantZombie.this.playSound(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_ATTACK, 0.3F, EntityMutantZombie.this.getSoundPitch());
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantZombie.this.attackTick < 25);
    }
    
    public void updateTask() {
      if (EntityMutantZombie.this.attackTick < 8)
        EntityMutantZombie.this.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0F, 30.0F); 
      if (EntityMutantZombie.this.attackTick == 8) {
        double x = this.attackTarget.posX - EntityMutantZombie.this.posX;
        double z = this.attackTarget.posZ - EntityMutantZombie.this.posZ;
        double d = Math.sqrt(x * x + z * z);
        this.dirX = x / d;
        this.dirZ = z / d;
      } 
      if (EntityMutantZombie.this.getAttackTarget() != null && EntityMutantZombie.this.attackTick == 12) {
        if (this.attackTarget != null && EntityMutantZombie.this.getDistance((Entity)this.attackTarget) <= EntityMutantZombie.this.width + this.attackTarget.width + 9.0F)
          EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " was smashed by ", DamageSource.causeMobDamage((EntityLivingBase)EntityMutantZombie.this), (float)EntityMutantZombie.this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()); 
        int x = MathHelper.floor(EntityMutantZombie.this.posX + this.dirX * 2.0D);
        int y = MathHelper.floor((EntityMutantZombie.this.getEntityBoundingBox()).minY);
        int z = MathHelper.floor(EntityMutantZombie.this.posZ + this.dirZ * 2.0D);
        int x1 = MathHelper.floor(EntityMutantZombie.this.posX + this.dirX * 8.0D * EntityMutantZombie.this.width);
        int z1 = MathHelper.floor(EntityMutantZombie.this.posZ + this.dirZ * 8.0D * EntityMutantZombie.this.width);
        Shockwave.createWaves(EntityMutantZombie.this.world, EntityMutantZombie.this.seismicWavesList, x, z, x1, z1, y);
        EntityMutantZombie.this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 0.8F + EntityMutantZombie.this.rand.nextFloat() * 0.4F);
      } 
    }
    
    public void resetTask() {
      EntityMutantZombie.this.setAttackID(0);
      this.attackTarget = null;
      this.dirX = -1.0D;
      this.dirZ = -1.0D;
    }
  }
  
  class RoarGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public RoarGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      if (EntityMutantZombie.this.getAttackID() == 6)
        return true; 
      this.attackTarget = EntityMutantZombie.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantZombie.this.onGround && EntityMutantZombie.this.getDistanceSq((Entity)this.attackTarget) > 16.0D) ? ((EntityMutantZombie.this.rand.nextFloat() * 100.0F < 0.35F)) : false;
    }
    
    public void startExecuting() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.setAttackID(6);
      EntityMutantZombie.this.idleTime = 0;
      EntityMutantZombie.this.livingSoundTime = -EntityMutantZombie.this.getTalkInterval();
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantZombie.this.attackTick < 120);
    }
    
    public void updateTask() {
      if (this.attackTarget != null && EntityMutantZombie.this.attackTick < 75)
        EntityMutantZombie.this.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0F, 30.0F); 
      if (EntityMutantZombie.this.attackTick == 10) {
        EntityMutantZombie.this.playSound(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_ROAR, 10.0F, EntityMutantZombie.this.getSoundPitch());
        for (Entity entity : EntityMutantZombie.this.world.getEntitiesInAABBexcluding((Entity)EntityMutantZombie.this, EntityMutantZombie.this.getEntityBoundingBox().grow((EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero()) ? 32.0D : 16.0D), EntitySelectors.CAN_AI_TARGET)) {
          if (entity instanceof EntityLivingBase && !false) {
            double x = entity.posX - EntityMutantZombie.this.posX;
            double z = entity.posZ - EntityMutantZombie.this.posZ;
            double d = Math.sqrt(x * x + z * z);
            entity.motionX = x / d * 2.0D;
            entity.motionY = 0.5D;
            entity.motionZ = z / d * 2.0D;
            entity.isAirBorne = true;
            if (entity instanceof EntityLivingBase)
              EntityMutantZombie.this.inflictEngenderMobDamage((EntityLivingBase)entity, " was yelled at to death by ", (new DamageSource("yell")).setDamageBypassesArmor(), (EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero()) ? 16.0F : 4.0F); 
          } 
        } 
      } 
      if (EntityMutantZombie.this.attackTick >= 20 && EntityMutantZombie.this.attackTick < 80)
        if (EntityMutantZombie.this.attackTick % ((EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero()) ? 4 : (EntityMutantZombie.this.world.isDaytime() ? 20 : 10)) == 0) {
          int x = MathHelper.floor(EntityMutantZombie.this.posX);
          int y = MathHelper.floor((EntityMutantZombie.this.getEntityBoundingBox()).minY);
          int z = MathHelper.floor(EntityMutantZombie.this.posZ);
          x += (1 + EntityMutantZombie.this.rand.nextInt(8)) * (EntityMutantZombie.this.rand.nextBoolean() ? 1 : -1);
          z += (1 + EntityMutantZombie.this.rand.nextInt(8)) * (EntityMutantZombie.this.rand.nextBoolean() ? 1 : -1);
          y = FriendlyZombieSummon.getSuitableGround(EntityMutantZombie.this.world, x, y - 1, z);
          if (y != -1)
            EntityMutantZombie.this.resurrections.add(new FriendlyZombieSummon(EntityMutantZombie.this.world, x, y, z)); 
        }  
      EntityMutantZombie.this.navigator.clearPath();
    }
    
    public void resetTask() {
      this.attackTarget = null;
      EntityMutantZombie.this.setAttackID(0);
      if (EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero())
        EntityMutantZombie.this.setSpecialAttackTimer(600); 
    }
  }
  
  class ThrowAttackGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    private int hit;
    
    private int finish;
    
    public ThrowAttackGoal() {
      this.hit = -1;
      this.finish = -1;
      setMutexBits(7);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantZombie.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantZombie.this.attackID == 5);
    }
    
    public void startExecuting() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.navigator.clearPath();
      this.attackTarget.dismountRidingEntity();
      EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " got tossed by ", (DamageSource)new EntityDamageSource("throw", (Entity)EntityMutantZombie.this), 4.0F);
      this.attackTarget.renderYawOffset = this.attackTarget.rotationYaw = this.attackTarget.rotationYawHead = (float)MathHelper.atan2(this.attackTarget.motionZ, this.attackTarget.motionX) * 57.295776F - 90.0F;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantZombie.this.attackID == 5 && this.finish < 10);
    }
    
    public void updateTask() {
      EntityMutantZombie.this.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0F, 30.0F);
      if (EntityMutantZombie.this.attackTick == 2) {
        double x = this.attackTarget.posX - EntityMutantZombie.this.posX;
        double z = this.attackTarget.posZ - EntityMutantZombie.this.posZ;
        double d = Math.sqrt(x * x + z * z);
        this.attackTarget.motionX = x / d;
        this.attackTarget.motionY = EntityMutantZombie.this.height;
        this.attackTarget.motionZ = z / d;
        this.attackTarget.velocityChanged = true;
      } 
      if (EntityMutantZombie.this.attackTick == 15) {
        double d1 = this.attackTarget.posX - EntityMutantZombie.this.posX;
        double d2 = this.attackTarget.posY - EntityMutantZombie.this.posY;
        double x = this.attackTarget.posZ - EntityMutantZombie.this.posZ;
        double z = Math.sqrt(d1 * d1 + d2 * d2 + x * x);
        EntityMutantZombie.this.motionX = d1 / z * 5.0D;
        EntityMutantZombie.this.motionY = d2 / z * EntityMutantZombie.this.height;
        EntityMutantZombie.this.motionZ = x / z * 5.0D;
      } else if (EntityMutantZombie.this.attackTick > 15) {
        double d1 = (EntityMutantZombie.this.width * 2.0F * EntityMutantZombie.this.width * 2.0F);
        double d2 = EntityMutantZombie.this.getDistanceSq(this.attackTarget.posX, (this.attackTarget.getEntityBoundingBox()).minY, this.attackTarget.posZ);
        if ((d2 < d1 || EntityMutantZombie.this.onGround || EntityMutantZombie.this.posY >= this.attackTarget.posY) && this.hit == -1) {
          this.hit = 0;
          EntityMutantZombie.this.setThrowAttackHit(true);
          EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " was smashed by ", (DamageSource)new EntityDamageSource("throw", (Entity)EntityMutantZombie.this), (float)EntityMutantZombie.this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * EntityMutantZombie.this.height);
          double x = this.attackTarget.posX - EntityMutantZombie.this.posX;
          double z = this.attackTarget.posZ - EntityMutantZombie.this.posZ;
          double d = Math.sqrt(x * x + z * z);
          this.attackTarget.motionX = x / d;
          this.attackTarget.motionY = -EntityMutantZombie.this.height;
          this.attackTarget.motionZ = z / d;
          this.attackTarget.hurtResistantTime = 10;
          this.attackTarget.velocityChanged = true;
          EntityUtil.disableShield(this.attackTarget, 150);
          EntityMutantZombie.this.playSound(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_GRUNT, 0.3F, EntityMutantZombie.this.getSoundPitch() - 0.2F);
          this.attackTarget.renderYawOffset = this.attackTarget.rotationYaw = this.attackTarget.rotationYawHead = (float)MathHelper.atan2(this.attackTarget.motionZ, this.attackTarget.motionX) * 57.295776F - 90.0F;
        } 
        if (this.hit >= 0)
          this.hit++; 
        if ((EntityMutantZombie.this.onGround || EntityMutantZombie.this.isInWater() || EntityMutantZombie.this.isInLava()) && this.finish == -1) {
          this.finish = 0;
          EntityMutantZombie.this.setThrowAttackFinished(true);
        } 
        if (this.finish >= 0)
          this.finish++; 
      } 
    }
    
    public void resetTask() {
      EntityMutantZombie.this.setAttackID(0);
      this.attackTarget = null;
      this.hit = -1;
      this.finish = -1;
      EntityMutantZombie.this.setThrowAttackHit(false);
      EntityMutantZombie.this.setThrowAttackFinished(false);
    }
  }
  
  class TossAttackGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public TossAttackGoal() {
      setMutexBits(7);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantZombie.this.getAttackTarget();
      return (this.attackTarget != null && this.attackTarget.onGround && EntityMutantZombie.this.attackID == 7);
    }
    
    public void startExecuting() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.navigator.clearPath();
      this.attackTarget.dismountRidingEntity();
      EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " got tossed by ", (DamageSource)new EntityDamageSource("throw", (Entity)EntityMutantZombie.this), 5.0F);
      this.attackTarget.renderYawOffset = this.attackTarget.rotationYaw = this.attackTarget.rotationYawHead = (float)MathHelper.atan2(this.attackTarget.motionZ, this.attackTarget.motionX) * 57.295776F - 90.0F;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantZombie.this.attackTick < 10);
    }
    
    public void updateTask() {
      EntityMutantZombie.this.getLookHelper().setLookPositionWithEntity((Entity)this.attackTarget, 30.0F, 30.0F);
      if (EntityMutantZombie.this.attackTick == 1) {
        double x = this.attackTarget.posX - EntityMutantZombie.this.posX;
        double z = this.attackTarget.posZ - EntityMutantZombie.this.posZ;
        double d = Math.sqrt(x * x + z * z);
        this.attackTarget.motionX = x / d;
        this.attackTarget.motionY = EntityMutantZombie.this.height;
        this.attackTarget.motionZ = z / d;
        this.attackTarget.velocityChanged = true;
      } 
    }
    
    public void resetTask() {
      EntityMutantZombie.this.setAttackID(0);
      this.attackTarget = null;
      EntityMutantZombie.this.setAttackTarget(null);
    }
  }
}


