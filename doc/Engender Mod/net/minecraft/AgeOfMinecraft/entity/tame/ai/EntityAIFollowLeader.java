package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGuardian;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIFollowLeader extends EntityAIBase {
  private EntityTameBase theCreature;
  
  private EntityLivingBase theOwner;
  
  World theWorld;
  
  private double followSpeed;
  
  private PathNavigate petPathfinder;
  
  private int field_75343_h;
  
  float maxDist;
  
  float minDist;
  
  private float field_75344_i;
  
  public EntityAIFollowLeader(EntityTameBase p_i1625_1_, double p_i1625_2_, float p_i1625_4_, float p_i1625_5_) {
    this.theCreature = p_i1625_1_;
    this.theWorld = p_i1625_1_.field_70170_p;
    this.followSpeed = p_i1625_2_;
    this.petPathfinder = p_i1625_1_.func_70661_as();
    this.minDist = p_i1625_4_;
    this.maxDist = p_i1625_5_;
    func_75248_a(0);
  }
  
  public boolean func_75250_a() {
    EntityLivingBase entitylivingbase = this.theCreature.getOwner();
    if (entitylivingbase == null)
      return false; 
    if (!this.theCreature.canFollowOwner())
      return false; 
    if (this.theCreature.field_70170_p.field_73011_w != entitylivingbase.field_70170_p.field_73011_w)
      return false; 
    this.theOwner = entitylivingbase;
    double d0 = this.theCreature.func_70032_d((Entity)entitylivingbase);
    return (d0 >= getMinDis() || this.theOwner.func_70093_af());
  }
  
  public boolean func_75253_b() {
    return ((this.theCreature.canFollowOwner() && this.theCreature.func_70032_d((Entity)this.theOwner) >= getMaxDis()) || this.theOwner.func_70093_af());
  }
  
  public void func_75249_e() {
    this.field_75343_h = 0;
    this.field_75344_i = this.theCreature.func_184643_a(PathNodeType.WATER);
    this.theCreature.func_184644_a(PathNodeType.WATER, 0.0F);
    this.theCreature.setSitResting(false);
  }
  
  public void func_75251_c() {
    this.theOwner = null;
    this.petPathfinder.func_75499_g();
    this.petPathfinder.func_75497_a((Entity)this.theCreature, 0.0D);
    this.theCreature.func_184644_a(PathNodeType.WATER, this.field_75344_i);
  }
  
  private boolean func_181065_a(BlockPos p_181065_1_) {
    IBlockState iblockstate = this.theWorld.func_180495_p(p_181065_1_);
    Block block = iblockstate.func_177230_c();
    return (block == Blocks.field_150350_a);
  }
  
  private double getMaxDis() {
    return this.maxDist;
  }
  
  private double getMinDis() {
    return this.minDist;
  }
  
  public void func_75246_d() {
    Vec3d vec3 = this.theOwner.func_70676_i(1.0F);
    if (this.theCreature instanceof EntityWither) {
      ((EntityWither)this.theCreature).updateWatchedTargetId(0, this.theOwner.func_145782_y());
      if (this.theCreature.field_70163_u < this.theOwner.field_70163_u || (!((EntityWither)this.theCreature).isArmored() && this.theCreature.field_70163_u < this.theOwner.field_70163_u + 5.0D + this.theOwner.func_70047_e()))
        this.theCreature.field_70181_x += 0.5D - this.theCreature.field_70181_x; 
      this.theCreature.func_70625_a((Entity)this.theOwner, 180.0F, 40.0F);
      double d0 = this.theOwner.field_70165_t - this.theCreature.field_70165_t;
      double d2 = this.theOwner.field_70161_v - this.theCreature.field_70161_v;
      double d3 = d0 * d0 + d2 * d2;
      if (d3 > 9.0D) {
        double d5 = MathHelper.func_76133_a(d3);
        if (this.theCreature.moralRaisedTimer > 200) {
          this.theCreature.field_70159_w += d0 / d5 * 0.75D - this.theCreature.field_70159_w;
          this.theCreature.field_70179_y += d2 / d5 * 0.75D - this.theCreature.field_70179_y;
        } else {
          this.theCreature.field_70159_w += d0 / d5 * 0.5D - this.theCreature.field_70159_w;
          this.theCreature.field_70179_y += d2 / d5 * 0.5D - this.theCreature.field_70179_y;
        } 
      } 
    } 
    if (this.theCreature instanceof EntityCommandBlockWither) {
      if (this.theCreature.field_70163_u < this.theOwner.field_70163_u || (!((EntityCommandBlockWither)this.theCreature).isArmored() && this.theCreature.field_70163_u < this.theOwner.field_70163_u + 5.0D + this.theOwner.func_70047_e()))
        this.theCreature.field_70181_x += 0.5D - this.theCreature.field_70181_x; 
      this.theCreature.func_70625_a((Entity)this.theOwner, 180.0F, 40.0F);
      double d0 = this.theOwner.field_70165_t - this.theCreature.field_70165_t;
      double d2 = this.theOwner.field_70161_v - this.theCreature.field_70161_v;
      double d3 = d0 * d0 + d2 * d2;
      if (d3 > 9.0D) {
        double d5 = MathHelper.func_76133_a(d3);
        if (this.theCreature.moralRaisedTimer > 200) {
          this.theCreature.field_70159_w += d0 / d5 * 0.75D - this.theCreature.field_70159_w;
          this.theCreature.field_70179_y += d2 / d5 * 0.75D - this.theCreature.field_70179_y;
        } else {
          this.theCreature.field_70159_w += d0 / d5 * 0.5D - this.theCreature.field_70159_w;
          this.theCreature.field_70179_y += d2 / d5 * 0.5D - this.theCreature.field_70179_y;
        } 
      } 
    } 
    double d1 = 1.0D + this.theCreature.field_70130_N + this.theCreature.field_70131_O;
    if (this.theCreature instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm)
      this.theCreature.func_70605_aq().func_75642_a(this.theOwner.field_70142_S - vec3.field_72450_a * d1, this.theOwner.field_70137_T - this.theCreature.field_70131_O - 16.0D, this.theOwner.field_70136_U - vec3.field_72449_c * d1, this.followSpeed); 
    if (this.theCreature instanceof EntityGuardian)
      if (((EntityGuardian)this.theCreature).func_70090_H()) {
        this.theCreature.func_70605_aq().func_75642_a(this.theOwner.field_70142_S - vec3.field_72450_a * d1, this.theOwner.field_70137_T, this.theOwner.field_70136_U - vec3.field_72449_c * d1, this.followSpeed);
      } else if (!EngenderConfig.mobs.useMobTalkerModels) {
        double d01 = this.theOwner.field_70165_t - ((EntityGuardian)this.theCreature).field_70165_t;
        double d11 = this.theOwner.field_70161_v - ((EntityGuardian)this.theCreature).field_70161_v;
        float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11);
        ((EntityGuardian)this.theCreature).field_70159_w = d01 / f2 * 0.5D * 0.5D + ((EntityGuardian)this.theCreature).field_70159_w;
        ((EntityGuardian)this.theCreature).field_70179_y = d11 / f2 * 0.5D * 0.5D + ((EntityGuardian)this.theCreature).field_70179_y;
        ((EntityGuardian)this.theCreature).func_70625_a((Entity)this.theOwner, 180.0F, 30.0F);
        if (((EntityGuardian)this.theCreature).field_70122_E) {
          ((EntityGuardian)this.theCreature).field_70181_x += 0.6D;
          ((EntityGuardian)this.theCreature).func_184185_a(SoundEvents.field_187684_cg, 1.0F, 1.0F);
        } 
      }  
    if (!this.theCreature.func_70093_af()) {
      this.theCreature.func_70671_ap().func_75651_a((Entity)this.theOwner, 180.0F, this.theCreature.func_70646_bf());
      this.theCreature.func_70624_b(null);
    } 
    if (--this.field_75343_h <= 0 || this.theCreature.func_70093_af()) {
      this.field_75343_h = 20;
      if (this.theCreature.func_70093_af())
        this.theCreature.func_70671_ap().func_75650_a(this.theOwner.field_70165_t + vec3.field_72450_a * 16.0D, this.theOwner.field_70163_u + this.theOwner.func_70047_e() + vec3.field_72448_b * 4.0D, this.theOwner.field_70161_v + vec3.field_72449_c * 16.0D, 180.0F, 180.0F); 
      if (this.theCreature instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex)
        this.theCreature.func_70605_aq().func_75642_a(this.theOwner.field_70142_S - vec3.field_72450_a * d1, this.theOwner.field_70137_T, this.theOwner.field_70136_U - vec3.field_72449_c * d1, this.followSpeed); 
      if (this.theCreature instanceof EntitySlime && !EngenderConfig.mobs.useMobTalkerModels) {
        this.theCreature.func_70625_a((Entity)this.theOwner, 10.0F, 10.0F);
        ((EntitySlime.SlimeMoveHelper)this.theCreature.func_70605_aq()).setDirection(this.theCreature.field_70759_as, true);
      } 
      if (!this.petPathfinder.func_75492_a(this.theOwner.field_70142_S - vec3.field_72450_a * d1, this.theOwner.field_70137_T, this.theOwner.field_70136_U - vec3.field_72449_c * d1, this.followSpeed) || this.theCreature.func_70090_H() || this.theCreature.func_180799_ab())
        if (this.theCreature.func_70032_d((Entity)this.theOwner) >= getMinDis() + 4.0D && !this.theCreature.func_110167_bD()) {
          int i = MathHelper.func_76128_c(this.theOwner.field_70165_t);
          int j = MathHelper.func_76128_c(this.theOwner.field_70161_v);
          int k = MathHelper.func_76141_d(this.theOwner.field_70170_p.func_175672_r(this.theOwner.func_180425_c()).func_177956_o());
          for (int l = 0; l <= 4; l++) {
            for (int i1 = 0; i1 <= 4; i1++) {
              if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && (this.theWorld.func_180495_p(new BlockPos(i + l, k - 1, j + i1)).func_177230_c() != Blocks.field_150350_a || this.theWorld.func_180495_p(new BlockPos(i + l, k - 1, j + i1)).func_177230_c() == Blocks.field_150355_j || this.theCreature instanceof net.minecraft.AgeOfMinecraft.entity.tame.Flying || this.theOwner.func_184218_aH()) && this.theWorld.func_180495_p(new BlockPos(i + l, k - 1, j + i1)).func_177230_c() != Blocks.field_150353_l && this.theWorld.func_180495_p(new BlockPos((i + l), k + (this.theCreature.func_174813_aQ()).field_72337_e, (j + i1))).func_177230_c() == Blocks.field_150350_a && func_181065_a(new BlockPos(i + l, k, j + i1)) && func_181065_a(new BlockPos(i + l, k + 1, j + i1))) {
                this.theCreature.field_70143_R *= 0.0F;
                if (this.theCreature.func_184218_aH()) {
                  this.theCreature.func_184187_bx().func_70012_b(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.theCreature.field_70177_z, this.theCreature.field_70125_A);
                  if (this.theCreature.func_184187_bx().func_184218_aH())
                    this.theCreature.func_184187_bx().func_184210_p(); 
                } 
                this.theCreature.func_70012_b(((i + l) + 0.5F), k, ((j + i1) + 0.5F), this.theCreature.field_70177_z, this.theCreature.field_70125_A);
                this.petPathfinder.func_75499_g();
                return;
              } 
            } 
          } 
        }  
    } 
  }
}
