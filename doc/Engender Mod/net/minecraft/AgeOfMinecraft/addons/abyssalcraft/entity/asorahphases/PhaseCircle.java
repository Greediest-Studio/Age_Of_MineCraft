package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PhaseCircle extends PhaseBaseFriendly {
  private Path field_188677_b;
  
  private Vec3d field_188678_c;
  
  private boolean field_188679_d;
  
  public PhaseCircle(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public PhaseListAsorah<PhaseCircle> getPhaseList() {
    return PhaseListAsorah.HOLDING_PATTERN;
  }
  
  public void doLocalUpdate() {
    double d0 = (this.field_188678_c == null) ? 0.0D : this.field_188678_c.func_186679_c(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v);
    if (d0 < 10.0D || d0 > 40000.0D)
      func_188675_j(); 
  }
  
  public void initPhase() {
    this.field_188677_b = null;
    this.field_188678_c = null;
  }
  
  public Vec3d getTargetLocation() {
    return this.field_188678_c;
  }
  
  private void func_188675_j() {
    if (this.field_188677_b != null && this.field_188677_b.func_75879_b()) {
      BlockPos blockpos = new BlockPos(this.dragon.field_70165_t, 48.0D, this.dragon.field_70161_v);
      if (this.dragon.getOwner() != null)
        blockpos = new BlockPos((this.dragon.getOwner()).field_70165_t, (this.dragon.getOwner()).field_70163_u, (this.dragon.getOwner()).field_70161_v); 
      if (this.dragon.func_70681_au().nextInt(5) == 0) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.LANDING_APPROACH);
        return;
      } 
      double d0 = 64.0D;
      EntityLivingBase entity = this.dragon.func_70638_az();
      if (entity != null)
        d0 = entity.func_174831_c(blockpos) / 512.0D; 
      if (entity != null && this.dragon.func_70681_au().nextInt(2) == 0) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
        ((PhaseRamAttack)this.dragon.getPhaseManager().<PhaseRamAttack>getPhase(PhaseListAsorah.CHARGING_PLAYER)).func_188668_a(new Vec3d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
        return;
      } 
      if (entity != null && this.dragon.func_70681_au().nextInt(3) == 0) {
        func_188674_a(entity);
        return;
      } 
    } 
    if (this.field_188677_b == null || this.field_188677_b.func_75879_b()) {
      int j = this.dragon.initPathPoints();
      int k = j;
      if (this.dragon.func_70681_au().nextInt(8) == 0) {
        this.field_188679_d = !this.field_188679_d;
        k = j + 6;
      } 
      if (this.field_188679_d) {
        k++;
      } else {
        k--;
      } 
      k -= 12;
      k &= 0x7;
      k += 12;
      this.field_188677_b = this.dragon.findPath(j, k, (PathPoint)null);
      if (this.field_188677_b != null)
        this.field_188677_b.func_75875_a(); 
    } 
    func_188676_k();
  }
  
  private void func_188674_a(EntityLivingBase p_188674_1_) {
    if (!p_188674_1_.func_184191_r((Entity)this.dragon)) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.STRAFE_PLAYER);
      ((PhaseFireballAndStrafe)this.dragon.getPhaseManager().<PhaseFireballAndStrafe>getPhase(PhaseListAsorah.STRAFE_PLAYER)).func_188686_a(p_188674_1_);
    } 
  }
  
  private void func_188676_k() {
    if (this.field_188677_b != null && !this.field_188677_b.func_75879_b()) {
      double d2;
      Vec3d vec3d = this.field_188677_b.func_186310_f();
      this.field_188677_b.func_75875_a();
      double d0 = vec3d.field_72450_a;
      double d1 = vec3d.field_72449_c;
      do {
        if (this.dragon.func_70638_az() != null) {
          d2 = vec3d.field_72448_b;
        } else {
          d2 = vec3d.field_72448_b + 30.0D + this.dragon.func_70681_au().nextDouble() * 20.0D;
        } 
      } while (d2 < vec3d.field_72448_b);
      if (this.dragon.getOwner() != null) {
        this.field_188678_c = new Vec3d((this.dragon.getOwner()).field_70165_t + d0, (this.dragon.getOwner()).field_70163_u + d2, (this.dragon.getOwner()).field_70161_v + d1);
      } else {
        this.field_188678_c = new Vec3d(d0, d2, d1);
      } 
    } 
  }
}
