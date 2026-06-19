package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhasePreBreathing extends PhaseBaseFriendly {
  private int attackingTicks;
  
  public PhasePreBreathing(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doClientRenderEffects() {
    this.dragon.world.playSound(this.dragon.posX, this.dragon.posY, this.dragon.posZ, SoundEvents.ENTITY_ENDERDRAGON_GROWL, this.dragon.getSoundCategory(), 2.5F, 0.8F + this.dragon.getRNG().nextFloat() * 0.3F, false);
  }
  
  public void doLocalUpdate() {
    BlockPos pos = this.dragon.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
    if (this.dragon.getJukeboxToDanceTo() != null) {
      this.dragon.setPositionAndUpdate(this.dragon.getJukeboxToDanceTo().getX(), this.dragon.getJukeboxToDanceTo().getY() + 16.0D, this.dragon.getJukeboxToDanceTo().getZ());
    } else if (!this.dragon.isWild()) {
      this.dragon.setPositionAndUpdate((this.dragon.getOwner()).posX, (this.dragon.getOwner()).posY + 4.0D, (this.dragon.getOwner()).posZ);
    } else {
      this.dragon.setPositionAndUpdate(pos.getX(), pos.getY(), pos.getZ());
    } 
    if (this.attackingTicks++ >= 40)
      this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_FLAMING); 
    if (this.dragon.getAttackTarget() != null)
      this.dragon.faceEntity(this.dragon.getAttackTarget(), 10.0F, 20.0F);
  }
  
  public void initPhase() {
    this.attackingTicks = 0;
  }
  
  public PhaseList<PhasePreBreathing> getPhaseList() {
    return PhaseList.SITTING_ATTACKING;
  }
}
