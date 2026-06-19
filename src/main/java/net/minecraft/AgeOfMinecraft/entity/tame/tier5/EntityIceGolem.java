package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIceGolem extends EntityTameBase implements IRangedAttackMob, Armored, Elemental {
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.0D, 40, 32.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.0D, true);
  
  public EntityIceGolem(World worldIn) {
    super(worldIn);
    setSize(1.25F, 2.58F);
    this.isOffensive = true;
    this.experienceValue = 20;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.67D, 80));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public String getDescName() {
    return "ice_golem";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityIceGolem(this.world);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.CONSTRUCT;
  }
  
  public float getBonusVSLight() {
    return 3.0F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.255D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void performSpecialAttack() {
    this.motionY = 1.0D;
    playSound(ESound.golemSpecial, 10.0F, 1.1F);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !player.isSneaking() && !this.world.isRemote)
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
      float sho = this.limbSwing - this.limbSwingAmount * (1.0F - this.ticksExisted * 0.001F) + 6.0F;
      float sho1 = (Math.abs(sho % 13.0F - 6.5F) - 3.25F) / 3.25F;
      if (i == 1)
        passenger.setPosition(this.posX - f4 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.posY + 1.375D, this.posZ - f11 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
      if (i == 0)
        passenger.setPosition(this.posX + f4 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.posY + 1.375D, this.posZ + f11 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
    } 
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
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
      List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(1.0D));
        for (Entity entity : list) {
            if (entity instanceof EntityLivingBase && !false && !this.world.isRemote && this.ticksExisted % 10 == 0)
                attackEntityAsMob(entity);
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
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    int i = MathHelper.floor(this.posX);
    int j = MathHelper.floor(this.posY);
    int k = MathHelper.floor(this.posZ);
    if (isWet() || this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F)
      attackEntityFrom((new DamageSource("melt")).setFireDamage().setDamageBypassesArmor(), 1.0F); 
    if (getAttackTarget() != null)
      if (getDistanceSq((Entity)getAttackTarget()) > 100.0D || getAttackTarget() instanceof net.minecraft.entity.EntityFlying || (getAttackTarget()).posY > this.posY + 4.0D) {
        this.tasks.addTask(2, (EntityAIBase)this.aiArrowAttack);
        this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.tasks.addTask(2, (EntityAIBase)this.aiAttackOnCollide);
        this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
      }  
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) < 64.0D && getSpecialAttackTimer() <= 0 && this.onGround && isHero())
      performSpecialAttack(); 
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D && this.rand.nextInt(5) == 0) {
      j = MathHelper.floor(this.posY - 0.2D);
      IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
      if (iblockstate.getMaterial() != Material.AIR)
        this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, Block.getStateId(iblockstate));
    } 
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    this.attackTimer = 10;
    this.world.setEntityState((Entity)this, (byte)5);
    playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
    AttributeModifier irongolemrandomizer = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Iron Golem randomizer", this.rand.nextDouble() * 0.75D, 1)).setSaved(false);
    IAttributeInstance iattributeinstanceattack = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    if (!iattributeinstanceattack.hasModifier(irongolemrandomizer))
      iattributeinstanceattack.applyModifier(irongolemrandomizer); 
    if (super.attackEntityAsMob(entityIn)) {
      if (iattributeinstanceattack.hasModifier(irongolemrandomizer))
        iattributeinstanceattack.removeModifier(irongolemrandomizer); 
      return true;
    } 
    return false;
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    double amount = 0.45D;
    if (this.world.getDifficulty() == EnumDifficulty.EASY)
      amount *= 0.75D; 
    if (this.world.getDifficulty() == EnumDifficulty.HARD)
      amount *= 1.5D; 
    if (!entity.isEntityAlive() && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).prevRenderYawOffset = ((EntityLivingBase)entity).renderYawOffset = ((EntityLivingBase)entity).prevRotationYaw = ((EntityLivingBase)entity).rotationYaw = ((EntityLivingBase)entity).prevRotationYawHead = ((EntityLivingBase)entity).rotationYawHead = this.rotationYawHead;
      float xRatio = MathHelper.sin(this.rotationYawHead * 0.017453292F);
      float zRatio = -MathHelper.cos(this.rotationYawHead * 0.017453292F);
      entity.isAirBorne = true;
      float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
      entity.motionX /= 2.0D;
      entity.motionZ /= 2.0D;
      entity.motionX -= xRatio / f * 2.0D;
      entity.motionZ -= zRatio / f * 2.0D;
      entity.motionY /= 2.0D;
      entity.motionY += amount;
      if (entity instanceof EntityPlayerMP)
        ((EntityPlayerMP)entity).connection.sendPacket((Packet)new SPacketEntityVelocity(entity)); 
    } 
    entity.motionY += amount;
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 5) {
      this.attackTimer = 10;
      playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
    EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.world, (EntityLivingBase)this);
    float f2 = this.renderYawOffset * 0.017453292F;
    float f19 = MathHelper.sin(f2);
    float f3 = MathHelper.cos(f2);
    double d1 = target.posX + (f19 * 1.5F) - this.posX;
    double d2 = target.posY - 4.5D + target.height - this.posY;
    double d3 = target.posZ - (f3 * 1.5F) - this.posZ;
    float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.25F;
    entitysnowball.shoot(d1, d2 + f, d3, 1.6F, 1.0F);
    this.attackTimer = 10;
    this.world.setEntityState((Entity)this, (byte)5);
    playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
    playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
    this.world.spawnEntity((Entity)entitysnowball);
    float f21 = MathHelper.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
    entitysnowball.motionX = d1 / f21 * 0.4D + entitysnowball.motionX;
    entitysnowball.motionY = d2 / f21 * 0.4D + entitysnowball.motionY;
    entitysnowball.motionZ = d3 / f21 * 0.4D + entitysnowball.motionZ;
    entitysnowball.damage = 4.0F;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void fall(float distance, float damageMultiplier) {
    if (getSpecialAttackTimer() <= 0 && isHero() && getAttackTarget() != null) {
      setSpecialAttackTimer(300);
      playSound(ESound.golemSmash, 10.0F, 1.0F);
      createEngenderModExplosionFireless((Entity)this, this.posX, this.posY - 2.0D, this.posZ, 3.0F, false);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(24.0D, 3.0D, 24.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty())
          for (EntityLivingBase entity : list) {
              if (entity != null && !false) {
                  entity.motionY += 1.5D;
                  inflictEngenderMobDamage(entity, " was smashed by ", DamageSource.causeExplosionDamage((EntityLivingBase) this), (entity instanceof net.minecraft.entity.monster.IMob) ? 16.0F : 8.0F);
                  entity.isAirBorne = true;
                  float f = MathHelper.sqrt(MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.sin(this.rotationYaw * 0.017453292F) + -MathHelper.cos(this.rotationYaw * 0.017453292F) * -MathHelper.cos(this.rotationYaw * 0.017453292F));
                  entity.motionX /= 2.0D;
                  entity.motionZ /= 2.0D;
                  entity.motionX -= (MathHelper.sin(this.rotationYaw * 0.017453292F) / f) * 1.0D;
                  entity.motionZ -= (-MathHelper.cos(this.rotationYaw * 0.017453292F) / f) * 1.0D;
              }
          }
    } 
    super.fall(distance, damageMultiplier);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SNOWMAN_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    playSound(SoundEvents.ENTITY_SNOWMAN_HURT, getSoundVolume(), getSoundPitch() - 0.1F);
    return SoundEvents.ENTITY_IRONGOLEM_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    playSound(SoundEvents.ENTITY_SNOWMAN_DEATH, getSoundVolume(), getSoundPitch() - 0.1F);
    return SoundEvents.ENTITY_IRONGOLEM_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, (isChild() ? 1.5F : 1.0F) / getFittness());
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_ICE_GOLEM;
  }
}

