package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases.EntityAreaEffectCloudOther;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPotionOther extends EntityPotion {
  public EntityPotionOther(World worldIn) {
    super(worldIn);
  }
  
  public EntityPotionOther(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
    super(worldIn, throwerIn, potionDamageIn);
  }
  
  public EntityPotionOther(World worldIn, double x, double y, double z, ItemStack potionDamageIn) {
    super(worldIn, x, y, z, potionDamageIn);
  }
  
  protected void func_70184_a(RayTraceResult result) {
    if (!this.field_70170_p.field_72995_K && func_85052_h() == null) {
      func_70106_y();
      return;
    } 
    if (!this.field_70170_p.field_72995_K && func_85052_h() != null && func_85052_h() instanceof EntityTameBase) {
      ItemStack itemstack = func_184543_l();
      PotionType potiontype = PotionUtils.func_185191_c(itemstack);
      List<PotionEffect> list = PotionUtils.func_185189_a(itemstack);
      boolean flag = (potiontype == PotionTypes.field_185230_b && list.isEmpty());
      if (result.field_72313_a == RayTraceResult.Type.BLOCK) {
        BlockPos blockpos = result.func_178782_a().func_177972_a(result.field_178784_b);
        extinguishFires(blockpos);
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
          extinguishFires(blockpos.func_177972_a(enumfacing)); 
      } 
      applyWater();
      if (result.field_72308_g != null) {
        if (result.field_72308_g instanceof EntityLivingBase && !((EntityTameBase)func_85052_h()).func_184191_r(result.field_72308_g)) {
          if (!list.isEmpty())
            if (isLingering()) {
              makeAreaOfEffectCloud(itemstack, potiontype);
            } else {
              applySplash(result, list);
            }  
          int i = potiontype.func_185172_c() ? 2007 : 2002;
          this.field_70170_p.func_175718_b(i, new BlockPos((Entity)this), PotionUtils.func_190932_c(itemstack));
          func_70106_y();
        } 
      } else if (result.field_72308_g == null) {
        if (!list.isEmpty())
          if (isLingering()) {
            makeAreaOfEffectCloud(itemstack, potiontype);
          } else {
            applySplash(result, list);
          }  
        int i = potiontype.func_185172_c() ? 2007 : 2002;
        this.field_70170_p.func_175718_b(i, new BlockPos((Entity)this), PotionUtils.func_190932_c(itemstack));
        func_70106_y();
      } 
    } 
  }
  
  private void applyWater() {
    AxisAlignedBB axisalignedbb = func_174813_aQ().func_72314_b(4.0D, 2.0D, 4.0D);
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, axisalignedbb, field_190546_d);
    if (!list.isEmpty())
      for (EntityLivingBase entitylivingbase : list) {
        double d0 = func_70068_e((Entity)entitylivingbase);
        if (d0 < 16.0D && isWaterSensitiveEntity(entitylivingbase))
          entitylivingbase.func_70097_a(DamageSource.field_76369_e, 1.0F); 
      }  
  }
  
  private void applySplash(RayTraceResult p_190543_1_, List<PotionEffect> p_190543_2_) {
    AxisAlignedBB axisalignedbb = func_174813_aQ().func_72314_b(4.0D, 2.0D, 4.0D);
    List<EntityLivingBase> list = this.field_70170_p.func_72872_a(EntityLivingBase.class, axisalignedbb);
    if (!list.isEmpty())
      for (EntityLivingBase entitylivingbase : list) {
        if (entitylivingbase.func_184603_cC()) {
          double d0 = func_70068_e((Entity)entitylivingbase);
          if (d0 < 16.0D) {
            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
            if (entitylivingbase == p_190543_1_.field_72308_g) {
              func_82149_j((Entity)entitylivingbase);
              d1 = 1.0D;
            } 
            if (((EntityTameBase)func_85052_h()).func_184191_r((Entity)entitylivingbase)) {
              for (PotionEffect potioneffect : p_190543_2_) {
                if (potioneffect.func_188419_a().func_76403_b()) {
                  potioneffect.func_188419_a().func_180793_a((Entity)this, (Entity)func_85052_h(), entitylivingbase, potioneffect.func_76458_c(), 0.5D);
                  continue;
                } 
                if (!potioneffect.func_188419_a().func_76398_f())
                  entitylivingbase.func_70690_d(new PotionEffect(potioneffect)); 
              } 
              continue;
            } 
            for (PotionEffect potioneffect : p_190543_2_) {
              if (potioneffect.func_188419_a().func_76403_b()) {
                ((EntityTameBase)func_85052_h()).inflictEngenderMobDamage(entitylivingbase, " was killed by magic created by ", (new EntityDamageSource("indirectMagic", (Entity)func_85052_h())).func_82726_p().func_76348_h().func_151518_m(), 8.0F);
                continue;
              } 
              if (potioneffect.func_188419_a().func_76398_f() && entitylivingbase.func_70687_e(potioneffect)) {
                entitylivingbase.func_70690_d(new PotionEffect(potioneffect));
                continue;
              } 
              ((EntityTameBase)func_85052_h()).inflictEngenderMobDamage(entitylivingbase, " was killed by magic created by ", (new EntityDamageSource("indirectMagic", (Entity)func_85052_h())).func_82726_p().func_76348_h().func_151518_m(), 8.0F);
            } 
          } 
        } 
      }  
  }
  
  private void makeAreaOfEffectCloud(ItemStack p_190542_1_, PotionType p_190542_2_) {
    EntityAreaEffectCloudOther entityareaeffectcloud = new EntityAreaEffectCloudOther(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
    if (func_85052_h() != null && func_85052_h() instanceof EntityTameBase)
      entityareaeffectcloud.setOwner((EntityTameBase)func_85052_h()); 
    entityareaeffectcloud.setRadius(3.0F);
    entityareaeffectcloud.setRadiusOnUse(-0.5F);
    entityareaeffectcloud.setWaitTime(10);
    entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
    entityareaeffectcloud.setPotion(p_190542_2_);
    for (PotionEffect potioneffect : PotionUtils.func_185190_b(p_190542_1_))
      entityareaeffectcloud.addEffect(new PotionEffect(potioneffect)); 
    NBTTagCompound nbttagcompound = p_190542_1_.func_77978_p();
    if (nbttagcompound != null && nbttagcompound.func_150297_b("CustomPotionColor", 99))
      entityareaeffectcloud.setColor(nbttagcompound.func_74762_e("CustomPotionColor")); 
    this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
  }
  
  private boolean isLingering() {
    return (func_184543_l().func_77973_b() == Items.field_185156_bI);
  }
  
  private void extinguishFires(BlockPos pos) {
    if (this.field_70170_p.func_180495_p(pos).func_177230_c() == Blocks.field_150480_ab)
      this.field_70170_p.func_180501_a(pos, Blocks.field_150350_a.func_176223_P(), 2); 
  }
  
  private static boolean isWaterSensitiveEntity(EntityLivingBase p_190544_0_) {
    return (p_190544_0_ instanceof net.minecraft.entity.monster.EntityEnderman || p_190544_0_ instanceof net.minecraft.entity.monster.EntityBlaze || p_190544_0_ instanceof EntityBlaze || p_190544_0_ instanceof EntityEnderman);
  }
}
