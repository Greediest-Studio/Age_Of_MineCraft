package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import java.util.Iterator;
import java.util.List;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.IPhaseAsorah;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseFireballAndStrafe;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseListAsorah;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseManagerAsorah;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases.PhaseRamAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathHeap;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDragonBoss extends EntityTameBase implements IEntityMultiPart, Massive, Armored, Flying, Undead {
  public static final DataParameter<Integer> PHASE = EntityDataManager.func_187226_a(EntityDragonBoss.class, DataSerializers.field_187192_b);
  
  public double[][] ringBuffer = new double[64][3];
  
  public int ringBufferIndex = -1;
  
  public ExtendMultiPartEntityPart[] dragonPartArray;
  
  public ExtendMultiPartEntityPart dragonPartHead;
  
  public ExtendMultiPartEntityPart dragonPartNeck;
  
  public ExtendMultiPartEntityPart dragonPartBody;
  
  public ExtendMultiPartEntityPart dragonPartTail1;
  
  public ExtendMultiPartEntityPart dragonPartTail2;
  
  public ExtendMultiPartEntityPart dragonPartTail3;
  
  public ExtendMultiPartEntityPart dragonPartWing1;
  
  public ExtendMultiPartEntityPart dragonPartWing2;
  
  public float prevAnimTime;
  
  public float animTime;
  
  public boolean slowed;
  
  private final PhaseManagerAsorah phaseManager;
  
  private int field_184678_bK = 200;
  
  private int sittingDamageReceived;
  
  private final PathPoint[] pathPoints = new PathPoint[24];
  
  private final int[] neighbors = new int[24];
  
  private final PathHeap pathFindQueue = new PathHeap();
  
  public EntityDragonMinion healingcircle;
  
  public EntityDragonBoss(World par1World) {
    super(par1World);
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 9.0F, 9.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 9.0F, 9.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 12.0F, 12.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 6.0F, 6.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 6.0F, 6.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 6.0F, 6.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 6.0F, 6.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 6.0F, 6.0F) };
    func_70606_j(func_110138_aP());
    func_70105_a(24.0F, 5.4F);
    this.field_70145_X = true;
    this.phaseManager = new PhaseManagerAsorah(this);
    getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    this.field_70158_ak = true;
    this.field_70178_ae = true;
    this.isOffensive = true;
  }
  
  public int playMusic() {
    return 6;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public PhaseManagerAsorah getPhaseManager() {
    return this.phaseManager;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186741_a(true);
    if (func_110143_aJ() > func_110138_aP() * 0.75D && this.bossInfo.func_186736_g() != BossInfo.Color.BLUE)
      this.bossInfo.func_186745_a(BossInfo.Color.BLUE); 
    if (func_110143_aJ() < func_110138_aP() * 0.75D && func_110143_aJ() > func_110138_aP() / 2.0F && this.bossInfo.func_186736_g() != BossInfo.Color.GREEN)
      this.bossInfo.func_186745_a(BossInfo.Color.GREEN); 
    if (func_110143_aJ() < func_110138_aP() / 2.0F && func_110143_aJ() > func_110138_aP() / 4.0F && this.bossInfo.func_186736_g() != BossInfo.Color.YELLOW)
      this.bossInfo.func_186745_a(BossInfo.Color.YELLOW); 
    if (func_110143_aJ() < func_110138_aP() / 4.0F && func_110143_aJ() > 0.0F && this.bossInfo.func_186736_g() != BossInfo.Color.RED)
      this.bossInfo.func_186745_a(BossInfo.Color.RED); 
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 3.0F;
  }
  
  public float getBonusVSMassive() {
    return 2.0F;
  }
  
  public float func_70047_e() {
    return 3.4875F;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public String func_70005_c_() {
    return TextFormatting.AQUA + super.func_70005_c_();
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(800.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(400.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0D);
    } 
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(100.0D);
    func_110148_a(SharedMonsterAttributes.field_188790_f).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(25.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(PHASE, Integer.valueOf(PhaseListAsorah.HOLDING_PATTERN.getId()));
  }
  
  public double[] getMovementOffsets(int par1, float par2) {
    if (func_110143_aJ() <= 0.0F)
      par2 = 0.0F; 
    par2 = 1.0F - par2;
    int j = this.ringBufferIndex - par1 * 1 & 0x3F;
    int k = this.ringBufferIndex - par1 * 1 - 1 & 0x3F;
    double[] adouble = new double[3];
    double d0 = this.ringBuffer[j][0];
    double d1 = MathHelper.func_76138_g(this.ringBuffer[k][0] - d0);
    adouble[0] = d0 + d1 * par2;
    d0 = this.ringBuffer[j][1];
    d1 = this.ringBuffer[k][1] - d0;
    adouble[1] = d0 + d1 * par2;
    adouble[2] = this.ringBuffer[j][2] + (this.ringBuffer[k][2] - this.ringBuffer[j][2]) * par2;
    return adouble;
  }
  
  protected boolean func_70692_ba() {
    return false;
  }
  
  public void func_70625_a(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
    double d1, d0 = entityIn.field_70165_t - this.field_70165_t;
    double d2 = entityIn.field_70161_v - this.field_70161_v;
    if (entityIn instanceof EntityLivingBase) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
      d1 = entitylivingbase.field_70163_u + entitylivingbase.func_70047_e() - this.field_70163_u + func_70047_e();
    } else {
      d1 = ((entityIn.func_174813_aQ()).field_72338_b + (entityIn.func_174813_aQ()).field_72337_e) / 2.0D - this.field_70163_u + func_70047_e();
    } 
    double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
    float f = (float)(MathHelper.func_181159_b(d2, d0) * 57.29577951308232D) - 90.0F;
    float f1 = (float)-(MathHelper.func_181159_b(d1, d3) * 57.29577951308232D);
    this.field_70125_A = updateRotation(this.field_70125_A, f1, maxPitchIncrease);
    this.field_70759_as = updateRotation(this.field_70759_as, f, maxYawIncrease);
  }
  
  private float updateRotation(float angle, float targetAngle, float maxIncrease) {
    float f = MathHelper.func_76142_g(targetAngle - angle);
    if (f > maxIncrease)
      f = maxIncrease; 
    if (f < -maxIncrease)
      f = -maxIncrease; 
    return angle + f;
  }
  
  public void func_70636_d() {
    if (this.convertionInt > 0) {
      this.phaseManager.setPhase(PhaseListAsorah.LANDING);
      this.field_70159_w *= 0.75D;
      this.field_70181_x *= 0.75D;
      this.field_70179_y *= 0.75D;
    } 
    if (this.convertionInt > 0 && this.field_70173_aa % 10 == 0) {
      EntityPlayer player = this.field_70170_p.func_72890_a((Entity)this, -1.0D);
      if (player != null)
        for (int i1 = 0; i1 < this.convertionInt; i1++) {
          float f1 = i1 * 3.1415927F / timesToConvert() * 0.5F;
          this.field_70170_p.func_175682_a(EnumParticleTypes.END_ROD, true, this.field_70165_t + MathHelper.func_76134_b(f1) * 12.0D, this.field_70163_u + this.field_70131_O + 1.0D, this.field_70161_v + MathHelper.func_76126_a(f1) * 12.0D, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
        }  
    } 
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
    if (this.field_70170_p.field_72995_K) {
      float f = MathHelper.func_76134_b(this.animTime * 3.1415927F * 2.0F);
      float f1 = MathHelper.func_76134_b(this.prevAnimTime * 3.1415927F * 2.0F);
      if (f1 <= -0.3F && f >= -0.3F)
        this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), func_70599_aP(), 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false); 
    } 
    this.field_70703_bu = false;
    this.field_70160_al = false;
    this.field_70122_E = true;
    if (getJukeboxToDanceTo() != null) {
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
      func_70661_as().func_75499_g();
      IBlockState iblockstate = this.field_70170_p.func_180495_p(getJukeboxToDanceTo());
      Block block = iblockstate.func_177230_c();
      if (this.field_70173_aa > 40)
        this.field_70173_aa = 0; 
      if (block != Blocks.field_150421_aI || (block == Blocks.field_150421_aI && !((Boolean)iblockstate.func_177229_b((IProperty)BlockJukebox.field_176432_a)).booleanValue()) || func_174831_c(this.jukeBoxToDanceTo) > 10000.0D) {
        setJukeboxToDanceTo(null);
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
      } 
    } 
    if (getJukeboxToDanceTo() == null && this.field_70173_aa % 60 == 0) {
      int i11 = MathHelper.func_76128_c(this.field_70163_u);
      int l1 = MathHelper.func_76128_c(this.field_70165_t);
      int i2 = MathHelper.func_76128_c(this.field_70161_v);
      boolean flag = false;
      for (int k2 = -12 - (int)this.field_70130_N; k2 <= 12 + (int)this.field_70130_N; k2++) {
        for (int l2 = -12 - (int)this.field_70130_N; l2 <= 12 + (int)this.field_70130_N; l2++) {
          for (int k = -12 - (int)this.field_70131_O; k <= 12 + (int)this.field_70131_O; k++) {
            int i3 = l1 + k2;
            int m = i11 + k;
            int n = i2 + l2;
            BlockPos blockpos = new BlockPos(i3, m, n);
            IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
            Block block = iblockstate.func_177230_c();
            if (block == Blocks.field_150421_aI && ((Boolean)iblockstate.func_177229_b((IProperty)BlockJukebox.field_176432_a)).booleanValue()) {
              setJukeboxToDanceTo(blockpos);
              getPhaseManager().setPhase(PhaseListAsorah.SITTING_SCANNING);
            } 
          } 
        } 
      } 
    } 
    this.dragonPartHead.field_70130_N = this.dragonPartHead.field_70131_O = 3.75F;
    this.dragonPartNeck.field_70130_N = this.dragonPartNeck.field_70131_O = 3.75F;
    this.dragonPartTail1.field_70130_N = this.dragonPartTail1.field_70131_O = 3.0F;
    this.dragonPartTail2.field_70130_N = this.dragonPartTail2.field_70131_O = 3.0F;
    this.dragonPartTail3.field_70130_N = this.dragonPartTail3.field_70131_O = 3.0F;
    this.dragonPartBody.field_70131_O = 5.25F;
    this.dragonPartBody.field_70130_N = 7.5F;
    this.dragonPartWing1.field_70131_O = 4.5F;
    this.dragonPartWing1.field_70130_N = 6.0F;
    this.dragonPartWing2.field_70131_O = 4.5F;
    this.dragonPartWing2.field_70130_N = 6.0F;
    Vec3d[] avec3d = new Vec3d[this.dragonPartArray.length];
    for (int j = 0; j < this.dragonPartArray.length; j++)
      avec3d[j] = new Vec3d((this.dragonPartArray[j]).field_70165_t, (this.dragonPartArray[j]).field_70163_u, (this.dragonPartArray[j]).field_70161_v); 
    float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F * 0.017453292F;
    float f16 = MathHelper.func_76134_b(f14);
    float f18 = MathHelper.func_76126_a(f14);
    float f2 = this.field_70177_z * 0.017453292F;
    float f19 = MathHelper.func_76126_a(f2);
    float f3 = MathHelper.func_76134_b(f2);
    double[] adouble = getMovementOffsets(5, 1.0F);
    double[] adouble1 = getMovementOffsets(14, 1.0F);
    double[] adouble2 = getMovementOffsets(16, 1.0F);
    this.dragonPartBody.func_70071_h_();
    this.dragonPartBody.func_70012_b(this.field_70165_t, this.field_70163_u - (MathHelper.func_76126_a(this.animTime * 6.2831855F - 0.5F) * 0.15F), this.field_70161_v, 0.0F, 0.0F);
    this.dragonPartWing1.func_70071_h_();
    this.dragonPartWing1.func_70012_b(this.field_70165_t + (f3 * 6.75F), this.field_70163_u + 1.5D + (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 3.0F), this.field_70161_v + (f19 * 6.75F), 0.0F, 0.0F);
    this.dragonPartWing2.func_70071_h_();
    this.dragonPartWing2.func_70012_b(this.field_70165_t - (f3 * 6.75F), this.field_70163_u + 1.5D + (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 3.0F), this.field_70161_v - (f19 * 6.75F), 0.0F, 0.0F);
    this.dragonPartNeck.func_70071_h_();
    this.dragonPartNeck.func_70012_b(this.field_70165_t + (f19 * 5.25F), this.field_70163_u + 1.5D - (MathHelper.func_76126_a(this.animTime * 6.2831855F + 1.0F) * 0.1F) + (f18 * 3.0F) - (this.field_70125_A / 90.0F) * Math.PI * 0.375D, this.field_70161_v - (f3 * 5.25F), 0.0F, 0.0F);
    this.dragonPartHead.func_70071_h_();
    this.dragonPartHead.func_70012_b(this.field_70165_t + (f19 * 9.0F), this.field_70163_u + 1.5D - (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 0.1F) + (f18 * 6.0F) - (this.field_70125_A / 90.0F) * Math.PI * 1.5D, this.field_70161_v - (f3 * 9.0F), 0.0F, 0.0F);
    for (int i = 0; i < 3; i++) {
      ExtendMultiPartEntityPart MultiPartEntityPart = null;
      if (i == 0)
        MultiPartEntityPart = this.dragonPartTail1; 
      if (i == 1)
        MultiPartEntityPart = this.dragonPartTail2; 
      if (i == 2)
        MultiPartEntityPart = this.dragonPartTail3; 
      adouble1 = getMovementOffsets(12 + i * 2, 1.0F);
      float f21 = this.field_70177_z * 0.017453292F + simplifyAngle(adouble1[0] - adouble[0]) * 0.017453292F;
      float f22 = MathHelper.func_76126_a(f21);
      float f7 = MathHelper.func_76134_b(f21);
      float f23 = 2.25F;
      float f24 = (i + 1) * 3.0F;
      MultiPartEntityPart.func_70071_h_();
      MultiPartEntityPart.func_70012_b(this.field_70165_t - ((f19 * f23 + f22 * f24) * f16), this.field_70163_u - (MathHelper.func_76126_a(this.animTime * 6.2831855F + i) * 0.3F * (i + 1)) - (f18 * (2.0F + (1 * i))) + f23, this.field_70161_v + ((f3 * f23 + f7 * f24) * f16), 0.0F, 0.0F);
    } 
    for (int l = 0; l < this.dragonPartArray.length; l++) {
      (this.dragonPartArray[l]).field_70169_q = (avec3d[l]).field_72450_a;
      (this.dragonPartArray[l]).field_70167_r = (avec3d[l]).field_72448_b;
      (this.dragonPartArray[l]).field_70166_s = (avec3d[l]).field_72449_c;
    } 
    if (isWild() && func_184753_b() == null) {
      if (this.field_70170_p.field_72995_K) {
        double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d3 = 10.0D;
        this.field_70170_p.func_175688_a(EnumParticleTypes.TOWN_AURA, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N - d0 * d3, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O) - d1 * d3, this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N - d2 * d3, d0, d1, d2, new int[0]);
      } 
    } else {
      if (func_70638_az() != null && getJukeboxToDanceTo() == null && !getPhaseManager().getCurrentPhase().getIsStationary()) {
        func_70625_a((Entity)func_70638_az(), 10.0F, 60.0F);
      } else if (!isWild()) {
        this.field_70759_as = this.field_70177_z;
        this.field_70125_A = getPhaseManager().getCurrentPhase().getIsStationary() ? 20.0F : 0.0F;
      } 
      this.convertionInt = 0;
      if (isHero())
        if (this.field_70170_p.field_72995_K) {
          double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d3 = 10.0D;
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartBody.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartBody.field_70130_N * 2.0F) - this.dragonPartBody.field_70130_N - d0 * d3, this.dragonPartBody.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartBody.field_70131_O) - d1 * d3, this.dragonPartBody.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartBody.field_70130_N * 2.0F) - this.dragonPartBody.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartHead.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartHead.field_70130_N * 2.0F) - this.dragonPartHead.field_70130_N - d0 * d3, this.dragonPartHead.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartHead.field_70131_O) - d1 * d3, this.dragonPartHead.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartHead.field_70130_N * 2.0F) - this.dragonPartHead.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartNeck.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartNeck.field_70130_N * 2.0F) - this.dragonPartNeck.field_70130_N - d0 * d3, this.dragonPartNeck.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartNeck.field_70131_O) - d1 * d3, this.dragonPartNeck.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartNeck.field_70130_N * 2.0F) - this.dragonPartNeck.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartWing1.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartWing1.field_70130_N * 2.0F) - this.dragonPartWing1.field_70130_N - d0 * d3, this.dragonPartWing1.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartWing1.field_70131_O) - d1 * d3, this.dragonPartWing1.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartWing1.field_70130_N * 2.0F) - this.dragonPartWing1.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartWing2.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartWing2.field_70130_N * 2.0F) - this.dragonPartWing2.field_70130_N - d0 * d3, this.dragonPartWing2.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartWing2.field_70131_O) - d1 * d3, this.dragonPartWing2.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartWing2.field_70130_N * 2.0F) - this.dragonPartWing2.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartTail1.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartTail1.field_70130_N * 2.0F) - this.dragonPartTail1.field_70130_N - d0 * d3, this.dragonPartTail1.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartTail1.field_70131_O) - d1 * d3, this.dragonPartTail1.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartTail1.field_70130_N * 2.0F) - this.dragonPartTail1.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartTail2.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartTail2.field_70130_N * 2.0F) - this.dragonPartTail2.field_70130_N - d0 * d3, this.dragonPartTail2.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartTail2.field_70131_O) - d1 * d3, this.dragonPartTail2.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartTail2.field_70130_N * 2.0F) - this.dragonPartTail2.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.dragonPartTail3.field_70165_t + (this.field_70146_Z.nextFloat() * this.dragonPartTail3.field_70130_N * 2.0F) - this.dragonPartTail3.field_70130_N - d0 * d3, this.dragonPartTail3.field_70163_u + (this.field_70146_Z.nextFloat() * this.dragonPartTail3.field_70131_O) - d1 * d3, this.dragonPartTail3.field_70161_v + (this.field_70146_Z.nextFloat() * this.dragonPartTail3.field_70130_N * 2.0F) - this.dragonPartTail3.field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
        }  
    } 
    if (this.field_70146_Z.nextInt(5) == 0 && !isWild() && getOwner().func_70643_av() != null && this.isOffensive)
      func_70624_b(getOwner().func_70643_av()); 
    this.field_70145_X = true;
    if (func_70093_af()) {
      this.bossInfo.func_186758_d(false);
    } else if (func_70089_S()) {
      this.bossInfo.func_186758_d(true);
    } 
    this.field_70122_E = false;
    this.field_70160_al = true;
    this.prevAnimTime = this.animTime;
    if (getSpecialAttackTimer() > 0)
      if (this.moralRaisedTimer > 200) {
        setSpecialAttackTimer(getSpecialAttackTimer() - 2);
      } else {
        setSpecialAttackTimer(getSpecialAttackTimer() - 1);
      }  
    if (func_110143_aJ() <= 0.0F) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f21 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity)
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f21, 0.0D, 0.0D, 0.0D, new int[0]); 
    } else {
      if (!getPhaseManager().getCurrentPhase().getIsStationary() && getPhaseManager().getCurrentPhase() != PhaseListAsorah.CHARGING_PLAYER && getPhaseManager().getCurrentPhase() != PhaseListAsorah.STRAFE_PLAYER && func_70638_az() != null && this.field_70173_aa % 60 == 0 && func_70681_au().nextInt(5) == 0)
        switch (this.field_70146_Z.nextInt(2)) {
          case 0:
            getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
            ((PhaseRamAttack)getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER)).func_188668_a(new Vec3d((func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v));
            break;
          case 1:
            getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
            ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseListAsorah.STRAFE_PLAYER)).func_188686_a(func_70638_az());
            break;
        }  
      if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityLivingBase && !(func_184179_bs() instanceof EntityPlayer)) {
        EntityLivingBase passenger = (EntityLivingBase)func_184179_bs();
        passenger.field_70761_aq = passenger.field_70177_z = passenger.field_70759_as = this.field_70177_z;
      } 
      if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
        EntityPlayer passenger = (EntityPlayer)func_184179_bs();
        passenger.field_70143_R *= 0.0F;
        passenger.field_70172_ad = 40;
        passenger.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 40, 4));
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
        this.field_70177_z = passenger.field_70177_z - 180.0F;
        this.field_70125_A = passenger.field_70125_A;
        for (int m = 0; m < 256; m++) {
          double d1 = 0.005D;
          if (this.moralRaisedTimer > 200)
            d1 *= 2.0D; 
          Vec3d vec3 = passenger.func_70676_i(1.0F);
          if (passenger.field_191988_bg > 0.0F)
            func_70107_b(this.field_70165_t + vec3.field_72450_a * d1, this.field_70163_u + vec3.field_72448_b * d1, this.field_70161_v + vec3.field_72449_c * d1); 
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
      if (func_70093_af() && !getPhaseManager().getCurrentPhase().getIsStationary() && this.field_70146_Z.nextInt(100) == 0)
        getPhaseManager().setPhase(PhaseListAsorah.LANDING_APPROACH); 
      if (!getPhaseManager().getCurrentPhase().getIsStationary() && !isWild() && (getOwner().func_110143_aJ() <= 6.0F || (!getOwner().func_184614_ca().func_190926_b() && getOwner().func_184614_ca().func_77973_b() == Items.field_151069_bo)) && func_70681_au().nextInt(20) == 0) {
        getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
        ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseListAsorah.STRAFE_PLAYER)).func_188686_a(getOwner());
      } 
      if (func_70089_S() && func_110143_aJ() < func_110138_aP() && this.field_70173_aa % ((func_70643_av() == null) ? 8 : 40) == 0) {
        func_70691_i(1.0F);
        spawnHeartParticle();
      } 
      if (!isWild() && func_70068_e((Entity)getOwner()) >= 48400.0D)
        func_70012_b((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v, this.field_70177_z, this.field_70125_A); 
      if (!this.field_70170_p.field_72995_K && func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && this.field_70146_Z.nextInt(160) == 0) {
        double d1 = this.dragonPartHead.field_70165_t;
        double d2 = this.dragonPartHead.field_70163_u + 0.25D;
        double d3 = this.dragonPartHead.field_70161_v;
        fireLightning((Entity)func_70638_az(), d1, d2, d3);
      } 
      if (func_70638_az() != null && func_70638_az().func_70089_S() && getSpecialAttackTimer() <= 0 && isHero()) {
        func_70642_aH();
        func_70642_aH();
        func_70642_aH();
        func_70642_aH();
        func_70642_aH();
        func_70642_aH();
        func_70642_aH();
        func_70642_aH();
        setSpecialAttackTimer(2000);
      } 
      if (getSpecialAttackTimer() > 1000)
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN); 
      if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && ((this.field_70173_aa % 6 == 0 && getSpecialAttackTimer() > 1000) || this.field_70146_Z.nextInt(100) == 0)) {
        double d6 = this.dragonPartHead.field_70165_t;
        double d7 = this.dragonPartHead.field_70163_u + 2.0D;
        double d8 = this.dragonPartHead.field_70161_v;
        double d9 = (func_70638_az()).field_70165_t - d6;
        double d10 = (func_70638_az()).field_70163_u + 1.0D - d7;
        double d11 = (func_70638_az()).field_70161_v - d8;
        this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
        EntityCoraliumChargeOther entitydragonfireball = new EntityCoraliumChargeOther(this.field_70170_p, (EntityLivingBase)this, d9, d10, d11);
        entitydragonfireball.field_70165_t = d6;
        entitydragonfireball.field_70163_u = d7;
        entitydragonfireball.field_70161_v = d8;
        this.field_70170_p.func_72838_d((Entity)entitydragonfireball);
      } 
      if (this.field_70146_Z.nextInt(2) == 0 && !isWild() && getOwner().func_70643_av() != null)
        func_70624_b(getOwner().func_70643_av()); 
      if (func_70638_az() != null && (!func_70638_az().func_70089_S() || !this.isOffensive || func_184191_r((Entity)func_70638_az())))
        func_70624_b(null); 
      updateHealingCircle();
      float f12 = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
      f12 *= (float)Math.pow(2.0D, this.field_70181_x);
      if (this.phaseManager.getCurrentPhase().getIsStationary() || getPhaseManager().getCurrentPhase() == PhaseListAsorah.CHARGING_PLAYER) {
        if (func_70093_af()) {
          this.animTime += 0.05F;
        } else {
          this.animTime += 0.1F;
        } 
      } else if (this.slowed) {
        this.animTime += f12 * 0.5F;
      } else {
        this.animTime += f12;
      } 
      int k;
      for (k = 0; k < 2; k++) {
        if (ACConfig.particleEntity)
          AbyssalCraft.proxy.spawnParticle("CorBlood", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D); 
      } 
      this.field_70760_ar = this.field_70761_aq = this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
      if (func_175446_cd()) {
        this.animTime = 0.5F;
      } else if (func_70089_S()) {
        if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
          EntityPlayer passenger = (EntityPlayer)func_184179_bs();
          passenger.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 40, 4));
          getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
          this.field_70761_aq = this.field_70177_z = passenger.field_70759_as + 180.0F;
          this.field_70125_A = 0.0F;
          for (int m = 0; m < 256; m++) {
            double d1 = 0.005D;
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
        if (this.ringBufferIndex < 0)
          for (k = 0; k < this.ringBuffer.length; k++) {
            this.ringBuffer[k][0] = this.field_70177_z;
            this.ringBuffer[k][1] = this.field_70163_u;
          }  
        if (++this.ringBufferIndex == this.ringBuffer.length)
          this.ringBufferIndex = 0; 
        this.ringBuffer[this.ringBufferIndex][0] = this.field_70177_z;
        this.ringBuffer[this.ringBufferIndex][1] = this.field_70163_u;
        if (this.field_70170_p.field_72995_K) {
          if (this.field_70716_bi > 0) {
            double d5 = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / this.field_70716_bi;
            double d0 = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / this.field_70716_bi;
            double d1 = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / this.field_70716_bi;
            double d2 = MathHelper.func_76138_g(this.field_184626_bk - this.field_70177_z);
            this.field_70177_z = (float)(this.field_70177_z + d2 / this.field_70716_bi);
            this.field_70125_A = (float)(this.field_70125_A + (this.field_70709_bj - this.field_70125_A) / this.field_70716_bi);
            this.field_70716_bi--;
            func_70107_b(d5, d0, d1);
            func_70101_b(this.field_70177_z, this.field_70125_A);
          } 
          this.phaseManager.getCurrentPhase().doClientRenderEffects();
        } else {
          IPhaseAsorah iphase = this.phaseManager.getCurrentPhase();
          iphase.doLocalUpdate();
          if (this.phaseManager.getCurrentPhase() != iphase) {
            iphase = this.phaseManager.getCurrentPhase();
            iphase.doLocalUpdate();
          } 
          Vec3d vec3d = iphase.getTargetLocation();
          if (vec3d != null) {
            double d6 = vec3d.field_72450_a - this.field_70165_t;
            double d7 = vec3d.field_72448_b - this.field_70163_u;
            double d8 = vec3d.field_72449_c - this.field_70161_v;
            if (func_184207_aI()) {
              double look = 2.0D;
              Vec3d vec3 = func_70676_i(1.0F);
              d6 = this.field_70165_t + vec3d.field_72450_a * look;
              d7 = this.field_70163_u + vec3d.field_72448_b * look;
              d8 = this.field_70165_t + vec3d.field_72449_c * look;
            } 
            double d3 = d6 * d6 + d7 * d7 + d8 * d8;
            float f6 = iphase.getMaxRiseOrFall();
            d7 = MathHelper.func_151237_a(d7 / MathHelper.func_76133_a(d6 * d6 + d8 * d8), -f6, f6);
            this.field_70181_x += d7 * 0.10000000149011612D;
            this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
            double d4 = MathHelper.func_151237_a(MathHelper.func_76138_g(180.0D - MathHelper.func_181159_b(d6, d8) * 57.29577951308232D - this.field_70177_z), -50.0D, 50.0D);
            Vec3d vec3d1 = (new Vec3d(vec3d.field_72450_a - this.field_70165_t, vec3d.field_72448_b - this.field_70163_u, vec3d.field_72449_c - this.field_70161_v)).func_72432_b();
            Vec3d vec3d2 = (new Vec3d(MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), this.field_70181_x, -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F))).func_72432_b();
            float f8 = Math.max(((float)vec3d2.func_72430_b(vec3d1) + 0.5F) / 1.5F, 0.0F);
            this.field_70704_bt *= 0.8F;
            this.field_70704_bt = (float)(this.field_70704_bt + d4 * iphase.getYawFactor());
            this.field_70177_z += this.field_70704_bt * 0.1F;
            float f9 = (float)(2.0D / (d3 + 1.0D));
            float f10 = 0.06F;
            func_191958_b(0.0F, 0.0F, -1.0F, f10 * (f8 * f9 + 1.0F - f9));
            if (getPhaseManager().getCurrentPhase() == PhaseListAsorah.CHARGING_PLAYER) {
              if (func_70093_af()) {
                func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 0.375D : 0.25D), this.field_70181_x * (func_184207_aI() ? 0.375D : 0.25D), this.field_70179_y * (func_184207_aI() ? 0.375D : 0.25D));
              } else if (this.moralRaisedTimer > 200) {
                func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 15.0D : 10.0D), this.field_70181_x * (func_184207_aI() ? 15.0D : 10.0D), this.field_70179_y * (func_184207_aI() ? 15.0D : 10.0D));
              } else {
                func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 7.5D : 5.0D), this.field_70181_x * (func_184207_aI() ? 7.5D : 5.0D), this.field_70179_y * (func_184207_aI() ? 7.5D : 5.0D));
              } 
            } else {
              if (func_70093_af())
                func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 0.375D : 0.25D), this.field_70181_x * (func_184207_aI() ? 0.375D : 0.25D), this.field_70179_y * (func_184207_aI() ? 0.375D : 0.25D)); 
              if (this.moralRaisedTimer > 200) {
                func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 3.0D : 2.0D), this.field_70181_x * (func_184207_aI() ? 3.0D : 2.0D), this.field_70179_y * (func_184207_aI() ? 3.0D : 2.0D));
              } else {
                func_70091_d(MoverType.SELF, this.field_70159_w * (func_184207_aI() ? 1.5D : 1.0D), this.field_70181_x * (func_184207_aI() ? 1.5D : 1.0D), this.field_70179_y * (func_184207_aI() ? 1.5D : 1.0D));
              } 
            } 
            Vec3d vec3d3 = (new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y)).func_72432_b();
            float f11 = ((float)vec3d3.func_72430_b(vec3d2) + 1.0F) / 2.0F;
            f11 = 0.8F + 0.15F * f11;
            if (func_184207_aI())
              f11 = 0.91F; 
            this.field_70159_w *= f11;
            this.field_70179_y *= f11;
            this.field_70181_x *= 0.9100000262260437D;
          } 
        } 
      } 
    } 
    if (!this.field_70170_p.field_72995_K) {
      collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartBody.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D)));
      collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartWing1.func_174813_aQ().func_72314_b(6.0D, 4.0D, 6.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
      collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartWing2.func_174813_aQ().func_72314_b(6.0D, 4.0D, 6.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
      collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail1.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D)));
      collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail2.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D)));
      collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail3.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D)));
      attackEntitiesInList(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartHead.func_174813_aQ().func_72314_b(3.0D, 3.0D, 3.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
      attackEntitiesInList(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartNeck.func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D).func_72317_d(0.0D, -2.0D, 0.0D)));
    } 
  }
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    if (this.deathTicks > 0)
      par1NBTTagCompound.func_74768_a("DeathTicks", this.deathTicks); 
    par1NBTTagCompound.func_74768_a("DragonPhase", this.phaseManager.getCurrentPhase().getPhaseList().getId());
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    this.deathTicks = par1NBTTagCompound.func_74762_e("DeathTicks");
    if (par1NBTTagCompound.func_74764_b("DragonPhase"))
      this.phaseManager.setPhase(PhaseListAsorah.getById(par1NBTTagCompound.func_74762_e("DragonPhase"))); 
  }
  
  private void updateHealingCircle() {
    if (this.healingcircle != null)
      if (this.healingcircle.field_70128_L) {
        this.healingcircle = null;
      } else if ((this.field_70173_aa + this.healingcircle.func_145782_y()) % 5 == 0) {
        func_70691_i(1.0F);
      }  
    if (this.field_70146_Z.nextInt(10) == 0) {
      float f = 64.0F;
      List<?> list = this.field_70170_p.func_72872_a(EntityDragonMinion.class, func_174813_aQ().func_72314_b(f, f, f));
      EntityDragonMinion entitydragonminion = null;
      double d0 = Double.MAX_VALUE;
      Iterator<?> iterator = list.iterator();
      while (iterator.hasNext()) {
        EntityDragonMinion entitydragonminion1 = (EntityDragonMinion)iterator.next();
        double d1 = entitydragonminion1.func_70068_e((Entity)this);
        if (d1 < d0) {
          d0 = d1;
          entitydragonminion = entitydragonminion1;
        } 
      } 
      this.healingcircle = entitydragonminion;
    } 
  }
  
  private void collideWithEntities(List<?> par1List) {
    double d0 = ((this.dragonPartBody.func_174813_aQ()).field_72340_a + (this.dragonPartBody.func_174813_aQ()).field_72336_d) / 2.0D;
    double d1 = ((this.dragonPartBody.func_174813_aQ()).field_72339_c + (this.dragonPartBody.func_174813_aQ()).field_72334_f) / 2.0D;
    Iterator<?> iterator = par1List.iterator();
    while (iterator.hasNext()) {
      Entity entity = (Entity)iterator.next();
      if (entity instanceof EntityLivingBase && !func_184191_r(entity)) {
        double d2 = entity.field_70165_t - d0;
        double d3 = entity.field_70161_v - d1;
        double d4 = d2 * d2 + d3 * d3;
        entity.func_70024_g(d2 / d4 * 4.0D, 0.20000000298023224D, d3 / d4 * 4.0D);
        if (entity instanceof EntityLivingBase) {
          ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70759_as = (float)MathHelper.func_181159_b(entity.field_70179_y, entity.field_70159_w) * 57.295776F - 90.0F;
          ((EntityLivingBase)entity).func_70604_c(null);
          if (entity instanceof EntityLiving)
            ((EntityLiving)entity).func_70624_b(null); 
        } 
      } 
    } 
  }
  
  private void attackEntitiesInList(List<?> par1List) {
    for (int i = 0; i < par1List.size(); i++) {
      Entity entity = (Entity)par1List.get(i);
      if (entity.field_70173_aa + entity.func_145782_y() % 10 == 0 && !this.field_70170_p.field_72995_K && entity instanceof EntityLivingBase && !func_184191_r(entity)) {
        func_70652_k(entity);
        func_184185_a(SoundEvents.field_187685_dH, 5.0F, 0.5F);
        if (ACConfig.hardcoreMode && entity instanceof EntityPlayer)
          entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 1.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
      } 
    } 
  }
  
  private float simplifyAngle(double par1) {
    return (float)MathHelper.func_76138_g(par1);
  }
  
  public int initPathPoints() {
    if (this.pathPoints[0] == null) {
      int i = 0;
      int j = 0;
      int k = 0;
      int l = 0;
      for (int i1 = 0; i1 < 24; i1++) {
        int j1 = 5;
        if (i1 < 12) {
          i = (int)(60.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.2617994F * i1)));
          k = (int)(60.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.2617994F * i1)));
        } else if (i1 < 20) {
          l = i1 - 12;
          i = (int)(40.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.3926991F * l)));
          k = (int)(40.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.3926991F * l)));
          j1 += 10;
        } else {
          l = i1 - 20;
          i = (int)(20.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.7853982F * l)));
          k = (int)(20.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.7853982F * l)));
        } 
        j = Math.max(this.field_70170_p.func_181545_F() + 10, this.field_70170_p.func_175672_r(new BlockPos(i, 0, k)).func_177956_o() + j1);
        this.pathPoints[i1] = new PathPoint(i, j, k);
      } 
      this.neighbors[0] = 6146;
      this.neighbors[1] = 8197;
      this.neighbors[2] = 8202;
      this.neighbors[3] = 16404;
      this.neighbors[4] = 32808;
      this.neighbors[5] = 32848;
      this.neighbors[6] = 65696;
      this.neighbors[7] = 131392;
      this.neighbors[8] = 131712;
      this.neighbors[9] = 263424;
      this.neighbors[10] = 526848;
      this.neighbors[11] = 525313;
      this.neighbors[12] = 1581057;
      this.neighbors[13] = 3166214;
      this.neighbors[14] = 2138120;
      this.neighbors[15] = 6373424;
      this.neighbors[16] = 4358208;
      this.neighbors[17] = 12910976;
      this.neighbors[18] = 9044480;
      this.neighbors[19] = 9706496;
      this.neighbors[20] = 15216640;
      this.neighbors[21] = 13688832;
      this.neighbors[22] = 11763712;
      this.neighbors[23] = 8257536;
    } 
    return getNearestPpIdx(this.field_70165_t, this.field_70163_u, this.field_70161_v);
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.1F : 10.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadPartYOffset(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
    double d0;
    IPhaseAsorah iphase = this.phaseManager.getCurrentPhase();
    PhaseListAsorah<? extends IPhaseAsorah> phaselist = iphase.getPhaseList();
    if (phaselist != PhaseListAsorah.LANDING && phaselist != PhaseListAsorah.TAKEOFF) {
      if (iphase.getIsStationary()) {
        d0 = p_184667_1_;
      } else if (p_184667_1_ == 6) {
        d0 = 0.0D;
      } else {
        d0 = p_184667_3_[1] - p_184667_2_[1];
      } 
    } else {
      BlockPos blockpos = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
      if (getOwner() != null)
        blockpos = new BlockPos((Entity)getOwner()); 
      float f = Math.max(MathHelper.func_76133_a(func_174831_c(blockpos)) / 4.0F, 1.0F);
      d0 = (p_184667_1_ / f);
    } 
    return (float)d0;
  }
  
  public Vec3d getHeadLookVec(float p_184665_1_) {
    Vec3d vec3d;
    IPhaseAsorah iphase = this.phaseManager.getCurrentPhase();
    PhaseListAsorah<? extends IPhaseAsorah> phaselist = iphase.getPhaseList();
    if (phaselist != PhaseListAsorah.LANDING && phaselist != PhaseListAsorah.TAKEOFF) {
      if (iphase.getIsStationary()) {
        float f4 = this.field_70125_A;
        float f5 = 1.5F;
        this.field_70125_A = -6.0F * f5 * 5.0F;
        vec3d = func_70676_i(p_184665_1_);
        this.field_70125_A = f4;
      } else {
        vec3d = func_70676_i(p_184665_1_);
      } 
    } else {
      BlockPos blockpos = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
      if (getOwner() != null)
        blockpos = new BlockPos((Entity)getOwner()); 
      float f = Math.max(MathHelper.func_76133_a(func_174831_c(blockpos)) / 4.0F, 1.0F);
      float f1 = 6.0F / f;
      float f2 = this.field_70125_A;
      float f3 = 1.5F;
      this.field_70125_A = -f1 * f3 * 5.0F;
      vec3d = func_70676_i(p_184665_1_);
      this.field_70125_A = f2;
    } 
    return vec3d;
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (PHASE.equals(key) && this.field_70170_p.field_72995_K)
      this.phaseManager.setPhase(PhaseListAsorah.getById(((Integer)func_184212_Q().func_187225_a(PHASE)).intValue())); 
    super.func_184206_a(key);
  }
  
  public int getNearestPpIdx(double x, double y, double z) {
    float f = 10000.0F;
    int i = 0;
    PathPoint pathpoint = new PathPoint(MathHelper.func_76128_c(x), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z));
    int j = 12;
    for (int k = j; k < 24; k++) {
      if (this.pathPoints[k] != null) {
        float f1 = this.pathPoints[k].func_75832_b(pathpoint);
        if (f1 < f) {
          f = f1;
          i = k;
        } 
      } 
    } 
    return i;
  }
  
  public Path findPath(int startIdx, int finishIdx, PathPoint andThen) {
    for (int i = 0; i < 24; i++) {
      PathPoint pathpoint = this.pathPoints[i];
      pathpoint.field_75842_i = false;
      pathpoint.field_75834_g = 0.0F;
      pathpoint.field_75836_e = 0.0F;
      pathpoint.field_75833_f = 0.0F;
      pathpoint.field_75841_h = null;
      pathpoint.field_75835_d = -1;
    } 
    PathPoint pathpoint4 = this.pathPoints[startIdx];
    PathPoint pathpoint5 = this.pathPoints[finishIdx];
    pathpoint4.field_75836_e = 0.0F;
    pathpoint4.field_75833_f = pathpoint4.func_75829_a(pathpoint5);
    pathpoint4.field_75834_g = pathpoint4.field_75833_f;
    this.pathFindQueue.func_75848_a();
    this.pathFindQueue.func_75849_a(pathpoint4);
    PathPoint pathpoint1 = pathpoint4;
    int j = 12;
    while (!this.pathFindQueue.func_75845_e()) {
      PathPoint pathpoint2 = this.pathFindQueue.func_75844_c();
      if (pathpoint2.equals(pathpoint5)) {
        if (andThen != null) {
          andThen.field_75841_h = pathpoint5;
          pathpoint5 = andThen;
        } 
        return makePath(pathpoint4, pathpoint5);
      } 
      if (pathpoint2.func_75829_a(pathpoint5) < pathpoint1.func_75829_a(pathpoint5))
        pathpoint1 = pathpoint2; 
      pathpoint2.field_75842_i = true;
      int k = 0;
      for (int l = 0; l < 24; l++) {
        if (this.pathPoints[l] == pathpoint2) {
          k = l;
          break;
        } 
      } 
      for (int i1 = j; i1 < 24; i1++) {
        if ((this.neighbors[k] & 1 << i1) > 0) {
          PathPoint pathpoint3 = this.pathPoints[i1];
          if (!pathpoint3.field_75842_i) {
            float f = pathpoint2.field_75836_e + pathpoint2.func_75829_a(pathpoint3);
            if (!pathpoint3.func_75831_a() || f < pathpoint3.field_75836_e) {
              pathpoint3.field_75841_h = pathpoint2;
              pathpoint3.field_75836_e = f;
              pathpoint3.field_75833_f = pathpoint3.func_75829_a(pathpoint5);
              if (pathpoint3.func_75831_a()) {
                this.pathFindQueue.func_75850_a(pathpoint3, pathpoint3.field_75836_e + pathpoint3.field_75833_f);
              } else {
                pathpoint3.field_75834_g = pathpoint3.field_75836_e + pathpoint3.field_75833_f;
                this.pathFindQueue.func_75849_a(pathpoint3);
              } 
            } 
          } 
        } 
      } 
    } 
    if (pathpoint1 == pathpoint4)
      return null; 
    if (andThen != null) {
      andThen.field_75841_h = pathpoint1;
      pathpoint1 = andThen;
    } 
    return makePath(pathpoint4, pathpoint1);
  }
  
  private Path makePath(PathPoint start, PathPoint finish) {
    int i = 1;
    for (PathPoint pathpoint = finish; pathpoint.field_75841_h != null; pathpoint = pathpoint.field_75841_h)
      i++; 
    PathPoint[] apathpoint = new PathPoint[i];
    PathPoint pathpoint1 = finish;
    i--;
    for (apathpoint[i] = finish; pathpoint1.field_75841_h != null; apathpoint[i] = pathpoint1) {
      pathpoint1 = pathpoint1.field_75841_h;
      i--;
    } 
    return new Path(apathpoint);
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean func_70965_a(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    if (func_110143_aJ() <= 0.0F)
      this.phaseManager.setPhase(PhaseListAsorah.DYING); 
    damage = this.phaseManager.getCurrentPhase().getAdjustedDamage(dragonPart, source, damage);
    if (dragonPart != this.dragonPartHead)
      damage = damage / 5.0F + Math.min(damage, 1.0F); 
    if (func_180431_b(source) || damage < 0.01F || source == DamageSource.field_82727_n || source == DamageSource.field_76368_d || source == DamageSource.field_76369_e || source == DamageSource.field_191291_g || source == DamageSource.field_76367_g)
      return false; 
    float f = func_110143_aJ();
    if (func_110143_aJ() <= 0.0F && !this.phaseManager.getCurrentPhase().getIsStationary() && !isWild())
      this.phaseManager.setPhase(PhaseListAsorah.DYING); 
    if (this.phaseManager.getCurrentPhase().getIsStationary()) {
      this.sittingDamageReceived = (int)(this.sittingDamageReceived + f - func_110143_aJ());
      if (this.sittingDamageReceived > 50.0F) {
        this.sittingDamageReceived = 0;
        if (source.func_76346_g() instanceof EntityLivingBase)
          switch (this.field_70146_Z.nextInt(2)) {
            case 0:
              getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
              ((PhaseRamAttack)getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER)).func_188668_a(new Vec3d(((EntityLivingBase)source.func_76346_g()).field_70165_t, ((EntityLivingBase)source.func_76346_g()).field_70163_u, ((EntityLivingBase)source.func_76346_g()).field_70161_v));
              break;
            case 1:
              getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
              ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseListAsorah.STRAFE_PLAYER)).func_188686_a((EntityLivingBase)source.func_76346_g());
              break;
          }  
      } 
    } 
    if (source.func_76346_g() != null && source.func_76346_g() instanceof EntityLivingBase)
      if (!isWild() && source.func_76346_g() == getOwner()) {
        getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
      } else {
        func_70624_b((EntityLivingBase)source.func_76346_g());
        if (this.field_70146_Z.nextInt(2) == 0) {
          getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
          ((PhaseRamAttack)getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER)).func_188668_a(new Vec3d((source.func_76346_g()).field_70165_t, (source.func_76346_g()).field_70163_u, (source.func_76346_g()).field_70161_v));
        } 
      }  
    return super.func_70097_a(source, damage);
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return func_70965_a((MultiPartEntityPart)this.dragonPartBody, source, amount);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public double func_70042_X() {
    return 4.825D;
  }
  
  protected boolean func_184219_q(Entity passenger) {
    return (func_184188_bt().size() < 5);
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      int i = func_184188_bt().indexOf(passenger);
      float f3 = this.field_70761_aq * 3.1415927F / 180.0F;
      float f11 = MathHelper.func_76126_a(f3);
      float f4 = MathHelper.func_76134_b(f3);
      if (i == 5)
        passenger.func_70107_b(this.dragonPartTail3.field_70165_t, this.dragonPartTail3.field_70163_u + 1.5D, this.dragonPartTail3.field_70161_v); 
      if (i == 4)
        passenger.func_70107_b(this.dragonPartTail2.field_70165_t, this.dragonPartTail2.field_70163_u + 1.5D, this.dragonPartTail2.field_70161_v); 
      if (i == 3)
        passenger.func_70107_b(this.dragonPartTail1.field_70165_t, this.dragonPartTail1.field_70163_u + 1.5D, this.dragonPartTail1.field_70161_v); 
      if (i == 2)
        passenger.func_70107_b(this.field_70165_t + (f11 * 1.5F), this.field_70163_u + func_70047_e() + 1.5D, this.field_70161_v - (f4 * 1.5F)); 
      if (i == 1)
        passenger.func_70107_b(this.field_70165_t + (f11 * -1.5F), this.field_70163_u + func_70047_e() + 1.5D, this.field_70161_v - (f4 * -1.5F)); 
      if (i == 0)
        passenger.func_70107_b(this.dragonPartNeck.field_70165_t, this.dragonPartNeck.field_70163_u + 1.5D, this.dragonPartNeck.field_70161_v); 
    } 
  }
  
  public void func_70690_d(PotionEffect potioneffectIn) {
    if (!potioneffectIn.func_188419_a().func_76398_f())
      super.func_70690_d(potioneffectIn); 
  }
  
  protected void func_70609_aI() {
    this.deathTicks++;
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity)
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]); 
    } 
    if (!this.field_70170_p.field_72995_K) {
      if (this.deathTicks > 150 && this.deathTicks % 5 == 0) {
        int i = 1500;
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
          this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.chunk_of_coralium)));
          this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.refined_coralium_ingot)));
          this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t + posneg(3), this.field_70163_u + this.field_70146_Z.nextInt(3), this.field_70161_v + posneg(3), new ItemStack(ACItems.coralium_plagued_flesh)));
        } 
      } 
      if (this.deathTicks == 1)
        this.field_70170_p.func_175669_a(1028, new BlockPos((Entity)this), 0); 
    } 
    func_70091_d(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
    this.field_70761_aq = this.field_70177_z += 20.0F;
    if (this.deathTicks == 20 && !this.field_70170_p.field_72995_K)
      SpecialTextUtil.OblivionaireGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.asorah.death.1") }); 
    if (this.deathTicks == 80 && !this.field_70170_p.field_72995_K)
      SpecialTextUtil.OblivionaireGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.asorah.death.2") }); 
    if (this.deathTicks == 140 && !this.field_70170_p.field_72995_K)
      SpecialTextUtil.OblivionaireGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.asorah.death.3") }); 
    if (this.deathTicks >= 200 && !this.field_70170_p.field_72995_K) {
      List<Entity> list = this.field_70170_p.field_72996_f;
      if (list != null)
        for (int k2 = 0; k2 < list.size(); k2++) {
          Entity entity = list.get(k2);
          if (entity instanceof EntityJzahar && entity.func_70089_S())
            SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { ((EntityJzahar)entity).func_184191_r((Entity)this) ? I18n.func_74838_a("message.jzaharhelpful.snidecomment.asorah") : I18n.func_74838_a("message.jzahar.snidecomment.asorah") }); 
        }  
      func_70106_y();
      this.field_70170_p.func_72838_d((Entity)new EntityItem(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, new ItemStack(ACItems.eye_of_the_abyss)));
    } 
  }
  
  private int posneg(int num) {
    return this.field_70146_Z.nextBoolean() ? this.field_70146_Z.nextInt(num) : (-1 * this.field_70146_Z.nextInt(num));
  }
  
  public Entity[] func_70021_al() {
    return (Entity[])this.dragonPartArray;
  }
  
  public boolean func_70067_L() {
    return (this.field_70170_p.func_72890_a((Entity)this, this.field_70130_N) != null && func_70089_S());
  }
  
  public World func_82194_d() {
    return this.field_70170_p;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187521_aK;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187526_aP;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187849_gA;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
  
  public void func_70653_a(Entity entityIn, float strength, double xRatio, double zRatio) {}
}
