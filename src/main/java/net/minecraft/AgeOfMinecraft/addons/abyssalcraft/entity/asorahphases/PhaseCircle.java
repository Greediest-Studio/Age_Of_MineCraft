package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PhaseCircle extends PhaseBaseFriendly {
  private Path currentPath;
  
  private Vec3d targetLocation;
  
  private boolean clockwise;
  
  public PhaseCircle(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public PhaseListAsorah<PhaseCircle> getPhaseList() {
    return PhaseListAsorah.HOLDING_PATTERN;
  }
  
  public void doLocalUpdate() {
    double d0 = (this.targetLocation == null) ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
    if (d0 < 10.0D || d0 > 40000.0D)
      findNewTarget(); 
  }
  
  public void initPhase() {
    this.currentPath = null;
    this.targetLocation = null;
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  private void findNewTarget() {
    if (this.currentPath != null && this.currentPath.isFinished()) {
      BlockPos blockpos = new BlockPos(this.dragon.posX, 48.0D, this.dragon.posZ);
      if (this.dragon.getOwner() != null)
        blockpos = new BlockPos((this.dragon.getOwner()).posX, (this.dragon.getOwner()).posY, (this.dragon.getOwner()).posZ); 
      if (this.dragon.getRNG().nextInt(5) == 0) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.LANDING_APPROACH);
        return;
      } 
      double d0 = 64.0D;
      EntityLivingBase entity = this.dragon.getAttackTarget();
      if (entity != null)
        d0 = entity.getDistanceSqToCenter(blockpos) / 512.0D; 
      if (entity != null && this.dragon.getRNG().nextInt(2) == 0) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
        this.dragon.getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER).setTarget(new Vec3d(entity.posX, entity.posY, entity.posZ));
        return;
      } 
      if (entity != null && this.dragon.getRNG().nextInt(3) == 0) {
        strafePlayer(entity);
        return;
      } 
    } 
    if (this.currentPath == null || this.currentPath.isFinished()) {
      int j = this.dragon.initPathPoints();
      int k = j;
      if (this.dragon.getRNG().nextInt(8) == 0) {
        this.clockwise = !this.clockwise;
        k = j + 6;
      } 
      if (this.clockwise) {
        k++;
      } else {
        k--;
      } 
      k -= 12;
      k &= 0x7;
      k += 12;
      this.currentPath = this.dragon.findPath(j, k, null);
      if (this.currentPath != null)
        this.currentPath.incrementPathIndex(); 
    } 
    navigateToNextPathNode();
  }
  
  private void strafePlayer(EntityLivingBase p_188674_1_) {
    if (!false) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
      this.dragon.getPhaseManager().getPhase(PhaseListAsorah.STRAFE_PLAYER).setTarget(p_188674_1_);
    } 
  }
  
  private void navigateToNextPathNode() {
    if (this.currentPath != null && !this.currentPath.isFinished()) {
      double d2;
      Vec3d vec3d = this.currentPath.getCurrentPos();
      this.currentPath.incrementPathIndex();
      double d0 = vec3d.x;
      double d1 = vec3d.z;
      do {
        if (this.dragon.getAttackTarget() != null) {
          d2 = vec3d.y;
        } else {
          d2 = vec3d.y + 30.0D + this.dragon.getRNG().nextDouble() * 20.0D;
        } 
      } while (d2 < vec3d.y);
      if (this.dragon.getOwner() != null) {
        this.targetLocation = new Vec3d((this.dragon.getOwner()).posX + d0, Flying.clampFlightY((this.dragon.getOwner()).posY + d2), (this.dragon.getOwner()).posZ + d1);
      } else {
        this.targetLocation = new Vec3d(d0, Flying.clampFlightY(d2), d1);
      } 
    } 
  }
}


