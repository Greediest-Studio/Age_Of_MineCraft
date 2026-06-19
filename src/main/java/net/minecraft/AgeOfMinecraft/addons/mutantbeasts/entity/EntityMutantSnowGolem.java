package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantSnowGolem extends EntityTameBase implements IRangedAttackMob, IShearable, IJumpingMount, Armored {
  private static final DataParameter<Boolean> PUMPKIN_EQUIPPED = EntityDataManager.createKey(EntityMutantSnowGolem.class, DataSerializers.BOOLEAN);
  
  private boolean isThrowing;
  
  private int throwingTick;
  
  protected float jumpPower;
  
  public EntityMutantSnowGolem(World worldIn) {
    super(worldIn);
    setSize(0.9F, 2.2F);
    this.tasks.addTask(0, new SwimJumpGoal());
    this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackRanged(this, 1.2D, 30, 24.0F));
    this.tasks.addTask(4, new ThrowIceGoal());
    this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 64.0F, 12.0F));
    this.tasks.addTask(7, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.75D));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public String getDescName() {
    return "mutant_snowman";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
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
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantSnowGolem(this.world);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !player.isSneaking() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty() && stack.getItem() instanceof net.minecraft.item.ItemBlock) {
      playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1.0F, 2.0F);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    return false;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(PUMPKIN_EQUIPPED, Boolean.TRUE);
  }
  
  public boolean isPumpkinEquipped() {
    return (Boolean) this.dataManager.get(PUMPKIN_EQUIPPED);
  }
  
  private void setPumpkinEquipped(boolean pumpkinEquipped) {
    this.dataManager.set(PUMPKIN_EQUIPPED, pumpkinEquipped);
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return (PathNavigate)(new MBGroundPathNavigator((EntityLiving)this, worldIn)).setAvoidRain(true);
  }
  
  public float getEyeHeight() {
    return 2.0F;
  }
  
  protected void frostWalk(BlockPos pos) {}
  
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
      this.rotationYaw = entitylivingbase.rotationYaw;
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
  
  public void onUpdate() {
    super.onUpdate();
    float biomeTemp = this.world.getBiome(getPosition()).getTemperature(getPosition());
    if (this.isThrowing)
      this.throwingTick++; 
    if (isWet() && this.ticksExisted % 20 == 0)
      attackEntityFrom(DamageSource.DROWN, 1.0F); 
    if (biomeTemp > 1.2F && !isPotionActive(MobEffects.FIRE_RESISTANCE)) {
      if (this.rand.nextFloat() > Math.min(80.0F, getHealth()) * 0.01F)
        this.world.setEntityState((Entity)this, (byte)4); 
      if (this.ticksExisted % 60 == 0)
        attackEntityFrom(DamageSource.ON_FIRE, 1.0F); 
    } 
    if (getHealth() > 0.0F && biomeTemp < 0.5F && this.ticksExisted % 200 == 0)
      heal(1.0F); 
    if (!this.world.isRemote && ForgeEventFactory.getMobGriefingEvent(this.world, (Entity)this)) {
      int x = MathHelper.floor(this.posX);
      int y = MathHelper.floor((getEntityBoundingBox()).minY);
      int z = MathHelper.floor(this.posZ);
      for (int i = -2; i <= 2; i++) {
        for (int j = -2; j <= 2; j++) {
          if (Math.abs(i) != 2 || Math.abs(j) != 2) {
            BlockPos blockpos = new BlockPos(x + i, y, z + j);
            BlockPos blockpos1 = new BlockPos(x + i, y - 1, z + j);
            BlockPos blockpos2 = new BlockPos(x + i, y + 1, z + j);
            boolean placeSnow = (biomeTemp < 0.95F && this.world.isAirBlock(blockpos) && Blocks.SNOW_LAYER.canPlaceBlockAt(this.world, blockpos));
            boolean placeIce = (this.world.getBlockState(blockpos1).getMaterial() == Material.WATER);
            if (this.world.getBlockState(blockpos).getMaterial() == Material.WATER)
              this.world.setBlockState(blockpos, Blocks.ICE.getDefaultState()); 
            if (this.world.getBlockState(blockpos2).getMaterial() == Material.WATER)
              this.world.setBlockState(blockpos2, Blocks.ICE.getDefaultState()); 
            if (!placeSnow || (((Math.abs(i) != 2 && Math.abs(j) != 2) || this.rand.nextInt(20) == 0) && ((Math.abs(i) != 1 && Math.abs(j) != 1) || this.rand.nextInt(10) == 0)))
              if (!placeIce || (((Math.abs(i) != 2 && Math.abs(j) != 2) || this.rand.nextInt(14) == 0) && ((Math.abs(i) != 1 && Math.abs(j) != 1) || this.rand.nextInt(6) == 0))) {
                if (placeSnow)
                  this.world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState()); 
                if (placeIce)
                  this.world.setBlockState(blockpos1, Blocks.ICE.getDefaultState()); 
              }  
          }
        }
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isThrowing() {
    return this.isThrowing;
  }
  
  @SideOnly(Side.CLIENT)
  public int getThrowingTick() {
    return this.throwingTick;
  }
  
  private void setThrowing(boolean isThrowing) {
    this.isThrowing = isThrowing;
    this.throwingTick = 0;
    this.world.setEntityState((Entity)this, (byte)(isThrowing ? 1 : 0));
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 0 || id == 1) {
      this.isThrowing = (id == 1);
      this.throwingTick = 0;
    } else if (id == 4) {
      this.world.spawnParticle(EnumParticleTypes.WATER_DROP, this.posX + (this.rand.nextFloat() * this.width * 1.5F) - this.width, this.posY - 0.15D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 1.5F) - this.width, 0.0D, 0.0D, 0.0D);
    } else if (id == 5 || id == 6 || id == 7) {
      int i = 0;
      while (true) {
        if (i < ((id == 5) ? 10 : ((id == 6) ? 30 : 1))) {
          double d0 = this.rand.nextGaussian() * 0.02D;
          double d1 = this.rand.nextGaussian() * 0.02D;
          double d2 = this.rand.nextGaussian() * 0.02D;
          if (id == 7) {
            this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2);
          } else {
            this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, Block.getStateId(Blocks.SNOW_LAYER.getDefaultState()));
          } 
          i++;
          continue;
        } 
        break;
      } 
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.getImmediateSource() instanceof net.minecraft.entity.projectile.EntitySnowball) {
      if (getHealth() < getMaxHealth()) {
        heal(1.0F);
        this.world.setEntityState((Entity)this, (byte)7);
        this.world.setEntityState((Entity)this, (byte)5);
      } 
      return false;
    } 
    boolean flag = super.attackEntityFrom(source, amount);
    if (flag && amount > 0.0F)
      this.world.setEntityState((Entity)this, (byte)6); 
    return flag;
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    if (!this.isThrowing)
      setThrowing(true); 
  }
  
  public void setSwingingArms(boolean swingingArms) {}
  
  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return isPumpkinEquipped();
  }
  
  public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    setPumpkinEquipped(false);
    return new ArrayList<>();
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setBoolean("Pumpkin", isPumpkinEquipped());
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    if (compound.hasKey("Pumpkin"))
      setPumpkinEquipped(compound.getBoolean("Pumpkin")); 
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_SNOW_GOLEM_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return MBSoundEvents.ENTITY_MUTANT_SNOW_GOLEM_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.BLOCK_SNOW_STEP, 0.15F, 1.0F);
  }
  
  protected ResourceLocation getLootTable() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_snow_golem");
  }
  
  class SwimJumpGoal extends EntityAIBase {
    private int jumpTick;
    
    private boolean waterReplaced;
    
    private BlockPos prevPos;
    
    public SwimJumpGoal() {
      setMutexBits(4);
      ((MBGroundPathNavigator)EntityMutantSnowGolem.this.navigator).setCanSwim(true);
    }
    
    public boolean shouldExecute() {
      return EntityMutantSnowGolem.this.isInWater();
    }
    
    public void startExecuting() {
      this.prevPos = new BlockPos(MathHelper.floor(EntityMutantSnowGolem.this.posX), MathHelper.floor((EntityMutantSnowGolem.this.getEntityBoundingBox()).minY) - 1, MathHelper.floor(EntityMutantSnowGolem.this.posZ));
      EntityMutantSnowGolem.this.motionX = ((EntityMutantSnowGolem.this.rand.nextFloat() - EntityMutantSnowGolem.this.rand.nextFloat()) * 0.9F);
      EntityMutantSnowGolem.this.motionY = 1.5D;
      EntityMutantSnowGolem.this.motionZ = ((EntityMutantSnowGolem.this.rand.nextFloat() - EntityMutantSnowGolem.this.rand.nextFloat()) * 0.9F);
      EntityMutantSnowGolem.this.attackEntityFrom(DamageSource.DROWN, 16.0F);
      EntityMutantSnowGolem.this.world.setEntityState((Entity)EntityMutantSnowGolem.this, (byte)6);
    }
    
    public boolean shouldContinueExecuting() {
      return (this.jumpTick > 0);
    }
    
    public void updateTask() {
      this.jumpTick--;
      if (!this.waterReplaced && !EntityMutantSnowGolem.this.isInWater() && this.jumpTick < 17 && ForgeEventFactory.getMobGriefingEvent(EntityMutantSnowGolem.this.world, (Entity)EntityMutantSnowGolem.this)) {
        int i = this.prevPos.getY();
        i = getWaterSurfaceHeight(EntityMutantSnowGolem.this.world, this.prevPos);
        if (i > EntityMutantSnowGolem.this.posY)
          return; 
        for (int x = -2; x <= 2; x++) {
          for (int y = -1; y <= 1; y++) {
            for (int z = -2; z <= 2; z++) {
              if (y == 0 || (Math.abs(x) != 2 && Math.abs(z) != 2)) {
                int posX = this.prevPos.getX() + x;
                int posY = this.prevPos.getY() + y;
                int posZ = this.prevPos.getZ() + z;
                Block block = EntityMutantSnowGolem.this.world.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
                if ((block == Blocks.AIR || block == Blocks.WATER) && ((y != 0) ? ((
                  
                  Math.abs(x) == 1 || Math.abs(z) == 1) && EntityMutantSnowGolem.this.rand.nextInt(4) == 0) : ((
                  
                  Math.abs(x) == 2 || Math.abs(z) == 2) && EntityMutantSnowGolem.this.rand.nextInt(3) == 0)))
                  EntityMutantSnowGolem.this.world.setBlockState(new BlockPos(posX, posY, posZ), Blocks.ICE.getDefaultState()); 
              } 
            } 
          } 
        } 
        Block topBlock = EntityMutantSnowGolem.this.world.getBlockState(this.prevPos.up(2)).getBlock();
        if (topBlock == Blocks.AIR)
          EntityMutantSnowGolem.this.world.setBlockState(this.prevPos.up(2), Blocks.ICE.getDefaultState()); 
        this.waterReplaced = true;
      } 
    }
    
    public void resetTask() {
      this.jumpTick = 20;
      this.waterReplaced = false;
    }
    
    int getWaterSurfaceHeight(World world, BlockPos coord) {
      int y = coord.getY();
      while (true) {
        Block block = world.getBlockState(coord.up()).getBlock();
        if (block == Blocks.WATER) {
          y++;
          continue;
        } 
        break;
      } 
      return y;
    }
  }
  
  class ThrowIceGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public boolean shouldExecute() {
      this.attackTarget = EntityMutantSnowGolem.this.getAttackTarget();
      return (this.attackTarget != null && EntityMutantSnowGolem.this.isThrowing);
    }
    
    public void startExecuting() {
      EntityMutantSnowGolem.this.navigator.clearPath();
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityMutantSnowGolem.this.isThrowing && EntityMutantSnowGolem.this.throwingTick < 20);
    }
    
    public void updateTask() {
      EntityMutantSnowGolem.this.renderYawOffset = EntityMutantSnowGolem.this.rotationYaw;
      if (EntityMutantSnowGolem.this.throwingTick == 7) {
        EntityThrowingBlock block = new EntityThrowingBlock(EntityMutantSnowGolem.this.world, EntityMutantSnowGolem.this);
        block.posY++;
        double x = this.attackTarget.posX + this.attackTarget.motionX - block.posX;
        double y = this.attackTarget.posY - block.posY;
        double z = this.attackTarget.posZ + this.attackTarget.motionZ - block.posZ;
        double xz = Math.sqrt(x * x + z * z);
        block.shoot(x, y + xz * 0.5D, z, 1.2F, 1.0F);
        block.setBlockState((!EntityMutantSnowGolem.this.getHeldItemMainhand().isEmpty() && EntityMutantSnowGolem.this.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBlock) ? Block.getBlockFromItem(EntityMutantSnowGolem.this.getHeldItemMainhand().getItem()).getDefaultState() : Blocks.ICE.getDefaultState());
        EntityMutantSnowGolem.this.world.spawnEntity((Entity)block);
        EntityMutantSnowGolem.this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, EntityMutantSnowGolem.this.getSoundVolume(), EntityMutantSnowGolem.this.getSoundPitch());
      } 
    }
    
    public void resetTask() {
      EntityMutantSnowGolem.this.setThrowing(false);
    }
  }
}

