package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhast extends EntityTameBase implements Massive, Flying, Light {
  private static final DataParameter<Boolean> SHOULD_FLY = EntityDataManager.func_187226_a(EntityGhast.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> ATTACKING = EntityDataManager.func_187226_a(EntityGhast.class, DataSerializers.field_187198_h);
  
  public boolean eleanor;
  
  private int explosionStrength = 1;
  
  public EntityGhast(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.5F, 1.95F);
    } else {
      func_70105_a(4.5F, 4.5F);
    } 
    this.field_70178_ae = true;
    this.isOffensive = true;
    this.field_70765_h = new GhastMoveHelper(this);
    this.field_70714_bg.func_75776_a(0, new AIFireballAttack(this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, EngenderConfig.mobs.useMobTalkerModels ? 64.0F : 100.0F, EngenderConfig.mobs.useMobTalkerModels ? 9.0F : 16.0F));
    this.field_70714_bg.func_75776_a(2, new AIRandomFly(this));
    this.field_70714_bg.func_75776_a(3, new AILookAround());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 20;
  }
  
  public String getDescName() {
    return "ghast";
  }
  
  public int getNextLevelRequirement() {
    return 250;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGhast(this.field_70170_p);
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 0.5F;
  }
  
  public float getBonusVSArmored() {
    return 3.0F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 2.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isAttacking() {
    return ((Boolean)this.field_70180_af.func_187225_a(ATTACKING)).booleanValue();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public int func_70641_bl() {
    return 1;
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
  
  public void setAttacking(boolean attacking) {
    this.field_70180_af.func_187227_b(ATTACKING, Boolean.valueOf(attacking));
  }
  
  public void setFlying(boolean attacking) {
    this.field_70180_af.func_187227_b(SHOULD_FLY, Boolean.valueOf(attacking));
  }
  
  public boolean isFlying() {
    return ((Boolean)this.field_70180_af.func_187225_a(SHOULD_FLY)).booleanValue();
  }
  
  public void performSpecialAttack() {
    func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), func_70647_i());
    setSpecialAttackTimer(1200);
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.25F) : super.func_70647_i();
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1; i++) {
        EntityGhast baby = new EntityGhast(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        baby.setGrowingAge(-60000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(func_184753_b());
        if (isMarried())
          for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
            baby.levelUp();  
        this.field_70170_p.func_72838_d((Entity)baby);
      }  
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_184207_aI() && func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)func_184179_bs();
      this.field_70761_aq = this.field_70177_z = this.field_70759_as = passenger.field_70177_z;
      this.field_70125_A = EngenderConfig.mobs.useMobTalkerModels ? passenger.field_70125_A : 0.0F;
    } 
    if (isHero() && getSpecialAttackTimer() > 1100) {
      func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), func_70647_i());
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(64.0D, 64.0D, 64.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!func_184191_r((Entity)entity)) {
              if (getSpecialAttackTimer() > 1190 && entity instanceof EntityCreature && !(entity instanceof EntityTameBase))
                ((EntityCreature)entity).field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)entity, EntityGhast.class, 128.0F, 1.5D, 1.5D)); 
              entity.field_70172_ad = 0;
              inflictEngenderMobDamage(entity, "'s ears exploded thanks to ", DamageSource.field_82727_n, 0.25F);
              entity.func_70024_g(this.field_70146_Z.nextGaussian() * 0.05D, this.field_70146_Z.nextGaussian() * 0.05D, this.field_70146_Z.nextGaussian() * 0.05D);
            }  
        }  
    } 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 2048.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (!EngenderConfig.mobs.useMobTalkerModels && this.eleanor)
      this.eleanor = false; 
    if (!EngenderConfig.mobs.useMobTalkerModels) {
      setFlying(true);
    } else {
      setFlying((func_70638_az() != null && !func_70631_g_()));
    } 
    if (!isFlying() && !func_184207_aI() && func_70089_S())
      this.field_70181_x *= 0.6D; 
    if (!isFlying() && (func_70090_H() || func_180799_ab()) && func_70681_au().nextFloat() < 0.8F && func_70089_S())
      func_70683_ar().func_75660_a(); 
    if (func_70638_az() != null && getOwner() != null && !func_70685_l((Entity)func_70638_az()) && this.field_70146_Z.nextInt(80) == 0)
      func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), func_70647_i() + 0.25F); 
    if (getOwner() != null)
      if (func_70638_az() == null && this.field_70173_aa % 10 == 0) {
        double d0 = func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
        List<Entity> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(d0, d0, d0), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            Entity entity = list.get(i1);
            if (entity != null && entity.func_70089_S() && func_70685_l(entity) && !func_184191_r(entity) && entity.func_70068_e((Entity)getOwner()) <= 256.0D) {
              func_70624_b((EntityLivingBase)entity);
            } else {
              list.remove(entity);
            } 
          }  
      }  
  }
  
  public int getFireballStrength() {
    return this.explosionStrength;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected void func_184231_a(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if ((isFlying() || func_184207_aI()) && func_70089_S()) {
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
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public boolean func_70617_f_() {
    return false;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (this.eleanor && source instanceof net.minecraft.util.EntityDamageSourceIndirect)
      return false; 
    Entity entity = source.func_76346_g();
    if (entity instanceof EntityLivingBase && EngenderConfig.mobs.useMobTalkerModels && this.eleanor && amount < 50.0F) {
      EntityLivingBase creature = (EntityLivingBase)entity;
      creature.func_70097_a(DamageSource.field_76377_j.func_76348_h().func_76359_i().func_151518_m(), amount);
      creature.func_70653_a((Entity)this, amount * 0.1F, -MathHelper.func_76126_a(creature.field_70759_as * 0.017453292F), MathHelper.func_76134_b(creature.field_70759_as * 0.017453292F));
    } 
    if (this.eleanor && (source.func_76347_k() || source.func_94541_c() || source.func_76352_a() || source.func_82725_o() || amount < 50.0F))
      return false; 
    if (EngenderConfig.mobs.useMobTalkerModels && amount >= 1.0F && this.eleanor)
      amount *= 1.0E-4F; 
    if (func_180431_b(source))
      return false; 
    if (source.func_76364_f() instanceof net.minecraft.entity.projectile.EntityLargeFireball && source.func_76346_g() instanceof EntityPlayer) {
      super.func_70097_a(source, 1000.0F);
      return true;
    } 
    return super.func_70097_a(source, amount);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(ATTACKING, Boolean.valueOf(false));
    this.field_70180_af.func_187214_a(SHOULD_FLY, Boolean.valueOf(false));
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(100.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(17.0D);
    func_110148_a(SharedMonsterAttributes.field_188790_f).func_111128_a(40.0D);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187551_bH;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187555_bJ;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187553_bI;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_GHAST;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.1F : 10.0F;
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("ExplosionPower", this.explosionStrength);
    if (this.eleanor)
      tagCompound.func_74757_a("Eleanor", true); 
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    if (tagCompund.func_150297_b("ExplosionPower", 99))
      this.explosionStrength = tagCompund.func_74762_e("ExplosionPower"); 
    if (tagCompund.func_150297_b("Eleanor", 99))
      this.eleanor = tagCompund.func_74767_n("Eleanor"); 
  }
  
  public void func_96094_a(String name) {
    super.func_96094_a(name);
    if (!EngenderConfig.loreFriendlyMode && EngenderConfig.mobs.useMobTalkerModels && "Eleanor".equals(name) && isHero()) {
      this.field_70173_aa = 0;
      becomeAHero();
      this.eleanor = true;
    } else {
      this.eleanor = false;
    } 
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.84F) : (this.field_70131_O * 0.66F);
  }
  
  public double func_70042_X() {
    return this.field_70131_O * (EngenderConfig.mobs.useMobTalkerModels ? 0.75D : 0.95D);
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
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.fleshHitCrushHeavy;
  }
  
  static class AIFireballAttack extends EntityAIBase {
    private EntityGhast ghast;
    
    public int attackTimer;
    
    public AIFireballAttack(EntityGhast ghast) {
      this.ghast = ghast;
    }
    
    public boolean func_75250_a() {
      return (this.ghast.func_70638_az() != null && !this.ghast.func_70093_af());
    }
    
    public void func_75249_e() {
      this.attackTimer = 0;
      this.ghast.setArmsRaised(true);
    }
    
    public void func_75251_c() {
      this.ghast.setAttacking(false);
      this.ghast.setArmsRaised(false);
    }
    
    public void func_75246_d() {
      EntityLivingBase entitylivingbase = this.ghast.func_70638_az();
      double d0 = 100.0D;
      if (entitylivingbase != null && entitylivingbase.func_70068_e((Entity)this.ghast) < d0 * d0) {
        World world = this.ghast.field_70170_p;
        this.attackTimer++;
        if (this.ghast.moralRaisedTimer > 200)
          this.attackTimer++; 
        if (this.attackTimer == 10)
          this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.8F + this.ghast.func_70681_au().nextFloat() * 0.4F + (this.ghast.func_70631_g_() ? 0.5F : 0.0F) + (EngenderConfig.mobs.useMobTalkerModels ? 0.25F : 0.0F)); 
        if (this.attackTimer == 20) {
          double d1 = EngenderConfig.mobs.useMobTalkerModels ? (this.ghast.func_70631_g_() ? 0.25D : 0.5D) : (this.ghast.func_70631_g_() ? 2.0D : 4.0D);
          Vec3d vec3 = this.ghast.func_70676_i(1.0F);
          double d2 = entitylivingbase.field_70165_t + entitylivingbase.field_70159_w * 10.0D - this.ghast.field_70165_t + vec3.field_72450_a * d1;
          double d3 = entitylivingbase.field_70163_u + ((entitylivingbase.field_70131_O > 8.0F) ? 7.0D : (entitylivingbase.field_70131_O * 0.5D)) - this.ghast.field_70163_u + 1.0D;
          double d4 = entitylivingbase.field_70161_v + entitylivingbase.field_70179_y * 10.0D - this.ghast.field_70161_v + vec3.field_72449_c * d1;
          if (this.ghast.func_70631_g_()) {
            EntitySmallFireballOther entitylargefireball = new EntitySmallFireballOther(world, (EntityLivingBase)this.ghast, d2, d3, d4);
            entitylargefireball.field_70165_t = this.ghast.field_70165_t + vec3.field_72450_a * d1;
            entitylargefireball.field_70163_u = this.ghast.field_70163_u + 1.0D;
            entitylargefireball.field_70161_v = this.ghast.field_70161_v + vec3.field_72449_c * d1;
            float dm = (float)this.ghast.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
            entitylargefireball.damage = dm;
            this.ghast.func_184185_a(SoundEvents.field_187557_bK, 10.0F, 1.5F);
            world.func_72838_d((Entity)entitylargefireball);
            if (this.ghast.moralRaisedTimer > 200) {
              this.attackTimer = -15;
            } else {
              this.attackTimer = -30;
            } 
          } else {
            EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(world, (EntityLivingBase)this.ghast, d2, d3, d4);
            entitylargefireball.field_92057_e = this.ghast.getFireballStrength() * (this.ghast.isHero() ? 2 : 1);
            entitylargefireball.field_70165_t = this.ghast.field_70165_t + vec3.field_72450_a * d1;
            entitylargefireball.field_70163_u = this.ghast.field_70163_u + 1.0D;
            entitylargefireball.field_70161_v = this.ghast.field_70161_v + vec3.field_72449_c * d1;
            float dm = (float)this.ghast.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
            entitylargefireball.damage = dm;
            this.ghast.func_184185_a(SoundEvents.field_187557_bK, 10.0F, 1.0F);
            world.func_72838_d((Entity)entitylargefireball);
            if (this.ghast.moralRaisedTimer > 200) {
              this.attackTimer = -20;
            } else {
              this.attackTimer = -40;
            } 
          } 
        } 
      } else if (this.attackTimer > 0) {
        this.attackTimer--;
      } 
      this.ghast.setAttacking((this.attackTimer > 10 || this.ghast.getSpecialAttackTimer() > 1100));
    }
  }
  
  class AILookAround extends EntityAIBase {
    private EntityGhast parentEntity = EntityGhast.this;
    
    public AILookAround() {
      func_75248_a(2);
    }
    
    public boolean func_75250_a() {
      return this.parentEntity.isFlying();
    }
    
    public void func_75246_d() {
      if (this.parentEntity.func_184179_bs() != null) {
        this.parentEntity.field_70126_B = this.parentEntity.field_70177_z = (this.parentEntity.func_184179_bs()).field_70177_z;
        this.parentEntity.field_70125_A = 0.0F;
        this.parentEntity.func_70101_b(this.parentEntity.field_70177_z, this.parentEntity.field_70125_A);
        this.parentEntity.field_70759_as = this.parentEntity.field_70761_aq = this.parentEntity.field_70177_z;
      } else if (this.parentEntity.func_70638_az() == null) {
        this.parentEntity.field_70761_aq = this.parentEntity.field_70177_z = this.parentEntity.field_70759_as = -((float)Math.atan2(this.parentEntity.field_70159_w, this.parentEntity.field_70179_y)) * 180.0F / 3.1415927F;
      } else {
        EntityLivingBase entitylivingbase = this.parentEntity.func_70638_az();
        this.parentEntity.field_70761_aq = this.parentEntity.field_70177_z = this.parentEntity.field_70759_as;
        this.parentEntity.func_70671_ap().func_75651_a((Entity)entitylivingbase, 180.0F, EngenderConfig.mobs.useMobTalkerModels ? 40.0F : 180.0F);
      } 
    }
  }
  
  static class AIRandomFly extends EntityAIBase {
    private EntityGhast ghast;
    
    public AIRandomFly(EntityGhast ghast) {
      this.ghast = ghast;
      func_75248_a(1);
    }
    
    public boolean func_75250_a() {
      EntityMoveHelper entitymovehelper = this.ghast.func_70605_aq();
      if (!entitymovehelper.func_75640_a())
        return true; 
      double d0 = entitymovehelper.func_179917_d() - this.ghast.field_70165_t;
      double d1 = entitymovehelper.func_179919_e() - this.ghast.field_70163_u;
      double d2 = entitymovehelper.func_179918_f() - this.ghast.field_70161_v;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return ((d3 < 1.0D || d3 > 3600.0D) && !this.ghast.func_184207_aI());
    }
    
    public boolean func_75253_b() {
      return (!this.ghast.isFlying() && !this.ghast.func_184207_aI());
    }
    
    public void func_75249_e() {
      if (this.ghast.func_184207_aI())
        func_75251_c(); 
      Random random = this.ghast.func_70681_au();
      if (this.ghast.getOwner() != null) {
        if (this.ghast.getOwner().func_70093_af() || !this.ghast.getCurrentBook().func_190926_b()) {
          double d0 = (this.ghast.getOwner()).field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d1 = (this.ghast.getOwner()).field_70163_u + 8.0D + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d2 = (this.ghast.getOwner()).field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } else if (!this.ghast.getCurrentBook().func_190926_b()) {
          double d0 = (this.ghast.getOwner()).field_70165_t;
          double d1 = (this.ghast.getOwner()).field_70163_u + 4.0D;
          double d2 = (this.ghast.getOwner()).field_70161_v;
          this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } else {
          double d0 = (this.ghast.getOwner()).field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = (this.ghast.getOwner()).field_70163_u + ((EngenderConfig.mobs.useMobTalkerModels || this.ghast.func_70631_g_()) ? 16.0D : 32.0D) + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = (this.ghast.getOwner()).field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } 
      } else {
        double d0 = this.ghast.field_70165_t + random.nextGaussian() * 32.0D;
        double d1 = this.ghast.field_70163_u + random.nextGaussian() * 32.0D;
        double d2 = this.ghast.field_70161_v + random.nextGaussian() * 32.0D;
        this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
      } 
    }
  }
  
  static class GhastMoveHelper extends EntityMoveHelper {
    private EntityGhast parentEntity;
    
    private int courseChangeCooldown;
    
    public GhastMoveHelper(EntityGhast ghast) {
      super((EntityLiving)ghast);
      this.parentEntity = ghast;
    }
    
    public void func_75641_c() {
      if (this.parentEntity.func_184179_bs() != null && this.parentEntity.func_184179_bs() instanceof EntityPlayer && this.parentEntity.func_184207_aI()) {
        EntityPlayer passenger = (EntityPlayer)this.parentEntity.func_184179_bs();
        this.parentEntity.field_70761_aq = this.parentEntity.field_70177_z = this.parentEntity.field_70759_as = passenger.field_70177_z;
        this.parentEntity.field_70125_A = EngenderConfig.mobs.useMobTalkerModels ? passenger.field_70125_A : 0.0F;
        Vec3d vec3 = passenger.func_70676_i(1.0F);
        double d0 = this.parentEntity.field_70165_t - this.parentEntity.field_70165_t + vec3.field_72450_a * 50.0D;
        double d1 = this.parentEntity.field_70163_u - this.parentEntity.field_70163_u + vec3.field_72448_b * 50.0D;
        double d2 = this.parentEntity.field_70161_v - this.parentEntity.field_70161_v + vec3.field_72449_c * 50.0D;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        d3 = MathHelper.func_76133_a(d3);
        if (passenger.field_191988_bg > 0.0F) {
          this.parentEntity.field_70159_w -= d0 / d3 * ((this.parentEntity.moralRaisedTimer > 0) ? 0.2D : 0.1D);
          this.parentEntity.field_70181_x -= d1 / d3 * ((this.parentEntity.moralRaisedTimer > 0) ? 0.2D : 0.1D);
          this.parentEntity.field_70179_y -= d2 / d3 * ((this.parentEntity.moralRaisedTimer > 0) ? 0.2D : 0.1D);
          if (this.parentEntity.field_70159_w > 0.5D)
            this.parentEntity.field_70159_w = 0.5D; 
          if (this.parentEntity.field_70181_x > 0.5D)
            this.parentEntity.field_70181_x = 0.5D; 
          if (this.parentEntity.field_70179_y > 0.5D)
            this.parentEntity.field_70179_y = 0.5D; 
          if (this.parentEntity.field_70159_w < -0.5D)
            this.parentEntity.field_70159_w = -0.5D; 
          if (this.parentEntity.field_70181_x < -0.5D)
            this.parentEntity.field_70181_x = -0.5D; 
          if (this.parentEntity.field_70179_y < -0.5D)
            this.parentEntity.field_70179_y = -0.5D; 
        } 
      } 
      if (this.parentEntity.isFlying() && !this.parentEntity.func_184207_aI()) {
        if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && this.parentEntity.getJukeboxToDanceTo() == null) {
          double d0 = this.field_75646_b - this.parentEntity.field_70165_t;
          double d1 = this.field_75647_c - this.parentEntity.field_70163_u;
          double d2 = this.field_75644_d - this.parentEntity.field_70161_v;
          double d3 = d0 * d0 + d1 * d1 + d2 * d2;
          if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.parentEntity.func_70681_au().nextInt(5) + 2;
            d3 = MathHelper.func_76133_a(d3);
            if (isNotColliding(this.field_75646_b, this.field_75647_c, this.field_75644_d, d3)) {
              this.parentEntity.field_70159_w += d0 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.2D : 0.1D);
              this.parentEntity.field_70181_x += d1 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.2D : 0.1D);
              this.parentEntity.field_70179_y += d2 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.2D : 0.1D);
            } else {
              this.field_188491_h = EntityMoveHelper.Action.WAIT;
            } 
          } 
        } 
      } else {
        super.func_75641_c();
      } 
    }
    
    private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
      double d0 = (x - this.parentEntity.field_70165_t) / p_179926_7_;
      double d1 = (y - this.parentEntity.field_70163_u) / p_179926_7_;
      double d2 = (z - this.parentEntity.field_70161_v) / p_179926_7_;
      AxisAlignedBB axisalignedbb = this.parentEntity.func_174813_aQ();
      for (int i = 1; i < p_179926_7_; i++) {
        axisalignedbb = axisalignedbb.func_72317_d(d0, d1, d2);
        if (!this.parentEntity.field_70170_p.func_184144_a((Entity)this.parentEntity, axisalignedbb).isEmpty())
          return false; 
      } 
      return true;
    }
  }
}
