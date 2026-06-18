package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityPigZombie extends EntityZombie implements IJumpingMount {
  private static final DataParameter<Boolean> OLDPEPIGMAN = EntityDataManager.func_187226_a(EntityPigZombie.class, DataSerializers.field_187198_h);
  
  private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
  
  private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.075D, 0)).func_111168_a(false);
  
  private int angerLevel;
  
  private int randomSoundDelay = 40;
  
  private UUID angerTargetUUID;
  
  protected float jumpPower;
  
  public EntityPigZombie(World worldIn) {
    super(worldIn);
    this.field_70178_ae = true;
    this.randomSoundDelay = 0;
    setToNotVillager();
  }
  
  public String getDescName() {
    return isOldPEPigman() ? "zombie_pigman_old" : "zombie_pigman";
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(OLDPEPIGMAN, Boolean.valueOf(false));
  }
  
  public boolean isOldPEPigman() {
    return ((Boolean)func_184212_Q().func_187225_a(OLDPEPIGMAN)).booleanValue();
  }
  
  public void setOldPEPigman(boolean childZombie) {
    func_184212_Q().func_187227_b(OLDPEPIGMAN, Boolean.valueOf(childZombie));
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityPigZombie(this.field_70170_p);
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0D);
  }
  
  protected boolean isValidLightLevel() {
    return true;
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
    return (EngenderConfig.mobs.useMobTalkerModels && this.field_70131_O >= 1.365F && !func_70631_g_());
  }
  
  public void createChild() {
    func_184185_a(ESound.girlMoan, 1.0F, func_70647_i() + 0.1F);
    int i;
    for (i = 0; i < 10; i++)
      spawnHeartParticle(); 
    if (!this.field_70170_p.field_72995_K)
      for (i = 0; i < 1 + this.field_70146_Z.nextInt(2); i++) {
        EntityPigZombie baby = new EntityPigZombie(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
        baby.setGrowingAge(-60000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(func_184753_b());
        baby.setToNotVillager();
        this.field_70170_p.func_72838_d((Entity)baby);
      }  
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.2F) : super.func_70647_i();
  }
  
  public void performSpecialAttack() {
    func_70638_az().func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e() * 5.0F);
    func_184185_a(ESound.pigmanSpecial, func_70599_aP(), 1.0F);
    setSpecialAttackTimer(500);
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 4.0D && this.field_70173_aa % 1 == 0 && this.moralRaisedTimer > 200)
      func_70652_k((Entity)func_70638_az()); 
    if (func_70638_az() != null && func_70638_az().func_70089_S() && func_70068_e((Entity)func_70638_az()) < (func_70638_az()).field_70130_N + 9.0D && getSpecialAttackTimer() <= 0 && isHero()) {
      func_70664_aZ();
      (func_70638_az()).field_70181_x += 2.0D;
      func_184185_a(ESound.pigmanSpecial, func_70599_aP(), 1.0F);
      performSpecialAttack();
    } 
  }
  
  protected void func_70619_bc() {
    IAttributeInstance iattributeinstance = func_110148_a(SharedMonsterAttributes.field_111263_d);
    if (isAngry()) {
      if (!iattributeinstance.func_180374_a(ATTACK_SPEED_BOOST_MODIFIER)) {
        func_70031_b(true);
        iattributeinstance.func_111121_a(ATTACK_SPEED_BOOST_MODIFIER);
      } 
      this.angerLevel--;
    } else if (iattributeinstance.func_180374_a(ATTACK_SPEED_BOOST_MODIFIER)) {
      iattributeinstance.func_111124_b(ATTACK_SPEED_BOOST_MODIFIER);
    } 
    if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0)
      func_184185_a(SoundEvents.field_187936_hj, 10000.0F, func_70647_i() + 1.8F); 
    if (this.moralRaisedTimer > 200) {
      func_70690_d(new PotionEffect(MobEffects.field_76424_c, 20, 9));
      this.angerLevel = 600;
      func_184185_a(SoundEvents.field_187936_hj, 1.0F, func_70647_i() + 1.8F);
      this.field_70172_ad = 0;
      this.field_70721_aZ *= 6.0F;
    } 
    super.func_70619_bc();
  }
  
  public boolean func_70058_J() {
    return (this.field_70170_p.func_72917_a(func_174813_aQ(), (Entity)this) && this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(func_174813_aQ()));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74777_a("Anger", (short)this.angerLevel);
    tagCompound.func_74757_a("OldPEPigman", isOldPEPigman());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    this.angerLevel = tagCompund.func_74765_d("Anger");
    setOldPEPigman(tagCompund.func_74767_n("OldPEPigman"));
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (func_180431_b(source))
      return false; 
    Entity entity = source.func_76346_g();
    if (entity instanceof EntityLivingBase)
      becomeAngryAt(entity); 
    return super.func_70097_a(source, amount);
  }
  
  public void becomeAngryAt(Entity p_70835_1_) {
    this.angerLevel = 600;
    this.randomSoundDelay = this.field_70146_Z.nextInt(150);
    if (p_70835_1_ instanceof EntityLivingBase && !func_184191_r(p_70835_1_))
      func_70624_b((EntityLivingBase)p_70835_1_); 
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
    func_70642_aH();
  }
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      this.field_70138_W = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof net.minecraft.entity.player.EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 0.8D * this.jumpPower;
        if (func_70644_a(MobEffects.field_76430_j))
          this.field_70181_x += ((func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1F); 
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
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public boolean isAngry() {
    return (this.angerLevel > 0);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187935_hi;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187938_hl;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187937_hk;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_ZOMBIE_PIGMAN;
  }
  
  protected void func_180481_a(DifficultyInstance difficulty) {
    func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151010_B));
    if (func_70681_au().nextInt(3) == 0) {
      int i = this.field_70146_Z.nextInt(3);
      if (i == 0) {
        func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_185159_cQ));
      } else {
        func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.field_151010_B));
      } 
    } 
    if (this.field_70146_Z.nextFloat() < 0.25F * difficulty.func_180170_c()) {
      int i = this.field_70146_Z.nextInt(2);
      float f = (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.325F : 0.25F;
      if (this.field_70146_Z.nextFloat() < 0.1F)
        i++; 
      if (this.field_70146_Z.nextFloat() < 0.15F)
        i++; 
      if (this.field_70146_Z.nextFloat() < 0.2F)
        i++; 
      boolean flag = true;
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
          ItemStack itemstack = func_184582_a(entityequipmentslot);
          if (!flag && this.field_70146_Z.nextFloat() < f)
            break; 
          flag = false;
          if (itemstack.func_190926_b()) {
            Item item = func_184636_a(entityequipmentslot, i);
            if (item != null)
              func_184201_a(entityequipmentslot, new ItemStack(item)); 
          } 
        } 
      } 
    } 
  }
  
  @Nullable
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    super.func_180482_a(difficulty, livingdata);
    setToNotVillager();
    if (!this.field_70170_p.field_72995_K && this.field_70170_p.field_73012_v.nextInt(4) == 0)
      setOldPEPigman(true); 
    return livingdata;
  }
}
