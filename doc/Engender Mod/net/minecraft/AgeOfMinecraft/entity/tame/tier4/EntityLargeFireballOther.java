package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLargeFireballOther extends EntityLargeFireball {
  public float damage = 17.0F;
  
  public EntityLargeFireballOther(World worldIn) {
    super(worldIn);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityLargeFireballOther(World worldIn, double p_i1768_2_, double p_i1768_4_, double p_i1768_6_, double p_i1768_8_, double p_i1768_10_, double p_i1768_12_) {
    super(worldIn, p_i1768_2_, p_i1768_4_, p_i1768_6_, p_i1768_8_, p_i1768_10_, p_i1768_12_);
  }
  
  public EntityLargeFireballOther(World worldIn, EntityLivingBase p_i1769_2_, double p_i1769_3_, double p_i1769_5_, double p_i1769_7_) {
    super(worldIn, p_i1769_2_, p_i1769_3_, p_i1769_5_, p_i1769_7_);
  }
  
  public boolean func_180427_aV() {
    return true;
  }
  
  protected void func_70227_a(RayTraceResult movingObject) {
    if (!this.field_70170_p.field_72995_K)
      if (movingObject.field_72308_g != null && !(movingObject.field_72308_g instanceof EntityLargeFireballOther)) {
        if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase && movingObject.field_72308_g instanceof EntityLivingBase) {
          if (!movingObject.field_72308_g.func_70045_F() && movingObject.field_72308_g instanceof net.minecraft.entity.passive.EntityAnimal)
            movingObject.field_72308_g.func_70015_d(30); 
          if (!((EntityTameBase)this.field_70235_a).isWild() && movingObject.field_72308_g instanceof net.minecraft.entity.monster.EntityGhast)
            this.damage = 1000.0F; 
          if (!((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g) || (movingObject.field_72308_g instanceof EntityTameBase && ((EntityTameBase)this.field_70235_a).func_184191_r(movingObject.field_72308_g) && ((EntityTameBase)this.field_70235_a).getFakeHealth() > 0.0F)) {
            func_82149_j(movingObject.field_72308_g);
            ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was fireballed by ", DamageSource.func_76362_a((EntityFireball)this, (Entity)this.field_70235_a), this.damage);
            func_174815_a(this.field_70235_a, movingObject.field_72308_g);
            boolean flag = EngenderConfig.mobs.grief;
            EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_92057_e, flag, flag);
            if (!movingObject.field_72308_g.func_70045_F())
              movingObject.field_72308_g.func_70015_d(30); 
            func_70106_y();
          } 
        } 
      } else if (movingObject.field_72308_g == null) {
        if (this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase)
          EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_92057_e, false, false); 
        func_70106_y();
      }  
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(2.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.field_70235_a != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S() && !((EntityTameBase)this.field_70235_a).func_184191_r((Entity)entity1))
          func_70227_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
