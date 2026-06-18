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
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(18.0D);
  }
  
  public String getDescName() {
    return "spider_ice";
  }
  
  public int getNextLevelRequirement() {
    return 40;
  }
  
  protected float getSoundPitch() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.getSoundPitch() + 0.1F) : super.getSoundPitch();
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityIceSpider(this.world);
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_) {
    if (super.attackEntityAsMob(p_70652_1_)) {
      if (p_70652_1_ instanceof EntityLivingBase) {
        byte b0 = 8;
        if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
          b0 = 16;
        } else if (this.world.getDifficulty() == EnumDifficulty.HARD) {
          b0 = 24;
        } 
        if (b0 > 0) {
          ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, b0 * 20, 1));
          if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD)
            ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, b0 * 20)); 
          if (this.world.getDifficulty() == EnumDifficulty.HARD)
            ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40)); 
          if (p_70652_1_ instanceof EntityLiving && ((EntityLiving)p_70652_1_).getAttackTarget() != null && this.world.getDifficulty() == EnumDifficulty.HARD && this.rand.nextInt(3) == 0)
            ((EntityLiving)p_70652_1_).setAttackTarget(null); 
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.translateToLocal("entity." + str + ".cmm.name");
    } 
    String s = EntityList.getEntityString((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_ICE_SPIDER;
  }
}
