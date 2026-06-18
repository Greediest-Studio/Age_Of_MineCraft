package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICustomLeapAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCaveSpider;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpider extends EntityTameBase implements IJumpingMount, Light {
  private static final DataParameter<Boolean> SURVIVAL_TEST_SKIN = EntityDataManager.createKey(EntitySpider.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntitySpider.class, DataSerializers.BYTE);
  
  protected float jumpPower;
  
  public EntitySpider(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.45F, 1.7F);
    } else {
      setSize(1.5F, 0.78F);
    } 
    this.isOffensive = true;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAICustomLeapAttack((EntityLiving)this, 0.6F, 0.6F, 0.8F, 0.5F, 4.0D, 16.0D, 6));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.1D, true));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.experienceValue = 3;
  }
  
  public String getDescName() {
    return isSurvivalTestSkin() ? "spider_beta" : "spider";
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySpider(this.world);
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 0.75F;
  }
  
  public float getBonusVSMassive() {
    return 0.5F;
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.2F) : super.getSoundPitch();
  }
  
  protected PathNavigate getNewNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(SURVIVAL_TEST_SKIN, Boolean.valueOf(false));
    this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
  }
  
  public boolean isSurvivalTestSkin() {
    return ((Boolean)getDataManager().get(SURVIVAL_TEST_SKIN)).booleanValue();
  }
  
  public void setSurvivalTestSkin(boolean childZombie) {
    getDataManager().set(SURVIVAL_TEST_SKIN, Boolean.valueOf(childZombie));
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setSurvivalTestSkin(tagCompund.getBoolean("EasterEgg"));
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    if (((Boolean)this.dataManager.get(SURVIVAL_TEST_SKIN)).booleanValue())
      tagCompound.setBoolean("EasterEgg", true); 
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
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
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote) {
      setBesideClimbableBlock(this.collidedHorizontally);
      if (isBeingRidden() && !this.onGround && this.ticksExisted % 10 == 0) {
        playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 1.5F);
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(4.0D), Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING }));
        if (list != null && !list.isEmpty() && this.jumpPower >= 1.0F)
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity1 = list.get(i1);
            if (entity1 != null && entity1.isEntityAlive())
              attackEntityAsMob((Entity)entity1); 
          }  
      } 
    } 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SPIDER_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_SPIDER_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_SPIDER_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
    if (EngenderConfig.mobs.useMobTalkerModels)
      super.playStepSound(pos, blockIn); 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_SPIDER;
  }
  
  public boolean isOnLadder() {
    return isBesideClimbableBlock();
  }
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 2 + this.rand.nextInt(3); i++) {
        if (this instanceof EntityCaveSpider) {
          EntityCaveSpider baby = new EntityCaveSpider(this.world);
          baby.copyLocationAndAnglesFrom((Entity)this);
          baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
          baby.setGrowingAge(-24000);
          baby.setChild(true);
          baby.setIsAntiMob(isAntiMob());
          baby.setIsHero(isHero());
          baby.setOwnerId(getOwnerId());
          if (isMarried())
            for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
              baby.levelUp();  
          this.world.spawnEntity((Entity)baby);
        } else {
          EntitySpider baby = new EntitySpider(this.world);
          baby.copyLocationAndAnglesFrom((Entity)this);
          baby.setGrowingAge(-32000);
          baby.setChild(true);
          baby.setOwnerId(getOwnerId());
          baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), (IEntityLivingData)null);
          if (isMarried())
            for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
              baby.levelUp();  
          this.world.spawnEntity((Entity)baby);
        } 
      }  
  }
  
  public void setInWeb() {}
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  public boolean isPotionApplicable(PotionEffect potioneffectIn) {
    return (potioneffectIn.getPotion() == MobEffects.POISON) ? false : super.isPotionApplicable(potioneffectIn);
  }
  
  public boolean isBesideClimbableBlock() {
    return ((((Byte)this.dataManager.get(CLIMBING)).byteValue() & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean climbing) {
    byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();
    if (climbing) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.dataManager.set(CLIMBING, Byte.valueOf(b0));
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    if (!this.world.isRemote && this.world.rand.nextInt(4) == 0)
      setSurvivalTestSkin(true); 
    if (this.world.rand.nextInt(100) == 0 && getGrowingAge() >= 0) {
      EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
      entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
      entityskeleton.onInitialSpawn(difficulty, (IEntityLivingData)null);
      this.world.spawnEntity((Entity)entityskeleton);
      entityskeleton.startRiding((Entity)this);
      if (!isWild())
        entityskeleton.setOwnerId(getOwnerId()); 
    } 
    if (livingdata == null) {
      livingdata = new GroupData();
      if (this.world.rand.nextFloat() < 0.25F * difficulty.getClampedAdditionalDifficulty())
        ((GroupData)livingdata).setRandomEffect(this.world.rand); 
    } 
    if (livingdata instanceof GroupData) {
      Potion potion = ((GroupData)livingdata).effect;
      if (potion != null)
        addPotionEffect(new PotionEffect(potion, 2147483647)); 
    } 
    return livingdata;
  }
  
  public float getEyeHeight() {
    return (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCreeder) ? (this.height * 0.88F) : (EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.84F) : (this.height * 0.74F));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (player.isSneaking() && stack.isEmpty() && getRidingEntity() == null) {
      List<EntitySkeleton> list = this.world.getEntitiesWithinAABB(EntitySkeleton.class, getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (getRidingEntity() == null && list != null && !list.isEmpty() && !isBeingRidden() && hasOwner(player))
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntitySkeleton entity = list.get(i1);
          if (entity != null)
            if (false && !entity.isRiding() && !this.world.isRemote) {
              player.swingArm(EnumHand.MAIN_HAND);
              entity.startRiding((Entity)this);
              playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 1.5F);
              break;
            }  
        }  
      return true;
    } 
    if (!player.isSneaking() && stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger))
      if (passenger instanceof net.minecraft.entity.passive.EntityAmbientCreature) {
        double d8 = 1.1D;
        Vec3d vec3 = getLook(1.0F);
        double dx = vec3.x * d8;
        double dz = vec3.z * d8;
        passenger.setPosition(this.posX + dx, this.posY + 0.25D, this.posZ + dz);
      } else {
        float f2 = this.renderYawOffset * 3.1415927F / 180.0F;
        float f19 = MathHelper.sin(f2);
        float f3 = MathHelper.cos(f2);
        passenger.setPosition(this.posX + (f19 * 0.2F), this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ - (f3 * 0.2F));
      }  
  }
  
  public double getMountedYOffset() {
    return this.height * 0.725D;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (isOnLadder() || (isRiding() && getRidingEntity() instanceof EntityPlayer && ((EntityPlayer)getRidingEntity()).limbSwingAmount != 0.0F))
      this.limbSwingAmount++; 
    if (getAttackTarget() != null && (!getAttackTarget().canEntityBeSeen((Entity)this) || !this.onGround || this.posY < (getAttackTarget()).posY))
      getMoveHelper().setMoveTo((getAttackTarget()).posX, (getAttackTarget()).posY, (getAttackTarget()).posZ, 1.0D); 
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) < 1024.0D && getDistanceSq((Entity)getAttackTarget()) > 64.0D && getSpecialAttackTimer() <= 0 && this.onGround && isHero()) {
      faceEntity((Entity)getAttackTarget(), 180.0F, 30.0F);
      performSpecialAttack();
    } 
  }
  
  public void performSpecialAttack() {
    double d01 = (getAttackTarget()).posX - this.posX;
    double d11 = (getAttackTarget()).posZ - this.posZ;
    float f21 = MathHelper.sqrt(d01 * d01 + d11 * d11);
    double hor = (f21 / 16.0F) * 1.3D;
    double ver = 0.9D;
    this.motionX = d01 / f21 * hor * hor + this.motionX * hor;
    this.motionZ = d11 / f21 * hor * hor + this.motionZ * hor;
    this.motionY = ver;
    playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 1.5F);
    setSpecialAttackTimer(100);
  }
  
  public void setJumpPower(int jumpPowerIn) {
    if (isBeingRidden()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        playSound(SoundEvents.ENTITY_SPIDER_AMBIENT, 1.0F, 1.5F);
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 30) {
      setSurvivalTestSkin(true);
    } else {
      super.handleStatusUpdate(id);
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
    EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
    if (isBeingRidden() && canBeSteered() && !(entitylivingbase instanceof net.minecraft.entity.passive.EntityAmbientCreature)) {
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      this.jumpMovementFactor = 0.1F;
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
        setBesideClimbableBlock(this.collidedHorizontally);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.onGround) {
        this.motionY = 0.6D * this.jumpPower;
        if (isPotionActive(MobEffects.JUMP_BOOST))
          this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F); 
        this.isAirBorne = true;
        float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
        float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
        this.motionX = (((this instanceof EntityCaveSpider) ? -2.5F : -2.0F) * f * this.jumpPower);
        this.motionZ = (((this instanceof EntityCaveSpider) ? 2.5F : 2.0F) * f1 * this.jumpPower);
        this.jumpPower = 0.0F;
        ForgeHooks.onLivingJump((EntityLivingBase)this);
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
  
  public static class GroupData implements IEntityLivingData {
    public Potion effect;
    
    public void setRandomEffect(Random rand) {
      int i = rand.nextInt(5);
      if (i <= 1) {
        this.effect = MobEffects.SPEED;
      } else if (i <= 2) {
        this.effect = MobEffects.STRENGTH;
      } else if (i <= 3) {
        this.effect = MobEffects.REGENERATION;
      } else if (i <= 4) {
        this.effect = MobEffects.INVISIBILITY;
      } 
    }
  }
}

