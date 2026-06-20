package net.minecraft.AgeOfMinecraft.entity.tame.cameos;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBoneAttack extends Entity {
  private static final DataParameter<Boolean> ISBLUE = EntityDataManager.createKey(EntityBoneAttack.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Integer> BONETYPE = EntityDataManager.createKey(EntityBoneAttack.class, DataSerializers.VARINT);
  
  public EntitySans shootingEntity;
  
  private int ticksAlive;
  
  public double accelerationX;
  
  public double accelerationY;
  
  public double accelerationZ;
  
  public EntityBoneAttack(World worldIn) {
    super(worldIn);
    setSize(0.325F, 0.325F);
  }
  
  protected void entityInit() {
    getDataManager().register(ISBLUE, Boolean.FALSE);
    getDataManager().register(BONETYPE, 0);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
    if (Double.isNaN(d0))
      d0 = 4.0D; 
    d0 *= 64.0D;
    return (distance < d0 * d0);
  }
  
  public EntityBoneAttack(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn);
    setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
    setPosition(x, y, z);
    double d0 = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
    this.accelerationX = accelX / d0 * 0.5D;
    this.accelerationY = accelY / d0 * 0.5D;
    this.accelerationZ = accelZ / d0 * 0.5D;
  }
  
  public EntityBoneAttack(World worldIn, EntitySans shooter, double accelX, double accelY, double accelZ) {
    super(worldIn);
    this.shootingEntity = shooter;
    setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
    setPosition(this.posX, this.posY, this.posZ);
    this.motionX = 0.0D;
    this.motionY = 0.0D;
    this.motionZ = 0.0D;
    accelX += this.rand.nextGaussian() * 0.5D;
    accelY += this.rand.nextGaussian() * 0.5D;
    accelZ += this.rand.nextGaussian() * 0.5D;
    double d0 = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
    this.accelerationX = accelX / d0 * 0.3D;
    this.accelerationY = accelY / d0 * 0.3D;
    this.accelerationZ = accelZ / d0 * 0.3D;
  }
  
  public void onUpdate() {
    switch (getBoneType()) {
      case 0:
        setSize(0.5F, 0.5F);
        break;
      default:
        setSize(0.325F, 2.0F);
        break;
    } 
    if (getBoneType() == 0)
      ProjectileHelper.rotateTowardsMovement(this, 0.9F); 
    if (this.ticksExisted > 140)
      setDead(); 
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) || ((this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this)))) {
      super.onUpdate();
      AxisAlignedBB hitbox = new AxisAlignedBB(-0.1625D, -0.1625D, -0.1625D, 0.1625D, 0.1625D, 0.1625D);
      switch (getBoneType()) {
        case 0:
          hitbox.grow(0.5D);
          break;
        default:
          hitbox = new AxisAlignedBB(-0.1625D, 0.0D, -0.1625D, 0.1625D, 2.0D, 0.1625D);
          break;
      } 
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(0.25D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (!list.isEmpty())
        for (EntityLivingBase entity1 : list) {
          if (this.shootingEntity != null && entity1 != null && entity1.isEntityAlive() && !false)
            onImpact(new RayTraceResult(entity1));
        }  
      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      float f = getMotionFactor();
      this.motionX += this.accelerationX;
      this.motionY += this.accelerationY;
      this.motionZ += this.accelerationZ;
      this.motionX *= f;
      this.motionY *= f;
      this.motionZ *= f;
      setPosition(this.posX, this.posY, this.posZ);
    } else {
      setDead();
    } 
  }
  
  protected float getMotionFactor() {
    switch (getBoneType()) {
      case 1:
        return 0.95F;
      case 2:
        return 0.975F;
      case 3:
        return 0.8F;
      case 4:
        return 0.7F;
    } 
    return 0.99F;
  }
  
  public boolean isImmuneToExplosions() {
    return true;
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getBoneType() != 4)
      if (movingObject.entityHit != null && movingObject.entityHit instanceof EntityLivingBase && (!isBlue() || movingObject.entityHit.motionX != 0.0D || movingObject.entityHit.motionY != 0.0D || movingObject.entityHit.motionZ != 0.0D))
        if (this.shootingEntity != null && this.shootingEntity instanceof net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase)
          if (movingObject.entityHit.isEntityAlive() && !false) {
            movingObject.entityHit.hurtResistantTime = 0;
            playSound(ESound.bonehit, 1.0F, 1.0F);
            this.shootingEntity.inflictEngenderMobDamage((EntityLivingBase)movingObject.entityHit, " was boned by ", (new DamageSource("sans")).setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(), 1.0F);
            this.shootingEntity.attackWithAdditionalEffects(this.shootingEntity);
            this.shootingEntity.karmicRetribution((EntityLivingBase)movingObject.entityHit, isBlue() ? 6 : 5);
          }    
  }
  
  public boolean isBlue() {
    return this.getDataManager().get(ISBLUE);
  }
  
  public void setBlue(boolean blue) {
    this.getDataManager().set(ISBLUE, blue);
  }
  
  public int getBoneType() {
    return this.getDataManager().get(BONETYPE);
  }
  
  public void setBoneType(int age) {
    this.getDataManager().set(BONETYPE, age);
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    compound.setTag("direction", newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
    compound.setTag("power", newDoubleNBTList(new double[] { this.accelerationX, this.accelerationY, this.accelerationZ }));
    compound.setInteger("life", this.ticksAlive);
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    if (compound.hasKey("power", 9)) {
      NBTTagList nbttaglist = compound.getTagList("power", 6);
      if (nbttaglist.tagCount() == 3) {
        this.accelerationX = nbttaglist.getDoubleAt(0);
        this.accelerationY = nbttaglist.getDoubleAt(1);
        this.accelerationZ = nbttaglist.getDoubleAt(2);
      } 
    } 
    this.ticksAlive = compound.getInteger("life");
    if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3) {
      NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
      this.motionX = nbttaglist1.getDoubleAt(0);
      this.motionY = nbttaglist1.getDoubleAt(1);
      this.motionZ = nbttaglist1.getDoubleAt(2);
    } 
  }
  
  public boolean canBeCollidedWith() {
    return false;
  }
  
  public float getCollisionBorderSize() {
    return 1.0F;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  public float getBrightness() {
    return 1.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }
}


