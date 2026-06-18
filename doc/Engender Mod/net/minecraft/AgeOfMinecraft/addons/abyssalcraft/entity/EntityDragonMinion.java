package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import java.util.Iterator;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityDragonMinion extends EntityTameBase implements IEntityMultiPart, Light, Flying, Undead {
  public static final float innerRotation = 0.0F;
  
  public double targetX;
  
  public double targetY;
  
  public double targetZ;
  
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
  
  public boolean forceNewTarget;
  
  public Entity target;
  
  public EntityDragonBoss healingcircle;
  
  public EntityDragonMinion(World par1World) {
    super(par1World);
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 3.0F, 3.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 3.0F, 3.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 4.0F, 4.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 2.0F, 2.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 2.0F, 2.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 2.0F, 2.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 2.0F, 2.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 2.0F, 2.0F) };
    func_70606_j(func_110138_aP());
    func_70105_a(8.0F, 3.0F);
    this.field_70145_X = true;
    this.targetY = 100.0D;
    this.field_70178_ae = true;
    this.isOffensive = true;
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityDragonMinion(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(60.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
    } 
  }
  
  public int getNextLevelRequirement() {
    return 300;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 2.0F;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
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
  
  protected Item func_146068_u() {
    return ACItems.coralium_plagued_flesh;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_SPECTRAL_DRAGON;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.2F : 2.0F;
  }
  
  public void func_70636_d() {
    if (this.field_70173_aa <= 20) {
      if (this.field_70173_aa == 3)
        func_70656_aK(); 
      this.field_70159_w *= 0.0D;
      this.field_70179_y *= 0.0D;
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
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
      if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && this.isOffensive && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()))
        if (func_70068_e((Entity)func_70638_az()) < (this.reachWidth * this.reachWidth + ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N) * ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N)) + 9.0D && (this.field_70173_aa + func_145782_y()) % 10 == 0)
          func_70652_k((Entity)func_70638_az());  
    } 
    func_174810_b(func_175446_cd());
    if (this.field_70170_p.field_72995_K) {
      float f = MathHelper.func_76134_b(this.animTime * 3.1415927F * 2.0F);
      float f1 = MathHelper.func_76134_b(this.prevAnimTime * 3.1415927F * 2.0F);
      if (f1 <= -0.3F && f >= -0.3F)
        this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187524_aN, func_184176_by(), func_70599_aP(), func_70647_i() + 0.1F, false); 
    } 
    this.prevAnimTime = this.animTime;
    if (func_110143_aJ() <= 0.0F) {
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity)
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + f, this.field_70163_u + 2.0D + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]); 
    } else if (!func_175446_cd()) {
      if (!isWild() && func_70068_e((Entity)getOwner()) > 4069.0D) {
        this.target = (Entity)getOwner();
        func_70624_b(null);
      } 
      if (func_70638_az() != null && this.target == null && this.field_70173_aa + func_145782_y() % 40 == 0)
        this.target = (Entity)func_70638_az(); 
      updateHealingCircle();
      float f = 0.2F / (MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 10.0F + 1.0F);
      f *= (float)Math.pow(2.0D, this.field_70181_x);
      if (this.field_70173_aa > 20)
        this.animTime += f; 
      if (func_70631_g_()) {
        this.animTime += f;
        this.animTime += f;
      } 
      if (func_184207_aI() && ((EntityLivingBase)func_184179_bs()).field_191988_bg == 0.0F)
        this.animTime += 0.1F; 
      this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
      if (this.ringBufferIndex < 0)
        for (int i = 0; i < this.ringBuffer.length; i++) {
          this.ringBuffer[i][0] = this.field_70177_z;
          this.ringBuffer[i][1] = this.field_70163_u;
        }  
      if (++this.ringBufferIndex == this.ringBuffer.length)
        this.ringBufferIndex = 0; 
      this.ringBuffer[this.ringBufferIndex][0] = this.field_70177_z;
      this.ringBuffer[this.ringBufferIndex][1] = this.field_70163_u;
      this.field_70761_aq = this.field_70177_z;
      this.dragonPartHead.field_70130_N = this.dragonPartHead.field_70131_O = 1.5F;
      this.dragonPartNeck.field_70130_N = this.dragonPartNeck.field_70131_O = 1.5F;
      this.dragonPartTail1.field_70130_N = this.dragonPartTail1.field_70131_O = 1.0F;
      this.dragonPartTail2.field_70130_N = this.dragonPartTail2.field_70131_O = 1.0F;
      this.dragonPartTail3.field_70130_N = this.dragonPartTail3.field_70131_O = 1.0F;
      this.dragonPartBody.field_70131_O = 1.5F;
      this.dragonPartBody.field_70130_N = 2.5F;
      this.dragonPartWing1.field_70131_O = 1.5F;
      this.dragonPartWing1.field_70130_N = 2.0F;
      this.dragonPartWing2.field_70131_O = 1.5F;
      this.dragonPartWing2.field_70130_N = 1.5F;
      float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
      float f16 = MathHelper.func_76134_b(f14);
      float f18 = MathHelper.func_76126_a(f14);
      float f21 = this.field_70177_z * 0.017453292F;
      float f19 = MathHelper.func_76126_a(f21);
      float f31 = MathHelper.func_76134_b(f21);
      double[] adouble = getMovementOffsets(5, 1.0F);
      double[] adouble1 = getMovementOffsets(14, 1.0F);
      double[] adouble2 = getMovementOffsets(16, 1.0F);
      this.dragonPartBody.func_70071_h_();
      this.dragonPartBody.func_70012_b(this.field_70165_t + (f19 * 0.25F), this.field_70163_u, this.field_70161_v - (f31 * 0.25F), 0.0F, 0.0F);
      this.dragonPartWing1.func_70071_h_();
      this.dragonPartWing1.func_70012_b(this.field_70165_t + (f31 * 2.25F), this.field_70163_u + (MathHelper.func_76126_a(this.animTime * 3.0F) * 1.5F), this.field_70161_v + (f19 * 2.25F), 0.0F, 0.0F);
      this.dragonPartWing2.func_70071_h_();
      this.dragonPartWing2.func_70012_b(this.field_70165_t - (f31 * 2.25F), this.field_70163_u + (MathHelper.func_76126_a(this.animTime * 3.0F) * 1.5F), this.field_70161_v - (f19 * 2.25F), 0.0F, 0.0F);
      this.dragonPartNeck.func_70071_h_();
      this.dragonPartNeck.func_70012_b(this.field_70165_t + (f19 * 2.0F), this.field_70163_u, this.field_70161_v - (f31 * 2.0F), 0.0F, 0.0F);
      this.dragonPartHead.func_70071_h_();
      this.dragonPartHead.func_70012_b(this.field_70165_t + (f19 * 3.5F), this.field_70163_u, this.field_70161_v - (f31 * 3.5F), 0.0F, 0.0F);
      for (int j = 0; j < 3; j++) {
        ExtendMultiPartEntityPart MultiPartEntityPart = null;
        if (j == 0)
          MultiPartEntityPart = this.dragonPartTail1; 
        if (j == 1)
          MultiPartEntityPart = this.dragonPartTail2; 
        if (j == 2)
          MultiPartEntityPart = this.dragonPartTail3; 
        adouble1 = getMovementOffsets(12 + j * 2, 1.0F);
        float f211 = this.field_70177_z * 3.1415927F / 180.0F + simplifyAngle(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
        float f22 = MathHelper.func_76126_a(f211);
        float f7 = MathHelper.func_76134_b(f211);
        float f23 = 0.75F;
        float f24 = (j + 1) * 1.0F;
        MultiPartEntityPart.func_70071_h_();
        MultiPartEntityPart.func_70012_b(this.field_70165_t - ((f19 * f23 + f22 * f24) * f16), this.field_70163_u + adouble1[1] - adouble[1] - ((f24 + f23) * f18) + 0.75D, this.field_70161_v + ((f31 * f23 + f7 * f24) * f16), 0.0F, 0.0F);
      } 
      if (!this.field_70170_p.field_72995_K) {
        collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartBody.func_174813_aQ().func_186662_g(1.0D)));
        collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartWing1.func_174813_aQ().func_186662_g(1.0D).func_72317_d(0.0D, -1.0D, 0.0D)));
        collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartWing2.func_174813_aQ().func_186662_g(1.0D).func_72317_d(0.0D, -1.0D, 0.0D)));
        collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail1.func_174813_aQ().func_186662_g(1.0D)));
        collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail2.func_174813_aQ().func_186662_g(1.0D)));
        collideWithEntities(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartTail3.func_174813_aQ().func_186662_g(1.0D)));
        attackEntitiesInList(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartHead.func_174813_aQ().func_186662_g(3.0D).func_72317_d(0.0D, -1.0D, 0.0D)));
        attackEntitiesInList(this.field_70170_p.func_72839_b((Entity)this, this.dragonPartNeck.func_174813_aQ().func_186662_g(3.0D).func_72317_d(0.0D, -1.0D, 0.0D)));
      } 
    } 
    if (this.field_70170_p.field_72995_K) {
      if (this.field_70716_bi > 0) {
        double d3 = this.field_70165_t + (this.field_184623_bh - this.field_70165_t) / this.field_70716_bi;
        double d0 = this.field_70163_u + (this.field_184624_bi - this.field_70163_u) / this.field_70716_bi;
        double d1 = this.field_70161_v + (this.field_184625_bj - this.field_70161_v) / this.field_70716_bi;
        double d2 = MathHelper.func_76138_g(this.field_184626_bk - this.field_70177_z);
        this.field_70177_z = (float)(this.field_70177_z + d2 / this.field_70716_bi);
        this.field_70125_A = (float)(this.field_70125_A + (this.field_70709_bj - this.field_70125_A) / this.field_70716_bi);
        this.field_70716_bi--;
        func_70107_b(d3, d0, d1);
        func_70101_b(this.field_70177_z, this.field_70125_A);
      } 
    } else {
      double d3 = this.targetX - this.field_70165_t;
      double d0 = this.targetY - this.field_70163_u;
      double d1 = this.targetZ - this.field_70161_v;
      double d2 = d3 * d3 + d0 * d0 + d1 * d1;
      if (this.target != null) {
        this.targetX = this.target.field_70165_t;
        this.targetZ = this.target.field_70161_v;
        double d4 = this.targetX - this.field_70165_t;
        double d5 = this.targetZ - this.field_70161_v;
        double d6 = Math.sqrt(d4 * d4 + d5 * d5);
        double d7 = 0.4000000059604645D + d6 / 80.0D - 1.0D;
        if (d7 > 10.0D)
          d7 = 10.0D; 
        this.targetY = (this.target.func_174813_aQ()).field_72338_b + d7;
      } else {
        this.targetX += this.field_70146_Z.nextGaussian() * 2.0D;
        this.targetZ += this.field_70146_Z.nextGaussian() * 2.0D;
      } 
      if (this.forceNewTarget || d2 < 100.0D || d2 > 22500.0D || this.field_70123_F || this.field_70124_G)
        setNewTarget(); 
      d0 /= MathHelper.func_76133_a(d3 * d3 + d1 * d1);
      float f3 = 0.6F;
      if (d0 < -f3)
        d0 = -f3; 
      if (d0 > f3)
        d0 = f3; 
      this.field_70181_x += d0 * 0.10000000149011612D;
      this.field_70177_z = MathHelper.func_76142_g(this.field_70177_z);
      double d8 = 180.0D - Math.atan2(d3, d1) * 180.0D / Math.PI;
      double d9 = MathHelper.func_76138_g(d8 - this.field_70177_z);
      if (d9 > 50.0D)
        d9 = 50.0D; 
      if (d9 < -50.0D)
        d9 = -50.0D; 
      Vec3d vec3 = (new Vec3d(this.targetX - this.field_70165_t, this.targetY - this.field_70163_u, this.targetZ - this.field_70161_v)).func_72432_b();
      Vec3d vec31 = (new Vec3d(MathHelper.func_76126_a(this.field_70177_z * 3.1415927F / 180.0F), this.field_70181_x, -MathHelper.func_76134_b(this.field_70177_z * 3.1415927F / 180.0F))).func_72432_b();
      float f4 = (float)(vec31.func_72430_b(vec3) + 0.5D) / 1.5F;
      if (f4 < 0.0F)
        f4 = 0.0F; 
      this.field_70704_bt *= 0.8F;
      float f5 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 1.0F + 1.0F;
      double d10 = Math.sqrt(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y) * 1.0D + 1.0D;
      if (d10 > 40.0D)
        d10 = 40.0D; 
      this.field_70704_bt = (float)(this.field_70704_bt + d9 * 0.699999988079071D / d10 / f5);
      this.field_70177_z += this.field_70704_bt * 0.1F;
      float f6 = (float)(2.0D / (d10 + 1.0D));
      float f7 = 0.06F;
      func_191958_b(0.0F, 0.0F, -1.0F, f7 * (f4 * f6 + 1.0F - f6));
      func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      Vec3d vec32 = (new Vec3d(this.field_70159_w, this.field_70181_x, this.field_70179_y)).func_72432_b();
      float f8 = (float)(vec32.func_72430_b(vec31) + 1.0D) / 2.5F;
      f8 = 0.8F + 0.15F * f8;
      this.field_70159_w *= f8;
      this.field_70179_y *= f8;
      this.field_70181_x *= 0.9100000262260437D;
    } 
    if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)func_184179_bs();
      passenger.field_70143_R *= 0.0F;
      passenger.field_70172_ad = 40;
      passenger.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 40, 4));
      this.field_70761_aq = this.field_70177_z = passenger.field_70759_as + 180.0F;
      this.field_70125_A = 0.0F;
      for (int i = 0; i < 256; i++) {
        double g = 0.005D;
        if (this.moralRaisedTimer > 200)
          g *= 2.0D; 
        if (func_70051_ag())
          g *= 2.0D; 
        Vec3d vec3 = passenger.func_70676_i(1.0F);
        if (passenger.field_191988_bg > 0.0F) {
          func_70107_b(this.field_70165_t + vec3.field_72450_a * g, this.field_70163_u + vec3.field_72448_b * g, this.field_70161_v + vec3.field_72449_c * g);
          Entity[] aentity = func_70021_al();
          if (aentity != null)
            for (Entity entity : aentity)
              entity.func_70012_b(entity.field_70165_t + vec3.field_72450_a * g, entity.field_70163_u + vec3.field_72448_b * g, entity.field_70161_v + vec3.field_72449_c * g, 0.0F, 0.0F);  
        } 
        if (passenger.field_191988_bg < 0.0F)
          func_70107_b(this.field_70165_t - vec3.field_72450_a * g, this.field_70163_u - vec3.field_72448_b * g, this.field_70161_v - vec3.field_72449_c * g); 
      } 
    } 
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
  
  public void func_70091_d(MoverType type, double x, double y, double z) {
    func_174826_a(func_174813_aQ().func_72317_d(x, y, z));
    func_174829_m();
    try {
      func_145775_I();
    } catch (Throwable throwable) {
      CrashReport crashreport = CrashReport.func_85055_a(throwable, "Checking entity block collision");
      CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
      func_85029_a(crashreportcategory);
      throw new ReportedException(crashreport);
    } 
  }
  
  protected void func_145775_I() {
    AxisAlignedBB axisalignedbb = func_174813_aQ();
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185345_c(axisalignedbb.field_72340_a + 0.001D, axisalignedbb.field_72338_b + 0.001D, axisalignedbb.field_72339_c + 0.001D);
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.func_185345_c(axisalignedbb.field_72336_d - 0.001D, axisalignedbb.field_72337_e - 0.001D, axisalignedbb.field_72334_f - 0.001D);
    BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.func_185346_s();
    if (this.field_70170_p.func_175707_a((BlockPos)blockpos$pooledmutableblockpos, (BlockPos)blockpos$pooledmutableblockpos1))
      for (int i = blockpos$pooledmutableblockpos.func_177958_n(); i <= blockpos$pooledmutableblockpos1.func_177958_n(); i++) {
        for (int j = blockpos$pooledmutableblockpos.func_177956_o(); j <= blockpos$pooledmutableblockpos1.func_177956_o(); j++) {
          for (int k = blockpos$pooledmutableblockpos.func_177952_p(); k <= blockpos$pooledmutableblockpos1.func_177952_p(); k++) {
            blockpos$pooledmutableblockpos2.func_181079_c(i, j, k);
            IBlockState iblockstate = this.field_70170_p.func_180495_p((BlockPos)blockpos$pooledmutableblockpos2);
            if (iblockstate.func_185904_a() == Material.field_151567_E)
              func_70024_g((this.field_70159_w > 0.0D) ? -3.0D : 3.0D, (this.field_70181_x > 0.0D) ? -3.0D : 3.0D, (this.field_70179_y > 0.0D) ? -3.0D : 3.0D); 
          } 
        } 
      }  
    blockpos$pooledmutableblockpos.func_185344_t();
    blockpos$pooledmutableblockpos1.func_185344_t();
    blockpos$pooledmutableblockpos2.func_185344_t();
  }
  
  private void updateHealingCircle() {
    if (this.healingcircle != null)
      if (this.healingcircle.field_70128_L) {
        if (!this.field_70170_p.field_72995_K)
          func_70965_a((MultiPartEntityPart)this.dragonPartHead, DamageSource.func_94539_a((Explosion)null), 1000.0F); 
        this.healingcircle = null;
      } else if (!this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0 && func_110143_aJ() <= func_110138_aP() && this.healingcircle.func_110143_aJ() < this.healingcircle.func_110138_aP()) {
        func_70965_a((MultiPartEntityPart)this.dragonPartHead, (new DamageSource("starve")).func_76348_h().func_151518_m(), 2.0F);
        this.healingcircle.func_70691_i(4.0F);
      }  
    if (this.field_70146_Z.nextInt(10) == 0) {
      float f = 64.0F;
      List<?> list = this.field_70170_p.func_72872_a(EntityDragonBoss.class, func_174813_aQ().func_72314_b(f, f, f));
      EntityDragonBoss entitydragonboss = null;
      double d0 = Double.MAX_VALUE;
      Iterator<?> iterator = list.iterator();
      while (iterator.hasNext()) {
        EntityDragonBoss entitydragonboss1 = (EntityDragonBoss)iterator.next();
        double d1 = entitydragonboss1.func_70068_e((Entity)this);
        if (d1 < d0) {
          d0 = d1;
          entitydragonboss = entitydragonboss1;
        } 
      } 
      this.healingcircle = entitydragonboss;
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
        entity.func_70024_g(d2 / d4 * 1.25D, 0.1D, d3 / d4 * 1.25D);
        entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), 1.0F);
        if (entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)entity))
          ((EntityLivingBase)entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100)); 
      } 
    } 
  }
  
  private void attackEntitiesInList(List<?> par1List) {
    for (int i = 0; i < par1List.size(); i++) {
      Entity entity = (Entity)par1List.get(i);
      if (entity.field_70173_aa + entity.func_145782_y() % 10 == 0 && !this.field_70170_p.field_72995_K && entity instanceof EntityLivingBase && !func_184191_r(entity)) {
        func_70652_k(entity);
        func_184185_a(SoundEvents.field_187685_dH, 5.0F, 0.75F);
        if (entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)entity))
          ((EntityLivingBase)entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.coralium_plague, 400)); 
        if (ACConfig.hardcoreMode && entity instanceof EntityPlayer)
          entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 1.0F); 
      } 
    } 
  }
  
  private void setNewTarget() {
    this.forceNewTarget = false;
    if (this.field_70146_Z.nextBoolean() && func_70638_az() != null) {
      this.target = (Entity)func_70638_az();
    } else {
      boolean flag = false;
      do {
        if (func_70638_az() != null) {
          this.targetX = (func_70638_az()).field_70165_t;
          this.targetY = (func_70638_az()).field_70163_u + 20.0D + (this.field_70146_Z.nextFloat() * 20.0F);
          this.targetZ = (func_70638_az()).field_70161_v;
          this.targetX += (this.field_70146_Z.nextFloat() * 40.0F - 20.0F);
          this.targetZ += (this.field_70146_Z.nextFloat() * 40.0F - 20.0F);
          double d0 = this.field_70165_t - this.targetX;
          double d1 = this.field_70163_u - this.targetY;
          double d2 = this.field_70161_v - this.targetZ;
          flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
        } else if (!isWild()) {
          this.targetX = (getOwner()).field_70165_t;
          this.targetY = (getOwner()).field_70163_u + 20.0D + (this.field_70146_Z.nextFloat() * 20.0F);
          this.targetZ = (getOwner()).field_70161_v;
          this.targetX += (this.field_70146_Z.nextFloat() * 60.0F - 30.0F);
          this.targetZ += (this.field_70146_Z.nextFloat() * 60.0F - 30.0F);
          double d0 = this.field_70165_t - this.targetX;
          double d1 = this.field_70163_u - this.targetY;
          double d2 = this.field_70161_v - this.targetZ;
          flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
        } else {
          this.targetX = 0.0D;
          this.targetY = (70.0F + this.field_70146_Z.nextFloat() * 50.0F);
          this.targetZ = 0.0D;
          this.targetX += (this.field_70146_Z.nextFloat() * 120.0F - 60.0F);
          this.targetZ += (this.field_70146_Z.nextFloat() * 120.0F - 60.0F);
          double d0 = this.field_70165_t - this.targetX;
          double d1 = this.field_70163_u - this.targetY;
          double d2 = this.field_70161_v - this.targetZ;
          flag = (d0 * d0 + d1 * d1 + d2 * d2 > 100.0D);
        } 
      } while (!flag);
      this.target = null;
    } 
  }
  
  private float simplifyAngle(double par1) {
    return (float)MathHelper.func_76138_g(par1);
  }
  
  public boolean func_70965_a(MultiPartEntityPart par1MultiPartEntityPart, DamageSource source, float damage) {
    if (par1MultiPartEntityPart != this.dragonPartHead)
      damage = damage / 2.0F + 1.0F; 
    if (damage < 0.01F || source.func_76352_a() || source == DamageSource.field_82727_n || source == DamageSource.field_76368_d || source == DamageSource.field_76369_e || source == DamageSource.field_191291_g || source == DamageSource.field_76367_g)
      return false; 
    float f = func_110143_aJ();
    if (source.func_76352_a()) {
      func_184185_a(getPierceHurtSound(), 3.0F, 1.0F);
    } else if (damage >= 7.0F || source.func_94541_c() || source == DamageSource.field_82728_o || source.func_76357_e() || source.func_151517_h() || source.func_82725_o() || source == DamageSource.field_76379_h || source == DamageSource.field_76371_c) {
      func_184185_a(getCrushHurtSound(), 3.0F, 1.0F);
    } else {
      func_184185_a(getRegularHurtSound(), 3.0F, 1.0F);
    } 
    func_70097_a(source, damage);
    return true;
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    return super.func_70097_a(par1DamageSource, par2);
  }
  
  public boolean takesFallDamage() {
    return false;
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
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
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
    return SoundEvents.field_191266_he;
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
}
