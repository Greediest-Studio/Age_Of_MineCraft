package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface IPhase {
  boolean getIsStationary();
  
  void doClientRenderEffects();
  
  void doLocalUpdate();
  
  void onCrystalDestroyed(EntityEnderCrystal paramEntityEnderCrystal, BlockPos paramBlockPos, DamageSource paramDamageSource, EntityPlayer paramEntityPlayer);
  
  void initPhase();
  
  void removeAreaEffect();
  
  float getMaxRiseOrFall();
  
  float getYawFactor();
  
  PhaseList<? extends IPhase> getPhaseList();
  
  Vec3d getTargetLocation();
  
  float getAdjustedDamage(MultiPartEntityPart paramMultiPartEntityPart, DamageSource paramDamageSource, float paramFloat);
}
