package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Tiny;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityEndermite extends EntityTameBase implements Light, Tiny, Ender {
  public EntityEndermite(World worldIn) {
    super(worldIn);
    func_70105_a(0.4F, 0.3F);
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 24.0F, 6.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 1;
  }
  
  public String getDescName() {
    return "endermite";
  }
  
  public int getNextLevelRequirement() {
    return 15;
  }
  
  public float getBonusVSArmored() {
    return 0.25F;
  }
  
  public float getBonusVSMassive() {
    return 0.25F;
  }
  
  public int timesToConvert() {
    return 3;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public float func_70047_e() {
    return this.field_70131_O / 3.0F;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(8.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0D);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(400);
    func_184185_a(ESound.bugSpecial, 10.0F, 1.0F);
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 2; i++) {
        EntityEndermite mob = new EntityEndermite(this.field_70170_p);
        mob.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
        mob.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        this.field_70170_p.func_72838_d((Entity)mob);
        mob.setOwnerId(func_184753_b());
      }  
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187535_aY;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187590_ba;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187536_aZ;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187592_bb, 0.15F, 1.0F / getFittness());
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_ENDERMITE;
  }
  
  public void func_70071_h_() {
    this.field_70760_ar = this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    super.func_70071_h_();
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_70638_az() != null && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (getSpecialAttackTimer() >= 380 && isHero()) {
      func_70661_as().func_75499_g();
      this.field_70159_w = 0.0D;
      this.field_70179_y = 0.0D;
      float f2 = this.field_70761_aq * 0.017453292F;
      float f19 = MathHelper.func_76126_a(f2);
      float f3 = MathHelper.func_76134_b(f2);
      for (int i = 0; i < 32; i++) {
        this.field_70170_p.func_175682_a(EnumParticleTypes.END_ROD, true, this.field_70165_t, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O * 2.0D, this.field_70161_v, (f3 * 0.15F), 0.01D, (f19 * 0.15F), new int[0]);
        this.field_70170_p.func_175682_a(EnumParticleTypes.END_ROD, true, this.field_70165_t, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O * 2.0D, this.field_70161_v, (f3 * -0.15F), 0.01D, (f19 * -0.15F), new int[0]);
      } 
    } 
    if (this.field_70170_p.field_72995_K)
      this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, new int[0]); 
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ARTHROPOD;
  }
}
