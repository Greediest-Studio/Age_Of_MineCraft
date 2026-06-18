package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import com.brandon3055.brandonscore.client.particle.BCEffectHandler;
import com.brandon3055.brandonscore.utils.FilterUtils;
import com.brandon3055.brandonscore.utils.Teleporter;
import com.brandon3055.brandonscore.utils.Utils;
import com.brandon3055.draconicevolution.client.DEParticles;
import com.brandon3055.draconicevolution.lib.DESoundHandler;
import java.util.Arrays;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacle;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormTentacleDevourer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGuardianProjectile extends Entity {
  protected static final DataParameter<Byte> TYPE = EntityDataManager.func_187226_a(EntityGuardianProjectile.class, DataSerializers.field_187191_a);
  
  public int type = 0;
  
  public EntityLivingBase target;
  
  public Entity shooter;
  
  public float power;
  
  public boolean isChaser;
  
  private double lastTickTargetDistance = 100.0D;
  
  private float heath = 5.0F;
  
  private DamageSource damageFireball = (new DamageSource("de.GuardianFireball")).func_76359_i().func_82726_p().func_94540_d();
  
  private DamageSource damageEnergy = (new DamageSource("de.GuardianEnergyBall")).func_76359_i().func_76348_h();
  
  private DamageSource damageChaos = (new DamageSource("de.GuardianChaosBall")).func_76359_i().func_76348_h().func_151518_m();
  
  public static final int FIREBOMB = 1;
  
  public static final int TELEPORT = 2;
  
  public static final int FIRE_CHASER = 3;
  
  public static final int ENERGY_CHASER = 4;
  
  public static final int CHAOS_CHASER = 5;
  
  public static final int MINI_CHAOS_CHASER = 6;
  
  public EntityGuardianProjectile(World world) {
    this(world, 0, (EntityLivingBase)null, 10.0F, (Entity)null);
  }
  
  public EntityGuardianProjectile(World world, int type, EntityLivingBase target, float power, Entity shooter) {
    super(world);
    this.type = type;
    this.target = target;
    this.shooter = shooter;
    this.power = power;
    this.isChaser = (type == 3 || type == 4 || type == 5 || type == 6);
    func_70105_a(1.0F, 1.0F);
    if (shooter != null) {
      if (!world.field_72995_K)
        DESoundHandler.playSoundFromServer(world, shooter.field_70165_t + 0.5D, shooter.field_70163_u + 0.5D, shooter.field_70161_v + 0.5D, SoundEvents.field_187527_aQ, SoundCategory.HOSTILE, 10.0F, this.field_70146_Z.nextFloat() * 0.3F + 0.85F, false, 256.0D); 
      this.field_70177_z = (shooter instanceof EntityChaosGuardian) ? (shooter.field_70177_z + 180.0F) : shooter.field_70177_z;
      this.field_70125_A = shooter.field_70125_A;
      if (type == 1 || type == 2) {
        this.field_70125_A += (this.field_70146_Z.nextFloat() - 0.5F) * 20.0F;
        this.field_70177_z += (this.field_70146_Z.nextFloat() - 0.5F) * 20.0F;
      } 
      if (shooter instanceof EntityChaosGuardian) {
        this.field_70159_w = (-MathHelper.func_76126_a(this.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F));
        this.field_70179_y = (MathHelper.func_76134_b(this.field_70177_z / 180.0F * 3.1415927F) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * 3.1415927F));
        this.field_70181_x = -MathHelper.func_76126_a(this.field_70125_A / 180.0F * 3.1415927F);
      } else if (target != null) {
        double d6 = target.field_70165_t - this.field_70165_t;
        double d7 = target.field_70163_u - this.field_70163_u;
        double d8 = target.field_70161_v - this.field_70161_v;
        double d9 = d6 * d6 + d7 * d7 + d8 * d8;
        this.field_70159_w = d6 / d9;
        this.field_70181_x = d7 / d9;
        this.field_70179_y = d8 / d9;
      } 
      double speed = 5.0D;
      this.field_70159_w *= speed;
      this.field_70181_x *= speed;
      this.field_70179_y *= speed;
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_70112_a(double distance) {
    double d0 = func_174813_aQ().func_72320_b();
    if (Double.isNaN(d0))
      d0 = 1.0D; 
    d0 = d0 * 64.0D * 100.0D;
    return (distance < d0 * d0);
  }
  
  protected void func_70088_a() {
    if (this.type == 4 || this.type == 5 || this.type == 6 || this.field_70170_p.field_72995_K)
      this.field_70145_X = true; 
    this.field_70180_af.func_187214_a(TYPE, Byte.valueOf((byte)this.type));
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (this.target instanceof EntityWitherStormHead && ((EntityWitherStormHead)this.target).residentWitherStorm != null)
      this.target = (EntityLivingBase)((EntityWitherStormHead)this.target).residentWitherStorm; 
    if (this.target instanceof EntityWitherStormTentacle && ((EntityWitherStormTentacle)this.target).residentWitherStorm != null)
      this.target = (EntityLivingBase)((EntityWitherStormTentacle)this.target).residentWitherStorm; 
    if (this.target instanceof EntityWitherStormTentacleDevourer && ((EntityWitherStormTentacleDevourer)this.target).residentWitherStorm != null)
      this.target = (EntityLivingBase)((EntityWitherStormTentacleDevourer)this.target).residentWitherStorm; 
    if (!this.field_70170_p.field_72995_K && this.field_70173_aa == 1)
      this.field_70180_af.func_187227_b(TYPE, Byte.valueOf((byte)this.type)); 
    if (this.field_70170_p.field_72995_K) {
      if (this.type == 0)
        this.type = ((Byte)this.field_70180_af.func_187225_a(TYPE)).byteValue(); 
      spawnParticle();
    } 
    if (this.shooter != null && this.shooter instanceof EntityTameBase && !((EntityTameBase)this.shooter).isWild() && ((EntityTameBase)this.shooter).getOwner().func_110143_aJ() <= ((EntityTameBase)this.shooter).getOwner().func_110138_aP() / 2.0F)
      this.target = ((EntityTameBase)this.shooter).getOwner(); 
    if (this.shooter != null && this.target != null && this.target == this.shooter && func_70032_d((Entity)this.target) <= 6.0D)
      func_70106_y(); 
    if ((this.target == null || (this.target != null && !this.target.func_70089_S())) && this.shooter != null && this.shooter instanceof EntityTameBase) {
      List<EntityLivingBase> entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(100.0D));
      if (!entities.isEmpty()) {
        for (int i = 0; i < entities.size(); i++) {
          EntityLivingBase entity = entities.get(this.field_70146_Z.nextInt(entities.size()));
          if (entity.func_70089_S() && entity instanceof EntityLivingBase && !((EntityTameBase)this.shooter).func_184191_r((Entity)entity))
            this.target = entity; 
        } 
      } else {
        this.target = (EntityLivingBase)this.shooter;
      } 
    } 
    if (this.target != null && !this.target.func_70089_S())
      this.target = (this.shooter != null) ? (EntityLivingBase)this.shooter : null; 
    this.field_70145_X = true;
    if (!this.field_70170_p.field_72995_K)
      if (this.target != null) {
        double tDist = Utils.getDistanceAtoB(this.target.field_70165_t, this.target.field_70163_u, this.target.field_70161_v, this.field_70165_t, this.field_70163_u, this.field_70161_v);
        double x = (this.target.field_70165_t - this.field_70165_t) / tDist;
        double y = (this.target.field_70163_u - this.field_70163_u) / tDist;
        double z = (this.target.field_70161_v - this.field_70161_v) / tDist;
        double speed = (this.type == 5) ? 0.15D : 0.1D;
        this.field_70159_w /= 1.1D;
        this.field_70181_x /= 1.1D;
        this.field_70179_y /= 1.1D;
        this.field_70159_w += x * speed;
        this.field_70181_x += y * speed;
        this.field_70179_y += z * speed;
        checkTargetCondition();
      }  
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
  }
  
  private boolean checkTargetCondition() {
    if (this.field_70170_p.field_72995_K)
      return false; 
    double targetDistance = Utils.getDistanceAtoB(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.target.field_70165_t, this.target.field_70163_u, this.target.field_70161_v);
    EntityLivingBase entityLivingBase = this.target;
    boolean genericHit = (entityLivingBase != null && targetDistance < this.power);
    switch (this.type) {
      case 1:
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.field_70132_H || this.field_70173_aa > 600 || this.heath <= 0.0F) {
          EntityTameBase.createEngenderModExplosion(this.shooter, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0F, true, EngenderConfig.mobs.grief);
          damageEntitiesInRadius(this.damageFireball, this.power, this.power * 2.0F);
          func_70106_y();
        } 
        break;
      case 2:
        if (genericHit) {
          EntityLivingBase entityLivingBase1 = (entityLivingBase != null) ? entityLivingBase : this.target;
          int r = this.field_70146_Z.nextInt();
          if (this.shooter != null && this.shooter instanceof EntityTameBase && entityLivingBase instanceof EntityLivingBase && !((EntityTameBase)this.shooter).func_184191_r((Entity)entityLivingBase))
            if (entityLivingBase1 instanceof EntityTameBase && ((EntityTameBase)entityLivingBase1).getTier() == EnumTier.TIER6) {
              (new Teleporter.TeleportLocation(this.shooter.field_70165_t + Math.cos(r) * 60.0D, this.field_70146_Z.nextInt(255), this.shooter.field_70161_v + Math.sin(r) * 60.0D, ((Entity)entityLivingBase1).field_71093_bK)).teleport((Entity)entityLivingBase1);
              damageEntitiesInRadius(this.damageFireball, this.power, this.power);
            } else {
              (new Teleporter.TeleportLocation(this.shooter.field_70165_t + Math.cos(r) * 600.0D, this.field_70146_Z.nextInt(255), this.shooter.field_70161_v + Math.sin(r) * 600.0D, ((Entity)entityLivingBase1).field_71093_bK)).teleport((Entity)entityLivingBase1);
              damageEntitiesInRadius(this.damageFireball, this.power, this.power * 2.0F);
            }  
          func_70106_y();
        } 
        break;
      case 3:
        this.field_70145_X = (this.field_70173_aa < 60);
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < (this.power / 2.0F)) || (this.field_70132_H && this.field_70173_aa > 60) || this.field_70173_aa > 400 || this.heath <= 0.0F) {
          EntityTameBase.createEngenderModExplosion(this.shooter, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0F, true, EngenderConfig.mobs.grief);
          damageEntitiesInRadius(this.damageFireball, this.power, this.power * 2.0F);
          func_70106_y();
        } 
        break;
      case 4:
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.field_70173_aa > 800 || this.heath <= 0.0F) {
          BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[] { 256, func_145782_y(), 0, 255, 255 });
          damageEntitiesInRadius(this.damageEnergy, this.power, this.power * 3.0F);
          this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187539_bB, SoundCategory.HOSTILE, 4.0F, (1.0F + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F, false);
          func_70106_y();
        } 
        break;
      case 5:
        if (genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.field_70173_aa > 800 || this.heath <= 0.0F) {
          BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[] { 256, func_145782_y(), 68, 0, 0 });
          damageEntitiesInRadius(this.damageChaos, this.power, this.power * 3.0F);
          this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187539_bB, SoundCategory.HOSTILE, 4.0F, (1.0F + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F, false);
          List<Entity> list = this.field_70170_p.func_175674_a(this.shooter, func_174813_aQ().func_186662_g(60.0D), FilterUtils.IS_PLAYER);
          for (int i = 3 + this.field_70146_Z.nextInt(3); i > 0; i--) {
            Entity target = (list.size() > 0) ? list.get(this.field_70146_Z.nextInt(list.size())) : null;
            if (!(target instanceof EntityLivingBase))
              target = null; 
            EntityGuardianProjectile newProjectile = new EntityGuardianProjectile(this.field_70170_p, 6, (EntityLivingBase)target, this.power / 2.0F, this.shooter);
            newProjectile.field_70181_x = 0.0D;
            int randDir = this.field_70146_Z.nextInt();
            double speed = 1.0D + this.field_70146_Z.nextDouble() * 5.0D;
            newProjectile.field_70159_w = Math.sin(randDir) * speed;
            newProjectile.field_70179_y = Math.cos(randDir) * speed;
            newProjectile.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
            newProjectile.shooter = this.shooter;
            newProjectile.target = this.target;
            this.field_70170_p.func_72838_d(newProjectile);
          } 
          func_70106_y();
        } 
        break;
      case 6:
        if ((genericHit || (targetDistance > this.lastTickTargetDistance && targetDistance < this.power) || this.field_70173_aa > 800 || this.heath <= 0.0F) && this.field_70173_aa > 5) {
          BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[] { 256, func_145782_y(), 68, 0, 0 });
          damageEntitiesInRadius(this.damageChaos, this.power, this.power * 3.0F);
          this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187539_bB, SoundCategory.HOSTILE, 4.0F, (1.0F + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F, false);
          func_70106_y();
        } 
        break;
    } 
    this.lastTickTargetDistance = targetDistance;
    return false;
  }
  
  private Entity getHitEntity() {
    Vec3d vec31 = new Vec3d(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    Vec3d vec3 = new Vec3d(this.field_70165_t + this.field_70159_w, this.field_70163_u + this.field_70181_x, this.field_70161_v + this.field_70179_y);
    Entity entityHit = null;
    List<Entity> list = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_72314_b(this.field_70159_w, this.field_70181_x, this.field_70179_y).func_72314_b(1.0D, 1.0D, 1.0D));
    double d0 = 0.0D;
    for (int i = 0; i < list.size(); i++) {
      Entity entity1 = list.get(i);
      if (this.target != null && entity1 == this.target && (entity1 != this.shooter || this.field_70173_aa >= 5)) {
        float f1 = 2.0F;
        AxisAlignedBB axisalignedbb1 = entity1.func_174813_aQ().func_72314_b(f1, f1, f1);
        RayTraceResult traceResult = axisalignedbb1.func_72327_a(vec31, vec3);
        if (traceResult != null) {
          double d1 = vec31.func_72438_d(traceResult.field_72307_f);
          if (d1 < d0 || d0 == 0.0D) {
            entityHit = entity1;
            d0 = d1;
          } 
        } 
      } 
    } 
    return (entityHit instanceof EntityGuardianProjectile) ? null : entityHit;
  }
  
  private void damageEntitiesInRadius(DamageSource source, double radius, float damage) {
    if (this.field_70170_p.field_72995_K)
      return; 
    List<EntityLivingBase> entities = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(radius));
    for (EntityLivingBase entityLivingBase : entities) {
      if (entityLivingBase == this.shooter)
        continue; 
      entityLivingBase.field_70172_ad = 0;
      if (this.shooter != null && entityLivingBase.func_70089_S() && this.shooter instanceof EntityTameBase && ((EntityTameBase)this.shooter).func_184191_r((Entity)entityLivingBase)) {
        func_184185_a(DESoundHandler.shieldUp, 10.0F, this.field_70146_Z.nextFloat() * 0.2F + 1.0F);
        entityLivingBase.func_70691_i(10.0F);
        entityLivingBase.func_184589_d(MobEffects.field_76440_q);
        entityLivingBase.func_184589_d(MobEffects.field_188423_x);
        entityLivingBase.func_184589_d(MobEffects.field_76438_s);
        entityLivingBase.func_184589_d(MobEffects.field_188424_y);
        entityLivingBase.func_184589_d(MobEffects.field_76419_f);
        entityLivingBase.func_184589_d(MobEffects.field_76431_k);
        entityLivingBase.func_184589_d(MobEffects.field_76421_d);
        entityLivingBase.func_184589_d(MobEffects.field_189112_A);
        entityLivingBase.func_184589_d(MobEffects.field_76437_t);
        entityLivingBase.func_184589_d(MobEffects.field_82731_v);
        switch (this.type) {
          case 4:
            entityLivingBase.func_70690_d(new PotionEffect(MobEffects.field_76424_c, 1000, 1));
            entityLivingBase.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 1000));
            break;
          case 5:
            entityLivingBase.func_70690_d(new PotionEffect(MobEffects.field_76428_l, 1000, 1));
            break;
          case 6:
            entityLivingBase.func_70690_d(new PotionEffect(MobEffects.field_76428_l, 100));
            break;
          case 3:
            entityLivingBase.func_70066_B();
            entityLivingBase.func_70690_d(new PotionEffect(MobEffects.field_76426_n, 2000));
            break;
          case 2:
            (new Teleporter.TeleportLocation(this.shooter.field_70165_t + Math.cos(this.field_70146_Z.nextInt()) * 200.0D, this.field_70146_Z.nextInt(255), this.shooter.field_70161_v + Math.sin(this.field_70146_Z.nextInt()) * 200.0D, entityLivingBase.field_71093_bK)).teleport((Entity)entityLivingBase);
            break;
          default:
            entityLivingBase.func_70691_i(10.0F);
            break;
        } 
      } 
      if (this.shooter != null && entityLivingBase.func_70089_S() && this.shooter instanceof EntityTameBase && !((EntityTameBase)this.shooter).func_184191_r((Entity)entityLivingBase)) {
        ((EntityTameBase)this.shooter).inflictEngenderMobDamage(entityLivingBase, (source == this.damageChaos) ? " was obliterated by " : ((source == this.damageEnergy) ? " was ionized by " : " was firebombed by "), source, damage / (float)(Utils.getDistanceAtoB(entityLivingBase.field_70165_t, entityLivingBase.field_70163_u, entityLivingBase.field_70161_v, this.field_70165_t, this.field_70163_u, this.field_70161_v) / radius));
        if (source == this.damageChaos && !(entityLivingBase instanceof net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntitySans))
          entityLivingBase.func_70606_j(entityLivingBase.func_110143_aJ() - 1.0F); 
      } 
    } 
  }
  
  public boolean func_70097_a(DamageSource source, float dmg) {
    if (this.heath <= 0.0F)
      return false; 
    if ((source.func_76346_g() instanceof EntityLivingBase || source.func_76346_g() instanceof net.minecraft.entity.projectile.EntityArrow) && this.field_70173_aa > 5)
      this.heath -= dmg; 
    if (source.func_76346_g() instanceof net.minecraft.entity.projectile.EntityArrow)
      source.func_76346_g().func_70106_y(); 
    if (this.heath <= 0.0F) {
      this.field_70170_p.func_72885_a(this, this.field_70165_t, this.field_70163_u, this.field_70161_v, 2.0F, false, false);
      func_70106_y();
    } 
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  private void spawnParticle() {
    int[] colour = getParticleColour();
    if (Arrays.equals(colour, new int[] { 0, 0, 0 }))
      return; 
    BCEffectHandler.spawnFX(DEParticles.GUARDIAN_PROJECTILE, this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, 0.0D, 0.0D, 0.0D, 256.0D, new int[] { func_145782_y(), colour[0], colour[1], colour[2] });
  }
  
  public int[] getParticleColour() {
    switch (this.type) {
      case 1:
        return new int[] { 255, 102, 0 };
      case 2:
        return new int[] { 0, 0, 0 };
      case 3:
        return new int[] { 255, 102, 0 };
      case 4:
        return new int[] { 0, 255, 255 };
      case 5:
        return new int[] { 68, 0, 0 };
      case 6:
        return new int[] { 68, 0, 0 };
    } 
    return new int[] { 0, 0, 0 };
  }
  
  public void func_70030_z() {
    super.func_70030_z();
  }
  
  public boolean func_70067_L() {
    return true;
  }
  
  public float func_70111_Y() {
    return 1.0F;
  }
  
  protected void func_70037_a(NBTTagCompound compound) {
    this.type = compound.func_74762_e("Type");
    if (!this.field_70170_p.field_72995_K)
      this.field_70180_af.func_187227_b(TYPE, Byte.valueOf((byte)this.type)); 
    this.field_70145_X = (this.type == 4 || this.type == 5 || this.type == 6);
    this.isChaser = (this.type == 3 || this.type == 4 || this.type == 5 || this.type == 6);
  }
  
  protected void func_70014_b(NBTTagCompound compound) {
    compound.func_74768_a("Type", this.type);
  }
}
