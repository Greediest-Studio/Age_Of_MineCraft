package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

public class EntityAIAvoidEntitySPC<T extends Entity> extends EntityAIBase {
  private final Predicate<Entity> canBeSeenSelector;
  
  protected EntityCreature theEntity;
  
  private final double farSpeed;
  
  private final double nearSpeed;
  
  protected T closestLivingEntity;
  
  private final float avoidDistance;
  
  private Path entityPathEntity;
  
  private final PathNavigate entityPathNavigate;
  
  private final Class<T> classToAvoid;
  
  private final Predicate<? super T> avoidTargetSelector;
  
  public EntityAIAvoidEntitySPC(EntityCreature theEntityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
    this(theEntityIn, classToAvoidIn, Predicates.alwaysTrue(), avoidDistanceIn, farSpeedIn, nearSpeedIn);
  }
  
  public EntityAIAvoidEntitySPC(EntityCreature theEntityIn, Class<T> classToAvoidIn, Predicate<? super T> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
    this.canBeSeenSelector = p_apply_1_ -> EntityAIAvoidEntitySPC.this.theEntity.getEntitySenses().canSee(p_apply_1_);
    this.theEntity = theEntityIn;
    this.classToAvoid = classToAvoidIn;
    this.avoidTargetSelector = avoidTargetSelectorIn;
    this.avoidDistance = avoidDistanceIn;
    this.farSpeed = farSpeedIn;
    this.nearSpeed = nearSpeedIn;
    this.entityPathNavigate = theEntityIn.getNavigator();
    setMutexBits(1);
  }
  
  public boolean shouldExecute() {
    List<T> list = this.theEntity.world.getEntitiesWithinAABB(this.classToAvoid, this.theEntity.getEntityBoundingBox().grow(this.avoidDistance, this.avoidDistance, this.avoidDistance), Predicates.and(EntitySelectors.CAN_AI_TARGET, this.canBeSeenSelector, this.avoidTargetSelector));
    if (list.isEmpty())
      return false; 
    this.closestLivingEntity = list.get(0);
    Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, new Vec3d(((Entity)this.closestLivingEntity).posX, ((Entity)this.closestLivingEntity).posY, ((Entity)this.closestLivingEntity).posZ));
    if (vec3d == null)
      return false; 
    if (!this.entityPathNavigate.noPath())
      return false; 
    if (((Entity)this.closestLivingEntity).isDead)
      return false; 
    if (this.closestLivingEntity.getDistanceSq(vec3d.x, vec3d.y, vec3d.z) < this.closestLivingEntity.getDistanceSq((Entity)this.theEntity))
      return false; 
    this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3d.x, vec3d.y, vec3d.z);
    return (this.entityPathEntity != null);
  }
  
  public boolean shouldContinueExecuting() {
    return (!this.entityPathNavigate.noPath() && !((Entity)this.closestLivingEntity).isDead);
  }
  
  public void startExecuting() {
    this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
  }
  
  public void resetTask() {
    this.closestLivingEntity = null;
  }
  
  public void updateTask() {
    if (this.theEntity.getDistanceSq((Entity)this.closestLivingEntity) < 49.0D) {
      this.theEntity.getNavigator().setSpeed(this.nearSpeed);
    } else {
      this.theEntity.getNavigator().setSpeed(this.farSpeed);
    } 
  }
}
