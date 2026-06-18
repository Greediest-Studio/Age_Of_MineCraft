package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityLesserShoggoth extends EntityTameBase implements Armored, Undead {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.BYTE);
  
  private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> FOOD = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.VARINT);
  
  private int monolithTimer;
  
  private float shoggothWidth = -1.0F;
  
  private float shoggothHeight;
  
  public EntityLesserShoggoth(World par1World) {
    super(par1World);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 6.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    setSize(1.9F, 2.35F);
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 400;
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 0.75F;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityLesserShoggoth(this.world);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return (getShoggothType() == 1) ? true : super.isEntityImmuneToCoralium();
  }
  
  public boolean isEntityImmuneToDread() {
    return (getShoggothType() == 2) ? true : super.isEntityImmuneToDread();
  }
  
  public boolean passesCoraliumPlague() {
    return (getShoggothType() == 1);
  }
  
  public boolean passesDreadPlague() {
    return (getShoggothType() == 2);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    } 
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(TYPE, Integer.valueOf(0));
    this.dataManager.register(FOOD, Integer.valueOf(0));
    this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote)
      setBesideClimbableBlock(this.collidedHorizontally); 
  }
  
  public int getShoggothType() {
    return ((Integer)this.dataManager.get(TYPE)).intValue();
  }
  
  public void setShoggothType(int par1) {
    this.dataManager.set(TYPE, Integer.valueOf(par1));
  }
  
  public void setFoodLevel(int par1) {
    this.dataManager.set(FOOD, Integer.valueOf(par1));
  }
  
  public int getFoodLevel() {
    return ((Integer)this.dataManager.get(FOOD)).intValue();
  }
  
  public void feed() {
    int food = getFoodLevel() + 1;
    this.dataManager.set(FOOD, Integer.valueOf(food));
    setGrowingAge(getGrowingAge() + 4000);
    if (getFittness() < 1.5F && this.rand.nextInt(10) == 0)
      setFittness(getFittness() + 0.05F); 
    if (getStrength() < 100.0F && this.rand.nextInt(10) == 0)
      setStrength(getStrength() + 0.05F); 
    if (getStamina() < 100.0F && this.rand.nextInt(10) == 0)
      setStamina(getStamina() + 0.05F); 
    if (getAgility() < 100.0F && this.rand.nextInt(10) == 0)
      setAgility(getAgility() + 0.05F); 
    if (getDexterity() < 100.0F && this.rand.nextInt(10) == 0)
      setDexterity(getDexterity() + 0.05F); 
  }
  
  public boolean isOnLadder() {
    return isBesideClimbableBlock();
  }
  
  public void reduceMonolithTimer() {
    if (this.monolithTimer - 100 >= 100) {
      this.monolithTimer -= 100;
    } else {
      this.monolithTimer = 0;
    } 
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    this.monolithTimer++;
    if (getFoodLevel() == 3 && !this.world.isRemote) {
      setFoodLevel(0);
      if (!isChild()) {
        EntityLesserShoggoth shoggoth = (EntityLesserShoggoth)spawnBaby(this);
        shoggoth.copyLocationAndAnglesFrom((Entity)this);
        shoggoth.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(this.posX, this.posY, this.posZ)), (IEntityLivingData)null);
        shoggoth.setGrowingAge(-24000);
        shoggoth.setOwnerId(getOwnerId());
        shoggoth.setShoggothType(getShoggothType());
        this.world.spawnEntity((Entity)shoggoth);
        playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      } 
    } 
    if (!this.world.isRemote && !isInvisible())
      for (int l = 0; l < 1; l++) {
        int x = MathHelper.floor(this.posX + ((l % 2 * 2 - 1) * 0.25F));
        int y = MathHelper.floor(this.posY);
        int z = MathHelper.floor(this.posZ + ((l / 2 % 2 * 2 - 1) * 0.25F));
        spawnOoze(x, y, z);
        if (!isChild()) {
          spawnOoze(x - 1, y, z);
          spawnOoze(x, y, z - 1);
          spawnOoze(x - 1, y, z - 1);
        } 
      }  
    if (this.monolithTimer >= 200 && !isInvisible()) {
      this.monolithTimer = 0;
      if (this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(32.0D)).size() > 5 && !isChild()) {
        for (EntityLesserShoggoth shoggoth : this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(16.0D)))
          shoggoth.reduceMonolithTimer(); 
        if (!this.world.isRemote)
          (new WorldGenShoggothMonolith()).generate(this.world, this.rand, new BlockPos(MathHelper.floor(this.posX) + 3, MathHelper.floor(this.posY), MathHelper.floor(this.posZ) + 3)); 
      } 
    } 
    for (int i = 0; i < 2 && getShoggothType() == 4 && !isInvisible() && ACConfig.particleEntity && this.world.provider.getDimension() != ACLib.dark_realm_id; i++)
      this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]); 
  }
  
  private void spawnOoze(int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    if (ACConfig.shoggothOoze)
      if ((this.world.getBlockState(pos).getMaterial() == Material.AIR || this.world.getBlockState(pos).getBlock().isReplaceable((IBlockAccess)this.world, pos)) && ACBlocks.shoggoth_ooze.canPlaceBlockAt(this.world, pos) && this.world
        .getBlockState(pos).getBlock() != ACBlocks.shoggoth_ooze && !this.world.getBlockState(pos).getMaterial().isLiquid()) {
        this.world.setBlockState(pos, ACBlocks.shoggoth_ooze.getDefaultState());
      } else if (this.world.getBlockState(pos).getBlock() == ACBlocks.shoggoth_ooze && ((Integer)this.world.getBlockState(pos).getValue((IProperty)BlockShoggothOoze.LAYERS)).intValue() < 8 && this.ticksExisted % 10 == 0 && this.rand
        .nextInt(5) == 0) {
        IBlockState state = this.world.getBlockState(pos);
        this.world.setBlockState(pos, state.withProperty((IProperty)BlockShoggothOoze.LAYERS, Integer.valueOf(((Integer)state.getValue((IProperty)BlockShoggothOoze.LAYERS)).intValue() + 1)));
      }  
  }
  
  public boolean isBesideClimbableBlock() {
    return ((((Byte)this.dataManager.get(CLIMBING)).byteValue() & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();
    if (par1) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.dataManager.set(CLIMBING, Byte.valueOf(b0));
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      playHurtSound(null); 
    switch (getShoggothType()) {
      case 1:
        if (!EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
          ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100)); 
        break;
      case 2:
        if (!EntityUtil.isEntityDread((EntityLivingBase)par1Entity))
          ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
        break;
      case 3:
        ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
        break;
      case 4:
        ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
        if (!isInvisible() && this.rand.nextInt(5) == 0) {
          playSound(ACSounds.shadow_death, 1.0F, getSoundPitch());
          addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 800));
        } 
        if (isInvisible())
          ((EntityLivingBase)par1Entity).hurtResistantTime = 0; 
        break;
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.isProjectile()) {
      playSound(SoundEvents.ENTITY_SMALL_SLIME_JUMP, getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
      return false;
    } 
    if (par1DamageSource == DamageSource.CACTUS)
      return false; 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.shoggoth_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource souce) {
    return ACSounds.shoggoth_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.shoggoth_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    if (!isInvisible())
      playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F); 
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    ItemStack item = new ItemStack(ACItems.shoggoth_flesh, 1, getShoggothType());
    if (item != null) {
      int i = this.rand.nextInt(3);
      if (par2 > 0)
        i += this.rand.nextInt(par2 + 1); 
      for (int j = 0; j < i; j++)
        entityDropItem(item, 0.0F); 
    } 
  }
  
  protected ResourceLocation getLootTable() {
    switch (getShoggothType()) {
      case 0:
        return ACLoot.ENTITY_LESSER_SHOGGOTH;
      case 1:
        return ACLoot.ENTITY_LESSER_ABYSSAL_SHOGGOTH;
      case 2:
        return ACLoot.ENTITY_LESSER_DREADED_SHOGGOTH;
      case 3:
        return ACLoot.ENTITY_LESSER_OMOTHOL_SHOGGOTH;
      case 4:
        return ACLoot.ENTITY_LESSER_SHADOW_SHOGGOTH;
    } 
    return null;
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
    if (!stack.isEmpty() && stack.getItem() == ACItems.shoggoth_flesh && hasOwner(player)) {
      if (getShoggothType() != stack.getMetadata()) {
        playSound(getAmbientSound(), getSoundVolume(), getSoundPitch());
        player.swingArm(hand);
        if (!this.world.isRemote) {
          entityDropItem(new ItemStack(ACItems.shoggoth_flesh, 1, getShoggothType()), 1.0F);
          setShoggothType(stack.getMetadata());
          stack.shrink(1);
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
        setBesideClimbableBlock(this.collidedHorizontally);
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
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    if (par1NBTTagCompound.getBoolean("IsBaby"))
      setChild(true); 
    if (par1NBTTagCompound.hasKey("ShoggothType")) {
      byte var2 = par1NBTTagCompound.getByte("ShoggothType");
      setShoggothType(var2);
    } 
    if (par1NBTTagCompound.hasKey("FoodLevel")) {
      byte var2 = par1NBTTagCompound.getByte("FoodLevel");
      setFoodLevel(var2);
    } 
    this.monolithTimer = par1NBTTagCompound.getInteger("MonolithTimer");
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    if (isChild())
      par1NBTTagCompound.setBoolean("IsBaby", true); 
    par1NBTTagCompound.setByte("ShoggothType", (byte)getShoggothType());
    par1NBTTagCompound.setByte("FoodLevel", (byte)getFoodLevel());
    par1NBTTagCompound.setInteger("MonolithTimer", this.monolithTimer);
  }
  
  public void onKillEntity(EntityLivingBase par1EntityLivingBase) {
    super.onKillEntity(par1EntityLivingBase);
    if (EntityUtil.isShoggothFood(par1EntityLivingBase))
      feed(); 
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);
    setShoggothType(0);
    if (this.world.provider.getDimension() == ACLib.abyssal_wasteland_id)
      setShoggothType(1); 
    if (this.world.provider.getDimension() == ACLib.dreadlands_id)
      setShoggothType(2); 
    if (this.world.provider.getDimension() == ACLib.omothol_id)
      setShoggothType(3); 
    if (this.world.provider.getDimension() == ACLib.dark_realm_id)
      setShoggothType(4); 
    if (data == null)
      data = new GroupData((this.world.rand.nextFloat() < ForgeModContainer.zombieBabyChance), null); 
    if (data instanceof GroupData) {
      GroupData groupdata = (GroupData)data;
      if (groupdata.isBaby)
        setGrowingAge(-24000); 
    } 
    return (IEntityLivingData)data;
  }
  
  class GroupData implements IEntityLivingData {
    public boolean isBaby;
    
    private GroupData(boolean par2) {
      this.isBaby = false;
      this.isBaby = par2;
    }
    
    GroupData(boolean par2, Object par4EntityLesserShoggothINNER1) {
      this(par2);
    }
  }
}

