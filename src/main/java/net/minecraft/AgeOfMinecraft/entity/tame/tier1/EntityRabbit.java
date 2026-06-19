package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Tiny;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtTarget;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRabbit extends EntityTameBase implements Light, Tiny, Animal {
  private static final DataParameter<Integer> RABBIT_TYPE = EntityDataManager.createKey(EntityRabbit.class, DataSerializers.VARINT);
  
  private int jumpTicks = 0;
  
  private int jumpDuration = 0;
  
  private boolean wasOnGround = false;
  
  private int currentMoveTypeDuration = 0;
  
  private int carrotTicks = 0;
  
  public EntityRabbit(World worldIn) {
    super(worldIn);
    setSize(0.4F, 0.5F);
    this.jumpHelper = new RabbitJumpHelper(this);
    this.moveHelper = new RabbitMoveHelper(this);
    this.tasks.addTask(5, (EntityAIBase)new AIRaidFarm(this));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D, 80));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.25D, 32.0F, 8.0F));
    setMovementSpeed(0.0D);
  }
  
  public String getDescName() {
    return (getRabbitType() == 99) ? "rabbit_murder" : "rabbit";
  }
  
  public int getNextLevelRequirement() {
    return (getRabbitType() == 99) ? 100 : 5;
  }
  
  public float getBonusVSArmored() {
    return 2.0F;
  }
  
  public float getBonusVSMassive() {
    return 0.5F;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityRabbit(this.world);
  }
  
  public boolean canBeButchered() {
    return (getRabbitType() != 99);
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock) ? 10.0F : (this.world.getLightBrightness(pos) - 0.5F);
  }
  
  protected float getJumpUpwardsMotion() {
    if (!this.collidedHorizontally && (!this.moveHelper.isUpdating() || this.moveHelper.getY() <= this.posY + 0.5D)) {
      Path pathentity = this.navigator.getPath();
      if (pathentity != null && pathentity.getCurrentPathIndex() < pathentity.getCurrentPathLength()) {
        Vec3d vec3d = pathentity.getPosition((Entity)this);
        if (vec3d.y > this.posY)
          return 0.5F; 
      } 
      return (this.moveHelper.getSpeed() <= 0.6D) ? 0.2F : 0.3F;
    } 
    return 0.5F;
  }
  
  public EnumTier getTier() {
    return (getRabbitType() == 99) ? EnumTier.TIER4 : EnumTier.TIER1;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(RABBIT_TYPE, 0);
  }
  
  public void updateAITasks() {
    super.updateAITasks();
    if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getRabbitType() == 99 && !isChild() && !false && getDistanceSq((Entity)getAttackTarget()) < (this.width * this.width + (getAttackTarget()).width * (getAttackTarget()).width) + 4.0D && (this.ticksExisted + getEntityId()) % 10 == 0)
      attackEntityAsMob((Entity)getAttackTarget()); 
    if (this.currentMoveTypeDuration > 0)
      this.currentMoveTypeDuration--; 
    if (this.carrotTicks > 0) {
      this.carrotTicks -= this.rand.nextInt(3);
      if (this.carrotTicks < 0)
        this.carrotTicks = 0; 
    } 
    if (this.onGround) {
      if (!this.wasOnGround) {
        setJumping(false);
        checkLandingDelay();
      } 
      if (getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
        EntityLivingBase entitylivingbase = getAttackTarget();
        if (entitylivingbase != null && getDistanceSq((Entity)entitylivingbase) < 16.0D) {
          calculateRotationYaw(entitylivingbase.posX, entitylivingbase.posZ);
          this.moveHelper.setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, this.moveHelper.getSpeed());
          startJumping();
          this.wasOnGround = true;
        } 
      } 
      RabbitJumpHelper entityrabbit$rabbitjumphelper = (RabbitJumpHelper)this.jumpHelper;
      if (!entityrabbit$rabbitjumphelper.getIsJumping()) {
        if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
          Path pathentity = this.navigator.getPath();
          Vec3d vec3d = new Vec3d(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
          if (pathentity != null && pathentity.getCurrentPathIndex() < pathentity.getCurrentPathLength())
            vec3d = pathentity.getPosition((Entity)this); 
          calculateRotationYaw(vec3d.x, vec3d.z);
          startJumping();
        } 
      } else if (!entityrabbit$rabbitjumphelper.canJump()) {
        enableJumpControl();
      } 
    } 
    this.wasOnGround = this.onGround;
  }
  
  public void startJumping() {
    setJumping(true);
    this.jumpDuration = 10;
    this.jumpTicks = 0;
  }
  
  public void spawnRunningParticles() {}
  
  private void calculateRotationYaw(double x, double z) {
    this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * 57.29577951308232D) - 90.0F;
  }
  
  private void enableJumpControl() {
    ((RabbitJumpHelper)this.jumpHelper).setCanJump(true);
  }
  
  private void disableJumpControl() {
    ((RabbitJumpHelper)this.jumpHelper).setCanJump(false);
  }
  
  private void updateMoveTypeDuration() {
    if (this.moveHelper.getSpeed() < 2.2D) {
      this.currentMoveTypeDuration = 10;
    } else {
      this.currentMoveTypeDuration = 1;
    } 
  }
  
  private void checkLandingDelay() {
    updateMoveTypeDuration();
    disableJumpControl();
  }
  
  public void onLivingUpdate() {
    ItemStack charge = (getRabbitType() == 99) ? new ItemStack(Items.SKULL) : ItemStack.EMPTY;
    charge.setStackDisplayName("Killer Rabbit");
    this.basicInventory.setInventorySlotContents(7, charge);
    super.onLivingUpdate();
    this.isOffensive = (getRabbitType() == 99);
    if (this.jumpTicks != this.jumpDuration) {
      if (this.jumpTicks == 0 && !this.world.isRemote)
        this.world.setEntityState((Entity)this, (byte)1); 
      this.jumpTicks++;
    } else if (this.jumpDuration != 0) {
      this.jumpTicks = 0;
      this.jumpDuration = 0;
    } 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("RabbitType", getRabbitType());
    tagCompound.setInteger("MoreCarrotTicks", this.carrotTicks);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setRabbitType(tagCompund.getInteger("RabbitType"));
    this.carrotTicks = tagCompund.getInteger("MoreCarrotTicks");
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    if (getRabbitType() == 99) {
      entityIn.hurtResistantTime = 0;
      playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
    } 
    return super.attackEntityAsMob(entityIn);
  }
  
  protected SoundEvent getJumpSound() {
    return SoundEvents.ENTITY_RABBIT_JUMP;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_RABBIT_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_RABBIT_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_RABBIT_DEATH;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (isEntityInvulnerable(source) || (source.isMagicDamage() && getRabbitType() == 99))
      return false; 
    return super.attackEntityFrom(source, amount);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_RABBIT;
  }
  
  public int getRabbitType() {
    return (Integer) this.dataManager.get(RABBIT_TYPE);
  }
  
  public void setRabbitType(int rabbitTypeId) {
    if (rabbitTypeId == 99) {
      getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
      this.tasks.addTask(4, (EntityAIBase)new AIEvilAttack(this));
      this.targetTasks.addTask(0, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
      this.targetTasks.addTask(1, (EntityAIBase)new EntityAILeaderHurtByTarget(this));
      this.targetTasks.addTask(2, (EntityAIBase)new EntityAILeaderHurtTarget(this));
      this.isOffensive = true;
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
      getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
      this.experienceValue = 50;
      if (!hasCustomName())
        setCustomNameTag(I18n.translateToLocal("entity.KillerBunny.name")); 
    } else {
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    } 
    this.experienceValue = 1;
    this.dataManager.set(RABBIT_TYPE, rabbitTypeId);
  }
  
  protected void jump() {
    super.jump();
    double d0 = this.moveHelper.getSpeed();
    if (d0 > 0.0D) {
      double d1 = this.motionX * this.motionX + this.motionZ * this.motionZ;
      if (d1 < 0.010000000000000002D)
        moveRelative(0.0F, 0.0F, 1.0F, 0.1F); 
    } 
    if (!this.world.isRemote)
      this.world.setEntityState((Entity)this, (byte)1); 
  }
  
  @SideOnly(Side.CLIENT)
  public float getJumpCompletion(float p_175521_1_) {
    return (this.jumpDuration == 0) ? 0.0F : ((this.jumpTicks + p_175521_1_) / this.jumpDuration);
  }
  
  public void setMovementSpeed(double newSpeed) {
    getNavigator().setSpeed(newSpeed);
    this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    int i = this.rand.nextInt(6);
    boolean flag = false;
    if (livingdata instanceof RabbitTypeData) {
      i = ((RabbitTypeData)livingdata).typeData;
      flag = true;
    } else {
      livingdata = new RabbitTypeData(i);
    } 
    setRabbitType(i);
    if (this.rand.nextInt(2500) == 0)
      setRabbitType(99); 
    return livingdata;
  }
  
  private boolean isCarrotEaten() {
    return (this.carrotTicks == 0);
  }
  
  protected void createEatingParticles() {
    BlockCarrot blockcarrot = (BlockCarrot)Blocks.CARROTS;
    IBlockState iblockstate = blockcarrot.withAge(blockcarrot.getMaxAge());
    this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, Block.getStateId(iblockstate));
    this.carrotTicks = 40;
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 1) {
      createRunningParticles();
      this.jumpDuration = 10;
      this.jumpTicks = 0;
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void notifyDataManagerChange(DataParameter<?> key) {
    super.notifyDataManagerChange(key);
  }
  
  static class AIEvilAttack extends EntityAIFriendlyAttackMelee {
    public AIEvilAttack(EntityRabbit rabbit) {
      super(rabbit, 3.0D, true);
    }
  }
  
  static class AIPanic extends EntityAIPanic {
    private EntityRabbit theEntity;
    
    public AIPanic(EntityRabbit rabbit, double speedIn) {
      super((EntityCreature)rabbit, speedIn);
      this.theEntity = rabbit;
    }
    
    public void updateTask() {
      super.updateTask();
      this.theEntity.setMovementSpeed(this.speed);
    }
  }
  
  static class AIRaidFarm extends EntityAIMoveToBlock {
    private final EntityRabbit rabbit;
    
    private boolean wantsToRaid;
    
    private boolean canRaid = false;
    
    public AIRaidFarm(EntityRabbit rabbitIn) {
      super((EntityCreature)rabbitIn, 0.75D, 16);
      this.rabbit = rabbitIn;
    }
    
    public boolean shouldExecute() {
      if (this.runDelay <= 0) {
        if (!EngenderConfig.mobs.grief)
          return false; 
        this.canRaid = false;
        this.wantsToRaid = this.rabbit.isCarrotEaten();
        this.wantsToRaid = true;
      } 
      return super.shouldExecute();
    }
    
    public boolean shouldContinueExecuting() {
      return (this.canRaid && super.shouldContinueExecuting());
    }
    
    public void startExecuting() {
      super.startExecuting();
    }
    
    public void resetTask() {
      super.resetTask();
    }
    
    public void updateTask() {
      super.updateTask();
      this.rabbit.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, 10.0F, this.rabbit.getVerticalFaceSpeed());
      if (getIsAboveDestination()) {
        World world = this.rabbit.world;
        BlockPos blockpos = this.destinationBlock.up();
        IBlockState iblockstate = world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        if (this.canRaid && block instanceof BlockCarrot) {
          Integer integer = (Integer)iblockstate.getValue((IProperty)BlockCarrot.AGE);
          if (integer == 0) {
            world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
            world.destroyBlock(blockpos, true);
          } else {
            world.setBlockState(blockpos, iblockstate.withProperty((IProperty)BlockCarrot.AGE, integer - 1), 2);
            world.playEvent(2001, blockpos, Block.getStateId(iblockstate));
          } 
          this.rabbit.createEatingParticles();
        } 
        this.canRaid = false;
        this.runDelay = 10;
      } 
    }
    
    protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
      Block block = worldIn.getBlockState(pos).getBlock();
      if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
        pos = pos.up();
        IBlockState iblockstate = worldIn.getBlockState(pos);
        block = iblockstate.getBlock();
        if (block instanceof BlockCarrot && ((BlockCarrot)block).isMaxAge(iblockstate)) {
          this.canRaid = true;
          return true;
        } 
      } 
      return false;
    }
  }
  
  public class RabbitJumpHelper extends EntityJumpHelper {
    private EntityRabbit theEntity;
    
    private boolean canJump = false;
    
    public RabbitJumpHelper(EntityRabbit rabbit) {
      super((EntityLiving)rabbit);
      this.theEntity = rabbit;
    }
    
    public boolean getIsJumping() {
      return this.isJumping;
    }
    
    public boolean canJump() {
      return this.canJump;
    }
    
    public void setCanJump(boolean p_180066_1_) {
      this.canJump = p_180066_1_;
    }
    
    public void doJump() {
      if (this.isJumping) {
        this.theEntity.startJumping();
        this.isJumping = false;
      } 
    }
  }
  
  static class RabbitMoveHelper extends EntityMoveHelper {
    private EntityRabbit theEntity;
    
    private double nextJumpSpeed;
    
    public RabbitMoveHelper(EntityRabbit rabbit) {
      super((EntityLiving)rabbit);
      this.theEntity = rabbit;
    }
    
    public void onUpdateMoveHelper() {
      if (this.theEntity.onGround && !this.theEntity.isJumping && !((EntityRabbit.RabbitJumpHelper)this.theEntity.jumpHelper).getIsJumping()) {
        this.theEntity.setMovementSpeed(0.0D);
      } else if (isUpdating()) {
        this.theEntity.setMovementSpeed(this.nextJumpSpeed);
      } 
      super.onUpdateMoveHelper();
    }
    
    public void setMoveTo(double x, double y, double z, double speedIn) {
      if (this.theEntity.isInWater())
        speedIn = 1.5D; 
      super.setMoveTo(x, y, z, speedIn);
      if (speedIn > 0.0D)
        this.nextJumpSpeed = speedIn; 
    }
  }
  
  public static class RabbitTypeData implements IEntityLivingData {
    public int typeData;
    
    public RabbitTypeData(int type) {
      this.typeData = type;
    }
  }
}

