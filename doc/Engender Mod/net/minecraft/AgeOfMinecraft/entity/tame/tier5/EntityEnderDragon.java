package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityBodyHelperDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityDragonFireballOther;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.IPhase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseFireballAndStrafe;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseList;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseManager;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.PhaseRamAttack;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEnderDragon extends EntityTameBase implements IEntityMultiPart, Massive, Flying, Armored, Ender {
  private static final DataParameter<Boolean> CARRIES_CRYSTAL = EntityDataManager.func_187226_a(EntityEnderDragon.class, DataSerializers.field_187198_h);
  
  public static final DataParameter<Integer> PHASE = EntityDataManager.func_187226_a(EntityEnderDragon.class, DataSerializers.field_187192_b);
  
  public int innerRotation;
  
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
  
  double sitPosX;
  
  double sitPosY;
  
  double sitPosZ;
  
  public float prevAnimTime;
  
  public float animTime;
  
  public boolean slowed;
  
  public boolean sitting;
  
  private final PhaseManager phaseManager;
  
  private int field_184678_bK = 200;
  
  private int sittingDamageReceived;
  
  private final PathPoint[] pathPoints = new PathPoint[24];
  
  private final int[] neighbors = new int[24];
  
  private final PathHeap pathFindQueue = new PathHeap();
  
  public EntityEnderDragon(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 6.0F, 6.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 6.0F, 6.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F) };
    func_70606_j(func_110138_aP());
    func_70105_a(16.0F, 3.6F);
    this.field_70145_X = true;
    this.field_70178_ae = true;
    this.field_70158_ak = true;
    this.phaseManager = new PhaseManager(this);
    getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
    this.field_98038_p = true;
    this.field_70728_aV = 500;
  }
  
  public String getDescName() {
    return "ender_dragon";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.LIGHT_PURPLE;
  }
  
  public int playMusic() {
    return 6;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public void func_70071_h_() {
    ItemStack charge = isCarryingCrystal() ? new ItemStack(Items.field_185158_cP) : ItemStack.field_190927_a;
    charge.func_151001_c("Carrying Crystal");
    this.basicInventory.func_70299_a(7, charge);
    super.func_70071_h_();
    this.innerRotation++;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186745_a(BossInfo.Color.PINK);
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
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
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.ENDER;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
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
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(128.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_188790_f).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(27.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(PHASE, Integer.valueOf(PhaseList.HOLDING_PATTERN.getId()));
    func_184212_Q().func_187214_a(CARRIES_CRYSTAL, Boolean.valueOf(false));
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public boolean isCarryingCrystal() {
    return ((Boolean)func_184212_Q().func_187225_a(CARRIES_CRYSTAL)).booleanValue();
  }
  
  public void setCarryingCrystal(boolean childZombie) {
    func_184212_Q().func_187227_b(CARRIES_CRYSTAL, Boolean.valueOf(childZombie));
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public float func_70047_e() {
    return 2.325F;
  }
  
  public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_) {
    if (func_110143_aJ() <= 0.0F)
      p_70974_2_ = 0.0F; 
    p_70974_2_ = 1.0F - p_70974_2_;
    int i = this.ringBufferIndex - p_70974_1_ & 0x3F;
    int j = this.ringBufferIndex - p_70974_1_ - 1 & 0x3F;
    double[] adouble = new double[3];
    double d0 = this.ringBuffer[i][0];
    double d1 = MathHelper.func_76138_g(this.ringBuffer[j][0] - d0);
    adouble[0] = d0 + d1 * p_70974_2_;
    d0 = this.ringBuffer[i][1];
    d1 = this.ringBuffer[j][1] - d0;
    adouble[1] = d0 + d1 * p_70974_2_;
    adouble[2] = this.ringBuffer[i][2] + (this.ringBuffer[j][2] - this.ringBuffer[i][2]) * p_70974_2_;
    return adouble;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_ENDER_DRAGON;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151061_bv && (hasOwner(player) || player.func_184191_r((Entity)this))) {
      func_184185_a(func_184639_G(), 0.25F, 0.75F);
      if (!func_184207_aI())
        if (this.sitting) {
          this.sitting = false;
        } else {
          this.sitting = true;
          this.sitPosX = this.field_70165_t;
          this.sitPosY = this.field_70163_u;
          this.sitPosZ = this.field_70161_v;
        }  
      return true;
    } 
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_185158_cP && (hasOwner(player) || player.func_184191_r((Entity)this))) {
      if (!isCarryingCrystal()) {
        setCarryingCrystal(true);
        func_70642_aH();
        this.field_70170_p.func_175718_b(3000, func_180425_c(), 0);
        if (!player.field_71075_bZ.field_75098_d)
          stack.func_190918_g(1); 
      } 
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public double func_70042_X() {
    return 3.25D;
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
        passenger.func_70107_b(this.dragonPartTail3.field_70165_t, this.dragonPartTail3.field_70163_u + 1.0D, this.dragonPartTail3.field_70161_v); 
      if (i == 4)
        passenger.func_70107_b(this.dragonPartTail2.field_70165_t, this.dragonPartTail2.field_70163_u + 1.0D, this.dragonPartTail2.field_70161_v); 
      if (i == 3)
        passenger.func_70107_b(this.dragonPartTail1.field_70165_t, this.dragonPartTail1.field_70163_u + 1.0D, this.dragonPartTail1.field_70161_v); 
      if (i == 2)
        passenger.func_70107_b(this.field_70165_t + (f11 * 1.0F), this.field_70163_u + func_70047_e() + 1.0D, this.field_70161_v - (f4 * 1.0F)); 
      if (i == 1)
        passenger.func_70107_b(this.field_70165_t + (f11 * -1.0F), this.field_70163_u + func_70047_e() + 1.0D, this.field_70161_v - (f4 * -1.0F)); 
      if (i == 0)
        passenger.func_70107_b(this.dragonPartNeck.field_70165_t, this.dragonPartNeck.field_70163_u + 1.0D, this.dragonPartNeck.field_70161_v); 
    } 
  }
  
  public void performSpecialAttack() {
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
  
  public boolean func_82171_bF() {
    return func_184179_bs() instanceof EntityPlayer;
  }
  
  public void func_70624_b(EntityLivingBase entitylivingbaseIn) {
    super.func_70624_b(entitylivingbaseIn);
  }
  
  protected EntityBodyHelper func_184650_s() {
    return (EntityBodyHelper)new EntityBodyHelperDragon((EntityLivingBase)this);
  }
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    super.func_70074_a(entityLivingIn);
  }
  
  public void func_70636_d() {
    if (this.field_70165_t == 0.0D && this.field_70163_u == 0.0D && this.field_70161_v == 0.0D) {
      EntityEnderDragon entityliving = new EntityEnderDragon(this.field_70170_p);
      if (!isWild()) {
        entityliving.func_82149_j((Entity)getOwner());
      } else {
        entityliving.func_70107_b(0.0D, 64.0D, 0.0D);
      } 
      entityliving.setOwnerId(func_184753_b());
      entityliving.getPhaseManager().setPhase(PhaseList.LANDING_APPROACH);
      entityliving.setLevel(getLevel());
      entityliving.setStrength(getStrength());
      entityliving.setStamina(getStamina());
      entityliving.setIntelligence(getIntelligence());
      entityliving.setAgility(getAgility());
      entityliving.setDexterity(getDexterity());
      entityliving.setIsHero(isHero());
      entityliving.setLastChance(hasLastChance());
      this.field_70170_p.func_72900_e((Entity)this);
      this.field_70170_p.func_72838_d((Entity)entityliving);
    } 
    if (this.convertionInt > 0) {
      this.phaseManager.setPhase(PhaseList.LANDING);
      this.field_70159_w *= 0.75D;
      this.field_70181_x *= 0.75D;
      this.field_70179_y *= 0.75D;
    } 
    if (getPhaseManager().getCurrentPhase() == PhaseList.LANDING_APPROACH) {
      func_70624_b((EntityLivingBase)null);
      double d0 = (isWild() ? 0.0D : (getOwner()).field_70165_t) - this.field_70165_t;
      double d1 = (isWild() ? this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a).func_177956_o() : ((getOwner()).field_70163_u + 4.0D)) - this.field_70163_u;
      double d2 = (isWild() ? 0.0D : (getOwner()).field_70161_v) - this.field_70161_v;
      double d3 = d0 * d0 + d2 * d2;
      double d5 = MathHelper.func_76133_a(d3);
      func_70091_d(MoverType.SELF, d0 / d5 * 0.99D - this.field_70159_w, d1 / d5 * 0.99D - this.field_70181_x, d2 / d5 * 0.99D - this.field_70179_y);
    } 
    if (!func_70089_S())
      func_70674_bp(); 
    if (this.sitting) {
      this.phaseManager.setPhase(PhaseList.HOLDING_PATTERN);
      func_70012_b(this.sitPosX, this.sitPosY, this.sitPosZ, this.field_70177_z, this.field_70125_A);
      this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
      if (func_70638_az() != null && this.phaseManager.getCurrentPhase() != PhaseList.LANDING_APPROACH && this.phaseManager.getCurrentPhase() != PhaseList.LANDING) {
        func_70625_a((Entity)func_70638_az(), 10.0F, 90.0F);
        this.field_70761_aq = this.field_70177_z = this.field_70759_as + 180.0F;
      } 
      if (!isWild() && func_70068_e((Entity)getOwner()) > 10000.0D) {
        this.sitPosX = (getOwner()).field_70165_t;
        this.sitPosY = (getOwner()).field_70163_u + 12.0D;
        this.sitPosZ = (getOwner()).field_70161_v;
      } else {
        this.sitPosX = this.field_70165_t;
        this.sitPosY = this.field_70163_u;
        this.sitPosZ = this.field_70161_v;
      } 
    } 
    if (this.field_70729_aU)
      this.phaseManager.setPhase(PhaseList.DYING); 
    if (func_110143_aJ() <= func_110138_aP() / 5.0F && !this.phaseManager.getCurrentPhase().getIsStationary()) {
      this.phaseManager.setPhase(PhaseList.LANDING_APPROACH);
      func_70624_b((EntityLivingBase)null);
    } 
    if (getJukeboxToDanceTo() != null) {
      Vec3d vec3d = getHeadLookVec(1.0F).func_72432_b();
      vec3d.func_178785_b(-0.7853982F);
      double d0 = this.dragonPartHead.field_70165_t;
      double d1 = this.dragonPartHead.field_70163_u + (this.dragonPartHead.field_70131_O / 2.0F);
      double d2 = this.dragonPartHead.field_70161_v;
      for (int k = 0; k < 8; k++) {
        double d3 = d0 + func_70681_au().nextGaussian() / 2.0D;
        double d4 = d1 + func_70681_au().nextGaussian() / 2.0D;
        double d5 = d2 + func_70681_au().nextGaussian() / 2.0D;
        this.field_70170_p.func_175688_a(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.field_72450_a * 0.07999999821186066D + this.field_70159_w, -vec3d.field_72448_b * 0.30000001192092896D + this.field_70181_x, -vec3d.field_72449_c * 0.07999999821186066D + this.field_70179_y, new int[0]);
        vec3d.func_178785_b(0.19634955F);
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
      if (!this.field_70170_p.field_72995_K && func_70638_az() == null) {
        this.field_70715_bh.func_75774_a();
        List<EntityTameBase> list = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_186662_g(func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e()), Predicates.and(EntitySelectors.field_94557_a, EntitySelectors.field_180132_d));
        for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
          EntityTameBase entitylivingbase = list.get(this.field_70146_Z.nextInt(list.size()));
          if (entitylivingbase != this && entitylivingbase.func_70089_S() && func_70685_l((Entity)entitylivingbase) && entitylivingbase.func_184753_b() == func_184753_b() && entitylivingbase.getFakeHealth() > 0.0F) {
            func_70624_b((EntityLivingBase)entitylivingbase);
            break;
          } 
          list.remove(entitylivingbase);
        } 
      } 
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
      func_70634_a(getJukeboxToDanceTo().func_177958_n(), getJukeboxToDanceTo().func_177956_o() + 12.0D, getJukeboxToDanceTo().func_177952_p());
      if (block != Blocks.field_150421_aI || (block == Blocks.field_150421_aI && !((Boolean)iblockstate.func_177229_b((IProperty)BlockJukebox.field_176432_a)).booleanValue()) || func_174831_c(this.jukeBoxToDanceTo) > 10000.0D) {
        setJukeboxToDanceTo(null);
        getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
        this.sitting = false;
      } 
    } 
    if (getJukeboxToDanceTo() == null && this.field_70173_aa % 60 == 0) {
      int i11 = (int)this.field_70163_u;
      int l1 = (int)this.field_70165_t;
      int i2 = (int)this.field_70161_v;
      boolean flag = false;
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
              getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
            } 
          } 
        } 
      } 
    } 
    this.dragonPartHead.field_70130_N = this.dragonPartHead.field_70131_O = 2.5F * getFittness();
    this.dragonPartNeck.field_70130_N = this.dragonPartNeck.field_70131_O = 2.5F * getFittness();
    this.dragonPartTail1.field_70130_N = this.dragonPartTail1.field_70131_O = 2.0F * getFittness();
    this.dragonPartTail2.field_70130_N = this.dragonPartTail2.field_70131_O = 2.0F * getFittness();
    this.dragonPartTail3.field_70130_N = this.dragonPartTail3.field_70131_O = 2.0F * getFittness();
    this.dragonPartBody.field_70131_O = 3.5F * getFittness();
    this.dragonPartBody.field_70130_N = 5.0F * getFittness();
    this.dragonPartWing1.field_70131_O = 3.0F * getFittness();
    this.dragonPartWing1.field_70130_N = 4.0F * getFittness();
    this.dragonPartWing2.field_70131_O = 3.0F * getFittness();
    this.dragonPartWing2.field_70130_N = 4.0F * getFittness();
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
    this.dragonPartBody.func_70012_b(this.field_70165_t, this.field_70163_u - (MathHelper.func_76126_a(this.animTime * 6.2831855F - 0.5F) * 0.1F * getFittness()), this.field_70161_v, 0.0F, 0.0F);
    this.dragonPartWing1.func_70071_h_();
    this.dragonPartWing1.func_70012_b(this.field_70165_t + (f3 * 4.5F * getFittness()), this.field_70163_u + 1.0D + (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 3.0F), this.field_70161_v + (f19 * 4.5F * getFittness()), 0.0F, 0.0F);
    this.dragonPartWing2.func_70071_h_();
    this.dragonPartWing2.func_70012_b(this.field_70165_t - (f3 * 4.5F * getFittness()), this.field_70163_u + 1.0D + (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 3.0F), this.field_70161_v - (f19 * 4.5F * getFittness()), 0.0F, 0.0F);
    this.dragonPartNeck.func_70071_h_();
    this.dragonPartNeck.func_70012_b(this.field_70165_t + (f19 * 3.5F * getFittness()), this.field_70163_u + 1.0D - (MathHelper.func_76126_a(this.animTime * 6.2831855F + 1.0F) * 0.1F * getFittness()) + (f18 * 2.0F * getFittness()) - (this.field_70125_A / 90.0F) * Math.PI * 0.25D, this.field_70161_v - (f3 * 3.5F * getFittness()), 0.0F, 0.0F);
    this.dragonPartHead.func_70071_h_();
    this.dragonPartHead.func_70012_b(this.field_70165_t + (f19 * 6.0F * getFittness()), this.field_70163_u + 1.0D - (MathHelper.func_76126_a(this.animTime * 6.2831855F) * 0.1F * getFittness()) + (f18 * 4.0F * getFittness()) - (this.field_70125_A / 90.0F) * Math.PI * 1.0D, this.field_70161_v - (f3 * 6.0F * getFittness()), 0.0F, 0.0F);
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
      float f23 = 1.5F;
      float f24 = (i + 1) * 2.0F;
      MultiPartEntityPart.func_70071_h_();
      MultiPartEntityPart.func_70012_b(this.field_70165_t - ((f19 * f23 + f22 * f24 * getFittness()) * f16), this.field_70163_u - (MathHelper.func_76126_a(this.animTime * 6.2831855F + i) * 0.2F * (i + 1) * getFittness()) - (f18 * (2.0F + (1 * i))) + f23, this.field_70161_v + ((f3 * f23 + f7 * f24 * getFittness()) * f16), 0.0F, 0.0F);
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
      if (func_70638_az() != null && getJukeboxToDanceTo() == null && this.phaseManager.getCurrentPhase() != PhaseList.LANDING_APPROACH && this.phaseManager.getCurrentPhase() != PhaseList.LANDING) {
        func_70625_a((Entity)func_70638_az(), 10.0F, 90.0F);
        if (this.sitting)
          this.field_70761_aq = this.field_70177_z = this.field_70759_as + 180.0F; 
      } else {
        this.field_70759_as = this.field_70177_z - 180.0F;
        this.field_70125_A = (getPhaseManager().getCurrentPhase().getIsStationary() || this.sitting) ? 20.0F : 0.0F;
      } 
      this.convertionInt = 0;
      if (isHero())
        for (int k = 0; k < this.dragonPartArray.length; k++) {
          avec3d[k] = new Vec3d((this.dragonPartArray[k]).field_70165_t, (this.dragonPartArray[k]).field_70163_u, (this.dragonPartArray[k]).field_70161_v);
          double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
          double d3 = 10.0D;
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, (this.dragonPartArray[k]).field_70165_t + (this.field_70146_Z.nextFloat() * (this.dragonPartArray[k]).field_70130_N * 2.0F) - (this.dragonPartArray[k]).field_70130_N - d0 * d3, (this.dragonPartArray[k]).field_70163_u + (this.field_70146_Z.nextFloat() * (this.dragonPartArray[k]).field_70131_O) - d1 * d3, (this.dragonPartArray[k]).field_70161_v + (this.field_70146_Z.nextFloat() * (this.dragonPartArray[k]).field_70130_N * 2.0F) - (this.dragonPartArray[k]).field_70130_N - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
        }  
    } 
    if (this.field_70146_Z.nextInt(5) == 0 && !isWild() && getOwner().func_70643_av() != null && this.isOffensive)
      func_70624_b(getOwner().func_70643_av()); 
    this.field_70145_X = true;
    this.reachWidth = 8.0F;
    this.field_70122_E = false;
    this.field_70160_al = true;
    if (this.field_70173_aa % 5 == 0)
      this.slowed = false; 
    if (this.field_70170_p.field_72995_K) {
      func_70606_j(func_110143_aJ());
      if (!func_174814_R()) {
        float f = MathHelper.func_76134_b(this.animTime * 6.2831855F);
        float f1 = MathHelper.func_76134_b(this.prevAnimTime * 6.2831855F);
        if (f1 <= -0.3F && f >= -0.3F)
          if (func_70093_af()) {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), 1.0F, 0.6F + this.field_70146_Z.nextFloat() * 0.3F, false);
          } else {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), 5.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false);
          }  
        if (!this.phaseManager.getCurrentPhase().getIsStationary() && --this.field_184678_bK < 0) {
          if (func_70093_af()) {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), 1.0F, 0.6F + this.field_70146_Z.nextFloat() * 0.3F, false);
          } else {
            this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), 5.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false);
          } 
          this.field_184678_bK = 200 + this.field_70146_Z.nextInt(200);
        } 
      } 
    } 
    this.prevAnimTime = this.animTime;
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
        IPhase iphase = this.phaseManager.getCurrentPhase();
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
          double d3 = d6 * d6 + d7 * d7 + d8 * d8;
          float f5 = iphase.getMaxRiseOrFall();
          d7 = MathHelper.func_151237_a(d7 / MathHelper.func_76133_a(d6 * d6 + d8 * d8), -f5, f5);
          this.field_70181_x += d7 * 0.10000000149011612D;
          this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
          double d4 = MathHelper.func_151237_a(MathHelper.func_76138_g(180.0D - MathHelper.func_181159_b(d6, d8) * 57.29577951308232D - this.field_70177_z), -50.0D, 50.0D);
          Vec3d vec3d1 = (new Vec3d(vec3d.field_72450_a - this.field_70165_t, vec3d.field_72448_b - this.field_70163_u, vec3d.field_72449_c - this.field_70161_v)).func_72432_b();
          Vec3d vec3d2 = (new Vec3d(MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), this.field_70181_x, -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F))).func_72432_b();
          float f7 = Math.max(((float)vec3d2.func_72430_b(vec3d1) + 0.5F) / 1.5F, 0.0F);
          this.field_70704_bt *= 0.8F;
          this.field_70704_bt = (float)(this.field_70704_bt + d4 * iphase.getYawFactor());
          this.field_70177_z += this.field_70704_bt * 0.1F;
          float f8 = (float)(2.0D / (d3 + 1.0D));
          float f9 = 0.06F;
          if (this.field_70173_aa > 20 && !this.sitting) {
            if (this.field_70173_aa > 20 && !this.sitting)
              func_191958_b(0.0F, 0.0F, -1.0F, 0.06F * (f7 * f8 + 1.0F - f8)); 
            if (getPhaseManager().getCurrentPhase() == PhaseList.CHARGING_PLAYER) {
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
          } 
          Vec3d vec3d3 = (new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y)).func_72432_b();
          float f10 = ((float)vec3d3.func_72430_b(vec3d2) + 1.0F) / 2.0F;
          f10 = 0.8F + 0.15F * f10;
          this.field_70159_w *= f10;
          this.field_70179_y *= f10;
          this.field_70181_x *= 0.9100000262260437D;
        } 
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
    } 
    if (getSpecialAttackTimer() > 0)
      if (this.moralRaisedTimer > 200) {
        setSpecialAttackTimer(getSpecialAttackTimer() - 2);
      } else {
        setSpecialAttackTimer(getSpecialAttackTimer() - 1);
      }  
    if (func_110143_aJ() <= 0.0F && this.deathTicks > 0) {
      for (int k = 0; k < this.dragonPartArray.length; k++) {
        avec3d[k] = new Vec3d((this.dragonPartArray[k]).field_70165_t, (this.dragonPartArray[k]).field_70163_u, (this.dragonPartArray[k]).field_70161_v);
        double d1 = (this.field_70146_Z.nextFloat() * (this.dragonPartArray[k]).field_70130_N - (this.dragonPartArray[k]).field_70130_N / 2.0F);
        double d2 = (this.field_70146_Z.nextFloat() * (this.dragonPartArray[k]).field_70131_O - (this.dragonPartArray[k]).field_70131_O / 2.0F);
        double d3 = (this.field_70146_Z.nextFloat() * (this.dragonPartArray[k]).field_70130_N - (this.dragonPartArray[k]).field_70130_N / 2.0F);
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, (this.dragonPartArray[k]).field_70165_t + d1, (this.dragonPartArray[k]).field_70163_u + d2, (this.dragonPartArray[k]).field_70161_v + d3, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
      } 
      float f13 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f15 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f17 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + f13, this.field_70163_u + f15, this.field_70161_v + f17, 0.0D, 0.0D, 0.0D, new int[0]);
    } else {
      if (func_70638_az() != null && !getPhaseManager().getCurrentPhase().getIsStationary() && getPhaseManager().getCurrentPhase() != PhaseList.CHARGING_PLAYER && getPhaseManager().getCurrentPhase() != PhaseList.STRAFE_PLAYER && func_70638_az() != null && this.field_70173_aa % 60 == 0 && func_70681_au().nextInt(5) == 0 && !this.sitting)
        switch (this.field_70146_Z.nextInt(2)) {
          case 0:
            getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
            ((PhaseRamAttack)getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).func_188668_a(new Vec3d((func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v));
            break;
          case 1:
            getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
            ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseList.STRAFE_PLAYER)).func_188686_a(func_70638_az());
            break;
        }  
      if (isHero() && getSpecialAttackTimer() > 1995) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(256.0D, 256.0D, 256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity = list.get(i1);
            if (entity != null)
              if (!func_184191_r((Entity)entity)) {
                double d6 = this.field_70165_t + this.field_70146_Z.nextDouble() * 32.0D - 16.0D;
                double d7 = this.field_70163_u + 64.0D + this.field_70146_Z.nextDouble() * 32.0D - 16.0D;
                double d8 = this.field_70161_v + this.field_70146_Z.nextDouble() * 32.0D - 16.0D;
                double d9 = entity.field_70165_t - d6;
                double d10 = entity.field_70163_u + (entity.field_70131_O / 2.0F) - d7 + (this.field_70131_O / 2.0F);
                double d11 = entity.field_70161_v - d8;
                this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
                EntityDragonFireballOther entitydragonfireball = new EntityDragonFireballOther(this.field_70170_p, (EntityLivingBase)this, d9, d10, d11);
                entitydragonfireball.field_70165_t = d6;
                entitydragonfireball.field_70163_u = d7;
                entitydragonfireball.field_70161_v = d8;
                this.field_70170_p.func_72838_d((Entity)entitydragonfireball);
              }  
          }  
      } 
      if (func_70638_az() != null && func_70638_az().func_70089_S() && getSpecialAttackTimer() <= 0 && isHero())
        performSpecialAttack(); 
      if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
        EntityPlayer passenger = (EntityPlayer)func_184179_bs();
        passenger.field_70143_R *= 0.0F;
        passenger.field_70172_ad = 40;
        passenger.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 40, 4));
        getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        this.field_70761_aq = this.field_70177_z = passenger.field_70759_as + 180.0F;
        this.field_70125_A = 0.0F;
        for (int k = 0; k < 256; k++) {
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
        getPhaseManager().setPhase(PhaseList.LANDING_APPROACH); 
      if (!getPhaseManager().getCurrentPhase().getIsStationary() && !isWild() && (getOwner().func_110143_aJ() <= 6.0F || (!getOwner().func_184614_ca().func_190926_b() && getOwner().func_184614_ca().func_77973_b() == Items.field_151069_bo)) && func_70681_au().nextInt(20) == 0) {
        getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
        ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseList.STRAFE_PLAYER)).func_188686_a(getOwner());
      } 
      if (!isWild() && func_70068_e((Entity)getOwner()) >= 48400.0D)
        func_70012_b((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v, this.field_70177_z, this.field_70125_A); 
      if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && this.field_70146_Z.nextInt(120) == 0) {
        double d1 = this.dragonPartHead.field_70165_t;
        double d2 = this.dragonPartHead.field_70163_u + 0.25D;
        double d3 = this.dragonPartHead.field_70161_v;
        if (getPolymorphTime() > 0 && this.field_70146_Z.nextBoolean()) {
          for (int k = 0; k < (isHero() ? 36 : 18); k++) {
            EntityMagicMissile entitymagicmissiles = new EntityMagicMissile(this.field_70170_p, (Entity)func_70638_az(), (EntityLivingBase)this, d1, d2, d3);
            Random random = new Random();
            entitymagicmissiles.field_70159_w += random.nextDouble() * 2.0D - 1.0D + this.field_70159_w;
            entitymagicmissiles.field_70181_x += random.nextDouble() * 2.0D + this.field_70181_x;
            entitymagicmissiles.field_70179_y += random.nextDouble() * 2.0D - 1.0D + this.field_70179_y;
            this.field_70170_p.func_72838_d((Entity)entitymagicmissiles);
          } 
        } else {
          fireLightning((Entity)func_70638_az(), d1, d2, d3);
        } 
      } 
      if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && this.field_70146_Z.nextInt(120) == 0) {
        double d6 = this.dragonPartHead.field_70165_t;
        double d7 = this.dragonPartHead.field_70163_u + 0.5D;
        double d8 = this.dragonPartHead.field_70161_v;
        double d9 = (func_70638_az()).field_70165_t - d6;
        double d10 = (func_70638_az()).field_70163_u + 1.0D - d7;
        double d11 = (func_70638_az()).field_70161_v - d8;
        this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
        if (getPolymorphTime() > 0 && this.field_70146_Z.nextBoolean()) {
          EntityInvisibleFangsProjectile entitydragonfireball = new EntityInvisibleFangsProjectile(this.field_70170_p, (EntityLivingBase)this, d6, d7, d8);
          entitydragonfireball.field_70165_t = d6;
          entitydragonfireball.field_70163_u = d7;
          entitydragonfireball.field_70161_v = d8;
          this.field_70170_p.func_72838_d((Entity)entitydragonfireball);
        } else {
          EntityDragonFireballOther entitydragonfireball = new EntityDragonFireballOther(this.field_70170_p, (EntityLivingBase)this, d9, d10, d11);
          entitydragonfireball.field_70165_t = d6;
          entitydragonfireball.field_70163_u = d7;
          entitydragonfireball.field_70161_v = d8;
          this.field_70170_p.func_72838_d((Entity)entitydragonfireball);
        } 
      } 
      if (this.field_70146_Z.nextInt(2) == 0 && !isWild() && getOwner().func_70643_av() != null)
        func_70624_b(getOwner().func_70643_av()); 
      if (func_70638_az() != null && (!func_70638_az().func_70089_S() || !this.isOffensive || func_184191_r((Entity)func_70638_az())))
        func_70624_b((EntityLivingBase)null); 
      updateDragonEnderCrystal();
      float f12 = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
      f12 *= (float)Math.pow(2.0D, this.field_70181_x);
      if (this.field_70173_aa > 20)
        if (this.phaseManager.getCurrentPhase().getIsStationary() || (func_184207_aI() && ((EntityLivingBase)func_184179_bs()).field_191988_bg == 0.0F) || getPhaseManager().getCurrentPhase() == PhaseList.CHARGING_PLAYER || this.sitting) {
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
      this.field_70760_ar = this.field_70761_aq = this.field_70177_z;
    } 
    if (this.field_70173_aa % 20 == 0)
      func_70691_i(1.0F); 
  }
  
  private float func_184662_q(float p_184662_1_) {
    double d0 = 0.0D;
    if (this.phaseManager.getCurrentPhase().getIsStationary() || getPhaseManager().getCurrentPhase() == PhaseList.HOVER) {
      d0 = -1.0D;
    } else {
      double[] adouble = getMovementOffsets(5, 1.0F);
      double[] adouble1 = getMovementOffsets(0, 1.0F);
      d0 = adouble[1] - adouble1[0];
    } 
    return (float)d0;
  }
  
  private void updateDragonEnderCrystal() {
    if (this.field_70173_aa % 5 == 0 && func_70089_S() && isCarryingCrystal())
      func_70606_j(func_110143_aJ() + 1.0F); 
  }
  
  protected void collideWithEntities(ExtendMultiPartEntityPart part, List list) {
    double d0 = ((part.func_174813_aQ()).field_72340_a + (part.func_174813_aQ()).field_72336_d) / 2.0D;
    double d1 = ((part.func_174813_aQ()).field_72339_c + (part.func_174813_aQ()).field_72334_f) / 2.0D;
    Iterator<Entity> iterator = list.iterator();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();
      if (func_70089_S() && entity instanceof EntityLivingBase && !func_184191_r(entity) && !this.field_70170_p.field_72995_K) {
        double d2 = entity.field_70165_t - d0;
        double d3 = entity.field_70161_v - d1;
        double d4 = d2 * d2 + d3 * d3;
        entity.func_70024_g(d2 / d4 * 4.0D, 0.75D, d3 / d4 * 4.0D);
        this.slowed = true;
        if (entity instanceof EntityLivingBase) {
          ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70759_as = (float)MathHelper.func_181159_b(entity.field_70179_y, entity.field_70159_w) * 57.295776F - 90.0F;
          ((EntityLivingBase)entity).func_70604_c(null);
          if (entity instanceof EntityLiving)
            ((EntityLiving)entity).func_70624_b(null); 
        } 
      } 
    } 
  }
  
  protected void flingEntities(ExtendMultiPartEntityPart part, List list) {
    double d0 = ((part.func_174813_aQ()).field_72340_a + (part.func_174813_aQ()).field_72336_d) / 1.5D;
    double d1 = ((part.func_174813_aQ()).field_72339_c + (part.func_174813_aQ()).field_72334_f) / 1.5D;
    Iterator<Entity> iterator = list.iterator();
    while (iterator.hasNext()) {
      Entity entity = iterator.next();
      if (func_70089_S() && entity instanceof EntityLivingBase && !func_184191_r(entity) && !this.field_70170_p.field_72995_K) {
        double d2 = entity.field_70165_t - d0;
        double d3 = entity.field_70161_v - d1;
        double d4 = d2 * d2 + d3 * d3;
        entity.func_70024_g(d2 / d4 * 32.0D, 1.5D, d3 / d4 * 32.0D);
        if (entity instanceof EntityLivingBase) {
          ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70759_as = (float)MathHelper.func_181159_b(entity.field_70179_y, entity.field_70159_w) * 57.295776F - 90.0F;
          ((EntityLivingBase)entity).func_70604_c(null);
          if (entity instanceof EntityLiving)
            ((EntityLiving)entity).func_70624_b(null); 
        } 
        entity.func_70097_a(DamageSource.field_188406_j, 4.0F);
      } 
    } 
  }
  
  protected void attackEntitiesInList(List<Entity> p_70971_1_) {
    for (int i = 0; i < p_70971_1_.size(); i++) {
      Entity entity = p_70971_1_.get(i);
      if (func_70089_S() && entity.field_70173_aa + entity.func_145782_y() % 10 == 0 && !this.field_70170_p.field_72995_K && entity instanceof EntityLivingBase && !func_184191_r(entity)) {
        func_70652_k(entity);
        if (entity instanceof EntityLivingBase) {
          ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70759_as = (float)MathHelper.func_181159_b(entity.field_70179_y, entity.field_70159_w) * 57.295776F - 90.0F;
          ((EntityLivingBase)entity).func_70604_c(null);
          if (entity instanceof EntityLiving)
            ((EntityLiving)entity).func_70624_b(null); 
        } 
        func_184185_a(SoundEvents.field_187685_dH, 5.0F, 0.5F);
        this.slowed = true;
      } 
    } 
  }
  
  private float simplifyAngle(double p_70973_1_) {
    return (float)MathHelper.func_76138_g(p_70973_1_);
  }
  
  protected boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_) {
    int i = MathHelper.func_76128_c(p_70972_1_.field_72340_a);
    int j = MathHelper.func_76128_c(p_70972_1_.field_72338_b);
    int k = MathHelper.func_76128_c(p_70972_1_.field_72339_c);
    int l = MathHelper.func_76128_c(p_70972_1_.field_72336_d);
    int i1 = MathHelper.func_76128_c(p_70972_1_.field_72337_e);
    int j1 = MathHelper.func_76128_c(p_70972_1_.field_72334_f);
    boolean flag1 = false;
    for (int k1 = i; k1 <= l; k1++) {
      for (int l1 = j; l1 <= i1; l1++) {
        for (int i2 = k; i2 <= j1; i2++) {
          BlockPos blockpos = new BlockPos(k1, l1, i2);
          IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
          Block block = iblockstate.func_177230_c();
          if (EngenderConfig.mobs.grief && func_70089_S() && !block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos))
            if (block.canEntityDestroy(iblockstate, (IBlockAccess)this.field_70170_p, blockpos, (Entity)this))
              if (block != Blocks.field_150384_bq && block != Blocks.field_150380_bt && block != Blocks.field_150357_h && block != Blocks.field_150377_bs && block != Blocks.field_150343_Z && block != Blocks.field_150483_bI && block != Blocks.field_185776_dc && block != Blocks.field_185777_dd && block != Blocks.field_150411_aY && block != Blocks.field_185775_db) {
                if (!this.field_70170_p.field_72995_K)
                  flag1 = (this.field_70170_p.func_175698_g(blockpos) || flag1); 
              } else {
                this.slowed = true;
              }   
        } 
      } 
    } 
    return EngenderConfig.mobs.grief;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean func_180431_b(DamageSource source) {
    return super.func_180431_b(source);
  }
  
  public boolean func_70965_a(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    if (func_180431_b(source))
      damage = 0.0F; 
    if (func_110143_aJ() <= 0.0F)
      this.phaseManager.setPhase(PhaseList.DYING); 
    damage = this.phaseManager.getCurrentPhase().getAdjustedDamage(dragonPart, source, damage);
    if (dragonPart != this.dragonPartHead)
      damage = damage / 5.0F + Math.min(damage, 1.0F); 
    if (func_180431_b(source) || damage < 0.01F || source == DamageSource.field_82727_n || source == DamageSource.field_76368_d || source == DamageSource.field_76369_e || source == DamageSource.field_191291_g || source == DamageSource.field_76367_g)
      return false; 
    float f = func_110143_aJ();
    super.func_70097_a(source, damage);
    if (func_110143_aJ() <= 0.0F && !this.phaseManager.getCurrentPhase().getIsStationary() && !isWild())
      this.phaseManager.setPhase(PhaseList.DYING); 
    if (this.phaseManager.getCurrentPhase().getIsStationary()) {
      this.sittingDamageReceived = (int)(this.sittingDamageReceived + f - func_110143_aJ());
      if (this.sittingDamageReceived > 50.0F) {
        this.sittingDamageReceived = 0;
        if (source.func_76346_g() instanceof EntityLivingBase)
          switch (this.field_70146_Z.nextInt(2)) {
            case 0:
              getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
              ((PhaseRamAttack)getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).func_188668_a(new Vec3d(((EntityLivingBase)source.func_76346_g()).field_70165_t, ((EntityLivingBase)source.func_76346_g()).field_70163_u, ((EntityLivingBase)source.func_76346_g()).field_70161_v));
              break;
            case 1:
              getPhaseManager().setPhase(PhaseList.STRAFE_PLAYER);
              ((PhaseFireballAndStrafe)getPhaseManager().getPhase(PhaseList.STRAFE_PLAYER)).func_188686_a((EntityLivingBase)source.func_76346_g());
              break;
          }  
      } 
    } 
    if (source.func_76346_g() != null && source.func_76346_g() instanceof EntityLivingBase)
      if (!isWild() && source.func_76346_g() == getOwner()) {
        getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
      } else {
        func_70624_b((EntityLivingBase)source.func_76346_g());
        if (this.field_70146_Z.nextInt(2) == 0) {
          getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
          ((PhaseRamAttack)getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).func_188668_a(new Vec3d((source.func_76346_g()).field_70165_t, (source.func_76346_g()).field_70163_u, (source.func_76346_g()).field_70161_v));
        } 
      }  
    return true;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return func_70965_a((MultiPartEntityPart)this.dragonPartBody, source, amount);
  }
  
  protected void func_70609_aI() {
    List<EntityLivingBase> list = Lists.newArrayList();
    List<Entity> entities = this.field_70170_p.func_72839_b((Entity)this, (new AxisAlignedBB(func_180425_c())).func_186662_g(256.0D));
    for (Entity entity : entities) {
      if (entity instanceof EntityEnderCrystal) {
        entity.func_70097_a(DamageSource.field_76377_j, 1.0F);
        func_70606_j(50.0F);
        this.field_70729_aU = false;
        getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
        this.field_70170_p.func_72960_a((Entity)this, (byte)35);
        func_184185_a(SoundEvents.field_191263_gW, 10.0F, 1.0F);
        this.deathTicks = 0;
        break;
      } 
    } 
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (!this.field_70170_p.field_72995_K) {
      if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
        int i = 1000;
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 8.0D, this.field_70161_v, j));
        } 
      } 
      if (this.deathTicks == 1) {
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
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your " + func_70005_c_() + " has fallen in battle.", new Object[0]));
        } 
      } 
    } 
    double d0 = (isWild() ? 0.0D : (getOwner()).field_70165_t) - this.field_70165_t;
    double d1 = (isWild() ? this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a).func_177956_o() : ((getOwner()).field_70163_u + 4.0D)) - this.field_70163_u;
    double d2 = (isWild() ? 0.0D : (getOwner()).field_70161_v) - this.field_70161_v;
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
    if (this.deathTicks == 200 && !this.field_70170_p.field_72995_K) {
      int i = 2000;
      while (i > 0) {
        int j = EntityXPOrb.func_70527_a(i);
        i -= j;
        this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 8.0D, this.field_70161_v, j));
      } 
      this.field_70170_p.func_180501_a(new BlockPos(this.field_70165_t, this.field_70163_u + 4.0D, this.field_70161_v), Blocks.field_150380_bt.func_176223_P(), 3);
      func_70099_a(new ItemStack(Items.field_151144_bL, 1, 5), 4.0F);
      func_145778_a(Items.field_185160_cR, 1, 4.0F);
      func_70106_y();
    } 
  }
  
  private void func_184668_a(int p_184668_1_) {
    while (p_184668_1_ > 0) {
      int i = EntityXPOrb.func_70527_a(p_184668_1_);
      p_184668_1_ -= i;
      this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, i));
    } 
  }
  
  public int initPathPoints() {
    if (this.pathPoints[0] == null) {
      for (int i = 0; i < 24; i++) {
        int l, i1, j = 5;
        if (i < 12) {
          l = (int)(60.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.2617994F * i)));
          i1 = (int)(60.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.2617994F * i)));
        } else if (i < 20) {
          int lvt_3_1_ = i - 12;
          l = (int)(40.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.3926991F * lvt_3_1_)));
          i1 = (int)(40.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.3926991F * lvt_3_1_)));
          j += 10;
        } else {
          int k1 = i - 20;
          l = (int)(20.0F * MathHelper.func_76134_b(2.0F * (-3.1415927F + 0.7853982F * k1)));
          i1 = (int)(20.0F * MathHelper.func_76126_a(2.0F * (-3.1415927F + 0.7853982F * k1)));
        } 
        int j1 = Math.max(this.field_70170_p.func_181545_F() + 10, this.field_70170_p.func_175672_r(new BlockPos(l, 0, i1)).func_177956_o() + j);
        this.pathPoints[i] = new PathPoint(l, j1, i1);
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
  
  public int getNearestPpIdx(double x, double y, double z) {
    float f = 10000.0F;
    int i = 0;
    PathPoint pathpoint = new PathPoint(MathHelper.func_76128_c(x), MathHelper.func_76128_c(y), MathHelper.func_76128_c(z));
    int j = 0;
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
  
  @Nullable
  public Path findPath(int startIdx, int finishIdx, @Nullable PathPoint andThen) {
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
    int j = 0;
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
        int ran = this.field_70146_Z.nextInt(23);
        if ((this.neighbors[k] & 1 << ran) > 0) {
          PathPoint pathpoint3 = this.pathPoints[ran];
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
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("DragonPhase", this.phaseManager.getCurrentPhase().getPhaseList().getId());
    tagCompound.func_74757_a("CarryingCrystal", isCarryingCrystal());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    if (tagCompund.func_74764_b("DragonPhase"))
      this.phaseManager.setPhase(PhaseList.getById(tagCompund.func_74762_e("DragonPhase"))); 
    setCarryingCrystal(tagCompund.func_74767_n("CarryingCrystal"));
  }
  
  protected void func_70623_bb() {}
  
  public Entity[] func_70021_al() {
    return (Entity[])this.dragonPartArray;
  }
  
  public boolean func_70067_L() {
    return (this.field_70170_p.func_72890_a((Entity)this, this.field_70130_N) != null && func_70089_S());
  }
  
  public World func_82194_d() {
    return this.field_70170_p;
  }
  
  protected void func_85033_bc() {}
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187521_aK;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187526_aP;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187533_aW;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.1F : 10.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadPartYOffset(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
    BlockPos blockpos = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
    if (getOwner() != null)
      blockpos = new BlockPos((Entity)getOwner()); 
    float f = Math.max(MathHelper.func_76133_a(func_174831_c(blockpos)) / 4.0F, 1.0F);
    return p_184667_1_ / f;
  }
  
  public Vec3d getHeadLookVec(float p_184665_1_) {
    Vec3d vec3d;
    IPhase iphase = this.phaseManager.getCurrentPhase();
    PhaseList<? extends IPhase> phaselist = iphase.getPhaseList();
    if (phaselist != PhaseList.LANDING && phaselist != PhaseList.TAKEOFF) {
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
  
  public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc) {
    EntityPlayer entityplayer;
    if (dmgSrc.func_76346_g() instanceof EntityPlayer) {
      entityplayer = (EntityPlayer)dmgSrc.func_76346_g();
    } else {
      entityplayer = this.field_70170_p.func_184139_a(pos, 64.0D, 64.0D);
    } 
    this.phaseManager.getCurrentPhase().onCrystalDestroyed(crystal, pos, dmgSrc, entityplayer);
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (PHASE.equals(key) && this.field_70170_p.field_72995_K)
      this.phaseManager.setPhase(PhaseList.getById(((Integer)func_184212_Q().func_187225_a(PHASE)).intValue())); 
    super.func_184206_a(key);
  }
  
  public PhaseManager getPhaseManager() {
    return this.phaseManager;
  }
  
  public void func_70690_d(PotionEffect potioneffectIn) {
    if (!potioneffectIn.func_188419_a().func_76398_f())
      super.func_70690_d(potioneffectIn); 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  public void func_70653_a(Entity entityIn, float strength, double xRatio, double zRatio) {}
}
