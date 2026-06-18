package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.util.math.Vec3d;

public class PhaseRamAttack extends PhaseBaseFriendly {
  private Vec3d targetLocation;
  
  private int timeSinceCharge = 0;
  
  public PhaseRamAttack(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (this.targetLocation == null) {
      this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
    } else if (this.timeSinceCharge > 0 && this.timeSinceCharge++ >= 10) {
      this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
    } else if (this.dragon.getPhaseManager().getCurrentPhase() != PhaseList.LANDING_APPROACH) {
      double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
      if (d0 < 10.0D || d0 > 25500.0D)
        this.timeSinceCharge++; 
    } 
  }
  
  public void initPhase() {
    if (this.dragon.getAttackTarget() == null) {
      this.targetLocation = null;
    } else {
      this.targetLocation = new Vec3d((this.dragon.getAttackTarget()).posX, (this.dragon.getAttackTarget()).posY + this.dragon.getRNG().nextDouble() * 2.0D - 1.0D, (this.dragon.getAttackTarget()).posZ);
    } 
    this.timeSinceCharge = 0;
  }
  
  public void setTarget(Vec3d p_188668_1_) {
    this.targetLocation = p_188668_1_;
  }
  
  public float getMaxRiseOrFall() {
    return 3.0F;
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  public PhaseList<PhaseRamAttack> getPhaseList() {
    return PhaseList.CHARGING_PLAYER;
  }
}
