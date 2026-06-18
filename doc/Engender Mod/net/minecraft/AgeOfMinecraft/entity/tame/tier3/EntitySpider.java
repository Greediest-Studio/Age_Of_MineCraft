package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICustomLeapAttack;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCaveSpider;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpider extends EntityTameBase implements IJumpingMount, Light {
  private static final DataParameter<Boolean> SURVIVAL_TEST_SKIN = EntityDataManager.func_187226_a(EntitySpider.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.func_187226_a(EntitySpider.class, DataSerializers.field_187191_a);
  
  protected float jumpPower;
  
  public EntitySpider(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.45F, 1.7F);
    } else {
      func_70105_a(1.5F, 0.78F);
    } 
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAICustomLeapAttack((EntityLiving)this, 0.6F, 0.6F, 0.8F, 0.5F, 4.0D, 16.0D, 6));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.1D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 3;
  }
  
  public String getDescName() {
    return isSurvivalTestSkin() ? "spider_beta" : "spider";
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySpider(this.field_70170_p);
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public float getBonusVSLight() {
    return 1.5F;
  }
  
  public float getBonusVSArmored() {
    return 0.75F;
  }
  
  public float getBonusVSMassive() {
    return 0.5F;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.2F) : super.func_70647_i();
  }
  
  protected PathNavigate getNewNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(SURVIVAL_TEST_SKIN, Boolean.valueOf(false));
    this.field_70180_af.func_187214_a(CLIMBING, Byte.valueOf((byte)0));
  }
  
  public boolean isSurvivalTestSkin() {
    return ((Boolean)func_184212_Q().func_187225_a(SURVIVAL_TEST_SKIN)).booleanValue();
  }
  
  public void setSurvivalTestSkin(boolean childZombie) {
    func_184212_Q().func_187227_b(SURVIVAL_TEST_SKIN, Boolean.valueOf(childZombie));
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setSurvivalTestSkin(tagCompund.func_74767_n("EasterEgg"));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    if (((Boolean)this.field_70180_af.func_187225_a(SURVIVAL_TEST_SKIN)).booleanValue())
      tagCompound.func_74757_a("EasterEgg", true); 
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
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
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K) {
      setBesideClimbableBlock(this.field_70123_F);
      if (func_184207_aI() && !this.field_70122_E && this.field_70173_aa % 10 == 0) {
        func_184185_a(SoundEvents.field_187817_fK, 1.0F, 1.5F);
        List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list != null && !list.isEmpty() && this.jumpPower >= 1.0F)
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityLivingBase entity1 = list.get(i1);
            if (entity1 != null && entity1.func_70089_S())
              func_70652_k((Entity)entity1); 
          }  
      } 
    } 
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(16.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.30000001192092896D);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187817_fK;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187821_fM;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187819_fL;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187823_fN, 0.1F, (EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F) / getFittness());
    if (EngenderConfig.mobs.useMobTalkerModels)
      super.func_180429_a(pos, blockIn); 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_SPIDER;
  }
  
  public boolean func_70617_f_() {
    return isBesideClimbableBlock();
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 2 + this.field_70146_Z.nextInt(3); i++) {
        if (this instanceof EntityCaveSpider) {
          EntityCaveSpider baby = new EntityCaveSpider(this.field_70170_p);
          baby.func_82149_j((Entity)this);
          baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
          baby.setGrowingAge(-24000);
          baby.setChild(true);
          baby.setIsAntiMob(isAntiMob());
          baby.setIsHero(isHero());
          baby.setOwnerId(func_184753_b());
          if (isMarried())
            for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
              baby.levelUp();  
          this.field_70170_p.func_72838_d((Entity)baby);
        } else {
          EntitySpider baby = new EntitySpider(this.field_70170_p);
          baby.func_82149_j((Entity)this);
          baby.setGrowingAge(-32000);
          baby.setChild(true);
          baby.setOwnerId(func_184753_b());
          baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
          if (isMarried())
            for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
              baby.levelUp();  
          this.field_70170_p.func_72838_d((Entity)baby);
        } 
      }  
  }
  
  public void func_70110_aj() {}
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  public boolean func_70687_e(PotionEffect potioneffectIn) {
    return (potioneffectIn.func_188419_a() == MobEffects.field_76436_u) ? false : super.func_70687_e(potioneffectIn);
  }
  
  public boolean isBesideClimbableBlock() {
    return ((((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue() & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean climbing) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue();
    if (climbing) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.field_70180_af.func_187227_b(CLIMBING, Byte.valueOf(b0));
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    if (!this.field_70170_p.field_72995_K && this.field_70170_p.field_73012_v.nextInt(4) == 0)
      setSurvivalTestSkin(true); 
    if (this.field_70170_p.field_73012_v.nextInt(100) == 0 && getGrowingAge() >= 0) {
      EntitySkeleton entityskeleton = new EntitySkeleton(this.field_70170_p);
      entityskeleton.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, 0.0F);
      entityskeleton.func_180482_a(difficulty, (IEntityLivingData)null);
      this.field_70170_p.func_72838_d((Entity)entityskeleton);
      entityskeleton.func_184220_m((Entity)this);
      if (!isWild())
        entityskeleton.setOwnerId(func_184753_b()); 
    } 
    if (livingdata == null) {
      livingdata = new GroupData();
      if (this.field_70170_p.field_73012_v.nextFloat() < 0.25F * difficulty.func_180170_c())
        ((GroupData)livingdata).func_111104_a(this.field_70170_p.field_73012_v); 
    } 
    if (livingdata instanceof GroupData) {
      Potion potion = ((GroupData)livingdata).field_188478_a;
      if (potion != null)
        func_70690_d(new PotionEffect(potion, 2147483647)); 
    } 
    return livingdata;
  }
  
  public float func_70047_e() {
    return (this instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCreeder) ? (this.field_70131_O * 0.88F) : (EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.84F) : (this.field_70131_O * 0.74F));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (player.func_70093_af() && stack.func_190926_b() && func_184187_bx() == null) {
      List<EntitySkeleton> list = this.field_70170_p.func_175647_a(EntitySkeleton.class, func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (func_184187_bx() == null && list != null && !list.isEmpty() && !func_184207_aI() && hasOwner(player))
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntitySkeleton entity = list.get(i1);
          if (entity != null)
            if (func_184191_r((Entity)entity) && !entity.func_184218_aH() && !this.field_70170_p.field_72995_K) {
              player.func_184609_a(EnumHand.MAIN_HAND);
              entity.func_184220_m((Entity)this);
              func_184185_a(SoundEvents.field_187817_fK, 1.0F, 1.5F);
              break;
            }  
        }  
      return true;
    } 
    if (!player.func_70093_af() && stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger))
      if (passenger instanceof net.minecraft.entity.passive.EntityAmbientCreature) {
        double d8 = 1.1D;
        Vec3d vec3 = func_70676_i(1.0F);
        double dx = vec3.field_72450_a * d8;
        double dz = vec3.field_72449_c * d8;
        passenger.func_70107_b(this.field_70165_t + dx, this.field_70163_u + 0.25D, this.field_70161_v + dz);
      } else {
        float f2 = this.field_70761_aq * 3.1415927F / 180.0F;
        float f19 = MathHelper.func_76126_a(f2);
        float f3 = MathHelper.func_76134_b(f2);
        passenger.func_70107_b(this.field_70165_t + (f19 * 0.2F), this.field_70163_u + func_70042_X() + passenger.func_70033_W(), this.field_70161_v - (f3 * 0.2F));
      }  
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.725D;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_70617_f_() || (func_184218_aH() && func_184187_bx() instanceof EntityPlayer && ((EntityPlayer)func_184187_bx()).field_70721_aZ != 0.0F))
      this.field_70721_aZ++; 
    if (func_70638_az() != null && (!func_70638_az().func_70685_l((Entity)this) || !this.field_70122_E || this.field_70163_u < (func_70638_az()).field_70163_u))
      func_70605_aq().func_75642_a((func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v, 1.0D); 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 1024.0D && func_70068_e((Entity)func_70638_az()) > 64.0D && getSpecialAttackTimer() <= 0 && this.field_70122_E && isHero()) {
      func_70625_a((Entity)func_70638_az(), 180.0F, 30.0F);
      performSpecialAttack();
    } 
  }
  
  public void performSpecialAttack() {
    double d01 = (func_70638_az()).field_70165_t - this.field_70165_t;
    double d11 = (func_70638_az()).field_70161_v - this.field_70161_v;
    float f21 = MathHelper.func_76133_a(d01 * d01 + d11 * d11);
    double hor = (f21 / 16.0F) * 1.3D;
    double ver = 0.9D;
    this.field_70159_w = d01 / f21 * hor * hor + this.field_70159_w * hor;
    this.field_70179_y = d11 / f21 * hor * hor + this.field_70179_y * hor;
    this.field_70181_x = ver;
    func_184185_a(SoundEvents.field_187817_fK, 1.0F, 1.5F);
    setSpecialAttackTimer(100);
  }
  
  public void func_110206_u(int jumpPowerIn) {
    if (func_184207_aI()) {
      if (jumpPowerIn < 0)
        jumpPowerIn = 0; 
      if (jumpPowerIn >= 90) {
        func_184185_a(SoundEvents.field_187817_fK, 1.0F, 1.5F);
        this.jumpPower = 1.0F;
      } else {
        this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 30) {
      setSurvivalTestSkin(true);
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public boolean func_184776_b() {
    return true;
  }
  
  public void func_184775_b(int p_184775_1_) {
    func_70642_aH();
  }
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
    if (func_184207_aI() && func_82171_bF() && !(entitylivingbase instanceof net.minecraft.entity.passive.EntityAmbientCreature)) {
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      this.field_70747_aH = 0.1F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
        setBesideClimbableBlock(this.field_70123_F);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 0.6D * this.jumpPower;
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
        this.field_70160_al = true;
        float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
        float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
        this.field_70159_w = (((this instanceof EntityCaveSpider) ? -2.5F : -2.0F) * f * this.jumpPower);
        this.field_70179_y = (((this instanceof EntityCaveSpider) ? 2.5F : 2.0F) * f1 * this.jumpPower);
        this.jumpPower = 0.0F;
        ForgeHooks.onLivingJump((EntityLivingBase)this);
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
  
  public static class GroupData implements IEntityLivingData {
    public Potion field_188478_a;
    
    public void func_111104_a(Random rand) {
      int i = rand.nextInt(5);
      if (i <= 1) {
        this.field_188478_a = MobEffects.field_76424_c;
      } else if (i <= 2) {
        this.field_188478_a = MobEffects.field_76420_g;
      } else if (i <= 3) {
        this.field_188478_a = MobEffects.field_76428_l;
      } else if (i <= 4) {
        this.field_188478_a = MobEffects.field_76441_p;
      } 
    }
  }
}
