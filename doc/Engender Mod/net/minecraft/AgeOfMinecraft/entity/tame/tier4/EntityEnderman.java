package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Ender;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityEndermite;
import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EntityEnderman extends EntityTameBase implements IJumpingMount, Armored, Ender {
  private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
  
  private static final AttributeModifier attackingSpeedBoostModifier = (new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448D, 0)).func_111168_a(false);
  
  private static final Set<Block> carriableBlocks = Sets.newIdentityHashSet();
  
  private static final DataParameter<Optional<IBlockState>> CARRIED_BLOCK = EntityDataManager.func_187226_a(EntityEnderman.class, DataSerializers.field_187197_g);
  
  private int field_184720_bx = 0;
  
  private int field_184721_by = 0;
  
  public boolean andr;
  
  private static final DataParameter<Boolean> OMNI_DODGE = EntityDataManager.func_187226_a(EntityEnderman.class, DataSerializers.field_187198_h);
  
  protected float jumpPower;
  
  public EntityEnderman(World worldIn) {
    super(worldIn);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.5F, 2.75F);
    } else {
      func_70105_a(0.5F, 2.875F);
    } 
    this.field_70138_W = 1.0F;
    this.isOffensive = true;
    func_184644_a(PathNodeType.WATER, -1.0F);
    func_184644_a(PathNodeType.DANGER_FIRE, -1.0F);
    func_184644_a(PathNodeType.DANGER_CACTUS, -1.0F);
    this.field_70714_bg.func_75776_a(0, new AIPlaceBlock(this));
    this.field_70714_bg.func_75776_a(0, new AITakeBlock(this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.5D, 48.0F, 12.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.5D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.field_70728_aV = 10;
  }
  
  public String getDescName() {
    return (!EngenderConfig.loreFriendlyMode && EngenderConfig.mobs.useMobTalkerModels && this.andr) ? "andr" : "enderman";
  }
  
  public int getNextLevelRequirement() {
    return 100;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityEnderman(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
  }
  
  public int timesToConvert() {
    return 27;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSMassive() {
    return 2.5F;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      return I18n.func_74838_a("entity." + str + ".cmm.name") + (canDodgeAllAttacks() ? " (Ultra Instinct)" : "");
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.func_74838_a("entity." + s + ".name") + (canDodgeAllAttacks() ? " (Ultra Instinct)" : "");
  }
  
  public void func_70624_b(EntityLivingBase entitylivingbaseIn) {
    super.func_70624_b(entitylivingbaseIn);
    IAttributeInstance iattributeinstance = func_110148_a(SharedMonsterAttributes.field_111263_d);
    if (entitylivingbaseIn == null) {
      this.field_184721_by = 0;
      iattributeinstance.func_111124_b(attackingSpeedBoostModifier);
    } else {
      this.field_184721_by = this.field_70173_aa;
      if (!iattributeinstance.func_180374_a(attackingSpeedBoostModifier))
        iattributeinstance.func_111121_a(attackingSpeedBoostModifier); 
    } 
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(CARRIED_BLOCK, Optional.absent());
    this.field_70180_af.func_187214_a(OMNI_DODGE, Boolean.valueOf(false));
  }
  
  public EnumTier getTier() {
    return (canDodgeAllAttacks() || this.andr) ? EnumTier.TIER6 : EnumTier.TIER4;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.ENDER;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return (EntityTameBase)new EntityMutantEnderman(this.field_70170_p); 
    return null;
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1 + this.field_70146_Z.nextInt(10); i++) {
        EntityEnderman baby = new EntityEnderman(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        baby.setGrowingAge(-100000);
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
  
  public void func_184716_o() {
    if (this.field_70173_aa >= this.field_184720_bx + 400) {
      this.field_184720_bx = this.field_70173_aa;
      if (!func_174814_R())
        this.field_70170_p.func_184134_a(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v, SoundEvents.field_187533_aW, func_184176_by(), func_70093_af() ? 1.0F : 2.5F, EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F, false); 
    } 
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (isArmsRaised() && this.field_70170_p.field_72995_K)
      func_184716_o(); 
    super.func_184206_a(key);
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    IBlockState iblockstate = getHeldBlockState();
    tagCompound.func_74757_a("Andrea", this.andr);
    tagCompound.func_74757_a("OmniDodge", canDodgeAllAttacks());
    if (iblockstate != null) {
      tagCompound.func_74777_a("carried", (short)Block.func_149682_b(iblockstate.func_177230_c()));
      tagCompound.func_74777_a("carriedData", (short)iblockstate.func_177230_c().func_176201_c(iblockstate));
    } 
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    IBlockState iblockstate;
    super.func_70037_a(tagCompund);
    setDodgeAllAttacks(Boolean.valueOf(tagCompund.func_74767_n("OmniDodge")).booleanValue());
    this.andr = tagCompund.func_74767_n("Andrea");
    if (tagCompund.func_150297_b("carried", 8)) {
      iblockstate = Block.func_149684_b(tagCompund.func_74779_i("carried")).func_176203_a(tagCompund.func_74765_d("carriedData") & 0xFFFF);
    } else {
      iblockstate = Block.func_149729_e(tagCompund.func_74765_d("carried")).func_176203_a(tagCompund.func_74765_d("carriedData") & 0xFFFF);
    } 
    if (iblockstate == null || iblockstate.func_177230_c() == null || iblockstate.func_185904_a() == Material.field_151579_a)
      iblockstate = null; 
    setHeldBlockState(iblockstate);
  }
  
  public boolean canDodgeAllAttacks() {
    return ((Boolean)this.field_70180_af.func_187225_a(OMNI_DODGE)).booleanValue();
  }
  
  public void setDodgeAllAttacks(boolean powered) {
    this.field_70180_af.func_187227_b(OMNI_DODGE, Boolean.valueOf(powered));
  }
  
  protected float func_70647_i() {
    return this.andr ? (super.func_70647_i() - 0.25F) : (EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.25F) : super.func_70647_i());
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.88F) : (this.field_70131_O * 0.89F);
  }
  
  public void performSpecialAttack() {
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(128.0D, 128.0D, 128.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
    if (list != null && !list.isEmpty())
      for (int i1 = 0; i1 < list.size(); i1++) {
        EntityLivingBase entity = list.get(i1);
        if (entity != null)
          if (!func_184191_r((Entity)entity)) {
            teleportTo(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
            teleportRandomly();
            func_70652_k((Entity)entity);
          }  
      }  
    setSpecialAttackTimer(this.andr ? 20 : 1200);
  }
  
  public void func_96094_a(String name) {
    super.func_96094_a(name);
    if (!EngenderConfig.loreFriendlyMode && EngenderConfig.mobs.useMobTalkerModels && !this.andr && "Andr".equals(name)) {
      this.field_70173_aa = 0;
      becomeAHero();
      this.andr = true;
    } else {
      this.andr = false;
    } 
  }
  
  public void func_70636_d() {
    ItemStack block = (getHeldBlockState() != null) ? new ItemStack(getHeldBlockState().func_177230_c()) : ItemStack.field_190927_a;
    this.basicInventory.func_70299_a(7, block);
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az() instanceof EntityWitherStorm && this.field_70146_Z.nextInt(100) == 0) {
      teleportTo((func_70638_az()).field_70165_t, (func_70638_az()).field_70163_u, (func_70638_az()).field_70161_v);
      (func_70638_az()).field_70181_x = -0.1D;
      ((EntityWitherStorm)func_70638_az()).Grow(((EntityWitherStorm)func_70638_az()).getSize() - 2);
      (func_70638_az()).field_70172_ad = 0;
      setHeldBlockState(Blocks.field_150343_Z.func_176223_P());
      func_70652_k((Entity)func_70638_az());
    } 
    if (getLevel() >= 300 && getStrength() >= 100.0F && getStamina() >= 100.0F && getIntelligence() >= 100.0F && getDexterity() >= 100.0F && getAgility() >= 100.0F)
      setDodgeAllAttacks(true); 
    if (func_70638_az() != null && canDodgeAllAttacks() && func_70661_as().func_75500_f())
      teleportToEntity((Entity)func_70638_az()); 
    if (canDodgeAllAttacks()) {
      func_70674_bp();
      func_70066_B();
      if (getEnergy() <= 0.0F) {
        func_70606_j(0.0F);
        func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i());
      } 
    } 
    if (EngenderConfig.mobs.useMobTalkerModels && !isHero() && this.andr)
      becomeAHero(); 
    if (func_70638_az() != null && func_70638_az().func_70089_S() && func_70068_e((Entity)func_70638_az()) < 512.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (func_70026_G() && !this.andr && this.field_70172_ad <= 10)
      func_70097_a((new DamageSource("waterburn")).func_76361_j().func_76348_h(), 2.0F); 
    if (func_70638_az() != null) {
      if (this.field_70173_aa % 400 == 0)
        func_184185_a(SoundEvents.field_187533_aW, func_70093_af() ? 1.0F : 2.5F, EngenderConfig.mobs.useMobTalkerModels ? 1.25F : 1.0F); 
      if ((func_70638_az()).field_70131_O <= 2.25F && func_70638_az().func_184222_aU() && func_70638_az() instanceof EntityLiving && !(func_70638_az() instanceof EntityTameBase)) {
        ((EntityLiving)func_70638_az()).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 60, 9));
        ((EntityLiving)func_70638_az()).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 60, 0));
        ((EntityLiving)func_70638_az()).func_70671_ap().func_75650_a((func_70638_az()).field_70165_t + this.field_70146_Z.nextDouble() * 60.0D - 30.0D, (func_70638_az()).field_70163_u + this.field_70146_Z.nextDouble() * 60.0D - 30.0D, (func_70638_az()).field_70161_v + this.field_70146_Z.nextDouble() * 60.0D - 30.0D, 180.0F, 180.0F);
        ((EntityLiving)func_70638_az()).func_70624_b((EntityLivingBase)null);
        (func_70638_az()).field_70761_aq = (func_70638_az()).field_70177_z = (func_70638_az()).field_70759_as;
        ((EntityLiving)func_70638_az()).field_70715_bh.field_75782_a.clear();
      } 
      if (canDodgeAllAttacks() && getEnergy() > 20.0F && this.field_70146_Z.nextInt(80) == 0) {
        (func_70638_az()).field_70172_ad = 0;
        teleportRandomly();
        this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
        setEnergy(getEnergy() - 0.01F);
        func_70652_k((Entity)func_70638_az());
      } 
    } 
    if (this.field_70170_p.field_72995_K && func_70089_S()) {
      int i;
      for (i = 0; i < 2; i++) {
        this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, -this.field_70146_Z.nextDouble(), (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D, new int[0]);
        if (canDodgeAllAttacks()) {
          this.field_70170_p.func_175688_a(EnumParticleTypes.DRAGON_BREATH, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.ENCHANTMENT_TABLE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.FIREWORKS_SPARK, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.CLOUD, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
          this.field_70170_p.func_175688_a(EnumParticleTypes.END_ROD, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O - 0.25D, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]);
        } 
      } 
      for (i = 0; i < 2; i++) {
        double d0 = (float)this.field_70165_t - 0.5D + this.field_70146_Z.nextFloat();
        double d1 = ((float)this.field_70163_u + this.field_70146_Z.nextFloat());
        double d2 = (float)this.field_70161_v - 0.5D + this.field_70146_Z.nextFloat();
        double d3 = (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D;
        double d4 = -this.field_70146_Z.nextDouble();
        double d5 = (this.field_70146_Z.nextDouble() - 0.5D) * 2.0D;
        d1 += this.field_70146_Z.nextDouble() * this.field_70131_O;
        this.field_70170_p.func_175688_a(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
      } 
    } 
    if (func_70089_S() && !func_184207_aI() && getGuardBlock() == null && getOwner() != null && (func_70068_e((Entity)getOwner()) > 4096.0D || !func_70685_l((Entity)getOwner())) && !this.field_70170_p.field_72995_K) {
      func_70624_b((EntityLivingBase)null);
      func_70661_as().func_75499_g();
      teleportTo((getOwner()).field_70165_t, (getOwner()).field_70163_u, (getOwner()).field_70161_v);
    } 
    if (func_70089_S() && !func_184207_aI() && func_70643_av() != null && func_70681_au().nextInt(20) == 0) {
      if (func_70643_av().func_70068_e((Entity)this) < 2.0D && !this.field_70170_p.field_72995_K)
        teleportRandomly(); 
      if (func_70643_av().func_70068_e((Entity)this) > 128.0D && !this.field_70170_p.field_72995_K)
        teleportToEntity((Entity)func_70643_av()); 
    } 
    super.func_70636_d();
  }
  
  protected void func_70619_bc() {
    if (!EngenderConfig.mobs.useMobTalkerModels && this.andr)
      this.andr = false; 
    if (func_70089_S() && func_70026_G() && !this.andr) {
      func_70015_d(10);
      teleportRandomly();
    } 
    super.func_70619_bc();
  }
  
  protected boolean teleportRandomly() {
    if (this.field_70170_p.func_72935_r() || func_70026_G())
      func_70642_aH(); 
    double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 32.0D;
    double d1 = this.field_70163_u + (this.field_70146_Z.nextInt(64) - 32);
    double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 32.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportToEntity(Entity p_70816_1_) {
    Vec3d vec3d = new Vec3d(this.field_70165_t - p_70816_1_.field_70165_t, (func_174813_aQ()).field_72338_b + (this.field_70131_O / 2.0F) - p_70816_1_.field_70163_u + p_70816_1_.func_70047_e(), this.field_70161_v - p_70816_1_.field_70161_v);
    vec3d = vec3d.func_72432_b();
    double d0 = 16.0D;
    double d1 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72450_a * d0;
    double d2 = this.field_70163_u + (this.field_70146_Z.nextInt(16) - 8) - vec3d.field_72448_b * d0;
    double d3 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 8.0D - vec3d.field_72449_c * d0;
    return teleportTo(d1, d2, d3);
  }
  
  protected boolean teleportTo(double x, double y, double z) {
    EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, x, y, z, 0.0F);
    if (MinecraftForge.EVENT_BUS.post((Event)event))
      return false; 
    boolean flag = (func_184595_k(event.getTargetX(), event.getTargetY(), event.getTargetZ()) && !isInLove() && !func_184218_aH());
    if (flag || canDodgeAllAttacks()) {
      this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70169_q, this.field_70167_r, this.field_70166_s, SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
      func_184185_a(SoundEvents.field_187534_aX, 1.0F, 1.0F);
      if (!this.field_70170_p.field_72995_K && this.field_70146_Z.nextFloat() < 0.01F) {
        EntityEndermite entityendermite = new EntityEndermite(this.field_70170_p);
        entityendermite.setOwnerId(func_184753_b());
        entityendermite.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
        this.field_70170_p.func_72838_d((Entity)entityendermite);
      } 
      return true;
    } 
    return flag;
  }
  
  public boolean func_184595_k(double x, double y, double z) {
    double d0 = this.field_70165_t;
    double d1 = this.field_70163_u;
    double d2 = this.field_70161_v;
    this.field_70165_t = x;
    this.field_70163_u = y;
    this.field_70161_v = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos((Entity)this);
    World world = this.field_70170_p;
    Random random = func_70681_au();
    if (world.func_175667_e(blockpos)) {
      boolean flag1 = false;
      while (!flag1 && blockpos.func_177956_o() > 0) {
        BlockPos blockpos1 = blockpos.func_177977_b();
        IBlockState iblockstate = world.func_180495_p(blockpos1);
        if (iblockstate.func_185904_a().func_76230_c()) {
          flag1 = true;
          continue;
        } 
        this.field_70163_u--;
        blockpos = blockpos1;
      } 
      if (flag1) {
        func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (func_184207_aI())
          func_184179_bs().func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v); 
        if (world.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !world.func_72953_d(func_174813_aQ()))
          flag = true; 
      } 
    } 
    if (!flag) {
      func_70634_a(d0, d1, d2);
      return false;
    } 
    int i = 128;
    for (int j = 0; j < 128; j++) {
      double d6 = j / 127.0D;
      float f = (random.nextFloat() - 0.5F) * 0.2F;
      float f1 = (random.nextFloat() - 0.5F) * 0.2F;
      float f2 = (random.nextFloat() - 0.5F) * 0.2F;
      double d3 = d0 + (this.field_70165_t - d0) * d6 + (random.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      double d4 = d1 + (this.field_70163_u - d1) * d6 + random.nextDouble() * this.field_70131_O;
      double d5 = d2 + (this.field_70161_v - d2) * d6 + (random.nextDouble() - 0.5D) * this.field_70130_N * 2.0D;
      world.func_175688_a(EnumParticleTypes.PORTAL, d3, d4, d5, f, f1, f2, new int[0]);
    } 
    if (this instanceof EntityCreature)
      func_70661_as().func_75499_g(); 
    return true;
  }
  
  protected SoundEvent func_184639_G() {
    return isArmsRaised() ? SoundEvents.field_187532_aV : SoundEvents.field_187529_aS;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i()); 
    return SoundEvents.field_187531_aU;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i()); 
    return SoundEvents.field_187530_aT;
  }
  
  protected Item func_146068_u() {
    return Items.field_151079_bi;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      IBlockState iblockstate = getHeldBlockState();
      if (hasOwner(player) && iblockstate != null) {
        if (!this.field_70170_p.field_72995_K)
          func_70099_a(new ItemStack(iblockstate.func_177230_c(), 1, iblockstate.func_177230_c().func_176201_c(iblockstate)), 0.0F); 
        setHeldBlockState((IBlockState)null);
      } else if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K) {
        player.func_184220_m((Entity)this);
      } 
      return true;
    } 
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151061_bv && (hasOwner(player) || player.func_184191_r((Entity)this))) {
      List<EntityTameBase> list = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_186662_g(256.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty() && !func_184207_aI())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null)
            if (func_184191_r((Entity)entity)) {
              this.field_70170_p.func_184133_a((EntityPlayer)null, entity.func_180425_c(), SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
              entity.func_184204_a(1);
            }  
        }  
      this.field_70170_p.func_184133_a((EntityPlayer)null, func_180425_c(), SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
      this.field_70170_p.func_184133_a((EntityPlayer)null, player.func_180425_c(), SoundEvents.field_187534_aX, func_184176_by(), 1.0F, 1.0F);
      func_184204_a(1);
      player.func_184204_a(1);
      return true;
    } 
    return false;
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      double d8 = 0.5D;
      Vec3d vec3 = func_70676_i(1.0F);
      double dx = vec3.field_72450_a * d8;
      double dz = vec3.field_72449_c * d8;
      passenger.func_70107_b(this.field_70165_t + dx, this.field_70163_u + 0.25D, this.field_70161_v + dz);
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
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = this.field_70759_as = entitylivingbase.field_70177_z;
      this.field_70125_A = 0.0F;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br;
      forward = entitylivingbase.field_191988_bg;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e() * ((EngenderConfig.mobs.useMobTalkerModels && this.andr) ? 15.0F : 1.5F));
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      if (EngenderConfig.mobs.useMobTalkerModels && this.andr) {
        this.field_70747_aH = 1.0F;
      } else {
        this.field_70747_aH = 0.02F;
      } 
      if (this.jumpPower > 0.0F && this.field_70122_E) {
        this.field_70181_x = this.jumpPower * ((EngenderConfig.mobs.useMobTalkerModels && this.andr) ? 25 : true) * getFittness();
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
  
  protected void func_82160_b(boolean wasRecentlyHit, int lootingModifier) {
    super.func_82160_b(wasRecentlyHit, lootingModifier);
    IBlockState iblockstate = getHeldBlockState();
    if (iblockstate != null) {
      Item item = Item.func_150898_a(iblockstate.func_177230_c());
      int i = item.func_77614_k() ? iblockstate.func_177230_c().func_176201_c(iblockstate) : 0;
      func_70099_a(new ItemStack(item, 1, i), 0.0F);
    } 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_ENDERMAN;
  }
  
  public void setHeldBlockState(IBlockState state) {
    this.field_70180_af.func_187227_b(CARRIED_BLOCK, Optional.fromNullable(state));
  }
  
  public IBlockState getHeldBlockState() {
    return (IBlockState)((Optional)this.field_70180_af.func_187225_a(CARRIED_BLOCK)).orNull();
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    Entity entity = source.func_76346_g();
    if (canDodgeAllAttacks() && getEnergy() > 0.0F) {
      teleportRandomly();
      this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
      setEnergy(getEnergy() - 3.0F);
      if (entity instanceof EntityLivingBase && !func_184191_r(entity))
        func_70652_k(entity); 
      return false;
    } 
    if (this.andr && source instanceof net.minecraft.util.EntityDamageSourceIndirect)
      return false; 
    if (entity instanceof EntityLivingBase && EngenderConfig.mobs.useMobTalkerModels && this.andr && amount < 50.0F) {
      EntityLivingBase creature = (EntityLivingBase)entity;
      creature.func_70097_a(DamageSource.field_76377_j.func_76348_h().func_76359_i().func_151518_m(), amount);
      creature.func_70653_a((Entity)this, amount * 0.1F, -MathHelper.func_76126_a(creature.field_70759_as * 0.017453292F), MathHelper.func_76134_b(creature.field_70759_as * 0.017453292F));
    } 
    if (this.andr && (source.func_76347_k() || source.func_94541_c() || source.func_76352_a() || source.func_82725_o() || amount < 50.0F))
      return false; 
    if (EngenderConfig.mobs.useMobTalkerModels && amount >= 1.0F && this.andr)
      amount *= 1.0E-4F; 
    setSitResting(false);
    if (func_70089_S() && !this.andr && (func_180431_b(source) || source.func_76346_g() instanceof net.minecraft.entity.monster.EntityCreeper || source instanceof net.minecraft.util.EntityDamageSourceIndirect || source.func_94541_c() || source.func_76352_a())) {
      if (teleportRandomly())
        return true; 
      return false;
    } 
    boolean flag = super.func_70097_a(source, amount);
    if (func_70089_S() && source.func_76363_c() && !this.andr)
      teleportRandomly(); 
    return flag;
  }
  
  public static void setCarriable(Block block, boolean canCarry) {
    if (canCarry) {
      carriableBlocks.add(block);
    } else {
      carriableBlocks.remove(block);
    } 
  }
  
  public static boolean getCarriable(Block block) {
    return carriableBlocks.contains(block);
  }
  
  static {
    carriableBlocks.add(Blocks.field_150349_c);
    carriableBlocks.add(Blocks.field_150346_d);
    carriableBlocks.add(Blocks.field_150354_m);
    carriableBlocks.add(Blocks.field_150322_A);
    carriableBlocks.add(Blocks.field_180395_cM);
    carriableBlocks.add(Blocks.field_150322_A);
    carriableBlocks.add(Blocks.field_150406_ce);
    carriableBlocks.add(Blocks.field_150405_ch);
    carriableBlocks.add(Blocks.field_150407_cf);
    carriableBlocks.add(Blocks.field_150351_n);
    carriableBlocks.add(Blocks.field_150328_O);
    carriableBlocks.add(Blocks.field_150327_N);
    carriableBlocks.add(Blocks.field_150338_P);
    carriableBlocks.add(Blocks.field_150337_Q);
    carriableBlocks.add(Blocks.field_150344_f);
    carriableBlocks.add(Blocks.field_150364_r);
    carriableBlocks.add(Blocks.field_150363_s);
    carriableBlocks.add(Blocks.field_185766_cS);
    carriableBlocks.add(Blocks.field_185765_cR);
    carriableBlocks.add(Blocks.field_150335_W);
    carriableBlocks.add(Blocks.field_150434_aF);
    carriableBlocks.add(Blocks.field_150435_aG);
    carriableBlocks.add(Blocks.field_150423_aK);
    carriableBlocks.add(Blocks.field_150440_ba);
    carriableBlocks.add(Blocks.field_189880_di);
    carriableBlocks.add(Blocks.field_189878_dg);
    carriableBlocks.add(Blocks.field_150385_bj);
    carriableBlocks.add(Blocks.field_189877_df);
    carriableBlocks.add(Blocks.field_150391_bh);
    carriableBlocks.add(Blocks.field_150424_aL);
    carriableBlocks.add(Blocks.field_150447_bR);
    carriableBlocks.add(Blocks.field_150479_bC);
    carriableBlocks.add(Blocks.field_150473_bD);
    carriableBlocks.add(Blocks.field_150404_cg);
    carriableBlocks.add(Blocks.field_150432_aD);
    carriableBlocks.add(Blocks.field_185778_de);
    carriableBlocks.add(Blocks.field_150367_z);
    carriableBlocks.add(Blocks.field_189879_dh);
    carriableBlocks.add(Blocks.field_150359_w);
    carriableBlocks.add(Blocks.field_150410_aZ);
    carriableBlocks.add(Blocks.field_150426_aN);
    carriableBlocks.add(Blocks.field_150425_aM);
    carriableBlocks.add(Blocks.field_150362_t);
    carriableBlocks.add(Blocks.field_150361_u);
    carriableBlocks.add(Blocks.field_150403_cj);
    carriableBlocks.add(Blocks.field_150365_q);
    carriableBlocks.add(Blocks.field_150366_p);
    carriableBlocks.add(Blocks.field_150369_x);
    carriableBlocks.add(Blocks.field_150450_ax);
    carriableBlocks.add(Blocks.field_150352_o);
    carriableBlocks.add(Blocks.field_150482_ag);
    carriableBlocks.add(Blocks.field_150412_bA);
  }
  
  static class AIPlaceBlock extends EntityAIBase {
    private final EntityEnderman enderman;
    
    public AIPlaceBlock(EntityEnderman p_i45843_1_) {
      this.enderman = p_i45843_1_;
    }
    
    public boolean func_75250_a() {
      return (this.enderman.getHeldBlockState() != null && (!EngenderConfig.mobs.grief || this.enderman.func_70681_au().nextInt(2000) == 0 || this.enderman.func_184218_aH()));
    }
    
    public void func_75246_d() {
      Random random = this.enderman.func_70681_au();
      World world = this.enderman.field_70170_p;
      int i = MathHelper.func_76128_c(this.enderman.field_70165_t - 1.0D + random.nextDouble() * 2.0D);
      int j = MathHelper.func_76128_c(this.enderman.field_70163_u + random.nextDouble() * 2.0D);
      int k = MathHelper.func_76128_c(this.enderman.field_70161_v - 1.0D + random.nextDouble() * 2.0D);
      BlockPos blockpos = new BlockPos(i, j, k);
      IBlockState iblockstate = world.func_180495_p(blockpos);
      IBlockState iblockstate1 = world.func_180495_p(blockpos.func_177977_b());
      IBlockState iblockstate2 = this.enderman.getHeldBlockState();
      if (iblockstate2 != null && func_188518_a(world, blockpos, iblockstate2.func_177230_c(), iblockstate, iblockstate1)) {
        this.enderman.field_70170_p.func_175718_b(2001, blockpos, Block.func_149682_b(iblockstate2.func_177230_c()));
        world.func_180501_a(blockpos, iblockstate2, 3);
        this.enderman.setHeldBlockState((IBlockState)null);
      } 
    }
    
    private boolean func_188518_a(World p_188518_1_, BlockPos p_188518_2_, Block p_188518_3_, IBlockState p_188518_4_, IBlockState p_188518_5_) {
      return (p_188518_5_.func_185904_a() == Material.field_151579_a) ? false : ((p_188518_4_.func_185904_a() != Material.field_151579_a) ? false : (!p_188518_3_.func_176196_c(p_188518_1_, p_188518_2_) ? false : p_188518_5_.func_185917_h()));
    }
  }
  
  static class AITakeBlock extends EntityAIBase {
    private final EntityEnderman enderman;
    
    public AITakeBlock(EntityEnderman p_i45841_1_) {
      this.enderman = p_i45841_1_;
    }
    
    public boolean func_75250_a() {
      return (this.enderman.getHeldBlockState() == null && !this.enderman.func_184207_aI() && (EngenderConfig.mobs.grief || (EngenderConfig.mobs.useMobTalkerModels && this.enderman.func_82150_aj())));
    }
    
    public void func_75246_d() {
      Random random = this.enderman.func_70681_au();
      World world = this.enderman.field_70170_p;
      int i = MathHelper.func_76128_c(this.enderman.field_70165_t - 3.0D + random.nextDouble() * 6.0D);
      int j = MathHelper.func_76128_c(this.enderman.field_70163_u - 1.0D + random.nextDouble() * 4.0D);
      int k = MathHelper.func_76128_c(this.enderman.field_70161_v - 3.0D + random.nextDouble() * 6.0D);
      BlockPos blockpos = new BlockPos(i, j, k);
      IBlockState iblockstate = world.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      RayTraceResult raytraceresult = world.func_147447_a(new Vec3d((MathHelper.func_76128_c(this.enderman.field_70165_t) + 0.5F), (j + 0.5F), (MathHelper.func_76128_c(this.enderman.field_70161_v) + 0.5F)), new Vec3d((i + 0.5F), (j + 0.5F), (k + 0.5F)), false, true, false);
      boolean flag = (raytraceresult != null && raytraceresult.func_178782_a().equals(blockpos));
      if (EntityEnderman.carriableBlocks.contains(block) && flag) {
        this.enderman.func_184609_a(EnumHand.MAIN_HAND);
        this.enderman.func_184609_a(EnumHand.OFF_HAND);
        this.enderman.func_70671_ap().func_75650_a(i, j, k, 180.0F, 40.0F);
        this.enderman.field_70170_p.func_175718_b(1021, blockpos, 0);
        this.enderman.field_70170_p.func_175718_b(2001, blockpos, Block.func_149682_b(block));
        this.enderman.setHeldBlockState(iblockstate);
        world.func_175698_g(blockpos);
      } 
    }
  }
}
