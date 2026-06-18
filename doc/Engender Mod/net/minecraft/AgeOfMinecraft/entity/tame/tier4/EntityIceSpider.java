package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityIceSpider extends EntitySpider {
  public EntityIceSpider(World worldIn) {
    super(worldIn);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(18.0D);
  }
  
  public String getDescName() {
    return "spider_ice";
  }
  
  public int getNextLevelRequirement() {
    return 40;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.1F) : super.func_70647_i();
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityIceSpider(this.field_70170_p);
  }
  
  public boolean func_70652_k(Entity p_70652_1_) {
    if (super.func_70652_k(p_70652_1_)) {
      if (p_70652_1_ instanceof EntityLivingBase) {
        byte b0 = 8;
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL) {
          b0 = 16;
        } else if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
          b0 = 24;
        } 
        if (b0 > 0) {
          ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76421_d, b0 * 20, 1));
          if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
            ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76437_t, b0 * 20)); 
          if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
            ((EntityLivingBase)p_70652_1_).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 40)); 
          if (p_70652_1_ instanceof EntityLiving && ((EntityLiving)p_70652_1_).func_70638_az() != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70146_Z.nextInt(3) == 0)
            ((EntityLiving)p_70652_1_).func_70624_b(null); 
        } 
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
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_ICE_SPIDER;
  }
}
