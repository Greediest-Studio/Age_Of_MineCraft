package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.util.math.Vec3d;

public class PhaseIdleHover extends PhaseBaseFriendly {
  private Vec3d targetLocation;
  
  public PhaseIdleHover(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (this.targetLocation == null)
      this.targetLocation = new Vec3d(this.dragon.posX, Flying.clampFlightY(this.dragon.posY), this.dragon.posZ); 
    if (!this.dragon.sitting)
      this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN); 
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void initPhase() {
    this.targetLocation = null;
  }
  
  public float getMaxRiseOrFall() {
    return 1.5F;
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  public PhaseList<PhaseIdleHover> getPhaseList() {
    return PhaseList.HOVER;
  }
}
