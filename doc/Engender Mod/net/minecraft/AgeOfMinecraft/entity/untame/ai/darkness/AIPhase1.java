package net.minecraft.AgeOfMinecraft.entity.untame.ai.darkness;

import net.minecraft.AgeOfMinecraft.entity.untame.tier5.EntityDarkness;
import net.minecraft.AgeOfMinecraft.util.Maths;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class AIPhase1 {
  private final EntityDarkness darkness;
  
  public AIPhase1(EntityDarkness darkness) {
    this.darkness = darkness;
  }
  
  public void stay() {
    if (this.darkness.shouldMove) {
      this.darkness.shouldMove = false;
      setTarget((Entity)this.darkness);
    } 
  }
  
  public void wander(double distance) {
    if (this.darkness.forceNewTarget || distance < 64.0D) {
      if (!this.darkness.shouldMove)
        this.darkness.shouldMove = true; 
      if (this.darkness.wingPos != 0)
        this.darkness.wingPos = 0; 
      if (this.darkness.func_70638_az() != null) {
        EntityLivingBase entityLivingBase = this.darkness.func_70638_az();
        if (entityLivingBase instanceof EntityPlayer && !((EntityPlayer)entityLivingBase).func_175149_v() && !((EntityPlayer)entityLivingBase).func_184812_l_()) {
          setTarget(((Entity)entityLivingBase).field_70165_t + Maths.random(-90.0D, 90.0D), Math.min(((Entity)entityLivingBase).field_70163_u + Maths.random(40.0D, 60.0D), 200.0D), ((Entity)entityLivingBase).field_70161_v + Maths.random(-90.0D, 90.0D));
        } else if (!(entityLivingBase instanceof EntityPlayer)) {
          setTarget(((Entity)entityLivingBase).field_70165_t + Maths.random(-90.0D, 90.0D), Math.min(((Entity)entityLivingBase).field_70163_u + Maths.random(40.0D, 60.0D), 200.0D), ((Entity)entityLivingBase).field_70161_v + Maths.random(-90.0D, 90.0D));
        } 
      } else if (this.darkness.field_71093_bK != 1) {
        setTarget(this.darkness.field_70165_t + Maths.random(-90.0D, 90.0D), Math.min(this.darkness.targetYRelative + Maths.random(40.0D, 60.0D), 200.0D), this.darkness.field_70161_v + Maths.random(-90.0D, 90.0D));
      } else {
        setTarget(Maths.random(-90.0D, 90.0D), Math.min(this.darkness.targetYRelative + Maths.random(40.0D, 60.0D), 200.0D), Maths.random(-90.0D, 90.0D));
      } 
    } 
  }
  
  public void leave() {
    if (this.darkness.shouldChangeAI)
      this.darkness.shouldChangeAI = false; 
    if (!this.darkness.shouldMove)
      this.darkness.shouldMove = true; 
    if (this.darkness.getAttackTime() == 0)
      this.darkness.func_70106_y(); 
    setTarget(this.darkness.field_70165_t + 250.0D, this.darkness.targetYRelative + Maths.random(40.0D, 60.0D), this.darkness.field_70161_v + Maths.random(-90.0D, 90.0D));
  }
  
  public void death() {
    if (this.darkness.shouldChangeAI)
      this.darkness.shouldChangeAI = false; 
    if (!this.darkness.shouldMove)
      this.darkness.shouldMove = false; 
    if (this.darkness.wingPos != 2)
      this.darkness.wingPos = 2; 
    if (this.darkness.getAttackTime() == 0)
      this.darkness.func_70106_y(); 
  }
  
  public void instantdeath() {
    if (this.darkness.shouldChangeAI)
      this.darkness.shouldChangeAI = false; 
    if (!this.darkness.shouldMove)
      this.darkness.shouldMove = false; 
    if (this.darkness.wingPos != 2)
      this.darkness.wingPos = 2; 
    if (this.darkness.getAttackTime() == 0)
      this.darkness.func_70106_y(); 
  }
  
  public void dragonFireball() {
    if (!this.darkness.shouldMove)
      this.darkness.shouldMove = true; 
    if (this.darkness.getAttackTime() % 20 == 0)
      setTarget(); 
    if (this.darkness.getAttackTime() % 40 == 0)
      this.darkness.shootBall((Entity)this.darkness.func_70638_az()); 
  }
  
  public void dragonAcid() {}
  
  public void swoop(double distance) {
    if (this.darkness.forceNewTarget || distance < 64.0D) {
      if (!this.darkness.shouldMove)
        this.darkness.shouldMove = true; 
      if (this.darkness.func_70638_az() != null)
        setTarget((Entity)this.darkness.func_70638_az()); 
    } 
  }
  
  public void bite() {}
  
  public void tailSwipe() {}
  
  public void dragonFireballBarrage() {}
  
  private void setTarget() {
    this.darkness.setTarget();
  }
  
  private void setTarget(Entity entity) {
    this.darkness.setTarget(entity);
  }
  
  private void setTarget(double posX, double posY, double posZ) {
    this.darkness.setTarget(posX, posY, posZ);
  }
}
