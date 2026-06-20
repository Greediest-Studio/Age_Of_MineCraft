package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMutantSkeletonArrow extends Entity {
  private static final DataParameter<Integer> TARGET_X = EntityDataManager.createKey(EntityMutantSkeletonArrow.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TARGET_Y = EntityDataManager.createKey(EntityMutantSkeletonArrow.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> TARGET_Z = EntityDataManager.createKey(EntityMutantSkeletonArrow.class, DataSerializers.VARINT);
  
  private static final DataParameter<Float> SPEED = EntityDataManager.createKey(EntityMutantSkeletonArrow.class, DataSerializers.FLOAT);
  
  private static final DataParameter<Integer> CLONES = EntityDataManager.createKey(EntityMutantSkeletonArrow.class, DataSerializers.VARINT);
  
  private int damage = 10 + this.rand.nextInt(3);
  
  private final List<Entity> pointedEntities = new ArrayList<>();
  
  private PotionEffect potionEffect;
  
  private EntityTameBase shooter;
  
  public EntityMutantSkeletonArrow(World world) {
    super(world);
    this.noClip = true;
  }
  
  public EntityMutantSkeletonArrow(World world, EntityTameBase shooter, EntityLivingBase target) {
    this(world);
    this.shooter = shooter;
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(world)) {
      setTargetX(target.posX);
      setTargetY(target.posY);
      setTargetZ(target.posZ);
    } 
    this.pointedEntities.add(target);
    double yPos = shooter.posY + shooter.getEyeHeight();
    if (shooter instanceof EntityMutantSkeleton)
      yPos -= shooter.getFittness(); 
    setPosition(shooter.posX, yPos, shooter.posZ);
    double x = getTargetX() - this.posX;
    double y = getTargetY() - this.posY;
    double z = getTargetZ() - this.posZ;
    double d = Math.sqrt(x * x + z * z);
    this.rotationYaw = 180.0F + (float)Math.toDegrees(Math.atan2(x, z));
    this.rotationPitch = (float)Math.toDegrees(Math.atan2(y, d));
  }
  
  protected void entityInit() {
    this.getDataManager().register(TARGET_X, 0);
    this.getDataManager().register(TARGET_Y, 0);
    this.getDataManager().register(TARGET_Z, 0);
    this.getDataManager().register(SPEED, 12.0F);
    this.getDataManager().register(CLONES, 10);
  }
  
  public double getTargetX() {
    return this.getDataManager().get(TARGET_X) / 10000.0D;
  }
  
  public void setTargetX(double x) {
    this.getDataManager().set(TARGET_X, (int) (x * 10000.0D));
  }
  
  public double getTargetY() {
    return this.getDataManager().get(TARGET_Y) / 10000.0D;
  }
  
  public void setTargetY(double y) {
    this.getDataManager().set(TARGET_Y, (int) (y * 10000.0D));
  }
  
  public double getTargetZ() {
    return this.getDataManager().get(TARGET_Z) / 10000.0D;
  }
  
  public void setTargetZ(double z) {
    this.getDataManager().set(TARGET_Z, (int) (z * 10000.0D));
  }
  
  public float getSpeed() {
    return this.getDataManager().get(SPEED) / 10.0F;
  }
  
  public void setSpeed(float speed) {
    this.getDataManager().set(SPEED, speed * 10.0F);
  }
  
  public int getClones() {
    return this.getDataManager().get(CLONES);
  }
  
  public void setClones(int clones) {
    this.getDataManager().set(CLONES, clones);
  }
  
  public void randomize(float scale) {
    setTargetX(getTargetX() + ((this.rand.nextFloat() - 0.5F) * scale * 2.0F));
    setTargetY(getTargetY() + ((this.rand.nextFloat() - 0.5F) * scale * 2.0F));
    setTargetZ(getTargetZ() + ((this.rand.nextFloat() - 0.5F) * scale * 2.0F));
  }
  
  public void setDamage(int damage) {
    this.damage = damage;
  }
  
  public void setPotionEffect(PotionEffect effect) {
    this.potionEffect = effect;
  }
  
  public void onUpdate() {
    super.onUpdate();
    double x = getTargetX() - this.posX;
    double y = getTargetY() - this.posY;
    double z = getTargetZ() - this.posZ;
    double d = Math.sqrt(x * x + z * z);
    this.rotationYaw = 180.0F + (float)Math.toDegrees(Math.atan2(x, z));
    if (this.rotationYaw > 360.0F)
      this.rotationYaw -= 360.0F; 
    this.rotationPitch = (float)Math.toDegrees(Math.atan2(y, d));
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (this.ticksExisted == 2)
        hitEntities(0); 
      if (this.ticksExisted == 3)
        hitEntities(32); 
      if (this.ticksExisted == 4)
        hitEntities(64); 
      if (this.ticksExisted == 5)
        handleEntities(); 
    } 
    if (this.ticksExisted > 10 || this.shooter == null)
      setDead(); 
  }
  
  protected void hitEntities(int offset) {
    double targetX = getTargetX();
    double targetY = getTargetY();
    double targetZ = getTargetZ();
    double d3 = this.posX - targetX;
    double d4 = this.posY - targetY;
    double d5 = this.posZ - targetZ;
    double dist = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
    double dx = (targetX - this.posX) / dist;
    double dy = (targetY - this.posY) / dist;
    double dz = (targetZ - this.posZ) / dist;
    for (int i = offset; i < offset + 64; i++) {
      double x = this.posX + dx * i * 0.5D;
      double y = this.posY + dy * i * 0.5D;
      double z = this.posZ + dz * i * 0.5D;
      AxisAlignedBB box = (new AxisAlignedBB(x, y, z, x, y, z)).grow(0.3D);
      this.pointedEntities.addAll(this.world.getEntitiesInAABBexcluding(this.shooter, box, Entity::canBeCollidedWith));
    } 
  }
  
  protected void handleEntities() {
    this.pointedEntities.remove(this);
    for (Entity entity : this.pointedEntities) {
      DamageSource damageSource = (new EntityDamageSourceIndirect("arrow", this, this.shooter) {
          public Vec3d getDamageLocation() {
            return null;
          }
        }).setProjectile();
      if (entity instanceof net.minecraft.entity.MultiPartEntityPart)
        damageSource.setExplosion(); 
      if (this.shooter != null && entity instanceof EntityLivingBase && !false) {
        if (entity.hurtResistantTime <= 0)
          this.world.playSound(null, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ARROW_HIT, getSoundCategory(), 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F)); 
        entity.hurtResistantTime--;
        this.shooter.inflictEngenderMobDamage((EntityLivingBase)entity, " was shot by ", damageSource, this.damage);
        if (this.potionEffect != null)
          this.shooter.inflictCustomStatusEffect(this.world.getDifficulty(), (EntityLivingBase)entity, this.potionEffect.getPotion(), this.potionEffect.getDuration(), this.potionEffect.getAmplifier()); 
      } 
    } 
    this.pointedEntities.clear();
  }
  
  public boolean isInvisible() {
    return true;
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    compound.setInteger("TicksExisted", this.ticksExisted);
    compound.setTag("Target", newDoubleNBTList(new double[] { getTargetX(), getTargetY(), getTargetZ() }));
    compound.setFloat("Speed", getSpeed());
    compound.setInteger("Clones", getClones());
    compound.setInteger("Damage", this.damage);
    if (this.potionEffect != null)
      compound.setTag("Effect", this.potionEffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    this.ticksExisted = compound.getInteger("TicksExisted");
    setSpeed(compound.getFloat("Speed"));
    setClones(compound.getInteger("Clones"));
    this.damage = compound.getInteger("Damage");
    if (compound.hasKey("Target", 9) && compound.getTagList("Target", 6).tagCount() == 3) {
      NBTTagList nbtTagList = compound.getTagList("Target", 6);
      setTargetX(nbtTagList.getDoubleAt(0));
      setTargetY(nbtTagList.getDoubleAt(1));
      setTargetZ(nbtTagList.getDoubleAt(2));
    } 
    if (compound.hasKey("Effect", 10))
      this.potionEffect = PotionEffect.readCustomPotionEffectFromNBT(compound); 
  }
}


