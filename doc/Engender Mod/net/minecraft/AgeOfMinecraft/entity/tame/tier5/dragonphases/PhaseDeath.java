package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseDeath extends PhaseBaseFriendly {
  private Vec3d field_188672_b;
  
  private int field_188673_c;
  
  public PhaseDeath(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public void doClientRenderEffects() {
    if (this.field_188673_c++ % 10 == 0) {
      float f = (this.dragon.func_70681_au().nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.dragon.func_70681_au().nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.dragon.func_70681_au().nextFloat() - 0.5F) * 8.0F;
      this.dragon.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.dragon.field_70165_t + f, this.dragon.field_70163_u + 2.0D + f1, this.dragon.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
  }
  
  public void doLocalUpdate() {
    this.field_188673_c++;
    if (this.field_188672_b == null) {
      BlockPos blockpos = this.dragon.field_70170_p.func_175645_m(WorldGenEndPodium.field_186139_a);
      if (this.dragon.getOwner() != null)
        blockpos = new BlockPos((Entity)this.dragon.getOwner()); 
      this.field_188672_b = new Vec3d(blockpos.func_177958_n(), blockpos.func_177956_o(), blockpos.func_177952_p());
    } 
    double d0 = this.field_188672_b.func_186679_c(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v);
    if (d0 >= 100.0D && d0 <= 25500.0D) {
      this.dragon.func_70606_j(1.0F);
    } else {
      this.dragon.func_70606_j(0.0F);
    } 
  }
  
  public void initPhase() {
    this.field_188672_b = null;
    this.field_188673_c = 0;
  }
  
  public float getMaxRiseOrFall() {
    return 3.0F;
  }
  
  public Vec3d getTargetLocation() {
    return this.field_188672_b;
  }
  
  public PhaseList<PhaseDeath> getPhaseList() {
    return PhaseList.DYING;
  }
}
