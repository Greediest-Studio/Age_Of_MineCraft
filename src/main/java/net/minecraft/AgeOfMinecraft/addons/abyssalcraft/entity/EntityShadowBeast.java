package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShadowBeast extends EntityTameBase implements Armored, Undead {
  private int shadowFlameShootTimer;
  
  public EntityShadowBeast(World par1World) {
    super(par1World);
    setSize(0.8F, 2.9F);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 48.0F, 12.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.isOffensive = true;
    this.isImmuneToFire = true;
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public int getNextLevelRequirement() {
    return 1500;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityShadowBeast(this.world);
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    swingArm(EnumHand.MAIN_HAND);
    swingArm(EnumHand.OFF_HAND);
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase) {
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 15));
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60));
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return flag;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected float getSoundPitch() {
    return super.getSoundPitch() - 0.3F;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.shadow_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.shadow_death;
  }
  
  protected Item getDropItem() {
    return ACItems.shadow_gem;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_SHADOW_BEAST;
  }
  
  public void onLivingUpdate() {
    for (int i = 0; i < 2 && ACConfig.particleEntity && this.world.provider.getDimension() != ACLib.dark_realm_id; i++)
      this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) <= 64.0D && this.shadowFlameShootTimer <= (isHero() ? -100 : -300))
      this.shadowFlameShootTimer = 100; 
    if (this.shadowFlameShootTimer > 0) {
      this.motionX *= 0.05D;
      this.motionZ *= 0.05D;
      this.world.setEntityState((Entity)this, (byte)23);
      if (this.ticksExisted % 5 == 0)
        this.world.playSound(null, new BlockPos(this.posX + 0.5D, this.posY + getEyeHeight(), this.posZ + 0.5D), SoundEvents.ENTITY_GHAST_SHOOT, getSoundCategory(), 0.5F + getRNG().nextFloat(), getRNG().nextFloat() * 0.7F + 0.3F); 
      Entity target = getHeadLookTarget();
      if (target != null) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D), Predicates.and(EntitySelectors.IS_ALIVE));
        if (list != null && !list.isEmpty())
            for (EntityLivingBase entity : list) {
                if (entity != null && !false && this.rand.nextInt(3) == 0)
                    if (entity.attackEntityFrom((new DamageSource("shadow")).setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(), (float) (15.0D - getDistance((Entity) entity)))) {
                        entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
                        entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
                    } else {
                        attackEntityAsMob((Entity) entity);
                        entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
                        entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
                    }
            }
        if (target.attackEntityFrom((new DamageSource("shadow")).setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(), (float)(15.0D - getDistance(target)))) {
          if (target instanceof EntityLivingBase) {
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
          } 
        } else {
          attackEntityAsMob(target);
          if (target instanceof EntityLivingBase) {
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 1));
          } 
        } 
      } 
    } 
    this.shadowFlameShootTimer--;
    super.onLivingUpdate();
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
      Calendar calendar = this.world.getCurrentDate();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    return par1EntityLivingData;
  }
  
  private Entity getHeadLookTarget() {
    Entity pointedEntity = null;
    double range = 4.0D + this.rand.nextDouble() * 8.0D;
    Vec3d srcVec = new Vec3d(this.posX, this.posY + getEyeHeight(), this.posZ);
    Vec3d lookVec = getLook(1.0F);
    RayTraceResult raytrace = this.world.rayTraceBlocks(srcVec, srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range));
    BlockPos hitpos = (raytrace != null) ? raytrace.getBlockPos() : null;
    double rx = (hitpos == null) ? range : Math.min(range, Math.abs(this.posX - hitpos.getX()));
    double ry = (hitpos == null) ? range : Math.min(range, Math.abs(this.posY - hitpos.getY()));
    double rz = (hitpos == null) ? range : Math.min(range, Math.abs(this.posZ - hitpos.getZ()));
    Vec3d destVec = srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);
    float var9 = 4.0F;
    List<Entity> possibleList = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().offset(lookVec.x * rx, lookVec.y * ry, lookVec.z * rz).grow(var9, var9, var9));
    double hitDist = 0.0D;
    for (Entity possibleEntity : possibleList) {
      if (possibleEntity != this && possibleEntity instanceof EntityLivingBase && !false) {
        float borderSize = possibleEntity.getCollisionBorderSize();
        AxisAlignedBB collisionBB = possibleEntity.getEntityBoundingBox().grow(borderSize, borderSize, borderSize);
        RayTraceResult interceptPos = collisionBB.calculateIntercept(srcVec, destVec);
        if (collisionBB.contains(srcVec)) {
          if (0.0D < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = 0.0D;
          } 
          continue;
        } 
        if (interceptPos != null) {
          double possibleDist = srcVec.distanceTo(interceptPos.hitVec);
          if (possibleDist < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = possibleDist;
          } 
        } 
      } 
    } 
    return pointedEntity;
  }
  
  protected void addMouthParticles() {
    if (this.world.isRemote) {
      Vec3d vector = getLookVec();
      double px = this.posX;
      double py = this.posY + getEyeHeight();
      double pz = this.posZ;
      for (int i = 0; i < 15; i++) {
        double dx = vector.x;
        double dy = vector.y;
        double dz = vector.z;
        double spread = 5.0D + getRNG().nextDouble() * 2.5D;
        double velocity = 0.5D + getRNG().nextDouble() * 0.5D;
        dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz);
      } 
    } else {
      this.world.setEntityState((Entity)this, (byte)23);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 23) {
      addMouthParticles();
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (isChild()) {
      if (hasOwner(player)) {
        player.swingArm(EnumHand.MAIN_HAND);
        if (getRidingEntity() == null) {
          startRiding((Entity)player, true);
        } else {
          dismountRidingEntity();
        } 
      } 
      return true;
    } 
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public double getMountedYOffset() {
    return this.height * 0.9D;
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
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
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
}

