package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPrisonSpider extends EntitySpider {
  public EntityPrisonSpider(World worldIn) {
    super(worldIn);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
  }
  
  public String getDescName() {
    return "spider_prison";
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.1F) : super.func_70647_i();
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityPrisonSpider(this.field_70170_p);
  }
  
  public boolean func_70652_k(Entity p_70652_1_) {
    if (super.func_70652_k(p_70652_1_)) {
      p_70652_1_.field_70159_w = 0.0D;
      p_70652_1_.field_70181_x = 0.0D;
      p_70652_1_.field_70179_y = 0.0D;
      if (p_70652_1_ instanceof EntityLivingBase) {
        inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)p_70652_1_, MobEffects.field_76421_d, 5, 1);
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
          inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)p_70652_1_, MobEffects.field_76421_d, 5, 0); 
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
          inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)p_70652_1_, MobEffects.field_76421_d, 5, 0); 
        if (p_70652_1_ instanceof EntityLiving && ((EntityLiving)p_70652_1_).func_70638_az() != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70146_Z.nextInt(3) == 0)
          ((EntityLiving)p_70652_1_).func_70624_b(null); 
      } 
      return true;
    } 
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_PRISON_SPIDER;
  }
}
