package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICustomLeapAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityOcelot extends EntityTameBase implements Light, Animal {
  public EntityOcelot(World worldIn) {
    super(worldIn);
    this.tasks.addTask(1, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIFollowLeader(this, 1.33D, 15.0F, 4.0F));
    this.tasks.addTask(3, new EntityAICustomLeapAttack(this, 0.3F, 0.6F, 0.8F, 0.5F, 3.0D, 20.0D, 6));
    this.tasks.addTask(4, new EntityAIFriendlyAttackMelee(this, 1.33D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.8D, 80));
    this.experienceValue = 1;
    setSize(0.6F, 0.7F);
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityOcelot(this.world);
  }
  
  public String getDescName() {
    return "cat";
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    List<EntityCreeper> list = this.world.getEntitiesWithinAABB(EntityCreeper.class, getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (list != null && !list.isEmpty() && this.ticksExisted % 40 == 0)
        for (EntityCreeper entity : list) {
            if (entity != null)
                entity.tasks.addTask(0, new EntityAIAvoidEntity(entity, EntityOcelot.class, 6.0F, 1.75D, 1.25D));
        }
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public void updateAITasks() {
    super.updateAITasks();
    if (getMoveHelper().isUpdating()) {
      double d0 = getMoveHelper().getSpeed();
      if (d0 <= 0.6D) {
        setSneaking(true);
        setSprinting(false);
      } else if (d0 >= 1.33D) {
        setSneaking(false);
        setSprinting(true);
      } else {
        setSneaking(false);
        setSprinting(false);
      } 
    } else {
      setSneaking(false);
      setSprinting(false);
    } 
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_CAT_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_CAT_DEATH;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_OCELOT;
  }
  
  public float getBlockPathWeight(BlockPos pos) {
    return (this.world.getBlockState(pos.down()).getBlock() == this.spawnableBlock) ? 10.0F : (this.world.getLightBrightness(pos) - 0.5F);
  }
  
  public boolean isNotColliding() {
    if (this.world.checkNoEntityCollision(getEntityBoundingBox(), this) && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox())) {
      BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
      if (blockpos.getY() < this.world.getSeaLevel())
        return false; 
      IBlockState iblockstate = this.world.getBlockState(blockpos.down());
      Block block = iblockstate.getBlock();
      if (block == Blocks.GRASS || block.isLeaves(iblockstate, this.world, blockpos.down()))
        return true; 
    } 
    return false;
  }
}
