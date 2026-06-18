package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.util.math.Vec3d;

public class PhaseIdleHover extends PhaseBaseFriendly {
  private Vec3d field_188680_b;
  
  public PhaseIdleHover(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public void doLocalUpdate() {
    this.field_188680_b = new Vec3d(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void initPhase() {
    this.field_188680_b = null;
  }
  
  public float getMaxRiseOrFall() {
    return 2.0F;
  }
  
  public Vec3d getTargetLocation() {
    return this.field_188680_b;
  }
  
  public PhaseListAsorah<PhaseIdleHover> getPhaseList() {
    return PhaseListAsorah.HOVER;
  }
}
