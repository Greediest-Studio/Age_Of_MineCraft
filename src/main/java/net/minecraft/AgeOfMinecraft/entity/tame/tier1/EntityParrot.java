package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityParrot extends EntityTameBase implements EntityFlying, Light, Animal, Flying {
  private static final DataParameter<Integer> VARIANT = EntityDataManager.createKey(EntityParrot.class, DataSerializers.VARINT);
  
  private static final Predicate<EntityLiving> CAN_MIMIC = p_apply_1_ -> (p_apply_1_ != null && EntityParrot.MIMIC_SOUNDS.containsKey(p_apply_1_.getClass()));
  
  private static final Map<Class<? extends Entity>, SoundEvent> MIMIC_SOUNDS = Maps.newHashMapWithExpectedSize(32);
  
  public float flap;
  
  public float flapSpeed;
  
  public float oFlapSpeed;
  
  public float oFlap;
  
  public float flapping = 1.0F;
  
  public EntityParrot(World worldIn) {
    super(worldIn);
    setSize(0.3F, 0.7F);
    this.moveHelper = new EntityFlyHelper(this);
  }
  
  public String getDescName() {
    return "parrot";
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    setVariant(this.rand.nextInt(5));
    return super.onInitialSpawn(difficulty, livingdata);
  }
  
  protected void initEntityAI() {
    EntityAITasks tasks = compatTasks();
    tasks.addTask(0, new EntityAISwimming(this));
    tasks.addTask(1, new EntityAIFollowLeader(this, 1.33D, 15.0F, 4.0F));
    tasks.addTask(2, new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    tasks.addTask(3, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.75D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    EntityTameBase.PathNavigateFlying pathnavigateflying = new EntityTameBase.PathNavigateFlying((EntityLiving)this, worldIn);
    pathnavigateflying.setCanOpenDoors(true);
    pathnavigateflying.setCanFloat(true);
    pathnavigateflying.setCanEnterDoors(true);
    return pathnavigateflying;
  }
  
  public float getEyeHeight() {
    return 0.6F;
  }
  
  public void onLivingUpdate() {
    playMimicSound(this.world, this);
    super.onLivingUpdate();
    calculateFlapping();
  }
  
  private void calculateFlapping() {
    this.oFlap = this.flap;
    this.oFlapSpeed = this.flapSpeed;
    this.flapSpeed = (float)(this.flapSpeed + (this.onGround ? -1 : 4) * 0.3D);
    this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
    if (!this.onGround && this.flapping < 1.0F)
      this.flapping = 1.0F; 
    this.flapping = (float)(this.flapping * 0.9D);
    if (!this.onGround && this.motionY < 0.0D && isEntityAlive())
      this.motionY *= 0.6D; 
    this.flap += this.flapping * 2.0F;
  }
  
  private static boolean playMimicSound(World worldIn, Entity p_192006_1_) {
    if (!p_192006_1_.isSilent() && worldIn.rand.nextInt(50) == 0) {
      List<EntityLiving> list = worldIn.getEntitiesWithinAABB(EntityLiving.class, p_192006_1_.getEntityBoundingBox().grow(20.0D), CAN_MIMIC);
      if (!list.isEmpty()) {
        EntityLiving entityliving = list.get(worldIn.rand.nextInt(list.size()));
        if (!entityliving.isSilent()) {
          SoundEvent soundevent = MIMIC_SOUNDS.get(entityliving.getClass());
          worldIn.playSound(null, p_192006_1_.posX, p_192006_1_.posY, p_192006_1_.posZ, soundevent, p_192006_1_.getSoundCategory(), 0.7F, getPitch(worldIn.rand));
          return true;
        } 
      } 
      return false;
    } 
    return false;
  }
  
  public boolean isBreedingItem(ItemStack stack) {
    return false;
  }
  
  public boolean getCanSpawnHere() {
    int i = MathHelper.floor(this.posX);
    int j = MathHelper.floor((getEntityBoundingBox()).minY);
    int k = MathHelper.floor(this.posZ);
    BlockPos blockpos = new BlockPos(i, j, k);
    Block block = this.world.getBlockState(blockpos.down()).getBlock();
    return (block instanceof net.minecraft.block.BlockLeaves || block == Blocks.GRASS || block instanceof net.minecraft.block.BlockLog || (block == Blocks.AIR && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere()));
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public static void playAmbientSound(World worldIn, Entity p_192005_1_) {
    if (!p_192005_1_.isSilent() && !playMimicSound(worldIn, p_192005_1_) && worldIn.rand.nextInt(200) == 0)
      worldIn.playSound(null, p_192005_1_.posX, p_192005_1_.posY, p_192005_1_.posZ, getAmbientSound(worldIn.rand), p_192005_1_.getSoundCategory(), 1.0F, getPitch(worldIn.rand));
  }
  
  @Nullable
  public SoundEvent getAmbientSound() {
    return getAmbientSound(this.rand);
  }
  
  private static SoundEvent getAmbientSound(Random random) {
    if (random.nextInt(1000) == 0) {
      List<SoundEvent> list = new ArrayList<>(MIMIC_SOUNDS.values());
      SoundEvent ret = list.get(random.nextInt(list.size()));
      return (ret == null) ? SoundEvents.ENTITY_PARROT_AMBIENT : ret;
    } 
    return SoundEvents.ENTITY_PARROT_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundEvents.ENTITY_PARROT_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_PARROT_DEATH;
  }
  
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F);
  }
  
  protected float playFlySound(float p_191954_1_) {
    playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F);
    return p_191954_1_ + this.flapSpeed / 2.0F;
  }
  
  protected boolean makeFlySound() {
    return true;
  }
  
  protected float getSoundPitch() {
    return getPitch(this.rand);
  }
  
  private static float getPitch(Random random) {
    return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
  }
  
  public SoundCategory getSoundCategory() {
    return SoundCategory.NEUTRAL;
  }
  
  public boolean canBePushed() {
    return true;
  }
  
  protected void collideWithEntity(Entity entityIn) {
    if (!(entityIn instanceof EntityPlayer))
      super.collideWithEntity(entityIn); 
  }
  
  public int getVariant() {
    return MathHelper.clamp(this.getDataManager().get(VARIANT), 0, 4);
  }
  
  public void setVariant(int p_191997_1_) {
    this.getDataManager().set(VARIANT, p_191997_1_);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(VARIANT, 0);
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    compound.setInteger("Variant", getVariant());
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    setVariant(compound.getInteger("Variant"));
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTableList.ENTITIES_PARROT;
  }
  
  public boolean isFlying() {
    return !this.onGround;
  }
  
  static {
    registerMimicSound(EntityBlaze.class, SoundEvents.E_PARROT_IM_BLAZE);
    registerMimicSound(EntityCaveSpider.class, SoundEvents.E_PARROT_IM_SPIDER);
    registerMimicSound(EntityCreeper.class, SoundEvents.E_PARROT_IM_CREEPER);
    registerMimicSound(EntityElderGuardian.class, SoundEvents.E_PARROT_IM_ELDER_GUARDIAN);
    registerMimicSound(EntityDragon.class, SoundEvents.E_PARROT_IM_ENDERDRAGON);
    registerMimicSound(EntityEnderman.class, SoundEvents.E_PARROT_IM_ENDERMAN);
    registerMimicSound(EntityEndermite.class, SoundEvents.E_PARROT_IM_ENDERMITE);
    registerMimicSound(EntityEvoker.class, SoundEvents.E_PARROT_IM_EVOCATION_ILLAGER);
    registerMimicSound(EntityGhast.class, SoundEvents.E_PARROT_IM_GHAST);
    registerMimicSound(EntityHusk.class, SoundEvents.E_PARROT_IM_HUSK);
    registerMimicSound(EntityIllusionIllager.class, SoundEvents.E_PARROT_IM_ILLUSION_ILLAGER);
    registerMimicSound(EntityMagmaCube.class, SoundEvents.E_PARROT_IM_MAGMACUBE);
    registerMimicSound(EntityPigZombie.class, SoundEvents.E_PARROT_IM_ZOMBIE_PIGMAN);
    registerMimicSound(EntityPolarBear.class, SoundEvents.E_PARROT_IM_POLAR_BEAR);
    registerMimicSound(EntityShulker.class, SoundEvents.E_PARROT_IM_SHULKER);
    registerMimicSound(EntitySilverfish.class, SoundEvents.E_PARROT_IM_SILVERFISH);
    registerMimicSound(EntitySkeleton.class, SoundEvents.E_PARROT_IM_SKELETON);
    registerMimicSound(EntitySlime.class, SoundEvents.E_PARROT_IM_SLIME);
    registerMimicSound(EntitySpider.class, SoundEvents.E_PARROT_IM_SPIDER);
    registerMimicSound(EntityStray.class, SoundEvents.E_PARROT_IM_STRAY);
    registerMimicSound(EntityVex.class, SoundEvents.E_PARROT_IM_VEX);
    registerMimicSound(EntityVindicator.class, SoundEvents.E_PARROT_IM_VINDICATION_ILLAGER);
    registerMimicSound(EntityWitch.class, SoundEvents.E_PARROT_IM_WITCH);
    registerMimicSound(EntityWither.class, SoundEvents.E_PARROT_IM_WITHER);
    registerMimicSound(EntityWitherSkeleton.class, SoundEvents.E_PARROT_IM_WITHER_SKELETON);
    registerMimicSound(EntityWolf.class, SoundEvents.E_PARROT_IM_WOLF);
    registerMimicSound(EntityZombie.class, SoundEvents.E_PARROT_IM_ZOMBIE);
    registerMimicSound(EntityZombieVillager.class, SoundEvents.E_PARROT_IM_ZOMBIE_VILLAGER);
  }
  
  public static void registerMimicSound(Class<? extends Entity> cls, SoundEvent sound) {
    MIMIC_SOUNDS.put(cls, sound);
  }
}
