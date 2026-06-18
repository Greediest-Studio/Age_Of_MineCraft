package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;

public class EntityAINearestAttackableTargetBox<T extends EntityLivingBase> extends EntityAITarget {
  protected final Class<T> targetClass;
  
  private final int targetChance;
  
  protected final EntityAINearestAttackableTarget.Sorter sorter;
  
  protected final Predicate<? super T> targetEntitySelector;
  
  protected T targetEntity;
  
  public EntityAINearestAttackableTargetBox(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
    this(creature, classTarget, checkSight, false);
  }
  
  public EntityAINearestAttackableTargetBox(EntityCreature creature, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
    this(creature, classTarget, 0, checkSight, onlyNearby, (Predicate<? super T>)null);
  }
  
  public EntityAINearestAttackableTargetBox(EntityCreature creature, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, @Nullable final Predicate<? super T> targetSelector) {
    super(creature, checkSight, onlyNearby);
    this.targetClass = classTarget;
    this.targetChance = chance;
    this.sorter = new EntityAINearestAttackableTarget.Sorter((Entity)creature);
    func_75248_a(1);
    this.targetEntitySelector = new Predicate<T>() {
        public boolean apply(@Nullable T p_apply_1_) {
          if (p_apply_1_ == null)
            return false; 
          if (targetSelector != null && !targetSelector.apply(p_apply_1_))
            return false; 
          return !EntitySelectors.field_180132_d.apply(p_apply_1_) ? false : EntityAINearestAttackableTargetBox.this.func_75296_a((EntityLivingBase)p_apply_1_, false);
        }
      };
  }
  
  public boolean func_75250_a() {
    if (this.targetChance > 0 && this.field_75299_d.func_70681_au().nextInt(this.targetChance) != 0)
      return false; 
    if (this.field_75299_d.func_184191_r((Entity)this.targetEntity))
      return false; 
    if (this.targetClass != EntityPlayer.class && this.targetClass != EntityPlayerMP.class) {
      List<T> list = this.field_75299_d.field_70170_p.func_175647_a(this.targetClass, getTargetableArea(func_111175_f()), this.targetEntitySelector);
      if (list.isEmpty())
        return false; 
      Collections.sort(list, (Comparator<? super T>)this.sorter);
      this.targetEntity = list.get(0);
      return true;
    } 
    this.targetEntity = (T)this.field_75299_d.field_70170_p.func_184150_a(this.field_75299_d.field_70165_t, this.field_75299_d.field_70163_u + this.field_75299_d.func_70047_e(), this.field_75299_d.field_70161_v, func_111175_f(), func_111175_f(), new Function<EntityPlayer, Double>() {
          @Nullable
          public Double apply(@Nullable EntityPlayer p_apply_1_) {
            return Double.valueOf(1.0D);
          }
        },  this.targetEntitySelector);
    return (this.targetEntity != null);
  }
  
  protected AxisAlignedBB getTargetableArea(double targetDistance) {
    return this.field_75299_d.func_174813_aQ().func_186662_g(targetDistance);
  }
  
  public void func_75249_e() {
    this.field_75299_d.func_70624_b((EntityLivingBase)this.targetEntity);
    super.func_75249_e();
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
