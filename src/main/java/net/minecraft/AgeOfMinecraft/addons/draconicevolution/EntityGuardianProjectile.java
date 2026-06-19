package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.brandon3055.brandonscore.client.particle.BCEffectHandler;
import com.brandon3055.brandonscore.utils.FilterUtils;
import com.brandon3055.brandonscore.utils.Teleporter;
import com.brandon3055.brandonscore.utils.Utils;
import com.brandon3055.draconicevolution.client.DEParticles;
import com.brandon3055.draconicevolution.lib.DESoundHandler;
import java.util.Arrays;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGuardianProjectile extends Entity {
  protected static final DataParameter<Byte> TYPE = EntityDataManager.createKey(EntityGuardianProjectile.class, DataSerializers.BYTE);
  
  public int type = 0;
  
  public EntityLivingBase target;
  
  public Entity shooter;
  
  public float power;
  
  public boolean isChaser;
  
  private double lastTickTargetDistance = 100.0D;
  
  private float heath = 5.0F;
  
  private DamageSource damageFireball = (new DamageSource("de.GuardianFireball")).setDamageAllowedInCreativeMode().setMagicDamage().setExplosion();
  
  private DamageSource damageEnergy = (new DamageSource("de.GuardianEnergyBall")).setDamageAllowedInCreativeMode().setDamageBypassesArmor();
  
  private DamageSource damageChaos = (new DamageSource("de.GuardianChaosBall")).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute();
  
  public static final int FIREBOMB = 1;
  
  public static final int TELEPORT = 2;
  
  public static final int FIRE_CHASER = 3;
  
  public static final int ENERGY_CHASER = 4;
  
  public static final int CHAOS_CHASER = 5;
  
  public static final int MINI_CHAOS_CHASER = 6;
  
  public EntityGuardianProjectile(World world) {
    this(world, 0, null, 10.0F, null);
  }
  
  public EntityGuardianProjectile(World world, int type, EntityLivingBase target, float power, Entity shooter) {
    super(world);
    this.type = type;
    this.target = target;
    this.shooter = shooter;
    this.power = power;
    this.isChaser = (type == 3 || type == 4 || type == 5 || type == 6);
    setSize(1.0F, 1.0F);
    if (shooter != null) {
      if (!world.isRemote)
        DESoundHandler.playSoundFromServer(world, shooter.posX + 0.5D, shooter.posY + 0.5D, shooter.posZ + 0.5D, SoundEvents.ENTITY_ENDERDRAGON_SHOOT, SoundCategory.HOSTILE, 10.0F, this.rand.nextFloat() * 0.3F + 0.85F, false, 256.0D); 
      this.rotationYaw = (shooter instanceof EntityChaosGuardian) ? (shooter.rotationYaw + 180.0F) : shooter.rotationYaw;
      this.rotationPitch = shooter.rotationPitch;
      if (type == 1 || type == 2) {
        this.rotationPitch += (this.rand.nextFloat() - 0.5F) * 20.0F;
        this.rotationYaw += (this.rand.nextFloat() - 0.5F) * 20.0F;
      } 
      if (shooter instanceof EntityChaosGuardian) {
        this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.1415927F));
        this.motionY = -MathHelper.sin(this.rotationPitch / 180.0F * 3.1415927F);
      } else if (target != null) {
        double d6 = target.posX - this.posX;
        double d7 = target.posY - this.posY;
        double d8 = target.posZ - this.posZ;
        double d9 = d6 * d6 + d7 * d7 + d8 * d8;
        this.motionX = d6 / d9;
        this.motionY = d7 / d9;
        this.motionZ = d8 / d9;
      } 
      double speed = 5.0D;
      this.motionX *= speed;
      this.motionY *= speed;
      this.motionZ *= speed;
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    double d0 = getEntityBoundingBox().getAverageEdgeLength();
    if (Double.isNaN(d0))
      d0 = 1.0D; 
    d0 = d0 * 64.0D * 100.0D;
    return (distance < d0 * d0);
  }
  
  protected void entityInit() {
    if (this.type == 4 || this.type == 5 || this.type == 6 || this.world.isRemote)
      this.noClip = true; 
    this.dataManager.register(TYPE, (byte) this.type);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (this.target instanceof EntityWitherStormHead && ((EntityWitherStormHead)this.target).residentWitherStorm != null)
      this.target = ((EntityWitherStormHead)this.target).residentWitherStorm;
    if (this.target instanceof EntityWitherStormTentacle && ((EntityWitherStormTentacle)this.target).residentWitherStorm != null)
      this.target = ((EntityWitherStormTentacle)this.target).residentWitherStorm;
    if (this.target instanceof EntityWitherStormTentacleDevourer && ((EntityWitherStormTentacleDevourer)this.target).residentWitherStorm != null)
      this.target = ((EntityWitherStormTentacleDevourer)this.target).residentWitherStorm;
    if (!this.world.isRemote && this.ticksExisted == 1)
      this.dataManager.set(TYPE, (byte) this.type);
    if (this.world.isRemote) {
      if (this.type == 0)
        this.type = this.dataManager.get(TYPE);
      spawnParticle();
    } 
    if (this.shooter != null && this.shooter instanceof EntityTameBase && !((EntityTameBase)this.shooter).isWild() && ((EntityTameBase)this.shooter).getOwner().getHealth() <= ((EntityTameBase)this.shooter).getOwner().getMaxHealth() / 2.0F)
      this.target = ((EntityTameBase)this.shooter).getOwner(); 
    if (this.shooter != null && this.target != null && this.target == this.shooter && getDistance(this.target) <= 6.0D)
      setDead(); 
    if ((this.target == null || (this.target != null && !this.target.isEntityAlive())) && this.shooter != null && this.shooter instanceof EntityTameBase) {
      List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(100.0D));
      if (!entities.isEmpty()) {
        for (int i = 0; i < entities.size(); i++) {
          EntityLivingBase entity = entities.get(this.rand.nextInt(entities.size()));
          if (entity.isEntityAlive() && entity instanceof EntityLivingBase && !false)
            this.target = entity; 
        } 
      } else {
        this.target = (EntityLivingBase)this.shooter;
      } 
    } 
    if (this.target != null && !this.target.isEntityAlive())
      this.target = (this.shooter != null) ? (EntityLivingBase)this.shooter : null; 
    this.noClip = true;
    if (!this.world.isRemote)
      if (this.target != null) {
        double tDist = Utils.getDistanceAtoB(this.target.posX, this.target.posY, this.target.posZ, this.posX, this.posY, this.posZ);
        double x = (this.target.posX - this.posX) / tDist;
        double y = (this.target.posY - this.posY) / tDist;
        double z = (this.target.posZ - this.posZ) / tDist;
        double speed = (this.type == 5) ? 0.15D : 0.1D;
        this.motionX /= 1.1D;
        this.motionY /= 1.1D;
        this.motionZ /= 1.1D;
        this.motionX += x * speed;
        this.motionY += y * speed;
        this.motionZ += z * speed;
        checkTargetCondition();
      }  
    move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
  }
  
  private boolean checkTargetCondition() {
    if (this.world.isRemote)
      return false; 
    double targetDistance = Utils.getDistanceAtoB(this.posX, this.posY, this.posZ, this.target.posX, this.target.posY, this.target.posZ);
    EntityLivingBase entityLivingBase = this.target;
    boolean genericHit = (entityLivingBase != null && targetDistance < this.power);
    switch (this.type) {
      case 1:
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.collided || this.ticksExisted > 600 || this.heath <= 0.0F) {
          EntityTameBase.createEngenderModExplosion(this.shooter, this.posX, this.posY, this.posZ, 2.0F, true, EngenderConfig.mobs.grief);
          damageEntitiesInRadius(this.damageFireball, this.power, this.power * 2.0F);
          setDead();
        } 
        break;
      case 2:
        if (genericHit) {
          EntityLivingBase entityLivingBase1 = (entityLivingBase != null) ? entityLivingBase : this.target;
          int r = this.rand.nextInt();
          if (this.shooter != null && this.shooter instanceof EntityTameBase && entityLivingBase instanceof EntityLivingBase && !false)
            if (entityLivingBase1 instanceof EntityTameBase && ((EntityTameBase)entityLivingBase1).getTier() == EnumTier.TIER6) {
              (new Teleporter.TeleportLocation(this.shooter.posX + Math.cos(r) * 60.0D, this.rand.nextInt(255), this.shooter.posZ + Math.sin(r) * 60.0D, entityLivingBase1.dimension)).teleport(entityLivingBase1);
              damageEntitiesInRadius(this.damageFireball, this.power, this.power);
            } else {
              (new Teleporter.TeleportLocation(this.shooter.posX + Math.cos(r) * 600.0D, this.rand.nextInt(255), this.shooter.posZ + Math.sin(r) * 600.0D, entityLivingBase1.dimension)).teleport(entityLivingBase1);
              damageEntitiesInRadius(this.damageFireball, this.power, this.power * 2.0F);
            }  
          setDead();
        } 
        break;
      case 3:
        this.noClip = (this.ticksExisted < 60);
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < (this.power / 2.0F)) || (this.collided && this.ticksExisted > 60) || this.ticksExisted > 400 || this.heath <= 0.0F) {
          EntityTameBase.createEngenderModExplosion(this.shooter, this.posX, this.posY, this.posZ, 2.0F, true, EngenderConfig.mobs.grief);
          damageEntitiesInRadius(this.damageFireball, this.power, this.power * 2.0F);
          setDead();
        } 
        break;
      case 4:
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.ticksExisted > 800 || this.heath <= 0.0F) {
          BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 256, getEntityId(), 0, 255, 255);
          damageEntitiesInRadius(this.damageEnergy, this.power, this.power * 3.0F);
          this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
          setDead();
        } 
        break;
      case 5:
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.ticksExisted > 800 || this.heath <= 0.0F) {
          BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 256, getEntityId(), 68, 0, 0);
          damageEntitiesInRadius(this.damageChaos, this.power, this.power * 3.0F);
          this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
          List<Entity> list = this.world.getEntitiesInAABBexcluding(this.shooter, getEntityBoundingBox().grow(60.0D), FilterUtils.IS_PLAYER);
          for (int i = 3 + this.rand.nextInt(3); i > 0; i--) {
            Entity target = (!list.isEmpty()) ? list.get(this.rand.nextInt(list.size())) : null;
            if (!(target instanceof EntityLivingBase))
              target = null; 
            EntityGuardianProjectile newProjectile = new EntityGuardianProjectile(this.world, 6, (EntityLivingBase)target, this.power / 2.0F, this.shooter);
            newProjectile.motionY = 0.0D;
            int randDir = this.rand.nextInt();
            double speed = 1.0D + this.rand.nextDouble() * 5.0D;
            newProjectile.motionX = Math.sin(randDir) * speed;
            newProjectile.motionZ = Math.cos(randDir) * speed;
            newProjectile.setPosition(this.posX, this.posY, this.posZ);
            newProjectile.shooter = this.shooter;
            newProjectile.target = this.target;
            this.world.spawnEntity(newProjectile);
          } 
          setDead();
        } 
        break;
      case 6:
        if ((genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.ticksExisted > 800 || this.heath <= 0.0F) && this.ticksExisted > 5) {
          BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 256, getEntityId(), 68, 0, 0);
          damageEntitiesInRadius(this.damageChaos, this.power, this.power * 3.0F);
          this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F, false);
          setDead();
        } 
        break;
    } 
    this.lastTickTargetDistance = targetDistance;
    return false;
  }
  
  private Entity getHitEntity() {
    Vec3d vec31 = new Vec3d(this.posX, this.posY, this.posZ);
    Vec3d vec3 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    Entity entityHit = null;
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(this.motionX, this.motionY, this.motionZ).grow(1.0D, 1.0D, 1.0D));
    double d0 = 0.0D;
      for (Entity entity1 : list) {
          if (this.target != null && entity1 == this.target && (entity1 != this.shooter || this.ticksExisted >= 5)) {
              float f1 = 2.0F;
              AxisAlignedBB axisalignedbb1 = entity1.getEntityBoundingBox().grow(f1, f1, f1);
              RayTraceResult traceResult = axisalignedbb1.calculateIntercept(vec31, vec3);
              if (traceResult != null) {
                  double d1 = vec31.distanceTo(traceResult.hitVec);
                  if (d1 < d0 || d0 == 0.0D) {
                      entityHit = entity1;
                      d0 = d1;
                  }
              }
          }
      }
    return (entityHit instanceof EntityGuardianProjectile) ? null : entityHit;
  }
  
  private void damageEntitiesInRadius(DamageSource source, double radius, float damage) {
    if (this.world.isRemote)
      return; 
    List<EntityLivingBase> entities = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(radius));
    for (EntityLivingBase entityLivingBase : entities) {
      if (entityLivingBase == this.shooter)
        continue; 
      entityLivingBase.hurtResistantTime = 0;
      if (this.shooter != null && entityLivingBase.isEntityAlive() && this.shooter instanceof EntityTameBase && false) {
        playSound(DESoundHandler.shieldUp, 10.0F, this.rand.nextFloat() * 0.2F + 1.0F);
        entityLivingBase.heal(10.0F);
        entityLivingBase.removePotionEffect(MobEffects.BLINDNESS);
        entityLivingBase.removePotionEffect(MobEffects.GLOWING);
        entityLivingBase.removePotionEffect(MobEffects.HUNGER);
        entityLivingBase.removePotionEffect(MobEffects.LEVITATION);
        entityLivingBase.removePotionEffect(MobEffects.MINING_FATIGUE);
        entityLivingBase.removePotionEffect(MobEffects.NAUSEA);
        entityLivingBase.removePotionEffect(MobEffects.SLOWNESS);
        entityLivingBase.removePotionEffect(MobEffects.UNLUCK);
        entityLivingBase.removePotionEffect(MobEffects.WEAKNESS);
        entityLivingBase.removePotionEffect(MobEffects.WITHER);
        switch (this.type) {
          case 4:
            entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1000, 1));
            entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1000));
            break;
          case 5:
            entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 1000, 1));
            break;
          case 6:
            entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100));
            break;
          case 3:
            entityLivingBase.extinguish();
            entityLivingBase.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2000));
            break;
          case 2:
            (new Teleporter.TeleportLocation(this.shooter.posX + Math.cos(this.rand.nextInt()) * 200.0D, this.rand.nextInt(255), this.shooter.posZ + Math.sin(this.rand.nextInt()) * 200.0D, entityLivingBase.dimension)).teleport(entityLivingBase);
            break;
          default:
            entityLivingBase.heal(10.0F);
            break;
        } 
      } 
      if (this.shooter != null && entityLivingBase.isEntityAlive() && this.shooter instanceof EntityTameBase && !false) {
        ((EntityTameBase)this.shooter).inflictEngenderMobDamage(entityLivingBase, (source == this.damageChaos) ? " was obliterated by " : ((source == this.damageEnergy) ? " was ionized by " : " was firebombed by "), source, damage / (float)(Utils.getDistanceAtoB(entityLivingBase.posX, entityLivingBase.posY, entityLivingBase.posZ, this.posX, this.posY, this.posZ) / radius));
        if (source == this.damageChaos && !(entityLivingBase instanceof net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans))
          entityLivingBase.setHealth(entityLivingBase.getHealth() - 1.0F); 
      } 
    } 
  }
  
  public boolean attackEntityFrom(DamageSource source, float dmg) {
    if (this.heath <= 0.0F)
      return false; 
    if ((source.getTrueSource() instanceof EntityLivingBase || source.getTrueSource() instanceof net.minecraft.entity.projectile.EntityArrow) && this.ticksExisted > 5)
      this.heath -= dmg; 
    if (source.getTrueSource() instanceof net.minecraft.entity.projectile.EntityArrow)
      source.getTrueSource().setDead(); 
    if (this.heath <= 0.0F) {
      this.world.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F, false, false);
      setDead();
    } 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  private void spawnParticle() {
    int[] colour = getParticleColour();
    if (Arrays.equals(colour, new int[] { 0, 0, 0 }))
      return; 
    BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.world, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, 256.0D, getEntityId(), colour[0], colour[1], colour[2]);
  }
  
  public int[] getParticleColour() {
    switch (this.type) {
      case 1:
        return new int[] { 255, 102, 0 };
      case 2:
        return new int[] { 0, 0, 0 };
      case 3:
        return new int[] { 255, 102, 0 };
      case 4:
        return new int[] { 0, 255, 255 };
      case 5:
        return new int[] { 68, 0, 0 };
      case 6:
        return new int[] { 68, 0, 0 };
    } 
    return new int[] { 0, 0, 0 };
  }
  
  public void onEntityUpdate() {
    super.onEntityUpdate();
  }
  
  public boolean canBeCollidedWith() {
    return true;
  }
  
  public float getCollisionBorderSize() {
    return 1.0F;
  }
  
  protected void readEntityFromNBT(NBTTagCompound compound) {
    this.type = compound.getInteger("Type");
    if (!this.world.isRemote)
      this.dataManager.set(TYPE, (byte) this.type);
    this.noClip = (this.type == 4 || this.type == 5 || this.type == 6);
    this.isChaser = (this.type == 3 || this.type == 4 || this.type == 5 || this.type == 6);
  }
  
  protected void writeEntityToNBT(NBTTagCompound compound) {
    compound.setInteger("Type", this.type);
  }
}


