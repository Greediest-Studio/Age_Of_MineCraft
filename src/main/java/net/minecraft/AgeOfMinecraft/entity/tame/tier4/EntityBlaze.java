package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlaze extends EntityTameBase implements IJumpingMount, Light, Flying, Elemental {
  private float heightOffset = 0.5F;
  
  private int heightOffsetUpdateTime;
  
  private static final DataParameter<Byte> ON_FIRE = EntityDataManager.createKey(EntityBlaze.class, DataSerializers.BYTE);
  
  protected float jumpPower;
  
  public EntityBlaze(World worldIn) {
    super(worldIn);
    setPathPriority(PathNodeType.WATER, -1.0F);
    this.isOffensive = true;
    this.isImmuneToFire = true;
    this.experienceValue = 10;
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.tasks.addTask(4, new AIFireballAttack(this));
    this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
    this.tasks.addTask(3, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.moveHelper = new EntityFlyHelper(this);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.5F, 1.95F);
    } else {
      setSize(0.6F, 1.8F);
    } 
  }
  
  public String getDescName() {
    return "blaze";
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    EntityTameBase.PathNavigateFlying pathnavigateflying = new EntityTameBase.PathNavigateFlying((EntityLiving)this, worldIn);
    pathnavigateflying.setCanOpenDoors(true);
    pathnavigateflying.setCanFloat(true);
    pathnavigateflying.setCanEnterDoors(true);
    return pathnavigateflying;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.75D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityBlaze(this.world);
  }
  
  public float getBonusVSLight() {
    return 0.8F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSFlying() {
    return 2.0F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(ON_FIRE, (byte) 0);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_BLAZE_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() - 0.1F); 
    return SoundEvents.ENTITY_BLAZE_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() - 0.1F); 
    return SoundEvents.ENTITY_BLAZE_DEATH;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }
  
  public float getBrightness() {
    return 1.0F;
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
  
  public void createChild() {
    super.createChild();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntityBlaze baby = new EntityBlaze(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-72000);
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
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(800);
  }
  
  public void onLivingUpdate() {
    if (isWet()) {
      playSound(SoundEvents.ENTITY_GENERIC_BURN, 1.0F, 1.0F);
      attackEntityFrom((new DamageSource("cooler")).setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled(), 4.0F);
      getMoveHelper().setMoveTo(this.posX, Flying.clampFlightY(this.posY + 3.0D), this.posZ, 1.0D);
    } 
    if (isHero() && getSpecialAttackTimer() > 790) {
      this.motionX = 0.0D;
      this.motionZ = 0.0D;
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty())
          for (EntityLivingBase entity : list) {
              if (entity != null)
                  if (!false) {
                      entity.setFire(60);
                      entity.hurtResistantTime = 0;
                      inflictEngenderMobDamage(entity, " was scorched to death by ", (new DamageSource("burn")).setDamageBypassesArmor().setFireDamage(), 1.0F);
                  }
          }
    } 
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 256.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (!this.onGround && this.motionY < 0.0D && isEntityAlive())
      this.motionY *= 0.6D; 
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (isHero() && getSpecialAttackTimer() > 790)
        for (int i = 0; i < 3000; i++) {
          this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 1.0D, this.posZ, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D);
          this.world.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY + 1.0D, this.posZ, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D, this.rand.nextDouble() - 0.5D);
        }  
      if (!isWet() && isEntityAlive() && getIllusionFormTime() <= 0) {
        if (this.rand.nextInt(24) == 0 && !isSilent())
          this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, getSoundCategory(), getSoundVolume(), this.rand.nextFloat() * 0.7F + 0.3F, false); 
        for (int i = 0; i < 2; i++) {
          if (isSneaking() || isChild()) {
            this.world.spawnParticle(isAntiMob() ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
          } else {
            this.world.spawnParticle(isAntiMob() ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
          } 
        } 
      } 
    } 
    super.onLivingUpdate();
  }
  
  protected void updateAITasks() {
    this.heightOffsetUpdateTime--;
    if (this.heightOffsetUpdateTime <= 0) {
      this.heightOffsetUpdateTime = 100;
      this.heightOffset = this.rand.nextFloat() * 6.0F;
    } 
    EntityLivingBase entitylivingbase = getAttackTarget();
    if (entitylivingbase != null && this.posY + getEyeHeight() - this.heightOffset < entitylivingbase.posY + entitylivingbase.getEyeHeight()) {
      this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
      this.isAirBorne = true;
    } 
    super.updateAITasks();
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected boolean canTriggerWalking() {
    return EngenderConfig.mobs.useMobTalkerModels;
  }
  
  public boolean isBurning() {
    return isCharged();
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_BLAZE;
  }
  
  public boolean isCharged() {
    return ((this.getDataManager().get(ON_FIRE) & 0x1) != 0);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty() && stack.getItem() != Items.SPAWN_EGG && (getSlotForItemStack(stack) == EntityEquipmentSlot.MAINHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || stack.getItem() == Items.BOW)) {
      playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 2.0F);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.OFFHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || (stack.getItem() instanceof net.minecraft.item.ItemFood && !(stack.getItem() instanceof net.minecraft.item.ItemAppleGold)) || stack.getItem() == Items.TIPPED_ARROW || stack.getItem() == Items.SHIELD)) {
      playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 2.0F);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD) {
      setItemStackToSlot(EntityEquipmentSlot.HEAD, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.HEAD, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST) {
      setItemStackToSlot(EntityEquipmentSlot.CHEST, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.CHEST, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS) {
      setItemStackToSlot(EntityEquipmentSlot.LEGS, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.LEGS, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.FEET) {
      setItemStackToSlot(EntityEquipmentSlot.FEET, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.FEET, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (false && stack.isEmpty() && player.isSneaking()) {
      dropEquipmentUndamaged();
      player.swingArm(hand);
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        player.startRiding(this);
      return true;
    } 
    return false;
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
    if (isBeingRidden() && canBeSteered()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch - 5.0F;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      this.jumpMovementFactor = 0.03F;
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } 
      this.prevLimbSwingAmount = this.limbSwingAmount;
      if (((EntityLivingBase)getControllingPassenger()).moveStrafing != 0.0F && this.ticksExisted % 40 == 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        this.world.playEvent(null, 1018, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
        Vec3d vec3 = getLook(1.0F);
        EntitySmallFireballOther entitysmallfireball = new EntitySmallFireballOther(this.world, this, this.posX + vec3.x * 16.0D - this.posX + vec3.x, this.posY + getEyeHeight() + vec3.y * 16.0D - this.posY + getEyeHeight() + vec3.y, this.posZ + vec3.z * 16.0D - this.posZ + vec3.z);
        entitysmallfireball.posY = this.posY + (this.height / 2.0F) + 0.5D;
        this.world.spawnEntity(entitysmallfireball);
        swingArm(EnumHand.MAIN_HAND);
        float dm = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        entitysmallfireball.damage = dm * 0.6F;
      } 
      for (int i = 0; i < 64; i++) {
        int in = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY - this.rand.nextDouble() * (4.0D + this.heightOffset));
        int k = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(in, j, k);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        if (block != Blocks.AIR)
          setPosition(this.posX, this.posY + 0.005D, this.posZ); 
      } 
      if (this.jumpPower > 0.0F) {
        this.motionY += this.jumpPower;
        if (isPotionActive(MobEffects.JUMP_BOOST))
          this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
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
      this.jumpMovementFactor = 0.02F;
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public void setOnFire(boolean onFire) {
    byte b0 = this.getDataManager().get(ON_FIRE);
    if (onFire && !isWet()) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.getDataManager().set(ON_FIRE, b0);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (amount == 0.0F && source.getDamageType() == "thrown" && source instanceof EntityDamageSourceIndirect && source.getTrueSource() instanceof net.minecraft.entity.projectile.EntitySnowball)
      amount = 3.0F; 
    return super.attackEntityFrom(source, amount);
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getRegularHurtSound() : ESound.metalHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getPierceHurtSound() : ESound.metalHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.metalHitCrush;
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.15F) : super.getSoundPitch();
  }
  
  static class AIFireballAttack extends EntityAIBase {
    private EntityBlaze blaze;
    
    private int attackStep;
    
    private int attackTime;
    
    public AIFireballAttack(EntityBlaze blazeIn) {
      this.blaze = blazeIn;
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
      return (entitylivingbase != null && entitylivingbase.isEntityAlive());
    }
    
    public void startExecuting() {
      this.attackStep = 0;
    }
    
    public void resetTask() {
      this.blaze.setOnFire(false);
    }
    
    public void updateTask() {
      this.attackTime--;
      EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
      this.blaze.getLookHelper().setLookPositionWithEntity(entitylivingbase, this.blaze.getHorizontalFaceSpeed(), this.blaze.getVerticalFaceSpeed());
      double d0 = this.blaze.getDistance(entitylivingbase);
      if (d0 < 3.0D) {
        if (this.attackTime <= 0) {
          this.attackTime = 20;
          this.blaze.attackEntityAsMob(entitylivingbase);
        } 
        this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, Flying.clampFlightY(entitylivingbase.posY), entitylivingbase.posZ, 1.0D);
      } else if (d0 < getFollowDistance()) {
        double d1 = entitylivingbase.posX - this.blaze.posX;
        double d2 = (entitylivingbase.getEntityBoundingBox()).minY + (entitylivingbase.height / 2.0F) - this.blaze.posY + (this.blaze.height / 2.0F);
        double d3 = entitylivingbase.posZ - this.blaze.posZ;
        if (this.attackTime <= 0) {
          this.attackStep++;
          if (this.attackStep == 1) {
            this.attackTime = (this.blaze.moralRaisedTimer > 0) ? 30 : 60;
            this.blaze.setOnFire(true);
          } else if (this.attackStep <= 4) {
            this.attackTime = (this.blaze.moralRaisedTimer > 0) ? 3 : 6;
          } else {
            this.attackTime = (this.blaze.moralRaisedTimer > 0) ? 50 : 100;
            this.attackStep = 0;
            this.blaze.setOnFire(false);
          } 
          if (this.attackStep > 1) {
            float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
            this.blaze.world.playEvent(null, 1018, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);
            for (int i = 0; i < 1; i++) {
              EntitySmallFireballOther entitysmallfireball = new EntitySmallFireballOther(this.blaze.world, this.blaze, d1 + (this.blaze.isHero() ? 0.0D : (this.blaze.getRNG().nextGaussian() * f)), d2, d3 + (this.blaze.isHero() ? 0.0D : (this.blaze.getRNG().nextGaussian() * f)));
              entitysmallfireball.posY = this.blaze.posY + (this.blaze.height / 2.0F) + 0.5D;
              this.blaze.world.spawnEntity(entitysmallfireball);
            } 
          } 
        } 
      } else {
        this.blaze.getNavigator().clearPath();
        this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, Flying.clampFlightY(entitylivingbase.posY), entitylivingbase.posZ, 1.0D);
      } 
      super.updateTask();
    }
    
    private double getFollowDistance() {
      IAttributeInstance iattributeinstance = this.blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
      return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
    }
  }
}

