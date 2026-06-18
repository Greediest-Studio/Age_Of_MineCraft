package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhasePreBreathing extends PhaseBaseFriendly {
  private int field_188662_b;
  
  public PhasePreBreathing(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doClientRenderEffects() {
    this.dragon.field_70170_p.func_184134_a(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v, SoundEvents.field_187525_aO, this.dragon.func_184176_by(), 2.5F, 0.8F + this.dragon.func_70681_au().nextFloat() * 0.3F, false);
  }
  
  public void doLocalUpdate() {
    BlockPos pos = this.dragon.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
    if (this.dragon.getJukeboxToDanceTo() != null) {
      this.dragon.func_70634_a(this.dragon.getJukeboxToDanceTo().func_177958_n(), this.dragon.getJukeboxToDanceTo().func_177956_o() + 16.0D, this.dragon.getJukeboxToDanceTo().func_177952_p());
    } else if (!this.dragon.isWild()) {
      this.dragon.func_70634_a((this.dragon.getOwner()).field_70165_t, (this.dragon.getOwner()).field_70163_u + 4.0D, (this.dragon.getOwner()).field_70161_v);
    } else {
      this.dragon.func_70634_a(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
    } 
    if (this.field_188662_b++ >= 40)
      this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_FLAMING); 
    if (this.dragon.func_70638_az() != null)
      this.dragon.func_70625_a((Entity)this.dragon.func_70638_az(), 10.0F, 20.0F); 
  }
  
  public void initPhase() {
    this.field_188662_b = 0;
  }
  
  public PhaseList<PhasePreBreathing> getPhaseList() {
    return PhaseList.SITTING_ATTACKING;
  }
}
