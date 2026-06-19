package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLesserDreadbeast extends EntityTameBase implements IRangedAttackMob, Armored, Massive {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityLesserDreadbeast.class, DataSerializers.BYTE);
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 24.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  public EntityLesserDreadbeast(World par1World) {
    super(par1World);
    setSize(1.5F, 1.5F);
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.2D, 48.0F, 16.0F));
    this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.8D));
    this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
    this.tasks.addTask(5, new EntityAILookIdle(this));
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 1500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityLesserDreadbeast(this.world);
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(600.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(36.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return new PathNavigateClimber(this, worldIn);
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return flag;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(CLIMBING, (byte) 0);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote)
      setBesideClimbableBlock(this.collidedHorizontally); 
  }
  
  protected float getSoundPitch() {
    return super.getSoundPitch() - 0.5F;
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.dread_spawn_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource souce) {
    return ACSounds.dread_spawn_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.dread_spawn_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
  }
  
  public boolean isOnLadder() {
    return isBesideClimbableBlock();
  }
  
  public boolean isBesideClimbableBlock() {
    return ((this.dataManager.get(CLIMBING) & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = this.dataManager.get(CLIMBING);
    if (par1) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.dataManager.set(CLIMBING, b0);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected Item getDropItem() {
    return ACItems.dreaded_shard_of_abyssalnite;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_LESSER_DREADBEAST;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.ticksExisted == 1)
      playSound(ESound.amalgamate, 1.0F, 1.0F); 
    if (getAttackTarget() != null)
      if (getDistanceSq(getAttackTarget()) > 100.0D || getAttackTarget() instanceof net.minecraft.entity.EntityFlying || (getAttackTarget()).posY > this.posY + 4.0D) {
        this.tasks.addTask(2, this.aiArrowAttack);
        this.tasks.removeTask(this.aiAttackOnCollide);
      } else {
        this.tasks.addTask(2, this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
      }  
    if (isEntityAlive() && !this.world.isRemote && this.world.rand.nextInt(200) == 0) {
      EntityDreadSpawn spawn = new EntityDreadSpawn(this.world);
      spawn.copyLocationAndAnglesFrom(this);
      spawn.setOwnerId(getOwnerId());
      this.world.spawnEntity(spawn);
    } 
    if (isEntityAlive() && !this.world.isRemote && this.world.rand.nextInt(10000) == 0) {
      EntityGreaterDreadSpawn spawn = new EntityGreaterDreadSpawn(this.world);
      spawn.copyLocationAndAnglesFrom(this);
      spawn.setOwnerId(getOwnerId());
      this.world.spawnEntity(spawn);
    } 
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    if (!this.world.isRemote) {
      EntityGreaterDreadSpawn spawn1 = new EntityGreaterDreadSpawn(this.world);
      EntityGreaterDreadSpawn spawn2 = new EntityGreaterDreadSpawn(this.world);
      spawn1.copyLocationAndAnglesFrom(this);
      spawn2.copyLocationAndAnglesFrom(this);
      spawn1.setOwnerId(getOwnerId());
      spawn2.setOwnerId(getOwnerId());
      this.world.spawnEntity(spawn1);
      this.world.spawnEntity(spawn2);
    } 
    super.onDeath(par1DamageSource);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    EntityDreadSlugOther entitydreadslug = new EntityDreadSlugOther(this.world, this);
    double d0 = target.posX - this.posX;
    double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - entitydreadslug.posY;
    double d2 = target.posZ - this.posZ;
    float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
    entitydreadslug.shoot(d0, d1 + f1, d2, 1.6F, 1.0F);
    playSound(ESound.amalgamate, 1.0F, 1.25F);
    this.world.spawnEntity(entitydreadslug);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (isChild()) {
      if (hasOwner(player)) {
        player.swingArm(EnumHand.MAIN_HAND);
        if (getRidingEntity() == null) {
          startRiding(player, true);
        } else {
          dismountRidingEntity();
        } 
      } 
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  public double getMountedYOffset() {
    return this.height * 0.5D;
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (isInWater() || isInLava())
        this.motionY += 0.05D; 
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * ((isInWater() || isInLava()) ? 5.0F : 1.0F));
        super.travel(strafe, vertical, forward);
        setBesideClimbableBlock(this.collidedHorizontally);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (entitylivingbase.moveForward > 0.0F && this.ticksExisted % 7 == 0)
        playStepSound(new BlockPos(this), this.world.getBlockState(new BlockPos(this)).getBlock());
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

