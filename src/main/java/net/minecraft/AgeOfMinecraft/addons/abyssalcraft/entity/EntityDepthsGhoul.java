package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
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
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityDepthsGhoul extends EntityTameBase implements IRangedAttackMob, Armored, Undead {
  private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityDepthsGhoul.class, DataSerializers.VARINT);
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier ghoulHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);
  
  private static final UUID peteUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A8");
  
  private static final AttributeModifier peteSpeedBoost = new AttributeModifier(peteUUID, "Pete Boost", 0.0699999958276749D, 0);
  
  private static final UUID wilsonUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A8");
  
  private static final AttributeModifier wilsonDamageBoost = new AttributeModifier(wilsonUUID, "Pete Boost", 3.0D, 0);
  
  private int helmetCount = 1;
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 24.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.0D, true);
  
  public EntityDepthsGhoul(World par1World) {
    super(par1World);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    setSize(0.75F, 2.9F);
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityDepthsGhoul(this.world);
  }
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean isEntityImmuneToCoralium() {
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
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    } 
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public String getName() {
    switch (getGhoulType()) {
      case 0:
        return super.getName();
      case 1:
        return "Pete";
      case 2:
        return "Mr. Wilson";
      case 3:
        return "Dr. Orange";
    } 
    return super.getName();
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(TYPE, Integer.valueOf(0));
  }
  
  public int getGhoulType() {
    return ((Integer)this.dataManager.get(TYPE)).intValue();
  }
  
  public void setGhoulType(int par1) {
    IAttributeInstance iattributeinstancepete = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
    IAttributeInstance iattributeinstancewilson = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    iattributeinstancepete.removeModifier(peteSpeedBoost);
    iattributeinstancewilson.removeModifier(wilsonDamageBoost);
    if (par1 == 1)
      iattributeinstancepete.applyModifier(peteSpeedBoost); 
    if (par1 == 2)
      iattributeinstancewilson.applyModifier(wilsonDamageBoost); 
    this.dataManager.set(TYPE, Integer.valueOf(par1));
  }
  
  public void onLivingUpdate() {
    this.isOffensive = true;
    if (getGhoulType() == 3) {
      if (getAttackTarget() != null && (getDistanceSq((Entity)getAttackTarget()) > 36.0D || getAttackTarget() instanceof net.minecraft.entity.EntityFlying || (getAttackTarget()).posY > this.posY + 4.0D)) {
        this.tasks.addTask(2, (EntityAIBase)this.aiArrowAttack);
        this.tasks.removeTask((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.tasks.addTask(2, (EntityAIBase)this.aiAttackOnCollide);
        this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
      } 
    } else {
      this.tasks.addTask(2, (EntityAIBase)this.aiAttackOnCollide);
      this.tasks.removeTask((EntityAIBase)this.aiArrowAttack);
    } 
    if (getGhoulType() != 3)
      this.tasks.addTask(2, (EntityAIBase)this.aiAttackOnCollide); 
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
  
  public void onDeath(DamageSource par1DamageSource) {
    super.onDeath(par1DamageSource);
    if (!this.world.isRemote)
      if (this.helmetCount > 0)
        dropItem((Item)Items.LEATHER_HELMET, this.helmetCount);  
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    swingArm(EnumHand.MAIN_HAND);
    swingArm(EnumHand.OFF_HAND);
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
    switch (getGhoulType()) {
      case 0:
        return ACSounds.ghoul_normal_ambient;
      case 1:
        return ACSounds.ghoul_pete_ambient;
      case 2:
        return ACSounds.ghoul_wilson_ambient;
      case 3:
        return ACSounds.ghoul_orange_ambient;
    } 
    return ACSounds.ghoul_normal_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.ghoul_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.ghoul_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
  }
  
  protected Item getDropItem() {
    return ACItems.coralium_plagued_flesh_on_a_bone;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  protected ResourceLocation getLootTable() {
    switch (getGhoulType()) {
      case 0:
        return ACLoot.ENTITY_DEPTHS_GHOUL;
      case 1:
        return ACLoot.ENTITY_DEPTHS_GHOUL_PETE;
      case 2:
        return ACLoot.ENTITY_DEPTHS_GHOUL_WILSON;
      case 3:
        return ACLoot.ENTITY_DEPTHS_GHOUL_ORANGE;
    } 
    return null;
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    if (par1NBTTagCompound.hasKey("GhoulType")) {
      byte var2 = par1NBTTagCompound.getByte("GhoulType");
      setGhoulType(var2);
    } 
    if (par1NBTTagCompound.hasKey("Helmets", 99))
      this.helmetCount = par1NBTTagCompound.getInteger("Helmets"); 
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    par1NBTTagCompound.setByte("GhoulType", (byte)getGhoulType());
    par1NBTTagCompound.setInteger("Helmets", this.helmetCount);
  }
  
  protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    if (this.rand.nextFloat() < 0.5F) {
      int i = this.rand.nextInt(2);
      float f = (this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.75F : 0.5F;
      if (this.rand.nextFloat() < 0.25F)
        i++; 
      if (this.rand.nextFloat() < 0.125F)
        i++; 
      if (this.rand.nextFloat() < 0.0625F)
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
    if (this.rand.nextFloat() < ((this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.5F : 0.1F)) {
      int i = this.rand.nextInt(3);
      if (i == 0) {
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.refined_coralium_sword));
        if (getRNG().nextInt(2) > 0)
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.refined_coralium_sword)); 
      } else {
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.refined_coralium_shovel));
        if (getRNG().nextInt(2) > 0)
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.refined_coralium_shovel)); 
      } 
    } 
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);
    int type = 0;
    if (this.world.rand.nextFloat() < 0.2F) {
      int temp = this.world.rand.nextInt(4);
      if (temp < 4)
        type = temp; 
    } 
    setGhoulType(type);
    float f = difficulty.getClampedAdditionalDifficulty();
    if (this.world.rand.nextFloat() < ForgeModContainer.zombieBabyChance) {
      setChild(true);
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
    attribute.removeModifier(ghoulHDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.applyModifier(ghoulHDamageBoost); 
    return (IEntityLivingData)data;
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    playSound(ACSounds.ghoul_orange_ambient, getSoundVolume(), getSoundPitch());
    for (int i = 0; i < (isHero() ? 5 : 1); i++) {
      EntitySquads squads = new EntitySquads(this.world, (EntityLivingBase)this);
      double d1 = target.posX + (isHero() ? (this.rand.nextDouble() * 2.0D - 1.0D) : 0.0D) - this.posX;
      double d2 = target.posY + (isHero() ? (this.rand.nextDouble() * 2.0D - 1.0D) : 0.0D) - 0.5D - this.posY + 2.25D;
      double d3 = target.posZ + (isHero() ? (this.rand.nextDouble() * 2.0D - 1.0D) : 0.0D) - this.posZ;
      float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
      squads.shoot(d1, d2 + f, d3, 1.6F, 1.0F);
      this.world.spawnEntity((Entity)squads);
      float f2 = MathHelper.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
      squads.motionX = d1 / f2 * 0.8D * 0.8D + squads.motionX;
      squads.motionY = d2 / f2 * 0.8D * 0.8D + squads.motionY;
      squads.motionZ = d3 / f2 * 0.8D * 0.8D + squads.motionZ;
    } 
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (getTotalArmorValue() >= 10) ? ESound.metalHit : ESound.fleshHit;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (!stack.isEmpty() && stack.getItem() == Items.LEATHER_HELMET) {
      this.helmetCount++;
      playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 1.0F, 1.0F);
      player.swingArm(hand);
      if (!this.world.isRemote)
        stack.shrink(1); 
      return true;
    } 
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.MAINHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || stack.getItem() == Items.BOW)) {
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
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty() && (getSlotForItemStack(stack) == EntityEquipmentSlot.OFFHAND || stack.getItem() instanceof net.minecraft.item.ItemSword || stack.getItem() instanceof net.minecraft.item.ItemTool || (stack.getItem() instanceof net.minecraft.item.ItemFood && !(stack.getItem() instanceof net.minecraft.item.ItemAppleGold)) || stack.getItem() == Items.TIPPED_ARROW || stack.getItem() == Items.SHIELD)) {
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
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD && stack.getItem() != Items.LEATHER_HELMET) {
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
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.CHEST) {
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
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.LEGS) {
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
    if (false && stack.isEmpty() && player.isSneaking()) {
      dropEquipmentUndamaged();
      player.swingArm(hand);
      return true;
    } 
    return false;
  }
}

