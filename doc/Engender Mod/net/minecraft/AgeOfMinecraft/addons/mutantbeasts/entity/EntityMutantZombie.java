package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.FriendlyZombieSummon;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.Shockwave;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantZombie extends EntityTameBase implements IJumpingMount, Undead, Massive, Armored {
  private static final DataParameter<Integer> ZOMBIE_VARIANT = EntityDataManager.func_187226_a(EntityMutantZombie.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> LIVES = EntityDataManager.func_187226_a(EntityMutantZombie.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Byte> THROW_ATTACK_STATE = EntityDataManager.func_187226_a(EntityMutantZombie.class, DataSerializers.field_187191_a);
  
  public static final int MAX_DEATH_TIME = 140;
  
  public static final int MAX_VANISH_TIME = 100;
  
  public static final byte MELEE_ATTACK = 4;
  
  public static final byte THROW_ATTACK = 5;
  
  public static final byte ROAR_ATTACK = 6;
  
  private int attackID;
  
  private int attackTick;
  
  public int throwHitTick = -1;
  
  public int throwFinishTick = -1;
  
  public int vanishTime;
  
  private final List<Shockwave> seismicWavesList = new ArrayList<>();
  
  private final List<FriendlyZombieSummon> resurrections = new ArrayList<>();
  
  private EntityLivingBase lastAttacker;
  
  private DamageSource deathCause = DamageSource.field_76377_j;
  
  protected float jumpPower;
  
  public EntityMutantZombie(World worldIn) {
    super(worldIn);
    this.field_70728_aV = 600;
    this.field_70158_ak = true;
    func_70105_a(1.8F, 3.2F);
    this.field_70714_bg.func_75776_a(1, new MeleeGoal());
    this.field_70714_bg.func_75776_a(1, new RoarGoal());
    this.field_70714_bg.func_75776_a(1, new ThrowAttackGoal());
    this.field_70714_bg.func_75776_a(1, new TossAttackGoal());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 64.0F, 12.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_zombie";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(150.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(12.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(96.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(22.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
    func_110148_a(SWIM_SPEED).func_111128_a(4.0D);
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void performSpecialAttack() {
    setAttackID(6);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_190929_cY) {
      setLives(getLives() + 2);
      stack.func_190918_g(1);
      (Minecraft.func_71410_x()).field_71452_i.func_191271_a((Entity)this, EnumParticleTypes.TOTEM, 30);
      func_184185_a(SoundEvents.field_191263_gW, 1.0F, 1.0F);
      performSpecialAttack();
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected boolean func_184219_q(Entity passenger) {
    return (func_184188_bt().size() < 3);
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      int i = func_184188_bt().indexOf(passenger);
      float f3 = this.field_70761_aq * 3.1415927F / 180.0F;
      float f11 = MathHelper.func_76126_a(f3);
      float f4 = MathHelper.func_76134_b(f3);
      if (i == 2)
        passenger.func_70107_b(this.field_70165_t - (f4 * 1.25F), this.field_70163_u + (isHero() ? 2.25D : 2.0D), this.field_70161_v - (f11 * 1.25F)); 
      if (i == 1)
        passenger.func_70107_b(this.field_70165_t + (f4 * 1.25F), this.field_70163_u + (isHero() ? 2.25D : 2.0D), this.field_70161_v + (f11 * 1.25F)); 
      if (i == 0)
        passenger.func_70107_b(this.field_70165_t, this.field_70163_u + (isHero() ? (2.5D * getFittness()) : (2.25D * getFittness())), this.field_70161_v); 
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
  
  public void func_184775_b(int p_184775_1_) {
    func_184185_a(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_GRUNT, 0.3F, 0.8F + this.field_70146_Z.nextFloat() * 0.4F);
  }
  
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
        this.field_70181_x = 3.0D * this.jumpPower * getFittness();
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
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(ZOMBIE_VARIANT, Integer.valueOf(0));
    func_184212_Q().func_187214_a(THROW_ATTACK_STATE, Byte.valueOf((byte)0));
    func_184212_Q().func_187214_a(LIVES, Integer.valueOf(0));
  }
  
  public int getZombieType() {
    return ((Integer)func_184212_Q().func_187225_a(ZOMBIE_VARIANT)).intValue() - 1;
  }
  
  public void setZombieType(int villagerType) {
    if (villagerType == 2)
      this.field_70178_ae = true; 
    if (villagerType == 3) {
      if (Loader.isModLoaded("abyssalcraft") && ACConfig.hardcoreMode) {
        func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(375.0D);
        func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(48.0D);
      } else {
        func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(187.5D);
        func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(24.0D);
      } 
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(150.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(12.0D);
    } 
    if (isAntiMob())
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() * 2.0D); 
    func_184212_Q().func_187227_b(ZOMBIE_VARIANT, Integer.valueOf(villagerType + 1));
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new MBGroundPathNavigator((EntityLiving)this, worldIn);
  }
  
  protected float func_110146_f(float renderYawOffset, float distance) {
    return !func_70089_S() ? distance : super.func_110146_f(renderYawOffset, distance);
  }
  
  public int getLives() {
    return ((Integer)this.field_70180_af.func_187225_a(LIVES)).intValue();
  }
  
  private void setLives(int lives) {
    this.field_70180_af.func_187227_b(LIVES, Integer.valueOf(lives));
  }
  
  public boolean hasThrowAttackHit() {
    return (((Byte)this.field_70180_af.func_187225_a(THROW_ATTACK_STATE)).byteValue() != 0);
  }
  
  public void setThrowAttackHit(boolean hit) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(THROW_ATTACK_STATE)).byteValue();
    this.field_70180_af.func_187227_b(THROW_ATTACK_STATE, Byte.valueOf(hit ? 1 : (byte)(b0 & 0xFFFFFFFE)));
  }
  
  public boolean isThrowAttackFinished() {
    return ((((Byte)this.field_70180_af.func_187225_a(THROW_ATTACK_STATE)).byteValue() & 0x2) != 0);
  }
  
  public void setThrowAttackFinished(boolean finished) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(THROW_ATTACK_STATE)).byteValue();
    this.field_70180_af.func_187227_b(THROW_ATTACK_STATE, Byte.valueOf(finished ? (byte)(b0 | 0x2) : (byte)(b0 & 0xFFFFFFFD)));
  }
  
  public int getAttackID() {
    return this.attackID;
  }
  
  public int getAttackTick() {
    return this.attackTick;
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.875F;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public int func_82143_as() {
    return 16;
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {}
  
  public EnumActionResult func_184199_a(EntityPlayer player, Vec3d vec, EnumHand hand) {
    if (player.func_184586_b(hand).func_77973_b() == Items.field_151033_d && this.field_70725_aQ > 0 && !func_70027_ad() && !func_70026_G()) {
      func_70015_d(8);
      player.func_184609_a(hand);
      player.func_184586_b(hand).func_77972_a(1, (EntityLivingBase)player);
      this.field_70170_p.func_184133_a(player, func_180425_c(), SoundEvents.field_187649_bu, func_184176_by(), 1.0F, this.field_70146_Z.nextFloat() * 0.4F + 0.8F);
      return EnumActionResult.SUCCESS;
    } 
    return EnumActionResult.PASS;
  }
  
  public boolean func_70652_k(Entity entityIn) {
    if (!this.field_70170_p.field_72995_K) {
      if (this.attackID == 0 && !(entityIn instanceof net.minecraft.entity.monster.EntityGolem) && !(entityIn instanceof net.minecraft.AgeOfMinecraft.entity.tame.Flying) && !(entityIn instanceof net.minecraft.entity.passive.EntityFlying) && !(entityIn instanceof net.minecraft.entity.EntityFlying) && (this.field_70146_Z.nextInt(4) == 0 || (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).func_110143_aJ() <= 4.0F)) && (this.field_70122_E || func_70090_H() || func_180799_ab()))
        setAttackID(7); 
      if (this.attackID == 0 && (this.field_70146_Z.nextInt(2) == 0 || entityIn instanceof net.minecraft.entity.monster.EntityCreeper) && (this.field_70122_E || func_70090_H() || func_180799_ab()))
        setAttackID(5); 
      if (this.attackID == 0 && (this.field_70122_E || func_70090_H() || func_180799_ab()))
        setAttackID(4); 
    } 
    return (getAttackID() != 0) ? true : super.func_70652_k(entityIn);
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (source.func_76352_a() && source.func_188404_v() != null)
      amount = (float)(amount * 0.5D); 
    if (this.attackID == 5)
      return false; 
    if (this.attackID == 6 && source != DamageSource.field_76380_i) {
      if (this.attackTick < 10)
        return false; 
      amount *= 0.15F;
    } 
    return super.func_70097_a(source, amount);
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 0 || (id >= 4 && id <= 7)) {
      this.attackID = id;
      this.attackTick = 0;
    } else {
      super.func_70103_a(id);
    } 
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (func_70638_az() != null) {
      EntityLivingBase entityLivingBase = func_70638_az();
      if (!entityLivingBase.func_70089_S()) {
        func_70624_b(null);
      } else if (func_70068_e((Entity)entityLivingBase) < 49.0D) {
        int chance = (!func_70685_l((Entity)entityLivingBase) || (func_189748_bU() != null && func_189748_bU().func_76352_a())) ? 5 : 20;
        if (this.attackID == 0 && (this.field_70122_E || this.field_70170_p.func_72953_d(func_174813_aQ())) && this.field_70146_Z.nextInt(chance) == 0)
          setAttackID(4); 
        if (this.attackID == 0 && func_70068_e((Entity)entityLivingBase) < 1.0D && this.field_70146_Z.nextInt(125) == 0 && func_70685_l((Entity)entityLivingBase))
          setAttackID(5); 
      } 
    } 
    if (ForgeEventFactory.getMobGriefingEvent(this.field_70170_p, (Entity)this)) {
      int x = MathHelper.func_76128_c(this.field_70165_t);
      int y = MathHelper.func_76128_c(this.field_70163_u);
      int z = MathHelper.func_76128_c(this.field_70161_v);
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          for (int k = 0; k <= 3; k++) {
            BlockPos blockpos = new BlockPos(x + i, y + k, z + j);
            IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            if ((iblockstate.func_177230_c() instanceof net.minecraft.block.BlockLeaves || iblockstate.func_177230_c() instanceof net.minecraft.block.BlockGlass) && ForgeEventFactory.onEntityDestroyBlock((EntityLivingBase)this, blockpos, iblockstate))
              this.field_70170_p.func_175655_b(blockpos, false); 
          } 
        } 
      } 
    } 
  }
  
  public boolean isEntityImmuneToCoralium() {
    return (getZombieType() == 3 || super.isEntityImmuneToCoralium());
  }
  
  public boolean passesCoraliumPlague() {
    return (getZombieType() == 3);
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    fixRotation();
    updateAnimation();
    updateMeleeGrounds();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    if (this.field_70178_ae)
      func_70066_B(); 
    if (func_70089_S() && !this.field_70170_p.func_72935_r() && this.field_70173_aa % 50 == 0)
      func_70691_i(1.0F); 
    for (int i = this.resurrections.size() - 1; i >= 0; i--) {
      FriendlyZombieSummon zr = this.resurrections.get(i);
      if (!zr.update(this))
        this.resurrections.remove(zr); 
    } 
    if (func_110143_aJ() > 0.0F) {
      this.field_70725_aQ = 0;
      this.vanishTime = 0;
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (this.attackID == 0 && func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && !func_184191_r((Entity)func_70638_az()) && func_70068_e((Entity)func_70638_az()) < (this.field_70130_N * this.field_70130_N + (func_70638_az()).field_70130_N * (func_70638_az()).field_70130_N) + 36.0D)
      func_70652_k((Entity)func_70638_az()); 
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null && this.attackID == 0 && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y != 0.0D && this.field_70146_Z.nextInt(5) == 0) {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(i, j, k));
      if (iblockstate.func_185904_a() != Material.field_151579_a)
        this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, this.field_70165_t + (this.field_70146_Z.nextFloat() - 0.5D) * this.field_70130_N, (func_174813_aQ()).field_72338_b + 0.1D, this.field_70161_v + (this.field_70146_Z.nextFloat() - 0.5D) * this.field_70130_N, 4.0D * (this.field_70146_Z.nextFloat() - 0.5D), 0.5D, (this.field_70146_Z.nextFloat() - 0.5D) * 4.0D, new int[] { Block.func_176210_f(iblockstate) }); 
    } 
  }
  
  private void fixRotation() {
    float yaw;
    for (yaw = this.field_70759_as - this.field_70761_aq; yaw < -180.0F; yaw += 360.0F);
    while (yaw >= 180.0F)
      yaw -= 360.0F; 
    float offset = 0.1F;
    if (this.attackID == 4)
      offset = 0.2F; 
    this.field_70761_aq += yaw * offset;
  }
  
  protected void updateAnimation() {
    if (this.attackID != 0)
      this.attackTick++; 
    if (this.attackID == 5) {
      if (hasThrowAttackHit()) {
        if (this.throwHitTick == -1)
          this.throwHitTick = 0; 
        this.throwHitTick++;
      } 
      if (isThrowAttackFinished()) {
        if (this.throwFinishTick == -1)
          this.throwFinishTick = 0; 
        this.throwFinishTick++;
      } 
    } else {
      this.throwHitTick = -1;
      this.throwFinishTick = -1;
    } 
  }
  
  protected void updateMeleeGrounds() {
    if (!this.seismicWavesList.isEmpty()) {
      Shockwave wave = this.seismicWavesList.remove(0);
      wave.affectBlocks(this.field_70170_p, (EntityLivingBase)this);
      AxisAlignedBB box = new AxisAlignedBB(wave.func_177958_n(), (wave.func_177956_o() + 1), wave.func_177952_p(), (wave.func_177958_n() + 3), (wave.func_177956_o() + 2), (wave.func_177952_p() + 3));
      if (wave.isFirst()) {
        double addScale = this.field_70146_Z.nextDouble() * 0.75D;
        box.func_72314_b(0.25D + addScale, 0.25D + addScale * 0.5D, 0.25D + addScale);
      } 
      for (Entity entity : this.field_70170_p.func_175674_a((Entity)this, box, EntitySelectors.field_188444_d)) {
        if (entity instanceof EntityLivingBase && !func_184191_r(entity)) {
          DamageSource source = DamageSource.func_76358_a((EntityLivingBase)this).func_151518_m();
          inflictEngenderMobDamage((EntityLivingBase)entity, " was smashed by ", source, wave.isFirst() ? (12 + this.field_70146_Z.nextInt(12)) : (6 + this.field_70146_Z.nextInt(6)));
          double x = entity.field_70165_t - this.field_70165_t;
          double z = entity.field_70161_v - this.field_70161_v;
          double d = Math.sqrt(x * x + z * z);
          entity.field_70159_w = x / d * 0.4D;
          entity.field_70181_x = 0.25D;
          entity.field_70179_y = z / d * 0.4D;
          if (entity instanceof EntityLivingBase) {
            EntityUtil.sendPlayerVelocityPacket(entity);
            EntityUtil.knockBackBlockingPlayer((Entity)this);
            if (this.field_70146_Z.nextInt(5) == 0)
              inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)entity, MobEffects.field_76438_s, 160, 1); 
          } 
        } 
      } 
    } 
  }
  
  public boolean func_96092_aw() {
    return false;
  }
  
  public void func_70645_a(DamageSource cause) {
    if (!this.field_70170_p.field_72995_K) {
      this.deathCause = cause;
      if (func_189748_bU() != null && func_189748_bU().func_76346_g() instanceof EntityLivingBase) {
        this.lastAttacker = (EntityLivingBase)func_189748_bU().func_76346_g();
      } else if (cause.func_76346_g() instanceof EntityLivingBase) {
        this.lastAttacker = (EntityLivingBase)cause.func_76346_g();
      } 
      if (this.field_70718_bc > 0)
        this.field_70718_bc += 140; 
    } 
  }
  
  public boolean leavesNoCorpse() {
    return false;
  }
  
  protected void func_70609_aI() {
    if (func_70027_ad()) {
      this.vanishTime++;
    } else if (this.vanishTime > 0) {
      this.vanishTime--;
    } 
    if (getLives() <= 0) {
      super.func_70609_aI();
      if (this.deathTicks == 40)
        super.func_70645_a(this.deathCause); 
    } else if (this.field_70122_E) {
      if (this.deathCause.func_76355_l() == "antimatter") {
        func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() * 2.0D);
        func_70606_j(func_110138_aP());
        this.field_70725_aQ = 0;
        this.field_70729_aU = false;
        setIsAntiMob(true);
        this.field_70173_aa = 0;
        this.field_70761_aq = this.field_70177_z = this.field_70759_as = 0.0F;
        this.field_70125_A = 0.0F;
        int i = this.field_70728_aV;
        while (i > 0 && !this.field_70170_p.field_72995_K) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
        } 
      } else {
        this.deathTicks++;
        if (this.deathTicks <= 80) {
          this.field_70725_aQ++;
        } else if (this.deathTicks >= 100) {
          this.field_70725_aQ--;
        } 
        if (this.field_70725_aQ <= -1) {
          this.field_70725_aQ = 0;
          this.deathTicks = 0;
          this.vanishTime = 0;
          if (!this.field_70170_p.field_72995_K) {
            setLives(getLives() - 1);
            if (this.lastAttacker != null) {
              func_70604_c(this.lastAttacker);
              this.lastAttacker.func_70604_c((EntityLivingBase)this);
            } 
            func_70606_j(Math.round(func_110138_aP() / 3.75F));
          } 
        } 
      } 
    } 
    if (!this.field_70122_E) {
      this.deathTicks = 0;
      this.field_70725_aQ = 0;
    } 
    if (this.vanishTime >= 100 && getLives() > 0) {
      if (!this.field_70170_p.field_72995_K) {
        if (EngenderConfig.general.useMessage && !isWild() && getOwner() instanceof EntityPlayerMP) {
          ((EntityPlayerMP)getOwner()).func_145747_a(func_110142_aN().func_151521_b());
          this.field_70170_p.func_184133_a((EntityPlayer)getOwner(), getOwner().func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), func_70647_i());
        } 
        setLives(0);
      } 
      this.field_70725_aQ = 0;
    } 
  }
  
  public int getSpawnTimer() {
    return 2;
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    float f = this.field_70170_p.func_175649_E(new BlockPos((Entity)this)).func_180168_b();
    if (func_70027_ad() && this.field_70146_Z.nextFloat() < f * 0.5F)
      entity.func_70015_d(5 * (int)f); 
    if (getZombieType() == 1 && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).field_70181_x += 0.2D;
      inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.field_76438_s, 200 * (int)f, 0);
      if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
        inflictCustomStatusEffect((this.field_70170_p.func_175659_aa() == EnumDifficulty.PEACEFUL) ? EnumDifficulty.PEACEFUL : EnumDifficulty.EASY, (EntityLivingBase)entity, MobEffects.field_76437_t, 200, 0); 
    } 
    if (getZombieType() == 2 && entity instanceof EntityLivingBase) {
      entity.field_70159_w = 0.0D;
      entity.field_70181_x = 0.0D;
      entity.field_70179_y = 0.0D;
      inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)entity, MobEffects.field_76421_d, 5, 1);
      if (entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70146_Z.nextInt(3) == 0)
        ((EntityLiving)entity).func_70624_b(null); 
    } 
    if (Loader.isModLoaded("abyssalcraft"))
      if (getZombieType() == 3 && entity instanceof EntityLivingBase) {
        entity.field_70159_w = 0.0D;
        entity.field_70181_x = 0.0D;
        entity.field_70179_y = 0.0D;
        inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)entity, MobEffects.field_76421_d, 5, 1);
        if (entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70146_Z.nextInt(3) == 0)
          ((EntityLiving)entity).func_70624_b(null); 
      }  
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    setAttackID(6);
    setLives(3);
    Biome biome = this.field_70170_p.func_180494_b(new BlockPos((Entity)this));
    if (biome instanceof net.minecraft.world.biome.BiomeDesert && this.field_70146_Z.nextInt(2) != 0) {
      setZombieType(1);
      this.field_70178_ae = true;
    } 
    if (biome instanceof net.minecraft.world.biome.BiomeSavanna && this.field_70146_Z.nextInt(2) != 0)
      setZombieType(2); 
    if (Loader.isModLoaded("abyssalcraft"))
      if (biome instanceof com.shinoow.abyssalcraft.common.world.biome.BiomeGenAbywasteland || (biome instanceof com.shinoow.abyssalcraft.common.world.biome.BiomeGenCorSwamp && this.field_70146_Z.nextInt(2) != 0) || this.field_71093_bK == ACLib.abyssal_wasteland_id)
        setZombieType(3);  
    if (getZombieType() == 3)
      if (Loader.isModLoaded("abyssalcraft") && ACConfig.hardcoreMode) {
        func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(375.0D);
        func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(48.0D);
      } else {
        func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(187.5D);
        func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(24.0D);
      }  
    return livingdata;
  }
  
  public void becomeAHero() {
    setIsHero(true);
    func_184185_a(ESound.hero, 100.0F, 1.0F);
    this.field_70173_aa = 0;
    setAttackID(6);
  }
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    super.func_70074_a(entityLivingIn);
    if ((this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager) {
      EntityVillager entityvillager = (EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.field_70170_p);
      entityzombie.field_70125_A = entityvillager.field_70125_A;
      entityzombie.field_70761_aq = entityzombie.field_70177_z = entityzombie.field_70759_as = entityvillager.field_70759_as;
      entityzombie.func_82149_j((Entity)entityLivingIn);
      this.field_70170_p.func_72900_e((Entity)entityLivingIn);
      entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityzombie)), null);
      entityzombie.setVillagerType(entityvillager.func_70946_n());
      entityzombie.func_94061_f(entityvillager.func_175446_cd());
      if (!isWild())
        entityzombie.setOwnerId(func_184753_b()); 
      if (entityvillager.func_145818_k_())
        entityzombie.func_96094_a(entityvillager.func_95999_t()); 
      this.field_70170_p.func_72838_d((Entity)entityzombie);
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
    } 
    if (entityLivingIn instanceof EntityVillager) {
      EntityVillager entityvillager = (EntityVillager)entityLivingIn;
      EntityZombie entityzombie = new EntityZombie(this.field_70170_p);
      entityzombie.field_70125_A = entityvillager.field_70125_A;
      entityzombie.field_70761_aq = entityzombie.field_70177_z = entityzombie.field_70759_as = entityvillager.field_70759_as;
      entityzombie.func_82149_j((Entity)entityLivingIn);
      this.field_70170_p.func_72900_e((Entity)entityLivingIn);
      entityzombie.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityzombie)), null);
      entityzombie.setVillagerType(entityvillager.getProfession());
      entityzombie.setGrowingAge(((EntityVillager)entityLivingIn).getGrowingAge());
      entityzombie.func_94061_f(entityvillager.func_175446_cd());
      if (!isWild())
        entityzombie.setOwnerId(func_184753_b()); 
      if (entityvillager.func_145818_k_())
        entityzombie.func_96094_a(entityvillager.func_95999_t()); 
      this.field_70170_p.func_72838_d((Entity)entityzombie);
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1026, new BlockPos((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v), 0);
    } 
  }
  
  protected void func_70629_bd() {
    this.field_70181_x += 0.03999999910593033D;
  }
  
  protected void func_180466_bG() {
    func_70629_bd();
  }
  
  private void setAttackID(int id) {
    this.attackID = id;
    this.attackTick = 0;
    this.field_70170_p.func_72960_a((Entity)this, (byte)id);
  }
  
  public boolean func_70601_bi() {
    return (super.func_70601_bi() && this.field_70170_p.func_175678_i(func_180425_c()));
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    switch (getZombieType()) {
      case 1:
        return I18n.func_74838_a("entity.MutantHuskHelpful.name");
      case 2:
        return I18n.func_74838_a("entity.MutantPrisonZombieHelpful.name");
      case 3:
        return I18n.func_74838_a("entity.MutantAbyssalZombieHelpful.name");
    } 
    return I18n.func_74838_a("entity." + s + ".name");
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74768_a("ZombieType", getZombieType());
    compound.func_74768_a("Lives", getLives());
    compound.func_74777_a("VanishTime", (short)this.vanishTime);
    if (!this.resurrections.isEmpty()) {
      NBTTagList nbtTagList = new NBTTagList();
      for (FriendlyZombieSummon resurrect : this.resurrections) {
        NBTTagCompound compound1 = NBTUtil.func_186859_a(resurrect.getPosition());
        compound1.func_74768_a("Tick", resurrect.getTick());
        nbtTagList.func_74742_a((NBTBase)compound1);
      } 
      compound.func_74782_a("Resurrections", (NBTBase)nbtTagList);
    } 
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    setLives(compound.func_74762_e("Lives"));
    setZombieType(compound.func_74762_e("ZombieType"));
    this.vanishTime = compound.func_74765_d("VanishTime");
    NBTTagList nbtTagList = compound.func_150295_c("Resurrections", 10);
    for (int i = 0; i < nbtTagList.func_74745_c(); i++) {
      NBTTagCompound compound1 = nbtTagList.func_150305_b(i);
      this.resurrections.add(i, new FriendlyZombieSummon(this.field_70170_p, NBTUtil.func_186861_c(compound1), compound1.func_74762_e("Tick")));
    } 
  }
  
  protected SoundEvent func_184639_G() {
    return MBSoundEvents.ENTITY_MUTANT_ZOMBIE_AMBIENT;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_ZOMBIE_HURT;
  }
  
  protected SoundEvent func_184615_bR() {
    return MBSoundEvents.ENTITY_MUTANT_ZOMBIE_DEATH;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187939_hm, 0.5F, 0.8F);
    func_184185_a(SoundEvents.field_187605_cG, 0.5F, func_70647_i());
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantZombie(this.field_70170_p);
  }
  
  protected ResourceLocation func_184647_J() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_zombie");
  }
  
  class MeleeGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    private double dirX;
    
    private double dirZ;
    
    public MeleeGoal() {
      this.dirX = -1.0D;
      this.dirZ = -1.0D;
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantZombie.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantZombie.this.attackID == 4);
    }
    
    public void func_75249_e() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.field_70699_by.func_75499_g();
      EntityMutantZombie.this.field_70757_a = -EntityMutantZombie.this.func_70627_aG();
      EntityMutantZombie.this.func_184185_a(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_ATTACK, 0.3F, EntityMutantZombie.this.func_70647_i());
    }
    
    public boolean func_75253_b() {
      return (EntityMutantZombie.this.attackTick < 25);
    }
    
    public void func_75246_d() {
      if (EntityMutantZombie.this.attackTick < 8)
        EntityMutantZombie.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F); 
      if (EntityMutantZombie.this.attackTick == 8) {
        double x = this.attackTarget.field_70165_t - EntityMutantZombie.this.field_70165_t;
        double z = this.attackTarget.field_70161_v - EntityMutantZombie.this.field_70161_v;
        double d = Math.sqrt(x * x + z * z);
        this.dirX = x / d;
        this.dirZ = z / d;
      } 
      if (EntityMutantZombie.this.func_70638_az() != null && EntityMutantZombie.this.attackTick == 12) {
        if (this.attackTarget != null && EntityMutantZombie.this.func_70032_d((Entity)this.attackTarget) <= EntityMutantZombie.this.field_70130_N + this.attackTarget.field_70130_N + 9.0F)
          EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " was smashed by ", DamageSource.func_76358_a((EntityLivingBase)EntityMutantZombie.this), (float)EntityMutantZombie.this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e()); 
        int x = MathHelper.func_76128_c(EntityMutantZombie.this.field_70165_t + this.dirX * 2.0D);
        int y = MathHelper.func_76128_c((EntityMutantZombie.this.func_174813_aQ()).field_72338_b);
        int z = MathHelper.func_76128_c(EntityMutantZombie.this.field_70161_v + this.dirZ * 2.0D);
        int x1 = MathHelper.func_76128_c(EntityMutantZombie.this.field_70165_t + this.dirX * 8.0D * EntityMutantZombie.this.field_70130_N);
        int z1 = MathHelper.func_76128_c(EntityMutantZombie.this.field_70161_v + this.dirZ * 8.0D * EntityMutantZombie.this.field_70130_N);
        Shockwave.createWaves(EntityMutantZombie.this.field_70170_p, EntityMutantZombie.this.seismicWavesList, x, z, x1, z1, y);
        EntityMutantZombie.this.func_184185_a(SoundEvents.field_187539_bB, 1.0F, 0.8F + EntityMutantZombie.this.field_70146_Z.nextFloat() * 0.4F);
      } 
    }
    
    public void func_75251_c() {
      EntityMutantZombie.this.setAttackID(0);
      this.attackTarget = null;
      this.dirX = -1.0D;
      this.dirZ = -1.0D;
    }
  }
  
  class RoarGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public RoarGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      if (EntityMutantZombie.this.getAttackID() == 6)
        return true; 
      this.attackTarget = EntityMutantZombie.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantZombie.this.field_70122_E && EntityMutantZombie.this.func_70068_e((Entity)this.attackTarget) > 16.0D) ? ((EntityMutantZombie.this.field_70146_Z.nextFloat() * 100.0F < 0.35F)) : false;
    }
    
    public void func_75249_e() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.setAttackID(6);
      EntityMutantZombie.this.field_70708_bq = 0;
      EntityMutantZombie.this.field_70757_a = -EntityMutantZombie.this.func_70627_aG();
    }
    
    public boolean func_75253_b() {
      return (EntityMutantZombie.this.attackTick < 120);
    }
    
    public void func_75246_d() {
      if (this.attackTarget != null && EntityMutantZombie.this.attackTick < 75)
        EntityMutantZombie.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F); 
      if (EntityMutantZombie.this.attackTick == 10) {
        EntityMutantZombie.this.func_184185_a(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_ROAR, 10.0F, EntityMutantZombie.this.func_70647_i());
        for (Entity entity : EntityMutantZombie.this.field_70170_p.func_175674_a((Entity)EntityMutantZombie.this, EntityMutantZombie.this.func_174813_aQ().func_186662_g((EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero()) ? 32.0D : 16.0D), EntitySelectors.field_188444_d)) {
          if (entity instanceof EntityLivingBase && !EntityMutantZombie.this.func_184191_r(entity)) {
            double x = entity.field_70165_t - EntityMutantZombie.this.field_70165_t;
            double z = entity.field_70161_v - EntityMutantZombie.this.field_70161_v;
            double d = Math.sqrt(x * x + z * z);
            entity.field_70159_w = x / d * 2.0D;
            entity.field_70181_x = 0.5D;
            entity.field_70179_y = z / d * 2.0D;
            entity.field_70160_al = true;
            if (entity instanceof EntityLivingBase)
              EntityMutantZombie.this.inflictEngenderMobDamage((EntityLivingBase)entity, " was yelled at to death by ", (new DamageSource("yell")).func_76348_h(), (EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero()) ? 16.0F : 4.0F); 
          } 
        } 
      } 
      if (EntityMutantZombie.this.attackTick >= 20 && EntityMutantZombie.this.attackTick < 80)
        if (EntityMutantZombie.this.attackTick % ((EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero()) ? 4 : (EntityMutantZombie.this.field_70170_p.func_72935_r() ? 20 : 10)) == 0) {
          int x = MathHelper.func_76128_c(EntityMutantZombie.this.field_70165_t);
          int y = MathHelper.func_76128_c((EntityMutantZombie.this.func_174813_aQ()).field_72338_b);
          int z = MathHelper.func_76128_c(EntityMutantZombie.this.field_70161_v);
          x += (1 + EntityMutantZombie.this.field_70146_Z.nextInt(8)) * (EntityMutantZombie.this.field_70146_Z.nextBoolean() ? 1 : -1);
          z += (1 + EntityMutantZombie.this.field_70146_Z.nextInt(8)) * (EntityMutantZombie.this.field_70146_Z.nextBoolean() ? 1 : -1);
          y = FriendlyZombieSummon.getSuitableGround(EntityMutantZombie.this.field_70170_p, x, y - 1, z);
          if (y != -1)
            EntityMutantZombie.this.resurrections.add(new FriendlyZombieSummon(EntityMutantZombie.this.field_70170_p, x, y, z)); 
        }  
      EntityMutantZombie.this.field_70699_by.func_75499_g();
    }
    
    public void func_75251_c() {
      this.attackTarget = null;
      EntityMutantZombie.this.setAttackID(0);
      if (EntityMutantZombie.this.getSpecialAttackTimer() <= 0 && EntityMutantZombie.this.isHero())
        EntityMutantZombie.this.setSpecialAttackTimer(600); 
    }
  }
  
  class ThrowAttackGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    private int hit;
    
    private int finish;
    
    public ThrowAttackGoal() {
      this.hit = -1;
      this.finish = -1;
      func_75248_a(7);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantZombie.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantZombie.this.attackID == 5);
    }
    
    public void func_75249_e() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.field_70699_by.func_75499_g();
      this.attackTarget.func_184210_p();
      EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " got tossed by ", (DamageSource)new EntityDamageSource("throw", (Entity)EntityMutantZombie.this), 4.0F);
      this.attackTarget.field_70761_aq = this.attackTarget.field_70177_z = this.attackTarget.field_70759_as = (float)MathHelper.func_181159_b(this.attackTarget.field_70179_y, this.attackTarget.field_70159_w) * 57.295776F - 90.0F;
    }
    
    public boolean func_75253_b() {
      return (EntityMutantZombie.this.attackID == 5 && this.finish < 10);
    }
    
    public void func_75246_d() {
      EntityMutantZombie.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F);
      if (EntityMutantZombie.this.attackTick == 2) {
        double x = this.attackTarget.field_70165_t - EntityMutantZombie.this.field_70165_t;
        double z = this.attackTarget.field_70161_v - EntityMutantZombie.this.field_70161_v;
        double d = Math.sqrt(x * x + z * z);
        this.attackTarget.field_70159_w = x / d;
        this.attackTarget.field_70181_x = EntityMutantZombie.this.field_70131_O;
        this.attackTarget.field_70179_y = z / d;
        this.attackTarget.field_70133_I = true;
      } 
      if (EntityMutantZombie.this.attackTick == 15) {
        double d1 = this.attackTarget.field_70165_t - EntityMutantZombie.this.field_70165_t;
        double d2 = this.attackTarget.field_70163_u - EntityMutantZombie.this.field_70163_u;
        double x = this.attackTarget.field_70161_v - EntityMutantZombie.this.field_70161_v;
        double z = Math.sqrt(d1 * d1 + d2 * d2 + x * x);
        EntityMutantZombie.this.field_70159_w = d1 / z * 5.0D;
        EntityMutantZombie.this.field_70181_x = d2 / z * EntityMutantZombie.this.field_70131_O;
        EntityMutantZombie.this.field_70179_y = x / z * 5.0D;
      } else if (EntityMutantZombie.this.attackTick > 15) {
        double d1 = (EntityMutantZombie.this.field_70130_N * 2.0F * EntityMutantZombie.this.field_70130_N * 2.0F);
        double d2 = EntityMutantZombie.this.func_70092_e(this.attackTarget.field_70165_t, (this.attackTarget.func_174813_aQ()).field_72338_b, this.attackTarget.field_70161_v);
        if ((d2 < d1 || EntityMutantZombie.this.field_70122_E || EntityMutantZombie.this.field_70163_u >= this.attackTarget.field_70163_u) && this.hit == -1) {
          this.hit = 0;
          EntityMutantZombie.this.setThrowAttackHit(true);
          EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " was smashed by ", (DamageSource)new EntityDamageSource("throw", (Entity)EntityMutantZombie.this), (float)EntityMutantZombie.this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * EntityMutantZombie.this.field_70131_O);
          double x = this.attackTarget.field_70165_t - EntityMutantZombie.this.field_70165_t;
          double z = this.attackTarget.field_70161_v - EntityMutantZombie.this.field_70161_v;
          double d = Math.sqrt(x * x + z * z);
          this.attackTarget.field_70159_w = x / d;
          this.attackTarget.field_70181_x = -EntityMutantZombie.this.field_70131_O;
          this.attackTarget.field_70179_y = z / d;
          this.attackTarget.field_70172_ad = 10;
          this.attackTarget.field_70133_I = true;
          EntityUtil.disableShield(this.attackTarget, 150);
          EntityMutantZombie.this.func_184185_a(MBSoundEvents.ENTITY_MUTANT_ZOMBIE_GRUNT, 0.3F, EntityMutantZombie.this.func_70647_i() - 0.2F);
          this.attackTarget.field_70761_aq = this.attackTarget.field_70177_z = this.attackTarget.field_70759_as = (float)MathHelper.func_181159_b(this.attackTarget.field_70179_y, this.attackTarget.field_70159_w) * 57.295776F - 90.0F;
        } 
        if (this.hit >= 0)
          this.hit++; 
        if ((EntityMutantZombie.this.field_70122_E || EntityMutantZombie.this.func_70090_H() || EntityMutantZombie.this.func_180799_ab()) && this.finish == -1) {
          this.finish = 0;
          EntityMutantZombie.this.setThrowAttackFinished(true);
        } 
        if (this.finish >= 0)
          this.finish++; 
      } 
    }
    
    public void func_75251_c() {
      EntityMutantZombie.this.setAttackID(0);
      this.attackTarget = null;
      this.hit = -1;
      this.finish = -1;
      EntityMutantZombie.this.setThrowAttackHit(false);
      EntityMutantZombie.this.setThrowAttackFinished(false);
    }
  }
  
  class TossAttackGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public TossAttackGoal() {
      func_75248_a(7);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantZombie.this.func_70638_az();
      return (this.attackTarget != null && this.attackTarget.field_70122_E && EntityMutantZombie.this.attackID == 7);
    }
    
    public void func_75249_e() {
      EntityMutantZombie.this.attackTick = 0;
      EntityMutantZombie.this.field_70699_by.func_75499_g();
      this.attackTarget.func_184210_p();
      EntityMutantZombie.this.inflictEngenderMobDamage(this.attackTarget, " got tossed by ", (DamageSource)new EntityDamageSource("throw", (Entity)EntityMutantZombie.this), 5.0F);
      this.attackTarget.field_70761_aq = this.attackTarget.field_70177_z = this.attackTarget.field_70759_as = (float)MathHelper.func_181159_b(this.attackTarget.field_70179_y, this.attackTarget.field_70159_w) * 57.295776F - 90.0F;
    }
    
    public boolean func_75253_b() {
      return (EntityMutantZombie.this.attackTick < 10);
    }
    
    public void func_75246_d() {
      EntityMutantZombie.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F);
      if (EntityMutantZombie.this.attackTick == 1) {
        double x = this.attackTarget.field_70165_t - EntityMutantZombie.this.field_70165_t;
        double z = this.attackTarget.field_70161_v - EntityMutantZombie.this.field_70161_v;
        double d = Math.sqrt(x * x + z * z);
        this.attackTarget.field_70159_w = x / d;
        this.attackTarget.field_70181_x = EntityMutantZombie.this.field_70131_O;
        this.attackTarget.field_70179_y = z / d;
        this.attackTarget.field_70133_I = true;
      } 
    }
    
    public void func_75251_c() {
      EntityMutantZombie.this.setAttackID(0);
      this.attackTarget = null;
      EntityMutantZombie.this.func_70624_b(null);
    }
  }
}
