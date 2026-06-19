package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PhaseFaceNearestEnemy extends PhaseBaseFriendly {
  private int scanningTime;
  
  public PhaseFaceNearestEnemy(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doLocalUpdate() {
    if (this.dragon.getOwner() != null)
      if (this.dragon.getJukeboxToDanceTo() != null) {
        this.dragon.setPositionAndUpdate(this.dragon.getJukeboxToDanceTo().getX(), this.dragon.getJukeboxToDanceTo().getY() + 16.0D, this.dragon.getJukeboxToDanceTo().getZ());
      } else {
        this.dragon.setPositionAndUpdate((this.dragon.getOwner()).posX, (this.dragon.getOwner()).posY + 4.0D, (this.dragon.getOwner()).posZ);
      }  
    if (this.dragon.getJukeboxToDanceTo() == null)
      this.scanningTime++; 
    EntityLivingBase entitylivingbase = this.dragon.getAttackTarget();
    if (entitylivingbase != null && this.dragon.getDistanceSq(entitylivingbase) < 10000.0D) {
      if (this.scanningTime > 30) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.SITTING_ATTACKING);
      } else {
        Vec3d vec3d = (new Vec3d(entitylivingbase.posX - this.dragon.posX, 0.0D, entitylivingbase.posZ - this.dragon.posZ)).normalize();
        Vec3d vec3d1 = (new Vec3d(MathHelper.sin(this.dragon.rotationYaw * 0.017453292F), 0.0D, -MathHelper.cos(this.dragon.rotationYaw * 0.017453292F))).normalize();
        float f = (float)vec3d1.dotProduct(vec3d);
        float f1 = (float)(Math.acos(f) * 57.29577951308232D) + 0.5F;
        if (f1 < 0.0F || f1 > 10.0F) {
          double d0 = entitylivingbase.posX - this.dragon.dragonPartHead.posX;
          double d1 = entitylivingbase.posZ - this.dragon.dragonPartHead.posZ;
          double d2 = MathHelper.clamp(MathHelper.wrapDegrees(180.0D - MathHelper.atan2(d0, d1) * 57.29577951308232D - this.dragon.rotationYaw), -100.0D, 100.0D);
          this.dragon.randomYawVelocity *= 0.8F;
          float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) + 1.0F;
          float f3 = f2;
          if (f2 > 40.0F)
            f2 = 40.0F; 
          this.dragon.randomYawVelocity = (float)(this.dragon.randomYawVelocity + d2 * (0.8F / f2 / f3));
          this.dragon.rotationYaw += this.dragon.randomYawVelocity;
          this.dragon.rotationYaw += this.dragon.randomYawVelocity;
        } 
      } 
    } else if (this.scanningTime >= 200) {
      entitylivingbase = this.dragon.getAttackTarget();
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.TAKEOFF);
      if (entitylivingbase != null && this.dragon.getDistanceSq(entitylivingbase) > 1024.0D) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.CHARGING_PLAYER);
        this.dragon.getPhaseManager().getPhase(PhaseListAsorah.CHARGING_PLAYER).setTarget(new Vec3d(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ));
      } 
    } else if (this.dragon.getOwner() != null) {
      this.dragon.rotationYaw = (this.dragon.getOwner()).rotationYaw - 180.0F;
    } 
  }
  
  public void initPhase() {
    this.scanningTime = 0;
  }
  
  public PhaseListAsorah<PhaseFaceNearestEnemy> getPhaseList() {
    return PhaseListAsorah.SITTING_SCANNING;
  }
}
