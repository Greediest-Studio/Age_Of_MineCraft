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
    this.canBeSeenSelector = new Predicate<Entity>() {
        public boolean apply(@Nullable Entity p_apply_1_) {
          return (EntityAIAvoidEntitySPC.this.theEntity.func_70635_at().func_75522_a(p_apply_1_) && !EntityAIAvoidEntitySPC.this.theEntity.func_184191_r(p_apply_1_));
        }
      };
    this.theEntity = theEntityIn;
    this.classToAvoid = classToAvoidIn;
    this.avoidTargetSelector = avoidTargetSelectorIn;
    this.avoidDistance = avoidDistanceIn;
    this.farSpeed = farSpeedIn;
    this.nearSpeed = nearSpeedIn;
    this.entityPathNavigate = theEntityIn.func_70661_as();
    func_75248_a(1);
  }
  
  public boolean func_75250_a() {
    List<T> list = this.theEntity.field_70170_p.func_175647_a(this.classToAvoid, this.theEntity.func_174813_aQ().func_72314_b(this.avoidDistance, this.avoidDistance, this.avoidDistance), Predicates.and(new Predicate[] { EntitySelectors.field_188444_d, this.canBeSeenSelector, this.avoidTargetSelector }));
    if (list.isEmpty())
      return false; 
    this.closestLivingEntity = list.get(0);
    Vec3d vec3d = RandomPositionGenerator.func_75461_b(this.theEntity, 16, 7, new Vec3d(((Entity)this.closestLivingEntity).field_70165_t, ((Entity)this.closestLivingEntity).field_70163_u, ((Entity)this.closestLivingEntity).field_70161_v));
    if (vec3d == null)
      return false; 
    if (!this.entityPathNavigate.func_75500_f())
      return false; 
    if (((Entity)this.closestLivingEntity).field_70128_L)
      return false; 
    if (this.closestLivingEntity.func_70092_e(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c) < this.closestLivingEntity.func_70068_e((Entity)this.theEntity))
      return false; 
    this.entityPathEntity = this.entityPathNavigate.func_75488_a(vec3d.field_72450_a, vec3d.field_72448_b, vec3d.field_72449_c);
    return (this.entityPathEntity != null);
  }
  
  public boolean func_75253_b() {
    return (!this.entityPathNavigate.func_75500_f() && !((Entity)this.closestLivingEntity).field_70128_L);
  }
  
  public void func_75249_e() {
    this.entityPathNavigate.func_75484_a(this.entityPathEntity, this.farSpeed);
  }
  
  public void func_75251_c() {
    this.closestLivingEntity = null;
  }
  
  public void func_75246_d() {
    if (this.theEntity.func_70068_e((Entity)this.closestLivingEntity) < 49.0D) {
      this.theEntity.func_70661_as().func_75489_a(this.nearSpeed);
    } else {
      this.theEntity.func_70661_as().func_75489_a(this.farSpeed);
    } 
  }
}
