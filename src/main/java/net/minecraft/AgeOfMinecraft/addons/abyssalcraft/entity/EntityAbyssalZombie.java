package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Loader;

public class EntityAbyssalZombie extends EntityTameBase implements Undead {
  private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityAbyssalZombie.class, DataSerializers.VARINT);
  
  private static final DataParameter<Byte> CHILD = EntityDataManager.createKey(EntityAbyssalZombie.class, DataSerializers.BYTE);
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);
  
  private int helmetCount = 1;
  
  public EntityAbyssalZombie(World par1World) {
    super(par1World);
    setSize(0.5F, 1.95F);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 9.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityAbyssalZombie(this.world);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    } 
  }
  
  public void onDeath(DamageSource par1DamageSource) {
    super.onDeath(par1DamageSource);
    if (!this.world.isRemote && getLimitedLife() <= 0)
      for (int i = 0; i < this.helmetCount; i++) {
        dropItem((Item)Items.LEATHER_HELMET, 1);
        this.helmetCount--;
      }  
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(CHILD, Byte.valueOf((byte)0));
    this.dataManager.register(TYPE, Integer.valueOf(0));
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public int getZombieType() {
    return ((Integer)this.dataManager.get(TYPE)).intValue();
  }
  
  public void setZombieType(int par1) {
    this.dataManager.set(TYPE, Integer.valueOf(par1));
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(700);
  }
  
  protected boolean isMovementBlocked() {
    return (getSpecialAttackTimer() > 600 || super.isMovementBlocked());
  }
  
  public void onLivingUpdate() {
    if (isHero() && getSpecialAttackTimer() > 600) {
      this.motionX = 0.0D;
      this.motionZ = 0.0D;
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(32.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!false) {
              entity.hurtResistantTime = 0;
              entity.attackEntityFrom(DamageSource.WITHER, 0.05F);
              if (EngenderConfig.general.useMessage && !entity.isEntityAlive() && !isWild())
                getOwner().sendMessage((ITextComponent)new TextComponentTranslation(entity.getName() + " was yelled at to death by " + getName() + " (" + getOwner().getName() + ")", new Object[0])); 
            }  
        }  
    } 
    if (isHero() && getSpecialAttackTimer() > 600 && getSpecialAttackTimer() < 660)
      this.rotationPitch = -50.0F; 
    if (isHero() && getSpecialAttackTimer() == 600) {
      List<EntityTameBase> list = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow(32.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null)
            if (false) {
              this.moralRaisedTimer = 600;
              entity.moralRaisedTimer = 600;
            }  
        }  
    } 
    if (isHero() && getSpecialAttackTimer() == 660)
      if (isChild()) {
        playSound(ESound.zombieSpecial, 10.0F, 1.25F);
      } else {
        playSound(ESound.zombieSpecial, 10.0F, 0.75F);
      }  
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (this.helmetCount < 0)
      this.helmetCount = 0; 
    if ((isChild() || isHero()) && this.helmetCount != 0) {
      if (!this.world.isRemote)
        dropItem((Item)Items.LEATHER_HELMET, 1); 
      this.helmetCount--;
    } 
    if (this.world.isDaytime() && !this.world.isRemote && !isChild() && !isImmuneToFire() && !isHero()) {
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
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag) {
      float f = this.world.getDifficultyForLocation(new BlockPos((Entity)this)).getAdditionalDifficulty();
      if (getItemStackFromSlot(EntityEquipmentSlot.MAINHAND) != null && (getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.FLINT_AND_STEEL || getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == Items.FIRE_CHARGE))
        par1Entity.setFire(12); 
      if (isBurning() && this.rand.nextFloat() < f * 0.5F)
        par1Entity.setFire(5 * (int)f); 
      if (flag)
        if (par1Entity instanceof EntityLivingBase && 
          !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
          ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));  
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.abyssal_zombie_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.abyssal_zombie_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.abyssal_zombie_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
  }
  
  protected Item getDropItem() {
    return ACItems.coralium_plagued_flesh;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_ABYSSAL_ZOMBIE;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    if (par1NBTTagCompound.getBoolean("IsBaby"))
      setChild(true); 
    if (par1NBTTagCompound.hasKey("ZombieType")) {
      byte var2 = par1NBTTagCompound.getByte("ZombieType");
      setZombieType(var2);
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    if (isChild())
      par1NBTTagCompound.setBoolean("IsBaby", true); 
    par1NBTTagCompound.setByte("ZombieType", (byte)getZombieType());
  }
  
  protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
    if (!InitHandler.INSTANCE.isItemBlacklisted((Entity)this, itemEntity.getItem()))
      super.updateEquipmentIfNeeded(itemEntity); 
  }
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (false && isChild() && stack.isEmpty() && player.isSneaking() && getRidingEntity() == null) {
      List<EntityChicken> list = this.world.getEntitiesWithinAABB(EntityChicken.class, getEntityBoundingBox().grow(16.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
      if (list != null && !list.isEmpty() && !isBeingRidden())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityChicken entity = list.get(i1);
          if (entity != null && !entity.isBeingRidden() && false && isChild() && !this.world.isRemote) {
            entity.ticksExisted = 0;
            startRiding((Entity)entity);
            playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
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
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.FEET) {
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
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.MAINHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || stack.getItem() == Items.BOW)) {
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
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.OFFHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || (stack.getItem() instanceof net.minecraft.item.ItemFood && !(stack.getItem() instanceof net.minecraft.item.ItemAppleGold)) || stack.getItem() == Items.TIPPED_ARROW || stack.getItem() == Items.SHIELD)) {
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
    if (false && stack.isEmpty() && player.isSneaking()) {
      dropEquipmentUndamaged();
      player.swingArm(hand);
      return true;
    } 
    return false;
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);
    float f = difficulty.getClampedAdditionalDifficulty();
    if (data == null)
      data = new GroupData((this.world.rand.nextFloat() < ForgeModContainer.zombieBabyChance), null); 
    if (data instanceof GroupData) {
      GroupData groupdata = (GroupData)data;
      if (groupdata.isBaby)
        setGrowingAge(-60000); 
    } 
    setEquipmentBasedOnDifficulty(difficulty);
    setEnchantmentBasedOnDifficulty(difficulty);
    if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
      Calendar calendar1 = this.world.getCurrentDate();
      if (calendar1.get(2) + 1 == 10 && calendar1.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    Calendar calendar = this.world.getCurrentDate();
    attribute.removeModifier(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.applyModifier(attackDamageBoost); 
    return (IEntityLivingData)data;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts")) {
      EntityMutantZombie mutant = new EntityMutantZombie(this.world);
      mutant.setZombieType(3);
      return (EntityTameBase)mutant;
    } 
    return null;
  }
  
  class GroupData implements IEntityLivingData {
    public boolean isBaby;
    
    private GroupData(boolean par2) {
      this.isBaby = false;
      this.isBaby = par2;
    }
    
    GroupData(boolean par2, Object par4EntityZombieINNER1) {
      this(par2);
    }
  }
}

