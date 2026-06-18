package net.minecraft.AgeOfMinecraft.entity.tame;

import alexiy.secure.contain.protect.entity.EntityShyGuy;
import chumbanotz.mutantbeasts.entity.SkullSpiritEntity;
import chumbanotz.mutantbeasts.entity.mutant.MutantEndermanEntity;
import com.brandon3055.draconicevolution.entity.EntityGuardianCrystal;
import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.common.entity.EntityAbygolem;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.lib.ACLib;
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
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEvoker;
import net.minecraft.AgeOfMinecraft.events.EngenderGeneralEvent;
import net.minecraft.AgeOfMinecraft.items.ItemLearningBook;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
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
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
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
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.pathfinding.FlyingNodeProcessor;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.CombatRules;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
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
import twilightforest.entity.boss.EntityTFLich;

@Interface(iface = "com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues", modid = "iceandfire")
public abstract class EntityTameBase extends EntityBase implements IEntityOwnable, ITeamedMobs, IBlacklistedFromStatues {
  private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
  
  private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5D, 1);
  
  private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> IS_MARRIED = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> HERO = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> REBIRTH = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> ANTIMOB = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> SITRESTING = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187198_h);
  
  protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187203_m);
  
  private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D838");
  
  private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.5D, 1)).func_111168_a(false);
  
  private static final UUID attackingBoostModifierUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D839");
  
  private static final AttributeModifier attackingBoostModifier = (new AttributeModifier(attackingBoostModifierUUID, "Attacking boost", 0.5D, 1)).func_111168_a(false);
  
  private static final DataParameter<Integer> HEROSPECIALATTACKTIMER = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  public static final IAttribute STRENGTH = (IAttribute)(new RangedAttribute((IAttribute)null, "engender.strength", 9.0D, 1.0D, 100.0D)).func_111117_a("Mob Strength").func_111112_a(true);
  
  public static final IAttribute STAMINA = (IAttribute)(new RangedAttribute((IAttribute)null, "engender.stamina", 9.0D, 1.0D, 100.0D)).func_111117_a("Mob Stamina").func_111112_a(true);
  
  public static final IAttribute INTELLIGENCE = (IAttribute)(new RangedAttribute((IAttribute)null, "engender.intelligence", 9.0D, 1.0D, 100.0D)).func_111117_a("Mob Intelligence").func_111112_a(true);
  
  public static final IAttribute DEXTERITY = (IAttribute)(new RangedAttribute((IAttribute)null, "engender.dexterity", 9.0D, 1.0D, 100.0D)).func_111117_a("Mob Dexterity").func_111112_a(true);
  
  public static final IAttribute AGILITY = (IAttribute)(new RangedAttribute((IAttribute)null, "engender.agility", 9.0D, 1.0D, 100.0D)).func_111117_a("Mob Agility").func_111112_a(true);
  
  public static final IAttribute FITTNESS = (IAttribute)(new RangedAttribute((IAttribute)null, "engender.fittness", 1.0D, 0.75D, 1.5D)).func_111117_a("Mob Fittness").func_111112_a(true);
  
  private static final DataParameter<Integer> AGE = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> LEVEL = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Float> EXP = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187193_c);
  
  private static final DataParameter<Float> TOTALEXP = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187193_c);
  
  private static final DataParameter<Float> ENERGY = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187193_c);
  
  private static final DataParameter<Optional<BlockPos>> GUARD_BLOCK_POS = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187201_k);
  
  private static final DataParameter<Integer> BOOK_ID = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> BOOK_DURABILITY = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Float> FAKE_HEALTH = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187193_c);
  
  private static final DataParameter<Integer> GHOST_TIME = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> ILLUSION_FORM_TIME = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> POLYMORPH_TIME = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> ATTACKSTATE = EntityDataManager.func_187226_a(EntityTameBase.class, DataSerializers.field_187192_b);
  
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
  
  protected Block spawnableBlock = (Block)Blocks.field_150349_c;
  
  protected int inLove;
  
  public float reachWidth;
  
  public int attackTimer;
  
  public int holdRoseTick;
  
  public int lastChanceInvul;
  
  public float pageFlip;
  
  public float pageFlipPrev;
  
  public float bookSpread;
  
  public float bookSpreadPrev;
  
  public float flipT;
  
  public float flipA;
  
  public int deathTicks;
  
  protected int blockTimer;
  
  public int exptobeadded;
  
  public float rotationPitchFalling;
  
  public float prevRotationPitchFalling;
  
  public String fakeTeam;
  
  protected BossInfoServer bossInfo = new BossInfoServer((ITextComponent)new TextComponentTranslation(func_70005_c_(), new Object[0]), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
  
  private EnumStudy currentStudy = EnumStudy.Physical;
  
  private ItemStack currentReadingBook = ItemStack.field_190927_a;
  
  public NBTTagCompound polymorpherData;
  
  public static final Item[] books = new Item[] { 
      null, (Item)EItem.learningBookBasic, (Item)EItem.learningBookBasicCombat, (Item)EItem.learningBookBasicCooking, (Item)EItem.learningBookBasicExercise, (Item)EItem.learningBookBasicKnowledge, (Item)EItem.learningBookModern, (Item)EItem.learningBookModernCombat, (Item)EItem.learningBookModernBrute, (Item)EItem.learningBookModernCooking, 
      (Item)EItem.learningBookModernEating, (Item)EItem.learningBookModernExercise, (Item)EItem.learningBookModernRunning, (Item)EItem.learningBookModernKnowledge, (Item)EItem.learningBookModernPacifist, (Item)EItem.learningBookAdvanced, (Item)EItem.learningBookAdvancedCombat, (Item)EItem.learningBookAdvancedBrute, (Item)EItem.learningBookAdvancedWarrior, (Item)EItem.learningBookAdvancedCooking, 
      (Item)EItem.learningBookAdvancedEating, (Item)EItem.learningBookAdvancedDieting, (Item)EItem.learningBookAdvancedExercise, (Item)EItem.learningBookAdvancedRunning, (Item)EItem.learningBookAdvancedLifting, (Item)EItem.learningBookAdvancedKnowledge, (Item)EItem.learningBookAdvancedPacifist, (Item)EItem.learningBookAdvancedWisdom, (Item)EItem.learningBookComplex, (Item)EItem.learningBookComplexCombat, 
      (Item)EItem.learningBookComplexBrute, (Item)EItem.learningBookComplexWarrior, (Item)EItem.learningBookComplexCooking, (Item)EItem.learningBookComplexEating, (Item)EItem.learningBookComplexDieting, (Item)EItem.learningBookComplexExercise, (Item)EItem.learningBookComplexRunning, (Item)EItem.learningBookComplexLifting, (Item)EItem.learningBookComplexKnowledge, (Item)EItem.learningBookComplexPacifist, 
      (Item)EItem.learningBookComplexWisdom, (Item)EItem.learningBookMaster, (Item)EItem.learningBookMasterCombat, (Item)EItem.learningBookMasterBrute, (Item)EItem.learningBookMasterWarrior, (Item)EItem.learningBookMasterCooking, (Item)EItem.learningBookMasterEating, (Item)EItem.learningBookMasterDieting, (Item)EItem.learningBookMasterExercise, (Item)EItem.learningBookMasterRunning, 
      (Item)EItem.learningBookMasterLifting, (Item)EItem.learningBookMasterKnowledge, (Item)EItem.learningBookMasterPacifist, (Item)EItem.learningBookMasterWisdom, (Item)EItem.learningBookArtifact, (Item)EItem.learningBookArtifactStrength, (Item)EItem.learningBookArtifactStamina, (Item)EItem.learningBookArtifactSpeed, (Item)EItem.learningBookArtifactIntellegence };
  
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
    this.breakDoor = new EntityAIBreakDoor((EntityLiving)this);
    this.openDoor = new EntityAIOpenDoor((EntityLiving)this, true);
    this.rainbow = new TextFormatting[] { 
        TextFormatting.RED, TextFormatting.GOLD, TextFormatting.YELLOW, TextFormatting.GREEN, TextFormatting.DARK_GREEN, TextFormatting.AQUA, TextFormatting.BLUE, TextFormatting.DARK_BLUE, TextFormatting.LIGHT_PURPLE, TextFormatting.DARK_PURPLE, 
        TextFormatting.DARK_RED };
    this.field_71088_bW = 100;
    this.basicInventory = new InventoryBasic("Basic inventory", false, 8);
    this.lastChanceInvul = getSpawnTimer();
    updateBossBar();
    this.chasingPosX = this.field_70165_t;
    this.chasingPosY = this.field_70163_u + func_70047_e();
    this.chasingPosZ = this.field_70161_v;
    setDoorAItask((getIntelligence() < 12.0F));
    this.field_70761_aq = this.field_70177_z = this.field_70759_as = this.field_70146_Z.nextFloat() * 360.0F;
    this.field_70122_E = true;
    for (int k = 0; k < this.field_184655_bs.length; k++)
      this.field_184655_bs[k] = 0.0F; 
    for (int j = 0; j < this.field_82174_bp.length; j++)
      this.field_82174_bp[j] = 0.0F; 
    this.field_70728_aV = 10;
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)this, EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            public boolean apply(EntityLivingBase p_apply_1_) {
              return (p_apply_1_ != null && ((EntityTameBase.this.shouldFleeAtLowHealth() && !EntityTameBase.this.func_184191_r((Entity)p_apply_1_) && p_apply_1_.func_70089_S() && EntityTameBase.this.getIntelligence() > 4.0F) || (EntityTameBase.this.func_70631_g_() && EngenderConfig.mobs.useMobTalkerModels && !EntityTameBase.this.func_184191_r((Entity)p_apply_1_)) || (p_apply_1_ instanceof EntityLiving && p_apply_1_.func_110143_aJ() <= 0.0F && p_apply_1_.field_70725_aQ <= 0 && !(p_apply_1_ instanceof EntityTameBase)) || (p_apply_1_ instanceof EntityWither && ((EntityWither)p_apply_1_).func_82212_n() > 0)));
            }
          },  16.0F, 1.25D, 1.25D));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIMobGirlMate(this, 1.2D));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIFollowWildAdult(this, 1.25D));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIBabyMobGirlFollowParent(this, 1.25D));
    this.field_70714_bg.func_75776_a(13, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLivingBase.class, 8.0F) {
          public boolean func_75250_a() {
            if (!EntityTameBase.this.getCurrentBook().func_190926_b() || EntityTameBase.this.func_70638_az() != null || EntityTameBase.this.func_70668_bt() != ESetup.WITHER_STORM)
              return false; 
            return super.func_75250_a();
          }
        });
    this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAIFriendlyHurtByTarget((EntityCreature)this, true, new Class[0]));
    this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAILeaderHurtByTarget(this));
    this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAILeaderHurtTarget(this));
    if (getBookID() != 0)
      setCurrentBook(new ItemStack(books[getBookID()], 1, getBookDurability())); 
    this.renderLocations = new Vec3d[2][4];
    for (int i = 0; i < 4; i++) {
      this.renderLocations[0][i] = new Vec3d(0.0D, 0.0D, 0.0D);
      this.renderLocations[1][i] = new Vec3d(0.0D, 0.0D, 0.0D);
    } 
    for (Entity entity4 : this.field_70170_p.field_73010_i) {
      if (!isWild() && func_184191_r(entity4))
        setOwnerId(entity4.getPersistentID()); 
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
  
  public int playMusic() {
    if (func_70631_g_()) {
      boolean help = false;
      for (EntityPlayer player : this.field_70170_p.field_73010_i) {
        if (player.func_70005_c_().equals("Mrbt0907"))
          help = true; 
      } 
      return help ? 7 : 0;
    } 
    return 0;
  }
  
  public void func_70691_i(float healAmount) {
    if (!this.field_70170_p.field_72995_K) {
      if (healAmount <= 0.0F)
        return; 
      float f = func_110143_aJ();
      if (f > 0.0F)
        func_70606_j(f + healAmount); 
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
    nbttagcompound.func_74778_a("id", func_70022_Q());
    func_189511_e(nbttagcompound);
    if (p_191994_1_.func_192027_g(nbttagcompound)) {
      this.field_70170_p.func_72900_e((Entity)this);
      return true;
    } 
    return false;
  }
  
  public boolean canSitOnShoulder() {
    return (this.rideCooldownCounter > 100);
  }
  
  public enum EnumStudy {
    Physical, Mental, Combative;
  }
  
  public Vec3d[] getRenderLocations(float p_193098_1_) {
    if (getGhostTime() <= 0)
      return this.renderLocations[1]; 
    double d0 = ((getGhostTime() - p_193098_1_) / 3.0F);
    d0 = Math.pow(d0, 0.25D);
    Vec3d[] avec3d = new Vec3d[4];
    for (int i = 0; i < 4; i++)
      avec3d[i] = this.renderLocations[1][i].func_186678_a(1.0D - d0).func_178787_e(this.renderLocations[0][i].func_186678_a(d0)); 
    return avec3d;
  }
  
  public void setCurrentBook(ItemStack stack) {
    this.currentReadingBook = stack;
  }
  
  public ItemStack getCurrentBook() {
    return (getBookID() != 0) ? this.currentReadingBook : ItemStack.field_190927_a;
  }
  
  protected boolean func_70610_aX() {
    return (func_110143_aJ() <= 0.0F || (!func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() instanceof ItemLearningBook));
  }
  
  public int getHoldRoseTick() {
    return this.holdRoseTick;
  }
  
  public int getAttackTimer() {
    return this.attackTimer;
  }
  
  public boolean shouldFleeAtLowHealth() {
    return (func_110143_aJ() <= 4.0F && func_110143_aJ() < func_110138_aP());
  }
  
  public static EngenderExplosion createEngenderModExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
    EngenderExplosion explosion = new EngenderExplosion(entityIn.field_70170_p, entityIn, x, y, z, strength, isFlaming, isSmoking);
    explosion.func_77278_a();
    explosion.func_77279_a(true);
    return explosion;
  }
  
  public static EngenderExplosion createEngenderModExplosionFireless(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
    return createEngenderModExplosion(entityIn, x, y, z, strength, false, isSmoking);
  }
  
  public void setArmsRaised(boolean armsRaised) {
    func_184212_Q().func_187227_b(ARMS_RAISED, Boolean.valueOf(armsRaised));
  }
  
  public boolean isArmsRaised() {
    return ((Boolean)func_184212_Q().func_187225_a(ARMS_RAISED)).booleanValue();
  }
  
  public int timesToConvert() {
    switch (getTier()) {
      case Mental:
        return 5;
      case Combative:
        return 11;
      case Physical:
        return 23;
      case null:
        return 79;
      case null:
        return Integer.MAX_VALUE;
    } 
    return 3;
  }
  
  public final float getStrength() {
    return (float)func_110148_a(STRENGTH).func_111125_b();
  }
  
  public void setStrength(float health) {
    if (!this.field_70170_p.field_72995_K) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      func_110148_a(STRENGTH).func_111128_a(health);
    } 
  }
  
  public final float getStamina() {
    return (float)func_110148_a(STAMINA).func_111125_b();
  }
  
  public void setStamina(float health) {
    if (!this.field_70170_p.field_72995_K) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      func_110148_a(STAMINA).func_111128_a(health);
    } 
  }
  
  public final float getIntelligence() {
    return (float)func_110148_a(INTELLIGENCE).func_111125_b();
  }
  
  public void setIntelligence(float health) {
    if (!this.field_70170_p.field_72995_K) {
      if (health <= 0.0F)
        health = 0.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      func_110148_a(INTELLIGENCE).func_111128_a(health);
    } 
  }
  
  public final float getDexterity() {
    return (float)func_110148_a(DEXTERITY).func_111125_b();
  }
  
  public void setDexterity(float health) {
    if (!this.field_70170_p.field_72995_K) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      func_110148_a(DEXTERITY).func_111128_a(health);
    } 
  }
  
  public final float getAgility() {
    return (float)func_110148_a(AGILITY).func_111125_b();
  }
  
  public void setAgility(float health) {
    if (!this.field_70170_p.field_72995_K) {
      if (health <= 1.0F)
        health = 1.0F; 
      if (health >= 100.0F)
        health = 100.0F; 
      func_110148_a(AGILITY).func_111128_a(health);
    } 
  }
  
  public final float getFittness() {
    return (float)func_110148_a(FITTNESS).func_111125_b();
  }
  
  public void setFittness(float health) {
    if (!this.field_70170_p.field_72995_K) {
      if (health <= 0.75F)
        health = 0.75F; 
      if (health >= 1.5F)
        health = 1.5F; 
      func_110148_a(FITTNESS).func_111128_a(health);
      func_70105_a(this.field_70130_N, this.field_70131_O);
    } 
  }
  
  public float getDefaultFittnessStat() {
    return 0.9F + this.field_70146_Z.nextFloat() * 0.2F;
  }
  
  public final float getEnergy() {
    return ((Float)this.field_70180_af.func_187225_a(ENERGY)).floatValue();
  }
  
  public void setEnergy(float health) {
    this.field_70180_af.func_187227_b(ENERGY, Float.valueOf(health));
  }
  
  public void learnLevelUp(ItemLearningBook book) {
    TextFormatting textFormatting1, STA, INT, DEX, AGL;
    float STRCAL, STACAL, INTCAL, DEXCAL, AGLCAL;
    String str1, STAS, INTS, DEXS, AGLS;
    if (book.isArtifact()) {
      STRCAL = 100.0F;
      STACAL = 100.0F;
      INTCAL = 100.0F;
      DEXCAL = 100.0F;
      AGLCAL = 100.0F;
      setLevel(299);
    } else {
      STRCAL = Math.round(this.field_70146_Z.nextFloat() * book.STRENGTH * 100.0F) / 100.0F;
      STACAL = Math.round(this.field_70146_Z.nextFloat() * book.STAMINA * 100.0F) / 100.0F;
      INTCAL = Math.round(this.field_70146_Z.nextFloat() * book.INTELEGENCE * 100.0F) / 100.0F;
      DEXCAL = Math.round(this.field_70146_Z.nextFloat() * book.DEXTERITY * 100.0F) / 100.0F;
      AGLCAL = Math.round(this.field_70146_Z.nextFloat() * book.AGILITY * 100.0F) / 100.0F;
    } 
    if (STRCAL + getStrength() < getStrength()) {
      textFormatting1 = TextFormatting.RED;
      str1 = "" + STRCAL + " STR ";
    } else if (STRCAL + getStrength() > getStrength()) {
      textFormatting1 = TextFormatting.GREEN;
      str1 = "+" + STRCAL + " STR ";
    } else {
      textFormatting1 = TextFormatting.RESET;
      str1 = "";
    } 
    if (STACAL + getStamina() < getStamina()) {
      STA = TextFormatting.RED;
      STAS = "" + STACAL + " STA ";
    } else if (STACAL + getStamina() > getStamina()) {
      STA = TextFormatting.GREEN;
      STAS = "+" + STACAL + " STA ";
    } else {
      STA = TextFormatting.RESET;
      STAS = "";
    } 
    if (INTCAL + getIntelligence() < getIntelligence()) {
      INT = TextFormatting.RED;
      INTS = "" + INTCAL + " INT ";
    } else if (INTCAL + getIntelligence() > getIntelligence()) {
      INT = TextFormatting.GREEN;
      INTS = "+" + INTCAL + " INT ";
    } else {
      INT = TextFormatting.RESET;
      INTS = "";
    } 
    if (DEXCAL + getDexterity() < getDexterity()) {
      DEX = TextFormatting.RED;
      DEXS = "" + DEXCAL + " DEX ";
    } else if (DEXCAL + getDexterity() > getDexterity()) {
      DEX = TextFormatting.GREEN;
      DEXS = "+" + DEXCAL + " DEX ";
    } else {
      DEX = TextFormatting.RESET;
      DEXS = "";
    } 
    if (AGLCAL + getAgility() < getAgility()) {
      AGL = TextFormatting.RED;
      AGLS = "" + AGLCAL + " AGL ";
    } else if (AGLCAL + getAgility() > getAgility()) {
      AGL = TextFormatting.GREEN;
      AGLS = "+" + AGLCAL + " AGL ";
    } else {
      AGL = TextFormatting.RESET;
      AGLS = "";
    } 
    if (STRCAL + STACAL + INTCAL + DEXCAL + AGLCAL == 0.0F) {
      textFormatting1 = TextFormatting.RED;
      str1 = "Nothing...";
      func_184185_a(SoundEvents.field_187511_aA, 0.5F, 1.0F);
    } else {
      this.exptobeadded = (int)(this.exptobeadded + this.field_70146_Z.nextFloat() * book.EXPERIENCE);
      if (isHero())
        this.exptobeadded = (int)(this.exptobeadded + this.field_70146_Z.nextFloat() * book.EXPERIENCE); 
      func_184185_a(SoundEvents.field_187734_u, 0.5F, 1.0F);
    } 
    getCurrentBook().func_77972_a(1, (EntityLivingBase)this);
    setStrength(getStrength() + STRCAL);
    setStamina(getStamina() + STACAL);
    setIntelligence(getIntelligence() + INTCAL);
    setDexterity(getDexterity() + DEXCAL);
    setAgility(getAgility() + AGLCAL);
    if (book.tier == 6) {
      if (!this.field_70170_p.field_72995_K && !isWild())
        getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(TextFormatting.AQUA + func_70005_c_() + TextFormatting.RESET + " has read \"" + TextFormatting.GOLD + getCurrentBook().func_82833_r() + TextFormatting.RESET + "\" and has learned: " + textFormatting1 + str1 + TextFormatting.RESET + STA + STAS + TextFormatting.RESET + INT + INTS + TextFormatting.RESET + DEX + DEXS + TextFormatting.RESET + AGL + AGLS, new Object[0])); 
    } else if (!this.field_70170_p.field_72995_K && !isWild()) {
      getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(TextFormatting.AQUA + func_70005_c_() + TextFormatting.RESET + " has read \"" + TextFormatting.GOLD + getCurrentBook().func_82833_r().substring(15) + TextFormatting.RESET + "\" and has learned: " + textFormatting1 + str1 + TextFormatting.RESET + STA + STAS + TextFormatting.RESET + INT + INTS + TextFormatting.RESET + DEX + DEXS + TextFormatting.RESET + AGL + AGLS, new Object[0]));
    } 
  }
  
  @Deprecated
  public EnumStudy getCurrentStudy() {
    return this.currentStudy;
  }
  
  @Deprecated
  public void setCurrentStudy(EnumStudy study, int exp) {
    if (!this.field_70170_p.field_72995_K && !hasLimitedLife()) {
      this.currentStudy = study;
      if (isHero())
        exp *= 2; 
      this.exptobeadded += exp;
      if (func_70631_g_())
        this.exptobeadded += exp; 
      if (study == EnumStudy.Mental && this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither) {
        this.exptobeadded += exp;
        this.exptobeadded += exp;
      } 
    } 
  }
  
  public final float getFakeHealth() {
    return ((Float)this.field_70180_af.func_187225_a(FAKE_HEALTH)).floatValue();
  }
  
  public void setFakeHealth(float health) {
    this.field_70180_af.func_187227_b(FAKE_HEALTH, Float.valueOf(MathHelper.func_76131_a(health, 0.0F, func_110138_aP() * 2.0F)));
  }
  
  protected void func_70664_aZ() {
    if (getEnergy() > 5.0F)
      super.func_70664_aZ(); 
    setEnergy(getEnergy() - 0.05F);
  }
  
  public void levelUp() {
    setEXP(0.0F);
    func_70656_aK();
    func_70674_bp();
    func_70690_d(new PotionEffect(MobEffects.field_76428_l, 200));
    switch (getTier()) {
      case Mental:
        func_70691_i(2.0F);
      case Combative:
        func_70691_i(4.0F);
      case Physical:
        func_70691_i(8.0F);
      case null:
        func_70691_i(16.0F);
      case null:
        func_70691_i(32.0F);
        break;
    } 
    func_70691_i(1.0F);
    setEnergy(100.0F);
    if (getLevel() >= 300) {
      setLevel(300);
      func_184185_a(SoundEvents.field_187734_u, 0.5F, 1.0F);
      if (!this.field_70170_p.field_72995_K)
        this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, 10 + this.field_70146_Z.nextInt(40))); 
    } else {
      if (getLevel() > 0 && getLevel() < 300)
        if (getLevel() == 299) {
          if (!this.field_70170_p.field_72995_K && !isWild())
            getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(TextFormatting.AQUA + func_70005_c_() + TextFormatting.RESET + " has reached " + TextFormatting.GOLD + "Max Level" + TextFormatting.RESET + "!", new Object[0])); 
          func_184185_a(SoundEvents.field_194228_if, 1.0F, 1.0F);
          if (!this.field_70170_p.field_72995_K)
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, 10 + this.field_70146_Z.nextInt(40))); 
        } else {
          if (!this.field_70170_p.field_72995_K && !isWild())
            getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(TextFormatting.AQUA + func_70005_c_() + TextFormatting.RESET + " has reached " + TextFormatting.BLUE + "Level " + (getLevel() + 1) + TextFormatting.RESET + "!", new Object[0])); 
          func_184185_a(SoundEvents.field_187802_ec, 0.5F, 1.0F);
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
  
  public void func_174812_G() {
    func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i());
    func_70656_aK();
    func_70106_y();
  }
  
  protected void func_70675_k(float damage) {
    damage /= 4.0F;
    if (damage < 1.0F)
      damage = 1.0F; 
    if (!this.field_70170_p.field_72995_K)
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        ItemStack itemstack = func_184582_a(entityequipmentslot);
        if (!itemstack.func_190926_b() && entityequipmentslot != EntityEquipmentSlot.MAINHAND && entityequipmentslot != EntityEquipmentSlot.OFFHAND)
          if (itemstack.func_77973_b() instanceof net.minecraft.item.ItemArmor) {
            setCurrentStudy(EnumStudy.Combative, (int)damage);
            itemstack.func_77972_a((int)damage, (EntityLivingBase)this);
          }  
      }  
  }
  
  protected void func_184590_k(float damage) {
    if (!this.field_70170_p.field_72995_K)
      if (damage >= 3.0F && this.field_184627_bm.func_77973_b() == Items.field_185159_cQ) {
        setCurrentStudy(EnumStudy.Combative, (int)damage);
        this.blockTimer = 40;
        int i = 1 + MathHelper.func_76141_d(damage);
        this.field_184627_bm.func_77972_a(i, (EntityLivingBase)this);
        func_184185_a(SoundEvents.field_187767_eL, 1.0F, 1.0F);
        if (this.field_184627_bm.func_190926_b()) {
          EnumHand enumhand = func_184600_cs();
          if (enumhand == EnumHand.MAIN_HAND) {
            func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
          } else {
            func_184201_a(EntityEquipmentSlot.OFFHAND, ItemStack.field_190927_a);
          } 
          this.field_184627_bm = ItemStack.field_190927_a;
          func_184185_a(SoundEvents.field_187769_eM, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
        } 
      }  
  }
  
  public boolean func_184585_cz() {
    return (func_70638_az() != null && this.blockTimer <= 0 && func_184592_cb() != null && !func_184587_cr() && func_184592_cb().func_77973_b().func_77661_b(func_184592_cb()) == EnumAction.BLOCK && func_70068_e((Entity)func_70638_az()) < 25.0D);
  }
  
  protected boolean func_146066_aG() {
    return (!this.isOffensive && func_70631_g_()) ? false : (!hasLimitedLife());
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(ARMS_RAISED, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(IS_CHILD, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(IS_MARRIED, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(OWNER_UNIQUE_ID, Optional.absent());
    func_184212_Q().func_187214_a(HERO, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(REBIRTH, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(ANTIMOB, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(SITRESTING, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(HEROSPECIALATTACKTIMER, Integer.valueOf(0));
    func_184212_Q().func_187214_a(AGE, Integer.valueOf(0));
    func_184212_Q().func_187214_a(LEVEL, Integer.valueOf(0));
    func_184212_Q().func_187214_a(EXP, Float.valueOf(0.0F));
    func_184212_Q().func_187214_a(TOTALEXP, Float.valueOf(0.0F));
    func_184212_Q().func_187214_a(ENERGY, Float.valueOf(100.0F));
    func_184212_Q().func_187214_a(FAKE_HEALTH, Float.valueOf(0.0F));
    func_184212_Q().func_187214_a(GUARD_BLOCK_POS, Optional.absent());
    func_184212_Q().func_187214_a(BOOK_ID, Integer.valueOf(0));
    func_184212_Q().func_187214_a(BOOK_DURABILITY, Integer.valueOf(0));
    func_184212_Q().func_187214_a(GHOST_TIME, Integer.valueOf(0));
    func_184212_Q().func_187214_a(ILLUSION_FORM_TIME, Integer.valueOf(0));
    func_184212_Q().func_187214_a(POLYMORPH_TIME, Integer.valueOf(0));
    func_184212_Q().func_187214_a(ATTACKSTATE, Integer.valueOf(0));
  }
  
  public int getAttackState() {
    return ((Integer)this.field_70180_af.func_187225_a(ATTACKSTATE)).intValue();
  }
  
  public void setAttackState(int age) {
    this.field_70180_af.func_187227_b(ATTACKSTATE, Integer.valueOf(age));
  }
  
  public int getPolymorphTime() {
    return ((Integer)this.field_70180_af.func_187225_a(POLYMORPH_TIME)).intValue();
  }
  
  public void setPolymorphTime(int age) {
    this.field_70180_af.func_187227_b(POLYMORPH_TIME, Integer.valueOf(age));
  }
  
  public int getIllusionFormTime() {
    return ((Integer)this.field_70180_af.func_187225_a(ILLUSION_FORM_TIME)).intValue();
  }
  
  public void setIllusionFormTime(int age) {
    this.field_70180_af.func_187227_b(ILLUSION_FORM_TIME, Integer.valueOf(age));
  }
  
  public int getGhostTime() {
    return ((Integer)this.field_70180_af.func_187225_a(GHOST_TIME)).intValue();
  }
  
  public void setGhostTime(int age) {
    this.field_70180_af.func_187227_b(GHOST_TIME, Integer.valueOf(age));
  }
  
  public int getBookID() {
    return ((Integer)this.field_70180_af.func_187225_a(BOOK_ID)).intValue();
  }
  
  public void setBookID(int age) {
    this.field_70180_af.func_187227_b(BOOK_ID, Integer.valueOf(age));
  }
  
  public int getBookDurability() {
    return ((Integer)this.field_70180_af.func_187225_a(BOOK_DURABILITY)).intValue();
  }
  
  public void setBookDurability(int age) {
    this.field_70180_af.func_187227_b(BOOK_DURABILITY, Integer.valueOf(age));
  }
  
  public boolean canBeMarried() {
    return (EngenderConfig.mobs.useMobTalkerModels && !func_70631_g_() && getTier() != EnumTier.TIER6 && func_70089_S() && func_184188_bt().isEmpty() && func_184187_bx() == null);
  }
  
  public boolean isMarried() {
    return ((Boolean)func_184212_Q().func_187225_a(IS_MARRIED)).booleanValue();
  }
  
  public void setMarried(boolean childZombie) {
    func_184212_Q().func_187227_b(IS_MARRIED, Boolean.valueOf(childZombie));
  }
  
  public boolean func_70631_g_() {
    return ((Boolean)func_184212_Q().func_187225_a(IS_CHILD)).booleanValue();
  }
  
  public void setChild(boolean childZombie) {
    func_184212_Q().func_187227_b(IS_CHILD, Boolean.valueOf(childZombie));
    if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
      IAttributeInstance iattributeinstance = func_110148_a(SharedMonsterAttributes.field_111263_d);
      iattributeinstance.func_111124_b(BABY_SPEED_BOOST);
      if (childZombie)
        iattributeinstance.func_111121_a(BABY_SPEED_BOOST); 
    } 
  }
  
  protected int func_70693_a(EntityPlayer player) {
    int xp = super.func_70693_a(player);
    if (func_70631_g_())
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
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    func_184641_n((this.field_70146_Z.nextFloat() < 0.1F));
    setGrowingAge((this.field_70170_p.field_73012_v.nextFloat() <= 0.05F) ? -20000 : 4000);
    setEnergy(100.0F);
    setLevel(1);
    setStrength(getDefaultStrengthStat());
    setStamina(getDefaultStaminaStat());
    setIntelligence(getDefaultIntelligenceStat());
    setDexterity(getDefaultDexterityStat());
    setAgility(getDefaultAgilityStat());
    setFittness(getDefaultFittnessStat());
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111121_a(new AttributeModifier("Random spawn bonus", this.field_70146_Z.nextGaussian() * 0.05D, 1));
    return livingdata;
  }
  
  public void createChild() {
    func_184185_a(ESound.girlMoan, 1.0F, func_70647_i());
    for (int i = 0; i < 10; i++)
      spawnHeartParticle(); 
  }
  
  public void func_184185_a(SoundEvent soundIn, float volume, float pitch) {
    if (soundIn != null)
      super.func_184185_a(soundIn, volume, pitch); 
  }
  
  public boolean isNotALivingThing() {
    return (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime || func_70662_br() || func_70668_bt() == ESetup.CONSTRUCT || this instanceof Structure || this instanceof Elemental);
  }
  
  public float getDefaultStrengthStat() {
    return 4.0F + this.field_70146_Z.nextFloat() * 8.0F;
  }
  
  public float getDefaultStaminaStat() {
    return isNotALivingThing() ? 100.0F : (4.0F + this.field_70146_Z.nextFloat() * 8.0F);
  }
  
  public float getDefaultIntelligenceStat() {
    return 4.0F + this.field_70146_Z.nextFloat() * 8.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 4.0F + this.field_70146_Z.nextFloat() * 8.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 4.0F + this.field_70146_Z.nextFloat() * 8.0F;
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
    return (EngenderConfig.mobs.useMobTalkerModels && this.field_70131_O >= 1.365F && !func_70631_g_());
  }
  
  public boolean func_82171_bF() {
    return func_184179_bs() instanceof EntityLivingBase;
  }
  
  public void incrementConversion(EntityPlayer player) {
    if (isWild())
      if (this.convertionInt >= timesToConvert()) {
        func_70624_b((EntityLivingBase)null);
        this.field_70170_p.func_180498_a((EntityPlayer)null, 3000, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
        setOwnerId(player.func_110124_au());
        this.field_70173_aa = 0;
        func_184185_a(ESound.converted, 3.0F, 1.0F);
        if (player instanceof EntityPlayerMP)
          ESetup.CONVERT_MOB.trigger((EntityPlayerMP)player, this); 
        if (player != null && !isWild() && !this.field_70170_p.field_72995_K)
          player.func_145747_a((ITextComponent)new TextComponentTranslation(func_70005_c_() + " has been converted by " + player.func_70005_c_() + " (" + (int)this.field_70165_t + ", " + (int)this.field_70163_u + ", " + (int)this.field_70161_v + ", )", new Object[0])); 
      } else {
        this.convertionInt++;
        func_184185_a(ESound.converting, 3.0F, 1.0F);
        func_70624_b((EntityLivingBase)null);
        this.convertionDelay = 200;
      }  
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER1;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_70112_a(double distance) {
    double d0 = func_174813_aQ().func_72320_b();
    if (Double.isNaN(d0))
      d0 = 1.0D; 
    d0 *= func_70093_af() ? 32.0D : 512.0D;
    return this.field_70158_ak ? true : ((distance < d0 * d0));
  }
  
  public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
    return !func_184191_r((Entity)p_142018_1_);
  }
  
  protected boolean func_70692_ba() {
    return (isWild() && !isABoss());
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0D);
    func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
    func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188790_f);
    func_110140_aT().func_111150_b(FITTNESS);
    func_110140_aT().func_111150_b(STRENGTH);
    func_110140_aT().func_111150_b(STAMINA);
    func_110140_aT().func_111150_b(INTELLIGENCE);
    func_110140_aT().func_111150_b(DEXTERITY);
    func_110140_aT().func_111150_b(AGILITY);
    func_110140_aT().func_111150_b(SharedMonsterAttributes.field_193334_e);
    func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188792_h);
  }
  
  public boolean func_70601_bi() {
    // Byte code:
    //   0: aload_0
    //   1: getfield field_70170_p : Lnet/minecraft/world/World;
    //   4: invokevirtual func_72912_H : ()Lnet/minecraft/world/storage/WorldInfo;
    //   7: invokevirtual func_76067_t : ()Lnet/minecraft/world/WorldType;
    //   10: getstatic net/minecraft/world/WorldType.field_77138_c : Lnet/minecraft/world/WorldType;
    //   13: if_acmpne -> 35
    //   16: aload_0
    //   17: getfield field_70146_Z : Ljava/util/Random;
    //   20: aload_0
    //   21: invokevirtual getSpawnChanceReduction : ()I
    //   24: iconst_5
    //   25: imul
    //   26: invokevirtual nextInt : (I)I
    //   29: ifne -> 60
    //   32: goto -> 49
    //   35: aload_0
    //   36: getfield field_70146_Z : Ljava/util/Random;
    //   39: aload_0
    //   40: invokevirtual getSpawnChanceReduction : ()I
    //   43: invokevirtual nextInt : (I)I
    //   46: ifne -> 60
    //   49: aload_0
    //   50: invokespecial func_70601_bi : ()Z
    //   53: ifeq -> 60
    //   56: iconst_1
    //   57: goto -> 61
    //   60: iconst_0
    //   61: ireturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #1335	-> 0
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	62	0	this	Lnet/minecraft/AgeOfMinecraft/entity/tame/EntityTameBase;
  }
  
  public float func_180484_a(BlockPos pos) {
    return 0.0F;
  }
  
  public int getSpawnChanceReduction() {
    switch (getTier()) {
      case Mental:
        return 100;
      case Combative:
        return 200;
      case Physical:
        return 400;
      case null:
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
    double d0 = this.field_70165_t;
    double d1 = this.field_70163_u;
    double d2 = this.field_70161_v;
    this.field_70165_t = x;
    this.field_70163_u = y;
    this.field_70161_v = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos((Entity)this);
    World world = this.field_70170_p;
    func_70681_au();
    if (world.func_175667_e(blockpos)) {
      boolean flag1 = false;
      while (!flag1 && blockpos.func_177956_o() > 0) {
        BlockPos blockpos1 = blockpos.func_177977_b();
        IBlockState iblockstate = world.func_180495_p(blockpos1);
        if (iblockstate.func_185904_a().func_76230_c()) {
          flag1 = true;
          continue;
        } 
        this.field_70163_u--;
        blockpos = blockpos1;
      } 
      if (flag1) {
        func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (world.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !world.func_72953_d(func_174813_aQ()))
          flag = true; 
      } 
    } 
    if (!flag) {
      func_70634_a(d0, d1, d2);
      return false;
    } 
    func_70661_as().func_75499_g();
    return true;
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  public boolean func_145773_az() {
    return (getDexterity() > 8.0F);
  }
  
  public int getSpecialAttackTimer() {
    return ((Integer)this.field_70180_af.func_187225_a(HEROSPECIALATTACKTIMER)).intValue();
  }
  
  public void setSpecialAttackTimer(int p_82215_1_) {
    this.field_70180_af.func_187227_b(HEROSPECIALATTACKTIMER, Integer.valueOf(p_82215_1_));
  }
  
  public void performSpecialAttack() {}
  
  public void setDoorAItask(boolean enabled) {
    ((PathNavigateGround)func_70661_as()).func_179688_b(enabled);
    ((PathNavigateGround)func_70661_as()).func_179691_c(true);
    if (enabled) {
      this.field_70714_bg.func_85156_a((EntityAIBase)this.openDoor);
      this.field_70714_bg.func_75776_a(1, (EntityAIBase)this.breakDoor);
    } else {
      this.field_70714_bg.func_85156_a((EntityAIBase)this.breakDoor);
      this.field_70714_bg.func_75776_a(1, (EntityAIBase)this.openDoor);
    } 
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    String s;
    super.func_70037_a(tagCompund);
    if (tagCompund.func_74764_b("PolymorpherData"))
      this.polymorpherData = tagCompund.func_74775_l("PolymorpherData"); 
    if (tagCompund.func_74764_b("LifeTicks"))
      setLimitedLife(tagCompund.func_74762_e("LifeTicks")); 
    if (tagCompund.func_150297_b("FakeHealth", 99))
      setFakeHealth(tagCompund.func_74760_g("FakeHealth")); 
    if (tagCompund.func_150297_b("CurrentBook", 9)) {
      NBTTagList nbttaglist1 = tagCompund.func_150295_c("CurrentBook", 10);
      setCurrentBook(new ItemStack(nbttaglist1.func_150305_b(0)));
    } 
    if (tagCompund.func_150297_b("STR", 99))
      setStrength(tagCompund.func_74760_g("STR")); 
    if (tagCompund.func_150297_b("STA", 99))
      setStamina(tagCompund.func_74760_g("STA")); 
    if (tagCompund.func_150297_b("INT", 99))
      setIntelligence(tagCompund.func_74760_g("INT")); 
    if (tagCompund.func_150297_b("DEX", 99))
      setDexterity(tagCompund.func_74760_g("DEX")); 
    if (tagCompund.func_150297_b("AGI", 99))
      setAgility(tagCompund.func_74760_g("AGI")); 
    if (tagCompund.func_150297_b("FIT", 99))
      setFittness(tagCompund.func_74760_g("FIT")); 
    setMarried(tagCompund.func_74767_n("IsMarried"));
    setChild(tagCompund.func_74767_n("IsBaby"));
    if (tagCompund.func_150297_b("OwnerUUID", 8)) {
      s = tagCompund.func_74779_i("OwnerUUID");
    } else {
      String s1 = tagCompund.func_74779_i("Owner");
      s = PreYggdrasilConverter.func_187473_a(func_184102_h(), s1);
    } 
    if (!s.isEmpty())
      try {
        setOwnerId(UUID.fromString(s));
      } catch (Throwable throwable) {} 
    this.field_70180_af.func_187227_b(REBIRTH, Boolean.valueOf(tagCompund.func_74767_n("LastChance")));
    this.field_70180_af.func_187227_b(HERO, Boolean.valueOf(tagCompund.func_74767_n("Hero")));
    this.field_70180_af.func_187227_b(ANTIMOB, Boolean.valueOf(tagCompund.func_74767_n("Anti")));
    this.field_70180_af.func_187227_b(SITRESTING, Boolean.valueOf(tagCompund.func_74767_n("SitResting")));
    setSpecialAttackTimer(tagCompund.func_74762_e("SAT"));
    this.inLove = tagCompund.func_74762_e("InLove");
    setGrowingAge(tagCompund.func_74762_e("Age"));
    setLevel(tagCompund.func_74762_e("Level"));
    setEXP(tagCompund.func_74760_g("EXP"));
    setTotalEXP(tagCompund.func_74760_g("TotalEXP"));
    setBookID(tagCompund.func_74762_e("BookID"));
    setBookDurability(tagCompund.func_74762_e("BookDurability"));
    if (tagCompund.func_150297_b("Energy", 99))
      setEnergy(tagCompund.func_74760_g("Energy")); 
    setGhostTime(tagCompund.func_74762_e("MirrorTime"));
    setIllusionFormTime(tagCompund.func_74762_e("IllusionTime"));
    setPolymorphTime(tagCompund.func_74762_e("MorphTime"));
    setAttackState(tagCompund.func_74762_e("AttackState"));
    setDoorAItask((getIntelligence() < 12.0F));
    if (tagCompund.func_74764_b("GPX")) {
      int i = tagCompund.func_74762_e("GPX");
      int j = tagCompund.func_74762_e("GPY");
      int k = tagCompund.func_74762_e("GPZ");
      this.field_70180_af.func_187227_b(GUARD_BLOCK_POS, Optional.of(new BlockPos(i, j, k)));
      this.randPosX = i;
      this.randPosY = j;
      this.randPosZ = k;
    } else {
      this.field_70180_af.func_187227_b(GUARD_BLOCK_POS, Optional.absent());
    } 
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    if (this.polymorpherData != null)
      tagCompound.func_74782_a("PolymorpherData", (NBTBase)this.polymorpherData); 
    NBTTagList nbttaglist = new NBTTagList();
    NBTTagCompound nbttagcompound = new NBTTagCompound();
    if (hasLimitedLife())
      tagCompound.func_74768_a("LifeTicks", getLimitedLife()); 
    if (!getCurrentBook().func_190926_b())
      getCurrentBook().func_77955_b(nbttagcompound); 
    nbttaglist.func_74742_a((NBTBase)nbttagcompound);
    tagCompound.func_74782_a("CurrentBook", (NBTBase)nbttaglist);
    tagCompound.func_74776_a("FakeHealth", getFakeHealth());
    tagCompound.func_74776_a("STR", getStrength());
    tagCompound.func_74776_a("STA", getStamina());
    tagCompound.func_74776_a("INT", getIntelligence());
    tagCompound.func_74776_a("DEX", getDexterity());
    tagCompound.func_74776_a("AGI", getAgility());
    tagCompound.func_74776_a("FIT", getFittness());
    if (func_184753_b() == null) {
      tagCompound.func_74778_a("OwnerUUID", "");
    } else {
      tagCompound.func_74778_a("OwnerUUID", func_184753_b().toString());
    } 
    tagCompound.func_74757_a("IsMarried", isMarried());
    tagCompound.func_74757_a("IsBaby", func_70631_g_());
    tagCompound.func_74757_a("Hero", isHero());
    tagCompound.func_74757_a("LastChance", ((Boolean)this.field_70180_af.func_187225_a(REBIRTH)).booleanValue());
    tagCompound.func_74757_a("Anti", ((Boolean)this.field_70180_af.func_187225_a(ANTIMOB)).booleanValue());
    tagCompound.func_74757_a("SitResting", ((Boolean)this.field_70180_af.func_187225_a(SITRESTING)).booleanValue());
    tagCompound.func_74768_a("SAT", getSpecialAttackTimer());
    tagCompound.func_74776_a("EXP", getEXP());
    tagCompound.func_74776_a("TotalEXP", getTotalEXP());
    tagCompound.func_74768_a("Age", getGrowingAge());
    tagCompound.func_74768_a("Level", getLevel());
    tagCompound.func_74768_a("BookID", getBookID());
    tagCompound.func_74768_a("BookDurability", getBookDurability());
    tagCompound.func_74776_a("Energy", getEnergy());
    tagCompound.func_74768_a("InLove", this.inLove);
    tagCompound.func_74768_a("MirrorTime", getGhostTime());
    tagCompound.func_74768_a("IllusionTime", getIllusionFormTime());
    tagCompound.func_74768_a("MorphTime", getPolymorphTime());
    tagCompound.func_74768_a("AttackState", getAttackState());
    if (getGuardBlock() != null) {
      tagCompound.func_74768_a("GPX", getGuardBlock().func_177958_n());
      tagCompound.func_74768_a("GPY", getGuardBlock().func_177956_o());
      tagCompound.func_74768_a("GPZ", getGuardBlock().func_177952_p());
    } 
  }
  
  public Entity func_184179_bs() {
    return func_184188_bt().isEmpty() ? null : func_184188_bt().get(0);
  }
  
  public boolean func_70686_a(Class<? extends EntityLivingBase> cls) {
    return true;
  }
  
  public boolean isCameo() {
    return false;
  }
  
  public boolean canBeButchered() {
    return false;
  }
  
  public boolean isSitResting() {
    return ((Boolean)this.field_70180_af.func_187225_a(SITRESTING)).booleanValue();
  }
  
  public void setSitResting(boolean bool) {
    this.field_70180_af.func_187227_b(SITRESTING, Boolean.valueOf(bool));
  }
  
  public boolean isAntiMob() {
    return ((Boolean)this.field_70180_af.func_187225_a(ANTIMOB)).booleanValue();
  }
  
  public void setIsAntiMob(boolean bool) {
    this.field_70180_af.func_187227_b(ANTIMOB, Boolean.valueOf(bool));
  }
  
  public boolean isHero() {
    return ((Boolean)this.field_70180_af.func_187225_a(HERO)).booleanValue();
  }
  
  public void setIsHero(boolean bool) {
    this.field_70180_af.func_187227_b(HERO, Boolean.valueOf(bool));
  }
  
  public void becomeAHero() {
    setIsHero(true);
    func_184185_a(ESound.hero, 100.0F, 1.0F);
    this.field_70173_aa = -20;
  }
  
  public boolean hasLastChance() {
    return ((Boolean)this.field_70180_af.func_187225_a(REBIRTH)).booleanValue();
  }
  
  public void setLastChance(boolean bool) {
    this.field_70180_af.func_187227_b(REBIRTH, Boolean.valueOf(bool));
  }
  
  public SoundCategory func_184176_by() {
    return SoundCategory.MASTER;
  }
  
  @Nullable
  public BlockPos getGuardBlock() {
    return (BlockPos)((Optional)this.field_70180_af.func_187225_a(GUARD_BLOCK_POS)).orNull();
  }
  
  public void setGuardBlock(@Nullable BlockPos pos) {
    this.field_70180_af.func_187227_b(GUARD_BLOCK_POS, Optional.fromNullable(pos));
  }
  
  public BlockPos getJukeboxToDanceTo() {
    return this.jukeBoxToDanceTo;
  }
  
  public void setJukeboxToDanceTo(BlockPos block) {
    this.jukeBoxToDanceTo = block;
  }
  
  @Nullable
  public UUID func_184753_b() {
    return (UUID)((Optional)this.field_70180_af.func_187225_a(OWNER_UNIQUE_ID)).orNull();
  }
  
  public void setOwnerId(@Nullable UUID p_184754_1_) {
    this.field_70180_af.func_187227_b(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
  }
  
  @Nullable
  public EntityLivingBase getOwner() {
    try {
      UUID uuid = func_184753_b();
      return (uuid == null) ? null : (EntityLivingBase)this.field_70170_p.func_152378_a(uuid);
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
    if (!this.field_70170_p.field_72995_K && !hasLimitedLife())
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        ItemStack itemstack = func_184582_a(entityequipmentslot);
        if (!itemstack.func_190926_b()) {
          if (EnchantmentHelper.func_190938_b(itemstack))
            func_70097_a((new DamageSource("cramming")).func_76348_h().func_151518_m().func_76351_m(), 10.0F); 
          if (EnchantmentHelper.func_190939_c(itemstack)) {
            func_184185_a(SoundEvents.field_191263_gW, 1.0F, 1.0F);
            func_70656_aK();
            func_184201_a(entityequipmentslot, ItemStack.field_190927_a);
          } else {
            func_184606_a_(itemstack);
            func_70099_a(itemstack, func_70047_e());
            func_184201_a(entityequipmentslot, ItemStack.field_190927_a);
          } 
        } 
      }  
  }
  
  public Team func_96124_cp() {
    if (!isWild()) {
      EntityLivingBase entitylivingbase = getOwner();
      if (entitylivingbase != null)
        return entitylivingbase.func_96124_cp(); 
    } 
    return super.func_96124_cp();
  }
  
  public boolean affectedByCommandingStaff() {
    return (!isHero() && !isCameo());
  }
  
  public boolean func_190631_cK() {
    return (this.field_70173_aa > getSpawnTimer() && !func_175446_cd() && func_70089_S());
  }
  
  public boolean isASwarmingMob() {
    return false;
  }
  
  public EnumPushReaction func_184192_z() {
    return func_175446_cd() ? EnumPushReaction.IGNORE : super.func_184192_z();
  }
  
  public boolean func_184191_r(Entity entity) {
    EntityLivingBase otherEntity = (EntityLivingBase)entity;
    if (!isWild()) {
      if (otherEntity == this || !otherEntity.func_190631_cK())
        return true; 
      EntityLivingBase entitylivingbase = getOwner();
      if (otherEntity == entitylivingbase)
        return true; 
      if (otherEntity instanceof net.minecraft.entity.monster.IMob)
        return false; 
      if (otherEntity == entitylivingbase.func_94060_bK())
        return false; 
      if (otherEntity == entitylivingbase.func_110144_aD())
        return false; 
      if (otherEntity instanceof EntityTameBase && ((EntityTameBase)otherEntity).isWild())
        return false; 
      return !isAThreatToOwner(otherEntity);
    } 
    if (isWild()) {
      if (otherEntity == this || !otherEntity.func_190631_cK())
        return true; 
      if (otherEntity instanceof EntityMob && (((EntityMob)otherEntity).func_70638_az() == null || (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither && !(otherEntity instanceof EntityWither) && ((EntityMob)otherEntity).func_70668_bt() != EnumCreatureAttribute.UNDEAD)))
        return true; 
      if (otherEntity instanceof EntityTameBase && ((EntityTameBase)otherEntity).isWild())
        return true; 
      if (otherEntity instanceof net.minecraft.entity.passive.EntityVillager && !func_70662_br() && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator) && !(this instanceof EntityVex) && !(this instanceof EntityEvoker))
        return true; 
      if (otherEntity instanceof net.minecraft.entity.passive.EntitySquid && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian))
        return true; 
      if (this.convertionInt > 0)
        return true; 
      return false;
    } 
    return super.func_184191_r((Entity)otherEntity);
  }
  
  public boolean isAThreatToOwner(EntityLivingBase otherEntity) {
    if (otherEntity == this || !otherEntity.func_190631_cK())
      return false; 
    if (Loader.isModLoaded("scp")) {
      if (otherEntity instanceof EntityShyGuy && (((EntityShyGuy)otherEntity).getState()).isPassive)
        return false; 
      if (otherEntity instanceof alexiy.secure.contain.protect.entity.EntityHeavyGolem)
        return false; 
      if (otherEntity instanceof alexiy.secure.contain.protect.entity.SCPHostileEntity)
        return true; 
    } 
    if (Loader.isModLoaded("twilightforest"))
      if (otherEntity instanceof EntityTFLich && ((EntityTFLich)otherEntity).isShadowClone())
        return false;  
    if (Loader.isModLoaded("mutantbeasts") && otherEntity instanceof chumbanotz.mutantbeasts.entity.mutant.MutantSnowGolemEntity)
      return false; 
    if (Loader.isModLoaded("tektopia") && otherEntity instanceof net.tangotek.tektopia.entities.EntityVillageNavigator && !(otherEntity instanceof net.tangotek.tektopia.entities.EntityNecromancer))
      return false; 
    if (Loader.isModLoaded("iceandfire") && otherEntity instanceof com.github.alexthe666.iceandfire.entity.EntityDragonEgg)
      return false; 
    if (Loader.isModLoaded("abyssalcraft") && otherEntity instanceof EntityRemnant && (EntityRemnant)otherEntity != getOwner().func_110144_aD() && !((EntityRemnant)otherEntity).isAngry())
      return false; 
    if (Loader.isModLoaded("abyssalcraft") && otherEntity instanceof EntityAbygolem && ((EntityAbygolem)otherEntity).func_70638_az() == null && (EntityAbygolem)otherEntity != getOwner().func_110144_aD())
      return false; 
    if (Loader.isModLoaded("mutantbeasts") && otherEntity instanceof MutantEndermanEntity && ((MutantEndermanEntity)otherEntity).func_70638_az() == null)
      return false; 
    if (Loader.isModLoaded("draconicevolution") && otherEntity instanceof EntityGuardianCrystal && ((EntityGuardianCrystal)otherEntity).health <= 0.0F)
      return false; 
    EntityLivingBase entitylivingbase = getOwner();
    if (otherEntity instanceof EntityPlayer) {
      if (otherEntity == entitylivingbase)
        return false; 
      if (((EntityPlayer)otherEntity).field_71075_bZ.field_75102_a)
        return false; 
      if (entitylivingbase.func_184191_r((Entity)otherEntity))
        return false; 
    } 
    if (otherEntity instanceof EntityCreature && (((EntityCreature)otherEntity).func_70638_az() == getOwner() || ((EntityCreature)otherEntity).func_70638_az() == this))
      return false; 
    if (otherEntity instanceof EntityPigZombie && !((EntityPigZombie)otherEntity).func_175457_ck())
      return false; 
    if (otherEntity instanceof EntityTameBase) {
      if (((EntityTameBase)otherEntity).func_175446_cd())
        return false; 
      if (((EntityTameBase)otherEntity).isWild() && (((EntityTameBase)otherEntity).convertionInt <= 0 || (((EntityTameBase)otherEntity).convertionInt > 0 && this instanceof EntityEvoker)))
        return false; 
      if (!((EntityTameBase)otherEntity).isWild() && ((EntityTameBase)otherEntity).getOwner() == entitylivingbase && ((EntityTameBase)otherEntity).getFakeHealth() <= 0.0F)
        return false; 
      if (!((EntityTameBase)otherEntity).isWild() && ((EntityTameBase)otherEntity).getOwner() != entitylivingbase && ((EntityTameBase)otherEntity).getOwner().func_184191_r((Entity)entitylivingbase))
        return false; 
      if (((EntityTameBase)otherEntity).getFakeHealth() > 0.0F)
        return true; 
    } 
    if (otherEntity instanceof EntityDragon && ((EntityDragon)otherEntity).func_184670_cT().func_188756_a() instanceof net.minecraft.entity.boss.dragon.phase.PhaseDying)
      return false; 
    if ((otherEntity instanceof EntityZombieVillager && ((EntityZombieVillager)otherEntity).func_82230_o()) || (otherEntity instanceof net.minecraft.entity.INpc && !(otherEntity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager)) || otherEntity instanceof net.minecraft.entity.monster.EntitySnowman || otherEntity instanceof net.minecraft.entity.passive.EntityAmbientCreature || otherEntity instanceof net.minecraft.entity.passive.EntityWaterMob || otherEntity instanceof net.minecraft.entity.passive.EntityAnimal)
      return false; 
    return true;
  }
  
  protected void func_70623_bb() {
    if (isWild() && func_184222_aU() && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortal))
      if (func_104002_bU()) {
        this.field_70708_bq = 0;
      } else {
        EntityPlayer entityPlayer = this.field_70170_p.func_72890_a((Entity)this, -1.0D);
        if (entityPlayer != null) {
          double d0 = ((Entity)entityPlayer).field_70165_t - this.field_70165_t;
          double d1 = ((Entity)entityPlayer).field_70163_u - this.field_70163_u;
          double d2 = ((Entity)entityPlayer).field_70161_v - this.field_70161_v;
          double d3 = d0 * d0 + d1 * d1 + d2 * d2;
          if (func_70692_ba() && d3 > 50000.0D)
            func_70106_y(); 
          if (this.field_70708_bq > 10000 && this.field_70146_Z.nextInt(1000) == 0 && d3 > 2000.0D && func_70692_ba()) {
            func_70106_y();
          } else if (d3 < 2000.0D) {
            this.field_70708_bq = 0;
          } 
        } 
      }  
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.1F : 1.0F;
  }
  
  public boolean func_180431_b(DamageSource source) {
    return (isWild() && func_184753_b() != null) ? true : super.func_180431_b(source);
  }
  
  protected float func_70672_c(DamageSource source, float damage) {
    damage = super.func_70672_c(source, damage);
    if (source.func_76346_g() == this)
      damage = 0.0F; 
    if (source.func_82725_o() && isHero())
      damage = (float)(damage * 0.25D); 
    return damage;
  }
  
  protected float func_70655_b(DamageSource source, float damage) {
    if (!source.func_76363_c()) {
      func_70675_k(damage);
      damage = CombatRules.func_189427_a(damage, func_70658_aO(), (float)func_110148_a(SharedMonsterAttributes.field_189429_h).func_111126_e());
    } else {
      damage *= 0.5F;
    } 
    return damage;
  }
  
  protected void func_70665_d(DamageSource source, float amount) {
    if (!func_180431_b(source)) {
      amount = ForgeHooks.onLivingHurt((EntityLivingBase)this, source, amount);
      Entity entity = source.func_76346_g();
      if (amount <= 0.0F)
        return; 
      if (isHero() && source.func_76355_l() == "fall" && amount > 4.0F)
        amount = 4.0F; 
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY)
        amount = Math.min(amount / 2.0F + 1.0F, amount); 
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
        amount = amount * 3.0F / 2.0F; 
      if (source.func_76346_g() instanceof net.minecraft.entity.monster.EntityVex)
        amount *= 0.33F; 
      if (isHero() && (source.func_76355_l() == "chaosImplosion" || source.func_76355_l() == "de.GuardianFireball" || source.func_76355_l() == "de.GuardianEnergyBall" || source.func_76355_l() == "de.GuardianChaosBall"))
        amount *= 0.05F; 
      if (isHero() && entity != null)
        amount *= (entity instanceof net.minecraft.entity.monster.IMob) ? 0.33333334F : 0.75F; 
      String s = TextFormatting.func_110646_a(func_70005_c_());
      if (getClass().equals(EntityCow.class) && s != null && s.equals("Bessy"))
        amount *= 0.01F; 
      amount = func_70655_b(source, amount);
      amount = func_70672_c(source, amount);
      float f = amount;
      amount = Math.max(amount - func_110139_bj(), 0.0F);
      func_110149_m(func_110139_bj() - f - amount);
      amount /= getFittness();
      if (getDamageCap() > 0 && amount >= (getDamageCap() / 2))
        amount /= this.field_70131_O + this.field_70130_N; 
      if (getDamageCap() > 0 && amount >= getDamageCap())
        amount = getDamageCap(); 
      if (amount != 0.0F && this.lastChanceInvul <= 0) {
        if (getIllusionFormTime() > 0) {
          func_184185_a(ESound.bugSpecial, 1.0F, 1.0F);
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          func_70656_aK();
          setIllusionFormTime(0);
        } 
        this.field_70721_aZ++;
        setEnergy(getEnergy() - 0.1F);
        float f1 = func_110143_aJ();
        func_70606_j(f1 - amount);
        func_110142_aN().func_94547_a(source, f1, amount);
        func_110149_m(func_110139_bj() - amount);
      } 
    } 
  }
  
  protected void damageEntityTraining(DamageSource source, float amount) {
    if (!func_180431_b(source) && getFakeHealth() > 0.0F) {
      Entity entity = source.func_76346_g();
      if (amount <= 0.0F)
        return; 
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL)
        amount = 0.0F; 
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY)
        amount *= 0.75F; 
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
        amount *= 1.5F; 
      if (source.func_76346_g() instanceof net.minecraft.entity.monster.EntityVex)
        amount *= 0.33F; 
      if (isHero() && entity != null)
        amount *= (entity instanceof net.minecraft.entity.monster.IMob) ? 0.33333334F : 0.75F; 
      amount = func_70655_b(source, amount);
      amount = func_70672_c(source, amount);
      float f = amount;
      amount = Math.max(amount - func_110139_bj(), 0.0F);
      func_110149_m(func_110139_bj() - f - amount);
      if (amount != 0.0F) {
        EngenderGeneralEvent.playOnHitSound(source, entity, f);
        this.field_70721_aZ++;
        float f1 = getFakeHealth();
        setFakeHealth(f1 - amount);
        func_70691_i(func_110138_aP());
        func_110142_aN().func_94547_a(source, f1, amount);
        func_110149_m(func_110139_bj() - amount);
        if (getFakeHealth() <= 0.0F) {
          this.field_70173_aa = 0;
          this.lastChanceInvul = 100;
          func_70674_bp();
          func_70691_i(func_110138_aP());
          func_70690_d(new PotionEffect(MobEffects.field_76431_k, 4));
          func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i());
          func_70624_b((EntityLivingBase)null);
          if (entity instanceof EntityTameBase) {
            ((EntityTameBase)entity).func_70674_bp();
            ((EntityTameBase)entity).func_70691_i(func_110138_aP());
            ((EntityTameBase)entity).func_70624_b((EntityLivingBase)null);
          } 
          if (!isWild()) {
            incrementConversion((EntityPlayer)getOwner());
            getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(func_70005_c_() + " has been defeated in training by " + entity.func_70005_c_(), new Object[0]));
          } 
        } 
      } 
    } 
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    Entity entity = source.func_76346_g();
    if (this == entity)
      return false; 
    if (entity instanceof EntityTameBase && ((EntityTameBase)entity).func_184191_r(entity) && getFakeHealth() > 0.0F) {
      boolean flag1 = true;
      if (this.field_70172_ad > this.field_70771_an / 2.0F) {
        damageEntityTraining(source, amount - this.field_110153_bc);
        flag1 = false;
      } else {
        this.field_110153_bc = amount;
        this.field_70172_ad = this.field_70771_an;
        damageEntityTraining(source, amount);
        this.field_70738_aO = 10;
        this.field_70737_aN = this.field_70738_aO;
      } 
      if (flag1) {
        if (source instanceof EntityDamageSource && ((EntityDamageSource)source).func_180139_w()) {
          this.field_70170_p.func_72960_a((Entity)this, (byte)33);
        } else {
          this.field_70170_p.func_72960_a((Entity)this, (byte)2);
        } 
        double d1 = entity.field_70165_t - this.field_70165_t;
        double d0;
        for (d0 = entity.field_70161_v - this.field_70161_v; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
          d1 = (Math.random() - Math.random()) * 0.01D; 
        this.field_70739_aP = (float)(MathHelper.func_181159_b(d0, d1) * 57.29577951308232D - this.field_70177_z);
        func_70653_a(entity, 0.4F, d1, d0);
        if (entity instanceof EntityLivingBase)
          func_70624_b((EntityLivingBase)entity); 
      } 
      func_184581_c(source);
    } else if (entity instanceof EntityTameBase && func_184191_r(entity)) {
      return false;
    } 
    if (getFakeHealth() <= 0.0F && entity != null && entity instanceof EntityTameBase && func_184191_r(entity))
      ((EntityTameBase)entity).func_70624_b((EntityLivingBase)null); 
    if (!ForgeHooks.onLivingAttack((EntityLivingBase)this, source, amount))
      return false; 
    if (getOwner() != null && entity == getOwner()) {
      if (this instanceof EntityChicken)
        ((EntityChicken)this).timeUntilNextEgg -= 40; 
      this.moralRaisedTimer += 40;
      this.prevChasingPosX = this.chasingPosX = this.field_70165_t + MathHelper.func_76126_a(this.field_70761_aq * 0.017453292F) * 6.0D;
      this.prevChasingPosY = this.chasingPosY = this.field_70163_u + func_70047_e();
      this.prevChasingPosZ = this.chasingPosZ = this.field_70165_t - MathHelper.func_76134_b(this.field_70761_aq * 0.017453292F) * 6.0D;
      this.field_70140_Q = 0.0F;
      func_70624_b((EntityLivingBase)null);
      func_70604_c(null);
      func_70661_as().func_75499_g();
      func_70661_as().func_75497_a((Entity)this, 0.0D);
      if (!this.field_70170_p.field_72995_K && func_85035_bI() > 0) {
        func_85034_r(func_85035_bI() - 1);
        func_70097_a(DamageSource.field_76367_g, 1.0F);
        func_184185_a(SoundEvents.field_187903_gc, func_70599_aP(), func_70647_i());
        EntityTippedArrow entityTippedArrow = new EntityTippedArrow(this.field_70170_p, (EntityLivingBase)this);
        entityTippedArrow.func_82149_j((Entity)this);
        ((EntityArrow)entityTippedArrow).field_70163_u = this.field_70163_u + func_70047_e();
        entityTippedArrow.func_184547_a((Entity)this, -30.0F, this.field_70146_Z.nextFloat() * 360.0F, 0.0F, 0.35F, 1.0F);
        ((EntityArrow)entityTippedArrow).field_70251_a = EntityArrow.PickupStatus.ALLOWED;
        this.field_70170_p.func_72838_d((Entity)entityTippedArrow);
      } 
    } 
    if (func_180431_b(source) || amount == 0.0F || (
      
      !takesFallDamage() && source.func_76355_l() == "fall") || (
      func_180427_aV() && source.func_94541_c()) || (isHero() && (source.func_76355_l() == "sulphuric_acid" || source.func_76355_l() == "thermal" || source.func_76355_l() == "oxygen_suffocation" || source.func_76355_l() == "wither" || source.func_76355_l() == "inFire" || source.func_76355_l() == "onFire" || source.func_76355_l() == "lava" || source.func_76355_l() == "hotFloor" || source.func_76355_l() == "magic" || source.func_76355_l() == "indirectMagic")) || (source
      .func_76347_k() && (func_70644_a(MobEffects.field_76426_n) || func_70045_F())) || (
      isEntityImmuneToCoralium() && source.func_76355_l() == "coralium") || (
      isEntityImmuneToDread() && source.func_76355_l() == "dread") || (
      isEntityImmuneToAntiMatter() && source.func_76355_l() == "antimatter") || (
      isEntityImmuneToDarkness() && source.func_76355_l() == "shadow") || (entity != null && entity instanceof EntityLivingBase && 
      func_184191_r(entity) && !this.field_70170_p.func_82736_K().func_82766_b("friendlyFire")) || (
      getTier() == EnumTier.TIER6 && (source.func_76355_l() == "sulphuric_acid" || source.func_76355_l() == "thermal" || source.func_76355_l() == "oxygen_suffocation" || source.func_76355_l() == "wither" || source.func_76355_l() == "inFire" || source.func_76355_l() == "onFire" || source.func_76355_l() == "lava" || source.func_76355_l() == "hotFloor" || source.func_76355_l() == "chaosImplosion")) || source == DamageSource.field_191291_g)
      return false; 
    setSitResting(false);
    if (!this.field_70170_p.field_72995_K && entity != null && (func_70638_az() == null || (func_70638_az() != null && func_70032_d(entity) < func_70032_d((Entity)func_70638_az()))) && entity instanceof EntityLivingBase && !func_184191_r(entity) && !source.func_94541_c())
      func_70624_b((EntityLivingBase)entity); 
    if (this.field_70172_ad <= 1)
      if (source.func_76352_a()) {
        func_184185_a(getPierceHurtSound(), 3.0F, 1.0F);
      } else if (amount >= 7.0F || source.func_94541_c() || source.func_151517_h() || source.func_76363_c() || source == DamageSource.field_82728_o || source.func_76357_e() || source.func_82725_o() || source == DamageSource.field_76371_c) {
        func_184185_a(getCrushHurtSound(), 3.0F, 1.0F);
      } else {
        func_184185_a(getRegularHurtSound(), 3.0F, 1.0F);
      }  
    return super.func_70097_a(source, amount);
  }
  
  public int getDamageCap() {
    return 0;
  }
  
  public void func_70653_a(Entity entityIn, float strength, double xRatio, double zRatio) {
    if (this.field_70146_Z.nextDouble() >= ((func_70089_S() || this.field_70146_Z.nextInt(200) != 0) ? func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e() : 0.0D)) {
      this.field_70160_al = true;
      this.field_70181_x += strength;
      func_70661_as().func_75499_g();
      this.field_70760_ar = this.field_70126_B = this.field_70758_at = this.field_70761_aq = this.field_70177_z = this.field_70759_as = (float)MathHelper.func_181159_b(this.field_70179_y, this.field_70159_w) * 57.295776F - 90.0F;
      if (!func_70089_S() && strength >= 1.0F)
        strength *= 2.0F; 
      float f = MathHelper.func_76133_a(xRatio * xRatio + zRatio * zRatio);
      this.field_70159_w /= 2.0D;
      this.field_70179_y /= 2.0D;
      this.field_70159_w -= xRatio / f * strength;
      this.field_70179_y -= zRatio / f * strength;
    } 
  }
  
  public void cleave(int lootingModifier, DamageSource source) {
    if (!this.field_70170_p.field_72995_K) {
      this.field_70718_bc = 100;
      for (int ai = 0; ai <= lootingModifier; ai++) {
        EntityTameBase addon = spawnBaby(this);
        addon.func_82149_j((Entity)this);
        this.field_70170_p.func_72838_d((Entity)addon);
        addon.func_70097_a(DamageSource.field_76366_f, addon.func_110138_aP());
        addon.func_70606_j(0.0F);
        addon.func_70106_y();
      } 
      func_70097_a(DamageSource.field_76366_f, func_110138_aP());
      func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i());
      func_70606_j(0.0F);
    } 
  }
  
  protected float getStrengthMultiplier() {
    return 1.0F;
  }
  
  public void attackWithAdditionalEffects(Entity entity) {}
  
  public boolean func_70652_k(Entity entity) {
    if (!func_70089_S())
      return false; 
    if (this instanceof EntityEnderman && ((EntityEnderman)this).canDodgeAllAttacks())
      entity.field_70172_ad = 0; 
    float f = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * (((func_70093_af() || func_82150_aj() || !func_70685_l(entity)) && entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() != this) ? 3 : true) * (isHero() ? ((entity instanceof net.minecraft.entity.monster.IMob) ? 3.0F : 1.5F) : 1.0F) * getStrengthMultiplier();
    int i = 0;
    if (entity.getClass() == EntityList.func_192839_a("scp:scp682"))
      f *= 3.0F; 
    f *= (getStrength() + 50.0F) / 100.0F;
    if (entity instanceof EntityTameBase && func_184191_r(entity) && getFakeHealth() > 0.0F) {
      setCurrentStudy(EnumStudy.Combative, (int)((f <= 2.0F) ? 1.0F : (f / 2.0F)));
      entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), f);
      ((EntityLivingBase)entity).func_70653_a((Entity)this, i * 0.5F + 0.3F, MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
    } 
    if (entity instanceof EntityLivingBase && func_184191_r(entity) && !this.field_70170_p.func_82736_K().func_82766_b("friendlyFire"))
      return false; 
    if (entity instanceof net.minecraft.entity.passive.EntityAmbientCreature) {
      entity.func_184220_m((Entity)this);
      func_184185_a(SoundEvents.field_187739_dZ, 1.0F, 1.5F);
      func_70691_i(2.0F);
    } 
    setSitResting(false);
    try {
      ReflectionHelper.findField(entity.getClass(), new String[] { "recentlyHit", "field_70718_bc" }).setInt(entity, 100);
    } catch (Exception exception) {}
    if (isASwarmingMob())
      if (entity != null) {
        List<EntityTameBase> allies = this.field_70170_p.func_72872_a(getClass(), func_174813_aQ().func_186662_g(3.0D));
        if (!this.field_70170_p.field_72995_K && 
          !allies.isEmpty())
          for (int i1 = 0; i1 < allies.size(); i1++) {
            EntityTameBase entities = allies.get(i1);
            if (entities.func_70089_S() && func_184191_r((Entity)entities) && entities.isASwarmingMob() && entities.getClass() == getClass())
              f += 0.1F; 
          }  
      }  
    IAttributeInstance bonus = func_110148_a(SharedMonsterAttributes.field_111264_e);
    AttributeModifier vslight = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D831"), "Light Bonus", getBonusVSLight(), 1)).func_111168_a(false);
    AttributeModifier vsarmored = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D832"), "Armored Bonus", getBonusVSArmored(), 1)).func_111168_a(false);
    AttributeModifier vsmassive = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D833"), "Massive Bonus", getBonusVSMassive(), 1)).func_111168_a(false);
    AttributeModifier vsflying = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D834"), "Flying Bonus", getBonusVSFlying(), 1)).func_111168_a(false);
    AttributeModifier vstiny = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D835"), "Tiny Bonus", getBonusVSTiny(), 1)).func_111168_a(false);
    AttributeModifier vsender = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D836"), "Ender Bonus", getBonusVSEnder(), 1)).func_111168_a(false);
    AttributeModifier vselemental = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D837"), "Elemental Bonus", getBonusVSElemental(), 1)).func_111168_a(false);
    AttributeModifier vsstructure = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D838"), "Structure Bonus", getBonusVSStructure(), 1)).func_111168_a(false);
    AttributeModifier vsundead = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Undead Bonus", getBonusVSUndead(), 1)).func_111168_a(false);
    AttributeModifier vsanimal = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D840"), "Animal Bonus", getBonusVSAnimal(), 1)).func_111168_a(false);
    if (entity instanceof EntityLivingBase) {
      if (entity instanceof Light)
        if (!bonus.func_180374_a(vslight))
          bonus.func_111121_a(vslight);  
      if (entity instanceof Armored)
        if (!bonus.func_180374_a(vsarmored))
          bonus.func_111121_a(vsarmored);  
      if (entity instanceof Flying)
        if (!bonus.func_180374_a(vsflying))
          bonus.func_111121_a(vsflying);  
      if (entity instanceof Massive)
        if (!bonus.func_180374_a(vsmassive))
          bonus.func_111121_a(vsmassive);  
      if (entity instanceof Tiny)
        if (!bonus.func_180374_a(vstiny))
          bonus.func_111121_a(vstiny);  
      if (entity instanceof Elemental)
        if (!bonus.func_180374_a(vselemental))
          bonus.func_111121_a(vselemental);  
      if (entity instanceof Structure)
        if (!bonus.func_180374_a(vsstructure))
          bonus.func_111121_a(vsstructure);  
      if (entity instanceof Undead)
        if (!bonus.func_180374_a(vsundead))
          bonus.func_111121_a(vsundead);  
      if (entity instanceof Ender)
        if (!bonus.func_180374_a(vsender))
          bonus.func_111121_a(vsender);  
      if (entity instanceof Animal)
        if (!bonus.func_180374_a(vsanimal))
          bonus.func_111121_a(vsanimal);  
    } 
    AttributeModifier summondebuff = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D855"), "Summon Debuff", 0.5D, 1)).func_111168_a(false);
    if (hasLimitedLife() && !bonus.func_180374_a(summondebuff))
      bonus.func_111121_a(summondebuff); 
    if (EngenderConfig.mobs.useMobTalkerModels && this instanceof EntityEnderman && ((EntityEnderman)this).andr) {
      i += 60;
      f *= 10000.0F;
      if (entity instanceof EntityLivingBase) {
        entity.field_70172_ad = 0;
        entity.field_70181_x = this.field_70131_O * 0.25D;
        ((EntityLivingBase)entity).func_70653_a((Entity)this, i * 0.5F + 0.3F, MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
      } 
      if (!this.field_70170_p.field_72995_K)
        createEngenderModExplosionFireless((Entity)this, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, 7.0F + entity.field_70131_O + entity.field_70130_N, false); 
    } 
    if (EngenderConfig.mobs.useMobTalkerModels && this instanceof EntityGhast && ((EntityGhast)this).eleanor) {
      i += 30;
      f *= 1000.0F;
      if (entity instanceof EntityLivingBase) {
        entity.field_70172_ad = 0;
        entity.field_70181_x = this.field_70131_O * 0.25D;
        ((EntityLivingBase)entity).func_70653_a((Entity)this, i * 0.5F + 0.3F, MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
      } 
      if (!this.field_70170_p.field_72995_K)
        createEngenderModExplosionFireless((Entity)this, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, 7.0F + entity.field_70131_O + entity.field_70130_N, false); 
    } 
    if (func_70093_af() && entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() != this)
      i += 4; 
    if (entity instanceof EntityLivingBase) {
      f += EnchantmentHelper.func_152377_a(func_184614_ca(), ((EntityLivingBase)entity).func_70668_bt());
      f += EnchantmentHelper.func_152377_a(func_184592_cb(), ((EntityLivingBase)entity).func_70668_bt());
      i += EnchantmentHelper.func_77501_a((EntityLivingBase)this);
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead) {
        i += 3;
        double d2 = entity.field_70165_t - this.field_70165_t;
        double d3 = entity.field_70161_v - this.field_70161_v;
        double d4 = d2 * d2 + d3 * d3;
        entity.field_70159_w += d2 / d4 * 2.0D;
        entity.field_70181_x += 0.5D;
        entity.field_70179_y += d3 / d4 * 2.0D;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon) {
        i += 4;
        double d2 = entity.field_70165_t - this.field_70165_t;
        double d3 = entity.field_70161_v - this.field_70161_v;
        double d4 = d2 * d2 + d3 * d3;
        entity.field_70159_w += d2 / d4 * 2.0D;
        entity.field_70181_x += 0.5D;
        entity.field_70179_y += d3 / d4 * 2.0D;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle) {
        i += 9;
        double d2 = entity.field_70165_t - this.field_70165_t;
        double d3 = entity.field_70161_v - this.field_70161_v;
        double d4 = d2 * d2 + d3 * d3;
        entity.field_70159_w += d2 / d4 * 4.0D;
        entity.field_70181_x += 0.5D;
        entity.field_70179_y += d3 / d4 * 4.0D;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer) {
        i += 12;
        double d2 = entity.field_70165_t - this.field_70165_t;
        double d3 = entity.field_70161_v - this.field_70161_v;
        double d4 = d2 * d2 + d3 * d3;
        entity.field_70159_w += d2 / d4 * 6.0D;
        entity.field_70181_x += 0.5D;
        entity.field_70179_y += d3 / d4 * 6.0D;
      } 
    } 
    if (Loader.isModLoaded("iceandfire") && entity instanceof EntityLivingBase && EntityGorgon.isStoneMob((EntityLivingBase)entity))
      entity.field_70172_ad = 0; 
    if (func_110148_a(AGILITY).func_111125_b() >= this.field_70146_Z.nextDouble() * 10000.0D) {
      entity.field_70172_ad = 0;
      f *= 10.0F;
      func_184185_a(SoundEvents.field_187539_bB, 5.0F, 1.0F);
      if (EngenderConfig.general.useMessage && !isWild())
        getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(func_70005_c_() + " got a critical hit!", new Object[0])); 
    } 
    boolean flag = entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), f);
    if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortal)
      flag = entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h(), f); 
    if (entity instanceof EntityLivingBase && !(entity instanceof EntityTameBase) && func_70668_bt() == ((EntityLivingBase)entity).func_70668_bt() && ((EntityLivingBase)entity).func_70668_bt() != EnumCreatureAttribute.UNDEFINED)
      flag = entity.func_70097_a(!isWild() ? DamageSource.func_76365_a((EntityPlayer)getOwner()) : new DamageSource("generic"), f); 
    if (entity instanceof EntityPlayer && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL)
      flag = ((EntityPlayer)entity).func_70097_a(DamageSource.field_76377_j, f); 
    if (Loader.isModLoaded("draconicevolution") && entity instanceof EntityGuardianCrystal) {
      flag = entity.func_70097_a(!isWild() ? DamageSource.func_76365_a((EntityPlayer)getOwner()) : new DamageSource("generic"), f);
      ((EntityGuardianCrystal)entity).shieldTime = 0;
      entity.field_70159_w = 0.0D;
      entity.field_70181_x = 0.0D;
      entity.field_70179_y = 0.0D;
    } 
    if (entity instanceof IEntityMultiPart) {
      i = 0;
      if (entity != null) {
        Entity[] aentity = entity.func_70021_al();
        if (aentity != null)
          for (Entity parts : aentity) {
            if (parts instanceof MultiPartEntityPart)
              flag = ((IEntityMultiPart)entity).func_70965_a((MultiPartEntityPart)parts, (entity instanceof EntityTameBase) ? DamageSource.func_76358_a((EntityLivingBase)this) : DamageSource.func_76365_a(isWild() ? this.field_70170_p.func_72890_a((Entity)this, -1.0D) : (EntityPlayer)getOwner()), f); 
          }  
      } 
    } 
    if (entity instanceof EntityLivingBase && (!func_184191_r(entity) || (entity instanceof EntityTameBase && func_184191_r(entity) && getFakeHealth() <= 0.0F)) && !this.field_70170_p.func_82736_K().func_82766_b("friendlyFire"))
      flag = false; 
    if (flag && entity != null) {
      if (!(this instanceof IEntityMultiPart)) {
        func_70625_a(entity, 180.0F, func_70646_bf());
        this.field_70761_aq = this.field_70177_z = this.field_70759_as;
      } 
      if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime)
        func_184185_a(SoundEvents.field_187870_fk, func_70599_aP(), func_70647_i()); 
      EntityPlayer player = this.field_70170_p.func_72890_a((Entity)this, 16.0D);
      if (isHero()) {
        entity.func_184185_a(SoundEvents.field_187730_dW, 1.0F, func_70647_i());
        if (player != null)
          player.func_71009_b(entity); 
        if (entity instanceof net.minecraft.entity.monster.IMob) {
          entity.func_184185_a(SoundEvents.field_187718_dS, 1.0F, func_70647_i());
          if (player != null)
            player.func_71047_c(entity); 
        } 
      } 
    } 
    if (entity instanceof EntityPlayer) {
      EntityPlayer entityplayer = (EntityPlayer)entity;
      ItemStack itemstack = func_184614_ca();
      ItemStack itemstack1 = entityplayer.func_184587_cr() ? entityplayer.func_184607_cu() : null;
      if ((this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || !func_184222_aU()) && itemstack1 != null && itemstack1.func_77973_b() == Items.field_185159_cQ) {
        entityplayer.func_184811_cZ().func_185145_a(Items.field_185159_cQ, 100);
        this.field_70170_p.func_72960_a((Entity)entityplayer, (byte)30);
      } 
      if (itemstack != null && itemstack.func_77973_b() instanceof net.minecraft.item.ItemAxe && itemstack1 != null && itemstack1.func_77973_b() == Items.field_185159_cQ) {
        float f1 = 0.25F + EnchantmentHelper.func_185293_e((EntityLivingBase)this) * 0.05F;
        if (this.field_70146_Z.nextFloat() < f1) {
          entityplayer.func_184811_cZ().func_185145_a(Items.field_185159_cQ, 100);
          this.field_70170_p.func_72960_a((Entity)entityplayer, (byte)30);
        } 
      } 
    } 
    if (func_184191_r(entity))
      func_70624_b((EntityLivingBase)null); 
    if ((entity instanceof EntityLivingBase || (entity instanceof EntityPlayer && !((EntityPlayer)entity).field_71075_bZ.field_75102_a)) && (!func_184191_r(entity) || (entity instanceof EntityTameBase && func_184191_r(entity) && getFakeHealth() <= 0.0F)) && (entity != this || (!isWild() && entity != getOwner()))) {
      func_184609_a(EnumHand.MAIN_HAND);
      setCurrentStudy(EnumStudy.Combative, (int)f);
      func_174815_a((EntityLivingBase)this, entity);
      if (entity instanceof EntityLivingBase && !((EntityLivingBase)entity).func_184222_aU() && ((EntityLivingBase)entity).func_110143_aJ() <= 1.0F)
        ((EntityLivingBase)entity).func_70606_j(0.0F); 
      if (this instanceof EntityVex)
        ((EntityVex)this).setIsCharging(false); 
      ((EntityLivingBase)entity).field_70721_aZ++;
      if (((EntityLivingBase)entity).func_110143_aJ() > 1.0F)
        ((EntityLivingBase)entity).func_70606_j(((EntityLivingBase)entity).func_110143_aJ() - (isHero() ? 0.03F : 0.01F)); 
      if (entity.func_180431_b(DamageSource.func_76358_a((EntityLivingBase)this)) && f >= 6.0F) {
        ((EntityLivingBase)entity).func_70606_j(((EntityLivingBase)entity).func_110143_aJ() - f);
        if (EngenderMod.isWoodLikeMob(entity)) {
          entity.func_184185_a(ESound.woodHitCrush, 2.0F, 1.0F);
        } else if (EngenderMod.isMetalLikeMob(entity)) {
          entity.func_184185_a(ESound.metalHitCrush, 2.0F, 1.0F);
        } else if (entity.field_70131_O >= 5.0F) {
          entity.func_184185_a(ESound.fleshHitCrushHeavy, 2.0F, 1.0F);
        } else {
          entity.func_184185_a(ESound.fleshHitCrush, 2.0F, 1.0F);
        } 
        if (((EntityLivingBase)entity).func_110143_aJ() <= 0.0F)
          ((EntityLivingBase)entity).func_70645_a(DamageSource.func_76358_a((EntityLivingBase)this)); 
      } 
    } 
    if (entity instanceof EntityLivingBase && (entity != this || (!isWild() && entity != getOwner())) && flag) {
      ((EntityLivingBase)entity).func_70653_a((Entity)this, i * 0.5F + 0.3F, MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
      if (entity instanceof EntityPlayerMP)
        ((EntityPlayerMP)entity).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity(entity)); 
      int j = EnchantmentHelper.func_90036_a((EntityLivingBase)this);
      if (j > 0)
        entity.func_70015_d(j * 4); 
      if (!func_184614_ca().func_190926_b() && func_184614_ca().func_77973_b() != null && !isWild())
        func_184614_ca().func_77961_a((EntityLivingBase)entity, (EntityPlayer)getOwner()); 
      if (!func_184592_cb().func_190926_b() && func_184592_cb().func_77973_b() != null && !isWild())
        func_184592_cb().func_77961_a((EntityLivingBase)entity, (EntityPlayer)getOwner()); 
      if (!func_184614_ca().func_190926_b() && func_184614_ca().func_77973_b() != null && this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie && func_184614_ca().func_77973_b() == Items.field_151010_B)
        func_184614_ca().func_77964_b(0); 
      if (!func_184592_cb().func_190926_b() && func_184592_cb().func_77973_b() != null && this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie && func_184592_cb().func_77973_b() == Items.field_151010_B)
        func_184592_cb().func_77964_b(0); 
      if (!func_184614_ca().func_190926_b() && func_184614_ca().func_77973_b() != null && this instanceof EntityVex && func_184614_ca().func_77973_b() == Items.field_151040_l)
        func_184614_ca().func_77964_b(0); 
      if (!func_184592_cb().func_190926_b() && func_184592_cb().func_77973_b() != null && this instanceof EntityVex && func_184592_cb().func_77973_b() == Items.field_151040_l)
        func_184592_cb().func_77964_b(0); 
      func_174815_a((EntityLivingBase)this, entity);
      setCurrentStudy(EnumStudy.Physical, (int)f);
    } 
    setEnergy(getEnergy() - 0.25F);
    if (EngenderConfig.general.useMessage && !entity.func_70089_S() && !isWild()) {
      boolean flag1 = false;
      try {
        flag1 = ReflectionHelper.findField(entity.getClass(), new String[] { "dead", "field_70729_aU" }).getBoolean(entity);
      } catch (Exception exception) {}
      if (!flag1) {
        if (entity instanceof EntityLivingBase)
          setCurrentStudy(EnumStudy.Combative, (int)((EntityLivingBase)entity).func_110138_aP()); 
        getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was " + (func_82150_aj() ? "ambushed" : (func_70093_af() ? "assassinated" : "slain")) + " by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0]));
      } 
    } 
    if (bonus.func_180374_a(vslight))
      bonus.func_111124_b(vslight); 
    if (bonus.func_180374_a(vsarmored))
      bonus.func_111124_b(vsarmored); 
    if (bonus.func_180374_a(vsmassive))
      bonus.func_111124_b(vsmassive); 
    if (bonus.func_180374_a(vsflying))
      bonus.func_111124_b(vsflying); 
    if (bonus.func_180374_a(vstiny))
      bonus.func_111124_b(vstiny); 
    if (bonus.func_180374_a(vselemental))
      bonus.func_111124_b(vselemental); 
    if (bonus.func_180374_a(vsstructure))
      bonus.func_111124_b(vsstructure); 
    if (bonus.func_180374_a(vsundead))
      bonus.func_111124_b(vsundead); 
    if (bonus.func_180374_a(vsender))
      bonus.func_111124_b(vsender); 
    if (bonus.func_180374_a(vsanimal))
      bonus.func_111124_b(vsanimal); 
    if (bonus.func_180374_a(summondebuff))
      bonus.func_111124_b(summondebuff); 
    return true;
  }
  
  public void func_70624_b(@Nullable EntityLivingBase entitylivingbaseIn) {
    if (!func_190631_cK())
      entitylivingbaseIn = null; 
    if (entitylivingbaseIn != null)
      setSitResting(false); 
    super.func_70624_b(entitylivingbaseIn);
  }
  
  public void fireLightning(Entity entity, double x, double y, double z) {
    if (entity != null && entity.func_70089_S()) {
      double d3 = x;
      double d4 = y;
      double d5 = z;
      double d6 = entity.field_70165_t - d3;
      double d7 = entity.field_70163_u - d4;
      double d8 = entity.field_70161_v - d5;
      EntityPortalLightning entitywitherskull = new EntityPortalLightning(this.field_70170_p, entity, (EntityLivingBase)this, d6, d7, d8);
      entitywitherskull.field_70163_u = d4;
      entitywitherskull.field_70165_t = d3;
      entitywitherskull.field_70161_v = d5;
      entitywitherskull.field_70233_c = d4;
      entitywitherskull.field_70232_b = d3;
      entitywitherskull.field_70230_d = d5;
      entitywitherskull.targetEntity = entity;
      this.field_70170_p.func_72838_d((Entity)entitywitherskull);
    } 
  }
  
  public void inflictCustomStatusEffect(EnumDifficulty scaling, EntityLivingBase entity, Potion effect, int time, int power) {
    if ((scaling == EnumDifficulty.PEACEFUL || func_184191_r((Entity)entity)) && effect.func_76398_f())
      return; 
    if (scaling == EnumDifficulty.NORMAL)
      time *= 2; 
    if (scaling == EnumDifficulty.HARD)
      time *= 5; 
    if (time > 0)
      entity.func_70690_d(new PotionEffect(effect, time * 20, power - 1 + scaling.func_151525_a())); 
  }
  
  public void inflictEngenderMobDamage(EntityLivingBase entity, String killmessage, DamageSource attacktype, float damage) {
    if (Loader.isModLoaded("scp"))
      if (entity instanceof alexiy.secure.contain.protect.entity.Entity682)
        damage *= 100.0F;  
    IAttributeInstance bonus = func_110148_a(SharedMonsterAttributes.field_111264_e);
    AttributeModifier vslight = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D831"), "Light Bonus", getBonusVSLight(), 1)).func_111168_a(false);
    AttributeModifier vsarmored = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D832"), "Armored Bonus", getBonusVSArmored(), 1)).func_111168_a(false);
    AttributeModifier vsmassive = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D833"), "Massive Bonus", getBonusVSMassive(), 1)).func_111168_a(false);
    AttributeModifier vsflying = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D834"), "Flying Bonus", getBonusVSFlying(), 1)).func_111168_a(false);
    AttributeModifier vstiny = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D835"), "Tiny Bonus", getBonusVSTiny(), 1)).func_111168_a(false);
    AttributeModifier vsender = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D836"), "Ender Bonus", getBonusVSEnder(), 1)).func_111168_a(false);
    AttributeModifier vselemental = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D837"), "Elemental Bonus", getBonusVSElemental(), 1)).func_111168_a(false);
    AttributeModifier vsstructure = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D838"), "Structure Bonus", getBonusVSStructure(), 1)).func_111168_a(false);
    AttributeModifier vsundead = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Undead Bonus", getBonusVSUndead(), 1)).func_111168_a(false);
    AttributeModifier vsanimal = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D840"), "Animal Bonus", getBonusVSAnimal(), 1)).func_111168_a(false);
    if (entity instanceof EntityLivingBase) {
      if (entity instanceof Light)
        if (!bonus.func_180374_a(vslight))
          bonus.func_111121_a(vslight);  
      if (entity instanceof Armored)
        if (!bonus.func_180374_a(vsarmored))
          bonus.func_111121_a(vsarmored);  
      if (entity instanceof Flying)
        if (!bonus.func_180374_a(vsflying))
          bonus.func_111121_a(vsflying);  
      if (entity instanceof Massive)
        if (!bonus.func_180374_a(vsmassive))
          bonus.func_111121_a(vsmassive);  
      if (entity instanceof Tiny)
        if (!bonus.func_180374_a(vstiny))
          bonus.func_111121_a(vstiny);  
      if (entity instanceof Elemental)
        if (!bonus.func_180374_a(vselemental))
          bonus.func_111121_a(vselemental);  
      if (entity instanceof Structure)
        if (!bonus.func_180374_a(vsstructure))
          bonus.func_111121_a(vsstructure);  
      if (entity instanceof Undead)
        if (!bonus.func_180374_a(vsundead))
          bonus.func_111121_a(vsundead);  
      if (entity instanceof Ender)
        if (!bonus.func_180374_a(vsender))
          bonus.func_111121_a(vsender);  
      if (entity instanceof Animal)
        if (!bonus.func_180374_a(vsanimal))
          bonus.func_111121_a(vsanimal);  
    } 
    AttributeModifier summondebuff = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D855"), "Summon Debuff", 0.5D, 1)).func_111168_a(false);
    if (hasLimitedLife() && !bonus.func_180374_a(summondebuff))
      bonus.func_111121_a(summondebuff); 
    if (!func_184191_r((Entity)entity) || (entity instanceof EntityTameBase && func_184191_r((Entity)entity) && getFakeHealth() > 0.0F)) {
      entity.field_70721_aZ++;
      damage *= (((func_70093_af() || func_82150_aj() || !func_70685_l((Entity)entity)) && entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() != this) ? 3 : true) * (isHero() ? ((entity instanceof net.minecraft.entity.monster.IMob) ? 3.0F : 1.5F) : 1.0F);
      try {
        ReflectionHelper.findField(entity.getClass(), new String[] { "recentlyHit", "field_70718_bc" }).setInt(entity, 100);
      } catch (Exception exception) {}
      if (Loader.isModLoaded("iceandfire") && entity instanceof EntityLivingBase && EntityGorgon.isStoneMob(entity))
        entity.field_70172_ad = 0; 
      if (Loader.isModLoaded("draconicevolution") && entity instanceof EntityGuardianCrystal) {
        entity.func_70097_a(!isWild() ? DamageSource.func_76365_a((EntityPlayer)getOwner()) : new DamageSource("generic"), damage);
        ((EntityGuardianCrystal)entity).shieldTime = 0;
        entity.field_70159_w = 0.0D;
        entity.field_70181_x = 0.0D;
        entity.field_70179_y = 0.0D;
      } 
      if (attacktype.func_76346_g() != null && !(attacktype instanceof net.minecraft.util.EntityDamageSourceIndirect) && func_110148_a(AGILITY).func_111125_b() >= this.field_70146_Z.nextDouble() * 10000.0D) {
        entity.field_70172_ad = 0;
        damage *= 10.0F;
        func_184185_a(SoundEvents.field_187539_bB, 5.0F, 1.0F);
        if (EngenderConfig.general.useMessage && !isWild())
          getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(func_70005_c_() + " got a critical hit!", new Object[0])); 
      } 
      damage *= getStrengthMultiplier();
      if (entity.func_70089_S()) {
        if (entity instanceof IEntityMultiPart) {
          if (entity != null) {
            Entity[] aentity = entity.func_70021_al();
            if (aentity != null) {
              Entity mob = aentity[this.field_70146_Z.nextInt((entity.func_70021_al()).length)];
              if (mob instanceof MultiPartEntityPart)
                if (!((IEntityMultiPart)entity).func_70965_a((MultiPartEntityPart)mob, (entity instanceof EntityTameBase) ? DamageSource.func_76358_a((EntityLivingBase)this) : DamageSource.func_76365_a(isWild() ? this.field_70170_p.func_72890_a((Entity)this, -1.0D) : (EntityPlayer)getOwner()), damage) && attacktype.func_76355_l() == "sans")
                  entity.func_70606_j(entity.func_110143_aJ() - 1.0F);  
            } 
          } 
        } else if (!entity.func_70097_a(attacktype, damage) && attacktype.func_76355_l() == "sans") {
          entity.func_70606_j(entity.func_110143_aJ() - 1.0F);
        } 
        if (!(entity instanceof EntityTameBase) && entity.func_180431_b(attacktype) && (damage >= 6.0F || attacktype.func_94541_c() || attacktype.func_151517_h() || attacktype.func_76363_c() || attacktype == DamageSource.field_82728_o || attacktype.func_76357_e() || attacktype.func_82725_o() || attacktype == DamageSource.field_76371_c)) {
          entity.func_70606_j(entity.func_110143_aJ() - damage);
          if (EngenderMod.isWoodLikeMob((Entity)entity)) {
            entity.func_184185_a(ESound.woodHitCrush, 2.0F, 1.0F);
          } else if (EngenderMod.isMetalLikeMob((Entity)entity)) {
            entity.func_184185_a(ESound.metalHitCrush, 2.0F, 1.0F);
          } else if (entity.field_70131_O >= 5.0F) {
            entity.func_184185_a(ESound.fleshHitCrushHeavy, 2.0F, 1.0F);
          } else {
            entity.func_184185_a(ESound.fleshHitCrush, 2.0F, 1.0F);
          } 
          if (entity.func_110143_aJ() <= 0.0F)
            entity.func_70645_a(attacktype); 
        } 
        entity.field_70172_ad--;
        if (isABoss() && entity.func_184222_aU())
          entity.field_70172_ad = 0; 
        setCurrentStudy(EnumStudy.Combative, (int)(damage / 4.0F));
        entity.field_70721_aZ++;
        setCurrentStudy(EnumStudy.Physical, (int)damage);
        if (entity.func_110143_aJ() > 1.0F)
          entity.func_70606_j(entity.func_110143_aJ() - (isHero() ? 0.03F : 0.01F)); 
        if (!entity.func_70089_S() && !isWild() && entity.func_110143_aJ() <= 0.0F) {
          boolean flag1 = false;
          try {
            flag1 = ReflectionHelper.findField(entity.getClass(), new String[] { "dead", "field_70729_aU" }).getBoolean(entity);
          } catch (Exception exception) {}
          if (EngenderConfig.general.useMessage && !flag1)
            getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + killmessage + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
        } 
        if (bonus.func_180374_a(vslight))
          bonus.func_111124_b(vslight); 
        if (bonus.func_180374_a(vsarmored))
          bonus.func_111124_b(vsarmored); 
        if (bonus.func_180374_a(vsmassive))
          bonus.func_111124_b(vsmassive); 
        if (bonus.func_180374_a(vsflying))
          bonus.func_111124_b(vsflying); 
        if (bonus.func_180374_a(vstiny))
          bonus.func_111124_b(vstiny); 
        if (bonus.func_180374_a(vselemental))
          bonus.func_111124_b(vselemental); 
        if (bonus.func_180374_a(vsstructure))
          bonus.func_111124_b(vsstructure); 
        if (bonus.func_180374_a(vsundead))
          bonus.func_111124_b(vsundead); 
        if (bonus.func_180374_a(vsender))
          bonus.func_111124_b(vsender); 
        if (bonus.func_180374_a(vsanimal))
          bonus.func_111124_b(vsanimal); 
        if (bonus.func_180374_a(summondebuff))
          bonus.func_111124_b(summondebuff); 
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_145770_h(double x, double y, double z) {
    return true;
  }
  
  protected final void func_70105_a(float width, float height) {
    if (this.field_70173_aa >= 1 && getTier() != EnumTier.TIER6) {
      float fit = getFittness();
      width *= fit;
      height *= fit;
    } 
    if (func_70631_g_()) {
      width *= 0.5F;
      height *= 0.5F;
    } 
    this.reachWidth = width;
    if (width != this.field_70130_N || height != this.field_70131_O) {
      float f = this.field_70130_N;
      this.field_70130_N = width;
      this.field_70131_O = height;
      if (this.field_70130_N < f) {
        double d0 = width / 2.0D;
        func_174826_a(new AxisAlignedBB(this.field_70165_t - d0, this.field_70163_u, this.field_70161_v - d0, this.field_70165_t + d0, this.field_70163_u + this.field_70131_O, this.field_70161_v + d0));
        return;
      } 
      AxisAlignedBB axisalignedbb = func_174813_aQ();
      func_174826_a(new AxisAlignedBB(axisalignedbb.field_72340_a, axisalignedbb.field_72338_b, axisalignedbb.field_72339_c, axisalignedbb.field_72340_a + this.field_70130_N, axisalignedbb.field_72338_b + this.field_70131_O, axisalignedbb.field_72339_c + this.field_70130_N));
    } 
  }
  
  public int getGrowingAge() {
    return ((Integer)this.field_70180_af.func_187225_a(AGE)).intValue();
  }
  
  public void setGrowingAge(int age) {
    this.field_70180_af.func_187227_b(AGE, Integer.valueOf(age));
    if (age == 0)
      resetInLove(); 
    if (age < 0 && !func_70631_g_())
      setChild(true); 
  }
  
  public int getLevel() {
    return ((Integer)this.field_70180_af.func_187225_a(LEVEL)).intValue();
  }
  
  public void setLevel(int age) {
    this.field_70180_af.func_187227_b(LEVEL, Integer.valueOf(age));
  }
  
  public float getEXP() {
    return ((Float)this.field_70180_af.func_187225_a(EXP)).floatValue();
  }
  
  public void setEXP(float age) {
    this.field_70180_af.func_187227_b(EXP, Float.valueOf(age));
  }
  
  public float getTotalEXP() {
    return ((Float)this.field_70180_af.func_187225_a(TOTALEXP)).floatValue();
  }
  
  public void setTotalEXP(float age) {
    this.field_70180_af.func_187227_b(TOTALEXP, Float.valueOf(age));
  }
  
  public int getNextLevelRequirement() {
    return 5;
  }
  
  public boolean func_180427_aV() {
    return (this.field_70173_aa <= 20 || this.lastChanceInvul > 0 || isHero() || func_175446_cd());
  }
  
  public boolean takesFallDamage() {
    return (!func_184207_aI() || getFakeHealth() > 0.0F || this.lastChanceInvul > 0 || this instanceof Flying || this instanceof Massive);
  }
  
  public Entity func_184204_a(int dimensionIn) {
    if (this.field_70173_aa < 400)
      return null; 
    return super.func_184204_a(dimensionIn);
  }
  
  protected void func_70619_bc() {
    if (!this.field_70170_p.field_72995_K && func_70638_az() == null && this.field_70146_Z.nextInt(20) == 0) {
      List<EntityTameBase> training = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_186662_g((getAttackState() > 1) ? 1.0D : func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e()), Predicates.and(EntitySelectors.field_94557_a, EntitySelectors.field_180132_d));
      for (int j2 = 0; j2 < 10 && !training.isEmpty(); j2++) {
        EntityTameBase entitylivingbase = training.get(this.field_70146_Z.nextInt(training.size()));
        if (entitylivingbase != this && entitylivingbase.func_70089_S() && func_70685_l((Entity)entitylivingbase) && entitylivingbase.func_184753_b() == func_184753_b() && entitylivingbase.getFakeHealth() > 0.0F) {
          func_70624_b((EntityLivingBase)entitylivingbase);
          break;
        } 
        training.remove(entitylivingbase);
      } 
    } 
    setGrowingAge(getGrowingAge() + 1);
    if (!isWild() && canBeMarried() && !getOwner().func_184614_ca().func_190926_b() && getOwner().func_184614_ca().func_77973_b() == EItem.weddingring)
      func_70671_ap().func_75651_a((Entity)getOwner(), func_184649_cE(), func_70646_bf()); 
    if (getGrowingAge() < 0)
      this.inLove = 0; 
    if (getGrowingAge() < 0 && !func_70631_g_()) {
      setChild(true);
    } else if (getGrowingAge() >= 0 && func_70631_g_()) {
      setChild(false);
      this.field_70173_aa = 1;
      func_184185_a(ESound.hero, 1.0F, 1.5F);
    } 
    if (getAttackState() > 0) {
      if (getAttackState() == 1 && func_174818_b(getGuardBlock()) >= 576.0D)
        func_70661_as().func_75492_a(getGuardBlock().func_177958_n(), getGuardBlock().func_177956_o(), getGuardBlock().func_177952_p(), 1.0D); 
    } else {
      setGuardBlock(func_180425_c().func_177977_b());
    } 
    this.field_71088_bW--;
    if (this.field_70173_aa > 400 && this.field_70146_Z.nextInt(60) == 0 && !isWild() && (getOwner()).field_70173_aa > 400 && func_70638_az() == null && !func_184218_aH() && !func_184207_aI()) {
      if (Loader.isModLoaded("twilightforest"))
        if (!isWild() && func_70638_az() == null && !func_184218_aH() && !func_184207_aI()) {
          int i11 = MathHelper.func_76128_c((getOwner()).field_70163_u);
          int l1 = MathHelper.func_76128_c((getOwner()).field_70165_t);
          int i2 = MathHelper.func_76128_c((getOwner()).field_70161_v);
          for (int k2 = -2; k2 <= 2; k2++) {
            for (int l2 = -2; l2 <= 2; l2++) {
              for (int j = -2; j <= 2; j++) {
                int i3 = l1 + k2;
                int k = i11 + j;
                int l = i2 + l2;
                BlockPos blockpos = new BlockPos(i3 + 0.5D, k, l + 0.5D);
                IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
                Block block = iblockstate.func_177230_c();
                if (block instanceof twilightforest.block.BlockTFPortal && this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c() != Blocks.field_150350_a && this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c() != Blocks.field_150427_aO) {
                  if (this instanceof EntityVex || this instanceof EntityGhast) {
                    func_70605_aq().func_75642_a(i3, k, l, 1.0D);
                  } else {
                    func_70661_as().func_75492_a(i3, k, l, 1.0D);
                  } 
                  if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof EntityGhast || func_174831_c(blockpos) < 4.0D) {
                    func_70634_a(i3 + 0.5D, k, l + 0.5D);
                    if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker)
                      func_184185_a(SoundEvents.field_187791_eX, func_70599_aP(), 0.95F); 
                  } 
                } 
              } 
            } 
          } 
        }  
      if (!isWild() && func_70638_az() == null && !func_184218_aH() && !func_184207_aI()) {
        int i11 = MathHelper.func_76128_c((getOwner()).field_70163_u);
        int l1 = MathHelper.func_76128_c((getOwner()).field_70165_t);
        int i2 = MathHelper.func_76128_c((getOwner()).field_70161_v);
        for (int k2 = -2; k2 <= 2; k2++) {
          for (int l2 = -2; l2 <= 2; l2++) {
            for (int j = -2; j <= 2; j++) {
              int i3 = l1 + k2;
              int k = i11 + j;
              int l = i2 + l2;
              BlockPos blockpos = new BlockPos(i3 + 0.5D, k, l + 0.5D);
              IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
              Block block = iblockstate.func_177230_c();
              if ((block instanceof net.minecraft.block.BlockEndGateway || block instanceof net.minecraft.block.BlockEndPortal || block instanceof net.minecraft.block.BlockPortal) && this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c() != Blocks.field_150350_a && this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c() != Blocks.field_150427_aO) {
                if (this instanceof EntityVex || this instanceof EntityGhast) {
                  func_70605_aq().func_75642_a(i3, k, l, 1.0D);
                } else {
                  func_70661_as().func_75492_a(i3, k, l, 1.0D);
                } 
                if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof EntityGhast || func_174831_c(blockpos) < 4.0D) {
                  if (block instanceof net.minecraft.block.BlockEndPortal && !func_184222_aU())
                    func_184204_a(1); 
                  func_70634_a(i3 + 0.5D, k, l + 0.5D);
                  if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker)
                    func_184185_a(SoundEvents.field_187791_eX, func_70599_aP(), 0.95F); 
                  if (block instanceof net.minecraft.block.BlockEndGateway) {
                    this.field_70170_p.func_175698_g(blockpos.func_177981_b(1));
                    this.field_70170_p.func_175698_g(blockpos.func_177981_b(2));
                    this.field_70170_p.func_175698_g(blockpos.func_177981_b(1).func_177974_f());
                    this.field_70170_p.func_175698_g(blockpos.func_177981_b(1).func_177978_c());
                    this.field_70170_p.func_175698_g(blockpos.func_177981_b(1).func_177976_e());
                    this.field_70170_p.func_175698_g(blockpos.func_177981_b(1).func_177968_d());
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      if (Loader.isModLoaded("abyssalcraft")) {
        int i11 = MathHelper.func_76128_c((getOwner()).field_70163_u);
        int l1 = MathHelper.func_76128_c((getOwner()).field_70165_t);
        int i2 = MathHelper.func_76128_c((getOwner()).field_70161_v);
        for (int k2 = -2; k2 <= 2; k2++) {
          for (int l2 = -2; l2 <= 2; l2++) {
            for (int j = -2; j <= 2; j++) {
              int i3 = l1 + k2;
              int k = i11 + j;
              int l = i2 + l2;
              BlockPos blockpos = new BlockPos(i3 + 0.5D, k, l + 0.5D);
              IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
              Block block = iblockstate.func_177230_c();
              if ((block instanceof com.shinoow.abyssalcraft.common.blocks.BlockAbyssPortal || block instanceof com.shinoow.abyssalcraft.common.blocks.BlockDreadlandsPortal || block instanceof com.shinoow.abyssalcraft.common.blocks.BlockOmotholPortal) && this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c().func_149662_c(this.field_70170_p.func_180495_p(blockpos.func_177977_b()))) {
                if (this instanceof EntityVex || this instanceof EntityGhast) {
                  func_70605_aq().func_75642_a(i3, k, l, 1.0D);
                } else {
                  func_70661_as().func_75492_a(i3, k, l, 1.0D);
                } 
                if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof EntityGhast || func_174831_c(blockpos) < 4.0D) {
                  if (block instanceof com.shinoow.abyssalcraft.common.blocks.BlockAbyssPortal && (!func_184222_aU() || func_184207_aI() || func_184218_aH()))
                    if (this.field_71093_bK == ACLib.abyssal_wasteland_id) {
                      func_184204_a(0);
                    } else {
                      func_184204_a(ACLib.abyssal_wasteland_id);
                    }  
                  if (block instanceof com.shinoow.abyssalcraft.common.blocks.BlockDreadlandsPortal && (!func_184222_aU() || func_184207_aI() || func_184218_aH()))
                    if (this.field_71093_bK == ACLib.dreadlands_id) {
                      func_184204_a(ACLib.abyssal_wasteland_id);
                    } else {
                      func_184204_a(ACLib.dreadlands_id);
                    }  
                  if (block instanceof com.shinoow.abyssalcraft.common.blocks.BlockOmotholPortal && (!func_184222_aU() || func_184207_aI() || func_184218_aH()))
                    if (this.field_71093_bK == ACLib.omothol_id) {
                      func_184204_a(ACLib.dreadlands_id);
                    } else {
                      func_184204_a(ACLib.omothol_id);
                    }  
                  func_70634_a(i3 + 0.5D, k, l + 0.5D);
                  if (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker)
                    func_184185_a(SoundEvents.field_187791_eX, func_70599_aP(), 0.95F); 
                } 
              } 
            } 
          } 
        } 
      } 
    } 
    if (!this.field_70145_X && func_70094_T()) {
      this.field_70181_x += 0.5D;
      func_70107_b(this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v);
      if (func_184218_aH() && func_184187_bx().func_70094_T()) {
        (func_184187_bx()).field_70181_x += 0.5D;
        func_184187_bx().func_70107_b(this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v);
      } 
    } 
    if (this.field_70173_aa > 20 && func_70089_S())
      super.func_70619_bc(); 
  }
  
  public double getKnockbackResistance() {
    return func_110148_a(STRENGTH).func_111125_b() / 100.0D;
  }
  
  public boolean canUseGuardBlock() {
    return (!func_70631_g_() && !isInLove() && !isCameo());
  }
  
  public boolean canTrample(World world, Block block, BlockPos pos, float fallDistance) {
    return false;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.UNDEFINED;
  }
  
  public boolean func_70687_e(PotionEffect potioneffectIn) {
    Potion potion = potioneffectIn.func_188419_a();
    if (func_70662_br() && (potion == MobEffects.field_76428_l || potion == MobEffects.field_76436_u))
      return false; 
    if (isEntityImmuneToCoralium() && potion.func_76393_a() == "potion.Cplague") {
      func_70688_c(potioneffectIn);
      return false;
    } 
    if (isEntityImmuneToDread() && potion.func_76393_a() == "potion.Dplague") {
      func_70688_c(potioneffectIn);
      return false;
    } 
    if (isEntityImmuneToAntiMatter() && potion.func_76393_a() == "potion.Antimatter") {
      func_70688_c(potioneffectIn);
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
  
  public double func_70033_W() {
    return (this.field_70131_O <= 0.5F) ? 0.2D : 0.0D;
  }
  
  public void func_184210_p() {
    Entity entity = func_184187_bx();
    super.func_184210_p();
    if (entity != null && entity != func_184187_bx()) {
      func_82149_j(entity);
      this.field_70137_T = this.field_70167_r = this.field_70163_u += entity.field_70131_O;
    } 
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return null;
  }
  
  public boolean func_184645_a(EntityPlayer player, EnumHand hand) {
    ItemStack itemstack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(itemstack.func_77973_b());
    if (func_70089_S() && func_184191_r((Entity)player) && itemstack.func_77973_b() != null && !itemstack.func_190926_b() && itemstack.func_77973_b() == EItem.statChecker) {
      itemstack.func_77973_b().func_111207_a(itemstack, player, (EntityLivingBase)this, hand);
      return true;
    } 
    if (hasLimitedLife())
      return false; 
    if (itemstack.func_77973_b() != null && !itemstack.func_190926_b() && getBookID() == 0 && itemstack.func_77973_b() instanceof ItemLearningBook) {
      player.func_184609_a(hand);
      for (int i = 0; i < books.length; i++) {
        if (itemstack.func_77973_b() == books[i]) {
          setBookID(i);
          break;
        } 
      } 
      setBookDurability(itemstack.func_77952_i());
      heldItem = new ItemStack(books[getBookID()], 1, getBookDurability());
      heldItem.func_77982_d(itemstack.func_77978_p());
      heldItem.func_77964_b(itemstack.func_77952_i());
      itemstack.func_190918_g(1);
      return true;
    } 
    if (getBookID() != 0 && itemstack.func_190926_b()) {
      player.func_184609_a(hand);
      func_184185_a(SoundEvents.field_187694_dK, 1.0F, 1.3F + this.field_70146_Z.nextFloat() * 0.4F);
      if (!this.field_70170_p.field_72995_K)
        if (getBookDurability() >= getCurrentBook().func_77958_k()) {
          setBookID(0);
          setBookDurability(0);
        } else {
          func_70099_a(new ItemStack(books[getBookID()], 1, getBookDurability()), 1.0F);
          setBookID(0);
          setBookDurability(0);
        }  
      return true;
    } 
    if (itemstack.func_77973_b() == Items.field_151063_bx) {
      if (!this.field_70170_p.field_72995_K) {
        Class<? extends Entity> oclass = EntityList.getClass(ItemMonsterPlacer.func_190908_h(itemstack));
        if (oclass != null && getClass() == oclass) {
          EntityTameBase idleTimeable = spawnBaby(this);
          if (idleTimeable != null) {
            idleTimeable.setOwnerId(func_184753_b());
            idleTimeable.func_82149_j((Entity)this);
            this.field_70170_p.func_72838_d((Entity)idleTimeable);
            idleTimeable.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
            idleTimeable.setGrowingAge(-24000);
            if (itemstack.func_82837_s())
              idleTimeable.func_96094_a(itemstack.func_82833_r()); 
            if (!player.field_71075_bZ.field_75098_d)
              itemstack.func_190918_g(1); 
          } 
        } 
      } 
      return true;
    } 
    if (func_70631_g_()) {
      if (hasOwner(player)) {
        player.func_184609_a(EnumHand.MAIN_HAND);
        if (func_184187_bx() == null) {
          func_184205_a((Entity)player, true);
        } else {
          func_184210_p();
        } 
      } 
      return true;
    } 
    if (hasOwner(player) && func_184187_bx() != null) {
      player.func_184609_a(EnumHand.MAIN_HAND);
      func_184210_p();
      return true;
    } 
    if (EngenderConfig.mobs.breeding && canBeMatedWith() && itemstack.func_77973_b() == Items.field_151153_ao && itemstack.func_77960_j() == ((getTier() == EnumTier.TIER5 || getTier() == EnumTier.TIER6) ? 1 : 0)) {
      if (hasOwner(player))
        if (isInLove()) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation(func_70005_c_() + " is already horny!!", new Object[0]), true);
        } else if (getGrowingAge() < 4000) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation(func_70005_c_() + " can't breed yet as they're too tired!", new Object[0]), true);
        } else if (getJukeboxToDanceTo() != null) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation("You can't breed with " + func_70005_c_() + " as she's already having fun!", new Object[0]), true);
        } else if (func_70638_az() != null) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation("You can't breed with " + func_70005_c_() + " as it isn't safe yet!", new Object[0]), true);
        } else if (func_180799_ab()) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation("You can't breed with " + func_70005_c_() + " as she's swimming in lava!", new Object[0]), true);
        } else if (func_70027_ad()) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation("You can't breed with " + func_70005_c_() + " as she's on fire!", new Object[0]), true);
        } else if (this.field_70134_J) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation("You can't breed with " + func_70005_c_() + " as she's too stuck in a web to do anything!", new Object[0]), true);
        } else if (!func_70089_S()) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation("You can't breed with a dead girl...", new Object[0]), true);
        } else {
          setInLove(player);
          player.func_184609_a(EnumHand.MAIN_HAND);
          itemstack.func_190918_g(1);
        }  
      return true;
    } 
    if (itemstack.func_77973_b().func_111207_a(itemstack, player, (EntityLivingBase)this, hand))
      return true; 
    if (canWearEasterEggs() && func_184191_r((Entity)player) && !itemstack.func_190926_b() && func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() && (itemstack.func_77973_b() == Items.field_151144_bL || itemstack.func_77973_b() == Items.field_151115_aP || itemstack.func_77973_b() == Items.field_151103_aS || itemstack.func_77973_b() == Item.func_150898_a(Blocks.field_185764_cQ) || itemstack.func_77973_b() == Items.field_151008_G)) {
      func_184201_a(EntityEquipmentSlot.HEAD, itemstack);
      func_184606_a_(itemstack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(itemstack.func_77978_p());
        heldItem.func_77964_b(itemstack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.HEAD, heldItem);
        itemstack.func_190918_g(1);
      } 
      return true;
    } 
    if (canWearEasterEggs() && func_184191_r((Entity)player) && player.func_70093_af() && itemstack.func_190926_b()) {
      dropEquipmentUndamaged();
      player.func_184609_a(hand);
      return true;
    } 
    if (func_184191_r((Entity)player) && !itemstack.func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && itemstack.func_77973_b() != Items.field_151057_cb && itemstack.func_77973_b() instanceof ItemFood && getEnergy() <= 100.0F) {
      func_184185_a(SoundEvents.field_187739_dZ, 1.0F, 1.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(itemstack.func_77978_p());
        heldItem.func_77964_b(itemstack.func_77952_i());
        heldItem.func_190920_e(itemstack.func_190916_E());
        func_184201_a(EntityEquipmentSlot.MAINHAND, heldItem);
        itemstack.func_190918_g(itemstack.func_190916_E());
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
  
  protected void func_82160_b(boolean wasRecentlyHit, int lootingModifier) {
    super.func_82160_b(wasRecentlyHit, lootingModifier);
    if (!getCurrentBook().func_190926_b()) {
      func_184185_a(SoundEvents.field_187694_dK, 1.0F, 1.3F + this.field_70146_Z.nextFloat() * 0.4F);
      if (!this.field_70170_p.field_72995_K)
        func_70099_a(new ItemStack(books[getBookID()], 1, getBookDurability()), 1.0F); 
      setBookID(0);
      setBookDurability(0);
    } 
    if (isMarried() && !this.field_70170_p.field_72995_K) {
      ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your wife is unconcious!", new Object[0]));
      ItemStack stack = new ItemStack((Item)EItem.weddingring);
      stack.func_77982_d(new NBTTagCompound());
      NBTTagCompound tag = serializeNBT();
      stack.func_77978_p().func_74782_a("Entity", (NBTBase)tag);
      stack.func_77978_p().func_74778_a("EntityName", func_70005_c_());
      EntityItem entityitem = new EntityItem(this.field_70170_p, (getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v, stack);
      entityitem.func_174869_p();
      this.field_70170_p.func_72838_d((Entity)entityitem);
    } 
  }
  
  public int func_70646_bf() {
    return !func_70089_S() ? 180 : 60;
  }
  
  public boolean leavesNoCorpse() {
    return (isMarried() || this.field_70170_p.func_82736_K().func_82766_b("disableCorpses"));
  }
  
  protected void func_70609_aI() {
    this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    func_70661_as().func_75499_g();
    func_70066_B();
    func_70674_bp();
    func_70624_b((EntityLivingBase)null);
    if (this.field_70122_E) {
      this.field_70725_aQ++;
      this.field_70721_aZ = 0.0F;
      this.field_184619_aG = 0.0F;
      setArmsRaised(false);
      this.prevRotationPitchFalling = this.rotationPitchFalling = 0.0F;
    } else if (getTier() == EnumTier.TIER6 || this instanceof EntityVex || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither) {
      this.field_70725_aQ++;
    } else {
      this.field_70725_aQ = 0;
    } 
    if (!this.field_70170_p.field_72995_K)
      if (this.field_70725_aQ == 2) {
        this.field_70714_bg.field_75782_a.clear();
        this.field_70715_bh.field_75782_a.clear();
        this.field_70761_aq = this.field_70177_z = this.field_70759_as;
        if (getOwner() != null)
          if (isHero()) {
            for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i)
              entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s " + func_70005_c_() + " has been killed!!!", new Object[0]), true); 
            ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("A Hero mob has fallen!", new Object[0]));
          }  
        if (!this.field_70170_p.field_72995_K && func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
          int i = func_70693_a(this.field_70717_bb);
          i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.field_70717_bb, i);
          while (i > 0) {
            int j = EntityXPOrb.func_70527_a(i);
            i -= j;
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, j));
          } 
          this.field_70728_aV = 0;
        } 
      }  
    if (this.field_70718_bc > 0)
      this.field_70718_bc++; 
    if (this.field_70725_aQ < 22 && this.field_70725_aQ > 2)
      this.field_70125_A--; 
    if (this.field_70725_aQ > 100) {
      if (this.field_70122_E)
        func_94061_f(true); 
      if (this.field_70725_aQ > 500 && !this.field_70170_p.func_180495_p(func_180425_c().func_177981_b((int)this.field_70131_O + 1)).func_185904_a().func_76220_a())
        this.field_70163_u -= 0.025D; 
    } 
    if (!this.field_70170_p.field_72995_K && this.field_70725_aQ >= (leavesNoCorpse() ? 20 : 600)) {
      func_70656_aK();
      this.field_70170_p.func_72900_e((Entity)this);
    } 
  }
  
  public void func_70645_a(DamageSource cause) {
    if (cause.func_76355_l() == "antimatter") {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() * 2.0D);
      func_70606_j(func_110138_aP());
      this.field_70725_aQ = 0;
      this.field_70729_aU = false;
      setIsAntiMob(true);
      this.field_70173_aa = 0;
      this.field_70761_aq = this.field_70177_z = this.field_70759_as = 0.0F;
      this.field_70125_A = 0.0F;
      int i = this.field_70728_aV;
      if (this instanceof EntityEvoker) {
        func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_190929_cY));
        func_184642_a(EntityEquipmentSlot.MAINHAND, 0.0F);
        func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_190929_cY));
        func_184642_a(EntityEquipmentSlot.OFFHAND, 0.0F);
      } 
      while (i > 0 && !this.field_70170_p.field_72995_K) {
        int j = EntityXPOrb.func_70527_a(i);
        i -= j;
        this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
      } 
    } else if (hasLastChance()) {
      this.lastChanceInvul = 200;
      func_70606_j(1.0F);
      func_70674_bp();
      inflictCustomStatusEffect(EnumDifficulty.PEACEFUL, (EntityLivingBase)this, MobEffects.field_188423_x, 10, 1);
      func_70604_c(null);
      func_70624_b((EntityLivingBase)null);
      setEnergy(100.0F);
      this.field_70170_p.func_72960_a((Entity)this, (byte)35);
      if (!isWild())
        func_82149_j((Entity)getOwner()); 
      setLastChance(false);
    } else {
      if (hasLimitedLife())
        func_174812_G(); 
      if (EngenderConfig.general.useMessage && !isWild() && getOwner() instanceof EntityPlayerMP && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer)) {
        if (!this.field_70170_p.field_72995_K)
          ((EntityPlayerMP)getOwner()).func_145747_a(func_110142_aN().func_151521_b()); 
        this.field_70170_p.func_184133_a((EntityPlayer)getOwner(), getOwner().func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), func_70647_i());
        ForgeHooks.onLivingDeath((EntityLivingBase)this, DamageSource.func_76358_a(getOwner()));
      } 
      dropEquipmentUndamaged();
      func_70624_b((EntityLivingBase)null);
      func_70604_c(null);
      if (this instanceof Flying) {
        float xRatio = MathHelper.func_76126_a(this.field_70759_as * 0.017453292F);
        float zRatio = -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F);
        float f = MathHelper.func_76129_c(xRatio * xRatio + zRatio * zRatio);
        this.field_70159_w -= xRatio / f * ((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() + 1.0F);
        this.field_70179_y -= zRatio / f * ((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() + 1.0F);
      } 
      if (cause.func_76346_g() instanceof EntityPlayerMP)
        CriteriaTriggers.field_192122_b.func_192211_a((EntityPlayerMP)cause.func_76346_g(), (Entity)this, cause); 
      super.func_70645_a(cause);
    } 
  }
  
  protected void func_82167_n(Entity entityIn) {
    if (func_190631_cK() && func_70643_av() == null && entityIn instanceof EntityLivingBase && !func_184191_r(entityIn))
      if (!(entityIn instanceof EntityPlayer) || (entityIn instanceof EntityPlayer && !((EntityPlayer)entityIn).field_71075_bZ.field_75102_a))
        func_70624_b((EntityLivingBase)entityIn);  
    if (entityIn instanceof EntityLivingBase && func_70089_S())
      if (!func_184223_x(entityIn))
        if (!entityIn.field_70145_X && !this.field_70145_X) {
          double d0 = entityIn.field_70165_t - this.field_70165_t;
          double d1 = entityIn.field_70161_v - this.field_70161_v;
          double d2 = MathHelper.func_76132_a(d0, d1);
          if (d2 >= 0.009999999776482582D) {
            d2 = MathHelper.func_76133_a(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0D / d2;
            if (d3 > 1.0D)
              d3 = 1.0D; 
            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05000000074505806D;
            d1 *= 0.05000000074505806D;
            d0 *= (1.0F - this.field_70144_Y);
            d1 *= (1.0F - this.field_70144_Y);
            if (!func_184207_aI() && !func_184191_r(entityIn))
              func_70024_g(-d0, 0.0D, -d1); 
            if (!entityIn.func_184207_aI() && !entityIn.func_184191_r((Entity)this))
              entityIn.func_70024_g(d0, 0.0D, d1); 
          } 
        }   
  }
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    if (this instanceof EntitySans)
      ((EntitySans)this).setAttackType(this.field_70146_Z.nextInt(6)); 
    if (!isWild()) {
      EntityPlayer player = (EntityPlayer)getOwner();
      player.func_70074_a(entityLivingIn);
      setCurrentStudy(EnumStudy.Physical, (int)entityLivingIn.func_110138_aP());
    } 
    super.func_70074_a(entityLivingIn);
    func_70661_as().func_75499_g();
    func_70661_as().func_75497_a((Entity)this, 1.0D);
    if (func_70638_az() != null && !func_70638_az().func_70089_S() && entityLivingIn == func_70638_az())
      func_70624_b((EntityLivingBase)null); 
    if (func_70638_az() != null && func_70638_az() instanceof EntityTameBase && func_184191_r((Entity)func_70638_az()) && ((EntityTameBase)func_70638_az()).getFakeHealth() <= 0.0F)
      func_70624_b((EntityLivingBase)null); 
    if (Loader.isModLoaded("abyssalcraft") && !this.field_70170_p.field_72995_K)
      if (EngenderMod.canBeTurned((Entity)entityLivingIn)) {
        if (passesDreadPlague()) {
          EntityDreadling EntityDephsZombie = new EntityDreadling(this.field_70170_p);
          EntityDephsZombie.func_82149_j((Entity)entityLivingIn);
          this.field_70170_p.func_72900_e((Entity)entityLivingIn);
          EntityDephsZombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)EntityDephsZombie)), (IEntityLivingData)null);
          EntityDephsZombie.setOwnerId(func_184753_b());
          this.field_70170_p.func_72838_d((Entity)EntityDephsZombie);
          this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
        } 
        if (passesCoraliumPlague())
          if (entityLivingIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid || entityLivingIn instanceof net.minecraft.entity.passive.EntitySquid || entityLivingIn instanceof EntityCoraliumSquid || entityLivingIn instanceof com.shinoow.abyssalcraft.common.entity.EntityCoraliumSquid) {
            EntityCoraliumSquid EntityDephsZombie = new EntityCoraliumSquid(this.field_70170_p);
            EntityDephsZombie.func_82149_j((Entity)entityLivingIn);
            this.field_70170_p.func_72900_e((Entity)entityLivingIn);
            EntityDephsZombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)EntityDephsZombie)), (IEntityLivingData)null);
            EntityDephsZombie.setOwnerId(func_184753_b());
            if (entityLivingIn.func_70631_g_())
              EntityDephsZombie.setGrowingAge(-60000); 
            this.field_70170_p.func_72838_d((Entity)EntityDephsZombie);
            this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
          } else {
            EntityAbyssalZombie EntityDephsZombie = new EntityAbyssalZombie(this.field_70170_p);
            EntityDephsZombie.func_82149_j((Entity)entityLivingIn);
            this.field_70170_p.func_72900_e((Entity)entityLivingIn);
            EntityDephsZombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)EntityDephsZombie)), (IEntityLivingData)null);
            EntityDephsZombie.setOwnerId(func_184753_b());
            if (entityLivingIn.func_70631_g_())
              EntityDephsZombie.setGrowingAge(-60000); 
            this.field_70170_p.func_72838_d((Entity)EntityDephsZombie);
            this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
          }  
      }  
  }
  
  protected void func_110159_bB() {
    if (func_110167_bD()) {
      if (!func_70089_S() || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.other.EntityPortal || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer)
        func_110160_i(true, true); 
      if (func_110166_bE() == null || (func_110166_bE()).field_70128_L)
        func_110160_i(true, true); 
    } 
    if (func_110167_bD() && func_110166_bE() != null && (func_110166_bE()).field_70170_p == this.field_70170_p) {
      Entity entity = func_110166_bE();
      func_175449_a(new BlockPos((int)entity.field_70165_t, (int)entity.field_70163_u, (int)entity.field_70161_v), 5);
      float f = func_70032_d(entity);
      func_142017_o(f);
      if (f > 3.0F)
        func_70661_as().func_75497_a(entity, 1.0D); 
      if (f > 9.0F) {
        double d0 = (entity.field_70165_t - this.field_70165_t) / f;
        double d1 = (entity.field_70163_u - this.field_70163_u) / f;
        double d2 = (entity.field_70161_v - this.field_70161_v) / f;
        this.field_70159_w += d0 * Math.abs(d0) * 0.4D;
        this.field_70181_x += d1 * Math.abs(d1) * 0.4D;
        this.field_70179_y += d2 * Math.abs(d2) * 0.4D;
      } 
    } 
  }
  
  public void spawnHeartParticle() {
    if (this.field_70170_p.field_72995_K) {
      if (func_70089_S())
        this.field_70170_p.func_175688_a(EnumParticleTypes.HEART, this.field_70165_t + this.field_70146_Z.nextGaussian(), this.field_70163_u + this.field_70131_O, this.field_70161_v + this.field_70146_Z.nextGaussian(), 0.0D, 0.0D, 0.0D, new int[0]); 
    } else {
      this.field_70170_p.func_72960_a((Entity)this, (byte)22);
    } 
  }
  
  public void spawnStressParticle() {
    if (this.field_70170_p.field_72995_K) {
      double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
      double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
      double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
      if (func_70089_S())
        this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + func_70047_e(), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d0, d1, d2, new int[0]); 
    } else {
      this.field_70170_p.func_72960_a((Entity)this, (byte)23);
    } 
  }
  
  public void func_70656_aK() {
    if (this.field_70170_p.field_72995_K) {
      for (int i = 0; i < 2 * (int)(this.field_70130_N + this.field_70131_O) + 5; i++) {
        double d0 = this.field_70146_Z.nextGaussian() * (this.field_70130_N / 2.0F);
        double d1 = this.field_70146_Z.nextDouble() * this.field_70131_O;
        double d2 = this.field_70146_Z.nextGaussian() * (this.field_70130_N / 2.0F);
        if (func_70089_S()) {
          this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + d0, this.field_70163_u + d1, this.field_70161_v + d2, this.field_70146_Z.nextGaussian() * 0.02D, this.field_70146_Z.nextGaussian() * 0.02D, this.field_70146_Z.nextGaussian() * 0.02D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_WITCH, this.field_70165_t + d0, this.field_70163_u + d1, this.field_70161_v + d2, this.field_70146_Z.nextGaussian() * 0.02D, this.field_70146_Z.nextGaussian() * 0.02D, this.field_70146_Z.nextGaussian() * 0.02D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + d0, this.field_70163_u + d1, this.field_70161_v + d2, this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), new int[0]);
        } 
      } 
    } else {
      this.field_70170_p.func_72960_a((Entity)this, (byte)20);
    } 
  }
  
  public void spawnConversionParticle() {
    if (this.field_70170_p.field_72995_K) {
      for (int i1 = 0; i1 < this.convertionInt; i1++) {
        float f1 = i1 * 3.1415927F / timesToConvert() * 0.5F;
        if (func_70089_S())
          this.field_70170_p.func_175682_a(EnumParticleTypes.END_ROD, true, this.field_70165_t + MathHelper.func_76134_b(f1) * ((this.field_70130_N > 6.0F) ? 6.0F : this.field_70130_N), this.field_70163_u + this.field_70131_O + 1.0D, this.field_70161_v + MathHelper.func_76126_a(f1) * ((this.field_70130_N > 6.0F) ? 6.0F : this.field_70130_N), this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]); 
      } 
    } else {
      this.field_70170_p.func_72960_a((Entity)this, (byte)21);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 23) {
      spawnStressParticle();
    } else if (id == 22) {
      spawnHeartParticle();
    } else if (id == 21) {
      spawnConversionParticle();
    } else {
      super.func_70103_a(id);
    } 
  }
  
  protected int func_70682_h(int air) {
    int i = EnchantmentHelper.func_185292_c((EntityLivingBase)this);
    if (isNotALivingThing())
      return air; 
    return (i > 0 && this.field_70146_Z.nextInt(i + 1) > 0) ? air : (air - 1);
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    this.field_70159_w = 0.0D;
    this.field_70179_y = 0.0D;
    this.field_70737_aN = 0;
    this.prevRotationPitchFalling = this.rotationPitchFalling = 0.0F;
    float[] ret = ForgeHooks.onLivingFall((EntityLivingBase)this, distance, damageMultiplier);
    if (ret == null)
      return; 
    distance = ret[0];
    damageMultiplier = ret[1];
    if (func_184207_aI())
      for (Entity entity : func_184188_bt())
        entity.func_180430_e(distance, damageMultiplier);  
    PotionEffect potioneffect = func_70660_b(MobEffects.field_76430_j);
    float f = (potioneffect == null) ? 0.0F : (potioneffect.func_76458_c() + 1);
    float energy = (getEnergy() <= 10.0F) ? 0.0F : (getEnergy() / 10.0F);
    int i = MathHelper.func_76123_f((distance - 3.0F - f - energy) * damageMultiplier);
    if (i > 0) {
      func_184185_a(func_184588_d(i), 1.0F, 1.0F);
      if (takesFallDamage()) {
        setEnergy(getEnergy() - i);
        func_70097_a(DamageSource.field_76379_h, i);
      } 
      int j = MathHelper.func_76128_c(this.field_70165_t);
      int k = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
      int l = MathHelper.func_76128_c(this.field_70161_v);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(j, k, l));
      if (iblockstate.func_185904_a() != Material.field_151579_a) {
        SoundType soundtype = iblockstate.func_177230_c().getSoundType(iblockstate, this.field_70170_p, new BlockPos(j, k, l), (Entity)this);
        func_184185_a(soundtype.func_185842_g(), soundtype.func_185843_a() * 0.5F, soundtype.func_185847_b() * 0.75F);
        func_184185_a(soundtype.func_185842_g(), soundtype.func_185843_a() * 0.5F, soundtype.func_185847_b() * 0.75F);
        func_184185_a(soundtype.func_185842_g(), soundtype.func_185843_a() * 0.5F, soundtype.func_185847_b() * 0.75F);
      } 
    } 
    this.field_70122_E = true;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (func_70643_av() != null && !func_70643_av().func_70089_S())
      func_70604_c(null); 
    if (!this.field_70170_p.field_72995_K && func_70643_av() == null && func_70638_az() == null)
      if (Maths.chance(75)) {
        List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e()), Predicates.and(new Predicate<Entity>() {
                public boolean apply(@Nullable Entity p_apply_1_) {
                  return (p_apply_1_.func_70089_S() && !EntityTameBase.this.func_184191_r(p_apply_1_));
                }
              },  EntitySelectors.field_188444_d));
        for (int j2 = 0; j2 < 10 && !list1.isEmpty(); j2++) {
          EntityLivingBase entitylivingbase = list1.get(this.field_70146_Z.nextInt(list1.size()));
          if (entitylivingbase != this) {
            if ((entitylivingbase instanceof EntityPlayer && !((EntityPlayer)entitylivingbase).func_184812_l_() && !((EntityPlayer)entitylivingbase).func_175149_v()) || !(entitylivingbase instanceof EntityPlayer)) {
              func_70624_b(entitylivingbase);
              break;
            } 
          } else {
            list1.remove(entitylivingbase);
          } 
        } 
      }  
    if (!this.field_70170_p.field_72995_K && Loader.isModLoaded("mutantbeasts"))
      for (SkullSpiritEntity mutation : this.field_70170_p.func_72872_a(SkullSpiritEntity.class, func_174813_aQ().func_186662_g(0.2D))) {
        mutation.func_70106_y();
        this.mutationTimer = 1;
      }  
    if (!isAMutant() && !this.field_70170_p.field_72995_K && this.mutationTimer > 0 && !isABoss()) {
      this.mutationTimer++;
      this.field_70159_w = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
      this.field_70179_y = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F);
      if (this.mutationTimer >= 120) {
        this.mutationTimer = -1;
        this.field_70170_p.func_180498_a((EntityPlayer)null, 3000, func_180425_c(), 0);
        InactMutation();
      } 
    } 
    if (!isWild() && this == getOwner().func_110144_aD())
      getOwner().func_130011_c(null); 
    if (!isABoss() && !isHero() && func_184753_b() == null && !this.field_70170_p.field_72995_K && this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL)
      func_70106_y(); 
    if (getCurrentBook() != ItemStack.field_190927_a && getBookDurability() >= getCurrentBook().func_77958_k()) {
      setBookID(0);
      setBookDurability(0);
    } 
    if (func_70638_az() != null && !func_70638_az().func_70089_S())
      func_70624_b((EntityLivingBase)null); 
    if (getGhostTime() > 0)
      setGhostTime(getGhostTime() - 1); 
    if (getIllusionFormTime() > 0)
      setIllusionFormTime(getIllusionFormTime() - 1); 
    if (getPolymorphTime() > 0)
      setPolymorphTime(getPolymorphTime() - 1); 
    if (this.polymorpherData != null && (getPolymorphTime() <= 0 || func_110143_aJ() <= 0.0F) && !(this instanceof EntityEvoker)) {
      EntityEvoker entityvex = new EntityEvoker(this.field_70170_p);
      entityvex.func_82149_j((Entity)this);
      entityvex.func_184185_a(ESound.bugSpecial, 10.0F, 0.5F);
      entityvex.func_184185_a(ESound.blast, 10.0F, 1.0F);
      entityvex.func_70656_aK();
      entityvex.func_70037_a(this.polymorpherData);
      entityvex.func_70014_b(this.polymorpherData);
      entityvex.setOwnerId(func_184753_b());
      entityvex.setIsHero(isHero());
      entityvex.setLastChance(hasLastChance());
      entityvex.setPolymorphTime((func_110143_aJ() <= 0.0F) ? 2000 : 600);
      entityvex.exptobeadded = this.exptobeadded;
      this.field_70170_p.func_72838_d((Entity)entityvex);
      this.field_70170_p.func_72900_e((Entity)this);
    } 
    if (getLimitedLife() > 0) {
      this.limitedLifespan = true;
      for (int i = 0; i < this.field_70130_N + this.field_70131_O; i++) {
        if (this.field_70170_p.field_72995_K) {
          double d4 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d5 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d6 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d7 = 10.0D;
          this.field_70170_p.func_175688_a(EnumParticleTypes.CRIT, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N - d4 * d7, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O) - d5 * d7, this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N - d6 * d7, 0.0D, 0.0D, 0.0D, new int[0]);
        } 
      } 
    } 
    if (hasLimitedLife()) {
      setLimitedLife(getLimitedLife() - 1);
      if (func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() <= 4.0D) {
        if (!this.field_70714_bg.field_75782_a.isEmpty())
          this.field_70714_bg.field_75782_a.clear(); 
        func_70656_aK();
      } 
      if (getLimitedLife() <= 0) {
        func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() - 1.0D);
        if (func_110143_aJ() > func_110138_aP())
          func_70606_j(func_110138_aP()); 
        if (func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() < 1.0D)
          func_174812_G(); 
        setLimitedLife(5);
      } 
    } 
    func_174805_g(false);
    this.rideCooldownCounter++;
    if (!func_70089_S() || (func_70638_az() != null && !func_70638_az().func_70089_S()) || (func_70638_az() != null && func_70638_az() instanceof EntityTameBase && getFakeHealth() <= 0.0F && ((EntityTameBase)func_70638_az()).getFakeHealth() <= 0.0F && func_184191_r((Entity)func_70638_az())))
      func_70624_b((EntityLivingBase)null); 
    this.currentReadingBook = (getBookID() != 0) ? new ItemStack(books[getBookID()], 1, getBookDurability()) : ItemStack.field_190927_a;
    this.basicInventory.func_70299_a(0, func_184582_a(EntityEquipmentSlot.HEAD));
    this.basicInventory.func_70299_a(1, func_184582_a(EntityEquipmentSlot.CHEST));
    this.basicInventory.func_70299_a(2, func_184582_a(EntityEquipmentSlot.LEGS));
    this.basicInventory.func_70299_a(3, func_184582_a(EntityEquipmentSlot.FEET));
    this.basicInventory.func_70299_a(4, func_184582_a(EntityEquipmentSlot.MAINHAND));
    this.basicInventory.func_70299_a(5, func_184582_a(EntityEquipmentSlot.OFFHAND));
    this.basicInventory.func_70299_a(6, getCurrentBook());
    if (getTier() == EnumTier.TIER6) {
      setLevel(300);
      setTotalEXP(2.1474836E9F);
      setEXP(2.1474836E9F);
    } 
    if (this.field_70143_R >= func_110143_aJ() * 2.0F && func_70089_S() && takesFallDamage() && this.field_70163_u <= -45.0D)
      func_70097_a(DamageSource.field_76379_h, Float.MAX_VALUE); 
    updateBossBar();
    if (!isABoss() && (!func_184218_aH() || (func_184218_aH() && func_184187_bx() instanceof net.minecraft.entity.projectile.EntityThrowable)) && (!(this instanceof Flying) || !func_70089_S()) && !this.field_70122_E && (this.field_70737_aN > 0 || func_184218_aH() || !func_70089_S())) {
      if (func_70090_H() || func_180799_ab()) {
        this.field_70181_x -= 0.10000000149011612D;
      } else {
        this.field_70737_aN = 10;
      } 
      this.field_70134_J = false;
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
      func_70661_as().func_75499_g();
      if (func_184218_aH()) {
        for (this.rotationPitchFalling = (float)(-(func_184187_bx()).field_70181_x * 114.59155902616465D); this.rotationPitchFalling - this.prevRotationPitchFalling < -180.0F; this.prevRotationPitchFalling -= 360.0F);
      } else {
        for (this.rotationPitchFalling = (float)(-this.field_70181_x * 114.59155902616465D); this.rotationPitchFalling - this.prevRotationPitchFalling < -180.0F; this.prevRotationPitchFalling -= 360.0F);
      } 
      while (this.rotationPitchFalling - this.prevRotationPitchFalling >= 180.0F)
        this.prevRotationPitchFalling += 360.0F; 
      this.rotationPitchFalling = this.prevRotationPitchFalling + (this.rotationPitchFalling - this.prevRotationPitchFalling) * (float)((this.field_70181_x == 0.0D) ? 0.01D : ((this.field_70181_x < 0.0D) ? (-this.field_70181_x / 5.0D) : (this.field_70181_x / 5.0D)));
      if (this.rotationPitchFalling >= 90.0F)
        this.rotationPitchFalling = 90.0F; 
      if (this.rotationPitchFalling <= -90.0F)
        this.rotationPitchFalling = -90.0F; 
      if (this.rotationPitchFalling < 90.0F && this.rotationPitchFalling > -90.0F && !this.field_70122_E && this.field_70759_as != (float)MathHelper.func_181159_b(this.field_70179_y, this.field_70159_w) * 57.295776F - 90.0F) {
        this.field_70760_ar = this.field_70126_B = this.field_70758_at = this.field_70761_aq = this.field_70177_z = this.field_70759_as = (float)MathHelper.func_181159_b(this.field_70179_y, this.field_70159_w) * 57.295776F - 90.0F;
        float xRatio = MathHelper.func_76126_a(this.field_70759_as * 0.017453292F);
        float zRatio = -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F);
        float f1 = MathHelper.func_76129_c(xRatio * xRatio + zRatio * zRatio);
      } 
    } else {
      this.prevRotationPitchFalling = this.rotationPitchFalling = 0.0F;
    } 
    this.prevChasingPosX = this.chasingPosX;
    this.prevChasingPosY = this.chasingPosY;
    this.prevChasingPosZ = this.chasingPosZ;
    double d0 = this.field_70165_t - this.chasingPosX;
    double d1 = this.field_70163_u - this.chasingPosY;
    double d2 = this.field_70161_v - this.chasingPosZ;
    double d3 = 10.0D;
    if (d0 > d3) {
      this.chasingPosX = this.field_70165_t;
      this.prevChasingPosX = this.chasingPosX;
    } 
    if (d2 > d3) {
      this.chasingPosZ = this.field_70161_v;
      this.prevChasingPosZ = this.chasingPosZ;
    } 
    if (d1 > d3) {
      this.chasingPosY = this.field_70163_u;
      this.prevChasingPosY = this.chasingPosY;
    } 
    if (d0 < -d3) {
      this.chasingPosX = this.field_70165_t;
      this.prevChasingPosX = this.chasingPosX;
    } 
    if (d2 < -d3) {
      this.chasingPosZ = this.field_70161_v;
      this.prevChasingPosZ = this.chasingPosZ;
    } 
    if (d1 < -d3) {
      this.chasingPosY = this.field_70163_u;
      this.prevChasingPosY = this.chasingPosY;
    } 
    this.chasingPosX += d0 * 0.25D;
    this.chasingPosZ += d2 * 0.25D;
    this.chasingPosY += d1 * 0.25D;
    if (!func_70089_S() && this.field_70163_u <= -200.0D)
      func_70106_y(); 
    if (func_184207_aI())
      for (Entity entity : func_184188_bt())
        entity.field_70181_x = this.field_70181_x;  
    if (!func_70089_S()) {
      func_70624_b((EntityLivingBase)null);
      func_70604_c(null);
      func_184210_p();
    } 
    this.field_70160_al = !this.field_70122_E;
    if (this.blockTimer > 0)
      this.blockTimer--; 
    if (this.convertionInt > 0 && !isWild())
      this.convertionInt = 0; 
    if (this.field_70173_aa % 300 == 0 && !func_184218_aH() && !func_184207_aI() && func_70638_az() == null && !func_70661_as().func_75500_f() && this.field_70122_E && this.field_70159_w == 0.0D && this.field_70179_y == 0.0D)
      setSitResting(true); 
    if (isSitResting())
      func_70661_as().func_75499_g(); 
    if (func_184218_aH() || func_184207_aI() || func_70638_az() != null || !func_70661_as().func_75500_f() || !this.field_70122_E || this.field_70159_w != 0.0D || this.field_70181_x != 0.0D || this.field_70179_y != 0.0D)
      setSitResting(false); 
    if (this.field_70173_aa > 20 && getCurrentBook().func_190926_b()) {
      setBookID(0);
      setBookDurability(0);
    } 
    if (func_70638_az() == null && !getCurrentBook().func_190926_b() && getCurrentBook().func_77973_b() instanceof ItemLearningBook) {
      setSitResting(true);
      this.field_70125_A = (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker || this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian) ? 0.0F : 30.0F;
      this.field_70759_as = this.field_70177_z = this.field_70761_aq;
      if (this.field_70173_aa % ((isHero() ? 40 : 80) - (int)func_110148_a(INTELLIGENCE).func_111125_b() / 5) == 0) {
        this.flipT++;
        int randomRead = this.field_70146_Z.nextInt((isHero() ? 40 : 80) - (int)func_110148_a(INTELLIGENCE).func_111125_b() / 5);
        if (getCurrentBook().func_77973_b() instanceof ItemLearningBook && randomRead == 1)
          learnLevelUp((ItemLearningBook)getCurrentBook().func_77973_b()); 
        if (!getCurrentBook().func_190926_b() && !func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_77973_b() instanceof ItemLearningBook)
          func_184201_a(EntityEquipmentSlot.OFFHAND, ItemStack.field_190927_a); 
      } 
    } 
    if (func_70638_az() != null && !getCurrentBook().func_190926_b() && getCurrentBook().func_77973_b() instanceof ItemLearningBook) {
      func_70099_a(new ItemStack(getCurrentBook().func_77973_b(), 1, getCurrentBook().func_77952_i()), func_70047_e());
      setCurrentBook(ItemStack.field_190927_a);
      setBookID(0);
      setBookDurability(0);
      func_184185_a(SoundEvents.field_187924_gx, 1.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.4F);
    } 
    if (!this.field_70170_p.field_72995_K && !func_184614_ca().func_190926_b() && func_184614_ca().func_77973_b() == Items.field_151054_z) {
      func_70099_a(new ItemStack(Items.field_151054_z), func_70047_e());
      func_184611_a(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
      func_184185_a(SoundEvents.field_187924_gx, 1.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.4F);
    } 
    if (!getCurrentBook().func_190926_b())
      setBookDurability(getCurrentBook().func_77952_i()); 
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(getKnockbackResistance());
    if (!isWild() && getOwner() instanceof EntityPlayer && func_70089_S() && 
      getTier() != EnumTier.TIER6 && getEXP() >= (getNextLevelRequirement() * getLevel()))
      levelUp(); 
    if (getEnergy() > 100.0F) {
      if (EngenderConfig.mobs.regeneration)
        func_70691_i(getEnergy() - 100.0F); 
      setEnergy(100.0F);
    } 
    if (getEnergy() <= 0.0F) {
      setEnergy(0.0F);
      if (func_184207_aI())
        func_184210_p(); 
    } 
    if (!this.field_70170_p.field_72995_K)
      if (!(this instanceof EntityEnderman) && !(this instanceof EntitySans) && (isNotALivingThing() || isABoss() || getTier() == EnumTier.TIER6 || isAMutant())) {
        setEnergy(100.0F);
      } else {
        if (func_70644_a(MobEffects.field_76438_s) && this.field_70173_aa % 10 == 0)
          setEnergy(getEnergy() - 1.0F * func_70660_b(MobEffects.field_76438_s).func_76458_c()); 
        if (func_70644_a(MobEffects.field_76428_l) && getEnergy() < 100.0F && this.field_70173_aa % 10 == 0)
          setEnergy(getEnergy() + 1.0F * func_70660_b(MobEffects.field_76428_l).func_76458_c()); 
        if (func_70051_ag() && this.field_70173_aa % 20 == 0)
          setEnergy(getEnergy() - 1.0F); 
        if (this.field_70159_w == 0.0D && this.field_70181_x == 0.0D && this.field_70179_y == 0.0D && getEnergy() < 100.0F && this.field_70173_aa % 40 == 0)
          setEnergy(getEnergy() + 1.0F); 
      }  
    if (func_184613_cA() && this.field_70173_aa % 20 == 0 && !this.field_70170_p.field_72995_K)
      setCurrentStudy(EnumStudy.Physical, 3); 
    if (func_184207_aI() && this.field_70173_aa % 60 == 0 && !this.field_70170_p.field_72995_K)
      setCurrentStudy(EnumStudy.Physical, 1); 
    if (func_70638_az() != null && (func_70638_az() == this || !func_70638_az().func_70089_S()))
      func_70624_b((EntityLivingBase)null); 
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
      this.field_70172_ad = this.lastChanceInvul;
      this.lastChanceInvul--;
    } 
    if (getEnergy() <= 5.0F)
      spawnStressParticle(); 
    if (getFakeHealth() <= 0.0F)
      setFakeHealth(0.0F); 
    if (func_175446_cd())
      this.field_184619_aG = 0.0F; 
    if (!func_70089_S())
      func_70674_bp(); 
    if (isHero())
      func_70066_B(); 
    if (!isSitResting() || !isWild() || isHero() || !func_184222_aU() || getTier() == EnumTier.TIER6 || this instanceof IEntityMultiPart)
      this.field_70708_bq = 0; 
    if (func_184614_ca().func_77973_b() == EItem.carrier || func_184592_cb().func_77973_b() == EItem.carrier)
      dropEquipmentUndamaged(); 
    if (this.field_70173_aa > 20 && func_184187_bx() != null && func_184187_bx() instanceof EntityPlayer && !(this instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime) && !func_70631_g_())
      func_184210_p(); 
    if (getLevel() >= 299 && getTier() != EnumTier.TIER6 && this.field_70146_Z.nextInt(5) == 0)
      this.field_70170_p.func_175688_a(EnumParticleTypes.END_ROD, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F - this.field_70130_N) * 0.6D, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F - this.field_70130_N) * 0.6D, 0.0D, 0.01D, 0.0D, new int[0]); 
    List<EntityTameBase> list = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_186662_g(0.1D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
    if (!this.field_70170_p.field_72995_K && list != null && !list.isEmpty() && isAntiMob())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityTameBase entity = list.get(i1);
        if (entity != null && entity.getClass() == getClass() && !entity.isAntiMob()) {
          createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 5.0F * (this.field_70130_N + this.field_70131_O) + list.size(), EngenderConfig.mobs.grief);
          entity.func_174812_G();
          func_174812_G();
        } 
      }  
    if (func_110143_aJ() > 0.0F) {
      if (this.field_70725_aQ > 0)
        this.field_70725_aQ--; 
      this.field_70729_aU = false;
    } 
    this.pageFlipPrev = this.pageFlip;
    this.bookSpreadPrev = this.bookSpread;
    if (getBookID() > 0 && func_70089_S()) {
      this.bookSpread += 0.05F;
      if (this.bookSpread == 0.1F)
        func_184185_a(SoundEvents.field_187694_dK, 1.0F, 1.3F + this.field_70146_Z.nextFloat() * 0.4F); 
    } else {
      this.bookSpread -= 0.1F;
    } 
    this.bookSpread = MathHelper.func_76131_a(this.bookSpread, 0.0F, 1.0F);
    this.pageFlipPrev = this.pageFlip;
    float f = (this.flipT - this.pageFlip) * 0.4F;
    f = MathHelper.func_76131_a(f, -0.2F, 0.2F);
    this.flipA += (f - this.flipA) * 0.9F;
    this.pageFlip += this.flipA;
    if (EngenderConfig.mobs.hunger && !this.field_70170_p.field_72995_K && func_70089_S() && getEnergy() <= 0.0F && this.field_70173_aa % 100 == 0)
      func_70097_a(DamageSource.field_76366_f, 2.0F); 
    if (EngenderConfig.mobs.regeneration) {
      if (!this.field_70170_p.field_72995_K && func_70089_S() && getEnergy() == 100.0F && this.field_70173_aa % 60 == 0)
        func_70691_i(1.0F); 
      if (!this.field_70170_p.field_72995_K && func_70089_S() && getEnergy() <= 90.0F && getEnergy() > 80.0F && func_110143_aJ() < func_110138_aP() && this.field_70172_ad <= 10 && this.field_70173_aa % 40 == 0) {
        func_70691_i(1.0F);
        setEnergy(getEnergy() - (float)(0.5009999871253967D - func_110148_a(STAMINA).func_111125_b() / 50.0D));
      } 
      if (!this.field_70170_p.field_72995_K && !isABoss() && !isNotALivingThing() && func_70089_S() && getEnergy() < 100.0F && getEnergy() > 90.0F && func_110143_aJ() < func_110138_aP() && this.field_70172_ad <= 10 && this.field_70173_aa % 10 == 0) {
        func_70691_i(2.0F);
        setEnergy(getEnergy() - (float)(5.050000190734863D - func_110148_a(STAMINA).func_111125_b() / 20.0D));
      } 
    } 
    func_70095_a((getOwner() != null && func_70089_S() && (getOwner().func_70093_af() || (this.inLove > 0 && this.field_70173_aa % 5 == 0 && func_70068_e((Entity)getOwner()) < 2.0D))));
    if (Loader.isModLoaded("abyssalcraft") && isEntityImmuneToAntiMatter() && func_70644_a(AbyssalCraftAPI.antimatter_potion)) {
      func_184596_c(MobEffects.field_76421_d);
      func_184596_c(MobEffects.field_76440_q);
      func_184596_c(MobEffects.field_76437_t);
      func_184596_c(MobEffects.field_76438_s);
      func_184596_c(AbyssalCraftAPI.antimatter_potion);
    } 
    if (isEntityImmuneToDarkness())
      func_184596_c(MobEffects.field_76440_q); 
    if (func_70644_a(MobEffects.field_76426_n) || func_70045_F())
      func_70066_B(); 
    if (func_70644_a(MobEffects.field_76426_n) || func_70045_F() || getIntelligence() <= 6.0F) {
      func_184644_a(PathNodeType.LAVA, 0.0F);
      func_184644_a(PathNodeType.DANGER_FIRE, 0.0F);
      func_184644_a(PathNodeType.DAMAGE_FIRE, 0.0F);
    } else {
      func_184644_a(PathNodeType.LAVA, -1.0F);
      func_184644_a(PathNodeType.DANGER_FIRE, 8.0F);
      func_184644_a(PathNodeType.DAMAGE_FIRE, 16.0F);
    } 
    if (!func_70089_S() && func_184207_aI())
      for (Entity entity : func_184188_bt()) {
        if (entity.func_184218_aH())
          entity.func_184210_p(); 
      }  
    if (func_175446_cd()) {
      this.field_70172_ad = this.field_70771_an;
      if (this.field_70173_aa > 21)
        this.field_70173_aa--; 
    } 
    func_174810_b(func_175446_cd());
    func_94061_f((this.field_70725_aQ > 40 || (isABoss() && !func_70089_S()) || (func_184753_b() != null && getOwner() == null)));
    this.prevRotationPitchFalling = this.rotationPitchFalling;
  }
  
  public boolean isAMutant() {
    return false;
  }
  
  public boolean func_70067_L() {
    return (!this.field_70128_L && func_70089_S() && !func_175446_cd());
  }
  
  public void func_70690_d(PotionEffect potioneffectIn) {
    if ((!isABoss() && !isAMutant() && !isHero()) || !potioneffectIn.func_188419_a().func_76398_f())
      super.func_70690_d(potioneffectIn); 
  }
  
  public void func_70636_d() {
    if (this.field_70173_aa % 10 == 0)
      if (this.colorCycleTime < this.rainbow.length) {
        this.colorCycleTime++;
      } else {
        this.colorCycleTime = 0;
      }  
    if (this.field_70173_aa % 50 == 0 && isAMutant())
      func_70691_i(2.0F); 
    if (this.convertionInt > 0) {
      func_70624_b((EntityLivingBase)null);
      this.field_70759_as = this.field_70177_z = this.field_70761_aq = (this.field_70173_aa * 5);
      this.convertionDelay--;
      if (this.convertionDelay <= 0) {
        this.convertionDelay = 40;
        this.convertionInt--;
      } 
    } 
    if (getFakeHealth() > 0.0F)
      this.field_70143_R *= 0.0F; 
    if (getGhostTime() > 0 && !isABoss()) {
      if (getGhostTime() <= 1 || this.field_70172_ad == 10 || !func_70089_S()) {
        for (int k = 0; k < 4; k++) {
          this.renderLocations[0][k] = this.renderLocations[1][k];
          this.renderLocations[1][k] = new Vec3d(0.0D, 0.0D, 0.0D);
        } 
      } else if (this.field_70172_ad == 1 || this.moralRaisedTimer == 1) {
        for (int j = 0; j < 4; j++) {
          float f1 = 1.0F + ((this.field_70130_N < 1.0F) ? 1.0F : this.field_70130_N) * 0.5F;
          float f11 = 1.0F + ((this.field_70130_N < 1.0F) ? 1.0F : this.field_70130_N) * 1.0F;
          this.renderLocations[0][j] = this.renderLocations[1][j];
          this.renderLocations[1][j] = new Vec3d((this.field_70146_Z.nextFloat() * f11 - f1), Math.max(0, this.field_70146_Z.nextInt(2)), (this.field_70146_Z.nextFloat() * f11 - f1));
        } 
        func_70656_aK();
        this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_193788_dg, func_184176_by(), 1.0F, 1.0F, false);
      } 
      if (getGhostTime() == 1) {
        for (int k = 0; k < 4; k++) {
          this.renderLocations[0][k] = this.renderLocations[1][k];
          this.renderLocations[1][k] = new Vec3d(0.0D, 0.0D, 0.0D);
        } 
        func_70656_aK();
        this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_193788_dg, func_184176_by(), 1.0F, 1.0F, false);
      } 
    } 
    this.field_70160_al = !this.field_70122_E;
    if (func_70090_H() && !func_70648_aU() && this.field_70173_aa % 20 == 0)
      setEnergy(getEnergy() - (float)(1.0099999904632568D - func_110148_a(STAMINA).func_111125_b() / 100.0D)); 
    if (this.field_70181_x > 0.0D)
      this.field_70143_R = 0.0F; 
    if (getJukeboxToDanceTo() != null && this.field_70173_aa % 10 == 0 && func_70089_S())
      this.field_70170_p.func_175688_a(EnumParticleTypes.NOTE, this.field_70165_t, this.field_70163_u + this.field_70131_O, this.field_70161_v, this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), this.field_70146_Z.nextDouble(), new int[0]); 
    if (this.convertionInt > 0 && this.field_70173_aa % 10 == 0)
      this.field_70170_p.func_72960_a((Entity)this, (byte)21); 
    if (this.convertionInt > 0)
      func_70661_as().func_75499_g(); 
    if (func_70638_az() != null && func_70638_az() instanceof EntityPlayer && ((EntityPlayer)func_70638_az()).field_71075_bZ.field_75102_a)
      func_70624_b((EntityLivingBase)null); 
    if (Loader.isModLoaded("iceandfire")) {
      List<?> list = this.field_70170_p.field_72996_f;
      if (!list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          Entity entity = (Entity)list.get(i1);
          if (entity.func_70089_S() && entity instanceof EntityMob) {
            EntityMob mob = (EntityMob)entity;
            if (mob.field_70130_N == 0.8F && mob.field_70131_O == 1.99F && mob.func_70638_az() != null)
              mob.func_70624_b((EntityLivingBase)mob); 
          } 
        }  
    } 
    if (func_184207_aI() && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid) && !(this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian)) {
      if (func_70090_H() || func_180799_ab())
        this.field_70181_x += 0.05D; 
      this.field_70703_bu = false;
    } 
    IAttributeInstance iattributeinstance = func_110148_a(SharedMonsterAttributes.field_111263_d);
    IAttributeInstance iattributeinstanceattack = func_110148_a(SharedMonsterAttributes.field_111264_e);
    if (this.moralRaisedTimer >= 600) {
      this.moralRaisedTimer = 600;
      byte b0 = 20;
      func_70690_d(new PotionEffect(MobEffects.field_76422_e, 20 * b0));
      func_184185_a(ESound.battlecry, 10.0F, 1.0F);
      if (this.field_70122_E)
        func_70664_aZ(); 
      func_184185_a(func_184639_G(), func_70599_aP(), func_70647_i() + this.field_70146_Z.nextFloat() * 0.15F);
      func_184185_a(func_184639_G(), func_70599_aP(), func_70647_i() + this.field_70146_Z.nextFloat() * 0.25F);
      func_184185_a(func_184639_G(), func_70599_aP(), func_70647_i() + this.field_70146_Z.nextFloat() * 0.35F);
      func_184185_a(func_184601_bQ(null), func_70599_aP(), func_70647_i() + this.field_70146_Z.nextFloat() * 0.35F);
    } 
    if (this.moralRaisedTimer <= 0) {
      if (iattributeinstance.func_180374_a(attackingSpeedBoostModifier))
        iattributeinstance.func_111124_b(attackingSpeedBoostModifier); 
      if (iattributeinstanceattack.func_180374_a(attackingBoostModifier))
        iattributeinstanceattack.func_111124_b(attackingBoostModifier); 
    } else {
      if (!iattributeinstance.func_180374_a(attackingSpeedBoostModifier))
        iattributeinstance.func_111121_a(attackingSpeedBoostModifier); 
      if (!iattributeinstanceattack.func_180374_a(attackingBoostModifier))
        iattributeinstanceattack.func_111121_a(attackingBoostModifier); 
    } 
    if (func_70638_az() != null)
      if (func_70661_as() instanceof PathNavigateGround && func_70032_d((Entity)func_70638_az()) > func_110148_a(SharedMonsterAttributes.field_111265_b).func_111125_b()) {
        func_70605_aq().func_188488_a(getFittness(), 0.0F);
        func_70625_a((Entity)func_70638_az(), func_184649_cE(), func_70646_bf());
      }  
    if (!func_70051_ag() && getOwner() != null && getEnergy() > 20.0F && func_70089_S() && (getOwner().func_70051_ag() || (func_70638_az() != null && this.field_191988_bg > 0.0F && (func_70638_az().func_110143_aJ() <= 4.0F || func_70032_d((Entity)func_70638_az()) > func_110148_a(SharedMonsterAttributes.field_111265_b).func_111125_b() * 1.5D)))) {
      func_70031_b(true);
    } else {
      func_70031_b(false);
    } 
    if (func_70644_a(MobEffects.field_76440_q) || (func_70644_a(MobEffects.field_76431_k) && func_70638_az() != null && func_70032_d((Entity)func_70638_az()) > this.reachWidth))
      func_70624_b((EntityLivingBase)null); 
    if (func_70644_a(MobEffects.field_76431_k)) {
      this.field_70759_as += MathHelper.func_76126_a(this.field_70173_aa * 0.2F) * 10.0F;
      this.field_70125_A += MathHelper.func_76134_b(this.field_70173_aa * 0.1F) * 10.0F;
      if (this.field_70173_aa % 10 == 0)
        func_70661_as().func_75499_g(); 
    } 
    if (this.inLove > 0) {
      this.inLove--;
      if (this.inLove % 10 == 0)
        for (int ai = 0; ai < 7; ai++)
          spawnHeartParticle();  
    } 
    if (func_184614_ca().func_77973_b() instanceof ItemFood && (getEnergy() < 90.0F || func_110143_aJ() <= func_110138_aP() / 2.0F)) {
      if (this.field_70173_aa > 53)
        this.field_70173_aa = 20; 
      func_184609_a(EnumHand.MAIN_HAND);
      func_184598_c(EnumHand.MAIN_HAND);
      if (this.field_70173_aa % 2 == 0) {
        this.field_70125_A = 40.0F;
      } else {
        this.field_70125_A = 0.0F;
      } 
      if (this.field_70173_aa == 50) {
        for (int ai = 0; ai < ((ItemFood)func_184614_ca().func_77973_b()).func_150905_g(func_184614_ca()); ai++)
          spawnHeartParticle(); 
        func_70691_i(((ItemFood)func_184614_ca().func_77973_b()).func_150905_g(func_184614_ca()));
        setEnergy(getEnergy() + (((ItemFood)func_184614_ca().func_77973_b()).func_150905_g(func_184614_ca()) * 5));
        func_184185_a(SoundEvents.field_187739_dZ, 0.5F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
      } 
    } 
    if (func_184592_cb().func_77973_b() instanceof ItemFood && (getEnergy() < 90.0F || func_110143_aJ() <= func_110138_aP() / 2.0F)) {
      if (this.field_70173_aa > 53)
        this.field_70173_aa = 20; 
      if (this.field_70173_aa % 2 == 0) {
        this.field_70125_A = 40.0F;
      } else {
        this.field_70125_A = 0.0F;
      } 
      func_184609_a(EnumHand.OFF_HAND);
      func_184598_c(EnumHand.OFF_HAND);
      if (this.field_70173_aa == 50) {
        spawnHeartParticle();
        func_70691_i(((ItemFood)func_184592_cb().func_77973_b()).func_150905_g(func_184592_cb()));
        setEnergy(getEnergy() + (((ItemFood)func_184592_cb().func_77973_b()).func_150905_g(func_184592_cb()) * 5));
        func_184185_a(SoundEvents.field_187739_dZ, 0.5F, this.field_70170_p.field_73012_v.nextFloat() * 0.1F + 0.9F);
      } 
    } 
    if (func_184187_bx() != null && func_184187_bx() instanceof EntityBoat && ((EntityBoat)func_184187_bx()).func_184179_bs() == this) {
      ((EntityBoat)func_184187_bx()).field_70177_z = this.field_70177_z;
      float f1 = 0.0F;
      if (this.field_191988_bg > 0.0F || func_70638_az() != null)
        f1 += 0.04F; 
      if (this.field_191988_bg < 0.0F)
        f1 -= 0.005F; 
      ((EntityBoat)func_184187_bx()).field_70159_w += (MathHelper.func_76126_a(-((EntityBoat)func_184187_bx()).field_70177_z * 0.017453292F) * f1);
      ((EntityBoat)func_184187_bx()).field_70179_y += (MathHelper.func_76134_b(((EntityBoat)func_184187_bx()).field_70177_z * 0.017453292F) * f1);
    } 
    if (getJukeboxToDanceTo() != null) {
      if (this.field_70173_aa % 20 == 0 && this instanceof EntityCreeper && ((EntityCreeper)this).getPowered())
        this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, this.field_70165_t - 0.5D, this.field_70163_u + 1.9D, this.field_70161_v - 0.5D, true)); 
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
      setSitResting(false);
      func_70661_as().func_75499_g();
      IBlockState iblockstate = this.field_70170_p.func_180495_p(getJukeboxToDanceTo());
      Block block = iblockstate.func_177230_c();
      if (block != Blocks.field_150421_aI || (block == Blocks.field_150421_aI && !((Boolean)iblockstate.func_177229_b((IProperty)BlockJukebox.field_176432_a)).booleanValue()) || func_174831_c(this.jukeBoxToDanceTo) > 10000.0D)
        setJukeboxToDanceTo((BlockPos)null); 
    } 
    if (func_70638_az() == null && getJukeboxToDanceTo() == null && (this.field_70173_aa % 40 == 0 || this.field_70173_aa < 5)) {
      int i11 = MathHelper.func_76128_c(this.field_70163_u);
      int l1 = MathHelper.func_76128_c(this.field_70165_t);
      int i2 = MathHelper.func_76128_c(this.field_70161_v);
      for (int k2 = -8 - (int)this.field_70130_N; k2 <= 8 + (int)this.field_70130_N; k2++) {
        for (int l2 = -8 - (int)this.field_70130_N; l2 <= 8 + (int)this.field_70130_N; l2++) {
          for (int j = -8 - (int)this.field_70131_O; j <= 8 + (int)this.field_70131_O; j++) {
            int i3 = l1 + k2;
            int k = i11 + j;
            int l = i2 + l2;
            BlockPos blockpos = new BlockPos(i3, k, l);
            IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            if (block == Blocks.field_150421_aI && ((Boolean)iblockstate.func_177229_b((IProperty)BlockJukebox.field_176432_a)).booleanValue())
              setJukeboxToDanceTo(blockpos); 
          } 
        } 
      } 
    } 
    if (func_70631_g_() && func_184218_aH() && func_184187_bx() instanceof EntityPlayer)
      if (((EntityPlayer)func_184187_bx()).func_184187_bx() != null && ((EntityPlayer)func_184187_bx()).func_184187_bx() instanceof EntityLivingBase) {
        this.field_70761_aq = this.field_70177_z = ((EntityLivingBase)((EntityPlayer)func_184187_bx()).func_184187_bx()).field_70177_z;
        this.field_70759_as = ((EntityLivingBase)((EntityPlayer)func_184187_bx()).func_184187_bx()).field_70759_as;
      } else {
        this.field_70761_aq = this.field_70177_z = ((EntityPlayer)func_184187_bx()).field_70177_z;
        this.field_70759_as = ((EntityPlayer)func_184187_bx()).field_70759_as;
      }  
    if (this.field_70173_aa > getSpawnTimer()) {
      super.func_70636_d();
      if (this.field_70138_W != ((this.field_70131_O >= 2.0F) ? (this.field_70131_O / 2.0F) : 1.0F))
        this.field_70138_W = (this.field_70131_O >= 2.0F) ? (this.field_70131_O / 2.0F) : 1.0F; 
      if (!isWild() && this.convertionInt > 0)
        this.convertionInt = 0; 
      if (!canBeMatedWith() && isInLove())
        resetInLove(); 
      if (getGrowingAge() < 4000)
        resetInLove(); 
      EntityPlayer player = this.field_70170_p.func_72890_a((Entity)this, 4.0D + this.field_70131_O + this.field_70130_N);
      if (EngenderConfig.mobs.useMobTalkerModels && func_70631_g_() && !func_184218_aH() && hasOwner(player))
        func_70671_ap().func_75651_a((Entity)player, func_184649_cE(), func_70646_bf()); 
      func_82168_bl();
      if ((func_110167_bD() && !this.field_70122_E) || this.field_70173_aa < 20)
        this.field_70143_R *= 0.0F; 
      if (getSpecialAttackTimer() > 0)
        setSpecialAttackTimer(getSpecialAttackTimer() - 1); 
      if (isHero() && this.field_70170_p.field_72995_K)
        if (func_70021_al() != null) {
          for (Entity part : func_70021_al()) {
            if (this.field_70170_p.field_72995_K)
              if (func_70089_S() && this.field_70146_Z.nextInt(2) == 0)
                this.field_70170_p.func_175688_a((func_184753_b() == null) ? EnumParticleTypes.REDSTONE : EnumParticleTypes.FIREWORKS_SPARK, part.field_70165_t + (this.field_70146_Z.nextFloat() * part.field_70130_N * 2.0F) - part.field_70130_N, part.field_70163_u + (this.field_70146_Z.nextFloat() * part.field_70131_O), part.field_70161_v + (this.field_70146_Z.nextFloat() * part.field_70130_N * 2.0F) - part.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);  
          } 
        } else if (this.field_70170_p.field_72995_K) {
          if (func_70089_S() && this.field_70146_Z.nextInt(2) == 0)
            this.field_70170_p.func_175688_a((func_184753_b() == null) ? EnumParticleTypes.REDSTONE : EnumParticleTypes.FIREWORKS_SPARK, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]); 
        }  
    } else {
      if (this.field_70173_aa == 2) {
        func_70656_aK();
        this.field_70172_ad = getSpawnTimer() * 2;
      } 
      this.field_70159_w = 0.0D;
      this.field_70179_y = 0.0D;
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    } 
    if (func_70089_S() && this.field_70122_E && func_70638_az() != null && (func_70638_az()).field_70163_u >= this.field_70163_u + 2.0D && this.field_70146_Z.nextInt(100) == 0) {
      this.field_70181_x = func_175134_bD() + 0.20000000298023224D;
      this.field_70160_al = true;
      ForgeHooks.onLivingJump((EntityLivingBase)this);
      double d0 = (func_70638_az()).field_70165_t - this.field_70165_t;
      double d1 = (func_70638_az()).field_70161_v - this.field_70161_v;
      float f1 = MathHelper.func_76133_a(d0 * d0 + d1 * d1);
      this.field_70159_w += d0 / f1 * 0.5D * 0.5D + this.field_70159_w;
      this.field_70179_y += d1 / f1 * 0.5D * 0.5D + this.field_70179_y;
    } 
  }
  
  public void func_70031_b(boolean sprinting) {
    if (getEnergy() <= 30.0F)
      sprinting = false; 
    super.func_70031_b(sprinting);
  }
  
  public void updateBossBar() {
    this.bossInfo.func_186739_a(func_145748_c_());
    if (func_70631_g_())
      this.bossInfo.func_186745_a(BossInfo.Color.RED); 
    this.bossInfo.func_186735_a((getFakeHealth() > 0.0F) ? (getFakeHealth() / func_110138_aP() * 2.0F) : (func_110143_aJ() / func_110138_aP()));
    this.bossInfo.func_186758_d((!func_70093_af() && !func_82150_aj() && func_70089_S()));
    this.bossInfo.func_186746_a((getTier() == EnumTier.TIER6) ? BossInfo.Overlay.NOTCHED_20 : ((getTier() == EnumTier.TIER5) ? ((func_110138_aP() >= 250.0F) ? BossInfo.Overlay.NOTCHED_12 : BossInfo.Overlay.NOTCHED_10) : ((func_110138_aP() >= 50.0F) ? BossInfo.Overlay.NOTCHED_6 : BossInfo.Overlay.PROGRESS)));
  }
  
  public boolean isABoss() {
    return false;
  }
  
  public boolean func_184222_aU() {
    return true;
  }
  
  public void func_184178_b(EntityPlayerMP player) {
    super.func_184178_b(player);
    if (isABoss() || (func_70631_g_() && player.func_70005_c_().equals("Mrbt0907")))
      this.bossInfo.func_186760_a(player); 
  }
  
  public void func_184203_c(EntityPlayerMP player) {
    super.func_184203_c(player);
    this.bossInfo.func_186761_b(player);
  }
  
  public int getSpawnTimer() {
    return 20;
  }
  
  public float getHealthPercent() {
    return func_110143_aJ() / func_110138_aP();
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
  
  public boolean func_70648_aU() {
    return (isABoss() || isNotALivingThing());
  }
  
  public void InactMutation() {
    if (getMutant() != null && getFittness() >= 1.5F) {
      EntityTameBase mutant = getMutant();
      mutant.func_82149_j((Entity)this);
      mutant.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
      mutant.setOwnerId(func_184753_b());
      mutant.setIsHero(isHero());
      mutant.setGrowingAge(getGrowingAge());
      this.field_70170_p.func_72838_d((Entity)mutant);
      dropEquipmentUndamaged();
      func_70106_y();
    } else {
      EntityTameBase clone;
      switch (this.field_70146_Z.nextInt(10)) {
        case 0:
          setFittness(getFittness() + 0.25F);
          return;
        case 1:
          levelUp();
          return;
        case 2:
          setEnergy(100.0F);
          func_70691_i(20.0F);
          return;
        case 3:
          clone = spawnBaby(this);
          clone.func_82149_j((Entity)this);
          clone.func_70037_a(serializeNBT());
          clone.setOwnerId(func_184753_b());
          this.field_70170_p.func_72838_d((Entity)clone);
          return;
        case 4:
          func_70690_d(new PotionEffect(MobEffects.field_82731_v, 400, 1));
          return;
        case 5:
          setFittness(getFittness() - 0.25F);
          return;
        case 6:
          if (!isAntiMob()) {
            func_70645_a((new DamageSource("antimatter")).func_76348_h().func_151518_m());
          } else {
            setFittness(getFittness() + 0.25F);
          } 
          return;
      } 
      this.field_70181_x++;
    } 
  }
  
  public void setSwingingArms(boolean swingingArms) {}
  
  public boolean canFollowOwner() {
    return (!func_184613_cA() && !isWild() && !func_175446_cd() && !func_70610_aX() && getAttackState() <= 0 && func_70638_az() == null && getCurrentBook().func_190926_b());
  }
  
  public class PathNavigateFlying extends PathNavigateGround {
    public PathNavigateFlying(EntityLiving p_i47412_1_, World p_i47412_2_) {
      super(p_i47412_1_, p_i47412_2_);
    }
    
    protected PathFinder func_179679_a() {
      this.field_179695_a = (NodeProcessor)new FlyingNodeProcessor();
      this.field_179695_a.func_186317_a(true);
      return new PathFinder(this.field_179695_a);
    }
    
    protected boolean func_75485_k() {
      return ((canFloat() && func_75506_l()) || !this.field_75515_a.func_184218_aH());
    }
    
    protected Vec3d func_75502_i() {
      return new Vec3d(this.field_75515_a.field_70165_t, this.field_75515_a.field_70163_u, this.field_75515_a.field_70161_v);
    }
    
    public Path func_75494_a(Entity entityIn) {
      return func_179680_a(new BlockPos(entityIn));
    }
    
    public void func_75501_e() {
      this.field_75510_g++;
      if (this.field_188562_p)
        func_188554_j(); 
      if (!func_75500_f()) {
        if (func_75485_k()) {
          func_75508_h();
        } else if (this.field_75514_c != null && this.field_75514_c.func_75873_e() < this.field_75514_c.func_75874_d()) {
          Vec3d vec3d = this.field_75514_c.func_75881_a((Entity)this.field_75515_a, this.field_75514_c.func_75873_e());
          if (MathHelper.func_76128_c(this.field_75515_a.field_70165_t) == MathHelper.func_76128_c(vec3d.field_72450_a) && MathHelper.func_76128_c(this.field_75515_a.field_70163_u) == MathHelper.func_76128_c(vec3d.field_72448_b) && MathHelper.func_76128_c(this.field_75515_a.field_70161_v) == MathHelper.func_76128_c(vec3d.field_72449_c))
            this.field_75514_c.func_75872_c(this.field_75514_c.func_75873_e() + 1); 
        } 
        func_192876_m();
        if (!func_75500_f()) {
          Vec3d vec3d1 = this.field_75514_c.func_75878_a((Entity)this.field_75515_a);
          this.field_75515_a.func_70605_aq().func_75642_a(vec3d1.field_72450_a, vec3d1.field_72448_b, vec3d1.field_72449_c, this.field_75511_d);
        } 
      } 
    }
    
    protected boolean func_75493_a(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
      int i = MathHelper.func_76128_c(posVec31.field_72450_a);
      int j = MathHelper.func_76128_c(posVec31.field_72448_b);
      int k = MathHelper.func_76128_c(posVec31.field_72449_c);
      double d0 = posVec32.field_72450_a - posVec31.field_72450_a;
      double d1 = posVec32.field_72448_b - posVec31.field_72448_b;
      double d2 = posVec32.field_72449_c - posVec31.field_72449_c;
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
      double d8 = i - posVec31.field_72450_a;
      double d9 = j - posVec31.field_72448_b;
      double d10 = k - posVec31.field_72449_c;
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
      int k1 = MathHelper.func_76128_c(posVec32.field_72450_a);
      int l1 = MathHelper.func_76128_c(posVec32.field_72448_b);
      int i2 = MathHelper.func_76128_c(posVec32.field_72449_c);
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
      this.field_179695_a.func_186321_b(p_192879_1_);
    }
    
    public void setCanEnterDoors(boolean p_192878_1_) {
      this.field_179695_a.func_186317_a(p_192878_1_);
    }
    
    public void setCanFloat(boolean p_192877_1_) {
      this.field_179695_a.func_186316_c(p_192877_1_);
    }
    
    public boolean canFloat() {
      return this.field_179695_a.func_186322_e();
    }
    
    public boolean func_188555_b(BlockPos pos) {
      return this.field_75513_b.func_180495_p(pos).func_185896_q();
    }
  }
}
