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
  
  protected BossInfoServer bossInfo = new BossInfoServer((ITextComponent)new TextComponentTranslation(func_70005_c_(), new Object[0]), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS);
  
  protected EntityUntameBase(World worldIn) {
    super(worldIn);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111264_e);
    func_110140_aT().func_111150_b(SharedMonsterAttributes.field_188790_f);
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    updateBossBar();
    if (this.invincibilityTicks > 0)
      this.invincibilityTicks--; 
  }
  
  public void setTarget() {
    if (func_70638_az() != null) {
      EntityLivingBase entityLivingBase = func_70638_az();
      setTarget(((Entity)entityLivingBase).field_70165_t, ((Entity)entityLivingBase).field_70163_u, ((Entity)entityLivingBase).field_70161_v);
    } 
  }
  
  public void setTarget(Entity entity) {
    if (entity != null)
      setTarget(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v); 
  }
  
  public void setTarget(double posX, double posY, double posZ) {
    this.forceNewTarget = false;
    this.targetX = posX;
    this.targetY = posY;
    this.targetZ = posZ;
  }
  
  public boolean func_180431_b(DamageSource source) {
    return (this.invincibilityTicks > 0 || super.func_180431_b(source));
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (func_180431_b(source))
      return false; 
    amount *= getDamageReduction(source, source.func_76346_g());
    if (getDamageCap() > 0.0F && amount >= getDamageCap())
      amount = getDamageCap(); 
    this.invincibilityTicks = getInvincibility();
    return super.func_70097_a(source, amount);
  }
  
  public void updateBossBar() {
    this.bossInfo.func_186739_a(func_145748_c_());
    this.bossInfo.func_186735_a(func_110143_aJ() / func_110138_aP());
    this.bossInfo.func_186758_d(func_70089_S());
    this.bossInfo.func_186746_a(BossInfo.Overlay.NOTCHED_20);
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
