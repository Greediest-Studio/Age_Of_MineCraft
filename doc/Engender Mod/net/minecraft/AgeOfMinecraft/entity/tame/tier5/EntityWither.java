package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
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
import net.minecraft.world.BossInfo;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWither extends EntityTameBase implements IEntityMultiPart, IRangedAttackMob, IJumpingMount, Flying, Armored, Undead {
  private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer>[] HEAD_TARGETS = new DataParameter[] { FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET };
  
  private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
  
  private float[] xRotationHeads = new float[2];
  
  private float[] yRotationHeads = new float[2];
  
  private float[] xRotOHeads = new float[2];
  
  private float[] yRotOHeads = new float[2];
  
  private int[] nextHeadUpdate = new int[2];
  
  private int[] idleHeadUpdates = new int[2];
  
  private float[] headRandomTurn = new float[2];
  
  private static final DataParameter<Integer> HOVERTIMER = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> RAMTIMER = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> SPAWNEDSKELETONS = EntityDataManager.func_187226_a(EntityWither.class, DataSerializers.field_187198_h);
  
  private double hoverX;
  
  private double hoverZ;
  
  public ExtendMultiPartEntityPart[] partArray;
  
  public ExtendMultiPartEntityPart mainHead = new ExtendMultiPartEntityPart(this, "mainHead", 1.0F, 1.0F);
  
  public ExtendMultiPartEntityPart rightHead = new ExtendMultiPartEntityPart(this, "rightHead", 0.75F, 0.75F);
  
  public ExtendMultiPartEntityPart leftHead = new ExtendMultiPartEntityPart(this, "leftHead", 0.75F, 0.75F);
  
  public ExtendMultiPartEntityPart spine = new ExtendMultiPartEntityPart(this, "spine", 0.4F, 1.6F);
  
  public ExtendMultiPartEntityPart lowerspine = new ExtendMultiPartEntityPart(this, "lowerspine", 0.4F, 0.6F);
  
  public ExtendMultiPartEntityPart rightRibs = new ExtendMultiPartEntityPart(this, "rightRibs", 0.5F, 0.8F);
  
  public ExtendMultiPartEntityPart leftRibs = new ExtendMultiPartEntityPart(this, "leftRibs", 0.5F, 0.8F);
  
  public ExtendMultiPartEntityPart rightsupport = new ExtendMultiPartEntityPart(this, "rightsupport", 0.4F, 0.4F);
  
  public ExtendMultiPartEntityPart leftsupport = new ExtendMultiPartEntityPart(this, "leftsupport", 0.4F, 0.4F);
  
  private int blockBreakCounter;
  
  private int lastActiveTime;
  
  private int timeSinceIgnited;
  
  protected float jumpPower;
  
  public EntityWither(World worldIn) {
    super(worldIn);
    this.partArray = new ExtendMultiPartEntityPart[] { this.mainHead, this.rightHead, this.leftHead, this.spine, this.lowerspine, this.rightRibs, this.leftRibs, this.rightsupport, this.leftsupport };
    func_70606_j(func_110138_aP());
    func_70105_a(0.9F, 3.3F);
    this.isOffensive = true;
    this.field_70178_ae = true;
    ((PathNavigateGround)func_70661_as()).func_179693_d(true);
    this.field_70714_bg.func_75776_a(0, new AIDoNothing());
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 4.0D, 100.0F, 16.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 4.0D, 50, 20.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIWanderAvoidWaterFlying((EntityCreature)this, 2.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70158_ak = true;
    this.field_70728_aV = 500;
    this.field_70765_h = (EntityMoveHelper)new EntityFlyHelper((EntityLiving)this);
  }
  
  public String getDescName() {
    return "wither";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.LIGHT_PURPLE;
  }
  
  public int playMusic() {
    return 2;
  }
  
  public boolean func_190631_cK() {
    return (getInvulTime() <= 0 && super.func_190631_cK());
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    EntityTameBase.PathNavigateFlying pathnavigateflying = new EntityTameBase.PathNavigateFlying(this, (EntityLiving)this, worldIn);
    pathnavigateflying.setCanOpenDoors(true);
    pathnavigateflying.setCanFloat(true);
    pathnavigateflying.setCanEnterDoors(true);
    return (PathNavigate)pathnavigateflying;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
  }
  
  public boolean hasSpawnedSkeletons() {
    return ((Boolean)func_184212_Q().func_187225_a(SPAWNEDSKELETONS)).booleanValue();
  }
  
  public void setCanSpawnSkeletons(boolean childZombie) {
    func_184212_Q().func_187227_b(SPAWNEDSKELETONS, Boolean.valueOf(childZombie));
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186745_a(BossInfo.Color.PURPLE);
    this.bossInfo.func_186741_a(true);
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float p_70831_1_) {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / 30.0F;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getSpawnTimer() {
    return 1;
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 2.0F;
  }
  
  public float getBonusVSFlying() {
    return 4.0F;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
    func_110148_a(SharedMonsterAttributes.field_193334_e).func_111128_a(3.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(30.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(FIRST_HEAD_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(SECOND_HEAD_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(THIRD_HEAD_TARGET, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(INVULNERABILITY_TIME, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(HOVERTIMER, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(RAMTIMER, Integer.valueOf(0));
    func_184212_Q().func_187214_a(SPAWNEDSKELETONS, Boolean.valueOf(false));
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74757_a("SpawnSkeletons", hasSpawnedSkeletons());
    tagCompound.func_74768_a("Invul", getInvulTime());
    tagCompound.func_74768_a("Hover", getHoverTime());
    tagCompound.func_74768_a("Ram", getRamTime());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setCanSpawnSkeletons(tagCompund.func_74767_n("SpawnSkeletons"));
    setInvulTime(tagCompund.func_74762_e("Invul"));
    setHoverTime(tagCompund.func_74762_e("Hover"));
    setRamTime(tagCompund.func_74762_e("Ram"));
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187925_gy;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187851_gB;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187849_gA;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public float func_70047_e() {
    return func_70093_af() ? (this.field_70131_O * 0.83F - 0.1F) : (this.field_70131_O * 0.83F);
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.945D;
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
    if (func_184207_aI() && func_82171_bF()) {
      if (getInvulTime() > 0) {
        int j = getInvulTime() - 1;
        if (j <= 0) {
          createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, isHero() ? 35.0F : 7.0F, EngenderConfig.mobs.grief);
          this.field_70170_p.func_175669_a(1023, new BlockPos((Entity)this), 0);
        } 
        setInvulTime(j);
        if (this.field_70173_aa % 1 == 0)
          func_70691_i(1.0F); 
      } 
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      entitylivingbase.field_70143_R *= 0.0F;
      entitylivingbase.field_70172_ad = 40;
      entitylivingbase.func_70690_d(new PotionEffect(MobEffects.field_76429_m, 40, 4));
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      this.field_70747_aH = 0.2F;
      if (((EntityLivingBase)func_184179_bs()).field_70702_br != 0.0F && this.field_70173_aa % 10 == 0 && !this.field_70170_p.field_72995_K) {
        Vec3d vec3 = entitylivingbase.func_70676_i(1.0F);
        double d0 = entitylivingbase.field_70165_t + vec3.field_72450_a * 50.0D;
        double d1 = entitylivingbase.field_70163_u + entitylivingbase.func_70047_e() + vec3.field_72448_b * 50.0D;
        double d2 = entitylivingbase.field_70161_v + vec3.field_72449_c * 50.0D;
        launchWitherSkullToCoords(0, d0, d1, d2, (this.field_70146_Z.nextFloat() < 0.001F));
      } 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } 
      int i;
      for (i = 0; i < 256; i++) {
        int in = MathHelper.func_76128_c(this.field_70165_t - 1.5D + this.field_70146_Z.nextDouble() * 3.0D);
        int j = MathHelper.func_76128_c(this.field_70163_u + 3.0D - this.field_70146_Z.nextDouble() * 8.0D);
        int k = MathHelper.func_76128_c(this.field_70161_v - 1.5D + this.field_70146_Z.nextDouble() * 3.0D);
        BlockPos blockpos = new BlockPos(in, j, k);
        IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
        Block block = iblockstate.func_177230_c();
        if (block != Blocks.field_150350_a)
          func_70107_b(this.field_70165_t, this.field_70163_u + 0.01D, this.field_70161_v); 
      } 
      if (this.jumpPower > 0.0F) {
        this.field_70181_x += this.jumpPower;
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.jumpPower = 0.0F;
      } 
      for (i = 1; i < 3; i++) {
        if (!this.field_70170_p.field_72995_K && this.field_70173_aa >= this.nextHeadUpdate[i - 1]) {
          this.nextHeadUpdate[i - 1] = this.field_70173_aa + 10 + this.field_70146_Z.nextInt(10);
          if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
            int k2 = i - 1;
            int l2 = this.idleHeadUpdates[i - 1];
            this.idleHeadUpdates[k2] = this.idleHeadUpdates[i - 1] + 1;
            if (l2 > 15) {
              float f = 10.0F;
              float f1 = 5.0F;
              double d0 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70165_t - f, this.field_70165_t + f);
              double d1 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70163_u - f1, this.field_70163_u + f1);
              double d2 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70161_v - f, this.field_70161_v + f);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              this.idleHeadUpdates[i - 1] = 0;
            } 
          } 
          int i1 = getWatchedTargetId(i);
          if (i1 > 0) {
            Entity entity = this.field_70170_p.func_73045_a(i1);
            if (!func_70093_af() && entity != null && entity.func_70089_S() && func_70068_e(entity) <= 900.0D && func_70685_l(entity)) {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.idleHeadUpdates[i - 1] = 0;
              if (this.moralRaisedTimer > 200) {
                this.nextHeadUpdate[i - 1] = this.field_70173_aa + 20 + this.field_70146_Z.nextInt(10);
              } else {
                this.nextHeadUpdate[i - 1] = this.field_70173_aa + 40 + this.field_70146_Z.nextInt(20);
              } 
            } else {
              updateWatchedTargetId(i, 0);
            } 
          } else {
            List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(20.0D, 8.0D, 20.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
            for (int k1 = 0; k1 < 10 && !list.isEmpty(); k1++) {
              EntityLivingBase entitylivingbase1 = list.get(this.field_70146_Z.nextInt(list.size()));
              if (entitylivingbase1 != this && entitylivingbase1.func_70089_S() && func_70685_l((Entity)entitylivingbase1) && (!func_184191_r((Entity)entitylivingbase1) || (entitylivingbase1 instanceof EntityTameBase && func_184191_r((Entity)entitylivingbase1) && entitylivingbase1 != this && ((EntityTameBase)entitylivingbase1).getFakeHealth() > 0.0F && getFakeHealth() > 0.0F)) && entitylivingbase1 != getOwner())
                if (entitylivingbase1 instanceof EntityPlayer) {
                  if (!((EntityPlayer)entitylivingbase1).field_71075_bZ.field_75102_a)
                    updateWatchedTargetId(i, entitylivingbase1.func_145782_y()); 
                } else {
                  updateWatchedTargetId(i, entitylivingbase1.func_145782_y());
                }  
              if (entitylivingbase1 == this || !entitylivingbase1.func_70089_S() || !func_70685_l((Entity)entitylivingbase1) || func_184191_r((Entity)entitylivingbase1) || entitylivingbase1 == getOwner())
                list.remove(entitylivingbase1); 
            } 
          } 
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
    } else if (func_70638_az() != null) {
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
        float f = 0.95F;
        float f1 = 0.16277136F / f * f * f;
        func_191958_b(strafe, vertical, forward, 0.02F);
        f = 0.95F;
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= f;
        this.field_70181_x *= f;
        this.field_70179_y *= f;
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
      this.field_70747_aH = 0.02F;
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public void performSpecialAttack() {
    func_70642_aH();
    func_184185_a(ESound.witherSpecial, 10.0F, 1.0F);
    setSpecialAttackTimer(1600);
  }
  
  public boolean func_180427_aV() {
    return (getInvulTime() > 1 || super.func_180427_aV());
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public void becomeAHero() {
    super.becomeAHero();
    func_82206_m();
  }
  
  public void func_70636_d() {
    if (!getCurrentBook().func_190926_b()) {
      this.idleHeadUpdates[0] = 0;
      this.idleHeadUpdates[1] = 0;
    } 
    if (!func_175446_cd() && !this.field_70170_p.field_72995_K) {
      if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && this.isOffensive && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()))
        if (func_70068_e((Entity)func_70638_az()) < (this.reachWidth * this.reachWidth + ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N) * ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N)) + 9.0D && (this.field_70173_aa + func_145782_y()) % 10 == 0)
          func_70652_k((Entity)func_70638_az());  
      if (func_70089_S()) {
        ChunkLoadingEvent.updateLoaded((Entity)this);
      } else {
        ChunkLoadingEvent.stopLoading((Entity)this);
      } 
    } 
    setRamTime(getRamTime() - 1);
    setHoverTime(getHoverTime() - 1);
    if (this.hoverX == 0.0D)
      this.hoverX = this.field_70165_t; 
    if (this.hoverZ == 0.0D)
      this.hoverZ = this.field_70161_v; 
    for (int i = 0; i < this.nextHeadUpdate.length; i++) {
      if (this.nextHeadUpdate[i] > 90)
        this.xRotationHeads[i] = func_82204_b(this.xRotationHeads[i], 90.0F, 15.0F); 
    } 
    float f2 = this.field_70761_aq * 0.017453292F;
    float f19 = MathHelper.func_76126_a(f2);
    float f3 = MathHelper.func_76134_b(f2);
    double ex = (!this.field_70122_E && !func_184207_aI() && !func_82150_aj()) ? (MathHelper.func_76134_b(this.field_70173_aa * 0.2F) * 0.2F) : 0.0D;
    this.mainHead.func_70071_h_();
    this.mainHead.func_70012_b(this.field_70165_t, this.field_70163_u + func_70047_e() - 0.4D + ex, this.field_70161_v, 0.0F, 0.0F);
    this.rightHead.func_70071_h_();
    this.rightHead.func_70012_b(this.field_70165_t - (f3 * 1.15F), this.field_70163_u + func_70047_e() - 0.7D + ex, this.field_70161_v - (f19 * 1.15F), 0.0F, 0.0F);
    this.leftHead.func_70071_h_();
    this.leftHead.func_70012_b(this.field_70165_t + (f3 * 1.15F), this.field_70163_u + func_70047_e() - 0.7D + ex, this.field_70161_v + (f19 * 1.15F), 0.0F, 0.0F);
    this.rightsupport.func_70071_h_();
    this.rightsupport.func_70012_b(this.field_70165_t - (f3 * 0.5F), this.field_70163_u + func_70047_e() - 0.8D + ex, this.field_70161_v - (f19 * 0.5F), 0.0F, 0.0F);
    this.leftsupport.func_70071_h_();
    this.leftsupport.func_70012_b(this.field_70165_t + (f3 * 0.5F), this.field_70163_u + func_70047_e() - 0.8D + ex, this.field_70161_v + (f19 * 0.5F), 0.0F, 0.0F);
    this.spine.func_70071_h_();
    this.spine.func_70012_b(this.field_70165_t - (f19 * (-0.25F + MathHelper.func_76134_b(this.field_70173_aa * 0.1F + 3.1415927F) * 0.1F)), this.field_70163_u + 0.75D + ex, this.field_70161_v - (f3 * (0.25F - MathHelper.func_76134_b(this.field_70173_aa * 0.1F + 3.1415927F) * 0.1F)), 0.0F, 0.0F);
    this.lowerspine.func_70071_h_();
    this.lowerspine.func_70012_b(this.field_70165_t - (f19 * (-0.75F + MathHelper.func_76134_b(this.field_70173_aa * 0.1F - 1.0F + 3.1415927F) * 0.2F)), this.field_70163_u + 0.25D + ex, this.field_70161_v - (f3 * (0.75F - MathHelper.func_76134_b(this.field_70173_aa * 0.1F - 1.0F + 3.1415927F) * 0.2F)), 0.0F, 0.0F);
    this.rightRibs.func_70071_h_();
    this.rightRibs.func_70012_b(this.spine.field_70165_t + (f3 * 0.375F), this.field_70163_u + 0.95D + ex, this.spine.field_70161_v + (f19 * 0.375F), 0.0F, 0.0F);
    this.leftRibs.func_70071_h_();
    this.leftRibs.func_70012_b(this.spine.field_70165_t - (f3 * 0.375F), this.field_70163_u + 0.95D + ex, this.spine.field_70161_v - (f19 * 0.375F), 0.0F, 0.0F);
    if (!this.field_70170_p.field_72995_K && getInvulTime() <= 1) {
      if (isArmored() && !hasSpawnedSkeletons() && getFakeHealth() <= 0.0F) {
        this.field_70181_x = -(0.9D + this.field_70181_x);
      } else if (getWatchedTargetId(0) > 0 && func_184179_bs() == null && !(func_184179_bs() instanceof EntityPlayer)) {
        Entity entity = this.field_70170_p.func_73045_a(getWatchedTargetId(0));
        if (entity != null) {
          if (entity instanceof EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) {
            for (int m = 0; m < 256; m++) {
              int in = MathHelper.func_76128_c(this.field_70165_t - 1.5D + this.field_70146_Z.nextDouble() * 3.0D);
              int n = MathHelper.func_76128_c(this.field_70163_u + 3.0D - this.field_70146_Z.nextDouble() * 8.0D);
              int i1 = MathHelper.func_76128_c(this.field_70161_v - 1.5D + this.field_70146_Z.nextDouble() * 3.0D);
              BlockPos blockpos = new BlockPos(in, n, i1);
              IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
              Block block = iblockstate.func_177230_c();
              if (block != Blocks.field_150350_a)
                func_70107_b(this.field_70165_t, this.field_70163_u + 0.01D, this.field_70161_v); 
            } 
            if (func_70032_d(entity) > 4.0D && getRamTime() <= 0) {
              this.hoverX = entity.field_70165_t;
              this.hoverZ = entity.field_70161_v;
              if (!(entity instanceof EntityWither) && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) && this.field_70163_u < entity.field_70163_u) {
                this.field_70122_E = false;
                this.field_70160_al = true;
                this.field_70181_x += 0.5D - this.field_70181_x;
              } 
              if (!(entity instanceof EntityWither) && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) && this.field_70163_u > entity.field_70163_u) {
                this.field_70122_E = false;
                this.field_70160_al = true;
                this.field_70181_x -= 0.5D + this.field_70181_x;
              } 
            } 
          } 
          if ((entity instanceof EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityCommandBlockWither) && ((EntityLiving)entity).func_70638_az() != this) {
            if (this.field_70163_u < entity.field_70163_u - 1.0D) {
              this.field_70122_E = false;
              this.field_70160_al = true;
              this.field_70181_x += 0.5D - this.field_70181_x;
            } 
            if (this.field_70163_u > entity.field_70163_u + 1.0D) {
              this.field_70122_E = false;
              this.field_70160_al = true;
              this.field_70181_x -= 0.5D + this.field_70181_x;
            } 
          } else {
            if (this.field_70163_u < (isArmored() ? (entity.field_70163_u - ((hasSpawnedSkeletons() && getFakeHealth() <= 0.0F) ? 8.0D : 1.0D)) : (entity.field_70163_u + 4.0D + entity.func_70047_e()))) {
              this.field_70122_E = false;
              this.field_70160_al = true;
              this.field_70181_x += 0.5D - this.field_70181_x;
            } 
            if (this.field_70163_u > entity.field_70163_u + (isArmored() ? (entity.field_70163_u - ((hasSpawnedSkeletons() && getFakeHealth() <= 0.0F) ? 8.0D : 1.0D)) : (entity.field_70163_u + 5.0D + entity.func_70047_e()))) {
              this.field_70122_E = false;
              this.field_70160_al = true;
              this.field_70181_x -= 0.5D + this.field_70181_x;
            } 
          } 
          func_70625_a(entity, 180.0F, 40.0F);
          if ((getHoverTime() > 40 && !isArmored()) || (isArmored() && getRamTime() < 140 && getRamTime() > 100)) {
            double d0 = this.hoverX - this.field_70165_t;
            double d1 = this.hoverZ - this.field_70161_v;
            double d3 = d0 * d0 + d1 * d1;
            if (d3 > 36.0D) {
              double d5 = MathHelper.func_76133_a(d3);
              if (this.moralRaisedTimer > 200 || isArmored() || (getRamTime() < 140 && getRamTime() > 100)) {
                this.field_70159_w += d0 / d5 * 1.8D - this.field_70159_w;
                this.field_70179_y += d1 / d5 * 1.8D - this.field_70179_y;
              } else {
                this.field_70159_w += d0 / d5 * 0.6D - this.field_70159_w;
                this.field_70179_y += d1 / d5 * 0.6D - this.field_70179_y;
              } 
            } 
          } 
        } 
      } 
      if (isArmored()) {
        if (!hasSpawnedSkeletons() && getFakeHealth() <= 0.0F && isArmored() && this.field_70122_E) {
          createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, isHero() ? 35.0F : 7.0F, EngenderConfig.mobs.grief);
          this.field_70170_p.func_175669_a(1023, new BlockPos((Entity)this), 0);
          for (int a = 0; a < 4; a++) {
            EntitySkeleton entityliving = new EntitySkeleton(this.field_70170_p);
            entityliving.func_82149_j((Entity)this);
            entityliving.field_70759_as = entityliving.field_70177_z;
            entityliving.field_70761_aq = entityliving.field_70177_z;
            entityliving.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entityliving)), null);
            entityliving.setSkeletonType(1);
            entityliving.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151052_q));
            if (entityliving.func_70681_au().nextInt(2) > 0)
              entityliving.func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_151052_q)); 
            this.field_70170_p.func_72838_d((Entity)entityliving);
            entityliving.setOwnerId(func_184753_b());
          } 
          setCanSpawnSkeletons(true);
        } 
        if (getRamTime() <= 0 && func_70638_az() != null) {
          setRamTime(200);
          func_70625_a((Entity)func_70638_az(), 180.0F, 0.0F);
        } 
        if (getRamTime() == 120) {
          double d1 = (func_70638_az() != null && func_70032_d((Entity)func_70638_az()) > 24.0D) ? func_70032_d((Entity)func_70638_az()) : 24.0D;
          Vec3d vec3d = func_70676_i(1.0F);
          this.hoverX = this.field_70165_t + vec3d.field_72450_a * d1;
          this.hoverZ = this.field_70161_v + vec3d.field_72449_c * d1;
        } 
        if (getRamTime() < 140 && getRamTime() > 100) {
          this.field_70181_x = -0.1D;
          double d0 = ((func_174813_aQ()).field_72340_a + (func_174813_aQ()).field_72336_d) * 5.0D;
          double d1 = ((func_174813_aQ()).field_72338_b + (func_174813_aQ()).field_72337_e) * 2.0D;
          double d2 = ((func_174813_aQ()).field_72339_c + (func_174813_aQ()).field_72334_f) * 5.0D;
          List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_186662_g(5.0D));
          if (!list.isEmpty())
            for (int i1 = 0; i1 < list.size(); i1++) {
              Entity e = list.get(i1);
              if (e.func_70089_S() && e instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase)e;
                if (!func_184191_r((Entity)entity)) {
                  double d3 = entity.field_70165_t - d0;
                  double d4 = entity.field_70161_v - d1;
                  double d5 = entity.field_70161_v - d2;
                  double d6 = d3 * d3 + d4 * d4 + d5 * d5;
                  inflictEngenderMobDamage(entity, " was rammed into by ", DamageSource.func_76358_a((EntityLivingBase)this), (float)func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111264_e).func_111125_b() * 2.0F);
                  entity.field_70761_aq = entity.field_70177_z = entity.field_70759_as = (float)MathHelper.func_181159_b(entity.field_70179_y, entity.field_70159_w) * 57.295776F - 90.0F;
                  entity.func_70604_c(null);
                  if (entity instanceof EntityLiving)
                    ((EntityLiving)entity).func_70624_b(null); 
                  entity.func_70024_g(d3 / d6 * 5.0D, d4 / d6 * 1.0D, d5 / d6 * 5.0D);
                } 
              } 
            }  
          if (!this.field_70170_p.field_72995_K && EngenderConfig.mobs.grief) {
            int i11 = MathHelper.func_76128_c(this.field_70163_u);
            int l1 = MathHelper.func_76128_c(this.field_70165_t);
            int i2 = MathHelper.func_76128_c(this.field_70161_v);
            boolean bool = false;
            for (int k2 = -2; k2 <= 2; k2++) {
              for (int l2 = -2; l2 <= 2; l2++) {
                for (int m = -1; m <= 3; m++) {
                  int i3 = l1 + k2;
                  int n = i11 + m;
                  int l = i2 + l2;
                  BlockPos blockpos = new BlockPos(i3, n, l);
                  IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
                  Block block = iblockstate.func_177230_c();
                  if (!block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos)) {
                    this;
                    if (canDestroyBlock(block))
                      bool = (this.field_70170_p.func_175655_b(blockpos, true) || bool); 
                  } 
                } 
              } 
            } 
            if (bool)
              this.field_70170_p.func_180498_a((EntityPlayer)null, 1022, new BlockPos((Entity)this), 0); 
          } 
        } 
      } else {
        if (!this.field_70170_p.field_72995_K && func_70638_az() != null && getHoverTime() <= 120 && getHoverTime() >= 60 && this.field_70173_aa % 20 == 0)
          func_82196_d(func_70638_az(), 1.0F); 
        if (func_70638_az() != null && getHoverTime() <= 0) {
          setHoverTime(120);
          this.hoverX = (func_70638_az()).field_70165_t + this.field_70146_Z.nextGaussian() * 10.0D;
          this.hoverZ = (func_70638_az()).field_70161_v + this.field_70146_Z.nextGaussian() * 10.0D;
        } 
      } 
    } 
    if (func_70093_af() || (getRamTime() < 180 && getRamTime() > 100 && this.field_70131_O != 2.3F)) {
      func_70105_a(0.9F, 2.3F);
    } else if (this.field_70131_O != 3.3F) {
      func_70105_a(0.9F, 3.3F);
    } 
    if (this.field_70181_x < 0.0D || (func_70638_az() != null && !func_184207_aI()))
      this.field_70181_x *= 0.6D; 
    if (func_70093_af() || getInvulTime() > 0)
      func_70624_b(null); 
    if (this.field_70163_u < 0.0D)
      this.field_70181_x += (0.5D - this.field_70181_x) * 0.6000000238418579D; 
    super.func_70636_d();
    int k;
    for (k = 0; k < 2; k++) {
      this.yRotOHeads[k] = this.yRotationHeads[k];
      this.xRotOHeads[k] = this.xRotationHeads[k];
    } 
    for (k = 0; k < 2; k++) {
      if (func_70638_az() != null)
        this.headRandomTurn[k] = 0.0F; 
      int m = getWatchedTargetId(k + 1);
      Entity entity1 = null;
      if (m > 0)
        entity1 = this.field_70170_p.func_73045_a(m); 
      if (entity1 != null) {
        double d1 = func_82214_u(k + 1);
        double d3 = func_82208_v(k + 1);
        double d5 = func_82213_w(k + 1);
        double d6 = entity1.field_70165_t - d1;
        double d7 = entity1.field_70163_u + entity1.func_70047_e() - d3;
        double d8 = entity1.field_70161_v - d5;
        double d9 = MathHelper.func_76133_a(d6 * d6 + d8 * d8);
        float f = (float)(Math.atan2(d8, d6) * 180.0D / Math.PI) - 90.0F;
        float f1 = (float)-(Math.atan2(d7, d9) * 180.0D / Math.PI);
        this.xRotationHeads[k] = func_82204_b(this.xRotationHeads[k], f1, 40.0F);
        this.yRotationHeads[k] = func_82204_b(this.yRotationHeads[k], f, 10.0F);
      } else {
        if ((this.field_70173_aa + func_145782_y()) % 400 == 0)
          this.headRandomTurn[0] = this.field_70146_Z.nextFloat() * 360.0F - 180.0F; 
        if ((this.field_70173_aa + func_145782_y()) % 400 == 0)
          this.headRandomTurn[1] = this.field_70146_Z.nextFloat() * 360.0F - 180.0F; 
        this.yRotationHeads[k] = func_82204_b(this.yRotationHeads[k], this.field_70760_ar + this.field_70761_aq - this.field_70760_ar + this.headRandomTurn[k], 2.0F);
      } 
    } 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (isHero() && getSpecialAttackTimer() == 1400) {
      createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, 7.0F, false);
      this.field_70170_p.func_175669_a(1023, new BlockPos((Entity)this), 0);
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(128.0D, 128.0D, 128.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!func_184191_r((Entity)entity) && entity.func_184222_aU() && !(entity instanceof EntityTameBase)) {
              entity.field_70172_ad = 0;
              entity.func_70097_a(DamageSource.field_76377_j, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_82728_o, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76367_g, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_188407_q, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76369_e, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76379_h, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_82729_p, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_188406_j, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76372_a, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76368_d, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76371_c, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_180137_b, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76376_m, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76370_b, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76380_i, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_76366_f, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_82727_n, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_190095_e, Float.MAX_VALUE);
              entity.func_70097_a(DamageSource.field_191552_t, Float.MAX_VALUE);
              entity.func_70097_a((new DamageSource("generic")).func_76348_h().func_151518_m(), Float.MAX_VALUE);
              if (EngenderConfig.general.useMessage && !entity.func_70089_S() && !isWild()) {
                entity.func_70106_y();
                getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " doesn't exist anymore...", new Object[0]));
              } 
            } else {
              list.remove(entity);
            }  
        }  
    } 
    if (this.field_70170_p.field_72995_K) {
      for (k = 0; k < 2; k++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]); 
      if (isHero() && getSpecialAttackTimer() >= 1400)
        for (int i1 = 0; i1 < 128; i1++)
          this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t, this.field_70163_u + 1.75D, this.field_70161_v, this.field_70146_Z.nextDouble() - 0.5D, this.field_70146_Z.nextDouble() - 0.5D, this.field_70146_Z.nextDouble() - 0.5D, new int[0]);  
    } 
    boolean flag = isArmored();
    int j;
    for (j = 0; j < 3; j++) {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_NORMAL, d10 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d2 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d4 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      if (flag && this.field_70170_p.field_73012_v.nextInt(4) == 0)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, d10 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d2 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, d4 + this.field_70146_Z.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]); 
    } 
    if (func_70090_H())
      for (j = 0; j < this.partArray.length; j++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, (this.partArray[j]).field_70165_t + this.field_70146_Z.nextGaussian() * ((this.partArray[j]).field_70130_N / 2.0F), (this.partArray[j]).field_70163_u + this.field_70146_Z.nextDouble() * ((this.partArray[j]).field_70130_N / 2.0F), (this.partArray[j]).field_70161_v + this.field_70146_Z.nextGaussian() * ((this.partArray[j]).field_70130_N / 2.0F), 0.0D, 0.0D, 0.0D, new int[0]);  
    if (getInvulTime() > 0)
      for (j = 0; j < 3; j++)
        this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + this.field_70146_Z.nextGaussian() * 1.0D, this.field_70163_u + (this.field_70146_Z.nextFloat() * 3.3F), this.field_70161_v + this.field_70146_Z.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);  
  }
  
  protected void func_70619_bc() {
    if (getInvulTime() > 0) {
      int i = getInvulTime() - 1;
      if (i <= 0) {
        createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, isHero() ? 35.0F : 7.0F, EngenderConfig.mobs.grief);
        this.field_70170_p.func_175669_a(1023, new BlockPos((Entity)this), 0);
      } 
      setInvulTime(i);
      if (this.field_70173_aa % 1 == 0)
        func_70691_i(1.0F); 
    } else {
      super.func_70619_bc();
      if (func_70638_az() == null && isArmored() && this.field_70146_Z.nextInt(400) == 0) {
        setInvulTime(220);
        this.field_70173_aa = 0;
        func_70642_aH();
        func_184185_a(ESound.createMob, 10.0F, 0.75F);
        func_184185_a(ESound.createBossMob, 1.0E7F, 1.0F);
      } 
      for (int i = 1; i < 3; i++) {
        if (this.field_70173_aa >= this.nextHeadUpdate[i - 1]) {
          this.nextHeadUpdate[i - 1] = this.field_70173_aa + 20 + this.field_70146_Z.nextInt(20);
          if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) {
            int k2 = i - 1;
            int l2 = this.idleHeadUpdates[i - 1];
            this.idleHeadUpdates[k2] = this.idleHeadUpdates[i - 1] + 1;
            if (l2 > 15) {
              float f = 10.0F;
              float f1 = 5.0F;
              double d0 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70165_t - f, this.field_70165_t + f);
              double d1 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70163_u - f1, this.field_70163_u + f1);
              double d2 = MathHelper.func_82716_a(this.field_70146_Z, this.field_70161_v - f, this.field_70161_v + f);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              this.idleHeadUpdates[i - 1] = 0;
            } 
          } 
          int i1 = getWatchedTargetId(i);
          if (i1 > 0) {
            Entity entity = this.field_70170_p.func_73045_a(i1);
            if (!func_70093_af() && entity != null && entity.func_70089_S() && func_70068_e(entity) <= 2000.0D && func_70685_l(entity)) {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.idleHeadUpdates[i - 1] = 0;
              if (this.moralRaisedTimer > 200) {
                this;
                this.nextHeadUpdate[i - 1] = this.field_70173_aa + 20 + this.field_70146_Z.nextInt(10) - (int)(func_110148_a(DEXTERITY).func_111125_b() * 0.1D);
              } else {
                this;
                this.nextHeadUpdate[i - 1] = this.field_70173_aa + 40 + this.field_70146_Z.nextInt(20) - (int)(func_110148_a(DEXTERITY).func_111125_b() * 0.2D);
              } 
            } else {
              updateWatchedTargetId(i, 0);
            } 
          } else {
            List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(32.0D, 32.0D, 32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
            for (int k1 = 0; k1 < 10 && !list.isEmpty(); k1++) {
              EntityLivingBase entitylivingbase = list.get(this.field_70146_Z.nextInt(list.size()));
              if (entitylivingbase != this && entitylivingbase.func_70089_S() && func_70685_l((Entity)entitylivingbase) && (!func_184191_r((Entity)entitylivingbase) || (func_70638_az() != null && this.field_70146_Z.nextInt(120) == 0))) {
                updateWatchedTargetId(i, entitylivingbase.func_145782_y());
              } else if (!entitylivingbase.func_70089_S() || !func_70685_l((Entity)entitylivingbase) || (func_184191_r((Entity)entitylivingbase) && this.field_70146_Z.nextInt(80) == 0)) {
                list.remove(entitylivingbase);
              } 
            } 
          } 
        } 
      } 
      if (func_70638_az() != null) {
        updateWatchedTargetId(0, func_70638_az().func_145782_y());
      } else {
        updateWatchedTargetId(0, 0);
      } 
      if (this.blockBreakCounter > 0) {
        this.blockBreakCounter--;
        if (this.blockBreakCounter == 0 && EngenderConfig.mobs.grief) {
          int i11 = MathHelper.func_76128_c(this.field_70163_u);
          int l1 = MathHelper.func_76128_c(this.field_70165_t);
          int i2 = MathHelper.func_76128_c(this.field_70161_v);
          boolean flag = false;
          for (int k2 = -1; k2 <= 1; k2++) {
            for (int l2 = -1; l2 <= 1; l2++) {
              for (int j = 0; j <= 3; j++) {
                int i3 = l1 + k2;
                int k = i11 + j;
                int l = i2 + l2;
                BlockPos blockpos = new BlockPos(i3, k, l);
                IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos);
                Block block = iblockstate.func_177230_c();
                if (!block.isAir(iblockstate, (IBlockAccess)this.field_70170_p, blockpos) && canDestroyBlock(block))
                  flag = (this.field_70170_p.func_175655_b(blockpos, true) || flag); 
              } 
            } 
          } 
          if (flag)
            this.field_70170_p.func_180498_a((EntityPlayer)null, 1022, new BlockPos((Entity)this), 0); 
        } 
      } 
      if (this.field_70173_aa % 20 == 0)
        func_70691_i(1.0F); 
    } 
  }
  
  public static boolean canDestroyBlock(Block p_181033_0_) {
    return (p_181033_0_ != Blocks.field_150357_h && p_181033_0_ != Blocks.field_150384_bq && p_181033_0_ != Blocks.field_150378_br && p_181033_0_ != Blocks.field_150483_bI && p_181033_0_ != Blocks.field_185776_dc && p_181033_0_ != Blocks.field_185777_dd && p_181033_0_ != Blocks.field_180401_cv);
  }
  
  public void func_82206_m() {
    setInvulTime(220);
    func_70606_j(func_110138_aP() / 3.0F);
  }
  
  public boolean func_70652_k(Entity p_70652_1_) {
    if (super.func_70652_k(p_70652_1_)) {
      if (p_70652_1_ instanceof EntityLivingBase)
        inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)p_70652_1_, MobEffects.field_82731_v, 10, 1); 
      if (!p_70652_1_.func_70089_S()) {
        func_70691_i(10.0F);
      } else {
        func_174815_a((EntityLivingBase)this, p_70652_1_);
      } 
      return true;
    } 
    return false;
  }
  
  private double func_82214_u(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.field_70165_t; 
    float f = (this.field_70761_aq + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.func_76134_b(f);
    return this.field_70165_t + f1 * 1.25D * func_110148_a(FITTNESS).func_111126_e();
  }
  
  private double func_82208_v(int p_82208_1_) {
    return func_70093_af() ? (this.field_70163_u + 1.25D * func_110148_a(FITTNESS).func_111126_e()) : (this.field_70163_u + 2.25D * func_110148_a(FITTNESS).func_111126_e());
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
  
  public void func_70110_aj() {}
  
  private double func_82213_w(int p_82213_1_) {
    if (p_82213_1_ <= 0)
      return this.field_70161_v; 
    float f = (this.field_70761_aq + (180 * (p_82213_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.func_76126_a(f);
    return this.field_70161_v + f1 * 1.25D * func_110148_a(FITTNESS).func_111126_e();
  }
  
  private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
    float f3 = MathHelper.func_76142_g(p_82204_2_ - p_82204_1_);
    if (f3 > p_82204_3_)
      f3 = p_82204_3_; 
    if (f3 < -p_82204_3_)
      f3 = -p_82204_3_; 
    return p_82204_1_ + f3;
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
    if (!this.field_70170_p.field_72995_K && getPolymorphTime() > 0 && this.field_70146_Z.nextBoolean()) {
      float j = this.field_70761_aq * 0.017453292F;
      float f1 = MathHelper.func_76134_b(j);
      float f2 = MathHelper.func_76126_a(j);
      double d3 = func_82214_u(p_82216_1_);
      double d4 = func_82208_v(p_82216_1_);
      double d5 = func_82213_w(p_82216_1_);
      EntityInvisibleFangsProjectile entitymagicmissiles = new EntityInvisibleFangsProjectile(this.field_70170_p, (Entity)p_82216_2_, (EntityLivingBase)this, d3, d4, d5);
      this.field_70170_p.func_72838_d((Entity)entitymagicmissiles);
    } else {
      launchWitherSkullToCoords(p_82216_1_, p_82216_2_.field_70165_t + p_82216_2_.field_70159_w * p_82216_2_.field_70159_w, p_82216_2_.field_70163_u + ((p_82216_2_.field_70131_O > 8.0F) ? 7.0D : (p_82216_2_.field_70131_O * 0.5D)), p_82216_2_.field_70161_v + p_82216_2_.field_70179_y * p_82216_2_.field_70179_y, (p_82216_1_ == 0 && !p_82216_2_.func_70685_l((Entity)this)));
    } 
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_) {
    func_184185_a(SoundEvents.field_187853_gC, func_70599_aP(), 1.0F);
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityWitherSkullOther entitywitherskull = new EntityWitherSkullOther(this.field_70170_p, (EntityLivingBase)this, d6, d7, d8);
    if (p_82209_8_)
      entitywitherskull.func_82343_e(true); 
    float f = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
    entitywitherskull.damage = f;
    entitywitherskull.field_70163_u = d4;
    entitywitherskull.field_70165_t = d3;
    entitywitherskull.field_70161_v = d5;
    if (!this.field_70170_p.field_72995_K)
      this.field_70170_p.func_72838_d((Entity)entitywitherskull); 
  }
  
  public void func_82196_d(EntityLivingBase p_82196_1_, float p_82196_2_) {
    launchWitherSkullToEntity(0, p_82196_1_);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_WITHER;
  }
  
  protected void func_70623_bb() {
    this.field_70708_bq = 0;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.1F : 2.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
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
  
  @SideOnly(Side.CLIENT)
  public float getHeadYRotation(int p_82207_1_) {
    return this.yRotationHeads[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadXRotation(int p_82210_1_) {
    return this.xRotationHeads[p_82210_1_];
  }
  
  public int getHoverTime() {
    return ((Integer)this.field_70180_af.func_187225_a(HOVERTIMER)).intValue();
  }
  
  public void setHoverTime(int p_82215_1_) {
    this.field_70180_af.func_187227_b(HOVERTIMER, Integer.valueOf(p_82215_1_));
  }
  
  public int getRamTime() {
    return ((Integer)this.field_70180_af.func_187225_a(RAMTIMER)).intValue();
  }
  
  public void setRamTime(int p_82215_1_) {
    this.field_70180_af.func_187227_b(RAMTIMER, Integer.valueOf(p_82215_1_));
  }
  
  public int getInvulTime() {
    return ((Integer)this.field_70180_af.func_187225_a(INVULNERABILITY_TIME)).intValue();
  }
  
  public void setInvulTime(int p_82215_1_) {
    this.field_70180_af.func_187227_b(INVULNERABILITY_TIME, Integer.valueOf(p_82215_1_));
  }
  
  public int getWatchedTargetId(int p_82203_1_) {
    return ((Integer)this.field_70180_af.func_187225_a(HEAD_TARGETS[p_82203_1_])).intValue();
  }
  
  public void updateWatchedTargetId(int targetOffset, int newId) {
    this.field_70180_af.func_187227_b(HEAD_TARGETS[targetOffset], Integer.valueOf(newId));
  }
  
  public boolean isArmored() {
    return (func_110143_aJ() <= func_110138_aP() / 2.0F || (getFakeHealth() <= func_110138_aP() && getFakeHealth() > 0.0F));
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
  
  protected void func_70609_aI() {
    this.lastActiveTime = this.timeSinceIgnited;
    this.timeSinceIgnited = ++this.deathTicks;
    setRamTime(0);
    setHoverTime(0);
    if (!this.field_70170_p.field_72995_K && this.deathTicks == 120) {
      func_184185_a(ESound.blast, 10.0F, 1.0F);
      createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 14.0F, EngenderConfig.mobs.grief);
      List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_186662_g(128.0D));
      this.field_70170_p.func_175718_b(3000, func_180425_c(), 0);
      func_184185_a(ESound.blast, 10.0F, 1.0F);
      if (list != null && !list.isEmpty())
        for (int i = 0; i < list.size(); i++) {
          Entity entity = list.get(i);
          double scale = (128.0D - entity.func_70011_f(this.field_70165_t, this.field_70163_u, this.field_70161_v)) / 128.0D;
          Vec3d dir = new Vec3d(entity.field_70165_t - this.field_70165_t, entity.field_70163_u - this.field_70163_u, entity.field_70161_v - this.field_70161_v);
          dir = dir.func_72432_b();
          if (entity instanceof EntityLivingBase)
            if (entity.func_70089_S()) {
              if (entity.func_70032_d((Entity)this) <= 16.0D) {
                entity.field_70172_ad = 0;
                inflictEngenderMobDamage((EntityLivingBase)entity, " was blown up by ", DamageSource.func_188405_b((EntityLivingBase)this), 96.0F);
              } 
              entity.func_70024_g(dir.field_72450_a * 2.5D * scale, 1.5D + this.field_70146_Z.nextDouble(), dir.field_72449_c * 2.5D * scale);
            } else {
              entity.func_70024_g(dir.field_72450_a * 2.5D * scale, 1.5D + this.field_70146_Z.nextDouble(), dir.field_72449_c * 2.5D * scale);
            }  
        }  
      if (!this.field_70170_p.field_72995_K && func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
        int i = func_70693_a(this.field_70717_bb);
        i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.field_70717_bb, i);
        while (i > 0) {
          int j = EntityXPOrb.func_70527_a(i);
          i -= j;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 8.0D, this.field_70161_v, j));
        } 
      } 
      for (int k = 0; k < 20; k++) {
        double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, d0, d1, new int[0]);
      } 
      func_70106_y();
    } 
    if (!this.field_70170_p.field_72995_K)
      if (this.deathTicks == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s " + func_70005_c_() + " has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your " + func_70005_c_() + " has been destroyed!", new Object[0]));
        }   
  }
  
  public boolean func_70067_L() {
    return true;
  }
  
  public World func_82194_d() {
    return this.field_70170_p;
  }
  
  public Entity[] func_70021_al() {
    return (Entity[])this.partArray;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return func_70965_a((MultiPartEntityPart)this.mainHead, source, amount);
  }
  
  public boolean func_70965_a(MultiPartEntityPart witherPart, DamageSource source, float damage) {
    if (isArmored())
      damage = damage / 2.0F + Math.min(damage, 1.0F); 
    if (witherPart == this.spine || witherPart == this.rightRibs || witherPart == this.leftRibs)
      damage = damage / 10.0F + Math.min(damage, 1.0F); 
    if (witherPart == this.lowerspine || witherPart == this.rightsupport || witherPart == this.leftsupport)
      damage = damage / 2.0F + Math.min(damage, 1.0F); 
    if (witherPart == this.rightHead && !source.func_94541_c())
      this.nextHeadUpdate[0] = this.nextHeadUpdate[0] + 200; 
    if (witherPart == this.leftHead && !source.func_94541_c())
      this.nextHeadUpdate[1] = this.nextHeadUpdate[1] + 200; 
    if (source.func_76346_g() != null && source.func_76346_g() instanceof EntityLivingBase)
      if (!isWild() && source.func_76346_g() == getOwner()) {
        updateWatchedTargetId(1, 0);
        updateWatchedTargetId(2, 0);
      }  
    if (func_180431_b(source) || getInvulTime() > 0 || damage < 0.01F || source == DamageSource.field_82727_n || source == DamageSource.field_76368_d || source == DamageSource.field_76369_e || source == DamageSource.field_191291_g || source == DamageSource.field_76367_g)
      return false; 
    if (isArmored() && source.func_76352_a())
      return false; 
    super.func_70097_a(source, damage);
    if (this.blockBreakCounter <= 0)
      this.blockBreakCounter = 20; 
    int i;
    for (i = 0; i < this.idleHeadUpdates.length; i++)
      this.idleHeadUpdates[i] = this.idleHeadUpdates[i] + 3; 
    if (!source.func_82725_o())
      for (i = 0; i < this.nextHeadUpdate.length; i++) {
        if (this.nextHeadUpdate[i] <= 80)
          this.nextHeadUpdate[i] = this.nextHeadUpdate[i] - 20; 
      }  
    return true;
  }
  
  class AIDoNothing extends EntityAIBase {
    public AIDoNothing() {
      func_75248_a(7);
    }
    
    public boolean func_75250_a() {
      return (EntityWither.this.getInvulTime() > 0);
    }
  }
}
