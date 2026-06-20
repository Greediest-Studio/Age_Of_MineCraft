package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemPEGun;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityAbstractIllagers;
import net.minecraft.AgeOfMinecraft.entity.tame.EntitySpellcasterIllager;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedBowAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityTippedArrowOther;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.util.EntityAICompat;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIllusioner extends EntitySpellcasterIllager implements IRangedAttackMob {
  private static final DataParameter<Integer> DISGUISE_ID = EntityDataManager.createKey(EntityIllusioner.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> DISGUSE_TIMER = EntityDataManager.createKey(EntityIllusioner.class, DataSerializers.VARINT);
  
  private final EntityAIAttackRangedBowAlly<EntityIllusioner> aiArrowAttack = new EntityAIAttackRangedBowAlly(this, 0.5D, 20, 15.0F);
  
  private final EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.0D, true);
  
  public EntityIllusioner(World worldIn) {
    super(worldIn);
    this.experienceValue = 50;
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 0.75D, 32.0F, 6.0F));
    this.tasks.addTask(2, new AICastingSpell());
    this.tasks.addTask(3, new AIIllusionFormSpell());
    this.tasks.addTask(3, new AIDisguiseSpell());
    this.tasks.addTask(4, new AIMirriorSpell());
    this.tasks.addTask(4, new AIFearSpell());
    this.tasks.addTask(4, new AIBlindnessSpell());
    this.tasks.addTask(4, new AIReinforcingSpell());
    this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.5D));
    this.tasks.addTask(6, new EntityAIWander(this, 0.5D, 80));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    setCombatTask();
  }
  
  public void setCombatTask() {
    if (this.world != null && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      this.tasks.removeTask(this.aiAttackOnCollide);
      this.tasks.removeTask(this.aiArrowAttack);
      ItemStack itemstack = getHeldItemMainhand();
      if (itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemBow) {
        this.tasks.addTask(4, this.aiArrowAttack);
      } else {
        this.tasks.addTask(4, this.aiAttackOnCollide);
      } 
    } 
  }
  
  public String getDescName() {
    return "illusioner";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(DISGUISE_ID, 0);
    getDataManager().register(DISGUSE_TIMER, 0);
  }
  
  public int getDisguiseTime() {
    return this.getDataManager().get(DISGUSE_TIMER);
  }
  
  public void setDisguiseTime(int age) {
    this.getDataManager().set(DISGUSE_TIMER, age);
  }
  
  public int getDisguiseID() {
    return this.getDataManager().get(DISGUISE_ID);
  }
  
  public void setDisguiseID(int age) {
    this.getDataManager().set(DISGUISE_ID, age);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("DiguiseID", getDisguiseID());
    tagCompound.setInteger("DiguiseTime", getDisguiseTime());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setDisguiseID(tagCompund.getInteger("DiguiseID"));
    setDisguiseTime(tagCompund.getInteger("DiguiseTime"));
    setCombatTask();
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(5.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
    setCombatTask();
    return super.onInitialSpawn(difficulty, livingdata);
  }
  
  protected ResourceLocation getLootTable() {
    return LootTableList.EMPTY;
  }
  
  protected SoundEvent getAmbientSound() {
    if (getDisguiseID() > 0 && getDisguiseTime() > 0) {
      playSound(SoundEvents.ENTITY_ILLUSION_ILLAGER_AMBIENT, 0.05F, getSoundPitch());
      switch (getDisguiseID()) {
        case 2:
          return SoundEvents.VINDICATION_ILLAGER_AMBIENT;
        case 3:
          return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
      } 
      return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    } 
    return SoundEvents.ENTITY_ILLUSION_ILLAGER_AMBIENT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_ILLAGER_DEATH;
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundEvents.ENTITY_ILLUSION_ILLAGER_HURT;
  }
  
  protected SoundEvent getSpellSound() {
    return SoundEvents.ENTITY_ILLAGER_CAST_SPELL;
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    if (getHeldItemMainhand().getItem() instanceof ItemPEGun) {
      if (((ItemPEGun)getHeldItemMainhand().getItem()).getContainedEnergy(getHeldItemMainhand()) > 0.0F) {
        float f = MathHelper.cos((this.rotationYawHead + (180 * ((isLeftHanded() ? 1 : 2) - 1))) * 0.017453292F);
        float f1 = MathHelper.sin((this.rotationYawHead + (180 * ((isLeftHanded() ? 1 : 2) - 1))) * 0.017453292F);
        ((ItemPEGun)getHeldItemMainhand().getItem()).consumeEnergy(getHeldItemMainhand(), 1.0F);
        Vec3d vec3 = getLook(1.0F);
        double d2 = target.posX - this.posX + f * 0.35D + vec3.x;
        double d3 = target.posY + 0.5D - this.posY + getEyeHeight() - 0.10000000149011612D + vec3.y;
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
    } else {
      EntityTippedArrowOther entityarrow = new EntityTippedArrowOther(this.world, this);
      double d0 = target.posX - this.posX;
      double d1 = target.posY + target.getEyeHeight() - 0.5D - this.posY + getEyeHeight() - 0.10000000149011612D;
      double d2 = target.posZ - this.posZ;
      double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
      entityarrow.shoot(d0, d1 + d3 * getDistance(target) * 0.013D, d2, 1.4F, 1.0F);
      int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
      int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this);
      if (getRidingEntity() != null) {
        entityarrow.setDamage((distanceFactor * 3.0F) + this.rand.nextGaussian() * 0.25D + 0.5D);
      } else {
        entityarrow.setDamage((distanceFactor * 1.5F) + this.rand.nextGaussian() * 0.25D + 0.5D);
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
      if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0)
        entityarrow.setFire(100); 
      if (getHeldItemOffhand() != null && getHeldItemOffhand().getItem() == Items.TIPPED_ARROW)
        entityarrow.setPotionEffect(getHeldItemOffhand()); 
      playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
      this.world.spawnEntity(entityarrow);
    } 
  }
  
  protected EntityArrow createArrowEntity(float p_193097_1_) {
    EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, this);
    entitytippedarrow.setEnchantmentEffectsFromEntity(this, p_193097_1_);
    return entitytippedarrow;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityAbstractIllagers.IllagerArmPose getArmPose() {
    if (isSpellcasting() && getDisguiseID() != 1)
      return EntityAbstractIllagers.IllagerArmPose.SPELLCASTING; 
    if (isArmsRaised() && getDisguiseID() != 1)
      return EntityAbstractIllagers.IllagerArmPose.BOW_AND_ARROW; 
    return EntityAbstractIllagers.IllagerArmPose.CROSSED;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (getDisguiseTime() > 0)
      setDisguiseTime(getDisguiseTime() - 1); 
    if (getDisguiseID() != 0 && (getDisguiseTime() < 0 || (getDisguiseTime() > 0 && this.hurtResistantTime > 0))) {
      playSound(SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, 1.0F, 1.0F);
      spawnExplosionParticle();
      setDisguiseID(0);
      setDisguiseTime(0);
    } 
  }
  
  public class EntityAIPanicFear extends EntityAIBase {
    protected final EntityCreature creature;
    
    protected double speed;
    
    protected double randPosX;
    
    protected double randPosY;
    
    protected double randPosZ;
    
    protected int feartimer;
    
    public EntityAIPanicFear(EntityCreature creature, double speedIn) {
      this.creature = creature;
      this.speed = speedIn;
      setMutexBits(1);
    }
    
    public boolean shouldExecute() {
      return (this.feartimer < 400 && findRandomPosition());
    }
    
    protected boolean findRandomPosition() {
      Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.creature, 5, 4);
      if (vec3d == null)
        return false; 
      this.randPosX = vec3d.x;
      this.randPosY = vec3d.y;
      this.randPosZ = vec3d.z;
      return true;
    }
    
    public void startExecuting() {
      this.creature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }
    
    public boolean shouldContinueExecuting() {
      return (this.feartimer < 400);
    }
    
    public void updateTask() {
      this.creature.setRevengeTarget(null);
      this.creature.setAttackTarget(null);
      if (!this.creature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed)) {
        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.creature, 5, 4);
        if (vec3d != null) {
          this.randPosX = vec3d.x;
          this.randPosY = vec3d.y;
          this.randPosZ = vec3d.z;
        } 
      } 
      this.feartimer++;
    }
    
    public void resetTask() {
      this.creature.tasks.removeTask(this);
    }
  }
  
  public class AIFearSpell extends EntitySpellcasterIllager.AIUseSpell {
    private EntityIllusioner.EntityAIPanicFear fear;
    
    private AIFearSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      return (!EntityIllusioner.this.getAttackTarget().isEntityUndead() && EntityIllusioner.this.getAttackTarget().isNonBoss() && (EntityIllusioner.this.getAttackTarget() instanceof EntityCreature || (EntityIllusioner.this.getAttackTarget() instanceof EntityTameBase && !((EntityTameBase)EntityIllusioner.this.getAttackTarget()).isABoss() && ((EntityTameBase)EntityIllusioner.this.getAttackTarget()).getIntelligence() < 24.0F)));
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityIllusioner.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    public void startExecuting() {
      super.startExecuting();
      EntityIllusioner.this.getAttackTarget().getEntityId();
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 500;
    }
    
    protected void castSpell() {
      List<EntityCreature> list = EntityIllusioner.this.world.getEntitiesWithinAABB(EntityCreature.class, EntityIllusioner.this.getAttackTarget().getEntityBoundingBox().grow(8.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      EntityIllusioner.this.world.playBroadcastSound(1023, new BlockPos(EntityIllusioner.this.getAttackTarget()), 0);
      EntityIllusioner.this.getAttackTarget().playSound(SoundEvents.ENTITY_WITHER_AMBIENT, 2.0F, 1.0F);
      for (EntityCreature entity : list) {
        this.fear = new EntityIllusioner.EntityAIPanicFear(entity, 1.5D);
        if (!EntityAICompat.containsTask(entity, this.fear) && !false)
          EntityAICompat.addTask(entity, 0, this.fear); 
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.ENTITY_ILLAGER_PREPARE_BLINDNESS;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FEAR;
    }
  }
  
  public class AIBlindnessSpell extends EntitySpellcasterIllager.AIUseSpell {
    private int lastTargetId;
    
    private AIBlindnessSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      if (EntityIllusioner.this.getAttackTarget().getEntityId() == this.lastTargetId)
        return false; 
      return (EntityIllusioner.this.getAttackTarget() instanceof net.minecraft.entity.player.EntityPlayer || (EntityIllusioner.this.getAttackTarget() instanceof EntityTameBase && EntityIllusioner.this.getAttackTarget().isNonBoss()));
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityIllusioner.this.getAttackTarget() != null && super.shouldContinueExecuting());
    }
    
    public void startExecuting() {
      super.startExecuting();
      this.lastTargetId = EntityIllusioner.this.getAttackTarget().getEntityId();
    }
    
    protected int getCastingTime() {
      return 20;
    }
    
    protected int getCastingInterval() {
      return 180;
    }
    
    protected void castSpell() {
      EntityIllusioner.this.getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.ENTITY_ILLAGER_PREPARE_BLINDNESS;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.BLINDNESS;
    }
  }
  
  public class AIMirriorSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIMirriorSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      return (EntityIllusioner.this.getGhostTime() <= 0);
    }
    
    protected int getCastingTime() {
      return 20;
    }
    
    protected int getCastingInterval() {
      return 340;
    }
    
    protected void castSpell() {
      EntityIllusioner.this.spawnExplosionParticle();
      EntityIllusioner.this.setGhostTime(1200);
    }
    
    @Nullable
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.DISAPPEAR;
    }
  }
  
  public class AIDisguiseSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIDisguiseSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (EntityIllusioner.this.isWild())
        return false; 
      if (EntityIllusioner.this.getAttackTarget() != null)
        return false; 
      if (EntityIllusioner.this.isSpellcasting())
        return false; 
      if (EntityIllusioner.this.ticksExisted < this.spellCooldown)
        return false; 
      return (EntityIllusioner.this.getDisguiseTime() <= 0);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityIllusioner.this.getAttackTarget() == null && super.shouldContinueExecuting());
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 100;
    }
    
    protected void castSpell() {
      EntityIllusioner.this.playSound(SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, 1.0F, 1.0F);
      EntityIllusioner.this.spawnExplosionParticle();
      EntityIllusioner.this.setGhostTime(0);
      EntityIllusioner.this.setDisguiseTime(12000);
      if (EntityIllusioner.this.world.isDaytime() && EntityIllusioner.this.world.canSeeSky(EntityIllusioner.this.getPosition())) {
        EntityIllusioner.this.setDisguiseID(1);
      } else if (EntityIllusioner.this.getLevel() < 50) {
        EntityIllusioner.this.setDisguiseID(2);
      } else {
        EntityIllusioner.this.setDisguiseID(3);
      } 
    }
    
    @Nullable
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.CHANGE_SELF;
    }
  }
  
  public class AIReinforcingSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = p_apply_1_ -> !p_apply_1_.isWild();
    
    public AIReinforcingSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (!super.shouldExecute())
        return false; 
      List<EntityTameBase> list = EntityIllusioner.this.world.getEntitiesWithinAABB(EntityTameBase.class, EntityIllusioner.this.getEntityBoundingBox().grow(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityIllusioner.this.rand.nextInt(list.size()));
      if (entity.getGhostTime() > 0 || !entity.isEntityAlive() || entity == EntityIllusioner.this) {
        list.remove(entity);
        return false;
      } 
      EntityIllusioner.this.setAllyTarget(entity);
      return true;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityIllusioner.this.getAllyTarget() != null && EntityIllusioner.this.getAllyTarget().isEntityAlive() && false && EntityIllusioner.this.getAllyTarget().getGhostTime() <= 0 && this.spellWarmup > 0);
    }
    
    public void resetTask() {
      super.resetTask();
      EntityIllusioner.this.setAllyTarget(null);
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityIllusioner.this.getAllyTarget();
      if (entitysheep != null && entitysheep.isEntityAlive() && false)
        if (entitysheep.getGhostTime() <= 0) {
          entitysheep.spawnExplosionParticle();
          entitysheep.setGhostTime(1200);
          entitysheep.hurtResistantTime = 10;
          resetTask();
        }  
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 100;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.BUFFER_ILLUSIONER;
    }
  }
  
  public class AIIllusionFormSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = p_apply_1_ -> !p_apply_1_.isWild();
    
    public AIIllusionFormSpell() {
      super();
    }
    
    public boolean shouldExecute() {
      if (EntityIllusioner.this.getAttackTarget() != null)
        return false; 
      if (EntityIllusioner.this.isSpellcasting())
        return false; 
      if (EntityIllusioner.this.ticksExisted < this.spellCooldown)
        return false; 
      List<EntityTameBase> list = EntityIllusioner.this.world.getEntitiesWithinAABB(EntityTameBase.class, EntityIllusioner.this.getEntityBoundingBox().grow(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityIllusioner.this.rand.nextInt(list.size()));
      if (entity.isABoss() || entity.getIllusionFormTime() > 0 || !entity.isEntityAlive() || entity == EntityIllusioner.this || entity instanceof EntityIllusioner || !false) {
        list.remove(entity);
        return false;
      } 
      EntityIllusioner.this.setAllyTarget(entity);
      return true;
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityIllusioner.this.getAllyTarget() != null && EntityIllusioner.this.getAllyTarget().isEntityAlive() && false && this.spellWarmup > 0);
    }
    
    public void resetTask() {
      super.resetTask();
      EntityIllusioner.this.setAllyTarget(null);
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityIllusioner.this.getAllyTarget();
      if (entitysheep != null && entitysheep.isEntityAlive() && false)
        if (entitysheep.getIllusionFormTime() <= 0) {
          entitysheep.playSound(ESound.bugSpecial, 1.0F, 1.0F);
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.spawnExplosionParticle();
          entitysheep.setIllusionFormTime(12000);
          entitysheep.hurtResistantTime = 10;
          entitysheep.ticksExisted = -10;
          resetTask();
        }  
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 60;
    }
    
    protected int getCastingInterval() {
      return 200;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.ENTITY_ILLAGER_PREPARE_BLINDNESS;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.ILLUSION_FORM;
    }
  }
}


