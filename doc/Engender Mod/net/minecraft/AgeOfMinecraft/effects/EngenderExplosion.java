package net.minecraft.AgeOfMinecraft.effects;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EngenderExplosion extends Explosion {
  private final boolean isFlaming;
  
  private final boolean isSmoking;
  
  private final Random explosionRNG;
  
  private final World world;
  
  private final double explosionX;
  
  private final double explosionY;
  
  private final double explosionZ;
  
  private final EntityTameBase exploder;
  
  private final float explosionSize;
  
  private final List<BlockPos> affectedBlockPositions;
  
  private final Map<EntityPlayer, Vec3d> playerKnockbackMap;
  
  private final Vec3d position;
  
  @SideOnly(Side.CLIENT)
  public EngenderExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
    this(worldIn, entityIn, x, y, z, size, false, true, affectedPositions);
  }
  
  @SideOnly(Side.CLIENT)
  public EngenderExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking, List<BlockPos> affectedPositions) {
    this(worldIn, entityIn, x, y, z, size, flaming, smoking);
    this.affectedBlockPositions.addAll(affectedPositions);
  }
  
  public EngenderExplosion(World worldIn, Entity entityIn, double x, double y, double z, float size, boolean flaming, boolean smoking) {
    super(worldIn, entityIn, x, y, z, size, flaming, smoking);
    this.explosionRNG = new Random();
    this.affectedBlockPositions = Lists.newArrayList();
    this.playerKnockbackMap = Maps.newHashMap();
    this.world = worldIn;
    this.exploder = (EntityTameBase)entityIn;
    this.explosionSize = size;
    this.explosionX = x;
    this.explosionY = y;
    this.explosionZ = z;
    this.isFlaming = flaming;
    this.isSmoking = smoking;
    this.position = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
  }
  
  public void func_77278_a() {
    if (this.explosionSize > 0.0F) {
      Set<BlockPos> set = Sets.newHashSet();
      int i = 16;
      for (int j = 0; j < 16; j++) {
        for (int k = 0; k < 16; k++) {
          for (int l = 0; l < 16; l++) {
            if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
              double d0 = (j / 15.0F * 2.0F - 1.0F);
              double d1 = (k / 15.0F * 2.0F - 1.0F);
              double d2 = (l / 15.0F * 2.0F - 1.0F);
              double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
              d0 /= d3;
              d1 /= d3;
              d2 /= d3;
              float f = this.explosionSize * (0.7F + this.world.field_73012_v.nextFloat() * 0.6F);
              double d4 = this.explosionX;
              double d6 = this.explosionY;
              double d8 = this.explosionZ;
              for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                BlockPos blockpos = new BlockPos(d4, d6, d8);
                IBlockState iblockstate = this.world.func_180495_p(blockpos);
                if (iblockstate.func_185904_a() != Material.field_151579_a) {
                  float f2 = (this.exploder != null) ? this.exploder.func_180428_a(this, this.world, blockpos, iblockstate) : iblockstate.func_177230_c().getExplosionResistance(this.world, blockpos, (Entity)null, this);
                  f -= (f2 + 0.3F) * 0.3F;
                } 
                if (f > 0.0F && (this.exploder == null || this.exploder.func_174816_a(this, this.world, blockpos, iblockstate, f)))
                  set.add(blockpos); 
                d4 += d0 * 0.30000001192092896D;
                d6 += d1 * 0.30000001192092896D;
                d8 += d2 * 0.30000001192092896D;
              } 
            } 
          } 
        } 
      } 
      this.affectedBlockPositions.addAll(set);
      float f3 = this.explosionSize;
      int k1 = MathHelper.func_76128_c(this.explosionX - f3 - 1.0D);
      int l1 = MathHelper.func_76128_c(this.explosionX + f3 + 1.0D);
      int i2 = MathHelper.func_76128_c(this.explosionY - f3 - 1.0D);
      int i1 = MathHelper.func_76128_c(this.explosionY + f3 + 1.0D);
      int j2 = MathHelper.func_76128_c(this.explosionZ - f3 - 1.0D);
      int j1 = MathHelper.func_76128_c(this.explosionZ + f3 + 1.0D);
      List<Entity> list = this.world.func_72839_b((Entity)this.exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
      ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
      Vec3d vec3d = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);
      for (int k2 = 0; k2 < list.size(); k2++) {
        Entity entity = list.get(k2);
        if (entity != null && !entity.func_180427_aV() && !(entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar) && !(entity instanceof net.minecraft.entity.projectile.EntityFireball)) {
          double d12 = entity.func_70011_f(this.explosionX, this.explosionY, this.explosionZ) / f3 * 4.0D;
          if (d12 <= 1.0D) {
            double d5 = entity.field_70165_t - this.explosionX;
            double d7 = entity.field_70163_u - this.explosionY;
            double d9 = entity.field_70161_v - this.explosionZ;
            if (entity.func_70011_f(this.explosionX, this.explosionY, this.explosionZ) <= 3.0D)
              d7 = entity.field_70163_u; 
            double d13 = MathHelper.func_76133_a(d5 * d5 + d7 * d7 + d9 * d9);
            if (d13 != 0.0D) {
              d5 /= d13;
              d7 /= d13;
              d9 /= d13;
              double d14 = this.world.func_72842_a(vec3d, entity.func_174813_aQ());
              double d10 = (1.0D - d12) * d14;
              if (entity instanceof EntityLivingBase && !this.exploder.func_184191_r(entity))
                this.exploder.inflictEngenderMobDamage((EntityLivingBase)entity, " was blown up by ", DamageSource.func_94539_a(this), (int)((d10 * d10 + d10) / 2.0D * 7.0D * f3 + 1.0D)); 
              double d11 = d10 * f3;
              if (entity instanceof EntityLivingBase)
                d11 = EnchantmentProtection.func_92092_a((EntityLivingBase)entity, d10); 
              if (!(entity instanceof net.minecraft.entity.IEntityMultiPart) && entity instanceof EntityLivingBase && !this.exploder.func_184191_r(entity)) {
                entity.field_70159_w += d5 * d11;
                entity.field_70181_x += d7 * d11 + 1.0D;
                entity.field_70179_y += d9 * d11;
                if (entity instanceof EntityLivingBase) {
                  ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70759_as = (float)MathHelper.func_181159_b(entity.field_70179_y, entity.field_70159_w) * 57.295776F - 90.0F;
                  ((EntityLivingBase)entity).func_70604_c(null);
                  if (entity instanceof EntityLiving)
                    ((EntityLiving)entity).func_70624_b(null); 
                } 
              } 
              if (entity instanceof EntityPlayer) {
                EntityPlayer entityplayer = (EntityPlayer)entity;
                if (!entityplayer.func_175149_v() && (!entityplayer.func_184812_l_() || !entityplayer.field_71075_bZ.field_75100_b))
                  this.playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10)); 
              } 
            } 
          } 
        } 
      } 
    } 
  }
  
  public void func_77279_a(boolean spawnParticles) {
    this.world.func_180498_a((EntityPlayer)null, 3000, new BlockPos(this.explosionX, this.explosionY, this.explosionZ), 0);
    if (this.isSmoking && this.explosionSize > 0.0F)
      for (BlockPos blockpos : this.affectedBlockPositions) {
        IBlockState iblockstate = this.world.func_180495_p(blockpos);
        Block block = iblockstate.func_177230_c();
        double d0 = (blockpos.func_177958_n() + this.world.field_73012_v.nextFloat());
        double d1 = (blockpos.func_177956_o() + this.world.field_73012_v.nextFloat());
        double d2 = (blockpos.func_177952_p() + this.world.field_73012_v.nextFloat());
        double d3 = d0 - this.explosionX;
        double d4 = d1 - this.explosionY;
        double d5 = d2 - this.explosionZ;
        double d6 = MathHelper.func_76133_a(d3 * d3 + d4 * d4 + d5 * d5);
        d3 /= d6;
        d4 /= d6;
        d5 /= d6;
        double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
        d7 *= (this.world.field_73012_v.nextFloat() * this.world.field_73012_v.nextFloat() + 0.3F);
        d3 *= d7;
        d4 *= d7;
        d5 *= d7;
        if (this.world instanceof WorldServer) {
          ((WorldServer)this.world).func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.explosionX) / 2.0D, (d1 + this.explosionY) / 2.0D, (d2 + this.explosionZ) / 2.0D, d3, d4, d5, new int[0]);
          ((WorldServer)this.world).func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
        } 
        if (iblockstate.func_185904_a() != Material.field_151579_a) {
          if (block.func_149659_a(this))
            block.func_180653_a(this.world, blockpos, this.world.func_180495_p(blockpos), 1.0F / this.explosionSize, 0); 
          block.onBlockExploded(this.world, blockpos, this);
        } 
      }  
    if (this.isFlaming)
      for (BlockPos blockpos1 : this.affectedBlockPositions) {
        if (this.world.func_180495_p(blockpos1).func_185904_a() == Material.field_151579_a && this.world.func_180495_p(blockpos1.func_177977_b()).func_185913_b() && this.explosionRNG.nextInt(3) == 0)
          this.world.func_175656_a(blockpos1, Blocks.field_150480_ab.func_176223_P()); 
      }  
  }
  
  public Map<EntityPlayer, Vec3d> func_77277_b() {
    return this.playerKnockbackMap;
  }
  
  @Nullable
  public EntityLivingBase func_94613_c() {
    return (this.exploder == null) ? null : ((this.exploder instanceof EntityLivingBase) ? (EntityLivingBase)this.exploder : null);
  }
  
  public void func_180342_d() {
    this.affectedBlockPositions.clear();
  }
  
  public List<BlockPos> func_180343_e() {
    return this.affectedBlockPositions;
  }
  
  public Vec3d getPosition() {
    return this.position;
  }
}
