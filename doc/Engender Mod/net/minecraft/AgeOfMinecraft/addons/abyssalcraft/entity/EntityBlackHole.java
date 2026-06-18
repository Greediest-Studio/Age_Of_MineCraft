package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlackHole extends Entity {
  public EntityTameBase shootingEntity;
  
  public EntityBlackHole(World worldIn) {
    super(worldIn);
    this.field_70156_m = true;
    func_70105_a(3.0F, 3.0F);
  }
  
  public EntityBlackHole(World worldIn, EntityTameBase entity) {
    this(worldIn);
    this.shootingEntity = entity;
    func_82149_j((Entity)entity);
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  protected void func_70088_a() {}
  
  public double func_70033_W() {
    return func_184218_aH() ? (-(func_184187_bx()).field_70131_O * 0.5D) : 0.0D;
  }
  
  public void func_70071_h_() {
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (this.field_70173_aa >= (func_184218_aH() ? 199 : 400)) {
      func_70106_y();
      if (func_184218_aH()) {
        this.field_70170_p.func_175718_b(3000, func_180425_c(), 0);
        func_184185_a(ESound.jzaharshout, 10.0F, 0.5F);
        func_184185_a(ESound.blast, 10.0F, 1.0F);
        func_184187_bx().func_70106_y();
      } 
    } 
    if (func_184218_aH() && func_184187_bx() != null && func_184187_bx() instanceof EntityLivingBase && ((EntityLivingBase)func_184187_bx()).field_70725_aQ > 0) {
      ((EntityLivingBase)func_184187_bx()).field_70725_aQ = 0;
      ((EntityLivingBase)func_184187_bx()).field_70737_aN = 10;
    } 
    List<Entity> list = this.field_70170_p.func_72839_b(this, func_174813_aQ().func_186662_g(64.0D));
    if (!func_184218_aH() && list != null && !list.isEmpty())
      for (int j = 0; j < list.size(); j++) {
        Entity entity = list.get(j);
        if (this.shootingEntity == null && entity instanceof EntityJzahar)
          this.shootingEntity = (EntityJzahar)entity; 
        if (this.shootingEntity != null && (entity == this.shootingEntity || (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75102_a) || (entity instanceof EntityLivingBase && this.shootingEntity.func_184191_r(entity)))) {
          entity.field_70172_ad = 100;
          if (entity instanceof EntityLivingBase)
            ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_76429_m, 100, 4)); 
        } 
        if (entity.func_70089_S() && !(entity instanceof EntityJzahar) && this.shootingEntity != null && entity instanceof EntityLivingBase && !this.shootingEntity.func_184191_r(entity)) {
          if (entity.field_70163_u < this.field_70163_u)
            entity.field_70181_x += 0.02500000037252903D; 
          double d1 = 20.0D;
          float x = MathHelper.func_76134_b((this.field_70173_aa + j * 2)) * (3.0F + (float)(this.field_70146_Z.nextGaussian() * 6.0D));
          float z = MathHelper.func_76126_a((this.field_70173_aa + j * 2)) * (3.0F + (float)(this.field_70146_Z.nextGaussian() * 6.0D));
          double d2 = this.field_70165_t + x - entity.field_70165_t;
          double d3 = this.field_70163_u + 1.0D - entity.field_70163_u;
          double d4 = this.field_70161_v + z - entity.field_70161_v;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.func_70024_g(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          if (entity.func_70068_e(this) <= 64.0D && this.shootingEntity != null && entity instanceof EntityLivingBase && !this.shootingEntity.func_184191_r(entity)) {
            entity.func_70097_a(DamageSource.field_76380_i, (entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian) ? 20.0F : 4.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.func_70089_S() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " touched a Black Hole's event horizon thanks to " + this.shootingEntity.func_70005_c_() + " (" + this.shootingEntity.getOwner().func_70005_c_() + ")", new Object[0])); 
          } 
          if (entity.func_70068_e(this) <= 16.0D && this.shootingEntity != null && entity instanceof EntityLivingBase && !this.shootingEntity.func_184191_r(entity)) {
            if (entity instanceof EntityLivingBase)
              ((EntityLivingBase)entity).func_70606_j((entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) ? (((EntityLivingBase)entity).func_110143_aJ() - 100.0F) : (((EntityLivingBase)entity).func_110143_aJ() - 10.0F)); 
            entity.func_70097_a(DamageSource.field_191291_g.func_76359_i().func_76348_h().func_151518_m(), (entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian) ? 500.0F : 50.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.func_70089_S() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was sucked into a Black Hole thanks to " + this.shootingEntity.func_70005_c_() + " (" + this.shootingEntity.getOwner().func_70005_c_() + ")", new Object[0])); 
            if (!entity.func_70089_S() && entity.func_184222_aU() && !(entity instanceof EntityTameBase)) {
              if (entity instanceof EntityLiving)
                ((EntityLiving)entity).func_70656_aK(); 
              entity.func_70106_y();
            } 
          } 
        } 
        if (entity != null && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityBlackHole) && !(entity instanceof EntityImplosion)) {
          float x = MathHelper.func_76134_b((this.field_70173_aa + j * 2)) * (3.0F + (float)(this.field_70146_Z.nextGaussian() * 6.0D));
          float z = MathHelper.func_76126_a((this.field_70173_aa + j * 2)) * (3.0F + (float)(this.field_70146_Z.nextGaussian() * 6.0D));
          double d1 = 20.0D;
          double d2 = this.field_70165_t + x - entity.field_70165_t;
          double d3 = this.field_70163_u + 1.0D - entity.field_70163_u;
          double d4 = this.field_70161_v + z - entity.field_70161_v;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.func_70107_b(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
          entity.func_70024_g(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          this.field_70170_p.func_72960_a(entity, (byte)2);
          if (entity.func_70068_e(this) <= 16.0D)
            entity.func_70106_y(); 
        } 
      }  
    int i = MathHelper.func_76128_c(this.field_70163_u);
    int i1 = MathHelper.func_76128_c(this.field_70165_t);
    int j1 = MathHelper.func_76128_c(this.field_70161_v);
    for (int l1 = -6; l1 <= 6; l1++) {
      for (int i2 = -6; i2 <= 6; i2++) {
        for (int j = -6; j <= 6; j++) {
          int j2 = i1 + l1;
          int k = i + j;
          int l = j1 + i2;
          BlockPos pos = new BlockPos(j2, k, l);
          IBlockState state = this.field_70170_p.func_180495_p(pos);
          Block block = state.func_177230_c();
          if (!func_184218_aH() && !block.isAir(state, (IBlockAccess)this.field_70170_p, pos) && this.field_70146_Z.nextInt(10) == 0 && !this.field_70170_p.field_72995_K && this.field_70170_p.func_175707_a(func_180425_c().func_177982_a(-32, -32, -32), func_180425_c().func_177982_a(32, 32, 32)) && block.func_176195_g(state, this.field_70170_p, new BlockPos(j2, k, l)) != -1.0F)
            if (block.func_149688_o(state).func_76224_d()) {
              this.field_70170_p.func_175698_g(new BlockPos(j2, k, l));
            } else {
              this.field_70170_p.func_72838_d((Entity)new EntityFallingBlock(this.field_70170_p, j2, k + 0.5D, l, block.func_176223_P()));
            }  
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
