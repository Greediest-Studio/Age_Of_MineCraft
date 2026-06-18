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
      if (this.darkness.getAttackTarget() != null) {
        EntityLivingBase entityLivingBase = this.darkness.getAttackTarget();
        if (entityLivingBase instanceof EntityPlayer && !((EntityPlayer)entityLivingBase).isSpectator() && !((EntityPlayer)entityLivingBase).isCreative()) {
          setTarget(((Entity)entityLivingBase).posX + Maths.random(-90.0D, 90.0D), Math.min(((Entity)entityLivingBase).posY + Maths.random(40.0D, 60.0D), 200.0D), ((Entity)entityLivingBase).posZ + Maths.random(-90.0D, 90.0D));
        } else if (!(entityLivingBase instanceof EntityPlayer)) {
          setTarget(((Entity)entityLivingBase).posX + Maths.random(-90.0D, 90.0D), Math.min(((Entity)entityLivingBase).posY + Maths.random(40.0D, 60.0D), 200.0D), ((Entity)entityLivingBase).posZ + Maths.random(-90.0D, 90.0D));
        } 
      } else if (this.darkness.dimension != 1) {
        setTarget(this.darkness.posX + Maths.random(-90.0D, 90.0D), Math.min(this.darkness.targetYRelative + Maths.random(40.0D, 60.0D), 200.0D), this.darkness.posZ + Maths.random(-90.0D, 90.0D));
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
      this.darkness.setDead(); 
    setTarget(this.darkness.posX + 250.0D, this.darkness.targetYRelative + Maths.random(40.0D, 60.0D), this.darkness.posZ + Maths.random(-90.0D, 90.0D));
  }
  
  public void death() {
    if (this.darkness.shouldChangeAI)
      this.darkness.shouldChangeAI = false; 
    if (!this.darkness.shouldMove)
      this.darkness.shouldMove = false; 
    if (this.darkness.wingPos != 2)
      this.darkness.wingPos = 2; 
    if (this.darkness.getAttackTime() == 0)
      this.darkness.setDead(); 
  }
  
  public void instantdeath() {
    if (this.darkness.shouldChangeAI)
      this.darkness.shouldChangeAI = false; 
    if (!this.darkness.shouldMove)
      this.darkness.shouldMove = false; 
    if (this.darkness.wingPos != 2)
      this.darkness.wingPos = 2; 
    if (this.darkness.getAttackTime() == 0)
      this.darkness.setDead(); 
  }
  
  public void dragonFireball() {
    if (!this.darkness.shouldMove)
      this.darkness.shouldMove = true; 
    if (this.darkness.getAttackTime() % 20 == 0)
      setTarget(); 
    if (this.darkness.getAttackTime() % 40 == 0)
      this.darkness.shootBall((Entity)this.darkness.getAttackTarget()); 
  }
  
  public void dragonAcid() {}
  
  public void swoop(double distance) {
    if (this.darkness.forceNewTarget || distance < 64.0D) {
      if (!this.darkness.shouldMove)
        this.darkness.shouldMove = true; 
      if (this.darkness.getAttackTarget() != null)
        setTarget((Entity)this.darkness.getAttackTarget()); 
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
