package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseHoveringOverOwner extends PhaseBaseFriendly {
  private Vec3d field_188685_b;
  
  public PhaseHoveringOverOwner(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public float getAdjustedDamage(MultiPartEntityPart pt, DamageSource src, float damage) {
    if (src.func_76346_g() instanceof net.minecraft.entity.projectile.EntityArrow) {
      src.func_76346_g().func_70015_d(1);
      return 0.0F;
    } 
    return super.getAdjustedDamage(pt, src, damage);
  }
  
  public void doClientRenderEffects() {
    Vec3d vec3d = this.dragon.getHeadLookVec(1.0F).func_72432_b();
    vec3d.func_178785_b(-0.7853982F);
    double d0 = this.dragon.dragonPartHead.field_70165_t;
    double d1 = this.dragon.dragonPartHead.field_70163_u + (this.dragon.dragonPartHead.field_70131_O / 2.0F);
    double d2 = this.dragon.dragonPartHead.field_70161_v;
    for (int i = 0; i < 8; i++) {
      double d3 = d0 + this.dragon.func_70681_au().nextGaussian() / 2.0D;
      double d4 = d1 + this.dragon.func_70681_au().nextGaussian() / 2.0D;
      double d5 = d2 + this.dragon.func_70681_au().nextGaussian() / 2.0D;
      this.dragon.field_70170_p.func_175688_a(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.field_72450_a * 0.07999999821186066D + this.dragon.field_70159_w, -vec3d.field_72448_b * 0.30000001192092896D + this.dragon.field_70181_x, -vec3d.field_72449_c * 0.07999999821186066D + this.dragon.field_70179_y, new int[0]);
      vec3d.func_178785_b(0.19634955F);
    } 
  }
  
  public void doLocalUpdate() {
    if (this.field_188685_b == null)
      if (this.dragon.getOwner() != null) {
        this.field_188685_b = new Vec3d((Vec3i)new BlockPos((this.dragon.getOwner()).field_70165_t, (this.dragon.getOwner()).field_70163_u + 4.0D, (this.dragon.getOwner()).field_70161_v));
      } else {
        this.field_188685_b = new Vec3d((Vec3i)this.dragon.field_70170_p.func_175672_r(WorldGenEndPodium.field_186139_a));
      }  
    if (this.field_188685_b.func_186679_c(this.dragon.field_70165_t, this.dragon.field_70163_u, this.dragon.field_70161_v) < 9.0D) {
      ((PhaseBreathing)this.dragon.getPhaseManager().<PhaseBreathing>getPhase(PhaseList.SITTING_FLAMING)).func_188663_j();
      this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
    } 
  }
  
  public float getMaxRiseOrFall() {
    return 1.5F;
  }
  
  public float getYawFactor() {
    float f = MathHelper.func_76133_a(this.dragon.field_70159_w * this.dragon.field_70159_w + this.dragon.field_70179_y * this.dragon.field_70179_y) + 1.0F;
    float f1 = Math.min(f, 40.0F);
    return f1 / f;
  }
  
  public void initPhase() {
    this.field_188685_b = null;
  }
  
  public Vec3d getTargetLocation() {
    return this.field_188685_b;
  }
  
  public PhaseList<PhaseHoveringOverOwner> getPhaseList() {
    return PhaseList.LANDING;
  }
}
