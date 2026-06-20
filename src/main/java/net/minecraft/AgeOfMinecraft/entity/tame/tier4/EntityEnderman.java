package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.util.EntityAICompat;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EntityEnderman extends EntityTameBase implements IJumpingMount, Armored, Ender {
  private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
  
  private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448D, 0)).setSaved(false);
  
  private static final Set<Block> carriableBlocks = Sets.newIdentityHashSet();
  
  private static final DataParameter<Optional<IBlockState>> CARRIED_BLOCK = EntityDataManager.createKey(EntityEnderman.class, DataSerializers.OPTIONAL_BLOCK_STATE);
  
  private int lastCreepySound = 0;
  
  private int targetChangeTime = 0;
  
  public boolean andr;
  
  private static final DataParameter<Boolean> OMNI_DODGE = EntityDataManager.createKey(EntityEnderman.class, DataSerializers.BOOLEAN);
  
  protected float jumpPower;
  
  public EntityEnderman(World worldIn) {
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
    this.tasks.addTask(0, new AIPlaceBlock(this));
    this.tasks.addTask(0, new AITakeBlock(this));
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.5D, 48.0F, 12.0F));
    this.tasks.addTask(3, new EntityAIFriendlyAttackMelee(this, 1.5D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.8D, 80));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    this.experienceValue = 10;
  }
  
  public String getDescName() {
    return (!EngenderConfig.loreFriendlyMode && EngenderConfig.mobs.useMobTalkerModels && this.andr) ? "andr" : "enderman";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityEnderman(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
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
      return I18n.translateToLocal("entity." + str + ".cmm.name") + (canDodgeAllAttacks() ? " (Ultra Instinct)" : "");
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name") + (canDodgeAllAttacks() ? " (Ultra Instinct)" : "");
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
    this.getDataManager().register(CARRIED_BLOCK, Optional.absent());
    this.getDataManager().register(OMNI_DODGE, Boolean.FALSE);
  }
  
  public EnumTier getTier() {
    return (canDodgeAllAttacks() || this.andr) ? EnumTier.TIER6 : EnumTier.TIER4;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.ENDER;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return new EntityMutantEnderman(this.world);
    return null;
  }
  
  public void createChild() {
    super.createChild();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (int i = 0; i < 1 + this.rand.nextInt(10); i++) {
        EntityEnderman baby = new EntityEnderman(this.world);
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
    IBlockState iblockstate = getHeldBlockState();
    tagCompound.setBoolean("Andrea", this.andr);
    tagCompound.setBoolean("OmniDodge", canDodgeAllAttacks());
    if (iblockstate != null) {
      tagCompound.setShort("carried", (short)Block.getIdFromBlock(iblockstate.getBlock()));
      tagCompound.setShort("carriedData", (short)iblockstate.getBlock().getMetaFromState(iblockstate));
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    IBlockState iblockstate;
    super.readEntityFromNBT(tagCompund);
    setDodgeAllAttacks(tagCompund.getBoolean("OmniDodge"));
    this.andr = tagCompund.getBoolean("Andrea");
    if (tagCompund.hasKey("carried", 8)) {
      iblockstate = Block.getBlockFromName(tagCompund.getString("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
    } else {
      iblockstate = Block.getBlockById(tagCompund.getShort("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
    } 
    if (iblockstate == null || iblockstate.getBlock() == null || iblockstate.getMaterial() == Material.AIR)
      iblockstate = null; 
    setHeldBlockState(iblockstate);
  }
  
  public boolean canDodgeAllAttacks() {
    return this.getDataManager().get(OMNI_DODGE);
  }
  
  public void setDodgeAllAttacks(boolean powered) {
    this.getDataManager().set(OMNI_DODGE, powered);
  }
  
  protected float getSoundPitch() {
    return this.andr ? (super.getSoundPitch() - 0.25F) : (EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.25F) : super.getSoundPitch());
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
                    teleportTo(entity.posX, entity.posY, entity.posZ);
                    teleportRandomly();
                    attackEntityAsMob(entity);
                }
        }
    setSpecialAttackTimer(this.andr ? 20 : 1200);
  }
  
  public void setCustomNameTag(String name) {
    super.setCustomNameTag(name);
    if (!EngenderConfig.loreFriendlyMode && EngenderConfig.mobs.useMobTalkerModels && !this.andr && "Andr".equals(name)) {
      this.ticksExisted = 0;
      becomeAHero();
      this.andr = true;
    } else {
      this.andr = false;
    } 
  }
  
  public void onLivingUpdate() {
    ItemStack block = (getHeldBlockState() != null) ? new ItemStack(getHeldBlockState().getBlock()) : ItemStack.EMPTY;
    this.basicInventory.setInventorySlotContents(7, block);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getAttackTarget() != null && getAttackTarget() instanceof EntityWitherStorm && this.rand.nextInt(100) == 0) {
      teleportTo((getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ);
      (getAttackTarget()).motionY = -0.1D;
      ((EntityWitherStorm)getAttackTarget()).Grow(((EntityWitherStorm)getAttackTarget()).getSize() - 2);
      (getAttackTarget()).hurtResistantTime = 0;
      setHeldBlockState(Blocks.OBSIDIAN.getDefaultState());
      attackEntityAsMob(getAttackTarget());
    } 
    if (getLevel() >= 300 && getStrength() >= 100.0F && getStamina() >= 100.0F && getIntelligence() >= 100.0F && getDexterity() >= 100.0F && getAgility() >= 100.0F)
      setDodgeAllAttacks(true); 
    if (getAttackTarget() != null && canDodgeAllAttacks() && getNavigator().noPath())
      teleportToEntity(getAttackTarget());
    if (canDodgeAllAttacks()) {
      clearActivePotions();
      extinguish();
      if (getEnergy() <= 0.0F) {
        setHealth(0.0F);
        playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
      } 
    } 
    if (EngenderConfig.mobs.useMobTalkerModels && !isHero() && this.andr)
      becomeAHero(); 
    if (getAttackTarget() != null && getAttackTarget().isEntityAlive() && getDistanceSq(getAttackTarget()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (isWet() && !this.andr && this.hurtResistantTime <= 10)
      attackEntityFrom((new DamageSource("waterburn")).setFireDamage().setDamageBypassesArmor(), 2.0F); 
    if (getAttackTarget() != null) {
      if (this.ticksExisted % 400 == 0)
        playSound(SoundEvents.ENTITY_ENDERMEN_STARE, isSneaking() ? 1.0F : 2.5F, EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F); 
      if ((getAttackTarget()).height <= 2.25F && getAttackTarget().isNonBoss() && getAttackTarget() instanceof EntityLiving && !(getAttackTarget() instanceof EntityTameBase)) {
        getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 9));
        getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 60, 0));
        ((EntityLiving)getAttackTarget()).getLookHelper().setLookPosition((getAttackTarget()).posX + this.rand.nextDouble() * 60.0D - 30.0D, (getAttackTarget()).posY + this.rand.nextDouble() * 60.0D - 30.0D, (getAttackTarget()).posZ + this.rand.nextDouble() * 60.0D - 30.0D, 180.0F, 180.0F);
        ((EntityLiving)getAttackTarget()).setAttackTarget(null);
        (getAttackTarget()).renderYawOffset = (getAttackTarget()).rotationYaw = (getAttackTarget()).rotationYawHead;
        EntityAICompat.clearTargetTasks((EntityLiving)getAttackTarget());
      } 
      if (canDodgeAllAttacks() && getEnergy() > 20.0F && this.rand.nextInt(80) == 0) {
        (getAttackTarget()).hurtResistantTime = 0;
        teleportRandomly();
        this.motionX = this.motionY = this.motionZ = 0.0D;
        setEnergy(getEnergy() - 0.01F);
        attackEntityAsMob(getAttackTarget());
      } 
    } 
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive()) {
      int i;
      for (i = 0; i < 2; i++) {
        this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
        if (canDodgeAllAttacks()) {
          this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
          this.world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
          this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
          this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
          this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
        } 
      } 
      for (i = 0; i < 2; i++) {
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
    if (isEntityAlive() && !isBeingRidden() && getGuardBlock() == null && getOwner() != null && (getDistanceSq(getOwner()) > 4096.0D || !canEntityBeSeen(getOwner())) && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      setAttackTarget(null);
      getNavigator().clearPath();
      teleportTo((getOwner()).posX, (getOwner()).posY, (getOwner()).posZ);
    } 
    if (isEntityAlive() && !isBeingRidden() && getRevengeTarget() != null && getRNG().nextInt(20) == 0) {
      if (getRevengeTarget().getDistanceSq(this) < 2.0D && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        teleportRandomly(); 
      if (getRevengeTarget().getDistanceSq(this) > 128.0D && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        teleportToEntity(getRevengeTarget());
    } 
    super.onLivingUpdate();
  }
  
  protected void updateAITasks() {
    if (!EngenderConfig.mobs.useMobTalkerModels && this.andr)
      this.andr = false; 
    if (isEntityAlive() && isWet() && !this.andr) {
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
    if (flag || canDodgeAllAttacks()) {
      this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F);
      playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
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
    if (stack.isEmpty() && getRidingEntity() == null) {
      IBlockState iblockstate = getHeldBlockState();
      if (hasOwner(player) && iblockstate != null) {
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          entityDropItem(new ItemStack(iblockstate.getBlock(), 1, iblockstate.getBlock().getMetaFromState(iblockstate)), 0.0F); 
        setHeldBlockState(null);
      } else if (!isWild() && false && !isChild() && !player.isSneaking() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        player.startRiding(this);
      } 
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
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * ((EngenderConfig.mobs.useMobTalkerModels && this.andr) ? 15.0F : 1.5F));
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (EngenderConfig.mobs.useMobTalkerModels && this.andr) {
        this.jumpMovementFactor = 1.0F;
      } else {
        this.jumpMovementFactor = 0.02F;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = this.jumpPower * ((EngenderConfig.mobs.useMobTalkerModels && this.andr) ? 25.0D : 1.0D) * getFittness();
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
  
  protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
    super.dropEquipment(wasRecentlyHit, lootingModifier);
    IBlockState iblockstate = getHeldBlockState();
    if (iblockstate != null) {
      Item item = Item.getItemFromBlock(iblockstate.getBlock());
      int i = item.getHasSubtypes() ? iblockstate.getBlock().getMetaFromState(iblockstate) : 0;
      entityDropItem(new ItemStack(item, 1, i), 0.0F);
    } 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_ENDERMAN;
  }
  
  public void setHeldBlockState(IBlockState state) {
    this.getDataManager().set(CARRIED_BLOCK, Optional.fromNullable(state));
  }
  
  public IBlockState getHeldBlockState() {
    return (IBlockState)((Optional<?>)this.getDataManager().get(CARRIED_BLOCK)).orNull();
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    Entity entity = source.getTrueSource();
    if (canDodgeAllAttacks() && getEnergy() > 0.0F) {
      teleportRandomly();
      this.motionX = this.motionY = this.motionZ = 0.0D;
      setEnergy(getEnergy() - 3.0F);
      if (entity instanceof EntityLivingBase && !false)
        attackEntityAsMob(entity); 
      return false;
    } 
    if (this.andr && source instanceof net.minecraft.util.EntityDamageSourceIndirect)
      return false; 
    if (entity instanceof EntityLivingBase && EngenderConfig.mobs.useMobTalkerModels && this.andr && amount < 50.0F) {
      EntityLivingBase creature = (EntityLivingBase)entity;
      creature.attackEntityFrom(DamageSource.GENERIC.setDamageBypassesArmor().setDamageAllowedInCreativeMode().setDamageIsAbsolute(), amount);
      creature.knockBack(this, amount * 0.1F, -MathHelper.sin(creature.rotationYawHead * 0.017453292F), MathHelper.cos(creature.rotationYawHead * 0.017453292F));
    } 
    if (this.andr && (source.isFireDamage() || source.isExplosion() || source.isProjectile() || source.isMagicDamage() || amount < 50.0F))
      return false; 
    if (EngenderConfig.mobs.useMobTalkerModels && amount >= 1.0F && this.andr)
      amount *= 1.0E-4F; 
    setSitResting(false);
    if (isEntityAlive() && !this.andr && (isEntityInvulnerable(source) || source.getTrueSource() instanceof net.minecraft.entity.monster.EntityCreeper || source instanceof net.minecraft.util.EntityDamageSourceIndirect || source.isExplosion() || source.isProjectile())) {
      if (teleportRandomly())
        return true; 
      return false;
    } 
    boolean flag = super.attackEntityFrom(source, amount);
    if (isEntityAlive() && source.isUnblockable() && !this.andr)
      teleportRandomly(); 
    return flag;
  }
  
  public static void setCarriable(Block block, boolean canCarry) {
    if (canCarry) {
      carriableBlocks.add(block);
    } else {
      carriableBlocks.remove(block);
    } 
  }
  
  public static boolean getCarriable(Block block) {
    return carriableBlocks.contains(block);
  }
  
  static {
    carriableBlocks.add(Blocks.GRASS);
    carriableBlocks.add(Blocks.DIRT);
    carriableBlocks.add(Blocks.SAND);
    carriableBlocks.add(Blocks.SANDSTONE);
    carriableBlocks.add(Blocks.RED_SANDSTONE);
    carriableBlocks.add(Blocks.STAINED_HARDENED_CLAY);
    carriableBlocks.add(Blocks.HARDENED_CLAY);
    carriableBlocks.add(Blocks.HAY_BLOCK);
    carriableBlocks.add(Blocks.GRAVEL);
    carriableBlocks.add(Blocks.RED_FLOWER);
    carriableBlocks.add(Blocks.YELLOW_FLOWER);
    carriableBlocks.add(Blocks.BROWN_MUSHROOM);
    carriableBlocks.add(Blocks.RED_MUSHROOM);
    carriableBlocks.add(Blocks.PLANKS);
    carriableBlocks.add(Blocks.LOG);
    carriableBlocks.add(Blocks.LOG2);
    carriableBlocks.add(Blocks.CHORUS_FLOWER);
    carriableBlocks.add(Blocks.CHORUS_PLANT);
    carriableBlocks.add(Blocks.TNT);
    carriableBlocks.add(Blocks.CACTUS);
    carriableBlocks.add(Blocks.CLAY);
    carriableBlocks.add(Blocks.PUMPKIN);
    carriableBlocks.add(Blocks.MELON_BLOCK);
    carriableBlocks.add(Blocks.BONE_BLOCK);
    carriableBlocks.add(Blocks.NETHER_WART_BLOCK);
    carriableBlocks.add(Blocks.NETHER_BRICK);
    carriableBlocks.add(Blocks.MAGMA);
    carriableBlocks.add(Blocks.MYCELIUM);
    carriableBlocks.add(Blocks.NETHERRACK);
    carriableBlocks.add(Blocks.TRAPPED_CHEST);
    carriableBlocks.add(Blocks.TRIPWIRE_HOOK);
    carriableBlocks.add(Blocks.TRIPWIRE);
    carriableBlocks.add(Blocks.CARPET);
    carriableBlocks.add(Blocks.ICE);
    carriableBlocks.add(Blocks.FROSTED_ICE);
    carriableBlocks.add(Blocks.DISPENSER);
    carriableBlocks.add(Blocks.RED_NETHER_BRICK);
    carriableBlocks.add(Blocks.GLASS);
    carriableBlocks.add(Blocks.GLASS_PANE);
    carriableBlocks.add(Blocks.GLOWSTONE);
    carriableBlocks.add(Blocks.SOUL_SAND);
    carriableBlocks.add(Blocks.LEAVES);
    carriableBlocks.add(Blocks.LEAVES2);
    carriableBlocks.add(Blocks.PACKED_ICE);
    carriableBlocks.add(Blocks.COAL_ORE);
    carriableBlocks.add(Blocks.IRON_ORE);
    carriableBlocks.add(Blocks.LAPIS_ORE);
    carriableBlocks.add(Blocks.REDSTONE_ORE);
    carriableBlocks.add(Blocks.GOLD_ORE);
    carriableBlocks.add(Blocks.DIAMOND_ORE);
    carriableBlocks.add(Blocks.EMERALD_ORE);
  }
  
  static class AIPlaceBlock extends EntityAIBase {
    private final EntityEnderman enderman;
    
    public AIPlaceBlock(EntityEnderman p_i45843_1_) {
      this.enderman = p_i45843_1_;
    }
    
    public boolean shouldExecute() {
      return (this.enderman.getHeldBlockState() != null && (!EngenderConfig.mobs.grief || this.enderman.getRNG().nextInt(2000) == 0 || this.enderman.isRiding()));
    }
    
    public void updateTask() {
      Random random = this.enderman.getRNG();
      World world = this.enderman.world;
      int i = MathHelper.floor(this.enderman.posX - 1.0D + random.nextDouble() * 2.0D);
      int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 2.0D);
      int k = MathHelper.floor(this.enderman.posZ - 1.0D + random.nextDouble() * 2.0D);
      BlockPos blockpos = new BlockPos(i, j, k);
      IBlockState iblockstate = world.getBlockState(blockpos);
      IBlockState iblockstate1 = world.getBlockState(blockpos.down());
      IBlockState iblockstate2 = this.enderman.getHeldBlockState();
      if (iblockstate2 != null && canPlaceBlock(world, blockpos, iblockstate2.getBlock(), iblockstate, iblockstate1)) {
        this.enderman.world.playEvent(2001, blockpos, Block.getIdFromBlock(iblockstate2.getBlock()));
        world.setBlockState(blockpos, iblockstate2, 3);
        this.enderman.setHeldBlockState(null);
      } 
    }
    
    private boolean canPlaceBlock(World p_188518_1_, BlockPos p_188518_2_, Block p_188518_3_, IBlockState p_188518_4_, IBlockState p_188518_5_) {
      return (p_188518_5_.getMaterial() == Material.AIR) ? false : ((p_188518_4_.getMaterial() != Material.AIR) ? false : (!p_188518_3_.canPlaceBlockAt(p_188518_1_, p_188518_2_) ? false : p_188518_5_.isFullCube()));
    }
  }
  
  static class AITakeBlock extends EntityAIBase {
    private final EntityEnderman enderman;
    
    public AITakeBlock(EntityEnderman p_i45841_1_) {
      this.enderman = p_i45841_1_;
    }
    
    public boolean shouldExecute() {
      return (this.enderman.getHeldBlockState() == null && !this.enderman.isBeingRidden() && (EngenderConfig.mobs.grief || (EngenderConfig.mobs.useMobTalkerModels && this.enderman.isInvisible())));
    }
    
    public void updateTask() {
      Random random = this.enderman.getRNG();
      World world = this.enderman.world;
      int i = MathHelper.floor(this.enderman.posX - 3.0D + random.nextDouble() * 6.0D);
      int j = MathHelper.floor(this.enderman.posY - 1.0D + random.nextDouble() * 4.0D);
      int k = MathHelper.floor(this.enderman.posZ - 3.0D + random.nextDouble() * 6.0D);
      BlockPos blockpos = new BlockPos(i, j, k);
      IBlockState iblockstate = world.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      RayTraceResult raytraceresult = world.rayTraceBlocks(new Vec3d((MathHelper.floor(this.enderman.posX) + 0.5F), (j + 0.5F), (MathHelper.floor(this.enderman.posZ) + 0.5F)), new Vec3d((i + 0.5F), (j + 0.5F), (k + 0.5F)), false, true, false);
      boolean flag = (raytraceresult != null && raytraceresult.getBlockPos().equals(blockpos));
      if (EntityEnderman.carriableBlocks.contains(block) && flag) {
        this.enderman.swingArm(EnumHand.MAIN_HAND);
        this.enderman.swingArm(EnumHand.OFF_HAND);
        this.enderman.getLookHelper().setLookPosition(i, j, k, 180.0F, 40.0F);
        this.enderman.world.playEvent(1021, blockpos, 0);
        this.enderman.world.playEvent(2001, blockpos, Block.getIdFromBlock(block));
        this.enderman.setHeldBlockState(iblockstate);
        world.setBlockToAir(blockpos);
      } 
    }
  }
}


