package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityOmotholGhoul extends EntityTameBase implements Armored, Undead {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);
  
  public EntityOmotholGhoul(World par1World) {
    super(par1World);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 48.0F, 12.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, false));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    setSize(0.9F, 3.9F);
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 400;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityOmotholGhoul(this.world);
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
  
  public float getBonusVSLight() {
    return 1.25F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.225D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(30.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
    } 
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    swingArm(EnumHand.MAIN_HAND);
    swingArm(EnumHand.OFF_HAND);
    if (par1Entity instanceof EntityLivingBase && !false) {
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 20));
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 20));
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return super.attackEntityAsMob(par1Entity);
  }
  
  protected float getSoundPitch() {
    return super.getSoundPitch() - 0.3F;
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.ghoul_normal_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource souce) {
    return ACSounds.ghoul_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.ghoul_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.25F, 0.9F);
  }
  
  protected Item getDropItem() {
    return ACItems.omothol_flesh;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_OMOTHOL_GHOUL;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public void onLivingUpdate() {
    this.isOffensive = true;
    super.onLivingUpdate();
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    float f = difficulty.getClampedAdditionalDifficulty();
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
    return par1EntityLivingData;
  }
  
  @Nullable
  public static Item getArmorByChance(EntityEquipmentSlot slotIn, int chance) {
    switch (slotIn) {
      case HEAD:
        if (chance == 0)
          return (Item)Items.DIAMOND_HELMET; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_helmet; 
        if (chance == 2)
          return ACItems.plated_coralium_helmet; 
        if (chance == 3)
          return ACItems.dreadium_samurai_helmet; 
        if (chance == 4)
          return ACItems.ethaxium_helmet; 
      case CHEST:
        if (chance == 0)
          return (Item)Items.DIAMOND_CHESTPLATE; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_chestplate; 
        if (chance == 2)
          return ACItems.plated_coralium_chestplate; 
        if (chance == 3)
          return ACItems.dreadium_samurai_chestplate; 
        if (chance == 4)
          return ACItems.ethaxium_chestplate; 
      case LEGS:
        if (chance == 0)
          return (Item)Items.DIAMOND_LEGGINGS; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_leggings; 
        if (chance == 2)
          return ACItems.plated_coralium_leggings; 
        if (chance == 3)
          return ACItems.dreadium_samurai_leggings; 
        if (chance == 4)
          return ACItems.ethaxium_leggings; 
      case FEET:
        if (chance == 0)
          return (Item)Items.DIAMOND_BOOTS; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_boots; 
        if (chance == 2)
          return ACItems.plated_coralium_boots; 
        if (chance == 3)
          return ACItems.dreadium_samurai_boots; 
        if (chance == 4)
          return ACItems.ethaxium_boots; 
        break;
    } 
    return null;
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
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.ethaxium_sword));
        if (getRNG().nextInt(2) > 0)
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.ethaxium_sword)); 
      } else {
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.ethaxium_shovel));
        if (getRNG().nextInt(2) > 0)
          setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.ethaxium_shovel)); 
      } 
    } 
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (getTotalArmorValue() >= 10) ? ESound.metalHit : ESound.fleshHit;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
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
    if (!stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && getSlotForItemStack(stack) == EntityEquipmentSlot.HEAD) {
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

