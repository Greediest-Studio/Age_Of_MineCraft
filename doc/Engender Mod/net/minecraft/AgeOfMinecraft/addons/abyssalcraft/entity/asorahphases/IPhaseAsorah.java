package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public interface IPhaseAsorah {
  boolean getIsStationary();
  
  void doClientRenderEffects();
  
  void doLocalUpdate();
  
  void onCrystalDestroyed(EntityDragonMinion paramEntityDragonMinion, BlockPos paramBlockPos, DamageSource paramDamageSource, EntityPlayer paramEntityPlayer);
  
  void initPhase();
  
  void removeAreaEffect();
  
  float getMaxRiseOrFall();
  
  float getYawFactor();
  
  PhaseListAsorah<? extends IPhaseAsorah> getPhaseList();
  
  Vec3d getTargetLocation();
  
  float getAdjustedDamage(MultiPartEntityPart paramMultiPartEntityPart, DamageSource paramDamageSource, float paramFloat);
}
