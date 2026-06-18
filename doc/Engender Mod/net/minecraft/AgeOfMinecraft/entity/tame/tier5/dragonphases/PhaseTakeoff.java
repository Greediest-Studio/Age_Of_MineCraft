package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseTakeoff extends PhaseBaseFriendly {
  private boolean firstTick;
  
  private Path currentPath;
  
  private Vec3d targetLocation;
  
  public PhaseTakeoff(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (!this.firstTick && this.currentPath != null) {
      BlockPos blockpos = this.dragon.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
      double d0 = this.dragon.func_174831_c(blockpos);
      if (d0 > 100.0D)
        this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN); 
    } else {
      this.firstTick = false;
      this.dragon.getPhaseManager().setPhase(PhaseList.HOLDING_PATTERN);
    } 
  }
  
  public void initPhase() {
    this.firstTick = true;
    this.currentPath = null;
    this.targetLocation = null;
  }
  
  private void findNewTarget() {
    int i = this.dragon.initPathPoints();
    Vec3d vec3d = this.dragon.getHeadLookVec(1.0F);
    int j = this.dragon.getNearestPpIdx(-vec3d.field_72450_a * 40.0D, 105.0D, -vec3d.field_72449_c * 40.0D);
    j -= 12;
    j &= 0x7;
    j += 12;
    this.currentPath = this.dragon.findPath(i, j, (PathPoint)null);
    if (this.currentPath != null) {
      this.currentPath.func_75875_a();
      navigateToNextPathNode();
    } 
  }
  
  private void navigateToNextPathNode() {
    double d0;
    Vec3d vec3d = this.currentPath.func_186310_f();
    this.currentPath.func_75875_a();
    do {
      d0 = vec3d.field_72448_b + (this.dragon.func_70681_au().nextFloat() * 20.0F);
    } while (d0 < vec3d.field_72448_b);
    this.targetLocation = new Vec3d(vec3d.field_72450_a, d0, vec3d.field_72449_c);
  }
  
  @Nullable
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  public PhaseList<PhaseTakeoff> getPhaseList() {
    return PhaseList.TAKEOFF;
  }
}
