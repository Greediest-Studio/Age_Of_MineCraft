package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperEssence;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.world.TeleporterDarkRealm;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityJzahar extends EntityTameBase implements IRangedAttackMob, Armored, Massive, Undead {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 10.0D, 0);
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 0.8D, 60, 64.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.0D, true);
  
  private int talkTimer;
  
  private int shoutTicks;
  
  private int iframes;
  
  private static final DataParameter<Integer> EARTHQUAKETIMER = EntityDataManager.func_187226_a(EntityJzahar.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> BLACKHOLETIMER = EntityDataManager.func_187226_a(EntityJzahar.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> IMPLOSIONTIMER = EntityDataManager.func_187226_a(EntityJzahar.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> SHOUTTIMER = EntityDataManager.func_187226_a(EntityJzahar.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> COOLDOWNTIMER = EntityDataManager.func_187226_a(EntityJzahar.class, DataSerializers.field_187192_b);
  
  private boolean doShout;
  
  double speed;
  
  public EntityJzahar(World par1World) {
    super(par1World);
    this.speed = 0.05D;
    func_70105_a(1.5F, 6.0F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 64.0F, 16.0F));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.6D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.field_70178_ae = true;
    this.field_70158_ak = true;
    this.isOffensive = true;
    setLevel(300);
  }
  
  public int playMusic() {
    return 8;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186741_a(true);
    if (func_110143_aJ() > func_110138_aP() * 0.75D && this.bossInfo.func_186736_g() != BossInfo.Color.BLUE)
      this.bossInfo.func_186745_a(BossInfo.Color.BLUE); 
    if (func_110143_aJ() < func_110138_aP() * 0.75D && func_110143_aJ() > func_110138_aP() / 2.0F && this.bossInfo.func_186736_g() != BossInfo.Color.GREEN)
      this.bossInfo.func_186745_a(BossInfo.Color.GREEN); 
    if (func_110143_aJ() < func_110138_aP() / 2.0F && func_110143_aJ() > func_110138_aP() / 4.0F && this.bossInfo.func_186736_g() != BossInfo.Color.YELLOW)
      this.bossInfo.func_186745_a(BossInfo.Color.YELLOW); 
    if (func_110143_aJ() < func_110138_aP() / 4.0F && func_110143_aJ() > 0.0F && this.bossInfo.func_186736_g() != BossInfo.Color.RED)
      this.bossInfo.func_186745_a(BossInfo.Color.RED); 
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public String func_70005_c_() {
    return TextFormatting.BLUE + super.func_70005_c_() + TextFormatting.WHITE;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 1.25F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 2.0F;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.35D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(20.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(100.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(500.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(50.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(EARTHQUAKETIMER, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(BLACKHOLETIMER, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(IMPLOSIONTIMER, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(SHOUTTIMER, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(COOLDOWNTIMER, Integer.valueOf(0));
  }
  
  public int getTimer(int timer) {
    switch (timer) {
      case 0:
        return ((Integer)this.field_70180_af.func_187225_a(EARTHQUAKETIMER)).intValue();
      case 1:
        return ((Integer)this.field_70180_af.func_187225_a(BLACKHOLETIMER)).intValue();
      case 2:
        return ((Integer)this.field_70180_af.func_187225_a(IMPLOSIONTIMER)).intValue();
      case 3:
        return ((Integer)this.field_70180_af.func_187225_a(SHOUTTIMER)).intValue();
      case 4:
        return ((Integer)this.field_70180_af.func_187225_a(COOLDOWNTIMER)).intValue();
    } 
    return 0;
  }
  
  public void setTimer(int timer, int value) {
    switch (timer) {
      case 0:
        this.field_70180_af.func_187227_b(EARTHQUAKETIMER, Integer.valueOf(value));
        break;
      case 1:
        this.field_70180_af.func_187227_b(BLACKHOLETIMER, Integer.valueOf(value));
        break;
      case 2:
        this.field_70180_af.func_187227_b(IMPLOSIONTIMER, Integer.valueOf(value));
        break;
      case 3:
        this.field_70180_af.func_187227_b(SHOUTTIMER, Integer.valueOf(value));
        break;
      case 4:
        this.field_70180_af.func_187227_b(COOLDOWNTIMER, Integer.valueOf(value));
        break;
    } 
  }
  
  public void decrementTimer(int timer) {
    setTimer(timer, getTimer(timer) - 1);
  }
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 100.0F;
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
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187594_A;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187526_aP;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187849_gA;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.5F : 5.0F;
  }
  
  protected boolean func_70692_ba() {
    return false;
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && this.isOffensive && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()) && func_70068_e((Entity)func_70638_az()) < (this.field_70130_N * this.field_70130_N + (func_70638_az()).field_70130_N * (func_70638_az()).field_70130_N) + 36.0D && (this.field_70173_aa + func_145782_y()) % 10 == 0)
      func_70652_k((Entity)func_70638_az()); 
    if (this.field_70173_aa % 5 == 0)
      func_70691_i(2.0F); 
  }
  
  protected boolean func_184219_q(Entity passenger) {
    return (func_184188_bt().size() < 3);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
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
      if (i == 2)
        passenger.func_70107_b(this.field_70165_t - (f4 * 1.5F), this.field_70163_u + (4.0F * getFittness()) + (MathHelper.func_76134_b(this.field_70173_aa * 0.3F) * 0.25F), this.field_70161_v - (f11 * 1.5F)); 
      if (i == 1)
        passenger.func_70107_b(this.field_70165_t + (f4 * 1.5F), this.field_70163_u + (4.0F * getFittness()) + (MathHelper.func_76134_b(this.field_70173_aa * 0.3F) * 0.25F), this.field_70161_v + (f11 * 1.5F)); 
      if (i == 0)
        passenger.func_70107_b(this.field_70165_t + (f11 * 0.25F), this.field_70163_u + (5.25F * getFittness()), this.field_70161_v - (f4 * 0.25F)); 
    } 
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_70090_H() || func_180799_ab())
        this.field_70181_x += 0.05D; 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * ((func_70090_H() || func_180799_ab()) ? 5.0F : 1.0F));
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (entitylivingbase.field_191988_bg > 0.0F && this.field_70173_aa % 7 == 0)
        func_180429_a(new BlockPos((Entity)this), this.field_70170_p.func_180495_p(new BlockPos((Entity)this)).func_177230_c()); 
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
  
  public void func_70690_d(PotionEffect potioneffectIn) {
    if (!potioneffectIn.func_188419_a().func_76398_f())
      super.func_70690_d(potioneffectIn); 
  }
  
  public boolean takesFallDamage() {
    return false;
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
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 4.5F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (source.func_76347_k())
      return false; 
    if (source.func_94541_c())
      return false; 
    if (source.func_82725_o())
      return false; 
    if (source.func_76355_l() == "chaosImplosion" || source.func_76355_l() == "de.GuardianFireball" || source.func_76355_l() == "de.GuardianEnergyBall" || source.func_76355_l() == "de.GuardianChaosBall") {
      amount *= 0.25F;
    } else {
      if (this.iframes > 0)
        return false; 
      this.iframes = 10;
    } 
    return super.func_70097_a(source, amount);
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  private double getHeadX(int par1) {
    if (par1 <= 0)
      return this.field_70165_t; 
    float f = (this.field_70761_aq + (180 * (par1 - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.func_76134_b(f);
    return this.field_70165_t + f1 * 1.3D;
  }
  
  private double getHeadY(int par1) {
    return (par1 <= 0) ? (this.field_70163_u + 3.75D) : (this.field_70163_u + 2.2D);
  }
  
  private double getHeadZ(int par1) {
    if (par1 <= 0)
      return this.field_70161_v; 
    float f = (this.field_70761_aq + (180 * (par1 - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.func_76126_a(f);
    return this.field_70161_v + f1 * 1.3D;
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.9F;
  }
  
  public void func_70636_d() {
    if (!this.field_70170_p.field_72995_K)
      if (func_70089_S()) {
        ChunkLoadingEvent.updateLoaded((Entity)this);
      } else {
        ChunkLoadingEvent.stopLoading((Entity)this);
      }  
    if (this.field_70163_u <= 0.0D) {
      func_70107_b(this.field_70165_t, 0.01D, this.field_70161_v);
      this.field_70122_E = true;
      this.field_70160_al = false;
    } 
    if (this.field_70181_x > 0.42D)
      this.field_70181_x = 0.42D; 
    this.isOffensive = true;
    if (this.talkTimer > 0)
      this.talkTimer--; 
    if (this.iframes > 0)
      this.iframes--; 
    float f = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
    float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 4.0F;
    float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 8.0F;
    List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_186662_g(100.0D));
    if (list != null && this.field_70173_aa > 20)
      for (int k2 = 0; k2 < list.size(); k2++) {
        Entity entity = list.get(k2);
        if (entity instanceof EntityLivingBase && entity.func_70089_S())
          if (entity instanceof net.minecraft.entity.boss.EntityDragon || entity instanceof net.minecraft.entity.boss.EntityWither) {
            if (!this.field_70170_p.field_72995_K) {
              ((EntityLivingBase)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0D);
              ((EntityLivingBase)entity).func_70606_j(1.0F);
              func_70652_k(entity);
              entity.func_70097_a(DamageSource.field_76380_i, 1.0F);
              if (!entity.func_70089_S()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.field_70170_p, this);
                entitywitherskull.func_82149_j(entity);
                entitywitherskull.field_70163_u++;
                this.field_70170_p.func_72838_d(entitywitherskull);
                entitywitherskull.func_184220_m(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was super-banished by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
                list.remove(entity);
                SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { !isWild() ? I18n.func_74838_a("message.jzaharhelpful.banish.vanilla") : I18n.func_74838_a("message.jzahar.banish.vanilla") });
              } 
            } else if (ACConfig.particleEntity) {
              this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, entity.field_70165_t + f, entity.field_70163_u + 2.0D + f1, entity.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
            } 
          } else if (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonBoss || entity instanceof com.shinoow.abyssalcraft.common.entity.EntitySacthoth || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityChagaroth) {
            if (!this.field_70170_p.field_72995_K) {
              ((EntityLivingBase)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0D);
              ((EntityLivingBase)entity).func_70606_j(1.0F);
              func_70652_k(entity);
              entity.func_70097_a(DamageSource.field_76380_i, 1.0F);
              if (!entity.func_70089_S()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.field_70170_p, this);
                entitywitherskull.func_82149_j(entity);
                entitywitherskull.field_70163_u++;
                this.field_70170_p.func_72838_d(entitywitherskull);
                entitywitherskull.func_184220_m(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was super-banished by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
                SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { !isWild() ? I18n.func_74838_a("message.jzaharhelpful.banish.ac") : I18n.func_74838_a("message.jzahar.banish.ac") });
              } 
            } else if (ACConfig.particleEntity) {
              this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, entity.field_70165_t + f, entity.field_70163_u + 2.0D + f1, entity.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
            } 
          } else if (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityJzahar) {
            if (!this.field_70170_p.field_72995_K) {
              ((EntityLivingBase)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0D);
              ((EntityLivingBase)entity).func_70606_j(1.0F);
              func_70652_k(entity);
              entity.func_70097_a(DamageSource.field_76380_i, 1.0F);
              if (!entity.func_70089_S()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.field_70170_p, this);
                entitywitherskull.func_82149_j(entity);
                entitywitherskull.field_70163_u++;
                this.field_70170_p.func_72838_d(entitywitherskull);
                entitywitherskull.func_184220_m(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was super-banished by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
                SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { !isWild() ? I18n.func_74838_a("message.jzaharhelpful.banish.jzh") : I18n.func_74838_a("message.jzahar.banish.jzh") });
              } 
            } else if (ACConfig.particleEntity) {
              this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, entity.field_70165_t + f, entity.field_70163_u + 2.0D + f1, entity.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
            } 
          } else if (!entity.func_184222_aU()) {
            if (!this.field_70170_p.field_72995_K) {
              ((EntityLivingBase)entity).func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0D);
              ((EntityLivingBase)entity).func_70606_j(1.0F);
              func_70652_k(entity);
              entity.func_70097_a(DamageSource.field_76380_i, 1.0F);
              if (Loader.isModLoaded("draconicevolution") && entity instanceof com.brandon3055.draconicevolution.entity.EntityGuardianCrystal)
                entity.func_70106_y(); 
              if (!entity.func_70089_S()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.field_70170_p, this);
                entitywitherskull.func_82149_j(entity);
                entitywitherskull.field_70163_u++;
                this.field_70170_p.func_72838_d(entitywitherskull);
                entitywitherskull.func_184220_m(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was super-banished by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
                SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { !isWild() ? I18n.func_74838_a("message.jzaharhelpful.banish.other") : I18n.func_74838_a("message.jzahar.banish.other") });
              } 
            } else if (ACConfig.particleEntity) {
              this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, entity.field_70165_t + f, entity.field_70163_u + 2.0D + f1, entity.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
            } 
          } else if (entity instanceof EntityPlayer && ((EntityPlayer)entity).field_71075_bZ.field_75098_d && this.talkTimer == 0 && func_70068_e(entity) <= 8.0D) {
            this.talkTimer = 1200;
            if (this.field_70170_p.field_72995_K)
              if (EntityUtil.isPlayerCoralium((EntityPlayer)entity)) {
                SpecialTextUtil.JzaharText(new String[] { "Hello, old friend." });
              } else if (func_184191_r(entity)) {
                SpecialTextUtil.JzaharText(new String[] { String.format(I18n.func_74838_a("message.jzaharhelpful.creative.1"), new Object[] { entity.func_70005_c_() }) });
                SpecialTextUtil.JzaharText(new String[] { I18n.func_74838_a("message.jzaharhelpful.creative.2") });
              } else {
                SpecialTextUtil.JzaharText(new String[] { String.format(I18n.func_74838_a("message.jzahar.creative.1"), new Object[] { entity.func_70005_c_() }) });
                SpecialTextUtil.JzaharText(new String[] { I18n.func_74838_a("message.jzahar.creative.2") });
              }  
          }  
      }  
    if (func_70638_az() != null)
      if (func_70068_e((Entity)func_70638_az()) > 200.0D || func_70638_az() instanceof net.minecraft.entity.EntityFlying || (func_70638_az()).field_70163_u > this.field_70163_u + 4.0D) {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiArrowAttack);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiAttackOnCollide);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      }  
    super.func_70636_d();
    if (!this.field_70170_p.field_72995_K && func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && this.field_70146_Z.nextInt(100) == 0) {
      double d1 = getHeadX(1);
      double d2 = getHeadY(1);
      double d3 = getHeadZ(1);
      fireLightning((Entity)func_70638_az(), d1, d2, d3);
    } 
    if (!this.field_70170_p.field_72995_K && func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && func_70638_az().func_70685_l((Entity)this) && this.field_70146_Z.nextInt(100) == 0) {
      double d1 = getHeadX(2);
      double d2 = getHeadY(2);
      double d3 = getHeadZ(2);
      fireLightning((Entity)func_70638_az(), d1, d2, d3);
    } 
    if (this.deathTicks == 0) {
      decrementTimer(0);
      decrementTimer(1);
      decrementTimer(2);
      decrementTimer(3);
      decrementTimer(4);
      if (getTimer(0) > 600)
        for (Entity entity : this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_186662_g(64.0D))) {
          if (entity.field_70122_E && entity instanceof EntityLivingBase && !func_184191_r(entity)) {
            entity.field_70159_w += (float)(Math.random() * 0.1D - 0.05D);
            entity.field_70181_x += (float)(Math.random() * 0.1D - 0.05D);
            entity.field_70179_y += (float)(Math.random() * 0.1D - 0.05D);
            ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 20, 3));
            if (this.field_70146_Z.nextInt(5) == 0)
              ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_76431_k, 20, 3)); 
            entity.field_70125_A += (float)(Math.random() * 4.0D - 2.0D);
            entity.field_70177_z += (float)(Math.random() * 4.0D - 2.0D);
            if (this.field_70146_Z.nextInt(5) == 0) {
              entity.field_70181_x = 0.5D;
              if (entity instanceof EntityLivingBase)
                inflictEngenderMobDamage((EntityLivingBase)entity, " was shaken to bits by ", DamageSource.field_190095_e, 1.0F); 
            } 
          } 
        }  
      if (getTimer(3) < 0 && this.field_70146_Z.nextInt(20) == 0 && func_70638_az() != null && func_70068_e((Entity)func_70638_az()) <= 9216.0D && !this.doShout && getTimer(4) < 0) {
        func_184185_a(ACSounds.jzahar_shout, 5.0F, 1.0F);
        if (!this.field_70170_p.field_72995_K)
          SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { "Uftoin..." }); 
        this.shoutTicks = getTimer(3) - 30;
        this.doShout = true;
        setTimer(4, 100);
      } 
      if (getTimer(3) < this.shoutTicks && this.doShout) {
        this.doShout = false;
        this.field_70170_p.func_72960_a((Entity)this, (byte)23);
        setTimer(3, 400);
        func_184185_a(ACSounds.jzahar_blast, 5.0F, 1.0F);
        if (!this.field_70170_p.field_72995_K)
          SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { "...mglagln!" }); 
        if (func_70089_S()) {
          double size = 64.0D;
          Vec3d vector = func_70040_Z();
          for (Entity entity : this.field_70170_p.func_72872_a(Entity.class, func_174813_aQ().func_186662_g(size).func_72317_d(vector.field_72450_a * 32.0D, vector.field_72448_b * 32.0D, vector.field_72449_c * 32.0D))) {
            if (entity instanceof EntityLivingBase && !func_184191_r(entity)) {
              double dx = vector.field_72450_a;
              double dy = vector.field_72449_c;
              double dz = vector.field_72449_c;
              double spread = 10.0D;
              double velocity = 2.0D + func_70681_au().nextDouble() * 8.0D;
              dx += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
              dy += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
              dz += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
              dx *= velocity;
              dy *= velocity * 0.25D;
              dz *= velocity;
              entity.func_70024_g(dx, dy, dz);
              if (entity instanceof EntityLivingBase)
                inflictEngenderMobDamage((EntityLivingBase)entity, " was shouted apart by ", DamageSource.field_188406_j, 2.0F * (float)velocity); 
            } 
          } 
        } 
      } 
      if (getTimer(0) < 0 && this.field_70146_Z.nextInt(400) == 0 && func_70638_az() != null && (func_70638_az()).field_70122_E && getTimer(4) < 0) {
        func_184609_a(EnumHand.MAIN_HAND);
        setTimer(0, 1000);
        func_184185_a(ACSounds.jzahar_earthquake, 5.0F, 1.0F);
        if (!this.field_70170_p.field_72995_K)
          SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { "Shugnah throd!" }); 
        setTimer(4, 100);
      } 
      if (getTimer(2) < 0 && func_70638_az() != null && getTimer(4) < 0) {
        func_184609_a(EnumHand.MAIN_HAND);
        setTimer(2, 1200);
        func_184185_a(ACSounds.jzahar_implosion, 5.0F, 1.0F);
        if (!this.field_70170_p.field_72995_K)
          SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { "Nilgh'ri mtli!" }); 
        EntityImplosion entitywitherskull = new EntityImplosion(this.field_70170_p, this);
        BlockPos targetpos = func_70638_az().func_180425_c();
        entitywitherskull.func_70107_b((targetpos.func_177958_n() + this.field_70146_Z.nextInt(10) * (this.field_70146_Z.nextBoolean() ? 1 : -1)), (targetpos.func_177956_o() + 2), (targetpos.func_177952_p() + this.field_70146_Z.nextInt(10) * (this.field_70146_Z.nextBoolean() ? 1 : -1)));
        this.field_70170_p.func_175718_b(3000, entitywitherskull.func_180425_c(), 0);
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d(entitywitherskull); 
        setTimer(4, 100);
      } 
      if (getTimer(1) < 0 && this.field_70146_Z.nextInt(800) == 0 && func_70638_az() != null && getTimer(4) < 0 && !ACConfig.no_black_holes) {
        func_184609_a(EnumHand.MAIN_HAND);
        setTimer(1, 1600);
        func_184185_a(ACSounds.jzahar_black_hole, 5.0F, 1.0F);
        if (!this.field_70170_p.field_72995_K)
          SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { "Ph'nilgh'ri n'ghft!" }); 
        EntityBlackHole entitywitherskull = new EntityBlackHole(this.field_70170_p, this);
        entitywitherskull.func_82149_j((Entity)func_70638_az());
        BlockPos targetpos = func_70638_az().func_180425_c();
        entitywitherskull.func_70107_b((targetpos.func_177958_n() + (5 + this.field_70146_Z.nextInt(10)) * (this.field_70146_Z.nextBoolean() ? 1 : -1)), (targetpos.func_177956_o() + 2), (targetpos.func_177952_p() + (5 + this.field_70146_Z.nextInt(10)) * (this.field_70146_Z.nextBoolean() ? 1 : -1)));
        this.field_70170_p.func_175718_b(3000, entitywitherskull.func_180425_c(), 0);
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d(entitywitherskull); 
        setTimer(4, 100);
      } 
    } 
  }
  
  protected void addShoutParticles() {
    if (this.field_70170_p.field_72995_K) {
      Vec3d vector = func_70040_Z();
      double px = this.field_70165_t + vector.field_72450_a * 5.0D;
      double py = this.field_70163_u + func_70047_e() + vector.field_72448_b * 5.0D;
      double pz = this.field_70161_v + vector.field_72449_c * 5.0D;
      for (int i = 0; i < 2000; i++) {
        double dx = vector.field_72450_a;
        double dy = vector.field_72448_b;
        double dz = vector.field_72449_c;
        double spread = 10.0D;
        double velocity = 2.0D + func_70681_au().nextDouble() * 18.0D;
        dx += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dy += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dz += func_70681_au().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.field_70170_p.func_175682_a(EnumParticleTypes.EXPLOSION_NORMAL, true, px + func_70681_au().nextDouble() - 0.5D, py + func_70681_au().nextDouble() - 0.5D, pz + func_70681_au().nextDouble() - 0.5D, dx, dy, dz, new int[0]);
      } 
    } else {
      this.field_70170_p.func_72960_a((Entity)this, (byte)23);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 23) {
      addShoutParticles();
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public void func_70014_b(NBTTagCompound nbttag) {
    super.func_70014_b(nbttag);
    if (this.deathTicks > 0)
      nbttag.func_74768_a("DeathTicks", this.deathTicks); 
    nbttag.func_74768_a("EarthquakeTime", getTimer(0));
    nbttag.func_74768_a("BlackHoleTime", getTimer(1));
    nbttag.func_74768_a("ImplosionTime", getTimer(2));
    nbttag.func_74768_a("ShoutTime", getTimer(3));
    nbttag.func_74768_a("CooldownTime", getTimer(4));
  }
  
  public void func_70037_a(NBTTagCompound nbttag) {
    super.func_70037_a(nbttag);
    this.deathTicks = nbttag.func_74762_e("DeathTicks");
    setTimer(0, nbttag.func_74762_e("EarthquakeTime"));
    setTimer(1, nbttag.func_74762_e("BlackHoleTime"));
    setTimer(2, nbttag.func_74762_e("ImplosionTime"));
    setTimer(3, nbttag.func_74762_e("ShoutTime"));
    setTimer(4, nbttag.func_74762_e("CooldownTime"));
  }
  
  protected void func_70609_aI() {
    this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
    this.deathTicks++;
    if (!this.field_70170_p.field_72995_K)
      if (this.deathTicks == 1) {
        List<Entity> list = this.field_70170_p.field_72996_f;
        if (list != null)
          for (int k2 = 0; k2 < list.size(); k2++) {
            Entity entity = list.get(k2);
            if (entity instanceof EntityJzahar && entity.func_70089_S())
              SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { ((EntityJzahar)entity).func_184191_r((Entity)this) ? I18n.func_74838_a("message.jzaharhelpful.snidecomment.jzahar") : I18n.func_74838_a("message.jzahar.snidecomment.jzahar") }); 
          }  
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s Jz'ahar has been destroyed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your Jz'ahar has been destroyed!", new Object[0]));
        } 
      }  
    if (this.deathTicks <= 800) {
      if (this.deathTicks == 410)
        for (int a = 1; a < 10; a++)
          func_184185_a(ACSounds.jzahar_charge, a, 1.0F);  
      if (this.deathTicks < 400)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t, this.field_70163_u + 2.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]); 
      float f = (this.field_70146_Z.nextFloat() - 0.5F) * 3.0F;
      float f1 = (this.field_70146_Z.nextFloat() - 0.5F) * 2.0F;
      float f2 = (this.field_70146_Z.nextFloat() - 0.5F) * 3.0F;
      if (this.deathTicks >= 100 && this.deathTicks < 400)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + f, this.field_70163_u + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]); 
      if (this.deathTicks >= 200 && this.deathTicks < 400) {
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + f, this.field_70163_u + f1, this.field_70161_v + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.field_70170_p.func_175688_a(EnumParticleTypes.LAVA, this.field_70165_t, this.field_70163_u + 2.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[] { 0 });
      } 
      this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t, this.field_70163_u + 1.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
      if (this.deathTicks >= 790 && this.deathTicks <= 800) {
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t, this.field_70163_u + 1.5D, this.field_70161_v, 0.0D, 0.0D, 0.0D, new int[0]);
        func_184185_a(SoundEvents.field_187539_bB, 4.0F, (1.0F + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F) * 0.7F);
      } 
      if (this.deathTicks > 400 && this.deathTicks < 800) {
        float size = 64.0F;
        List<Entity> list = this.field_70170_p.func_72872_a(Entity.class, func_174813_aQ().func_72314_b(size, size, size));
        for (Entity entity : list) {
          double scale = (size - entity.func_70011_f(this.field_70165_t, this.field_70163_u, this.field_70161_v)) / size;
          Vec3d dir = new Vec3d(entity.field_70165_t - this.field_70165_t, entity.field_70163_u - this.field_70163_u, entity.field_70161_v - this.field_70161_v);
          dir = dir.func_72432_b();
          if (entity instanceof EntityLivingBase && func_184191_r(entity)) {
            entity.func_70024_g(dir.field_72450_a * this.speed * scale, dir.field_72448_b * this.speed * scale, dir.field_72449_c * this.speed * scale);
            continue;
          } 
          entity.func_70024_g(dir.field_72450_a * -this.speed * scale, dir.field_72448_b * -this.speed * scale, dir.field_72449_c * -this.speed * scale);
        } 
        this.speed += 0.002D;
      } 
    } 
    if (!this.field_70170_p.field_72995_K && 
      this.deathTicks > 750 && this.deathTicks % 5 == 0) {
      int i = 500;
      while (i > 0) {
        int j = EntityXPOrb.func_70527_a(i);
        i -= j;
        this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
      } 
    } 
    if (this.deathTicks == 790 && !this.field_70170_p.field_72995_K) {
      List<BlockPos> blocks = new ArrayList<>();
      for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
          for (int z = 0; z < 10; z++) {
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z)))
              blocks.add(new BlockPos(this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v + z)); 
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t - x, this.field_70163_u + y, this.field_70161_v + z)))
              blocks.add(new BlockPos(this.field_70165_t - x, this.field_70163_u + y, this.field_70161_v + z)); 
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v - z)))
              blocks.add(new BlockPos(this.field_70165_t + x, this.field_70163_u + y, this.field_70161_v - z)); 
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t - x, this.field_70163_u + y, this.field_70161_v - z)))
              blocks.add(new BlockPos(this.field_70165_t - x, this.field_70163_u + y, this.field_70161_v - z)); 
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t + x, this.field_70163_u - y, this.field_70161_v + z)))
              blocks.add(new BlockPos(this.field_70165_t + x, this.field_70163_u - y, this.field_70161_v + z)); 
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t - x, this.field_70163_u - y, this.field_70161_v + z)))
              blocks.add(new BlockPos(this.field_70165_t - x, this.field_70163_u - y, this.field_70161_v + z)); 
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t + x, this.field_70163_u - y, this.field_70161_v - z)))
              blocks.add(new BlockPos(this.field_70165_t + x, this.field_70163_u - y, this.field_70161_v - z)); 
            if (!this.field_70170_p.func_175623_d(new BlockPos(this.field_70165_t - x, this.field_70163_u - y, this.field_70161_v - z)))
              blocks.add(new BlockPos(this.field_70165_t - x, this.field_70163_u - y, this.field_70161_v - z)); 
          } 
        } 
      } 
      for (BlockPos pos : blocks) {
        if (this.field_70170_p.func_180495_p(pos).func_177230_c() != Blocks.field_150357_h)
          this.field_70170_p.func_175698_g(pos); 
      } 
      if (!this.field_70170_p.func_72872_a(Entity.class, func_174813_aQ().func_72314_b(3.0D, 1.0D, 3.0D)).isEmpty()) {
        List<Entity> entities = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(3.0D, 1.0D, 3.0D));
        for (Entity entity : entities) {
          if (entity instanceof EntityPlayer && !func_184191_r(entity)) {
            EntityPlayer player = (EntityPlayer)entity;
            player.func_70606_j(1.0F);
            player.func_70690_d(new PotionEffect(MobEffects.field_76440_q, 2400, 5));
            player.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 2400, 5));
            player.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 2400, 5));
            player.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 2400, 5));
            player.func_70690_d(new PotionEffect(MobEffects.field_76419_f, 2400, 5));
            player.func_70690_d(new PotionEffect(MobEffects.field_76437_t, 2400, 5));
            player.func_70690_d(new PotionEffect(MobEffects.field_76438_s, 2400, 5));
            player.func_70690_d(new PotionEffect(MobEffects.field_76436_u, 2400, 5));
            if (player instanceof EntityPlayerMP) {
              WorldServer worldServer = (WorldServer)player.field_70170_p;
              EntityPlayerMP mp = (EntityPlayerMP)player;
              mp.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 80, 255));
              mp.field_71133_b.func_184103_al().transferPlayerToDimension(mp, ACLib.dark_realm_id, (Teleporter)new TeleporterDarkRealm(worldServer));
            } 
            continue;
          } 
          if ((entity instanceof EntityLivingBase && !func_184191_r(entity)) || !(entity instanceof EntityLivingBase))
            entity.func_70106_y(); 
        } 
      } 
    } 
    if (!isWild()) {
      if (this.deathTicks == 20 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.1") }); 
      if (this.deathTicks == 100 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.2") }); 
      if (this.deathTicks == 180 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.3") }); 
      if (this.deathTicks == 260 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.4") }); 
      if (this.deathTicks == 340 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.5") }); 
      if (this.deathTicks == 420 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.6") }); 
      if (this.deathTicks == 500 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.7") }); 
      if (this.deathTicks == 580 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.8") }); 
      if (this.deathTicks == 660 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.9") }); 
      if (this.deathTicks == 800 && !this.field_70170_p.field_72995_K) {
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzaharhelpful.death.10") });
        int i = 60000;
        this.field_70170_p.func_72838_d((Entity)new EntityGatekeeperEssence(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v));
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
        } 
        func_70106_y();
      } 
    } else {
      if (this.deathTicks == 20 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.1") }); 
      if (this.deathTicks == 100 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.2") }); 
      if (this.deathTicks == 180 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.3") }); 
      if (this.deathTicks == 260 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.4") }); 
      if (this.deathTicks == 340 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.5") }); 
      if (this.deathTicks == 420 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.6") }); 
      if (this.deathTicks == 500 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.7") }); 
      if (this.deathTicks == 580 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.8") }); 
      if (this.deathTicks == 660 && !this.field_70170_p.field_72995_K)
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.9") }); 
      if (this.deathTicks == 800 && !this.field_70170_p.field_72995_K) {
        SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.death.10") });
        int i = 60000;
        this.field_70170_p.func_72838_d((Entity)new EntityGatekeeperEssence(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v));
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
        } 
        func_70106_y();
      } 
    } 
  }
  
  private void launchWitherSkullToEntity(int par1, EntityLivingBase par2EntityLivingBase) {
    launchWitherSkullToCoords(par1, par2EntityLivingBase.field_70165_t + par2EntityLivingBase.field_70159_w * 10.0D, par2EntityLivingBase.field_70163_u + 1.0D + ((par2EntityLivingBase.field_70131_O > 8.0F) ? 7.0D : (par2EntityLivingBase.field_70131_O * 0.5D)) + par2EntityLivingBase.field_70181_x * 10.0D, par2EntityLivingBase.field_70161_v + par2EntityLivingBase.field_70179_y * 10.0D, (par1 == 0 && this.field_70146_Z.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int par1, double par2, double par4, double par6, boolean par8) {
    this.field_70170_p.func_180498_a((EntityPlayer)null, 1024, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), 0);
    double d3 = getHeadX(par1);
    double d4 = getHeadY(par1);
    double d5 = getHeadZ(par1);
    double d6 = par2 - d3;
    double d7 = par4 - d4;
    double d8 = par6 - d5;
    EntityMiniBlackHole entitywitherskull = new EntityMiniBlackHole(this.field_70170_p, (EntityLivingBase)this, d6, d7, d8);
    entitywitherskull.field_70163_u = d4;
    entitywitherskull.field_70165_t = d3;
    entitywitherskull.field_70161_v = d5;
    this.field_70170_p.func_72838_d((Entity)entitywitherskull);
  }
  
  public void func_82196_d(EntityLivingBase par1EntityLivingBase, float par2) {
    launchWitherSkullToEntity(0, par1EntityLivingBase);
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.func_180482_a(difficulty, par1EntityLivingData);
    SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { "Allahu Akbar!" });
    IAttributeInstance attribute = func_110148_a(SharedMonsterAttributes.field_111264_e);
    Calendar calendar = this.field_70170_p.func_83015_S();
    attribute.func_111124_b(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.func_111121_a(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  public void func_70653_a(Entity entityIn, float strength, double xRatio, double zRatio) {}
}
