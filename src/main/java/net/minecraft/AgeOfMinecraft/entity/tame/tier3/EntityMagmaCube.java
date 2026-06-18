package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMagmaCube extends EntitySlime {
  public EntityMagmaCube(World worldIn) {
    super(worldIn);
    this.isImmuneToFire = true;
  }
  
  public String getDescName() {
    return "lava_slime";
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (isWet()) {
      playSound(SoundEvents.ENTITY_GENERIC_BURN, 1.0F, 1.0F);
      attackEntityFrom((new DamageSource("cooler")).setDamageBypassesArmor().setDamageIsAbsolute().setDifficultyScaled(), 4.0F);
    } 
  }
  
  public int getNextLevelRequirement() {
    return 15 * getSlimeSize();
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.getEntityString((Entity)this);
      if (str == null)
        str = "generic"; 
      return (getSlimeSize() >= 4) ? I18n.translateToLocal("entity.LavaSlimeHelpful.cmm4.name") : (isSmallSlime() ? I18n.translateToLocal("entity.LavaSlimeHelpful.cmm1.name") : I18n.translateToLocal("entity.LavaSlimeHelpful.cmm2.name"));
    } 
    String s = EntityList.getEntityString((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.translateToLocal("entity." + s + ".name");
  }
  
  public boolean isNotColliding() {
    return (this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox()));
  }
  
  public void setSlimeSize(int size) {
    super.setSlimeSize(size);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((size * 2));
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }
  
  public float getBrightness() {
    return 1.0F;
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.FLAME;
  }
  
  protected EntitySlime createInstance() {
    return new EntityMagmaCube(this.world);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return isSmallSlime() ? LootTableList.EMPTY : ELoot.ENTITIES_MAGMA_CUBE;
  }
  
  public boolean isBurning() {
    return false;
  }
  
  protected int getJumpDelay() {
    return super.getJumpDelay() * 4;
  }
  
  protected void alterSquishAmount() {
    this.squishAmount *= 0.9F;
  }
  
  protected void jump() {
    playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.35F : 1.1F));
    this.motionY += (0.42F + getSlimeSize() * 0.1F);
    this.isAirBorne = true;
    ForgeHooks.onLivingJump((EntityLivingBase)this);
  }
  
  protected void handleJumpLava() {
    this.motionY = (0.22F + getSlimeSize() * 0.05F);
    this.isAirBorne = true;
  }
  
  protected boolean canDamagePlayer() {
    return true;
  }
  
  protected int getAttackStrength() {
    return super.getAttackStrength() + 2;
  }
  
  protected SoundEvent getJumpSound() {
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_SQUISH : SoundEvents.ENTITY_MAGMACUBE_JUMP;
  }
  
  protected boolean makesSoundOnLand() {
    return true;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlHurt, getSoundVolume(), getSoundPitch() + ((getSlimeSize() > 6) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_HURT : SoundEvents.ENTITY_MAGMACUBE_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      playSound(ESound.girlDeath, getSoundVolume(), getSoundPitch() + ((getSlimeSize() > 3) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_DEATH : SoundEvents.ENTITY_MAGMACUBE_DEATH;
  }
  
  protected SoundEvent getSquishSound() {
    return isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_SQUISH : SoundEvents.ENTITY_MAGMACUBE_SQUISH;
  }
  
}
