package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseLightningAndStrafe extends PhaseBaseFriendly {
  private int fireballCharge;
  
  private Path currentPath;
  
  private Vec3d targetLocation;
  
  private EntityLivingBase attackTarget;
  
  private boolean holdingPatternClockwise;
  
  public PhaseLightningAndStrafe(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (this.attackTarget == null) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    } else {
      if (this.currentPath != null && this.currentPath.isFinished()) {
        double d0 = this.attackTarget.posX;
        double d1 = this.attackTarget.posZ;
        double d2 = d0 - this.dragon.posX;
        double d3 = d1 - this.dragon.posZ;
        double d4 = MathHelper.sqrt(d2 * d2 + d3 * d3);
        double d5 = Math.min(0.4000000059604645D + d4 / 80.0D - 1.0D, 10.0D);
        this.targetLocation = new Vec3d(d0, Flying.clampFlightY(this.attackTarget.posY + d5), d1);
      } 
      double d12 = (this.targetLocation == null) ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
      if (d12 < 100.0D || d12 > 22500.0D)
        findNewTarget(); 
      double d13 = 64.0D;
      if (this.attackTarget.getDistanceSq((Entity)this.dragon) < d13 * d13) {
        if (this.dragon.canEntityBeSeen((Entity)this.attackTarget)) {
          this.fireballCharge++;
          Vec3d vec3d1 = (new Vec3d(this.attackTarget.posX - this.dragon.posX, 0.0D, this.attackTarget.posZ - this.dragon.posZ)).normalize();
          Vec3d vec3d = (new Vec3d(MathHelper.sin(this.dragon.rotationYaw * 0.017453292F), 0.0D, -MathHelper.cos(this.dragon.rotationYaw * 0.017453292F))).normalize();
          float f1 = (float)vec3d.dotProduct(vec3d1);
          float f = (float)(Math.acos(f1) * 57.29577951308232D);
          f += 0.5F;
          if (this.fireballCharge >= 5 && f >= 0.0F && f < 10.0F) {
            this.dragon.world.addWeatherEffect((Entity)new EntityLightningBolt(this.dragon.world, this.dragon.dragonPartWing1.posX - 0.5D, this.dragon.dragonPartWing1.posY, this.dragon.dragonPartWing1.posZ - 0.5D, true));
            this.dragon.world.addWeatherEffect((Entity)new EntityLightningBolt(this.dragon.world, this.dragon.dragonPartWing2.posX - 0.5D, this.dragon.dragonPartWing2.posY, this.dragon.dragonPartWing2.posZ - 0.5D, true));
            this.dragon.world.addWeatherEffect((Entity)new EntityLightningBolt(this.dragon.world, this.attackTarget.posX - 0.5D, this.attackTarget.posY, this.attackTarget.posZ - 0.5D, false));
            this.dragon.attackEntityAsMob((Entity)this.attackTarget);
            this.dragon.attackEntityAsMob((Entity)this.attackTarget);
            this.dragon.attackEntityAsMob((Entity)this.attackTarget);
            this.dragon.attackEntityAsMob((Entity)this.attackTarget);
            this.dragon.attackEntityAsMob((Entity)this.attackTarget);
            this.fireballCharge = 0;
            if (this.currentPath != null)
              while (!this.currentPath.isFinished())
                this.currentPath.incrementPathIndex();  
            this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
            this.dragon.setAttackTarget(null);
          } 
        } else if (this.fireballCharge > 0) {
          this.fireballCharge--;
        } 
      } else if (this.fireballCharge > 0) {
        this.fireballCharge--;
      } 
    } 
  }
  
  private void findNewTarget() {
    if (this.currentPath == null || this.currentPath.isFinished()) {
      int j = 6;
      if (this.dragon.getRNG().nextInt(8) == 0)
        this.holdingPatternClockwise = !this.holdingPatternClockwise; 
      if (this.holdingPatternClockwise) {
        j++;
      } else {
        j--;
      } 
      j -= 12;
      j &= 0x7;
      j += 12;
      if (this.currentPath != null)
        this.currentPath.incrementPathIndex(); 
    } 
    navigateToNextPathNode();
  }
  
  private void navigateToNextPathNode() {
    if (this.currentPath != null && !this.currentPath.isFinished()) {
      double d1;
      Vec3d vec3d = this.currentPath.getCurrentPos();
      this.currentPath.incrementPathIndex();
      double d0 = vec3d.x;
      double d2 = vec3d.z;
      do {
        d1 = vec3d.y + 30.0D + (this.dragon.getRNG().nextFloat() * 20.0F);
      } while (d1 < vec3d.y);
      this.targetLocation = new Vec3d(d0, Flying.clampFlightY(d1), d2);
    } 
  }
  
  public void initPhase() {
    this.fireballCharge = 0;
    this.targetLocation = null;
    this.currentPath = null;
    this.attackTarget = null;
  }
  
  public void setTarget(EntityLivingBase p_188686_1_) {
    this.attackTarget = p_188686_1_;
    int i = this.dragon.initPathPoints();
    int j = this.dragon.getNearestPpIdx(this.attackTarget.posX, this.attackTarget.posY, this.attackTarget.posZ);
    int k = MathHelper.floor(this.attackTarget.posX);
    int l = MathHelper.floor(this.attackTarget.posZ);
    double d0 = k - this.dragon.posX;
    double d1 = l - this.dragon.posZ;
    double d2 = MathHelper.sqrt(d0 * d0 + d1 * d1);
    double d3 = Math.min(0.4000000059604645D + d2 / 80.0D - 1.0D, 10.0D);
    int i1 = MathHelper.floor(Flying.clampFlightY(this.attackTarget.posY + d3));
    PathPoint pathpoint = new PathPoint(k, i1, l);
    this.currentPath = this.dragon.findPath(i, j, pathpoint);
    if (this.currentPath != null) {
      this.currentPath.incrementPathIndex();
      navigateToNextPathNode();
    } 
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  public PhaseListAsorah<PhaseLightningAndStrafe> getPhaseList() {
    return PhaseListAsorah.TAKEOFF;
  }
}
