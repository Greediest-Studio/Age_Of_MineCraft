package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import java.util.List;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIMobGirlMate extends EntityAIBase {
  private final EntityTameBase theAnimal;
  
  World world;
  
  int spawnBabyDelay;
  
  double moveSpeed;
  
  public EntityAIMobGirlMate(EntityTameBase animal, double speedIn) {
    this.theAnimal = animal;
    this.world = animal.world;
    this.moveSpeed = speedIn;
    setMutexBits(3);
  }
  
  public boolean shouldExecute() {
    if (!this.theAnimal.isInLove())
      return false; 
    if (this.theAnimal.isWild())
      return false; 
    if (!EngenderConfig.mobs.useMobTalkerModels)
      return false; 
    if (!EngenderConfig.mobs.breeding)
      return false; 
    return (this.theAnimal.canBeMatedWith() && !this.theAnimal.isChild() && this.theAnimal.getOwner() != null);
  }
  
  public boolean shouldContinueExecuting() {
    return (this.theAnimal.getOwner().isEntityAlive() && this.theAnimal.isInLove() && this.spawnBabyDelay < 200);
  }
  
  public void resetTask() {
    this.spawnBabyDelay = 0;
  }
  
  public void updateTask() {
    if (this.theAnimal.getDistanceSq(this.theAnimal.getOwner()) < 2.0D) {
      if ((this.theAnimal instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || this.theAnimal instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast || this.theAnimal instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityMagmaCube) && !this.theAnimal.getOwner().isPotionActive(MobEffects.FIRE_RESISTANCE))
        this.theAnimal.getOwner().setFire(10); 
      if (this.spawnBabyDelay % ((this.spawnBabyDelay >= 100) ? 5 : 10) == 0) {
        this.theAnimal.hurtResistantTime = 0;
        this.theAnimal.attackEntityFrom(new DamageSource("outOfWorld"), 0.01F);
      } 
      Vec3d vec3 = this.theAnimal.getOwner().getLook(1.0F);
      this.theAnimal.setPosition((this.theAnimal.getOwner()).posX + vec3.x * 0.5D, (this.theAnimal.getOwner()).posY, (this.theAnimal.getOwner()).posZ + vec3.z * 0.5D);
      this.theAnimal.prevRenderYawOffset = this.theAnimal.renderYawOffset = this.theAnimal.prevRotationYaw = this.theAnimal.rotationYaw = this.theAnimal.prevRotationYawHead = this.theAnimal.rotationYawHead = (this.theAnimal.getOwner()).rotationYaw;
      this.theAnimal.prevRotationPitch = this.theAnimal.rotationPitch = (this.theAnimal.getOwner()).rotationPitch;
      this.spawnBabyDelay++;
      if (this.spawnBabyDelay >= 200)
        spawnBaby(); 
    } else {
      this.theAnimal.getLookHelper().setLookPositionWithEntity(this.theAnimal.getOwner(), 10.0F, this.theAnimal.getVerticalFaceSpeed());
      this.theAnimal.getNavigator().tryMoveToEntityLiving(this.theAnimal.getOwner(), this.moveSpeed);
      this.theAnimal.getMoveHelper().setMoveTo((this.theAnimal.getOwner()).posX, (this.theAnimal.getOwner()).posY, (this.theAnimal.getOwner()).posZ, this.moveSpeed);
    } 
  }
  
  private EntityPlayer getNearbyMate() {
    List<EntityPlayer> list = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.theAnimal.getEntityBoundingBox().grow(8.0D));
    double d0 = Double.MAX_VALUE;
    EntityPlayer entityanimal = null;
    for (EntityPlayer entityanimal1 : list) {
      if (this.theAnimal.canBeMatedWith() && this.theAnimal.getDistanceSq(entityanimal1) < d0) {
        entityanimal = entityanimal1;
        d0 = this.theAnimal.getDistanceSq(entityanimal1);
      } 
    } 
    return entityanimal;
  }
  
  private void spawnBaby() {
    if (this.theAnimal.getOwner() != null) {
      EntityPlayer entityplayer = (EntityPlayer)this.theAnimal.getOwner();
      if (entityplayer == null)
        entityplayer = (EntityPlayer)this.theAnimal.getOwner(); 
      this.theAnimal.setGrowingAge(0);
      this.theAnimal.resetInLove();
      this.theAnimal.createChild();
      this.theAnimal.setSitResting(false);
      Random random = this.theAnimal.getRNG();
      for (int i = 0; i < 100; i++) {
        double d0 = random.nextGaussian() * 0.02D;
        double d1 = random.nextGaussian() * 0.02D;
        double d2 = random.nextGaussian() * 0.02D;
        double d3 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
        double d4 = 0.5D + random.nextDouble() * this.theAnimal.height;
        double d5 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
        this.world.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + d3, this.theAnimal.posY + d4, this.theAnimal.posZ + d5, d0, d1, d2);
      } 
      if (this.world.getGameRules().getBoolean("doMobLoot"))
        this.world.spawnEntity(new EntityXPOrb(this.world, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, random.nextInt(15) + 5 + (int)this.theAnimal.height + (int)this.theAnimal.width + (int)this.theAnimal.getEyeHeight()));
    } 
  }
}
