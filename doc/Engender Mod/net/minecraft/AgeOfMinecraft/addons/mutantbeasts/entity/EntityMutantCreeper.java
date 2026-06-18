package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.entity.CreeperMinionEggEntity;
import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMutantCreeper extends EntityTameBase implements IJumpingMount, Massive, Armored {
  private static final DataParameter<Byte> STATUS = EntityDataManager.func_187226_a(EntityMutantCreeper.class, DataSerializers.field_187191_a);
  
  public static final int MAX_CHARGE_TIME = 100;
  
  public static final int MAX_DEATH_TIME = 100;
  
  private int chargeTime;
  
  private int chargeHits;
  
  private boolean canSummonLightning;
  
  private DamageSource deathCause = DamageSource.field_76377_j;
  
  protected float jumpPower;
  
  public EntityMutantCreeper(World worldIn) {
    super(worldIn);
    this.field_70728_aV = 600;
    this.field_70158_ak = true;
    func_70105_a(1.6F, 2.8F);
    this.reachWidth = 3.6F;
    this.field_70714_bg.func_75776_a(1, new JumpGoal());
    this.field_70714_bg.func_75776_a(1, new SpawnMinionsGoal());
    this.field_70714_bg.func_75776_a(1, new ChargeAttackGoal());
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.3D, 64.0F, 12.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.3D, true));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_creeper";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(120.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.26D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(96.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
    func_110148_a(SWIM_SPEED).func_111128_a(4.5D);
  }
  
  public boolean func_180427_aV() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantCreeper(this.field_70170_p);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(STATUS, Byte.valueOf((byte)0));
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean getPowered() {
    return ((((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue() & 0x1) != 0);
  }
  
  private void setPowered(boolean powered) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue();
    this.field_70180_af.func_187227_b(STATUS, Byte.valueOf(powered ? 1 : (byte)(b0 & 0xFFFFFFFE)));
  }
  
  public boolean isJumpAttacking() {
    return ((((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue() & 0x2) != 0);
  }
  
  private void setJumpAttacking(boolean jumping) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue();
    this.field_70180_af.func_187227_b(STATUS, Byte.valueOf(jumping ? (byte)(b0 | 0x2) : (byte)(b0 & 0xFFFFFFFD)));
  }
  
  public boolean isCharging() {
    return ((((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue() & 0x4) != 0);
  }
  
  public void setCharging(boolean flag) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue();
    this.field_70180_af.func_187227_b(STATUS, Byte.valueOf(flag ? (byte)(b0 | 0x4) : (byte)(b0 & 0xFFFFFFFB)));
  }
  
  public float func_70047_e() {
    return 2.6F;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187928_hb, 0.15F, 1.5F);
    func_184185_a(SoundEvents.field_187605_cG, 0.5F, 1.0F);
  }
  
  public boolean func_70652_k(Entity entity) {
    if (super.func_70652_k(entity)) {
      if (getPowered()) {
        this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
        entity.func_70077_a(new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
      } 
      return true;
    } 
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new MBGroundPathNavigator((EntityLiving)this, worldIn);
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {}
  
  public void attackWithAdditionalEffects(Entity entity) {
    double amount = 1.0D;
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY)
      amount *= 0.75D; 
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
      amount *= 1.5D; 
    if (!entity.func_70089_S() && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).field_70760_ar = ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70126_B = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70758_at = ((EntityLivingBase)entity).field_70759_as = this.field_70759_as;
      float xRatio = MathHelper.func_76126_a(this.field_70759_as * 0.017453292F);
      float zRatio = -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F);
      entity.field_70160_al = true;
      float f = MathHelper.func_76129_c(xRatio * xRatio + zRatio * zRatio);
      entity.field_70159_w -= xRatio / f * amount * 4.0D;
      entity.field_70179_y -= zRatio / f * amount * 4.0D;
      entity.field_70181_x += amount;
      EntityUtil.sendPlayerVelocityPacket(entity);
    } 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151033_d) {
      this.field_70170_p.func_184148_a(player, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187649_bu, func_184176_by(), 1.0F, this.field_70146_Z.nextFloat() * 0.4F + 0.8F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        if (hasOwner(player))
          if (!getPowered()) {
            if (this.field_70170_p.func_175710_j(func_180425_c())) {
              this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, this.field_70165_t - 0.5D, this.field_70163_u + 1.625D, this.field_70161_v - 0.5D, false));
            } else {
              func_70656_aK();
            } 
          } else {
            setCharging(true);
          }  
        stack.func_77972_a(1, (EntityLivingBase)player);
      } 
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (getPowered() && source.func_76347_k()) {
      func_70066_B();
      return false;
    } 
    if (source.func_94541_c()) {
      float healAmount = amount / 2.0F;
      if (func_110143_aJ() < func_110138_aP()) {
        func_70691_i(healAmount);
        if (this.field_70170_p instanceof WorldServer)
          for (int i = 0; i < (int)(healAmount / 2.0F); i++) {
            double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
            double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
            ((WorldServer)this.field_70170_p).func_175739_a(EnumParticleTypes.HEART, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + 0.5D + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, 0, d0, d1, d2, 1.0D, new int[0]);
          }  
      } 
      return true;
    } 
    if (isCharging()) {
      if (!source.func_82725_o() && source.func_76364_f() instanceof EntityLivingBase)
        source.func_76364_f().func_70097_a(DamageSource.func_92087_a((Entity)this), 2.0F); 
      if (!this.field_70170_p.field_72995_K && amount > 0.0F && source.func_76364_f() != null && super.func_70097_a(source, amount))
        this.chargeHits--; 
    } 
    return super.func_70097_a(source, amount);
  }
  
  public void func_70077_a(EntityLightningBolt lightningBolt) {
    setPowered(true);
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public void func_70071_h_() {
    if (isCharging()) {
      int i = this.chargeTime % 20;
      if (i == 0 || i == 20)
        func_184185_a(MBSoundEvents.ENTITY_MUTANT_CREEPER_CHARGE, 0.6F, 0.7F + this.field_70146_Z.nextFloat() * 0.6F); 
      this.chargeTime++;
    } 
    super.func_70071_h_();
    this.field_70178_ae = getPowered();
    if (!this.field_70170_p.field_72995_K)
      if (isJumpAttacking()) {
        if (this.field_70122_E) {
          setJumpAttacking(false);
          createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, getPowered() ? 10.0F : 5.0F, EngenderConfig.mobs.grief);
        } 
      } else if (func_70089_S() && !func_175446_cd() && func_70094_T() && this.field_70173_aa % 30 == 0) {
        setJumpAttacking(true);
      }  
  }
  
  public boolean func_96092_aw() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float partialTick) {
    float f = this.field_70725_aQ / 100.0F;
    if (isCharging())
      f = (this.field_70173_aa % 20 < 10) ? 0.6F : 0.0F; 
    return f * 255.0F;
  }
  
  public void func_70645_a(DamageSource cause) {
    setCharging(false);
    func_184185_a(MBSoundEvents.ENTITY_MUTANT_CREEPER_DEATH, 0.9F, 1.0F);
    if (!this.field_70170_p.field_72995_K)
      this.deathCause = cause; 
  }
  
  public boolean leavesNoCorpse() {
    return false;
  }
  
  protected void func_70609_aI() {
    float explosionPower = getPowered() ? 12.0F : 8.0F;
    float f1 = explosionPower * 1.5F;
    for (Entity entity : this.field_70170_p.func_175674_a((Entity)this, func_174813_aQ().func_186662_g(f1), EntitySelectors.field_188444_d)) {
      double x = this.field_70165_t - entity.field_70165_t;
      double y = this.field_70163_u - entity.field_70163_u;
      double z = this.field_70161_v - entity.field_70161_v;
      double d = Math.sqrt(x * x + y * y + z * z);
      float f2 = this.field_70725_aQ / 100.0F;
      entity.func_70024_g(x / d * f2 * 0.2D, y / d * f2 * 0.09D, z / d * f2 * 0.2D);
    } 
    func_70107_b(this.field_70165_t + (this.field_70146_Z.nextFloat() * 0.2F) - 0.10000000149011612D, this.field_70163_u, this.field_70161_v + (this.field_70146_Z.nextFloat() * 0.2F) - 0.10000000149011612D);
    if (func_70094_T())
      func_145771_j(this.field_70165_t, ((func_174813_aQ()).field_72338_b + (func_174813_aQ()).field_72337_e) / 2.0D, this.field_70161_v); 
    super.func_70609_aI();
    if (this.field_70725_aQ >= 100) {
      if (!this.field_70170_p.field_72995_K) {
        createEngenderModExplosion((Entity)this, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, explosionPower, func_70027_ad(), EngenderConfig.mobs.grief);
        if (!this.field_70170_p.field_72995_K && func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
          int i = func_70693_a(this.field_70717_bb);
          i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.field_70717_bb, i);
          while (i > 0) {
            int j = EntityXPOrb.func_70527_a(i);
            i -= j;
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, j));
          } 
          this.field_70728_aV = 0;
        } 
        EntityUtil.spawnLingeringCloud((EntityLivingBase)this);
        CreeperMinionEggEntity egg = new CreeperMinionEggEntity(this.field_70170_p);
        egg.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        this.field_70170_p.func_72838_d((Entity)egg);
      } 
      super.func_70645_a(this.deathCause);
      func_70106_y();
    } 
  }
  
  public boolean ableToCauseSkullDrop() {
    return getPowered();
  }
  
  protected void func_70629_bd() {
    this.field_70181_x += 0.03999999910593033D;
  }
  
  protected void func_180466_bG() {
    func_70629_bd();
  }
  
  public float func_180428_a(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
    float f = super.func_180428_a(explosionIn, worldIn, pos, blockStateIn);
    return (getPowered() && EntityWither.func_181033_a(blockStateIn.func_177230_c()) && ForgeEventFactory.onEntityDestroyBlock((EntityLivingBase)this, pos, blockStateIn)) ? Math.min(0.8F, f) : f;
  }
  
  public boolean func_70601_bi() {
    return (super.func_70601_bi() && this.field_70170_p.func_175678_i(func_180425_c()));
  }
  
  protected SoundEvent func_184639_G() {
    return MBSoundEvents.ENTITY_MUTANT_CREEPER_AMBIENT;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_CREEPER_HURT;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187568_ap;
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74757_a("JumpAttacking", isJumpAttacking());
    compound.func_74757_a("Charging", isCharging());
    compound.func_74768_a("ChargeTime", this.chargeTime);
    compound.func_74768_a("ChargeHits", this.chargeHits);
    compound.func_74757_a("SummonLightning", this.canSummonLightning);
    if (this.field_70725_aQ > 0 && this.field_70717_bb != null)
      compound.func_186854_a("KillerUUID", this.field_70717_bb.func_110124_au()); 
    for (String unusedNBT : new String[] { "powered", "Fuse", "ExplosionRadius", "ignited" })
      compound.func_82580_o(unusedNBT); 
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    setJumpAttacking(compound.func_74767_n("JumpAttacking"));
    setCharging(compound.func_74767_n("Charging"));
    this.chargeTime = compound.func_74762_e("ChargeTime");
    this.chargeHits = compound.func_74762_e("ChargeHits");
    this.canSummonLightning = compound.func_74767_n("SummonLightning");
    if (compound.func_186855_b("KillerUUID")) {
      this.field_70718_bc = Integer.MAX_VALUE;
      this.field_70717_bb = this.field_70170_p.func_152378_a(compound.func_186857_a("KillerUUID"));
      this.deathCause = DamageSource.func_76365_a(this.field_70717_bb);
    } 
  }
  
  protected ResourceLocation func_184647_J() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_creeper");
  }
  
  class SpawnMinionsGoal extends EntityAIBase {
    public boolean func_75250_a() {
      return (EntityMutantCreeper.this.func_70638_az() != null && EntityMutantCreeper.this.func_70068_e((Entity)EntityMutantCreeper.this.func_70638_az()) <= 1024.0D && EntityMutantCreeper.this.field_70122_E && !EntityMutantCreeper.this.isCharging()) ? ((EntityMutantCreeper.this.field_70146_Z.nextFloat() >= 0.99F)) : false;
    }
    
    public void func_75249_e() {
      int maxSpawn = EntityMutantCreeper.this.field_70170_p.func_175659_aa().func_151525_a() * 2;
      for (int i = (int)Math.ceil((EntityMutantCreeper.this.func_110143_aJ() / EntityMutantCreeper.this.func_110138_aP() * maxSpawn)); i > 0; i--) {
        EntityCreeper creeper = new EntityCreeper(EntityMutantCreeper.this.field_70170_p);
        creeper.field_70173_aa = 30;
        double x = EntityMutantCreeper.this.field_70165_t + (EntityMutantCreeper.this.field_70146_Z.nextFloat() - EntityMutantCreeper.this.field_70146_Z.nextFloat());
        double y = EntityMutantCreeper.this.field_70163_u + (EntityMutantCreeper.this.field_70146_Z.nextFloat() * 0.5F);
        double z = EntityMutantCreeper.this.field_70161_v + (EntityMutantCreeper.this.field_70146_Z.nextFloat() - EntityMutantCreeper.this.field_70146_Z.nextFloat());
        double xx = (EntityMutantCreeper.this.func_70638_az()).field_70165_t - EntityMutantCreeper.this.field_70165_t;
        double yy = (EntityMutantCreeper.this.func_70638_az()).field_70163_u - EntityMutantCreeper.this.field_70163_u;
        double zz = (EntityMutantCreeper.this.func_70638_az()).field_70161_v - EntityMutantCreeper.this.field_70161_v;
        creeper.field_70159_w = xx + (EntityMutantCreeper.this.field_70146_Z.nextFloat() * 0.05F);
        creeper.field_70181_x = yy + (EntityMutantCreeper.this.field_70146_Z.nextFloat() * 0.05F);
        creeper.field_70179_y = zz + (EntityMutantCreeper.this.field_70146_Z.nextFloat() * 0.05F);
        creeper.func_70012_b(x, y, z, EntityMutantCreeper.this.field_70177_z, EntityMutantCreeper.this.field_70125_A);
        creeper.setOwnerId(EntityMutantCreeper.this.func_184753_b());
        creeper.func_70624_b(EntityMutantCreeper.this.func_70638_az());
        creeper.setGrowingAge(-18000);
        creeper.setLimitedLife(100);
        creeper.setPowered(EntityMutantCreeper.this.getPowered());
        EntityMutantCreeper.this.field_70170_p.func_72838_d((Entity)creeper);
      } 
    }
    
    public boolean func_75253_b() {
      return false;
    }
  }
  
  class ChargeAttackGoal extends EntityAIBase {
    public ChargeAttackGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase target = EntityMutantCreeper.this.func_70638_az();
      boolean attemptHeal = (EntityMutantCreeper.this.func_110138_aP() - EntityMutantCreeper.this.func_110143_aJ() >= EntityMutantCreeper.this.func_110138_aP() / 6.0F);
      return EntityMutantCreeper.this.isCharging() ? true : ((target != null && EntityMutantCreeper.this.field_70122_E && attemptHeal && EntityMutantCreeper.this.func_70068_e((Entity)target) >= 25.0D && EntityMutantCreeper.this.func_70068_e((Entity)target) <= 1024.0D) ? ((EntityMutantCreeper.this.field_70146_Z.nextFloat() * 100.0F < 0.7F)) : false);
    }
    
    public boolean func_75253_b() {
      if (EntityMutantCreeper.this.canSummonLightning && EntityMutantCreeper.this.func_70638_az() != null && EntityMutantCreeper.this.func_70068_e((Entity)EntityMutantCreeper.this.func_70638_az()) < 25.0D)
        return false; 
      return (EntityMutantCreeper.this.chargeTime < 100 && EntityMutantCreeper.this.chargeHits > 0);
    }
    
    public void func_75249_e() {
      EntityMutantCreeper.this.setCharging(true);
      EntityMutantCreeper.this.field_70699_by.func_75499_g();
      if (EntityMutantCreeper.this.chargeHits == 0)
        EntityMutantCreeper.this.chargeHits = 3 + EntityMutantCreeper.this.field_70146_Z.nextInt(3); 
      if (EntityMutantCreeper.this.field_70146_Z.nextInt(EntityMutantCreeper.this.field_70170_p.func_72911_I() ? 2 : 6) == 0 && !EntityMutantCreeper.this.getPowered())
        EntityMutantCreeper.this.canSummonLightning = true; 
    }
    
    public void func_75246_d() {}
    
    public void func_75251_c() {
      if (EntityMutantCreeper.this.canSummonLightning && EntityMutantCreeper.this.func_70638_az() != null && EntityMutantCreeper.this.func_70068_e((Entity)EntityMutantCreeper.this.func_70638_az()) < 25.0D && EntityMutantCreeper.this.field_70170_p.func_175710_j(EntityMutantCreeper.this.func_180425_c())) {
        EntityMutantCreeper.this.field_70170_p.func_72838_d((Entity)new EntityLightningBolt(EntityMutantCreeper.this.field_70170_p, EntityMutantCreeper.this.field_70165_t, EntityMutantCreeper.this.field_70163_u, EntityMutantCreeper.this.field_70161_v, false));
      } else if (EntityMutantCreeper.this.chargeTime >= 100) {
        EntityMutantCreeper.this.func_70691_i(EntityMutantCreeper.this.func_110138_aP() / 4.0F);
        EntityMutantCreeper.this.field_70170_p.func_72960_a((Entity)EntityMutantCreeper.this, (byte)6);
      } 
      EntityMutantCreeper.this.chargeTime = 0;
      EntityMutantCreeper.this.chargeHits = 4 + EntityMutantCreeper.this.field_70146_Z.nextInt(3);
      EntityMutantCreeper.this.setCharging(false);
      EntityMutantCreeper.this.canSummonLightning = false;
    }
  }
  
  class JumpGoal extends EntityAIBase {
    public boolean func_75250_a() {
      EntityLivingBase target = EntityMutantCreeper.this.func_70638_az();
      return (target != null && EntityMutantCreeper.this.func_70011_f(target.field_70165_t, EntityMutantCreeper.this.field_70163_u, target.field_70161_v) >= 32.0D && EntityMutantCreeper.this.field_70122_E && !EntityMutantCreeper.this.isCharging());
    }
    
    public void func_75249_e() {
      EntityMutantCreeper.this.setJumpAttacking(true);
      double d01 = (EntityMutantCreeper.this.func_70638_az()).field_70165_t - EntityMutantCreeper.this.field_70165_t;
      double d11 = (EntityMutantCreeper.this.func_70638_az()).field_70161_v - EntityMutantCreeper.this.field_70161_v;
      float f21 = MathHelper.func_76133_a(d01 * d01 + d11 * d11);
      double hor = f21 / EntityMutantCreeper.this.func_70011_f((EntityMutantCreeper.this.func_70638_az()).field_70165_t, EntityMutantCreeper.this.field_70163_u, (EntityMutantCreeper.this.func_70638_az()).field_70161_v) * 2.0D;
      double ver = 2.0D;
      EntityMutantCreeper.this.field_70159_w = d01 / f21 * hor * hor;
      EntityMutantCreeper.this.field_70179_y = d11 / f21 * hor * hor;
      EntityMutantCreeper.this.field_70181_x = ver;
      EntityMutantCreeper.this.func_70097_a(DamageSource.field_76377_j, 1.0F);
    }
    
    public boolean func_75253_b() {
      return false;
    }
  }
  
  public void func_110206_u(int jumpPowerIn) {
    if (func_184207_aI()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  public boolean func_184776_b() {
    return true;
  }
  
  public void func_184775_b(int p_184775_1_) {}
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br / 3.0F;
      forward = entitylivingbase.field_191988_bg / 3.0F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        if (this.jumpPower >= 0.9F)
          setJumpAttacking(true); 
        this.field_70181_x = 3.0D * this.jumpPower * getFittness();
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-1.6F * f * this.jumpPower);
          this.field_70179_y += (1.6F * f1 * this.jumpPower);
        } 
        this.jumpPower = 0.0F;
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
}
