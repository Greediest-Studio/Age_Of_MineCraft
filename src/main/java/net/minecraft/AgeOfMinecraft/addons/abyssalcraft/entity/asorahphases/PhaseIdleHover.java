package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.util.math.Vec3d;

public class PhaseIdleHover extends PhaseBaseFriendly {
  private Vec3d targetLocation;
  
  public PhaseIdleHover(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    this.targetLocation = new Vec3d(this.dragon.posX, Flying.clampFlightY(this.dragon.posY), this.dragon.posZ);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void initPhase() {
    this.targetLocation = null;
  }
  
  public float getMaxRiseOrFall() {
    return 2.0F;
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  public PhaseListAsorah<PhaseIdleHover> getPhaseList() {
    return PhaseListAsorah.HOVER;
  }
}
