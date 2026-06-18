package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseLightningAndStrafe extends PhaseBaseFriendly {
  private int field_188690_c;
  
  private Path field_188691_d;
  
  private Vec3d field_188692_e;
  
  private EntityLivingBase attackTarget;
  
  private boolean field_188694_g;
  
  public PhaseLightningAndStrafe(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (this.attackTarget == null) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    } else {
      if (this.field_188691_d != null && this.field_188691_d.func_75879_b()) {
        double d0 = this.attackTarget.field_70165_t;
        double d1 = this.attackTarget.field_70161_v;
        double d2 = d0 - this.dragon.field_70165_t;
        double d3 = d1 - this.dragon.field_70161_v;
        double d4 = MathHelper.func_76133_a(d2 * d2 + d3 * d3);
        double d5 = Math.min(0.4000000059604645D + d4 / 80.0D - 1.0D, 10.0D);
        this.field_188692_e = new Vec3d(d0, this.attackTarget.field_70163_u + d5, d1);
      } 
      double d12 = (this.field_188692_e == null) ? 0.0D : this.field_188692_e.func_186679_c(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v);
      if (d12 < 100.0D || d12 > 22500.0D)
        func_188687_j(); 
      double d13 = 64.0D;
      if (this.attackTarget.func_70068_e((Entity)this.dragon) < d13 * d13) {
        if (this.dragon.func_70685_l((Entity)this.attackTarget)) {
          this.field_188690_c++;
          Vec3d vec3d1 = (new Vec3d(this.attackTarget.field_70165_t - this.dragon.field_70165_t, 0.0D, this.attackTarget.field_70161_v - this.dragon.field_70161_v)).func_72432_b();
          Vec3d vec3d = (new Vec3d(MathHelper.func_76126_a(this.dragon.field_70177_z * 0.017453292F), 0.0D, -MathHelper.func_76134_b(this.dragon.field_70177_z * 0.017453292F))).func_72432_b();
          float f1 = (float)vec3d.func_72430_b(vec3d1);
          float f = (float)(Math.acos(f1) * 57.29577951308232D);
          f += 0.5F;
          if (this.field_188690_c >= 5 && f >= 0.0F && f < 10.0F) {
            this.dragon.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.dragon.field_70170_p, this.dragon.dragonPartWing1.field_70165_t - 0.5D, this.dragon.dragonPartWing1.field_70163_u, this.dragon.dragonPartWing1.field_70161_v - 0.5D, true));
            this.dragon.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.dragon.field_70170_p, this.dragon.dragonPartWing2.field_70165_t - 0.5D, this.dragon.dragonPartWing2.field_70163_u, this.dragon.dragonPartWing2.field_70161_v - 0.5D, true));
            this.dragon.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.dragon.field_70170_p, this.attackTarget.field_70165_t - 0.5D, this.attackTarget.field_70163_u, this.attackTarget.field_70161_v - 0.5D, false));
            this.dragon.func_70652_k((Entity)this.attackTarget);
            this.dragon.func_70652_k((Entity)this.attackTarget);
            this.dragon.func_70652_k((Entity)this.attackTarget);
            this.dragon.func_70652_k((Entity)this.attackTarget);
            this.dragon.func_70652_k((Entity)this.attackTarget);
            this.field_188690_c = 0;
            if (this.field_188691_d != null)
              while (!this.field_188691_d.func_75879_b())
                this.field_188691_d.func_75875_a();  
            this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
            this.dragon.func_70624_b(null);
          } 
        } else if (this.field_188690_c > 0) {
          this.field_188690_c--;
        } 
      } else if (this.field_188690_c > 0) {
        this.field_188690_c--;
      } 
    } 
  }
  
  private void func_188687_j() {
    if (this.field_188691_d == null || this.field_188691_d.func_75879_b()) {
      int j = 6;
      if (this.dragon.func_70681_au().nextInt(8) == 0)
        this.field_188694_g = !this.field_188694_g; 
      if (this.field_188694_g) {
        j++;
      } else {
        j--;
      } 
      j -= 12;
      j &= 0x7;
      j += 12;
      if (this.field_188691_d != null)
        this.field_188691_d.func_75875_a(); 
    } 
    func_188688_k();
  }
  
  private void func_188688_k() {
    if (this.field_188691_d != null && !this.field_188691_d.func_75879_b()) {
      double d1;
      Vec3d vec3d = this.field_188691_d.func_186310_f();
      this.field_188691_d.func_75875_a();
      double d0 = vec3d.field_72450_a;
      double d2 = vec3d.field_72449_c;
      do {
        d1 = vec3d.field_72448_b + 30.0D + (this.dragon.func_70681_au().nextFloat() * 20.0F);
      } while (d1 < vec3d.field_72448_b);
      this.field_188692_e = new Vec3d(d0, d1, d2);
    } 
  }
  
  public void initPhase() {
    this.field_188690_c = 0;
    this.field_188692_e = null;
    this.field_188691_d = null;
    this.attackTarget = null;
  }
  
  public void func_188686_a(EntityLivingBase p_188686_1_) {
    this.attackTarget = p_188686_1_;
    int i = this.dragon.initPathPoints();
    int j = this.dragon.getNearestPpIdx(this.attackTarget.field_70165_t, this.attackTarget.field_70163_u, this.attackTarget.field_70161_v);
    int k = MathHelper.func_76128_c(this.attackTarget.field_70165_t);
    int l = MathHelper.func_76128_c(this.attackTarget.field_70161_v);
    double d0 = k - this.dragon.field_70165_t;
    double d1 = l - this.dragon.field_70161_v;
    double d2 = MathHelper.func_76133_a(d0 * d0 + d1 * d1);
    double d3 = Math.min(0.4000000059604645D + d2 / 80.0D - 1.0D, 10.0D);
    int i1 = MathHelper.func_76128_c(this.attackTarget.field_70163_u + d3);
    PathPoint pathpoint = new PathPoint(k, i1, l);
    this.field_188691_d = this.dragon.findPath(i, j, pathpoint);
    if (this.field_188691_d != null) {
      this.field_188691_d.func_75875_a();
      func_188688_k();
    } 
  }
  
  public Vec3d getTargetLocation() {
    return this.field_188692_e;
  }
  
  public PhaseListAsorah<PhaseLightningAndStrafe> getPhaseList() {
    return PhaseListAsorah.TAKEOFF;
  }
}
