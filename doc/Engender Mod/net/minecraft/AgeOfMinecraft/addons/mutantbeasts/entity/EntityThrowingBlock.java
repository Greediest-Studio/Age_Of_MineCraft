package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.item.MBItems;
import chumbanotz.mutantbeasts.util.EntityUtil;
import com.google.common.base.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityThrowingBlock extends EntityThrowable {
  private static final DataParameter<Optional<IBlockState>> BLOCK_STATE = EntityDataManager.func_187226_a(EntityThrowingBlock.class, DataSerializers.field_187197_g);
  
  private static final DataParameter<Integer> THROWER_ENTITY_ID = EntityDataManager.func_187226_a(EntityThrowingBlock.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> HELD = EntityDataManager.func_187226_a(EntityThrowingBlock.class, DataSerializers.field_187198_h);
  
  private UUID ownerUUID;
  
  public EntityThrowingBlock(World worldIn) {
    super(worldIn);
    func_70105_a(1.0F, 1.0F);
  }
  
  public EntityThrowingBlock(World worldIn, EntityMutantSnowGolem mutantSnowGolem) {
    super(worldIn, mutantSnowGolem.field_70165_t, mutantSnowGolem.field_70163_u + 1.855D, mutantSnowGolem.field_70161_v);
    this.field_70177_z = mutantSnowGolem.field_70177_z;
    setThrower((EntityLivingBase)mutantSnowGolem);
  }
  
  public EntityThrowingBlock(World world, EntityMutantEnderman enderman, int armID) {
    super(world, enderman.field_70165_t, enderman.field_70163_u + 4.7D, enderman.field_70161_v);
    setThrower((EntityLivingBase)enderman);
    setBlockState(Block.func_176220_d(enderman.heldBlock[armID]));
    boolean outer = (armID <= 2);
    boolean right = (armID == 1);
    EntityLivingBase living = enderman.func_70638_az();
    Vec3d forward = EntityUtil.getDirVector(this.field_70177_z, outer ? 2.7F : 1.4F);
    Vec3d strafe = EntityUtil.getDirVector(this.field_70177_z + (right ? 90.0F : -90.0F), outer ? 2.2F : 2.0F);
    this.field_70165_t += forward.field_72450_a + strafe.field_72450_a;
    this.field_70163_u += ((outer ? 2.8F : 1.1F) - 4.8F);
    this.field_70161_v += forward.field_72449_c + strafe.field_72449_c;
    if (living != null) {
      func_70186_c(living.field_70165_t - this.field_70165_t, living.field_70163_u - this.field_70163_u, living.field_70161_v - this.field_70161_v, 1.4F, 1.0F);
    } else {
      throwBlock((EntityLivingBase)enderman);
    } 
  }
  
  public EntityThrowingBlock(World world, EntityPlayer player, IBlockState blockState, BlockPos pos) {
    super(world, (EntityLivingBase)player);
    setThrower((EntityLivingBase)player);
    setBlockState(blockState);
    func_70107_b(pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D);
    setHeld(true);
  }
  
  protected void func_70088_a() {
    this.field_70180_af.func_187214_a(BLOCK_STATE, Optional.of(Blocks.field_150349_c.func_176223_P()));
    this.field_70180_af.func_187214_a(THROWER_ENTITY_ID, Integer.valueOf(-1));
    this.field_70180_af.func_187214_a(HELD, Boolean.valueOf(false));
  }
  
  public IBlockState getBlockState() {
    return (IBlockState)((Optional)this.field_70180_af.func_187225_a(BLOCK_STATE)).or(Blocks.field_150349_c.func_176223_P());
  }
  
  public void setBlockState(IBlockState state) {
    this.field_70180_af.func_187227_b(BLOCK_STATE, Optional.of(state));
  }
  
  @Nullable
  public EntityLivingBase func_85052_h() {
    if (this.field_70192_c == null && this.ownerUUID != null && this.field_70170_p instanceof WorldServer) {
      Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(this.ownerUUID);
      if (entity instanceof EntityLivingBase) {
        setThrower((EntityLivingBase)entity);
      } else {
        this.ownerUUID = null;
      } 
    } 
    return this.field_70192_c;
  }
  
  private void setThrower(EntityLivingBase thrower) {
    this.field_70192_c = thrower;
    this.field_70180_af.func_187227_b(THROWER_ENTITY_ID, Integer.valueOf(thrower.func_145782_y()));
  }
  
  public EntityLivingBase getThrowerByID() {
    int throwerId = ((Integer)this.field_70180_af.func_187225_a(THROWER_ENTITY_ID)).intValue();
    if (throwerId >= 0) {
      Entity entity = this.field_70170_p.func_73045_a(throwerId);
      if (entity instanceof EntityLivingBase)
        return (EntityLivingBase)entity; 
    } 
    return null;
  }
  
  public boolean isHeld() {
    return ((Boolean)this.field_70180_af.func_187225_a(HELD)).booleanValue();
  }
  
  private void setHeld(boolean held) {
    this.field_70180_af.func_187227_b(HELD, Boolean.valueOf(held));
  }
  
  protected float func_70185_h() {
    if (getThrowerByID() instanceof EntityMutantSnowGolem)
      return 0.06F; 
    if (getThrowerByID() instanceof EntityPlayer)
      return 0.04F; 
    return 0.01F;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public boolean func_70067_L() {
    return func_70089_S();
  }
  
  public boolean func_70104_M() {
    return (isHeld() && func_70089_S());
  }
  
  public boolean func_70075_an() {
    return false;
  }
  
  public float func_70111_Y() {
    return 0.5F;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 3)
      for (int i = 0; i < 60; i++) {
        double x = this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N;
        double y = this.field_70163_u + 0.5D + (this.field_70146_Z.nextFloat() * this.field_70131_O);
        double z = this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N;
        double motx = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 3.0F);
        double moty = (0.5F + this.field_70146_Z.nextFloat() * 2.0F);
        double motz = ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 3.0F);
        this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, x, y, z, motx, moty, motz, new int[] { Block.func_176210_f(getBlockState()) });
      }  
  }
  
  public void func_70071_h_() {
    this.field_70142_S = this.field_70165_t;
    this.field_70137_T = this.field_70163_u;
    this.field_70136_U = this.field_70161_v;
    if (!this.field_70170_p.field_72995_K)
      func_70052_a(6, func_184202_aL()); 
    func_70030_z();
    if (isHeld()) {
      EntityLivingBase thrower = func_85052_h();
      if (thrower == null) {
        thrower = getThrowerByID();
        if (thrower != null) {
          this.field_70192_c = thrower;
        } else {
          setHeld(false);
        } 
      } else {
        Vec3d vec = thrower.func_70040_Z();
        double x = thrower.field_70165_t + vec.field_72450_a * 1.6D - this.field_70165_t;
        double y = thrower.field_70163_u + thrower.func_70047_e() + vec.field_72448_b * 1.6D - this.field_70163_u;
        double z = thrower.field_70161_v + vec.field_72449_c * 1.6D - this.field_70161_v;
        float offset = 0.6F;
        this.field_70159_w = x * offset;
        this.field_70181_x = y * offset;
        this.field_70179_y = z * offset;
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        if (!this.field_70170_p.field_72995_K && thrower instanceof EntityPlayer) {
          EntityPlayer player = (EntityPlayer)thrower;
          if (player == null || !player.func_70089_S() || player.func_175149_v() || !player.func_70685_l((Entity)this) || (player.func_184614_ca().func_77973_b() != MBItems.ENDERSOUL_HAND && player.func_184592_cb().func_77973_b() != MBItems.ENDERSOUL_HAND))
            setHeld(false); 
        } 
      } 
    } else {
      float f;
      RayTraceResult raytraceresult = ProjectileHelper.func_188802_a((Entity)this, true, (this.field_70173_aa >= 25), (Entity)this.field_70192_c);
      if (raytraceresult != null && !ForgeEventFactory.onProjectileImpact(this, raytraceresult))
        func_70184_a(raytraceresult); 
      this.field_70165_t += this.field_70159_w;
      this.field_70163_u += this.field_70181_x;
      this.field_70161_v += this.field_70179_y;
      ProjectileHelper.func_188803_a((Entity)this, 0.2F);
      if (func_70090_H()) {
        for (int i = 0; i < 4; i++)
          this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t - this.field_70159_w * 0.25D, this.field_70163_u - this.field_70181_x * 0.25D, this.field_70161_v - this.field_70179_y * 0.25D, this.field_70159_w, this.field_70181_x, this.field_70179_y, new int[0]); 
        f = 0.8F;
      } else {
        f = 0.99F;
      } 
      this.field_70159_w *= f;
      this.field_70181_x *= f;
      this.field_70179_y *= f;
      if (!func_189652_ae())
        this.field_70181_x -= func_70185_h(); 
      func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    } 
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    if (player.func_70093_af())
      return false; 
    if (player.func_184586_b(hand).func_77973_b() != MBItems.ENDERSOUL_HAND || player.func_184811_cZ().func_185141_a(MBItems.ENDERSOUL_HAND))
      return false; 
    if (isHeld()) {
      if (func_85052_h() == player) {
        if (!this.field_70170_p.field_72995_K) {
          setHeld(false);
          throwBlock((EntityLivingBase)player);
        } 
        player.func_184609_a(hand);
        player.func_184586_b(hand).func_77972_a(1, (EntityLivingBase)player);
        player.func_184811_cZ().func_185145_a(MBItems.ENDERSOUL_HAND, 20);
        return true;
      } 
      return false;
    } 
    setHeld(true);
    setThrower((EntityLivingBase)player);
    return true;
  }
  
  private void throwBlock(EntityLivingBase living) {
    this.field_70177_z = living.field_70177_z;
    this.field_70125_A = living.field_70125_A;
    float f = 0.4F;
    float PI = 3.1415927F;
    this.field_70159_w = (-MathHelper.func_76126_a(this.field_70177_z / 180.0F * PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * PI) * f);
    this.field_70181_x = (-MathHelper.func_76126_a(this.field_70125_A / 180.0F * PI) * f);
    this.field_70179_y = (MathHelper.func_76134_b(this.field_70177_z / 180.0F * PI) * MathHelper.func_76134_b(this.field_70125_A / 180.0F * PI) * f);
    func_70186_c(this.field_70159_w, this.field_70181_x, this.field_70179_y, 1.4F, 1.0F);
  }
  
  protected void func_70184_a(RayTraceResult result) {
    EntityLivingBase thrower = func_85052_h();
    IBlockState blockState = getBlockState();
    BlockPos pos = new BlockPos((Entity)this);
    if (result.field_72313_a == RayTraceResult.Type.ENTITY && thrower != null && thrower instanceof EntityTameBase) {
      if (((EntityTameBase)thrower).func_70638_az() != null)
        func_82149_j((Entity)((EntityTameBase)thrower).func_70638_az()); 
      float damage = blockState.func_177230_c().func_149638_a((Entity)this);
      if (damage <= 4.0F)
        damage = 4.0F; 
      if (damage > 50.0F)
        damage = 50.0F; 
      for (EntityLiving entity : this.field_70170_p.func_175647_a(EntityLiving.class, func_174813_aQ().func_72314_b(2.5D, 2.0D, 2.5D), IMob.class::isInstance)) {
        if (func_70068_e((Entity)entity) <= 6.25D)
          ((EntityTameBase)thrower).inflictEngenderMobDamage((EntityLivingBase)entity, " was pummeled by ", (DamageSource)new EntityDamageSource("thrown", (Entity)thrower), damage + this.field_70146_Z.nextInt(3)); 
      } 
      if (result.field_72313_a == RayTraceResult.Type.ENTITY && result.field_72308_g instanceof EntityLivingBase)
        ((EntityTameBase)thrower).inflictEngenderMobDamage((EntityLivingBase)result.field_72308_g, " was pummeled by ", (DamageSource)new EntityDamageSource("thrown", (Entity)thrower), damage); 
      if (!this.field_70170_p.field_72995_K) {
        this.field_70170_p.func_72960_a((Entity)this, (byte)3);
        this.field_70170_p.func_175718_b(2001, pos, Block.func_176210_f(blockState));
        if (blockState.func_177230_c() == Blocks.field_150335_W && thrower instanceof EntityTameBase)
          EntityTameBase.createEngenderModExplosionFireless((Entity)thrower, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 4.0F, this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")); 
        func_70106_y();
      } 
    } else {
      if (result.field_72313_a == RayTraceResult.Type.BLOCK) {
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_175718_b(2001, pos, Block.func_176210_f(blockState)); 
      } else if (result.field_72313_a == RayTraceResult.Type.ENTITY && !this.field_70170_p.field_72995_K && result.field_72308_g != thrower) {
        this.field_70170_p.func_175718_b(2001, pos, Block.func_176210_f(blockState));
        result.field_72308_g.func_70097_a(DamageSource.func_76356_a((Entity)this, (Entity)thrower), 4.0F);
      } 
      for (Entity entity : this.field_70170_p.func_175674_a((Entity)this, func_174813_aQ().func_186662_g(2.0D), EntitySelectors.field_188444_d)) {
        if (entity instanceof EntityLivingBase && entity.func_70067_L() && func_70068_e(entity) <= 4.0D && entity != thrower) {
          double x = entity.field_70165_t - this.field_70165_t;
          double z = entity.field_70161_v - this.field_70161_v;
          double d = Math.sqrt(x * x + z * z);
          entity.field_70159_w = x / d * 0.6000000238418579D;
          entity.field_70181_x = 0.20000000298023224D;
          entity.field_70179_y = z / d * 0.6000000238418579D;
          entity.func_70097_a(DamageSource.func_188403_a((Entity)this, thrower), (6 + this.field_70146_Z.nextInt(3)));
        } 
      } 
      if (!this.field_70170_p.field_72995_K)
        func_70106_y(); 
    } 
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    compound.func_74757_a("Held", isHeld());
    if (getBlockState() != null)
      compound.func_74782_a("BlockState", (NBTBase)NBTUtil.func_190009_a(new NBTTagCompound(), getBlockState())); 
    if (this.ownerUUID != null)
      compound.func_186854_a("OwnerUUID", this.ownerUUID); 
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    setHeld(compound.func_74767_n("Held"));
    setBlockState(NBTUtil.func_190008_d(compound.func_74775_l("BlockState")));
    if (compound.func_186855_b("OwnerUUID"))
      this.ownerUUID = compound.func_186857_a("OwnerUUID"); 
  }
}
