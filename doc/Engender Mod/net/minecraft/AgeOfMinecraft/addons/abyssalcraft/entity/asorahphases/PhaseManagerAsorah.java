package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;

public class PhaseManagerAsorah {
  private final EntityDragonBoss dragon;
  
  private final IPhaseAsorah[] phases = new IPhaseAsorah[PhaseListAsorah.getTotalPhases()];
  
  private IPhaseAsorah phase;
  
  public PhaseManagerAsorah(EntityDragonBoss dragonIn) {
    this.dragon = dragonIn;
    setPhase(PhaseListAsorah.HOVER);
  }
  
  public void setPhase(PhaseListAsorah<?> phaseIn) {
    if (this.phase == null || phaseIn != this.phase.getPhaseList()) {
      if (this.phase != null)
        this.phase.removeAreaEffect(); 
      this.phase = getPhase(phaseIn);
      if (!this.dragon.field_70170_p.field_72995_K)
        this.dragon.func_184212_Q().func_187227_b(EntityDragonBoss.PHASE, Integer.valueOf(phaseIn.getId())); 
      this.phase.initPhase();
    } 
  }
  
  public IPhaseAsorah getCurrentPhase() {
    return this.phase;
  }
  
  public <T extends IPhaseAsorah> T getPhase(PhaseListAsorah<T> phaseIn) {
    int i = phaseIn.getId();
    if (this.phases[i] == null)
      this.phases[i] = phaseIn.createPhase(this.dragon); 
    return (T)this.phases[i];
  }
}
