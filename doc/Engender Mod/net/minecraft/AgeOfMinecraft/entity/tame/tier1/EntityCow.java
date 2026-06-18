package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
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
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityCow extends EntityTameBase implements IJumpingMount, Light, Animal {
  protected float jumpPower;
  
  public EntityCow(World worldIn) {
    super(worldIn);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 15.0F, 4.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.5D, 80));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 3;
    func_70105_a(0.9F, 1.4F);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  public String getDescName() {
    return "cow";
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSFlying() {
    return 0.5F;
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
  
  public boolean canBeButchered() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityCow(this.field_70170_p);
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == this.spawnableBlock) ? 10.0F : (this.field_70170_p.func_175724_o(pos) - 0.5F);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187558_ak;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187562_am;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187560_al;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187566_ao, 0.15F, 1.0F / getFittness());
  }
  
  protected float func_70599_aP() {
    return 0.4F;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_COW;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151133_ar && hasOwner(player)) {
      player.func_184185_a(SoundEvents.field_187564_an, 1.0F, 1.0F);
      stack.func_190918_g(1);
      if (stack.func_190926_b()) {
        player.func_184611_a(hand, new ItemStack(Items.field_151117_aB));
      } else if (!player.field_71071_by.func_70441_a(new ItemStack(Items.field_151117_aB))) {
        player.func_71019_a(new ItemStack(Items.field_151117_aB), false);
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
    func_70642_aH();
  }
  
  public void func_184777_r_() {}
  
  public void func_70636_d() {
    super.func_70636_d();
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.825D;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      this.field_70138_W = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70759_as = entitylivingbase.field_70759_as;
      this.field_70125_A = 90.0F;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (forward != 0.0F) {
        this.field_70177_z = this.field_70761_aq = this.field_70759_as;
        this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      } 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 0.8D * this.jumpPower * getFittness();
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-0.4F * f * this.jumpPower);
          this.field_70179_y += (0.4F * f1 * this.jumpPower);
        } 
        this.jumpPower = 0.0F;
      } 
      if (forward > 0.0F) {
        List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(2.0D, 1.0D, 2.0D));
        for (int i = 0; i < list.size(); i++) {
          Entity entity = list.get(i);
          if (entity instanceof EntityLivingBase && !func_184191_r(entity)) {
            func_70652_k(entity);
            double d01 = ((func_174813_aQ()).field_72340_a + (func_174813_aQ()).field_72336_d) / 2.0D;
            double d11 = ((func_174813_aQ()).field_72339_c + (func_174813_aQ()).field_72334_f) / 2.0D;
            double d2 = entity.field_70165_t - d01;
            double d3 = entity.field_70161_v - d11;
            double d4 = d2 * d2 + d3 * d3;
            entity.func_70024_g(d2 / d4 * 3.0D, 0.1D, d3 / d4 * 3.0D);
            if (func_70068_e(entity) < 9.0D || !entity.func_70089_S())
              entity.func_70024_g(d2 / d4 * 3.0D, 0.1D, d3 / d4 * 3.0D); 
          } 
        } 
        if (this.moralRaisedTimer > 100) {
          List<Entity> list1 = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(2.0D, 2.0D, 2.0D));
          for (int j = 0; j < list1.size(); j++) {
            Entity entity = list1.get(j);
            if (entity instanceof EntityLivingBase && !func_184191_r(entity)) {
              func_70652_k(entity);
              double d01 = ((func_174813_aQ()).field_72340_a + (func_174813_aQ()).field_72336_d) / 2.0D;
              double d11 = ((func_174813_aQ()).field_72339_c + (func_174813_aQ()).field_72334_f) / 2.0D;
              double d2 = entity.field_70165_t - d01;
              double d3 = entity.field_70161_v - d11;
              double d4 = d2 * d2 + d3 * d3;
              entity.func_70024_g(d2 / d4 * 3.0D * getFittness(), 0.15D, d3 / d4 * 3.0D * getFittness());
              if (func_70068_e(entity) < 9.0D || !entity.func_70089_S())
                entity.func_70024_g(d2 / d4 * 3.0D * getFittness(), 0.15D, d3 / d4 * 3.0D * getFittness()); 
            } 
          } 
        } 
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
  
  public float func_70047_e() {
    return this.field_70131_O;
  }
}
