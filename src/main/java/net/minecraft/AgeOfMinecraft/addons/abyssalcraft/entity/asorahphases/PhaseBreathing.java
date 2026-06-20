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
  private int flameTicks;
  
  private int flameCount;
  
  private EntityAreaEffectCloudOther areaEffectCloud;
  
  public PhaseBreathing(EntityDragonBoss dragonIn) {
    super(dragonIn);
  }
  
  public boolean getIsStationary() {
    return true;
  }
  
  public void doClientRenderEffects() {
    this.flameTicks++;
    if (this.flameTicks % 2 == 0 && this.flameTicks < 30) {
      Vec3d vec3d = this.dragon.getHeadLookVec(1.0F).normalize();
      vec3d.rotateYaw(-0.7853982F);
      double d0 = this.dragon.dragonPartHead.posX;
      double d1 = this.dragon.dragonPartHead.posY + (this.dragon.dragonPartHead.height / 2.0F);
      double d2 = this.dragon.dragonPartHead.posZ;
      for (int i = 0; i < 8; i++) {
        double d3 = d0 + this.dragon.getRNG().nextGaussian() / 2.0D;
        double d4 = d1 + this.dragon.getRNG().nextGaussian() / 2.0D;
        double d5 = d2 + this.dragon.getRNG().nextGaussian() / 2.0D;
        for (int j = 0; j < 4; j++)
          this.dragon.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d3, d4, d5, -vec3d.x * 0.1D * j, -vec3d.y * 0.75D, -vec3d.z * 0.1D * j);
        vec3d.rotateYaw(0.19634955F);
      } 
    } 
  }
  
  public void doLocalUpdate() {
    if (this.dragon.getAttackTarget() != null)
      this.dragon.faceEntity(this.dragon.getAttackTarget(), 10.0F, 20.0F);
    if (this.dragon.getOwner() != null)
      this.dragon.setPositionAndUpdate((this.dragon.getOwner()).posX, (this.dragon.getOwner()).posY + 4.0D, (this.dragon.getOwner()).posZ); 
    this.flameTicks++;
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.dragon.world) && this.dragon.getAttackTarget() != null && this.dragon.getAttackTarget().isEntityAlive() && this.dragon.getAttackTarget().canEntityBeSeen(this.dragon) && this.dragon.getRNG().nextInt(100) == 0) {
      double d6 = this.dragon.dragonPartHead.posX;
      double d7 = this.dragon.dragonPartHead.posY + 2.0D;
      double d8 = this.dragon.dragonPartHead.posZ;
      double d9 = (this.dragon.getAttackTarget()).posX - d6;
      double d10 = (this.dragon.getAttackTarget()).posY + 1.0D - d7;
      double d11 = (this.dragon.getAttackTarget()).posZ - d8;
      this.dragon.world.playEvent(null, 1016, new BlockPos(this.dragon), 0);
      EntityCoraliumChargeOther entitydragonfireball = new EntityCoraliumChargeOther(this.dragon.world, this.dragon, d9, d10, d11);
      entitydragonfireball.posX = d6;
      entitydragonfireball.posY = d7;
      entitydragonfireball.posZ = d8;
      this.dragon.world.spawnEntity(entitydragonfireball);
    } 
    if (this.flameTicks >= 200)
      if (this.flameCount >= 4) {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.HOLDING_PATTERN);
      } else {
        this.dragon.getPhaseManager().setPhase(PhaseListAsorah.SITTING_SCANNING);
      }  
  }
  
  public void initPhase() {
    this.flameTicks = 0;
    this.flameCount++;
  }
  
  public void removeAreaEffect() {
    if (this.areaEffectCloud != null) {
      this.areaEffectCloud.setDead();
      this.areaEffectCloud = null;
    } 
  }
  
  public PhaseListAsorah<PhaseBreathing> getPhaseList() {
    return PhaseListAsorah.SITTING_FLAMING;
  }
  
  public void resetFlameCount() {
    this.flameCount = 0;
  }
}
