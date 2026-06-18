package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.brandon3055.brandonscore.utils.Utils;
import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityDragonFireballOther;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class EntityChaosGuardian extends EntityEnderDragon {
  public int homeX = 0;
  
  public int homeY = -1;
  
  public int homeZ = 0;
  
  public boolean homeSet = false;
  
  public double targetX;
  
  public double targetY;
  
  public double targetZ;
  
  public boolean forceNewTarget;
  
  private int nextAttackTimer = 100;
  
  private int attackInProgress = -1;
  
  private int attackTimer = 0;
  
  private EnumBehaviour previousBehaviour = EnumBehaviour.ROAMING;
  
  private int ignitionChargeTimer = 0;
  
  private static final int ATTACK_FIREBALL_CHARGE = 0;
  
  private static final int ATTACK_FIREBALL_CHASER = 1;
  
  private static final int ATTACK_ENERGY_CHASER = 2;
  
  private static final int ATTACK_CHAOS_CHASER = 3;
  
  private static final int ATTACK_TELEPORT = 4;
  
  private static final int ATTACK_LIGHTNING_BOLT = 5;
  
  private static final int ATTACK_ENDER_CHARGE = 6;
  
  private static final int ATTACK_WILD_CARD = 7;
  
  private static final int ATTACK_HEAVY_WIND = 8;
  
  public float circlePosition = 0.0F;
  
  public float circleDirection = 1.0F;
  
  public EnumBehaviour behaviour = EnumBehaviour.ROAMING;
  
  private static final List<WeightedAttack> weightedAttacks = Lists.newArrayList((Object[])new WeightedAttack[] { new WeightedAttack(16, 0), new WeightedAttack(14, 1), new WeightedAttack(12, 2), new WeightedAttack(10, 3), new WeightedAttack(5, 5), new WeightedAttack(10, 6) });
  
  private static final List<WeightedAttack> weightedLowHealthAttaxks = Lists.newArrayList((Object[])new WeightedAttack[] { new WeightedAttack(5, 1), new WeightedAttack(5, 4), new WeightedAttack(10, 2), new WeightedAttack(15, 3), new WeightedAttack(15, 5), new WeightedAttack(105, 6) });
  
  private static final List<WeightedBehaviour> weightedBehaviours = Lists.newArrayList((Object[])new WeightedBehaviour[] { new WeightedBehaviour(1, EnumBehaviour.LOW_HEALTH_STRATEGY), new WeightedBehaviour(10, EnumBehaviour.GUARDING), new WeightedBehaviour(20, EnumBehaviour.CHARGING), new WeightedBehaviour(12, EnumBehaviour.FIREBOMB), new WeightedBehaviour(20, EnumBehaviour.CIRCLE_PLAYER) });
  
  public EntityChaosGuardian(World par1World) {
    super(par1World);
    this.bossInfo.func_186745_a(BossInfo.Color.RED);
    this.bossInfo.func_186741_a(true);
    this.bossInfo.func_186746_a(BossInfo.Overlay.NOTCHED_20);
    setLevel(300);
    this.homeX = (par1World.func_72890_a((Entity)this, -1.0D) != null) ? (int)(par1World.func_72890_a((Entity)this, -1.0D)).field_70165_t : (int)this.field_70165_t;
    this.homeY = (par1World.func_72890_a((Entity)this, -1.0D) != null) ? ((int)(par1World.func_72890_a((Entity)this, -1.0D)).field_70163_u + 40) : (int)this.field_70163_u;
    this.homeZ = (par1World.func_72890_a((Entity)this, -1.0D) != null) ? (int)(par1World.func_72890_a((Entity)this, -1.0D)).field_70161_v : (int)this.field_70161_v;
    func_70107_b((this.homeX + 200), (this.homeY + 100), (this.homeZ + 200));
    this.targetX = this.homeX;
    this.targetY = this.homeY;
    this.targetZ = this.homeZ;
    func_70642_aH();
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "chaos_guardian";
  }
  
  public int playMusic() {
    return 10;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186741_a(true);
    if (func_110143_aJ() > func_110138_aP() * 0.75D && this.bossInfo.func_186736_g() != BossInfo.Color.RED)
      this.bossInfo.func_186745_a(BossInfo.Color.RED); 
    if (func_110143_aJ() < func_110138_aP() * 0.75D && func_110143_aJ() > func_110138_aP() / 2.0F && this.bossInfo.func_186736_g() != BossInfo.Color.PURPLE)
      this.bossInfo.func_186745_a(BossInfo.Color.PURPLE); 
    if (func_110143_aJ() < func_110138_aP() / 2.0F && func_110143_aJ() > func_110138_aP() / 4.0F && this.bossInfo.func_186736_g() != BossInfo.Color.PINK)
      this.bossInfo.func_186745_a(BossInfo.Color.PINK); 
    if (func_110143_aJ() < func_110138_aP() / 4.0F && func_110143_aJ() > 0.0F && this.bossInfo.func_186736_g() != BossInfo.Color.WHITE)
      this.bossInfo.func_186745_a(BossInfo.Color.WHITE); 
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(2000.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(50.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(20.0D);
  }
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 100.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 100.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 100.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public void func_70636_d() {
    if (this.field_70173_aa % 10 == 0)
      func_70691_i(1.0F); 
    if (this.field_70181_x >= 2.0D || this.field_70181_x <= -2.0D)
      this.field_70181_x = 0.0D; 
    if (func_70089_S() && isCarryingCrystal())
      func_70606_j(func_110143_aJ() + 0.1F); 
    if (this.innerRotation > 500)
      this.innerRotation = 0; 
    if (func_70638_az() != null && getJukeboxToDanceTo() == null)
      func_70625_a((Entity)func_70638_az(), 10.0F, 90.0F); 
    if (func_70638_az() != null && (!func_70638_az().func_70089_S() || func_184191_r((Entity)func_70638_az())))
      func_70624_b(null); 
    if (func_70638_az() == null)
      this.behaviour = EnumBehaviour.GO_HOME; 
    if (this.moralRaisedTimer <= 0)
      this.moralRaisedTimer = 0; 
    if (this.moralRaisedTimer > 0)
      this.moralRaisedTimer--; 
    if (func_175446_cd()) {
      func_94061_f(func_175446_cd());
      this.field_70172_ad = this.field_70771_an;
      if (this.field_70173_aa > 21)
        this.field_70173_aa--; 
    } else {
      if (func_110143_aJ() > func_110138_aP() / 5.0F)
        this.field_70715_bh.func_75774_a(); 
      if (!this.field_70170_p.field_72995_K) {
        if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && this.isOffensive && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()))
          if (func_70068_e((Entity)func_70638_az()) < (this.reachWidth * this.reachWidth + ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N) * ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N)) + 9.0D && (this.field_70173_aa + func_145782_y()) % 10 == 0)
            func_70652_k((Entity)func_70638_az());  
        if (func_70089_S()) {
          ChunkLoadingEvent.updateLoaded((Entity)this);
        } else {
          ChunkLoadingEvent.stopLoading((Entity)this);
        } 
      } 
    } 
    func_174810_b(func_175446_cd());
    this.field_70703_bu = false;
    this.field_70160_al = false;
    this.field_70122_E = true;
    if (getJukeboxToDanceTo() != null) {
      func_70661_as().func_75499_g();
      IBlockState iblockstate = this.field_70170_p.func_180495_p(getJukeboxToDanceTo());
      Block block = iblockstate.func_177230_c();
      if (this.field_70173_aa > 100)
        this.field_70173_aa = 20; 
      if (this.innerRotation > 500)
        this.innerRotation = 0; 
      this.sitting = true;
      this.field_70159_w = this.field_70181_x = this.field_70179_y *= 0.0D;
      func_70634_a(getJukeboxToDanceTo().func_177958_n(), getJukeboxToDanceTo().func_177956_o() + 12.0D, getJukeboxToDanceTo().func_177952_p());
      if (block != Blocks.field_150421_aI || (block == Blocks.field_150421_aI && !((Boolean)iblockstate.func_177229_b((IProperty)BlockJukebox.field_176432_a)).booleanValue()) || func_174831_c(this.jukeBoxToDanceTo) > 10000.0D) {
        setJukeboxToDanceTo(null);
        this.sitting = false;
      } 
    } 
    if (getJukeboxToDanceTo() == null && this.field_70173_aa % 60 == 0) {
      int i11 = MathHelper.func_76128_c(this.field_70163_u);
      int l1 = MathHelper.func_76128_c(this.field_70165_t);
      int i2 = MathHelper.func_76128_c(this.field_70161_v);
      for (int k2 = -12 - (int)this.field_70130_N; k2 <= 12 + (int)this.field_70130_N; k2++) {
        for (int l2 = -12 - (int)this.field_70130_N; l2 <= 12 + (int)this.field_70130_N; l2++) {
          for (int k = -18 - (int)this.field_70131_O; k <= 18 + (int)this.field_70131_O; k++) {
            int i3 = l1 + k2;
            int m = i11 + k;
            int n = i2 + l2;
            BlockPos blockpos = new BlockPos(i3, m, n);
            IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            if (block == Blocks.field_150421_aI && ((Boolean)iblockstate.func_177229_b((IProperty)BlockJukebox.field_176432_a)).booleanValue()) {
              setJukeboxToDanceTo(blockpos);
              if (this.field_70173_aa > 100)
                this.field_70173_aa = 20; 
              if (this.innerRotation > 500)
                this.innerRotation = 0; 
            } 
          } 
        } 
      } 
    } 
    if (!isWild()) {
      this.homeX = (int)(getOwner()).field_70165_t;
      this.homeY = (int)(getOwner()).field_70163_u + 5;
      this.homeZ = (int)(getOwner()).field_70161_v;
    } else {
      this.homeX = (int)this.field_70165_t;
      this.homeY = (int)this.field_70163_u;
      this.homeZ = (int)this.field_70161_v;
    } 
    if (!this.homeSet) {
      this.homeX = (int)this.field_70165_t;
      this.homeY = (int)this.field_70163_u;
      this.homeZ = (int)this.field_70161_v;
      this.targetX = this.homeX;
      this.targetY = this.homeY;
      this.targetZ = this.homeZ;
      this.homeSet = true;
    } 
    float moveSpeedMultiplier = this.behaviour.dragonSpeed;
    if (this.field_70170_p.field_72995_K) {
      float f = MathHelper.func_76134_b(this.animTime * 3.1415927F * 2.0F);
      float f1 = MathHelper.func_76134_b(this.prevAnimTime * 3.1415927F * 2.0F);
      if (f1 <= -0.3F && f >= -0.3F)
        if (this.deathTicks <= 0)
          if (func_70093_af()) {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), 1.0F, 0.6F + this.field_70146_Z.nextFloat() * 0.3F, false);
          } else {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, SoundCategory.HOSTILE, 5.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false);
          }   
    } 
    this.prevAnimTime = this.animTime;
    if (!this.field_70170_p.field_72995_K) {
      updateTarget();
      customAIUpdate();
      if (this.behaviour == EnumBehaviour.FIREBOMB && Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.homeX, (this.homeY + 30), this.homeZ) <= 3.0D)
        moveSpeedMultiplier = 0.0F; 
    } 
    if (func_110143_aJ() <= 0.0F) {
      this.behaviour = EnumBehaviour.DEAD;
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
    if (func_175446_cd()) {
      this.animTime = 0.0F;
    } else if (func_70089_S()) {
      if (this.ringBufferIndex < 0)
        for (int k = 0; k < this.ringBuffer.length; k++) {
          this.ringBuffer[k][0] = this.field_70177_z;
          this.ringBuffer[k][1] = this.field_70163_u;
        }  
      if (++this.ringBufferIndex == this.ringBuffer.length)
        this.ringBufferIndex = 0; 
      this.ringBuffer[this.ringBufferIndex][0] = this.field_70177_z;
      this.ringBuffer[this.ringBufferIndex][1] = this.field_70163_u;
      float f = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
      f *= (moveSpeedMultiplier == 0.0F) ? 1.0F : moveSpeedMultiplier;
      f *= (float)Math.pow(2.0D, this.field_70181_x);
      if (func_184207_aI() || getJukeboxToDanceTo() != null) {
        this.animTime += 0.1F;
      } else if (this.slowed) {
        this.animTime += f * 0.5F;
      } else {
        this.animTime += f;
      } 
      if (this.field_70170_p.field_72995_K) {
        if (this.field_70716_bi > 0) {
          double d10 = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / this.field_70716_bi;
          double d0 = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / this.field_70716_bi;
          double d1 = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / this.field_70716_bi;
          double d2 = MathHelper.func_76138_g(this.field_184626_bk - this.field_70177_z);
          this.field_70177_z = (float)(this.field_70177_z + d2 / this.field_70716_bi);
          this.field_70125_A = (float)(this.field_70125_A + (this.field_70709_bj - this.field_70125_A) / this.field_70716_bi);
          this.field_70716_bi--;
          func_70107_b(d10, d0, d1);
          func_70101_b(this.field_70177_z, this.field_70125_A);
        } 
      } else {
        double d10 = this.targetX - this.field_70165_t;
        double d0 = this.targetY - this.field_70163_u;
        double d1 = this.targetZ - this.field_70161_v;
        double d2 = d10 * d10 + d0 * d0 + d1 * d1;
        if (func_70093_af() && func_70638_az() == null) {
          this.targetX = this.homeX;
          this.targetY = this.homeY;
          this.targetZ = this.homeZ;
        } 
        if (func_70638_az() != null) {
          if (this.behaviour == EnumBehaviour.CIRCLE_PLAYER) {
            this.targetX = (func_70638_az()).field_70165_t + (int)(Math.cos(this.circlePosition) * 60.0D);
            this.targetZ = (func_70638_az()).field_70161_v + (int)(Math.sin(this.circlePosition) * 60.0D);
            moveSpeedMultiplier = 1.0F + Math.min((float)Utils.getDistanceAtoB(this.targetX, this.targetZ, this.field_70165_t, this.field_70161_v) / 50.0F * 3.0F, 3.0F);
          } else {
            this.targetX = (func_70638_az()).field_70165_t;
            this.targetZ = (func_70638_az()).field_70161_v;
          } 
          double d3 = this.targetX - this.field_70165_t;
          double d5 = this.targetZ - this.field_70161_v;
          double d7 = Math.sqrt(d3 * d3 + d5 * d5);
          double d8 = 0.4000000059604645D + d7 / 80.0D - 1.0D;
          if (d8 > 10.0D)
            d8 = 10.0D; 
          this.targetY = (func_70638_az().func_174813_aQ()).field_72338_b + d8 + ((this.behaviour == EnumBehaviour.CIRCLE_PLAYER) ? 50 : 10);
        } else if (this.behaviour != EnumBehaviour.FIREBOMB) {
          this.targetX += this.field_70146_Z.nextGaussian() * 2.0D;
          this.targetZ += this.field_70146_Z.nextGaussian() * 2.0D;
        } 
        if (this.forceNewTarget || d2 < 100.0D || d2 > 22500.0D || this.field_70123_F || this.field_70124_G)
          setNewTarget(); 
        d0 /= MathHelper.func_76133_a(d10 * d10 + d1 * d1);
        float f12 = 0.6F;
        if (d0 < -f12)
          d0 = -f12; 
        if (d0 > f12)
          d0 = f12; 
        this.field_70181_x += d0 * 0.10000000149011612D;
        this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
        double d4 = 180.0D - Math.atan2(d10, d1) * 180.0D / Math.PI;
        double d6 = MathHelper.func_76138_g(d4 - this.field_70177_z);
        if (d6 > 50.0D)
          d6 = 50.0D; 
        if (d6 < -50.0D)
          d6 = -50.0D; 
        Vec3d vec3 = (new Vec3d(this.targetX - this.field_70165_t, this.targetY - this.field_70163_u, this.targetZ - this.field_70161_v)).func_72432_b();
        Vec3d vec32 = (new Vec3d(MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F), this.field_70181_x, -MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F))).func_72432_b();
        float f5 = (float)(vec32.func_72430_b(vec3) + 0.5D) / 1.5F;
        if (f5 < 0.0F)
          f5 = 0.0F; 
        this.field_70704_bt *= 0.8F;
        float f6 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 1.0F + 1.0F;
        double d9 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 1.0D + 1.0D;
        if (d9 > 40.0D)
          d9 = 40.0D; 
        this.field_70704_bt = (float)(this.field_70704_bt + d6 * 0.699999988079071D / d9 / f6);
        this.field_70177_z += this.field_70704_bt * 0.1F;
        float f1 = (float)(2.0D / (d9 + 1.0D));
        float f8 = 0.06F;
        func_191958_b(0.0F, 0.0F, -1.0F, 0.06F * (f1 * f8 + 1.0F - f8));
        if (func_70093_af()) {
          func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 0.375D : 0.25D), this.field_70181_x * (func_184207_aI() ? 0.375D : 0.25D), this.field_70179_y * (func_184207_aI() ? 0.375D : 0.25D));
        } else if (this.slowed) {
          func_70091_d(MoverType.SELF, this.field_70159_w * 0.800000011920929D * moveSpeedMultiplier, this.field_70181_x * 0.800000011920929D * moveSpeedMultiplier, this.field_70179_y * 0.800000011920929D * moveSpeedMultiplier);
        } else if (this.moralRaisedTimer > 200) {
          func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 3.0D : 2.0D), this.field_70181_x * (func_184207_aI() ? 3.0D : 2.0D), this.field_70179_y * (func_184207_aI() ? 3.0D : 2.0D));
        } else {
          func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 1.5D : 1.0D), this.field_70181_x * (func_184207_aI() ? 1.5D : 1.0D), this.field_70179_y * (func_184207_aI() ? 1.5D : 1.0D));
        } 
        Vec3d vec31 = (new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y)).func_72432_b();
        float f9 = (float)(vec31.func_72430_b(vec32) + 1.0D) / 2.0F;
        f9 = 0.8F + 0.15F * f9;
        this.field_70159_w *= f9;
        this.field_70179_y *= f9;
        this.field_70181_x *= 0.9100000262260437D;
      } 
    } 
    this.dragonPartHead.field_70130_N = this.dragonPartHead.field_70131_O = 2.5F;
    this.dragonPartNeck.field_70130_N = this.dragonPartNeck.field_70131_O = 2.5F;
    this.dragonPartTail1.field_70130_N = this.dragonPartTail1.field_70131_O = 2.0F;
    this.dragonPartTail2.field_70130_N = this.dragonPartTail2.field_70131_O = 2.0F;
    this.dragonPartTail3.field_70130_N = this.dragonPartTail3.field_70131_O = 2.0F;
    this.dragonPartBody.field_70131_O = 3.5F;
    this.dragonPartBody.field_70130_N = 5.0F;
    this.dragonPartWing1.field_70131_O = 3.0F;
    this.dragonPartWing1.field_70130_N = 4.0F;
    this.dragonPartWing2.field_70131_O = 3.0F;
    this.dragonPartWing2.field_70130_N = 4.0F;
    Vec3d[] avec3d = new Vec3d[this.dragonPartArray.length];
    for (int j = 0; j < this.dragonPartArray.length; j++)
      avec3d[j] = new Vec3d((this.dragonPartArray[j]).field_70165_t, (this.dragonPartArray[j]).field_70163_u, (this.dragonPartArray[j]).field_70161_v); 
    float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F * 0.017453292F;
    float f16 = MathHelper.func_76134_b(f14);
    float f18 = MathHelper.func_76126_a(f14);
    float f7 = this.field_70177_z * 0.017453292F;
    float f19 = MathHelper.func_76126_a(f7);
    float f3 = MathHelper.func_76134_b(f7);
    double[] adouble = getMovementOffsets(5, 1.0F);
    double[] adouble1 = getMovementOffsets(14, 1.0F);
    this.dragonPartBody.func_70071_h_();
    this.dragonPartBody.func_70012_b(this.field_70165_t, this.field_70163_u - (MathHelper.func_76126_a(this.animTime * 6.2831855F - 0.5F) * 0.1F), this.field_70161_v, 0.0F, 0.0F);
    this.dragonPartWing1.func_70071_h_();
    this.dragonPartWing1.func_70012_b(this.field_70165_t + (f3 * 4.5F), this.field_70163_u + 1.0D + (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 3.0F), this.field_70161_v + (f19 * 4.5F), 0.0F, 0.0F);
    this.dragonPartWing2.func_70071_h_();
    this.dragonPartWing2.func_70012_b(this.field_70165_t - (f3 * 4.5F), this.field_70163_u + 1.0D + (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 3.0F), this.field_70161_v - (f19 * 4.5F), 0.0F, 0.0F);
    this.dragonPartNeck.func_70071_h_();
    this.dragonPartNeck.func_70012_b(this.field_70165_t + (f19 * 3.5F), this.field_70163_u + 1.0D - (MathHelper.func_76126_a(this.animTime * 6.2831855F + 1.0F) * 0.1F) + (f18 * 2.0F) - (this.field_70125_A / 90.0F) * Math.PI * 0.25D, this.field_70161_v - (f3 * 3.5F), 0.0F, 0.0F);
    this.dragonPartHead.func_70071_h_();
    this.dragonPartHead.func_70012_b(this.field_70165_t + (f19 * 6.0F), this.field_70163_u + 1.0D - (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 0.1F) + (f18 * 4.0F) - (this.field_70125_A / 90.0F) * Math.PI * 1.0D, this.field_70161_v - (f3 * 6.0F), 0.0F, 0.0F);
    for (int i = 0; i < 3; i++) {
      ExtendMultiPartEntityPart extendMultiPartEntityPart;
      MultiPartEntityPart MultiPartEntityPart = null;
      if (i == 0)
        extendMultiPartEntityPart = this.dragonPartTail1; 
      if (i == 1)
        extendMultiPartEntityPart = this.dragonPartTail2; 
      if (i == 2)
        extendMultiPartEntityPart = this.dragonPartTail3; 
      adouble1 = getMovementOffsets(12 + i * 2, 1.0F);
      float f21 = this.field_70177_z * 0.017453292F + simplifyAngle(adouble1[0] - adouble[0]) * 0.017453292F;
      float f22 = MathHelper.func_76126_a(f21);
      float f71 = MathHelper.func_76134_b(f21);
      float f23 = 1.5F;
      float f24 = (i + 1) * 2.0F;
      extendMultiPartEntityPart.func_70071_h_();
      extendMultiPartEntityPart.func_70012_b(this.field_70165_t - ((f19 * f23 + f22 * f24) * f16), this.field_70163_u - (MathHelper.func_76126_a(this.animTime * 6.2831855F + i) * 0.2F * (i + 1)) - (f18 * (2.0F + (1 * i))) + f23, this.field_70161_v + ((f3 * f23 + f71 * f24) * f16), 0.0F, 0.0F);
    } 
    for (int l = 0; l < this.dragonPartArray.length; l++) {
      (this.dragonPartArray[l]).field_70169_q = (avec3d[l]).field_72450_a;
      (this.dragonPartArray[l]).field_70167_r = (avec3d[l]).field_72448_b;
      (this.dragonPartArray[l]).field_70166_s = (avec3d[l]).field_72449_c;
    } 
    if (!this.field_70170_p.field_72995_K) {
      collideWithEntities(this.dragonPartHead, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartHead.func_174813_aQ().func_186662_g(1.0D)));
      collideWithEntities(this.dragonPartNeck, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartNeck.func_174813_aQ().func_186662_g(1.0D)));
      collideWithEntities(this.dragonPartBody, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartBody.func_174813_aQ().func_186662_g(1.0D)));
      flingEntities(this.dragonPartWing1, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartWing1.func_174813_aQ().func_186662_g(4.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
      flingEntities(this.dragonPartWing2, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartWing2.func_174813_aQ().func_186662_g(4.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
      collideWithEntities(this.dragonPartTail1, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail1.func_174813_aQ().func_186662_g(1.0D)));
      collideWithEntities(this.dragonPartTail2, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail2.func_174813_aQ().func_186662_g(1.0D)));
      collideWithEntities(this.dragonPartTail3, this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail3.func_174813_aQ().func_186662_g(1.0D)));
      attackEntitiesInList(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartHead.func_174813_aQ().func_186662_g(3.0D)));
      attackEntitiesInList(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartNeck.func_174813_aQ().func_186662_g(2.0D)));
    } 
    destroyBlocksInAABB(this.dragonPartHead.func_174813_aQ());
    destroyBlocksInAABB(this.dragonPartNeck.func_174813_aQ());
    destroyBlocksInAABB(this.dragonPartBody.func_174813_aQ());
    destroyBlocksInAABB(this.dragonPartWing1.func_174813_aQ());
    destroyBlocksInAABB(this.dragonPartWing2.func_174813_aQ());
    destroyBlocksInAABB(this.dragonPartTail1.func_174813_aQ());
    destroyBlocksInAABB(this.dragonPartTail2.func_174813_aQ());
    destroyBlocksInAABB(this.dragonPartTail3.func_174813_aQ());
    if (!this.field_70170_p.field_72995_K && func_70089_S() && func_110143_aJ() < func_110138_aP() && this.field_70737_aN <= 5)
      func_70691_i((func_70638_az() != null || func_70644_a(MobEffects.field_76438_s)) ? 0.02F : 0.1F); 
    if (!isWild() && func_70068_e((Entity)getOwner()) >= 48400.0D) {
      this.behaviour = EnumBehaviour.GUARDING;
      func_70012_b((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v, this.field_70177_z, this.field_70125_A);
    } 
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && this.field_70146_Z.nextInt(40) == 0) {
      List<EntityLivingBase> entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_70638_az().func_174813_aQ().func_186662_g(3.0D));
      for (EntityLivingBase entityLivingBase : entities) {
        if (!func_184191_r((Entity)entityLivingBase))
          fireLightning((Entity)entityLivingBase, this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u + 0.25D, this.dragonPartHead.field_70161_v); 
      } 
    } 
    if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)func_184179_bs();
      passenger.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 40, 4));
      this.field_70761_aq = this.field_70177_z = passenger.field_70759_as + 180.0F;
      this.field_70125_A = 0.0F;
      for (int k = 0; k < 256; k++) {
        double d1 = 0.0075D;
        if (this.moralRaisedTimer > 200)
          d1 *= 2.0D; 
        if (func_70051_ag())
          d1 *= 2.0D; 
        Vec3d vec3 = passenger.func_70676_i(1.0F);
        if (passenger.field_191988_bg > 0.0F) {
          func_70107_b(this.field_70165_t + vec3.field_72450_a * d1, this.field_70163_u + vec3.field_72448_b * d1, this.field_70161_v + vec3.field_72449_c * d1);
          Entity[] aentity = func_70021_al();
          if (aentity != null)
            for (Entity entity : aentity)
              entity.func_70012_b(entity.field_70165_t + vec3.field_72450_a * d1, entity.field_70163_u + vec3.field_72448_b * d1, entity.field_70161_v + vec3.field_72449_c * d1, 0.0F, 0.0F);  
        } 
        if (passenger.field_191988_bg < 0.0F)
          func_70107_b(this.field_70165_t - vec3.field_72450_a * d1, this.field_70163_u - vec3.field_72448_b * d1, this.field_70161_v - vec3.field_72449_c * d1); 
      } 
    } 
    if (!isWild() && getOwner().func_70051_ag()) {
      func_70031_b(true);
    } else {
      func_70031_b(false);
    } 
    if (!isWild() && getOwner().func_70093_af()) {
      func_70095_a(true);
    } else {
      func_70095_a(false);
    } 
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && this.field_70146_Z.nextInt(120) == 0) {
      EntityGuardianProjectile projectile;
      double distance;
      switch (this.field_70146_Z.nextInt(4)) {
        case 0:
          projectile = new EntityGuardianProjectile(this.field_70170_p, 1, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 8.0F, (Entity)this);
          projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
          projectile.shooter = (Entity)this;
          projectile.target = func_70638_az();
          this.field_70170_p.func_72838_d(projectile);
          distance = Utils.getDistanceAtoB((func_70638_az()).field_70165_t, (func_70638_az()).field_70161_v, this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70161_v);
          this.field_70125_A = (float)Math.toDegrees(Math.atan2((func_70638_az()).field_70163_u - this.dragonPartHead.field_70163_u, distance)) * -1.0F;
          break;
        case 1:
          projectile = new EntityGuardianProjectile(this.field_70170_p, 3, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 2.0F, (Entity)this);
          projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
          projectile.shooter = (Entity)this;
          projectile.target = func_70638_az();
          this.field_70170_p.func_72838_d(projectile);
          break;
        case 2:
          projectile = new EntityGuardianProjectile(this.field_70170_p, 4, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 10.0F, (Entity)this);
          projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
          projectile.shooter = (Entity)this;
          projectile.target = func_70638_az();
          this.field_70170_p.func_72838_d(projectile);
          break;
        case 3:
          projectile = new EntityGuardianProjectile(this.field_70170_p, 5, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 10.0F, (Entity)this);
          projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
          projectile.shooter = (Entity)this;
          projectile.target = func_70638_az();
          this.field_70170_p.func_72838_d(projectile);
          break;
      } 
    } 
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az().func_70089_S() && this.field_70146_Z.nextInt(200) == 0 && func_70032_d((Entity)func_70638_az()) >= 100.0D) {
      this.behaviour = EnumBehaviour.CHARGING;
      this.targetX = (func_70638_az()).field_70165_t;
      this.targetY = (func_70638_az()).field_70163_u;
      this.targetZ = (func_70638_az()).field_70161_v;
    } 
    if (this.attackInProgress != -1 && this.nextAttackTimer <= 0)
      this.attackInProgress = -1; 
  }
  
  private void customAIUpdate() {
    if (func_110143_aJ() > 0.0F && func_110143_aJ() < func_110138_aP() * 0.2F)
      this.behaviour = EnumBehaviour.LOW_HEALTH_STRATEGY; 
    switch (this.behaviour) {
      case ROAMING:
        if (Utils.getDistanceAtoB(this.field_70165_t, this.field_70161_v, this.homeX, this.homeZ) < 200.0D)
          selectNewBehaviour(); 
        break;
      case GO_HOME:
        if (Utils.getDistanceAtoB(this.field_70165_t, this.field_70161_v, this.homeX, this.homeZ) < 70.0D)
          selectNewBehaviour(); 
        break;
      case GUARDING:
        break;
      case CHARGING:
        if (Utils.getDistanceAtoB(this.field_70165_t, this.field_70161_v, this.homeX, this.homeZ) > 300.0D)
          this.behaviour = EnumBehaviour.GO_HOME; 
        break;
      case CIRCLE_PLAYER:
        this.circlePosition += 0.02F * this.circleDirection;
        if (Utils.getDistanceAtoB(this.field_70165_t, this.field_70161_v, this.homeX, this.homeZ) > 300.0D || this.field_70163_u > 250.0D)
          this.behaviour = EnumBehaviour.GO_HOME; 
        break;
      case LOW_HEALTH_STRATEGY:
        if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 60.0D && this.attackInProgress != 4) {
          int escape = 0;
          while (escape < 50) {
            this.targetX = this.homeX + (this.field_70146_Z.nextDouble() - 0.5D) * 220.0D;
            this.targetY = (this.homeY + 30) + this.field_70146_Z.nextDouble() * 20.0D;
            this.targetZ = this.homeZ + (this.field_70146_Z.nextDouble() - 0.5D) * 220.0D;
            escape++;
          } 
          func_70624_b(null);
        } 
        break;
      default:
        func_70624_b(null);
        this.targetX = this.homeX;
        this.targetY = this.homeY;
        this.targetZ = this.homeZ;
        break;
    } 
    if (this.behaviour == EnumBehaviour.DEAD)
      return; 
    if (this.field_70173_aa % 1000 == 0 && this.field_70146_Z.nextBoolean())
      this.ignitionChargeTimer = 0; 
    if (this.field_70173_aa % ((this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) ? 1000 : 2000) == 0)
      selectNewBehaviour(); 
    if (this.ignitionChargeTimer > 1 || (this.ignitionChargeTimer == 1 && this.field_70173_aa % 20 == 0))
      this.ignitionChargeTimer--; 
    if (this.ignitionChargeTimer <= 0 && !this.field_70170_p.field_72995_K)
      if ((this.field_70173_aa - 19) % 20 == 0)
        this.ignitionChargeTimer = ((this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) ? 1000 : 2000) + this.field_70146_Z.nextInt(600);  
    updateAttack();
  }
  
  private void updateAttack() {
    if (this.field_70170_p.field_72995_K || this.behaviour == EnumBehaviour.DEAD)
      return; 
    if (this.behaviour == EnumBehaviour.FIREBOMB && Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.homeX, (this.homeY + 30), this.homeZ) <= 3.0D) {
      if (func_70638_az() == null || this.field_70173_aa % 100 == 0)
        setNewTarget(); 
      if (func_70638_az() != null) {
        double distance = Utils.getDistanceAtoB((func_70638_az()).field_70165_t, (func_70638_az()).field_70161_v, this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70161_v);
        if (Utils.getDistanceAtoB((func_70638_az()).field_70165_t, (func_70638_az()).field_70161_v, this.field_70165_t, this.field_70161_v) < 5.0D)
          distance *= -1.0D; 
        float anglePitch = (float)Math.toDegrees(Math.atan2((func_70638_az()).field_70163_u - this.dragonPartHead.field_70163_u, distance)) * -1.0F;
        float angleYaw = (float)Math.toDegrees(Math.atan2((func_70638_az()).field_70165_t - this.dragonPartHead.field_70165_t, (func_70638_az()).field_70161_v - this.field_70161_v)) * -1.0F;
        this.field_70125_A = anglePitch;
        if (Utils.getDistanceAtoB((func_70638_az()).field_70165_t, (func_70638_az()).field_70161_v, this.field_70165_t, this.field_70161_v) > 8.0D)
          this.field_70177_z = angleYaw + 180.0F; 
        if (this.field_70173_aa % 2 == 0) {
          EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.field_70170_p, 1, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 8.0F, (Entity)this);
          projectile.func_70107_b(this.dragonPartHead.field_70165_t + Math.cos(((this.field_70177_z - 90.0F) / 180.0F * 3.1415927F)) * 2.0D, this.dragonPartHead.field_70163_u + 1.5D, this.dragonPartHead.field_70161_v + Math.sin(((this.field_70177_z - 90.0F) / 180.0F * 3.1415927F)) * 2.0D);
          this.field_70170_p.func_72838_d(projectile);
        } 
      } 
    } else if (this.nextAttackTimer > 0 && func_70638_az() != null) {
      this.nextAttackTimer--;
    } else if (this.nextAttackTimer == 0) {
      if (this.attackInProgress == -1) {
        selectNewAttack();
        switch (this.attackInProgress) {
          case 0:
            this.attackTimer = 100 + this.field_70146_Z.nextInt(100);
            this.previousBehaviour = this.behaviour;
            this.behaviour = EnumBehaviour.CHARGING;
            break;
          case 1:
            this.attackTimer = 60 + this.field_70146_Z.nextInt(60);
            break;
          case 2:
            this.attackTimer = 60 + this.field_70146_Z.nextInt(60);
            break;
          case 3:
            this.attackTimer = 60 + this.field_70146_Z.nextInt(60);
            break;
          case 4:
            this.attackTimer = 10 + this.field_70146_Z.nextInt(10);
            break;
          case 5:
            this.attackTimer = 120 + this.field_70146_Z.nextInt(60);
            break;
          case 6:
            this.attackTimer = 120 + this.field_70146_Z.nextInt(60);
            break;
          case 7:
            this.attackTimer = 80 + this.field_70146_Z.nextInt(40);
            break;
          case 8:
            this.attackTimer = 80;
            this.previousBehaviour = this.behaviour;
            this.behaviour = EnumBehaviour.CHARGING;
            break;
        } 
      } 
      switch (this.attackInProgress) {
        case 0:
          if (func_70638_az() != null && Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, (func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v) > 10.0D) {
            if (this.attackTimer % 2 == 0) {
              EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.field_70170_p, 1, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 8.0F, (Entity)this);
              projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
              projectile.shooter = (Entity)this;
              projectile.target = func_70638_az();
              this.field_70170_p.func_72838_d(projectile);
            } 
            double distance = Utils.getDistanceAtoB((func_70638_az()).field_70165_t, (func_70638_az()).field_70161_v, this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70161_v);
            this.field_70125_A = (float)Math.toDegrees(Math.atan2((func_70638_az()).field_70163_u - this.dragonPartHead.field_70163_u, distance)) * -1.0F;
            break;
          } 
          this.attackTimer = 0;
          break;
        case 1:
          if (func_70638_az() != null && this.attackTimer % 10 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.field_70170_p, 3, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 2.0F, (Entity)this);
            projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
            projectile.shooter = (Entity)this;
            projectile.target = func_70638_az();
            this.field_70170_p.func_72838_d(projectile);
          } 
          break;
        case 2:
          if (func_70638_az() != null && this.attackTimer % 10 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.field_70170_p, 4, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 10.0F, (Entity)this);
            projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
            projectile.shooter = (Entity)this;
            projectile.target = func_70638_az();
            this.field_70170_p.func_72838_d(projectile);
          } 
          break;
        case 3:
          if (func_70638_az() != null && this.attackTimer % 10 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.field_70170_p, 5, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 10.0F, (Entity)this);
            projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
            projectile.shooter = (Entity)this;
            projectile.target = func_70638_az();
            this.field_70170_p.func_72838_d(projectile);
          } 
          break;
        case 4:
          if (func_70638_az() == null) {
            this.attackInProgress = -1;
            return;
          } 
          if (func_70638_az() != null && Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, (func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v) > 15.0D) {
            if (this.attackTimer % 2 == 0) {
              EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.field_70170_p, 2, func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 8.0F, (Entity)this);
              projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
              projectile.shooter = (Entity)this;
              projectile.target = func_70638_az();
              this.field_70170_p.func_72838_d(projectile);
            } 
            double distance = Utils.getDistanceAtoB((func_70638_az()).field_70165_t, (func_70638_az()).field_70161_v, this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70161_v);
            this.field_70125_A = (float)Math.toDegrees(Math.atan2((func_70638_az()).field_70163_u - this.dragonPartHead.field_70163_u, distance)) * -1.0F;
            break;
          } 
          this.attackTimer = 0;
          break;
        case 5:
          if (func_70638_az() != null && this.attackTimer % 20 == 0) {
            List<EntityLivingBase> entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_70638_az().func_174813_aQ().func_186662_g(3.0D));
            for (EntityLivingBase entityLivingBase : entities) {
              if (!func_184191_r((Entity)entityLivingBase))
                fireLightning((Entity)entityLivingBase, this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u + 0.25D, this.dragonPartHead.field_70161_v); 
            } 
          } 
          break;
        case 6:
          if (func_70638_az() != null && Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, (func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v) > 15.0D)
            if (this.attackTimer % 5 == 0) {
              double d6 = this.dragonPartHead.field_70165_t;
              double d7 = this.dragonPartHead.field_70163_u + 0.5D;
              double d8 = this.dragonPartHead.field_70161_v;
              double d9 = (func_70638_az()).field_70165_t - d6;
              double d10 = (func_70638_az()).field_70163_u + 1.0D - d7;
              double d11 = (func_70638_az()).field_70161_v - d8;
              this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
              EntityDragonFireballOther entitydragonfireball = new EntityDragonFireballOther(this.field_70170_p, (EntityLivingBase)this, d9, d10, d11);
              entitydragonfireball.field_70165_t = d6;
              entitydragonfireball.field_70163_u = d7;
              entitydragonfireball.field_70161_v = d8;
              this.field_70170_p.func_72838_d((Entity)entitydragonfireball);
            }  
          break;
        case 7:
          if (func_70638_az() != null && this.attackTimer % 2 == 0) {
            EntityGuardianProjectile projectile = new EntityGuardianProjectile(this.field_70170_p, 1 + this.field_70146_Z.nextInt(5), func_70638_az(), 5.0F + this.field_70146_Z.nextFloat() * 10.0F, (Entity)this);
            projectile.func_70107_b(this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70163_u, this.dragonPartHead.field_70161_v);
            projectile.shooter = (Entity)this;
            projectile.target = func_70638_az();
            this.field_70170_p.func_72838_d(projectile);
          } 
          break;
        case 8:
          if (func_70638_az() != null && Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, (func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v) > 10.0D) {
            this.animTime += 0.25F;
            if (this.attackTimer % 20 == 0) {
              func_184185_a(SoundEvents.field_187524_aN, func_70599_aP(), func_70647_i() - 0.25F);
              List<EntityLivingBase> entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(16.0D));
              for (EntityLivingBase entityLivingBase : entities) {
                if (!func_184191_r((Entity)entityLivingBase)) {
                  double d0 = entityLivingBase.field_70165_t - this.field_70165_t;
                  double d1 = entityLivingBase.field_70161_v - this.field_70161_v;
                  double d2 = MathHelper.func_76132_a(d0, d1);
                  d2 = MathHelper.func_76133_a(d2);
                  d0 /= d2;
                  d1 /= d2;
                  double d3 = 1.0D / d2;
                  if (d3 > 1.0D)
                    d3 = 1.0D; 
                  d0 *= d3;
                  d1 *= d3;
                  d0 *= 1.750000000745058D;
                  d1 *= 1.750000000745058D;
                  if (!entityLivingBase.func_184207_aI())
                    entityLivingBase.func_70024_g(d0, 1.0D, d1); 
                } 
              } 
            } 
            double distance = Utils.getDistanceAtoB((func_70638_az()).field_70165_t, (func_70638_az()).field_70161_v, this.dragonPartHead.field_70165_t, this.dragonPartHead.field_70161_v);
            this.field_70125_A = (float)Math.toDegrees(Math.atan2((func_70638_az()).field_70163_u - this.dragonPartHead.field_70163_u, distance)) * -1.0F;
          } 
          break;
      } 
      this.attackTimer--;
      if (this.attackTimer <= -1) {
        if (this.attackInProgress == 0)
          this.behaviour = this.previousBehaviour; 
        this.attackInProgress = -1;
        this.nextAttackTimer = -1;
      } 
    } else {
      this.nextAttackTimer = (this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) ? (10 + this.field_70146_Z.nextInt(50)) : (200 + this.field_70146_Z.nextInt(400));
    } 
  }
  
  private void selectNewAttack() {
    if (this.behaviour == EnumBehaviour.DEAD)
      return; 
    if (this.behaviour == EnumBehaviour.LOW_HEALTH_STRATEGY) {
      this.attackInProgress = ((WeightedAttack)WeightedRandom.func_76271_a(this.field_70146_Z, weightedLowHealthAttaxks)).attack;
    } else if (this.behaviour != EnumBehaviour.FIREBOMB) {
      this.attackInProgress = ((WeightedAttack)WeightedRandom.func_76271_a(this.field_70146_Z, weightedAttacks)).attack;
    } else {
      this.attackInProgress = 2;
    } 
  }
  
  private void selectNewBehaviour() {
    if (this.field_70170_p.field_72995_K || this.behaviour == EnumBehaviour.DEAD)
      return; 
    EnumBehaviour newBehaviour = this.behaviour;
    for (; newBehaviour == this.behaviour; newBehaviour = ((WeightedBehaviour)WeightedRandom.func_76271_a(this.field_70146_Z, weightedBehaviours)).randomBehaviour);
    this.behaviour = newBehaviour;
    this.previousBehaviour = this.behaviour;
  }
  
  private void updateTarget() {
    switch (this.behaviour) {
      case FIREBOMB:
        if (Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.homeX, (this.homeY + 30), this.homeZ) > 3.0D) {
          this.targetX = this.homeX;
          this.targetY = (this.homeY + 30);
          this.targetZ = this.homeZ;
        } 
        break;
      case DEAD:
        this.targetX = this.homeX;
        this.targetY = this.homeY;
        this.targetZ = this.homeZ;
        func_70624_b(null);
        break;
    } 
  }
  
  private void setNewTarget() {
    if (this.behaviour == EnumBehaviour.DEAD)
      return; 
    this.forceNewTarget = false;
    switch (this.behaviour) {
      case ROAMING:
        this.targetX = (this.homeX + this.field_70146_Z.nextFloat() * 120.0F - 60.0F);
        this.targetY = (this.homeY + 60) + this.field_70146_Z.nextDouble() * 40.0D;
        this.targetZ = (this.homeZ + this.field_70146_Z.nextFloat() * 120.0F - 60.0F);
        break;
      case GO_HOME:
        this.targetX = this.homeX;
        this.targetY = (this.homeY + 30) + this.field_70146_Z.nextDouble() * 30.0D;
        this.targetZ = this.homeZ;
      case GUARDING:
        this.targetX = this.homeX;
        this.targetY = (this.homeY + 5) + this.field_70146_Z.nextDouble() * 5.0D;
        this.targetZ = this.homeZ;
        func_70624_b(null);
        break;
      case CHARGING:
        if (func_70638_az() != null) {
          this.targetX = (func_70638_az()).field_70165_t + (this.field_70146_Z.nextFloat() * 60.0F) - 30.0D;
          this.targetY = (func_70638_az()).field_70163_u + 10.0D + this.field_70146_Z.nextDouble() * 10.0D;
          this.targetZ = (func_70638_az()).field_70161_v + (this.field_70146_Z.nextFloat() * 60.0F) - 30.0D;
        } 
      case CIRCLE_PLAYER:
        if (func_70638_az() != null) {
          this.targetX = (func_70638_az()).field_70165_t + (this.field_70146_Z.nextFloat() * 120.0F) - 60.0D;
          this.targetY = (func_70638_az()).field_70163_u + 30.0D + this.field_70146_Z.nextDouble() * 30.0D;
          this.targetZ = (func_70638_az()).field_70161_v + (this.field_70146_Z.nextFloat() * 120.0F) - 60.0D;
        } 
        break;
      case LOW_HEALTH_STRATEGY:
        this.targetX = (this.homeX + this.field_70146_Z.nextFloat() * 60.0F - 30.0F);
        this.targetY = (this.homeY + 30) + this.field_70146_Z.nextDouble() * 20.0D;
        this.targetZ = (this.homeZ + this.field_70146_Z.nextFloat() * 60.0F - 30.0F);
        break;
      case FIREBOMB:
        if (func_70638_az() != null) {
          this.targetX = (func_70638_az()).field_70165_t + (this.field_70146_Z.nextFloat() * 120.0F) - 60.0D;
          this.targetY = (func_70638_az()).field_70163_u + 20.0D + this.field_70146_Z.nextDouble() * 20.0D;
          this.targetZ = (func_70638_az()).field_70161_v + (this.field_70146_Z.nextFloat() * 120.0F) - 60.0D;
        } 
        break;
      case DEAD:
        this.targetX = this.homeX;
        this.targetY = this.homeY;
        this.targetZ = this.homeZ;
        break;
    } 
  }
  
  public boolean func_70965_a(MultiPartEntityPart part, DamageSource damageSource, float dmg) {
    if (this.behaviour == EnumBehaviour.DEAD)
      return false; 
    if (damageSource.func_76355_l() == "chaosImplosion" || damageSource.func_76355_l() == "de.GuardianFireball" || damageSource.func_76355_l() == "de.GuardianEnergyBall" || damageSource.func_76355_l() == "de.GuardianChaosBall")
      dmg *= 0.15F; 
    switch (this.behaviour) {
      case GUARDING:
        if (this.field_70146_Z.nextInt(5) == 0)
          selectNewBehaviour(); 
        break;
      case CHARGING:
        if (this.field_70146_Z.nextInt(6) == 0)
          selectNewBehaviour(); 
        break;
      case CIRCLE_PLAYER:
        if (this.field_70146_Z.nextInt(6) == 0) {
          selectNewBehaviour();
          break;
        } 
        if (this.field_70146_Z.nextInt(4) == 0)
          this.circleDirection *= -1.0F; 
        break;
      case LOW_HEALTH_STRATEGY:
        if (this.field_70146_Z.nextInt(6) == 0 && func_110143_aJ() >= func_110138_aP() * 0.2F)
          selectNewBehaviour(); 
        if (this.attackInProgress != 4) {
          int escape = 0;
          while (escape < 50) {
            this.targetX = this.homeX + (this.field_70146_Z.nextDouble() - 0.5D) * 260.0D;
            this.targetY = (this.homeY + 20) + (this.field_70146_Z.nextDouble() - 0.5D) * 50.0D;
            this.targetZ = this.homeZ + (this.field_70146_Z.nextDouble() - 0.5D) * 260.0D;
            escape++;
          } 
          func_70624_b(null);
        } 
        break;
      case FIREBOMB:
        if ((func_70638_az() == null && Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.homeX, (this.homeY + 30), this.homeZ) <= 3.0D) || this.field_70146_Z.nextInt(5) == 0)
          selectNewBehaviour(); 
        if (damageSource.func_76346_g() instanceof EntityLivingBase && damageSource.func_76346_g() != func_70638_az() && this.field_70170_p.func_72933_a(new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v), new Vec3d((damageSource.func_76346_g()).field_70165_t, (damageSource.func_76346_g()).field_70163_u, (damageSource.func_76346_g()).field_70161_v)) == null)
          func_70624_b((EntityLivingBase)damageSource.func_76346_g()); 
        break;
    } 
    return super.func_70965_a(part, damageSource, dmg);
  }
  
  public void setToGuard() {
    this.behaviour = EnumBehaviour.GUARDING;
  }
  
  private enum EnumBehaviour {
    ROAMING(1.0F),
    GO_HOME(1.3F),
    GUARDING(0.8F),
    CHARGING(2.0F),
    FIREBOMB(1.5F),
    CIRCLE_PLAYER(1.2F),
    LOW_HEALTH_STRATEGY(2.0F),
    DEAD(0.5F);
    
    public float dragonSpeed;
    
    EnumBehaviour(float dragonSpeed) {
      this.dragonSpeed = dragonSpeed;
    }
  }
  
  private static class WeightedAttack extends WeightedRandom.Item {
    public int attack;
    
    public WeightedAttack(int weight, int attack) {
      super(weight);
      this.attack = attack;
    }
  }
  
  private static class WeightedBehaviour extends WeightedRandom.Item {
    public EntityChaosGuardian.EnumBehaviour randomBehaviour;
    
    public WeightedBehaviour(int weight, EntityChaosGuardian.EnumBehaviour randomBehaviour) {
      super(weight);
      this.randomBehaviour = randomBehaviour;
    }
  }
  
  protected void func_70609_aI() {
    if (!isWild()) {
      this.homeX = (int)(getOwner()).field_70165_t;
      this.homeY = (int)(getOwner()).field_70163_u + 5;
      this.homeZ = (int)(getOwner()).field_70161_v;
    } 
    double d0 = this.homeX - this.field_70165_t;
    double d1 = this.homeY - this.field_70163_u;
    double d2 = this.homeZ - this.field_70161_v;
    double d3 = d0 * d0 + d2 * d2;
    if (this.field_70173_aa > 20)
      if (d3 > 1.0D && this.deathTicks < 1) {
        double d5 = MathHelper.func_76133_a(d3);
        this.field_70761_aq = this.field_70177_z = this.field_70759_as = (float)Math.atan2(this.field_70179_y, this.field_70159_w) * 57.295776F - 90.0F + 180.0F;
        this.field_70159_w += d0 / d5 * 0.75D - this.field_70159_w;
        this.field_70181_x += d1 / d5 * 0.75D - this.field_70181_x;
        this.field_70179_y += d2 / d5 * 0.75D - this.field_70179_y;
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      } else {
        func_70091_d(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
        this.field_70761_aq = this.field_70759_as = this.field_70177_z += 10.0F;
        this.deathTicks++;
      }  
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (!this.field_70170_p.field_72995_K && this.deathTicks == 1) {
      if (Loader.isModLoaded("draconicevolution")) {
        Entity var8 = EntityList.func_188429_b(new ResourceLocation("draconicevolution:dragonheartitem"), this.field_70170_p);
        this.field_70170_p.func_72838_d(var8);
      } 
      if (Loader.isModLoaded("draconicevolution")) {
        Entity var8 = EntityList.func_188429_b(new ResourceLocation("draconicevolution:dragonheartitem"), this.field_70170_p);
        this.field_70170_p.func_72838_d(var8);
      } 
      this.field_70170_p.func_175669_a(1028, new BlockPos((Entity)this), 0);
      if (getOwner() != null) {
        for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
          this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), SoundEvents.field_187522_aL, func_184176_by(), func_70599_aP(), 1.0F);
          entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s " + func_70005_c_() + " has been killed!!!", new Object[0]), true);
        } 
        ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your Chaos Guardian has fallen in battle.", new Object[0]));
      } 
    } 
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      EntityLightningBolt bolt = new EntityLightningBolt(this.field_70170_p, this.homeX, (this.homeY + 1), this.homeZ, true);
      bolt.field_70158_ak = true;
      this.field_70170_p.func_72942_c((Entity)bolt);
    } 
    if (this.deathTicks == 200 && !this.field_70170_p.field_72995_K) {
      int i = 200000;
      while (i > 0) {
        int j = EntityXPOrb.func_70527_a(i);
        i -= j;
        this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
      } 
      func_184185_a(ESound.blast, 100.0F, 0.75F);
      func_70106_y();
      List<EntityLivingBase> entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(200.0D));
      for (EntityLivingBase entity : entities) {
        if (!entity.func_180427_aV() && !func_184191_r((Entity)entity))
          entity.func_70097_a((new EntityDamageSource("explosion.player", (Entity)this)).func_94540_d().func_76348_h().func_151518_m(), 10000.0F); 
      } 
    } 
  }
  
  private float simplifyAngle(double par1) {
    return (float)MathHelper.func_76138_g(par1);
  }
  
  public NBTTagCompound func_189511_e(NBTTagCompound compound) {
    super.func_189511_e(compound);
    compound.func_74768_a("HomeXCoord", this.homeX);
    compound.func_74768_a("HomeYCoord", this.homeY);
    compound.func_74768_a("HomeZCoord", this.homeZ);
    compound.func_74778_a("Behaviour", this.behaviour.name());
    compound.func_74757_a("HomeSet", this.homeSet);
    return compound;
  }
  
  public void func_70020_e(NBTTagCompound compound) {
    super.func_70020_e(compound);
    this.homeX = compound.func_74762_e("HomeXCoord");
    this.homeY = compound.func_74762_e("HomeYCoord");
    this.homeZ = compound.func_74762_e("HomeZCoord");
    if (compound.func_74764_b("Behaviour"))
      this.behaviour = EnumBehaviour.valueOf(compound.func_74779_i("Behaviour")); 
    this.homeSet = compound.func_74767_n("HomeSet");
    this.targetX = this.homeX;
    this.targetZ = this.homeZ;
  }
}
