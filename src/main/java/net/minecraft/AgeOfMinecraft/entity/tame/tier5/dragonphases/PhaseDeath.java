package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseDeath extends PhaseBaseFriendly {
  private Vec3d targetLocation;
  
  private int time;
  
  public PhaseDeath(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public void doClientRenderEffects() {
    if (this.time++ % 10 == 0) {
      float f = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.dragon.getRNG().nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.dragon.getRNG().nextFloat() - 0.5F) * 8.0F;
      this.dragon.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.dragon.posX + f, this.dragon.posY + 2.0D + f1, this.dragon.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
  }
  
  public void doLocalUpdate() {
    this.time++;
    if (this.targetLocation == null) {
      BlockPos blockpos = this.dragon.world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION);
      if (this.dragon.getOwner() != null)
        blockpos = new BlockPos((Entity)this.dragon.getOwner()); 
      this.targetLocation = new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ());
    } 
    double d0 = this.targetLocation.squareDistanceTo(this.dragon.posX, this.dragon.posY, this.dragon.posZ);
    if (d0 >= 100.0D && d0 <= 25500.0D) {
      this.dragon.setHealth(1.0F);
    } else {
      this.dragon.setHealth(0.0F);
    } 
  }
  
  public void initPhase() {
    this.targetLocation = null;
    this.time = 0;
  }
  
  public float getMaxRiseOrFall() {
    return 3.0F;
  }
  
  public Vec3d getTargetLocation() {
    return this.targetLocation;
  }
  
  public PhaseList<PhaseDeath> getPhaseList() {
    return PhaseList.DYING;
  }
}
