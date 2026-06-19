package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemPEGun;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeleton;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedBowAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntitySmallFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntitySnowballHarmful;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Loader;

public class EntitySkeleton extends EntityTameBase implements IRangedAttackMob, Undead {
  private static final DataParameter<Integer> SKELETON_VARIANT = EntityDataManager.createKey(EntitySkeleton.class, DataSerializers.VARINT);
  
  private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EntitySkeleton.class, DataSerializers.BOOLEAN);
  
  private final EntityAIAttackRangedBowAlly<EntitySkeleton> aiArrowAttack = new EntityAIAttackRangedBowAlly<>(this, 1.0D, 5, 15.0F);
  
  private final EntityAIAttackRangedAlly aiRangedAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 15.0F);
  
  private final EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  private int helmetCount = 1;
  
  public EntitySkeleton(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIRestrictSun((EntityCreature)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 6.0F));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    if (getSkeletonType() == 1) {
      setSize(0.6F, 2.39F);
    } else {
      setSize(0.5F, 1.95F);
    } 
    if (worldIn != null && !worldIn.isRemote)
      setCombatTask(); 
    this.experienceValue = 4;
  }
  
  public String getDescName() {
    switch (getSkeletonType()) {
      case 1:
        return "wither_skeleton";
      case 2:
        return "stray";
    } 
    return "skeleton";
  }
  
  public int getNextLevelRequirement() {
    return (getTier() == EnumTier.TIER4) ? 100 : 25;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySkeleton(this.world);
  }
  
  public int timesToConvert() {
    return (getSkeletonType() != 0) ? 27 : 13;
  }
  
  public EnumTier getTier() {
    return (getSkeletonType() != 0) ? EnumTier.TIER4 : EnumTier.TIER3;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (getRidingEntity() != null && getRidingEntity() instanceof EntitySkeletonHorse) {
        switch (getSkeletonType()) {
          case 1:
            return I18n.translateToLocal("entity.WitherSkeletonHorsemanHelpful.cmm.name");
          case 2:
            return I18n.translateToLocal("entity.StrayHorsemanHelpful.cmm.name");
        } 
        return I18n.translateToLocal("entity.SkeletonHorsemanHelpful.cmm.name");
      } 
      switch (getSkeletonType()) {
        case 1:
          return I18n.translateToLocal("entity.WitherSkeletonHelpful.cmm.name");
        case 2:
          return I18n.translateToLocal("entity.StrayHelpful.cmm.name");
      } 
      return I18n.translateToLocal("entity.SkeletonHelpful.cmm.name");
    } 
    if (getRidingEntity() != null && getRidingEntity() instanceof EntitySkeletonHorse) {
      switch (getSkeletonType()) {
        case 1:
          return I18n.translateToLocal("entity.WitherSkeletonHorsemanHelpful.name");
        case 2:
          return I18n.translateToLocal("entity.StrayHorsemanHelpful.name");
      } 
      return I18n.translateToLocal("entity.SkeletonHorsemanHelpful.name");
    } 
    switch (getSkeletonType()) {
      case 1:
        return I18n.translateToLocal("entity.WitherSkeletonHelpful.name");
      case 2:
        return I18n.translateToLocal("entity.StrayHelpful.name");
    } 
    return I18n.translateToLocal("entity.SkeletonHelpful.name");
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(SKELETON_VARIANT, 0);
    this.dataManager.register(ATTACKING, Boolean.FALSE);
  }
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntitySkeleton baby = new EntitySkeleton(this.world);
        baby.copyLocationAndAnglesFrom((Entity)this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), (IEntityLivingData)null);
        baby.setGrowingAge(-48000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        baby.setSkeletonType(getSkeletonType());
        baby.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
        baby.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
        baby.setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
        baby.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
        baby.setItemStackToSlot(EntityEquipmentSlot.LEGS, ItemStack.EMPTY);
        baby.setItemStackToSlot(EntityEquipmentSlot.FEET, ItemStack.EMPTY);
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity((Entity)baby);
      }  
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return (EntityTameBase)new EntityMutantSkeleton(this.world); 
    return null;
  }
  
  protected SoundEvent getAmbientSound() {
    switch (getSkeletonType()) {
      case 1:
        return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
      case 2:
        return SoundEvents.ENTITY_STRAY_AMBIENT;
    } 
    return SoundEvents.ENTITY_SKELETON_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.05F); 
    switch (getSkeletonType()) {
      case 1:
        return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
      case 2:
        return SoundEvents.ENTITY_STRAY_HURT;
    } 
    return SoundEvents.ENTITY_SKELETON_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.05F); 
    switch (getSkeletonType()) {
      case 1:
        return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
      case 2:
        return SoundEvents.ENTITY_STRAY_DEATH;
    } 
    return SoundEvents.ENTITY_SKELETON_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    switch (getSkeletonType()) {
      case 1:
        playSound(SoundEvents.ENTITY_WITHER_SKELETON_STEP, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
      case 2:
        playSound(SoundEvents.ENTITY_STRAY_STEP, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
        break;
    } 
    playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
    if (EngenderConfig.mobs.useMobTalkerModels)
      super.playStepSound(pos, blockIn); 
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.15F) : super.getSoundPitch();
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    if (super.attackEntityAsMob(p_70652_1_)) {
      if (getSkeletonType() == 1 && p_70652_1_ instanceof EntityLivingBase) {
        int i = 5;
        if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
          i = 10;
        } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
          i = 20;
        } 
        if (i > 0)
          ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(MobEffects.WITHER, i * 20, 0 + this.world.getDifficulty().getId())); 
      } 
      if (getSkeletonType() == 2 && p_70652_1_ instanceof EntityLivingBase) {
        int i = 10;
        if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
          i = 20;
        } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
          i = 40;
        } 
        if (i > 0)
          ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, i * 20, 0 + this.world.getDifficulty().getId())); 
      } 
      if (getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) != null && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() instanceof net.minecraft.item.ItemBow) {
        swingArm(EnumHand.OFF_HAND);
        playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, getSoundVolume(), 1.0F);
        knockBack((Entity)this, 0.5F, MathHelper.sin(this.rotationYawHead * 0.017453292F), -MathHelper.cos(this.rotationYawHead * 0.017453292F));
        ((EntityLivingBase)p_70652_1_).knockBack((Entity)this, 1.0F, MathHelper.sin(this.rotationYawHead * 0.017453292F), -MathHelper.cos(this.rotationYawHead * 0.017453292F));
      } 
      if (getItemStackFromSlot(EntityEquipmentSlot.OFFHAND) != null && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == Items.SHIELD) {
        playSound(SoundEvents.ITEM_SHIELD_BLOCK, getSoundVolume(), 1.0F);
        knockBack((Entity)this, 0.5F, -MathHelper.sin(this.rotationYawHead * 0.017453292F), MathHelper.cos(this.rotationYawHead * 0.017453292F));
        ((EntityLivingBase)p_70652_1_).knockBack((Entity)this, 1.0F, MathHelper.sin(this.rotationYawHead * 0.017453292F), -MathHelper.cos(this.rotationYawHead * 0.017453292F));
      } 
      if (getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) != null && (getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.FLINT_AND_STEEL || getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.FIRE_CHARGE))
        p_70652_1_.setFire(12); 
      return true;
    } 
    return false;
  }
  
  public boolean isPotionApplicable(PotionEffect potioneffectIn) {
    return (potioneffectIn.getPotion() == MobEffects.WITHER && getSkeletonType() == 1) ? false : super.isPotionApplicable(potioneffectIn);
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public void performSpecialAttack() {
    if (getHeldItem(EnumHand.MAIN_HAND) != null && getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof net.minecraft.item.ItemBow) {
      setSpecialAttackTimer(500);
      playSound(ESound.skeletonSpecial, 10.0F, 1.0F);
    } else {
      getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 5.0F);
      setSpecialAttackTimer(500);
    } 
  }
  
  public void onLivingUpdate() {
    ItemStack hats = (this.helmetCount > 0) ? new ItemStack((Item)Items.LEATHER_HELMET, this.helmetCount) : ItemStack.EMPTY;
    this.basicInventory.setInventorySlotContents(7, hats);
    if (isRiding() && getRidingEntity() instanceof EntitySkeletonHorse)
      getPassengers().clear(); 
    if (getOwner() != null)
      if (getDistanceSq((Entity)getOwner()) >= 2304.0D && isElytraFlying()) {
        double d01 = (getOwner()).posX - this.posX;
        double d11 = (getOwner()).posY - this.posY;
        double d21 = (getOwner()).posZ - this.posZ;
        float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
        this.motionX = d01 / f2 * 0.5D * 0.5D + this.motionX * 0.5D;
        this.motionY = d11 / f2 * 0.5D * 0.5D + this.motionZ * 0.5D;
        this.motionZ = d21 / f2 * 0.5D * 0.5D + this.motionZ * 0.5D;
        faceEntity((Entity)getOwner(), 180.0F, 180.0F);
      }  
    if (this.world.canSeeSky(getPosition()) && ((getAttackTarget() != null && this.onGround) || (!isWild() && (getOwner()).posY > this.posY && getOwner().isElytraFlying())) && getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null && getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
      this.motionY = 1.0D;
      setFlag(7, true);
    } 
    if (!this.onGround && getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null && getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA && !isElytraFlying())
      setFlag(7, true); 
    if (this.onGround && isElytraFlying())
      setFlag(7, false); 
    if (isElytraFlying())
      this.renderYawOffset = this.rotationYaw = -((float)MathHelper.atan2(this.motionX, this.motionZ)) * 57.295776F; 
    ItemStack item = getHeldItem(EnumHand.MAIN_HAND);
    ItemStack secitem = getHeldItem(EnumHand.OFF_HAND);
    if (!item.isEmpty() && !secitem.isEmpty() && item.getItem() instanceof net.minecraft.item.ItemBow && !(secitem.getItem() instanceof net.minecraft.item.ItemBow) && secitem.getItem() != Items.TIPPED_ARROW && getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) <= 128.0D && (getAttackTarget()).posY <= this.posY + 3.0D) {
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, secitem);
      setItemStackToSlot(EntityEquipmentSlot.OFFHAND, item);
    } 
    if (!item.isEmpty() && !secitem.isEmpty() && secitem.getItem() instanceof net.minecraft.item.ItemBow && !(item.getItem() instanceof net.minecraft.item.ItemBow) && item.getItem() != Items.TIPPED_ARROW && getAttackTarget() != null && (getDistanceSq((Entity)getAttackTarget()) > 128.0D || (getAttackTarget()).posY > this.posY + 3.0D)) {
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, secitem);
      setItemStackToSlot(EntityEquipmentSlot.OFFHAND, item);
    } 
    if (!item.isEmpty() && item.getItem() instanceof net.minecraft.item.ItemBow) {
      if (getAttackTarget() != null && getAttackTarget().isEntityAlive() && getDistanceSq((Entity)getAttackTarget()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
        performSpecialAttack(); 
      if (getAttackTarget() != null && isHero() && getSpecialAttackTimer() < 490 && getSpecialAttackTimer() > 470)
        attackEntityWithRangedAttack(getAttackTarget(), 2.0F); 
    } else if (getAttackTarget() != null && getAttackTarget().isEntityAlive() && getDistanceSq((Entity)getAttackTarget()) < 32.0D && getSpecialAttackTimer() <= 0 && isHero()) {
      performSpecialAttack();
    } 
    if (getRidingEntity() != null && getRidingEntity() instanceof EntitySkeletonHorse) {
      getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
      if (isSneaking()) {
        getRidingEntity().setSneaking(true);
      } else {
        getRidingEntity().setSneaking(false);
      } 
      if (isSprinting()) {
        getRidingEntity().setSprinting(true);
      } else {
        getRidingEntity().setSprinting(false);
      } 
    } 
    if (getSkeletonType() == 1 && getRidingEntity() == null)
      getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D); 
    if (this.world.isRemote && getSkeletonType() == 1)
      this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
    if (this.helmetCount < 0)
      this.helmetCount = 0; 
    if ((getSkeletonType() == 1 || isAntiMob() || isChild() || isHero()) && this.helmetCount != 0)
      if (!this.world.isRemote)
        for (int i = 0; i < this.helmetCount; i++) {
          playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
          dropItem((Item)Items.LEATHER_HELMET, 1);
          this.helmetCount--;
        }   
    ItemStack head = getItemStackFromSlot(EntityEquipmentSlot.HEAD);
    if (!head.isEmpty() && head.getItem() == Items.LEATHER_HELMET && isEntityAlive() && !this.world.isDaytime() && !this.world.isRemote) {
      swingArm(EnumHand.MAIN_HAND);
      this.helmetCount++;
      setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
      playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
    } 
    if (isEntityAlive() && this.world.isDaytime() && !this.world.isRemote && !isChild() && getSkeletonType() != 1 && !isAntiMob() && !isHero() && !isImmuneToFire()) {
      float f = getBrightness();
      if (f > 0.5F && this.ticksExisted % (!getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() ? 80 : 10) == 0 && this.world.canSeeSky(new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ))) {
        boolean flag = true;
        ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (!itemstack.isEmpty()) {
          if (itemstack.isItemStackDamageable()) {
            itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
            if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
              renderBrokenItemStack(itemstack);
              setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
            } 
          } 
          flag = false;
        } 
        if (flag)
          if (this.helmetCount > 0) {
            swingArm(EnumHand.MAIN_HAND);
            swingArm(EnumHand.OFF_HAND);
            this.helmetCount--;
            setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((Item)Items.LEATHER_HELMET));
            playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
          } else {
            setFire(8);
          }  
      } 
    } 
    super.onLivingUpdate();
  }
  
  public void updateRidden() {
    super.updateRidden();
    if (getRidingEntity() instanceof EntityCreature) {
      EntityCreature entitycreature = (EntityCreature)getRidingEntity();
      this.renderYawOffset = entitycreature.renderYawOffset;
      entitycreature.rotationPitch = this.rotationPitch;
      entitycreature.rotationYawHead = this.rotationYawHead;
      if (getAttackTarget() != null)
        entitycreature.setAttackTarget(getAttackTarget()); 
      if (this.ticksExisted % 40 == 0) {
        this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
        if (getAttackTarget() == null) {
          entitycreature.heal(5.0F);
        } else {
          entitycreature.heal(1.0F);
        } 
      } 
    } 
  }
  
  public void onDeath(DamageSource cause) {
    super.onDeath(cause);
    if (!this.world.isRemote && getLimitedLife() <= 0)
      for (int i = 0; i < this.helmetCount; i++) {
        dropItem((Item)Items.LEATHER_HELMET, 1);
        this.helmetCount--;
      }  
    if (cause.getTrueSource() instanceof EntityCreeper && ((EntityCreeper)cause.getTrueSource()).getPowered())
      entityDropItem(new ItemStack(Items.SKULL, 1, (getSkeletonType() == 1) ? 1 : 0), 0.0F); 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    switch (getSkeletonType()) {
      case 1:
        return ELoot.ENTITIES_WITHER_SKELETON;
      case 2:
        return ELoot.ENTITIES_STRAY;
    } 
    return ELoot.ENTITIES_SKELETON;
  }
  
  protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    if (this.rand.nextFloat() < 0.25F * difficulty.getClampedAdditionalDifficulty()) {
      int i = this.rand.nextInt(2);
      float f = (this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.325F : 0.25F;
      if (this.rand.nextFloat() < 0.1F)
        i++; 
      if (this.rand.nextFloat() < 0.15F)
        i++; 
      if (this.rand.nextFloat() < 0.2F)
        i++; 
      boolean flag = true;
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
          ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
          if (!flag && this.rand.nextFloat() < f)
            break; 
          flag = false;
          if (itemstack.isEmpty()) {
            Item item = getArmorByChance(entityequipmentslot, i);
            if (item != null)
              setItemStackToSlot(entityequipmentslot, new ItemStack(item)); 
          } 
        } 
      } 
    } 
    if (getSkeletonType() == 1) {
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
      if (getRNG().nextInt(3) == 0) {
        int i = this.rand.nextInt(3);
        if (i == 0) {
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        } else {
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.STONE_SWORD));
        } 
      } 
    } else {
      setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
      if (this.rand.nextFloat() < ((this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.05F : 0.01F)) {
        int i = this.rand.nextInt(3);
        ItemStack stack = new ItemStack(Items.TIPPED_ARROW);
        if (i == 0) {
          PotionUtils.addPotionToItemStack(stack, PotionTypes.STRONG_POISON);
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
        } else {
          PotionUtils.addPotionToItemStack(stack, PotionTypes.LONG_WEAKNESS);
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
        } 
      } else if (getRNG().nextInt(20) == 0) {
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.WOODEN_SWORD));
      } 
    } 
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    Biome biome = this.world.getBiome(new BlockPos((Entity)this));
    if (this.world.provider instanceof net.minecraft.world.WorldProviderHell && this.rand.nextInt(5) != 0) {
      setSkeletonType(1);
    } else if (biome instanceof net.minecraft.world.biome.BiomeSnow && this.rand.nextInt(5) != 0) {
      setSkeletonType(2);
    } 
    setEquipmentBasedOnDifficulty(difficulty);
    setEnchantmentBasedOnDifficulty(difficulty);
    if (getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null) {
      Calendar calendar = this.world.getCurrentDate();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    return livingdata;
  }
  
  public void setCombatTask() {
    if (this.world != null && !this.world.isRemote) {
      this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
      this.tasks.removeTask((EntityAIBase)this.aiRangedAttack);
      this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
      ItemStack itemstack = getHeldItemMainhand();
      if ((itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemBow) || itemstack.getItem() == Items.SNOWBALL || itemstack.getItem() == Items.FIRE_CHARGE || itemstack.getItem() == Items.EGG) {
        this.tasks.addTask(4, (itemstack.getItem() instanceof net.minecraft.item.ItemBow && getIntelligence() >= 10.0F) ? (EntityAIBase)this.aiArrowAttack : (EntityAIBase)this.aiRangedAttack);
      } else {
        this.tasks.addTask(4, (EntityAIBase)this.aiAttackOnCollide);
      } 
    } 
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
    if (getHeldItemMainhand().getItem() instanceof ItemPEGun) {
      if (((ItemPEGun)getHeldItemMainhand().getItem()).getContainedEnergy(getHeldItemMainhand()) > 0.0F) {
        float f = MathHelper.cos((this.rotationYawHead + (180 * ((isLeftHanded() ? 1 : 2) - 1))) * 0.017453292F);
        float f1 = MathHelper.sin((this.rotationYawHead + (180 * ((isLeftHanded() ? 1 : 2) - 1))) * 0.017453292F);
        ((ItemPEGun)getHeldItemMainhand().getItem()).consumeEnergy(getHeldItemMainhand(), 1.0F);
        double d22 = 64.0D;
        Vec3d vec3 = getLook(1.0F);
        double d2 = target.posX - this.posX + f * 0.35D + vec3.x;
        double d3 = target.posY + target.getEyeHeight() - 0.1D - this.posY + getEyeHeight() - 0.10000000149011612D + vec3.y;
        double d4 = target.posZ - this.posZ + f1 * 0.35D + vec3.z;
        EntityPEGunPellet entitywitherskull = new EntityPEGunPellet(this.world, (EntityLivingBase)this, d2, d3, d4);
        entitywitherskull.posX = this.posX + f * 0.35D + vec3.x;
        entitywitherskull.posY = this.posY + getEyeHeight() - 0.10000000149011612D + vec3.y;
        entitywitherskull.posZ = this.posZ + f1 * 0.35D + vec3.z;
        entitywitherskull.damage = ((ItemPEGun)getHeldItemMainhand().getItem()).getProjectileDamage(getHeldItemMainhand());
        if (!this.world.isRemote)
          this.world.spawnEntity((Entity)entitywitherskull); 
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5F, 0.5F + getRNG().nextFloat() * 0.25F);
        playSound(ESound.pegunfire, 0.5F, 0.6F + getRNG().nextFloat() * 0.2F + entitywitherskull.damage / 100.0F);
      } else {
        playSound(ESound.pegunjam, 0.5F, 1.0F);
        entityDropItem(getHeldItemMainhand(), 1.4F);
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
      } 
    } else if (getHeldItemMainhand().getItem() == Items.SNOWBALL) {
      EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.world, (EntityLivingBase)this);
      double d0 = target.posY + target.getEyeHeight() - 1.15D;
      double d1 = target.posX - this.posX;
      double d2 = d0 - entitysnowball.posY;
      double d3 = target.posZ - this.posZ;
      float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.shoot(d1, d2 + f, d3, 1.6F, 26.0F - getDexterity() / 4.0F);
      playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity((Entity)entitysnowball);
      swingArm(EnumHand.MAIN_HAND);
      entitysnowball.damage = (target instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || target instanceof net.minecraft.entity.monster.EntityBlaze) ? 3.0F : ((this.rand.nextInt(3) == 0 || !(target instanceof EntityTameBase)) ? 1.0F : 0.0F);
    } else if (getHeldItemMainhand().getItem() == Items.EGG) {
      EntityEgg entitysnowball = new EntityEgg(this.world, (EntityLivingBase)this);
      double d0 = target.posY + target.getEyeHeight() - 1.15D;
      double d1 = target.posX - this.posX;
      double d2 = d0 - entitysnowball.posY;
      double d3 = target.posZ - this.posZ;
      float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.shoot(d1, d2 + f, d3, 1.6F, 26.0F - getDexterity() / 4.0F);
      playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity((Entity)entitysnowball);
      swingArm(EnumHand.MAIN_HAND);
    } else if (getHeldItemMainhand().getItem() == Items.FIRE_CHARGE) {
      double d1 = isChild() ? 0.25D : 0.5D;
      Vec3d vec3 = getLook(1.0F);
      double d2 = target.posX - this.posX + vec3.x * d1;
      double d3 = target.posY + ((target.height > 8.0F) ? 7.0D : (target.height * 0.5D)) - this.posY + 1.0D;
      double d4 = target.posZ - this.posZ + vec3.z * d1;
      EntitySmallFireballOther entitylargefireball = new EntitySmallFireballOther(this.world, (EntityLivingBase)this, d2, d3, d4);
      entitylargefireball.posX = this.posX + vec3.x * d1;
      entitylargefireball.posY = this.posY + 1.0D;
      entitylargefireball.posZ = this.posZ + vec3.z * d1;
      float dm = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
      entitylargefireball.damage = dm;
      playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0F, 1.5F);
      this.world.spawnEntity((Entity)entitylargefireball);
      swingArm(EnumHand.MAIN_HAND);
    } else {
      EntityTippedArrowOther entityarrow = new EntityTippedArrowOther(this.world, (EntityLivingBase)this);
      double d0 = target.posX - this.posX;
      double d1 = target.posY + target.getEyeHeight() - this.posY + getEyeHeight() - 0.10000000149011612D;
      double d2 = target.posZ - this.posZ;
      double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
      entityarrow.shoot(d0, d1 + d3 * getDistance((Entity)target) * 0.013D, d2, 1.4F, isHero() ? 1.0F : 9.0F);
      int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, (EntityLivingBase)this);
      int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, (EntityLivingBase)this);
      if (getRidingEntity() != null) {
        entityarrow.setDamage((p_82196_2_ * 3.0F) + this.rand.nextGaussian() * 0.25D + 0.5D);
      } else {
        entityarrow.setDamage((p_82196_2_ * 1.5F) + this.rand.nextGaussian() * 0.25D + 0.5D);
      } 
      if (target instanceof net.minecraft.entity.boss.EntityDragon && this.posY < target.posY - 10.0D)
        entityarrow.motionY++; 
      if (i > 0)
        entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D); 
      if (isHero())
        entityarrow.setDamage(entityarrow.getDamage() * 2.0D); 
      if (this.moralRaisedTimer > 200)
        entityarrow.setDamage(entityarrow.getDamage() * 1.5D); 
      if (getRidingEntity() != null) {
        j += 2;
        entityarrow.setIsCritical(true);
      } 
      if (j > 0)
        entityarrow.setKnockbackStrength(j); 
      if (getSkeletonType() == 2)
        entityarrow.addEffect(new PotionEffect(MobEffects.SLOWNESS, 600)); 
      if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, (EntityLivingBase)this) > 0 || getSkeletonType() == 1)
        entityarrow.setFire(100); 
      if (getHeldItemOffhand() != null && getHeldItemOffhand().getItem() == Items.TIPPED_ARROW)
        entityarrow.setPotionEffect(getHeldItemOffhand()); 
      playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity((Entity)entityarrow);
    } 
  }
  
  public boolean isBurning() {
    return (getSkeletonType() == 1) ? false : super.isBurning();
  }
  
  public int getSkeletonType() {
    return (Integer) this.dataManager.get(SKELETON_VARIANT);
  }
  
  public void setSkeletonType(int p_82201_1_) {
    this.dataManager.set(SKELETON_VARIANT, p_82201_1_);
    this.isImmuneToFire = (p_82201_1_ == 1);
    if (p_82201_1_ == 1)
      getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D); 
    if (p_82201_1_ == 1) {
      setSize(0.6F, 2.39F);
    } else {
      setSize(0.5F, 1.95F);
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    if (tagCompund.hasKey("SkeletonType", 99)) {
      int i = tagCompund.getByte("SkeletonType");
      setSkeletonType(i);
    } 
    if (tagCompund.hasKey("Helmets", 99))
      this.helmetCount = tagCompund.getInteger("Helmets"); 
    setCombatTask();
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setByte("SkeletonType", (byte)getSkeletonType());
    tagCompound.setInteger("Helmets", this.helmetCount);
  }
  
  public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
    super.setItemStackToSlot(slotIn, stack);
    if (!this.world.isRemote && slotIn == EntityEquipmentSlot.MAINHAND)
      setCombatTask(); 
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.82F) : (this.height * 0.84F);
  }
  
  public double getYOffset() {
    return (getSkeletonType() == 1) ? (super.getYOffset() - 0.54D) : (super.getYOffset() - 0.45D);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (false && stack.isEmpty() && player.isSneaking() && getRidingEntity() == null) {
      List<EntitySkeletonHorse> list = this.world.getEntitiesWithinAABB(EntitySkeletonHorse.class, getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty() && !isBeingRidden())
          for (EntitySkeletonHorse entity : list) {
              if (entity != null && !entity.isBeingRidden() && !this.world.isRemote) {
                  entity.setHorseTamed(true);
                  entity.setRearing(true);
                  entity.ticksExisted = 0;
                  startRiding((Entity) entity);
                  playSound(SoundEvents.ENTITY_HORSE_ARMOR, 1.0F, 1.0F);
                  break;
              }
          }
      return true;
    } 
    if (!stack.isEmpty() && stack.getItem() == Items.LEATHER_HELMET) {
      this.helmetCount++;
      playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
      player.swingArm(hand);
      if (!this.world.isRemote)
        stack.shrink(1); 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD || stack.getItem() == Items.BONE || stack.getItem() == Item.getItemFromBlock(Blocks.END_ROD) || stack.getItem() == Items.FEATHER) && stack.getItem() != Items.LEATHER_HELMET) {
      setItemStackToSlot(EntityEquipmentSlot.HEAD, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.HEAD, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST) {
      setItemStackToSlot(EntityEquipmentSlot.CHEST, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.CHEST, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS) {
      setItemStackToSlot(EntityEquipmentSlot.LEGS, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.LEGS, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.FEET) {
      setItemStackToSlot(EntityEquipmentSlot.FEET, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.FEET, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty() && stack.getItem() != Items.NAME_TAG && (getSlotForItemStack(stack) == EntityEquipmentSlot.MAINHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || stack.getItem() == Items.BOW)) {
      playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 2.0F);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.OFFHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || (stack.getItem() instanceof net.minecraft.item.ItemFood && !(stack.getItem() instanceof net.minecraft.item.ItemAppleGold)) || stack.getItem() == Items.TIPPED_ARROW || stack.getItem().getItemUseAction(stack) == EnumAction.BLOCK || stack.getItem() == Items.TOTEM_OF_UNDYING)) {
      playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 2.0F);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    return false;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (getTotalArmorValue() > 10) ? ESound.metalHit : (EngenderConfig.mobs.useMobTalkerModels ? super.getRegularHurtSound() : ESound.woodHit);
  }
  
  protected SoundEvent getPierceHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getPierceHurtSound() : ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.woodHitCrush;
  }
}

