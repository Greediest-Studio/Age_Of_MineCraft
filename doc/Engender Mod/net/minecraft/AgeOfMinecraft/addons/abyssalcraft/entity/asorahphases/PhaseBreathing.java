package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumChargeOther;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PhaseBreathing extends PhaseBaseFriendly {
  private int field_188664_b;
  
  private int field_188665_c;
  
  private EntityAreaEffectCloudOther areaEffectCloud;
  
  public PhaseBreathing(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doClientRenderEffects() {
    this.field_188664_b++;
    if (this.field_188664_b % 2 == 0 && this.field_188664_b < 30) {
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
          this.dragon.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, d3, d4, d5, -vec3d.field_72450_a * 0.1D * j, -vec3d.field_72448_b * 0.75D, -vec3d.field_72449_c * 0.1D * j, new int[0]); 
        vec3d.func_178785_b(0.19634955F);
      } 
    } 
  }
  
  public void doLocalUpdate() {
    if (this.dragon.func_70638_az() != null)
      this.dragon.func_70625_a((Entity)this.dragon.func_70638_az(), 10.0F, 20.0F); 
    if (this.dragon.getOwner() != null)
      this.dragon.func_70634_a((this.dragon.getOwner()).field_70165_t, (this.dragon.getOwner()).field_70163_u + 4.0D, (this.dragon.getOwner()).field_70161_v); 
    this.field_188664_b++;
    if (!this.dragon.field_70170_p.field_72995_K && this.dragon.func_70638_az() != null && this.dragon.func_70638_az().func_70089_S() && this.dragon.func_70638_az().func_70685_l((Entity)this.dragon) && this.dragon.func_70681_au().nextInt(100) == 0) {
      double d6 = this.dragon.dragonPartHead.field_70165_t;
      double d7 = this.dragon.dragonPartHead.field_70163_u + 2.0D;
      double d8 = this.dragon.dragonPartHead.field_70161_v;
      double d9 = (this.dragon.func_70638_az()).field_70165_t - d6;
      double d10 = (this.dragon.func_70638_az()).field_70163_u + 1.0D - d7;
      double d11 = (this.dragon.func_70638_az()).field_70161_v - d8;
      this.dragon.field_70170_p.func_180498_a((EntityPlayer)null, 1016, new BlockPos((Entity)this.dragon), 0);
      EntityCoraliumChargeOther entitydragonfireball = new EntityCoraliumChargeOther(this.dragon.field_70170_p, (EntityLivingBase)this.dragon, d9, d10, d11);
      entitydragonfireball.field_70165_t = d6;
      entitydragonfireball.field_70163_u = d7;
      entitydragonfireball.field_70161_v = d8;
      this.dragon.field_70170_p.func_72838_d((Entity)entitydragonfireball);
    } 
    if (this.field_188664_b >= 200)
      if (this.field_188665_c >= 4) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
      } else {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.SITTING_SCANNING);
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
  
  public PhaseListAsorah<PhaseBreathing> getPhaseList() {
    return PhaseListAsorah.SITTING_FLAMING;
  }
  
  public void func_188663_j() {
    this.field_188665_c = 0;
  }
}
