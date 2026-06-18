package net.minecraft.AgeOfMinecraft.entity.tame.other;

import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityPortalLightning extends EntityFireball {
  public Entity targetEntity;
  
  public EntityPortalLightning(World worldIn) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_189654_d(true);
  }
  
  public EntityPortalLightning(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn);
    this.field_70235_a = shooter;
    func_70012_b(accelX, accelY, accelZ, shooter.field_70177_z, shooter.field_70125_A);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.field_70232_b = accelX;
    this.field_70233_c = accelY;
    this.field_70230_d = accelZ;
  }
  
  public EntityPortalLightning(World worldIn, Entity target, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
    this(worldIn);
    this.field_70235_a = shooter;
    func_70012_b(accelX, accelY, accelZ, shooter.field_70177_z, shooter.field_70125_A);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.targetEntity = target;
    this.field_70232_b = accelX;
    this.field_70233_c = accelY;
    this.field_70230_d = accelZ;
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
  }
  
  public EntityPortalLightning(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    this(worldIn);
    func_70012_b(accelX, accelY, accelZ, 0.0F, 0.0F);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.field_70232_b = accelX;
    this.field_70233_c = accelY;
    this.field_70230_d = accelZ;
  }
  
  protected EnumParticleTypes func_184563_j() {
    return EnumParticleTypes.END_ROD;
  }
  
  protected void func_70227_a(RayTraceResult result) {}
  
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
    return 0.99F;
  }
  
  public void func_70071_h_() {
    if (!this.field_70170_p.field_72995_K) {
      func_70052_a(6, func_184202_aL());
      if (!this.field_70128_L) {
        ChunkLoadingEvent.updateLoaded((Entity)this);
      } else {
        ChunkLoadingEvent.stopLoading((Entity)this);
      } 
    } 
    func_70105_a(0.25F, 0.25F);
    this.field_70145_X = true;
    func_189654_d(true);
    func_70030_z();
    if (this.targetEntity != null && !this.targetEntity.func_70089_S())
      this.targetEntity = null; 
    this.field_70159_w *= 0.0D;
    this.field_70181_x *= 0.0D;
    this.field_70179_y *= 0.0D;
    if (this.field_70173_aa == 1) {
      this.field_70232_b = this.field_70165_t;
      this.field_70233_c = this.field_70163_u;
      this.field_70230_d = this.field_70161_v;
      func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    } 
    if (this.field_70173_aa > 1 && this.targetEntity != null)
      func_70012_b(this.targetEntity.field_70165_t, this.targetEntity.field_70163_u + this.targetEntity.func_70047_e() * 0.2D, this.targetEntity.field_70161_v, this.targetEntity.field_70177_z, this.targetEntity.field_70125_A); 
    if (this.field_70173_aa > 2) {
      short short1 = (short)(int)func_70092_e(this.field_70232_b, this.field_70233_c, this.field_70230_d);
      for (int i = 0; i < short1; i++) {
        double d9 = i / (short1 - 1.0D);
        double d6 = this.field_70165_t + (this.field_70165_t - this.field_70232_b) * -d9;
        double d7 = this.field_70163_u + (this.field_70163_u - this.field_70233_c) * -d9;
        double d8 = this.field_70161_v + (this.field_70161_v - this.field_70230_d) * -d9;
        if (this.field_70170_p.field_72995_K)
          this.field_70170_p.func_175682_a(EnumParticleTypes.END_ROD, true, d6, d7, d8, 0.0D, 0.01D, 0.0D, new int[0]); 
      } 
    } 
    if (this.field_70173_aa > 5 && this.field_70235_a != null && this.targetEntity != null && func_70032_d(this.targetEntity) <= 1.0D) {
      this.targetEntity.func_184185_a(ESound.lightningShot, 10000.0F, 1.0F);
      this.targetEntity.func_70077_a(null);
      this.targetEntity.field_70172_ad = 0;
      if (this.field_70235_a instanceof EntityTameBase) {
        this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, this.field_70232_b - 0.5D, this.field_70233_c, this.field_70230_d - 0.5D, true));
        List<Entity> entities = this.field_70170_p.func_72839_b((Entity)this, (new AxisAlignedBB(func_180425_c())).func_186662_g(2.0D));
        for (Entity entity : entities) {
          if (entity instanceof EntityLivingBase) {
            ((EntityTameBase)this.field_70235_a).inflictEngenderMobDamage((EntityLivingBase)this.targetEntity, " was electrocuted by ", (new EntityDamageSource("indirectMagic", (Entity)this.field_70235_a)).func_82726_p().func_76348_h().func_151518_m(), 10.0F);
            if (entity.func_70089_S())
              ((EntityTameBase)this.field_70235_a).func_70652_k(this.targetEntity); 
          } 
        } 
        this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187754_de, SoundCategory.WEATHER, 10000.0F, 0.8F + this.field_70146_Z.nextFloat() * 0.2F);
        this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187752_dd, SoundCategory.WEATHER, 2.0F, 0.5F + this.field_70146_Z.nextFloat() * 0.2F);
        if (this.targetEntity instanceof EntityLivingBase && !(this.targetEntity instanceof EntityPlayer) && !(this.targetEntity instanceof net.minecraft.entity.EntityLiving)) {
          this.targetEntity.field_70181_x++;
          if (this.targetEntity instanceof EntityLivingBase)
            if (!this.targetEntity.func_70089_S() && !this.targetEntity.field_70128_L)
              this.field_70235_a.func_70074_a((EntityLivingBase)this.targetEntity);  
          this.targetEntity.func_70015_d(100);
        } 
      } 
      func_70106_y();
    } 
    if (this.field_70173_aa > 20 || (!this.field_70170_p.field_72995_K && this.targetEntity == null))
      func_70106_y(); 
  }
}
