package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.item.MBItems;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEndersoulFragment extends Entity {
  public static final Predicate<Entity> IS_VALID_TARGET;
  
  static {
    IS_VALID_TARGET = (entity -> (EntitySelectors.field_188444_d.apply(entity) && entity.func_70067_L()));
  }
  
  private static final DataParameter<Boolean> TAMED = EntityDataManager.func_187226_a(EntityEndersoulFragment.class, DataSerializers.field_187198_h);
  
  private int explodeTick = 20 + this.field_70146_Z.nextInt(20);
  
  public final float[][] stickRotations = new float[8][3];
  
  private EntityMutantEnderman owner;
  
  private EntityPlayer collector;
  
  public EntityEndersoulFragment(World world) {
    super(world);
    for (int i = 0; i < this.stickRotations.length; i++) {
      for (int j = 0; j < (this.stickRotations[i]).length; j++)
        this.stickRotations[i][j] = this.field_70146_Z.nextFloat() * 2.0F * 3.1415927F; 
    } 
    func_70105_a(0.75F, 0.75F);
  }
  
  public EntityEndersoulFragment(World world, EntityMutantEnderman owner) {
    this(world);
    this.owner = owner;
  }
  
  protected void func_70088_a() {
    this.field_70180_af.func_187214_a(TAMED, Boolean.valueOf(false));
  }
  
  public boolean isTamed() {
    return ((Boolean)this.field_70180_af.func_187225_a(TAMED)).booleanValue();
  }
  
  public void setTamed(boolean tamed) {
    this.field_70180_af.func_187227_b(TAMED, Boolean.valueOf(tamed));
  }
  
  public EntityPlayer getCollector() {
    return this.collector;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public boolean func_70067_L() {
    return func_70089_S();
  }
  
  public boolean func_70104_M() {
    return func_70089_S();
  }
  
  public boolean func_70075_an() {
    return !isTamed();
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 3)
      EntityUtil.spawnEndersoulParticles(this, 64, 0.8F); 
  }
  
  private void explode() {
    func_184185_a(MBSoundEvents.ENTITY_ENDERSOUL_FRAGMENT_EXPLODE, 1.0F, 1.5F);
    this.field_70170_p.func_72960_a(this, (byte)3);
    for (Entity entity : this.field_70170_p.func_175674_a(this, func_174813_aQ().func_186662_g(5.0D), IS_VALID_TARGET)) {
      if (func_70068_e(entity) <= 25.0D) {
        boolean protectedPlayer = isProtected(entity);
        if (entity instanceof EntityLivingBase && this.owner != null && !protectedPlayer)
          this.owner.inflictEngenderMobDamage((EntityLivingBase)entity, "'s soul was flaied by the soul fragments of ", (DamageSource)new EntityDamageSource("thrown", (Entity)this.owner), 1.0F); 
        if (!protectedPlayer) {
          double x = entity.field_70165_t - this.field_70165_t;
          double z = entity.field_70161_v - this.field_70161_v;
          double d = Math.sqrt(x * x + z * z);
          entity.field_70159_w = 0.800000011920929D * x / d;
          entity.field_70181_x = (this.field_70146_Z.nextFloat() * 0.6F - 0.1F);
          entity.field_70179_y = 0.800000011920929D * z / d;
          EntityUtil.sendPlayerVelocityPacket(entity);
        } 
      } 
    } 
    func_70106_y();
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (this.collector == null && this.field_70181_x > -0.05000000074505806D && !func_189652_ae())
      this.field_70181_x = Math.max(-0.05000000074505806D, this.field_70181_x - 0.10000000149011612D); 
    func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
    this.field_70159_w *= 0.9D;
    this.field_70181_x *= 0.9D;
    this.field_70179_y *= 0.9D;
    if (this.collector != null && !this.collector.func_70089_S())
      this.collector = null; 
    if (!this.field_70170_p.field_72995_K) {
      if (!isTamed() && --this.explodeTick == 0)
        explode(); 
      if (this.collector != null && func_70068_e((Entity)this.collector) > 9.0D) {
        float scale = 0.05F;
        func_70024_g((this.collector.field_70165_t - this.field_70165_t) * scale, (this.collector.field_70163_u + (this.collector.field_70131_O / 3.0F) - this.field_70163_u) * scale, (this.collector.field_70161_v - this.field_70161_v) * scale);
      } 
    } 
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    if (isTamed()) {
      if (this.collector == null && !player.func_70093_af()) {
        this.collector = player;
        func_184185_a(SoundEvents.field_187604_bf, 1.0F, 1.0F);
        return true;
      } 
      if (this.collector == player && player.func_70093_af()) {
        this.collector = null;
        func_184185_a(SoundEvents.field_187604_bf, 1.0F, 1.5F);
        return true;
      } 
      return false;
    } 
    if (!this.field_70170_p.field_72995_K)
      setTamed(true); 
    this.collector = player;
    func_184185_a(SoundEvents.field_187802_ec, 1.0F, 1.5F);
    return true;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (!this.field_70170_p.field_72995_K && func_70089_S())
      explode(); 
    return true;
  }
  
  public boolean isProtected(Entity entity) {
    if (!(entity instanceof EntityLivingBase))
      return false; 
    EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
    if (this.owner != null && this.owner.func_184191_r((Entity)entityLivingBase))
      return false; 
    return (entityLivingBase.func_184614_ca().func_77973_b() == MBItems.ENDERSOUL_HAND || entityLivingBase.func_184592_cb().func_77973_b() == MBItems.ENDERSOUL_HAND);
  }
  
  protected void func_70014_b(NBTTagCompound compound) {
    compound.func_74757_a("Tamed", isTamed());
    compound.func_74768_a("ExplodeTick", this.explodeTick);
  }
  
  protected void func_70037_a(NBTTagCompound compound) {
    if (compound.func_74764_b("ExplodeTick"))
      this.explodeTick = compound.func_74762_e("ExplodeTick"); 
    setTamed((compound.func_74767_n("Collected") || compound.func_74767_n("Tamed")));
  }
}
