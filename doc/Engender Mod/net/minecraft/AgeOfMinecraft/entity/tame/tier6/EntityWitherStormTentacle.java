package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIBabyMobGirlFollowParent;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIMobGirlMate;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherStormTentacle extends EntityTameBase implements Massive, Armored, Flying, Undead {
  public EntityWitherStorm residentWitherStorm;
  
  public EntityWitherStormTentacle(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.field_70178_ae = true;
    func_70105_a(6.0F, 6.0F);
    this.field_70728_aV = 300;
    setLevel(300);
    this.field_70158_ak = true;
    this.field_98038_p = true;
    ((PathNavigateGround)func_70661_as()).func_179688_b(false);
    ((PathNavigateGround)func_70661_as()).func_179691_c(false);
    this.field_70714_bg.func_85156_a((EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_85156_a((EntityAIBase)new EntityAIMobGirlMate(this, 1.2D));
    this.field_70714_bg.func_85156_a((EntityAIBase)new EntityAIBabyMobGirlFollowParent(this, 1.2D));
    this.field_70714_bg.func_85156_a((EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLivingBase.class, 8.0F));
    this.field_70714_bg.func_85156_a((EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityGolem.class, 3.0F, 1.0F));
    this.field_70714_bg.func_85156_a((EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityVillager.class, 3.0F, 1.0F));
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "wither_storm_tentacle";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public void func_184178_b(EntityPlayerMP player) {}
  
  public void func_184203_c(EntityPlayerMP player) {}
  
  public boolean func_184645_a(EntityPlayer player, EnumHand hand) {
    return (this.residentWitherStorm != null) ? this.residentWitherStorm.func_184230_a(player, hand) : false;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(256.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(75.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
  }
  
  public boolean func_70652_k(Entity entityIn) {
    if (super.func_70652_k(entityIn)) {
      entityIn.func_184185_a(ESound.witherStormTentacleWhack, 10.0F, func_70647_i() + 0.15F);
      List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, entityIn.func_174813_aQ().func_186662_g(3.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list1 != null && !list1.isEmpty())
        for (int i1 = 0; i1 < list1.size(); i1++) {
          EntityLivingBase entity1 = list1.get(i1);
          if (!func_184191_r((Entity)entity1))
            super.func_70652_k((Entity)entity1); 
        }  
      this.field_70170_p.func_175718_b(3000, entityIn.func_180425_c(), 0);
      return true;
    } 
    return false;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.WITHER_STORM;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 0.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 100.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 100.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public void kill() {}
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public float func_70047_e() {
    return 3.0F;
  }
  
  public void func_70110_aj() {}
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    func_184185_a(ESound.witherStormFall, 10.0F, 1.0F);
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    return "Wither Storm Tentacle";
  }
  
  protected void func_184231_a(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    if (this.residentWitherStorm == null)
      super.func_184231_a(y, onGroundIn, state, pos); 
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (this.residentWitherStorm != null) {
      if (func_70090_H()) {
        func_191958_b(strafe, vertical, forward, 0.02F);
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= 0.800000011920929D;
        this.field_70181_x *= 0.800000011920929D;
        this.field_70179_y *= 0.800000011920929D;
      } else if (func_180799_ab()) {
        func_191958_b(strafe, vertical, forward, 0.02F);
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= 0.5D;
        this.field_70181_x *= 0.5D;
        this.field_70179_y *= 0.5D;
      } else {
        float f = 0.8F;
        float f1 = 0.16277136F / f * f * f;
        func_191958_b(strafe, vertical, forward, 0.02F);
        f = 0.8F;
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= f;
        this.field_70181_x *= f;
        this.field_70179_y *= f;
      } 
      this.field_184618_aE = this.field_70721_aZ;
      double d1 = this.field_70165_t - this.field_70169_q;
      double d0 = this.field_70161_v - this.field_70166_s;
      float f2 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 4.0F;
      if (f2 > 1.0F)
        f2 = 1.0F; 
      this.field_70721_aZ += (f2 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public boolean func_70617_f_() {
    return false;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return ESound.witherStormHurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ESound.witherStormHurt;
  }
  
  protected float func_70599_aP() {
    return (func_70093_af() || this.residentWitherStorm == null) ? 1.0F : 100.0F;
  }
  
  protected float func_70647_i() {
    return 1.0F;
  }
  
  public boolean func_70104_M() {
    return false;
  }
  
  protected void func_70623_bb() {
    this.field_70708_bq = 0;
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
  
  public float func_70013_c() {
    return 1.0F;
  }
  
  public void func_70690_d(PotionEffect p_70690_1_) {
    if (this.residentWitherStorm == null || (this.residentWitherStorm != null && !this.residentWitherStorm.func_70089_S()))
      super.func_70690_d(p_70690_1_); 
  }
  
  public void func_70106_y() {
    super.func_70106_y();
    if (this.residentWitherStorm != null) {
      if (this == this.residentWitherStorm.tentacle1)
        this.residentWitherStorm.tentacle1 = null; 
      if (this == this.residentWitherStorm.tentacle2)
        this.residentWitherStorm.tentacle2 = null; 
      if (this == this.residentWitherStorm.tentacle3)
        this.residentWitherStorm.tentacle3 = null; 
      if (this == this.residentWitherStorm.tentacle4)
        this.residentWitherStorm.tentacle4 = null; 
      if (this == this.residentWitherStorm.tentacle5)
        this.residentWitherStorm.tentacle5 = null; 
    } 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_WITHER_STORM_TENTACLE;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public void func_70030_z() {
    int i = func_70086_ai();
    super.func_70030_z();
    if (func_70089_S() && this.residentWitherStorm == null) {
      i--;
      func_70050_g(i);
      if (func_70086_ai() == -10) {
        func_70050_g(0);
        func_70097_a((new DamageSource("sever")).func_76348_h().func_151518_m(), 10.0F);
      } 
    } else {
      func_70050_g(100);
    } 
  }
  
  public void func_70636_d() {
    if (this.residentWitherStorm != null && !this.field_70170_p.field_72995_K) {
      if (func_70089_S()) {
        ChunkLoadingEvent.updateLoaded((Entity)this);
      } else {
        ChunkLoadingEvent.stopLoading((Entity)this);
      } 
      setOwnerId(this.residentWitherStorm.func_184753_b());
    } 
    if (this.residentWitherStorm != null && this.residentWitherStorm.field_70128_L)
      this.residentWitherStorm = null; 
    if (this.residentWitherStorm != null) {
      float rot = this.residentWitherStorm.field_70759_as * 0.017453292F;
      float oned = MathHelper.func_76126_a(rot);
      float twod = MathHelper.func_76134_b(rot);
      if (this.residentWitherStorm.doesntContainACommandBlock()) {
        if (this.residentWitherStorm.tentacle1 != null && this == this.residentWitherStorm.tentacle1)
          func_70012_b(this.residentWitherStorm.field_70165_t, this.residentWitherStorm.field_70163_u - 2.0D, this.residentWitherStorm.field_70161_v, 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle2 != null && this == this.residentWitherStorm.tentacle2)
          func_70012_b(this.residentWitherStorm.field_70165_t + (twod * 4.0F), this.field_70163_u - 1.0D, this.residentWitherStorm.field_70161_v + (oned * 4.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle3 != null && this == this.residentWitherStorm.tentacle3)
          func_70012_b(this.residentWitherStorm.field_70165_t - (twod * -2.0F), this.field_70163_u + 4.0D, this.residentWitherStorm.field_70161_v - (oned * -2.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle4 != null && this == this.residentWitherStorm.tentacle4)
          func_70012_b(this.residentWitherStorm.field_70165_t + (twod * -5.0F), this.field_70163_u + 3.0D, this.residentWitherStorm.field_70161_v + (oned * -5.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle5 != null && this == this.residentWitherStorm.tentacle5)
          func_70012_b(this.residentWitherStorm.field_70165_t, this.residentWitherStorm.field_70163_u + 2.0D, this.residentWitherStorm.field_70161_v, 0.0F, 0.0F); 
      } else {
        if (this.residentWitherStorm.tentacle1 != null && this == this.residentWitherStorm.tentacle1)
          func_70012_b(this.residentWitherStorm.field_70165_t, this.residentWitherStorm.field_70163_u - 12.0D, this.field_70161_v, 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle2 != null && this == this.residentWitherStorm.tentacle2)
          func_70012_b(this.residentWitherStorm.field_70165_t + (twod * 2.0F), this.residentWitherStorm.field_70163_u - 8.0D, this.field_70161_v + (oned * 2.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle3 != null && this == this.residentWitherStorm.tentacle3)
          func_70012_b(this.residentWitherStorm.field_70165_t - (twod * -8.0F), this.residentWitherStorm.field_70163_u + 8.0D, this.field_70161_v - (oned * -8.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle4 != null && this == this.residentWitherStorm.tentacle4)
          func_70012_b(this.residentWitherStorm.field_70165_t + (twod * -5.0F), this.residentWitherStorm.field_70163_u + 4.0D, this.field_70161_v + (oned * -5.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.tentacle5 != null && this == this.residentWitherStorm.tentacle5)
          func_70012_b(this.residentWitherStorm.field_70165_t, this.residentWitherStorm.field_70163_u + 3.0D, this.residentWitherStorm.field_70161_v, 0.0F, 0.0F); 
      } 
    } 
    if (this.field_70173_aa == 25)
      this.field_70173_aa += 40 + this.field_70146_Z.nextInt(120); 
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (func_110143_aJ() <= 0.0F) {
      this.residentWitherStorm = null;
      float f13 = (this.field_70146_Z.nextFloat() - 0.5F) * 6.0F;
      float f15 = (this.field_70146_Z.nextFloat() - 0.5F) * 6.0F;
      float f17 = (this.field_70146_Z.nextFloat() - 0.5F) * 6.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f13, this.field_70163_u + 2.0D + f15, this.field_70161_v + f17, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (this.residentWitherStorm != null) {
      this.field_70122_E = false;
      this.field_70160_al = true;
    } 
    if (this.residentWitherStorm != null && this.residentWitherStorm.field_70737_aN <= 0)
      this.residentWitherStorm.field_70737_aN = 10; 
    if (this.field_70173_aa % 10 == 0 && this.residentWitherStorm != null)
      func_70691_i(1.0F); 
    if (func_70090_H() && this.residentWitherStorm == null)
      this.field_70181_x += 0.25D; 
    this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    EntityLivingBase entity = func_70638_az();
    if (!func_82150_aj() && entity != null && entity instanceof EntityLivingBase && func_70685_l((Entity)entity) && func_70068_e((Entity)entity) < 2916.0D && (this.field_70173_aa + func_145782_y()) % (30 + this.field_70146_Z.nextInt(10)) == 0)
      func_70652_k((Entity)entity); 
    super.func_70636_d();
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (source.func_76355_l() == "chaosImplosion" || source.func_76355_l() == "de.GuardianFireball" || source.func_76355_l() == "de.GuardianEnergyBall" || source.func_76355_l() == "de.GuardianChaosBall")
      amount *= 0.2F; 
    if (func_180431_b(source))
      return false; 
    if (source.func_76355_l() != "chaosImplosion" && !source.func_76352_a() && !source.func_82725_o() && source != DamageSource.field_76371_c && source != DamageSource.field_76370_b && source != DamageSource.field_76372_a && source != DamageSource.field_76368_d && source != DamageSource.field_76379_h && source != DamageSource.field_76369_e && !(source.func_76346_g() instanceof EntityWitherStormSkull)) {
      if (this.residentWitherStorm != null)
        this.residentWitherStorm.func_70097_a(source, amount * 0.3F); 
      return super.func_70097_a(source, amount);
    } 
    return false;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
}
