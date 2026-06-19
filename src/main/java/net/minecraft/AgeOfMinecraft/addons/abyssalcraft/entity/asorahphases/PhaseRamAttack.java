package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.util.math.Vec3d;

public class PhaseRamAttack extends PhaseBaseFriendly {
  private Vec3d targetLocation;
  
  private int timeSinceCharge = 0;
  
  public PhaseRamAttack(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (this.targetLocation == null) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    } else if (this.timeSinceCharge > 0 && this.timeSinceCharge++ >= 10) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    } else {
      double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
      if (d0 < 10.0D || d0 > 90000.0D || this.dragon.collidedHorizontally || this.dragon.collidedVertically)
        this.timeSinceCharge++; 
    } 
  }
  
  public void initPhase() {
    this.targetLocation = null;
    this.timeSinceCharge = 0;
  }
  
  public void setTarget(Vec3d p_188668_1_) {
    this.targetLocation = new Vec3d(p_188668_1_.x, Flying.clampFlightY(p_188668_1_.y), p_188668_1_.z);
  }
  
  public float getMaxRiseOrFall() {
    return 3.0F;
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  public PhaseListAsorah<PhaseRamAttack> getPhaseList() {
    return PhaseListAsorah.CHARGING_PLAYER;
  }
}
