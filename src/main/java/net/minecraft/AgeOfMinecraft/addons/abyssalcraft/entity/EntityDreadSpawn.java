package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.List;
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

public class EntityDreadSpawn extends EntityTameBase implements Light {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityDreadSpawn.class, DataSerializers.BYTE);
  
  private static boolean hasMerged;
  
  public EntityDreadSpawn(World par1World) {
    super(par1World);
    setSize(0.425F, 0.425F);
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.1D, 32.0F, 9.0F));
    this.tasks.addTask(2, new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.8D));
    this.tasks.addTask(4, new EntityAIWander(this, 0.8D));
    this.tasks.addTask(5, new EntityAILookIdle(this));
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityDreadSpawn(this.world);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public float getBonusVSLight() {
    return 1.1F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    } 
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
    return new PathNavigateClimber(this, worldIn);
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof net.minecraft.entity.player.EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return flag;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(CLIMBING, (byte) 0);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote)
      setBesideClimbableBlock(this.collidedHorizontally); 
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
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isOnLadder() {
    return isBesideClimbableBlock();
  }
  
  public boolean isBesideClimbableBlock() {
    return ((this.dataManager.get(CLIMBING) & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = this.dataManager.get(CLIMBING);
    if (par1) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.dataManager.set(CLIMBING, b0);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected Item getDropItem() {
    return ACItems.dread_fragment;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_DREAD_SPAWN;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (getRidingEntity() != null && getRidingEntity() instanceof EntityDreadSlugOther) {
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = (getRidingEntity()).rotationYaw + 180.0F;
      this.rotationPitch = -(getRidingEntity()).rotationPitch;
    } 
    if (this.ticksExisted == 1)
      playSound(ESound.amalgamate, 1.0F, 1.0F); 
    List<EntityDreadSpawn> dreadspawns = this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(0.5D));
    if (!this.world.isRemote && 
      !dreadspawns.isEmpty() && 
      dreadspawns.size() >= 5 && !hasMerged) {
      hasMerged = true;
      for (int i = 0; i < 5 && false; i++)
        this.world.removeEntity(dreadspawns.get(i));
      EntityGreaterDreadSpawn greaterspawn = new EntityGreaterDreadSpawn(this.world);
      greaterspawn.copyLocationAndAnglesFrom(this);
      greaterspawn.setOwnerId(getOwnerId());
      this.world.removeEntity(this);
      this.world.spawnEntity(greaterspawn);
      hasMerged = false;
    } 
    List<Entity> list = this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(8.0D));
    if (!list.isEmpty() && list.size() >= 5 && (this.ticksExisted + getEntityId()) % 20 == 0)
        for (Entity entity : list) {
            if (entity.isEntityAlive() && entity instanceof EntityDreadSpawn) {
                EntityDreadSpawn mob = (EntityDreadSpawn) entity;
                if (false)
                    getNavigator().tryMoveToEntityLiving(mob, 1.2D);
            }
        }
  }
}

