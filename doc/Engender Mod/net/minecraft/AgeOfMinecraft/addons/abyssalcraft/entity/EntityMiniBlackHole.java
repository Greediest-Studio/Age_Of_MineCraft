package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityMiniBlackHole extends EntityFireball {
  public EntityTameBase field_70235_a;
  
  public EntityMiniBlackHole(World worldIn) {
    super(worldIn);
    func_70105_a(0.5F, 0.5F);
  }
  
  public EntityMiniBlackHole(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    this.field_70235_a = (EntityTameBase)shooter;
    func_70105_a(0.5F, 0.5F);
  }
  
  public EntityMiniBlackHole(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn, x, y, z, accelX, accelY, accelZ);
    func_70105_a(0.5F, 0.5F);
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.SMOKE_LARGE;
  }
  
  protected boolean func_184564_k() {
    return false;
  }
  
  protected float func_82341_c() {
    return 0.8F;
  }
  
  protected void func_70227_a(RayTraceResult result) {
    if (!this.field_70170_p.field_72995_K && this.field_70235_a != null && result.field_72308_g != null && (result.field_72308_g instanceof EntityJzahar || result.field_72308_g instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm || result.field_72308_g instanceof net.minecraft.entity.IEntityMultiPart)) {
      this.field_70235_a.func_70652_k(result.field_72308_g);
      EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 3.0F, false, false);
      func_70106_y();
    } 
    if (!this.field_70170_p.field_72995_K && this.field_70235_a != null && result.field_72308_g == null) {
      EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 3.0F, false, false);
      func_70106_y();
    } 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (this.field_70173_aa > 200 && !this.field_70170_p.field_72995_K && this.field_70235_a != null) {
      EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 3.0F, false, false);
      func_70106_y();
    } 
    this.field_70170_p.func_175688_a(func_184563_j(), this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
    List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_186662_g(8.0D));
    if (list != null && !list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        if (entity.func_70089_S() && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) && !(entity instanceof EntityJzahar) && this.field_70235_a != null && entity instanceof EntityLivingBase && !this.field_70235_a.func_184191_r(entity)) {
          if (entity.field_70163_u < this.field_70163_u)
            entity.field_70181_x += 0.02500000037252903D; 
          double d1 = 1.0D;
          float x = MathHelper.func_76134_b((this.field_70173_aa + i)) * (1.0F + (float)(this.field_70146_Z.nextGaussian() * 2.0D));
          float z = MathHelper.func_76126_a((this.field_70173_aa + i)) * (1.0F + (float)(this.field_70146_Z.nextGaussian() * 2.0D));
          double d2 = this.field_70165_t + x - entity.field_70165_t;
          double d3 = this.field_70163_u - entity.field_70163_u;
          double d4 = this.field_70161_v + z - entity.field_70161_v;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.func_70024_g(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          if (entity.func_70068_e((Entity)this) <= 9.0D && this.field_70235_a != null && entity instanceof EntityLivingBase && !this.field_70235_a.func_184191_r(entity)) {
            entity.func_70097_a(DamageSource.field_76380_i, 1.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.func_70089_S() && !this.field_70235_a.isWild())
              this.field_70235_a.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " touched a Mini Black Hole's event horizon thanks to " + this.field_70235_a.func_70005_c_() + " (" + this.field_70235_a.getOwner().func_70005_c_() + ")", new Object[0])); 
          } 
          if (entity.func_70068_e((Entity)this) <= 2.0D && this.field_70235_a != null && entity instanceof EntityLivingBase && !this.field_70235_a.func_184191_r(entity)) {
            if (entity instanceof EntityLivingBase)
              ((EntityLivingBase)entity).func_70606_j(((EntityLivingBase)entity).func_110143_aJ() - 0.1F); 
            entity.func_70097_a(DamageSource.field_191291_g.func_76359_i().func_76348_h().func_151518_m(), 4.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.func_70089_S() && !this.field_70235_a.isWild())
              this.field_70235_a.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was sucked into a Mini Black Hole thanks to " + this.field_70235_a.func_70005_c_() + " (" + this.field_70235_a.getOwner().func_70005_c_() + ")", new Object[0])); 
            if (!entity.func_70089_S() && entity.func_184222_aU() && !(entity instanceof EntityTameBase)) {
              if (entity instanceof EntityLiving)
                ((EntityLiving)entity).func_70656_aK(); 
              entity.func_70106_y();
            } 
          } 
        } 
        if (entity != null && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityMiniBlackHole) && !(entity instanceof EntityBlackHole) && !(entity instanceof EntityImplosion)) {
          double d1 = 1.0D;
          double d2 = this.field_70165_t - entity.field_70165_t;
          double d3 = this.field_70163_u - entity.field_70163_u;
          double d4 = this.field_70161_v - entity.field_70161_v;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.func_70107_b(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
          entity.func_70024_g(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          if (entity instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityGuardianProjectile && func_70032_d(entity) <= 1.0D && this.field_70235_a != null && this.field_70235_a instanceof EntityTameBase) {
            EntityTameBase.createEngenderModExplosion((Entity)this.field_70235_a, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, 2.0F, true, false);
            entity.func_70106_y();
          } 
        } 
      }  
  }
}
