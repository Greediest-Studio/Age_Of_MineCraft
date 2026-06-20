package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.entity.CreeperMinionEggEntity;
import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantCreeper extends EntityTameBase implements IJumpingMount, Massive, Armored {
  private static final DataParameter<Byte> STATUS = EntityDataManager.createKey(EntityMutantCreeper.class, DataSerializers.BYTE);
  
  public static final int MAX_CHARGE_TIME = 100;
  
  public static final int MAX_DEATH_TIME = 100;
  
  private int chargeTime;
  
  private int chargeHits;
  
  private boolean canSummonLightning;
  
  private DamageSource deathCause = DamageSource.GENERIC;
  
  protected float jumpPower;
  
  public EntityMutantCreeper(World worldIn) {
    super(worldIn);
    this.experienceValue = 600;
    this.ignoreFrustumCheck = true;
    setSize(1.6F, 2.8F);
    this.reachWidth = 3.6F;
    this.tasks.addTask(1, new JumpGoal());
    this.tasks.addTask(1, new SpawnMinionsGoal());
    this.tasks.addTask(1, new ChargeAttackGoal());
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.3D, 64.0F, 12.0F));
    this.tasks.addTask(3, new EntityAIFriendlyAttackMelee(this, 1.3D, true));
    this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
    this.tasks.addTask(7, new EntityAILookIdle(this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_creeper";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(96.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
    getEntityAttribute(SWIM_SPEED).setBaseValue(4.5D);
  }
  
  public boolean isImmuneToExplosions() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantCreeper(this.world);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(STATUS, (byte) 0);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean getPowered() {
    return ((this.getDataManager().get(STATUS) & 0x1) != 0);
  }
  
  private void setPowered(boolean powered) {
    byte b0 = this.getDataManager().get(STATUS);
    this.getDataManager().set(STATUS, powered ? 1 : (byte) (b0 & 0xFFFFFFFE));
  }
  
  public boolean isJumpAttacking() {
    return ((this.getDataManager().get(STATUS) & 0x2) != 0);
  }
  
  private void setJumpAttacking(boolean jumping) {
    byte b0 = this.getDataManager().get(STATUS);
    this.getDataManager().set(STATUS, jumping ? (byte) (b0 | 0x2) : (byte) (b0 & 0xFFFFFFFD));
  }
  
  public boolean isCharging() {
    return ((this.getDataManager().get(STATUS) & 0x4) != 0);
  }
  
  public void setCharging(boolean flag) {
    byte b0 = this.getDataManager().get(STATUS);
    this.getDataManager().set(STATUS, flag ? (byte) (b0 | 0x4) : (byte) (b0 & 0xFFFFFFFB));
  }
  
  public float getEyeHeight() {
    return 2.6F;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, 0.15F, 1.5F);
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 0.5F, 1.0F);
  }
  
  public boolean attackEntityAsMob(Entity entity) {
    if (super.attackEntityAsMob(entity)) {
      if (getPowered()) {
        this.world.addWeatherEffect(new EntityLightningBolt(this.world, entity.posX - 0.5D, entity.posY + entity.height, entity.posZ - 0.5D, true));
        entity.onStruckByLightning(new EntityLightningBolt(this.world, entity.posX - 0.5D, entity.posY + entity.height, entity.posZ - 0.5D, true));
      } 
      return true;
    } 
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return new MBGroundPathNavigator(this, worldIn);
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  public void attackWithAdditionalEffects(Entity entity) {
    double amount = 1.0D;
    if (this.world.getDifficulty() == EnumDifficulty.EASY)
      amount *= 0.75D; 
    if (this.world.getDifficulty() == EnumDifficulty.HARD)
      amount *= 1.5D; 
    if (!entity.isEntityAlive() && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).prevRenderYawOffset = ((EntityLivingBase)entity).renderYawOffset = entity.prevRotationYaw = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).prevRotationYawHead = ((EntityLivingBase)entity).rotationYawHead = this.rotationYawHead;
      float xRatio = MathHelper.sin(this.rotationYawHead * 0.017453292F);
      float zRatio = -MathHelper.cos(this.rotationYawHead * 0.017453292F);
      entity.isAirBorne = true;
      float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
      entity.motionX -= xRatio / f * amount * 4.0D;
      entity.motionZ -= zRatio / f * amount * 4.0D;
      entity.motionY += amount;
      EntityUtil.sendPlayerVelocityPacket(entity);
    } 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.isEmpty() && stack.getItem() == Items.FLINT_AND_STEEL) {
      this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        if (hasOwner(player))
          if (!getPowered()) {
            if (this.world.canBlockSeeSky(getPosition())) {
              this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX - 0.5D, this.posY + 1.625D, this.posZ - 0.5D, false));
            } else {
              spawnExplosionParticle();
            } 
          } else {
            setCharging(true);
          }  
        stack.damageItem(1, player);
      } 
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !player.isSneaking() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (getPowered() && source.isFireDamage()) {
      extinguish();
      return false;
    } 
    if (source.isExplosion()) {
      float healAmount = amount / 2.0F;
      if (getHealth() < getMaxHealth()) {
        heal(healAmount);
        if (this.world instanceof WorldServer)
          for (int i = 0; i < (int)(healAmount / 2.0F); i++) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0, d0, d1, d2, 1.0D);
          }  
      } 
      return true;
    } 
    if (isCharging()) {
      if (!source.isMagicDamage() && source.getImmediateSource() instanceof EntityLivingBase)
        source.getImmediateSource().attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && amount > 0.0F && source.getImmediateSource() != null && super.attackEntityFrom(source, amount))
        this.chargeHits--; 
    } 
    return super.attackEntityFrom(source, amount);
  }
  
  public void onStruckByLightning(EntityLightningBolt lightningBolt) {
    setPowered(true);
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public void onUpdate() {
    if (isCharging()) {
      int i = this.chargeTime % 20;
      if (i == 0 || i == 20)
        playSound(MBSoundEvents.ENTITY_MUTANT_CREEPER_CHARGE, 0.6F, 0.7F + this.rand.nextFloat() * 0.6F); 
      this.chargeTime++;
    } 
    super.onUpdate();
    this.isImmuneToFire = getPowered();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (isJumpAttacking()) {
        if (this.onGround) {
          setJumpAttacking(false);
          createEngenderModExplosionFireless(this, this.posX, this.posY + 1.0D, this.posZ, getPowered() ? 10.0F : 5.0F, EngenderConfig.mobs.grief);
        } 
      } else if (isEntityAlive() && !isAIDisabled() && isEntityInsideOpaqueBlock() && this.ticksExisted % 30 == 0) {
        setJumpAttacking(true);
      }  
  }
  
  public boolean isPushedByWater() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float partialTick) {
    float f = this.deathTime / 100.0F;
    if (isCharging())
      f = (this.ticksExisted % 20 < 10) ? 0.6F : 0.0F; 
    return f * 255.0F;
  }
  
  public void onDeath(DamageSource cause) {
    setCharging(false);
    playSound(MBSoundEvents.ENTITY_MUTANT_CREEPER_DEATH, 0.9F, 1.0F);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      this.deathCause = cause; 
  }
  
  public boolean leavesNoCorpse() {
    return false;
  }
  
  protected void onDeathUpdate() {
    if (usesVanillaDeathUpdate()) {
      super.onDeathUpdate();
      return;
    }
    float explosionPower = getPowered() ? 12.0F : 8.0F;
    float f1 = explosionPower * 1.5F;
    for (Entity entity : this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().grow(f1), EntitySelectors.CAN_AI_TARGET)) {
      double x = this.posX - entity.posX;
      double y = this.posY - entity.posY;
      double z = this.posZ - entity.posZ;
      double d = Math.sqrt(x * x + y * y + z * z);
      float f2 = this.deathTime / 100.0F;
      entity.addVelocity(x / d * f2 * 0.2D, y / d * f2 * 0.09D, z / d * f2 * 0.2D);
    } 
    setPosition(this.posX + (this.rand.nextFloat() * 0.2F) - 0.10000000149011612D, this.posY, this.posZ + (this.rand.nextFloat() * 0.2F) - 0.10000000149011612D);
    if (isEntityInsideOpaqueBlock())
      pushOutOfBlocks(this.posX, ((getEntityBoundingBox()).minY + (getEntityBoundingBox()).maxY) / 2.0D, this.posZ); 
    super.onDeathUpdate();
    if (this.deathTime >= 100) {
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        createEngenderModExplosion(this, this.posX, this.posY + 1.0D, this.posZ, explosionPower, isBurning(), EngenderConfig.mobs.grief);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")) {
          int i = getExperiencePoints(this.attackingPlayer);
          i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
          while (i > 0) {
            int j = EntityXPOrb.getXPSplit(i);
            i -= j;
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + getEyeHeight(), this.posZ, j));
          } 
          this.experienceValue = 0;
        } 
        EntityUtil.spawnLingeringCloud(this);
        CreeperMinionEggEntity egg = new CreeperMinionEggEntity(this.world);
        egg.setPosition(this.posX, this.posY, this.posZ);
        this.world.spawnEntity(egg);
      } 
      super.onDeath(this.deathCause);
      setDead();
    } 
  }
  
  public boolean ableToCauseSkullDrop() {
    return getPowered();
  }
  
  protected void handleJumpWater() {
    this.motionY += 0.03999999910593033D;
  }
  
  protected void handleJumpLava() {
    handleJumpWater();
  }
  
  public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
    float f = super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
    return (getPowered() && EntityWither.canDestroyBlock(blockStateIn.getBlock()) && ForgeEventFactory.onEntityDestroyBlock(this, pos, blockStateIn)) ? Math.min(0.8F, f) : f;
  }
  
  public boolean getCanSpawnHere() {
    return (super.getCanSpawnHere() && this.world.canSeeSky(getPosition()));
  }
  
  protected SoundEvent getAmbientSound() {
    return MBSoundEvents.ENTITY_MUTANT_CREEPER_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_CREEPER_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_CREEPER_DEATH;
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setBoolean("JumpAttacking", isJumpAttacking());
    compound.setBoolean("Charging", isCharging());
    compound.setInteger("ChargeTime", this.chargeTime);
    compound.setInteger("ChargeHits", this.chargeHits);
    compound.setBoolean("SummonLightning", this.canSummonLightning);
    if (this.deathTime > 0 && this.attackingPlayer != null)
      compound.setUniqueId("KillerUUID", this.attackingPlayer.getUniqueID()); 
    for (String unusedNBT : new String[] { "powered", "Fuse", "ExplosionRadius", "ignited" })
      compound.removeTag(unusedNBT); 
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    setJumpAttacking(compound.getBoolean("JumpAttacking"));
    setCharging(compound.getBoolean("Charging"));
    this.chargeTime = compound.getInteger("ChargeTime");
    this.chargeHits = compound.getInteger("ChargeHits");
    this.canSummonLightning = compound.getBoolean("SummonLightning");
    if (compound.hasUniqueId("KillerUUID")) {
      this.recentlyHit = Integer.MAX_VALUE;
      this.attackingPlayer = this.world.getPlayerEntityByUUID(compound.getUniqueId("KillerUUID"));
      this.deathCause = DamageSource.causePlayerDamage(this.attackingPlayer);
    } 
  }
  
  protected ResourceLocation getLootTable() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_creeper");
  }
  
  class SpawnMinionsGoal extends EntityAIBase {
    public boolean shouldExecute() {
      return (EntityMutantCreeper.this.getAttackTarget() != null && EntityMutantCreeper.this.getDistanceSq(EntityMutantCreeper.this.getAttackTarget()) <= 1024.0D && EntityMutantCreeper.this.onGround && !EntityMutantCreeper.this.isCharging()) ? ((EntityMutantCreeper.this.rand.nextFloat() >= 0.99F)) : false;
    }
    
    public void startExecuting() {
      int maxSpawn = EntityMutantCreeper.this.world.getDifficulty().getId() * 2;
      for (int i = (int)Math.ceil((EntityMutantCreeper.this.getHealth() / EntityMutantCreeper.this.getMaxHealth() * maxSpawn)); i > 0; i--) {
        EntityCreeper creeper = new EntityCreeper(EntityMutantCreeper.this.world);
        creeper.ticksExisted = 30;
        double x = EntityMutantCreeper.this.posX + (EntityMutantCreeper.this.rand.nextFloat() - EntityMutantCreeper.this.rand.nextFloat());
        double y = EntityMutantCreeper.this.posY + (EntityMutantCreeper.this.rand.nextFloat() * 0.5F);
        double z = EntityMutantCreeper.this.posZ + (EntityMutantCreeper.this.rand.nextFloat() - EntityMutantCreeper.this.rand.nextFloat());
        double xx = (EntityMutantCreeper.this.getAttackTarget()).posX - EntityMutantCreeper.this.posX;
        double yy = (EntityMutantCreeper.this.getAttackTarget()).posY - EntityMutantCreeper.this.posY;
        double zz = (EntityMutantCreeper.this.getAttackTarget()).posZ - EntityMutantCreeper.this.posZ;
        creeper.motionX = xx + (EntityMutantCreeper.this.rand.nextFloat() * 0.05F);
        creeper.motionY = yy + (EntityMutantCreeper.this.rand.nextFloat() * 0.05F);
        creeper.motionZ = zz + (EntityMutantCreeper.this.rand.nextFloat() * 0.05F);
        creeper.setLocationAndAngles(x, y, z, EntityMutantCreeper.this.rotationYaw, EntityMutantCreeper.this.rotationPitch);
        creeper.setOwnerId(EntityMutantCreeper.this.getOwnerId());
        creeper.setAttackTarget(EntityMutantCreeper.this.getAttackTarget());
        creeper.setGrowingAge(-18000);
        creeper.setLimitedLife(100);
        creeper.setPowered(EntityMutantCreeper.this.getPowered());
        EntityMutantCreeper.this.world.spawnEntity(creeper);
      } 
    }
    
    public boolean shouldContinueExecuting() {
      return false;
    }
  }
  
  class ChargeAttackGoal extends EntityAIBase {
    public ChargeAttackGoal() {
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      EntityLivingBase target = EntityMutantCreeper.this.getAttackTarget();
      boolean attemptHeal = (EntityMutantCreeper.this.getMaxHealth() - EntityMutantCreeper.this.getHealth() >= EntityMutantCreeper.this.getMaxHealth() / 6.0F);
      return EntityMutantCreeper.this.isCharging() ? true : ((target != null && EntityMutantCreeper.this.onGround && attemptHeal && EntityMutantCreeper.this.getDistanceSq(target) >= 25.0D && EntityMutantCreeper.this.getDistanceSq(target) <= 1024.0D) ? ((EntityMutantCreeper.this.rand.nextFloat() * 100.0F < 0.7F)) : false);
    }
    
    public boolean shouldContinueExecuting() {
      if (EntityMutantCreeper.this.canSummonLightning && EntityMutantCreeper.this.getAttackTarget() != null && EntityMutantCreeper.this.getDistanceSq(EntityMutantCreeper.this.getAttackTarget()) < 25.0D)
        return false; 
      return (EntityMutantCreeper.this.chargeTime < 100 && EntityMutantCreeper.this.chargeHits > 0);
    }
    
    public void startExecuting() {
      EntityMutantCreeper.this.setCharging(true);
      EntityMutantCreeper.this.navigator.clearPath();
      if (EntityMutantCreeper.this.chargeHits == 0)
        EntityMutantCreeper.this.chargeHits = 3 + EntityMutantCreeper.this.rand.nextInt(3); 
      if (EntityMutantCreeper.this.rand.nextInt(EntityMutantCreeper.this.world.isThundering() ? 2 : 6) == 0 && !EntityMutantCreeper.this.getPowered())
        EntityMutantCreeper.this.canSummonLightning = true; 
    }
    
    public void updateTask() {}
    
    public void resetTask() {
      if (EntityMutantCreeper.this.canSummonLightning && EntityMutantCreeper.this.getAttackTarget() != null && EntityMutantCreeper.this.getDistanceSq(EntityMutantCreeper.this.getAttackTarget()) < 25.0D && EntityMutantCreeper.this.world.canBlockSeeSky(EntityMutantCreeper.this.getPosition())) {
        EntityMutantCreeper.this.world.spawnEntity(new EntityLightningBolt(EntityMutantCreeper.this.world, EntityMutantCreeper.this.posX, EntityMutantCreeper.this.posY, EntityMutantCreeper.this.posZ, false));
      } else if (EntityMutantCreeper.this.chargeTime >= 100) {
        EntityMutantCreeper.this.heal(EntityMutantCreeper.this.getMaxHealth() / 4.0F);
        EntityMutantCreeper.this.world.setEntityState(EntityMutantCreeper.this, (byte)6);
      } 
      EntityMutantCreeper.this.chargeTime = 0;
      EntityMutantCreeper.this.chargeHits = 4 + EntityMutantCreeper.this.rand.nextInt(3);
      EntityMutantCreeper.this.setCharging(false);
      EntityMutantCreeper.this.canSummonLightning = false;
    }
  }
  
  class JumpGoal extends EntityAIBase {
    public boolean shouldExecute() {
      EntityLivingBase target = EntityMutantCreeper.this.getAttackTarget();
      return (target != null && EntityMutantCreeper.this.getDistance(target.posX, EntityMutantCreeper.this.posY, target.posZ) >= 32.0D && EntityMutantCreeper.this.onGround && !EntityMutantCreeper.this.isCharging());
    }
    
    public void startExecuting() {
      EntityMutantCreeper.this.setJumpAttacking(true);
      double d01 = (EntityMutantCreeper.this.getAttackTarget()).posX - EntityMutantCreeper.this.posX;
      double d11 = (EntityMutantCreeper.this.getAttackTarget()).posZ - EntityMutantCreeper.this.posZ;
      float f21 = MathHelper.sqrt(d01 * d01 + d11 * d11);
      double hor = f21 / EntityMutantCreeper.this.getDistance((EntityMutantCreeper.this.getAttackTarget()).posX, EntityMutantCreeper.this.posY, (EntityMutantCreeper.this.getAttackTarget()).posZ) * 2.0D;
      double ver = 2.0D;
      EntityMutantCreeper.this.motionX = d01 / f21 * hor * hor;
      EntityMutantCreeper.this.motionZ = d11 / f21 * hor * hor;
      EntityMutantCreeper.this.motionY = ver;
      EntityMutantCreeper.this.attackEntityFrom(DamageSource.GENERIC, 1.0F);
    }
    
    public boolean shouldContinueExecuting() {
      return false;
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
        if (this.jumpPower >= 0.9F)
          setJumpAttacking(true); 
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
}

