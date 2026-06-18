package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.client.animationapi.IAnimatedEntity;
import chumbanotz.mutantbeasts.entity.BodyPartEntity;
import chumbanotz.mutantbeasts.pathfinding.MBGroundPathNavigator;
import chumbanotz.mutantbeasts.util.EntityUtil;
import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "chumbanotz.mutantbeasts.client.animationapi.IAnimatedEntity", modid = "mutantbeasts")
public class EntityMutantSkeleton extends EntityTameBase implements IRangedAttackMob, IJumpingMount, Undead, Massive, Armored, IAnimatedEntity {
  public static final byte MELEE_ATTACK = 4;
  
  public static final byte SHOOT_ATTACK = 5;
  
  public static final byte MULTI_SHOT_ATTACK = 6;
  
  public static final byte CONSTRICT_RIBS_ATTACK = 7;
  
  private int attackID;
  
  private int attackTick;
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 0.0D, 10, 96.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.1D, true);
  
  protected float jumpPower;
  
  public EntityMutantSkeleton(World worldIn) {
    super(worldIn);
    this.field_70728_aV = 600;
    this.field_70158_ak = true;
    func_70105_a(0.9F, 3.6F);
    this.field_70714_bg.func_75776_a(1, new MeleeGoal());
    this.field_70714_bg.func_75776_a(1, new SnipeGoal());
    this.field_70714_bg.func_75776_a(1, new ShootGoal());
    this.field_70714_bg.func_75776_a(1, new MultiShotGoal());
    this.field_70714_bg.func_75776_a(1, new ConstrictRibsAttackGoal());
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 64.0F, 12.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.75D));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public String getDescName() {
    return "mutant_skeleton";
  }
  
  public int playMusic() {
    return EngenderConfig.general.useMiniMusic ? 2 : 0;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(160.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(96.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
    func_110148_a(SWIM_SPEED).func_111128_a(5.0D);
  }
  
  public boolean isAMutant() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityMutantSkeleton(this.field_70170_p);
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
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new MBGroundPathNavigator((EntityLiving)this, worldIn);
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.9027F;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 0 || (id >= 4 && id <= 8)) {
      this.attackID = id;
      this.attackTick = 0;
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    func_184644_a(PathNodeType.WATER, func_70090_H() ? 16.0F : -1.0F);
    if (this.attackID != 0)
      this.attackTick++; 
    func_184596_c(MobEffects.field_76440_q);
    if (this.attackID == 0 && func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && !func_184191_r((Entity)func_70638_az()) && func_70068_e((Entity)func_70638_az()) < (this.field_70130_N * this.field_70130_N + (func_70638_az()).field_70130_N * (func_70638_az()).field_70130_N) + 9.0D)
      func_70652_k((Entity)func_70638_az()); 
    if (!this.field_70170_p.func_72935_r() && this.field_70173_aa % 50 == 0 && func_110143_aJ() < func_110138_aP())
      func_70691_i(1.0F); 
    if (func_70638_az() != null) {
      if (func_70032_d((Entity)func_70638_az()) > 8.0D || func_70638_az() instanceof net.minecraft.entity.EntityFlying || (func_70638_az()).field_70163_u > this.field_70163_u + 6.0D) {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiArrowAttack);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiAttackOnCollide);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      } 
    } else {
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
    } 
  }
  
  public boolean func_70652_k(Entity entityIn) {
    if (!this.field_70170_p.field_72995_K && this.attackID == 0)
      if (func_110143_aJ() <= func_110138_aP() * 0.375F && this.field_70146_Z.nextInt(2) == 0) {
        setAttackID(6);
      } else if (this.field_70146_Z.nextInt(4) != 0) {
        setAttackID(4);
      } else if (this.field_70122_E || func_70090_H() || func_180799_ab()) {
        setAttackID(7);
      }  
    return true;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return super.func_70097_a(source, amount);
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  protected boolean func_184228_n(Entity entityIn) {
    return false;
  }
  
  public boolean func_96092_aw() {
    return false;
  }
  
  public int getAnimationID() {
    return this.attackID;
  }
  
  public int getAnimationTick() {
    return this.attackTick;
  }
  
  protected void func_70609_aI() {
    super.func_70609_aI();
    if (this.field_70725_aQ > 2) {
      if (!this.field_70170_p.field_72995_K) {
        for (Entity entity : this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_72314_b(3.0D, 2.0D, 3.0D))) {
          if (entity instanceof EntityLivingBase)
            inflictEngenderMobDamage((EntityLivingBase)entity, "'s body was mutilated by a flying body part from ", DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h(), 7.0F); 
        } 
        for (int i = 0; i < 18; i++) {
          int j = i;
          if (i >= 3)
            j = i + 1; 
          if (j >= 4)
            j++; 
          if (j >= 5)
            j++; 
          if (j >= 6)
            j++; 
          if (j >= 9)
            j++; 
          if (j >= 10)
            j++; 
          if (j >= 11)
            j++; 
          if (j >= 12)
            j++; 
          if (j >= 15)
            j++; 
          if (j >= 16)
            j++; 
          if (j >= 17)
            j++; 
          if (j >= 18)
            j++; 
          if (j >= 20)
            j++; 
          BodyPartEntity part = new BodyPartEntity(this.field_70170_p, (EntityLiving)this, j);
          part.field_70159_w += (this.field_70146_Z.nextFloat() * 0.8F * 2.0F - 0.8F);
          part.field_70181_x += (this.field_70146_Z.nextFloat() * 0.25F + 0.1F);
          part.field_70179_y += (this.field_70146_Z.nextFloat() * 0.8F * 2.0F - 0.8F);
          this.field_70170_p.func_72838_d((Entity)part);
        } 
      } 
      func_174812_G();
    } 
  }
  
  protected void func_70629_bd() {
    this.field_70181_x += 0.03999999910593033D;
  }
  
  protected void func_180466_bG() {
    func_70629_bd();
  }
  
  public boolean func_70601_bi() {
    return (super.func_70601_bi() && this.field_70170_p.func_175678_i(func_180425_c()));
  }
  
  protected SoundEvent func_184639_G() {
    return MBSoundEvents.ENTITY_MUTANT_SKELETON_AMBIENT;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_MUTANT_SKELETON_HURT;
  }
  
  protected SoundEvent func_184615_bR() {
    return MBSoundEvents.ENTITY_MUTANT_SKELETON_DEATH;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(MBSoundEvents.ENTITY_MUTANT_SKELETON_STEP, 0.15F, 1.0F);
    func_184185_a(SoundEvents.field_187605_cG, 0.15F, func_70647_i());
  }
  
  protected ResourceLocation func_184647_J() {
    return new ResourceLocation("mutantbeasts", "entities/mutant_skeleton");
  }
  
  private void setAttackID(int id) {
    this.attackID = id;
    this.attackTick = 0;
    this.field_70170_p.func_72960_a((Entity)this, (byte)id);
  }
  
  class MeleeGoal extends EntityAIBase {
    public MeleeGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      return (EntityMutantSkeleton.this.func_70638_az() != null && EntityMutantSkeleton.this.attackID == 4);
    }
    
    public boolean func_75253_b() {
      return (EntityMutantSkeleton.this.attackTick < 14);
    }
    
    public void func_75249_e() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public void func_75246_d() {
      if (EntityMutantSkeleton.this.func_70638_az() != null && EntityMutantSkeleton.this.func_70638_az().func_70089_S())
        EntityMutantSkeleton.this.func_70671_ap().func_75651_a((Entity)EntityMutantSkeleton.this.func_70638_az(), 30.0F, 30.0F); 
      if (EntityMutantSkeleton.this.attackTick == 3) {
        for (Entity entity : EntityMutantSkeleton.this.field_70170_p.func_175674_a((Entity)EntityMutantSkeleton.this, EntityMutantSkeleton.this.func_174813_aQ().func_186662_g(4.0D), EntitySelectors.field_188444_d)) {
          double dist = EntityMutantSkeleton.this.func_70032_d(entity);
          double x = EntityMutantSkeleton.this.field_70165_t - entity.field_70165_t;
          double z = EntityMutantSkeleton.this.field_70161_v - entity.field_70161_v;
          if (entity instanceof EntityLivingBase && dist <= (2.3F + EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.3F) && EntityUtil.isFacing((EntityLivingBase)EntityMutantSkeleton.this, x, z, 60.0F)) {
            float power = 1.8F + EntityMutantSkeleton.this.field_70146_Z.nextInt(5) * 0.15F;
            entity.func_184210_p();
            EntityMutantSkeleton.this.inflictEngenderMobDamage((EntityLivingBase)entity, " was crushed by ", DamageSource.func_76358_a((EntityLivingBase)EntityMutantSkeleton.this), (float)EntityMutantSkeleton.this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() + 2.0F);
            entity.field_70159_w = -x / dist * power;
            entity.field_70181_x = Math.max(0.2800000011920929D, entity.field_70181_x);
            entity.field_70179_y = -z / dist * power;
            EntityUtil.knockBackBlockingPlayer((Entity)EntityMutantSkeleton.this);
          } 
        } 
        EntityMutantSkeleton.this.func_184185_a(SoundEvents.field_187721_dT, 1.0F, 1.0F / (EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.4F + 1.2F));
      } 
    }
    
    public void func_75251_c() {
      EntityMutantSkeleton.this.setAttackID(0);
    }
  }
  
  class ConstrictRibsAttackGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public ConstrictRibsAttackGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantSkeleton.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantSkeleton.this.attackID == 7);
    }
    
    public boolean func_75253_b() {
      return (EntityMutantSkeleton.this.attackTick < 20);
    }
    
    public void func_75249_e() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public void func_75246_d() {
      if (EntityMutantSkeleton.this.attackTick == 5)
        this.attackTarget.func_184210_p(); 
      if (EntityMutantSkeleton.this.attackTick == 6) {
        this.attackTarget.func_184210_p();
        EntityMutantSkeleton.this.inflictEngenderMobDamage(this.attackTarget, " was crushed by ", DamageSource.func_76358_a((EntityLivingBase)EntityMutantSkeleton.this).func_151518_m(), (float)EntityMutantSkeleton.this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() + 6.0F);
        this.attackTarget.field_70159_w = ((1.0F + EntityMutantSkeleton.this.func_70681_au().nextFloat() * 0.4F) * (EntityMutantSkeleton.this.func_70681_au().nextBoolean() ? true : -1));
        this.attackTarget.field_70181_x = (0.4F + EntityMutantSkeleton.this.func_70681_au().nextFloat() * 0.8F);
        this.attackTarget.field_70179_y = ((1.0F + EntityMutantSkeleton.this.func_70681_au().nextFloat() * 0.4F) * (EntityMutantSkeleton.this.func_70681_au().nextBoolean() ? true : -1));
        EntityMutantSkeleton.this.func_184185_a(SoundEvents.field_187539_bB, 0.5F, 0.8F + EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.4F);
        this.attackTarget.field_70133_I = true;
        EntityUtil.disableShield(this.attackTarget, 100);
      } 
    }
    
    public void func_75251_c() {
      EntityMutantSkeleton.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  class ShootGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public ShootGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantSkeleton.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantSkeleton.this.attackID == 5);
    }
    
    public void func_75249_e() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public boolean func_75253_b() {
      return (EntityMutantSkeleton.this.attackTick < 32);
    }
    
    public void func_75246_d() {
      EntityMutantSkeleton.this.field_70699_by.func_75499_g();
      EntityMutantSkeleton.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F);
      EntityMutantSkeleton.this.field_70761_aq = EntityMutantSkeleton.this.field_70177_z = EntityMutantSkeleton.this.field_70759_as;
      if (EntityMutantSkeleton.this.attackTick == 26) {
        EntityMutantSkeletonArrow arrowEntity = new EntityMutantSkeletonArrow(EntityMutantSkeleton.this.field_70170_p, EntityMutantSkeleton.this, this.attackTarget);
        if (EntityMutantSkeleton.this.field_70737_aN > 0)
          arrowEntity.randomize(EntityMutantSkeleton.this.field_70737_aN / 2.0F); 
        if (EntityMutantSkeleton.this.field_70146_Z.nextInt(6) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_82731_v, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.field_70146_Z.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_189112_A, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.field_70146_Z.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76431_k, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.field_70146_Z.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76436_u, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0)); 
        if (EntityMutantSkeleton.this.field_70146_Z.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76437_t, 120 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 1)); 
        if (EntityMutantSkeleton.this.field_70146_Z.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76438_s, 120 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 1)); 
        if (EntityMutantSkeleton.this.field_70146_Z.nextInt(4) == 0)
          arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76421_d, 120 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 1)); 
        EntityMutantSkeleton.this.field_70170_p.func_72838_d(arrowEntity);
        EntityMutantSkeleton.this.func_184185_a(SoundEvents.field_187737_v, 1.0F, 1.0F / (EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.4F + 1.2F) + 0.25F);
      } 
    }
    
    public void func_75251_c() {
      EntityMutantSkeleton.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  class SnipeGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    public SnipeGoal() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantSkeleton.this.func_70638_az();
      return (this.attackTarget != null && EntityMutantSkeleton.this.attackID == 8);
    }
    
    public void func_75249_e() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public boolean func_75253_b() {
      return (EntityMutantSkeleton.this.attackTick < 128);
    }
    
    public void func_75246_d() {
      EntityMutantSkeleton.this.field_70699_by.func_75499_g();
      EntityMutantSkeleton.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F);
      EntityMutantSkeleton.this.field_70761_aq = EntityMutantSkeleton.this.field_70177_z = EntityMutantSkeleton.this.field_70759_as;
      if (EntityMutantSkeleton.this.attackTick == 26)
        EntityMutantSkeleton.this.func_184185_a(SoundEvents.field_187540_ab, 1.0F, 0.75F); 
      if (EntityMutantSkeleton.this.attackTick == 104) {
        EntityMutantSkeletonArrow arrowEntity = new EntityMutantSkeletonArrow(EntityMutantSkeleton.this.field_70170_p, EntityMutantSkeleton.this, this.attackTarget);
        arrowEntity.setDamage(50);
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_82731_v, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_189112_A, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76440_q, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76431_k, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76436_u, 80 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 0));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76437_t, 120 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 1));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76438_s, 120 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 1));
        arrowEntity.setPotionEffect(new PotionEffect(MobEffects.field_76421_d, 120 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 1));
        EntityMutantSkeleton.this.field_70170_p.func_72838_d(arrowEntity);
        EntityMutantSkeleton.this.func_184185_a(SoundEvents.field_187731_t, 1.0F, 1.0F / (EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.4F + 1.2F) + 0.25F);
        EntityMutantSkeleton.this.func_184185_a(SoundEvents.field_187737_v, 1.0F, 1.0F / (EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.4F + 1.2F) + 0.25F);
      } 
    }
    
    public void func_75251_c() {
      EntityMutantSkeleton.this.setAttackID(0);
      this.attackTarget = null;
    }
  }
  
  class MultiShotGoal extends EntityAIBase {
    private EntityLivingBase attackTarget;
    
    private final List<EntityMutantSkeletonArrow> shots;
    
    public MultiShotGoal() {
      this.shots = new ArrayList<>();
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      this.attackTarget = EntityMutantSkeleton.this.func_70638_az();
      return (this.attackTarget != null && (EntityMutantSkeleton.this.field_70122_E || EntityMutantSkeleton.this.func_70090_H() || EntityMutantSkeleton.this.func_180799_ab()) && EntityMutantSkeleton.this.attackID == 6);
    }
    
    public void func_75249_e() {
      EntityMutantSkeleton.this.attackTick = 0;
    }
    
    public boolean func_75253_b() {
      return (EntityMutantSkeleton.this.attackTick < 30);
    }
    
    public void func_75246_d() {
      EntityMutantSkeleton.this.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F);
      EntityMutantSkeleton.this.field_70761_aq = EntityMutantSkeleton.this.field_70177_z = EntityMutantSkeleton.this.field_70759_as;
      if (EntityMutantSkeleton.this.attackTick == 10) {
        double x = this.attackTarget.field_70165_t - EntityMutantSkeleton.this.field_70165_t;
        double z = this.attackTarget.field_70161_v - EntityMutantSkeleton.this.field_70161_v;
        float scale = 0.06F + EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.03F;
        if (EntityMutantSkeleton.this.func_70068_e((Entity)this.attackTarget) < 16.0D) {
          x *= -1.0D;
          z *= -1.0D;
          scale *= 5.0F;
        } 
        EntityMutantSkeleton.this.field_70159_w = x * scale;
        EntityMutantSkeleton.this.field_70181_x = 1.5D;
        EntityMutantSkeleton.this.field_70179_y = z * scale;
      } 
      if (EntityMutantSkeleton.this.attackTick >= 24 && EntityMutantSkeleton.this.attackTick < 28) {
        if (!this.shots.isEmpty()) {
          for (EntityMutantSkeletonArrow arrowEntity : this.shots)
            EntityMutantSkeleton.this.field_70170_p.func_72838_d(arrowEntity); 
          this.shots.clear();
        } 
        for (int i = 0; i < 6 + EntityMutantSkeleton.this.field_70146_Z.nextInt(3); i++) {
          EntityMutantSkeletonArrow shot = new EntityMutantSkeletonArrow(EntityMutantSkeleton.this.field_70170_p, EntityMutantSkeleton.this, this.attackTarget);
          shot.setSpeed(1.2F - EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.1F);
          shot.setClones(2);
          shot.randomize(3.0F);
          shot.setDamage(5 + EntityMutantSkeleton.this.field_70146_Z.nextInt(5));
          shot.setPotionEffect(new PotionEffect(MobEffects.field_76421_d, 120 + EntityMutantSkeleton.this.field_70146_Z.nextInt(60), 1));
          this.shots.add(shot);
        } 
        EntityMutantSkeleton.this.func_184185_a(SoundEvents.field_187737_v, 1.0F, 1.0F / (EntityMutantSkeleton.this.field_70146_Z.nextFloat() * 0.4F + 1.2F) + 0.25F);
      } 
    }
    
    public void func_75251_c() {
      this.shots.clear();
      if (this.attackTarget.func_70089_S() && EntityMutantSkeleton.this.func_70011_f(this.attackTarget.field_70165_t, EntityMutantSkeleton.this.field_70163_u, this.attackTarget.field_70161_v) <= 12.0D) {
        EntityMutantSkeleton.this.setAttackID(7);
        double x = this.attackTarget.field_70165_t - EntityMutantSkeleton.this.field_70165_t;
        double z = this.attackTarget.field_70161_v - EntityMutantSkeleton.this.field_70161_v;
        float scale = 0.1F;
        EntityMutantSkeleton.this.field_70159_w = x * scale;
        EntityMutantSkeleton.this.field_70181_x = -1.5D;
        EntityMutantSkeleton.this.field_70179_y = z * scale;
        this.attackTarget.field_70172_ad = 0;
        this.attackTarget = null;
      } else {
        EntityMutantSkeleton.this.setAttackID(0);
        this.attackTarget = null;
      } 
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
        this.field_70181_x = 2.0D * this.jumpPower * getFittness();
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-3.0F * f * this.jumpPower);
          this.field_70179_y += (3.0F * f1 * this.jumpPower);
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
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    if (!this.field_70170_p.field_72995_K && this.attackID == 0)
      if (func_70011_f(target.field_70165_t, this.field_70163_u, target.field_70161_v) <= 12.0D) {
        setAttackID(6);
      } else if (this.field_70122_E || func_70090_H() || func_180799_ab()) {
        if (this.field_70146_Z.nextInt(5) == 0) {
          setAttackID(8);
        } else {
          setAttackID(5);
        } 
      }  
  }
  
  public void setAnimationID(int paramInt) {}
  
  public void setAnimationTick(int paramInt) {}
}
