package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
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
  
  public PhaseApproachOwner(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public PhaseList<PhaseApproachOwner> getPhaseList() {
    return PhaseList.LANDING_APPROACH;
  }
  
  public void initPhase() {
    this.currentPath = null;
    this.targetLocation = null;
  }
  
  public void doLocalUpdate() {
    double d0 = (this.targetLocation == null) ? 0.0D : this.targetLocation.func_186679_c(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v);
    if (d0 < 10.0D || d0 > 25500.0D)
      func_188681_j(); 
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  private void func_188681_j() {
    if (this.currentPath == null || this.currentPath.func_75879_b()) {
      int j, i = this.dragon.initPathPoints();
      BlockPos blockpos = this.dragon.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
      EntityPlayer entityplayer = this.dragon.field_70170_p.func_184139_a(blockpos, 128.0D, 128.0D);
      if (this.dragon.getOwner() != null)
        blockpos = new BlockPos((Entity)this.dragon.getOwner()); 
      if (entityplayer != null && entityplayer != this.dragon.getOwner()) {
        Vec3d vec3d = (new Vec3d(entityplayer.field_70165_t, 0.0D, entityplayer.field_70161_v)).func_72432_b();
        j = this.dragon.getNearestPpIdx(-vec3d.field_72450_a * 40.0D, 105.0D, -vec3d.field_72449_c * 40.0D);
      } else {
        j = this.dragon.getNearestPpIdx(40.0D, blockpos.func_177956_o(), 0.0D);
      } 
      PathPoint pathpoint = new PathPoint(blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p());
      this.currentPath = this.dragon.findPath(i, j, pathpoint);
      if (this.currentPath != null)
        this.currentPath.func_75875_a(); 
    } 
    func_188682_k();
    if (this.currentPath != null && this.currentPath.func_75879_b())
      this.dragon.getPhaseManager().setPhase(PhaseList.LANDING); 
  }
  
  private void func_188682_k() {
    if (this.currentPath != null && !this.currentPath.func_75879_b()) {
      double d2;
      Vec3d vec3d = this.currentPath.func_186310_f();
      this.currentPath.func_75875_a();
      double d0 = vec3d.field_72450_a;
      double d1 = vec3d.field_72449_c;
      do {
        d2 = vec3d.field_72448_b + (this.dragon.func_70681_au().nextFloat() * 20.0F);
      } while (d2 < vec3d.field_72448_b);
      this.targetLocation = new Vec3d(d0, d2, d1);
    } 
  }
}
