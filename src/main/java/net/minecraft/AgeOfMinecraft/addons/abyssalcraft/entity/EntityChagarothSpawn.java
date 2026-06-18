package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityChagarothSpawn extends EntityTameBase implements Light {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityChagarothSpawn.class, DataSerializers.BYTE);
  
  public EntityChagarothSpawn(World par1World) {
    super(par1World);
    setSize(0.5F, 0.5F);
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 9.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityChagarothSpawn(this.world);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    } 
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof net.minecraft.entity.player.EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote)
      setBesideClimbableBlock(this.collidedHorizontally); 
  }
  
  protected float getSoundPitch() {
    return super.getSoundPitch() - 0.1F;
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.dread_spawn_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.dread_spawn_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.dread_spawn_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
  }
  
  public boolean isOnLadder() {
    return isBesideClimbableBlock();
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
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void onLivingUpdate() {
    if (getRidingEntity() != null && getRidingEntity() instanceof EntityDreadSlugOther) {
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = (getRidingEntity()).rotationYaw + 180.0F;
      this.rotationPitch = -(getRidingEntity()).rotationPitch;
    } 
    if (this.ticksExisted == 1)
      playSound(ESound.amalgamate, 1.0F, 1.0F); 
    super.onLivingUpdate();
  }
  
  protected Item getDropItem() {
    return ACItems.dread_fragment;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_SPAWN_OF_CHAGAROTH;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
}
