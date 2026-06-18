package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityElderGuardian;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGuardian extends EntityTameBase implements Armored {
  private static final DataParameter<Byte> STATUS = EntityDataManager.func_187226_a(EntityGuardian.class, DataSerializers.field_187191_a);
  
  private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.func_187226_a(EntityGuardian.class, DataSerializers.field_187192_b);
  
  private float field_175482_b;
  
  private float field_175484_c;
  
  private float tailSwiping;
  
  private float spineExtention;
  
  private float field_175486_bm;
  
  private EntityLivingBase targetedEntity;
  
  private int field_175479_bo;
  
  private boolean field_175480_bp;
  
  protected EntityAIWander wander;
  
  public EntityGuardian(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.field_70728_aV = 10;
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.45F, 1.75F);
    } else {
      func_70105_a(1.0F, 1.0F);
    } 
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 48.0F, 8.0F));
    this.field_70714_bg.func_75776_a(4, new AIGuardianAttack());
    EntityAIMoveTowardsRestriction entityaimovetowardsrestriction;
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)(entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D)));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)(this.wander = new EntityAIWander((EntityCreature)this, 1.0D, 80)));
    this.field_70714_bg.func_75776_a(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.wander.func_75248_a(3);
    entityaimovetowardsrestriction.func_75248_a(3);
    this.field_70765_h = new GuardianMoveHelper(this);
    this.field_175484_c = this.field_175482_b = this.field_70146_Z.nextFloat();
  }
  
  public String getDescName() {
    return "guardian";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGuardian(this.field_70170_p);
  }
  
  public float getBonusVSLight() {
    return 3.0F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
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
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1 + this.field_70146_Z.nextInt(2); i++) {
        EntityElderGuardian entityElderGuardian;
        EntityGuardian baby = new EntityGuardian(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        if (this.field_70146_Z.nextFloat() < 0.01F) {
          entityElderGuardian = new EntityElderGuardian(this.field_70170_p);
          entityElderGuardian.setGrowingAge(-100000);
          entityElderGuardian.setElder();
        } else {
          entityElderGuardian.setGrowingAge(-24000);
        } 
        entityElderGuardian.setChild(true);
        entityElderGuardian.setIsAntiMob(isAntiMob());
        entityElderGuardian.setIsHero(isHero());
        entityElderGuardian.setOwnerId(func_184753_b());
        if (isMarried())
          for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
            entityElderGuardian.levelUp();  
        this.field_70170_p.func_72838_d((Entity)entityElderGuardian);
      }  
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.3F) : super.func_70647_i();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public float func_70603_bj() {
    return EngenderConfig.mobs.useMobTalkerModels ? 1.0F : 1.0F;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.4D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(32.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
  }
  
  public float getAttackAnimationScale(float p_175477_1_) {
    return (this.field_175479_bo + p_175477_1_) / func_175464_ck();
  }
  
  protected PathNavigate getNewNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateSwimmer((EntityLiving)this, worldIn);
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos).func_185904_a() == Material.field_151586_h) ? (10.0F + this.field_70170_p.func_175724_o(pos) - 0.5F) : super.func_180484_a(pos);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(STATUS, Byte.valueOf((byte)0));
    this.field_70180_af.func_187214_a(TARGET_ENTITY, Integer.valueOf(0));
  }
  
  private boolean isSyncedFlagSet(int flagId) {
    return ((((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue() & flagId) != 0);
  }
  
  private void setSyncedFlag(int flagId, boolean state) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(STATUS)).byteValue();
    if (state) {
      this.field_70180_af.func_187227_b(STATUS, Byte.valueOf((byte)(b0 | flagId)));
    } else {
      this.field_70180_af.func_187227_b(STATUS, Byte.valueOf((byte)(b0 & (flagId ^ 0xFFFFFFFF))));
    } 
  }
  
  public boolean func_175472_n() {
    return isSyncedFlagSet(2);
  }
  
  private void func_175476_l(boolean p_175476_1_) {
    setSyncedFlag(2, p_175476_1_);
  }
  
  public boolean func_96092_aw() {
    return false;
  }
  
  public int func_175464_ck() {
    return 80;
  }
  
  public boolean isElder() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public void setElder() {
    this.field_175486_bm = this.spineExtention = 1.0F;
  }
  
  private void setTargetedEntity(int entityId) {
    this.field_70180_af.func_187227_b(TARGET_ENTITY, Integer.valueOf(entityId));
  }
  
  public boolean hasTargetedEntity() {
    return (((Integer)this.field_70180_af.func_187225_a(TARGET_ENTITY)).intValue() != 0 && func_70089_S());
  }
  
  public EntityLivingBase getTargetedEntity() {
    if (!hasTargetedEntity())
      return null; 
    if (this.field_70170_p.field_72995_K) {
      if (this.targetedEntity != null)
        return this.targetedEntity; 
      Entity entity = this.field_70170_p.func_73045_a(((Integer)this.field_70180_af.func_187225_a(TARGET_ENTITY)).intValue());
      if (entity instanceof EntityLivingBase) {
        this.targetedEntity = (EntityLivingBase)entity;
        return this.targetedEntity;
      } 
      return null;
    } 
    return func_70638_az();
  }
  
  public void func_184206_a(DataParameter<?> key) {
    super.func_184206_a(key);
    if (TARGET_ENTITY.equals(key)) {
      this.field_175479_bo = 0;
      this.targetedEntity = null;
    } 
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187670_cb;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187687_ch;
  }
  
  protected SoundEvent func_184615_bR() {
    func_70624_b((EntityLivingBase)null);
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    if (!func_70090_H())
      func_184185_a(SoundEvents.field_187681_cf, func_70599_aP(), func_70647_i()); 
    return SoundEvents.field_187678_ce;
  }
  
  protected boolean func_70041_e_() {
    return (!func_70090_H() && EngenderConfig.mobs.useMobTalkerModels);
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.85F + ((func_184218_aH() || func_70090_H() || func_180799_ab() || isSitResting()) ? (MathHelper.func_76134_b(this.field_70173_aa * 0.2F) * 0.1F) : 0.0F)) : (this.field_70131_O * 0.5F);
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_190929_cY && !(this instanceof EntityElderGuardian)) {
      EntityElderGuardian baby = new EntityElderGuardian(this.field_70170_p);
      baby.func_82149_j((Entity)this);
      baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
      baby.setOwnerId(func_184753_b());
      this.field_70170_p.func_72838_d((Entity)baby);
      this.field_70170_p.func_72900_e((Entity)this);
      stack.func_190918_g(1);
      (Minecraft.func_71410_x()).field_71452_i.func_191271_a((Entity)this, EnumParticleTypes.TOTEM, 30);
      func_184185_a(SoundEvents.field_191263_gW, 1.0F, 1.0F);
      this.field_70173_aa = 0;
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void func_70636_d() {
    this.field_70765_h = (!func_70090_H() && EngenderConfig.mobs.useMobTalkerModels) ? new EntityMoveHelper((EntityLiving)this) : new GuardianMoveHelper(this);
    if (!EngenderConfig.mobs.useMobTalkerModels)
      this.field_70761_aq = this.field_70177_z = this.field_70759_as; 
    if (this.field_70725_aQ > 0)
      this.field_70181_x = -0.6D; 
    func_70031_b(false);
    if (this.field_70170_p.field_72995_K) {
      this.field_175484_c = this.field_175482_b;
      if (!func_70090_H() && !isElder() && !func_70644_a(MobEffects.field_188424_y)) {
        this.tailSwiping = 1.0F;
        this.field_175480_bp = (this.field_70181_x < 0.0D && this.field_70170_p.func_175677_d((new BlockPos((Entity)this)).func_177977_b(), false));
      } else if (func_175472_n()) {
        if (this.tailSwiping < 0.5F) {
          this.tailSwiping = 4.0F;
        } else {
          this.tailSwiping += (0.5F - this.tailSwiping) * 0.1F;
        } 
      } else if (func_70631_g_()) {
        this.tailSwiping = 1.0F;
      } else {
        this.tailSwiping += (0.125F - this.tailSwiping) * 0.2F;
      } 
      if (!func_175446_cd()) {
        this.field_175482_b += this.tailSwiping;
        this.field_175486_bm = this.spineExtention;
      } 
      if (!func_70090_H() && !func_184218_aH() && !func_70644_a(MobEffects.field_188424_y)) {
        if (isElder()) {
          if (this.field_70122_E && !func_70093_af()) {
            this.spineExtention += (0.7F - this.spineExtention) * 0.05F;
          } else {
            this.spineExtention += (-0.7F - this.spineExtention) * 0.25F;
          } 
        } else {
          this.spineExtention = this.field_70146_Z.nextFloat() - 0.5F;
        } 
      } else if (func_175472_n() || func_184218_aH() || func_70644_a(MobEffects.field_188424_y)) {
        this.spineExtention += (-0.7F - this.spineExtention) * 0.25F;
      } else {
        this.spineExtention += (0.25F - this.spineExtention) * 0.06F;
      } 
      if (func_175446_cd())
        this.spineExtention = 0.0F; 
      if (func_175472_n() && func_70090_H()) {
        Vec3d vec3 = func_70676_i(0.0F);
        for (int i = 0; i < 2; i++)
          this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N - vec3.field_72450_a * 1.5D, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - vec3.field_72448_b * 1.5D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N - vec3.field_72449_c * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]); 
      } 
      if (hasTargetedEntity()) {
        if (this.field_175479_bo < func_175464_ck())
          this.field_175479_bo++; 
        EntityLivingBase entitylivingbase = getTargetedEntity();
        if (entitylivingbase != null) {
          func_70671_ap().func_75651_a((Entity)entitylivingbase, 180.0F, func_70646_bf());
          func_70671_ap().func_75649_a();
          double d5 = func_175477_p(0.0F);
          double d0 = entitylivingbase.field_70165_t - this.field_70165_t;
          double d1 = entitylivingbase.field_70163_u + entitylivingbase.func_70047_e() - this.field_70163_u + func_70047_e();
          double d2 = entitylivingbase.field_70161_v - this.field_70161_v;
          double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
          d0 /= d3;
          d1 /= d3;
          d2 /= d3;
          double d4 = this.field_70146_Z.nextDouble();
          while (d4 < d3) {
            d4 += 1.8D - d5 + this.field_70146_Z.nextDouble() * (1.7D - d5);
            this.field_70170_p.func_175688_a(EnumParticleTypes.WATER_BUBBLE, this.field_70165_t + d0 * d4, this.field_70163_u + d1 * d4 + func_70047_e(), this.field_70161_v + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
          } 
        } 
      } 
    } 
    if (this.field_70171_ac) {
      func_70050_g(300);
    } else if (!func_184207_aI() && func_70089_S() && this.field_70122_E && !func_184207_aI() && !isInLove() && !func_70093_af() && !isElder() && !EngenderConfig.mobs.useMobTalkerModels) {
      this.field_70159_w += ((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * (func_70631_g_() ? 0.2F : 0.4F));
      this.field_70179_y += ((this.field_70146_Z.nextFloat() * 2.0F - 1.0F) * (func_70631_g_() ? 0.2F : 0.4F));
      this.field_70177_z = this.field_70146_Z.nextFloat() * 360.0F;
      func_70664_aZ();
    } 
    if (hasTargetedEntity())
      this.field_70761_aq = this.field_70177_z = this.field_70759_as; 
    if (this.field_70173_aa % 20 == 0)
      func_175476_l(false); 
    super.func_70636_d();
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.85D;
  }
  
  public boolean shouldDismountInWater(Entity rider) {
    return false;
  }
  
  protected void func_70664_aZ() {
    this.field_70737_aN = 0;
    func_184185_a(SoundEvents.field_187684_cg, 1.0F, func_70631_g_() ? 1.5F : 1.0F);
    this.field_70181_x += (func_70631_g_() ? 0.5D : 0.6D) - this.field_70181_x;
    this.field_70122_E = false;
    this.field_70160_al = true;
  }
  
  @SideOnly(Side.CLIENT)
  public float func_175471_a(float p_175471_1_) {
    return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * p_175471_1_;
  }
  
  @SideOnly(Side.CLIENT)
  public float func_175469_o(float p_175469_1_) {
    return this.field_175486_bm + (this.spineExtention - this.field_175486_bm) * p_175469_1_;
  }
  
  public float func_175477_p(float p_175477_1_) {
    return (this.field_175479_bo + p_175477_1_) / func_175464_ck();
  }
  
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_GUARDIAN;
  }
  
  public boolean func_70058_J() {
    return (this.field_70170_p.func_72917_a(func_174813_aQ(), (Entity)this) && this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ()).isEmpty());
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (super.func_70097_a(source, amount)) {
      if (!func_175472_n() && !source.func_82725_o() && source.func_76346_g() instanceof EntityLivingBase) {
        EntityLivingBase entitylivingbase = (EntityLivingBase)source.func_76346_g();
        if (!source.func_94541_c() && (!entitylivingbase.func_184191_r((Entity)this) || (getOwner() != null && entitylivingbase == getOwner()))) {
          inflictEngenderMobDamage(entitylivingbase, " died trying to attack ", DamageSource.func_92087_a((Entity)this), 2.0F);
          func_184185_a(SoundEvents.field_187903_gc, func_70599_aP(), func_70647_i());
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public int func_70646_bf() {
    return EngenderConfig.mobs.useMobTalkerModels ? 40 : 180;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
    if (func_184207_aI() && func_82171_bF()) {
      entitylivingbase.func_70690_d(new PotionEffect(MobEffects.field_76427_o, 200, 0, true, true));
      entitylivingbase.func_70690_d(new PotionEffect(MobEffects.field_76439_r, 200, 0, true, true));
      if (func_70638_az() == null) {
        this.field_70761_aq = this.field_70177_z = this.field_70759_as = entitylivingbase.field_70177_z;
        this.field_70125_A = 0.0F;
        func_70101_b(this.field_70177_z, this.field_70125_A);
        this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      } 
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      this.field_70747_aH = 0.1F;
      if (func_184186_bw()) {
        Vec3d vec3 = entitylivingbase.func_70676_i(1.0F);
        double d0 = this.field_70165_t - this.field_70165_t + vec3.field_72450_a * 50.0D;
        double d1 = this.field_70163_u - this.field_70163_u + vec3.field_72448_b * 50.0D;
        double d2 = this.field_70161_v - this.field_70161_v + vec3.field_72449_c * 50.0D;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        d3 = MathHelper.func_76133_a(d3);
        if ((forward != 0.0F || strafe != 0.0F) && func_70090_H()) {
          this.field_70159_w -= d0 / d3 * ((this.moralRaisedTimer > 0) ? 0.1D : 0.05D);
          this.field_70181_x -= d1 / d3 * ((this.moralRaisedTimer > 0) ? 0.1D : 0.05D);
          this.field_70179_y -= d2 / d3 * ((this.moralRaisedTimer > 0) ? 0.1D : 0.05D);
          if (this.field_70159_w > (isElder() ? 0.3D : 0.6D))
            this.field_70159_w = isElder() ? 0.3D : 0.6D; 
          if (this.field_70181_x > (isElder() ? 0.3D : 0.6D))
            this.field_70181_x = isElder() ? 0.3D : 0.6D; 
          if (this.field_70179_y > (isElder() ? 0.3D : 0.6D))
            this.field_70179_y = isElder() ? 0.3D : 0.6D; 
          if (this.field_70159_w < -(isElder() ? 0.3D : 0.6D))
            this.field_70159_w = -(isElder() ? 0.3D : 0.6D); 
          if (this.field_70181_x < -(isElder() ? 0.3D : 0.6D))
            this.field_70181_x = -(isElder() ? 0.3D : 0.6D); 
          if (this.field_70179_y < -(isElder() ? 0.3D : 0.6D))
            this.field_70179_y = -(isElder() ? 0.3D : 0.6D); 
          func_175476_l(true);
        } 
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        if (func_70090_H() && this.field_70725_aQ <= 0) {
          func_191958_b(strafe, vertical, forward, 0.1F);
          func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
          this.field_70159_w *= 0.8999999761581421D;
          this.field_70181_x *= 0.8999999761581421D;
          this.field_70179_y *= 0.8999999761581421D;
          if (func_70090_H() && !func_175472_n() && func_70638_az() == null && !func_184207_aI())
            this.field_70181_x -= 0.005D; 
        } else {
          if ((forward != 0.0F || strafe != 0.0F) && this.field_70122_E && !EngenderConfig.mobs.useMobTalkerModels)
            func_70664_aZ(); 
          super.func_191986_a(strafe, vertical, forward);
        } 
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      this.field_184618_aE = this.field_70721_aZ;
      double d5 = this.field_70165_t - this.field_70169_q;
      double d7 = this.field_70161_v - this.field_70166_s;
      float f10 = MathHelper.func_76133_a(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.field_70721_aZ += (f10 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
    } else if (func_70613_aW()) {
      if (func_70090_H() && this.field_70725_aQ <= 0) {
        func_191958_b(strafe, vertical, forward, 0.1F);
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= 0.8999999761581421D;
        this.field_70181_x *= 0.8999999761581421D;
        this.field_70179_y *= 0.8999999761581421D;
        if (func_70090_H() && !func_175472_n() && func_70638_az() == null && !func_184207_aI())
          this.field_70181_x -= 0.005D; 
      } else {
        super.func_191986_a(strafe, vertical, forward);
      } 
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  class AIGuardianAttack extends EntityAIBase {
    private EntityGuardian guardian = EntityGuardian.this;
    
    private int tickCounter;
    
    public AIGuardianAttack() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.guardian.func_70638_az();
      return (entitylivingbase != null && entitylivingbase.func_70089_S() && !this.guardian.func_70631_g_());
    }
    
    public boolean func_75253_b() {
      return (super.func_75253_b() && this.guardian.func_70638_az() != null && this.tickCounter < this.guardian.func_175464_ck());
    }
    
    public void func_75249_e() {
      this.tickCounter = -10;
      this.guardian.func_70661_as().func_75499_g();
      this.guardian.func_70671_ap().func_75651_a((Entity)this.guardian.func_70638_az(), 180.0F, this.guardian.func_70646_bf());
      this.guardian.field_70160_al = true;
      this.guardian.setSitResting(false);
    }
    
    public void func_75251_c() {
      this.guardian.setTargetedEntity(0);
      this.guardian.func_70624_b((EntityLivingBase)null);
      this.guardian.wander.func_179480_f();
    }
    
    public void func_75246_d() {
      this.guardian.setSitResting(false);
      EntityLivingBase entitylivingbase = this.guardian.func_70638_az();
      this.guardian.func_70661_as().func_75499_g();
      this.guardian.func_70671_ap().func_75651_a((Entity)entitylivingbase, 180.0F, this.guardian.func_70646_bf());
      if (!this.guardian.func_70685_l((Entity)entitylivingbase)) {
        this.guardian.func_70624_b((EntityLivingBase)null);
      } else {
        this.tickCounter++;
        if (this.guardian.moralRaisedTimer > 200)
          this.tickCounter++; 
        if (this.tickCounter > 0)
          this.guardian.func_184185_a(SoundEvents.field_187675_cd, 1.0F, 0.5F + (float)(this.tickCounter * 0.025D)); 
        if (this.tickCounter > 0 && this.guardian.isHero() && this.guardian.getSpecialAttackTimer() <= 0)
          this.guardian.func_184185_a(SoundEvents.field_187675_cd, 10.0F, 0.5F + (float)(this.tickCounter * 0.025D)); 
        if (this.tickCounter == 0) {
          this.guardian.setTargetedEntity(this.guardian.func_70638_az().func_145782_y());
        } else if (this.tickCounter >= this.guardian.func_175464_ck() && (entitylivingbase.field_70172_ad == 0 || this.guardian.isElder())) {
          float f = 1.0F;
          if (this.guardian.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
            f += 2.0F; 
          if (this.guardian.isElder()) {
            f += 2.0F;
            entitylivingbase.field_70760_ar = entitylivingbase.field_70761_aq = entitylivingbase.field_70126_B = entitylivingbase.field_70177_z = entitylivingbase.field_70758_at = entitylivingbase.field_70759_as = this.guardian.field_70759_as;
            float xRatio = MathHelper.func_76126_a(entitylivingbase.field_70759_as * 0.017453292F);
            float zRatio = -MathHelper.func_76134_b(entitylivingbase.field_70759_as * 0.017453292F);
            float f1 = MathHelper.func_76129_c(xRatio * xRatio + zRatio * zRatio);
            entitylivingbase.field_70159_w -= xRatio / f1 / this.guardian.func_70032_d((Entity)entitylivingbase) * 4.0D;
            entitylivingbase.field_70179_y -= zRatio / f1 / this.guardian.func_70032_d((Entity)entitylivingbase) * 4.0D;
            entitylivingbase.field_70181_x++;
            if (entitylivingbase instanceof EntityPlayerMP)
              ((EntityPlayerMP)entitylivingbase).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity((Entity)entitylivingbase)); 
          } 
          this.guardian.inflictEngenderMobDamage(entitylivingbase, " was laser beamed by ", DamageSource.func_76358_a((EntityLivingBase)this.guardian).func_82726_p(), (float)this.guardian.func_110140_aT().func_111151_a(SharedMonsterAttributes.field_111264_e).func_111125_b() + f);
          if (this.guardian.isHero() && this.guardian.getSpecialAttackTimer() <= 0) {
            this.guardian.func_184185_a(SoundEvents.field_187855_gD, 10.0F, 1.25F);
            entitylivingbase.field_70172_ad = 0;
            EntityTameBase.createEngenderModExplosion((Entity)this.guardian, entitylivingbase.field_70165_t, entitylivingbase.field_70163_u + (entitylivingbase.field_70131_O / 2.0F), entitylivingbase.field_70161_v, 7.0F, true, false);
            entitylivingbase.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this.guardian), (float)this.guardian.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * 3.0F);
            this.guardian.setSpecialAttackTimer(600);
          } 
        } 
        super.func_75246_d();
      } 
    }
  }
  
  class GuardianMoveHelper extends EntityMoveHelper {
    private EntityGuardian entityGuardian;
    
    public GuardianMoveHelper(EntityGuardian guardian) {
      super((EntityLiving)guardian);
      this.entityGuardian = guardian;
    }
    
    public void func_75641_c() {
      if ((this.entityGuardian.func_70090_H() || this.entityGuardian.func_70644_a(MobEffects.field_188424_y)) && this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && !this.entityGuardian.func_70661_as().func_75500_f()) {
        double d0 = this.field_75646_b - this.entityGuardian.field_70165_t;
        double d1 = this.field_75647_c - this.entityGuardian.field_70163_u;
        double d2 = this.field_75644_d - this.entityGuardian.field_70161_v;
        double d3 = MathHelper.func_76133_a(d0 * d0 + d1 * d1 + d2 * d2);
        d1 /= d3;
        float f = (float)(MathHelper.func_181159_b(d2, d0) * 57.29577951308232D) - 90.0F;
        this.entityGuardian.field_70177_z = func_75639_a(this.entityGuardian.field_70177_z, f, 90.0F);
        this.entityGuardian.field_70761_aq = this.entityGuardian.field_70177_z;
        float f1 = (float)(this.field_75645_e * this.entityGuardian.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        this.entityGuardian.func_70659_e(this.entityGuardian.func_70689_ay() + (f1 - this.entityGuardian.func_70689_ay()) * 0.125F);
        double d4 = Math.sin((this.entityGuardian.field_70173_aa + this.entityGuardian.func_145782_y()) * 0.5D) * 0.05D;
        double d5 = Math.cos((this.entityGuardian.field_70177_z * 0.017453292F));
        double d6 = Math.sin((this.entityGuardian.field_70177_z * 0.017453292F));
        this.entityGuardian.field_70159_w += d4 * d5;
        this.entityGuardian.field_70179_y += d4 * d6;
        d4 = Math.sin((this.entityGuardian.field_70173_aa + this.entityGuardian.func_145782_y()) * 0.75D) * 0.05D;
        this.entityGuardian.field_70181_x += d4 * (d6 + d5) * 0.25D;
        this.entityGuardian.field_70181_x += this.entityGuardian.func_70689_ay() * d1 * 0.1D;
        EntityLookHelper entitylookhelper = this.entityGuardian.func_70671_ap();
        double d7 = this.entityGuardian.field_70165_t + d0 / d3 * 2.0D;
        double d8 = this.entityGuardian.func_70047_e() + this.entityGuardian.field_70163_u + d1 / d3;
        double d9 = this.entityGuardian.field_70161_v + d2 / d3 * 2.0D;
        double d10 = entitylookhelper.func_180423_e();
        double d11 = entitylookhelper.func_180422_f();
        double d12 = entitylookhelper.func_180421_g();
        if (!entitylookhelper.func_180424_b()) {
          d10 = d7;
          d11 = d8;
          d12 = d9;
        } 
        this.entityGuardian.func_70671_ap().func_75650_a(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
        this.entityGuardian.func_175476_l(true);
      } else {
        if ((this.entityGuardian.func_70090_H() || this.entityGuardian.func_70644_a(MobEffects.field_188424_y)) && this.entityGuardian.field_70122_E && (this.entityGuardian.field_191988_bg != 0.0F || this.entityGuardian.field_70702_br != 0.0F))
          this.entityGuardian.func_70664_aZ(); 
        this.entityGuardian.func_70659_e(0.0F);
        this.entityGuardian.func_175476_l(false);
      } 
    }
  }
}
