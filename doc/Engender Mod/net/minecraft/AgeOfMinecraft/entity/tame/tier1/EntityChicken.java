package net.minecraft.AgeOfMinecraft.entity.tame.tier1;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Animal;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Tiny;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityChicken extends EntityTameBase implements IJumpingMount, Light, Tiny, Animal {
  public float wingRotation;
  
  public float destPos;
  
  public float field_70884_g;
  
  public float field_70888_h;
  
  public float wingRotDelta = 1.0F;
  
  public int timeUntilNextEgg;
  
  public boolean chickenJockey;
  
  protected float jumpPower;
  
  public EntityChicken(World worldIn) {
    super(worldIn);
    this.timeUntilNextEgg = this.field_70146_Z.nextInt(600) + 600;
    func_184644_a(PathNodeType.WATER, 0.0F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.4D, 24.0F, 8.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 2;
    func_70105_a(0.4F, 0.7F);
  }
  
  public String getDescName() {
    return "chicken";
  }
  
  public float getBonusVSArmored() {
    return 0.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public boolean canBeButchered() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityChicken(this.field_70170_p);
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.95F;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
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
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_184207_aI() && this.field_70146_Z.nextInt(100) == 0 && (func_184179_bs()).field_70131_O > getFittness() + 0.5F)
      func_70097_a(DamageSource.field_76368_d, 1.0F); 
    if (isHero() && !this.field_70170_p.field_72995_K && func_70638_az() != null) {
      (func_70638_az()).field_70172_ad = 0;
      func_70671_ap().func_75651_a((Entity)func_70638_az(), 180.0F, 40.0F);
      EntityEgg entitysnowball = new EntityEgg(this.field_70170_p, (EntityLivingBase)this);
      double d0 = (func_70638_az()).field_70163_u + func_70638_az().func_70047_e() - 1.100000023841858D;
      double d1 = (func_70638_az()).field_70165_t - this.field_70165_t;
      double d2 = d0 - entitysnowball.field_70163_u;
      double d3 = (func_70638_az()).field_70161_v - this.field_70161_v;
      float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.2F;
      entitysnowball.func_70186_c(d1, d2 + f, d3, 1.6F, 4.0F);
      func_184185_a(SoundEvents.field_187511_aA, 1.0F, 1.5F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d((Entity)entitysnowball);
    } 
    if (isChickenJockey()) {
      this.isOffensive = true;
    } else {
      this.isOffensive = false;
    } 
    if (func_184179_bs() != null && func_184179_bs() instanceof EntityZombie) {
      EntityZombie passenger = (EntityZombie)func_184179_bs();
      this.field_70761_aq = this.field_70177_z = this.field_70759_as = passenger.field_70759_as;
      this.field_70125_A = passenger.field_70125_A;
      if (passenger.func_70638_az() != null)
        func_70661_as().func_75497_a((Entity)passenger.func_70638_az(), 1.0D); 
    } 
    this.field_70888_h = this.wingRotation;
    this.field_70884_g = this.destPos;
    this.destPos = (float)(this.destPos + (this.field_70122_E ? -1 : 4) * 0.3D);
    this.destPos = MathHelper.func_76131_a(this.destPos, 0.0F, 1.0F);
    if (!this.field_70122_E && this.wingRotDelta < 1.0F)
      this.wingRotDelta = 1.0F; 
    this.wingRotDelta = (float)(this.wingRotDelta * 0.9D);
    if (!this.field_70122_E && this.field_70181_x < 0.0D && func_70089_S())
      this.field_70181_x *= 0.6D; 
    this.wingRotation += this.wingRotDelta * 2.0F;
    if (!this.field_70170_p.field_72995_K && !func_70631_g_() && !isChickenJockey() && --this.timeUntilNextEgg <= 0) {
      func_184185_a(SoundEvents.field_187665_Y, func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
      func_145779_a(Items.field_151110_aK, 1);
      this.timeUntilNextEgg = this.field_70146_Z.nextInt(600) + 600;
    } 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187660_W;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187666_Z;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187663_X;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187538_aa, 0.15F, 1.0F / getFittness());
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_CHICKEN;
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    this.chickenJockey = tagCompund.func_74767_n("IsChickenJockey");
    if (tagCompund.func_74764_b("EggLayTime"))
      this.timeUntilNextEgg = tagCompund.func_74762_e("EggLayTime"); 
  }
  
  protected int func_70693_a(EntityPlayer player) {
    return isChickenJockey() ? 10 : super.func_70693_a(player);
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74757_a("IsChickenJockey", this.chickenJockey);
    tagCompound.func_74768_a("EggLayTime", this.timeUntilNextEgg);
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.65D;
  }
  
  public void func_184232_k(Entity passenger) {
    super.func_184232_k(passenger);
    float f = MathHelper.func_76126_a(this.field_70761_aq * 0.017453292F);
    float f1 = MathHelper.func_76134_b(this.field_70761_aq * 0.017453292F);
    float f2 = 0.1F;
    float f3 = 0.0F;
    passenger.func_70107_b(this.field_70165_t + (f2 * f), this.field_70163_u + func_70042_X() + passenger.func_70033_W() + f3, this.field_70161_v - (f2 * f1));
    if (passenger instanceof EntityLivingBase)
      ((EntityLivingBase)passenger).field_70761_aq = this.field_70761_aq; 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (player.func_70093_af()) {
        List<EntityZombie> list = this.field_70170_p.func_175647_a(EntityZombie.class, func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
        if (func_184187_bx() == null && list != null && !list.isEmpty() && !func_184207_aI() && hasOwner(player))
          for (int i1 = 0; i1 < list.size(); i1++) {
            EntityZombie entity = list.get(i1);
            if (entity != null)
              if (entity.func_70631_g_() && func_184191_r((Entity)entity) && !entity.func_184218_aH() && !this.field_70170_p.field_72995_K) {
                player.func_184609_a(EnumHand.MAIN_HAND);
                entity.func_184220_m((Entity)this);
                func_184185_a(SoundEvents.field_187660_W, 1.0F, 1.5F);
                break;
              }  
          }  
        return true;
      } 
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
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
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = 0.6D * this.jumpPower * getFittness();
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
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
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
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public boolean isChickenJockey() {
    return this.chickenJockey;
  }
  
  public void setChickenJockey(boolean jockey) {
    this.chickenJockey = jockey;
  }
}
