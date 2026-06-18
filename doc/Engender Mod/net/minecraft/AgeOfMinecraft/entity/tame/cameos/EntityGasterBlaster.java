package net.minecraft.AgeOfMinecraft.entity.tame.cameos;

import java.util.Collections;
import java.util.List;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGasterBlaster extends EntityLiving {
  public static final DataParameter<Byte> STATUS = EntityDataManager.func_187226_a(EntityGasterBlaster.class, DataSerializers.field_187191_a);
  
  public int warmUpTime;
  
  public int fireLength;
  
  public EntitySans shootingEntity;
  
  public double targetpointx;
  
  public double targetpointy;
  
  public double targetpointz;
  
  public EntityGasterBlaster(World worldIn) {
    super(worldIn);
    func_189654_d(true);
    this.field_70145_X = true;
    this.field_70178_ae = true;
    this.field_70158_ak = true;
    func_70105_a(1.0F, 1.0F);
    func_174805_g(false);
  }
  
  public EntityGasterBlaster(World worldIn, double posX, double posY, double posZ, double targetX, double targetY, double targetZ) {
    this(worldIn);
    func_70107_b(posX, posY, posZ);
    this.targetpointx = targetX;
    this.targetpointy = targetY;
    this.targetpointz = targetZ;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(STATUS, Byte.valueOf((byte)0));
  }
  
  protected void hitEntities() {
    for (int i = 0; i < 80; i++) {
      Vec3d vec3 = func_70676_i(1.0F);
      double x = this.field_70165_t + vec3.field_72450_a * i;
      double y = this.field_70163_u + (isSmall() ? 1.0D : 0.5D) + vec3.field_72448_b * i;
      double z = this.field_70161_v + vec3.field_72449_c * i;
      AxisAlignedBB box = (new AxisAlignedBB(x, y, z, x, y, z)).func_186662_g(isSmall() ? 0.5D : 1.0D);
      List<Entity> list = this.field_70170_p.func_175674_a((Entity)this, box, Entity::func_70067_L);
      if (!list.isEmpty())
        for (Entity entity1 : list) {
          if (entity1 instanceof EntityLivingBase && this.shootingEntity != null && entity1 != null && entity1.func_70089_S() && !this.shootingEntity.func_184191_r(entity1)) {
            EntityLivingBase hitmob = (EntityLivingBase)entity1;
            hitmob.field_70172_ad = 0;
            hitmob.func_184185_a(ESound.bonehit, 1.0F, 1.0F);
            this.shootingEntity.inflictEngenderMobDamage(hitmob, " was blasted by a Gaster Blaster shot by ", (new DamageSource("sans")).func_76348_h().func_151518_m().func_82726_p(), 1.0F);
            this.shootingEntity.attackWithAdditionalEffects((Entity)this.shootingEntity);
            this.shootingEntity.karmicRetribution(hitmob, 10);
          } 
        }  
    } 
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74757_a("Small", isSmall());
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    setSmall(compound.func_74767_n("Small"));
  }
  
  public boolean func_70104_M() {
    return false;
  }
  
  protected void func_82167_n(Entity entityIn) {}
  
  protected void func_85033_bc() {}
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_70112_a(double distance) {
    return true;
  }
  
  private void playParticles() {
    if (this.field_70170_p instanceof WorldServer)
      ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.BLOCK_DUST, this.field_70165_t, this.field_70163_u + this.field_70131_O / 1.5D, this.field_70161_v, 10, (this.field_70130_N / 4.0F), (this.field_70131_O / 4.0F), (this.field_70130_N / 4.0F), 0.05D, new int[] { Block.func_176210_f(Blocks.field_150344_f.func_176223_P()) }); 
  }
  
  protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
    this.field_70760_ar = this.field_70126_B;
    this.field_70761_aq = this.field_70177_z;
    return 0.0F;
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.325F;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (!func_189652_ae())
      super.func_191986_a(strafe, vertical, forward); 
  }
  
  public void func_181013_g(float offset) {
    this.field_70760_ar = this.field_70126_B = offset;
    this.field_70758_at = this.field_70759_as = offset;
  }
  
  public void func_70034_d(float rotation) {
    this.field_70760_ar = this.field_70126_B = rotation;
    this.field_70758_at = this.field_70759_as = rotation;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    func_70671_ap().func_75650_a(this.targetpointx, this.targetpointy, this.targetpointz, 180.0F, 90.0F);
    if (--this.warmUpTime <= -20)
      this.fireLength++; 
    if (this.fireLength == 10)
      func_184185_a(ESound.gasterblasterfire, 3.0F, 1.0F); 
    this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    if (this.fireLength <= 15 && this.fireLength >= 10)
      hitEntities(); 
    if (this.fireLength > 20)
      this.field_191988_bg = -1.0F; 
    if (this.fireLength > 30)
      func_70106_y(); 
  }
  
  private float updateRotation(float p_75652_1_, float p_75652_2_) {
    float f = MathHelper.func_76142_g(p_75652_2_ - p_75652_1_);
    return p_75652_1_ + f;
  }
  
  public boolean func_70631_g_() {
    return isSmall();
  }
  
  public void func_174812_G() {
    func_70106_y();
  }
  
  public boolean func_180427_aV() {
    return true;
  }
  
  public EnumPushReaction func_184192_z() {
    return EnumPushReaction.PUSH_ONLY;
  }
  
  public void setSmall(boolean small) {
    this.field_70180_af.func_187227_b(STATUS, Byte.valueOf(setBit(((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue(), 1, small)));
  }
  
  public boolean isSmall() {
    return ((((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue() & 0x1) != 0);
  }
  
  private void setMarker(boolean marker) {
    this.field_70180_af.func_187227_b(STATUS, Byte.valueOf(setBit(((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue(), 16, marker)));
    func_70105_a(0.5F, 1.975F);
  }
  
  private byte setBit(byte p_184797_1_, int p_184797_2_, boolean p_184797_3_) {
    if (p_184797_3_) {
      p_184797_1_ = (byte)(p_184797_1_ | p_184797_2_);
    } else {
      p_184797_1_ = (byte)(p_184797_1_ & (p_184797_2_ ^ 0xFFFFFFFF));
    } 
    return p_184797_1_;
  }
  
  public void func_70077_a(EntityLightningBolt lightningBolt) {}
  
  public boolean func_184603_cC() {
    return false;
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (STATUS.equals(key))
      func_70105_a(0.5F, 1.975F); 
    super.func_184206_a(key);
  }
  
  public boolean func_190631_cK() {
    return false;
  }
  
  public Iterable<ItemStack> func_184193_aE() {
    return Collections.emptyList();
  }
  
  public ItemStack func_184582_a(EntityEquipmentSlot slotIn) {
    return ItemStack.field_190927_a;
  }
  
  public void func_184201_a(EntityEquipmentSlot slotIn, ItemStack stack) {}
  
  public EnumHandSide func_184591_cq() {
    return null;
  }
}
