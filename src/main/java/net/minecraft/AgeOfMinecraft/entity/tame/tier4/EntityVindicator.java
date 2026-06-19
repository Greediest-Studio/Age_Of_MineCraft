package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityAbstractIllagers;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIVindicatorBreakDoor;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVindicator extends EntityAbstractIllagers {
  protected static final DataParameter<Byte> DATA_FLAGS_ID = EntityDataManager.createKey(EntityVindicator.class, DataSerializers.BYTE);
  
  private boolean johnny;
  
  public EntityVindicator(World worldIn) {
    super(worldIn);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(3, new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
    this.tasks.addTask(5, new EntityAIWander(this, 0.6D, 80));
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.0D, 32.0F, 6.0F));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    this.experienceValue = 10;
  }
  
  public String getDescName() {
    return "vindicator";
  }
  
  @SideOnly(Side.CLIENT)
  public EntityAbstractIllagers.IllagerArmPose getArmPose() {
    return isAggressive() ? EntityAbstractIllagers.IllagerArmPose.ATTACKING : EntityAbstractIllagers.IllagerArmPose.CROSSED;
  }
  
  public int getNextLevelRequirement() {
    return 150;
  }
  
  public float getBonusVSLight() {
    return 0.75F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 1.5F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityVindicator(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3499999940395355D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(15.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(DATA_FLAGS_ID, (byte) 0);
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.4F) : super.getSoundPitch();
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString(this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString(this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public void createChild() {
    super.createChild();
    if (!this.world.isRemote)
      for (int i = 0; i < 1; i++) {
        EntityVindicator baby = new EntityVindicator(this.world);
        baby.copyLocationAndAnglesFrom(this);
        baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
        baby.setGrowingAge(-60000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(getOwnerId());
        if (isMarried())
          for (int e = 0; e < 10 + this.rand.nextInt(10); e++)
            baby.levelUp();  
        this.world.spawnEntity(baby);
      }  
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_VINDICATION_ILLAGER;
  }
  
  @SideOnly(Side.CLIENT)
  private boolean getVindicatorFlag(int p_190637_1_) {
    int i = this.dataManager.get(DATA_FLAGS_ID);
    return ((i & p_190637_1_) != 0);
  }
  
  private void setVindicatorFlag(int p_190638_1_, boolean p_190638_2_) {
    int i = this.dataManager.get(DATA_FLAGS_ID);
    if (p_190638_2_) {
      i |= p_190638_1_;
    } else {
      i &= p_190638_1_ ^ 0xFFFFFFFF;
    } 
    this.dataManager.set(DATA_FLAGS_ID, (byte) (i & 0xFF));
  }
  
  public void setAggressive(boolean p_190636_1_) {
    setVindicatorFlag(1, p_190636_1_);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isAggressive() {
    return getVindicatorFlag(1);
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    if (this.johnny)
      compound.setBoolean("Johnny", true); 
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    if (compound.hasKey("Johnny", 99))
      this.johnny = compound.getBoolean("Johnny"); 
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
    setEquipmentBasedOnDifficulty(difficulty);
    setEnchantmentBasedOnDifficulty(difficulty);
    return ientitylivingdata;
  }
  
  protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (getAttackTarget() != null)
      setSprinting(true); 
    setAggressive(this.johnny ? true : ((getAttackTarget() != null)));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (false && !stack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty() && stack.getItem() instanceof net.minecraft.item.ItemAxe) {
      playLivingSound();
      playSound(SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, 1.0F, 1.0F);
      player.swingArm(hand);
      if (!this.world.isRemote) {
        heldItem.setTagCompound(stack.getTagCompound());
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.shrink(1);
      } 
      return true;
    } 
    if (false && stack.isEmpty() && player.isSneaking()) {
      dropEquipmentUndamaged();
      player.swingArm(hand);
      playSound(SoundEvents.VINDICATION_ILLAGER_AMBIENT, 1.0F, 1.0F);
      return true;
    } 
    return false;
  }
  
  public void setCustomNameTag(String name) {
    super.setCustomNameTag(name);
    if (!this.johnny && "Johnny".equals(name)) {
      this.ticksExisted = 0;
      playSound(ESound.heresJohnny, 2.0F, 1.0F);
      this.johnny = true;
      ((PathNavigateGround)getNavigator()).setBreakDoors(true);
      this.tasks.addTask(1, new EntityAIVindicatorBreakDoor(this));
    } else {
      this.johnny = false;
      ((PathNavigateGround)getNavigator()).setBreakDoors(false);
      this.tasks.removeTask(new EntityAIVindicatorBreakDoor(this));
    } 
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.VINDICATION_ILLAGER_AMBIENT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() - 0.3F); 
    return SoundEvents.VINDICATION_ILLAGER_DEATH;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() - 0.3F); 
    return SoundEvents.ENTITY_VINDICATION_ILLAGER_HURT;
  }
}

