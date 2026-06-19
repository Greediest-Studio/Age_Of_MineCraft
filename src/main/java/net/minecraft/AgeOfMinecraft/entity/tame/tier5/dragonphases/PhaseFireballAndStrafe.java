package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseFireballAndStrafe extends PhaseBaseFriendly {
  private int fireballCharge;
  
  private Path currentPath;
  
  private Vec3d targetLocation;
  
  private EntityLivingBase attackTarget;
  
  private boolean holdingPatternClockwise;
  
  public PhaseFireballAndStrafe(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (this.attackTarget == null) {
      this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
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
            double d14 = 8.0D;
            Vec3d vec3d2 = this.dragon.getLook(1.0F);
            double d6 = this.dragon.dragonPartHead.posX;
            double d7 = this.dragon.dragonPartHead.posY + 2.0D;
            double d8 = this.dragon.dragonPartHead.posZ;
            double d9 = this.attackTarget.posX - d6;
            double d10 = this.attackTarget.posY + 1.0D - d7;
            double d11 = this.attackTarget.posZ - d8;
            this.dragon.world.playEvent((EntityPlayer)null, 1016, new BlockPos((Entity)this.dragon), 0);
            EntityDragonFireballOther entitydragonfireball = new EntityDragonFireballOther(this.dragon.world, (EntityLivingBase)this.dragon, d9, d10, d11);
            entitydragonfireball.posX = d6;
            entitydragonfireball.posY = d7;
            entitydragonfireball.posZ = d8;
            this.dragon.world.spawnEntity((Entity)entitydragonfireball);
            this.fireballCharge = 0;
            if (this.currentPath != null)
              while (!this.currentPath.isFinished())
                this.currentPath.incrementPathIndex();  
            this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
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
  
  public PhaseList<PhaseFireballAndStrafe> getPhaseList() {
    return PhaseList.STRAFE_PLAYER;
  }
}
