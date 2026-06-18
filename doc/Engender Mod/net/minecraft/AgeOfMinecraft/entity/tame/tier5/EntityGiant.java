package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGiant extends EntityTameBase implements Massive, Armored {
  public EntityGiant(World worldIn) {
    super(worldIn);
    func_70105_a(3.0F, 12.0F);
    this.field_70138_W = 3.0F;
    this.isOffensive = true;
    this.field_70178_ae = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 48.0F, 8.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 250;
    this.field_70158_ak = true;
    this.field_70728_aV = 50;
  }
  
  public String getDescName() {
    return "giant";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  public int getNextLevelRequirement() {
    return 1000;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGiant(this.field_70170_p);
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.func_74838_a("entity." + str + ".cmm.name");
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.func_74838_a("entity." + s + ".name");
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  protected boolean func_184219_q(Entity passenger) {
    return (func_184188_bt().size() < 3);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      int i = func_184188_bt().indexOf(passenger);
      float f3 = this.field_70761_aq * 3.1415927F / 180.0F;
      float f11 = MathHelper.func_76126_a(f3);
      float f4 = MathHelper.func_76134_b(f3);
      if (EngenderConfig.mobs.useMobTalkerModels) {
        if (i == 2)
          passenger.func_70107_b(this.field_70165_t - (f4 * 2.1F * getFittness()), this.field_70163_u + (isHero() ? (8.0D * getFittness()) : (7.5D * getFittness())), this.field_70161_v - (f11 * 2.1F * getFittness())); 
        if (i == 1)
          passenger.func_70107_b(this.field_70165_t + (f4 * 2.1F * getFittness()), this.field_70163_u + (isHero() ? (8.0D * getFittness()) : (7.5D * getFittness())), this.field_70161_v + (f11 * 2.1F * getFittness())); 
        if (i == 0)
          passenger.func_70107_b(this.field_70165_t, this.field_70163_u + (isHero() ? (12.0D * getFittness()) : (11.35D * getFittness())), this.field_70161_v); 
      } else {
        if (i == 2)
          passenger.func_70107_b(this.field_70165_t - (f4 * 2.25F), this.field_70163_u + (isHero() ? 9.0D : 8.5D), this.field_70161_v - (f11 * 2.25F)); 
        if (i == 1)
          passenger.func_70107_b(this.field_70165_t + (f4 * 2.25F), this.field_70163_u + (isHero() ? 9.0D : 8.5D), this.field_70161_v + (f11 * 2.25F)); 
        if (i == 0)
          passenger.func_70107_b(this.field_70165_t, this.field_70163_u + (isHero() ? (12.0D * getFittness()) : (11.35D * getFittness())), this.field_70161_v); 
      } 
    } 
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    double amount = 0.6D;
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY)
      amount *= 0.75D; 
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
      amount *= 1.5D; 
    ((EntityLivingBase)entity).func_70653_a((Entity)this, 1.0F, MathHelper.func_76126_a(this.field_70177_z * 0.017453292F), -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
    if (!entity.func_70089_S() && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).field_70760_ar = ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70126_B = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70758_at = ((EntityLivingBase)entity).field_70759_as = this.field_70759_as;
      float xRatio = MathHelper.func_76126_a(this.field_70759_as * 0.017453292F);
      float zRatio = -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F);
      entity.field_70160_al = true;
      float f = MathHelper.func_76129_c(xRatio * xRatio + zRatio * zRatio);
      entity.field_70159_w /= 2.0D;
      entity.field_70179_y /= 2.0D;
      entity.field_70159_w -= xRatio / f * 3.0D;
      entity.field_70179_y -= zRatio / f * 3.0D;
      entity.field_70181_x /= 2.0D;
      entity.field_70181_x += amount;
      if (entity instanceof EntityPlayerMP)
        ((EntityPlayerMP)entity).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity(entity)); 
    } 
    entity.field_70181_x += amount;
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.84F) : (this.field_70131_O * 0.87F);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(48.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(50.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(24.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = 10.0F;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(2.0D, 0.0D, 2.0D));
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        if (entity instanceof EntityLivingBase && !func_184191_r(entity) && !this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0) {
          func_70652_k(entity);
          double d01 = ((func_174813_aQ()).field_72340_a + (func_174813_aQ()).field_72336_d) / 2.0D;
          double d11 = ((func_174813_aQ()).field_72339_c + (func_174813_aQ()).field_72334_f) / 2.0D;
          double d2 = entity.field_70165_t - d01;
          double d3 = entity.field_70161_v - d11;
          double d4 = d2 * d2 + d3 * d3;
          entity.func_70024_g(d2 / d4 * 6.0D, 0.25D, d3 / d4 * 6.0D);
          if (entity.field_70131_O >= 1.0F)
            func_70024_g(-(d2 / d4 * 1.25D), 0.25D, -(d3 / d4 * 1.25D)); 
        } 
      } 
      this.field_184618_aE = this.field_70721_aZ;
      double d5 = this.field_70165_t - this.field_70169_q;
      double d7 = this.field_70161_v - this.field_70166_s;
      float f10 = MathHelper.func_76133_a(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.field_70721_aZ += (f10 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public void func_70636_d() {
    this.reachWidth = 6.0F;
    if (isHero() && !isWild())
      if (this.field_70170_p.field_72995_K) {
        double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d3 = 10.0D;
        this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.field_70165_t + (this.field_70146_Z.nextFloat() * 3.0F * 2.0F) - 3.0D - d0 * d3, this.field_70163_u + (this.field_70146_Z.nextFloat() * 12.0F) - d1 * d3, this.field_70161_v + (this.field_70146_Z.nextFloat() * 3.0F * 2.0F) - 3.0D - d2 * d3, d0, 0.10000000149011612D, d2, new int[0]);
      }  
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 128.0D && getSpecialAttackTimer() <= 0 && this.field_70122_E && isHero())
      performSpecialAttack(); 
    this.field_70138_W = 4.0F;
    super.func_70636_d();
    for (int t = 0; t < 8; t++) {
      if (this.field_70159_w != 0.0D && this.field_70179_y != 0.0D) {
        int i = MathHelper.func_76128_c(this.field_70165_t);
        int j = MathHelper.func_76128_c(this.field_70163_u - 0.25D);
        int k = MathHelper.func_76128_c(this.field_70161_v);
        IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(i, j, k));
        if (iblockstate.func_185904_a() != Material.field_151579_a)
          this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, this.field_70165_t + (this.field_70146_Z.nextFloat() - 0.5D) * this.field_70130_N, (func_174813_aQ()).field_72338_b + 0.1D, this.field_70161_v + (this.field_70146_Z.nextFloat() - 0.5D) * this.field_70130_N, 4.0D * (this.field_70146_Z.nextFloat() - 0.5D), 0.5D, (this.field_70146_Z.nextFloat() - 0.5D) * 4.0D, new int[] { Block.func_176210_f(iblockstate) }); 
      } 
    } 
  }
  
  public void performSpecialAttack() {
    this.field_70181_x = 2.0D;
    func_184185_a(ESound.golemSpecial, 10.0F, 0.75F);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    if (getSpecialAttackTimer() <= 0 && isHero() && func_70638_az() != null) {
      setSpecialAttackTimer(400);
      func_184185_a(ESound.golemSmash, 10.0F, 0.9F);
      createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u - 2.0D, this.field_70161_v, 3.0F, false);
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(48.0D, 3.0D, 48.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null && !func_184191_r((Entity)entity)) {
            inflictEngenderMobDamage(entity, " was smashed by ", DamageSource.func_188405_b((EntityLivingBase)this), 50.0F);
            entity.field_70160_al = true;
            float f = MathHelper.func_76129_c(MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) * MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) + -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
            entity.field_70159_w /= 2.0D;
            entity.field_70179_y /= 2.0D;
            entity.field_70159_w -= (MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) / f) * 1.0D;
            entity.field_70179_y -= (-MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) / f) * 1.0D;
            entity.field_70181_x += (this.field_70146_Z.nextInt(30) == 0) ? 30.0D : 3.0D;
          } 
        }  
    } 
    super.func_180430_e(distance, damageMultiplier);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187899_gZ;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.guyHurt, func_70599_aP(), func_70647_i() + 0.4F); 
    return SoundEvents.field_187934_hh;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.guyDeath, func_70599_aP(), func_70647_i() + 0.4F); 
    return SoundEvents.field_187930_hd;
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_) {
    func_184185_a(SoundEvents.field_187605_cG, 3.0F, ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F + 0.8F) / getFittness());
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.1F : 5.0F;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_GIANT;
  }
  
  protected float func_70647_i() {
    return func_70631_g_() ? ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) : ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.1F + 0.5F);
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  public boolean func_70067_L() {
    return true;
  }
  
  public World getWorld() {
    return this.field_70170_p;
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    return func_70097_a(source, damage);
  }
}
