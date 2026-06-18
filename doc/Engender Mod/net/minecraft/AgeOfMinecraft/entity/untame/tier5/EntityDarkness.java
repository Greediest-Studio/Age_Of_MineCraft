package net.minecraft.AgeOfMinecraft.entity.untame.tier5;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityBodyHelperDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.untame.EntityUntameBase;
import net.minecraft.AgeOfMinecraft.entity.untame.ai.darkness.AIPhase1;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.util.Maths;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDarkness extends EntityUntameBase implements IEntityMultiPart, IMob {
  private final AIPhase1 AI_1 = new AIPhase1(this);
  
  public boolean shouldMove = true;
  
  public boolean shouldChangeAI = true;
  
  public int wingPos = 0;
  
  public double targetYRelative;
  
  private static final DataParameter<Float> maxEnergy = EntityDataManager.func_187226_a(EntityDarkness.class, DataSerializers.field_187193_c);
  
  private static final DataParameter<Float> energy = EntityDataManager.func_187226_a(EntityDarkness.class, DataSerializers.field_187193_c);
  
  public static final DataParameter<Boolean> isHardmode = EntityDataManager.func_187226_a(EntityDarkness.class, DataSerializers.field_187198_h);
  
  public static final DataParameter<Integer> phase = EntityDataManager.func_187226_a(EntityDarkness.class, DataSerializers.field_187192_b);
  
  public static final DataParameter<Integer> cooldown = EntityDataManager.func_187226_a(EntityDarkness.class, DataSerializers.field_187192_b);
  
  public static final DataParameter<Integer> attackTicks = EntityDataManager.func_187226_a(EntityDarkness.class, DataSerializers.field_187192_b);
  
  public static final DataParameter<Integer> attack = EntityDataManager.func_187226_a(EntityDarkness.class, DataSerializers.field_187192_b);
  
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
  
  public float prevAnimTime;
  
  public float animTime;
  
  public boolean slowed;
  
  public final List<EntityEnderCrystal> crystals = Lists.newArrayList();
  
  public EntityDarkness(World worldIn) {
    super(worldIn);
    setEnergy(getMaxEnergy());
    func_70105_a(16.0F, 3.6F);
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 6.0F, 6.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 6.0F, 6.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F) };
    this.field_70145_X = true;
    this.field_70178_ae = true;
    this.field_70158_ak = true;
    this.field_98038_p = true;
  }
  
  public int playMusic() {
    return (getPhase() < 4) ? 15 : ((getPhase() < 13) ? 16 : 0);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(128.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(750.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(0.5D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(0.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(1.0D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(maxEnergy, Float.valueOf(20.0F));
    func_184212_Q().func_187214_a(energy, Float.valueOf(20.0F));
    func_184212_Q().func_187214_a(isHardmode, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(phase, Integer.valueOf(0));
    func_184212_Q().func_187214_a(attack, Integer.valueOf(0));
    func_184212_Q().func_187214_a(attackTicks, Integer.valueOf(0));
    func_184212_Q().func_187214_a(cooldown, Integer.valueOf(0));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("Phase", getPhase());
    tagCompound.func_74776_a("Energy", getEnergy());
    tagCompound.func_74757_a("Hardmode", getHardmode());
    tagCompound.func_74776_a("Energy", getEnergy());
    tagCompound.func_74776_a("MaxEnergy", getMaxEnergy());
    if (EngenderConfig.debugMode) {
      tagCompound.func_74768_a("Move", getAttackID());
      tagCompound.func_74768_a("Cooldown", getCooldown());
    } 
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setHardmode(tagCompund.func_74767_n("Hardmode"));
    setPhase(tagCompund.func_74762_e("Phase"));
    setEnergy(tagCompund.func_74760_g("Energy"));
    setMaxEnergy(tagCompund.func_74760_g("MaxEnergy"));
    if (EngenderConfig.debugMode) {
      setAttackID(tagCompund.func_74762_e("Move"));
      setCooldown(tagCompund.func_74762_e("Cooldown"));
    } 
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    setMaxEnergy(750.0F * (this.field_70170_p.field_73010_i.size() * 0.33F + 1.0F));
    setEnergy(getMaxEnergy());
    setAttackID(1);
    this.forceNewTarget = true;
    sendMessage("spawn", true);
    return livingdata;
  }
  
  public void func_174812_G() {
    setPhase(18);
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (getPhase() > 9 && getPhase() < 14 && getEnergy() <= 0.0F) {
      setPhase(14);
    } else if (getPhase() < 10 && getEnergy() <= 0.0F) {
      setPhase(19);
    } 
    if (this.field_70170_p.field_72995_K) {
      float f12 = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
      f12 *= (float)Math.pow(2.0D, this.field_70181_x);
      if (this.slowed) {
        this.animTime += f12 * 0.5F;
      } else if (this.wingPos == 1) {
        this.animTime = 1.25F;
      } else if (this.wingPos == 2) {
        this.animTime = 0.45F;
      } else {
        this.animTime += f12;
      } 
    } 
    double targetX = this.targetX - this.field_70165_t;
    double targetY = this.targetY - this.field_70163_u;
    double targetZ = this.targetZ - this.field_70161_v;
    double distance = targetX * targetX + targetY * targetY + targetZ * targetZ;
    if (getCooldown() > 0)
      setCooldown(getCooldown() - 1); 
    if (getAttackTime() > 0)
      setAttackTime(getAttackTime() - 1); 
    onPhaseUpdate();
    onAIUpdate(distance);
    this.innerRotation++;
  }
  
  public void func_70636_d() {
    if (this.field_70703_bu)
      this.field_70703_bu = false; 
    if (!this.field_70122_E)
      this.field_70122_E = true; 
    if (this.field_70160_al)
      this.field_70160_al = false; 
    if (!this.field_70145_X)
      this.field_70145_X = true; 
    if (func_175446_cd() && !func_174814_R())
      func_174810_b(true); 
    onCrystalUpdate();
    if (func_110138_aP() != getMaxEnergy())
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(getMaxEnergy()); 
    if (func_110143_aJ() != getEnergy())
      func_70606_j(getEnergy()); 
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
    float f2 = this.field_70177_z * 0.017453292F;
    float f19 = MathHelper.func_76126_a(f2);
    float f3 = MathHelper.func_76134_b(f2);
    double[] adouble = getMovementOffsets(5, 1.0F);
    double[] adouble1 = getMovementOffsets(14, 1.0F);
    getMovementOffsets(16, 1.0F);
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
      MultiPartEntityPart.func_70012_b(this.field_70165_t - ((f19 * f23 + f22 * f24) * f16), this.field_70163_u - (MathHelper.func_76126_a(this.animTime * 6.2831855F + i) * 0.2F * (i + 1)) - (f18 * (2.0F + (1 * i))) + f23, this.field_70161_v + ((f3 * f23 + f7 * f24) * f16), 0.0F, 0.0F);
    } 
    for (int l = 0; l < this.dragonPartArray.length; l++) {
      (this.dragonPartArray[l]).field_70169_q = (avec3d[l]).field_72450_a;
      (this.dragonPartArray[l]).field_70167_r = (avec3d[l]).field_72448_b;
      (this.dragonPartArray[l]).field_70166_s = (avec3d[l]).field_72449_c;
    } 
    if (this.field_70173_aa % 5 == 0 && this.slowed)
      this.slowed = false; 
    if (this.field_70170_p.field_72995_K)
      if (!func_174814_R()) {
        float f = MathHelper.func_76134_b(this.animTime * 6.2831855F);
        float f1 = MathHelper.func_76134_b(this.prevAnimTime * 6.2831855F);
        if (f1 <= -0.3F && f >= -0.3F)
          this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), 5.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.3F, false); 
      }  
    this.prevAnimTime = this.animTime;
    if (func_175446_cd()) {
      this.animTime = 0.0F;
    } else if (func_70089_S()) {
      if (EngenderConfig.mobs.regeneration && this.field_70173_aa % 20 == 0)
        change(1.0F); 
      this.field_70715_bh.func_75774_a();
      if (this.field_70173_aa % 40 == 0 && !this.field_70170_p.field_72995_K && (func_70638_az() == null || Maths.chance(75))) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e()), Predicates.and(EntitySelectors.field_94557_a, EntitySelectors.field_180132_d));
        for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
          EntityLivingBase entitylivingbase = list.get(this.field_70146_Z.nextInt(list.size()));
          if (entitylivingbase != this && entitylivingbase.func_70089_S() && func_70685_l((Entity)entitylivingbase)) {
            if ((entitylivingbase instanceof EntityPlayer && !((EntityPlayer)entitylivingbase).func_184812_l_() && !((EntityPlayer)entitylivingbase).func_175149_v()) || !(entitylivingbase instanceof EntityPlayer)) {
              func_70624_b(entitylivingbase);
              break;
            } 
          } else {
            list.remove(entitylivingbase);
          } 
        } 
      } 
      if (!this.field_70170_p.field_72995_K)
        if (func_70089_S()) {
          ChunkLoadingEvent.updateLoaded((Entity)this);
        } else {
          ChunkLoadingEvent.stopLoading((Entity)this);
        }  
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
          double targetX = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / this.field_70716_bi;
          double targetY = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / this.field_70716_bi;
          double targetZ = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / this.field_70716_bi;
          double distance = MathHelper.func_76138_g(this.field_184626_bk - this.field_70177_z);
          this.field_70177_z = (float)(this.field_70177_z + distance / this.field_70716_bi);
          this.field_70125_A = (float)(this.field_70125_A + (this.field_70709_bj - this.field_70125_A) / this.field_70716_bi);
          this.field_70716_bi--;
          func_70107_b(targetX, targetY, targetZ);
          func_70101_b(this.field_70177_z, this.field_70125_A);
        } 
      } else {
        double targetX = this.targetX - this.field_70165_t;
        double targetY = this.targetY - this.field_70163_u;
        double targetZ = this.targetZ - this.field_70161_v;
        targetY /= MathHelper.func_76133_a(targetX * targetX + targetZ * targetZ);
        float f12 = 0.6F;
        if (targetY < -f12)
          targetY = -f12; 
        if (targetY > f12)
          targetY = f12; 
        this.field_70181_x += targetY * 0.10000000149011612D;
        this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
        double d4 = 180.0D - Math.atan2(targetX, targetZ) * 180.0D / Math.PI;
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
        float f7 = (float)(2.0D / (d9 + 1.0D));
        float f8 = 0.06F;
        func_191958_b(0.0F, 0.0F, -1.0F, 0.06F * (f7 * f8 + 1.0F - f8));
        if (this.shouldMove)
          func_70091_d(MoverType.SELF, this.field_70159_w * func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b(), this.field_70181_x * func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b(), this.field_70179_y * func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b()); 
        Vec3d vec31 = (new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y)).func_72432_b();
        float f9 = (float)(vec31.func_72430_b(vec32) + 1.0D) / 2.0F;
        f9 = 0.8F + 0.15F * f9;
        this.field_70159_w *= f9;
        this.field_70179_y *= f9;
        this.field_70181_x *= 0.9100000262260437D;
      } 
    } else {
      func_70674_bp();
    } 
    if (getEnergy() <= 0.0F && this.deathTicks > 0) {
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
      if (getPhase() > 2) {
        damageEntities(this.dragonPartHead, (getAttackID() == 4) ? 0.5F : 1.0F, "darkness.bite", (getAttackID() == 3) ? 8.0D : 0.0D);
        damageEntities(this.dragonPartNeck, 0.5F, "mob", 8.0D);
        damageEntities(this.dragonPartBody, 0.5F, "mob", 8.0D);
        damageEntities(this.dragonPartTail1, 0.5F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail2, 0.5F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail3, 0.5F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartWing1, 0.35F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
        damageEntities(this.dragonPartWing2, 0.35F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
      } else if (getPhase() == 1) {
        damageEntities(this.dragonPartHead, (getAttackID() == 4) ? 0.5F : 1.0F, "darkness.bite", (getAttackID() == 3) ? 8.0D : 0.0D);
        damageEntities(this.dragonPartNeck, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartBody, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartTail1, 0.35F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail2, 0.35F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail3, 0.35F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartWing1, 0.25F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
        damageEntities(this.dragonPartWing2, 0.25F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
      } else if (getPhase() == 0) {
        damageEntities(this.dragonPartHead, (getAttackID() == 4) ? 0.5F : 1.0F, "darkness.bite", (getAttackID() == 3) ? 8.0D : 0.0D);
        damageEntities(this.dragonPartNeck, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartBody, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartTail1, 0.35F, "darkness.tail", 8.0D);
        damageEntities(this.dragonPartTail2, 0.35F, "darkness.tail", 8.0D);
        damageEntities(this.dragonPartTail3, 0.35F, "darkness.tail", 8.0D);
        damageEntities(this.dragonPartWing1, 0.25F, "darkness.wing", 24.0D);
        damageEntities(this.dragonPartWing2, 0.25F, "darkness.wing", 24.0D);
      } else {
        damageEntities(this.dragonPartHead, 1.0F, "generic", 24.0D);
        damageEntities(this.dragonPartNeck, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartBody, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartTail1, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartTail2, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartTail3, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartWing1, 0.25F, "generic", 24.0D);
        damageEntities(this.dragonPartWing2, 0.25F, "generic", 24.0D);
      } 
      destroyBlocksInAABB(this.dragonPartHead.func_174813_aQ());
      destroyBlocksInAABB(this.dragonPartNeck.func_174813_aQ());
      destroyBlocksInAABB(this.dragonPartBody.func_174813_aQ());
      destroyBlocksInAABB(this.dragonPartWing1.func_174813_aQ());
      destroyBlocksInAABB(this.dragonPartWing2.func_174813_aQ());
      destroyBlocksInAABB(this.dragonPartTail1.func_174813_aQ());
      destroyBlocksInAABB(this.dragonPartTail2.func_174813_aQ());
      destroyBlocksInAABB(this.dragonPartTail3.func_174813_aQ());
      if (func_70638_az() != null && !func_70638_az().func_70089_S())
        func_70624_b(null); 
      this.field_70760_ar = this.field_70761_aq = this.field_70177_z;
    } 
  }
  
  protected void func_70609_aI() {
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (!this.field_70170_p.field_72995_K) {
      if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
        int i = 5000;
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
      } 
      double d0 = 0.0D;
      double d1 = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a).func_177956_o();
      double d2 = 0.0D;
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
        int i = 100000;
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 8.0D, this.field_70161_v, j));
        } 
        this.field_70170_p.func_180501_a(new BlockPos(this.field_70165_t, this.field_70163_u + 4.0D, this.field_70161_v), Blocks.field_150380_bt.func_176223_P(), 3);
        func_70106_y();
      } 
    } 
  }
  
  public void onPhaseUpdate() {
    if (getPhase() == 0 && getEnergy() <= getMaxEnergy() * 0.75F) {
      setPhase(2);
    } else if (getPhase() == 2 && getEnergy() <= getMaxEnergy() * 0.15F) {
      setPhase(4);
    } else if (getPhase() == 4 && getEnergy() < getMaxEnergy()) {
      change(getMaxEnergy() * 0.003F);
      if (getEnergy() >= getMaxEnergy())
        setPhase(6); 
    } else if (getPhase() == 6 && getEnergy() <= getMaxEnergy() * 0.6F) {
      setPhase(8);
    } else if (getPhase() == 8 && getEnergy() <= getMaxEnergy() * 0.2F) {
      setPhase(10);
    } 
    if (func_70638_az() != null && getCooldown() <= 0 && getAttackTime() <= 0 && this.shouldChangeAI) {
      switch (getPhase()) {
        case 18:
          setAttack(0, 400, 100.0F);
          return;
        case 19:
          setAttack(0, 40, 100.0F);
          return;
      } 
      if (!setAttack(2, 140, 50.0F))
        if (!setAttack(6, 40, 60.0F))
          setAttack(1, 180, 100.0F);  
    } else if (this.shouldChangeAI) {
      setAttack(1, 180, 100.0F);
    } 
  }
  
  public void onAIUpdate(double distance) {
    if (this.field_70170_p.func_175672_r(func_180425_c()).func_177956_o() > 0) {
      this.targetYRelative = this.field_70170_p.func_175672_r(func_180425_c()).func_177956_o();
    } else {
      this.targetYRelative = 60.0D;
    } 
    if (this.field_70173_aa % 200 == 0 && func_70638_az() != null) {
      EngenderMod.console("Attacking: " + func_70638_az().func_70005_c_() + "\nCurrent Attack Mode: " + getAttackID() + "\nCurrent Phase: " + getPhase());
    } else if (this.field_70173_aa % 200 == 0) {
      EngenderMod.console("Attacking: None\nCurrent Attack Mode: " + getAttackID() + "\nCurrent Phase: " + getPhase());
    } 
    switch (getPhase()) {
      case 0:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
          case 2:
            this.AI_1.swoop(distance);
            return;
          case 6:
            this.AI_1.dragonFireball();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 1:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
          case 2:
            this.AI_1.swoop(distance);
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 2:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 3:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 4:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 5:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 6:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 7:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 8:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 9:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 10:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.swoop(distance);
        return;
      case 11:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.swoop(distance);
        return;
      case 12:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 13:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 14:
        this.AI_1.instantdeath();
        return;
      case 15:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 16:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 17:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 18:
        this.AI_1.leave();
        return;
      case 19:
        this.AI_1.instantdeath();
        return;
    } 
    this.AI_1.stay();
  }
  
  public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc) {}
  
  private void onCrystalUpdate() {
    for (int i = 1; i <= this.crystals.size(); i++) {
      EntityEnderCrystal crystal = this.crystals.get(i - 1);
      if (crystal != null)
        if (crystal.field_70128_L || func_70032_d((Entity)crystal) > 48.0D) {
          this.crystals.remove(crystal);
          i--;
        } else if (crystal.field_70261_a % 10 == 0 && getEnergy() < getEnergyCap()) {
          setEnergy(getEnergy() + 2.0F);
        }  
    } 
    if (Maths.random(10) == 0) {
      List<EntityEnderCrystal> list = this.field_70170_p.func_72872_a(EntityEnderCrystal.class, func_174813_aQ().func_186662_g(48.0D));
      for (EntityEnderCrystal entityendercrystal : list) {
        if (!this.crystals.contains(entityendercrystal))
          this.crystals.add(entityendercrystal); 
      } 
    } 
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186745_a(BossInfo.Color.PURPLE);
  }
  
  public void func_70653_a(Entity entityIn, float strength, double xRatio, double zRatio) {}
  
  public void func_184178_b(EntityPlayerMP player) {
    super.func_184178_b(player);
    this.bossInfo.func_186760_a(player);
  }
  
  public void func_184203_c(EntityPlayerMP player) {
    super.func_184203_c(player);
    this.bossInfo.func_186761_b(player);
  }
  
  public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_) {
    if (getEnergy() <= 0.0F)
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
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    super.func_70074_a(entityLivingIn);
    if (!this.field_70170_p.field_72995_K && this.deathTicks <= 0 && entityLivingIn instanceof EntityPlayer)
      if (getPhase() > 3 && getPhase() < 12) {
        sendMessage("kill.final", 10);
      } else {
        sendMessage("kill.generic", 10);
      }  
  }
  
  protected void func_70623_bb() {}
  
  protected void func_85033_bc() {}
  
  protected void damageEntities(ExtendMultiPartEntityPart bodypart, float percentage, String damagetype, double velocity) {
    List<EntityLivingBase> list = this.field_70170_p.func_72872_a(EntityLivingBase.class, bodypart.func_174813_aQ().func_186662_g(3.0D));
    for (EntityLivingBase entity : list) {
      if (entity != this && func_70089_S() && (!(entity instanceof EntityPlayer) || (entity instanceof EntityPlayer && !((EntityPlayer)entity).func_184812_l_() && !((EntityPlayer)entity).func_175149_v()))) {
        entity.func_70097_a((DamageSource)new EntityDamageSource(damagetype, (Entity)this), (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * percentage);
        this.slowed = true;
        if (velocity > 0.0D && !(entity instanceof IEntityMultiPart) && entity.field_70173_aa % 10 == 0) {
          double d2 = entity.field_70165_t - bodypart.field_70165_t;
          double d3 = entity.field_70161_v - bodypart.field_70165_t;
          double d4 = d2 * d2 + d3 * d3;
          entity.func_70024_g(d2 / d4 * velocity, velocity * 0.025D, d3 / d4 * velocity);
        } 
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
          if (EngenderConfig.mobs.grief && func_70089_S() && !block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos) && 
            block.canEntityDestroy(iblockstate, (IBlockAccess)this.field_70170_p, blockpos, (Entity)this) && 
            block != Blocks.field_150384_bq && block != Blocks.field_150380_bt && block != Blocks.field_150357_h && block != Blocks.field_150377_bs && block != Blocks.field_150343_Z && block != Blocks.field_150483_bI && block != Blocks.field_185776_dc && block != Blocks.field_185777_dd && block != Blocks.field_150411_aY && block != Blocks.field_185775_db)
            if (!this.field_70170_p.field_72995_K) {
              flag1 = (this.field_70170_p.func_175698_g(blockpos) || flag1);
            } else {
              this.slowed = true;
            }  
        } 
      } 
    } 
    return EngenderConfig.mobs.grief;
  }
  
  public boolean func_70965_a(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    if (func_180431_b(source))
      damage = 0.0F; 
    if (dragonPart != this.dragonPartHead)
      damage = damage / 5.0F + Math.min(damage, 1.0F); 
    if (func_180431_b(source) || damage < 0.01F || source == DamageSource.field_82727_n || source == DamageSource.field_76368_d || source == DamageSource.field_76369_e || source == DamageSource.field_191291_g || source == DamageSource.field_76367_g)
      return false; 
    return super.func_70097_a(source, damage);
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return func_70965_a((MultiPartEntityPart)this.dragonPartBody, source, amount);
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
  
  protected EntityBodyHelper func_184650_s() {
    return (EntityBodyHelper)new EntityBodyHelperDragon((EntityLivingBase)this);
  }
  
  public World func_82194_d() {
    return this.field_70170_p;
  }
  
  public boolean func_180431_b(DamageSource source) {
    return isPhase(4, 5) ? true : super.func_180431_b(source);
  }
  
  public boolean func_70067_L() {
    return (this.field_70170_p.func_72890_a((Entity)this, this.field_70130_N) != null && func_70089_S());
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadPartYOffset(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
    BlockPos blockpos = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
    float f = Math.max(MathHelper.func_76133_a(func_174831_c(blockpos)) / 4.0F, 1.0F);
    return p_184667_1_ / f;
  }
  
  public Vec3d getHeadLookVec(float p_184665_1_) {
    BlockPos blockpos = this.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
    float f = Math.max(MathHelper.func_76133_a(func_174831_c(blockpos)) / 4.0F, 1.0F);
    float f1 = 6.0F / f;
    float f2 = this.field_70125_A;
    float f3 = 1.5F;
    this.field_70125_A = -f1 * f3 * 5.0F;
    Vec3d vec3d = func_70676_i(p_184665_1_);
    this.field_70125_A = f2;
    return vec3d;
  }
  
  public Entity[] func_70021_al() {
    return (Entity[])this.dragonPartArray;
  }
  
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
  
  public float func_70047_e() {
    return 2.325F;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public boolean isPhase(int phase) {
    return (phase == getPhase());
  }
  
  public boolean isPhase(int phase, int phaseHard) {
    return (phase == getPhase()) ? true : ((phaseHard == getPhase()));
  }
  
  public int getPhase() {
    return ((Integer)this.field_70180_af.func_187225_a(phase)).intValue();
  }
  
  public void setPhase(int newPhase) {
    if (!this.field_70170_p.field_72995_K)
      switch (newPhase) {
        case 1:
          changeStats(15.0D, 2.0D, 0.5D, 25.0D, 1.2D, 256.0D);
          sendMessage("respawn", 5);
          break;
        case 2:
          changeStats(15.0D, 10.0D, 1.0D, 40.0D, 1.2D, 256.0D);
          sendMessage("phase2", 5);
          break;
        case 3:
          changeStats(20.0D, 15.0D, 1.0D, 60.0D, 1.55D, 256.0D);
          sendMessage("rephase2", 5);
          break;
        case 4:
          sendMessage("phase3", 5);
          sendMessage("phase3.1", true);
          break;
        case 5:
          sendMessage("rephase3", 5);
          sendMessage("rephase3.1", true);
          break;
        case 6:
          changeStats(10.0D, 10.0D, 1.0D, 200.0D, 1.5D, 256.0D);
          sendMessage("phase3.2", true);
          break;
        case 7:
          changeStats(20.0D, 10.0D, 1.0D, 235.0D, 2.0D, 256.0D);
          sendMessage("phase3.2", true);
          break;
        case 8:
          sendMessage("phase3.comment1", 5);
          break;
        case 9:
          sendMessage("rephase3.comment1", 5);
          break;
        case 10:
          changeStats(20.0D, 20.0D, 1.0D, 200.0D, 1.55D, 512.0D);
          sendMessage("phase3.comment2", 5);
          break;
        case 11:
          changeStats(30.0D, 30.0D, 1.0D, 235.0D, 2.25D, 512.0D);
          sendMessage("rephase3.comment2", 5);
          break;
        case 12:
          sendMessage("phase3.move.finalcannon", true);
          sendMessage("phase3.move.finalcannon", 5);
          break;
        case 13:
          changeStats(0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 128.0D);
          sendMessage("mercy", 10);
          break;
        case 14:
          sendMessage("death", 10);
          break;
        case 15:
          sendMessage("redeath", 10);
          break;
        case 16:
          sendMessage("save", 10);
          break;
        case 17:
          sendMessage("win", 10);
          break;
        case 18:
          setEnergy(getMaxEnergy());
          changeStats(30.0D, 30.0D, 1.0D, 0.0D, 0.5D, 512.0D);
          sendMessage("cheat.leave", 5);
          break;
        case 19:
          sendMessage("cheat.instakill", 5);
          break;
        default:
          changeStats(10.0D, 0.0D, 0.5D, 20.0D, 1.02D, 128.0D);
          sendMessage("spawn", 5);
          break;
      }  
    this.forceNewTarget = true;
    setAttack(1, 80, 100.0F);
    func_184212_Q().func_187227_b(phase, Integer.valueOf(newPhase));
  }
  
  public boolean isAttackID(int id) {
    return (getAttackID() == id);
  }
  
  public int getAttackID() {
    return ((Integer)this.field_70180_af.func_187225_a(attack)).intValue();
  }
  
  public void setAttackID(int newAttack) {
    func_184212_Q().func_187227_b(attack, Integer.valueOf(newAttack));
  }
  
  public int getAttackTime() {
    return ((Integer)this.field_70180_af.func_187225_a(attackTicks)).intValue();
  }
  
  public void setAttackTime(int ticks) {
    func_184212_Q().func_187227_b(attackTicks, Integer.valueOf(ticks));
  }
  
  public int getCooldown() {
    return ((Integer)this.field_70180_af.func_187225_a(cooldown)).intValue();
  }
  
  public void setCooldown(int newCooldown) {
    func_184212_Q().func_187227_b(cooldown, Integer.valueOf(newCooldown));
  }
  
  public boolean setAttack(int id, int length, float chance) {
    if (Maths.chance(chance)) {
      setAttackID(id);
      if (length > 0)
        setAttackTime(length); 
      return true;
    } 
    return false;
  }
  
  public boolean getHardmode() {
    return ((Boolean)this.field_70180_af.func_187225_a(isHardmode)).booleanValue();
  }
  
  public void setHardmode(boolean hardmode) {
    func_184212_Q().func_187227_b(isHardmode, Boolean.valueOf(hardmode));
  }
  
  protected void change(float energy) {
    if (getEnergy() < getMaxEnergy())
      setEnergy(getEnergy() + MathHelper.func_76131_a(energy, 0.0F, getMaxEnergy())); 
  }
  
  protected float getEnergy() {
    return ((Float)this.field_70180_af.func_187225_a(energy)).floatValue();
  }
  
  protected void setEnergy(float newEnergy) {
    func_184212_Q().func_187227_b(energy, Float.valueOf(newEnergy));
  }
  
  protected float getMaxEnergy() {
    return ((Float)this.field_70180_af.func_187225_a(maxEnergy)).floatValue();
  }
  
  protected void setMaxEnergy(float newEnergy) {
    func_184212_Q().func_187227_b(maxEnergy, Float.valueOf(newEnergy));
  }
  
  public boolean func_184222_aU() {
    return EngenderMod.sensorsShowJzahars(this.field_70170_p);
  }
  
  private float getEnergyCap() {
    switch (getPhase()) {
      case 1:
        return getMaxEnergy() * 0.75F;
      case 5:
        return getMaxEnergy() * 0.5F;
    } 
    return getMaxEnergy();
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.ENDER;
  }
  
  public float getDamageCap() {
    return EngenderConfig.nightmareMode ? 25.0F : 50.0F;
  }
  
  public float getDamageReduction(DamageSource source, Entity entity) {
    return (entity == null) ? 1.0F : (!entity.func_184222_aU() ? 0.9F : (!(entity instanceof EntityPlayer) ? ((entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase) ? 0.85F : ((getPhase() < 2) ? 0.8F : 0.75F)) : ((getPhase() > 3) ? 0.98F : 1.0F)));
  }
  
  protected void func_70665_d(DamageSource source, float amount) {
    if (!func_180431_b(source)) {
      amount = ForgeHooks.onLivingHurt((EntityLivingBase)this, source, amount);
      if (amount <= 0.0F)
        return; 
      amount = func_70655_b(source, amount);
      amount = func_70672_c(source, amount);
      float f = amount;
      amount = Math.max(amount - func_110139_bj(), 0.0F);
      func_110149_m(func_110139_bj() - f - amount);
      amount = ForgeHooks.onLivingDamage((EntityLivingBase)this, source, amount);
      if (amount != 0.0F) {
        float f1 = getEnergy();
        func_110142_aN().func_94547_a(source, f1, amount);
        setEnergy(f1 - amount);
        func_110149_m(func_110139_bj() - amount);
      } 
    } 
  }
  
  public boolean func_70089_S() {
    return (getEnergy() >= 0.0F);
  }
  
  public boolean shootBall(Entity entity) {
    if (entity != null) {
      shootBall(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
      return true;
    } 
    return false;
  }
  
  public void shootBall(double posX, double posY, double posZ) {
    if (!this.field_70170_p.field_72995_K) {
      double d6 = this.dragonPartHead.field_70165_t;
      double d7 = this.dragonPartHead.field_70163_u + 0.5D;
      double d8 = this.dragonPartHead.field_70161_v;
      double d9 = posX - d6;
      double d10 = posY + 1.0D - d7;
      double d11 = posZ - d8;
      this.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
      EntityDragonFireball entitydragonfireball = new EntityDragonFireball(this.field_70170_p, (EntityLivingBase)this, d9, d10, d11);
      entitydragonfireball.field_70165_t = d6;
      entitydragonfireball.field_70163_u = d7;
      entitydragonfireball.field_70161_v = d8;
      this.field_70170_p.func_72838_d((Entity)entitydragonfireball);
    } 
  }
  
  private void sendMessage(String key, boolean isStatus) {
    sendMessage(key, 0, isStatus);
  }
  
  private void sendMessage(String key, int iter) {
    sendMessage(key, iter, false);
  }
  
  private void sendMessage(String key, int iter, boolean isStatus) {
    if (!this.field_70170_p.field_72995_K) {
      String iteration = (iter > 1) ? ("." + Maths.random(1, iter)) : "";
      String status = isStatus ? "status." : "";
      for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
        entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation(TextFormatting.LIGHT_PURPLE + I18n.func_135052_a("entity.darkness." + status + key + iteration, new Object[] { entityplayer.func_70005_c_() }), new Object[0]), isStatus);
      } 
    } 
  }
  
  public void changeStats(double defense, double toughness, double kbr, double attack, double speed, double followRange) {
    if (func_110148_a(SharedMonsterAttributes.field_188791_g).func_111125_b() != defense)
      func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(defense); 
    if (func_110148_a(SharedMonsterAttributes.field_189429_h).func_111125_b() != toughness)
      func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(toughness); 
    if (func_110148_a(SharedMonsterAttributes.field_111266_c).func_111125_b() != kbr)
      func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(kbr); 
    if (func_110148_a(SharedMonsterAttributes.field_111264_e).func_111125_b() != attack)
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(attack); 
    if (func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b() != speed)
      func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(speed); 
    if (func_110148_a(SharedMonsterAttributes.field_111265_b).func_111125_b() != followRange)
      func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(followRange); 
  }
}
