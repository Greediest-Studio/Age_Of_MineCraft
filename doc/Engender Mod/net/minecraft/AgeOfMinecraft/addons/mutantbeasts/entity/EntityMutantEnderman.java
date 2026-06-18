package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBParticles;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantEnderman extends EntityTameBase implements IRangedAttackMob, IJumpingMount, Undead, Massive, Armored, Ender {
  private static final DataParameter<Byte> ACTIVE_ARM = EntityDataManager.func_187226_a(EntityMutantEnderman.class, DataSerializers.field_187191_a);
  
  private static final DataParameter<Byte> CLONE_STATE = EntityDataManager.func_187226_a(EntityMutantEnderman.class, DataSerializers.field_187191_a);
  
  public static final int MAX_DEATH_TIME = 280;
  
  public static final byte MELEE_ATTACK = 4;
  
  public static final byte THROW_ATTACK = 5;
  
  public static final byte STARE_ATTACK = 6;
  
  public static final byte TELEPORT_ATTACK = 7;
  
  public static final byte SCREAM_ATTACK = 8;
  
  public static final byte CLONE_ATTACK = 9;
  
  public final int[] heldBlock = new int[5];
  
  public static final byte TELESMASH_ATTACK = 10;
  
  public static final byte DEATH_ATTACK = 11;
  
  private int attackID;
  
  private int attackTick;
  
  private int prevArmScale;
  
  private int armScale;
  
  public int hasTarget;
  
  private int screamDelayTick;
  
  public final int[] heldBlockTick = new int[5];
  
  private boolean triggerThrowBlock;
  
  private int blockSearchTick;
  
  private List<EntityMutantEnderman> cloneList;
  
  private List<Entity> deathEntities;
  
  private EntityMutantEnderman cloner;
  
  private DamageSource deathCause = DamageSource.field_76377_j;
  
  private int dirty = -1;
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.2D, 40, 96.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  protected float jumpPower;
  
  public EntityMutantEnderman(World worldIn) {
    super(worldIn);
    this.field_70728_aV = 900;
    this.field_70158_ak = true;
    this.cloneList = new ArrayList<>();
    func_70105_a(0.9F, 4.2F);
    this.field_70714_bg.func_75776_a(1, new MeleeGoal());
    this.field_70714_bg.func_75776_a(1, new ThrowBlockGoal());
    this.field_70714_bg.func_75776_a(1, new ForcedLookGoal());
    this.field_70714_bg.func_75776_a(1, new ScreamGoal());
    this.field_70714_bg.func_75776_a(1, new CloneGoal());
    this.field_70714_bg.func_75776_a(1, new TeleSmashGoal());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 64.0F, 12.0F));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.83D, 0.0F));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_enderman";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(96.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(24.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantEnderman(this.field_70170_p);
  }
  
  public int getSpawnTimer() {
    return 2;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 750;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(ACTIVE_ARM, Byte.valueOf((byte)0));
    this.field_70180_af.func_187214_a(CLONE_STATE, Byte.valueOf((byte)0));
  }
  
  public int getActiveArm() {
    return ((Byte)this.field_70180_af.func_187225_a(ACTIVE_ARM)).byteValue();
  }
  
  private void setActiveArm(int armID) {
    this.field_70180_af.func_187227_b(ACTIVE_ARM, Byte.valueOf((byte)armID));
  }
  
  public boolean isClone() {
    return (((Byte)this.field_70180_af.func_187225_a(CLONE_STATE)).byteValue() > 0);
  }
  
  private boolean isDecoyClone() {
    return (((Byte)this.field_70180_af.func_187225_a(CLONE_STATE)).byteValue() > 1);
  }
  
  private void setCloneState(int newState) {
    byte currentState = ((Byte)this.field_70180_af.func_187225_a(CLONE_STATE)).byteValue();
    if (currentState == newState)
      return; 
    if (currentState == 1 && newState == 0) {
      for (EntityMutantEnderman clone : this.cloneList)
        clone.func_70609_aI(); 
      this.cloneList.clear();
      spawnTeleportParticles(true);
      this.field_70699_by.func_75499_g();
      this.field_70138_W = 2.5F;
    } 
    this.field_70180_af.func_187227_b(CLONE_STATE, Byte.valueOf((byte)newState));
    setAttackID((newState > 0) ? 9 : 0);
  }
  
  public int getAttackID() {
    return this.attackID;
  }
  
  private void setAttackID(int attackID) {
    this.attackID = attackID;
    this.field_70170_p.func_72960_a((Entity)this, (byte)attackID);
  }
  
  public int getAttackTick() {
    return this.attackTick;
  }
  
  public float func_70047_e() {
    return isClone() ? 2.55F : (this.field_70131_O * 0.9285F);
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new MBGroundPathNavigator((EntityLiving)this, worldIn);
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public int func_82143_as() {
    return isClone() ? 3 : super.func_82143_as();
  }
  
  public boolean func_70067_L() {
    return (super.func_70067_L() && this.attackID != 7);
  }
  
  public void func_184206_a(DataParameter<?> key) {
    super.func_184206_a(key);
    if (CLONE_STATE.equals(key))
      if (isClone()) {
        func_70105_a(0.6F, 2.9F);
      } else {
        func_70105_a(0.9F, 4.2F);
      }  
  }
  
  protected boolean teleportRandomly() {
    if (this.field_70170_p.func_72935_r() || func_70026_G())
      func_70642_aH(); 
    double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 32.0D;
    double d1 = this.field_70163_u + (this.field_70146_Z.nextInt(64) - 32);
    double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 32.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportToEntity(Entity p_70816_1_) {
    Vec3d vec3d = new Vec3d(this.field_70165_t - p_70816_1_.field_70165_t, (func_174813_aQ()).field_72338_b + (this.field_70131_O / 2.0F) - p_70816_1_.field_70163_u + p_70816_1_.func_70047_e(), this.field_70161_v - p_70816_1_.field_70161_v);
    vec3d = vec3d.func_72432_b();
    double d0 = 16.0D;
    double d1 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72450_a * d0;
    double d2 = this.field_70163_u + (this.field_70146_Z.nextInt(16) - 8) - vec3d.field_72448_b * d0;
    double d3 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72449_c * d0;
    return teleportTo(d1, d2, d3);
  }
  
  protected boolean teleportTo(double x, double y, double z) {
    EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, x, y, z, 0.0F);
    if (MinecraftForge.EVENT_BUS.post((Event)event))
      return false; 
    boolean flag = (func_184595_k(event.getTargetX(), event.getTargetY(), event.getTargetZ()) && !isInLove() && !func_184218_aH());
    if (flag) {
      this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70169_q, this.field_70167_r, this.field_70166_s, SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
      func_184185_a(MBSoundEvents.ENTITY_MUTANT_ENDERMAN_TELEPORT, 1.0F, 1.0F);
      teleportAttack((EntityLivingBase)this);
      if (!this.field_70170_p.field_72995_K && this.field_70146_Z.nextFloat() < 0.01F) {
        EntityEndermite entityendermite = new EntityEndermite(this.field_70170_p);
        entityendermite.setOwnerId(func_184753_b());
        entityendermite.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
        this.field_70170_p.func_72838_d((Entity)entityendermite);
      } 
      return true;
    } 
    return flag;
  }
  
  public boolean func_184595_k(double x, double y, double z) {
    spawnTeleportParticles(false);
    teleportAttack((EntityLivingBase)this);
    double d0 = this.field_70165_t;
    double d1 = this.field_70163_u;
    double d2 = this.field_70161_v;
    this.field_70165_t = x;
    this.field_70163_u = y;
    this.field_70161_v = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos((Entity)this);
    World world = this.field_70170_p;
    Random random = func_70681_au();
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
        spawnTeleportParticles(false);
        teleportAttack((EntityLivingBase)this);
        if (func_184207_aI())
          func_184179_bs().func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v); 
        if (world.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !world.func_72953_d(func_174813_aQ()))
          flag = true; 
      } 
    } 
    if (!flag) {
      func_70634_a(d0, d1, d2);
      return false;
    } 
    for (int j = 0; j < 128; j++) {
      double d6 = j / 127.0D;
      float f = (random.nextFloat() - 0.5F) * 0.2F;
      float f1 = (random.nextFloat() - 0.5F) * 0.2F;
      float f2 = (random.nextFloat() - 0.5F) * 0.2F;
      double d3 = d0 + (this.field_70165_t - d0) * d6 + (random.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      double d4 = d1 + (this.field_70163_u - d1) * d6 + random.nextDouble() * this.field_70131_O;
      double d5 = d2 + (this.field_70161_v - d2) * d6 + (random.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      world.func_175688_a(EnumParticleTypes.PORTAL, d3, d4, d5, f, f1, f2, new int[0]);
    } 
    if (this instanceof EntityCreature)
      func_70661_as().func_75499_g(); 
    return true;
  }
  
  public void func_70624_b(@Nullable EntityLivingBase entitylivingbaseIn) {
    super.func_70624_b(entitylivingbaseIn);
    func_70052_a(2, (entitylivingbaseIn != null));
  }
  
  public boolean isAggressive() {
    return func_70083_f(2);
  }
  
  @SideOnly(Side.CLIENT)
  public float getArmScale(float partialTicks) {
    return (this.prevArmScale + (this.armScale - this.prevArmScale) * partialTicks) / 10.0F;
  }
  
  private void updateTargetTick() {
    this.prevArmScale = this.armScale;
    if (isAggressive())
      this.hasTarget = 20; 
    boolean emptyHanded = true;
    int i;
    for (i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] > 0)
        emptyHanded = false; 
      if (this.hasTarget > 0) {
        if (this.heldBlock[i] > 0)
          this.heldBlockTick[i] = Math.min(10, this.heldBlockTick[i] + 1); 
      } else {
        this.heldBlockTick[i] = Math.max(0, this.heldBlockTick[i] - 1);
      } 
    } 
    if (this.hasTarget > 0) {
      this.armScale = Math.min(10, this.armScale + 1);
    } else if (emptyHanded) {
      this.armScale = Math.max(0, this.armScale - 1);
    } else if (!this.field_70170_p.field_72995_K) {
      for (i = 1; i < this.heldBlock.length; i++) {
        if (this.heldBlock[i] != 0 && this.heldBlockTick[i] == 0) {
          BlockPos pos = new BlockPos(this.field_70165_t - 1.5D + this.field_70146_Z.nextDouble() * 4.0D, this.field_70163_u - 0.5D + this.field_70146_Z.nextDouble() * 2.5D, this.field_70161_v - 1.5D + this.field_70146_Z.nextDouble() * 4.0D);
          if (this.field_70170_p.func_175623_d(pos) && !this.field_70170_p.func_175623_d(pos.func_177977_b()) && this.field_70170_p.func_175665_u(pos.func_177977_b()) && ForgeEventFactory.getMobGriefingEvent(this.field_70170_p, (Entity)this)) {
            this.field_70170_p.func_175656_a(pos, Block.func_176220_d(this.heldBlock[i]));
            Block block = Block.func_149729_e(this.heldBlock[i]);
            SoundType soundType = block.getSoundType(block.func_176223_P(), this.field_70170_p, pos, (Entity)this);
            func_184185_a(soundType.func_185841_e(), (soundType.func_185843_a() + 1.0F) / 2.0F, soundType.func_185847_b() * 0.8F);
            sendHoldBlock(i, 0, 0);
          } else {
            this.triggerThrowBlock = true;
          } 
        } 
      } 
    } 
    this.hasTarget = Math.max(0, this.hasTarget - 1);
  }
  
  private void updateScreamEntities() {
    this.screamDelayTick = Math.max(0, this.screamDelayTick - 1);
    if (this.attackID == 8 && this.attackTick >= 40 && this.attackTick <= 160)
      for (Entity entity : this.field_70170_p.func_175674_a((Entity)this, func_174813_aQ().func_186662_g(32.0D), EntityEndersoulFragment.IS_VALID_TARGET)) {
        if (entity instanceof EntityLivingBase && !func_184191_r(entity))
          entity.field_70125_A += 5.0F; 
      }  
  }
  
  private void spawnTeleportParticles(boolean clone) {
    if (clone && !func_174814_R())
      this.field_70170_p.func_184148_a(null, this.field_70165_t, this.field_70163_u + this.field_70131_O / 2.0D, this.field_70161_v, MBSoundEvents.ENTITY_MUTANT_ENDERMAN_TELEPORT, func_184176_by(), 1.0F, 1.0F); 
    this.field_70170_p.func_72960_a((Entity)this, (byte)(clone ? 12 : 1));
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 1) {
      spawnBigParticles();
    } else if (id == 12) {
      EntityUtil.spawnEndersoulParticles((Entity)this, 256, 1.8F);
    } else if (id == 0 || (id >= 4 && id <= 11)) {
      this.attackID = id;
      this.attackTick = 0;
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (this.attackID != 0)
      this.attackTick++; 
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    if (this.attackID == 0 && func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && !func_184191_r((Entity)func_70638_az()) && func_70068_e((Entity)func_70638_az()) < (this.field_70130_N * this.field_70130_N + (func_70638_az()).field_70130_N * (func_70638_az()).field_70130_N) + 9.0D)
      func_70652_k((Entity)func_70638_az()); 
    updateTargetTick();
    updateScreamEntities();
    double h = (this.attackID != 11) ? this.field_70131_O : (this.field_70131_O + 1.0F);
    double w = (this.attackID != 11) ? this.field_70130_N : (this.field_70130_N * 1.5F);
    boolean targetBlind = (func_70638_az() != null && func_70638_az().func_70644_a(MobEffects.field_76440_q));
    if (!targetBlind && !isClone()) {
      for (int i = 0; i < 3; i++) {
        double x = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * w;
        double y = this.field_70163_u + this.field_70146_Z.nextDouble() * h - 0.25D;
        double z = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * w;
        this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, x, y, z, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, new int[0]);
      } 
      if (func_70638_az() != null) {
        if ((func_70638_az() instanceof net.minecraft.entity.EntityFlying || (func_70638_az()).field_70163_u > this.field_70163_u + this.field_70131_O) && !isClone() && !isDecoyClone()) {
          this.field_70714_bg.func_75776_a(3, (EntityAIBase)this.aiArrowAttack);
          this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
        } else {
          this.field_70714_bg.func_75776_a(3, (EntityAIBase)this.aiAttackOnCollide);
          this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
        } 
      } else {
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      } 
    } 
  }
  
  private void updateBlockFrenzy() {
    this.blockSearchTick = Math.max(0, this.blockSearchTick - 1);
    if (func_70638_az() != null && this.attackID == 0) {
      if (this.blockSearchTick == 0 && this.field_70146_Z.nextInt((!func_70781_l() || func_70638_az() instanceof IRangedAttackMob) ? 100 : 600) == 0)
        this.blockSearchTick = 200 + this.field_70146_Z.nextInt(80); 
      if (this.blockSearchTick > 0) {
        BlockPos pos = new BlockPos(this.field_70165_t - 2.5D + this.field_70146_Z.nextDouble() * 5.0D, this.field_70163_u - 0.5D + this.field_70146_Z.nextDouble() * 3.0D, this.field_70161_v - 2.5D + this.field_70146_Z.nextDouble() * 5.0D);
        IBlockState blockState = this.field_70170_p.func_180495_p(pos);
        int index = getFavorableHand();
        if (index != -1 && EntityEnderman.getCarriable(blockState.func_177230_c())) {
          this.heldBlock[index] = Block.func_149682_b(blockState.func_177230_c());
          this.heldBlockTick[index] = 0;
        } 
      } 
    } 
  }
  
  private void updateTeleport() {
    EntityLivingBase entityLivingBase = func_70638_az();
    teleportByChance((entityLivingBase == null) ? 1600 : 800, (Entity)entityLivingBase);
    if (entityLivingBase != null) {
      double d = func_70068_e((Entity)entityLivingBase);
      if (func_184223_x((Entity)entityLivingBase) || d > 1024.0D || (d > 36.0D && !func_70781_l()))
        teleportByChance(10, (Entity)entityLivingBase); 
    } 
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (isDecoyClone() && this.cloner != null && this.cloner.func_70638_az() != null) {
      func_70625_a((Entity)this.cloner.func_70638_az(), 10.0F, 40.0F);
      if (this.field_70173_aa % 10 == 0)
        func_70661_as().func_75497_a((Entity)this.cloner.func_70638_az(), 1.2D); 
      if (this.field_70173_aa % 20 == 0)
        func_70652_k((Entity)this.cloner.func_70638_az()); 
    } 
    if (func_70026_G() && this.field_70173_aa % 40 == 0 && !isClone())
      func_70097_a(DamageSource.field_76370_b, 2.0F); 
    if (this.dirty >= 0)
      this.dirty++; 
    if (this.dirty >= 8) {
      this.dirty = -1;
      for (int i = 1; i < this.heldBlock.length; i++) {
        if (this.heldBlock[i] > 0)
          sendHoldBlock(i, this.heldBlock[i], 0); 
      } 
    } 
    updateBlockFrenzy();
    updateTeleport();
  }
  
  private int getAvailableHand() {
    List<Integer> list = new ArrayList<>();
    for (int i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] == 0)
        list.add(Integer.valueOf(i)); 
    } 
    if (list.isEmpty())
      return -1; 
    return ((Integer)list.get(this.field_70146_Z.nextInt(list.size()))).intValue();
  }
  
  private int getFavorableHand() {
    List<Integer> outer = new ArrayList<>();
    List<Integer> inner = new ArrayList<>();
    for (int i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] == 0)
        if (i <= 2) {
          outer.add(Integer.valueOf(i));
        } else {
          inner.add(Integer.valueOf(i));
        }  
    } 
    if (outer.isEmpty() && inner.isEmpty())
      return -1; 
    if (!outer.isEmpty())
      return ((Integer)outer.get(this.field_70146_Z.nextInt(outer.size()))).intValue(); 
    return ((Integer)inner.get(this.field_70146_Z.nextInt(inner.size()))).intValue();
  }
  
  private int getThrowingHand() {
    List<Integer> outer = new ArrayList<>();
    List<Integer> inner = new ArrayList<>();
    for (int i = 1; i < this.heldBlock.length; i++) {
      if (this.heldBlock[i] != 0)
        if (i <= 2) {
          outer.add(Integer.valueOf(i));
        } else {
          inner.add(Integer.valueOf(i));
        }  
    } 
    if (outer.isEmpty() && inner.isEmpty())
      return -1; 
    if (!inner.isEmpty())
      return ((Integer)inner.get(this.field_70146_Z.nextInt(inner.size()))).intValue(); 
    return ((Integer)outer.get(this.field_70146_Z.nextInt(outer.size()))).intValue();
  }
  
  public boolean func_70652_k(Entity entityIn) {
    if (!this.field_70170_p.field_72995_K && this.attackID == 0) {
      int i = getAvailableHand();
      if (!teleportByChance(6, entityIn))
        if (i != -1) {
          boolean allHandsFree = (this.heldBlock[1] == 0 && this.heldBlock[2] == 0);
          if (allHandsFree && (this.field_70146_Z.nextInt(10) == 0 || func_110143_aJ() <= func_110138_aP() / 4.0F)) {
            setAttackID(7);
          } else if (allHandsFree && this.field_70146_Z.nextInt(2) == 0) {
            setAttackID(10);
          } else {
            setActiveArm(i);
            setAttackID(4);
          } 
        } else {
          this.triggerThrowBlock = true;
        }  
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    } 
    if (this.attackID == 9) {
      boolean flag = super.func_70652_k(entityIn);
      if (!this.field_70170_p.field_72995_K ? (isDecoyClone() ? (this.field_70146_Z.nextInt(3) != 0) : (this.field_70146_Z.nextInt(2) == 0)) : (this.field_70146_Z.nextInt(2) == 0)) {
        double x = entityIn.field_70165_t + ((this.field_70146_Z.nextFloat() - 0.5F) * 24.0F);
        double z = entityIn.field_70161_v + ((this.field_70146_Z.nextFloat() - 0.5F) * 24.0F);
        double y = entityIn.field_70163_u + this.field_70146_Z.nextInt(5) + 4.0D;
        teleportTo(x, y, z);
      } 
      if (flag)
        func_70691_i(4.0F); 
      return flag;
    } 
    return true;
  }
  
  public boolean func_70687_e(PotionEffect potioneffectIn) {
    return (!isClone() && super.func_70687_e(potioneffectIn));
  }
  
  private boolean teleportByChance(int chance, Entity entity) {
    if (this.attackID != 0 && !isClone())
      return false; 
    if (this.field_70146_Z.nextInt(Math.max(1, chance)) == 0)
      return (entity == null) ? teleportRandomly() : teleportToEntity(entity); 
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  private void spawnBigParticles() {
    int temp = 256;
    if (this.attackID == 7)
      temp *= 2; 
    for (int i = 0; i < temp; i++) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 1.8F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 1.8F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 1.8F;
      boolean death = (this.attackID != 11);
      double h = death ? this.field_70131_O : (this.field_70131_O + 1.0F);
      double w = death ? this.field_70130_N : (this.field_70130_N * 1.5F);
      double tempX = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * w;
      double tempY = this.field_70163_u + (this.field_70146_Z.nextDouble() - 0.5D) * h + (death ? 1.5F : 0.5F);
      double tempZ = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * w;
      this.field_70170_p.func_175688_a(MBParticles.ENDERSOUL, tempX, tempY, tempZ, f, f1, f2, new int[0]);
    } 
  }
  
  public void teleportAttack(EntityLivingBase attacker) {
    double r = 3.0D;
    int duration = 140 + attacker.func_70681_au().nextInt(60);
    DamageSource damageSource = DamageSource.func_76358_a(attacker);
    if (attacker instanceof EntityPlayer) {
      r = 2.0D;
      duration = 100;
      damageSource = DamageSource.func_76365_a((EntityPlayer)attacker);
    } 
    for (Entity entity : attacker.field_70170_p.func_175674_a((Entity)attacker, attacker.func_174813_aQ().func_186662_g(r), EntityEndersoulFragment.IS_VALID_TARGET)) {
      if (entity instanceof EntityLivingBase && !func_184191_r(entity)) {
        EntityLivingBase living = (EntityLivingBase)entity;
        inflictEngenderMobDamage((EntityLivingBase)entity, " was splattered by ", damageSource, 4.0F);
        if (attacker.func_70681_au().nextInt(3) == 0)
          living.func_70690_d(new PotionEffect(MobEffects.field_76440_q, duration)); 
        double x = living.field_70165_t - attacker.field_70165_t;
        double z = living.field_70161_v - attacker.field_70161_v;
        double signX = x / Math.abs(x);
        double signZ = z / Math.abs(z);
        if (!Double.isNaN(signX) && !Double.isNaN(signZ)) {
          living.field_70159_w = (r * signX * 2.0D - x) * 0.20000000298023224D;
          living.field_70181_x = 0.20000000298023224D;
          living.field_70179_y = (r * signZ * 2.0D - z) * 0.20000000298023224D;
          EntityUtil.knockBackBlockingPlayer((Entity)attacker);
        } 
      } 
    } 
  }
  
  private void createClone(double x, double y, double z) {
    EntityMutantEnderman clone = new EntityMutantEnderman(this.field_70170_p);
    clone.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    clone.cloner = this;
    clone.field_70728_aV = this.field_70146_Z.nextInt(2);
    clone.setOwnerId(func_184753_b());
    clone.setCloneState(2);
    clone.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(func_110138_aP());
    clone.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
    clone.func_70606_j(func_110143_aJ());
    clone.func_70624_b(func_70638_az());
    if (clone.teleportTo(x, y, z)) {
      this.field_70170_p.func_72838_d((Entity)clone);
      this.cloneList.add(clone);
    } 
  }
  
  private EntityMutantEnderman getRandomClone() {
    if (this.cloneList.isEmpty())
      return this; 
    return this.cloneList.get(this.field_70146_Z.nextInt(this.cloneList.size()));
  }
  
  public boolean func_96092_aw() {
    return false;
  }
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    if (isClone())
      func_70691_i(4.0F); 
  }
  
  public void func_70645_a(DamageSource cause) {
    if (isDecoyClone())
      return; 
    super.func_70645_a(cause);
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (!this.field_70170_p.field_72995_K)
      if (isClone()) {
        if (isDecoyClone()) {
          amount = 1.0F;
          func_70609_aI();
        } else {
          setCloneState(0);
        } 
      } else {
        if (this.attackID == 8 && source != DamageSource.field_76380_i)
          return false; 
        if (this.attackID == 6)
          this.attackTick = 100; 
        Entity entity = source.func_76346_g();
        boolean betterDodge = (entity == null);
        if (source.func_76352_a() || source == DamageSource.field_76379_h)
          betterDodge = true; 
        if (betterDodge) {
          if (!isWild())
            teleportToEntity((Entity)getOwner()); 
          teleportRandomly();
          return false;
        } 
        return super.func_70097_a(source, amount);
      }  
    return false;
  }
  
  public boolean leavesNoCorpse() {
    return false;
  }
  
  protected void func_70609_aI() {
    if (isDecoyClone()) {
      spawnTeleportParticles(true);
      this.field_70128_L = true;
    } else {
      super.func_70609_aI();
      func_174810_b(false);
      if (this.field_70725_aQ == 80)
        func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i()); 
      this.attackID = 11;
      if (!this.field_70170_p.field_72995_K) {
        if (this.field_70725_aQ >= 60 && this.field_70725_aQ < 80 && this.deathEntities == null)
          this.deathEntities = this.field_70170_p.func_175674_a((Entity)this, func_174813_aQ().func_186662_g(16.0D), EntityEndersoulFragment.IS_VALID_TARGET); 
        if (this.field_70725_aQ >= 60 && this.field_70146_Z.nextInt(3) != 0) {
          EntityEndersoulFragment orb = new EntityEndersoulFragment(this.field_70170_p, this);
          orb.func_70107_b(this.field_70165_t, this.field_70163_u + 3.8D, this.field_70161_v);
          orb.field_70159_w = ((this.field_70146_Z.nextFloat() - 0.5F) * 1.5F);
          orb.field_70181_x = ((this.field_70146_Z.nextFloat() - 0.5F) * 1.5F);
          orb.field_70179_y = ((this.field_70146_Z.nextFloat() - 0.5F) * 1.5F);
          this.field_70170_p.func_72838_d(orb);
        } 
        if (this.field_70725_aQ >= 80 && this.field_70725_aQ < 260 && this.deathEntities != null)
          for (int i = 0; i < this.deathEntities.size(); i++) {
            Entity entity = this.deathEntities.get(i);
            if (entity.field_70143_R > 4.5F)
              entity.field_70143_R = 4.5F; 
            if (entity instanceof EntityLivingBase && func_184191_r(entity)) {
              this.deathEntities.remove(i);
              i--;
            } else if (entity instanceof EntityLivingBase && !func_184191_r(entity)) {
              double x = this.field_70165_t - entity.field_70165_t;
              double z = this.field_70161_v - entity.field_70161_v;
              double d = Math.sqrt(x * x + z * z);
              entity.field_70159_w = 0.800000011920929D * x / d;
              entity.field_70179_y = 0.800000011920929D * z / d;
              inflictEngenderMobDamage((EntityLivingBase)entity, " was pummeled by ", DamageSource.field_76376_m, 1.0F);
              if (this.field_70163_u + 4.0D > entity.field_70163_u)
                entity.field_70181_x = Math.max(entity.field_70181_x, 0.4000000059604645D); 
              EntityUtil.sendPlayerVelocityPacket(entity);
            } 
          }  
        if (this.field_70725_aQ >= 280) {
          EntityUtil.dropExperience((EntityLiving)this, this.field_70718_bc, this::func_70693_a, this.field_70717_bb);
          func_70106_y();
        } 
      } 
    } 
  }
  
  public EntityItem func_70099_a(ItemStack stack, float offsetY) {
    return super.func_70099_a(stack, (this.field_70725_aQ > 0) ? 3.84F : 0.0F);
  }
  
  public boolean func_70601_bi() {
    if (this.field_70146_Z.nextInt(3) == 0)
      return false; 
    if (this.field_70170_p.field_73011_w.func_186058_p() == DimensionType.THE_END && this.field_70146_Z.nextInt(2600) != 0)
      return false; 
    return (super.func_70601_bi() && this.field_70170_p.func_175678_i(func_180425_c()));
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74774_a("CloneState", ((Byte)this.field_70180_af.func_187225_a(CLONE_STATE)).byteValue());
    compound.func_74777_a("ScreamDelay", (short)this.screamDelayTick);
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    if (compound.func_74771_c("CloneState") < 2) {
      setCloneState(0);
    } else {
      func_70106_y();
    } 
    this.screamDelayTick = compound.func_74765_d("ScreamDelay");
  }
  
  public void func_70642_aH() {
    if (this.attackID != 8 && !isClone())
      super.func_70642_aH(); 
  }
  
  protected SoundEvent func_184639_G() {
    return MBSoundEvents.ENTITY_MUTANT_ENDERMAN_AMBIENT;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return isDecoyClone() ? null : MBSoundEvents.ENTITY_MUTANT_ENDERMAN_HURT;
  }
  
  protected SoundEvent func_184615_bR() {
    return isClone() ? null : MBSoundEvents.ENTITY_MUTANT_ENDERMAN_DEATH;
  }
  
  protected ResourceLocation func_184647_J() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_enderman");
  }
  
  public void sendHoldBlock(int blockIndex, int blockId, int blockData) {
    this.heldBlock[blockIndex] = blockId;
    this.heldBlockTick[blockIndex] = 0;
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    setAttackID(8);
    func_70656_aK();
    return livingdata;
  }
  
  public void becomeAHero() {
    setIsHero(true);
    func_184185_a(ESound.hero, 100.0F, 1.0F);
    this.field_70173_aa = 0;
    setAttackID(8);
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187566_ao, 0.15F, 1.0F);
    func_184185_a(SoundEvents.field_187605_cG, 0.15F, func_70647_i());
  }
  
  class ForcedLookGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public ForcedLookGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantEnderman.this.func_70638_az();
      return (EntityMutantEnderman.this.attackID == 6 && this.attackTarget != null);
    }
    
    public void func_75249_e() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.func_70661_as().func_75499_g();
      float pitch = 0.7F + EntityMutantEnderman.this.field_70146_Z.nextFloat() * 0.2F;
      EntityMutantEnderman.this.func_184185_a(MBSoundEvents.ENTITY_MUTANT_ENDERMAN_STARE, 2.5F, pitch);
    }
    
    public boolean func_75253_b() {
      if (this.attackTarget == null || !this.attackTarget.func_70089_S())
        return false; 
      return (EntityMutantEnderman.this.attackTick < 20);
    }
    
    public void func_75246_d() {
      if (this.attackTarget != null)
        EntityMutantEnderman.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 45.0F, 45.0F); 
    }
    
    public void func_75251_c() {
      EntityMutantEnderman.this.setAttackID(0);
      EntityMutantEnderman.this.inflictEngenderMobDamage(this.attackTarget, " died from an unknown force made by ", DamageSource.field_76376_m.func_151518_m(), 4.0F + EntityMutantEnderman.this.func_110138_aP() / EntityMutantEnderman.this.func_110143_aJ());
      EntityMutantEnderman.this.inflictCustomStatusEffect(EntityMutantEnderman.this.field_70170_p.func_175659_aa(), this.attackTarget, MobEffects.field_76440_q, 160 + EntityMutantEnderman.this.field_70146_Z.nextInt(140), 0);
      this.attackTarget = null;
    }
  }
  
  class MeleeGoal extends EntityAIBase {
    public boolean func_75250_a() {
      return (EntityMutantEnderman.this.attackID == 4);
    }
    
    public void func_75249_e() {
      EntityMutantEnderman.this.attackTick = 0;
    }
    
    public boolean func_75253_b() {
      return (EntityMutantEnderman.this.attackTick < 10);
    }
    
    public void func_75246_d() {
      if (EntityMutantEnderman.this.attackTick == 3) {
        EntityMutantEnderman.this.func_184185_a(SoundEvents.field_187727_dV, 1.0F, 0.8F);
        for (Entity entity : EntityMutantEnderman.this.field_70170_p.func_175674_a((Entity)EntityMutantEnderman.this, EntityMutantEnderman.this.func_174813_aQ().func_186662_g(6.0D), EntityEndersoulFragment.IS_VALID_TARGET)) {
          double dist = EntityMutantEnderman.this.func_70032_d(entity);
          double x = EntityMutantEnderman.this.field_70165_t - entity.field_70165_t;
          double z = EntityMutantEnderman.this.field_70161_v - entity.field_70161_v;
          boolean lower = (EntityMutantEnderman.this.getActiveArm() >= 3);
          if ((EntityMutantEnderman.this.func_174813_aQ()).field_72338_b <= (entity.func_174813_aQ()).field_72337_e && dist <= (lower ? 4.0D : 6.0D) && EntityUtil.isFacing((EntityLivingBase)EntityMutantEnderman.this, x, z, 3.0F + (1.0F - (float)dist / 4.0F) * 40.0F)) {
            DamageSource damageSource = DamageSource.func_76358_a((EntityLivingBase)EntityMutantEnderman.this);
            float attackDamage = (float)EntityMutantEnderman.this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
            float damage = (attackDamage > 0.0F) ? (attackDamage * (lower ? 1.5F : 3.0F)) : 0.0F;
            if (entity instanceof EntityLivingBase)
              EntityMutantEnderman.this.inflictEngenderMobDamage((EntityLivingBase)entity, " was slapped by ", damageSource, damage); 
            float power = 0.4F + EntityMutantEnderman.this.field_70146_Z.nextFloat() * 0.2F;
            if (!lower)
              power += 0.2F; 
            entity.field_70159_w = -x / dist * power;
            entity.field_70181_x = (power * 0.6F);
            entity.field_70179_y = -z / dist * power;
            EntityUtil.knockBackBlockingPlayer((Entity)EntityMutantEnderman.this);
          } 
        } 
      } 
    }
    
    public void func_75251_c() {
      EntityMutantEnderman.this.setAttackID(0);
    }
  }
  
  class CloneGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public boolean func_75250_a() {
      if (EntityMutantEnderman.this.isDecoyClone() || EntityMutantEnderman.this.func_70638_az() == null)
        return false; 
      if (EntityMutantEnderman.this.heldBlock[1] == 0 && EntityMutantEnderman.this.heldBlock[2] == 0)
        return (EntityMutantEnderman.this.attackID == 7 && !EntityMutantEnderman.this.isClone()); 
      return false;
    }
    
    public void func_75249_e() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.setCloneState(1);
      EntityMutantEnderman.this.func_70066_B();
      EntityMutantEnderman.this.func_70674_bp();
      EntityMutantEnderman.this.spawnTeleportParticles(true);
      EntityMutantEnderman.this.field_70138_W = 1.0F;
      this.attackTarget = EntityMutantEnderman.this.func_70638_az();
      double x = this.attackTarget.field_70165_t + ((EntityMutantEnderman.this.field_70146_Z.nextFloat() - 0.5F) * 24.0F);
      double z = this.attackTarget.field_70161_v + ((EntityMutantEnderman.this.field_70146_Z.nextFloat() - 0.5F) * 24.0F);
      double y = this.attackTarget.field_70163_u + 8.0D;
      for (int i = 0; i < 7; i++) {
        x = this.attackTarget.field_70165_t + ((EntityMutantEnderman.this.field_70146_Z.nextFloat() - 0.5F) * 24.0F);
        z = this.attackTarget.field_70161_v + ((EntityMutantEnderman.this.field_70146_Z.nextFloat() - 0.5F) * 24.0F);
        y = this.attackTarget.field_70163_u + 8.0D;
        EntityMutantEnderman.this.createClone(x, y, z);
      } 
      EntityMutantEnderman.this.teleportTo(x, y, z);
      EntityMutantEnderman.this.createClone(EntityMutantEnderman.this.field_70169_q, EntityMutantEnderman.this.field_70167_r, EntityMutantEnderman.this.field_70166_s);
      EntityUtil.divertAttackers((EntityLiving)EntityMutantEnderman.this, (EntityLivingBase)EntityMutantEnderman.this.getRandomClone());
    }
    
    public boolean func_75253_b() {
      return (this.attackTarget != null && this.attackTarget.func_70089_S() && !EntityMutantEnderman.this.cloneList.isEmpty() && EntityMutantEnderman.this.attackID == 9 && EntityMutantEnderman.this.attackTick < 600);
    }
    
    public void func_75246_d() {
      for (int i = EntityMutantEnderman.this.cloneList.size() - 1; i >= 0; i--) {
        EntityMutantEnderman clone = EntityMutantEnderman.this.cloneList.get(i);
        if (!clone.func_70089_S()) {
          EntityMutantEnderman.this.cloneList.remove(i);
          EntityUtil.divertAttackers((EntityLiving)clone, (EntityLivingBase)EntityMutantEnderman.this.getRandomClone());
        } else {
          clone.func_70624_b(this.attackTarget);
        } 
      } 
    }
    
    public void func_75251_c() {
      EntityMutantEnderman.this.setCloneState(0);
      this.attackTarget = null;
    }
  }
  
  class ScreamGoal extends EntityAIBase {
    public ScreamGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      if (EntityMutantEnderman.this.attackID == 8 && !EntityMutantEnderman.this.isClone())
        return true; 
      if (EntityMutantEnderman.this.func_70638_az() != null)
        return (EntityMutantEnderman.this.screamDelayTick > 0) ? false : ((EntityMutantEnderman.this.field_70146_Z.nextInt((EntityMutantEnderman.this.func_70026_G() || EntityMutantEnderman.this.func_70638_az() instanceof net.minecraft.entity.EntityFlying) ? 2 : 1000) == 0)); 
      return false;
    }
    
    public void func_75249_e() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.setAttackID(8);
      EntityMutantEnderman.this.func_70661_as().func_75499_g();
    }
    
    public boolean func_75253_b() {
      return (EntityMutantEnderman.this.screamDelayTick <= 0 && EntityMutantEnderman.this.attackTick < 165 && !EntityMutantEnderman.this.isClone());
    }
    
    public void func_75246_d() {
      if (EntityMutantEnderman.this.attackTick >= 40) {
        if (EntityMutantEnderman.this.attackTick == 40) {
          EntityMutantEnderman.this.field_70170_p.func_72912_H().func_76084_b(false);
          EntityMutantEnderman.this.spawnTeleportParticles(false);
          EntityMutantEnderman.this.func_184185_a(MBSoundEvents.ENTITY_MUTANT_ENDERMAN_SCREAM, 5.0F, 0.7F + EntityMutantEnderman.this.field_70146_Z.nextFloat() * 0.1F);
        } 
        List<Entity> screamEntities = EntityMutantEnderman.this.field_70170_p.func_175674_a((Entity)EntityMutantEnderman.this, EntityMutantEnderman.this.func_174813_aQ().func_186662_g(48.0D), EntityEndersoulFragment.IS_VALID_TARGET);
        for (int i = 0; i < screamEntities.size(); i++) {
          Entity entity = screamEntities.get(i);
          if (entity instanceof EntityLivingBase)
            if (EntityMutantEnderman.this.func_184191_r(entity)) {
              screamEntities.remove(i);
              i--;
            } else {
              EntityMutantEnderman.this.inflictEngenderMobDamage((EntityLivingBase)entity, " died from a heartattack caused by ", DamageSource.func_76358_a((EntityLivingBase)EntityMutantEnderman.this).func_76348_h().func_151518_m(), 4.0F);
              if (entity instanceof EntityLivingBase) {
                EntityLivingBase living = (EntityLivingBase)entity;
                living.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 120, 3));
                living.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 120 + EntityMutantEnderman.this.field_70146_Z.nextInt(180), EntityMutantEnderman.this.field_70146_Z.nextInt(2)));
                living.func_70690_d(new PotionEffect(MobEffects.field_76437_t, 300 + EntityMutantEnderman.this.field_70146_Z.nextInt(300), EntityMutantEnderman.this.field_70146_Z.nextInt(2)));
                living.func_70690_d(new PotionEffect(MobEffects.field_76438_s, 120 + EntityMutantEnderman.this.field_70146_Z.nextInt(60), 10 + EntityMutantEnderman.this.field_70146_Z.nextInt(2)));
                living.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 120 + EntityMutantEnderman.this.field_70146_Z.nextInt(400), 0));
              } 
            }  
        } 
      } 
    }
    
    public void func_75251_c() {
      EntityMutantEnderman.this.setAttackID(0);
      EntityMutantEnderman.this.screamDelayTick = 600;
    }
  }
  
  class TeleSmashGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public TeleSmashGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantEnderman.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantEnderman.this.attackID == 10);
    }
    
    public void func_75249_e() {
      EntityMutantEnderman.this.attackTick = 0;
      this.attackTarget.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 20, 5));
      this.attackTarget.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 160 + this.attackTarget.func_70681_au().nextInt(160), 0));
    }
    
    public boolean func_75253_b() {
      return (EntityMutantEnderman.this.attackID == 10 && EntityMutantEnderman.this.attackTick < 30);
    }
    
    public void func_75246_d() {
      if (EntityMutantEnderman.this.attackTick < 20)
        EntityMutantEnderman.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F); 
      if (EntityMutantEnderman.this.attackTick == 17)
        this.attackTarget.func_184210_p(); 
      if (EntityMutantEnderman.this.attackTick == 18 && this.attackTarget != null) {
        this.attackTarget.field_70170_p.func_184133_a(null, this.attackTarget.func_180425_c(), SoundEvents.field_187539_bB, this.attackTarget.func_184176_by(), 1.2F, 0.9F + this.attackTarget.func_70681_au().nextFloat() * 0.2F);
        if (EntityMutantEnderman.this.func_70032_d((Entity)this.attackTarget) > 24.0F) {
          double x = EntityMutantEnderman.this.field_70165_t;
          double y = EntityMutantEnderman.this.field_70163_u;
          double z = EntityMutantEnderman.this.field_70161_v;
          this.attackTarget.func_70634_a(x, y, z);
        } else {
          double x = this.attackTarget.field_70165_t + ((this.attackTarget.func_70681_au().nextFloat() - 0.5F) * 14.0F);
          double y = (this.attackTarget.field_70163_u >= EntityMutantEnderman.this.field_70163_u + 13.0D) ? EntityMutantEnderman.this.field_70163_u : (this.attackTarget.field_70163_u + this.attackTarget.func_70681_au().nextFloat() + 13.0D);
          double z = this.attackTarget.field_70161_v + ((this.attackTarget.func_70681_au().nextFloat() - 0.5F) * 14.0F);
          this.attackTarget.func_70634_a(x, y, z);
        } 
        this.attackTarget.field_70170_p.func_184133_a(null, this.attackTarget.func_180425_c(), SoundEvents.field_187539_bB, this.attackTarget.func_184176_by(), 1.2F, 0.9F + this.attackTarget.func_70681_au().nextFloat() * 0.2F);
        this.attackTarget.func_184597_cx();
        EntityMutantEnderman.this.inflictEngenderMobDamage(this.attackTarget, " died from a heartattack caused by ", DamageSource.func_76358_a((EntityLivingBase)EntityMutantEnderman.this).func_76348_h(), (float)EntityMutantEnderman.this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e());
        EntityMutantEnderman.this.spawnTeleportParticles(false);
      } 
    }
    
    public void func_75251_c() {
      EntityMutantEnderman.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  public class ThrowBlockGoal extends EntityAIBase {
    public boolean func_75250_a() {
      if (EntityMutantEnderman.this.attackID != 0)
        return false; 
      if (!EntityMutantEnderman.this.triggerThrowBlock)
        return false; 
      if (EntityMutantEnderman.this.func_70638_az() != null && !EntityMutantEnderman.this.func_70685_l((Entity)EntityMutantEnderman.this.func_70638_az()))
        return false; 
      int id = EntityMutantEnderman.this.getThrowingHand();
      if (id == -1)
        return false; 
      EntityMutantEnderman.this.setActiveArm(id);
      return true;
    }
    
    public void func_75249_e() {
      EntityMutantEnderman.this.attackTick = 0;
      EntityMutantEnderman.this.setAttackID(5);
      int id = EntityMutantEnderman.this.getActiveArm();
      EntityMutantEnderman.this.field_70170_p.func_72838_d((Entity)new EntityThrowingBlock(EntityMutantEnderman.this.field_70170_p, EntityMutantEnderman.this, id));
      EntityMutantEnderman.this.sendHoldBlock(id, 0, 0);
    }
    
    public boolean func_75253_b() {
      return (EntityMutantEnderman.this.attackID == 5 && EntityMutantEnderman.this.attackTick < 14);
    }
    
    public void func_75251_c() {
      EntityMutantEnderman.this.setAttackID(0);
      EntityMutantEnderman.this.setActiveArm(0);
      EntityMutantEnderman.this.triggerThrowBlock = false;
    }
  }
  
  public void func_110206_u(int jumpPowerIn) {
    if (func_184207_aI()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  public boolean func_184776_b() {
    return true;
  }
  
  public void func_184775_b(int p_184775_1_) {}
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br / 3.0F;
      forward = entitylivingbase.field_191988_bg / 3.0F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 2.0D * this.jumpPower * getFittness();
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-1.6F * f * this.jumpPower);
          this.field_70179_y += (1.6F * f1 * this.jumpPower);
        } 
        this.jumpPower = 0.0F;
      } 
      this.field_184618_aE = this.field_70721_aZ;
      double d5 = this.field_70165_t - this.field_70169_q;
      double d7 = this.field_70161_v - this.field_70166_s;
      float f10 = MathHelper.func_76133_a(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.field_70721_aZ += (f10 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    if (!this.field_70170_p.field_72995_K && this.attackID == 0)
      if (this.field_70146_Z.nextInt() == 0) {
        setAttackID(10);
      } else {
        setAttackID(6);
      }  
  }
}
