package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLesserDreadbeast extends EntityTameBase implements IRangedAttackMob, Armored, Massive {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.func_187226_a(EntityLesserDreadbeast.class, DataSerializers.field_187191_a);
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 24.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.2D, true);
  
  public EntityLesserDreadbeast(World par1World) {
    super(par1World);
    func_70105_a(1.5F, 1.5F);
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 48.0F, 16.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70178_ae = true;
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 1500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityLesserDreadbeast(this.field_70170_p);
  }
  
  public boolean passesDreadPlague() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(5.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(600.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(36.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(18.0D);
    } 
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
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(CLIMBING, Byte.valueOf((byte)0));
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K)
      setBesideClimbableBlock(this.field_70123_F); 
  }
  
  protected float func_70647_i() {
    return super.func_70647_i() - 0.5F;
  }
  
  protected SoundEvent func_184639_G() {
    return ACSounds.dread_spawn_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource souce) {
    return ACSounds.dread_spawn_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.dread_spawn_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187939_hm, 0.15F, 1.0F);
  }
  
  public boolean func_70617_f_() {
    return isBesideClimbableBlock();
  }
  
  public boolean isBesideClimbableBlock() {
    return ((((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue() & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(CLIMBING)).byteValue();
    if (par1) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.field_70180_af.func_187227_b(CLIMBING, Byte.valueOf(b0));
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected Item func_146068_u() {
    return ACItems.dreaded_shard_of_abyssalnite;
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_LESSER_DREADBEAST;
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (this.field_70173_aa == 1)
      func_184185_a(ESound.amalgamate, 1.0F, 1.0F); 
    if (func_70638_az() != null)
      if (func_70068_e((Entity)func_70638_az()) > 100.0D || func_70638_az() instanceof net.minecraft.entity.EntityFlying || (func_70638_az()).field_70163_u > this.field_70163_u + 4.0D) {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiArrowAttack);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiAttackOnCollide);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      }  
    if (func_70089_S() && !this.field_70170_p.field_72995_K && this.field_70170_p.field_73012_v.nextInt(200) == 0) {
      EntityDreadSpawn spawn = new EntityDreadSpawn(this.field_70170_p);
      spawn.func_82149_j((Entity)this);
      spawn.setOwnerId(func_184753_b());
      this.field_70170_p.func_72838_d((Entity)spawn);
    } 
    if (func_70089_S() && !this.field_70170_p.field_72995_K && this.field_70170_p.field_73012_v.nextInt(10000) == 0) {
      EntityGreaterDreadSpawn spawn = new EntityGreaterDreadSpawn(this.field_70170_p);
      spawn.func_82149_j((Entity)this);
      spawn.setOwnerId(func_184753_b());
      this.field_70170_p.func_72838_d((Entity)spawn);
    } 
  }
  
  public void func_70645_a(DamageSource par1DamageSource) {
    if (!this.field_70170_p.field_72995_K) {
      EntityGreaterDreadSpawn spawn1 = new EntityGreaterDreadSpawn(this.field_70170_p);
      EntityGreaterDreadSpawn spawn2 = new EntityGreaterDreadSpawn(this.field_70170_p);
      spawn1.func_82149_j((Entity)this);
      spawn2.func_82149_j((Entity)this);
      spawn1.setOwnerId(func_184753_b());
      spawn2.setOwnerId(func_184753_b());
      this.field_70170_p.func_72838_d((Entity)spawn1);
      this.field_70170_p.func_72838_d((Entity)spawn2);
    } 
    super.func_70645_a(par1DamageSource);
  }
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    EntityDreadSlugOther entitydreadslug = new EntityDreadSlugOther(this.field_70170_p, (EntityLivingBase)this);
    double d0 = target.field_70165_t - this.field_70165_t;
    double d1 = target.field_70163_u + target.func_70047_e() - 1.100000023841858D - entitydreadslug.field_70163_u;
    double d2 = target.field_70161_v - this.field_70161_v;
    float f1 = MathHelper.func_76133_a(d0 * d0 + d2 * d2) * 0.2F;
    entitydreadslug.func_70186_c(d0, d1 + f1, d2, 1.6F, 1.0F);
    func_184185_a(ESound.amalgamate, 1.0F, 1.25F);
    this.field_70170_p.func_72838_d((Entity)entitydreadslug);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (func_70631_g_()) {
      if (hasOwner(player)) {
        player.func_184609_a(EnumHand.MAIN_HAND);
        if (func_184187_bx() == null) {
          func_184205_a((Entity)player, true);
        } else {
          func_184210_p();
        } 
      } 
      return true;
    } 
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  public double func_70042_X() {
    return this.field_70131_O * 0.5D;
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_70090_H() || func_180799_ab())
        this.field_70181_x += 0.05D; 
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * ((func_70090_H() || func_180799_ab()) ? 5.0F : 1.0F));
        super.func_191986_a(strafe, vertical, forward);
        setBesideClimbableBlock(this.field_70123_F);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (entitylivingbase.field_191988_bg > 0.0F && this.field_70173_aa % 7 == 0)
        func_180429_a(new BlockPos((Entity)this), this.field_70170_p.func_180495_p(new BlockPos((Entity)this)).func_177230_c()); 
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
