package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPEGunPellet extends EntityFireball {
  public float damage = 2.0F;
  
  public int texture = 1;
  
  public EntityPEGunPellet(World worldIn) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_189654_d(true);
  }
  
  public EntityPEGunPellet(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    super(worldIn, shooter, accelX, accelY, accelZ);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_189654_d(true);
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.ENCHANTMENT_TABLE;
  }
  
  protected void func_70227_a(RayTraceResult result) {
    if (!this.field_70170_p.field_72995_K) {
      if (this.field_70235_a != null && result.field_72308_g != null && result.field_72308_g instanceof net.minecraft.entity.monster.EntityEnderman)
        result.field_72308_g.func_70097_a((new EntityDamageSourceIndirect("blaster", (Entity)this, (Entity)this.field_70235_a)).func_76348_h(), 0.0F); 
      if (this.field_70235_a != null && result.field_72308_g != null && result.field_72308_g instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman)
        result.field_72308_g.func_70097_a((new EntityDamageSourceIndirect("blaster", (Entity)this, (Entity)this.field_70235_a)).func_76348_h(), 0.0F); 
      if (this.field_70235_a != null && result.field_72308_g != null && result.field_72308_g.func_70089_S() && !(result.field_72308_g instanceof net.minecraft.entity.monster.EntityEnderman) && !(result.field_72308_g instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman)) {
        result.field_72308_g.field_70172_ad = 0;
        if (this.field_70235_a instanceof EntityTameBase && result.field_72308_g instanceof EntityLivingBase) {
          if (!((EntityTameBase)this.field_70235_a).func_184191_r(result.field_72308_g)) {
            result.field_72308_g.func_70015_d((int)this.damage);
            if (this.damage >= 10.0F) {
              result.field_72308_g.func_70097_a((new EntityDamageSourceIndirect("blaster", (Entity)this, (Entity)this.field_70235_a)).func_76348_h().func_151518_m(), this.damage);
            } else {
              result.field_72308_g.func_70097_a((new EntityDamageSourceIndirect("blaster", (Entity)this, (Entity)this.field_70235_a)).func_76348_h(), this.damage);
            } 
          } 
        } else {
          result.field_72308_g.func_70015_d((int)this.damage);
          func_184185_a(SoundEvents.field_187539_bB, 0.5F, 2.0F);
          if (this.damage >= 10.0F) {
            result.field_72308_g.func_70097_a((new EntityDamageSourceIndirect("blaster", (Entity)this, (Entity)this.field_70235_a)).func_76348_h().func_151518_m(), this.damage);
          } else {
            result.field_72308_g.func_70097_a((new EntityDamageSourceIndirect("blaster", (Entity)this, (Entity)this.field_70235_a)).func_76348_h(), this.damage);
          } 
        } 
        func_174815_a(this.field_70235_a, result.field_72308_g);
        func_70106_y();
      } 
    } 
  }
  
  public boolean func_70027_ad() {
    return false;
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  protected float func_82341_c() {
    return 1.0F;
  }
  
  public void func_70071_h_() {
    if (this.field_70235_a == null) {
      func_70106_y();
    } else {
      super.func_70071_h_();
      for (int i = 0; i < this.texture - 1; i++) {
        this.field_70159_w += this.field_70232_b;
        this.field_70181_x += this.field_70233_c;
        this.field_70179_y += this.field_70230_d;
      } 
      if (this.damage >= 20.0F) {
        this.texture = 10;
      } else if (this.damage < 20.0F && this.damage >= 16.0F) {
        this.texture = 8;
      } else if (this.damage < 16.0F && this.damage >= 12.0F) {
        this.texture = 6;
      } else if (this.damage < 12.0F && this.damage >= 8.0F) {
        this.texture = 4;
      } else if (this.damage < 8.0F && this.damage >= 0.0F) {
        this.texture = 2;
      } 
      func_70105_a(0.125F, 0.125F);
      this.field_70145_X = true;
      func_189654_d(true);
      AbyssalCraftAPI.getInternalMethodHandler().spawnParticle("PEStream", this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.2D, this.field_70161_v, 0.0D, 0.0D, 0.0D);
    } 
  }
}
