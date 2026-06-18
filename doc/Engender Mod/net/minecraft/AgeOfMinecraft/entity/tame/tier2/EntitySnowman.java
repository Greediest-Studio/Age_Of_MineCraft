package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSnowGolem;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtByTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAILeaderHurtTarget;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntitySnowballHarmful;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class EntitySnowman extends EntityTameBase implements IRangedAttackMob, Light, Elemental {
  private static final DataParameter<Byte> PUMPKIN_EQUIPPED = EntityDataManager.func_187226_a(EntitySnowman.class, DataSerializers.field_187191_a);
  
  public EntitySnowman(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.45F, 1.75F);
    } else {
      func_70105_a(0.7F, 1.9F);
    } 
    this.isOffensive = true;
    func_184644_a(PathNodeType.WATER, -1.0F);
    func_184644_a(PathNodeType.LAVA, -1.0F);
    func_184644_a(PathNodeType.DANGER_FIRE, -1.0F);
    func_184644_a(PathNodeType.DAMAGE_FIRE, -1.0F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 24.0F, 6.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 1.25D, 30, 16.0F));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70715_bh.func_75776_a(0, (EntityAIBase)new EntityAIFriendlyHurtByTarget((EntityCreature)this, true, new Class[0]));
    this.field_70715_bh.func_75776_a(1, (EntityAIBase)new EntityAILeaderHurtByTarget(this));
    this.field_70715_bh.func_75776_a(2, (EntityAIBase)new EntityAILeaderHurtTarget(this));
    this.field_70728_aV = 1;
  }
  
  public String getDescName() {
    return isPumpkinEquipped() ? "snowman" : "snowgolem";
  }
  
  public float getBonusVSArmored() {
    return 0.25F;
  }
  
  public float getBonusVSMassive() {
    return 0.25F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public int timesToConvert() {
    return 3;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySnowman(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(4.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(PUMPKIN_EQUIPPED, Byte.valueOf((byte)0));
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return (EntityTameBase)new EntityMutantSnowGolem(this.field_70170_p); 
    return null;
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
    if (!this.field_70170_p.field_72995_K) {
      EntitySnowman baby = new EntitySnowman(this.field_70170_p);
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
    } 
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.CONSTRUCT;
  }
  
  public void func_70636_d() {
    ItemStack charge = isPumpkinEquipped() ? new ItemStack(Blocks.field_150423_aK) : ItemStack.field_190927_a;
    charge.func_151001_c("Pumpkin Sheared");
    this.basicInventory.func_70299_a(7, charge);
    super.func_70636_d();
    if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && this.isOffensive && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()))
      if (func_70068_e((Entity)func_70638_az()) < (this.reachWidth * this.reachWidth + ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N) * ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N)) + 9.0D && (this.field_70173_aa + func_145782_y()) % 20 == 0) {
        func_70652_k((Entity)func_70638_az());
        func_184609_a(EnumHand.MAIN_HAND);
        if (!func_184592_cb().func_190926_b())
          func_184609_a(EnumHand.OFF_HAND); 
      }  
    if (!this.field_70170_p.field_72995_K) {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      if (func_70026_G() || this.field_70170_p.func_180494_b(new BlockPos(i, 0, k)).func_180626_a(new BlockPos(i, j, k)) > 1.0F)
        func_70097_a((new DamageSource("melt")).func_76361_j().func_76348_h(), 1.0F); 
      if (!EngenderConfig.mobs.grief)
        return; 
      for (int l = 0; l < 4; l++) {
        i = MathHelper.func_76128_c(this.field_70165_t + ((l % 2 * 2 - 1) * 0.25F));
        j = MathHelper.func_76128_c(this.field_70163_u);
        k = MathHelper.func_76128_c(this.field_70161_v + ((l / 2 % 2 * 2 - 1) * 0.25F));
        BlockPos blockpos = new BlockPos(i, j, k);
        if (this.field_70170_p.func_180495_p(blockpos).func_185904_a() == Material.field_151579_a && this.field_70170_p.func_180494_b(new BlockPos(i, 0, k)).func_180626_a(blockpos) < 0.8F && Blocks.field_150431_aC.func_176196_c(this.field_70170_p, blockpos))
          this.field_70170_p.func_175656_a(blockpos, Blocks.field_150431_aC.func_176223_P()); 
      } 
    } 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_SNOWMAN;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
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
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151097_aZ) {
      if (hasOwner(player) && !isPumpkinEquipped() && !this.field_70170_p.field_72995_K) {
        this.field_70170_p.func_175718_b(2001, func_180425_c().func_177984_a(), Block.func_176210_f(Blocks.field_150423_aK.func_176223_P()));
        setPumpkinEquipped(true);
        stack.func_77972_a(1, (EntityLivingBase)player);
      } 
      return true;
    } 
    return false;
  }
  
  public boolean isPumpkinEquipped() {
    return ((((Byte)this.field_70180_af.func_187225_a(PUMPKIN_EQUIPPED)).byteValue() & 0x10) != 0);
  }
  
  public void setPumpkinEquipped(boolean p_184747_1_) {
    byte b0 = ((Byte)this.field_70180_af.func_187225_a(PUMPKIN_EQUIPPED)).byteValue();
    if (p_184747_1_) {
      this.field_70180_af.func_187227_b(PUMPKIN_EQUIPPED, Byte.valueOf((byte)(b0 | 0x10)));
    } else {
      this.field_70180_af.func_187227_b(PUMPKIN_EQUIPPED, Byte.valueOf((byte)(b0 & 0xFFFFFFEF)));
    } 
  }
  
  public void func_82196_d(EntityLivingBase target, float p_82196_2_) {
    EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.field_70170_p, (EntityLivingBase)this);
    double d0 = target.field_70163_u + target.func_70047_e() - 1.15D;
    double d1 = target.field_70165_t - this.field_70165_t;
    double d2 = d0 - entitysnowball.field_70163_u;
    double d3 = target.field_70161_v - this.field_70161_v;
    float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.2F;
    entitysnowball.func_70186_c(d1, d2 + f, d3, 1.6F, isPumpkinEquipped() ? 45.0F : 1.0F);
    func_184185_a(SoundEvents.field_187805_fE, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
    this.field_70170_p.func_72838_d((Entity)entitysnowball);
    func_184609_a(EnumHand.MAIN_HAND);
    entitysnowball.damage = (target instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityBlaze || target instanceof net.minecraft.entity.monster.EntityBlaze) ? 3.0F : ((this.field_70146_Z.nextInt(3) == 0 || !(target instanceof EntityTameBase)) ? 1.0F : 0.0F);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.2F) : super.func_70647_i();
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.84F) : (this.field_70131_O * 0.89F);
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187799_fB;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187803_fD;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187801_fC;
  }
}
