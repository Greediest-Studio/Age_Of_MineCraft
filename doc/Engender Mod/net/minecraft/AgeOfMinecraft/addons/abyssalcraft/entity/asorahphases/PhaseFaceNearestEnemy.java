package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseFaceNearestEnemy extends PhaseBaseFriendly {
  private int field_188667_b;
  
  public PhaseFaceNearestEnemy(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doLocalUpdate() {
    if (this.dragon.getOwner() != null)
      if (this.dragon.getJukeboxToDanceTo() != null) {
        this.dragon.func_70634_a(this.dragon.getJukeboxToDanceTo().func_177958_n(), this.dragon.getJukeboxToDanceTo().func_177956_o() + 16.0D, this.dragon.getJukeboxToDanceTo().func_177952_p());
      } else {
        this.dragon.func_70634_a((this.dragon.getOwner()).field_70165_t, (this.dragon.getOwner()).field_70163_u + 4.0D, (this.dragon.getOwner()).field_70161_v);
      }  
    if (this.dragon.getJukeboxToDanceTo() == null)
      this.field_188667_b++; 
    EntityLivingBase entitylivingbase = this.dragon.func_70638_az();
    if (entitylivingbase != null && this.dragon.func_70068_e((Entity)entitylivingbase) < 10000.0D) {
      if (this.field_188667_b > 30) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.SITTING_ATTACKING);
      } else {
        Vec3d vec3d = (new Vec3d(entitylivingbase.field_70165_t - this.dragon.field_70165_t, 0.0D, entitylivingbase.field_70161_v - this.dragon.field_70161_v)).func_72432_b();
        Vec3d vec3d1 = (new Vec3d(MathHelper.func_76126_a(this.dragon.field_70177_z * 0.017453292F), 0.0D, -MathHelper.func_76134_b(this.dragon.field_70177_z * 0.017453292F))).func_72432_b();
        float f = (float)vec3d1.func_72430_b(vec3d);
        float f1 = (float)(Math.acos(f) * 57.29577951308232D) + 0.5F;
        if (f1 < 0.0F || f1 > 10.0F) {
          double d0 = entitylivingbase.field_70165_t - this.dragon.dragonPartHead.field_70165_t;
          double d1 = entitylivingbase.field_70161_v - this.dragon.dragonPartHead.field_70161_v;
          double d2 = MathHelper.func_151237_a(MathHelper.func_76138_g(180.0D - MathHelper.func_181159_b(d0, d1) * 57.29577951308232D - this.dragon.field_70177_z), -100.0D, 100.0D);
          this.dragon.field_70704_bt *= 0.8F;
          float f2 = MathHelper.func_76133_a(d0 * d0 + d1 * d1) + 1.0F;
          float f3 = f2;
          if (f2 > 40.0F)
            f2 = 40.0F; 
          this.dragon.field_70704_bt = (float)(this.dragon.field_70704_bt + d2 * (0.8F / f2 / f3));
          this.dragon.field_70177_z += this.dragon.field_70704_bt;
          this.dragon.field_70177_z += this.dragon.field_70704_bt;
        } 
      } 
    } else if (this.field_188667_b >= 200) {
      entitylivingbase = this.dragon.func_70638_az();
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.TAKEOFF);
      if (entitylivingbase != null && this.dragon.func_70068_e((Entity)entitylivingbase) > 1024.0D) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
        ((PhaseRamAttack)this.dragon.getPhaseManager().<PhaseRamAttack>getPhase(PhaseListAsorah.CHARGING_PLAYER)).func_188668_a(new Vec3d(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v));
      } 
    } else if (this.dragon.getOwner() != null) {
      this.dragon.field_70177_z = (this.dragon.getOwner()).field_70177_z - 180.0F;
    } 
  }
  
  public void initPhase() {
    this.field_188667_b = 0;
  }
  
  public PhaseListAsorah<PhaseFaceNearestEnemy> getPhaseList() {
    return PhaseListAsorah.SITTING_SCANNING;
  }
}
