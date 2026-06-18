package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEvokerFangOther extends EntityEvokerFangs {
  private int warmupDelayTicks;
  
  private boolean sentSpikeEvent;
  
  private int lifeTicks;
  
  private boolean clientSideAttackStarted;
  
  private EntityLivingBase caster;
  
  private UUID casterUuid;
  
  public EntityEvokerFangOther(World worldIn) {
    super(worldIn);
    this.lifeTicks = 22;
  }
  
  public EntityEvokerFangOther(World worldIn, double x, double y, double z, float p_i47276_8_, int p_i47276_9_, EntityLivingBase casterIn) {
    this(worldIn);
    this.warmupDelayTicks = p_i47276_9_;
    func_190549_a(casterIn);
    this.field_70177_z = p_i47276_8_ * 57.295776F;
    func_70107_b(x, y, z);
  }
  
  protected void func_70088_a() {}
  
  public void func_190549_a(@Nullable EntityLivingBase p_190549_1_) {
    this.caster = p_190549_1_;
    this.casterUuid = (p_190549_1_ == null) ? null : p_190549_1_.func_110124_au();
  }
  
  @Nullable
  public EntityLivingBase func_190552_j() {
    if (this.caster == null && this.casterUuid != null && this.field_70170_p instanceof WorldServer) {
      Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(this.casterUuid);
      if (entity instanceof EntityLivingBase)
        this.caster = (EntityLivingBase)entity; 
    } 
    return this.caster;
  }
  
  protected void func_70037_a(NBTTagCompound compound) {
    this.warmupDelayTicks = compound.func_74762_e("Warmup");
    this.casterUuid = compound.func_186857_a("OwnerUUID");
  }
  
  protected void func_70014_b(NBTTagCompound compound) {
    compound.func_74768_a("Warmup", this.warmupDelayTicks);
    if (this.casterUuid != null)
      compound.func_186854_a("OwnerUUID", this.casterUuid); 
  }
  
  public void func_70071_h_() {
    if (!this.field_70170_p.field_72995_K)
      func_70052_a(6, func_184202_aL()); 
    func_70030_z();
    if (this.field_70170_p.field_72995_K) {
      if (this.clientSideAttackStarted) {
        this.lifeTicks--;
        if (this.lifeTicks == 14)
          for (int i = 0; i < 12; i++) {
            double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * this.field_70130_N * 0.5D;
            double d1 = this.field_70163_u + 0.05D + this.field_70146_Z.nextDouble() * 1.0D;
            double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * this.field_70130_N * 0.5D;
            double d3 = (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * 0.3D;
            double d4 = 0.3D + this.field_70146_Z.nextDouble() * 0.3D;
            double d5 = (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) * 0.3D;
            this.field_70170_p.func_175688_a(EnumParticleTypes.CRIT, d0, d1 + 1.0D, d2, d3, d4, d5, new int[0]);
          }  
      } 
    } else if (--this.warmupDelayTicks < 0) {
      if (this.warmupDelayTicks == -8)
        for (EntityLivingBase entitylivingbase : this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(1.0D))) {
          if (func_190552_j() != null && func_190552_j() instanceof EntityTameBase && (!((EntityTameBase)func_190552_j()).func_184191_r((Entity)entitylivingbase) || (entitylivingbase instanceof EntityTameBase && ((EntityTameBase)func_190552_j()).func_184191_r((Entity)entitylivingbase) && ((EntityTameBase)func_190552_j()).getFakeHealth() > 0.0F)))
            damage(entitylivingbase); 
        }  
      if (!this.sentSpikeEvent) {
        this.field_70170_p.func_72960_a((Entity)this, (byte)4);
        this.sentSpikeEvent = true;
      } 
      if (--this.lifeTicks < 0)
        func_70106_y(); 
    } 
  }
  
  private void damage(EntityLivingBase p_190551_1_) {
    EntityLivingBase entitylivingbase = func_190552_j();
    if (p_190551_1_.func_70089_S() && !p_190551_1_.func_190530_aW()) {
      p_190551_1_.field_70172_ad = 0;
      if (entitylivingbase instanceof EntityTameBase && entitylivingbase != null && p_190551_1_ != entitylivingbase) {
        p_190551_1_.func_184185_a(SoundEvents.field_187800_eb, 1.0F, 0.9F);
        func_174815_a(entitylivingbase, (Entity)p_190551_1_);
        p_190551_1_.field_70181_x += this.field_70146_Z.nextDouble();
        ((EntityTameBase)entitylivingbase).inflictEngenderMobDamage(p_190551_1_, " was chopped up by ", DamageSource.func_76354_b((Entity)this, (Entity)entitylivingbase), 6.0F);
      } 
    } 
    if (entitylivingbase == null)
      p_190551_1_.func_70097_a(DamageSource.field_76376_m, 8.0F); 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    super.func_70103_a(id);
    if (id == 4) {
      this.clientSideAttackStarted = true;
      if (!func_174814_R())
        this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_191242_bl, func_184176_by(), 1.0F, this.field_70146_Z.nextFloat() * 0.2F + 0.85F, false); 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public float func_190550_a(float p_190550_1_) {
    if (!this.clientSideAttackStarted)
      return 0.0F; 
    int i = this.lifeTicks - 2;
    return (i <= 0) ? 1.0F : (1.0F - (i - p_190550_1_) / 20.0F);
  }
}
