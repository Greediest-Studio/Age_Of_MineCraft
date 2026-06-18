package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

public class EntityDreadguard extends EntityTameBase implements Armored {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);
  
  private int flameShootTimer;
  
  public EntityDreadguard(World par1World) {
    super(par1World);
    setSize(0.75F, 2.9F);
    this.isImmuneToFire = true;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 12.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 1000;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityDreadguard(this.world);
  }
  
  protected void dropEquipmentUndamaged() {}
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(240.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.isFireDamage())
      return false; 
    if (par1DamageSource.getTrueSource() instanceof net.minecraft.entity.projectile.EntityArrow)
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.dreadguard_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.dreadguard_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.dreadguard_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.25F, 0.9F);
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 0.5F, (isChild() ? 1.5F : 1.0F) / getFittness());
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_DREADGUARD;
  }
  
  protected Item getDropItem() {
    return ACItems.dreaded_shard_of_abyssalnite;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(ACItems.dreaded_abyssalnite_helmet));
    setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ACItems.dreaded_abyssalnite_chestplate));
    setItemStackToSlot(EntityEquipmentSlot.LEGS, new ItemStack(ACItems.dreaded_abyssalnite_leggings));
    setItemStackToSlot(EntityEquipmentSlot.FEET, new ItemStack(ACItems.dreaded_abyssalnite_boots));
    IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    Calendar calendar = this.world.getCurrentDate();
    attribute.removeModifier(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
      attribute.applyModifier(attackDamageBoost);
      if (this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    return par1EntityLivingData;
  }
  
  public void onLivingUpdate() {
    if (this.ticksExisted == 1)
      playSound(ESound.amalgamate, 1.0F, 1.0F); 
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) <= 64.0D && this.flameShootTimer <= (isHero() ? -100 : -200))
      this.flameShootTimer = 60; 
    if (this.flameShootTimer > 0) {
      this.motionX *= 0.0D;
      this.motionZ *= 0.0D;
      setAIMoveSpeed(0.0F);
      this.world.setEntityState((Entity)this, (byte)23);
      if (this.ticksExisted % 5 == 0)
        this.world.playSound(null, new BlockPos(this.posX + 0.5D, this.posY + getEyeHeight(), this.posZ + 0.5D), ACSounds.dreadguard_barf, getSoundCategory(), 0.5F + getRNG().nextFloat(), getRNG().nextFloat() * 0.5F + 0.2F); 
      Entity target = getHeadLookTarget();
      if (target != null) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity = list.get(i1);
            if (entity != null && !false && this.rand.nextInt(3) == 0)
              if (entity.attackEntityFrom(AbyssalCraftAPI.dread, (float)(15.0D - getDistance((Entity)entity)))) {
                entity.addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
                entity.setFire((int)(15.0F - getDistance((Entity)entity)));
              } else {
                attackEntityAsMob((Entity)entity);
                entity.setFire((int)(15.0F - getDistance((Entity)entity)));
              }  
          }  
        if (target.attackEntityFrom(AbyssalCraftAPI.dread, (float)(15.0D - getDistance(target)))) {
          if (target instanceof EntityLivingBase)
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
          target.setFire((int)(15.0F - getDistance(target)));
        } else {
          attackEntityAsMob(target);
          target.setFire((int)(15.0F - getDistance(target)));
        } 
      } 
    } 
    this.flameShootTimer--;
    super.onLivingUpdate();
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
      for (int i = 0; i < 5; i++) {
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
        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz, new int[] { Item.getIdFromItem(ACItems.dreaded_shard_of_abyssalnite) });
        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz, new int[] { Item.getIdFromItem(ACItems.dread_fragment) });
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
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.metalHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.metalHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.metalHitCrush;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
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
  
  protected boolean canFitPassenger(Entity passenger) {
    return (getPassengers().size() < 2);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      int i = getPassengers().indexOf(passenger);
      float f3 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f11 = MathHelper.sin(f3);
      float f4 = MathHelper.cos(f3);
      if (i == 1)
        passenger.setPosition(this.posX - (f4 * 0.75F * getFittness()), this.posY + (1.7F * getFittness()), this.posZ - (f11 * 0.75F * getFittness())); 
      if (i == 0)
        passenger.setPosition(this.posX + (f4 * 0.75F * getFittness()), this.posY + (1.7F * getFittness()), this.posZ + (f11 * 0.75F * getFittness())); 
    } 
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

