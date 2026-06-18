package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityMutantSkeletonArrow extends Entity {
  private static final DataParameter<Integer> TARGET_X = EntityDataManager.func_187226_a(EntityMutantSkeletonArrow.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TARGET_Y = EntityDataManager.func_187226_a(EntityMutantSkeletonArrow.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> TARGET_Z = EntityDataManager.func_187226_a(EntityMutantSkeletonArrow.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Float> SPEED = EntityDataManager.func_187226_a(EntityMutantSkeletonArrow.class, DataSerializers.field_187193_c);
  
  private static final DataParameter<Integer> CLONES = EntityDataManager.func_187226_a(EntityMutantSkeletonArrow.class, DataSerializers.field_187192_b);
  
  private int damage = 10 + this.field_70146_Z.nextInt(3);
  
  private final List<Entity> pointedEntities = new ArrayList<>();
  
  private PotionEffect potionEffect;
  
  private EntityTameBase shooter;
  
  public EntityMutantSkeletonArrow(World world) {
    super(world);
    this.field_70145_X = true;
  }
  
  public EntityMutantSkeletonArrow(World world, EntityTameBase shooter, EntityLivingBase target) {
    this(world);
    this.shooter = shooter;
    if (!world.field_72995_K) {
      setTargetX(target.field_70165_t);
      setTargetY(target.field_70163_u);
      setTargetZ(target.field_70161_v);
    } 
    this.pointedEntities.add(target);
    double yPos = shooter.field_70163_u + shooter.func_70047_e();
    if (shooter instanceof EntityMutantSkeleton)
      yPos -= shooter.getFittness(); 
    func_70107_b(shooter.field_70165_t, yPos, shooter.field_70161_v);
    double x = getTargetX() - this.field_70165_t;
    double y = getTargetY() - this.field_70163_u;
    double z = getTargetZ() - this.field_70161_v;
    double d = Math.sqrt(x * x + z * z);
    this.field_70177_z = 180.0F + (float)Math.toDegrees(Math.atan2(x, z));
    this.field_70125_A = (float)Math.toDegrees(Math.atan2(y, d));
  }
  
  protected void func_70088_a() {
    this.field_70180_af.func_187214_a(TARGET_X, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(TARGET_Y, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(TARGET_Z, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(SPEED, Float.valueOf(12.0F));
    this.field_70180_af.func_187214_a(CLONES, Integer.valueOf(10));
  }
  
  public double getTargetX() {
    return ((Integer)this.field_70180_af.func_187225_a(TARGET_X)).intValue() / 10000.0D;
  }
  
  public void setTargetX(double x) {
    this.field_70180_af.func_187227_b(TARGET_X, Integer.valueOf((int)(x * 10000.0D)));
  }
  
  public double getTargetY() {
    return ((Integer)this.field_70180_af.func_187225_a(TARGET_Y)).intValue() / 10000.0D;
  }
  
  public void setTargetY(double y) {
    this.field_70180_af.func_187227_b(TARGET_Y, Integer.valueOf((int)(y * 10000.0D)));
  }
  
  public double getTargetZ() {
    return ((Integer)this.field_70180_af.func_187225_a(TARGET_Z)).intValue() / 10000.0D;
  }
  
  public void setTargetZ(double z) {
    this.field_70180_af.func_187227_b(TARGET_Z, Integer.valueOf((int)(z * 10000.0D)));
  }
  
  public float getSpeed() {
    return ((Float)this.field_70180_af.func_187225_a(SPEED)).floatValue() / 10.0F;
  }
  
  public void setSpeed(float speed) {
    this.field_70180_af.func_187227_b(SPEED, Float.valueOf(speed * 10.0F));
  }
  
  public int getClones() {
    return ((Integer)this.field_70180_af.func_187225_a(CLONES)).intValue();
  }
  
  public void setClones(int clones) {
    this.field_70180_af.func_187227_b(CLONES, Integer.valueOf(clones));
  }
  
  public void randomize(float scale) {
    setTargetX(getTargetX() + ((this.field_70146_Z.nextFloat() - 0.5F) * scale * 2.0F));
    setTargetY(getTargetY() + ((this.field_70146_Z.nextFloat() - 0.5F) * scale * 2.0F));
    setTargetZ(getTargetZ() + ((this.field_70146_Z.nextFloat() - 0.5F) * scale * 2.0F));
  }
  
  public void setDamage(int damage) {
    this.damage = damage;
  }
  
  public void setPotionEffect(PotionEffect effect) {
    this.potionEffect = effect;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    double x = getTargetX() - this.field_70165_t;
    double y = getTargetY() - this.field_70163_u;
    double z = getTargetZ() - this.field_70161_v;
    double d = Math.sqrt(x * x + z * z);
    this.field_70177_z = 180.0F + (float)Math.toDegrees(Math.atan2(x, z));
    if (this.field_70177_z > 360.0F)
      this.field_70177_z -= 360.0F; 
    this.field_70125_A = (float)Math.toDegrees(Math.atan2(y, d));
    if (!this.field_70170_p.field_72995_K) {
      if (this.field_70173_aa == 2)
        hitEntities(0); 
      if (this.field_70173_aa == 3)
        hitEntities(32); 
      if (this.field_70173_aa == 4)
        hitEntities(64); 
      if (this.field_70173_aa == 5)
        handleEntities(); 
    } 
    if (this.field_70173_aa > 10 || this.shooter == null)
      func_70106_y(); 
  }
  
  protected void hitEntities(int offset) {
    double targetX = getTargetX();
    double targetY = getTargetY();
    double targetZ = getTargetZ();
    double d3 = this.field_70165_t - targetX;
    double d4 = this.field_70163_u - targetY;
    double d5 = this.field_70161_v - targetZ;
    double dist = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
    double dx = (targetX - this.field_70165_t) / dist;
    double dy = (targetY - this.field_70163_u) / dist;
    double dz = (targetZ - this.field_70161_v) / dist;
    for (int i = offset; i < offset + 64; i++) {
      double x = this.field_70165_t + dx * i * 0.5D;
      double y = this.field_70163_u + dy * i * 0.5D;
      double z = this.field_70161_v + dz * i * 0.5D;
      AxisAlignedBB box = (new AxisAlignedBB(x, y, z, x, y, z)).func_186662_g(0.3D);
      this.pointedEntities.addAll(this.field_70170_p.func_175674_a((Entity)this.shooter, box, Entity::func_70067_L));
    } 
  }
  
  protected void handleEntities() {
    this.pointedEntities.remove(this);
    for (Entity entity : this.pointedEntities) {
      DamageSource damageSource = (new EntityDamageSourceIndirect("arrow", this, (Entity)this.shooter) {
          public Vec3d func_188404_v() {
            return null;
          }
        }).func_76349_b();
      if (entity instanceof net.minecraft.entity.MultiPartEntityPart)
        damageSource.func_94540_d(); 
      if (this.shooter != null && entity instanceof EntityLivingBase && !this.shooter.func_184191_r(entity)) {
        if (entity.field_70172_ad <= 0)
          this.field_70170_p.func_184148_a(null, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, SoundEvents.field_187731_t, func_184176_by(), 1.0F, 1.2F / (this.field_70146_Z.nextFloat() * 0.2F + 0.9F)); 
        entity.field_70172_ad--;
        this.shooter.inflictEngenderMobDamage((EntityLivingBase)entity, " was shot by ", damageSource, this.damage);
        if (this.potionEffect != null)
          this.shooter.inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)entity, this.potionEffect.func_188419_a(), this.potionEffect.func_76459_b(), this.potionEffect.func_76458_c()); 
      } 
    } 
    this.pointedEntities.clear();
  }
  
  public boolean func_82150_aj() {
    return true;
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    compound.func_74768_a("TicksExisted", this.field_70173_aa);
    compound.func_74782_a("Target", (NBTBase)func_70087_a(new double[] { getTargetX(), getTargetY(), getTargetZ() }));
    compound.func_74776_a("Speed", getSpeed());
    compound.func_74768_a("Clones", getClones());
    compound.func_74768_a("Damage", this.damage);
    if (this.potionEffect != null)
      compound.func_74782_a("Effect", (NBTBase)this.potionEffect.func_82719_a(new NBTTagCompound())); 
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    this.field_70173_aa = compound.func_74762_e("TicksExisted");
    setSpeed(compound.func_74760_g("Speed"));
    setClones(compound.func_74762_e("Clones"));
    this.damage = compound.func_74762_e("Damage");
    if (compound.func_150297_b("Target", 9) && compound.func_150295_c("Target", 6).func_74745_c() == 3) {
      NBTTagList nbtTagList = compound.func_150295_c("Target", 6);
      setTargetX(nbtTagList.func_150309_d(0));
      setTargetY(nbtTagList.func_150309_d(1));
      setTargetZ(nbtTagList.func_150309_d(2));
    } 
    if (compound.func_150297_b("Effect", 10))
      this.potionEffect = PotionEffect.func_82722_b(compound); 
  }
}
