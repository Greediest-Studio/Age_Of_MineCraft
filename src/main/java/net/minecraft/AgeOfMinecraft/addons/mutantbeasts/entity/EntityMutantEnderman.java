package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBParticles;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantEnderman extends EntityTameBase implements IRangedAttackMob, IJumpingMount, Undead, Massive, Armored, Ender {
  private static final DataParameter<Byte> ACTIVE_ARM = EntityDataManager.createKey(EntityMutantEnderman.class, DataSerializers.BYTE);
  
  private static final DataParameter<Byte> CLONE_STATE = EntityDataManager.createKey(EntityMutantEnderman.class, DataSerializers.BYTE);
  
  public static final int MAX_DEATH_TIME = 280;
  
  public static final byte MELEE_ATTACK = 4;
  
  public static final byte THROW_ATTACK = 5;
  
  public static final byte STARE_ATTACK = 6;
  
  public static final byte TELEPORT_ATTACK = 7;
  
  public static final byte SCREAM_ATTACK = 8;
  
  public static final byte CLONE_ATTACK = 9;
  
  public final int[] heldBlock = new int[5];
  
  public static final byte TELESMASH_ATTACK = 10;
  
  public static final byte DEATH_ATTACK = 11;
  
  private int attackID;
  
  private int attackTick;
  
  private int prevArmScale;
  
  private int armScale;
  
  public int hasTarget;
  
  private int screamDelayTick;
  
  public final int[] heldBlockTick = new int[5];
  
  private boolean triggerThrowBlock;
  
  private int blockSearchTick;
  
  private List<EntityMutantEnderman> cloneList;
  
  private List<Entity> deathEntities;
  
  private EntityMutantEnderman cloner;
  
  private DamageSource deathCause = DamageSource.GENERIC;
  
  private int dirty = -1;
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.2D, 40, 96.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  protected float jumpPower;
  
  public EntityMutantEnderman(World worldIn) {
    super(worldIn);
    this.experienceValue = 900;
    this.ignoreFrustumCheck = true;
    this.cloneList = new ArrayList<>();
    setSize(0.9F, 4.2F);
    this.tasks.addTask(1, new MeleeGoal());
    this.tasks.addTask(1, new ThrowBlockGoal());
    this.tasks.addTask(1, new ForcedLookGoal());
    this.tasks.addTask(1, new ScreamGoal());
    this.tasks.addTask(1, new CloneGoal());
    this.tasks.addTask(1, new TeleSmashGoal());
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.2D, 64.0F, 12.0F));
    this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 0.83D, 0.0F));
    this.tasks.addTask(8, new EntityAILookIdle(this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_enderman";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(96.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(24.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantEnderman(this.world);
  }
  
  public int getSpawnTimer() {
    return 2;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 750;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !player.isSneaking() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(ACTIVE_ARM, (byte) 0);
    this.getDataManager().register(CLONE_STATE, (byte) 0);
  }
  
  public int getActiveArm() {
    return this.getDataManager().get(ACTIVE_ARM);
  }
  
  private void setActiveArm(int armID) {
    this.getDataManager().set(ACTIVE_ARM, (byte) armID);
  }
  
  public boolean isClone() {
    return (this.getDataManager().get(CLONE_STATE) > 0);
  }
  
  private boolean isDecoyClone() {
    return (this.getDataManager().get(CLONE_STATE) > 1);
  }
  
  private void setCloneState(int newState) {
    byte currentState = this.getDataManager().get(CLONE_STATE);
    if (currentState == newState)
      return; 
    if (currentState == 1 && newState == 0) {
      for (EntityMutantEnderman clone : this.cloneList)
        clone.onDeathUpdate(); 
      this.cloneList.clear();
      spawnTeleportParticles(true);
      this.navigator.clearPath();
      this.stepHeight = 2.5F;
    } 
    this.getDataManager().set(CLONE_STATE, (byte) newState);
    setAttackID((newState > 0) ? 9 : 0);
  }
  
  public int getAttackID() {
    return this.attackID;
  }
  
  private void setAttackID(int attackID) {
    this.attackID = attackID;
    this.world.setEntityState(this, (byte)attackID);
  }
  
  public int getAttackTick() {
    return this.attackTick;
  }
  
  public float getEyeHeight() {
    return isClone() ? 2.55F : (this.height * 0.9285F);
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return new MBGroundPathNavigator(this, worldIn);
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public int getMaxFallHeight() {
    return isClone() ? 3 : super.getMaxFallHeight();
  }
  
  public boolean canBeCollidedWith() {
    return (super.canBeCollidedWith() && this.attackID != 7);
  }
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    super.notifyDataManagerChange(key);
    if (CLONE_STATE.equals(key))
      if (isClone()) {
        setSize(0.6F, 2.9F);
      } else {
        setSize(0.9F, 4.2F);
      }  
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
      playSound(MBSoundEvents.ENTITY_MUTANT_ENDERMAN_TELEPORT, 1.0F, 1.0F);
      teleportAttack(this);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.rand.nextFloat() < 0.01F) {
        EntityEndermite entityendermite = new EntityEndermite(this.world);
        entityendermite.setOwnerId(getOwnerId());
        entityendermite.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        this.world.spawnEntity(entityendermite);
      } 
      return true;
    } 
    return flag;
  }
  
  public boolean attemptTeleport(double x, double y, double z) {
    spawnTeleportParticles(false);
    teleportAttack(this);
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
        spawnTeleportParticles(false);
        teleportAttack(this);
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
  
  public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
    super.setAttackTarget(entitylivingbaseIn);
    setFlag(2, (entitylivingbaseIn != null));
  }
  
  public boolean isAggressive() {
    return getFlag(2);
  }
  
  @SideOnly(Side.CLIENT)
  public float getArmScale(float partialTicks) {
    return (this.prevArmScale + (this.armScale - this.prevArmScale) * partialTicks) / 10.0F;
  }
  
  private void updateTargetTick() {
    this.prevArmScale = this.armScale;
    if (isAggressive())
      this.hasTarget = 20; 
    boolean emptyHanded = true;
    int i;
    for (i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] > 0)
        emptyHanded = false; 
      if (this.hasTarget > 0) {
        if (this.heldBlock[i] > 0)
          this.heldBlockTick[i] = Math.min(10, this.heldBlockTick[i] + 1); 
      } else {
        this.heldBlockTick[i] = Math.max(0, this.heldBlockTick[i] - 1);
      } 
    } 
    if (this.hasTarget > 0) {
      this.armScale = Math.min(10, this.armScale + 1);
    } else if (emptyHanded) {
      this.armScale = Math.max(0, this.armScale - 1);
    } else if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      for (i = 1; i < this.heldBlock.length; i++) {
        if (this.heldBlock[i] != 0 && this.heldBlockTick[i] == 0) {
          BlockPos pos = new BlockPos(this.posX - 1.5D + this.rand.nextDouble() * 4.0D, this.posY - 0.5D + this.rand.nextDouble() * 2.5D, this.posZ - 1.5D + this.rand.nextDouble() * 4.0D);
          if (this.world.isAirBlock(pos) && !this.world.isAirBlock(pos.down()) && this.world.isBlockFullCube(pos.down()) && ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
            this.world.setBlockState(pos, Block.getStateById(this.heldBlock[i]));
            Block block = Block.getBlockById(this.heldBlock[i]);
            SoundType soundType = block.getSoundType(block.getDefaultState(), this.world, pos, this);
            playSound(soundType.getPlaceSound(), (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
            sendHoldBlock(i, 0, 0);
          } else {
            this.triggerThrowBlock = true;
          } 
        } 
      } 
    } 
    this.hasTarget = Math.max(0, this.hasTarget - 1);
  }
  
  private void updateScreamEntities() {
    this.screamDelayTick = Math.max(0, this.screamDelayTick - 1);
    if (this.attackID == 8 && this.attackTick >= 40 && this.attackTick <= 160)
      for (Entity entity : this.world.getEntitiesInAABBexcluding((Entity)this, getEntityBoundingBox().grow(32.0D), EntityEndersoulFragment.IS_VALID_TARGET)) {
        if (entity instanceof EntityLivingBase && !false)
          entity.rotationPitch += 5.0F; 
      }  
  }
  
  private void spawnTeleportParticles(boolean clone) {
    if (clone && !isSilent())
      this.world.playSound(null, this.posX, this.posY + this.height / 2.0D, this.posZ, MBSoundEvents.ENTITY_MUTANT_ENDERMAN_TELEPORT, getSoundCategory(), 1.0F, 1.0F); 
    this.world.setEntityState(this, (byte)(clone ? 12 : 1));
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 1) {
      spawnBigParticles();
    } else if (id == 12) {
      EntityUtil.spawnEndersoulParticles(this, 256, 1.8F);
    } else if (id == 0 || (id >= 4 && id <= 11)) {
      this.attackID = id;
      this.attackTick = 0;
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.attackID != 0)
      this.attackTick++; 
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    if (this.attackID == 0 && isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && !false && getDistanceSq(getAttackTarget()) < (this.width * this.width + (getAttackTarget()).width * (getAttackTarget()).width) + 9.0D)
      attackEntityAsMob(getAttackTarget());
    updateTargetTick();
    updateScreamEntities();
    double h = (this.attackID != 11) ? this.height : (this.height + 1.0F);
    double w = (this.attackID != 11) ? this.width : (this.width * 1.5F);
    boolean targetBlind = (getAttackTarget() != null && getAttackTarget().isPotionActive(MobEffects.BLINDNESS));
    if (!targetBlind && !isClone()) {
      for (int i = 0; i < 3; i++) {
        double x = this.posX + (this.rand.nextDouble() - 0.5D) * w;
        double y = this.posY + this.rand.nextDouble() * h - 0.25D;
        double z = this.posZ + (this.rand.nextDouble() - 0.5D) * w;
        this.world.spawnParticle(EnumParticleTypes.PORTAL, x, y, z, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
      } 
      if (getAttackTarget() != null) {
        if ((getAttackTarget() instanceof net.minecraft.entity.EntityFlying || (getAttackTarget()).posY > this.posY + this.height) && !isClone() && !isDecoyClone()) {
          this.tasks.addTask(3, this.aiArrowAttack);
          this.tasks.removeTask(this.aiAttackOnCollide);
        } else {
          this.tasks.addTask(3, this.aiAttackOnCollide);
          this.tasks.removeTask(this.aiArrowAttack);
        } 
      } else {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
      } 
    } 
  }
  
  private void updateBlockFrenzy() {
    this.blockSearchTick = Math.max(0, this.blockSearchTick - 1);
    if (getAttackTarget() != null && this.attackID == 0) {
      if (this.blockSearchTick == 0 && this.rand.nextInt((!hasPath() || getAttackTarget() instanceof IRangedAttackMob) ? 100 : 600) == 0)
        this.blockSearchTick = 200 + this.rand.nextInt(80); 
      if (this.blockSearchTick > 0) {
        BlockPos pos = new BlockPos(this.posX - 2.5D + this.rand.nextDouble() * 5.0D, this.posY - 0.5D + this.rand.nextDouble() * 3.0D, this.posZ - 2.5D + this.rand.nextDouble() * 5.0D);
        IBlockState blockState = this.world.getBlockState(pos);
        int index = getFavorableHand();
        if (index != -1 && EntityEnderman.getCarriable(blockState.getBlock())) {
          this.heldBlock[index] = Block.getIdFromBlock(blockState.getBlock());
          this.heldBlockTick[index] = 0;
        } 
      } 
    } 
  }
  
  private void updateTeleport() {
    EntityLivingBase entityLivingBase = getAttackTarget();
    teleportByChance((entityLivingBase == null) ? 1600 : 800, entityLivingBase);
    if (entityLivingBase != null) {
      double d = getDistanceSq(entityLivingBase);
      if (isRidingSameEntity(entityLivingBase) || d > 1024.0D || (d > 36.0D && !hasPath()))
        teleportByChance(10, entityLivingBase);
    } 
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (isDecoyClone() && this.cloner != null && this.cloner.getAttackTarget() != null) {
      faceEntity(this.cloner.getAttackTarget(), 10.0F, 40.0F);
      if (this.ticksExisted % 10 == 0)
        getNavigator().tryMoveToEntityLiving(this.cloner.getAttackTarget(), 1.2D);
      if (this.ticksExisted % 20 == 0)
        attackEntityAsMob(this.cloner.getAttackTarget());
    } 
    if (isWet() && this.ticksExisted % 40 == 0 && !isClone())
      attackEntityFrom(DamageSource.ON_FIRE, 2.0F); 
    if (this.dirty >= 0)
      this.dirty++; 
    if (this.dirty >= 8) {
      this.dirty = -1;
      for (int i = 1; i < this.heldBlock.length; i++) {
        if (this.heldBlock[i] > 0)
          sendHoldBlock(i, this.heldBlock[i], 0); 
      } 
    } 
    updateBlockFrenzy();
    updateTeleport();
  }
  
  private int getAvailableHand() {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] == 0)
        list.add(i);
    } 
    if (list.isEmpty())
      return -1; 
    return list.get(this.rand.nextInt(list.size()));
  }
  
  private int getFavorableHand() {
    List<Integer> outer = new ArrayList<>();
    List<Integer> inner = new ArrayList<>();
    for (int i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] == 0)
        if (i <= 2) {
          outer.add(i);
        } else {
          inner.add(i);
        }  
    } 
    if (outer.isEmpty() && inner.isEmpty())
      return -1; 
    if (!outer.isEmpty())
      return outer.get(this.rand.nextInt(outer.size()));
    return inner.get(this.rand.nextInt(inner.size()));
  }
  
  private int getThrowingHand() {
    List<Integer> outer = new ArrayList<>();
    List<Integer> inner = new ArrayList<>();
    for (int i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] != 0)
        if (i <= 2) {
          outer.add(i);
        } else {
          inner.add(i);
        }  
    } 
    if (outer.isEmpty() && inner.isEmpty())
      return -1; 
    if (!inner.isEmpty())
      return inner.get(this.rand.nextInt(inner.size()));
    return outer.get(this.rand.nextInt(outer.size()));
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.attackID == 0) {
      int i = getAvailableHand();
      if (!teleportByChance(6, entityIn))
        if (i != -1) {
          boolean allHandsFree = (this.heldBlock[1] == 0 && this.heldBlock[2] == 0);
          if (allHandsFree && (this.rand.nextInt(10) == 0 || getHealth() <= getMaxHealth() / 4.0F)) {
            setAttackID(7);
          } else if (allHandsFree && this.rand.nextInt(2) == 0) {
            setAttackID(10);
          } else {
            setActiveArm(i);
            setAttackID(4);
          } 
        } else {
          this.triggerThrowBlock = true;
        }  
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    } 
    if (this.attackID == 9) {
      boolean flag = super.attackEntityAsMob(entityIn);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) ? (isDecoyClone() ? (this.rand.nextInt(3) != 0) : (this.rand.nextInt(2) == 0)) : (this.rand.nextInt(2) == 0)) {
        double x = entityIn.posX + ((this.rand.nextFloat() - 0.5F) * 24.0F);
        double z = entityIn.posZ + ((this.rand.nextFloat() - 0.5F) * 24.0F);
        double y = entityIn.posY + this.rand.nextInt(5) + 4.0D;
        teleportTo(x, y, z);
      } 
      if (flag)
        heal(4.0F); 
      return flag;
    } 
    return true;
  }
  
  public boolean isPotionApplicable(PotionEffect potioneffectIn) {
    return (!isClone() && super.isPotionApplicable(potioneffectIn));
  }
  
  private boolean teleportByChance(int chance, Entity entity) {
    if (this.attackID != 0 && !isClone())
      return false; 
    if (this.rand.nextInt(Math.max(1, chance)) == 0)
      return (entity == null) ? teleportRandomly() : teleportToEntity(entity); 
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  private void spawnBigParticles() {
    int temp = 256;
    if (this.attackID == 7)
      temp *= 2; 
    for (int i = 0; i < temp; i++) {
      float f = (this.rand.nextFloat() - 0.5F) * 1.8F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 1.8F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 1.8F;
      boolean death = (this.attackID != 11);
      double h = death ? this.height : (this.height + 1.0F);
      double w = death ? this.width : (this.width * 1.5F);
      double tempX = this.posX + (this.rand.nextDouble() - 0.5D) * w;
      double tempY = this.posY + (this.rand.nextDouble() - 0.5D) * h + (death ? 1.5F : 0.5F);
      double tempZ = this.posZ + (this.rand.nextDouble() - 0.5D) * w;
      this.world.spawnParticle(MBParticles.ENDERSOUL, tempX, tempY, tempZ, f, f1, f2);
    } 
  }
  
  public void teleportAttack(EntityLivingBase attacker) {
    double r = 3.0D;
    int duration = 140 + attacker.getRNG().nextInt(60);
    DamageSource damageSource = DamageSource.causeMobDamage(attacker);
    if (attacker instanceof EntityPlayer) {
      r = 2.0D;
      duration = 100;
      damageSource = DamageSource.causePlayerDamage((EntityPlayer)attacker);
    } 
    for (Entity entity : attacker.world.getEntitiesInAABBexcluding(attacker, attacker.getEntityBoundingBox().grow(r), EntityEndersoulFragment.IS_VALID_TARGET)) {
      if (entity instanceof EntityLivingBase && !false) {
        EntityLivingBase living = (EntityLivingBase)entity;
        inflictEngenderMobDamage((EntityLivingBase)entity, " was splattered by ", damageSource, 4.0F);
        if (attacker.getRNG().nextInt(3) == 0)
          living.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, duration)); 
        double x = living.posX - attacker.posX;
        double z = living.posZ - attacker.posZ;
        double signX = x / Math.abs(x);
        double signZ = z / Math.abs(z);
        if (!Double.isNaN(signX) && !Double.isNaN(signZ)) {
          living.motionX = (r * signX * 2.0D - x) * 0.20000000298023224D;
          living.motionY = 0.20000000298023224D;
          living.motionZ = (r * signZ * 2.0D - z) * 0.20000000298023224D;
          EntityUtil.knockBackBlockingPlayer(attacker);
        } 
      } 
    } 
  }
  
  private void createClone(double x, double y, double z) {
    EntityMutantEnderman clone = new EntityMutantEnderman(this.world);
    clone.setPosition(this.posX, this.posY, this.posZ);
    clone.cloner = this;
    clone.experienceValue = this.rand.nextInt(2);
    clone.setOwnerId(getOwnerId());
    clone.setCloneState(2);
    clone.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMaxHealth());
    clone.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    clone.setHealth(getHealth());
    clone.setAttackTarget(getAttackTarget());
    if (clone.teleportTo(x, y, z)) {
      this.world.spawnEntity(clone);
      this.cloneList.add(clone);
    } 
  }
  
  private EntityMutantEnderman getRandomClone() {
    if (this.cloneList.isEmpty())
      return this; 
    return this.cloneList.get(this.rand.nextInt(this.cloneList.size()));
  }
  
  public boolean isPushedByWater() {
    return false;
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    if (isClone())
      heal(4.0F); 
  }
  
  public void onDeath(DamageSource cause) {
    if (isDecoyClone())
      return; 
    super.onDeath(cause);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (isClone()) {
        if (isDecoyClone()) {
          amount = 1.0F;
          onDeathUpdate();
        } else {
          setCloneState(0);
        } 
      } else {
        if (this.attackID == 8 && source != DamageSource.OUT_OF_WORLD)
          return false; 
        if (this.attackID == 6)
          this.attackTick = 100; 
        Entity entity = source.getTrueSource();
        boolean betterDodge = (entity == null);
        if (source.isProjectile() || source == DamageSource.FALL)
          betterDodge = true; 
        if (betterDodge) {
          if (!isWild())
            teleportToEntity(getOwner());
          teleportRandomly();
          return false;
        } 
        return super.attackEntityFrom(source, amount);
      }  
    return false;
  }
  
  public boolean leavesNoCorpse() {
    return false;
  }
  
  protected void onDeathUpdate() {
    if (isDecoyClone()) {
      spawnTeleportParticles(true);
      this.isDead = true;
    } else {
      super.onDeathUpdate();
      setSilent(false);
      if (this.deathTime == 80)
        playSound(getDeathSound(), getSoundVolume(), getSoundPitch()); 
      this.attackID = 11;
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        if (this.deathTime >= 60 && this.deathTime < 80 && this.deathEntities == null)
          this.deathEntities = this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().grow(16.0D), EntityEndersoulFragment.IS_VALID_TARGET);
        if (this.deathTime >= 60 && this.rand.nextInt(3) != 0) {
          EntityEndersoulFragment orb = new EntityEndersoulFragment(this.world, this);
          orb.setPosition(this.posX, this.posY + 3.8D, this.posZ);
          orb.motionX = ((this.rand.nextFloat() - 0.5F) * 1.5F);
          orb.motionY = ((this.rand.nextFloat() - 0.5F) * 1.5F);
          orb.motionZ = ((this.rand.nextFloat() - 0.5F) * 1.5F);
          this.world.spawnEntity(orb);
        } 
        if (this.deathTime >= 80 && this.deathTime < 260 && this.deathEntities != null)
          for (int i = 0; i < this.deathEntities.size(); i++) {
            Entity entity = this.deathEntities.get(i);
            if (entity.fallDistance > 4.5F)
              entity.fallDistance = 4.5F; 
            if (entity instanceof EntityLivingBase && false) {
              this.deathEntities.remove(i);
              i--;
            } else if (entity instanceof EntityLivingBase && !false) {
              double x = this.posX - entity.posX;
              double z = this.posZ - entity.posZ;
              double d = Math.sqrt(x * x + z * z);
              entity.motionX = 0.800000011920929D * x / d;
              entity.motionZ = 0.800000011920929D * z / d;
              inflictEngenderMobDamage((EntityLivingBase)entity, " was pummeled by ", DamageSource.MAGIC, 1.0F);
              if (this.posY + 4.0D > entity.posY)
                entity.motionY = Math.max(entity.motionY, 0.4000000059604645D); 
              EntityUtil.sendPlayerVelocityPacket(entity);
            } 
          }  
        if (this.deathTime >= 280) {
          EntityUtil.dropExperience(this, this.recentlyHit, this::getExperiencePoints, this.attackingPlayer);
          setDead();
        } 
      } 
    } 
  }
  
  public EntityItem entityDropItem(ItemStack stack, float offsetY) {
    return super.entityDropItem(stack, (this.deathTime > 0) ? 3.84F : 0.0F);
  }
  
  public boolean getCanSpawnHere() {
    if (this.rand.nextInt(3) == 0)
      return false; 
    if (this.world.provider.getDimensionType() == DimensionType.THE_END && this.rand.nextInt(2600) != 0)
      return false; 
    return (super.getCanSpawnHere() && this.world.canSeeSky(getPosition()));
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setByte("CloneState", this.getDataManager().get(CLONE_STATE));
    compound.setShort("ScreamDelay", (short)this.screamDelayTick);
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    if (compound.getByte("CloneState") < 2) {
      setCloneState(0);
    } else {
      setDead();
    } 
    this.screamDelayTick = compound.getShort("ScreamDelay");
  }
  
  public void playLivingSound() {
    if (this.attackID != 8 && !isClone())
      super.playLivingSound(); 
  }
  
  protected SoundEvent getAmbientSound() {
    return MBSoundEvents.ENTITY_MUTANT_ENDERMAN_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return isDecoyClone() ? null : MBSoundEvents.ENTITY_MUTANT_ENDERMAN_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return isClone() ? null : MBSoundEvents.ENTITY_MUTANT_ENDERMAN_DEATH;
  }
  
  protected ResourceLocation getLootTable() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_enderman");
  }
  
  public void sendHoldBlock(int blockIndex, int blockId, int blockData) {
    this.heldBlock[blockIndex] = blockId;
    this.heldBlockTick[blockIndex] = 0;
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    setAttackID(8);
    spawnExplosionParticle();
    return livingdata;
  }
  
  public void becomeAHero() {
    setIsHero(true);
    playSound(ESound.hero, 100.0F, 1.0F);
    this.ticksExisted = 0;
    setAttackID(8);
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 0.15F, getSoundPitch());
  }
  
  class ForcedLookGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public ForcedLookGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantEnderman.this.getAttackTarget();
      return (EntityMutantEnderman.this.attackID == 6 && this.attackTarget != null);
    }
    
    public void startExecuting() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.getNavigator().clearPath();
      float pitch = 0.7F + EntityMutantEnderman.this.rand.nextFloat() * 0.2F;
      EntityMutantEnderman.this.playSound(MBSoundEvents.ENTITY_MUTANT_ENDERMAN_STARE, 2.5F, pitch);
    }
    
    public boolean shouldContinueExecuting() {
      if (this.attackTarget == null || !this.attackTarget.isEntityAlive())
        return false; 
      return (EntityMutantEnderman.this.attackTick < 20);
    }
    
    public void updateTask() {
      if (this.attackTarget != null)
        EntityMutantEnderman.this.getLookHelper().setLookPositionWithEntity(this.attackTarget, 45.0F, 45.0F);
    }
    
    public void resetTask() {
      EntityMutantEnderman.this.setAttackID(0);
      EntityMutantEnderman.this.inflictEngenderMobDamage(this.attackTarget, " died from an unknown force made by ", DamageSource.MAGIC.setDamageIsAbsolute(), 4.0F + EntityMutantEnderman.this.getMaxHealth() / EntityMutantEnderman.this.getHealth());
      EntityMutantEnderman.this.inflictCustomStatusEffect(EntityMutantEnderman.this.world.getDifficulty(), this.attackTarget, MobEffects.BLINDNESS, 160 + EntityMutantEnderman.this.rand.nextInt(140), 0);
      this.attackTarget = null;
    }
  }
  
  class MeleeGoal extends EntityAIBase {
    public boolean shouldExecute() {
      return (EntityMutantEnderman.this.attackID == 4);
    }
    
    public void startExecuting() {
      EntityMutantEnderman.this.attackTick = 0;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantEnderman.this.attackTick < 10);
    }
    
    public void updateTask() {
      if (EntityMutantEnderman.this.attackTick == 3) {
        EntityMutantEnderman.this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 0.8F);
        for (Entity entity : EntityMutantEnderman.this.world.getEntitiesInAABBexcluding(EntityMutantEnderman.this, EntityMutantEnderman.this.getEntityBoundingBox().grow(6.0D), EntityEndersoulFragment.IS_VALID_TARGET)) {
          double dist = EntityMutantEnderman.this.getDistance(entity);
          double x = EntityMutantEnderman.this.posX - entity.posX;
          double z = EntityMutantEnderman.this.posZ - entity.posZ;
          boolean lower = (EntityMutantEnderman.this.getActiveArm() >= 3);
          if ((EntityMutantEnderman.this.getEntityBoundingBox()).minY <= (entity.getEntityBoundingBox()).maxY && dist <= (lower ? 4.0D : 6.0D) && EntityUtil.getHeadAngle(EntityMutantEnderman.this, x, z) <= 3.0F + (1.0F - (float)dist / 4.0F) * 40.0F) {
            DamageSource damageSource = DamageSource.causeMobDamage(EntityMutantEnderman.this);
            float attackDamage = (float)EntityMutantEnderman.this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
            float damage = (attackDamage > 0.0F) ? (attackDamage * (lower ? 1.5F : 3.0F)) : 0.0F;
            if (entity instanceof EntityLivingBase)
              EntityMutantEnderman.this.inflictEngenderMobDamage((EntityLivingBase)entity, " was slapped by ", damageSource, damage); 
            float power = 0.4F + EntityMutantEnderman.this.rand.nextFloat() * 0.2F;
            if (!lower)
              power += 0.2F; 
            entity.motionX = -x / dist * power;
            entity.motionY = (power * 0.6F);
            entity.motionZ = -z / dist * power;
            EntityUtil.knockBackBlockingPlayer(EntityMutantEnderman.this);
          } 
        } 
      } 
    }
    
    public void resetTask() {
      EntityMutantEnderman.this.setAttackID(0);
    }
  }
  
  class CloneGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public boolean shouldExecute() {
      if (EntityMutantEnderman.this.isDecoyClone() || EntityMutantEnderman.this.getAttackTarget() == null)
        return false; 
      if (EntityMutantEnderman.this.heldBlock[1] == 0 && EntityMutantEnderman.this.heldBlock[2] == 0)
        return (EntityMutantEnderman.this.attackID == 7 && !EntityMutantEnderman.this.isClone()); 
      return false;
    }
    
    public void startExecuting() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.setCloneState(1);
      EntityMutantEnderman.this.extinguish();
      EntityMutantEnderman.this.clearActivePotions();
      EntityMutantEnderman.this.spawnTeleportParticles(true);
      EntityMutantEnderman.this.stepHeight = 1.0F;
      this.attackTarget = EntityMutantEnderman.this.getAttackTarget();
      double x = this.attackTarget.posX + ((EntityMutantEnderman.this.rand.nextFloat() - 0.5F) * 24.0F);
      double z = this.attackTarget.posZ + ((EntityMutantEnderman.this.rand.nextFloat() - 0.5F) * 24.0F);
      double y = this.attackTarget.posY + 8.0D;
      for (int i = 0; i < 7; i++) {
        x = this.attackTarget.posX + ((EntityMutantEnderman.this.rand.nextFloat() - 0.5F) * 24.0F);
        z = this.attackTarget.posZ + ((EntityMutantEnderman.this.rand.nextFloat() - 0.5F) * 24.0F);
        y = this.attackTarget.posY + 8.0D;
        EntityMutantEnderman.this.createClone(x, y, z);
      } 
      EntityMutantEnderman.this.teleportTo(x, y, z);
      EntityMutantEnderman.this.createClone(EntityMutantEnderman.this.prevPosX, EntityMutantEnderman.this.prevPosY, EntityMutantEnderman.this.prevPosZ);
      EntityUtil.divertAttackers(EntityMutantEnderman.this, EntityMutantEnderman.this.getRandomClone());
    }
    
    public boolean shouldContinueExecuting() {
      return (this.attackTarget != null && this.attackTarget.isEntityAlive() && !EntityMutantEnderman.this.cloneList.isEmpty() && EntityMutantEnderman.this.attackID == 9 && EntityMutantEnderman.this.attackTick < 600);
    }
    
    public void updateTask() {
      for (int i = EntityMutantEnderman.this.cloneList.size() - 1; i >= 0; i--) {
        EntityMutantEnderman clone = EntityMutantEnderman.this.cloneList.get(i);
        if (!clone.isEntityAlive()) {
          EntityMutantEnderman.this.cloneList.remove(i);
          EntityUtil.divertAttackers(clone, EntityMutantEnderman.this.getRandomClone());
        } else {
          clone.setAttackTarget(this.attackTarget);
        } 
      } 
    }
    
    public void resetTask() {
      EntityMutantEnderman.this.setCloneState(0);
      this.attackTarget = null;
    }
  }
  
  class ScreamGoal extends EntityAIBase {
    public ScreamGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      if (EntityMutantEnderman.this.attackID == 8 && !EntityMutantEnderman.this.isClone())
        return true; 
      if (EntityMutantEnderman.this.getAttackTarget() != null)
        return (EntityMutantEnderman.this.screamDelayTick > 0) ? false : ((EntityMutantEnderman.this.rand.nextInt((EntityMutantEnderman.this.isWet() || EntityMutantEnderman.this.getAttackTarget() instanceof net.minecraft.entity.EntityFlying) ? 2 : 1000) == 0)); 
      return false;
    }
    
    public void startExecuting() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.setAttackID(8);
      EntityMutantEnderman.this.getNavigator().clearPath();
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantEnderman.this.screamDelayTick <= 0 && EntityMutantEnderman.this.attackTick < 165 && !EntityMutantEnderman.this.isClone());
    }
    
    public void updateTask() {
      if (EntityMutantEnderman.this.attackTick >= 40) {
        if (EntityMutantEnderman.this.attackTick == 40) {
          EntityMutantEnderman.this.world.getWorldInfo().setRaining(false);
          EntityMutantEnderman.this.spawnTeleportParticles(false);
          EntityMutantEnderman.this.playSound(MBSoundEvents.ENTITY_MUTANT_ENDERMAN_SCREAM, 5.0F, 0.7F + EntityMutantEnderman.this.rand.nextFloat() * 0.1F);
        } 
        List<Entity> screamEntities = EntityMutantEnderman.this.world.getEntitiesInAABBexcluding(EntityMutantEnderman.this, EntityMutantEnderman.this.getEntityBoundingBox().grow(48.0D), EntityEndersoulFragment.IS_VALID_TARGET);
        for (int i = 0; i < screamEntities.size(); i++) {
          Entity entity = screamEntities.get(i);
          if (entity instanceof EntityLivingBase)
            if (false) {
              screamEntities.remove(i);
              i--;
            } else {
              EntityMutantEnderman.this.inflictEngenderMobDamage((EntityLivingBase)entity, " died from a heartattack caused by ", DamageSource.causeMobDamage(EntityMutantEnderman.this).setDamageBypassesArmor().setDamageIsAbsolute(), 4.0F);
              if (entity instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase)entity;
                living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 3));
                living.addPotionEffect(new PotionEffect(MobEffects.POISON, 120 + EntityMutantEnderman.this.rand.nextInt(180), EntityMutantEnderman.this.rand.nextInt(2)));
                living.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 300 + EntityMutantEnderman.this.rand.nextInt(300), EntityMutantEnderman.this.rand.nextInt(2)));
                living.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 120 + EntityMutantEnderman.this.rand.nextInt(60), 10 + EntityMutantEnderman.this.rand.nextInt(2)));
                living.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 120 + EntityMutantEnderman.this.rand.nextInt(400), 0));
              } 
            }  
        } 
      } 
    }
    
    public void resetTask() {
      EntityMutantEnderman.this.setAttackID(0);
      EntityMutantEnderman.this.screamDelayTick = 600;
    }
  }
  
  class TeleSmashGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public TeleSmashGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantEnderman.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantEnderman.this.attackID == 10);
    }
    
    public void startExecuting() {
      EntityMutantEnderman.this.attackTick = 0;
      this.attackTarget.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 5));
      this.attackTarget.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 160 + this.attackTarget.getRNG().nextInt(160), 0));
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantEnderman.this.attackID == 10 && EntityMutantEnderman.this.attackTick < 30);
    }
    
    public void updateTask() {
      if (EntityMutantEnderman.this.attackTick < 20)
        EntityMutantEnderman.this.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);
      if (EntityMutantEnderman.this.attackTick == 17)
        this.attackTarget.dismountRidingEntity(); 
      if (EntityMutantEnderman.this.attackTick == 18 && this.attackTarget != null) {
        this.attackTarget.world.playSound(null, this.attackTarget.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, this.attackTarget.getSoundCategory(), 1.2F, 0.9F + this.attackTarget.getRNG().nextFloat() * 0.2F);
        if (EntityMutantEnderman.this.getDistance(this.attackTarget) > 24.0F) {
          double x = EntityMutantEnderman.this.posX;
          double y = EntityMutantEnderman.this.posY;
          double z = EntityMutantEnderman.this.posZ;
          this.attackTarget.setPositionAndUpdate(x, y, z);
        } else {
          double x = this.attackTarget.posX + ((this.attackTarget.getRNG().nextFloat() - 0.5F) * 14.0F);
          double y = (this.attackTarget.posY >= EntityMutantEnderman.this.posY + 13.0D) ? EntityMutantEnderman.this.posY : (this.attackTarget.posY + this.attackTarget.getRNG().nextFloat() + 13.0D);
          double z = this.attackTarget.posZ + ((this.attackTarget.getRNG().nextFloat() - 0.5F) * 14.0F);
          this.attackTarget.setPositionAndUpdate(x, y, z);
        } 
        this.attackTarget.world.playSound(null, this.attackTarget.getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, this.attackTarget.getSoundCategory(), 1.2F, 0.9F + this.attackTarget.getRNG().nextFloat() * 0.2F);
        this.attackTarget.stopActiveHand();
        EntityMutantEnderman.this.inflictEngenderMobDamage(this.attackTarget, " died from a heartattack caused by ", DamageSource.causeMobDamage(EntityMutantEnderman.this).setDamageBypassesArmor(), (float)EntityMutantEnderman.this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
        EntityMutantEnderman.this.spawnTeleportParticles(false);
      } 
    }
    
    public void resetTask() {
      EntityMutantEnderman.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  public class ThrowBlockGoal extends EntityAIBase {
    public boolean shouldExecute() {
      if (EntityMutantEnderman.this.attackID != 0)
        return false; 
      if (!EntityMutantEnderman.this.triggerThrowBlock)
        return false; 
      if (EntityMutantEnderman.this.getAttackTarget() != null && !EntityMutantEnderman.this.canEntityBeSeen(EntityMutantEnderman.this.getAttackTarget()))
        return false; 
      int id = EntityMutantEnderman.this.getThrowingHand();
      if (id == -1)
        return false; 
      EntityMutantEnderman.this.setActiveArm(id);
      return true;
    }
    
    public void startExecuting() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.setAttackID(5);
      int id = EntityMutantEnderman.this.getActiveArm();
      EntityMutantEnderman.this.world.spawnEntity(new EntityThrowingBlock(EntityMutantEnderman.this.world, EntityMutantEnderman.this, id));
      EntityMutantEnderman.this.sendHoldBlock(id, 0, 0);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantEnderman.this.attackID == 5 && EntityMutantEnderman.this.attackTick < 14);
    }
    
    public void resetTask() {
      EntityMutantEnderman.this.setAttackID(0);
      EntityMutantEnderman.this.setActiveArm(0);
      EntityMutantEnderman.this.triggerThrowBlock = false;
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
  
  public void handleStartJump(int p_184775_1_) {}
  
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
        this.motionY = 2.0D * this.jumpPower * getFittness();
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
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.attackID == 0)
      if (this.rand.nextInt() == 0) {
        setAttackID(10);
      } else {
        setAttackID(6);
      }  
  }
}


