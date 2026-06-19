package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindNearestUnalliedTarget extends EntityAIBase {
  private static final Logger LOGGER = LogManager.getLogger();
  
  private final EntityTameBase mob;
  
  private final Predicate<EntityLivingBase> predicate;
  
  private final Sorter sorter;
  
  private EntityLivingBase target;
  
  private final Class<? extends EntityLivingBase> classToCheck;
  
  public EntityAIFindNearestUnalliedTarget(EntityTameBase mobIn, Class<? extends EntityLivingBase> p_i45884_2_) {
    this.mob = mobIn;
    this.classToCheck = p_i45884_2_;
    this.predicate = p_apply_1_ -> {
      double d0 = EntityAIFindNearestUnalliedTarget.this.getFollowRange();
      if (p_apply_1_.isSneaking())
        d0 *= 0.75D;
      if (p_apply_1_.isInvisible())
        return false;
      if (!EntityAIFindNearestUnalliedTarget.this.mob.attackable())
        return false;
      if (EntityAIFindNearestUnalliedTarget.this.mob.getAttackTarget() != null)
        return false;
      return (p_apply_1_.getDistance((Entity)EntityAIFindNearestUnalliedTarget.this.mob) > d0 || EntityAIFindNearestUnalliedTarget.this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) ? false : EntityAITarget.isSuitableTarget((EntityLiving)EntityAIFindNearestUnalliedTarget.this.mob, p_apply_1_, false, true);
    };
    this.sorter = new Sorter((Entity)mobIn);
  }
  
  public boolean shouldExecute() {
    double d0 = getFollowRange();
    List<EntityLivingBase> list = this.mob.world.getEntitiesWithinAABB(this.classToCheck, this.mob.getEntityBoundingBox().grow(d0), this.predicate);
    list.sort(this.sorter);
    if (list.isEmpty())
      return false; 
    this.target = list.get(0);
    return true;
  }
  
  public boolean shouldContinueExecuting() {
    EntityLivingBase entitylivingbase = this.mob.getAttackTarget();
    if (entitylivingbase == null)
      return false; 
    if (!entitylivingbase.isEntityAlive())
      return false; 
    double d0 = getFollowRange();
    if (this.mob.getDistance((Entity)entitylivingbase) > d0)
      return false; 
    return (!(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).interactionManager.isCreative());
  }
  
  public void startExecuting() {
    this.mob.setAttackTarget(this.target);
    super.startExecuting();
  }
  
  public void resetTask() {
    this.mob.setAttackTarget((EntityLivingBase)null);
    super.startExecuting();
  }
  
  protected double getFollowRange() {
    IAttributeInstance iattributeinstance = this.mob.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
    return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
  }
  
  public static class Sorter implements Comparator<Entity> {
    private final Entity entity;
    
    public Sorter(Entity entityIn) {
      this.entity = entityIn;
    }
    
    public int compare(Entity p_compare_1_, Entity p_compare_2_) {
      double d0 = this.entity.getDistanceSq(p_compare_1_);
      double d1 = this.entity.getDistanceSq(p_compare_2_);
      if (d0 < d1)
        return -1; 
      return (d0 > d1) ? 1 : 0;
    }
  }
}
