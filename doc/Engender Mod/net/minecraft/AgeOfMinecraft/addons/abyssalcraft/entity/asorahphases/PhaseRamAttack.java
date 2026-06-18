package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.util.math.Vec3d;

public class PhaseRamAttack extends PhaseBaseFriendly {
  private Vec3d field_188670_c;
  
  private int field_188671_d = 0;
  
  public PhaseRamAttack(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    if (this.field_188670_c == null) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    } else if (this.field_188671_d > 0 && this.field_188671_d++ >= 10) {
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
    } else {
      double d0 = this.field_188670_c.func_186679_c(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v);
      if (d0 < 10.0D || d0 > 90000.0D || this.dragon.field_70123_F || this.dragon.field_70124_G)
        this.field_188671_d++; 
    } 
  }
  
  public void initPhase() {
    this.field_188670_c = null;
    this.field_188671_d = 0;
  }
  
  public void func_188668_a(Vec3d p_188668_1_) {
    this.field_188670_c = p_188668_1_;
  }
  
  public float getMaxRiseOrFall() {
    return 3.0F;
  }
  
  public Vec3d getTargetLocation() {
    return this.field_188670_c;
  }
  
  public PhaseListAsorah<PhaseRamAttack> getPhaseList() {
    return PhaseListAsorah.CHARGING_PLAYER;
  }
}
