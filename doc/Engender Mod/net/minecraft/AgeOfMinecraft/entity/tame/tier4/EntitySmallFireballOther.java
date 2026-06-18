package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySmallFireballOther extends EntitySmallFireball {
  public float damage = 6.0F;
  
  public EntitySmallFireballOther(World worldIn) {
    super(worldIn);
    func_70105_a(0.3125F, 0.3125F);
  }
  
  public EntitySmallFireballOther(World worldIn, EntityLivingBase p_i1771_2_, double p_i1771_3_, double p_i1771_5_, double p_i1771_7_) {
    super(worldIn, p_i1771_2_, p_i1771_3_, p_i1771_5_, p_i1771_7_);
    func_70105_a(0.3125F, 0.3125F);
  }
  
  public EntitySmallFireballOther(World worldIn, double p_i1772_2_, double p_i1772_4_, double p_i1772_6_, double p_i1772_8_, double p_i1772_10_, double p_i1772_12_) {
    super(worldIn, p_i1772_2_, p_i1772_4_, p_i1772_6_, p_i1772_8_, p_i1772_10_, p_i1772_12_);
    func_70105_a(0.3125F, 0.3125F);
  }
  
  protected void func_70227_a(RayTraceResult movingObject) {
    if (!this.field_70170_p.field_72995_K) {
      if (this.field_70235_a != null) {
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity1 = list.get(i1);
            if (entity1 != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart) {
              this.field_70235_a.func_70652_k((Entity)entity1);
              func_174815_a(this.field_70235_a, (Entity)entity1);
              func_70106_y();
            } 
          }  
      } 
      if (movingObject.field_72308_g != null && movingObject.field_72308_g.field_70172_ad <= 5 && movingObject.field_72308_g.func_70089_S()) {
        if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && movingObject.field_72308_g instanceof EntityLivingBase) {
          if (!movingObject.field_72308_g.func_70045_F() && movingObject.field_72308_g instanceof net.minecraft.entity.passive.EntityAnimal)
            movingObject.field_72308_g.func_70015_d(10); 
          if (!((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g) || (movingObject.field_72308_g instanceof EntityTameBase && ((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g) && ((EntityTameBase)this.field_70235_a).getFakeHealth() > 0.0F)) {
            func_82149_j(movingObject.field_72308_g);
            ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was fireballed by ", DamageSource.func_76362_a((EntityFireball)this, (Entity)this.field_70235_a), this.damage);
            func_174815_a(this.field_70235_a, movingObject.field_72308_g);
            if (!movingObject.field_72308_g.func_70045_F())
              movingObject.field_72308_g.func_70015_d(10); 
            if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && ((EntityTameBase)this.field_70235_a).isHero())
              EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0F, EngenderConfig.mobs.grief, false); 
            func_70106_y();
          } 
        } 
      } else if (movingObject.field_72308_g == null) {
        boolean flag1 = true;
        if (this.field_70235_a != null && this.field_70235_a instanceof net.minecraft.entity.EntityLiving)
          flag1 = EngenderConfig.mobs.grief; 
        if (flag1) {
          BlockPos blockpos = movingObject.func_178782_a().func_177972_a(movingObject.field_178784_b);
          if (this.field_70170_p.func_175623_d(blockpos)) {
            this.field_70170_p.func_175656_a(blockpos, Blocks.field_150480_ab.func_176223_P());
            if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && ((EntityTameBase)this.field_70235_a).isHero())
              EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, 1.0F, EngenderConfig.mobs.grief, false); 
            func_70106_y();
          } 
        } 
      } 
    } 
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_180427_aV() {
    return true;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.field_70235_a != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S() && !((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entity1))
          func_70227_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
