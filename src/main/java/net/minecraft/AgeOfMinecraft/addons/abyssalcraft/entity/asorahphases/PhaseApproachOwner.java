package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseApproachOwner extends PhaseBaseFriendly {
  private Path currentPath;
  
  private Vec3d targetLocation;
  
  public PhaseApproachOwner(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public PhaseListAsorah<PhaseApproachOwner> getPhaseList() {
    return PhaseListAsorah.LANDING_APPROACH;
  }
  
  public void initPhase() {
    this.currentPath = null;
    this.targetLocation = null;
  }
  
  public void doLocalUpdate() {
    double d0 = (this.targetLocation == null) ? 0.0D : this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
    if (d0 < 10.0D || d0 > 90000.0D)
      findNewTarget(); 
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  private void findNewTarget() {
    if (this.currentPath == null || this.currentPath.isFinished()) {
      int j, i = this.dragon.initPathPoints();
      BlockPos blockpos = this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
      EntityPlayer entityplayer = this.dragon.world.getNearestAttackablePlayer(blockpos, 128.0D, 128.0D);
      if (this.dragon.getOwner() != null)
        blockpos = new BlockPos((Entity)this.dragon.getOwner()); 
      if (entityplayer != null && entityplayer != this.dragon.getOwner()) {
        Vec3d vec3d = (new Vec3d(entityplayer.posX, 0.0D, entityplayer.posZ)).normalize();
        j = this.dragon.getNearestPpIdx(-vec3d.x * 40.0D, 105.0D, -vec3d.z * 40.0D);
      } else {
        j = this.dragon.getNearestPpIdx(40.0D, blockpos.getY(), 0.0D);
      } 
      PathPoint pathpoint = new PathPoint(blockpos.getX(), blockpos.getY(), blockpos.getZ());
      if (this.dragon.getOwner() != null)
        pathpoint = new PathPoint((int)(this.dragon.getOwner()).posX, (int)(this.dragon.getOwner()).posY, (int)(this.dragon.getOwner()).posZ); 
      this.currentPath = this.dragon.findPath(i, j, pathpoint);
      if (this.currentPath != null)
        this.currentPath.incrementPathIndex(); 
    } 
    navigateToNextPathNode();
    if (this.currentPath != null && this.currentPath.isFinished())
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.LANDING); 
  }
  
  private void navigateToNextPathNode() {
    if (this.currentPath != null && !this.currentPath.isFinished()) {
      double d2;
      Vec3d vec3d = this.currentPath.getCurrentPos();
      this.currentPath.incrementPathIndex();
      double d0 = vec3d.x;
      double d1 = vec3d.z;
      do {
        d2 = vec3d.y + (this.dragon.getRNG().nextFloat() * 20.0F);
      } while (d2 < vec3d.y);
      this.targetLocation = new Vec3d(d0, d2, d1);
    } 
  }
}
