package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityImplosion extends Entity {
  private static final DataParameter<Integer> IMPLOSIONTIMER = EntityDataManager.func_187226_a(EntityImplosion.class, DataSerializers.field_187192_b);
  
  public EntityTameBase shootingEntity;
  
  public EntityImplosion(World worldIn) {
    super(worldIn);
    this.field_70156_m = true;
    func_70105_a(2.0F, 2.0F);
  }
  
  public EntityImplosion(World worldIn, EntityTameBase entity) {
    this(worldIn);
    this.shootingEntity = entity;
    func_82149_j((Entity)entity);
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  protected void func_70088_a() {
    this.field_70180_af.func_187214_a(IMPLOSIONTIMER, Integer.valueOf(0));
  }
  
  public int getImplosionTime() {
    return ((Integer)this.field_70180_af.func_187225_a(IMPLOSIONTIMER)).intValue();
  }
  
  public void setImplosionTime(int time) {
    this.field_70180_af.func_187227_b(IMPLOSIONTIMER, Integer.valueOf(time));
  }
  
  public void func_70106_y() {
    List<Entity> list = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_186662_g(128.0D));
    this.field_70170_p.func_175718_b(3000, func_180425_c(), 0);
    func_184185_a(ESound.blast, 10.0F, 1.0F);
    func_184185_a(ESound.jzaharshout, 10.0F, 1.0F);
    if (list != null && !list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        double scale = (128.0D - entity.func_70011_f(this.field_70165_t, this.field_70163_u, this.field_70161_v)) / 128.0D;
        Vec3d dir = new Vec3d(entity.field_70165_t - this.field_70165_t, entity.field_70163_u - this.field_70163_u, entity.field_70161_v - this.field_70161_v);
        dir = dir.func_72432_b();
        if (this.shootingEntity != null && (entity == this.shootingEntity || (entity instanceof EntityLivingBase && this.shootingEntity.func_184191_r(entity)))) {
          entity.field_70172_ad = 100;
          if (entity instanceof EntityLivingBase)
            ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_76429_m, 100, 4)); 
        } else if (entity.func_70089_S()) {
          if (entity.func_70068_e(this) <= 25.0D) {
            entity.field_70172_ad = 0;
            entity.func_70097_a(DamageSource.field_180137_b, 100.0F);
            if (EngenderConfig.general.useMessage && this.shootingEntity != null && entity instanceof EntityLivingBase && !entity.func_70089_S() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was blasted apart by an Implosion thanks to " + this.shootingEntity.func_70005_c_() + " (" + this.shootingEntity.getOwner().func_70005_c_() + ")", new Object[0])); 
          } 
          entity.func_70024_g(dir.field_72450_a * 10.0D * scale, 2.0D + this.field_70146_Z.nextDouble(), dir.field_72449_c * 10.0D * scale);
        } 
      }  
    super.func_70106_y();
  }
  
  public void func_70071_h_() {
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    func_70091_d(MoverType.SELF, 0.0D, 0.05D, 0.0D);
    setImplosionTime(getImplosionTime() + 1);
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    float size = 128.0F;
    List<Entity> list = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_186662_g(size));
    if (getImplosionTime() > 500)
      func_70106_y(); 
    if (getImplosionTime() == 1)
      func_184185_a(ACSounds.jzahar_charge, 10.0F, 1.0F); 
    if (list != null && !list.isEmpty())
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        double scale = (size - entity.func_70011_f(this.field_70165_t, this.field_70163_u, this.field_70161_v)) / size;
        Vec3d dir = new Vec3d(entity.field_70165_t - this.field_70165_t, entity.field_70163_u - this.field_70163_u, entity.field_70161_v - this.field_70161_v);
        dir = dir.func_72432_b();
        if (this.shootingEntity == null && entity instanceof EntityJzahar)
          this.shootingEntity = (EntityJzahar)entity; 
        if (entity.func_70089_S() && !(entity instanceof EntityJzahar) && this.shootingEntity != null && entity instanceof EntityLivingBase && !this.shootingEntity.func_184191_r(entity)) {
          entity.func_70024_g(dir.field_72450_a * -getImplosionTime() * 0.001D * scale, dir.field_72448_b * -getImplosionTime() * 0.001D * scale, dir.field_72449_c * -getImplosionTime() * 0.001D * scale);
          if (entity.func_70068_e(this) <= 4.0D) {
            entity.func_70097_a(DamageSource.field_180137_b, 4.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.func_70089_S() && this.shootingEntity != null && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was electricuted by " + this.shootingEntity.func_70005_c_() + " (" + this.shootingEntity.getOwner().func_70005_c_() + ")", new Object[0])); 
          } 
        } 
      }  
  }
  
  protected void func_70014_b(NBTTagCompound compound) {}
  
  protected void func_70037_a(NBTTagCompound compound) {}
  
  public EnumPushReaction func_184192_z() {
    return EnumPushReaction.IGNORE;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_70112_a(double distance) {
    return true;
  }
}
