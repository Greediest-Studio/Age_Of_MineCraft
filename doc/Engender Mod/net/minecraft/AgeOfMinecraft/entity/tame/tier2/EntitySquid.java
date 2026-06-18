package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySquid extends EntityTameBase implements Light, Animal {
  public float squidPitch;
  
  public float prevSquidPitch;
  
  public float squidYaw;
  
  public float prevSquidYaw;
  
  public float squidRotation;
  
  public float prevSquidRotation;
  
  public float tentacleAngle;
  
  public float lastTentacleAngle;
  
  private float randomMotionSpeed;
  
  private float rotationVelocity;
  
  private float rotateSpeed;
  
  private float randomMotionVecX;
  
  private float randomMotionVecY;
  
  private float randomMotionVecZ;
  
  public EntitySquid(World worldIn) {
    super(worldIn);
    func_70105_a(0.9F, 0.9F);
    this.field_70146_Z.setSeed((1 + func_145782_y()));
    this.rotationVelocity = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F;
    this.field_70714_bg.func_75776_a(0, new AIMoveRandom(this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70728_aV = 2;
  }
  
  public String getDescName() {
    return "Derp".equals(func_70005_c_()) ? "derp_squid" : "squid";
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySquid(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.5F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected SoundEvent func_184639_G() {
    return !func_70090_H() ? SoundEvents.field_187833_fS : SoundEvents.field_187829_fQ;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187833_fS;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187831_fR;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos).func_185904_a() == Material.field_151586_h) ? (10.0F + this.field_70170_p.func_175724_o(pos) - 0.5F) : super.func_180484_a(pos);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_SQUID;
  }
  
  public boolean func_70090_H() {
    return (func_70089_S() && (this.field_70171_ac || func_189652_ae() || "Derp".equals(func_70005_c_()) || (this.field_70170_p.func_83015_S().get(2) + 1 == 4 && this.field_70170_p.func_83015_S().get(5) == 1)));
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_70090_H())
      this.field_70143_R = 0.0F; 
    if (this.field_70725_aQ > 0)
      this.field_70181_x = -0.6D; 
    this.prevSquidPitch = this.squidPitch;
    this.prevSquidYaw = this.squidYaw;
    this.prevSquidRotation = this.squidRotation;
    this.lastTentacleAngle = this.tentacleAngle;
    this.squidRotation += this.rotationVelocity;
    if (this.squidRotation > 6.283185307179586D)
      if (this.field_70170_p.field_72995_K) {
        this.squidRotation = 6.2831855F;
      } else {
        this.squidRotation = (float)(this.squidRotation - 6.283185307179586D);
        if (this.field_70146_Z.nextInt(10) == 0)
          this.rotationVelocity = 1.0F / (this.field_70146_Z.nextFloat() + 1.0F) * 0.2F; 
        this.field_70170_p.func_72960_a((Entity)this, (byte)19);
      }  
    if (func_175446_cd()) {
      this.field_70159_w = 0.0D;
      this.field_70181_x = 0.0D;
      this.field_70179_y = 0.0D;
    } else if (func_70090_H()) {
      if (this.squidRotation < 3.1415927F) {
        float f = this.squidRotation / 3.1415927F;
        this.tentacleAngle = MathHelper.func_76126_a(f * f * 3.1415927F) * 3.1415927F * ((func_70638_az() != null) ? 0.5F : 0.25F) * (func_70631_g_() ? 2 : true);
        if (f > 0.9D) {
          this.randomMotionSpeed = 1.0F;
          this.rotateSpeed = 1.0F;
        } else {
          this.rotateSpeed *= 0.8F;
        } 
      } else {
        this.tentacleAngle = 0.0F;
        this.randomMotionSpeed *= 0.9F;
        this.rotateSpeed *= 0.99F;
      } 
      if (!this.field_70170_p.field_72995_K) {
        this.field_70159_w = (this.randomMotionVecX * this.randomMotionSpeed);
        this.field_70181_x = (this.randomMotionVecY * this.randomMotionSpeed);
        this.field_70179_y = (this.randomMotionVecZ * this.randomMotionSpeed);
      } 
      float f1 = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y);
      this.field_70761_aq += (-((float)MathHelper.func_181159_b(this.field_70159_w, this.field_70179_y)) * 57.295776F - this.field_70761_aq) * 0.1F;
      this.field_70177_z = this.field_70761_aq;
      this.squidYaw = (float)(this.squidYaw + Math.PI * this.rotateSpeed * 1.5D);
      this.squidPitch += (-((float)MathHelper.func_181159_b(f1, this.field_70181_x)) * 57.295776F - this.squidPitch) * 0.1F;
    } else {
      this.tentacleAngle = MathHelper.func_76135_e(MathHelper.func_76126_a(this.squidRotation)) * 3.1415927F * 0.25F;
      if (!this.field_70170_p.field_72995_K) {
        this.field_70159_w = 0.0D;
        this.field_70179_y = 0.0D;
        if (func_70644_a(MobEffects.field_188424_y)) {
          this.field_70181_x += 0.05D * (func_70660_b(MobEffects.field_188424_y).func_76458_c() + 1) - this.field_70181_x;
        } else if (!func_189652_ae()) {
          this.field_70181_x -= 0.08D;
        } 
        this.field_70181_x *= 0.9800000190734863D;
      } 
      this.squidPitch = (float)(this.squidPitch + (-90.0F - this.squidPitch) * 0.02D);
    } 
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.7D;
  }
  
  public boolean shouldDismountInWater(Entity rider) {
    return false;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_70089_S()) {
      func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
      if (!func_70090_H()) {
        if (this.field_70122_E) {
          this.field_70159_w = 0.0D;
          this.field_70179_y = 0.0D;
        } 
        BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.func_185345_c(this.field_70165_t, (func_174813_aQ()).field_72338_b - 1.0D, this.field_70161_v);
        if (func_70644_a(MobEffects.field_188424_y)) {
          this.field_70181_x += (0.05D * (func_70660_b(MobEffects.field_188424_y).func_76458_c() + 1) - this.field_70181_x) * 0.2D;
        } else {
          blockpos$pooledmutableblockpos.func_189532_c(this.field_70165_t, 0.0D, this.field_70161_v);
          if (!this.field_70170_p.field_72995_K || (this.field_70170_p.func_175667_e((BlockPos)blockpos$pooledmutableblockpos) && this.field_70170_p.func_175726_f((BlockPos)blockpos$pooledmutableblockpos).func_177410_o())) {
            if (!func_189652_ae())
              this.field_70181_x -= 0.08D; 
          } else if (this.field_70163_u > 0.0D) {
            this.field_70181_x = -0.1D;
          } else {
            this.field_70181_x = 0.0D;
          } 
        } 
        this.field_70181_x *= 0.9800000190734863D;
      } 
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 19) {
      this.squidRotation = 0.0F;
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn) {
    this.randomMotionVecX = randomMotionVecXIn;
    this.randomMotionVecY = randomMotionVecYIn;
    this.randomMotionVecZ = randomMotionVecZIn;
  }
  
  public boolean hasMovementVector() {
    return (this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F);
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public boolean handleLavaMovement() {
    return this.field_70170_p.func_72917_a(func_174813_aQ(), (Entity)this);
  }
  
  public int func_70627_aG() {
    return 120;
  }
  
  public void func_70030_z() {
    int i = func_70086_ai();
    super.func_70030_z();
    if (func_70089_S() && !func_70090_H()) {
      i--;
      func_70050_g(i);
      if (func_70086_ai() == -10) {
        func_70050_g(0);
        func_70097_a(DamageSource.field_82727_n, 3.0F);
        if (!this.field_70170_p.field_72995_K && this.field_70146_Z.nextInt(10) == 0 && this.field_70122_E) {
          this.field_70159_w += ((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.4F);
          this.field_70179_y += ((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * 0.4F);
          this.field_70177_z = this.field_70146_Z.nextFloat() * 360.0F;
          func_70664_aZ();
        } 
      } 
    } else {
      func_70050_g(300);
    } 
  }
  
  public boolean func_96092_aw() {
    return false;
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
  
  static class AIMoveRandom extends EntityAIBase {
    private final EntitySquid squid;
    
    public AIMoveRandom(EntitySquid p_i45859_1_) {
      this.squid = p_i45859_1_;
    }
    
    public boolean func_75250_a() {
      return true;
    }
    
    public void func_75246_d() {
      if (this.squid.func_70089_S() && this.squid.func_70638_az() != null && this.squid.func_70638_az().func_70089_S() && !this.squid.func_70631_g_() && !this.squid.func_184191_r((Entity)this.squid.func_70638_az()) && this.squid.func_70068_e((Entity)this.squid.func_70638_az()) < (this.squid.field_70130_N * this.squid.field_70130_N + (this.squid.func_70638_az()).field_70130_N * (this.squid.func_70638_az()).field_70130_N) + 4.0D && (this.squid.field_70173_aa + this.squid.func_145782_y()) % 20 == 0)
        this.squid.func_70652_k((Entity)this.squid.func_70638_az()); 
      int i = this.squid.func_70654_ax();
      if (i > 100) {
        this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
      } else if (this.squid.func_184207_aI() && this.squid.func_184179_bs() instanceof EntityPlayer && this.squid.func_70090_H() && this.squid.hasMovementVector()) {
        EntityPlayer passenger = (EntityPlayer)this.squid.func_184179_bs();
        passenger.func_70690_d(new PotionEffect(MobEffects.field_76427_o, 200, 0, true, true));
        passenger.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 200, 0, true, true));
        double d1 = 0.6D;
        Vec3d vec3 = passenger.func_70676_i(1.0F);
        this.squid.setMovementVector((float)(vec3.field_72450_a * d1), (float)(vec3.field_72448_b * d1), (float)(vec3.field_72449_c * d1));
      } else if (this.squid.func_70638_az() != null && this.squid.func_70090_H() && this.squid.hasMovementVector()) {
        double d01 = (this.squid.func_70638_az()).field_70165_t - this.squid.field_70165_t;
        double d11 = (this.squid.func_70638_az()).field_70163_u - this.squid.field_70163_u;
        double d21 = (this.squid.func_70638_az()).field_70161_v - this.squid.field_70161_v;
        float fl2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
        float f1 = (float)(d01 / fl2 * 0.2D + this.squid.field_70159_w);
        float f2 = (float)(d11 / fl2 * 0.2D + this.squid.field_70181_x);
        float f3 = (float)(d21 / fl2 * 0.2D + this.squid.field_70179_y);
        this.squid.setMovementVector(f1, f2, f3);
      } else if (this.squid.getOwner() != null && this.squid.func_70068_e((Entity)this.squid.getOwner()) > 128.0D && this.squid.func_70090_H() && this.squid.hasMovementVector()) {
        double d01 = (this.squid.getOwner()).field_70165_t - this.squid.field_70165_t;
        double d11 = (this.squid.getOwner()).field_70163_u - this.squid.field_70163_u;
        double d21 = (this.squid.getOwner()).field_70161_v - this.squid.field_70161_v;
        float fl2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
        float f1 = (float)(d01 / fl2 * 0.2D + this.squid.field_70159_w);
        float f2 = (float)(d11 / fl2 * 0.2D + this.squid.field_70181_x);
        float f3 = (float)(d21 / fl2 * 0.2D + this.squid.field_70179_y);
        this.squid.setMovementVector(f1, f2, f3);
      } else if (this.squid.func_70681_au().nextInt(50) == 0 || !this.squid.func_70090_H() || !this.squid.hasMovementVector()) {
        float f = this.squid.func_70681_au().nextFloat() * 6.2831855F;
        float f1 = MathHelper.func_76134_b(f) * 0.2F;
        float f2 = -0.1F + this.squid.func_70681_au().nextFloat() * 0.2F;
        float f3 = MathHelper.func_76126_a(f) * 0.2F;
        this.squid.setMovementVector(f1, f2, f3);
      } 
    }
  }
}
