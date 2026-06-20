package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIFollowLeader extends EntityAIBase {
  private EntityTameBase theCreature;
  
  private EntityLivingBase theOwner;
  
  World theWorld;
  
  private double followSpeed;
  
  private PathNavigate petPathfinder;
  
  private int timeToRecalcPath;
  
  float maxDist;
  
  float minDist;
  
  private float oldWaterCost;
  
  public EntityAIFollowLeader(EntityTameBase p_i1625_1_, double p_i1625_2_, float p_i1625_4_, float p_i1625_5_) {
    this.theCreature = p_i1625_1_;
    this.theWorld = p_i1625_1_.world;
    this.followSpeed = p_i1625_2_;
    this.petPathfinder = p_i1625_1_.getNavigator();
    this.minDist = p_i1625_4_;
    this.maxDist = p_i1625_5_;
    setMutexBits(0);
  }
  
  public boolean shouldExecute() {
    EntityLivingBase entitylivingbase = this.theCreature.getOwner();
    if (entitylivingbase == null)
      return false; 
    if (!this.theCreature.canFollowOwner())
      return false; 
    if (this.theCreature.world.provider != net.minecraft.AgeOfMinecraft.util.EntityCompat.world(entitylivingbase).provider)
      return false; 
    this.theOwner = entitylivingbase;
    double d0 = this.theCreature.getDistance(entitylivingbase);
    return (d0 >= getMinDis() || this.theOwner.isSneaking());
  }
  
  public boolean shouldContinueExecuting() {
    return ((this.theCreature.canFollowOwner() && this.theCreature.getDistance(this.theOwner) >= getMaxDis()) || this.theOwner.isSneaking());
  }
  
  public void startExecuting() {
    this.timeToRecalcPath = 0;
    this.oldWaterCost = this.theCreature.getPathPriority(PathNodeType.WATER);
    this.theCreature.setPathPriority(PathNodeType.WATER, 0.0F);
    this.theCreature.setSitResting(false);
  }
  
  public void resetTask() {
    this.theOwner = null;
    this.petPathfinder.clearPath();
    this.petPathfinder.tryMoveToEntityLiving(this.theCreature, 0.0D);
    this.theCreature.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
  }
  
  private boolean func_181065_a(BlockPos p_181065_1_) {
    IBlockState iblockstate = this.theWorld.getBlockState(p_181065_1_);
    Block block = iblockstate.getBlock();
    return (block == Blocks.AIR);
  }
  
  private double getMaxDis() {
    return this.maxDist;
  }
  
  private double getMinDis() {
    return this.minDist;
  }
  
  public void updateTask() {
    Vec3d vec3 = this.theOwner.getLook(1.0F);
    if (this.theCreature instanceof EntityWither) {
      ((EntityWither)this.theCreature).updateWatchedTargetId(0, this.theOwner.getEntityId());
      if (this.theCreature.posY < Flying.MAX_FLIGHT_TARGET_Y && (this.theCreature.posY < Flying.clampFlightY(this.theOwner.posY) || (!((EntityWither)this.theCreature).isArmored() && this.theCreature.posY < Flying.clampFlightY(this.theOwner.posY + 5.0D + this.theOwner.getEyeHeight()))))
        this.theCreature.motionY += 0.5D - this.theCreature.motionY; 
      this.theCreature.faceEntity(this.theOwner, 180.0F, 40.0F);
      double d0 = this.theOwner.posX - this.theCreature.posX;
      double d2 = this.theOwner.posZ - this.theCreature.posZ;
      double d3 = d0 * d0 + d2 * d2;
      if (d3 > 9.0D) {
        double d5 = MathHelper.sqrt(d3);
        if (this.theCreature.moralRaisedTimer > 200) {
          this.theCreature.motionX += d0 / d5 * 0.75D - this.theCreature.motionX;
          this.theCreature.motionZ += d2 / d5 * 0.75D - this.theCreature.motionZ;
        } else {
          this.theCreature.motionX += d0 / d5 * 0.5D - this.theCreature.motionX;
          this.theCreature.motionZ += d2 / d5 * 0.5D - this.theCreature.motionZ;
        } 
      } 
    } 
    if (this.theCreature instanceof EntityCommandBlockWither) {
      if (this.theCreature.posY < Flying.MAX_FLIGHT_TARGET_Y && (this.theCreature.posY < Flying.clampFlightY(this.theOwner.posY) || (!((EntityCommandBlockWither)this.theCreature).isArmored() && this.theCreature.posY < Flying.clampFlightY(this.theOwner.posY + 5.0D + this.theOwner.getEyeHeight()))))
        this.theCreature.motionY += 0.5D - this.theCreature.motionY; 
      this.theCreature.faceEntity(this.theOwner, 180.0F, 40.0F);
      double d0 = this.theOwner.posX - this.theCreature.posX;
      double d2 = this.theOwner.posZ - this.theCreature.posZ;
      double d3 = d0 * d0 + d2 * d2;
      if (d3 > 9.0D) {
        double d5 = MathHelper.sqrt(d3);
        if (this.theCreature.moralRaisedTimer > 200) {
          this.theCreature.motionX += d0 / d5 * 0.75D - this.theCreature.motionX;
          this.theCreature.motionZ += d2 / d5 * 0.75D - this.theCreature.motionZ;
        } else {
          this.theCreature.motionX += d0 / d5 * 0.5D - this.theCreature.motionX;
          this.theCreature.motionZ += d2 / d5 * 0.5D - this.theCreature.motionZ;
        } 
      } 
    } 
    double d1 = 1.0D + this.theCreature.width + this.theCreature.height;
    if (this.theCreature instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm)
      this.theCreature.getMoveHelper().setMoveTo(this.theOwner.lastTickPosX - vec3.x * d1, this.theOwner.lastTickPosY - this.theCreature.height - 16.0D, this.theOwner.lastTickPosZ - vec3.z * d1, this.followSpeed); 
    if (this.theCreature instanceof EntityGuardian)
      if (this.theCreature.isInWater()) {
        this.theCreature.getMoveHelper().setMoveTo(this.theOwner.lastTickPosX - vec3.x * d1, this.theOwner.lastTickPosY, this.theOwner.lastTickPosZ - vec3.z * d1, this.followSpeed);
      } else if (!EngenderConfig.mobs.useMobTalkerModels) {
        double d01 = this.theOwner.posX - this.theCreature.posX;
        double d11 = this.theOwner.posZ - this.theCreature.posZ;
        float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11);
        this.theCreature.motionX = d01 / f2 * 0.5D * 0.5D + this.theCreature.motionX;
        this.theCreature.motionZ = d11 / f2 * 0.5D * 0.5D + this.theCreature.motionZ;
        this.theCreature.faceEntity(this.theOwner, 180.0F, 30.0F);
        if (this.theCreature.onGround) {
          this.theCreature.motionY += 0.6D;
          this.theCreature.playSound(SoundEvents.ENTITY_GUARDIAN_FLOP, 1.0F, 1.0F);
        } 
      }  
    if (!this.theCreature.isSneaking()) {
      this.theCreature.getLookHelper().setLookPositionWithEntity(this.theOwner, 180.0F, this.theCreature.getVerticalFaceSpeed());
      this.theCreature.setAttackTarget(null);
    } 
    if (--this.timeToRecalcPath <= 0 || this.theCreature.isSneaking()) {
      this.timeToRecalcPath = 20;
      if (this.theCreature.isSneaking())
        this.theCreature.getLookHelper().setLookPosition(this.theOwner.posX + vec3.x * 16.0D, this.theOwner.posY + this.theOwner.getEyeHeight() + vec3.y * 4.0D, this.theOwner.posZ + vec3.z * 16.0D, 180.0F, 180.0F); 
      if (this.theCreature instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex)
        this.theCreature.getMoveHelper().setMoveTo(this.theOwner.lastTickPosX - vec3.x * d1, this.theOwner.lastTickPosY, this.theOwner.lastTickPosZ - vec3.z * d1, this.followSpeed); 
      if (this.theCreature instanceof EntitySlime && !EngenderConfig.mobs.useMobTalkerModels) {
        this.theCreature.faceEntity(this.theOwner, 10.0F, 10.0F);
        ((EntitySlime.SlimeMoveHelper)this.theCreature.getMoveHelper()).setDirection(this.theCreature.rotationYawHead, true);
      } 
      if (!this.petPathfinder.tryMoveToXYZ(this.theOwner.lastTickPosX - vec3.x * d1, this.theOwner.lastTickPosY, this.theOwner.lastTickPosZ - vec3.z * d1, this.followSpeed) || this.theCreature.isInWater() || this.theCreature.isInLava())
        if (this.theCreature.getDistance(this.theOwner) >= getMinDis() + 4.0D && !this.theCreature.getLeashed()) {
          int i = MathHelper.floor(this.theOwner.posX);
          int j = MathHelper.floor(this.theOwner.posZ);
          int k = MathHelper.floor(this.theOwner.world.getTopSolidOrLiquidBlock(this.theOwner.getPosition()).getY());
          for (int l = 0; l <= 4; l++) {
            for (int i1 = 0; i1 <= 4; i1++) {
              if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && (this.theWorld.getBlockState(new BlockPos(i + l, k - 1, j + i1)).getBlock() != Blocks.AIR || this.theWorld.getBlockState(new BlockPos(i + l, k - 1, j + i1)).getBlock() == Blocks.WATER || this.theCreature instanceof net.minecraft.AgeOfMinecraft.entity.tame.Flying || this.theOwner.isRiding()) && this.theWorld.getBlockState(new BlockPos(i + l, k - 1, j + i1)).getBlock() != Blocks.LAVA && this.theWorld.getBlockState(new BlockPos((i + l), k + (this.theCreature.getEntityBoundingBox()).maxY, (j + i1))).getBlock() == Blocks.AIR && func_181065_a(new BlockPos(i + l, k, j + i1)) && func_181065_a(new BlockPos(i + l, k + 1, j + i1))) {
                this.theCreature.fallDistance *= 0.0F;
                if (this.theCreature.isRiding()) {
                  this.theCreature.getRidingEntity().setLocationAndAngles(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.theCreature.rotationYaw, this.theCreature.rotationPitch);
                  if (this.theCreature.getRidingEntity().isRiding())
                    this.theCreature.getRidingEntity().dismountRidingEntity(); 
                } 
                this.theCreature.setLocationAndAngles(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.theCreature.rotationYaw, this.theCreature.rotationPitch);
                this.petPathfinder.clearPath();
                return;
              } 
            } 
          } 
        }  
    } 
  }
}
