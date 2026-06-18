package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSnowGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntitySnowballHarmful;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class EntitySnowman extends EntityTameBase implements IRangedAttackMob, Light, Elemental {
  private static final DataParameter<Byte> PUMPKIN_EQUIPPED = EntityDataManager.createKey(EntitySnowman.class, DataSerializers.BYTE);
  
  public EntitySnowman(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      setSize(0.45F, 1.75F);
    } else {
      setSize(0.7F, 1.9F);
    } 
    this.isOffensive = true;
    setPathPriority(PathNodeType.WATER, -1.0F);
    setPathPriority(PathNodeType.LAVA, -1.0F);
    setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
    setPathPriority(PathNodeType.DAMAGE_FIRE, -1.0F);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 24.0F, 6.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 1.25D, 30, 16.0F));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.targetTasks.addTask(0, (EntityAIBase)new EntityAIFriendlyHurtByTarget((EntityCreature)this, true, new Class[0]));
    this.targetTasks.addTask(1, (EntityAIBase)new EntityAILeaderHurtByTarget(this));
    this.targetTasks.addTask(2, (EntityAIBase)new EntityAILeaderHurtTarget(this));
    this.experienceValue = 1;
  }
  
  public String getDescName() {
    return isPumpkinEquipped() ? "snowman" : "snowgolem";
  }
  
  public float getBonusVSArmored() {
    return 0.25F;
  }
  
  public float getBonusVSMassive() {
    return 0.25F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public int timesToConvert() {
    return 3;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySnowman(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(PUMPKIN_EQUIPPED, Byte.valueOf((byte)0));
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return (EntityTameBase)new EntityMutantSnowGolem(this.world); 
    return null;
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
    if (!this.world.isRemote) {
      EntitySnowman baby = new EntitySnowman(this.world);
      baby.copyLocationAndAnglesFrom((Entity)this);
      baby.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      baby.setGrowingAge(-24000);
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
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.CONSTRUCT;
  }
  
  public void onLivingUpdate() {
    ItemStack charge = isPumpkinEquipped() ? new ItemStack(Blocks.PUMPKIN) : ItemStack.EMPTY;
    charge.setStackDisplayName("Pumpkin Sheared");
    this.basicInventory.setInventorySlotContents(7, charge);
    super.onLivingUpdate();
    if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false)
      if (getDistanceSq((Entity)getAttackTarget()) < (this.reachWidth * this.reachWidth + ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width) * ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width)) + 9.0D && (this.ticksExisted + getEntityId()) % 20 == 0) {
        attackEntityAsMob((Entity)getAttackTarget());
        swingArm(EnumHand.MAIN_HAND);
        if (!getHeldItemOffhand().isEmpty())
          swingArm(EnumHand.OFF_HAND); 
      }  
    if (!this.world.isRemote) {
      int i = MathHelper.floor(this.posX);
      int j = MathHelper.floor(this.posY);
      int k = MathHelper.floor(this.posZ);
      if (isWet() || this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F)
        attackEntityFrom((new DamageSource("melt")).setFireDamage().setDamageBypassesArmor(), 1.0F); 
      if (!EngenderConfig.mobs.grief)
        return; 
      for (int l = 0; l < 4; l++) {
        i = MathHelper.floor(this.posX + ((l % 2 * 2 - 1) * 0.25F));
        j = MathHelper.floor(this.posY);
        k = MathHelper.floor(this.posZ + ((l / 2 % 2 * 2 - 1) * 0.25F));
        BlockPos blockpos = new BlockPos(i, j, k);
        if (this.world.getBlockState(blockpos).getMaterial() == Material.AIR && this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(blockpos) < 0.8F && Blocks.SNOW_LAYER.canPlaceBlockAt(this.world, blockpos))
          this.world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState()); 
      } 
    } 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_SNOWMAN;
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
    if (!stack.isEmpty() && stack.getItem() == Items.SHEARS) {
      if (hasOwner(player) && !isPumpkinEquipped() && !this.world.isRemote) {
        this.world.playEvent(2001, getPosition().up(), Block.getStateId(Blocks.PUMPKIN.getDefaultState()));
        setPumpkinEquipped(true);
        stack.damageItem(1, (EntityLivingBase)player);
      } 
      return true;
    } 
    return false;
  }
  
  public boolean isPumpkinEquipped() {
    return ((((Byte)this.dataManager.get(PUMPKIN_EQUIPPED)).byteValue() & 0x10) != 0);
  }
  
  public void setPumpkinEquipped(boolean p_184747_1_) {
    byte b0 = ((Byte)this.dataManager.get(PUMPKIN_EQUIPPED)).byteValue();
    if (p_184747_1_) {
      this.dataManager.set(PUMPKIN_EQUIPPED, Byte.valueOf((byte)(b0 | 0x10)));
    } else {
      this.dataManager.set(PUMPKIN_EQUIPPED, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
    } 
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float p_82196_2_) {
    EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.world, (EntityLivingBase)this);
    double d0 = target.posY + target.getEyeHeight() - 1.15D;
    double d1 = target.posX - this.posX;
    double d2 = d0 - entitysnowball.posY;
    double d3 = target.posZ - this.posZ;
    float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
    entitysnowball.shoot(d1, d2 + f, d3, 1.6F, isPumpkinEquipped() ? 45.0F : 1.0F);
    playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
    this.world.spawnEntity((Entity)entitysnowball);
    swingArm(EnumHand.MAIN_HAND);
    entitysnowball.damage = (target instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || target instanceof net.minecraft.entity.monster.EntityBlaze) ? 3.0F : ((this.rand.nextInt(3) == 0 || !(target instanceof EntityTameBase)) ? 1.0F : 0.0F);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.2F) : super.getSoundPitch();
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.84F) : (this.height * 0.89F);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SNOWMAN_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_SNOWMAN_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + 0.1F); 
    return SoundEvents.ENTITY_SNOWMAN_DEATH;
  }
}

