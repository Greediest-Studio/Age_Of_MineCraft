package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpiderPig extends EntityTameBase implements IJumpingMount {
  private static final DataParameter<Boolean> CLIMBING = EntityDataManager.createKey(EntitySpiderPig.class, DataSerializers.BOOLEAN);
  
  private int leapCooldown;
  
  private int leapTick;
  
  private boolean isLeaping;
  
  private float chargePower;
  
  private int chargingTick;
  
  private int chargeExhaustion;
  
  private boolean chargeExhausted;
  
  private final List<WebPos> webList = new ArrayList<>(12);
  
  public EntitySpiderPig(World worldIn) {
    super(worldIn);
    setSize(1.4F, 0.9F);
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 64.0F, 12.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.1D, true));
    this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
  }
  
  public String getDescName() {
    return "spiderpig";
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(CLIMBING, Boolean.valueOf(false));
  }
  
  public boolean isBesideClimbableBlock() {
    return ((Boolean)this.dataManager.get(CLIMBING)).booleanValue();
  }
  
  private void setBesideClimbableBlock(boolean climbing) {
    this.dataManager.set(CLIMBING, Boolean.valueOf(climbing));
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  public float getEyeHeight() {
    return this.height * 0.75F;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean isPotionApplicable(PotionEffect potioneffectIn) {
    return (potioneffectIn.getPotion() != MobEffects.POISON && super.isPotionApplicable(potioneffectIn));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !player.isSneaking() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return super.interact(player, hand);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void onUpdate() {
    super.onUpdate();
    setBesideClimbableBlock(this.collidedHorizontally);
    if (this.chargeExhaustion >= 120)
      this.chargeExhausted = true; 
    if (this.chargeExhaustion <= 0)
      this.chargeExhausted = false; 
    this.chargeExhaustion = Math.max(0, this.chargeExhaustion - 1);
    if (!this.world.isRemote) {
      this.targetTasks.setControlFlag(1, !isChild());
      this.leapCooldown = Math.max(0, this.leapCooldown - 1);
      if (this.leapTick > 10 && this.onGround)
        this.isLeaping = false; 
      updateWebList(false);
      updateChargeState();
      if (this.ticksExisted % 600 == 0)
        heal(1.0F); 
    } 
  }
  
  private void updateWebList(boolean onlyCheckSize) {
    if (!onlyCheckSize) {
      for (int i = 0; i < this.webList.size(); i++) {
        WebPos coord = this.webList.get(i);
        if (this.world.getBlockState(coord) != Blocks.WEB.getDefaultState()) {
          this.webList.remove(i);
          i--;
        } else {
          --coord.timeLeft;
        } 
      } 
      if (!this.webList.isEmpty()) {
        WebPos first = this.webList.get(0);
        if (first.timeLeft < 0) {
          this.webList.remove(0);
          removeWeb(first);
        } 
      } 
    } 
    while (this.webList.size() > 12) {
      WebPos first = this.webList.remove(0);
      removeWeb(first);
    } 
  }
  
  private void removeWeb(BlockPos pos) {
    if (this.world.getBlockState(pos) == Blocks.WEB.getDefaultState()) {
      this.world.playEvent(2001, pos, Block.getStateId(Blocks.WEB.getDefaultState()));
      this.world.setBlockState(pos, Blocks.AIR.getDefaultState());
    } 
  }
  
  private void updateChargeState() {
    if (this.chargingTick > 0)
      for (EntityLivingBase entityLivingBase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox(), EntitySelectors.notRiding((Entity)this))) {
        if (entityLivingBase != this && entityLivingBase != getOwner())
          attackEntityAsMob((Entity)entityLivingBase); 
      }  
    this.chargingTick = Math.max(0, this.chargingTick - 1);
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    this.isLeaping = false;
    boolean spiderType = (entityIn instanceof EntitySpider || entityIn instanceof EntitySpiderPig);
    float damage = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    if (entityIn.world.getBlockState(entityIn.getPosition()).getMaterial() == Material.WEB && !spiderType)
      damage += 4.0F; 
    boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), damage);
    if ((!isBeingRidden() || flag) && this.rand.nextInt(2) == 0 && ForgeEventFactory.getMobGriefingEvent(this.world, (Entity)this)) {
      double dx = entityIn.posX - entityIn.prevPosX;
      double dz = entityIn.posZ - entityIn.prevPosZ;
      BlockPos pos = new BlockPos((int)(entityIn.posX + dx * 0.5D), MathHelper.floor((getEntityBoundingBox()).minY), (int)(entityIn.posZ + dz * 0.5D));
      Material material = this.world.getBlockState(pos).getMaterial();
      if (!material.isSolid() && !material.isLiquid() && material != Material.WEB && !spiderType) {
        this.world.setBlockState(pos, Blocks.WEB.getDefaultState());
        this.webList.add(new WebPos(pos, isBeingRidden() ? 600 : 1200));
        updateWebList(true);
        this.motionY = Math.max(0.25D, this.motionY);
        this.fallDistance = 0.0F;
      } 
    } 
    return flag;
  }
  
  public boolean canJump() {
    return !this.chargeExhausted;
  }
  
  @SideOnly(Side.CLIENT)
  public void setJumpPower(int jumpPowerIn) {
    this.chargeExhaustion += 50 * jumpPowerIn / 100;
    this.chargePower = 1.0F * jumpPowerIn / 100.0F;
  }
  
  public void handleStartJump(int jumpPowerIn) {
    this.chargingTick = 8 * jumpPowerIn / 100;
  }
  
  public void handleStopJump() {}
  
  protected boolean isMovementBlocked() {
    return (super.isMovementBlocked() || isBeingRidden());
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden() && canBeSteered()) {
      EntityLivingBase livingentity = (EntityLivingBase)getControllingPassenger();
      this.stepHeight = 1.0F;
      this.prevRotationYaw = this.rotationYaw = this.rotationYawHead = livingentity.rotationYaw;
      this.prevRotationPitch = this.rotationPitch = livingentity.rotationPitch * 0.4F;
      setRotation(this.rotationYaw, this.rotationPitch);
      while (this.renderYawOffset > this.rotationYawHead + 180.0F)
        this.renderYawOffset -= 360.0F; 
      while (this.renderYawOffset < this.rotationYawHead - 180.0F)
        this.renderYawOffset += 360.0F; 
      if (!this.chargeExhausted && this.chargePower > 0.0F && (this.onGround || this.world.containsAnyLiquid(getEntityBoundingBox()))) {
        float pitch = this.rotationPitch;
        this.rotationPitch = 0.0F;
        this.rotationPitch = pitch;
        double power = 1.600000023841858D * this.chargePower;
        this.motionX = (getLookVec()).x * power;
        this.motionY = 0.30000001192092896D;
        this.motionZ = (getLookVec()).z * power;
        this.chargePower = 0.0F;
      } 
      this.jumpMovementFactor = getAIMoveSpeed() * 0.1F;
      if (canPassengerSteer()) {
        strafe = livingentity.moveStrafing * 0.8F;
        forward = livingentity.moveForward * 0.6F;
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
        super.travel(strafe, vertical, forward);
      } else if (livingentity instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } else {
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d1 = this.posX - this.prevPosX;
        double d0 = this.posZ - this.prevPosZ;
        float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
        if (f2 > 1.0F)
          f2 = 1.0F; 
        this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
      } 
    } else {
      this.stepHeight = 0.6F;
      this.jumpMovementFactor = 0.02F;
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    if (!this.world.isRemote && 
      isPigOrSpider(entityLivingIn)) {
      EntitySpiderPig spiderPigEntity = new EntitySpiderPig(this.world);
      entityLivingIn.setDead();
      this.world.spawnEntity((Entity)spiderPigEntity);
    } 
    super.onKillEntity(entityLivingIn);
  }
  
  public boolean isOnLadder() {
    return isBesideClimbableBlock();
  }
  
  public void setInWeb() {}
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySpiderPig(this.world);
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    if (!this.webList.isEmpty()) {
      NBTTagList nbtTagList = new NBTTagList();
      for (WebPos coord : this.webList) {
        NBTTagCompound compound1 = NBTUtil.createPosTag(coord);
        compound1.setInteger("TimeLeft", coord.timeLeft);
        nbtTagList.appendTag((NBTBase)compound1);
      } 
      compound.setTag("Webs", (NBTBase)nbtTagList);
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    NBTTagList listnbt = compound.getTagList("Webs", 10);
    for (int i = 0; i < listnbt.tagCount(); i++) {
      NBTTagCompound compound1 = listnbt.getCompoundTagAt(i);
      this.webList.add(i, new WebPos(NBTUtil.getPosFromTag(compound1), compound1.getInteger("TimeLeft")));
    } 
  }
  
  protected SoundEvent getAmbientSound() {
    return MBSoundEvents.ENTITY_SPIDER_PIG_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_SPIDER_PIG_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return MBSoundEvents.ENTITY_SPIDER_PIG_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
  }
  
  public static boolean isPigOrSpider(EntityLivingBase livingEntity) {
    return (livingEntity.getClass() == EntityPig.class || livingEntity.getClass() == EntitySpider.class);
  }
  
  protected ResourceLocation getLootTable() {
    return new ResourceLocation("mutantbeasts", "entities/spider_pig");
  }
  
  class LeapAttackGoal extends EntityAIBase {
    public boolean shouldExecute() {
      EntityLivingBase target = EntitySpiderPig.this.getAttackTarget();
      return (target != null && EntitySpiderPig.this.leapCooldown <= 0 && (EntitySpiderPig.this.onGround || EntitySpiderPig.this.isInWater()) && ((EntitySpiderPig.this.getDistanceSq((Entity)target) < 64.0D && EntitySpiderPig.this.rand.nextInt(8) == 0) || EntitySpiderPig.this.getDistanceSq((Entity)target) < 6.25D));
    }
    
    public void startExecuting() {
      EntitySpiderPig.this.isLeaping = true;
      EntitySpiderPig.this.leapCooldown = 15;
      EntityLivingBase target = EntitySpiderPig.this.getAttackTarget();
      double x = target.posX - EntitySpiderPig.this.posX;
      double y = target.posY - EntitySpiderPig.this.posY;
      double z = target.posZ - EntitySpiderPig.this.posZ;
      double d = MathHelper.sqrt(x * x + y * y + z * z);
      double scale = (2.0F + 0.2F * EntitySpiderPig.this.rand.nextFloat() * EntitySpiderPig.this.rand.nextFloat());
      EntitySpiderPig.this.motionX = x / d * scale;
      EntitySpiderPig.this.motionY = y / d * scale * 0.5D + 0.3D;
      EntitySpiderPig.this.motionZ = z / d * scale;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntitySpiderPig.this.isLeaping && EntitySpiderPig.this.leapTick < 40);
    }
    
    public void updateTask() {
      ++EntitySpiderPig.this.leapTick;
    }
    
    public void resetTask() {
      EntitySpiderPig.this.leapTick = 0;
    }
  }
  
  static class WebPos extends BlockPos {
    private int timeLeft;
    
    public WebPos(BlockPos pos, int timeLeft) {
      super((Vec3i)pos);
      this.timeLeft = timeLeft;
    }
  }
}

