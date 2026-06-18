package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityPig extends EntityTameBase implements IJumpingMount, Light, Animal {
  private static final DataParameter<Boolean> SADDLED = EntityDataManager.func_187226_a(EntityPig.class, DataSerializers.field_187198_h);
  
  protected float jumpPower;
  
  public EntityPig(World worldIn) {
    super(worldIn);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 16.0F, 4.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 1;
    func_70105_a(0.9F, 0.9F);
  }
  
  public String getDescName() {
    return getSaddled() ? "pig_saddled" : "pig";
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityPig(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float func_180484_a(BlockPos pos) {
    return (this.field_70170_p.func_180495_p(pos.func_177977_b()).func_177230_c() == this.spawnableBlock) ? 10.0F : (this.field_70170_p.func_175724_o(pos) - 0.5F);
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(400);
    func_184185_a(ESound.pigSpecial, 5.0F, func_70647_i());
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(32.0D, 32.0D, 32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
    if (list != null && !list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (entity != null)
          if (!func_184191_r((Entity)entity)) {
            entity.func_70690_d(new PotionEffect(MobEffects.field_76431_k, 200, 0));
            entity.func_70690_d(new PotionEffect(MobEffects.field_76419_f, 200, 0));
            entity.func_70690_d(new PotionEffect(MobEffects.field_76421_d, 200, 0));
          }  
      }  
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(SADDLED, Boolean.valueOf(false));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74757_a("Saddle", getSaddled());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setSaddled(tagCompund.func_74767_n("Saddle"));
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187697_dL;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187703_dN;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187700_dM;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187709_dP, 0.15F, 1.0F / getFittness());
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151141_av && hasOwner(player)) {
      setSaddled(true);
      func_184185_a(SoundEvents.field_187706_dO, 0.5F, 1.0F);
      stack.func_190918_g(1);
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public void func_70645_a(DamageSource cause) {
    super.func_70645_a(cause);
    if (!this.field_70170_p.field_72995_K)
      if (getSaddled()) {
        func_145779_a(Items.field_151141_av, 1);
        setSaddled(false);
      }  
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_PIG;
  }
  
  public boolean getSaddled() {
    return ((Boolean)this.field_70180_af.func_187225_a(SADDLED)).booleanValue();
  }
  
  public void setSaddled(boolean saddled) {
    if (saddled) {
      this.field_70180_af.func_187227_b(SADDLED, Boolean.valueOf(true));
    } else {
      this.field_70180_af.func_187227_b(SADDLED, Boolean.valueOf(false));
    } 
  }
  
  public void func_70077_a(EntityLightningBolt lightningBolt) {
    if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
      EntityPigZombie entitypigzombie = new EntityPigZombie(this.field_70170_p);
      entitypigzombie.func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.field_151010_B));
      entitypigzombie.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
      entitypigzombie.func_94061_f(func_175446_cd());
      if (func_145818_k_())
        entitypigzombie.func_96094_a(func_95999_t()); 
      if (!isWild())
        entitypigzombie.setOwnerId(func_184753_b()); 
      this.field_70170_p.func_72838_d((Entity)entitypigzombie);
      if (func_184207_aI())
        func_184179_bs().func_184220_m((Entity)entitypigzombie); 
      func_70106_y();
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
    func_70642_aH();
  }
  
  public void func_184777_r_() {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      this.field_70138_W = 1.0F;
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70759_as = entitylivingbase.field_70759_as;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (forward != 0.0F) {
        this.field_70177_z = this.field_70761_aq = this.field_70759_as;
        this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      } 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * (getSaddled() ? 1.5F : 1.0F));
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 0.7D * this.jumpPower * getFittness();
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
}
