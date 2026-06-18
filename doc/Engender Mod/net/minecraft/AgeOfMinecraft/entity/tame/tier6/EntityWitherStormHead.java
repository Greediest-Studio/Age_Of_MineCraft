package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIBabyMobGirlFollowParent;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIMobGirlMate;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherStormHead extends EntityTameBase implements IRangedAttackMob, Massive, Armored, Flying, Undead {
  public EntityWitherStorm residentWitherStorm;
  
  public int openMouthCounter;
  
  public EntityWitherStormHead(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.field_70178_ae = true;
    func_70105_a(9.0F, 9.0F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAttackRangedAlly(this, 0.0D, 60, 128.0F));
    this.field_70728_aV = 500;
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
    return "wither_storm_head";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public void func_184178_b(EntityPlayerMP player) {}
  
  public void func_184203_c(EntityPlayerMP player) {}
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(128.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(50.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
  }
  
  public boolean func_70652_k(Entity entityIn) {
    if (super.func_70652_k(entityIn)) {
      List<EntityLivingBase> list1 = this.field_70170_p.func_175647_a(EntityLivingBase.class, entityIn.func_174813_aQ().func_186662_g(3.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
      if (list1 != null && !list1.isEmpty())
        for (int i1 = 0; i1 < list1.size(); i1++) {
          EntityLivingBase entity1 = list1.get(i1);
          if (!func_184191_r((Entity)entity1))
            super.func_70652_k((Entity)entity1); 
        }  
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
    return 32.0F + this.field_70146_Z.nextFloat() * 32.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 64.0F + this.field_70146_Z.nextFloat() * 24.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 16.0F + this.field_70146_Z.nextFloat() * 16.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public void func_70076_C() {}
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public float func_70047_e() {
    return 5.0F;
  }
  
  public boolean func_174833_aM() {
    return false;
  }
  
  public void func_70110_aj() {}
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    func_184185_a(ESound.witherStormFall, 10.0F, 1.0F);
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
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    return "Wither Storm Head";
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
  
  protected SoundEvent func_184639_G() {
    this.openMouthCounter = 30;
    return ESound.witherStormRoar;
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
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_WITHER_STORM_HEAD;
  }
  
  public void func_70106_y() {
    super.func_70106_y();
    if (this.residentWitherStorm != null) {
      if (this == this.residentWitherStorm.centerHead)
        this.residentWitherStorm.centerHead = null; 
      if (this == this.residentWitherStorm.rightHead)
        this.residentWitherStorm.rightHead = null; 
      if (this == this.residentWitherStorm.leftHead)
        this.residentWitherStorm.leftHead = null; 
    } 
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.75D;
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      double d8 = 6.0D;
      Vec3d vec3 = func_70676_i(1.0F);
      double dx = vec3.field_72450_a * d8;
      double dy = vec3.field_72448_b * d8;
      double dz = vec3.field_72449_c * d8;
      passenger.func_70107_b(this.field_70165_t + dx, this.field_70163_u + dy + func_70042_X(), this.field_70161_v + dz);
    } 
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
        if (this.residentWitherStorm.centerHead != null && this == this.residentWitherStorm.centerHead)
          func_70012_b(this.residentWitherStorm.field_70165_t + (oned * -7.0F), this.residentWitherStorm.field_70163_u, this.residentWitherStorm.field_70161_v - (twod * -7.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.rightHead != null && this == this.residentWitherStorm.rightHead)
          func_70012_b(this.residentWitherStorm.field_70165_t + (twod * -7.0F) + (oned * -7.0F), this.residentWitherStorm.field_70163_u, this.residentWitherStorm.field_70161_v + (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.leftHead != null && this == this.residentWitherStorm.leftHead)
          func_70012_b(this.residentWitherStorm.field_70165_t - (twod * -7.0F) + (oned * -7.0F), this.residentWitherStorm.field_70163_u, this.residentWitherStorm.field_70161_v - (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F); 
      } else {
        if (this.residentWitherStorm.centerHead != null && this == this.residentWitherStorm.centerHead)
          func_70012_b(this.residentWitherStorm.field_70165_t + (oned * -7.0F), this.residentWitherStorm.field_70163_u, this.residentWitherStorm.field_70161_v - (twod * -7.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.rightHead != null && this == this.residentWitherStorm.rightHead)
          func_70012_b(this.residentWitherStorm.field_70165_t + (twod * -20.0F) + (oned * -4.0F), this.residentWitherStorm.field_70163_u + 10.0D, this.residentWitherStorm.field_70161_v + (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.leftHead != null && this == this.residentWitherStorm.leftHead)
          func_70012_b(this.residentWitherStorm.field_70165_t - (twod * -20.0F) + (oned * -4.0F), this.residentWitherStorm.field_70163_u + 10.0D, this.residentWitherStorm.field_70161_v - (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F); 
      } 
    } 
    if (this.field_70173_aa == 25)
      this.field_70173_aa += 40 + this.field_70146_Z.nextInt(120); 
    if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
      EntityLivingBase passenger = (EntityLivingBase)func_184179_bs();
      passenger.field_70143_R *= 0.0F;
      passenger.field_70172_ad = 40;
      passenger.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 40, 4));
      this.field_70761_aq = this.field_70177_z = this.field_70759_as = passenger.field_70177_z;
      this.field_70125_A = passenger.field_70125_A;
      func_70624_b(null);
    } 
    if (this.residentWitherStorm != null) {
      this.field_70122_E = false;
      this.field_70160_al = true;
    } 
    this.field_70169_q = this.field_70165_t;
    this.field_70167_r = this.field_70163_u;
    this.field_70166_s = this.field_70161_v;
    if (func_110143_aJ() <= 0.0F) {
      this.residentWitherStorm = null;
      float f13 = (this.field_70146_Z.nextFloat() - 0.5F) * 9.0F;
      float f15 = (this.field_70146_Z.nextFloat() - 0.5F) * 9.0F;
      float f17 = (this.field_70146_Z.nextFloat() - 0.5F) * 9.0F;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + f13, this.field_70163_u + 2.0D + f15, this.field_70161_v + f17, 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (this.residentWitherStorm != null && this.residentWitherStorm.field_70737_aN <= 0)
      this.residentWitherStorm.field_70737_aN = 10; 
    if (this.field_70173_aa % 10 == 0 && this.residentWitherStorm != null)
      func_70691_i(1.0F); 
    if (func_70090_H() && this.residentWitherStorm == null)
      this.field_70181_x += 0.25D; 
    this.openMouthCounter--;
    EntityLivingBase entity = func_70638_az();
    if (!func_82150_aj() && entity != null)
      func_70625_a((Entity)entity, 20.0F, 180.0F); 
    if (entity != null && !func_184191_r((Entity)entity) && entity.func_110143_aJ() > 0.0F && (!(entity instanceof EntityTameBase) || (entity instanceof EntityTameBase && !((EntityTameBase)entity).isABoss()))) {
      if (!this.field_70170_p.field_72995_K && entity.func_184222_aU())
        entity.func_70690_d(new PotionEffect(MobEffects.field_82731_v, 2147483647, 1)); 
      double d01 = this.field_70165_t - entity.field_70165_t;
      double d11 = this.field_70163_u + func_70047_e() - entity.field_70163_u;
      double d21 = this.field_70161_v - entity.field_70161_v;
      float f2 = MathHelper.func_76133_a(d01 * d01 + d11 * d11 + d21 * d21);
      if (entity instanceof EntityEnderman || entity instanceof EntityEnderman) {
        ((EntityLiving)entity).func_70624_b((this.residentWitherStorm != null) ? (EntityLivingBase)this.residentWitherStorm : (EntityLivingBase)this);
        if (this.field_70146_Z.nextInt(500) == 0) {
          if (entity instanceof EntityEnderman && ((EntityEnderman)entity).func_175489_ck() == null) {
            func_70097_a(DamageSource.func_76358_a(entity), 2500.0F);
            ((EntityEnderman)entity).func_175490_a(Blocks.field_150343_Z.func_176223_P());
          } 
          if (entity instanceof EntityEnderman && ((EntityEnderman)entity).getHeldBlockState() == null) {
            func_70097_a(DamageSource.func_76358_a(entity), 2500.0F);
            ((EntityEnderman)entity).setHeldBlockState(Blocks.field_150343_Z.func_176223_P());
          } 
        } 
      } else if (entity instanceof net.minecraft.entity.boss.EntityDragon && entity.func_110143_aJ() <= 1.0F) {
        ((EntityLiving)entity).func_70656_aK();
      } else if (entity instanceof EntityTameBase && (entity instanceof EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar || !((EntityTameBase)entity).func_184222_aU() || ((EntityTameBase)entity).isHero())) {
        ((EntityLiving)entity).func_70624_b((EntityLivingBase)this);
      } else {
        if (entity instanceof EntityLiving && !entity.func_184222_aU())
          ((EntityLiving)entity).func_70624_b((EntityLivingBase)this); 
        entity.field_70159_w = d01 / f2 * 0.5D * 0.5D + entity.field_70159_w * 0.5D;
        entity.field_70181_x = d11 / f2 * 0.5D * 0.5D + entity.field_70181_x * 0.5D;
        entity.field_70179_y = d21 / f2 * 0.5D * 0.5D + entity.field_70179_y * 0.5D;
      } 
      int in = 1;
      if (this.residentWitherStorm != null) {
        if (this.residentWitherStorm.getPhase() == EnumWitherStormPhase.Devourer)
          in *= 2; 
        if (this.residentWitherStorm.getPhase() == EnumWitherStormPhase.ThunderStorm)
          in *= 4; 
      } 
      for (int i1 = 0; i1 < 1500 * in; i1++) {
        int i11 = MathHelper.func_76128_c(entity.field_70163_u) + MathHelper.func_76136_a(this.field_70146_Z, 2, 3 * in) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        int l1 = MathHelper.func_76128_c(entity.field_70165_t) + MathHelper.func_76136_a(this.field_70146_Z, 2, 3 * in) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        int i2 = MathHelper.func_76128_c(entity.field_70161_v) + MathHelper.func_76136_a(this.field_70146_Z, 2, 3 * in) * MathHelper.func_76136_a(this.field_70146_Z, -1, 1);
        BlockPos blockpos = new BlockPos(l1, i11, i2);
        IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
        Block block = iblockstate.func_177230_c();
        if (this.residentWitherStorm != null && func_110143_aJ() <= 0.0F && this.field_70170_p.func_175710_j(blockpos) && !block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos) && !this.field_70170_p.field_72995_K && this.field_70170_p.func_175707_a(blockpos, blockpos) && block.func_176195_g(iblockstate, this.field_70170_p, new BlockPos(l1, i11, i2)) != -1.0F)
          if (EngenderConfig.mobs.grief)
            if (block.func_149688_o(iblockstate).func_76224_d()) {
              this.field_70170_p.func_175698_g(new BlockPos(l1, i11, i2));
            } else {
              this.field_70170_p.func_72838_d((Entity)new EntityFallingBlock(this.field_70170_p, l1, i11, i2, block.func_176223_P()));
            }   
      } 
      if (func_70068_e((Entity)entity) < 512.0D) {
        this.openMouthCounter = 5;
        if ((this.field_70173_aa + func_145782_y()) % 10 == 0)
          func_70652_k((Entity)entity); 
      } 
      List<EntityLiving> list1111 = this.field_70170_p.func_175647_a(EntityLiving.class, entity.func_174813_aQ().func_186662_g((this.residentWitherStorm != null) ? (this.residentWitherStorm.getSize() / 25000.0D + 4.0D) : 4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list1111 != null && !list1111.isEmpty())
        for (int i = 0; i < list1111.size(); i++) {
          EntityLiving entity1 = list1111.get(i);
          if (entity1 != null && entity1.func_70089_S() && (!func_184191_r((Entity)entity1) || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer)) {
            double d011 = entity.field_70165_t - entity1.field_70165_t;
            double d111 = entity.field_70163_u - entity1.field_70163_u;
            double d211 = entity.field_70161_v - entity1.field_70161_v;
            float f21 = MathHelper.func_76133_a(d011 * d011 + d111 * d111 + d211 * d211);
            if (entity1 instanceof EntityEnderman || (entity instanceof EntityTameBase && (entity instanceof EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar || !((EntityTameBase)entity).func_184222_aU() || ((EntityTameBase)entity).isHero()))) {
              entity1.func_70624_b((EntityLivingBase)this);
              if (this.field_70146_Z.nextInt(500) == 0 && (entity1 instanceof EntityEnderman || entity1 instanceof EntityEnderman)) {
                func_70097_a(DamageSource.func_76358_a((EntityLivingBase)entity1), 500.0F);
                if (entity1 instanceof EntityEnderman && ((EntityEnderman)entity1).func_175489_ck() == null)
                  ((EntityEnderman)entity1).func_175490_a(Blocks.field_150343_Z.func_176223_P()); 
              } 
            } else {
              entity1.field_70159_w = d01 / f2 * 0.75D * 0.75D + entity1.field_70159_w * 0.5D;
              entity1.field_70181_x = d11 / f2 * 0.75D * 0.75D + entity1.field_70181_x * 0.5D;
              entity1.field_70179_y = d21 / f2 * 0.75D * 0.75D + entity1.field_70179_y * 0.5D;
            } 
          } 
        }  
    } 
    List<EntityLiving> list11111 = this.field_70170_p.func_175647_a(EntityLiving.class, func_174813_aQ().func_72314_b(4.0D, 4.0D, 4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (list11111 != null && !list11111.isEmpty())
      for (int i1 = 0; i1 < list11111.size(); i1++) {
        EntityLiving entity1 = list11111.get(i1);
        if (this.residentWitherStorm == null && entity1 != null && entity1.func_70089_S() && (!func_184191_r((Entity)entity1) || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity instanceof EntityEnderman) && !(entity instanceof EntityEnderman) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer))
          func_70652_k((Entity)entity1); 
        if (this.residentWitherStorm != null && entity1 != null && func_70032_d((Entity)entity1) <= 10.0D && entity1.func_70089_S() && (!func_184191_r((Entity)entity1) || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity instanceof EntityEnderman) && !(entity instanceof EntityEnderman) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer)) {
          if (!isWild() && EngenderConfig.general.useMessage)
            getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity1.func_70005_c_() + " was eaten by The Wither Storm (" + getOwner().func_70005_c_() + ")", new Object[0])); 
          this.field_70170_p.func_72960_a((Entity)entity1, (byte)3);
          if (!entity1.func_184222_aU()) {
            entity1.func_70606_j(0.0F);
            entity1.field_70181_x++;
            entity1.field_70181_x++;
            entity1.field_70181_x++;
          } else {
            entity1.func_70106_y();
          } 
          this.residentWitherStorm.Grow(this.residentWitherStorm.getSize() + 1 + (int)entity1.func_110138_aP() + (int)entity1.field_70131_O * (int)entity1.field_70131_O + (int)entity1.field_70130_N * (int)entity1.field_70130_N);
          this.residentWitherStorm.func_70691_i((1 + (int)entity1.func_110138_aP() + (int)entity1.field_70131_O * (int)entity1.field_70131_O + (int)entity1.field_70130_N * (int)entity1.field_70130_N));
          this.openMouthCounter = 2;
        } 
      }  
    super.func_70636_d();
  }
  
  private void launchWitherSkullToEntity(EntityLivingBase p_82216_2_) {
    this.field_70170_p.func_180498_a((EntityPlayer)null, 1024, new BlockPos((Entity)this), 0);
    double d1 = 2.0D;
    Vec3d vec3 = func_70676_i(1.0F);
    double d2 = p_82216_2_.field_70165_t - this.field_70165_t + vec3.field_72450_a;
    double d3 = p_82216_2_.field_70163_u - p_82216_2_.field_70181_x - this.field_70163_u + 2.0D;
    double d4 = p_82216_2_.field_70161_v - this.field_70161_v + vec3.field_72449_c;
    func_184185_a(SoundEvents.field_187853_gC, 10.0F, 0.8F);
    func_184185_a(SoundEvents.field_187539_bB, 10.0F, (1.0F + (this.field_70170_p.field_73012_v.nextFloat() - this.field_70170_p.field_73012_v.nextFloat()) * 0.2F) * 0.7F);
    EntityWitherStormSkull entitylargefireball = new EntityWitherStormSkull(this.field_70170_p, (EntityLivingBase)this, d2, d3, d4);
    entitylargefireball.field_70165_t = this.field_70165_t + vec3.field_72450_a * d1;
    entitylargefireball.field_70163_u = this.field_70163_u + 2.0D;
    entitylargefireball.field_70161_v = this.field_70161_v + vec3.field_72449_c * d1;
    this.field_70170_p.func_72838_d((Entity)entitylargefireball);
    this.openMouthCounter = 5;
    float dm = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
    entitylargefireball.damage = dm;
  }
  
  public void func_82196_d(EntityLivingBase p_82196_1_, float p_82196_2_) {
    if (!func_82150_aj() && !func_184191_r((Entity)p_82196_1_))
      launchWitherSkullToEntity(p_82196_1_); 
  }
  
  public boolean func_184645_a(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return (this.residentWitherStorm != null) ? this.residentWitherStorm.func_184230_a(player, hand) : false;
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
  
  public EnumPushReaction func_184192_z() {
    return EnumPushReaction.IGNORE;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
}
