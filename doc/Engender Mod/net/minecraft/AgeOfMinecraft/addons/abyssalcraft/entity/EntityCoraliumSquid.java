package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySquid;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityCoraliumSquid extends EntitySquid implements IRangedAttackMob {
  public EntityCoraliumSquid(World worldIn) {
    super(worldIn);
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIAttackRanged(this, 1.0D, 20, 8.0F));
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  public void func_82167_n(Entity entity) {
    if (entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)entity))
      ((EntityLivingBase)entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100)); 
    super.func_82167_n(entity);
  }
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    EntityInkProjectileOther entityinkprojectile = new EntityInkProjectileOther(this.field_70170_p, (EntityLivingBase)this);
    double d0 = target.field_70165_t - this.field_70165_t;
    double d1 = target.field_70163_u + 0.5D - this.field_70163_u + func_70047_e();
    double d2 = target.field_70161_v - this.field_70161_v;
    float f1 = MathHelper.func_76133_a(d0 * d0 + d2 * d2) * 0.2F;
    entityinkprojectile.func_70186_c(d0, d1 + f1, d2, 1.6F, 1.0F);
    func_70642_aH();
    this.field_70170_p.func_72838_d((Entity)entityinkprojectile);
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
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
  
  public void func_184724_a(boolean swingingArms) {}
}
