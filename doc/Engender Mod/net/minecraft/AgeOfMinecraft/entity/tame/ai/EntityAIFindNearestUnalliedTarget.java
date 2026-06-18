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
    this.predicate = new Predicate<EntityLivingBase>() {
        public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
          double d0 = EntityAIFindNearestUnalliedTarget.this.getFollowRange();
          if (p_apply_1_.func_70093_af())
            d0 *= 0.75D; 
          if (p_apply_1_.func_82150_aj())
            return false; 
          if (!EntityAIFindNearestUnalliedTarget.this.mob.func_190631_cK())
            return false; 
          if (EntityAIFindNearestUnalliedTarget.this.mob.func_70638_az() != null)
            return false; 
          return (p_apply_1_.func_70032_d((Entity)EntityAIFindNearestUnalliedTarget.this.mob) > d0 || EntityAIFindNearestUnalliedTarget.this.mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) ? false : EntityAITarget.func_179445_a((EntityLiving)EntityAIFindNearestUnalliedTarget.this.mob, p_apply_1_, false, true);
        }
      };
    this.sorter = new Sorter((Entity)mobIn);
  }
  
  public boolean func_75250_a() {
    double d0 = getFollowRange();
    List<EntityLivingBase> list = this.mob.field_70170_p.func_175647_a(this.classToCheck, this.mob.func_174813_aQ().func_186662_g(d0), this.predicate);
    Collections.sort(list, this.sorter);
    if (list.isEmpty())
      return false; 
    this.target = list.get(0);
    return true;
  }
  
  public boolean func_75253_b() {
    EntityLivingBase entitylivingbase = this.mob.func_70638_az();
    if (entitylivingbase == null)
      return false; 
    if (!entitylivingbase.func_70089_S())
      return false; 
    if (this.mob.func_184191_r((Entity)entitylivingbase))
      return false; 
    double d0 = getFollowRange();
    if (this.mob.func_70032_d((Entity)entitylivingbase) > d0)
      return false; 
    return (!(entitylivingbase instanceof EntityPlayerMP) || !((EntityPlayerMP)entitylivingbase).field_71134_c.func_73083_d());
  }
  
  public void func_75249_e() {
    this.mob.func_70624_b(this.target);
    super.func_75249_e();
  }
  
  public void func_75251_c() {
    this.mob.func_70624_b((EntityLivingBase)null);
    super.func_75249_e();
  }
  
  protected double getFollowRange() {
    IAttributeInstance iattributeinstance = this.mob.func_110148_a(SharedMonsterAttributes.field_111265_b);
    return (iattributeinstance == null) ? 16.0D : iattributeinstance.func_111126_e();
  }
  
  public static class Sorter implements Comparator<Entity> {
    private final Entity entity;
    
    public Sorter(Entity entityIn) {
      this.entity = entityIn;
    }
    
    public int compare(Entity p_compare_1_, Entity p_compare_2_) {
      double d0 = this.entity.func_70068_e(p_compare_1_);
      double d1 = this.entity.func_70068_e(p_compare_2_);
      if (d0 < d1)
        return -1; 
      return (d0 > d1) ? 1 : 0;
    }
  }
}
