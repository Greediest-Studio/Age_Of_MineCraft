package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
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

public class EntityGreaterDreadSpawn extends EntityTameBase implements IRangedAttackMob, Armored {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityGreaterDreadSpawn.class, DataSerializers.BYTE);
  
  private static boolean hasMerged;
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 16.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  public EntityGreaterDreadSpawn(World par1World) {
    super(par1World);
    setSize(0.95F, 0.95F);
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 48.0F, 16.0F));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGreaterDreadSpawn(this.world);
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.5F;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(24.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    } 
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
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
    return super.getSoundPitch() - 0.3F;
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
    return (((Byte) this.dataManager.get(CLIMBING) & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = (Byte) this.dataManager.get(CLIMBING);
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
    return ACLoot.ENTITY_GREATER_DREAD_SPAWN;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.ticksExisted == 1)
      playSound(ESound.amalgamate, 1.0F, 1.0F); 
    if (getAttackTarget() != null)
      if (getDistanceSq((Entity)getAttackTarget()) > 100.0D || getAttackTarget() instanceof net.minecraft.entity.EntityFlying || (getAttackTarget()).posY > this.posY + 4.0D) {
        this.tasks.addTask(2, (EntityAIBase)this.aiArrowAttack);
        this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.tasks.addTask(2, (EntityAIBase)this.aiAttackOnCollide);
        this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
      }  
    List<EntityGreaterDreadSpawn> greaterspawns = this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(1.0D));
    if (!this.world.isRemote && 
      !greaterspawns.isEmpty() && 
      greaterspawns.size() >= 5 && !hasMerged) {
      hasMerged = true;
      for (int i = 0; i < 5 && false; i++)
        this.world.removeEntity((Entity)greaterspawns.get(i)); 
      EntityLesserDreadbeast lesserdreadbeast = new EntityLesserDreadbeast(this.world);
      lesserdreadbeast.copyLocationAndAnglesFrom((Entity)this);
      lesserdreadbeast.setOwnerId(getOwnerId());
      this.world.removeEntity((Entity)this);
      this.world.spawnEntity((Entity)lesserdreadbeast);
      hasMerged = false;
    } 
    List<Entity> list = this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(8.0D));
    if (!list.isEmpty() && list.size() >= 5 && (this.ticksExisted + getEntityId()) % 20 == 0)
        for (Entity entity : list) {
            if (entity.isEntityAlive() && entity instanceof EntityGreaterDreadSpawn) {
                EntityGreaterDreadSpawn mob = (EntityGreaterDreadSpawn) entity;
                if (false)
                    getNavigator().tryMoveToEntityLiving((Entity) mob, 1.2D);
            }
        }
    if (isEntityAlive() && !this.world.isRemote && this.world.rand.nextInt(2000) == 0) {
      EntityDreadSpawn spawn = new EntityDreadSpawn(this.world);
      spawn.copyLocationAndAnglesFrom((Entity)this);
      spawn.setOwnerId(getOwnerId());
      this.world.spawnEntity((Entity)spawn);
    } 
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    if (!this.world.isRemote) {
      EntityDreadSpawn spawn1 = new EntityDreadSpawn(this.world);
      EntityDreadSpawn spawn2 = new EntityDreadSpawn(this.world);
      spawn1.copyLocationAndAnglesFrom((Entity)this);
      spawn2.copyLocationAndAnglesFrom((Entity)this);
      spawn1.setOwnerId(getOwnerId());
      spawn2.setOwnerId(getOwnerId());
      this.world.spawnEntity((Entity)spawn1);
      this.world.spawnEntity((Entity)spawn2);
    } 
    super.onDeath(par1DamageSource);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    EntityDreadSlugOther entitydreadslug = new EntityDreadSlugOther(this.world, (EntityLivingBase)this);
    double d0 = target.posX - this.posX;
    double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - entitydreadslug.posY;
    double d2 = target.posZ - this.posZ;
    float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
    entitydreadslug.shoot(d0, d1 + f1, d2, 1.6F, 4.0F);
    playSound(ESound.amalgamate, 1.0F, 1.25F);
    this.world.spawnEntity((Entity)entitydreadslug);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      float f2 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f19 = MathHelper.sin(f2);
      float f3 = MathHelper.cos(f2);
      passenger.setPosition(this.posX + (f19 * 0.2F), this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ - (f3 * 0.2F));
    } 
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
        playStepSound(new BlockPos((Entity)this), this.world.getBlockState(new BlockPos((Entity)this)).getBlock()); 
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

