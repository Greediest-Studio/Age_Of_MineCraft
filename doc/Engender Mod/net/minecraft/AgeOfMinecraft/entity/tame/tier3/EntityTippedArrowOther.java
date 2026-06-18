package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityTippedArrowOther extends EntityTippedArrow {
  EntityLivingBase shooter;
  
  public EntityTippedArrowOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityTippedArrowOther(World worldIn, double x, double y, double z) {
    super(worldIn, x, y, z);
  }
  
  public EntityTippedArrowOther(World worldIn, EntityLivingBase shooter) {
    super(worldIn, shooter);
    this.shooter = shooter;
  }
  
  protected void func_184549_a(RayTraceResult p_184549_1_) {
    Entity entity = p_184549_1_.field_72308_g;
    if (!this.field_70170_p.field_72995_K && this.shooter != null && entity != null && entity instanceof EntityLivingBase && !this.shooter.func_184191_r(entity) && entity != ((EntityTameBase)this.shooter).getOwner()) {
      DamageSource damagesource;
      float f = MathHelper.func_76133_a(this.field_70159_w * this.field_70159_w + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y);
      int i = MathHelper.func_76143_f(f * func_70242_d());
      if (func_70241_g())
        i += this.field_70146_Z.nextInt(i / 2 + 2); 
      if (this.field_70250_c == null) {
        damagesource = DamageSource.func_76353_a((EntityArrow)this, (Entity)this);
      } else {
        damagesource = DamageSource.func_76353_a((EntityArrow)this, (Entity)this.shooter);
      } 
      if (entity instanceof EntityLivingBase && this.shooter != null && entity.func_70089_S() && this.shooter instanceof EntityTameBase && (!((EntityTameBase)this.shooter).func_184191_r(entity) || (entity instanceof EntityTameBase && ((EntityTameBase)this.shooter).func_184191_r(entity) && ((EntityTameBase)this.shooter).getFakeHealth() > 0.0F))) {
        ((EntityTameBase)this.shooter).inflictEngenderMobDamage((EntityLivingBase)entity, " was shot by ", damagesource, i);
        func_184548_a((EntityLivingBase)entity);
        if (this.shooter instanceof EntitySkeleton && (entity instanceof net.minecraft.entity.monster.EntityCreeper || entity instanceof EntityCreeper) && !entity.func_70089_S()) {
          int i1 = Item.func_150891_b(Items.field_151096_cd);
          int j = Item.func_150891_b(Items.field_151084_co);
          int k = i1 + this.field_70146_Z.nextInt(j - i1 + 1);
          entity.func_145779_a(Item.func_150899_d(k), 1);
        } 
        if (((EntityTameBase)this.shooter).isHero() && func_70242_d() > 0.0D) {
          func_70239_b(func_70242_d() - 0.5D);
        } else {
          func_70106_y();
        } 
      } 
    } else if (!this.field_70170_p.field_72995_K && entity == null && func_70094_T()) {
      func_184185_a(SoundEvents.field_187731_t, 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F));
      func_70106_y();
    } 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (func_70094_T())
      for (int u = 0; u < 3.0D + func_70242_d(); u++) {
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
        int k = MathHelper.func_76128_c(this.field_70161_v);
        IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(i, j, k));
        if (iblockstate.func_185904_a() != Material.field_151579_a)
          this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, this.field_70165_t, (func_174813_aQ()).field_72338_b + 0.3D, this.field_70161_v, -this.field_70159_w, 2.0D, -this.field_70179_y, new int[] { Block.func_176210_f(iblockstate) }); 
      }  
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(2.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.shooter != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S() && !((EntityTameBase)this.shooter).func_184191_r((Entity)entity1))
          func_184549_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
}
