package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.ForgeHooks;

public class EntitySlime extends EntityTameBase implements IJumpingMount {
  private static final DataParameter<Integer> SLIME_SIZE = EntityDataManager.func_187226_a(EntitySlime.class, DataSerializers.field_187192_b);
  
  public float squishAmount;
  
  public float squishFactor;
  
  public float prevSquishFactor;
  
  private boolean wasOnGround;
  
  private int field_179924_h;
  
  protected float jumpPower;
  
  public EntitySlime(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
      this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
      this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.8D));
      this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    } else {
      this.field_70765_h = new SlimeMoveHelper(this);
      this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
      this.field_70714_bg.func_75776_a(2, new AISlimeFloat(this));
      this.field_70714_bg.func_75776_a(3, new AISlimeAttack(this));
      this.field_70714_bg.func_75776_a(4, new AISlimeFaceRandom(this));
      this.field_70714_bg.func_75776_a(5, new AISlimeHop(this));
    } 
  }
  
  public String getDescName() {
    return "slime";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public int getNextLevelRequirement() {
    return 10 * getSlimeSize();
  }
  
  public float getBonusVSLight() {
    return 1.25F;
  }
  
  public float getBonusVSArmored() {
    return 2.0F;
  }
  
  public float getBonusVSMassive() {
    return 0.5F;
  }
  
  public boolean func_70631_g_() {
    return (getSlimeSize() == 0);
  }
  
  public void setChild(boolean childZombie) {}
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(SLIME_SIZE, Integer.valueOf(1));
  }
  
  public int timesToConvert() {
    return 4 * getSlimeSize();
  }
  
  public void setSlimeSize(int size) {
    this.field_70180_af.func_187227_b(SLIME_SIZE, Integer.valueOf(size));
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(size * 0.125F, size * 0.5F - 0.05F);
      if (size <= 1)
        func_70105_a(0.2F, 0.85F); 
      if (size == 2 || size == 3)
        func_70105_a(0.35F, 1.5F); 
    } else {
      func_70105_a(0.5F * size, 0.5F * size);
    } 
    func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((size * size));
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(EngenderConfig.mobs.useMobTalkerModels ? 0.25D : (0.35F + 0.15F * size));
    func_70606_j(func_110138_aP());
    this.field_70728_aV = size;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public int getSlimeSize() {
    return ((Integer)this.field_70180_af.func_187225_a(SLIME_SIZE)).intValue();
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("Size", getSlimeSize() - 1);
    tagCompound.func_74757_a("wasOnGround", this.wasOnGround);
  }
  
  public boolean func_189101_db() {
    return (getSlimeSize() <= 1);
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    int i = tagCompund.func_74762_e("Size");
    if (i < 0)
      i = 0; 
    setSlimeSize(i + 1);
    this.wasOnGround = tagCompund.func_74767_n("wasOnGround");
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.SLIME;
  }
  
  protected SoundEvent getJumpSound() {
    return isSmallSlime() ? SoundEvents.field_189110_fE : SoundEvents.field_187882_fq;
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 2 + this.field_70146_Z.nextInt(3); i++) {
        EntitySlime baby = createInstance();
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(func_184753_b());
        baby.setSlimeSize(getSlimeSize() / 2);
        this.field_70170_p.func_72838_d((Entity)baby);
      }  
  }
  
  public void func_70071_h_() {
    ItemStack size = new ItemStack((this instanceof EntityMagmaCube) ? Items.field_151064_bs : Items.field_151123_aH, getSlimeSize());
    size.func_151001_c("Size: " + getSlimeSize());
    this.basicInventory.func_70299_a(7, size);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(getAttackStrength());
    this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
    this.prevSquishFactor = this.squishFactor;
    super.func_70071_h_();
    if (this.field_70122_E && !this.wasOnGround) {
      int i = getSlimeSize();
      if (spawnCustomParticles())
        i = 0; 
      for (int j = 0; j < i * 8; j++) {
        float f = this.field_70146_Z.nextFloat() * 6.2831855F;
        float f1 = this.field_70146_Z.nextFloat() * 0.5F + 0.5F;
        float f2 = MathHelper.func_76126_a(f) * this.field_70130_N * f1;
        float f3 = MathHelper.func_76134_b(f) * this.field_70130_N * f1;
        World world = this.field_70170_p;
        EnumParticleTypes enumparticletypes = getParticleType();
        double d0 = this.field_70165_t + f2;
        double d1 = this.field_70161_v + f3;
        world.func_175688_a(enumparticletypes, d0, (func_174813_aQ()).field_72338_b, d1, 0.0D, 0.0D, 0.0D, new int[0]);
      } 
      func_184185_a(func_184709_cY(), func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.05F : 0.8F));
      this.squishAmount = -0.5F;
    } else if (!this.field_70122_E && this.wasOnGround) {
      this.squishAmount = 1.0F;
    } 
    this.wasOnGround = this.field_70122_E;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      this.squishAmount *= 0.3F;
    } else {
      alterSquishAmount();
    } 
  }
  
  protected void alterSquishAmount() {
    this.squishAmount *= 0.6F;
  }
  
  protected int getJumpDelay() {
    return (this.moralRaisedTimer > 200) ? (this.field_70146_Z.nextInt(10) + 5) : (this.field_70146_Z.nextInt(20) + 10);
  }
  
  protected EntitySlime createInstance() {
    return new EntitySlime(this.field_70170_p);
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (SLIME_SIZE.equals(key)) {
      int size = getSlimeSize();
      if (EngenderConfig.mobs.useMobTalkerModels) {
        func_70105_a(size * 0.125F, size * 0.5F - 0.05F);
        if (getSlimeSize() <= 1)
          func_70105_a(0.2F, 0.85F); 
        if (getSlimeSize() == 2 || size == 3)
          func_70105_a(0.35F, 1.5F); 
      } else {
        func_70105_a(0.5F * size, 0.5F * size);
      } 
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    } 
    super.func_184206_a(key);
  }
  
  public void func_70106_y() {
    int i = getSlimeSize();
    if (!this.field_70170_p.field_72995_K && i > 1 && func_110143_aJ() <= 0.0F) {
      int j = 2 + this.field_70146_Z.nextInt(3);
      for (int k = 0; k < j; k++) {
        float f = ((k % 2) - 0.5F) * i / 4.0F;
        float f1 = ((k / 2) - 0.5F) * i / 4.0F;
        EntitySlime entityslime = createInstance();
        entityslime.func_70014_b(getEntityData());
        entityslime.setOwnerId(func_184753_b());
        entityslime.setSlimeSize(i / 2);
        entityslime.func_70012_b(this.field_70165_t + f, this.field_70163_u, this.field_70161_v + f1, this.field_70146_Z.nextFloat() * 360.0F, 0.0F);
        this.field_70170_p.func_72838_d((Entity)entityslime);
      } 
    } 
    super.func_70106_y();
  }
  
  public void performSpecialAttack() {
    func_70664_aZ();
    this.field_70181_x++;
    func_184185_a(func_184710_cZ(), func_70599_aP(), ((func_70681_au().nextFloat() - func_70681_au().nextFloat()) * 0.2F + 1.0F) * 0.8F);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    this.field_70702_br = this.field_191988_bg = 0.0F;
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 64.0D * getSlimeSize() && getSpecialAttackTimer() <= 0 && isHero()) {
      setSpecialAttackTimer(100 * getSlimeSize());
      func_184185_a(ESound.golemSmash, 10.0F, 2.0F - getSlimeSize() * 0.25F);
      createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u - 0.5D, this.field_70161_v, getSlimeSize(), false);
      if (func_70638_az() != null && !func_184191_r((Entity)func_70638_az())) {
        double d01 = (func_70638_az()).field_70165_t - this.field_70165_t;
        double d11 = (func_70638_az()).field_70161_v - this.field_70161_v;
        float f21 = MathHelper.func_76133_a(d01 * d01 + d11 * d11);
        double hor = (f21 / 16.0F) * 1.25D;
        this.field_70159_w = d01 / f21 * hor * hor + this.field_70159_w * hor;
        this.field_70179_y = d11 / f21 * hor * hor + this.field_70179_y * hor;
        this.field_70181_x = 0.6000000238418579D;
        double dou = func_70068_e((Entity)func_70638_az());
        if (dou <= 16.0D)
          func_70652_k((Entity)func_70638_az()); 
      } 
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(16.0D * getSlimeSize()), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!func_184191_r((Entity)entity)) {
              entity.field_70181_x += 0.75D;
              func_70652_k((Entity)entity);
            }  
        }  
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    func_85033_bc();
    if (func_184218_aH() && func_184187_bx() instanceof EntityLivingBase)
      this.field_70761_aq = this.field_70177_z = this.field_70759_as = ((EntityLivingBase)func_184187_bx()).field_70759_as; 
    if (func_70638_az() != null && !this.field_70122_E && EngenderConfig.mobs.useMobTalkerModels && func_70068_e((Entity)func_70638_az()) > ((func_70638_az()).field_70130_N * (func_70638_az()).field_70130_N) + 16.0D) {
      double d0 = (func_70638_az()).field_70165_t - this.field_70165_t;
      double d1 = (func_70638_az()).field_70161_v - this.field_70161_v;
      double d3 = d0 * d0 + d1 * d1;
      double d5 = MathHelper.func_76133_a(d3);
      this.field_70159_w += d0 / d5 * 0.5D - this.field_70159_w;
      this.field_70179_y += d1 / d5 * 0.5D - this.field_70179_y;
    } 
    if (func_70638_az() != null && this.field_70122_E && EngenderConfig.mobs.useMobTalkerModels)
      if (this.field_179924_h-- <= 0) {
        this.field_179924_h = getJumpDelay();
        if (func_70638_az() != null)
          this.field_179924_h /= 3; 
        if (func_70027_ad())
          this.field_179924_h /= 3; 
        if (!func_70093_af())
          func_70683_ar().func_75660_a(); 
      }  
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 32.0D * getSlimeSize() && getSpecialAttackTimer() <= 0 && this.field_70122_E && isHero())
      performSpecialAttack(); 
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      return (getSlimeSize() >= 4) ? I18n.func_74838_a("entity.SlimeHelpful.cmm4.name") : (isSmallSlime() ? I18n.func_74838_a("entity.SlimeHelpful.cmm1.name") : I18n.func_74838_a("entity.SlimeHelpful.cmm2.name"));
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.func_74838_a("entity." + s + ".name");
  }
  
  public void func_70108_f(Entity entityIn) {
    super.func_70108_f(entityIn);
    if (entityIn instanceof EntityLivingBase) {
      EntityLivingBase entity = (EntityLivingBase)entityIn;
      if (this.field_70173_aa > 10 && func_70685_l((Entity)entity) && func_70068_e((Entity)entity) < this.field_70130_N * 1.5D * this.field_70130_N * 1.5D && func_70652_k((Entity)entity))
        func_184185_a(SoundEvents.field_187870_fk, func_70599_aP(), func_70647_i()); 
    } 
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI() && func_82171_bF()) {
      if (func_70090_H() || func_180799_ab())
        if (func_70681_au().nextFloat() < 0.8F)
          this.field_70181_x += 0.1D - this.field_70181_x;  
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      this.field_70747_aH = 0.05F;
      if (func_184186_bw()) {
        func_70659_e(EngenderConfig.mobs.useMobTalkerModels ? (float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() : 0.0F);
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        func_70664_aZ();
        this.field_70181_x += this.jumpPower * ((this instanceof EntityMagmaCube) ? 1.0D : 0.25D);
        this.field_70160_al = true;
        if (forward > 0.0F) {
          float f = MathHelper.func_76126_a(this.field_70177_z * 0.017453292F);
          float f1 = MathHelper.func_76134_b(this.field_70177_z * 0.017453292F);
          this.field_70159_w += (-0.4F * f * this.jumpPower);
          this.field_70179_y += (0.4F * f1 * this.jumpPower);
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
      this.field_70747_aH = 0.02F;
      super.func_191986_a(strafe, vertical, forward);
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
  
  public void func_184775_b(int p_184775_1_) {
    func_184185_a(func_184710_cZ(), func_70599_aP(), ((func_70681_au().nextFloat() - func_70681_au().nextFloat()) * 0.2F + 1.0F) * 0.8F);
  }
  
  public void func_184777_r_() {}
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && func_184191_r((Entity)player) && getSlimeSize() < (isHero() ? 16 : 4) && stack.func_77973_b() == ((this instanceof EntityMagmaCube) ? Items.field_151064_bs : Items.field_151123_aH)) {
      if (!player.field_71075_bZ.field_75098_d)
        stack.func_190918_g(1); 
      func_184185_a(func_184709_cY(), func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.05F : 0.8F));
      setSlimeSize(getSlimeSize() + 1);
      func_184185_a(func_184709_cY(), func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.05F : 0.8F));
      int i = getSlimeSize();
      if (spawnCustomParticles())
        i = 0; 
      for (int j = 0; j < i * 8; j++) {
        float f = this.field_70146_Z.nextFloat() * 6.2831855F;
        float f1 = this.field_70146_Z.nextFloat() * 0.5F + 0.5F;
        float f2 = MathHelper.func_76126_a(f) * i * 0.5F * f1;
        float f3 = MathHelper.func_76134_b(f) * i * 0.5F * f1;
        World world = this.field_70170_p;
        EnumParticleTypes enumparticletypes = getParticleType();
        double d0 = this.field_70165_t + f2;
        double d1 = this.field_70161_v + f3;
        world.func_175688_a(enumparticletypes, d0, (func_174813_aQ()).field_72338_b, d1, 0.0D, 0.0D, 0.0D, new int[0]);
      } 
      return true;
    } 
    if (isSmallSlime()) {
      if (hasOwner(player) && EngenderConfig.mobs.useMobTalkerModels) {
        player.func_184609_a(EnumHand.MAIN_HAND);
        if (func_184187_bx() == null) {
          func_184205_a((Entity)player, true);
        } else {
          func_184210_p();
        } 
      } 
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null && !isSmallSlime()) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.8F) : (0.625F * this.field_70131_O);
  }
  
  protected boolean canDamagePlayer() {
    return true;
  }
  
  protected int getAttackStrength() {
    return (this.moralRaisedTimer > 200) ? (getSlimeSize() * 2) : getSlimeSize();
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + ((getSlimeSize() > 6) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return func_189101_db() ? SoundEvents.field_187898_fy : SoundEvents.field_187880_fp;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + ((getSlimeSize() > 6) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return func_189101_db() ? SoundEvents.field_187896_fx : SoundEvents.field_187874_fm;
  }
  
  protected SoundEvent func_184709_cY() {
    return func_189101_db() ? SoundEvents.field_187898_fy : SoundEvents.field_187886_fs;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.25F) : super.func_70647_i();
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return isSmallSlime() ? ELoot.ENTITIES_SLIME : LootTableList.field_186419_a;
  }
  
  public boolean isSmallSlime() {
    return (getSlimeSize() <= 1);
  }
  
  protected float func_70599_aP() {
    return (func_70093_af() ? 0.025F : 0.25F) * getSlimeSize();
  }
  
  public int func_70646_bf() {
    return 40;
  }
  
  protected boolean makesSoundOnJump() {
    return false;
  }
  
  protected boolean makesSoundOnLand() {
    return (getSlimeSize() > 2);
  }
  
  protected void func_70664_aZ() {
    func_184185_a(getJumpSound(), func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.35F : 1.1F));
    this.field_70181_x += 0.42D;
    this.field_70160_al = true;
    ForgeHooks.onLivingJump((EntityLivingBase)this);
  }
  
  public double func_70042_X() {
    return this.field_70131_O - 0.25D + this.squishFactor * getSlimeSize() * 0.25D * ((this instanceof EntityMagmaCube) ? 3.0D : 1.5D);
  }
  
  public double func_70033_W() {
    return EngenderConfig.mobs.useMobTalkerModels ? 0.0D : 0.5D;
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    int i = this.field_70146_Z.nextInt(3);
    if (i < 2 && this.field_70146_Z.nextFloat() < 0.5F * difficulty.func_180170_c())
      i++; 
    int j = 1 << i;
    if (this.field_70146_Z.nextInt(200) == 0) {
      setSlimeSize(8);
    } else {
      setSlimeSize(j);
    } 
    return super.func_180482_a(difficulty, livingdata);
  }
  
  protected SoundEvent func_184710_cZ() {
    return func_189101_db() ? SoundEvents.field_187900_fz : SoundEvents.field_187886_fs;
  }
  
  protected boolean spawnCustomParticles() {
    return false;
  }
  
  static class AISlimeAttack extends EntityAIBase {
    private final EntitySlime slime;
    
    private int growTieredTimer;
    
    public AISlimeAttack(EntitySlime slimeIn) {
      this.slime = slimeIn;
      func_75248_a(2);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.slime.func_70638_az();
      return (entitylivingbase == null) ? false : (!entitylivingbase.func_70089_S() ? false : ((!(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).field_71075_bZ.field_75102_a)));
    }
    
    public void func_75249_e() {
      this.growTieredTimer = 300;
      super.func_75249_e();
    }
    
    public boolean func_75253_b() {
      EntityLivingBase entitylivingbase = this.slime.func_70638_az();
      return (entitylivingbase == null) ? false : (!entitylivingbase.func_70089_S() ? false : ((entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).field_71075_bZ.field_75102_a) ? false : ((--this.growTieredTimer > 0))));
    }
    
    public void func_75246_d() {
      this.slime.func_70625_a((Entity)this.slime.func_70638_az(), 10.0F, 10.0F);
      ((EntitySlime.SlimeMoveHelper)this.slime.func_70605_aq()).setDirection(this.slime.field_70177_z, this.slime.canDamagePlayer());
      if (this.slime.field_70173_aa % 10 == 0 && this.slime.func_70685_l((Entity)this.slime.func_70638_az()) && this.slime.func_70068_e((Entity)this.slime.func_70638_az()) < this.slime.field_70130_N * 1.5D * this.slime.field_70130_N * 1.5D && this.slime.func_70638_az() != null)
        this.slime.func_70652_k((Entity)this.slime.func_70638_az()); 
    }
  }
  
  static class AISlimeFaceRandom extends EntityAIBase {
    private final EntitySlime slime;
    
    private float chosenDegrees;
    
    private int nextRandomizeTime;
    
    public AISlimeFaceRandom(EntitySlime slimeIn) {
      this.slime = slimeIn;
      func_75248_a(2);
    }
    
    public boolean func_75250_a() {
      return (this.slime.func_70638_az() == null && (this.slime.field_70122_E || this.slime.func_70090_H() || this.slime.func_180799_ab() || this.slime.func_70644_a(MobEffects.field_188424_y)));
    }
    
    public void func_75246_d() {
      if (--this.nextRandomizeTime <= 0) {
        this.nextRandomizeTime = 40 + this.slime.func_70681_au().nextInt(60);
        this.chosenDegrees = this.slime.func_70681_au().nextInt(360);
      } 
      ((EntitySlime.SlimeMoveHelper)this.slime.func_70605_aq()).setDirection(this.chosenDegrees, false);
    }
  }
  
  static class AISlimeFloat extends EntityAIBase {
    private final EntitySlime slime;
    
    public AISlimeFloat(EntitySlime slimeIn) {
      this.slime = slimeIn;
      func_75248_a(5);
      ((PathNavigateGround)slimeIn.func_70661_as()).func_179693_d(true);
    }
    
    public boolean func_75250_a() {
      return (this.slime.func_70090_H() || this.slime.func_180799_ab());
    }
    
    public void func_75246_d() {
      if (this.slime.func_70681_au().nextFloat() < 0.8F)
        this.slime.func_70683_ar().func_75660_a(); 
      ((EntitySlime.SlimeMoveHelper)this.slime.func_70605_aq()).setSpeed(1.2D);
    }
  }
  
  static class AISlimeHop extends EntityAIBase {
    private final EntitySlime slime;
    
    public AISlimeHop(EntitySlime slimeIn) {
      this.slime = slimeIn;
      func_75248_a(5);
    }
    
    public boolean func_75250_a() {
      return true;
    }
    
    public void func_75246_d() {
      ((EntitySlime.SlimeMoveHelper)this.slime.func_70605_aq()).setSpeed(1.0D);
    }
  }
  
  public static class SlimeMoveHelper extends EntityMoveHelper {
    private float yRot;
    
    private int jumpDelay;
    
    private final EntitySlime slime;
    
    private boolean isAggressive;
    
    public SlimeMoveHelper(EntitySlime slimeIn) {
      super((EntityLiving)slimeIn);
      this.slime = slimeIn;
      this.yRot = 180.0F * slimeIn.field_70177_z / 3.1415927F;
    }
    
    public void setDirection(float p_179920_1_, boolean p_179920_2_) {
      this.yRot = p_179920_1_;
      this.isAggressive = p_179920_2_;
    }
    
    public void setSpeed(double speedIn) {
      this.field_75645_e = speedIn;
      this.field_188491_h = EntityMoveHelper.Action.MOVE_TO;
    }
    
    public void func_75641_c() {
      this.field_75648_a.field_70177_z = func_75639_a(this.field_75648_a.field_70177_z, this.yRot, 90.0F);
      this.field_75648_a.field_70759_as = this.field_75648_a.field_70177_z;
      this.field_75648_a.field_70761_aq = this.field_75648_a.field_70177_z;
      if (this.field_188491_h != EntityMoveHelper.Action.MOVE_TO || this.slime.getJukeboxToDanceTo() != null || !((EntityTameBase)this.field_75648_a).getCurrentBook().func_190926_b()) {
        this.field_75648_a.func_191989_p(0.0F);
      } else {
        this.field_188491_h = EntityMoveHelper.Action.WAIT;
        if (this.field_75648_a.field_70122_E && !this.field_75648_a.func_184207_aI() && !this.field_75648_a.func_184218_aH()) {
          this.field_75648_a.func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
          if (this.jumpDelay-- <= 0) {
            this.jumpDelay = this.slime.getJumpDelay();
            if (this.isAggressive)
              this.jumpDelay /= 3; 
            if (this.field_75648_a.func_70027_ad())
              this.jumpDelay /= 3; 
            this.slime.func_70683_ar().func_75660_a();
          } else {
            this.slime.field_70702_br = 0.0F;
            this.slime.field_191988_bg = 0.0F;
            this.field_75648_a.func_70659_e(0.0F);
          } 
        } else {
          this.field_75648_a.func_70659_e((float)(this.field_75645_e * this.field_75648_a.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e()));
        } 
      } 
    }
  }
}
