package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIHarvestFarmland;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAITradePlayer;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityWitch;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import net.minecraft.world.storage.MapDecoration;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVillager extends EntityTameBase implements IMerchant, INpc, Light {
  private static final DataParameter<Integer> PROFESSION = EntityDataManager.func_187226_a(EntityVillager.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<String> PROFESSION_STR = EntityDataManager.func_187226_a(EntityVillager.class, DataSerializers.field_187194_d);
  
  private static final DataParameter<Integer> CAREER = EntityDataManager.func_187226_a(EntityVillager.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<String> CAREER_STR = EntityDataManager.func_187226_a(EntityVillager.class, DataSerializers.field_187194_d);
  
  private int randomTickDivider;
  
  private boolean isMating;
  
  private boolean isPlaying;
  
  Village villageObj;
  
  private EntityPlayer buyingPlayer;
  
  private MerchantRecipeList buyingList;
  
  private int timeUntilReset;
  
  private boolean needsInitilization;
  
  private boolean isWillingToMate;
  
  private int wealth;
  
  private String lastBuyingPlayer;
  
  private int careerLevel;
  
  private boolean isLookingForHome;
  
  private boolean areAdditionalTasksSet;
  
  private InventoryBasic villagerInventory;
  
  @Deprecated
  private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP = new ITradeList[][][][] { { { { 
            new EmeraldForItems(Items.field_151015_O, new PriceInfo(4, 8)), new EmeraldForItems(Items.field_151174_bG, new PriceInfo(4, 8)), new EmeraldForItems(Items.field_151172_bF, new PriceInfo(4, 8)), new EmeraldForItems(Items.field_185164_cV, new PriceInfo(4, 8)), new EmeraldForItems(
              
              Item.func_150898_a(Blocks.field_150423_aK), new PriceInfo(1, 1)), new EmeraldForItems(
              Item.func_150898_a(Blocks.field_150440_ba), new PriceInfo(1, 1)), new EmeraldForItems(Items.field_151044_h, new PriceInfo(4, 8)), new EmeraldForItems(Items.field_151137_ax, new PriceInfo(4, 8)), new EmeraldForItems(Items.field_151042_j, new PriceInfo(2, 4)), new EmeraldForItems(Items.field_151043_k, new PriceInfo(1, 2)), 
            new EmeraldForItems(Items.field_151045_i, new PriceInfo(1, 1)), new EmeraldForItems(Items.field_151078_bh, new PriceInfo(8, 16)), new EmeraldForItems(Items.field_151103_aS, new PriceInfo(8, 16)), new EmeraldForItems(Items.field_151007_F, new PriceInfo(8, 16)), new EmeraldForItems(Items.field_151016_H, new PriceInfo(8, 16)), new EmeraldForItems(Items.field_151070_bp, new PriceInfo(1, 4)), new EmeraldsForItem(Items.field_151079_bi, new PriceInfo(2, 4)), new EmeraldsForItem(Items.field_151072_bj, new PriceInfo(4, 8)), new EmeraldsForItem(Items.field_151073_bk, new PriceInfo(8, 8)), new EmeraldsForItem(Items.field_151156_bN, new PriceInfo(64, 64)), 
            new ListItemForEmeralds(Items.field_151025_P, new PriceInfo(-16, -8)), new ListItemForEmeralds(Items.field_151034_e, new PriceInfo(-24, -16)), new ListItemForEmeralds(Items.field_151153_ao, new PriceInfo(4, 4)), new ListItemForEmeralds(new ItemStack(Items.field_151153_ao, 1, 1), new PriceInfo(24, 24)), new ListItemForEmeralds(Items.field_151150_bK, new PriceInfo(4, 4)), new ListItemForEmeralds(Items.field_151106_aX, new PriceInfo(-64, -64)), new ListItemForEmeralds(Items.field_151168_bH, new PriceInfo(-32, -16)), new ListItemForEmeralds(Items.field_151077_bg, new PriceInfo(-16, -8)), new ListItemForEmeralds(Items.field_179557_bn, new PriceInfo(-16, -8)), new ListItemForEmeralds(Items.field_151157_am, new PriceInfo(-16, -8)), 
            new ListItemForEmeralds(Items.field_151083_be, new PriceInfo(-16, -8)), new ListItemForEmeralds(Items.field_151105_aU, new PriceInfo(1, 1)), new ListItemForEmeralds(Items.field_151012_L, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.field_151056_x, new PriceInfo(3, 3)), new ListItemForEmeralds(Items.field_151048_u, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.field_185159_cQ, new PriceInfo(3, 3)), new ListItemForEmeralds((Item)Items.field_151161_ac, new PriceInfo(5, 5)), new ListItemForEmeralds((Item)Items.field_151163_ad, new PriceInfo(8, 8)), new ListItemForEmeralds((Item)Items.field_151173_ae, new PriceInfo(7, 7)), new ListItemForEmeralds((Item)Items.field_151175_af, new PriceInfo(4, 4)), 
            new ItemAndEmeraldToItem(Items.field_185161_cS, new PriceInfo(64, 64), Items.field_185160_cR, new PriceInfo(1, 1)), new ItemAndEmeraldToItem(Items.field_179571_av, new PriceInfo(64, 64), Items.field_190929_cY, new PriceInfo(1, 1)), new ListEnchantedBookForEmeralds() } } } };
  
  public EntityVillager(World worldIn) {
    this(worldIn, 0);
  }
  
  public EntityVillager(World worldIn, int professionId) {
    super(worldIn);
    this.villagerInventory = new InventoryBasic("Items", false, 8);
    setProfession(professionId);
    func_70105_a(0.5F, 1.9F);
    ((PathNavigateGround)func_70661_as()).func_179688_b(true);
    func_98053_h(true);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAITradePlayer(this));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFollowLeader(this, 1.5D, 24.0F, 9.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.5D, true));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIHarvestFarmland(this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D, 80));
    this.field_70714_bg.func_75776_a(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
    func_98053_h(true);
    this.field_70728_aV = 5;
    populateBuyingList();
  }
  
  public String getDescName() {
    return "villager";
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityVillager(this.field_70170_p);
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
  
  public EnumTier getTier() {
    return EnumTier.TIER2;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + 0.25F) : super.func_70647_i();
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K) {
      EntityVillager baby = new EntityVillager(this.field_70170_p);
      baby.func_82149_j((Entity)this);
      baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), (IEntityLivingData)null);
      baby.setGrowingAge(-24000);
      baby.setChild(true);
      baby.setOwnerId(func_184753_b());
      if (isMarried())
        for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
          baby.levelUp();  
      this.field_70170_p.func_72838_d((Entity)baby);
    } 
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
  }
  
  public boolean func_70058_J() {
    if (this.field_70170_p.func_72917_a(func_174813_aQ(), (Entity)this) && this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(func_174813_aQ())) {
      BlockPos blockpos = new BlockPos(this.field_70165_t, (func_174813_aQ()).field_72338_b, this.field_70161_v);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(blockpos.func_177977_b());
      Block block = iblockstate.func_177230_c();
      if (block == Blocks.field_185774_da || block == Blocks.field_150351_n || block == Blocks.field_150322_A || block == Blocks.field_150364_r || block == Blocks.field_150344_f || block == Blocks.field_150347_e || block == Blocks.field_150325_L)
        return true; 
    } 
    return false;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (isTrading())
      func_70671_ap().func_75651_a((Entity)func_70931_l_(), 10.0F, 40.0F); 
  }
  
  protected void func_70619_bc() {
    if (!isTrading() && this.timeUntilReset > 0) {
      this.timeUntilReset--;
      if (this.timeUntilReset <= 0) {
        if (this.needsInitilization) {
          Iterator<MerchantRecipe> iterator = this.buyingList.iterator();
          while (iterator.hasNext()) {
            MerchantRecipe merchantrecipe = iterator.next();
            merchantrecipe.func_82783_a(2048);
          } 
          populateBuyingList();
          this.needsInitilization = false;
          if (this.villageObj != null && this.lastBuyingPlayer != null)
            this.field_70170_p.func_72960_a((Entity)this, (byte)14); 
        } 
        func_70690_d(new PotionEffect(MobEffects.field_76428_l, 200, 1));
      } 
    } 
    super.func_70619_bc();
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!isTrading() && !isAntiMob()) {
      if (func_184191_r((Entity)player)) {
        if (this.buyingList == null)
          populateBuyingList(); 
        if (hand == EnumHand.MAIN_HAND)
          player.func_71029_a(StatList.field_188074_H); 
        if (!this.field_70170_p.field_72995_K && !this.buyingList.isEmpty() && !func_70093_af()) {
          func_70932_a_(player);
          player.func_180472_a(this);
        } else if (this.buyingList.isEmpty()) {
          return false;
        } 
        return true;
      } 
      return true;
    } 
    return false;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(PROFESSION, Integer.valueOf(0));
    this.field_70180_af.func_187214_a(CAREER, Integer.valueOf(0));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("Profession", getProfession());
    tagCompound.func_74768_a("Riches", this.wealth);
    tagCompound.func_74768_a("Career", getCareer());
    tagCompound.func_74768_a("CareerLevel", this.careerLevel);
    tagCompound.func_74757_a("Willing", this.isWillingToMate);
    if (this.buyingList != null)
      tagCompound.func_74782_a("Offers", (NBTBase)this.buyingList.func_77202_a()); 
    NBTTagList nbttaglist = new NBTTagList();
    for (int i = 0; i < this.villagerInventory.func_70302_i_(); i++) {
      ItemStack itemstack = this.villagerInventory.func_70301_a(i);
      if (!itemstack.func_190926_b())
        nbttaglist.func_74742_a((NBTBase)itemstack.func_77955_b(new NBTTagCompound())); 
    } 
    tagCompound.func_74782_a("Inventory", (NBTBase)nbttaglist);
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setProfession(tagCompund.func_74762_e("Profession"));
    this.wealth = tagCompund.func_74762_e("Riches");
    setCareer(tagCompund.func_74762_e("Career"));
    this.careerLevel = tagCompund.func_74762_e("CareerLevel");
    this.isWillingToMate = tagCompund.func_74767_n("Willing");
    if (tagCompund.func_150297_b("Offers", 10)) {
      NBTTagCompound nbttagcompound = tagCompund.func_74775_l("Offers");
      this.buyingList = new MerchantRecipeList(nbttagcompound);
    } 
    NBTTagList nbttaglist = tagCompund.func_150295_c("Inventory", 10);
    for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
      ItemStack itemstack = new ItemStack(nbttaglist.func_150305_b(i));
      if (!itemstack.func_190926_b())
        this.villagerInventory.func_174894_a(itemstack); 
    } 
    func_98053_h(true);
  }
  
  public int func_70627_aG() {
    return (func_70638_az() != null) ? 5 : 80;
  }
  
  protected SoundEvent func_184639_G() {
    return isTrading() ? SoundEvents.field_187914_gn : SoundEvents.field_187910_gj;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187912_gl;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187911_gk;
  }
  
  public void func_70645_a(DamageSource cause) {
    super.func_70645_a(cause);
    if (!this.field_70170_p.field_72995_K) {
      for (int i = 0; i < this.villagerInventory.func_70302_i_(); i++) {
        if (this.villagerInventory != null) {
          ItemStack itemstack = this.villagerInventory.func_70301_a(i);
          if (itemstack != null) {
            func_145779_a(itemstack.func_77973_b(), itemstack.func_190916_E());
            this.villagerInventory.func_70298_a(i, itemstack.func_190916_E());
          } 
        } 
      } 
      if (this.wealth > 0)
        func_145779_a(Items.field_151166_bC, this.wealth); 
    } 
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_VILLAGER;
  }
  
  public void setProfession(int professionId) {
    this.field_70180_af.func_187227_b(PROFESSION, Integer.valueOf(professionId));
  }
  
  public int getProfession() {
    return Math.max(((Integer)this.field_70180_af.func_187225_a(PROFESSION)).intValue() % 5, 0);
  }
  
  public void setCareer(int professionId) {
    this.field_70180_af.func_187227_b(CAREER, Integer.valueOf(professionId));
  }
  
  public int getCareer() {
    return Math.max(((Integer)this.field_70180_af.func_187225_a(CAREER)).intValue() % 5, 0);
  }
  
  public boolean isMating() {
    return this.isMating;
  }
  
  public void setMating(boolean mating) {
    this.isMating = mating;
  }
  
  public void setPlaying(boolean playing) {
    this.isPlaying = playing;
  }
  
  public boolean isPlaying() {
    return this.isPlaying;
  }
  
  public void func_70932_a_(EntityPlayer p_70932_1_) {
    this.buyingPlayer = p_70932_1_;
  }
  
  public EntityPlayer func_70931_l_() {
    return this.buyingPlayer;
  }
  
  public boolean isTrading() {
    return (this.buyingPlayer != null);
  }
  
  public boolean getIsWillingToMate(boolean p_175550_1_) {
    if (!this.isWillingToMate && p_175550_1_ && func_175553_cp()) {
      boolean flag1 = false;
      for (int i = 0; i < this.villagerInventory.func_70302_i_(); i++) {
        ItemStack itemstack = this.villagerInventory.func_70301_a(i);
        if (itemstack != null)
          if (itemstack.func_77973_b() == Items.field_151025_P && itemstack.func_190916_E() >= 3) {
            flag1 = true;
            this.villagerInventory.func_70298_a(i, 3);
          } else if ((itemstack.func_77973_b() == Items.field_151174_bG || itemstack.func_77973_b() == Items.field_151172_bF) && itemstack.func_190916_E() >= 12) {
            flag1 = true;
            this.villagerInventory.func_70298_a(i, 12);
          }  
        if (flag1) {
          this.field_70170_p.func_72960_a((Entity)this, (byte)18);
          this.isWillingToMate = true;
          break;
        } 
      } 
    } 
    return this.isWillingToMate;
  }
  
  public void setIsWillingToMate(boolean p_175549_1_) {
    this.isWillingToMate = p_175549_1_;
  }
  
  public void func_70933_a(MerchantRecipe p_70933_1_) {
    p_70933_1_.func_77399_f();
    this.field_70757_a = -func_70627_aG();
    func_184185_a(SoundEvents.field_187915_go, func_70599_aP(), func_70647_i());
    int i = 3 + this.field_70146_Z.nextInt(4);
    setCurrentStudy(EntityTameBase.EnumStudy.Mental, 1);
    this.timeUntilReset = 40;
    this.needsInitilization = true;
    this.isWillingToMate = true;
    if (this.buyingPlayer != null) {
      this.lastBuyingPlayer = this.buyingPlayer.func_70005_c_();
    } else {
      this.lastBuyingPlayer = null;
    } 
    i += 5;
    if (p_70933_1_.func_77394_a().func_77973_b() == Items.field_151166_bC)
      this.wealth += p_70933_1_.func_77394_a().func_190916_E(); 
    if (p_70933_1_.func_180322_j())
      this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, i)); 
  }
  
  public void func_110297_a_(ItemStack p_110297_1_) {
    if (!this.field_70170_p.field_72995_K && this.field_70757_a > -func_70627_aG() + 20) {
      this.field_70757_a = -func_70627_aG();
      if (p_110297_1_ != null) {
        func_184185_a(SoundEvents.field_187915_go, func_70599_aP(), func_70647_i());
      } else {
        func_184185_a(SoundEvents.field_187913_gm, func_70599_aP(), func_70647_i());
      } 
    } 
  }
  
  public MerchantRecipeList func_70934_b(EntityPlayer p_70934_1_) {
    if (this.buyingList == null)
      populateBuyingList(); 
    return this.buyingList;
  }
  
  private void populateBuyingList() {
    ITradeList[][][] aitradelist = DEFAULT_TRADE_LIST_MAP[0];
    if (getCareer() != 0 && this.careerLevel != 0) {
      this.careerLevel++;
    } else {
      setCareer(this.field_70146_Z.nextInt(aitradelist.length) + 1);
      this.careerLevel = 1;
    } 
    if (this.buyingList == null)
      this.buyingList = new MerchantRecipeList(); 
    int i = getCareer() - 1;
    int j = this.careerLevel - 1;
    ITradeList[][] aitradelist1 = aitradelist[i];
    if (j < aitradelist1.length) {
      ITradeList[] aitradelist2 = aitradelist1[j];
      ITradeList[] aitradelist3 = aitradelist2;
      int k = aitradelist2.length;
      for (int l = 0; l < k; l++) {
        ITradeList itradelist = aitradelist3[l];
        itradelist.addMerchantRecipe(this, this.buyingList, this.field_70146_Z);
      } 
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70930_a(MerchantRecipeList p_70930_1_) {}
  
  public ITextComponent func_145748_c_() {
    Team team = func_96124_cp();
    String s = func_95999_t();
    if (s != null && !s.isEmpty()) {
      TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.func_96667_a(team, s));
      textcomponentstring.func_150256_b().func_150209_a(func_174823_aP());
      textcomponentstring.func_150256_b().func_179989_a(func_189512_bd());
      return (ITextComponent)textcomponentstring;
    } 
    if (this.buyingList == null)
      populateBuyingList(); 
    String s1 = null;
    switch (getProfession()) {
      case 0:
        if (getCareer() == 1) {
          s1 = "farmer";
          break;
        } 
        if (getCareer() == 2) {
          s1 = "fisherman";
          break;
        } 
        if (getCareer() == 3) {
          s1 = "shepherd";
          break;
        } 
        if (getCareer() == 4)
          s1 = "fletcher"; 
        break;
      case 1:
        if (getCareer() == 1) {
          s1 = "librarian";
          break;
        } 
        if (getCareer() == 2)
          s1 = "cartographer"; 
        break;
      case 2:
        s1 = "cleric";
        break;
      case 3:
        if (getCareer() == 1) {
          s1 = "armor";
          break;
        } 
        if (getCareer() == 2) {
          s1 = "weapon";
          break;
        } 
        if (getCareer() == 3)
          s1 = "tool"; 
        break;
      case 4:
        if (getCareer() == 1) {
          s1 = "butcher";
          break;
        } 
        if (getCareer() == 2)
          s1 = "leather"; 
        break;
      case 5:
        s1 = "nitwit";
        break;
    } 
    TextComponentTranslation textComponentTranslation = new TextComponentTranslation("entity.Villager." + s1, new Object[0]);
    textComponentTranslation.func_150256_b().func_150209_a(func_174823_aP());
    textComponentTranslation.func_150256_b().func_179989_a(func_189512_bd());
    if (team != null)
      textComponentTranslation.func_150256_b().func_150238_a(team.func_178775_l()); 
    return (ITextComponent)textComponentTranslation;
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.86F;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 12) {
      spawnParticles(EnumParticleTypes.HEART);
    } else if (id == 13) {
      spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
    } else if (id == 14) {
      spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
    } else {
      super.func_70103_a(id);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  private void spawnParticles(EnumParticleTypes particleType) {
    for (int i = 0; i < 5; i++) {
      double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
      double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
      double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
      this.field_70170_p.func_175688_a(particleType, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + 1.0D + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d0, d1, d2, new int[0]);
    } 
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    livingdata = super.func_180482_a(difficulty, livingdata);
    setRandomProfession(this, this.field_70170_p.field_73012_v);
    return livingdata;
  }
  
  public void setRandomProfession(EntityVillager entity, Random rand) {
    entity.setProfession(rand.nextInt(5));
    switch (getProfession()) {
      case 0:
        entity.setProfession(1 + rand.nextInt(3));
        return;
      case 1:
        entity.setProfession(1 + rand.nextInt());
        return;
      case 3:
        entity.setProfession(1 + rand.nextInt());
        return;
      case 4:
        entity.setProfession(1 + rand.nextInt());
        return;
    } 
    entity.setProfession(1);
  }
  
  public void func_70077_a(EntityLightningBolt lightningBolt) {
    if (!this.field_70170_p.field_72995_K && !this.field_70128_L) {
      EntityWitch entitywitch = new EntityWitch(this.field_70170_p);
      entitywitch.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, this.field_70177_z, this.field_70125_A);
      entitywitch.func_180482_a(this.field_70170_p.func_175649_E(new BlockPos((Entity)entitywitch)), (IEntityLivingData)null);
      entitywitch.func_94061_f(func_175446_cd());
      if (func_145818_k_())
        entitywitch.func_96094_a(func_95999_t()); 
      if (!isWild())
        entitywitch.setOwnerId(func_184753_b()); 
      this.field_70170_p.func_72838_d((Entity)entitywitch);
      func_70106_y();
    } 
  }
  
  public InventoryBasic getVillagerInventory() {
    return this.villagerInventory;
  }
  
  protected void func_175445_a(EntityItem p_175445_1_) {
    ItemStack itemstack = p_175445_1_.func_92059_d();
    Item item = itemstack.func_77973_b();
    if (canVillagerPickupItem(item)) {
      ItemStack itemstack1 = this.villagerInventory.func_174894_a(itemstack);
      if (itemstack1 == null) {
        p_175445_1_.func_70106_y();
      } else {
        itemstack.func_190920_e(itemstack1.func_190916_E());
      } 
    } 
  }
  
  private boolean canVillagerPickupItem(Item p_175558_1_) {
    return (p_175558_1_ == Items.field_185164_cV || p_175558_1_ == Items.field_185163_cU || p_175558_1_ == Items.field_151025_P || p_175558_1_ == Items.field_151174_bG || p_175558_1_ == Items.field_151172_bF || p_175558_1_ == Items.field_151015_O || p_175558_1_ == Items.field_151014_N);
  }
  
  public boolean func_175553_cp() {
    return hasEnoughItems(1);
  }
  
  public boolean canAbondonItems() {
    return hasEnoughItems(2);
  }
  
  public boolean func_175557_cr() {
    boolean flag = (getProfession() == 0);
    return !hasEnoughItems(5);
  }
  
  private boolean hasEnoughItems(int p_175559_1_) {
    boolean flag = (getProfession() == 0);
    for (int j = 0; j < this.villagerInventory.func_70302_i_(); j++) {
      ItemStack itemstack = this.villagerInventory.func_70301_a(j);
      if (itemstack != null) {
        if ((itemstack.func_77973_b() == Items.field_151025_P && itemstack.func_190916_E() >= 3 * p_175559_1_) || (itemstack.func_77973_b() == Items.field_151174_bG && itemstack.func_190916_E() >= 12 * p_175559_1_) || (itemstack.func_77973_b() == Items.field_151172_bF && itemstack.func_190916_E() >= 12 * p_175559_1_))
          return true; 
        if (flag && itemstack.func_77973_b() == Items.field_151015_O && itemstack.func_190916_E() >= 9 * p_175559_1_)
          return true; 
      } 
    } 
    return false;
  }
  
  public boolean isFarmItemInInventory() {
    for (int i = 0; i < this.villagerInventory.func_70302_i_(); i++) {
      ItemStack itemstack = this.villagerInventory.func_70301_a(i);
      if (itemstack != null && (itemstack.func_77973_b() == Items.field_185163_cU || itemstack.func_77973_b() == Items.field_151014_N || itemstack.func_77973_b() == Items.field_151174_bG || itemstack.func_77973_b() == Items.field_151172_bF))
        return true; 
    } 
    return false;
  }
  
  public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_) {
    if (super.func_174820_d(p_174820_1_, p_174820_2_))
      return true; 
    int j = p_174820_1_ - 300;
    if (j >= 0 && j < this.villagerInventory.func_70302_i_()) {
      this.villagerInventory.func_70299_a(j, p_174820_2_);
      if (p_174820_2_.func_77973_b() == Items.field_151025_P)
        func_184185_a(SoundEvents.field_187915_go, func_70599_aP(), func_70647_i()); 
      return true;
    } 
    return false;
  }
  
  public static class EmeraldForItems implements ITradeList {
    public Item sellItem;
    
    public EntityVillager.PriceInfo price;
    
    public EmeraldForItems(Item p_i45815_1_, EntityVillager.PriceInfo p_i45815_2_) {
      this.sellItem = p_i45815_1_;
      this.price = p_i45815_2_;
    }
    
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
      int i = 1;
      if (this.price != null)
        i = this.price.getPrice(random); 
      recipeList.add(new MerchantRecipe(new ItemStack(this.sellItem, i, 0), Items.field_151166_bC));
    }
  }
  
  public static class EmeraldsForItem implements ITradeList {
    public Item buyItem;
    
    public EntityVillager.PriceInfo price;
    
    public EmeraldsForItem(Item p_i45815_1_, EntityVillager.PriceInfo p_i45815_2_) {
      this.buyItem = p_i45815_1_;
      this.price = p_i45815_2_;
    }
    
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
      int i = 1;
      if (this.price != null)
        i = this.price.getPrice(random); 
      recipeList.add(new MerchantRecipe(new ItemStack(this.buyItem), new ItemStack(Items.field_151166_bC, i, 0)));
    }
  }
  
  public static interface ITradeList {
    void addMerchantRecipe(IMerchant param1IMerchant, MerchantRecipeList param1MerchantRecipeList, Random param1Random);
  }
  
  public static class ItemAndEmeraldToItem implements ITradeList {
    public ItemStack buyingItemStack;
    
    public EntityVillager.PriceInfo buyingPriceInfo;
    
    public ItemStack sellingItemstack;
    
    public EntityVillager.PriceInfo sellingPriceInfo;
    
    public ItemAndEmeraldToItem(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_) {
      this.buyingItemStack = new ItemStack(p_i45813_1_);
      this.buyingPriceInfo = p_i45813_2_;
      this.sellingItemstack = new ItemStack(p_i45813_3_);
      this.sellingPriceInfo = p_i45813_4_;
    }
    
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
      int i = 1;
      if (this.buyingPriceInfo != null)
        i = this.buyingPriceInfo.getPrice(random); 
      int j = 1;
      if (this.sellingPriceInfo != null)
        j = this.sellingPriceInfo.getPrice(random); 
      recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItemStack.func_77973_b(), i, this.buyingItemStack.func_77960_j()), new ItemStack(Items.field_151166_bC), new ItemStack(this.sellingItemstack.func_77973_b(), j, this.sellingItemstack.func_77960_j())));
    }
  }
  
  public static class ListEnchantedBookForEmeralds implements ITradeList {
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
      Enchantment enchantment = (Enchantment)Enchantment.field_185264_b.func_186801_a(random);
      int i = MathHelper.func_76136_a(random, enchantment.func_77319_d(), enchantment.func_77325_b());
      ItemStack itemstack = ItemEnchantedBook.func_92111_a(new EnchantmentData(enchantment, i));
      int j = 1 + random.nextInt(1 + i) + 2 * i;
      if (j > 10)
        j = 10; 
      recipeList.add(new MerchantRecipe(new ItemStack(Items.field_151122_aG), new ItemStack(Items.field_151166_bC, j), itemstack));
    }
  }
  
  public static class ListEnchantedItemForEmeralds implements ITradeList {
    public ItemStack enchantedItemStack;
    
    public EntityVillager.PriceInfo priceInfo;
    
    public ListEnchantedItemForEmeralds(Item p_i45814_1_, EntityVillager.PriceInfo p_i45814_2_) {
      this.enchantedItemStack = new ItemStack(p_i45814_1_);
      this.priceInfo = p_i45814_2_;
    }
    
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
      int i = 1;
      if (this.priceInfo != null)
        i = this.priceInfo.getPrice(random); 
      ItemStack itemstack = new ItemStack(Items.field_151166_bC, i, 0);
      ItemStack itemstack1 = new ItemStack(this.enchantedItemStack.func_77973_b(), 1, this.enchantedItemStack.func_77960_j());
      itemstack1 = EnchantmentHelper.func_77504_a(random, itemstack1, 30, true);
      recipeList.add(new MerchantRecipe(itemstack, itemstack1));
    }
  }
  
  public static class ListItemForEmeralds implements ITradeList {
    public ItemStack itemToBuy;
    
    public EntityVillager.PriceInfo priceInfo;
    
    public ListItemForEmeralds(Item p_i45811_1_, EntityVillager.PriceInfo p_i45811_2_) {
      this.itemToBuy = new ItemStack(p_i45811_1_);
      this.priceInfo = p_i45811_2_;
    }
    
    public ListItemForEmeralds(ItemStack p_i45812_1_, EntityVillager.PriceInfo p_i45812_2_) {
      this.itemToBuy = p_i45812_1_;
      this.priceInfo = p_i45812_2_;
    }
    
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
      ItemStack itemstack1, itemstack;
      int i = 1;
      if (this.priceInfo != null)
        i = this.priceInfo.getPrice(random); 
      if (i < 0) {
        itemstack = new ItemStack(Items.field_151166_bC, 1, 0);
        itemstack1 = new ItemStack(this.itemToBuy.func_77973_b(), -i, this.itemToBuy.func_77960_j());
      } else {
        itemstack = new ItemStack(Items.field_151166_bC, i, 0);
        itemstack1 = new ItemStack(this.itemToBuy.func_77973_b(), 1, this.itemToBuy.func_77960_j());
      } 
      recipeList.add(new MerchantRecipe(itemstack, itemstack1));
    }
  }
  
  static class TreasureMapForEmeralds implements ITradeList {
    public EntityVillager.PriceInfo value;
    
    public String destination;
    
    public MapDecoration.Type destinationType;
    
    public TreasureMapForEmeralds(EntityVillager.PriceInfo p_i47340_1_, String p_i47340_2_, MapDecoration.Type p_i47340_3_) {
      this.value = p_i47340_1_;
      this.destination = p_i47340_2_;
      this.destinationType = p_i47340_3_;
    }
    
    public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
      int i = this.value.getPrice(random);
      World world = merchant.func_190670_t_();
      BlockPos blockpos = world.func_190528_a(this.destination, merchant.func_190671_u_(), true);
      if (blockpos != null) {
        ItemStack itemstack = ItemMap.func_190906_a(world, blockpos.func_177958_n(), blockpos.func_177952_p(), (byte)2, true, true);
        ItemMap.func_190905_a(world, itemstack);
        MapData.func_191094_a(itemstack, blockpos, "+", this.destinationType);
        itemstack.func_190924_f("filled_map." + this.destination.toLowerCase(Locale.ROOT));
        recipeList.add(new MerchantRecipe(new ItemStack(Items.field_151166_bC, i), new ItemStack(Items.field_151111_aL), itemstack));
      } 
    }
  }
  
  public static class PriceInfo extends Tuple {
    public PriceInfo(int p_i45810_1_, int p_i45810_2_) {
      super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
    }
    
    public int getPrice(Random p_179412_1_) {
      return (((Integer)func_76341_a()).intValue() >= ((Integer)func_76340_b()).intValue()) ? ((Integer)func_76341_a()).intValue() : (((Integer)func_76341_a()).intValue() + p_179412_1_.nextInt(((Integer)func_76340_b()).intValue() - ((Integer)func_76341_a()).intValue() + 1));
    }
  }
  
  public World func_190670_t_() {
    return this.field_70170_p;
  }
  
  public BlockPos func_190671_u_() {
    return new BlockPos((Entity)this);
  }
}
