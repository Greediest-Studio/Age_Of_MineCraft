package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;

public class PhasePreBreathing extends PhaseBaseFriendly {
  private int field_188662_b;
  
  public PhasePreBreathing(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doClientRenderEffects() {
    this.dragon.field_70170_p.func_184134_a(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v, SoundEvents.field_187525_aO, this.dragon.func_184176_by(), 2.5F, 0.8F + this.dragon.func_70681_au().nextFloat() * 0.3F, false);
  }
  
  public void doLocalUpdate() {
    if (this.dragon.getOwner() != null)
      this.dragon.func_70634_a((this.dragon.getOwner()).field_70165_t, (this.dragon.getOwner()).field_70163_u + 4.0D, (this.dragon.getOwner()).field_70161_v); 
    if (this.field_188662_b++ >= 40)
      this.dragon.getPhaseManager().setPhase(PhaseListAsorah.SITTING_FLAMING); 
    if (this.dragon.func_70638_az() != null)
      this.dragon.func_70625_a((Entity)this.dragon.func_70638_az(), 10.0F, 20.0F); 
  }
  
  public void initPhase() {
    this.field_188662_b = 0;
  }
  
  public PhaseListAsorah<PhasePreBreathing> getPhaseList() {
    return PhaseListAsorah.SITTING_ATTACKING;
  }
}
