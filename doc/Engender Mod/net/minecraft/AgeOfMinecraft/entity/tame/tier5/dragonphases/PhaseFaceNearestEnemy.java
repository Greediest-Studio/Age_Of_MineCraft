package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseFaceNearestEnemy extends PhaseBaseFriendly {
  private int field_188667_b;
  
  public PhaseFaceNearestEnemy(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doLocalUpdate() {
    BlockPos pos = this.dragon.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a);
    if (this.dragon.getJukeboxToDanceTo() != null) {
      this.dragon.func_70634_a(this.dragon.getJukeboxToDanceTo().func_177958_n(), this.dragon.getJukeboxToDanceTo().func_177956_o() + 12.0D, this.dragon.getJukeboxToDanceTo().func_177952_p());
    } else if (!this.dragon.isWild()) {
      this.dragon.func_70634_a((this.dragon.getOwner()).field_70165_t, (this.dragon.getOwner()).field_70163_u + 4.0D, (this.dragon.getOwner()).field_70161_v);
    } else {
      this.dragon.func_70634_a(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
    } 
    if (this.dragon.getJukeboxToDanceTo() == null)
      this.field_188667_b++; 
    EntityLivingBase entitylivingbase = this.dragon.func_70638_az();
    if (entitylivingbase != null && (this.dragon.func_70068_e((Entity)entitylivingbase) < 1024.0D || (!this.dragon.isWild() && !this.dragon.getOwner().func_184614_ca().func_190926_b() && this.dragon.getOwner().func_184614_ca().func_77973_b() == Items.field_151069_bo))) {
      if (this.field_188667_b > 30) {
        this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_ATTACKING);
      } else {
        this.dragon.func_70625_a((Entity)entitylivingbase, 10.0F, 90.0F);
        this.dragon.field_70761_aq = this.dragon.field_70177_z = this.dragon.field_70759_as + 180.0F;
      } 
    } else if (this.field_188667_b >= 200) {
      entitylivingbase = this.dragon.func_70638_az();
      this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);
      if (entitylivingbase != null && this.dragon.func_70068_e((Entity)entitylivingbase) > 1024.0D) {
        this.dragon.getPhaseManager().setPhase(PhaseList.CHARGING_PLAYER);
        ((PhaseRamAttack)this.dragon.getPhaseManager().<PhaseRamAttack>getPhase(PhaseList.CHARGING_PLAYER)).func_188668_a(new Vec3d(entitylivingbase.field_70165_t, entitylivingbase.field_70163_u, entitylivingbase.field_70161_v));
      } 
    } else if (!this.dragon.isWild()) {
      this.dragon.field_70177_z = (this.dragon.getOwner()).field_70177_z - 180.0F;
    } 
  }
  
  public void initPhase() {
    this.field_188667_b = 0;
  }
  
  public PhaseList<PhaseFaceNearestEnemy> getPhaseList() {
    return PhaseList.SITTING_SCANNING;
  }
}
