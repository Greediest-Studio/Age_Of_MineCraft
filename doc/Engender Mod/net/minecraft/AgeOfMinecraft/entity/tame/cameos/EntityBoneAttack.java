package net.minecraft.AgeOfMinecraft.entity.tame.cameos;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBoneAttack extends Entity {
  private static final DataParameter<Boolean> ISBLUE = EntityDataManager.func_187226_a(EntityBoneAttack.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Integer> BONETYPE = EntityDataManager.func_187226_a(EntityBoneAttack.class, DataSerializers.field_187192_b);
  
  public EntitySans shootingEntity;
  
  private int ticksAlive;
  
  public double accelerationX;
  
  public double accelerationY;
  
  public double accelerationZ;
  
  public EntityBoneAttack(World worldIn) {
    super(worldIn);
    func_70105_a(0.325F, 0.325F);
  }
  
  protected void func_70088_a() {
    func_184212_Q().func_187214_a(ISBLUE, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(BONETYPE, Integer.valueOf(0));
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_70112_a(double distance) {
    double d0 = func_174813_aQ().func_72320_b() * 4.0D;
    if (Double.isNaN(d0))
      d0 = 4.0D; 
    d0 *= 64.0D;
    return (distance < d0 * d0);
  }
  
  public EntityBoneAttack(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
    super(worldIn);
    func_70012_b(x, y, z, this.field_70177_z, this.field_70125_A);
    func_70107_b(x, y, z);
    double d0 = MathHelper.func_76133_a(accelX * accelX + accelY * accelY + accelZ * accelZ);
    this.accelerationX = accelX / d0 * 0.5D;
    this.accelerationY = accelY / d0 * 0.5D;
    this.accelerationZ = accelZ / d0 * 0.5D;
  }
  
  public EntityBoneAttack(World worldIn, EntitySans shooter, double accelX, double accelY, double accelZ) {
    super(worldIn);
    this.shootingEntity = shooter;
    func_70012_b(shooter.field_70165_t, shooter.field_70163_u, shooter.field_70161_v, shooter.field_70177_z, shooter.field_70125_A);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    this.field_70159_w = 0.0D;
    this.field_70181_x = 0.0D;
    this.field_70179_y = 0.0D;
    accelX += this.field_70146_Z.nextGaussian() * 0.5D;
    accelY += this.field_70146_Z.nextGaussian() * 0.5D;
    accelZ += this.field_70146_Z.nextGaussian() * 0.5D;
    double d0 = MathHelper.func_76133_a(accelX * accelX + accelY * accelY + accelZ * accelZ);
    this.accelerationX = accelX / d0 * 0.3D;
    this.accelerationY = accelY / d0 * 0.3D;
    this.accelerationZ = accelZ / d0 * 0.3D;
  }
  
  public void func_70071_h_() {
    switch (getBoneType()) {
      case 0:
        func_70105_a(0.5F, 0.5F);
        break;
      default:
        func_70105_a(0.325F, 2.0F);
        break;
    } 
    if (getBoneType() == 0)
      ProjectileHelper.func_188803_a(this, 0.9F); 
    if (this.field_70173_aa > 140)
      func_70106_y(); 
    if (this.field_70170_p.field_72995_K || ((this.shootingEntity == null || !this.shootingEntity.field_70128_L) && this.field_70170_p.func_175667_e(new BlockPos(this)))) {
      super.func_70071_h_();
      AxisAlignedBB hitbox = new AxisAlignedBB(-0.1625D, -0.1625D, -0.1625D, 0.1625D, 0.1625D, 0.1625D);
      switch (getBoneType()) {
        case 0:
          hitbox.func_186662_g(0.5D);
          break;
        default:
          hitbox = new AxisAlignedBB(-0.1625D, 0.0D, -0.1625D, 0.1625D, 2.0D, 0.1625D);
          break;
      } 
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(0.25D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (!list.isEmpty())
        for (EntityLivingBase entity1 : list) {
          if (this.shootingEntity != null && entity1 != null && entity1.func_70089_S() && !this.shootingEntity.func_184191_r((Entity)entity1))
            onImpact(new RayTraceResult((Entity)entity1)); 
        }  
      this.field_70165_t += this.field_70159_w;
      this.field_70163_u += this.field_70181_x;
      this.field_70161_v += this.field_70179_y;
      float f = getMotionFactor();
      this.field_70159_w += this.accelerationX;
      this.field_70181_x += this.accelerationY;
      this.field_70179_y += this.accelerationZ;
      this.field_70159_w *= f;
      this.field_70181_x *= f;
      this.field_70179_y *= f;
      func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    } else {
      func_70106_y();
    } 
  }
  
  protected float getMotionFactor() {
    switch (getBoneType()) {
      case 1:
        return 0.95F;
      case 2:
        return 0.975F;
      case 3:
        return 0.8F;
      case 4:
        return 0.7F;
    } 
    return 0.99F;
  }
  
  public boolean func_180427_aV() {
    return true;
  }
  
  protected void onImpact(RayTraceResult movingObject) {
    if (!this.field_70170_p.field_72995_K && getBoneType() != 4)
      if (movingObject.field_72308_g != null && movingObject.field_72308_g instanceof EntityLivingBase && (!isBlue() || movingObject.field_72308_g.field_70159_w != 0.0D || movingObject.field_72308_g.field_70181_x != 0.0D || movingObject.field_72308_g.field_70179_y != 0.0D))
        if (this.shootingEntity != null && this.shootingEntity instanceof net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase)
          if (movingObject.field_72308_g.func_70089_S() && !this.shootingEntity.func_184191_r(movingObject.field_72308_g)) {
            movingObject.field_72308_g.field_70172_ad = 0;
            func_184185_a(ESound.bonehit, 1.0F, 1.0F);
            this.shootingEntity.inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was boned by ", (new DamageSource("sans")).func_76348_h().func_151518_m().func_82726_p(), 1.0F);
            this.shootingEntity.attackWithAdditionalEffects((Entity)this.shootingEntity);
            this.shootingEntity.karmicRetribution((EntityLivingBase)movingObject.field_72308_g, isBlue() ? 6 : 5);
          }    
  }
  
  public boolean isBlue() {
    return ((Boolean)this.field_70180_af.func_187225_a(ISBLUE)).booleanValue();
  }
  
  public void setBlue(boolean blue) {
    this.field_70180_af.func_187227_b(ISBLUE, Boolean.valueOf(blue));
  }
  
  public int getBoneType() {
    return ((Integer)this.field_70180_af.func_187225_a(BONETYPE)).intValue();
  }
  
  public void setBoneType(int age) {
    this.field_70180_af.func_187227_b(BONETYPE, Integer.valueOf(age));
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    compound.func_74782_a("direction", (NBTBase)func_70087_a(new double[] { this.field_70159_w, this.field_70181_x, this.field_70179_y }));
    compound.func_74782_a("power", (NBTBase)func_70087_a(new double[] { this.accelerationX, this.accelerationY, this.accelerationZ }));
    compound.func_74768_a("life", this.ticksAlive);
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    if (compound.func_150297_b("power", 9)) {
      NBTTagList nbttaglist = compound.func_150295_c("power", 6);
      if (nbttaglist.func_74745_c() == 3) {
        this.accelerationX = nbttaglist.func_150309_d(0);
        this.accelerationY = nbttaglist.func_150309_d(1);
        this.accelerationZ = nbttaglist.func_150309_d(2);
      } 
    } 
    this.ticksAlive = compound.func_74762_e("life");
    if (compound.func_150297_b("direction", 9) && compound.func_150295_c("direction", 6).func_74745_c() == 3) {
      NBTTagList nbttaglist1 = compound.func_150295_c("direction", 6);
      this.field_70159_w = nbttaglist1.func_150309_d(0);
      this.field_70181_x = nbttaglist1.func_150309_d(1);
      this.field_70179_y = nbttaglist1.func_150309_d(2);
    } 
  }
  
  public boolean func_70067_L() {
    return false;
  }
  
  public float func_70111_Y() {
    return 1.0F;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  public float func_70013_c() {
    return 1.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
}
