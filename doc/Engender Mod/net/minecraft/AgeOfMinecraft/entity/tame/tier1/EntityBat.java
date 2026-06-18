package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import java.util.Calendar;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Tiny;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBat extends EntityTameBase implements EntityFlying, Light, Flying, Tiny, Animal {
  private static final DataParameter<Byte> HANGING = EntityDataManager.func_187226_a(EntityBat.class, DataSerializers.field_187191_a);
  
  private BlockPos spawnPosition;
  
  public EntityBat(World worldIn) {
    super(worldIn);
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    setIsBatHanging(true);
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 2.0D, 16.0F, 8.0F));
    this.field_70728_aV = 1;
    func_70105_a(0.5F, 0.9F);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(HANGING, Byte.valueOf((byte)0));
  }
  
  public String getDescName() {
    return "bat";
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSFlying() {
    return 2.0F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public float getBonusVSTiny() {
    return 1.25F;
  }
  
  public float getBonusVSStructure() {
    return 0.1F;
  }
  
  public float getBonusVSElemental() {
    return 1.0F;
  }
  
  public float getBonusVSUndead() {
    return 0.5F;
  }
  
  public float getBonusVSEnder() {
    return 0.25F;
  }
  
  public float getBonusVSAnimal() {
    return 1.5F;
  }
  
  protected float func_70599_aP() {
    return 0.1F;
  }
  
  protected float func_70647_i() {
    return super.func_70647_i() * 0.95F;
  }
  
  protected SoundEvent func_184639_G() {
    return (getIsBatHanging() && this.field_70146_Z.nextInt(4) != 0) ? null : SoundEvents.field_187740_w;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187743_y;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187742_x;
  }
  
  public boolean func_70104_M() {
    return false;
  }
  
  protected void func_82167_n(Entity entityIn) {}
  
  protected void func_85033_bc() {}
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(6.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  public boolean getIsBatHanging() {
    return ((((Byte)this.field_70180_af.func_187225_a(HANGING)).byteValue() & 0x1) != 0);
  }
  
  public void setIsBatHanging(boolean isHanging) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(HANGING)).byteValue();
    if (isHanging) {
      this.field_70180_af.func_187227_b(HANGING, Byte.valueOf((byte)(b0 | 0x1)));
    } else {
      this.field_70180_af.func_187227_b(HANGING, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (!func_70026_G() && func_70089_S() && getIllusionFormTime() > 0) {
      if (this.field_70146_Z.nextInt(24) == 0 && !func_174814_R())
        this.field_70170_p.func_184134_a(this.field_70165_t + 0.5D, this.field_70163_u + 0.5D, this.field_70161_v + 0.5D, SoundEvents.field_187597_B, func_184176_by(), func_70599_aP(), this.field_70146_Z.nextFloat() * 0.7F + 0.3F, false); 
      for (int i = 0; i < 2; i++) {
        if (func_70093_af() || func_70631_g_()) {
          this.field_70170_p.func_175688_a(isAntiMob() ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 0.6000000238418579D, this.field_70163_u + this.field_70146_Z.nextDouble() * 1.7999999523162842D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 0.6000000238418579D, 0.0D, 0.0D, 0.0D, new int[0]);
        } else {
          this.field_70170_p.func_175688_a(isAntiMob() ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 0.6000000238418579D, this.field_70163_u + this.field_70146_Z.nextDouble() * 1.7999999523162842D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 0.6000000238418579D, 0.0D, 0.0D, 0.0D, new int[0]);
        } 
      } 
    } 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
    this.field_70163_u = MathHelper.func_76128_c(this.field_70163_u) + 1.0D - this.field_70131_O;
    if (!this.field_70122_E && this.field_70181_x < 0.0D && func_70089_S())
      this.field_70181_x *= 0.6D; 
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    BlockPos blockpos = new BlockPos((Entity)this);
    BlockPos blockpos1 = blockpos.func_177984_a();
    if (getIsBatHanging()) {
      if (!this.field_70170_p.func_180495_p(blockpos1).func_185915_l()) {
        setIsBatHanging(false);
        this.field_70170_p.func_180498_a((EntityPlayer)null, 1025, blockpos, 0);
      } else {
        if (this.field_70146_Z.nextInt(200) == 0)
          this.field_70759_as = this.field_70146_Z.nextInt(360); 
        if (this.field_70170_p.func_184136_b((Entity)this, 4.0D) != null) {
          setIsBatHanging(false);
          this.field_70170_p.func_180498_a((EntityPlayer)null, 1025, blockpos, 0);
        } 
      } 
    } else {
      if (this.spawnPosition != null && (!this.field_70170_p.func_175623_d(this.spawnPosition) || this.spawnPosition.func_177956_o() < 1))
        this.spawnPosition = null; 
      if (this.spawnPosition == null || this.field_70146_Z.nextInt(30) == 0 || this.spawnPosition.func_177954_c((int)this.field_70165_t, (int)this.field_70163_u, (int)this.field_70161_v) < 4.0D)
        this.spawnPosition = new BlockPos((int)this.field_70165_t + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7), (int)this.field_70163_u + this.field_70146_Z.nextInt(6) - 2, (int)this.field_70161_v + this.field_70146_Z.nextInt(7) - this.field_70146_Z.nextInt(7)); 
      if (this.field_70146_Z.nextInt(100) == 0 && this.field_70170_p.func_180495_p(blockpos1).func_185915_l())
        setIsBatHanging(true); 
      if (func_70660_b(MobEffects.field_188425_z) == null)
        if (this.field_70170_p.func_72890_a((Entity)this, 200.0D) != null && func_70638_az() == null && this.field_70170_p.func_72890_a((Entity)this, 200.0D) == getOwner() && func_70068_e((Entity)getOwner()) > 200.0D) {
          double d01 = (getOwner()).field_70165_t - this.field_70165_t;
          double d11 = (getOwner()).field_70163_u - this.field_70163_u;
          double d21 = (getOwner()).field_70161_v - this.field_70161_v;
          float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
          this.field_70159_w = d01 / f2 * 0.5D * 0.5D + this.field_70159_w * 0.5D;
          this.field_70181_x = d11 / f2 * 0.5D * 0.5D + this.field_70179_y * 0.5D;
          this.field_70179_y = d21 / f2 * 0.5D * 0.5D + this.field_70179_y * 0.5D;
          func_70625_a((Entity)getOwner(), 180.0F, 30.0F);
        } else if (func_70638_az() != null) {
          double d01 = (func_70638_az()).field_70165_t - this.field_70165_t;
          double d11 = (func_70638_az()).field_70161_v - this.field_70161_v;
          float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11);
          this.field_70159_w = d01 / f2 * 0.5D * 0.5D + this.field_70159_w;
          this.field_70179_y = d11 / f2 * 0.5D * 0.5D + this.field_70179_y;
          func_70625_a((Entity)func_70638_az(), 180.0F, 30.0F);
          if (this.field_70163_u < (func_70638_az()).field_70163_u)
            this.field_70181_x += 0.25D - this.field_70181_x; 
        } else {
          double d0 = this.spawnPosition.func_177958_n() + 0.5D - this.field_70165_t;
          double d1 = this.spawnPosition.func_177956_o() + 0.1D - this.field_70163_u;
          double d2 = this.spawnPosition.func_177952_p() + 0.5D - this.field_70161_v;
          this.field_70159_w += (Math.signum(d0) * 0.5D - this.field_70159_w) * 0.10000000149011612D;
          this.field_70181_x += (Math.signum(d1) * 0.699999988079071D - this.field_70181_x) * 0.10000000149011612D;
          this.field_70179_y += (Math.signum(d2) * 0.5D - this.field_70179_y) * 0.10000000149011612D;
          float f = (float)(Math.atan2(this.field_70179_y, this.field_70159_w) * 180.0D / Math.PI) - 90.0F;
          float f1 = MathHelper.func_76142_g(f - this.field_70177_z);
          this.field_191988_bg = 0.5F;
          this.field_70177_z += f1;
        }  
    } 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && hasOwner(player)) {
      func_184581_c(null);
      func_70057_ab();
      player.func_184609_a(EnumHand.MAIN_HAND);
      func_70690_d(new PotionEffect(MobEffects.field_188425_z, 100));
      player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, (player.func_70660_b(MobEffects.field_76439_r) != null) ? (player.func_70660_b(MobEffects.field_76439_r).func_76459_b() + 200) : 200));
      return true;
    } 
    return false;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected void func_180433_a(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_) {}
  
  public boolean func_145773_az() {
    return true;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (func_180431_b(source))
      return false; 
    if (!this.field_70170_p.field_72995_K && getIsBatHanging())
      setIsBatHanging(false); 
    return super.func_70097_a(source, amount);
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    this.field_70180_af.func_187227_b(HANGING, Byte.valueOf(tagCompund.func_74771_c("BatFlags")));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74774_a("BatFlags", ((Byte)this.field_70180_af.func_187225_a(HANGING)).byteValue());
  }
  
  public float func_70047_e() {
    return this.field_70131_O / 2.0F;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_BAT;
  }
  
  public boolean func_70601_bi() {
    BlockPos blockpos = new BlockPos(this.field_70165_t, (func_174813_aQ()).field_72338_b, this.field_70161_v);
    if (blockpos.func_177956_o() >= this.field_70170_p.func_181545_F())
      return false; 
    int i = this.field_70170_p.func_175671_l(blockpos);
    int j = 4;
    if (isDateAroundHalloween(this.field_70170_p.func_83015_S())) {
      j = 7;
    } else if (this.field_70146_Z.nextBoolean()) {
      return false;
    } 
    return (i >= this.field_70146_Z.nextInt(j)) ? false : super.func_70601_bi();
  }
  
  private boolean isDateAroundHalloween(Calendar p_175569_1_) {
    return ((p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20) || (p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3));
  }
}
