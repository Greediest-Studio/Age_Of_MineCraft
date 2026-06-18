package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
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
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityLesserShoggoth extends EntityTameBase implements Armored, Undead {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.func_187226_a(EntityLesserShoggoth.class, DataSerializers.field_187191_a);
  
  private static final DataParameter<Integer> TYPE = EntityDataManager.func_187226_a(EntityLesserShoggoth.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> FOOD = EntityDataManager.func_187226_a(EntityLesserShoggoth.class, DataSerializers.field_187192_b);
  
  private int monolithTimer;
  
  private float shoggothWidth = -1.0F;
  
  private float shoggothHeight;
  
  public EntityLesserShoggoth(World par1World) {
    super(par1World);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    func_70105_a(1.9F, 2.35F);
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 400;
  }
  
  public float getBonusVSLight() {
    return 2.0F;
  }
  
  public float getBonusVSArmored() {
    return 0.75F;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityLesserShoggoth(this.field_70170_p);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return (getShoggothType() == 1) ? true : super.isEntityImmuneToCoralium();
  }
  
  public boolean isEntityImmuneToDread() {
    return (getShoggothType() == 2) ? true : super.isEntityImmuneToDread();
  }
  
  public boolean passesCoraliumPlague() {
    return (getShoggothType() == 1);
  }
  
  public boolean passesDreadPlague() {
    return (getShoggothType() == 2);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(16.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
    } 
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(TYPE, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(FOOD, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(CLIMBING, Byte.valueOf((byte)0));
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K)
      setBesideClimbableBlock(this.field_70123_F); 
  }
  
  public int getShoggothType() {
    return ((Integer)this.field_70180_af.func_187225_a(TYPE)).intValue();
  }
  
  public void setShoggothType(int par1) {
    this.field_70180_af.func_187227_b(TYPE, Integer.valueOf(par1));
  }
  
  public void setFoodLevel(int par1) {
    this.field_70180_af.func_187227_b(FOOD, Integer.valueOf(par1));
  }
  
  public int getFoodLevel() {
    return ((Integer)this.field_70180_af.func_187225_a(FOOD)).intValue();
  }
  
  public void feed() {
    int food = getFoodLevel() + 1;
    this.field_70180_af.func_187227_b(FOOD, Integer.valueOf(food));
    setGrowingAge(getGrowingAge() + 4000);
    if (getFittness() < 1.5F && this.field_70146_Z.nextInt(10) == 0)
      setFittness(getFittness() + 0.05F); 
    if (getStrength() < 100.0F && this.field_70146_Z.nextInt(10) == 0)
      setStrength(getStrength() + 0.05F); 
    if (getStamina() < 100.0F && this.field_70146_Z.nextInt(10) == 0)
      setStamina(getStamina() + 0.05F); 
    if (getAgility() < 100.0F && this.field_70146_Z.nextInt(10) == 0)
      setAgility(getAgility() + 0.05F); 
    if (getDexterity() < 100.0F && this.field_70146_Z.nextInt(10) == 0)
      setDexterity(getDexterity() + 0.05F); 
  }
  
  public boolean func_70617_f_() {
    return isBesideClimbableBlock();
  }
  
  public void reduceMonolithTimer() {
    if (this.monolithTimer - 100 >= 100) {
      this.monolithTimer -= 100;
    } else {
      this.monolithTimer = 0;
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    this.monolithTimer++;
    if (getFoodLevel() == 3 && !this.field_70170_p.field_72995_K) {
      setFoodLevel(0);
      if (!func_70631_g_()) {
        EntityLesserShoggoth shoggoth = (EntityLesserShoggoth)spawnBaby(this);
        shoggoth.func_82149_j((Entity)this);
        shoggoth.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v)), (IEntityLivingData)null);
        shoggoth.setGrowingAge(-24000);
        shoggoth.setOwnerId(func_184753_b());
        shoggoth.setShoggothType(getShoggothType());
        this.field_70170_p.func_72838_d((Entity)shoggoth);
        func_184185_a(SoundEvents.field_187665_Y, 1.0F, (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
      } 
    } 
    if (!this.field_70170_p.field_72995_K && !func_82150_aj())
      for (int l = 0; l < 1; l++) {
        int x = MathHelper.func_76128_c(this.field_70165_t + ((l % 2 * 2 - 1) * 0.25F));
        int y = MathHelper.func_76128_c(this.field_70163_u);
        int z = MathHelper.func_76128_c(this.field_70161_v + ((l / 2 % 2 * 2 - 1) * 0.25F));
        spawnOoze(x, y, z);
        if (!func_70631_g_()) {
          spawnOoze(x - 1, y, z);
          spawnOoze(x, y, z - 1);
          spawnOoze(x - 1, y, z - 1);
        } 
      }  
    if (this.monolithTimer >= 200 && !func_82150_aj()) {
      this.monolithTimer = 0;
      if (this.field_70170_p.func_72872_a(getClass(), func_174813_aQ().func_186662_g(32.0D)).size() > 5 && !func_70631_g_()) {
        for (EntityLesserShoggoth shoggoth : this.field_70170_p.func_72872_a(getClass(), func_174813_aQ().func_186662_g(16.0D)))
          shoggoth.reduceMonolithTimer(); 
        if (!this.field_70170_p.field_72995_K)
          (new WorldGenShoggothMonolith()).func_180709_b(this.field_70170_p, this.field_70146_Z, new BlockPos(MathHelper.func_76128_c(this.field_70165_t) + 3, MathHelper.func_76128_c(this.field_70163_u), MathHelper.func_76128_c(this.field_70161_v) + 3)); 
      } 
    } 
    for (int i = 0; i < 2 && getShoggothType() == 4 && !func_82150_aj() && ACConfig.particleEntity && this.field_70170_p.field_73011_w.getDimension() != ACLib.dark_realm_id; i++)
      this.field_70170_p.func_175688_a(EnumParticleTypes.SMOKE_LARGE, this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * this.field_70131_O, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * this.field_70130_N, 0.0D, 0.0D, 0.0D, new int[0]); 
  }
  
  private void spawnOoze(int x, int y, int z) {
    BlockPos pos = new BlockPos(x, y, z);
    if (ACConfig.shoggothOoze)
      if ((this.field_70170_p.func_180495_p(pos).func_185904_a() == Material.field_151579_a || this.field_70170_p.func_180495_p(pos).func_177230_c().func_176200_f((IBlockAccess)this.field_70170_p, pos)) && ACBlocks.shoggoth_ooze.func_176196_c(this.field_70170_p, pos) && this.field_70170_p
        .func_180495_p(pos).func_177230_c() != ACBlocks.shoggoth_ooze && !this.field_70170_p.func_180495_p(pos).func_185904_a().func_76224_d()) {
        this.field_70170_p.func_175656_a(pos, ACBlocks.shoggoth_ooze.func_176223_P());
      } else if (this.field_70170_p.func_180495_p(pos).func_177230_c() == ACBlocks.shoggoth_ooze && ((Integer)this.field_70170_p.func_180495_p(pos).func_177229_b((IProperty)BlockShoggothOoze.LAYERS)).intValue() < 8 && this.field_70173_aa % 10 == 0 && this.field_70146_Z
        .nextInt(5) == 0) {
        IBlockState state = this.field_70170_p.func_180495_p(pos);
        this.field_70170_p.func_175656_a(pos, state.func_177226_a((IProperty)BlockShoggothOoze.LAYERS, Integer.valueOf(((Integer)state.func_177229_b((IProperty)BlockShoggothOoze.LAYERS)).intValue() + 1)));
      }  
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
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase)
      func_184581_c(null); 
    switch (getShoggothType()) {
      case 1:
        if (!EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
          ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100)); 
        break;
      case 2:
        if (!EntityUtil.isEntityDread((EntityLivingBase)par1Entity))
          ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.dread_plague, 100)); 
        break;
      case 3:
        ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 100));
        break;
      case 4:
        ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 100));
        if (!func_82150_aj() && this.field_70146_Z.nextInt(5) == 0) {
          func_184185_a(ACSounds.shadow_death, 1.0F, func_70647_i());
          func_70690_d(new PotionEffect(MobEffects.field_76441_p, 800));
        } 
        if (func_82150_aj())
          ((EntityLivingBase)par1Entity).field_70172_ad = 0; 
        break;
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.func_76352_a()) {
      func_184185_a(SoundEvents.field_189110_fE, func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / 0.8F);
      return false;
    } 
    if (par1DamageSource == DamageSource.field_76367_g)
      return false; 
    return super.func_70097_a(par1DamageSource, par2);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected SoundEvent func_184639_G() {
    return ACSounds.shoggoth_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource souce) {
    return ACSounds.shoggoth_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.shoggoth_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    if (!func_82150_aj())
      func_184185_a(SoundEvents.field_187823_fN, 0.15F, 1.0F); 
  }
  
  protected void func_70628_a(boolean par1, int par2) {
    ItemStack item = new ItemStack(ACItems.shoggoth_flesh, 1, getShoggothType());
    if (item != null) {
      int i = this.field_70146_Z.nextInt(3);
      if (par2 > 0)
        i += this.field_70146_Z.nextInt(par2 + 1); 
      for (int j = 0; j < i; j++)
        func_70099_a(item, 0.0F); 
    } 
  }
  
  protected ResourceLocation func_184647_J() {
    switch (getShoggothType()) {
      case 0:
        return ACLoot.ENTITY_LESSER_SHOGGOTH;
      case 1:
        return ACLoot.ENTITY_LESSER_ABYSSAL_SHOGGOTH;
      case 2:
        return ACLoot.ENTITY_LESSER_DREADED_SHOGGOTH;
      case 3:
        return ACLoot.ENTITY_LESSER_OMOTHOL_SHOGGOTH;
      case 4:
        return ACLoot.ENTITY_LESSER_SHADOW_SHOGGOTH;
    } 
    return null;
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
    if (!stack.func_190926_b() && stack.func_77973_b() == ACItems.shoggoth_flesh && hasOwner(player)) {
      if (getShoggothType() != stack.func_77960_j()) {
        func_184185_a(func_184639_G(), func_70599_aP(), func_70647_i());
        player.func_184609_a(hand);
        if (!this.field_70170_p.field_72995_K) {
          func_70099_a(new ItemStack(ACItems.shoggoth_flesh, 1, getShoggothType()), 1.0F);
          setShoggothType(stack.func_77960_j());
          stack.func_190918_g(1);
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
  
  public boolean func_70662_br() {
    return true;
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    if (par1NBTTagCompound.func_74767_n("IsBaby"))
      setChild(true); 
    if (par1NBTTagCompound.func_74764_b("ShoggothType")) {
      byte var2 = par1NBTTagCompound.func_74771_c("ShoggothType");
      setShoggothType(var2);
    } 
    if (par1NBTTagCompound.func_74764_b("FoodLevel")) {
      byte var2 = par1NBTTagCompound.func_74771_c("FoodLevel");
      setFoodLevel(var2);
    } 
    this.monolithTimer = par1NBTTagCompound.func_74762_e("MonolithTimer");
  }
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    if (func_70631_g_())
      par1NBTTagCompound.func_74757_a("IsBaby", true); 
    par1NBTTagCompound.func_74774_a("ShoggothType", (byte)getShoggothType());
    par1NBTTagCompound.func_74774_a("FoodLevel", (byte)getFoodLevel());
    par1NBTTagCompound.func_74768_a("MonolithTimer", this.monolithTimer);
  }
  
  public void func_70074_a(EntityLivingBase par1EntityLivingBase) {
    super.func_70074_a(par1EntityLivingBase);
    if (EntityUtil.isShoggothFood(par1EntityLivingBase))
      feed(); 
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    Object data = super.func_180482_a(difficulty, par1EntityLivingData);
    setShoggothType(0);
    if (this.field_70170_p.field_73011_w.getDimension() == ACLib.abyssal_wasteland_id)
      setShoggothType(1); 
    if (this.field_70170_p.field_73011_w.getDimension() == ACLib.dreadlands_id)
      setShoggothType(2); 
    if (this.field_70170_p.field_73011_w.getDimension() == ACLib.omothol_id)
      setShoggothType(3); 
    if (this.field_70170_p.field_73011_w.getDimension() == ACLib.dark_realm_id)
      setShoggothType(4); 
    if (data == null)
      data = new GroupData((this.field_70170_p.field_73012_v.nextFloat() < ForgeModContainer.zombieBabyChance), null); 
    if (data instanceof GroupData) {
      GroupData groupdata = (GroupData)data;
      if (groupdata.isBaby)
        setGrowingAge(-24000); 
    } 
    return (IEntityLivingData)data;
  }
  
  class GroupData implements IEntityLivingData {
    public boolean isBaby;
    
    private GroupData(boolean par2) {
      this.isBaby = false;
      this.isBaby = par2;
    }
    
    GroupData(boolean par2, Object par4EntityLesserShoggothINNER1) {
      this(par2);
    }
  }
}
