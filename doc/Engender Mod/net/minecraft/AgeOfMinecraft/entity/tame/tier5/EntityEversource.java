package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityZombie;
import net.minecraft.AgeOfMinecraft.registry.EItem;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EntityEversource extends EntityTameBase implements IJumpingMount, Light {
  public float wingRotation;
  
  public float destPos;
  
  public float field_70884_g;
  
  public float field_70888_h;
  
  public float wingRotDelta = 1.0F;
  
  public int timeUntilNextEgg;
  
  public boolean chickenJockey;
  
  protected float jumpPower;
  
  public EntityEversource(World worldIn) {
    super(worldIn);
    this.timeUntilNextEgg = 200 + this.field_70146_Z.nextInt(600);
    func_184644_a(PathNodeType.WATER, 0.0F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 16.0F, 8.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.5D, 80));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 50;
    func_70105_a(0.4F, 0.7F);
  }
  
  public String getDescName() {
    return "eversource";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityEversource(this.field_70170_p);
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.95F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(12.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
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
    if (!this.field_70170_p.field_72995_K && func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && !func_184191_r((Entity)func_70638_az()) && func_70068_e((Entity)func_70638_az()) < (this.field_70130_N * this.field_70130_N + (func_70638_az()).field_70130_N * (func_70638_az()).field_70130_N) + 9.0D)
      func_70652_k((Entity)func_70638_az()); 
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
      func_184185_a(ESound.createMob, func_70599_aP(), 1.0F);
      func_184185_a(SoundEvents.field_187665_Y, func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
      func_145779_a(randomSpawnItem(), 1);
      this.timeUntilNextEgg = isHero() ? (100 + this.field_70146_Z.nextInt(100)) : (200 + this.field_70146_Z.nextInt(600));
    } 
  }
  
  private Item randomSpawnItem() {
    switch (this.field_70146_Z.nextInt(42)) {
      default:
        return (Item)EItem.chickenItem;
      case 1:
        return (Item)EItem.batItem;
      case 2:
        return (Item)EItem.cowItem;
      case 3:
        return (Item)EItem.mooshroomItem;
      case 4:
        return (Item)EItem.pigItem;
      case 5:
        return (Item)EItem.rabbitItem;
      case 6:
        return (Item)EItem.sheepItem;
      case 7:
        return (Item)EItem.ozelotItem;
      case 8:
        return (Item)EItem.squidItem;
      case 9:
        return (Item)EItem.llamaItem;
      case 10:
        return (Item)EItem.villagerItem;
      case 11:
        return (Item)EItem.snowmanItem;
      case 12:
        return (Item)EItem.silverfishItem;
      case 13:
        return (Item)EItem.endermiteItem;
      case 14:
        return (Item)EItem.wolfItem;
      case 15:
        return (Item)EItem.spiderItem;
      case 16:
        return (Item)EItem.zombieItem;
      case 17:
        return (Item)EItem.skeletonItem;
      case 18:
        return (Item)EItem.polarBearItem;
      case 19:
        return (Item)EItem.slimeItem;
      case 20:
        return (Item)EItem.magmacubeItem;
      case 21:
        return (Item)EItem.vexItem;
      case 22:
        return (Item)EItem.spiderjockeyItem;
      case 23:
        return (Item)EItem.chickenjockeyItem;
      case 24:
        return (Item)EItem.blazeItem;
      case 25:
        return (Item)EItem.endermanItem;
      case 26:
        return (Item)EItem.cavespiderItem;
      case 27:
        return (Item)EItem.pigzombieItem;
      case 28:
        return (Item)EItem.guardianItem;
      case 29:
        return (Item)EItem.ghastItem;
      case 30:
        return (Item)EItem.huskItem;
      case 31:
        return (Item)EItem.shulkerItem;
      case 32:
        return (Item)EItem.strayItem;
      case 33:
        return (Item)EItem.witchItem;
      case 34:
        return (Item)EItem.vindicatorItem;
      case 35:
        return (Item)EItem.witherskeletonItem;
      case 36:
        return (Item)EItem.killerrabbitItem;
      case 37:
        return (Item)EItem.elderguardianItem;
      case 38:
        return (Item)EItem.the4horsemenItem;
      case 39:
        return (Item)EItem.evokerItem;
      case 40:
        return (Item)EItem.giantItem;
      case 41:
        break;
    } 
    return (Item)EItem.villagergolemItem;
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
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = this.jumpPower * getFittness();
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
