package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.item.MBItems;
import chumbanotz.mutantbeasts.util.EntityUtil;
import com.google.common.base.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrowingBlock extends EntityThrowable {
  private static final DataParameter<Optional<IBlockState>> BLOCK_STATE = EntityDataManager.createKey(EntityThrowingBlock.class, DataSerializers.OPTIONAL_BLOCK_STATE);
  
  private static final DataParameter<Integer> THROWER_ENTITY_ID = EntityDataManager.createKey(EntityThrowingBlock.class, DataSerializers.VARINT);
  
  private static final DataParameter<Boolean> HELD = EntityDataManager.createKey(EntityThrowingBlock.class, DataSerializers.BOOLEAN);
  
  private UUID ownerUUID;
  
  public EntityThrowingBlock(World worldIn) {
    super(worldIn);
    setSize(1.0F, 1.0F);
  }
  
  public EntityThrowingBlock(World worldIn, EntityMutantSnowGolem mutantSnowGolem) {
    super(worldIn, mutantSnowGolem.posX, mutantSnowGolem.posY + 1.855D, mutantSnowGolem.posZ);
    this.rotationYaw = mutantSnowGolem.rotationYaw;
    setThrower((EntityLivingBase)mutantSnowGolem);
  }
  
  public EntityThrowingBlock(World world, EntityMutantEnderman enderman, int armID) {
    super(world, enderman.posX, enderman.posY + 4.7D, enderman.posZ);
    setThrower((EntityLivingBase)enderman);
    setBlockState(Block.getStateById(enderman.heldBlock[armID]));
    boolean outer = (armID <= 2);
    boolean right = (armID == 1);
    EntityLivingBase living = enderman.getAttackTarget();
    Vec3d forward = EntityUtil.getDirVector(this.rotationYaw, outer ? 2.7F : 1.4F);
    Vec3d strafe = EntityUtil.getDirVector(this.rotationYaw + (right ? 90.0F : -90.0F), outer ? 2.2F : 2.0F);
    this.posX += forward.x + strafe.x;
    this.posY += ((outer ? 2.8F : 1.1F) - 4.8F);
    this.posZ += forward.z + strafe.z;
    if (living != null) {
      shoot(living.posX - this.posX, living.posY - this.posY, living.posZ - this.posZ, 1.4F, 1.0F);
    } else {
      throwBlock((EntityLivingBase)enderman);
    } 
  }
  
  public EntityThrowingBlock(World world, EntityPlayer player, IBlockState blockState, BlockPos pos) {
    super(world, (EntityLivingBase)player);
    setThrower((EntityLivingBase)player);
    setBlockState(blockState);
    setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
    setHeld(true);
  }
  
  protected void entityInit() {
    this.dataManager.register(BLOCK_STATE, Optional.of(Blocks.GRASS.getDefaultState()));
    this.dataManager.register(THROWER_ENTITY_ID, Integer.valueOf(-1));
    this.dataManager.register(HELD, Boolean.valueOf(false));
  }
  
  public IBlockState getBlockState() {
    return (IBlockState)((Optional)this.dataManager.get(BLOCK_STATE)).or(Blocks.GRASS.getDefaultState());
  }
  
  public void setBlockState(IBlockState state) {
    this.dataManager.set(BLOCK_STATE, Optional.of(state));
  }
  
  @Nullable
  public EntityLivingBase getThrower() {
    if (this.thrower == null && this.ownerUUID != null && this.world instanceof WorldServer) {
      Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.ownerUUID);
      if (entity instanceof EntityLivingBase) {
        setThrower((EntityLivingBase)entity);
      } else {
        this.ownerUUID = null;
      } 
    } 
    return this.thrower;
  }
  
  private void setThrower(EntityLivingBase thrower) {
    this.thrower = thrower;
    this.dataManager.set(THROWER_ENTITY_ID, Integer.valueOf(thrower.getEntityId()));
  }
  
  public EntityLivingBase getThrowerByID() {
    int throwerId = ((Integer)this.dataManager.get(THROWER_ENTITY_ID)).intValue();
    if (throwerId >= 0) {
      Entity entity = this.world.getEntityByID(throwerId);
      if (entity instanceof EntityLivingBase)
        return (EntityLivingBase)entity; 
    } 
    return null;
  }
  
  public boolean isHeld() {
    return ((Boolean)this.dataManager.get(HELD)).booleanValue();
  }
  
  private void setHeld(boolean held) {
    this.dataManager.set(HELD, Boolean.valueOf(held));
  }
  
  protected float getGravityVelocity() {
    if (getThrowerByID() instanceof EntityMutantSnowGolem)
      return 0.06F; 
    if (getThrowerByID() instanceof EntityPlayer)
      return 0.04F; 
    return 0.01F;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return isEntityAlive();
  }
  
  public boolean canBePushed() {
    return (isHeld() && isEntityAlive());
  }
  
  public boolean canBeAttackedWithItem() {
    return false;
  }
  
  public float getCollisionBorderSize() {
    return 0.5F;
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 3)
      for (int i = 0; i < 60; i++) {
        double x = this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width;
        double y = this.posY + 0.5D + (this.rand.nextFloat() * this.height);
        double z = this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width;
        double motx = ((this.rand.nextFloat() - this.rand.nextFloat()) * 3.0F);
        double moty = (0.5F + this.rand.nextFloat() * 2.0F);
        double motz = ((this.rand.nextFloat() - this.rand.nextFloat()) * 3.0F);
        this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, x, y, z, motx, moty, motz, new int[] { Block.getStateId(getBlockState()) });
      }  
  }
  
  public void onUpdate() {
    this.lastTickPosX = this.posX;
    this.lastTickPosY = this.posY;
    this.lastTickPosZ = this.posZ;
    if (!this.world.isRemote)
      setFlag(6, isGlowing()); 
    onEntityUpdate();
    if (isHeld()) {
      EntityLivingBase thrower = getThrower();
      if (thrower == null) {
        thrower = getThrowerByID();
        if (thrower != null) {
          this.thrower = thrower;
        } else {
          setHeld(false);
        } 
      } else {
        Vec3d vec = thrower.getLookVec();
        double x = thrower.posX + vec.x * 1.6D - this.posX;
        double y = thrower.posY + thrower.getEyeHeight() + vec.y * 1.6D - this.posY;
        double z = thrower.posZ + vec.z * 1.6D - this.posZ;
        float offset = 0.6F;
        this.motionX = x * offset;
        this.motionY = y * offset;
        this.motionZ = z * offset;
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        if (!this.world.isRemote && thrower instanceof EntityPlayer) {
          EntityPlayer player = (EntityPlayer)thrower;
          if (player == null || !player.isEntityAlive() || player.isSpectator() || !player.canEntityBeSeen((Entity)this) || (player.getHeldItemMainhand().getItem() != MBItems.ENDERSOUL_HAND && player.getHeldItemOffhand().getItem() != MBItems.ENDERSOUL_HAND))
            setHeld(false); 
        } 
      } 
    } else {
      float f;
      RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast((Entity)this, true, (this.ticksExisted >= 25), (Entity)this.thrower);
      if (raytraceresult != null && !ForgeEventFactory.onProjectileImpact(this, raytraceresult))
        onImpact(raytraceresult); 
      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      ProjectileHelper.rotateTowardsMovement((Entity)this, 0.2F);
      if (isInWater()) {
        for (int i = 0; i < 4; i++)
          this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]); 
        f = 0.8F;
      } else {
        f = 0.99F;
      } 
      this.motionX *= f;
      this.motionY *= f;
      this.motionZ *= f;
      if (!hasNoGravity())
        this.motionY -= getGravityVelocity(); 
      setPosition(this.posX, this.posY, this.posZ);
    } 
  }
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    if (player.isSneaking())
      return false; 
    if (player.getHeldItem(hand).getItem() != MBItems.ENDERSOUL_HAND || player.getCooldownTracker().hasCooldown(MBItems.ENDERSOUL_HAND))
      return false; 
    if (isHeld()) {
      if (getThrower() == player) {
        if (!this.world.isRemote) {
          setHeld(false);
          throwBlock((EntityLivingBase)player);
        } 
        player.swingArm(hand);
        player.getHeldItem(hand).damageItem(1, (EntityLivingBase)player);
        player.getCooldownTracker().setCooldown(MBItems.ENDERSOUL_HAND, 20);
        return true;
      } 
      return false;
    } 
    setHeld(true);
    setThrower((EntityLivingBase)player);
    return true;
  }
  
  private void throwBlock(EntityLivingBase living) {
    this.rotationYaw = living.rotationYaw;
    this.rotationPitch = living.rotationPitch;
    float f = 0.4F;
    float PI = 3.1415927F;
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * PI) * MathHelper.cos(this.rotationPitch / 180.0F * PI) * f);
    this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * PI) * f);
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * PI) * MathHelper.cos(this.rotationPitch / 180.0F * PI) * f);
    shoot(this.motionX, this.motionY, this.motionZ, 1.4F, 1.0F);
  }
  
  protected void onImpact(RayTraceResult result) {
    EntityLivingBase thrower = getThrower();
    IBlockState blockState = getBlockState();
    BlockPos pos = new BlockPos((Entity)this);
    if (result.typeOfHit == RayTraceResult.Type.ENTITY && thrower != null && thrower instanceof EntityTameBase) {
      if (((EntityTameBase)thrower).getAttackTarget() != null)
        copyLocationAndAnglesFrom((Entity)((EntityTameBase)thrower).getAttackTarget()); 
      float damage = blockState.getBlock().getExplosionResistance((Entity)this);
      if (damage <= 4.0F)
        damage = 4.0F; 
      if (damage > 50.0F)
        damage = 50.0F; 
      for (EntityLiving entity : this.world.getEntitiesWithinAABB(EntityLiving.class, getEntityBoundingBox().grow(2.5D, 2.0D, 2.5D), IMob.class::isInstance)) {
        if (getDistanceSq((Entity)entity) <= 6.25D)
          ((EntityTameBase)thrower).inflictEngenderMobDamage((EntityLivingBase)entity, " was pummeled by ", (DamageSource)new EntityDamageSource("thrown", (Entity)thrower), damage + this.rand.nextInt(3)); 
      } 
      if (result.typeOfHit == RayTraceResult.Type.ENTITY && result.entityHit instanceof EntityLivingBase)
        ((EntityTameBase)thrower).inflictEngenderMobDamage((EntityLivingBase)result.entityHit, " was pummeled by ", (DamageSource)new EntityDamageSource("thrown", (Entity)thrower), damage); 
      if (!this.world.isRemote) {
        this.world.setEntityState((Entity)this, (byte)3);
        this.world.playEvent(2001, pos, Block.getStateId(blockState));
        if (blockState.getBlock() == Blocks.TNT && thrower instanceof EntityTameBase)
          EntityTameBase.createEngenderModExplosionFireless((Entity)thrower, this.posX, this.posY + 1.0D, this.posZ, 4.0F, this.world.getGameRules().getBoolean("mobGriefing")); 
        setDead();
      } 
    } else {
      if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
        if (!this.world.isRemote)
          this.world.playEvent(2001, pos, Block.getStateId(blockState)); 
      } else if (result.typeOfHit == RayTraceResult.Type.ENTITY && !this.world.isRemote && result.entityHit != thrower) {
        this.world.playEvent(2001, pos, Block.getStateId(blockState));
        result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage((Entity)this, (Entity)thrower), 4.0F);
      } 
      for (Entity entity : this.world.getEntitiesInAABBexcluding((Entity)this, getEntityBoundingBox().grow(2.0D), EntitySelectors.CAN_AI_TARGET)) {
        if (entity instanceof EntityLivingBase && entity.canBeCollidedWith() && getDistanceSq(entity) <= 4.0D && entity != thrower) {
          double x = entity.posX - this.posX;
          double z = entity.posZ - this.posZ;
          double d = Math.sqrt(x * x + z * z);
          entity.motionX = x / d * 0.6000000238418579D;
          entity.motionY = 0.20000000298023224D;
          entity.motionZ = z / d * 0.6000000238418579D;
          entity.attackEntityFrom(DamageSource.causeIndirectDamage((Entity)this, thrower), (6 + this.rand.nextInt(3)));
        } 
      } 
      if (!this.world.isRemote)
        setDead(); 
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    compound.setBoolean("Held", isHeld());
    if (getBlockState() != null)
      compound.setTag("BlockState", (NBTBase)NBTUtil.writeBlockState(new NBTTagCompound(), getBlockState())); 
    if (this.ownerUUID != null)
      compound.setUniqueId("OwnerUUID", this.ownerUUID); 
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    setHeld(compound.getBoolean("Held"));
    setBlockState(NBTUtil.readBlockState(compound.getCompoundTag("BlockState")));
    if (compound.hasUniqueId("OwnerUUID"))
      this.ownerUUID = compound.getUniqueId("OwnerUUID"); 
  }
}
