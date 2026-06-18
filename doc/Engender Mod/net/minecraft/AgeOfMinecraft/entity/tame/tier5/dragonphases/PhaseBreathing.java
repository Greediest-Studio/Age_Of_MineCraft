package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.gen.feature.WorldGenEndPodium;

public class PhaseBreathing extends PhaseBaseFriendly {
  private int field_188664_b;
  
  private int field_188665_c;
  
  private EntityAreaEffectCloudOther areaEffectCloud;
  
  public PhaseBreathing(EntityEnderDragon dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doClientRenderEffects() {
    this.field_188664_b++;
    if (this.field_188664_b < 30) {
      Vec3d vec3d = this.dragon.getHeadLookVec(1.0F).func_72432_b();
      vec3d.func_178785_b(-0.7853982F);
      double d0 = this.dragon.dragonPartHead.field_70165_t;
      double d1 = this.dragon.dragonPartHead.field_70163_u + (this.dragon.dragonPartHead.field_70131_O / 2.0F);
      double d2 = this.dragon.dragonPartHead.field_70161_v;
      for (int i = 0; i < 8; i++) {
        double d3 = d0 + this.dragon.func_70681_au().nextGaussian() / 2.0D;
        double d4 = d1 + this.dragon.func_70681_au().nextGaussian() / 2.0D;
        double d5 = d2 + this.dragon.func_70681_au().nextGaussian() / 2.0D;
        for (int j = 0; j < 4; j++)
          this.dragon.field_70170_p.func_175688_a(EnumParticleTypes.DRAGON_BREATH, d3, d4, d5, -vec3d.field_72450_a * 0.1D * j, -vec3d.field_72448_b * 0.75D, -vec3d.field_72449_c * 0.1D * j, new int[0]); 
        vec3d.func_178785_b(0.19634955F);
      } 
    } 
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
    this.field_188664_b++;
    if (this.field_188664_b < 30)
      this.dragon.func_184185_a(SoundEvents.field_187527_aQ, 5.0F, 0.5F); 
    if (this.field_188664_b >= 200) {
      if (this.field_188665_c >= 4) {
        this.dragon.getPhaseManager().setPhase(PhaseList.TAKEOFF);
      } else {
        this.dragon.getPhaseManager().setPhase(PhaseList.SITTING_SCANNING);
      } 
    } else if (this.field_188664_b == 20) {
      Vec3d vec3d = (new Vec3d(this.dragon.dragonPartHead.field_70165_t - this.dragon.field_70165_t, 0.0D, this.dragon.dragonPartHead.field_70161_v - this.dragon.field_70161_v)).func_72432_b();
      float f = 5.0F;
      double d0 = this.dragon.dragonPartHead.field_70165_t + vec3d.field_72450_a * f / 2.0D;
      double d1 = this.dragon.dragonPartHead.field_70161_v + vec3d.field_72449_c * f / 2.0D;
      double d2 = this.dragon.dragonPartHead.field_70163_u + (this.dragon.dragonPartHead.field_70131_O / 2.0F);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.func_76128_c(d0), MathHelper.func_76128_c(d2), MathHelper.func_76128_c(d1));
      while (this.dragon.field_70170_p.func_175623_d((BlockPos)blockpos$mutableblockpos)) {
        d2--;
        blockpos$mutableblockpos.func_181079_c(MathHelper.func_76128_c(d0), MathHelper.func_76128_c(d2), MathHelper.func_76128_c(d1));
      } 
      d2 = (MathHelper.func_76128_c(d2) + 1);
      this.areaEffectCloud = new EntityAreaEffectCloudOther(this.dragon.field_70170_p, d0, d2, d1);
      this.areaEffectCloud.setOwner((EntityTameBase)this.dragon);
      this.areaEffectCloud.setRadius(1.0F);
      this.areaEffectCloud.setDuration(80);
      this.areaEffectCloud.setRadiusPerTick(f / this.areaEffectCloud.getDuration());
      this.areaEffectCloud.setParticle(EnumParticleTypes.DRAGON_BREATH);
      this.areaEffectCloud.addEffect(new PotionEffect(MobEffects.field_76433_i));
      this.dragon.field_70170_p.func_72838_d(this.areaEffectCloud);
    } 
  }
  
  public void initPhase() {
    this.field_188664_b = 0;
    this.field_188665_c++;
  }
  
  public void removeAreaEffect() {
    if (this.areaEffectCloud != null) {
      this.areaEffectCloud.func_70106_y();
      this.areaEffectCloud = null;
    } 
  }
  
  public PhaseList<PhaseBreathing> getPhaseList() {
    return PhaseList.SITTING_FLAMING;
  }
  
  public void func_188663_j() {
    this.field_188665_c = 0;
  }
}
