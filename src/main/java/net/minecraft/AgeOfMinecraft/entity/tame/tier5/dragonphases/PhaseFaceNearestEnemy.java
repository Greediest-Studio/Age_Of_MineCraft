package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseFaceNearestEnemy extends PhaseBaseFriendly {
  private int scanningTime;
  
  public PhaseFaceNearestEnemy(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doLocalUpdate() {
    BlockPos pos = this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
    if (this.dragon.getJukeboxToDanceTo() != null) {
      this.dragon.setPositionAndUpdate(this.dragon.getJukeboxToDanceTo().getX(), this.dragon.getJukeboxToDanceTo().getY() + 12.0D, this.dragon.getJukeboxToDanceTo().getZ());
    } else if (!this.dragon.isWild()) {
      this.dragon.setPositionAndUpdate((this.dragon.getOwner()).posX, (this.dragon.getOwner()).posY + 4.0D, (this.dragon.getOwner()).posZ);
    } else {
      this.dragon.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
    } 
    if (this.dragon.getJukeboxToDanceTo() == null)
      this.scanningTime++; 
    EntityLivingBase entitylivingbase = this.dragon.getAttackTarget();
    if (entitylivingbase != null && (this.dragon.getDistanceSq((Entity)entitylivingbase) < 1024.0D || (!this.dragon.isWild() && !this.dragon.getOwner().getHeldItemMainhand().isEmpty() && this.dragon.getOwner().getHeldItemMainhand().getItem() == Items.GLASS_BOTTLE))) {
      if (this.scanningTime > 30) {
        this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_ATTACKING);
      } else {
        this.dragon.faceEntity((Entity)entitylivingbase, 10.0F, 90.0F);
        this.dragon.renderYawOffset = this.dragon.rotationYaw = this.dragon.rotationYawHead + 180.0F;
      } 
    } else if (this.scanningTime >= 200) {
      entitylivingbase = this.dragon.getAttackTarget();
      this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);
      if (entitylivingbase != null && this.dragon.getDistanceSq((Entity)entitylivingbase) > 1024.0D) {
        this.dragon.getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
        ((PhaseRamAttack)this.dragon.getPhaseManager().getPhase(PhaseList.CHARGING_PLAYER)).setTarget(new Vec3d(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ));
      } 
    } else if (!this.dragon.isWild()) {
      this.dragon.rotationYaw = (this.dragon.getOwner()).rotationYaw - 180.0F;
    } 
  }
  
  public void initPhase() {
    this.scanningTime = 0;
  }
  
  public PhaseList<PhaseFaceNearestEnemy> getPhaseList() {
    return PhaseList.SITTING_SCANNING;
  }
}
