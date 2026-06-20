package net.minecraft.AgeOfMinecraft.entity.tame;

import chumbanotz.mutantbeasts.entity.SkullSpiritEntity;
import com.brandon3055.draconicevolution.entity.EntityGuardianCrystal;
import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.lib.ACLib;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumSquid;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadling;
import net.minecraft.AgeOfMinecraft.effects.EngenderExplosion;
import net.minecraft.AgeOfMinecraft.entity.EntityBase;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIBabyMobGirlFollowParent;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowWildAdult;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIMobGirlMate;
import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans;
import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortalLightning;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.util.AttributeCompat;
import net.minecraft.AgeOfMinecraft.util.EntityAICompat;
import net.minecraft.AgeOfMinecraft.util.Maths;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.pathfinding.FlyingNodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues", modid = "iceandfire")
public abstract class EntityTameBase extends EntityBase implements IEntityOwnable, IBlacklistedFromStatues {
  private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
  
  private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5D, 1);
  
  private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> IS_MARRIED = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> HERO = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> REBIRTH = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> ANTIMOB = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Boolean> SITRESTING = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.BOOLEAN);
  
  protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.OPTIONAL_UNIQUE_ID);
  
  private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D838");
  
  private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.5D, 1)).setSaved(false);
  
  private static final UUID attackingBoostModifierUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D839");
  
  private static final AttributeModifier attackingBoostModifier = (new AttributeModifier(attackingBoostModifierUUID, "Attacking boost", 0.5D, 1)).setSaved(false);
  
  private static final DataParameter<Integer> HEROSPECIALATTACKTIMER = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  public static final IAttribute STRENGTH = AttributeCompat.ranged("engender.strength", 9.0D, 1.0D, 100.0D, "Mob Strength", true);
  
  public static final IAttribute STAMINA = AttributeCompat.ranged("engender.stamina", 9.0D, 1.0D, 100.0D, "Mob Stamina", true);
  
  public static final IAttribute INTELLIGENCE = AttributeCompat.ranged("engender.intelligence", 9.0D, 1.0D, 100.0D, "Mob Intelligence", true);
  
  public static final IAttribute DEXTERITY = AttributeCompat.ranged("engender.dexterity", 9.0D, 1.0D, 100.0D, "Mob Dexterity", true);
  
  public static final IAttribute AGILITY = AttributeCompat.ranged("engender.agility", 9.0D, 1.0D, 100.0D, "Mob Agility", true);
  
  public static final IAttribute FITTNESS = AttributeCompat.ranged("engender.fittness", 1.0D, 0.75D, 1.5D, "Mob Fittness", true);
  
  private static final DataParameter<Integer> AGE = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> LEVEL = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  private static final DataParameter<Float> EXP = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.FLOAT);
  
  private static final DataParameter<Float> TOTALEXP = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.FLOAT);
  
  private static final DataParameter<Float> ENERGY = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.FLOAT);
  
  private static final DataParameter<Optional<BlockPos>> GUARD_BLOCK_POS = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.OPTIONAL_BLOCK_POS);
  
  private static final DataParameter<Integer> BOOK_ID = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> BOOK_DURABILITY = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  private static final DataParameter<Float> FAKE_HEALTH = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.FLOAT);
  
  private static final DataParameter<Integer> GHOST_TIME = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> ILLUSION_FORM_TIME = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> POLYMORPH_TIME = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> ATTACKSTATE = EntityDataManager.createKey(EntityTameBase.class, DataSerializers.VARINT);
  
  public boolean isOffensive;
  
  public double randPosX;
  
  public double randPosY;
  
  public double randPosZ;
  
  public double prevChasingPosX;
  
  public double prevChasingPosY;
  
  public double prevChasingPosZ;
  
  public double chasingPosX;
  
  public double chasingPosY;
  
  public double chasingPosZ;
  
  public BlockPos jukeBoxToDanceTo;
  
  public int mutationTimer;
  
  public int moralRaisedTimer;
  
  public int convertionInt;
  
  public int convertionDelay;
  
  protected Block spawnableBlock = Blocks.GRASS;
  
  protected int inLove;
  
  public float reachWidth;
  
  public int attackTimer;
  
  public int holdRoseTick;
  
  public int lastChanceInvul;

  public float bookSpread;

  public int deathTicks;
  
  protected int blockTimer;
  
  public int exptobeadded;
  
  public float rotationPitchFalling;
  
  public float prevRotationPitchFalling;
  
  public String fakeTeam;

  private String compatCustomNameTag = "";

  public int timeUntilPortal;
  
  protected BossInfoServer bossInfo = new BossInfoServer(new TextComponentString("Engender Mob"), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
  
  private EnumStudy currentStudy = EnumStudy.Physical;
  
  private ItemStack currentReadingBook = ItemStack.EMPTY;
  
  public NBTTagCompound polymorpherData;
  
  public static final Item[] books = new Item[] {};
  
  public InventoryBasic basicInventory;
  
  public final Vec3d[][] renderLocations;
  
  private int rideCooldownCounter;
  
  private boolean limitedLifespan;
  
  private int limitedLifeTicks;
  
  private int colorCycleTime;
  
  public TextFormatting[] rainbow;
  
  private final EntityAIBreakDoor breakDoor;
  
  private final EntityAIOpenDoor openDoor;

  public EntityTameBase(World worldIn) {
    super(worldIn);
    this.breakDoor = new EntityAIBreakDoor(this);
    this.openDoor = new EntityAIOpenDoor(this, true);
    this.rainbow = new TextFormatting[] {
            TextFormatting.RED, TextFormatting.GOLD, TextFormatting.YELLOW, TextFormatting.GREEN, TextFormatting.DARK_GREEN, TextFormatting.AQUA, TextFormatting.BLUE, TextFormatting.DARK_BLUE, TextFormatting.LIGHT_PURPLE, TextFormatting.DARK_PURPLE,
            TextFormatting.DARK_RED };
    this.timeUntilPortal = 100;
    this.basicInventory = new InventoryBasic("Basic inventory", false, 8);
    this.lastChanceInvul = getSpawnTimer();
    this.chasingPosX = this.posX;
    this.chasingPosY = this.posY + getBaseEyeHeight();
    this.chasingPosZ = this.posZ;
    setDoorAItask(true);
    this.renderYawOffset = this.rotationYaw = this.rotationYawHead = this.rand.nextFloat() * 360.0F;
    this.onGround = true;
    Arrays.fill(this.inventoryArmorDropChances, 0.0F);
    Arrays.fill(this.inventoryHandsDropChances, 0.0F);
    this.experienceValue = 10;
    this.tasks.addTask(1, new EntityAIAvoidEntitySPC(this, EntityLivingBase.class, (Predicate<EntityLivingBase>) p_apply_1_ -> (p_apply_1_ != null && ((EntityTameBase.this.shouldFleeAtLowHealth() && p_apply_1_.isEntityAlive() && EntityTameBase.this.getIntelligence() > 4.0F) || (EntityTameBase.this.isChild() && EngenderConfig.mobs.useMobTalkerModels) || (p_apply_1_ instanceof EntityLiving && p_apply_1_.getHealth() <= 0.0F && p_apply_1_.deathTime <= 0 && !(p_apply_1_ instanceof EntityTameBase)) || (p_apply_1_ instanceof EntityWither && ((EntityWither)p_apply_1_).getInvulTime() > 0))),  16.0F, 1.25D, 1.25D));
    this.tasks.addTask(0, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(0, new EntityAIMobGirlMate(this, 1.2D));
    this.tasks.addTask(0, new EntityAIFollowWildAdult(this, 1.25D));
    this.tasks.addTask(0, new EntityAIBabyMobGirlFollowParent(this, 1.25D));
    this.tasks.addTask(13, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F));
    this.targetTasks.addTask(0, new EntityAIFriendlyHurtByTarget(this, true));
    this.targetTasks.addTask(0, new EntityAILeaderHurtByTarget(this));
    this.targetTasks.addTask(0, new EntityAILeaderHurtTarget(this));
    this.renderLocations = new Vec3d[2][4];
    for (int i = 0; i < 4; i++) {
      this.renderLocations[0][i] = new Vec3d(0.0D, 0.0D, 0.0D);
      this.renderLocations[1][i] = new Vec3d(0.0D, 0.0D, 0.0D);
    }
  }
  
  public TextFormatting rainbowText() {
    return this.rainbow[this.colorCycleTime];
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.DARK_GRAY;
  }
  
  public String getDescName() {
    return "";
  }

  public float getEyeHeight() {
    return getBaseEyeHeight();
  }

  protected float getBaseEyeHeight() {
    return this.height * 0.85F;
  }

  public String getName() {
    if (hasCustomName())
      return getCustomNameTag();
    String name = EntityList.getEntityString(this);
    return name == null ? "Engender Mob" : name;
  }

  public boolean hasCustomName() {
    return this.compatCustomNameTag != null && !this.compatCustomNameTag.isEmpty();
  }

  public String getCustomNameTag() {
    return this.compatCustomNameTag == null ? "" : this.compatCustomNameTag;
  }

  public void setCustomNameTag(String name) {
    this.compatCustomNameTag = name == null ? "" : name;
  }

  public ITextComponent getDisplayName() {
    return new TextComponentString(getName());
  }
  
  public int playMusic() {
    if (isChild()) {
      boolean help = false;
      for (EntityPlayer player : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world)) {
        if (player.getName().equals("Mrbt0907"))
          help = true; 
      } 
      return help ? 7 : 0;
    } 
    return 0;
  }
  
  public void heal(float healAmount) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (healAmount <= 0.0F)
        return; 
      float f = getHealth();
      if (f > 0.0F)
        setHealth(f + healAmount); 
    } 
  }
  
  public void setLimitedLife(int limitedLifeTicksIn) {
    this.limitedLifeTicks = limitedLifeTicksIn;
  }
  
  public boolean hasLimitedLife() {
    return this.limitedLifespan;
  }
  
  public int getLimitedLife() {
    return this.limitedLifeTicks;
  }
  
  public boolean setEntityOnShoulder(EntityPlayer p_191994_1_) {
    NBTTagCompound nbttagcompound = new NBTTagCompound();
    nbttagcompound.setString("id", getEntityString());
    writeToNBT(nbttagcompound);
    if (p_191994_1_.addShoulderEntity(nbttagcompound)) {
      this.world.removeEntity(this);
      return true;
    } 
    return false;
  }
  
  public boolean canSitOnShoulder() {
    return (this.rideCooldownCounter > 100);
  }
  
  public enum EnumStudy {
    Physical, Mental, Combative
  }
  
  public Vec3d[] getRenderLocations(float p_193098_1_) {
    if (getGhostTime() <= 0)
      return this.renderLocations[1]; 
    double d0 = ((getGhostTime() - p_193098_1_) / 3.0F);
    d0 = Math.pow(d0, 0.25D);
    Vec3d[] avec3d = new Vec3d[4];
    for (int i = 0; i < 4; i++)
      avec3d[i] = this.renderLocations[1][i].scale(1.0D - d0).add(this.renderLocations[0][i].scale(d0)); 
    return avec3d;
  }
  
  public void setCurrentBook(ItemStack stack) {
    this.currentReadingBook = stack;
  }
  
  public ItemStack getCurrentBook() {
    return (getBookID() != 0) ? this.currentReadingBook : ItemStack.EMPTY;
  }

  protected boolean isMovementBlocked() {
    return getHealth() <= 0.0F;
  }

  public int getHoldRoseTick() {
    return this.holdRoseTick;
  }
  
  public int getAttackTimer() {
    return this.attackTimer;
  }
  
  public boolean shouldFleeAtLowHealth() {
    return (getHealth() <= 4.0F && getHealth() < getMaxHealth());
  }
  
  public static EngenderExplosion createEngenderModExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
    EngenderExplosion explosion = new EngenderExplosion(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entityIn), entityIn, x, y, z, strength, isFlaming, isSmoking);
    explosion.doExplosionA();
    explosion.doExplosionB(true);
    return explosion;
  }
  
  public static EngenderExplosion createEngenderModExplosionFireless(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
    return createEngenderModExplosion(entityIn, x, y, z, strength, false, isSmoking);
  }
  
  public void setArmsRaised(boolean armsRaised) {
    getDataManager().set(ARMS_RAISED, armsRaised);
  }
  
  public boolean isArmsRaised() {
    return getDataManager().get(ARMS_RAISED);
  }
  
  public int timesToConvert() {
    switch (getTier()) {
      case TIER1:
        return 3;
      case TIER2:
        return 5;
      case TIER3:
        return 11;
      case TIER4:
        return 23;
      case TIER5:
        return 79;
      case TIER6:
        return Integer.MAX_VALUE;
    } 
    return 3;
  }
  
  public final float getStrength() {
    return (float)net.minecraft.AgeOfMinecraft.util.AttributeCompat.getBaseValue(this, STRENGTH, getDefaultStrengthStat());
  }
  
  public void setStrength(float health) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      net.minecraft.AgeOfMinecraft.util.AttributeCompat.setBaseValue(this, STRENGTH, health);
    } 
  }
  
  public final float getStamina() {
    return (float)net.minecraft.AgeOfMinecraft.util.AttributeCompat.getBaseValue(this, STAMINA, getDefaultStaminaStat());
  }
  
  public void setStamina(float health) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      net.minecraft.AgeOfMinecraft.util.AttributeCompat.setBaseValue(this, STAMINA, health);
    } 
  }
  
  public final float getIntelligence() {
    return (float)net.minecraft.AgeOfMinecraft.util.AttributeCompat.getBaseValue(this, INTELLIGENCE, getDefaultIntelligenceStat());
  }
  
  public void setIntelligence(float health) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (health <= 0.0F)
        health = 0.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      net.minecraft.AgeOfMinecraft.util.AttributeCompat.setBaseValue(this, INTELLIGENCE, health);
    } 
  }
  
  public final float getDexterity() {
    return (float)net.minecraft.AgeOfMinecraft.util.AttributeCompat.getBaseValue(this, DEXTERITY, getDefaultDexterityStat());
  }
  
  public void setDexterity(float health) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      net.minecraft.AgeOfMinecraft.util.AttributeCompat.setBaseValue(this, DEXTERITY, health);
    } 
  }
  
  public final float getAgility() {
    return (float)net.minecraft.AgeOfMinecraft.util.AttributeCompat.getBaseValue(this, AGILITY, getDefaultAgilityStat());
  }
  
  public void setAgility(float health) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      net.minecraft.AgeOfMinecraft.util.AttributeCompat.setBaseValue(this, AGILITY, health);
    } 
  }
  
  public final float getFittness() {
    return (float)net.minecraft.AgeOfMinecraft.util.AttributeCompat.getBaseValue(this, FITTNESS, getDefaultFittnessStat());
  }
  
  public void setFittness(float health) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (health <= 0.75F)
        health = 0.75F; 
      if (health >= 1.5F)
        health = 1.5F; 
      net.minecraft.AgeOfMinecraft.util.AttributeCompat.setBaseValue(this, FITTNESS, health);
      setSize(this.width, this.height);
    } 
  }
  
  public float getDefaultFittnessStat() {
    return 0.9F + this.rand.nextFloat() * 0.2F;
  }
  
  public final float getEnergy() {
    return this.getDataManager().get(ENERGY);
  }
  
  public void setEnergy(float health) {
    this.getDataManager().set(ENERGY, health);
  }

  @Deprecated
  public EnumStudy getCurrentStudy() {
    return this.currentStudy;
  }
  
  @Deprecated
  public void setCurrentStudy(EnumStudy study, int exp) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !hasLimitedLife()) {
      this.currentStudy = study;
      if (isHero())
        exp *= 2; 
      this.exptobeadded += exp;
      if (isChild())
        this.exptobeadded += exp; 
      if (study == EnumStudy.Mental && this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither) {
        this.exptobeadded += exp;
        this.exptobeadded += exp;
      } 
    } 
  }
  
  public final float getFakeHealth() {
    return this.getDataManager().get(FAKE_HEALTH);
  }
  
  public void setFakeHealth(float health) {
    this.getDataManager().set(FAKE_HEALTH, MathHelper.clamp(health, 0.0F, getMaxHealth() * 2.0F));
  }
  
  protected void jump() {
    if (getEnergy() > 5.0F)
      super.jump(); 
    setEnergy(getEnergy() - 0.05F);
  }
  
  public void levelUp() {
    setEXP(0.0F);
    spawnExplosionParticle();
    clearActivePotions();
    addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200));
    switch (getTier()) {
      case TIER1:
        heal(2.0F);
        break;
      case TIER2:
        heal(4.0F);
        break;
      case TIER3:
        heal(8.0F);
        break;
      case TIER4:
        heal(16.0F);
        break;
      case TIER5:
      case TIER6:
        heal(32.0F);
        break;
    } 
    heal(1.0F);
    setEnergy(100.0F);
    if (getLevel() >= 300) {
      setLevel(300);
      playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 0.5F, 1.0F);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + getEyeHeight(), this.posZ, 10 + this.rand.nextInt(40)));
    } else {
      if (getLevel() > 0 && getLevel() < 300)
        if (getLevel() == 299) {
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !isWild())
            getOwner().sendMessage(new TextComponentTranslation(TextFormatting.AQUA + getName() + TextFormatting.RESET + " has reached " + TextFormatting.GOLD + "Max Level" + TextFormatting.RESET + "!", new Object[0]));
          playSound(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + getEyeHeight(), this.posZ, 10 + this.rand.nextInt(40)));
        } else {
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !isWild())
            getOwner().sendMessage(new TextComponentTranslation(TextFormatting.AQUA + getName() + TextFormatting.RESET + " has reached " + TextFormatting.BLUE + "Level " + (getLevel() + 1) + TextFormatting.RESET + "!", new Object[0]));
          playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);
        }  
      setLevel(getLevel() + 1);
      setFittness(getFittness() + 0.001F);
      switch (getCurrentStudy()) {
        case Mental:
          setStrength(getStrength() + 0.5F);
          setStamina(getStamina() + 1.0F);
          setIntelligence(getIntelligence() + 2.0F);
          setDexterity(getDexterity() + 1.5F);
          setAgility(getAgility() + 0.5F);
          break;
        case Combative:
          setStrength(getStrength() + 2.0F);
          setStamina(getStamina() + 1.0F);
          setIntelligence(getIntelligence() + 1.0F);
          setDexterity(getDexterity() + 1.0F);
          setAgility(getAgility() + 2.0F);
          break;
        case Physical:
          setStrength(getStrength() + 1.5F);
          setStamina(getStamina() + 2.0F);
          setIntelligence(getIntelligence() + 0.5F);
          setDexterity(getDexterity() + 1.0F);
          setAgility(getAgility() + 0.5F);
          break;
      } 
    } 
  }
  
  public void onKillCommand() {
    playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
    spawnExplosionParticle();
    setDead();
  }
  
  protected void damageArmor(float damage) {
    damage /= 4.0F;
    if (damage < 1.0F)
      damage = 1.0F; 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
        if (!itemstack.isEmpty() && entityequipmentslot != EntityEquipmentSlot.MAINHAND && entityequipmentslot != EntityEquipmentSlot.OFFHAND)
          if (itemstack.getItem() instanceof net.minecraft.item.ItemArmor) {
            setCurrentStudy(EnumStudy.Combative, (int)damage);
            itemstack.damageItem((int)damage, this);
          }  
      }  
  }
  
  protected void damageShield(float damage) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (damage >= 3.0F && this.activeItemStack.getItem() == Items.SHIELD) {
        setCurrentStudy(EnumStudy.Combative, (int)damage);
        this.blockTimer = 40;
        int i = 1 + MathHelper.floor(damage);
        this.activeItemStack.damageItem(i, this);
        playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 1.0F);
        if (this.activeItemStack.isEmpty()) {
          EnumHand enumhand = getActiveHand();
          if (enumhand == EnumHand.MAIN_HAND) {
            setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
          } else {
            setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
          } 
          this.activeItemStack = ItemStack.EMPTY;
          playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
        } 
      }  
  }
  
  public boolean isActiveItemStackBlocking() {
    return (getAttackTarget() != null && this.blockTimer <= 0 && getHeldItemOffhand() != null && !isHandActive() && getHeldItemOffhand().getItem().getItemUseAction(getHeldItemOffhand()) == EnumAction.BLOCK && getDistanceSq(getAttackTarget()) < 25.0D);
  }
  
  protected boolean canDropLoot() {
    return (!this.isOffensive && isChild()) ? false : (!hasLimitedLife());
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(ARMS_RAISED, Boolean.FALSE);
    getDataManager().register(IS_CHILD, Boolean.FALSE);
    getDataManager().register(IS_MARRIED, Boolean.FALSE);
    getDataManager().register(OWNER_UNIQUE_ID, Optional.absent());
    getDataManager().register(HERO, Boolean.FALSE);
    getDataManager().register(REBIRTH, Boolean.FALSE);
    getDataManager().register(ANTIMOB, Boolean.FALSE);
    getDataManager().register(SITRESTING, Boolean.FALSE);
    getDataManager().register(HEROSPECIALATTACKTIMER, 0);
    getDataManager().register(AGE, 0);
    getDataManager().register(LEVEL, 0);
    getDataManager().register(EXP, 0.0F);
    getDataManager().register(TOTALEXP, 0.0F);
    getDataManager().register(ENERGY, 100.0F);
    getDataManager().register(FAKE_HEALTH, 0.0F);
    getDataManager().register(GUARD_BLOCK_POS, Optional.absent());
    getDataManager().register(BOOK_ID, 0);
    getDataManager().register(BOOK_DURABILITY, 0);
    getDataManager().register(GHOST_TIME, 0);
    getDataManager().register(ILLUSION_FORM_TIME, 0);
    getDataManager().register(POLYMORPH_TIME, 0);
    getDataManager().register(ATTACKSTATE, 0);
  }
  
  public int getAttackState() {
    return this.getDataManager().get(ATTACKSTATE);
  }
  
  public void setAttackState(int age) {
    this.getDataManager().set(ATTACKSTATE, age);
  }
  
  public int getPolymorphTime() {
    return this.getDataManager().get(POLYMORPH_TIME);
  }
  
  public void setPolymorphTime(int age) {
    this.getDataManager().set(POLYMORPH_TIME, age);
  }
  
  public int getIllusionFormTime() {
    return this.getDataManager().get(ILLUSION_FORM_TIME);
  }
  
  public void setIllusionFormTime(int age) {
    this.getDataManager().set(ILLUSION_FORM_TIME, age);
  }
  
  public int getGhostTime() {
    return this.getDataManager().get(GHOST_TIME);
  }
  
  public void setGhostTime(int age) {
    this.getDataManager().set(GHOST_TIME, age);
  }
  
  public int getBookID() {
    return this.getDataManager().get(BOOK_ID);
  }
  
  public void setBookID(int age) {
    this.getDataManager().set(BOOK_ID, age);
  }
  
  public int getBookDurability() {
    return this.getDataManager().get(BOOK_DURABILITY);
  }
  
  public void setBookDurability(int age) {
    this.getDataManager().set(BOOK_DURABILITY, age);
  }
  
  public boolean canBeMarried() {
    return (EngenderConfig.mobs.useMobTalkerModels && !isChild() && getTier() != EnumTier.TIER6 && isEntityAlive() && getPassengers().isEmpty() && getRidingEntity() == null);
  }
  
  public boolean isMarried() {
    return getDataManager().get(IS_MARRIED);
  }
  
  public void setMarried(boolean childZombie) {
    getDataManager().set(IS_MARRIED, childZombie);
  }
  
  public boolean isChild() {
    return getDataManager().get(IS_CHILD);
  }
  
  public void setChild(boolean childZombie) {
    getDataManager().set(IS_CHILD, childZombie);
    if (this.world != null && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
      iattributeinstance.removeModifier(BABY_SPEED_BOOST);
      if (childZombie)
        iattributeinstance.applyModifier(BABY_SPEED_BOOST); 
    } 
  }
  
  protected int getExperiencePoints(EntityPlayer player) {
    int xp = super.getExperiencePoints(player);
    if (isChild())
      xp = (int)(xp * 2.5F); 
    xp = (int)(xp + getStrength() / 10.0F);
    xp = (int)(xp + getStamina() / 10.0F);
    xp = (int)(xp + getAgility() / 10.0F);
    xp = (int)(xp + getIntelligence() / 10.0F);
    xp = (int)(xp + getDexterity() / 10.0F);
    xp = (int)(xp * getFittness());
    return xp;
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    setLeftHanded((this.rand.nextFloat() < 0.1F));
    setGrowingAge((this.world.rand.nextFloat() <= 0.05F) ? -20000 : 4000);
    setEnergy(100.0F);
    setLevel(1);
    setStrength(getDefaultStrengthStat());
    setStamina(getDefaultStaminaStat());
    setIntelligence(getDefaultIntelligenceStat());
    setDexterity(getDefaultDexterityStat());
    setAgility(getDefaultAgilityStat());
    setFittness(getDefaultFittnessStat());
    setDoorAItask((getIntelligence() < 12.0F));
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
    return livingdata;
  }
  
  public void createChild() {
    playSound(ESound.girlMoan, 1.0F, getSoundPitch());
    for (int i = 0; i < 10; i++)
      spawnHeartParticle(); 
  }
  
  public void playSound(SoundEvent soundIn, float volume, float pitch) {
    if (soundIn != null)
      super.playSound(soundIn, volume, pitch); 
  }
  
  public boolean isNotALivingThing() {
    return (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime || isEntityUndead() || getCreatureAttribute() == ESetup.CONSTRUCT || this instanceof Structure || this instanceof Elemental);
  }
  
  public float getDefaultStrengthStat() {
    return 4.0F + this.rand.nextFloat() * 8.0F;
  }
  
  public float getDefaultStaminaStat() {
    return isNotALivingThing() ? 100.0F : (4.0F + this.rand.nextFloat() * 8.0F);
  }
  
  public float getDefaultIntelligenceStat() {
    return 4.0F + this.rand.nextFloat() * 8.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 4.0F + this.rand.nextFloat() * 8.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 4.0F + this.rand.nextFloat() * 8.0F;
  }
  
  public float getBonusVSLight() {
    return 1.0F;
  }
  
  public float getBonusVSArmored() {
    return 1.0F;
  }
  
  public float getBonusVSFlying() {
    return 1.0F;
  }
  
  public float getBonusVSMassive() {
    return 1.0F;
  }
  
  public float getBonusVSTiny() {
    return 1.0F;
  }
  
  public float getBonusVSStructure() {
    return 1.0F;
  }
  
  public float getBonusVSElemental() {
    return 1.0F;
  }
  
  public float getBonusVSUndead() {
    return 1.0F;
  }
  
  public float getBonusVSEnder() {
    return 1.0F;
  }
  
  public float getBonusVSAnimal() {
    return 1.0F;
  }
  
  public boolean canBeMatedWith() {
    return (EngenderConfig.mobs.useMobTalkerModels && this.height >= 1.365F && !isChild());
  }
  
  public boolean canBeSteered() {
    return getControllingPassenger() instanceof EntityLivingBase;
  }
  
  public void incrementConversion(EntityPlayer player) {
    this.convertionInt = 0;
    this.convertionDelay = 0;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER1;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    double d0 = getEntityBoundingBox().getAverageEdgeLength();
    if (Double.isNaN(d0))
      d0 = 1.0D; 
    d0 *= isSneaking() ? 32.0D : 512.0D;
    return this.ignoreFrustumCheck ? true : ((distance < d0 * d0));
  }
  
  public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
    return true;
  }
  
  protected boolean canDespawn() {
    return (isWild() && !isABoss());
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
    getAttributeMap().registerAttribute(FITTNESS);
    getAttributeMap().registerAttribute(STRENGTH);
    getAttributeMap().registerAttribute(STAMINA);
    getAttributeMap().registerAttribute(INTELLIGENCE);
    getAttributeMap().registerAttribute(DEXTERITY);
    getAttributeMap().registerAttribute(AGILITY);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.LUCK);
  }
  
  public boolean getCanSpawnHere() {
    int reduction = getSpawnChanceReduction();
    if (this.world.getWorldInfo().getTerrainType() == net.minecraft.world.WorldType.FLAT) {
      reduction *= 5;
    }
    return this.rand.nextInt(reduction) == 0 && super.getCanSpawnHere();
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return 0.0F;
  }
  
  public int getSpawnChanceReduction() {
    switch (getTier()) {
      case TIER1:
        return 50;
      case TIER2:
        return 100;
      case TIER3:
        return 200;
      case TIER4:
        return 400;
      case TIER5:
      case TIER6:
        return 800;
    } 
    return 50;
  }
  
  public int getDoorBreakingThreashHold() {
    return 32;
  }
  
  public int getInstaDoorBreakingThreashHold() {
    return 64;
  }
  
  public int getIronDoorBreakingThreashHold() {
    return 96;
  }
  
  public boolean teleportTo_(double x, double y, double z) {
    double d0 = this.posX;
    double d1 = this.posY;
    double d2 = this.posZ;
    this.posX = x;
    this.posY = y;
    this.posZ = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos(this);
    World world = this.world;
    getRNG();
    if (world.isBlockLoaded(blockpos)) {
      boolean flag1 = false;
      while (!flag1 && blockpos.getY() > 0) {
        BlockPos blockpos1 = blockpos.down();
        IBlockState iblockstate = world.getBlockState(blockpos1);
        if (iblockstate.getMaterial().blocksMovement()) {
          flag1 = true;
          continue;
        } 
        this.posY--;
        blockpos = blockpos1;
      } 
      if (flag1) {
        setPositionAndUpdate(this.posX, this.posY, this.posZ);
        if (world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(getEntityBoundingBox()))
          flag = true; 
      } 
    } 
    if (!flag) {
      setPositionAndUpdate(d0, d1, d2);
      return false;
    } 
    getNavigator().clearPath();
    return true;
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  public boolean doesEntityNotTriggerPressurePlate() {
    return (getDexterity() > 8.0F);
  }
  
  public int getSpecialAttackTimer() {
    return this.getDataManager().get(HEROSPECIALATTACKTIMER);
  }
  
  public void setSpecialAttackTimer(int p_82215_1_) {
    this.getDataManager().set(HEROSPECIALATTACKTIMER, p_82215_1_);
  }
  
  public void performSpecialAttack() {}
  
  public void setDoorAItask(boolean enabled) {
    ((PathNavigateGround)getNavigator()).setBreakDoors(enabled);
    ((PathNavigateGround)getNavigator()).setEnterDoors(true);
    if (enabled) {
      EntityAICompat.removeTask(this, this.openDoor);
      EntityAICompat.addTask(this, 1, this.breakDoor);
    } else {
      EntityAICompat.removeTask(this, this.breakDoor);
      EntityAICompat.addTask(this, 1, this.openDoor);
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    String s;
    super.readEntityFromNBT(tagCompund);
    if (tagCompund.hasKey("PolymorpherData"))
      this.polymorpherData = tagCompund.getCompoundTag("PolymorpherData"); 
    if (tagCompund.hasKey("LifeTicks"))
      setLimitedLife(tagCompund.getInteger("LifeTicks")); 
    if (tagCompund.hasKey("FakeHealth", 99))
      setFakeHealth(tagCompund.getFloat("FakeHealth")); 
    if (tagCompund.hasKey("CurrentBook", 9)) {
      NBTTagList nbttaglist1 = tagCompund.getTagList("CurrentBook", 10);
      setCurrentBook(new ItemStack(nbttaglist1.getCompoundTagAt(0)));
    } 
    if (tagCompund.hasKey("STR", 99))
      setStrength(tagCompund.getFloat("STR")); 
    if (tagCompund.hasKey("STA", 99))
      setStamina(tagCompund.getFloat("STA")); 
    if (tagCompund.hasKey("INT", 99))
      setIntelligence(tagCompund.getFloat("INT")); 
    if (tagCompund.hasKey("DEX", 99))
      setDexterity(tagCompund.getFloat("DEX")); 
    if (tagCompund.hasKey("AGI", 99))
      setAgility(tagCompund.getFloat("AGI")); 
    if (tagCompund.hasKey("FIT", 99))
      setFittness(tagCompund.getFloat("FIT")); 
    setMarried(tagCompund.getBoolean("IsMarried"));
    setChild(tagCompund.getBoolean("IsBaby"));
    if (tagCompund.hasKey("OwnerUUID", 8)) {
      s = tagCompund.getString("OwnerUUID");
      if (!s.isEmpty()) {
        try {
          setOwnerId(UUID.fromString(s));
        } catch (Throwable throwable) {
          setOwnerId(null);
        }
      } else {
        setOwnerId(null);
      }
    } else {
      setOwnerId(null);
    }
    this.getDataManager().set(REBIRTH, tagCompund.getBoolean("LastChance"));
    this.getDataManager().set(HERO, tagCompund.getBoolean("Hero"));
    this.getDataManager().set(ANTIMOB, tagCompund.getBoolean("Anti"));
    this.getDataManager().set(SITRESTING, tagCompund.getBoolean("SitResting"));
    setSpecialAttackTimer(tagCompund.getInteger("SAT"));
    this.inLove = tagCompund.getInteger("InLove");
    setGrowingAge(tagCompund.getInteger("Age"));
    setLevel(tagCompund.getInteger("Level"));
    setEXP(tagCompund.getFloat("EXP"));
    setTotalEXP(tagCompund.getFloat("TotalEXP"));
    setBookID(tagCompund.getInteger("BookID"));
    setBookDurability(tagCompund.getInteger("BookDurability"));
    if (tagCompund.hasKey("Energy", 99))
      setEnergy(tagCompund.getFloat("Energy")); 
    setGhostTime(tagCompund.getInteger("MirrorTime"));
    setIllusionFormTime(tagCompund.getInteger("IllusionTime"));
    setPolymorphTime(tagCompund.getInteger("MorphTime"));
    setAttackState(tagCompund.getInteger("AttackState"));
    setDoorAItask((getIntelligence() < 12.0F));
    if (tagCompund.hasKey("GPX")) {
      int i = tagCompund.getInteger("GPX");
      int j = tagCompund.getInteger("GPY");
      int k = tagCompund.getInteger("GPZ");
      this.getDataManager().set(GUARD_BLOCK_POS, Optional.of(new BlockPos(i, j, k)));
      this.randPosX = i;
      this.randPosY = j;
      this.randPosZ = k;
    } else {
      this.getDataManager().set(GUARD_BLOCK_POS, Optional.absent());
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    if (this.polymorpherData != null)
      tagCompound.setTag("PolymorpherData", this.polymorpherData);
    NBTTagList nbttaglist = new NBTTagList();
    NBTTagCompound nbttagcompound = new NBTTagCompound();
    if (hasLimitedLife())
      tagCompound.setInteger("LifeTicks", getLimitedLife()); 
    if (!getCurrentBook().isEmpty())
      getCurrentBook().writeToNBT(nbttagcompound); 
    nbttaglist.appendTag(nbttagcompound);
    tagCompound.setTag("CurrentBook", nbttaglist);
    tagCompound.setFloat("FakeHealth", getFakeHealth());
    tagCompound.setFloat("STR", getStrength());
    tagCompound.setFloat("STA", getStamina());
    tagCompound.setFloat("INT", getIntelligence());
    tagCompound.setFloat("DEX", getDexterity());
    tagCompound.setFloat("AGI", getAgility());
    tagCompound.setFloat("FIT", getFittness());
    if (getOwnerId() == null) {
      tagCompound.setString("OwnerUUID", "");
    } else {
      tagCompound.setString("OwnerUUID", getOwnerId().toString());
    }
    tagCompound.setBoolean("IsMarried", isMarried());
    tagCompound.setBoolean("IsBaby", isChild());
    tagCompound.setBoolean("Hero", isHero());
    tagCompound.setBoolean("LastChance", this.getDataManager().get(REBIRTH));
    tagCompound.setBoolean("Anti", this.getDataManager().get(ANTIMOB));
    tagCompound.setBoolean("SitResting", this.getDataManager().get(SITRESTING));
    tagCompound.setInteger("SAT", getSpecialAttackTimer());
    tagCompound.setFloat("EXP", getEXP());
    tagCompound.setFloat("TotalEXP", getTotalEXP());
    tagCompound.setInteger("Age", getGrowingAge());
    tagCompound.setInteger("Level", getLevel());
    tagCompound.setInteger("BookID", getBookID());
    tagCompound.setInteger("BookDurability", getBookDurability());
    tagCompound.setFloat("Energy", getEnergy());
    tagCompound.setInteger("InLove", this.inLove);
    tagCompound.setInteger("MirrorTime", getGhostTime());
    tagCompound.setInteger("IllusionTime", getIllusionFormTime());
    tagCompound.setInteger("MorphTime", getPolymorphTime());
    tagCompound.setInteger("AttackState", getAttackState());
    if (getGuardBlock() != null) {
      tagCompound.setInteger("GPX", getGuardBlock().getX());
      tagCompound.setInteger("GPY", getGuardBlock().getY());
      tagCompound.setInteger("GPZ", getGuardBlock().getZ());
    } 
  }
  
  public Entity getControllingPassenger() {
    return getPassengers().isEmpty() ? null : getPassengers().get(0);
  }
  
  public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
    return true;
  }
  
  public boolean isCameo() {
    return false;
  }
  
  public boolean canBeButchered() {
    return false;
  }
  
  public boolean isSitResting() {
    return this.getDataManager().get(SITRESTING);
  }
  
  public void setSitResting(boolean bool) {
    this.getDataManager().set(SITRESTING, bool);
  }
  
  public boolean isAntiMob() {
    return this.getDataManager().get(ANTIMOB);
  }
  
  public void setIsAntiMob(boolean bool) {
    this.getDataManager().set(ANTIMOB, bool);
  }
  
  public boolean isHero() {
    return this.getDataManager().get(HERO);
  }
  
  public void setIsHero(boolean bool) {
    this.getDataManager().set(HERO, bool);
  }
  
  public void becomeAHero() {
    setIsHero(true);
    playSound(ESound.hero, 100.0F, 1.0F);
    this.ticksExisted = -20;
  }
  
  public boolean hasLastChance() {
    return this.getDataManager().get(REBIRTH);
  }
  
  public void setLastChance(boolean bool) {
    this.getDataManager().set(REBIRTH, bool);
  }
  
  public SoundCategory getSoundCategory() {
    return SoundCategory.MASTER;
  }
  
  @Nullable
  public BlockPos getGuardBlock() {
    return (BlockPos)((Optional<?>)this.getDataManager().get(GUARD_BLOCK_POS)).orNull();
  }
  
  public void setGuardBlock(@Nullable BlockPos pos) {
    this.getDataManager().set(GUARD_BLOCK_POS, Optional.fromNullable(pos));
  }
  
  public BlockPos getJukeboxToDanceTo() {
    return this.jukeBoxToDanceTo;
  }
  
  public void setJukeboxToDanceTo(BlockPos block) {
    this.jukeBoxToDanceTo = block;
  }
  
  @Nullable
  public UUID getOwnerId() {
    return (UUID)((Optional<?>)this.getDataManager().get(OWNER_UNIQUE_ID)).orNull();
  }
  
  public void setOwnerId(@Nullable UUID p_184754_1_) {
    this.getDataManager().set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
  }
  
  @Nullable
  public EntityLivingBase getOwner() {
    try {
      UUID uuid = getOwnerId();
      EntityPlayer player = (uuid == null) ? null : this.world.getPlayerEntityByUUID(uuid);
      return player;
    } catch (IllegalArgumentException var2) {
      return null;
    }
  }
  
  public boolean isOwner(EntityLivingBase entityIn) {
    return (entityIn == getOwner());
  }
  
  public boolean isWild() {
    return (getOwner() == null);
  }
  
  public boolean hasOwner(EntityPlayer player) {
    return (!isWild() && getOwner() == player);
  }
  
  protected void dropEquipmentUndamaged() {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !hasLimitedLife())
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
        if (!itemstack.isEmpty()) {
          if (EnchantmentHelper.hasBindingCurse(itemstack))
            attackEntityFrom((new DamageSource("cramming")).setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled(), 10.0F); 
          if (EnchantmentHelper.hasVanishingCurse(itemstack)) {
            playSound(SoundEvents.ITEM_TOTEM_USE, 1.0F, 1.0F);
            spawnExplosionParticle();
            setItemStackToSlot(entityequipmentslot, ItemStack.EMPTY);
          } else {
            playEquipSound(itemstack);
            entityDropItem(itemstack, getEyeHeight());
            setItemStackToSlot(entityequipmentslot, ItemStack.EMPTY);
          } 
        } 
      }  
  }
  
  public boolean affectedByCommandingStaff() {
    return (!isHero() && !isCameo());
  }
  
  public boolean attackable() {
    return (this.ticksExisted > getSpawnTimer() && !isAIDisabled() && isEntityAlive());
  }
  
  public boolean isASwarmingMob() {
    return false;
  }
  
  public EnumPushReaction getPushReaction() {
    return isAIDisabled() ? EnumPushReaction.IGNORE : super.getPushReaction();
  }
  
  public boolean isAThreatToOwner(EntityLivingBase otherEntity) {
    return false;
  }
  
  protected void despawnEntity() {
    if (isWild() && isNonBoss() && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortal))
      if (isNoDespawnRequired()) {
        this.idleTime = 0;
      } else {
        EntityPlayer entityPlayer = this.world.getClosestPlayerToEntity(this, -1.0D);
        if (entityPlayer != null) {
          double d0 = entityPlayer.posX - this.posX;
          double d1 = entityPlayer.posY - this.posY;
          double d2 = entityPlayer.posZ - this.posZ;
          double d3 = d0 * d0 + d1 * d1 + d2 * d2;
          if (canDespawn() && d3 > 50000.0D)
            setDead(); 
          if (this.idleTime > 10000 && this.rand.nextInt(1000) == 0 && d3 > 2000.0D && canDespawn()) {
            setDead();
          } else if (d3 < 2000.0D) {
            this.idleTime = 0;
          } 
        } 
      }  
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 1.0F;
  }
  
  public boolean isEntityInvulnerable(DamageSource source) {
    return (isWild() && getOwnerId() != null) ? true : super.isEntityInvulnerable(source);
  }
  
  protected float applyPotionDamageCalculations(DamageSource source, float damage) {
    damage = super.applyPotionDamageCalculations(source, damage);
    if (source.getTrueSource() == this)
      damage = 0.0F; 
    if (source.isMagicDamage() && isHero())
      damage = (float)(damage * 0.25D); 
    return damage;
  }
  
  protected float applyArmorCalculations(DamageSource source, float damage) {
    if (!source.isUnblockable()) {
      damageArmor(damage);
      damage = CombatRules.getDamageAfterAbsorb(damage, getTotalArmorValue(), (float)getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
    } else {
      damage *= 0.5F;
    } 
    return damage;
  }
  
  protected void damageEntity(DamageSource source, float amount) {
    if (!isEntityInvulnerable(source)) {
      amount = ForgeHooks.onLivingHurt(this, source, amount);
      Entity entity = source.getTrueSource();
      if (amount <= 0.0F)
        return; 
      if (isHero() && source.getDamageType() == "fall" && amount > 4.0F)
        amount = 4.0F; 
      if (this.world.getDifficulty() == EnumDifficulty.EASY)
        amount = Math.min(amount / 2.0F + 1.0F, amount); 
      if (this.world.getDifficulty() == EnumDifficulty.HARD)
        amount = amount * 3.0F / 2.0F; 
      if (source.getTrueSource() instanceof net.minecraft.entity.monster.EntityVex)
        amount *= 0.33F; 
      if (isHero() && (source.getDamageType() == "chaosImplosion" || source.getDamageType() == "de.GuardianFireball" || source.getDamageType() == "de.GuardianEnergyBall" || source.getDamageType() == "de.GuardianChaosBall"))
        amount *= 0.05F; 
      if (isHero() && entity != null)
        amount *= (entity instanceof net.minecraft.entity.monster.IMob) ? 0.33333334F : 0.75F; 
      String s = TextFormatting.getTextWithoutFormattingCodes(getName());
      if (getClass().equals(EntityCow.class) && s != null && s.equals("Bessy"))
        amount *= 0.01F; 
      amount = applyArmorCalculations(source, amount);
      amount = applyPotionDamageCalculations(source, amount);
      float f = amount;
      amount = Math.max(amount - getAbsorptionAmount(), 0.0F);
      setAbsorptionAmount(getAbsorptionAmount() - f - amount);
      amount /= getFittness();
      if (getDamageCap() > 0 && amount >= (getDamageCap() / 2))
        amount /= this.height + this.width; 
      if (getDamageCap() > 0 && amount >= getDamageCap())
        amount = getDamageCap(); 
      if (amount != 0.0F && this.lastChanceInvul <= 0) {
        if (getIllusionFormTime() > 0) {
          playSound(ESound.bugSpecial, 1.0F, 1.0F);
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          spawnExplosionParticle();
          setIllusionFormTime(0);
        } 
        this.limbSwingAmount++;
        setEnergy(getEnergy() - 0.1F);
        float f1 = getHealth();
        setHealth(f1 - amount);
        getCombatTracker().trackDamage(source, f1, amount);
        setAbsorptionAmount(getAbsorptionAmount() - amount);
      } 
    } 
  }
  
  protected void damageEntityTraining(DamageSource source, float amount) {
    if (!isEntityInvulnerable(source) && getFakeHealth() > 0.0F) {
      Entity entity = source.getTrueSource();
      if (amount <= 0.0F)
        return; 
      if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        amount = 0.0F; 
      if (this.world.getDifficulty() == EnumDifficulty.EASY)
        amount *= 0.75F; 
      if (this.world.getDifficulty() == EnumDifficulty.HARD)
        amount *= 1.5F; 
      if (source.getTrueSource() instanceof net.minecraft.entity.monster.EntityVex)
        amount *= 0.33F; 
      if (isHero() && entity != null)
        amount *= (entity instanceof net.minecraft.entity.monster.IMob) ? 0.33333334F : 0.75F; 
      amount = applyArmorCalculations(source, amount);
      amount = applyPotionDamageCalculations(source, amount);
      float f = amount;
      amount = Math.max(amount - getAbsorptionAmount(), 0.0F);
      setAbsorptionAmount(getAbsorptionAmount() - f - amount);
      if (amount != 0.0F) {
        this.limbSwingAmount++;
        float f1 = getFakeHealth();
        setFakeHealth(f1 - amount);
        heal(getMaxHealth());
        getCombatTracker().trackDamage(source, f1, amount);
        setAbsorptionAmount(getAbsorptionAmount() - amount);
        if (getFakeHealth() <= 0.0F) {
          this.ticksExisted = 0;
          this.lastChanceInvul = 100;
          clearActivePotions();
          heal(getMaxHealth());
          addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 4));
          playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
          setAttackTarget(null);
          if (entity instanceof EntityTameBase) {
            ((EntityTameBase)entity).clearActivePotions();
            ((EntityTameBase)entity).heal(getMaxHealth());
            ((EntityTameBase)entity).setAttackTarget(null);
          } 
          if (!isWild()) {
            incrementConversion((EntityPlayer)getOwner());
            getOwner().sendMessage(new TextComponentTranslation(getName() + " has been defeated in training by " + entity.getName(), new Object[0]));
          } 
        } 
      } 
    } 
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return super.attackEntityFrom(source, amount);
  }
  
  public int getDamageCap() {
    return 0;
  }
  
  public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
    if (this.rand.nextDouble() >= ((isEntityAlive() || this.rand.nextInt(200) != 0) ? getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue() : 0.0D)) {
      this.isAirBorne = true;
      this.motionY += strength;
      getNavigator().clearPath();
      this.prevRenderYawOffset = this.prevRotationYaw = this.prevRotationYawHead = this.renderYawOffset = this.rotationYaw = this.rotationYawHead = (float)MathHelper.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
      if (!isEntityAlive() && strength >= 1.0F)
        strength *= 2.0F; 
      float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
      this.motionX /= 2.0D;
      this.motionZ /= 2.0D;
      this.motionX -= xRatio / f * strength;
      this.motionZ -= zRatio / f * strength;
    } 
  }
  
  public void cleave(int lootingModifier, DamageSource source) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      this.recentlyHit = 100;
      for (int ai = 0; ai <= lootingModifier; ai++) {
        EntityTameBase addon = spawnBaby(this);
        addon.copyLocationAndAnglesFrom(this);
        this.world.spawnEntity(addon);
        addon.attackEntityFrom(DamageSource.STARVE, addon.getMaxHealth());
        addon.setHealth(0.0F);
        addon.setDead();
      } 
      attackEntityFrom(DamageSource.STARVE, getMaxHealth());
      playSound(getDeathSound(), getSoundVolume(), getSoundPitch());
      setHealth(0.0F);
    } 
  }
  
  protected float getStrengthMultiplier() {
    return 1.0F;
  }
  
  public void attackWithAdditionalEffects(Entity entity) {}
  
  public boolean attackEntityAsMob(Entity entity) {
    if (!isEntityAlive())
      return false; 
    if (this instanceof EntityEnderman && ((EntityEnderman)this).canDodgeAllAttacks())
      entity.hurtResistantTime = 0; 
    float f = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * (((isSneaking() || isInvisible() || !canEntityBeSeen(entity)) && entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != this) ? 3.0F : 1.0F) * (isHero() ? ((entity instanceof net.minecraft.entity.monster.IMob) ? 3.0F : 1.5F) : 1.0F) * getStrengthMultiplier();
    int i = 0;
    f *= (getStrength() + 50.0F) / 100.0F;
    if (entity instanceof net.minecraft.entity.passive.EntityAmbientCreature) {
      entity.startRiding(this);
      playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.5F);
      heal(2.0F);
    } 
    setSitResting(false);
    try {
      ReflectionHelper.findField(entity.getClass(), new String[] { "recentlyHit", "recentlyHit" }).setInt(entity, 100);
    } catch (Exception exception) {}
    if (isASwarmingMob())
      if (entity != null) {
        List<EntityTameBase> allies = this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(3.0D));
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && 
          !allies.isEmpty())
            for (EntityTameBase entities : allies) {
                if (entities.isEntityAlive() && entities.isASwarmingMob() && entities.getClass() == getClass())
                    f += 0.1F;
            }
      }  
    IAttributeInstance bonus = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    AttributeModifier vslight = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D831"), "Light Bonus", getBonusVSLight(), 1)).setSaved(false);
    AttributeModifier vsarmored = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D832"), "Armored Bonus", getBonusVSArmored(), 1)).setSaved(false);
    AttributeModifier vsmassive = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D833"), "Massive Bonus", getBonusVSMassive(), 1)).setSaved(false);
    AttributeModifier vsflying = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D834"), "Flying Bonus", getBonusVSFlying(), 1)).setSaved(false);
    AttributeModifier vstiny = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D835"), "Tiny Bonus", getBonusVSTiny(), 1)).setSaved(false);
    AttributeModifier vsender = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D836"), "Ender Bonus", getBonusVSEnder(), 1)).setSaved(false);
    AttributeModifier vselemental = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D837"), "Elemental Bonus", getBonusVSElemental(), 1)).setSaved(false);
    AttributeModifier vsstructure = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D838"), "Structure Bonus", getBonusVSStructure(), 1)).setSaved(false);
    AttributeModifier vsundead = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Undead Bonus", getBonusVSUndead(), 1)).setSaved(false);
    AttributeModifier vsanimal = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D840"), "Animal Bonus", getBonusVSAnimal(), 1)).setSaved(false);
    if (entity instanceof EntityLivingBase) {
      if (entity instanceof Light)
        if (!bonus.hasModifier(vslight))
          bonus.applyModifier(vslight);  
      if (entity instanceof Armored)
        if (!bonus.hasModifier(vsarmored))
          bonus.applyModifier(vsarmored);  
      if (entity instanceof Flying)
        if (!bonus.hasModifier(vsflying))
          bonus.applyModifier(vsflying);  
      if (entity instanceof Massive)
        if (!bonus.hasModifier(vsmassive))
          bonus.applyModifier(vsmassive);  
      if (entity instanceof Tiny)
        if (!bonus.hasModifier(vstiny))
          bonus.applyModifier(vstiny);  
      if (entity instanceof Elemental)
        if (!bonus.hasModifier(vselemental))
          bonus.applyModifier(vselemental);  
      if (entity instanceof Structure)
        if (!bonus.hasModifier(vsstructure))
          bonus.applyModifier(vsstructure);  
      if (entity instanceof Undead)
        if (!bonus.hasModifier(vsundead))
          bonus.applyModifier(vsundead);  
      if (entity instanceof Ender)
        if (!bonus.hasModifier(vsender))
          bonus.applyModifier(vsender);  
      if (entity instanceof Animal)
        if (!bonus.hasModifier(vsanimal))
          bonus.applyModifier(vsanimal);  
    } 
    AttributeModifier summondebuff = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D855"), "Summon Debuff", 0.5D, 1)).setSaved(false);
    if (hasLimitedLife() && !bonus.hasModifier(summondebuff))
      bonus.applyModifier(summondebuff); 
    if (EngenderConfig.mobs.useMobTalkerModels && this instanceof EntityEnderman && ((EntityEnderman)this).andr) {
      i += 60;
      f *= 10000.0F;
      if (entity instanceof EntityLivingBase) {
        entity.hurtResistantTime = 0;
        entity.motionY = this.height * 0.25D;
        ((EntityLivingBase)entity).knockBack(this, i * 0.5F + 0.3F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
      } 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        createEngenderModExplosionFireless(this, entity.posX, entity.posY, entity.posZ, 7.0F + entity.height + entity.width, false);
    } 
    if (EngenderConfig.mobs.useMobTalkerModels && this instanceof EntityGhast && ((EntityGhast)this).eleanor) {
      i += 30;
      f *= 1000.0F;
      if (entity instanceof EntityLivingBase) {
        entity.hurtResistantTime = 0;
        entity.motionY = this.height * 0.25D;
        ((EntityLivingBase)entity).knockBack(this, i * 0.5F + 0.3F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
      } 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        createEngenderModExplosionFireless(this, entity.posX, entity.posY, entity.posZ, 7.0F + entity.height + entity.width, false);
    } 
    if (isSneaking() && entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != this)
      i += 4; 
    if (entity instanceof EntityLivingBase) {
      f += EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(), ((EntityLivingBase)entity).getCreatureAttribute());
      f += EnchantmentHelper.getModifierForCreature(getHeldItemOffhand(), ((EntityLivingBase)entity).getCreatureAttribute());
      i += EnchantmentHelper.getKnockbackModifier(this);
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead) {
        i += 3;
        double d2 = entity.posX - this.posX;
        double d3 = entity.posZ - this.posZ;
        double d4 = d2 * d2 + d3 * d3;
        entity.motionX += d2 / d4 * 2.0D;
        entity.motionY += 0.5D;
        entity.motionZ += d3 / d4 * 2.0D;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon) {
        i += 4;
        double d2 = entity.posX - this.posX;
        double d3 = entity.posZ - this.posZ;
        double d4 = d2 * d2 + d3 * d3;
        entity.motionX += d2 / d4 * 2.0D;
        entity.motionY += 0.5D;
        entity.motionZ += d3 / d4 * 2.0D;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle) {
        i += 9;
        double d2 = entity.posX - this.posX;
        double d3 = entity.posZ - this.posZ;
        double d4 = d2 * d2 + d3 * d3;
        entity.motionX += d2 / d4 * 4.0D;
        entity.motionY += 0.5D;
        entity.motionZ += d3 / d4 * 4.0D;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer) {
        i += 12;
        double d2 = entity.posX - this.posX;
        double d3 = entity.posZ - this.posZ;
        double d4 = d2 * d2 + d3 * d3;
        entity.motionX += d2 / d4 * 6.0D;
        entity.motionY += 0.5D;
        entity.motionZ += d3 / d4 * 6.0D;
      } 
    } 
    if (Loader.isModLoaded("iceandfire") && entity instanceof EntityLivingBase && EntityGorgon.isStoneMob((EntityLivingBase)entity))
      entity.hurtResistantTime = 0; 
    if (getEntityAttribute(AGILITY).getBaseValue() >= this.rand.nextDouble() * 10000.0D) {
      entity.hurtResistantTime = 0;
      f *= 10.0F;
      playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 5.0F, 1.0F);
      if (EngenderConfig.general.useMessage && !isWild())
        getOwner().sendMessage(new TextComponentTranslation(getName() + " got a critical hit!", new Object[0]));
    } 
    boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), f);
    if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortal)
      flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor(), f);
    if (entity instanceof EntityLivingBase && !(entity instanceof EntityTameBase) && getCreatureAttribute() == ((EntityLivingBase)entity).getCreatureAttribute() && ((EntityLivingBase)entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEFINED)
      flag = entity.attackEntityFrom(!isWild() ? DamageSource.causePlayerDamage((EntityPlayer)getOwner()) : new DamageSource("generic"), f); 
    if (entity instanceof EntityPlayer && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
      flag = entity.attackEntityFrom(DamageSource.GENERIC, f);
    if (Loader.isModLoaded("draconicevolution") && entity instanceof EntityGuardianCrystal) {
      flag = entity.attackEntityFrom(!isWild() ? DamageSource.causePlayerDamage((EntityPlayer)getOwner()) : new DamageSource("generic"), f);
      ((EntityGuardianCrystal)entity).shieldTime = 0;
      entity.motionX = 0.0D;
      entity.motionY = 0.0D;
      entity.motionZ = 0.0D;
    } 
    if (entity instanceof IEntityMultiPart) {
      i = 0;
      if (entity != null) {
        Entity[] aentity = entity.getParts();
        if (aentity != null)
          for (Entity parts : aentity) {
            if (parts instanceof MultiPartEntityPart)
              flag = ((IEntityMultiPart)entity).attackEntityFromPart((MultiPartEntityPart)parts, DamageSource.causeMobDamage(this), f);
          }  
      } 
    } 
    if (flag && entity != null) {
      if (!(this instanceof IEntityMultiPart)) {
        faceEntity(entity, 180.0F, getVerticalFaceSpeed());
        this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime)
        playSound(SoundEvents.ENTITY_SLIME_ATTACK, getSoundVolume(), getSoundPitch()); 
      EntityPlayer player = this.world.getClosestPlayerToEntity(this, 16.0D);
      if (isHero()) {
        entity.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, getSoundPitch());
        if (player != null)
          player.onCriticalHit(entity); 
        if (entity instanceof net.minecraft.entity.monster.IMob) {
          entity.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, 1.0F, getSoundPitch());
          if (player != null)
            player.onEnchantmentCritical(entity); 
        } 
      } 
    } 
    if (entity instanceof EntityPlayer) {
      EntityPlayer entityplayer = (EntityPlayer)entity;
      ItemStack itemstack = getHeldItemMainhand();
      ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : null;
      if ((this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || !isNonBoss()) && itemstack1 != null && itemstack1.getItem() == Items.SHIELD) {
        entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
        this.world.setEntityState(entityplayer, (byte)30);
      } 
      if (itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemAxe && itemstack1 != null && itemstack1.getItem() == Items.SHIELD) {
        float f1 = 0.25F + EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
        if (this.rand.nextFloat() < f1) {
          entityplayer.getCooldownTracker().setCooldown(Items.SHIELD, 100);
          this.world.setEntityState(entityplayer, (byte)30);
        } 
      } 
    } 
    if ((entity instanceof EntityLivingBase || (entity instanceof EntityPlayer && !((EntityPlayer)entity).capabilities.disableDamage)) && (entity != this || (!isWild() && entity != getOwner()))) {
      swingArm(EnumHand.MAIN_HAND);
      setCurrentStudy(EnumStudy.Combative, (int)f);
      applyEnchantments(this, entity);
      if (entity instanceof EntityLivingBase && !entity.isNonBoss() && ((EntityLivingBase)entity).getHealth() <= 1.0F)
        ((EntityLivingBase)entity).setHealth(0.0F); 
      if (this instanceof EntityVex)
        ((EntityVex)this).setIsCharging(false); 
      ((EntityLivingBase)entity).limbSwingAmount++;
      if (((EntityLivingBase)entity).getHealth() > 1.0F)
        ((EntityLivingBase)entity).setHealth(((EntityLivingBase)entity).getHealth() - (isHero() ? 0.03F : 0.01F)); 
      if (entity.isEntityInvulnerable(DamageSource.causeMobDamage(this)) && f >= 6.0F) {
        ((EntityLivingBase)entity).setHealth(((EntityLivingBase)entity).getHealth() - f);
      if (entity.height >= 5.0F) {
          entity.playSound(ESound.fleshHitCrushHeavy, 2.0F, 1.0F);
        } else {
          entity.playSound(ESound.fleshHitCrush, 2.0F, 1.0F);
        } 
        if (((EntityLivingBase)entity).getHealth() <= 0.0F)
          ((EntityLivingBase)entity).onDeath(DamageSource.causeMobDamage(this));
      } 
    } 
    if (entity instanceof EntityLivingBase && (entity != this || (!isWild() && entity != getOwner())) && flag) {
      ((EntityLivingBase)entity).knockBack(this, i * 0.5F + 0.3F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
      if (entity instanceof EntityPlayerMP)
        ((EntityPlayerMP)entity).connection.sendPacket(new SPacketEntityVelocity(entity));
      int j = EnchantmentHelper.getFireAspectModifier(this);
      if (j > 0)
        entity.setFire(j * 4); 
      if (!getHeldItemMainhand().isEmpty() && getHeldItemMainhand().getItem() != null && !isWild())
        getHeldItemMainhand().hitEntity((EntityLivingBase)entity, (EntityPlayer)getOwner()); 
      if (!getHeldItemOffhand().isEmpty() && getHeldItemOffhand().getItem() != null && !isWild())
        getHeldItemOffhand().hitEntity((EntityLivingBase)entity, (EntityPlayer)getOwner()); 
      if (!getHeldItemMainhand().isEmpty() && getHeldItemMainhand().getItem() != null && this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie && getHeldItemMainhand().getItem() == Items.GOLDEN_SWORD)
        getHeldItemMainhand().setItemDamage(0); 
      if (!getHeldItemOffhand().isEmpty() && getHeldItemOffhand().getItem() != null && this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie && getHeldItemOffhand().getItem() == Items.GOLDEN_SWORD)
        getHeldItemOffhand().setItemDamage(0); 
      if (!getHeldItemMainhand().isEmpty() && getHeldItemMainhand().getItem() != null && this instanceof EntityVex && getHeldItemMainhand().getItem() == Items.IRON_SWORD)
        getHeldItemMainhand().setItemDamage(0); 
      if (!getHeldItemOffhand().isEmpty() && getHeldItemOffhand().getItem() != null && this instanceof EntityVex && getHeldItemOffhand().getItem() == Items.IRON_SWORD)
        getHeldItemOffhand().setItemDamage(0); 
      applyEnchantments(this, entity);
      setCurrentStudy(EnumStudy.Physical, (int)f);
    } 
    setEnergy(getEnergy() - 0.25F);
    if (EngenderConfig.general.useMessage && !entity.isEntityAlive() && !isWild()) {
      boolean flag1 = false;
      try {
        flag1 = ReflectionHelper.findField(entity.getClass(), new String[] { "dead", "dead" }).getBoolean(entity);
      } catch (Exception exception) {}
      if (!flag1) {
        if (entity instanceof EntityLivingBase)
          setCurrentStudy(EnumStudy.Combative, (int)((EntityLivingBase)entity).getMaxHealth()); 
        getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was " + (isInvisible() ? "ambushed" : (isSneaking() ? "assassinated" : "slain")) + " by " + getName() + " (" + getOwner().getName() + ")", new Object[0]));
      } 
    } 
    if (bonus.hasModifier(vslight))
      bonus.removeModifier(vslight); 
    if (bonus.hasModifier(vsarmored))
      bonus.removeModifier(vsarmored); 
    if (bonus.hasModifier(vsmassive))
      bonus.removeModifier(vsmassive); 
    if (bonus.hasModifier(vsflying))
      bonus.removeModifier(vsflying); 
    if (bonus.hasModifier(vstiny))
      bonus.removeModifier(vstiny); 
    if (bonus.hasModifier(vselemental))
      bonus.removeModifier(vselemental); 
    if (bonus.hasModifier(vsstructure))
      bonus.removeModifier(vsstructure); 
    if (bonus.hasModifier(vsundead))
      bonus.removeModifier(vsundead); 
    if (bonus.hasModifier(vsender))
      bonus.removeModifier(vsender); 
    if (bonus.hasModifier(vsanimal))
      bonus.removeModifier(vsanimal); 
    if (bonus.hasModifier(summondebuff))
      bonus.removeModifier(summondebuff); 
    return true;
  }
  
  public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
    if (!attackable())
      entitylivingbaseIn = null; 
    if (entitylivingbaseIn != null)
      setSitResting(false); 
    super.setAttackTarget(entitylivingbaseIn);
  }
  
  public void fireLightning(Entity entity, double x, double y, double z) {
    if (entity != null && entity.isEntityAlive()) {
      double d3 = x;
      double d4 = y;
      double d5 = z;
      double d6 = entity.posX - d3;
      double d7 = entity.posY - d4;
      double d8 = entity.posZ - d5;
      EntityPortalLightning entitywitherskull = new EntityPortalLightning(this.world, entity, this, d6, d7, d8);
      entitywitherskull.posY = d4;
      entitywitherskull.posX = d3;
      entitywitherskull.posZ = d5;
      entitywitherskull.accelerationY = d4;
      entitywitherskull.accelerationX = d3;
      entitywitherskull.accelerationZ = d5;
      entitywitherskull.targetEntity = entity;
      this.world.spawnEntity(entitywitherskull);
    } 
  }
  
  public void inflictCustomStatusEffect(EnumDifficulty scaling, EntityLivingBase entity, Potion effect, int time, int power) {
    if (scaling == EnumDifficulty.PEACEFUL && effect.isBadEffect())
      return; 
    if (scaling == EnumDifficulty.NORMAL)
      time *= 2; 
    if (scaling == EnumDifficulty.HARD)
      time *= 5; 
    if (time > 0)
      entity.addPotionEffect(new PotionEffect(effect, time * 20, power - 1 + scaling.getId())); 
  }
  
  public void inflictEngenderMobDamage(EntityLivingBase entity, String killmessage, DamageSource attacktype, float damage) {
    IAttributeInstance bonus = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    AttributeModifier vslight = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D831"), "Light Bonus", getBonusVSLight(), 1)).setSaved(false);
    AttributeModifier vsarmored = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D832"), "Armored Bonus", getBonusVSArmored(), 1)).setSaved(false);
    AttributeModifier vsmassive = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D833"), "Massive Bonus", getBonusVSMassive(), 1)).setSaved(false);
    AttributeModifier vsflying = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D834"), "Flying Bonus", getBonusVSFlying(), 1)).setSaved(false);
    AttributeModifier vstiny = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D835"), "Tiny Bonus", getBonusVSTiny(), 1)).setSaved(false);
    AttributeModifier vsender = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D836"), "Ender Bonus", getBonusVSEnder(), 1)).setSaved(false);
    AttributeModifier vselemental = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D837"), "Elemental Bonus", getBonusVSElemental(), 1)).setSaved(false);
    AttributeModifier vsstructure = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D838"), "Structure Bonus", getBonusVSStructure(), 1)).setSaved(false);
    AttributeModifier vsundead = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Undead Bonus", getBonusVSUndead(), 1)).setSaved(false);
    AttributeModifier vsanimal = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D840"), "Animal Bonus", getBonusVSAnimal(), 1)).setSaved(false);
    if (entity instanceof EntityLivingBase) {
      if (entity instanceof Light)
        if (!bonus.hasModifier(vslight))
          bonus.applyModifier(vslight);  
      if (entity instanceof Armored)
        if (!bonus.hasModifier(vsarmored))
          bonus.applyModifier(vsarmored);  
      if (entity instanceof Flying)
        if (!bonus.hasModifier(vsflying))
          bonus.applyModifier(vsflying);  
      if (entity instanceof Massive)
        if (!bonus.hasModifier(vsmassive))
          bonus.applyModifier(vsmassive);  
      if (entity instanceof Tiny)
        if (!bonus.hasModifier(vstiny))
          bonus.applyModifier(vstiny);  
      if (entity instanceof Elemental)
        if (!bonus.hasModifier(vselemental))
          bonus.applyModifier(vselemental);  
      if (entity instanceof Structure)
        if (!bonus.hasModifier(vsstructure))
          bonus.applyModifier(vsstructure);  
      if (entity instanceof Undead)
        if (!bonus.hasModifier(vsundead))
          bonus.applyModifier(vsundead);  
      if (entity instanceof Ender)
        if (!bonus.hasModifier(vsender))
          bonus.applyModifier(vsender);  
      if (entity instanceof Animal)
        if (!bonus.hasModifier(vsanimal))
          bonus.applyModifier(vsanimal);  
    } 
    AttributeModifier summondebuff = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D855"), "Summon Debuff", 0.5D, 1)).setSaved(false);
    if (hasLimitedLife() && !bonus.hasModifier(summondebuff))
      bonus.applyModifier(summondebuff); 
    entity.limbSwingAmount++;
    damage *= (((isSneaking() || isInvisible() || !canEntityBeSeen(entity)) && entity instanceof EntityLiving && ((EntityLiving)entity).getAttackTarget() != this) ? 3.0F : 1.0F) * (isHero() ? ((entity instanceof net.minecraft.entity.monster.IMob) ? 3.0F : 1.5F) : 1.0F);
    try {
      ReflectionHelper.findField(entity.getClass(), new String[] { "recentlyHit", "recentlyHit" }).setInt(entity, 100);
    } catch (Exception exception) {}
    if (Loader.isModLoaded("iceandfire") && entity instanceof EntityLivingBase && EntityGorgon.isStoneMob(entity))
      entity.hurtResistantTime = 0; 
    if (Loader.isModLoaded("draconicevolution") && entity instanceof EntityGuardianCrystal) {
      entity.attackEntityFrom(!isWild() ? DamageSource.causePlayerDamage((EntityPlayer)getOwner()) : new DamageSource("generic"), damage);
      ((EntityGuardianCrystal)entity).shieldTime = 0;
      entity.motionX = 0.0D;
      entity.motionY = 0.0D;
      entity.motionZ = 0.0D;
    } 
    if (attacktype.getTrueSource() != null && !(attacktype instanceof net.minecraft.util.EntityDamageSourceIndirect) && getEntityAttribute(AGILITY).getBaseValue() >= this.rand.nextDouble() * 10000.0D) {
      entity.hurtResistantTime = 0;
      damage *= 10.0F;
      playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 5.0F, 1.0F);
      if (EngenderConfig.general.useMessage && !isWild())
        getOwner().sendMessage(new TextComponentTranslation(getName() + " got a critical hit!", new Object[0]));
    } 
    damage *= getStrengthMultiplier();
    if (entity.isEntityAlive()) {
      if (entity instanceof IEntityMultiPart) {
        if (entity != null) {
          Entity[] aentity = entity.getParts();
          if (aentity != null) {
            Entity mob = aentity[this.rand.nextInt((entity.getParts()).length)];
            if (mob instanceof MultiPartEntityPart)
              if (!((IEntityMultiPart)entity).attackEntityFromPart((MultiPartEntityPart)mob, DamageSource.causeMobDamage(this), damage) && attacktype.getDamageType() == "sans")
                entity.setHealth(entity.getHealth() - 1.0F);  
          } 
        } 
      } else if (!entity.attackEntityFrom(attacktype, damage) && attacktype.getDamageType() == "sans") {
        entity.setHealth(entity.getHealth() - 1.0F);
      } 
      if (!(entity instanceof EntityTameBase) && entity.isEntityInvulnerable(attacktype) && (damage >= 6.0F || attacktype.isExplosion() || attacktype.isDamageAbsolute() || attacktype.isUnblockable() || attacktype == DamageSource.ANVIL || attacktype.canHarmInCreative() || attacktype.isMagicDamage() || attacktype == DamageSource.LAVA)) {
        entity.setHealth(entity.getHealth() - damage);
      if (entity.height >= 5.0F) {
          entity.playSound(ESound.fleshHitCrushHeavy, 2.0F, 1.0F);
        } else {
          entity.playSound(ESound.fleshHitCrush, 2.0F, 1.0F);
        } 
        if (entity.getHealth() <= 0.0F)
          entity.onDeath(attacktype); 
      } 
      entity.hurtResistantTime--;
      if (isABoss() && entity.isNonBoss())
        entity.hurtResistantTime = 0; 
      setCurrentStudy(EnumStudy.Combative, (int)(damage / 4.0F));
      entity.limbSwingAmount++;
      setCurrentStudy(EnumStudy.Physical, (int)damage);
      if (entity.getHealth() > 1.0F)
        entity.setHealth(entity.getHealth() - (isHero() ? 0.03F : 0.01F)); 
      if (!entity.isEntityAlive() && !isWild() && entity.getHealth() <= 0.0F) {
        boolean flag1 = false;
        try {
          flag1 = ReflectionHelper.findField(entity.getClass(), new String[] { "dead", "dead" }).getBoolean(entity);
        } catch (Exception exception) {}
        if (EngenderConfig.general.useMessage && !flag1)
          getOwner().sendMessage(new TextComponentTranslation(entity.getName() + killmessage + getName() + " (" + getOwner().getName() + ")", new Object[0]));
      } 
      if (bonus.hasModifier(vslight))
        bonus.removeModifier(vslight); 
      if (bonus.hasModifier(vsarmored))
        bonus.removeModifier(vsarmored); 
      if (bonus.hasModifier(vsmassive))
        bonus.removeModifier(vsmassive); 
      if (bonus.hasModifier(vsflying))
        bonus.removeModifier(vsflying); 
      if (bonus.hasModifier(vstiny))
        bonus.removeModifier(vstiny); 
      if (bonus.hasModifier(vselemental))
        bonus.removeModifier(vselemental); 
      if (bonus.hasModifier(vsstructure))
        bonus.removeModifier(vsstructure); 
      if (bonus.hasModifier(vsundead))
        bonus.removeModifier(vsundead); 
      if (bonus.hasModifier(vsender))
        bonus.removeModifier(vsender); 
      if (bonus.hasModifier(vsanimal))
        bonus.removeModifier(vsanimal); 
      if (bonus.hasModifier(summondebuff))
        bonus.removeModifier(summondebuff); 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRender3d(double x, double y, double z) {
    return true;
  }
  
  protected final void setSize(float width, float height) {
    if (this.ticksExisted >= 1 && getTier() != EnumTier.TIER6) {
      float fit = getFittness();
      width *= fit;
      height *= fit;
    } 
    if (isChild()) {
      width *= 0.5F;
      height *= 0.5F;
    } 
    this.reachWidth = width;
    if (width != this.width || height != this.height) {
      float f = this.width;
      this.width = width;
      this.height = height;
      if (this.width < f) {
        double d0 = width / 2.0D;
        setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + this.height, this.posZ + d0));
        return;
      } 
      AxisAlignedBB axisalignedbb = getEntityBoundingBox();
      setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + this.width, axisalignedbb.minY + this.height, axisalignedbb.minZ + this.width));
    } 
  }
  
  public int getGrowingAge() {
    return this.getDataManager().get(AGE);
  }
  
  public void setGrowingAge(int age) {
    this.getDataManager().set(AGE, age);
    if (age == 0)
      resetInLove(); 
    if (age < 0 && !isChild())
      setChild(true); 
  }
  
  public int getLevel() {
    return this.getDataManager().get(LEVEL);
  }
  
  public void setLevel(int age) {
    this.getDataManager().set(LEVEL, age);
  }
  
  public float getEXP() {
    return this.getDataManager().get(EXP);
  }
  
  public void setEXP(float age) {
    this.getDataManager().set(EXP, age);
  }
  
  public float getTotalEXP() {
    return this.getDataManager().get(TOTALEXP);
  }
  
  public void setTotalEXP(float age) {
    this.getDataManager().set(TOTALEXP, age);
  }
  
  public int getNextLevelRequirement() {
    return 5;
  }
  
  public boolean isImmuneToExplosions() {
    return (this.ticksExisted <= 20 || this.lastChanceInvul > 0 || isHero() || isAIDisabled());
  }
  
  public boolean takesFallDamage() {
    return (!isBeingRidden() || getFakeHealth() > 0.0F || this.lastChanceInvul > 0 || this instanceof Flying || this instanceof Massive);
  }
  
  public Entity changeDimension(int dimensionIn) {
    if (this.ticksExisted < 400)
      return null; 
    return super.changeDimension(dimensionIn);
  }
  
  protected void updateAITasks() {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getAttackTarget() == null && this.rand.nextInt(20) == 0) {
      List<EntityTameBase> training = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow((getAttackState() > 1) ? 1.0D : getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()), Predicates.and(EntitySelectors.IS_ALIVE, EntitySelectors.NOT_SPECTATING));
      for (int j2 = 0; j2 < 10 && !training.isEmpty(); j2++) {
        EntityTameBase entitylivingbase = training.get(this.rand.nextInt(training.size()));
        if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen(entitylivingbase) && entitylivingbase.getOwnerId() == getOwnerId() && entitylivingbase.getFakeHealth() > 0.0F) {
          setAttackTarget(entitylivingbase);
          break;
        } 
        training.remove(entitylivingbase);
      } 
    } 
    setGrowingAge(getGrowingAge() + 1);
    if (getGrowingAge() < 0)
      this.inLove = 0; 
    if (getGrowingAge() < 0 && !isChild()) {
      setChild(true);
    } else if (getGrowingAge() >= 0 && isChild()) {
      setChild(false);
      this.ticksExisted = 1;
      playSound(ESound.hero, 1.0F, 1.5F);
    } 
    if (getAttackState() > 0) {
      if (getAttackState() == 1 && getDistanceSq(getGuardBlock()) >= 576.0D)
        getNavigator().tryMoveToXYZ(getGuardBlock().getX(), getGuardBlock().getY(), getGuardBlock().getZ(), 1.0D); 
    } else {
      setGuardBlock(getPosition().down());
    } 
    this.timeUntilPortal--;
    if (this.ticksExisted > 400 && this.rand.nextInt(60) == 0 && !isWild() && (getOwner()).ticksExisted > 400 && getAttackTarget() == null && !isRiding() && !isBeingRidden()) {
      if (!isWild() && getAttackTarget() == null && !isRiding() && !isBeingRidden()) {
        int i11 = MathHelper.floor((getOwner()).posY);
        int l1 = MathHelper.floor((getOwner()).posX);
        int i2 = MathHelper.floor((getOwner()).posZ);
        for (int k2 = -2; k2 <= 2; k2++) {
          for (int l2 = -2; l2 <= 2; l2++) {
            for (int j = -2; j <= 2; j++) {
              int i3 = l1 + k2;
              int k = i11 + j;
              int l = i2 + l2;
              BlockPos blockpos = new BlockPos(i3 + 0.5D, k, l + 0.5D);
              IBlockState iblockstate = this.world.getBlockState(blockpos);
              Block block = iblockstate.getBlock();
              if ((block instanceof net.minecraft.block.BlockEndGateway || block instanceof net.minecraft.block.BlockEndPortal || block instanceof net.minecraft.block.BlockPortal) && this.world.getBlockState(blockpos.down()).getBlock() != Blocks.AIR && this.world.getBlockState(blockpos.down()).getBlock() != Blocks.PORTAL) {
                if (this instanceof EntityVex || this instanceof EntityGhast) {
                  getMoveHelper().setMoveTo(i3, k, l, 1.0D);
                } else {
                  getNavigator().tryMoveToXYZ(i3, k, l, 1.0D);
                } 
                if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof EntityGhast || getDistanceSqToCenter(blockpos) < 4.0D) {
                  if (block instanceof net.minecraft.block.BlockEndPortal && !isNonBoss())
                    changeDimension(1); 
                  setPositionAndUpdate(i3 + 0.5D, k, l + 0.5D);
                  if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker)
                    playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, getSoundVolume(), 0.95F); 
                  if (block instanceof net.minecraft.block.BlockEndGateway) {
                    this.world.setBlockToAir(blockpos.up(1));
                    this.world.setBlockToAir(blockpos.up(2));
                    this.world.setBlockToAir(blockpos.up(1).east());
                    this.world.setBlockToAir(blockpos.up(1).north());
                    this.world.setBlockToAir(blockpos.up(1).west());
                    this.world.setBlockToAir(blockpos.up(1).south());
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      if (Loader.isModLoaded("abyssalcraft")) {
        int i11 = MathHelper.floor((getOwner()).posY);
        int l1 = MathHelper.floor((getOwner()).posX);
        int i2 = MathHelper.floor((getOwner()).posZ);
        for (int k2 = -2; k2 <= 2; k2++) {
          for (int l2 = -2; l2 <= 2; l2++) {
            for (int j = -2; j <= 2; j++) {
              int i3 = l1 + k2;
              int k = i11 + j;
              int l = i2 + l2;
              BlockPos blockpos = new BlockPos(i3 + 0.5D, k, l + 0.5D);
              IBlockState iblockstate = this.world.getBlockState(blockpos);
              Block block = iblockstate.getBlock();
              if ((block instanceof com.shinoow.abyssalcraft.common.blocks.BlockAbyssPortal || block instanceof com.shinoow.abyssalcraft.common.blocks.BlockDreadlandsPortal || block instanceof com.shinoow.abyssalcraft.common.blocks.BlockOmotholPortal) && this.world.getBlockState(blockpos.down()).getBlock().isOpaqueCube(this.world.getBlockState(blockpos.down()))) {
                if (this instanceof EntityVex || this instanceof EntityGhast) {
                  getMoveHelper().setMoveTo(i3, k, l, 1.0D);
                } else {
                  getNavigator().tryMoveToXYZ(i3, k, l, 1.0D);
                } 
                if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof EntityGhast || getDistanceSqToCenter(blockpos) < 4.0D) {
                  if (block instanceof com.shinoow.abyssalcraft.common.blocks.BlockAbyssPortal && (!isNonBoss() || isBeingRidden() || isRiding()))
                    if (this.dimension == ACLib.abyssal_wasteland_id) {
                      changeDimension(0);
                    } else {
                      changeDimension(ACLib.abyssal_wasteland_id);
                    }  
                  if (block instanceof com.shinoow.abyssalcraft.common.blocks.BlockDreadlandsPortal && (!isNonBoss() || isBeingRidden() || isRiding()))
                    if (this.dimension == ACLib.dreadlands_id) {
                      changeDimension(ACLib.abyssal_wasteland_id);
                    } else {
                      changeDimension(ACLib.dreadlands_id);
                    }  
                  if (block instanceof com.shinoow.abyssalcraft.common.blocks.BlockOmotholPortal && (!isNonBoss() || isBeingRidden() || isRiding()))
                    if (this.dimension == ACLib.omothol_id) {
                      changeDimension(ACLib.dreadlands_id);
                    } else {
                      changeDimension(ACLib.omothol_id);
                    }  
                  setPositionAndUpdate(i3 + 0.5D, k, l + 0.5D);
                  if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker)
                    playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, getSoundVolume(), 0.95F); 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    if (!this.noClip && isEntityInsideOpaqueBlock()) {
      this.motionY += 0.5D;
      setPosition(this.posX, this.posY + 1.0D, this.posZ);
      if (isRiding() && getRidingEntity().isEntityInsideOpaqueBlock()) {
        (getRidingEntity()).motionY += 0.5D;
        getRidingEntity().setPosition(this.posX, this.posY + 1.0D, this.posZ);
      } 
    } 
    if (this.ticksExisted > 20 && isEntityAlive())
      super.updateAITasks(); 
  }
  
  public double getKnockbackResistance() {
    return getEntityAttribute(STRENGTH).getBaseValue() / 100.0D;
  }
  
  public boolean canUseGuardBlock() {
    return (!isChild() && !isInLove() && !isCameo());
  }
  
  public boolean canTrample(World world, Block block, BlockPos pos, float fallDistance) {
    return false;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.UNDEFINED;
  }
  
  public boolean isPotionApplicable(PotionEffect potioneffectIn) {
    Potion potion = potioneffectIn.getPotion();
    if (isEntityUndead() && (potion == MobEffects.REGENERATION || potion == MobEffects.POISON))
      return false; 
    if (isEntityImmuneToCoralium() && potion.getName() == "potion.Cplague") {
      onFinishedPotionEffect(potioneffectIn);
      return false;
    } 
    if (isEntityImmuneToDread() && potion.getName() == "potion.Dplague") {
      onFinishedPotionEffect(potioneffectIn);
      return false;
    } 
    if (isEntityImmuneToAntiMatter() && potion.getName() == "potion.Antimatter") {
      onFinishedPotionEffect(potioneffectIn);
      return false;
    } 
    return true;
  }
  
  public boolean isInLove() {
    return (this.inLove > 0);
  }
  
  public void resetInLove() {
    this.inLove = 0;
  }
  
  public void setInLove(EntityPlayer player) {
    this.inLove = 600;
  }
  
  public double getYOffset() {
    return (this.height <= 0.5F) ? 0.2D : 0.0D;
  }
  
  public void dismountRidingEntity() {
    Entity entity = getRidingEntity();
    super.dismountRidingEntity();
    if (entity != null && entity != getRidingEntity()) {
      copyLocationAndAnglesFrom(entity);
      this.lastTickPosY = this.prevPosY = this.posY += entity.height;
    } 
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return null;
  }

  public boolean processInteract(EntityPlayer player, EnumHand hand) {
    ItemStack itemstack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(itemstack.getItem());
    if (isEntityAlive() && itemstack.getItem() != null && !itemstack.isEmpty() && itemstack.getItem() == EItem.statChecker) {
      itemstack.getItem().itemInteractionForEntity(itemstack, player, this, hand);
      return true;
    }
    if (hasLimitedLife())
      return false;
    if (itemstack.getItem() == Items.SPAWN_EGG) {
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        Class<? extends Entity> oclass = EntityList.getClass(ItemMonsterPlacer.getNamedIdFrom(itemstack));
        if (oclass != null && getClass() == oclass) {
          EntityTameBase idleTimeable = spawnBaby(this);
          if (idleTimeable != null) {
            idleTimeable.setOwnerId(getOwnerId());
            idleTimeable.copyLocationAndAnglesFrom(this);
            this.world.spawnEntity(idleTimeable);
            idleTimeable.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
            idleTimeable.setGrowingAge(-24000);
            if (itemstack.hasDisplayName())
              idleTimeable.setCustomNameTag(itemstack.getDisplayName());
            if (!player.capabilities.isCreativeMode)
              itemstack.shrink(1);
          }
        }
      }
      return true;
    }
    if (isChild()) {
      if (hasOwner(player)) {
        player.swingArm(EnumHand.MAIN_HAND);
        if (getRidingEntity() == null) {
          startRiding(player, true);
        } else {
          dismountRidingEntity();
        }
      }
      return true;
    }
    if (hasOwner(player) && getRidingEntity() != null) {
      player.swingArm(EnumHand.MAIN_HAND);
      dismountRidingEntity();
      return true;
    }
    if (EngenderConfig.mobs.breeding && canBeMatedWith() && itemstack.getItem() == Items.GOLDEN_APPLE && itemstack.getMetadata() == ((getTier() == EnumTier.TIER5 || getTier() == EnumTier.TIER6) ? 1 : 0)) {
      if (hasOwner(player))
        if (isInLove()) {
          player.sendStatusMessage(new TextComponentTranslation(getName() + " is already horny!!", new Object[0]), true);
        } else if (getGrowingAge() < 4000) {
          player.sendStatusMessage(new TextComponentTranslation(getName() + " can't breed yet as they're too tired!", new Object[0]), true);
        } else if (getJukeboxToDanceTo() != null) {
          player.sendStatusMessage(new TextComponentTranslation("You can't breed with " + getName() + " as she's already having fun!", new Object[0]), true);
        } else if (getAttackTarget() != null) {
          player.sendStatusMessage(new TextComponentTranslation("You can't breed with " + getName() + " as it isn't safe yet!", new Object[0]), true);
        } else if (isInLava()) {
          player.sendStatusMessage(new TextComponentTranslation("You can't breed with " + getName() + " as she's swimming in lava!", new Object[0]), true);
        } else if (isBurning()) {
          player.sendStatusMessage(new TextComponentTranslation("You can't breed with " + getName() + " as she's on fire!", new Object[0]), true);
        } else if (this.isInWeb) {
          player.sendStatusMessage(new TextComponentTranslation("You can't breed with " + getName() + " as she's too stuck in a web to do anything!", new Object[0]), true);
        } else if (!isEntityAlive()) {
          player.sendStatusMessage(new TextComponentTranslation("You can't breed with a dead girl...", new Object[0]), true);
        } else {
          setInLove(player);
          player.swingArm(EnumHand.MAIN_HAND);
          itemstack.shrink(1);
        }
      return true;
    }
    if (itemstack.getItem().itemInteractionForEntity(itemstack, player, this, hand))
      return true;
    if (canWearEasterEggs() && !itemstack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty() && (itemstack.getItem() == Items.SKULL || itemstack.getItem() == Items.FISH || itemstack.getItem() == Items.BONE || itemstack.getItem() == Item.getItemFromBlock(Blocks.END_ROD) || itemstack.getItem() == Items.FEATHER)) {
      setItemStackToSlot(EntityEquipmentSlot.HEAD, itemstack);
      playEquipSound(itemstack);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(itemstack.getTagCompound());
        heldItem.setItemDamage(itemstack.getItemDamage());
        setItemStackToSlot(EntityEquipmentSlot.HEAD, heldItem);
        itemstack.shrink(1);
      }
      return true;
    }
    if (canWearEasterEggs() && player.isSneaking() && itemstack.isEmpty()) {
      dropEquipmentUndamaged();
      player.swingArm(hand);
      return true;
    }
    if (!itemstack.isEmpty() && getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty() && itemstack.getItem() != Items.NAME_TAG && itemstack.getItem() instanceof ItemFood && getEnergy() <= 100.0F) {
      playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
      player.swingArm(hand);
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        heldItem.setTagCompound(itemstack.getTagCompound());
        heldItem.setItemDamage(itemstack.getItemDamage());
        heldItem.setCount(itemstack.getCount());
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, heldItem);
        itemstack.shrink(itemstack.getCount());
      }
      return true;
    }
    return interact(player, hand);
  }
  
  public boolean canWearEasterEggs() {
    return true;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    return false;
  }

  protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
    super.dropEquipment(wasRecentlyHit, lootingModifier);
  }
  
  public int getVerticalFaceSpeed() {
    return !isEntityAlive() ? 180 : 60;
  }
  
  public boolean leavesNoCorpse() {
    return (isMarried() || this.world.getGameRules().getBoolean("disableCorpses"));
  }
  
  protected void onDeathUpdate() {
    this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    getNavigator().clearPath();
    extinguish();
    clearActivePotions();
    setAttackTarget(null);
    if (this.onGround) {
      this.deathTime++;
      this.limbSwingAmount = 0.0F;
      this.limbSwing = 0.0F;
      setArmsRaised(false);
      this.prevRotationPitchFalling = this.rotationPitchFalling = 0.0F;
    } else if (getTier() == EnumTier.TIER6 || this instanceof EntityVex || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither) {
      this.deathTime++;
    } else {
      this.deathTime = 0;
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (this.deathTime == 2) {
        EntityAICompat.clearTasks(this);
        EntityAICompat.clearTargetTasks(this);
        this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
        if (getOwner() != null)
          if (isHero()) {
            for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world))
              entityplayer.sendStatusMessage(new TextComponentTranslation("§4" + getOwner().getName() + "'s " + getName() + " has been killed!!!", new Object[0]), true);
            getOwner().sendMessage(new TextComponentTranslation("A Hero mob has fallen!", new Object[0]));
          }  
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")) {
          int i = getExperiencePoints(this.attackingPlayer);
          i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
          while (i > 0) {
            int j = EntityXPOrb.getXPSplit(i);
            i -= j;
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + getEyeHeight(), this.posZ, j));
          } 
          this.experienceValue = 0;
        } 
      }  
    if (this.recentlyHit > 0)
      this.recentlyHit++; 
    if (this.deathTime < 22 && this.deathTime > 2)
      this.rotationPitch--; 
    if (this.deathTime > 100) {
      if (this.onGround)
        setNoAI(true); 
      if (this.deathTime > 500 && !this.world.getBlockState(getPosition().up((int)this.height + 1)).getMaterial().isSolid())
        this.posY -= 0.025D; 
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.deathTime >= (leavesNoCorpse() ? 20 : 600)) {
      spawnExplosionParticle();
      this.world.removeEntity(this);
    } 
  }
  
  public void onDeath(DamageSource cause) {
    if (cause.getDamageType() == "antimatter") {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() * 2.0D);
      setHealth(getMaxHealth());
      this.deathTime = 0;
      this.dead = false;
      setIsAntiMob(true);
      this.ticksExisted = 0;
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = 0.0F;
      this.rotationPitch = 0.0F;
      int i = this.experienceValue;
      if (this instanceof EntityEvoker) {
        setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
        setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
        setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
        setDropChance(EntityEquipmentSlot.OFFHAND, 0.0F);
      } 
      while (i > 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
      } 
    } else if (hasLastChance()) {
      this.lastChanceInvul = 200;
      setHealth(1.0F);
      clearActivePotions();
      inflictCustomStatusEffect(EnumDifficulty.PEACEFUL, this, MobEffects.GLOWING, 10, 1);
      setRevengeTarget(null);
      setAttackTarget(null);
      setEnergy(100.0F);
      this.world.setEntityState(this, (byte)35);
      if (!isWild())
        copyLocationAndAnglesFrom(getOwner());
      setLastChance(false);
    } else {
      if (hasLimitedLife())
        onKillCommand(); 
      if (EngenderConfig.general.useMessage && !isWild() && getOwner() instanceof EntityPlayerMP && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer)) {
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          getOwner().sendMessage(getCombatTracker().getDeathMessage());
        this.world.playSound((EntityPlayer)getOwner(), getOwner().getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), getSoundPitch());
        ForgeHooks.onLivingDeath(this, DamageSource.causeMobDamage(getOwner()));
      } 
      dropEquipmentUndamaged();
      setAttackTarget(null);
      setRevengeTarget(null);
      if (this instanceof Flying) {
        float xRatio = MathHelper.sin(this.rotationYawHead * 0.017453292F);
        float zRatio = -MathHelper.cos(this.rotationYawHead * 0.017453292F);
        float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
        this.motionX -= xRatio / f * ((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() + 1.0F);
        this.motionZ -= zRatio / f * ((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() + 1.0F);
      } 
      if (cause.getTrueSource() instanceof EntityPlayerMP)
        CriteriaTriggers.PLAYER_KILLED_ENTITY.trigger((EntityPlayerMP)cause.getTrueSource(), this, cause);
      super.onDeath(cause);
    } 
  }
  
  protected void collideWithEntity(Entity entityIn) {
    if (attackable() && getRevengeTarget() == null && entityIn instanceof EntityLivingBase)
      if (!(entityIn instanceof EntityPlayer) || (entityIn instanceof EntityPlayer && !((EntityPlayer)entityIn).capabilities.disableDamage))
        setAttackTarget((EntityLivingBase)entityIn);  
    if (entityIn instanceof EntityLivingBase && isEntityAlive())
      if (!isRidingSameEntity(entityIn))
        if (!entityIn.noClip && !this.noClip) {
          double d0 = entityIn.posX - this.posX;
          double d1 = entityIn.posZ - this.posZ;
          double d2 = MathHelper.absMax(d0, d1);
          if (d2 >= 0.009999999776482582D) {
            d2 = MathHelper.sqrt(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;
            if (d3 > 1.0D)
              d3 = 1.0D; 
            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05000000074505806D;
            d1 *= 0.05000000074505806D;
            d0 *= (1.0F - this.entityCollisionReduction);
            d1 *= (1.0F - this.entityCollisionReduction);
            if (!isBeingRidden())
              addVelocity(-d0, 0.0D, -d1); 
            if (!entityIn.isBeingRidden())
              entityIn.addVelocity(d0, 0.0D, d1); 
          } 
        }   
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    if (this instanceof EntitySans)
      ((EntitySans)this).setAttackType(this.rand.nextInt(6)); 
    if (!isWild()) {
      EntityPlayer player = (EntityPlayer)getOwner();
      player.onKillEntity(entityLivingIn);
      setCurrentStudy(EnumStudy.Physical, (int)entityLivingIn.getMaxHealth());
    } 
    super.onKillEntity(entityLivingIn);
    getNavigator().clearPath();
    getNavigator().tryMoveToEntityLiving(this, 1.0D);
    if (getAttackTarget() != null && !getAttackTarget().isEntityAlive() && entityLivingIn == getAttackTarget())
      setAttackTarget(null);
    if (Loader.isModLoaded("abyssalcraft") && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (EngenderMod.canBeTurned(entityLivingIn)) {
        if (passesDreadPlague()) {
          EntityDreadling EntityDephsZombie = new EntityDreadling(this.world);
          EntityDephsZombie.copyLocationAndAnglesFrom(entityLivingIn);
          this.world.removeEntity(entityLivingIn);
          EntityDephsZombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(EntityDephsZombie)), null);
          EntityDephsZombie.setOwnerId(getOwnerId());
          this.world.spawnEntity(EntityDephsZombie);
          this.world.playEvent(null, 1026, new BlockPos(this.posX, this.posY, this.posZ), 0);
        } 
        if (passesCoraliumPlague())
          if (entityLivingIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid || entityLivingIn instanceof net.minecraft.entity.passive.EntitySquid || entityLivingIn instanceof EntityCoraliumSquid || entityLivingIn instanceof com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid) {
            EntityCoraliumSquid EntityDephsZombie = new EntityCoraliumSquid(this.world);
            EntityDephsZombie.copyLocationAndAnglesFrom(entityLivingIn);
            this.world.removeEntity(entityLivingIn);
            EntityDephsZombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(EntityDephsZombie)), null);
            EntityDephsZombie.setOwnerId(getOwnerId());
            if (entityLivingIn.isChild())
              EntityDephsZombie.setGrowingAge(-60000); 
            this.world.spawnEntity(EntityDephsZombie);
            this.world.playEvent(null, 1026, new BlockPos(this.posX, this.posY, this.posZ), 0);
          } else {
            EntityAbyssalZombie EntityDephsZombie = new EntityAbyssalZombie(this.world);
            EntityDephsZombie.copyLocationAndAnglesFrom(entityLivingIn);
            this.world.removeEntity(entityLivingIn);
            EntityDephsZombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(EntityDephsZombie)), null);
            EntityDephsZombie.setOwnerId(getOwnerId());
            if (entityLivingIn.isChild())
              EntityDephsZombie.setGrowingAge(-60000); 
            this.world.spawnEntity(EntityDephsZombie);
            this.world.playEvent(null, 1026, new BlockPos(this.posX, this.posY, this.posZ), 0);
          }  
      }  
  }
  
  protected void updateLeashedState() {
    if (getLeashed()) {
      if (!isEntityAlive() || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortal || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer)
        clearLeashed(true, true); 
      if (getLeashHolder() == null || (getLeashHolder()).isDead)
        clearLeashed(true, true); 
    } 
    if (getLeashed() && getLeashHolder() != null && (getLeashHolder()).world == this.world) {
      Entity entity = getLeashHolder();
      setHomePosAndDistance(new BlockPos((int)entity.posX, (int)entity.posY, (int)entity.posZ), 5);
      float f = getDistance(entity);
      onLeashDistance(f);
      if (f > 3.0F)
        getNavigator().tryMoveToEntityLiving(entity, 1.0D); 
      if (f > 9.0F) {
        double d0 = (entity.posX - this.posX) / f;
        double d1 = (entity.posY - this.posY) / f;
        double d2 = (entity.posZ - this.posZ) / f;
        this.motionX += d0 * Math.abs(d0) * 0.4D;
        this.motionY += d1 * Math.abs(d1) * 0.4D;
        this.motionZ += d2 * Math.abs(d2) * 0.4D;
      } 
    } 
  }
  
  public void spawnHeartParticle() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (isEntityAlive())
        this.world.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextGaussian(), this.posY + this.height, this.posZ + this.rand.nextGaussian(), 0.0D, 0.0D, 0.0D);
    } else {
      this.world.setEntityState(this, (byte)22);
    } 
  }
  
  public void spawnStressParticle() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      double d0 = this.rand.nextGaussian() * 0.02D;
      double d1 = this.rand.nextGaussian() * 0.02D;
      double d2 = this.rand.nextGaussian() * 0.02D;
      if (isEntityAlive())
        this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + getEyeHeight(), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2);
    } else {
      this.world.setEntityState(this, (byte)23);
    } 
  }
  
  public void spawnExplosionParticle() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      for (int i = 0; i < 2 * (int)(this.width + this.height) + 5; i++) {
        double d0 = this.rand.nextGaussian() * (this.width / 2.0F);
        double d1 = this.rand.nextDouble() * this.height;
        double d2 = this.rand.nextGaussian() * (this.width / 2.0F);
        if (isEntityAlive()) {
          this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + d0, this.posY + d1, this.posZ + d2, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D);
          this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + d0, this.posY + d1, this.posZ + d2, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D, this.rand.nextGaussian() * 0.02D);
          this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + d0, this.posY + d1, this.posZ + d2, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
        } 
      } 
    } else {
      this.world.setEntityState(this, (byte)20);
    } 
  }
  
  public void spawnConversionParticle() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      for (int i1 = 0; i1 < this.convertionInt; i1++) {
        float f1 = i1 * 3.1415927F / timesToConvert() * 0.5F;
        if (isEntityAlive())
          this.world.spawnParticle(EnumParticleTypes.END_ROD, true, this.posX + MathHelper.cos(f1) * (Math.min(this.width, 6.0F)), this.posY + this.height + 1.0D, this.posZ + MathHelper.sin(f1) * (Math.min(this.width, 6.0F)), this.motionX, this.motionY, this.motionZ);
      } 
    } else {
      this.world.setEntityState(this, (byte)21);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 23) {
      spawnStressParticle();
    } else if (id == 22) {
      spawnHeartParticle();
    } else if (id == 21) {
      spawnConversionParticle();
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  protected int decreaseAirSupply(int air) {
    int i = EnchantmentHelper.getRespirationModifier(this);
    if (isNotALivingThing())
      return air; 
    return (i > 0 && this.rand.nextInt(i + 1) > 0) ? air : (air - 1);
  }
  
  public void fall(float distance, float damageMultiplier) {
    this.motionX = 0.0D;
    this.motionZ = 0.0D;
    this.hurtTime = 0;
    this.prevRotationPitchFalling = this.rotationPitchFalling = 0.0F;
    float[] ret = ForgeHooks.onLivingFall(this, distance, damageMultiplier);
    if (ret == null)
      return; 
    distance = ret[0];
    damageMultiplier = ret[1];
    if (isBeingRidden())
      for (Entity entity : getPassengers())
        entity.fall(distance, damageMultiplier);  
    PotionEffect potioneffect = getActivePotionEffect(MobEffects.JUMP_BOOST);
    float f = (potioneffect == null) ? 0.0F : (potioneffect.getAmplifier() + 1);
    float energy = (getEnergy() <= 10.0F) ? 0.0F : (getEnergy() / 10.0F);
    int i = MathHelper.ceil((distance - 3.0F - f - energy) * damageMultiplier);
    if (i > 0) {
      playSound(getFallSound(i), 1.0F, 1.0F);
      if (takesFallDamage()) {
        setEnergy(getEnergy() - i);
        attackEntityFrom(DamageSource.FALL, i);
      } 
      int j = MathHelper.floor(this.posX);
      int k = MathHelper.floor(this.posY - 0.20000000298023224D);
      int l = MathHelper.floor(this.posZ);
      IBlockState iblockstate = this.world.getBlockState(new BlockPos(j, k, l));
      if (iblockstate.getMaterial() != Material.AIR) {
        SoundType soundtype = iblockstate.getBlock().getSoundType(iblockstate, this.world, new BlockPos(j, k, l), this);
        playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
        playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
        playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
      } 
    } 
    this.onGround = true;
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getRevengeTarget() != null && !getRevengeTarget().isEntityAlive())
      setRevengeTarget(null); 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getRevengeTarget() == null && getAttackTarget() == null)
      if (Maths.chance(75)) {
        List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()), Predicates.and((Predicate<Entity>) Entity::isEntityAlive,  EntitySelectors.CAN_AI_TARGET));
        for (int j2 = 0; j2 < 10 && !list1.isEmpty(); j2++) {
          EntityLivingBase entitylivingbase = list1.get(this.rand.nextInt(list1.size()));
          if (entitylivingbase != this) {
            if ((entitylivingbase instanceof EntityPlayer && !((EntityPlayer)entitylivingbase).isCreative() && !((EntityPlayer)entitylivingbase).isSpectator()) || !(entitylivingbase instanceof EntityPlayer)) {
              setAttackTarget(entitylivingbase);
              break;
            } 
          } else {
            list1.remove(entitylivingbase);
          } 
        } 
      }  
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && Loader.isModLoaded("mutantbeasts"))
      for (SkullSpiritEntity mutation : this.world.getEntitiesWithinAABB(SkullSpiritEntity.class, getEntityBoundingBox().grow(0.2D))) {
        mutation.setDead();
        this.mutationTimer = 1;
      }  
    if (!isAMutant() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.mutationTimer > 0 && !isABoss()) {
      this.mutationTimer++;
      this.motionX = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
      this.motionZ = ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
      if (this.mutationTimer >= 120) {
        this.mutationTimer = -1;
        this.world.playEvent(null, 3000, getPosition(), 0);
        InactMutation();
      } 
    } 
    if (!isWild() && this == getOwner().getLastAttackedEntity())
      getOwner().setLastAttackedEntity(null); 
    if (!isABoss() && !isHero() && getOwnerId() == null && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
      setDead();
    if (getAttackTarget() != null && !getAttackTarget().isEntityAlive())
      setAttackTarget(null);
    if (getGhostTime() > 0)
      setGhostTime(getGhostTime() - 1); 
    if (getIllusionFormTime() > 0)
      setIllusionFormTime(getIllusionFormTime() - 1); 
    if (getPolymorphTime() > 0)
      setPolymorphTime(getPolymorphTime() - 1); 
    if (this.polymorpherData != null && (getPolymorphTime() <= 0 || getHealth() <= 0.0F) && !(this instanceof EntityEvoker)) {
      EntityEvoker entityvex = new EntityEvoker(this.world);
      entityvex.copyLocationAndAnglesFrom(this);
      entityvex.playSound(ESound.bugSpecial, 10.0F, 0.5F);
      entityvex.playSound(ESound.blast, 10.0F, 1.0F);
      entityvex.spawnExplosionParticle();
      entityvex.readEntityFromNBT(this.polymorpherData);
      entityvex.writeEntityToNBT(this.polymorpherData);
      entityvex.setOwnerId(getOwnerId());
      entityvex.setIsHero(isHero());
      entityvex.setLastChance(hasLastChance());
      entityvex.setPolymorphTime((getHealth() <= 0.0F) ? 2000 : 600);
      entityvex.exptobeadded = this.exptobeadded;
      this.world.spawnEntity(entityvex);
      this.world.removeEntity(this);
    } 
    if (getLimitedLife() > 0) {
      this.limitedLifespan = true;
      for (int i = 0; i < this.width + this.height; i++) {
        if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
          double d4 = this.rand.nextGaussian() * 0.02D;
          double d5 = this.rand.nextGaussian() * 0.02D;
          double d6 = this.rand.nextGaussian() * 0.02D;
          double d7 = 10.0D;
          this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d4 * d7, this.posY + (this.rand.nextFloat() * this.height) - d5 * d7, this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width - d6 * d7, 0.0D, 0.0D, 0.0D);
        } 
      } 
    } 
    if (hasLimitedLife()) {
      setLimitedLife(getLimitedLife() - 1);
      if (getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() <= 4.0D) {
        if (EntityAICompat.hasTasks(this))
          EntityAICompat.clearTasks(this); 
        spawnExplosionParticle();
      } 
      if (getLimitedLife() <= 0) {
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() - 1.0D);
        if (getHealth() > getMaxHealth())
          setHealth(getMaxHealth()); 
        if (getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() < 1.0D)
          onKillCommand(); 
        setLimitedLife(5);
      } 
    } 
    setAlwaysRenderNameTag(false);
    this.rideCooldownCounter++;
    if (!isEntityAlive() || (getAttackTarget() != null && !getAttackTarget().isEntityAlive()))
      setAttackTarget(null);
    this.basicInventory.setInventorySlotContents(0, getItemStackFromSlot(EntityEquipmentSlot.HEAD));
    this.basicInventory.setInventorySlotContents(1, getItemStackFromSlot(EntityEquipmentSlot.CHEST));
    this.basicInventory.setInventorySlotContents(2, getItemStackFromSlot(EntityEquipmentSlot.LEGS));
    this.basicInventory.setInventorySlotContents(3, getItemStackFromSlot(EntityEquipmentSlot.FEET));
    this.basicInventory.setInventorySlotContents(4, getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));
    this.basicInventory.setInventorySlotContents(5, getItemStackFromSlot(EntityEquipmentSlot.OFFHAND));
    if (getTier() == EnumTier.TIER6) {
      setLevel(300);
      setTotalEXP(2.1474836E9F);
      setEXP(2.1474836E9F);
    } 
    if (this.fallDistance >= getHealth() * 2.0F && isEntityAlive() && takesFallDamage() && this.posY <= -45.0D)
      attackEntityFrom(DamageSource.FALL, Float.MAX_VALUE); 
    updateBossBar();
    if (!isABoss() && (!isRiding() || (isRiding() && getRidingEntity() instanceof net.minecraft.entity.projectile.EntityThrowable)) && (!(this instanceof Flying) || !isEntityAlive()) && !this.onGround && (this.hurtTime > 0 || isRiding() || !isEntityAlive())) {
      if (isInWater() || isInLava()) {
        this.motionY -= 0.10000000149011612D;
      } else {
        this.hurtTime = 10;
      } 
      this.isInWeb = false;
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
      getNavigator().clearPath();
      if (isRiding()) {
        for (this.rotationPitchFalling = (float)(-(getRidingEntity()).motionY * 114.59155902616465D); this.rotationPitchFalling - this.prevRotationPitchFalling < -180.0F; this.prevRotationPitchFalling -= 360.0F);
      } else {
        for (this.rotationPitchFalling = (float)(-this.motionY * 114.59155902616465D); this.rotationPitchFalling - this.prevRotationPitchFalling < -180.0F; this.prevRotationPitchFalling -= 360.0F);
      } 
      while (this.rotationPitchFalling - this.prevRotationPitchFalling >= 180.0F)
        this.prevRotationPitchFalling += 360.0F; 
      this.rotationPitchFalling = this.prevRotationPitchFalling + (this.rotationPitchFalling - this.prevRotationPitchFalling) * (float)((this.motionY == 0.0D) ? 0.01D : ((this.motionY < 0.0D) ? (-this.motionY / 5.0D) : (this.motionY / 5.0D)));
      if (this.rotationPitchFalling >= 90.0F)
        this.rotationPitchFalling = 90.0F; 
      if (this.rotationPitchFalling <= -90.0F)
        this.rotationPitchFalling = -90.0F; 
      if (this.rotationPitchFalling < 90.0F && this.rotationPitchFalling > -90.0F && !this.onGround && this.rotationYawHead != (float)MathHelper.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F) {
        this.prevRenderYawOffset = this.prevRotationYaw = this.prevRotationYawHead = this.renderYawOffset = this.rotationYaw = this.rotationYawHead = (float)MathHelper.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
        float xRatio = MathHelper.sin(this.rotationYawHead * 0.017453292F);
        float zRatio = -MathHelper.cos(this.rotationYawHead * 0.017453292F);
        float f1 = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
      } 
    } else {
      this.prevRotationPitchFalling = this.rotationPitchFalling = 0.0F;
    } 
    this.prevChasingPosX = this.chasingPosX;
    this.prevChasingPosY = this.chasingPosY;
    this.prevChasingPosZ = this.chasingPosZ;
    double d0 = this.posX - this.chasingPosX;
    double d1 = this.posY - this.chasingPosY;
    double d2 = this.posZ - this.chasingPosZ;
    double d3 = 10.0D;
    if (d0 > d3) {
      this.chasingPosX = this.posX;
      this.prevChasingPosX = this.chasingPosX;
    } 
    if (d2 > d3) {
      this.chasingPosZ = this.posZ;
      this.prevChasingPosZ = this.chasingPosZ;
    } 
    if (d1 > d3) {
      this.chasingPosY = this.posY;
      this.prevChasingPosY = this.chasingPosY;
    } 
    if (d0 < -d3) {
      this.chasingPosX = this.posX;
      this.prevChasingPosX = this.chasingPosX;
    } 
    if (d2 < -d3) {
      this.chasingPosZ = this.posZ;
      this.prevChasingPosZ = this.chasingPosZ;
    } 
    if (d1 < -d3) {
      this.chasingPosY = this.posY;
      this.prevChasingPosY = this.chasingPosY;
    } 
    this.chasingPosX += d0 * 0.25D;
    this.chasingPosZ += d2 * 0.25D;
    this.chasingPosY += d1 * 0.25D;
    if (!isEntityAlive() && this.posY <= -200.0D)
      setDead(); 
    if (isBeingRidden())
      for (Entity entity : getPassengers())
        entity.motionY = this.motionY;  
    if (!isEntityAlive()) {
      setAttackTarget(null);
      setRevengeTarget(null);
      dismountRidingEntity();
    } 
    this.isAirBorne = !this.onGround;
    if (this.blockTimer > 0)
      this.blockTimer--; 
    if (this.convertionInt > 0 && !isWild())
      this.convertionInt = 0; 
    if (this.ticksExisted % 300 == 0 && !isRiding() && !isBeingRidden() && getAttackTarget() == null && !getNavigator().noPath() && this.onGround && this.motionX == 0.0D && this.motionZ == 0.0D)
      setSitResting(true); 
    if (isSitResting())
      getNavigator().clearPath(); 
    if (isRiding() || isBeingRidden() || getAttackTarget() != null || !getNavigator().noPath() || !this.onGround || this.motionX != 0.0D || this.motionY != 0.0D || this.motionZ != 0.0D)
      setSitResting(false);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !getHeldItemMainhand().isEmpty() && getHeldItemMainhand().getItem() == Items.BOWL) {
      entityDropItem(new ItemStack(Items.BOWL), getEyeHeight());
      setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
      playSound(SoundEvents.ENTITY_WITCH_THROW, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
    }
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(getKnockbackResistance());
    if (!isWild() && getOwner() instanceof EntityPlayer && isEntityAlive() && 
      getTier() != EnumTier.TIER6 && getEXP() >= (getNextLevelRequirement() * getLevel()))
      levelUp(); 
    if (getEnergy() > 100.0F) {
      if (EngenderConfig.mobs.regeneration)
        heal(getEnergy() - 100.0F); 
      setEnergy(100.0F);
    } 
    if (getEnergy() <= 0.0F) {
      setEnergy(0.0F);
      if (isBeingRidden())
        dismountRidingEntity(); 
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (!(this instanceof EntityEnderman) && !(this instanceof EntitySans) && (isNotALivingThing() || isABoss() || getTier() == EnumTier.TIER6 || isAMutant())) {
        setEnergy(100.0F);
      } else {
        if (isPotionActive(MobEffects.HUNGER) && this.ticksExisted % 10 == 0)
          setEnergy(getEnergy() - 1.0F * getActivePotionEffect(MobEffects.HUNGER).getAmplifier()); 
        if (isPotionActive(MobEffects.REGENERATION) && getEnergy() < 100.0F && this.ticksExisted % 10 == 0)
          setEnergy(getEnergy() + 1.0F * getActivePotionEffect(MobEffects.REGENERATION).getAmplifier()); 
        if (isSprinting() && this.ticksExisted % 20 == 0)
          setEnergy(getEnergy() - 1.0F); 
        if (this.motionX == 0.0D && this.motionY == 0.0D && this.motionZ == 0.0D && getEnergy() < 100.0F && this.ticksExisted % 40 == 0)
          setEnergy(getEnergy() + 1.0F); 
      }  
    if (isElytraFlying() && this.ticksExisted % 20 == 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      setCurrentStudy(EnumStudy.Physical, 3); 
    if (isBeingRidden() && this.ticksExisted % 60 == 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      setCurrentStudy(EnumStudy.Physical, 1); 
    if (getAttackTarget() != null && (getAttackTarget() == this || !getAttackTarget().isEntityAlive()))
      setAttackTarget(null);
    if (this.attackTimer > 0)
      this.attackTimer--; 
    if (this.holdRoseTick > 0)
      this.holdRoseTick--; 
    if (this.moralRaisedTimer > 0)
      this.moralRaisedTimer--; 
    if (this.exptobeadded > 0) {
      this.exptobeadded--;
      setEXP(getEXP() + 1.0F);
      setTotalEXP(getTotalEXP() + 1.0F);
      if (this.exptobeadded >= 100)
        for (int ex = 1; ex <= this.exptobeadded / 10 * ex; ex++) {
          this.exptobeadded -= ex;
          setEXP(getEXP() + ex);
          setTotalEXP(getTotalEXP() + ex);
        }  
    } 
    if (this.lastChanceInvul > 0) {
      this.hurtResistantTime = this.lastChanceInvul;
      this.lastChanceInvul--;
    } 
    if (getEnergy() <= 5.0F)
      spawnStressParticle(); 
    if (getFakeHealth() <= 0.0F)
      setFakeHealth(0.0F); 
    if (isAIDisabled())
      this.limbSwing = 0.0F; 
    if (!isEntityAlive())
      clearActivePotions(); 
    if (isHero())
      extinguish(); 
    if (!isSitResting() || !isWild() || isHero() || !isNonBoss() || getTier() == EnumTier.TIER6 || this instanceof IEntityMultiPart)
      this.idleTime = 0; 
    if (EItem.carrier != null && (getHeldItemMainhand().getItem() == EItem.carrier || getHeldItemOffhand().getItem() == EItem.carrier))
      dropEquipmentUndamaged(); 
    if (this.ticksExisted > 20 && getRidingEntity() != null && getRidingEntity() instanceof EntityPlayer && !(this instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime) && !isChild())
      dismountRidingEntity(); 
    if (getLevel() >= 299 && getTier() != EnumTier.TIER6 && this.rand.nextInt(5) == 0)
      this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX + (this.rand.nextFloat() * this.width * 2.0F - this.width) * 0.6D, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextFloat() * this.width * 2.0F - this.width) * 0.6D, 0.0D, 0.01D, 0.0D);
    List<EntityTameBase> list = this.world.getEntitiesWithinAABB(EntityTameBase.class, getEntityBoundingBox().grow(0.1D), Predicates.and(EntitySelectors.IS_ALIVE));
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && list != null && !list.isEmpty() && isAntiMob())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityTameBase entity = list.get(i1);
        if (entity != null && entity.getClass() == getClass() && !entity.isAntiMob()) {
          createEngenderModExplosionFireless(this, this.posX, this.posY, this.posZ, 5.0F * (this.width + this.height) + list.size(), EngenderConfig.mobs.grief);
          entity.onKillCommand();
          onKillCommand();
        } 
      }  
    if (getHealth() > 0.0F) {
      if (this.deathTime > 0)
        this.deathTime--; 
      this.dead = false;
    }
    if (EngenderConfig.mobs.hunger && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive() && getEnergy() <= 0.0F && this.ticksExisted % 100 == 0)
      attackEntityFrom(DamageSource.STARVE, 2.0F); 
    if (EngenderConfig.mobs.regeneration) {
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive() && getEnergy() == 100.0F && this.ticksExisted % 60 == 0)
        heal(1.0F); 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive() && getEnergy() <= 90.0F && getEnergy() > 80.0F && getHealth() < getMaxHealth() && this.hurtResistantTime <= 10 && this.ticksExisted % 40 == 0) {
        heal(1.0F);
        setEnergy(getEnergy() - (float)(0.5009999871253967D - getEntityAttribute(STAMINA).getBaseValue() / 50.0D));
      } 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !isABoss() && !isNotALivingThing() && isEntityAlive() && getEnergy() < 100.0F && getEnergy() > 90.0F && getHealth() < getMaxHealth() && this.hurtResistantTime <= 10 && this.ticksExisted % 10 == 0) {
        heal(2.0F);
        setEnergy(getEnergy() - (float)(5.050000190734863D - getEntityAttribute(STAMINA).getBaseValue() / 20.0D));
      } 
    } 
    setSneaking((getOwner() != null && isEntityAlive() && (getOwner().isSneaking() || (this.inLove > 0 && this.ticksExisted % 5 == 0 && getDistanceSq(getOwner()) < 2.0D))));
    if (Loader.isModLoaded("abyssalcraft") && isEntityImmuneToAntiMatter() && isPotionActive(AbyssalCraftAPI.antimatter_potion)) {
      removeActivePotionEffect(MobEffects.SLOWNESS);
      removeActivePotionEffect(MobEffects.BLINDNESS);
      removeActivePotionEffect(MobEffects.WEAKNESS);
      removeActivePotionEffect(MobEffects.HUNGER);
      removeActivePotionEffect(AbyssalCraftAPI.antimatter_potion);
    } 
    if (isEntityImmuneToDarkness())
      removeActivePotionEffect(MobEffects.BLINDNESS); 
    if (isPotionActive(MobEffects.FIRE_RESISTANCE) || isImmuneToFire())
      extinguish(); 
    if (isPotionActive(MobEffects.FIRE_RESISTANCE) || isImmuneToFire() || getIntelligence() <= 6.0F) {
      setPathPriority(PathNodeType.LAVA, 0.0F);
      setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
      setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
    } else {
      setPathPriority(PathNodeType.LAVA, -1.0F);
      setPathPriority(PathNodeType.DANGER_FIRE, 8.0F);
      setPathPriority(PathNodeType.DAMAGE_FIRE, 16.0F);
    } 
    if (!isEntityAlive() && isBeingRidden())
      for (Entity entity : getPassengers()) {
        if (entity.isRiding())
          entity.dismountRidingEntity(); 
      }  
    if (isAIDisabled()) {
      this.hurtResistantTime = this.maxHurtResistantTime;
      if (this.ticksExisted > 21)
        this.ticksExisted--; 
    } 
    setSilent(isAIDisabled());
    setNoAI((this.deathTime > 40 || (isABoss() && !isEntityAlive()) || (getOwnerId() != null && getOwner() == null)));
    this.prevRotationPitchFalling = this.rotationPitchFalling;
  }
  
  public boolean isAMutant() {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return (!this.isDead && isEntityAlive() && !isAIDisabled());
  }
  
  public void addPotionEffect(PotionEffect potioneffectIn) {
    if ((!isABoss() && !isAMutant() && !isHero()) || !potioneffectIn.getPotion().isBadEffect())
      super.addPotionEffect(potioneffectIn); 
  }
  
  public void onLivingUpdate() {
    if (this.ticksExisted % 10 == 0)
      if (this.colorCycleTime < this.rainbow.length) {
        this.colorCycleTime++;
      } else {
        this.colorCycleTime = 0;
      }  
    if (this.ticksExisted % 50 == 0 && isAMutant())
      heal(2.0F); 
    if (this.convertionInt > 0) {
      setAttackTarget(null);
      this.rotationYawHead = this.rotationYaw = this.renderYawOffset = (this.ticksExisted * 5);
      this.convertionDelay--;
      if (this.convertionDelay <= 0) {
        this.convertionDelay = 40;
        this.convertionInt--;
      } 
    } 
    if (getFakeHealth() > 0.0F)
      this.fallDistance *= 0.0F; 
    if (getGhostTime() > 0 && !isABoss()) {
      if (getGhostTime() <= 1 || this.hurtResistantTime == 10 || !isEntityAlive()) {
        for (int k = 0; k < 4; k++) {
          this.renderLocations[0][k] = this.renderLocations[1][k];
          this.renderLocations[1][k] = new Vec3d(0.0D, 0.0D, 0.0D);
        } 
      } else if (this.hurtResistantTime == 1 || this.moralRaisedTimer == 1) {
        for (int j = 0; j < 4; j++) {
          float f1 = 1.0F + (Math.max(this.width, 1.0F)) * 0.5F;
          float f11 = 1.0F + (Math.max(this.width, 1.0F)) * 1.0F;
          this.renderLocations[0][j] = this.renderLocations[1][j];
          this.renderLocations[1][j] = new Vec3d((this.rand.nextFloat() * f11 - f1), Math.max(0, this.rand.nextInt(2)), (this.rand.nextFloat() * f11 - f1));
        } 
        spawnExplosionParticle();
        this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, getSoundCategory(), 1.0F, 1.0F, false);
      } 
      if (getGhostTime() == 1) {
        for (int k = 0; k < 4; k++) {
          this.renderLocations[0][k] = this.renderLocations[1][k];
          this.renderLocations[1][k] = new Vec3d(0.0D, 0.0D, 0.0D);
        } 
        spawnExplosionParticle();
        this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, getSoundCategory(), 1.0F, 1.0F, false);
      } 
    } 
    this.isAirBorne = !this.onGround;
    if (isInWater() && !canBreatheUnderwater() && this.ticksExisted % 20 == 0)
      setEnergy(getEnergy() - (float)(1.0099999904632568D - getEntityAttribute(STAMINA).getBaseValue() / 100.0D)); 
    if (this.motionY > 0.0D)
      this.fallDistance = 0.0F; 
    if (getJukeboxToDanceTo() != null && this.ticksExisted % 10 == 0 && isEntityAlive())
      this.world.spawnParticle(EnumParticleTypes.NOTE, this.posX, this.posY + this.height, this.posZ, this.rand.nextDouble(), this.rand.nextDouble(), this.rand.nextDouble());
    if (this.convertionInt > 0 && this.ticksExisted % 10 == 0)
      this.world.setEntityState(this, (byte)21);
    if (this.convertionInt > 0)
      getNavigator().clearPath(); 
    if (getAttackTarget() != null && getAttackTarget() instanceof EntityPlayer && ((EntityPlayer)getAttackTarget()).capabilities.disableDamage)
      setAttackTarget(null);
    if (Loader.isModLoaded("iceandfire")) {
      List<?> list = net.minecraft.AgeOfMinecraft.util.EntityCompat.loadedEntityList(this.world);
      if (!list.isEmpty())
          for (Object o : list) {
              Entity entity = (Entity) o;
              if (entity.isEntityAlive() && entity instanceof EntityMob) {
                  EntityMob mob = (EntityMob) entity;
                  if (mob.width == 0.8F && mob.height == 1.99F && mob.getAttackTarget() != null)
                      mob.setAttackTarget(mob);
              }
          }
    } 
    if (isBeingRidden() && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian)) {
      if (isInWater() || isInLava())
        this.motionY += 0.05D; 
      this.isJumping = false;
    } 
    IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
    IAttributeInstance iattributeinstanceattack = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    if (this.moralRaisedTimer >= 600) {
      this.moralRaisedTimer = 600;
      byte b0 = 20;
      addPotionEffect(new PotionEffect(MobEffects.HASTE, 20 * b0));
      playSound(ESound.battlecry, 10.0F, 1.0F);
      if (this.onGround)
        jump(); 
      playSound(getAmbientSound(), getSoundVolume(), getSoundPitch() + this.rand.nextFloat() * 0.15F);
      playSound(getAmbientSound(), getSoundVolume(), getSoundPitch() + this.rand.nextFloat() * 0.25F);
      playSound(getAmbientSound(), getSoundVolume(), getSoundPitch() + this.rand.nextFloat() * 0.35F);
      playSound(getHurtSound(null), getSoundVolume(), getSoundPitch() + this.rand.nextFloat() * 0.35F);
    } 
    if (this.moralRaisedTimer <= 0) {
      if (iattributeinstance.hasModifier(attackingSpeedBoostModifier))
        iattributeinstance.removeModifier(attackingSpeedBoostModifier); 
      if (iattributeinstanceattack.hasModifier(attackingBoostModifier))
        iattributeinstanceattack.removeModifier(attackingBoostModifier); 
    } else {
      if (!iattributeinstance.hasModifier(attackingSpeedBoostModifier))
        iattributeinstance.applyModifier(attackingSpeedBoostModifier); 
      if (!iattributeinstanceattack.hasModifier(attackingBoostModifier))
        iattributeinstanceattack.applyModifier(attackingBoostModifier); 
    } 
    if (getAttackTarget() != null)
      if (getNavigator() instanceof PathNavigateGround && getDistance(getAttackTarget()) > getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue()) {
        getMoveHelper().strafe(getFittness(), 0.0F);
        faceEntity(getAttackTarget(), getHorizontalFaceSpeed(), getVerticalFaceSpeed());
      }  
    if (!isSprinting() && getOwner() != null && getEnergy() > 20.0F && isEntityAlive() && (getOwner().isSprinting() || (getAttackTarget() != null && this.moveForward > 0.0F && (getAttackTarget().getHealth() <= 4.0F || getDistance(getAttackTarget()) > getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() * 1.5D)))) {
      setSprinting(true);
    } else {
      setSprinting(false);
    } 
    if (isPotionActive(MobEffects.BLINDNESS) || (isPotionActive(MobEffects.NAUSEA) && getAttackTarget() != null && getDistance(getAttackTarget()) > this.reachWidth))
      setAttackTarget(null);
    if (isPotionActive(MobEffects.NAUSEA)) {
      this.rotationYawHead += MathHelper.sin(this.ticksExisted * 0.2F) * 10.0F;
      this.rotationPitch += MathHelper.cos(this.ticksExisted * 0.1F) * 10.0F;
      if (this.ticksExisted % 10 == 0)
        getNavigator().clearPath(); 
    } 
    if (this.inLove > 0) {
      this.inLove--;
      if (this.inLove % 10 == 0)
        for (int ai = 0; ai < 7; ai++)
          spawnHeartParticle();  
    } 
    if (getHeldItemMainhand().getItem() instanceof ItemFood && (getEnergy() < 90.0F || getHealth() <= getMaxHealth() / 2.0F)) {
      if (this.ticksExisted > 53)
        this.ticksExisted = 20; 
      swingArm(EnumHand.MAIN_HAND);
      setActiveHand(EnumHand.MAIN_HAND);
      if (this.ticksExisted % 2 == 0) {
        this.rotationPitch = 40.0F;
      } else {
        this.rotationPitch = 0.0F;
      } 
      if (this.ticksExisted == 50) {
        for (int ai = 0; ai < ((ItemFood)getHeldItemMainhand().getItem()).getHealAmount(getHeldItemMainhand()); ai++)
          spawnHeartParticle(); 
        heal(((ItemFood)getHeldItemMainhand().getItem()).getHealAmount(getHeldItemMainhand()));
        setEnergy(getEnergy() + (((ItemFood)getHeldItemMainhand().getItem()).getHealAmount(getHeldItemMainhand()) * 5));
        playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
      } 
    } 
    if (getHeldItemOffhand().getItem() instanceof ItemFood && (getEnergy() < 90.0F || getHealth() <= getMaxHealth() / 2.0F)) {
      if (this.ticksExisted > 53)
        this.ticksExisted = 20; 
      if (this.ticksExisted % 2 == 0) {
        this.rotationPitch = 40.0F;
      } else {
        this.rotationPitch = 0.0F;
      } 
      swingArm(EnumHand.OFF_HAND);
      setActiveHand(EnumHand.OFF_HAND);
      if (this.ticksExisted == 50) {
        spawnHeartParticle();
        heal(((ItemFood)getHeldItemOffhand().getItem()).getHealAmount(getHeldItemOffhand()));
        setEnergy(getEnergy() + (((ItemFood)getHeldItemOffhand().getItem()).getHealAmount(getHeldItemOffhand()) * 5));
        playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
      } 
    } 
    if (getRidingEntity() != null && getRidingEntity() instanceof EntityBoat && getRidingEntity().getControllingPassenger() == this) {
      getRidingEntity().rotationYaw = this.rotationYaw;
      float f1 = 0.0F;
      if (this.moveForward > 0.0F || getAttackTarget() != null)
        f1 += 0.04F; 
      if (this.moveForward < 0.0F)
        f1 -= 0.005F; 
      getRidingEntity().motionX += (MathHelper.sin(-getRidingEntity().rotationYaw * 0.017453292F) * f1);
      getRidingEntity().motionZ += (MathHelper.cos(getRidingEntity().rotationYaw * 0.017453292F) * f1);
    } 
    if (getJukeboxToDanceTo() != null) {
      if (this.ticksExisted % 20 == 0 && this instanceof EntityCreeper && ((EntityCreeper)this).getPowered())
        this.world.addWeatherEffect(new EntityLightningBolt(this.world, this.posX - 0.5D, this.posY + 1.9D, this.posZ - 0.5D, true));
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
      setSitResting(false);
      getNavigator().clearPath();
      IBlockState iblockstate = this.world.getBlockState(getJukeboxToDanceTo());
      Block block = iblockstate.getBlock();
      if (block != Blocks.JUKEBOX || (block == Blocks.JUKEBOX && !(Boolean) iblockstate.getValue((IProperty) BlockJukebox.HAS_RECORD)) || getDistanceSqToCenter(this.jukeBoxToDanceTo) > 10000.0D)
        setJukeboxToDanceTo(null);
    } 
    if (getAttackTarget() == null && getJukeboxToDanceTo() == null && (this.ticksExisted % 40 == 0 || this.ticksExisted < 5)) {
      int i11 = MathHelper.floor(this.posY);
      int l1 = MathHelper.floor(this.posX);
      int i2 = MathHelper.floor(this.posZ);
      for (int k2 = -8 - (int)this.width; k2 <= 8 + (int)this.width; k2++) {
        for (int l2 = -8 - (int)this.width; l2 <= 8 + (int)this.width; l2++) {
          for (int j = -8 - (int)this.height; j <= 8 + (int)this.height; j++) {
            int i3 = l1 + k2;
            int k = i11 + j;
            int l = i2 + l2;
            BlockPos blockpos = new BlockPos(i3, k, l);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (block == Blocks.JUKEBOX && (Boolean) iblockstate.getValue((IProperty) BlockJukebox.HAS_RECORD))
              setJukeboxToDanceTo(blockpos); 
          } 
        } 
      } 
    } 
    if (isChild() && isRiding() && getRidingEntity() instanceof EntityPlayer)
      if (getRidingEntity().getRidingEntity() != null && getRidingEntity().getRidingEntity() instanceof EntityLivingBase) {
        this.renderYawOffset = this.rotationYaw = getRidingEntity().getRidingEntity().rotationYaw;
        this.rotationYawHead = ((EntityLivingBase) getRidingEntity().getRidingEntity()).rotationYawHead;
      } else {
        this.renderYawOffset = this.rotationYaw = getRidingEntity().rotationYaw;
        this.rotationYawHead = ((EntityPlayer)getRidingEntity()).rotationYawHead;
      }  
    if (this.ticksExisted > getSpawnTimer()) {
      super.onLivingUpdate();
      if (this.stepHeight != ((this.height >= 2.0F) ? (this.height / 2.0F) : 1.0F))
        this.stepHeight = (this.height >= 2.0F) ? (this.height / 2.0F) : 1.0F; 
      if (!isWild() && this.convertionInt > 0)
        this.convertionInt = 0; 
      if (!canBeMatedWith() && isInLove())
        resetInLove(); 
      if (getGrowingAge() < 4000)
        resetInLove(); 
      EntityPlayer player = this.world.getClosestPlayerToEntity(this, 4.0D + this.height + this.width);
      if (EngenderConfig.mobs.useMobTalkerModels && isChild() && !isRiding() && hasOwner(player))
        getLookHelper().setLookPositionWithEntity(player, getHorizontalFaceSpeed(), getVerticalFaceSpeed());
      updateArmSwingProgress();
      if ((getLeashed() && !this.onGround) || this.ticksExisted < 20)
        this.fallDistance *= 0.0F; 
      if (getSpecialAttackTimer() > 0)
        setSpecialAttackTimer(getSpecialAttackTimer() - 1); 
      if (isHero() && net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        if (getParts() != null) {
          for (Entity part : getParts()) {
            if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
              if (isEntityAlive() && this.rand.nextInt(2) == 0)
                this.world.spawnParticle((getOwnerId() == null) ? EnumParticleTypes.REDSTONE : EnumParticleTypes.FIREWORKS_SPARK, part.posX + (this.rand.nextFloat() * part.width * 2.0F) - part.width, part.posY + (this.rand.nextFloat() * part.height), part.posZ + (this.rand.nextFloat() * part.width * 2.0F) - part.width, 0.0D, 0.0D, 0.0D);
          } 
        } else if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
          if (isEntityAlive() && this.rand.nextInt(2) == 0)
            this.world.spawnParticle((getOwnerId() == null) ? EnumParticleTypes.REDSTONE : EnumParticleTypes.FIREWORKS_SPARK, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D);
        }  
    } else {
      if (this.ticksExisted == 2) {
        spawnExplosionParticle();
        this.hurtResistantTime = getSpawnTimer() * 2;
      } 
      this.motionX = 0.0D;
      this.motionZ = 0.0D;
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    } 
    if (isEntityAlive() && this.onGround && getAttackTarget() != null && (getAttackTarget()).posY >= this.posY + 2.0D && this.rand.nextInt(100) == 0) {
      this.motionY = getJumpUpwardsMotion() + 0.20000000298023224D;
      this.isAirBorne = true;
      ForgeHooks.onLivingJump(this);
      double d0 = (getAttackTarget()).posX - this.posX;
      double d1 = (getAttackTarget()).posZ - this.posZ;
      float f1 = MathHelper.sqrt(d0 * d0 + d1 * d1);
      this.motionX += d0 / f1 * 0.5D * 0.5D + this.motionX;
      this.motionZ += d1 / f1 * 0.5D * 0.5D + this.motionZ;
    } 
  }
  
  public void setSprinting(boolean sprinting) {
    if (getEnergy() <= 30.0F)
      sprinting = false; 
    super.setSprinting(sprinting);
  }
  
  public void updateBossBar() {
    this.bossInfo.setName(getDisplayName());
    if (isChild())
      this.bossInfo.setColor(BossInfo.Color.RED); 
    this.bossInfo.setPercent((getFakeHealth() > 0.0F) ? (getFakeHealth() / getMaxHealth() * 2.0F) : (getHealth() / getMaxHealth()));
    this.bossInfo.setVisible((!isSneaking() && !isInvisible() && isEntityAlive()));
    this.bossInfo.setOverlay((getTier() == EnumTier.TIER6) ? BossInfo.Overlay.NOTCHED_20 : ((getTier() == EnumTier.TIER5) ? ((getMaxHealth() >= 250.0F) ? BossInfo.Overlay.NOTCHED_12 : BossInfo.Overlay.NOTCHED_10) : ((getMaxHealth() >= 50.0F) ? BossInfo.Overlay.NOTCHED_6 : BossInfo.Overlay.PROGRESS)));
  }
  
  public boolean isABoss() {
    return false;
  }
  
  public boolean isNonBoss() {
    return true;
  }
  
  public void addTrackingPlayer(EntityPlayerMP player) {
    super.addTrackingPlayer(player);
    if (isABoss() || (isChild() && player.getName().equals("Mrbt0907")))
      this.bossInfo.addPlayer(player); 
  }
  
  public void removeTrackingPlayer(EntityPlayerMP player) {
    super.removeTrackingPlayer(player);
    this.bossInfo.removePlayer(player);
  }
  
  public int getSpawnTimer() {
    return 20;
  }
  
  public float getHealthPercent() {
    return getHealth() / getMaxHealth();
  }
  
  public float getEnergyPercent() {
    return getEnergy() / 100.0F;
  }
  
  public float getEXPPercent() {
    return getEXP() / (getNextLevelRequirement() * getLevel());
  }
  
  public boolean isEntityImmuneToCoralium() {
    return (isABoss() || isHero() || isCameo());
  }
  
  public boolean isEntityImmuneToDread() {
    return (isABoss() || isHero() || isCameo());
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return (isABoss() || isHero() || isCameo() || isAntiMob());
  }
  
  public boolean isEntityImmuneToDarkness() {
    return (isABoss() || isHero() || isCameo());
  }
  
  public boolean passesCoraliumPlague() {
    return false;
  }
  
  public boolean passesDreadPlague() {
    return false;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.fleshHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.fleshHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrush;
  }
  
  public boolean isWoodLike() {
    return (getCrushHurtSound() == ESound.woodHitCrush);
  }
  
  public boolean isMetalLike() {
    return (getCrushHurtSound() == ESound.metalHitCrush);
  }
  
  public boolean canBeTurnedToStone() {
    return (getTier() != EnumTier.TIER6 && !isABoss() && !isHero() && isCameo() && !(this instanceof Structure));
  }
  
  public EntityTameBase getMutant() {
    return null;
  }
  
  public boolean canBreatheUnderwater() {
    return (isABoss() || isNotALivingThing());
  }
  
  public void InactMutation() {
    if (getMutant() != null && getFittness() >= 1.5F) {
      EntityTameBase mutant = getMutant();
      mutant.copyLocationAndAnglesFrom(this);
      mutant.onInitialSpawn(this.world.getDifficultyForLocation(getPosition()), null);
      mutant.setOwnerId(getOwnerId());
      mutant.setIsHero(isHero());
      mutant.setGrowingAge(getGrowingAge());
      this.world.spawnEntity(mutant);
      dropEquipmentUndamaged();
      setDead();
    } else {
      EntityTameBase clone;
      switch (this.rand.nextInt(10)) {
        case 0:
          setFittness(getFittness() + 0.25F);
          return;
        case 1:
          levelUp();
          return;
        case 2:
          setEnergy(100.0F);
          heal(20.0F);
          return;
        case 3:
          clone = spawnBaby(this);
          clone.copyLocationAndAnglesFrom(this);
          clone.readEntityFromNBT(serializeNBT());
          clone.setOwnerId(getOwnerId());
          this.world.spawnEntity(clone);
          return;
        case 4:
          addPotionEffect(new PotionEffect(MobEffects.WITHER, 400, 1));
          return;
        case 5:
          setFittness(getFittness() - 0.25F);
          return;
        case 6:
          if (!isAntiMob()) {
            onDeath((new DamageSource("antimatter")).setDamageBypassesArmor().setDamageIsAbsolute());
          } else {
            setFittness(getFittness() + 0.25F);
          } 
          return;
      } 
      this.motionY++;
    } 
  }
  
  public void setSwingingArms(boolean swingingArms) {}
  
  public boolean canFollowOwner() {
    return (!isElytraFlying() && !isWild() && !isAIDisabled() && !isMovementBlocked() && getAttackState() <= 0 && getAttackTarget() == null && getCurrentBook().isEmpty());
  }
  
  public class PathNavigateFlying extends PathNavigateGround {
    public PathNavigateFlying(EntityLiving p_i47412_1_, World p_i47412_2_) {
      super(p_i47412_1_, p_i47412_2_);
    }
    
    protected PathFinder getPathFinder() {
      this.nodeProcessor = new FlyingNodeProcessor();
      this.nodeProcessor.setCanEnterDoors(true);
      return new PathFinder(this.nodeProcessor);
    }
    
    protected boolean canNavigate() {
      return ((canFloat() && isInLiquid()) || !this.entity.isRiding());
    }
    
    protected Vec3d getEntityPosition() {
      return new Vec3d(this.entity.posX, this.entity.posY, this.entity.posZ);
    }
    
    public Path getPathToEntityLiving(Entity entityIn) {
      return getPathToPos(new BlockPos(entityIn));
    }
    
    public void onUpdateNavigation() {
      this.totalTicks++;
      if (this.tryUpdatePath)
        updatePath(); 
      if (!noPath()) {
        if (canNavigate()) {
          pathFollow();
        } else if (this.currentPath != null && this.currentPath.getCurrentPathIndex() < this.currentPath.getCurrentPathLength()) {
          Vec3d vec3d = this.currentPath.getVectorFromIndex(this.entity, this.currentPath.getCurrentPathIndex());
          if (MathHelper.floor(this.entity.posX) == MathHelper.floor(vec3d.x) && MathHelper.floor(this.entity.posY) == MathHelper.floor(vec3d.y) && MathHelper.floor(this.entity.posZ) == MathHelper.floor(vec3d.z))
            this.currentPath.setCurrentPathIndex(this.currentPath.getCurrentPathIndex() + 1); 
        } 
        debugPathFinding();
        if (!noPath()) {
          Vec3d vec3d1 = this.currentPath.getPosition(this.entity);
          this.entity.getMoveHelper().setMoveTo(vec3d1.x, Flying.clampFlightY(vec3d1.y), vec3d1.z, this.speed);
        } 
      } 
    }
    
    protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
      int i = MathHelper.floor(posVec31.x);
      int j = MathHelper.floor(posVec31.y);
      int k = MathHelper.floor(posVec31.z);
      double d0 = posVec32.x - posVec31.x;
      double d1 = posVec32.y - posVec31.y;
      double d2 = posVec32.z - posVec31.z;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      if (d3 < 1.0E-8D)
        return false; 
      double d4 = 1.0D / Math.sqrt(d3);
      d0 *= d4;
      d1 *= d4;
      d2 *= d4;
      double d5 = 1.0D / Math.abs(d0);
      double d6 = 1.0D / Math.abs(d1);
      double d7 = 1.0D / Math.abs(d2);
      double d8 = i - posVec31.x;
      double d9 = j - posVec31.y;
      double d10 = k - posVec31.z;
      if (d0 >= 0.0D)
        d8++; 
      if (d1 >= 0.0D)
        d9++; 
      if (d2 >= 0.0D)
        d10++; 
      d8 /= d0;
      d9 /= d1;
      d10 /= d2;
      int l = (d0 < 0.0D) ? -1 : 1;
      int i1 = (d1 < 0.0D) ? -1 : 1;
      int j1 = (d2 < 0.0D) ? -1 : 1;
      int k1 = MathHelper.floor(posVec32.x);
      int l1 = MathHelper.floor(posVec32.y);
      int i2 = MathHelper.floor(posVec32.z);
      int j2 = k1 - i;
      int k2 = l1 - j;
      int l2 = i2 - k;
      while (j2 * l > 0 || k2 * i1 > 0 || l2 * j1 > 0) {
        if (d8 < d10 && d8 <= d9) {
          d8 += d5;
          i += l;
          j2 = k1 - i;
          continue;
        } 
        if (d9 < d8 && d9 <= d10) {
          d9 += d6;
          j += i1;
          k2 = l1 - j;
          continue;
        } 
        d10 += d7;
        k += j1;
        l2 = i2 - k;
      } 
      return true;
    }
    
    public void setCanOpenDoors(boolean p_192879_1_) {
      this.nodeProcessor.setCanOpenDoors(p_192879_1_);
    }
    
    public void setCanEnterDoors(boolean p_192878_1_) {
      this.nodeProcessor.setCanEnterDoors(p_192878_1_);
    }
    
    public void setCanFloat(boolean p_192877_1_) {
      this.nodeProcessor.setCanSwim(p_192877_1_);
    }
    
    public boolean canFloat() {
      return this.nodeProcessor.getCanSwim();
    }
    
    public boolean canEntityStandOnPos(BlockPos pos) {
      return this.world.getBlockState(pos).isTopSolid();
    }
  }
}
