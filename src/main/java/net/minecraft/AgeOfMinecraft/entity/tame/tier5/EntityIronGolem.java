package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIronGolem extends EntityTameBase implements Armored {
  public EntityIronGolem(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.6F, 2.4F);
    } else {
      setSize(1.25F, 2.68F);
    } 
    this.experienceValue = 20;
    this.isOffensive = true;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.67D, 80));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public String getDescName() {
    return "iron_golem";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityIronGolem(this.world);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
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
  
  public float getBonusVSStructure() {
    return 2.0F;
  }
  
  public float getBonusVSUndead() {
    return 1.5F;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  protected void entityInit() {
    super.entityInit();
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.1F) : super.getSoundPitch();
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 1; i++) {
        EntityIronGolem baby = new EntityIronGolem(this.world);
        baby.copyLocationAndAnglesFrom((Entity)this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-100000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity((Entity)baby);
      }  
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (this.hurtResistantTime <= 0 && !isInvisible() && this.holdRoseTick > 0 && !isRiding() && !isBeingRidden() && !this.tasks.taskEntries.contains(new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 8.0F, 1.0F))) {
      this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 8.0F, 1.0F));
    } else {
      this.tasks.removeTask((EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 8.0F, 1.0F));
    } 
    if (this.hurtResistantTime <= 0 && !isInvisible() && this.world.getClosestPlayerToEntity((Entity)this, 8.0D) != null && false && getAttackTarget() == null && this.rand.nextInt(100) == 0 && this.holdRoseTick == 0 && !isBeingRidden() && getJukeboxToDanceTo() == null)
      setHoldingRose(true); 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void performSpecialAttack() {
    this.motionY = 1.0D;
    playSound(ESound.golemSpecial, 10.0F, 1.0F);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (getHoldRoseTick() > 0) {
        if (!this.world.isRemote) {
          dropItemWithOffset(Item.getItemFromBlock((Block)Blocks.RED_FLOWER), 1, BlockFlower.EnumFlowerType.POPPY.getMeta());
          setHoldingRose(false);
        } 
      } else if (!isWild() && false && !isChild() && !player.isSneaking() && !this.world.isRemote) {
        player.startRiding((Entity)this);
      } 
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
      if (EngenderConfig.mobs.useMobTalkerModels) {
        if (i == 1)
          passenger.setPosition(this.posX - f4 * (0.5D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.posY + 1.1D, this.posZ - f11 * (0.5D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
        if (i == 0)
          passenger.setPosition(this.posX + f4 * (0.5D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.posY + 1.1D, this.posZ + f11 * (0.5D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
      } else {
        if (i == 1)
          passenger.setPosition(this.posX - f4 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.posY + 1.375D, this.posZ - f11 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
        if (i == 0)
          passenger.setPosition(this.posX + f4 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.posY + 1.375D, this.posZ + f11 * (0.6499999761581421D + ((this.limbSwingAmount >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
      } 
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
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
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
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) < 64.0D && getSpecialAttackTimer() <= 0 && this.onGround && isHero())
      performSpecialAttack(); 
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D && this.rand.nextInt(5) == 0) {
      int i = MathHelper.floor(this.posX);
      int j = MathHelper.floor(this.posY - 0.20000000298023224D);
      int k = MathHelper.floor(this.posZ);
      IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
      if (iblockstate.getMaterial() != Material.AIR)
        this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockstate) }); 
    } 
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    if (entityIn instanceof EntityZombieVillager) {
      setHoldingRose(true);
      net.minecraft.entity.passive.EntityVillager entityvillager = new net.minecraft.entity.passive.EntityVillager(this.world);
      entityvillager.copyLocationAndAnglesFrom(entityIn);
      entityvillager.setProfession(((EntityZombieVillager)entityIn).getProfession());
      entityvillager.finalizeMobSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null, false);
      entityvillager.setLookingForHome();
      if (((EntityZombieVillager)entityIn).isChild())
        entityvillager.setGrowingAge(-24000); 
      this.world.removeEntity(entityIn);
      entityvillager.setNoAI(((EntityZombieVillager)entityIn).isAIDisabled());
      if (((EntityZombieVillager)entityIn).hasCustomName())
        entityvillager.setCustomNameTag(((EntityZombieVillager)entityIn).getCustomNameTag()); 
      this.world.spawnEntity((Entity)entityvillager);
      entityvillager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
      this.world.playEvent((EntityPlayer)null, 1027, new BlockPos((int)((EntityZombieVillager)entityIn).posX, (int)((EntityZombieVillager)entityIn).posY, (int)((EntityZombieVillager)entityIn).posZ), 0);
      if (entityvillager.getProfession() == 5)
        entityvillager.onKillCommand(); 
      return true;
    } 
    if (entityIn instanceof EntityZombie && ((EntityZombie)entityIn).isVillager()) {
      setHoldingRose(true);
      EntityVillager entityvillager = new EntityVillager(this.world);
      entityvillager.copyLocationAndAnglesFrom(entityIn);
      entityvillager.setProfession(((EntityZombie)entityIn).getVillagerType());
      entityvillager.setGrowingAge(((EntityZombie)entityIn).getGrowingAge());
      this.world.removeEntity(entityIn);
      entityvillager.setNoAI(((EntityZombie)entityIn).isAIDisabled());
      entityvillager.setOwnerId(getOwnerId());
      if (((EntityZombie)entityIn).hasCustomName())
        entityvillager.setCustomNameTag(((EntityZombie)entityIn).getCustomNameTag()); 
      this.world.spawnEntity((Entity)entityvillager);
      entityvillager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
      this.world.playEvent((EntityPlayer)null, 1027, new BlockPos((int)((EntityZombie)entityIn).posX, (int)((EntityZombie)entityIn).posY, (int)((EntityZombie)entityIn).posZ), 0);
      return true;
    } 
    if (entityIn instanceof EntityVillager && !isWild() && !false) {
      setHoldingRose(true);
      ((EntityVillager)entityIn).setOwnerId(getOwnerId());
      setAttackTarget(null);
      return true;
    } 
    this.attackTimer = 10;
    this.world.setEntityState((Entity)this, (byte)4);
    playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
    AttributeModifier irongolemrandomizer = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Iron Golem randomizer", this.rand.nextDouble() * 2.5D, 1)).setSaved(false);
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
    double amount = 0.6D;
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
    if (id == 4) {
      this.attackTimer = 10;
      playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
    } else if (id == 11) {
      this.holdRoseTick = 100;
    } else if (id == 12) {
      this.holdRoseTick = 0;
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void setHoldingRose(boolean p_70851_1_) {
    this.holdRoseTick = p_70851_1_ ? 100 : 0;
    this.world.setEntityState((Entity)this, (byte)(p_70851_1_ ? 11 : 12));
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void fall(float distance, float damageMultiplier) {
    if (getSpecialAttackTimer() <= 0 && isHero() && getAttackTarget() != null) {
      setSpecialAttackTimer(300);
      playSound(ESound.golemSmash, 10.0F, 1.0F);
      createEngenderModExplosionFireless((Entity)this, this.posX, this.posY - 2.0D, this.posZ, 3.0F, false);
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(24.0D, 3.0D, 24.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null && !false) {
            inflictEngenderMobDamage(entity, " was smashed by ", DamageSource.causeExplosionDamage((EntityLivingBase)this), (entity instanceof net.minecraft.entity.monster.IMob) ? 24.0F : 12.0F);
            entity.isAirBorne = true;
            float f = MathHelper.sqrt(MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.sin(this.rotationYaw * 0.017453292F) + -MathHelper.cos(this.rotationYaw * 0.017453292F) * -MathHelper.cos(this.rotationYaw * 0.017453292F));
            entity.motionX /= 2.0D;
            entity.motionZ /= 2.0D;
            entity.motionX -= (MathHelper.sin(this.rotationYaw * 0.017453292F) / f) * 1.0D;
            entity.motionZ -= (-MathHelper.cos(this.rotationYaw * 0.017453292F) / f) * 1.0D;
            entity.motionY += 1.5D;
          } 
        }  
    } 
    super.fall(distance, damageMultiplier);
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels) {
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() - 0.2F);
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() - 0.1F);
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch());
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F);
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.2F);
    } 
    return SoundEvents.ENTITY_IRONGOLEM_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels) {
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() - 0.2F);
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() - 0.1F);
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch());
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F);
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.2F);
    } 
    return SoundEvents.ENTITY_IRONGOLEM_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, (isChild() ? 1.5F : 1.0F) / getFittness());
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_IRON_GOLEM;
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
}

