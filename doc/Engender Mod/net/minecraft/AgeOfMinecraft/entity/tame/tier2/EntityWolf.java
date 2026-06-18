package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICustomLeapAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWolf extends EntityTameBase implements IJumpingMount, Light, Animal {
  protected static final DataParameter<Byte> ANGRY = EntityDataManager.func_187226_a(EntityWolf.class, DataSerializers.field_187191_a);
  
  private float headRotationCourse;
  
  private float headRotationCourseOld;
  
  private boolean isWet;
  
  private boolean isShaking;
  
  private float timeWolfIsShaking;
  
  private float prevTimeWolfIsShaking;
  
  protected float jumpPower;
  
  public EntityWolf(World worldIn) {
    super(worldIn);
    func_70105_a(0.6F, 0.8F);
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 15.0F, 4.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAICustomLeapAttack((EntityLiving)this, 0.4F, 0.75F, 0.8F, 0.5F, 4.0D, 24.0D, 3));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 3;
  }
  
  public String getDescName() {
    return "wolf";
  }
  
  public int getNextLevelRequirement() {
    return 15;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityWolf(this.field_70170_p);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == this.spawnableBlock) ? 10.0F : (this.field_70170_p.func_175724_o(pos) - 0.5F);
  }
  
  public void func_70624_b(EntityLivingBase p_70624_1_) {
    super.func_70624_b(p_70624_1_);
    setAngry(true);
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (func_70638_az() != null) {
      setAngry(true);
    } else {
      setAngry(false);
    } 
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187869_gK, 0.15F, 1.0F / getFittness());
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(ANGRY, Byte.valueOf((byte)0));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74757_a("Angry", isAngry());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setAngry(tagCompund.func_74767_n("Angry"));
  }
  
  protected SoundEvent func_184639_G() {
    return isAngry() ? SoundEvents.field_187861_gG : SoundEvents.field_187857_gE;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187863_gH;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187859_gF;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_WOLF;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (!this.field_70170_p.field_72995_K && this.isWet && !this.isShaking && !func_70781_l() && this.field_70122_E) {
      this.isShaking = true;
      this.timeWolfIsShaking = 0.0F;
      this.prevTimeWolfIsShaking = 0.0F;
      this.field_70170_p.func_72960_a((Entity)this, (byte)8);
    } 
    if (!this.field_70170_p.field_72995_K && func_70638_az() == null && isAngry())
      setAngry(false); 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    this.headRotationCourseOld = this.headRotationCourse;
    this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
    if (func_70026_G()) {
      this.isWet = true;
      this.isShaking = false;
      this.timeWolfIsShaking = 0.0F;
      this.prevTimeWolfIsShaking = 0.0F;
    } else if ((this.isWet || this.isShaking) && this.isShaking) {
      if (this.timeWolfIsShaking == 0.0F)
        func_184185_a(SoundEvents.field_187867_gJ, func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F); 
      this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
      this.timeWolfIsShaking += 0.05F;
      if (this.prevTimeWolfIsShaking >= 2.0F) {
        this.isWet = false;
        this.isShaking = false;
        this.prevTimeWolfIsShaking = 0.0F;
        this.timeWolfIsShaking = 0.0F;
      } 
      if (this.timeWolfIsShaking > 0.4F) {
        float f = (float)(func_174813_aQ()).field_72338_b;
        int i = (int)(MathHelper.func_76126_a((this.timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);
        for (int j = 0; j < i; j++) {
          float f1 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N * 0.5F;
          float f2 = (this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * this.field_70130_N * 0.5F;
          this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_SPLASH, this.field_70165_t + f1, (f + 0.8F), this.field_70161_v + f2, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]);
        } 
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isWolfWet() {
    return this.isWet;
  }
  
  @SideOnly(Side.CLIENT)
  public float getShadingWhileWet(float p_70915_1_) {
    return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
    float f2 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;
    if (f2 < 0.0F) {
      f2 = 0.0F;
    } else if (f2 > 1.0F) {
      f2 = 1.0F;
    } 
    return MathHelper.func_76126_a(f2 * 3.1415927F) * MathHelper.func_76126_a(f2 * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
  }
  
  @SideOnly(Side.CLIENT)
  public float getInterestedAngle(float p_70917_1_) {
    return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * 3.1415927F;
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.8F;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() instanceof ItemFood) {
      ItemFood itemfood = (ItemFood)stack.func_77973_b();
      if (itemfood.func_77845_h() && func_110143_aJ() < func_110138_aP()) {
        stack.func_190918_g(1);
        func_70691_i(itemfood.func_150905_g(stack));
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
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 8) {
      this.isShaking = true;
      this.timeWolfIsShaking = 0.0F;
      this.prevTimeWolfIsShaking = 0.0F;
    } else {
      super.func_70103_a(id);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public float getTailRotation() {
    return isAngry() ? 1.5393804F : 0.62831855F;
  }
  
  public boolean isAngry() {
    return ((((Byte)this.field_70180_af.func_187225_a(ANGRY)).byteValue() & 0x2) != 0);
  }
  
  public void setAngry(boolean angry) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(ANGRY)).byteValue();
    if (angry) {
      this.field_70180_af.func_187227_b(ANGRY, Byte.valueOf((byte)(b0 | 0x2)));
    } else {
      this.field_70180_af.func_187227_b(ANGRY, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
    } 
  }
  
  public void func_110206_u(int jumpPowerIn) {
    if (func_184207_aI()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        func_184185_a(SoundEvents.field_187817_fK, 1.0F, 1.5F);
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
    func_70642_aH();
  }
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
    if (func_184207_aI() && func_82171_bF()) {
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      this.field_70747_aH = 0.05F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 0.6D * this.jumpPower;
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
        float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
        this.field_70159_w = (-2.0F * f * this.jumpPower);
        this.field_70179_y = (2.0F * f1 * this.jumpPower);
        this.jumpPower = 0.0F;
        ForgeHooks.onLivingJump((EntityLivingBase)this);
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
      this.field_70747_aH = 0.02F;
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
}
