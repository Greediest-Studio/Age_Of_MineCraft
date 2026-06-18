package net.minecraft.AgeOfMinecraft.entity.untame;

import net.minecraft.AgeOfMinecraft.entity.EntityBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityUntameBase extends EntityBase {
  public boolean forceNewTarget;
  
  public double targetX;
  
  public double targetY;
  
  public double targetZ;
  
  public int deathTicks;
  
  public int invincibilityTicks;
  
  protected BossInfoServer bossInfo = new BossInfoServer((ITextComponent)new TextComponentTranslation(getName(), new Object[0]), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
  
  protected EntityUntameBase(World worldIn) {
    super(worldIn);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
  }
  
  public void onUpdate() {
    super.onUpdate();
    updateBossBar();
    if (this.invincibilityTicks > 0)
      this.invincibilityTicks--; 
  }
  
  public void setTarget() {
    if (getAttackTarget() != null) {
      EntityLivingBase entityLivingBase = getAttackTarget();
      setTarget(((Entity)entityLivingBase).posX, ((Entity)entityLivingBase).posY, ((Entity)entityLivingBase).posZ);
    } 
  }
  
  public void setTarget(Entity entity) {
    if (entity != null)
      setTarget(entity.posX, entity.posY, entity.posZ); 
  }
  
  public void setTarget(double posX, double posY, double posZ) {
    this.forceNewTarget = false;
    this.targetX = posX;
    this.targetY = posY;
    this.targetZ = posZ;
  }
  
  public boolean isEntityInvulnerable(DamageSource source) {
    return (this.invincibilityTicks > 0 || super.isEntityInvulnerable(source));
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (isEntityInvulnerable(source))
      return false; 
    amount *= getDamageReduction(source, source.getTrueSource());
    if (getDamageCap() > 0.0F && amount >= getDamageCap())
      amount = getDamageCap(); 
    this.invincibilityTicks = getInvincibility();
    return super.attackEntityFrom(source, amount);
  }
  
  public void updateBossBar() {
    this.bossInfo.setName(getDisplayName());
    this.bossInfo.setPercent(getHealth() / getMaxHealth());
    this.bossInfo.setVisible(isEntityAlive());
    this.bossInfo.setOverlay(BossInfo.Overlay.NOTCHED_20);
  }
  
  public int getInvincibility() {
    return 0;
  }
  
  public float getDamageCap() {
    return 0.0F;
  }
  
  public float getDamageReduction(DamageSource source, Entity entity) {
    return 1.0F;
  }
}
