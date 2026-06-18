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
    this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackRanged(this, 1.0D, 20, 8.0F));
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  public void collideWithEntity(Entity entity) {
    if (entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)entity))
      ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100)); 
    super.collideWithEntity(entity);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    EntityInkProjectileOther entityinkprojectile = new EntityInkProjectileOther(this.world, (EntityLivingBase)this);
    double d0 = target.posX - this.posX;
    double d1 = target.posY + 0.5D - this.posY + getEyeHeight();
    double d2 = target.posZ - this.posZ;
    float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
    entityinkprojectile.shoot(d0, d1 + f1, d2, 1.6F, 1.0F);
    playLivingSound();
    this.world.spawnEntity((Entity)entityinkprojectile);
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
  
  public void setSwingingArms(boolean swingingArms) {}
}
