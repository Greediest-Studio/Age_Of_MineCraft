package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;

public class PhasePreBreathing extends PhaseBaseFriendly {
  private int attackingTicks;
  
  public PhasePreBreathing(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doClientRenderEffects() {
    this.dragon.world.playSound(this.dragon.posX, this.dragon.posY, this.dragon.posZ, SoundEvents.ENTITY_ENDERDRAGON_GROWL, this.dragon.getSoundCategory(), 2.5F, 0.8F + this.dragon.getRNG().nextFloat() * 0.3F, false);
  }
  
  public void doLocalUpdate() {
    if (this.dragon.getOwner() != null)
      this.dragon.setPositionAndUpdate((this.dragon.getOwner()).posX, (this.dragon.getOwner()).posY + 4.0D, (this.dragon.getOwner()).posZ); 
    if (this.attackingTicks++ >= 40)
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.SITTING_FLAMING); 
    if (this.dragon.getAttackTarget() != null)
      this.dragon.faceEntity(this.dragon.getAttackTarget(), 10.0F, 20.0F);
  }
  
  public void initPhase() {
    this.attackingTicks = 0;
  }
  
  public PhaseListAsorah<PhasePreBreathing> getPhaseList() {
    return PhaseListAsorah.SITTING_ATTACKING;
  }
}
