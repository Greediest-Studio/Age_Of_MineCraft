package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityCaveSpider extends EntitySpider {
  public EntityCaveSpider(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.35F, 1.365F);
    } else {
      func_70105_a(0.8F, 0.475F);
    } 
    this.field_70728_aV = 2;
  }
  
  public String getDescName() {
    return "spider_cave";
  }
  
  public int getNextLevelRequirement() {
    return 30;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D);
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.1F) : super.func_70647_i();
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityCaveSpider(this.field_70170_p);
  }
  
  public int timesToConvert() {
    return 17;
  }
  
  public boolean func_70652_k(Entity p_70652_1_) {
    if (super.func_70652_k(p_70652_1_)) {
      if (p_70652_1_ instanceof EntityLivingBase) {
        byte b0 = 7;
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
          b0 = 15;
        } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
          b0 = 21;
        } 
        if (b0 > 0)
          ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76436_u, b0 * 20, 1)); 
      } 
      return true;
    } 
    return false;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.func_74838_a("entity." + str + ".cmm.name");
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.func_74838_a("entity." + s + ".name");
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean func_70058_J() {
    if (this.field_70170_p.func_72917_a(func_174813_aQ(), (Entity)this) && this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(func_174813_aQ())) {
      BlockPos blockpos = new BlockPos(this.field_70165_t, (func_174813_aQ()).field_72338_b, this.field_70161_v);
      if (this.field_70170_p.func_175710_j(blockpos.func_177984_a()))
        return false; 
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos.func_177977_b());
      Block block = iblockstate.func_177230_c();
      if (block == Blocks.field_150344_f)
        return true; 
    } 
    return false;
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.84F) : (this.field_70131_O * 0.74F);
  }
  
  public void updateRiderPosition() {
    Entity entity = func_184179_bs();
    if (entity != null) {
      double d8 = -0.13D;
      Vec3d vec3 = func_70676_i(1.0F);
      double dx = vec3.field_72450_a * d8;
      double dz = vec3.field_72449_c * d8;
      entity.func_70107_b(this.field_70165_t + dx, this.field_70163_u + func_70042_X() + entity.func_70033_W(), this.field_70161_v + dz);
    } 
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.5D;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    this.field_191988_bg *= 1.5F;
    this.field_70702_br *= 1.5F;
    super.func_191986_a(strafe, vertical, forward);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_CAVE_SPIDER;
  }
}
