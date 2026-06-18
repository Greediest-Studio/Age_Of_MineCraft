package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySquid extends EntityTameBase implements Light, Animal {
  public float squidPitch;
  
  public float prevSquidPitch;
  
  public float squidYaw;
  
  public float prevSquidYaw;
  
  public float squidRotation;
  
  public float prevSquidRotation;
  
  public float tentacleAngle;
  
  public float lastTentacleAngle;
  
  private float randomMotionSpeed;
  
  private float rotationVelocity;
  
  private float rotateSpeed;
  
  private float randomMotionVecX;
  
  private float randomMotionVecY;
  
  private float randomMotionVecZ;
  
  public EntitySquid(World worldIn) {
    super(worldIn);
    setSize(0.9F, 0.9F);
    this.rand.setSeed((1 + getEntityId()));
    this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
    this.tasks.addTask(0, new AIMoveRandom(this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.experienceValue = 2;
  }
  
  public String getDescName() {
    return "Derp".equals(getName()) ? "derp_squid" : "squid";
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySquid(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public float getEyeHeight() {
    return this.height * 0.5F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected SoundEvent getAmbientSound() {
    return !isInWater() ? SoundEvents.ENTITY_SQUID_HURT : SoundEvents.ENTITY_SQUID_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_SQUID_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_SQUID_DEATH;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos).getMaterial() == Material.WATER) ? (10.0F + this.world.getLightBrightness(pos) - 0.5F) : super.getBlockPathWeight(pos);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_SQUID;
  }
  
  public boolean isInWater() {
    return (isEntityAlive() && (this.inWater || hasNoGravity() || "Derp".equals(getName()) || (this.world.getCurrentDate().get(2) + 1 == 4 && this.world.getCurrentDate().get(5) == 1)));
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (isInWater())
      this.fallDistance = 0.0F; 
    if (this.deathTime > 0)
      this.motionY = -0.6D; 
    this.prevSquidPitch = this.squidPitch;
    this.prevSquidYaw = this.squidYaw;
    this.prevSquidRotation = this.squidRotation;
    this.lastTentacleAngle = this.tentacleAngle;
    this.squidRotation += this.rotationVelocity;
    if (this.squidRotation > 6.283185307179586D)
      if (this.world.isRemote) {
        this.squidRotation = 6.2831855F;
      } else {
        this.squidRotation = (float)(this.squidRotation - 6.283185307179586D);
        if (this.rand.nextInt(10) == 0)
          this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F; 
        this.world.setEntityState((Entity)this, (byte)19);
      }  
    if (isAIDisabled()) {
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
    } else if (isInWater()) {
      if (this.squidRotation < 3.1415927F) {
        float f = this.squidRotation / 3.1415927F;
        this.tentacleAngle = MathHelper.sin(f * f * 3.1415927F) * 3.1415927F * ((getAttackTarget() != null) ? 0.5F : 0.25F) * (isChild() ? 2.0F : 1.0F);
        if (f > 0.9D) {
          this.randomMotionSpeed = 1.0F;
          this.rotateSpeed = 1.0F;
        } else {
          this.rotateSpeed *= 0.8F;
        } 
      } else {
        this.tentacleAngle = 0.0F;
        this.randomMotionSpeed *= 0.9F;
        this.rotateSpeed *= 0.99F;
      } 
      if (!this.world.isRemote) {
        this.motionX = (this.randomMotionVecX * this.randomMotionSpeed);
        this.motionY = (this.randomMotionVecY * this.randomMotionSpeed);
        this.motionZ = (this.randomMotionVecZ * this.randomMotionSpeed);
      } 
      float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.renderYawOffset += (-((float)MathHelper.atan2(this.motionX, this.motionZ)) * 57.295776F - this.renderYawOffset) * 0.1F;
      this.rotationYaw = this.renderYawOffset;
      this.squidYaw = (float)(this.squidYaw + Math.PI * this.rotateSpeed * 1.5D);
      this.squidPitch += (-((float)MathHelper.atan2(f1, this.motionY)) * 57.295776F - this.squidPitch) * 0.1F;
    } else {
      this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * 3.1415927F * 0.25F;
      if (!this.world.isRemote) {
        this.motionX = 0.0D;
        this.motionZ = 0.0D;
        if (isPotionActive(MobEffects.LEVITATION)) {
          this.motionY += 0.05D * (getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY;
        } else if (!hasNoGravity()) {
          this.motionY -= 0.08D;
        } 
        this.motionY *= 0.9800000190734863D;
      } 
      this.squidPitch = (float)(this.squidPitch + (-90.0F - this.squidPitch) * 0.02D);
    } 
  }
  
  public double getMountedYOffset() {
    return this.height * 0.7D;
  }
  
  public boolean shouldDismountInWater(Entity rider) {
    return false;
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isEntityAlive()) {
      move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
      if (!isInWater()) {
        if (this.onGround) {
          this.motionX = 0.0D;
          this.motionZ = 0.0D;
        } 
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, (getEntityBoundingBox()).minY - 1.0D, this.posZ);
        if (isPotionActive(MobEffects.LEVITATION)) {
          this.motionY += (0.05D * (getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY) * 0.2D;
        } else {
          blockpos$pooledmutableblockpos.setPos(this.posX, 0.0D, this.posZ);
          if (!this.world.isRemote || (this.world.isBlockLoaded((BlockPos)blockpos$pooledmutableblockpos) && this.world.getChunk((BlockPos)blockpos$pooledmutableblockpos).isLoaded())) {
            if (!hasNoGravity())
              this.motionY -= 0.08D; 
          } else if (this.posY > 0.0D) {
            this.motionY = -0.1D;
          } else {
            this.motionY = 0.0D;
          } 
        } 
        this.motionY *= 0.9800000190734863D;
      } 
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 19) {
      this.squidRotation = 0.0F;
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
    this.randomMotionVecX = randomMotionVecXIn;
    this.randomMotionVecY = randomMotionVecYIn;
    this.randomMotionVecZ = randomMotionVecZIn;
  }
  
  public boolean hasMovementVector() {
    return (this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F);
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean handleLavaMovement() {
    return this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this);
  }
  
  public int getTalkInterval() {
    return 120;
  }
  
  public void onEntityUpdate() {
    int i = getAir();
    super.onEntityUpdate();
    if (isEntityAlive() && !isInWater()) {
      i--;
      setAir(i);
      if (getAir() == -10) {
        setAir(0);
        attackEntityFrom(DamageSource.WITHER, 3.0F);
        if (!this.world.isRemote && this.rand.nextInt(10) == 0 && this.onGround) {
          this.motionX += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
          this.motionZ += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
          this.rotationYaw = this.rand.nextFloat() * 360.0F;
          jump();
        } 
      } 
    } else {
      setAir(300);
    } 
  }
  
  public boolean isPushedByWater() {
    return false;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  static class AIMoveRandom extends EntityAIBase {
    private final EntitySquid squid;
    
    public AIMoveRandom(EntitySquid p_i45859_1_) {
      this.squid = p_i45859_1_;
    }
    
    public boolean shouldExecute() {
      return true;
    }
    
    public void updateTask() {
      if (this.squid.isEntityAlive() && this.squid.getAttackTarget() != null && this.squid.getAttackTarget().isEntityAlive() && !this.squid.isChild() && !false && this.squid.getDistanceSq((Entity)this.squid.getAttackTarget()) < (this.squid.width * this.squid.width + (this.squid.getAttackTarget()).width * (this.squid.getAttackTarget()).width) + 4.0D && (this.squid.ticksExisted + this.squid.getEntityId()) % 20 == 0)
        this.squid.attackEntityAsMob((Entity)this.squid.getAttackTarget()); 
      int i = this.squid.getIdleTime();
      if (i > 100) {
        this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
      } else if (this.squid.isBeingRidden() && this.squid.getControllingPassenger() instanceof EntityPlayer && this.squid.isInWater() && this.squid.hasMovementVector()) {
        EntityPlayer passenger = (EntityPlayer)this.squid.getControllingPassenger();
        passenger.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 200, 0, true, true));
        passenger.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 200, 0, true, true));
        double d1 = 0.6D;
        Vec3d vec3 = passenger.getLook(1.0F);
        this.squid.setMovementVector((float)(vec3.x * d1), (float)(vec3.y * d1), (float)(vec3.z * d1));
      } else if (this.squid.getAttackTarget() != null && this.squid.isInWater() && this.squid.hasMovementVector()) {
        double d01 = (this.squid.getAttackTarget()).posX - this.squid.posX;
        double d11 = (this.squid.getAttackTarget()).posY - this.squid.posY;
        double d21 = (this.squid.getAttackTarget()).posZ - this.squid.posZ;
        float fl2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
        float f1 = (float)(d01 / fl2 * 0.2D + this.squid.motionX);
        float f2 = (float)(d11 / fl2 * 0.2D + this.squid.motionY);
        float f3 = (float)(d21 / fl2 * 0.2D + this.squid.motionZ);
        this.squid.setMovementVector(f1, f2, f3);
      } else if (this.squid.getOwner() != null && this.squid.getDistanceSq((Entity)this.squid.getOwner()) > 128.0D && this.squid.isInWater() && this.squid.hasMovementVector()) {
        double d01 = (this.squid.getOwner()).posX - this.squid.posX;
        double d11 = (this.squid.getOwner()).posY - this.squid.posY;
        double d21 = (this.squid.getOwner()).posZ - this.squid.posZ;
        float fl2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
        float f1 = (float)(d01 / fl2 * 0.2D + this.squid.motionX);
        float f2 = (float)(d11 / fl2 * 0.2D + this.squid.motionY);
        float f3 = (float)(d21 / fl2 * 0.2D + this.squid.motionZ);
        this.squid.setMovementVector(f1, f2, f3);
      } else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.isInWater() || !this.squid.hasMovementVector()) {
        float f = this.squid.getRNG().nextFloat() * 6.2831855F;
        float f1 = MathHelper.cos(f) * 0.2F;
        float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
        float f3 = MathHelper.sin(f) * 0.2F;
        this.squid.setMovementVector(f1, f2, f3);
      } 
    }
  }
}


