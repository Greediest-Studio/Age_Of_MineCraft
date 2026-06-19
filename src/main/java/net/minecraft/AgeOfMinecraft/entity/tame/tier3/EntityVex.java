package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtTarget;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVex extends EntityTameBase implements Light, Flying, Elemental {
  protected static final DataParameter<Byte> VEX_FLAGS = EntityDataManager.createKey(EntityVex.class, DataSerializers.BYTE);
  
  @Nullable
  private BlockPos boundOrigin;
  
  public EntityVex(World worldIn) {
    super(worldIn);
    this.isImmuneToFire = true;
    this.moveHelper = new AIMoveControl(this);
    setSize(0.4F, 0.8F);
    this.experienceValue = 3;
  }
  
  public String getDescName() {
    return "vex";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public int getNextLevelRequirement() {
    return 20;
  }
  
  public void move(MoverType type, double x, double y, double z) {
    super.move(type, x, y, z);
    doBlockCollisions();
  }
  
  protected void dropEquipmentUndamaged() {}
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 1.1F;
  }
  
  public float getBonusVSArmored() {
    return 0.9F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityVex(this.world);
  }
  
  public void onUpdate() {
    this.noClip = true;
    super.onUpdate();
    this.noClip = false;
    if (isEntityAlive()) {
      setNoGravity(true);
    } else {
      setNoGravity(false);
    } 
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
    if (!isWild())
      if (getAttackTarget() != null) {
        setBoundOrigin(getAttackTarget().getPosition());
      } else {
        setBoundOrigin((getJukeboxToDanceTo() != null) ? getJukeboxToDanceTo().up(2) : ((getGuardBlock() != null) ? new BlockPos(this.randPosX, this.randPosY, this.randPosZ) : getOwner().getPosition().up((int)getOwner().getEyeHeight())));
      }  
    if (!this.world.isRemote && isEntityAlive() && getAttackTarget() == null && getSpecialAttackTimer() > 600) {
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(24.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
        EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
        if (entitylivingbase != this && !false && entitylivingbase.isEntityAlive()) {
          setAttackTarget(entitylivingbase);
          Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
          this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
          break;
        } 
      } 
    } 
  }
  
  protected void initEntityAI() {
    super.initEntityAI();
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(4, new AIChargeAttack());
    this.tasks.addTask(8, new AIMoveRandom());
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 24.0F, 6.0F));
    this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.targetTasks.addTask(0, (EntityAIBase)new EntityAIFriendlyHurtByTarget((EntityCreature)this, true, new Class[0]));
    this.targetTasks.addTask(1, (EntityAIBase)new EntityAILeaderHurtByTarget(this));
    this.targetTasks.addTask(2, (EntityAIBase)new EntityAILeaderHurtTarget(this));
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(VEX_FLAGS, (byte) 0);
  }
  
  public void readEntityFromNBT(NBTTagCompound compound) {
    super.readEntityFromNBT(compound);
    if (compound.hasKey("BoundX"))
      this.boundOrigin = new BlockPos(compound.getInteger("BoundX"), compound.getInteger("BoundY"), compound.getInteger("BoundZ")); 
  }
  
  public void writeEntityToNBT(NBTTagCompound compound) {
    super.writeEntityToNBT(compound);
    if (this.boundOrigin != null) {
      compound.setInteger("BoundX", this.boundOrigin.getX());
      compound.setInteger("BoundY", this.boundOrigin.getY());
      compound.setInteger("BoundZ", this.boundOrigin.getZ());
    } 
  }
  
  @Nullable
  public BlockPos getBoundOrigin() {
    return this.boundOrigin;
  }
  
  public void setBoundOrigin(@Nullable BlockPos boundOriginIn) {
    this.boundOrigin = boundOriginIn;
  }
  
  private boolean getVexFlag(int p_190656_1_) {
    int i = (Byte) this.dataManager.get(VEX_FLAGS);
    return ((i & p_190656_1_) != 0);
  }
  
  private void setVexFlag(int p_190660_1_, boolean p_190660_2_) {
    int i = this.dataManager.get(VEX_FLAGS);
    if (p_190660_2_) {
      i |= p_190660_1_;
    } else {
      i &= ~p_190660_1_;
    } 
    this.dataManager.set(VEX_FLAGS, (byte) (i & 0xFF));
  }
  
  public boolean isCharging() {
    return (getVexFlag(1) || getJukeboxToDanceTo() != null);
  }
  
  public void setIsCharging(boolean p_190648_1_) {
    setVexFlag(1, p_190648_1_);
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_VEX_AMBIENT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_VEX_DEATH;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_VEX_HURT;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }
  
  public float getBrightness() {
    return 1.0F;
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    setEquipmentBasedOnDifficulty(difficulty);
    setEnchantmentBasedOnDifficulty(difficulty);
    return super.onInitialSpawn(difficulty, livingdata);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_VEX;
  }
  
  protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
    setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
  }
  
  class AIChargeAttack extends EntityAIBase {
    public AIChargeAttack() {
      setMutexBits(1);
    }
    
    public boolean shouldExecute() {
      return (EntityVex.this.getAttackTarget() != null && EntityVex.this.getSpecialAttackTimer() > 600) ? true : ((EntityVex.this.getAttackTarget() != null && !EntityVex.this.getMoveHelper().isUpdating() && EntityVex.this.rand.nextInt(7) == 0) ? ((EntityVex.this.getDistanceSq((Entity)EntityVex.this.getAttackTarget()) > 4.0D)) : false);
    }
    
    public boolean shouldContinueExecuting() {
      return (EntityVex.this.getAttackTarget() != null && EntityVex.this.getSpecialAttackTimer() > 600) ? true : ((EntityVex.this.getMoveHelper().isUpdating() && EntityVex.this.isCharging() && EntityVex.this.getAttackTarget() != null && EntityVex.this.getAttackTarget().isEntityAlive()));
    }
    
    public void startExecuting() {
      EntityLivingBase entitylivingbase = EntityVex.this.getAttackTarget();
      Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
      EntityVex.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
      EntityVex.this.setIsCharging(true);
      EntityVex.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
      if (EntityVex.this.getAttackTarget() != null && EntityVex.this.getSpecialAttackTimer() <= 0 && EntityVex.this.isHero()) {
        EntityVex.this.setSpecialAttackTimer(900);
        EntityVex.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 10.0F, 0.75F);
      } 
    }
    
    public void resetTask() {
      EntityVex.this.setIsCharging(false);
    }
    
    public void updateTask() {
      EntityLivingBase entitylivingbase = EntityVex.this.getAttackTarget();
      if (entitylivingbase != null && entitylivingbase.isEntityAlive())
        if (EntityVex.this.getDistanceSq((Entity)entitylivingbase) <= (EntityVex.this.width * EntityVex.this.width + entitylivingbase.width * entitylivingbase.width) + 9.0D) {
          EntityVex.this.attackEntityAsMob((Entity)entitylivingbase);
        } else {
          double d0 = EntityVex.this.getDistanceSq((Entity)entitylivingbase);
          if (d0 < 9.0D) {
            Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
            EntityVex.this.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
          } 
        }  
    }
  }
  
  class AIMoveControl extends EntityMoveHelper {
    public AIMoveControl(EntityVex vex) {
      super((EntityLiving)vex);
    }
    
    public void setMoveTo(double x, double y, double z, double speedIn) {
      super.setMoveTo(x, Flying.clampFlightY(y), z, speedIn);
    }
    
    public void onUpdateMoveHelper() {
      if (this.action == EntityMoveHelper.Action.MOVE_TO) {
        double d0 = this.posX - EntityVex.this.posX;
        double d1 = this.posY - EntityVex.this.posY;
        double d2 = this.posZ - EntityVex.this.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        d3 = MathHelper.sqrt(d3);
        if (d3 < EntityVex.this.getEntityBoundingBox().getAverageEdgeLength()) {
          this.action = EntityMoveHelper.Action.WAIT;
          EntityVex.this.motionX *= 0.5D;
          EntityVex.this.motionY *= 0.5D;
          EntityVex.this.motionZ *= 0.5D;
        } else {
          EntityVex.this.motionX += d0 / d3 * 0.2D * this.speed;
          EntityVex.this.motionY += d1 / d3 * 0.05D * this.speed;
          EntityVex.this.motionZ += d2 / d3 * 0.2D * this.speed;
          if (EntityVex.this.getAttackTarget() == null) {
            EntityVex.this.rotationYaw = -((float)MathHelper.atan2(EntityVex.this.motionX, EntityVex.this.motionZ)) * 57.295776F;
            EntityVex.this.renderYawOffset = EntityVex.this.rotationYaw;
          } else {
            double d4 = (EntityVex.this.getAttackTarget()).posX - EntityVex.this.posX;
            double d5 = (EntityVex.this.getAttackTarget()).posZ - EntityVex.this.posZ;
            EntityVex.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * 57.295776F;
            EntityVex.this.renderYawOffset = EntityVex.this.rotationYaw;
            EntityVex.this.faceEntity((Entity)EntityVex.this.getAttackTarget(), 10.0F, 40.0F);
          } 
        } 
      } 
    }
  }
  
  class AIMoveRandom extends EntityAIBase {
    public AIMoveRandom() {
      setMutexBits(1);
    }
    
    public boolean shouldExecute() {
      return (!EntityVex.this.getMoveHelper().isUpdating() && (EntityVex.this.rand.nextInt(7) == 0 || EntityVex.this.getJukeboxToDanceTo() != null));
    }
    
    public boolean shouldContinueExecuting() {
      return false;
    }
    
    public void updateTask() {
      BlockPos blockpos = EntityVex.this.getBoundOrigin();
      if (blockpos == null)
        blockpos = new BlockPos((Entity)EntityVex.this); 
      for (int i = 0; i < 3; i++) {
        BlockPos blockpos1 = blockpos.add(EntityVex.this.rand.nextInt(15) - 7, EntityVex.this.rand.nextInt(11) - 5, EntityVex.this.rand.nextInt(15) - 7);
        if (EntityVex.this.world.isAirBlock(blockpos1)) {
          EntityVex.this.moveHelper.setMoveTo(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.5D, blockpos1.getZ() + 0.5D, 0.25D);
          if (EntityVex.this.getAttackTarget() == null) {
            if (EntityVex.this.getJukeboxToDanceTo() != null) {
              EntityVex.this.getLookHelper().setLookPosition(EntityVex.this.getJukeboxToDanceTo().getX() + 0.5D, EntityVex.this.getJukeboxToDanceTo().getY() + 0.5D, EntityVex.this.getJukeboxToDanceTo().getZ() + 0.5D, 180.0F, 0.0F);
              break;
            } 
            EntityVex.this.getLookHelper().setLookPosition(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.5D, blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
          } 
          break;
        } 
      } 
    }
  }
}

