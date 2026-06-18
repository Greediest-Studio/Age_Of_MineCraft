package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLlama extends EntityTameBase implements IRangedAttackMob, Light, Animal {
  private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.func_187226_a(EntityLlama.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> DATA_VARIANT_ID = EntityDataManager.func_187226_a(EntityLlama.class, DataSerializers.field_187192_b);
  
  public EntityLlama(World worldIn) {
    super(worldIn);
    func_70105_a(0.9F, 1.87F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 1.2D, 20, 50, 12.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 3;
  }
  
  public String getDescName() {
    return "llama";
  }
  
  public int getNextLevelRequirement() {
    return 10;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == this.spawnableBlock) ? 10.0F : (this.field_70170_p.func_175724_o(pos) - 0.5F);
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityLlama(this.field_70170_p);
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74768_a("Variant", getVariant());
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    setStrength(compound.func_74762_e("Strength"));
    super.func_70037_a(compound);
    setVariant(compound.func_74762_e("Variant"));
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((15.0F + this.field_70146_Z.nextInt(8) + this.field_70146_Z.nextInt(9)));
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(DATA_COLOR_ID, Integer.valueOf(-1));
    this.field_70180_af.func_187214_a(DATA_VARIANT_ID, Integer.valueOf(0));
  }
  
  public int getVariant() {
    return MathHelper.func_76125_a(((Integer)this.field_70180_af.func_187225_a(DATA_VARIANT_ID)).intValue(), 0, 3);
  }
  
  public void setVariant(int variantIn) {
    this.field_70180_af.func_187227_b(DATA_VARIANT_ID, Integer.valueOf(variantIn));
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      float f = MathHelper.func_76134_b(this.field_70761_aq * 0.017453292F);
      float f1 = MathHelper.func_76126_a(this.field_70761_aq * 0.017453292F);
      float f2 = 0.3F;
      passenger.func_70107_b(this.field_70165_t + (0.3F * f1), this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v - (0.3F * f));
    } 
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.67D;
  }
  
  protected boolean handleEating(EntityPlayer player, ItemStack stack) {
    int i = 0;
    int j = 0;
    float f = 0.0F;
    boolean flag = false;
    Item item = stack.func_77973_b();
    if (item == Items.field_151015_O) {
      i = 10;
      j = 3;
      f = 2.0F;
    } 
    if (func_110143_aJ() < func_110138_aP() && f > 0.0F) {
      func_70691_i(f);
      flag = true;
    } 
    if (flag && !func_174814_R())
      this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_191253_dD, func_184176_by(), 1.0F, 1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F); 
    return flag;
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    int i;
    livingdata = super.func_180482_a(difficulty, livingdata);
    if (livingdata instanceof GroupData) {
      i = ((GroupData)livingdata).variant;
    } else {
      i = this.field_70146_Z.nextInt(4);
      livingdata = new GroupData(i);
    } 
    setVariant(i);
    return livingdata;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean hasColor() {
    return (getColor() != null);
  }
  
  protected SoundEvent getAngrySound() {
    return SoundEvents.field_191250_dA;
  }
  
  protected SoundEvent func_184639_G() {
    return (func_70638_az() != null || this.field_70146_Z.nextInt(5) == 0) ? SoundEvents.field_191250_dA : SoundEvents.field_191260_dz;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_191254_dE;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_191252_dC;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_191256_dG, 0.15F, 1.0F / getFittness());
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_LLAMA;
  }
  
  public void makeMad() {
    SoundEvent soundevent = getAngrySound();
    if (soundevent != null)
      func_184185_a(soundevent, func_70599_aP(), func_70647_i()); 
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
  
  public void func_70636_d() {
    super.func_70636_d();
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70759_as = entitylivingbase.field_70759_as;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (forward != 0.0F) {
        this.field_70177_z = this.field_70761_aq = this.field_70759_as;
        this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      } 
      if (func_70090_H() || func_180799_ab())
        this.field_70181_x += 0.05D; 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * ((func_70090_H() || func_180799_ab()) ? 20.0F : 1.0F));
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (entitylivingbase.field_191988_bg > 0.0F && this.field_70173_aa % 7 == 0)
        func_180429_a(new BlockPos((Entity)this), this.field_70170_p.func_180495_p(new BlockPos((Entity)this)).func_177230_c()); 
      this.field_184618_aE = this.field_70721_aZ;
      double d1 = this.field_70165_t - this.field_70169_q;
      double d0 = this.field_70161_v - this.field_70166_s;
      float f2 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 2.0F;
      if (f2 > 1.0F)
        f2 = 1.0F; 
      this.field_70721_aZ += (f2 - this.field_70721_aZ) * 0.6F;
      this.field_184619_aG += this.field_70721_aZ * 0.25F;
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
  
  private void setColor(@Nullable EnumDyeColor color) {
    this.field_70180_af.func_187227_b(DATA_COLOR_ID, Integer.valueOf((color == null) ? -1 : color.func_176765_a()));
  }
  
  @Nullable
  public EnumDyeColor getColor() {
    int i = ((Integer)this.field_70180_af.func_187225_a(DATA_COLOR_ID)).intValue();
    return (i == -1) ? null : EnumDyeColor.func_176764_b(i);
  }
  
  public int getMaxTemper() {
    return 30;
  }
  
  private void spit(EntityLivingBase target) {
    EntityLlamaSpitOther entityllamaspit = new EntityLlamaSpitOther(this.field_70170_p, this);
    double d0 = target.field_70165_t - this.field_70165_t;
    double d1 = (target.func_174813_aQ()).field_72338_b - 1.25D - entityllamaspit.field_70163_u;
    double d2 = target.field_70161_v - this.field_70161_v;
    float f = MathHelper.func_76133_a(d0 * d0 + d2 * d2) * 0.25F;
    entityllamaspit.func_70186_c(d0, d1 + f, d2, 2.0F, 4.0F);
    this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_191255_dF, func_184176_by(), func_70599_aP(), func_70647_i());
    this.field_70170_p.func_72838_d((Entity)entityllamaspit);
  }
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    if (func_70032_d((Entity)target) <= (target.field_70130_N + this.field_70130_N)) {
      func_70652_k((Entity)target);
      func_70661_as().func_75497_a((Entity)target, 1.2D);
    } else {
      spit(target);
    } 
  }
  
  static class GroupData implements IEntityLivingData {
    public int variant;
    
    private GroupData(int variantIn) {
      this.variant = variantIn;
    }
  }
}
