package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicates;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemPEGun;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedBowAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntitySmallFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntitySnowballHarmful;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityZombie extends EntityTameBase implements IRangedAttackMob, Undead {
  private static final UUID UUID1 = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A1");
  
  private static final UUID UUID2 = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A2");
  
  private static final UUID UUID3 = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A3");
  
  private static final AttributeModifier attackDamageBoost1 = new AttributeModifier(UUID1, "First Boost", 1.0D, 0);
  
  private static final AttributeModifier attackDamageBoost2 = new AttributeModifier(UUID2, "Second Boost", 1.0D, 0);
  
  private static final AttributeModifier attackDamageBoost3 = new AttributeModifier(UUID3, "Third Boost", 1.0D, 0);
  
  protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (new RangedAttribute(null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
  
  private static final DataParameter<Integer> ZOMBIE_VARIANT = EntityDataManager.createKey(EntityZombie.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> VILLAGER_TYPE = EntityDataManager.createKey(EntityZombie.class, DataSerializers.VARINT);
  
  private static final DataParameter<Boolean> CONVERTING = EntityDataManager.createKey(EntityZombie.class, DataSerializers.BOOLEAN);
  
  private int conversionTime;
  
  private int helmetCount = 1;
  
  private final EntityAIAttackRangedBowAlly<EntityZombie> aiArrowAttack = new EntityAIAttackRangedBowAlly<>(this, 1.0D, 5, 15.0F);
  
  private final EntityAIAttackRangedAlly aiRangedAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 15.0F);
  
  private final EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  public EntityZombie(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(0, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D, 80));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    setSize(0.5F, 1.95F);
    this.experienceValue = 5;
    if (worldIn != null && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn))
      setCombatTask(); 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
    Random random = compatRandom();
    getAttributeMap().registerAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(random.nextDouble() * 0.10000000149011612D);
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(ZOMBIE_VARIANT, 0);
    getDataManager().register(VILLAGER_TYPE, 0);
    getDataManager().register(CONVERTING, Boolean.FALSE);
  }
  
  public String getDescName() {
    if (isChild())
      return "zombie_baby"; 
    if (isVillager())
      return "zombie_villager"; 
    switch (getZombieType()) {
      case 1:
        return "zombie_husk";
      case 2:
        return "zombie_prison";
    } 
    return "zombie";
  }
  
  public int getNextLevelRequirement() {
    return (getTier() == EnumTier.TIER4) ? 100 : 25;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityZombie(this.world);
  }
  
  public boolean canBeMatedWith() {
    return super.canBeMatedWith();
  }
  
  protected float getSoundPitch() {
    return (EngenderConfig.mobs.useMobTalkerModels && (isVillager() || isChild())) ? (super.getSoundPitch() + 0.25F) : super.getSoundPitch();
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString(this);
      if (str == null)
        str = "generic"; 
      switch (getZombieType()) {
        case 1:
          return I18n.translateToLocal("entity.HuskHelpful.cmm.name");
        case 2:
          return I18n.translateToLocal("entity.PrisonZombieHelpful.cmm.name");
      } 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    switch (getZombieType()) {
      case 1:
        return I18n.translateToLocal("entity.HuskHelpful.name");
      case 2:
        return I18n.translateToLocal("entity.PrisonZombieHelpful.name");
    } 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public EnumTier getTier() {
    return (getZombieType() != 0) ? EnumTier.TIER4 : EnumTier.TIER3;
  }
  
  public boolean isVillager() {
    return isAntiMob() ? false : ((getDataManager().get(VILLAGER_TYPE) > 0));
  }
  
  public int getVillagerType() {
    return getDataManager().get(VILLAGER_TYPE) - 1;
  }
  
  public void setVillagerType(int villagerType) {
    getDataManager().set(VILLAGER_TYPE, isAntiMob() ? 0 : (villagerType + 1));
  }
  
  public int getZombieType() {
    return getDataManager().get(ZOMBIE_VARIANT) - 1;
  }
  
  public void setZombieType(int villagerType) {
    getDataManager().set(ZOMBIE_VARIANT, villagerType + 1);
  }
  
  public void setToNotVillager() {
    getDataManager().set(VILLAGER_TYPE, 0);
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
    if (getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) != null && (getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.FLINT_AND_STEEL || getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.FIRE_CHARGE))
      entity.setFire(12); 
    if (isBurning() && this.rand.nextFloat() < f * 0.5F)
      entity.setFire(5 * (int)f); 
    if (getZombieType() == 1 && entity instanceof EntityLivingBase) {
      entity.motionY += 0.2D;
      inflictCustomStatusEffect((this.world.getDifficulty() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.HUNGER, 200 * (int)f, 0);
      if (this.world.getDifficulty() == EnumDifficulty.HARD)
        inflictCustomStatusEffect((this.world.getDifficulty() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.WEAKNESS, 200, 0); 
    } 
    if (getZombieType() == 2 && entity instanceof EntityLivingBase) {
      entity.motionX = 0.0D;
      entity.motionY = 0.0D;
      entity.motionZ = 0.0D;
      inflictCustomStatusEffect(this.world.getDifficulty(), (EntityLivingBase)entity, MobEffects.SLOWNESS, 5, 1);
      if (entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != null && this.world.getDifficulty() == EnumDifficulty.HARD && this.rand.nextInt(3) == 0)
        ((EntityLiving)entity).setAttackTarget(null); 
    } 
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(700);
  }
  
  protected boolean isMovementBlocked() {
    return (getSpecialAttackTimer() > 600 || super.isMovementBlocked());
  }
  
  public void createChild() {
    playSound(isVillager() ? ESound.girlMoan : ESound.guyDeath, 1.0F, getSoundPitch() + 0.1F);
    playSound(getDeathSound(), 1.0F, getSoundPitch() + 0.15F);
    int i;
    for (i = 0; i < 10; i++)
      spawnHeartParticle(); 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isVillager())
      for (i = 0; i < 1 + this.rand.nextInt(2); i++) {
        EntityZombie baby = new EntityZombie(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-48000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        baby.setVillagerType(getVillagerType());
        baby.setZombieType(getZombieType());
        this.world.spawnEntity(baby);
      }  
  }
  
  public void onLivingUpdate() {
    if (isVillager() && (getZombieType() == 1 || getZombieType() == 2 || getZombieType() == 3))
      setToNotVillager(); 
    IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    if (getHealth() < getMaxHealth() * 0.75D && !iattributeinstance.hasModifier(attackDamageBoost1))
      iattributeinstance.applyModifier(attackDamageBoost1); 
    if (getHealth() < getMaxHealth() * 0.5F && !iattributeinstance.hasModifier(attackDamageBoost2))
      iattributeinstance.applyModifier(attackDamageBoost2); 
    if (getHealth() < getMaxHealth() * 0.25F && !iattributeinstance.hasModifier(attackDamageBoost3))
      iattributeinstance.applyModifier(attackDamageBoost3); 
    if (getHealth() >= getMaxHealth() * 0.75D && iattributeinstance.hasModifier(attackDamageBoost1))
      iattributeinstance.removeModifier(attackDamageBoost1); 
    if (getHealth() >= getMaxHealth() * 0.5F && iattributeinstance.hasModifier(attackDamageBoost2))
      iattributeinstance.removeModifier(attackDamageBoost2); 
    if (getHealth() >= getMaxHealth() * 0.25F && iattributeinstance.hasModifier(attackDamageBoost3))
      iattributeinstance.removeModifier(attackDamageBoost3); 
    if (getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemAppleGold && isVillager() && isPotionActive(MobEffects.WEAKNESS)) {
      if (this.ticksExisted > 53)
        this.ticksExisted = 20; 
      swingArm(EnumHand.MAIN_HAND);
      setActiveHand(EnumHand.MAIN_HAND);
      if (this.ticksExisted % 2 == 0) {
        this.rotationPitch = 40.0F;
      } else {
        this.rotationPitch = 0.0F;
      } 
      if (this.ticksExisted == 50) {
        for (int ai = 0; ai < ((ItemFood)getHeldItemMainhand().getItem()).getHealAmount(getHeldItemMainhand()); ai++)
          spawnHeartParticle(); 
        heal(((ItemFood)getHeldItemMainhand().getItem()).getHealAmount(getHeldItemMainhand()));
        playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        startConversion(200);
      } 
    } 
    if (isEntityAlive() && getHeldItemOffhand().getItem() instanceof net.minecraft.item.ItemAppleGold && isVillager() && isPotionActive(MobEffects.WEAKNESS)) {
      if (this.ticksExisted > 53)
        this.ticksExisted = 20; 
      if (this.ticksExisted % 2 == 0) {
        this.rotationPitch = 40.0F;
      } else {
        this.rotationPitch = 0.0F;
      } 
      swingArm(EnumHand.OFF_HAND);
      setActiveHand(EnumHand.OFF_HAND);
      if (this.ticksExisted == 50) {
        spawnHeartParticle();
        heal(((ItemFood)getHeldItemOffhand().getItem()).getHealAmount(getHeldItemOffhand()));
        playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        startConversion(200);
      } 
    } 
    if (getOwner() != null)
      if (getDistanceSq(getOwner()) >= 2304.0D && isElytraFlying()) {
        double d01 = (getOwner()).posX - this.posX;
        double d11 = (getOwner()).posY - this.posY;
        double d21 = (getOwner()).posZ - this.posZ;
        float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
        this.motionX = d01 / f2 * 0.5D * 0.5D + this.motionX * 0.5D;
        this.motionY = d11 / f2 * 0.5D * 0.5D + this.motionZ * 0.5D;
        this.motionZ = d21 / f2 * 0.5D * 0.5D + this.motionZ * 0.5D;
        faceEntity(getOwner(), 180.0F, 180.0F);
      }  
    if (this.world.canSeeSky(getPosition()) && ((getAttackTarget() != null && this.onGround) || (!isWild() && (getOwner()).posY > this.posY && getOwner().isElytraFlying())) && getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null && getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
      this.motionY = 1.0D;
      setFlag(7, true);
    } 
    if (!this.onGround && getItemStackFromSlot(EntityEquipmentSlot.CHEST) != null && getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA && !isElytraFlying())
      setFlag(7, true); 
    if (isElytraFlying())
      this.renderYawOffset = this.rotationYaw = -((float)MathHelper.atan2(this.motionX, this.motionZ)) * 57.295776F; 
    if (!(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie)) {
      if (isHero() && getSpecialAttackTimer() > 600) {
        this.motionX = 0.0D;
        this.motionZ = 0.0D;
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(32.0D, 32.0D, 32.0D), Predicates.and(EntitySelectors.IS_ALIVE));
        if (list != null && !list.isEmpty())
            for (EntityLivingBase entity : list) {
                if (entity != null)
                    if (!false) {
                        entity.hurtResistantTime = 0;
                        inflictEngenderMobDamage(entity, " was yelled at to death by ", (new DamageSource("yell")).setDamageBypassesArmor(), 0.05F);
                    }
            }
      } 
      if (isHero() && getSpecialAttackTimer() > 600 && getSpecialAttackTimer() < 640)
        this.rotationPitch = -50.0F; 
      if (isHero() && getSpecialAttackTimer() == 600) {
        List<EntityTameBase> list = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow(32.0D, 32.0D, 32.0D), Predicates.and(EntitySelectors.IS_ALIVE));
        if (list != null && !list.isEmpty())
            for (EntityTameBase entity : list) {
                if (entity != null)
                    if (false) {
                        this.moralRaisedTimer = 600;
                        entity.moralRaisedTimer = 600;
                    }
            }
      } 
      if (isHero() && getSpecialAttackTimer() == 640) {
        if (isChild()) {
          playSound(ESound.zombieSpecial, 10.0F, 1.5F);
        } else {
          playSound(ESound.zombieSpecial, 10.0F, 1.0F);
        } 
        spawnZombieAlly();
      } 
      if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
        performSpecialAttack(); 
    } 
    if (this.helmetCount < 0)
      this.helmetCount = 0; 
    if ((this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie || isAntiMob() || getZombieType() == 1 || isChild() || isHero()) && this.helmetCount != 0) {
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        dropItem(Items.LEATHER_HELMET, 1);
      this.helmetCount--;
    } 
    ItemStack head = getItemStackFromSlot(EntityEquipmentSlot.HEAD);
    if (!head.isEmpty() && head.getItem() == Items.LEATHER_HELMET && isEntityAlive() && !this.world.isDaytime() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      swingArm(EnumHand.MAIN_HAND);
      this.helmetCount++;
      setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
      playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
    } 
    if (isEntityAlive() && this.world.isDaytime() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !isChild() && !isImmuneToFire() && !isAntiMob() && getZombieType() != 1 && !isHero()) {
      float f = getBrightness();
      if (f > 0.5F && this.ticksExisted % (!getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() ? 80 : 10) == 0 && this.world.canSeeSky(new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ))) {
        boolean flag = true;
        ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if (!itemstack.isEmpty()) {
          if (itemstack.isItemStackDamageable()) {
            itemstack.damageItem(1, this);
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
            this.helmetCount--;
            setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.LEATHER_HELMET));
            playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
          } else {
            setFire(8);
          }  
      } 
    } 
    super.onLivingUpdate();
  }
  
  public void onUpdate() {
    ItemStack hats = (this.helmetCount > 0) ? new ItemStack(Items.LEATHER_HELMET, this.helmetCount) : ItemStack.EMPTY;
    this.basicInventory.setInventorySlotContents(7, hats);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isConverting()) {
      int i = getConversionTimeBoost();
      this.conversionTime -= i;
      if (this.conversionTime <= 0)
        convertToVillager(); 
    } 
    super.onUpdate();
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
  
  protected SoundEvent getAmbientSound() {
    return (getZombieType() == 1) ? SoundEvents.ENTITY_HUSK_AMBIENT : (isVillager() ? SoundEvents.ENTITY_ZOMBIE_VILLAGER_AMBIENT : SoundEvents.ENTITY_ZOMBIE_AMBIENT);
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(isVillager() ? ESound.girlHurt : ESound.guyHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return (getZombieType() == 1) ? SoundEvents.ENTITY_HUSK_HURT : (isVillager() ? SoundEvents.ENTITY_ZOMBIE_VILLAGER_HURT : SoundEvents.ENTITY_ZOMBIE_HURT);
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(isVillager() ? ESound.girlDeath : ESound.guyDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return (getZombieType() == 1) ? SoundEvents.ENTITY_HUSK_DEATH : (isVillager() ? SoundEvents.ENTITY_ZOMBIE_VILLAGER_DEATH : SoundEvents.ENTITY_ZOMBIE_DEATH);
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie)
      playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F / getFittness()); 
    playSound((getZombieType() == 1) ? SoundEvents.ENTITY_HUSK_STEP : (isVillager() ? SoundEvents.ENTITY_ZOMBIE_VILLAGER_STEP : SoundEvents.ENTITY_ZOMBIE_STEP), 0.15F, 1.0F / getFittness());
  }
  
  public void onDeath(DamageSource cause) {
    super.onDeath(cause);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getLimitedLife() <= 0)
      for (int i = 0; i < this.helmetCount; i++) {
        dropItem(Items.LEATHER_HELMET, 1);
        this.helmetCount--;
      }  
    if (cause.getTrueSource() instanceof EntityCreeper && ((EntityCreeper)cause.getTrueSource()).getPowered())
      entityDropItem(new ItemStack(Items.SKULL, 1, 2), 0.0F); 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    switch (getZombieType()) {
      case 1:
        return ELoot.ENTITIES_HUSK;
      case 2:
        return ELoot.ENTITIES_PRISON_ZOMBIE;
    } 
    if (isVillager())
      return ELoot.ENTITIES_ZOMBIE_VILLAGER; 
    return ELoot.ENTITIES_ZOMBIE;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
    super.dropFewItems(p_70628_1_, p_70628_2_);
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    if (this.rand.nextFloat() < ((this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.1F : 0.05F)) {
      int i = this.rand.nextInt(3);
      if (i == 0) {
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        if (getRNG().nextInt(3) == 0)
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.IRON_SWORD)); 
      } else {
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
        if (getRNG().nextInt(3) == 0)
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.IRON_SHOVEL)); 
      } 
    } 
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
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("ZombieType", getZombieType());
    if (isVillager()) {
      tagCompound.setBoolean("IsVillager", true);
      tagCompound.setInteger("VillagerProfession", getVillagerType());
    } 
    tagCompound.setInteger("ConversionTime", isConverting() ? this.conversionTime : -1);
    tagCompound.setInteger("Helmets", this.helmetCount);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setZombieType(tagCompund.getInteger("ZombieType"));
    if (tagCompund.getBoolean("IsVillager"))
      if (tagCompund.hasKey("VillagerProfession", 99)) {
        setVillagerType(tagCompund.getInteger("VillagerProfession"));
      } else {
        setVillagerType(this.world.rand.nextInt(5));
      }  
    if (tagCompund.hasKey("ConversionTime", 99) && tagCompund.getInteger("ConversionTime") > -1)
      startConversion(tagCompund.getInteger("ConversionTime")); 
    if (tagCompund.hasKey("Helmets", 99))
      this.helmetCount = tagCompund.getInteger("Helmets"); 
    setCombatTask();
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    super.onKillEntity(entityLivingIn);
    if ((this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof net.minecraft.entity.passive.EntityVillager) {
      net.minecraft.entity.passive.EntityVillager entityvillager = (net.minecraft.entity.passive.EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.world);
      entityzombie.rotationPitch = entityvillager.rotationPitch;
      entityzombie.renderYawOffset = entityzombie.rotationYaw = entityzombie.rotationYawHead = entityvillager.rotationYawHead;
      entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
      this.world.removeEntity(entityLivingIn);
      entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombie)), new GroupData(false, true));
      entityzombie.setVillagerType(entityvillager.getProfession());
      entityzombie.setChild(entityLivingIn.isChild());
      entityzombie.setNoAI(entityvillager.isAIDisabled());
      if (!isWild())
        entityzombie.setOwnerId(getOwnerId()); 
      if (entityvillager.hasCustomName())
        entityzombie.setCustomNameTag(entityvillager.getCustomNameTag()); 
      this.world.spawnEntity(entityzombie);
      this.world.playEvent(null, 1026, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
    } 
    if (entityLivingIn instanceof net.minecraft.entity.passive.EntityVillager) {
      net.minecraft.entity.passive.EntityVillager entityvillager = (net.minecraft.entity.passive.EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.world);
      entityzombie.rotationPitch = entityvillager.rotationPitch;
      entityzombie.renderYawOffset = entityzombie.rotationYaw = entityzombie.rotationYawHead = entityvillager.rotationYawHead;
      entityzombie.copyLocationAndAnglesFrom(entityLivingIn);
      this.world.removeEntity(entityLivingIn);
      entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombie)), new GroupData(false, true));
      entityzombie.setVillagerType(entityvillager.getProfession());
      entityzombie.setChild(entityLivingIn.isChild());
      entityzombie.setNoAI(entityvillager.isAIDisabled());
      if (!isWild())
        entityzombie.setOwnerId(getOwnerId()); 
      if (entityvillager.hasCustomName())
        entityzombie.setCustomNameTag(entityvillager.getCustomNameTag()); 
      this.world.spawnEntity(entityzombie);
      this.world.playEvent(null, 1026, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
    } 
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? ((getZombieType() == 1) ? (this.height * 0.9F) : (this.height * 0.84F)) : ((getZombieType() == 1) ? (this.height * 0.9F) : (this.height * 0.87F));
  }
  
  protected boolean canEquipItem(ItemStack p_175448_1_) {
    return (p_175448_1_.getItem() == Items.EGG && isChild() && isRiding()) ? false : super.canEquipItem(p_175448_1_);
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    float f = difficulty.getClampedAdditionalDifficulty();
    if (livingdata == null)
      livingdata = new GroupData((this.world.rand.nextFloat() < ForgeModContainer.zombieBabyChance), (this.world.rand.nextFloat() < 0.05F)); 
    if (livingdata instanceof GroupData) {
      GroupData groupdata = (GroupData)livingdata;
      Biome biome = this.world.getBiome(new BlockPos(this));
      if (biome instanceof net.minecraft.world.biome.BiomeDesert && this.rand.nextInt(5) != 0) {
        setZombieType(1);
        setToNotVillager();
      } 
      if (groupdata.isVillager)
        setVillagerType(this.rand.nextInt(5)); 
      if (groupdata.isChild) {
        setChild(true);
        setGrowingAge(-60000);
        if (this.world.rand.nextFloat() < 0.05D) {
          List<EntityChicken> list = this.world.getEntitiesWithinAABB(EntityChicken.class, getEntityBoundingBox().grow(5.0D, 3.0D, 5.0D), EntitySelectors.IS_STANDALONE);
          if (!list.isEmpty()) {
            EntityChicken entitychicken = list.get(0);
            entitychicken.setChickenJockey(true);
            startRiding(entitychicken);
            entitychicken.setOwnerId(getOwnerId());
          } 
        } else if (this.world.rand.nextFloat() < 0.05D) {
          EntityChicken entitychicken1 = new EntityChicken(this.world);
          entitychicken1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
          entitychicken1.onInitialSpawn(difficulty, null);
          entitychicken1.setChickenJockey(true);
          entitychicken1.setOwnerId(getOwnerId());
          this.world.spawnEntity(entitychicken1);
          startRiding(entitychicken1);
        } 
      } 
    } 
    setEquipmentBasedOnDifficulty(difficulty);
    setEnchantmentBasedOnDifficulty(difficulty);
    if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
      Calendar calendar = this.world.getCurrentDate();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    double d0 = this.rand.nextDouble() * 1.5D * f;
    if (d0 > 1.0D)
      getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2)); 
    if (this.rand.nextFloat() < f * 0.05F) {
      getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
    } 
    return livingdata;
  }
  
  public void becomeAHero() {
    super.becomeAHero();
    getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (false && isChild() && stack.isEmpty() && player.isSneaking() && getRidingEntity() == null) {
      List<EntityChicken> list = this.world.getEntitiesWithinAABB(EntityChicken.class, getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty() && !isBeingRidden())
          for (EntityChicken entity : list) {
              if (entity != null && !entity.isBeingRidden() && false && isChild() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
                  entity.ticksExisted = 0;
                  startRiding(entity);
                  playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
                  break;
              }
          }
      return true;
    } 
    if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie && !stack.isEmpty() && stack.getItem() == Items.SADDLE && getRidingEntity() == null && (hasOwner(player) || false)) {
      playSound(SoundEvents.ENTITY_PIG_SADDLE, 0.5F, 1.0F);
      player.startRiding(this);
      return true;
    } 
    if (!stack.isEmpty() && stack.getItem() == Items.LEATHER_HELMET) {
      this.helmetCount++;
      playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        stack.shrink(1); 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD || stack.getItem() == Items.BONE || stack.getItem() == Item.getItemFromBlock(Blocks.END_ROD) || stack.getItem() == Items.FEATHER) && stack.getItem() != Items.LEATHER_HELMET) {
      setItemStackToSlot(EntityEquipmentSlot.HEAD, stack);
      playEquipSound(stack);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
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
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
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
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
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
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
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
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.OFFHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || (stack.getItem() instanceof ItemFood && (!(stack.getItem() instanceof net.minecraft.item.ItemAppleGold) || (stack.getItem() == Items.GOLDEN_APPLE && stack.getMetadata() == 0 && isVillager() && isPotionActive(MobEffects.WEAKNESS)))) || stack.getItem() == Items.TIPPED_ARROW || stack.getItem().getItemUseAction(stack) == EnumAction.BLOCK || stack.getItem() == Items.TOTEM_OF_UNDYING)) {
      playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 2.0F);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(stack.getTagCompound());
        heldItem.setItemDamage(stack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    return false;
  }
  
  protected void startConversion(int ticks) {
    this.conversionTime = ticks;
    getDataManager().set(CONVERTING, Boolean.TRUE);
    removePotionEffect(MobEffects.WEAKNESS);
    addPotionEffect(new PotionEffect(MobEffects.STRENGTH, ticks, Math.min(this.world.getDifficulty().getId() - 1, 0)));
    this.world.setEntityState(this, (byte)16);
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 16) {
      if (!isSilent())
        this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false); 
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (super.attackEntityFrom(source, amount)) {
      EntityLivingBase entitylivingbase = getAttackTarget();
      if (entitylivingbase == null && source.getTrueSource() instanceof EntityLivingBase)
        entitylivingbase = (EntityLivingBase)source.getTrueSource(); 
      if (entitylivingbase != null && isHero())
        spawnZombieAlly(); 
      return true;
    } 
    return false;
  }
  
  public void spawnZombieAlly() {
    int i = MathHelper.floor(this.posX);
    int j = MathHelper.floor(this.posY);
    int k = MathHelper.floor(this.posZ);
    if (this.rand.nextFloat() < getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).getAttributeValue() && this.world.getGameRules().getBoolean("doMobSpawning"))
      for (int l = 0; l < 50; l++) {
        int i1 = i + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
        int j1 = j + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
        int k1 = k + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
        if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1), EnumFacing.UP) && this.world.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10) {
          EntityZombie entityzombie = new EntityZombie(this.world);
          entityzombie.setPosition(i1, j1, k1);
          if (!this.world.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) && this.world.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), entityzombie) && this.world.getCollisionBoxes(entityzombie, entityzombie.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(entityzombie.getEntityBoundingBox())) {
            this.world.spawnEntity(entityzombie);
            entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityzombie)), null);
            entityzombie.setZombieType(getZombieType());
            entityzombie.setOwnerId(getOwnerId());
            entityzombie.setIsAntiMob(isAntiMob());
            entityzombie.setGrowingAge(getGrowingAge());
            if (isVillager() && this.rand.nextFloat() < 0.25F)
              entityzombie.setVillagerType(this.rand.nextInt(5)); 
            getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).getBaseValue() - 0.05D);
            entityzombie.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0D);
            break;
          } 
        } 
      }  
  }
  
  public boolean isConverting() {
    return getDataManager().get(CONVERTING);
  }
  
  protected void convertToVillager() {
    EntityVillager entityvillager = new EntityVillager(this.world);
    entityvillager.copyLocationAndAnglesFrom(this);
    entityvillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(entityvillager)), null);
    entityvillager.renderYawOffset = entityvillager.rotationYaw = entityvillager.rotationYawHead = this.rotationYawHead;
    entityvillager.rotationPitch = this.rotationPitch;
    entityvillager.setNoAI(isAIDisabled());
    entityvillager.setProfession(getVillagerType());
    entityvillager.setOwnerId(getOwnerId());
    if (hasCustomName())
      entityvillager.setCustomNameTag(getCustomNameTag()); 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (this.helmetCount > 0)
        dropItem(Items.LEATHER_HELMET, this.helmetCount);
    this.world.spawnEntity(entityvillager);
    entityvillager.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
    this.world.playEvent(null, 1027, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), 0);
    this.world.removeEntity(this);
  }
  
  protected int getConversionTimeBoost() {
    int i = 1;
    if (this.rand.nextFloat() < 0.01F) {
      int j = 0;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      for (int k = (int)this.posX - 4; k < (int)this.posX + 4 && j < 14; k++) {
        for (int l = (int)this.posY - 4; l < (int)this.posY + 4 && j < 14; l++) {
          for (int i1 = (int)this.posZ - 4; i1 < (int)this.posZ + 4 && j < 14; i1++) {
            Block block = this.world.getBlockState(blockpos$mutableblockpos.setPos(k, l, i1)).getBlock();
            if (block == Blocks.IRON_BARS || block == Blocks.BED) {
              if (this.rand.nextFloat() < 0.3F)
                i++; 
              j++;
            } 
          } 
        } 
      } 
    } 
    return i;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (getTotalArmorValue() >= 10) ? ESound.metalHit : ESound.fleshHit;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts")) {
      EntityMutantZombie mutant = new EntityMutantZombie(this.world);
      mutant.setZombieType(getZombieType());
      return mutant;
    } 
    return null;
  }
  
  public class GroupData implements IEntityLivingData {
    public boolean isChild;
    
    public boolean isVillager;
    
    public GroupData(boolean isBaby, boolean isVillagerZombie) {
      this.isChild = false;
      this.isVillager = false;
      this.isChild = isBaby;
      this.isVillager = isVillagerZombie;
    }
  }
  
  public void setCombatTask() {
    if (this.world != null && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      this.tasks.removeTask(this.aiAttackOnCollide);
      this.tasks.removeTask(this.aiRangedAttack);
      this.tasks.removeTask(this.aiArrowAttack);
      ItemStack itemstack = getHeldItemMainhand();
      if ((itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemBow) || itemstack.getItem() == Items.SNOWBALL || itemstack.getItem() == Items.FIRE_CHARGE || itemstack.getItem() == Items.EGG) {
        this.tasks.addTask(4, (itemstack.getItem() instanceof net.minecraft.item.ItemBow && getIntelligence() >= 50.0F) ? (EntityAIBase)this.aiArrowAttack : (EntityAIBase)this.aiRangedAttack);
      } else {
        this.tasks.addTask(4, this.aiAttackOnCollide);
      } 
    } 
  }
  
  public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
    super.setItemStackToSlot(slotIn, stack);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && slotIn == EntityEquipmentSlot.MAINHAND)
      setCombatTask(); 
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
        EntityPEGunPellet entitywitherskull = new EntityPEGunPellet(this.world, this, d2, d3, d4);
        entitywitherskull.posX = this.posX + f * 0.35D + vec3.x;
        entitywitherskull.posY = this.posY + getEyeHeight() - 0.10000000149011612D + vec3.y;
        entitywitherskull.posZ = this.posZ + f1 * 0.35D + vec3.z;
        entitywitherskull.damage = ((ItemPEGun)getHeldItemMainhand().getItem()).getProjectileDamage(getHeldItemMainhand());
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitywitherskull);
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 0.5F, 0.5F + getRNG().nextFloat() * 0.25F);
        playSound(ESound.pegunfire, 0.5F, 0.6F + getRNG().nextFloat() * 0.2F + entitywitherskull.damage / 100.0F);
      } else {
        playSound(ESound.pegunjam, 0.5F, 1.0F);
        entityDropItem(getHeldItemMainhand(), 1.4F);
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
      } 
    } else if (getHeldItemMainhand().getItem() == Items.SNOWBALL) {
      EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.world, this);
      double d0 = target.posY + target.getEyeHeight() - 1.15D;
      double d1 = target.posX - this.posX;
      double d2 = d0 - entitysnowball.posY;
      double d3 = target.posZ - this.posZ;
      float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.shoot(d1, d2 + f, d3, 1.6F, 26.0F - getDexterity() / 4.0F);
      playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(entitysnowball);
      swingArm(EnumHand.MAIN_HAND);
      entitysnowball.damage = (target instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || target instanceof net.minecraft.entity.monster.EntityBlaze) ? 3.0F : ((this.rand.nextInt(3) == 0 || !(target instanceof EntityTameBase)) ? 1.0F : 0.0F);
    } else if (getHeldItemMainhand().getItem() == Items.EGG) {
      EntityEgg entitysnowball = new EntityEgg(this.world, this);
      double d0 = target.posY + target.getEyeHeight() - 1.15D;
      double d1 = target.posX - this.posX;
      double d2 = d0 - entitysnowball.posY;
      double d3 = target.posZ - this.posZ;
      float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.shoot(d1, d2 + f, d3, 1.6F, 26.0F - getDexterity() / 4.0F);
      playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(entitysnowball);
      swingArm(EnumHand.MAIN_HAND);
    } else if (getHeldItemMainhand().getItem() == Items.FIRE_CHARGE) {
      double d1 = isChild() ? 0.25D : 0.5D;
      Vec3d vec3 = getLook(1.0F);
      double d2 = target.posX - this.posX + vec3.x * d1;
      double d3 = target.posY + ((target.height > 8.0F) ? 7.0D : (target.height * 0.5D)) - this.posY + 1.0D;
      double d4 = target.posZ - this.posZ + vec3.z * d1;
      EntitySmallFireballOther entitylargefireball = new EntitySmallFireballOther(this.world, this, d2, d3, d4);
      entitylargefireball.posX = this.posX + vec3.x * d1;
      entitylargefireball.posY = this.posY + 1.0D;
      entitylargefireball.posZ = this.posZ + vec3.z * d1;
      float dm = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
      entitylargefireball.damage = dm;
      playSound(SoundEvents.ENTITY_GHAST_SHOOT, 1.0F, 1.5F);
      this.world.spawnEntity(entitylargefireball);
      swingArm(EnumHand.MAIN_HAND);
    } else {
      EntityTippedArrowOther entityarrow = new EntityTippedArrowOther(this.world, this);
      double d0 = target.posX - this.posX;
      double d1 = target.posY + target.getEyeHeight() - this.posY + getEyeHeight() - 0.10000000149011612D;
      double d2 = target.posZ - this.posZ;
      double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
      entityarrow.shoot(d0, d1 + d3 * getDistance(target) * 0.013D, d2, 1.4F, (getIntelligence() < 50.0F) ? (29.0F - getIntelligence() / 5.0F) : (isHero() ? 1.0F : 9.0F));
      int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
      int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this);
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
      if (getZombieType() == 2)
        entityarrow.addEffect(new PotionEffect(MobEffects.SLOWNESS, 600)); 
      if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0)
        entityarrow.setFire(100); 
      if (getHeldItemOffhand() != null && getHeldItemOffhand().getItem() == Items.TIPPED_ARROW)
        entityarrow.setPotionEffect(getHeldItemOffhand()); 
      playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(entityarrow);
    } 
  }
}


