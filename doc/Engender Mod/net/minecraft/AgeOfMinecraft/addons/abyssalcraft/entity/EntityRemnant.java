package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRemnant extends EntityTameBase implements IMerchant, Undead {
  private static final DataParameter<Integer> PROFESSION = EntityDataManager.func_187226_a(EntityRemnant.class, DataSerializers.field_187192_b);
  
  private EntityPlayer tradingPlayer;
  
  private MerchantRecipeList tradingList;
  
  private int timeUntilReset;
  
  private boolean needsInitilization;
  
  private int wealth;
  
  private int timer;
  
  private float field_82191_bN;
  
  public static final Map<Item, Tuple> itemSellingList = new HashMap<>();
  
  public static final Map<Item, Tuple> coinSellingList = new HashMap<>();
  
  public EntityRemnant(World par1World) {
    super(par1World);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFollowLeader(this, 1.2D, 24.0F, 9.0F));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityJzahar.class, 8.0F, 1.0F));
    this.field_70714_bg.func_75776_a(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
    this.isOffensive = true;
    func_70105_a(0.5F, 1.95F);
  }
  
  public int getNextLevelRequirement() {
    return 300;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0D);
    } 
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityRemnant(this.field_70170_p);
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    func_184609_a(EnumHand.MAIN_HAND);
    func_184609_a(EnumHand.OFF_HAND);
    boolean flag = super.func_70652_k(par1Entity);
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected void func_70619_bc() {
    if (!isTrading() && this.timeUntilReset > 0) {
      this.timeUntilReset--;
      if (this.timeUntilReset <= 0) {
        if (this.needsInitilization) {
          if (this.tradingList.size() > 1) {
            Iterator<MerchantRecipe> iterator = this.tradingList.iterator();
            while (iterator.hasNext()) {
              MerchantRecipe merchantrecipe = iterator.next();
              merchantrecipe.func_82783_a(2048);
            } 
          } 
          addDefaultEquipmentAndRecipies(1);
          this.needsInitilization = false;
        } 
        func_70690_d(new PotionEffect(MobEffects.field_76428_l, 200, 0));
      } 
    } 
    super.func_70619_bc();
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    if (func_70089_S() && !player.func_70093_af())
      if (EntityUtil.hasNecronomicon(player) && ownsTheirBook(player)) {
        if (!isTrading()) {
          if (!this.field_70170_p.field_72995_K) {
            func_70932_a_(player);
            player.func_180472_a(this);
            player.func_145747_a((ITextComponent)new TextComponentString(func_70005_c_() + ": " + I18n.func_74838_a("message.remnanthelpful.trade")));
            return true;
          } 
        } else if (!this.tradingPlayer.func_110124_au().equals(player.func_110124_au())) {
          player.func_145747_a((ITextComponent)new TextComponentString(func_70005_c_() + ": " + I18n.func_74838_a("message.remnanthelpful.busy")));
        } 
      } else {
        advice(player);
        return true;
      }  
    return false;
  }
  
  private boolean ownsTheirBook(EntityPlayer player) {
    for (ItemStack stack : player.field_71071_by.field_70462_a) {
      if (stack != null && stack.func_77973_b() instanceof ItemNecronomicon && !((ItemNecronomicon)stack.func_77973_b()).isOwner(player, stack))
        return false; 
    } 
    return true;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(PROFESSION, Integer.valueOf(0));
  }
  
  private void advice(EntityPlayer player) {
    int insultNum = this.field_70170_p.field_73012_v.nextInt(5);
    String translated = func_70005_c_() + ": " + String.format(I18n.func_74838_a("message.remnanthelpful.advice." + insultNum), new Object[] { player.func_70005_c_() });
    if (this.field_70170_p.field_72995_K) {
      List<EntityPlayer> players = this.field_70170_p.func_72872_a(EntityPlayer.class, player.func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D));
      if (players != null) {
        Iterator<EntityPlayer> i = players.iterator();
        while (i.hasNext()) {
          EntityPlayer player1 = i.next();
          player1.func_145747_a((ITextComponent)new TextComponentString(translated));
        } 
      } 
    } 
  }
  
  public void enrage(boolean call, EntityLivingBase enemy) {
    func_70624_b(enemy);
    enrage(call);
  }
  
  public void enrage(boolean call) {
    if (call) {
      List<EntityRemnant> friends = this.field_70170_p.func_72872_a(getClass(), func_174813_aQ().func_72314_b(16.0D, 16.0D, 16.0D));
      if (friends != null) {
        Iterator<EntityRemnant> iter = friends.iterator();
        while (iter.hasNext())
          ((EntityRemnant)iter.next()).enrage(false, func_70638_az()); 
      } 
      func_184185_a(ACSounds.remnant_scream, 3.0F, 1.0F);
    } 
    setAngry();
  }
  
  public boolean func_70097_a(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.func_76346_g() instanceof EntityLivingBase && !func_184191_r(par1DamageSource.func_76346_g())) {
      if (func_70638_az() != par1DamageSource.func_76346_g()) {
        func_70624_b((EntityLivingBase)par1DamageSource.func_76346_g());
        enrage(true, func_70638_az());
      } 
      if (!isAngry()) {
        enrage(true);
      } else {
        enrage((this.field_70146_Z.nextInt(10) == 0));
      } 
    } 
    return super.func_70097_a(par1DamageSource, par2);
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (isAngry()) {
      this.timer--;
      if (this.timer <= 0)
        this.timer = 0; 
    } 
  }
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    par1NBTTagCompound.func_74768_a("Profession", getProfession());
    par1NBTTagCompound.func_74768_a("Money", this.wealth);
    par1NBTTagCompound.func_74768_a("AngerTimer", this.timer);
    if (this.tradingList != null)
      par1NBTTagCompound.func_74782_a("Offers", (NBTBase)this.tradingList.func_77202_a()); 
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    setProfession(par1NBTTagCompound.func_74762_e("Profession"));
    this.wealth = par1NBTTagCompound.func_74762_e("Money");
    this.timer = par1NBTTagCompound.func_74762_e("AngerTimer");
    if (par1NBTTagCompound.func_150297_b("Offers", 10)) {
      NBTTagCompound nbttagcompound1 = par1NBTTagCompound.func_74775_l("Offers");
      this.tradingList = new MerchantRecipeList(nbttagcompound1);
    } 
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean isAngry() {
    return (this.timer > 0);
  }
  
  public void setAngry() {
    this.timer = 600;
  }
  
  protected Item func_146068_u() {
    return ACItems.eldritch_scale;
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_REMNANT;
  }
  
  protected boolean func_70692_ba() {
    return false;
  }
  
  protected SoundEvent func_184639_G() {
    return (getProfession() == 2) ? ACSounds.remnant_priest_chant : null;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource souce) {
    return ACSounds.remnant_scream;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.shadow_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187823_fN, 0.15F, 1.0F);
  }
  
  public void setProfession(int par1) {
    this.field_70180_af.func_187227_b(PROFESSION, Integer.valueOf(par1));
  }
  
  public int getProfession() {
    return ((Integer)this.field_70180_af.func_187225_a(PROFESSION)).intValue();
  }
  
  public void func_70932_a_(EntityPlayer var1) {
    this.tradingPlayer = var1;
  }
  
  public EntityPlayer func_70931_l_() {
    return this.tradingPlayer;
  }
  
  public boolean isTrading() {
    return (this.tradingPlayer != null);
  }
  
  public MerchantRecipeList func_70934_b(EntityPlayer var1) {
    if (this.tradingList == null)
      addDefaultEquipmentAndRecipies(1); 
    return this.tradingList;
  }
  
  private float adjustProbability(float par1) {
    float f1 = par1 + this.field_82191_bN;
    return (f1 > 0.9F) ? (0.9F - f1 - 0.9F) : f1;
  }
  
  private void addDefaultEquipmentAndRecipies(int par1) {
    int k;
    Item[] aitem, aitem1;
    int j;
    if (this.tradingList != null) {
      this.field_82191_bN = MathHelper.func_76129_c(this.tradingList.size()) * 0.2F;
    } else {
      this.field_82191_bN = 0.0F;
    } 
    MerchantRecipeList list = new MerchantRecipeList();
    switch (getProfession()) {
      case 0:
        addItemTrade(list, Items.field_151015_O, this.field_70146_Z, adjustProbability(0.9F));
        addItemTrade(list, Item.func_150898_a(Blocks.field_150325_L), this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, Items.field_151076_bf, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, Items.field_179566_aV, this.field_70146_Z, adjustProbability(0.4F));
        addCoinTrade(list, Items.field_151025_P, this.field_70146_Z, adjustProbability(0.9F));
        addCoinTrade(list, Items.field_151127_ba, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, Items.field_151034_e, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, Items.field_151106_aX, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, (Item)Items.field_151097_aZ, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, Items.field_151033_d, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.fish_on_a_plate, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, Items.field_151032_g, this.field_70146_Z, adjustProbability(0.5F));
        if (this.field_70146_Z.nextFloat() < adjustProbability(0.5F))
          list.add(new MerchantRecipe(new ItemStack(Blocks.field_150351_n, 10), new ItemStack(ACItems.elder_engraved_coin), new ItemStack(Items.field_151145_ak, 4 + this.field_70146_Z.nextInt(2), 0))); 
        break;
      case 1:
        addItemTrade(list, Items.field_151121_aF, this.field_70146_Z, adjustProbability(0.8F));
        addItemTrade(list, Items.field_151122_aG, this.field_70146_Z, adjustProbability(0.8F));
        addItemTrade(list, Items.field_151164_bB, this.field_70146_Z, adjustProbability(0.3F));
        addItemTrade(list, Items.field_151078_bh, this.field_70146_Z, adjustProbability(0.7F));
        addItemTrade(list, ACItems.coralium_plagued_flesh, this.field_70146_Z, adjustProbability(0.7F));
        addItemTrade(list, ACItems.dread_fragment, this.field_70146_Z, adjustProbability(0.7F));
        addItemTrade(list, ACItems.omothol_flesh, this.field_70146_Z, adjustProbability(0.7F));
        addItemTrade(list, ACItems.rotten_anti_flesh, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, Item.func_150898_a(Blocks.field_150342_X), this.field_70146_Z, adjustProbability(0.8F));
        addCoinTrade(list, Item.func_150898_a(Blocks.field_150359_w), this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, Items.field_151111_aL, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, Items.field_151113_aN, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.necronomicon, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.abyssal_wasteland_necronomicon, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadlands_necronomicon, this.field_70146_Z, adjustProbability(0.1F));
        break;
      case 2:
        addCoinTrade(list, Items.field_151061_bv, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, Items.field_151062_by, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, Items.field_151137_ax, this.field_70146_Z, adjustProbability(0.4F));
        addCoinTrade(list, Item.func_150898_a(Blocks.field_150426_aN), this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ritual_charm, this.field_70146_Z, adjustProbability(0.4F));
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.cthulhu_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.hastur_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.jzahar_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.azathoth_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.nyarlathotep_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.yog_sothoth_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.shub_niggurath_charm, 1);
        addCoinTrade(list, ACItems.staff_of_rending, this.field_70146_Z, adjustProbability(0.1F));
        aitem = new Item[] { ACItems.ethaxium_sword, ACItems.ethaxium_chestplate, ACItems.ethaxium_axe, ACItems.ethaxium_pickaxe, ACItems.ethaxium_shovel };
        aitem1 = aitem;
        j = aitem.length;
        k = 0;
        while (k < j) {
          Item item = aitem1[k];
          if (this.field_70146_Z.nextFloat() < adjustProbability(0.05F))
            list.add(new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(ACItems.elder_engraved_coin, 2 + this.field_70146_Z.nextInt(3), 0), EnchantmentHelper.func_77504_a(this.field_70146_Z, new ItemStack(item, 1, 0), 15 + this.field_70146_Z.nextInt(15), true))); 
          k++;
        } 
        break;
      case 3:
        addItemTrade(list, Items.field_151044_h, this.field_70146_Z, adjustProbability(0.7F));
        addItemTrade(list, ACItems.abyssalnite_ingot, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, ACItems.refined_coralium_ingot, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, ACItems.dreadium_ingot, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, ACItems.ethaxium_ingot, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_sword, this.field_70146_Z, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_axe, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_pickaxe, this.field_70146_Z, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_shovel, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_hoe, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_boots, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_helmet, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_chestplate, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_leggings, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.blank_engraving, this.field_70146_Z, adjustProbability(0.2F));
        break;
      case 4:
        addItemTrade(list, Items.field_151044_h, this.field_70146_Z, adjustProbability(0.7F));
        addItemTrade(list, Items.field_151147_al, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, Items.field_151082_bd, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, Items.field_151076_bf, this.field_70146_Z, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.washcloth, this.field_70146_Z, adjustProbability(0.4F));
        addCoinTrade(list, ACItems.mre, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.pork_on_a_plate, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.beef_on_a_plate, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.chicken_on_a_plate, this.field_70146_Z, adjustProbability(0.3F));
        break;
      case 5:
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.cthulhu_engraved_coin, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.hastur_engraved_coin, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.jzahar_engraved_coin, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.azathoth_engraved_coin, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.nyarlathotep_engraved_coin, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.yog_sothoth_engraved_coin, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.shub_niggurath_engraved_coin, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 16, ACItems.cthulhu_engraved_coin, 2);
        addCoinTrade(list, ACItems.elder_engraved_coin, 16, ACItems.hastur_engraved_coin, 2);
        addCoinTrade(list, ACItems.elder_engraved_coin, 16, ACItems.jzahar_engraved_coin, 2);
        addCoinTrade(list, ACItems.elder_engraved_coin, 16, ACItems.azathoth_engraved_coin, 2);
        addCoinTrade(list, ACItems.elder_engraved_coin, 16, ACItems.nyarlathotep_engraved_coin, 2);
        addCoinTrade(list, ACItems.elder_engraved_coin, 16, ACItems.yog_sothoth_engraved_coin, 2);
        addCoinTrade(list, ACItems.elder_engraved_coin, 16, ACItems.shub_niggurath_engraved_coin, 2);
        addCoinTrade(list, ACItems.elder_engraved_coin, 24, ACItems.cthulhu_engraved_coin, 3);
        addCoinTrade(list, ACItems.elder_engraved_coin, 24, ACItems.hastur_engraved_coin, 3);
        addCoinTrade(list, ACItems.elder_engraved_coin, 24, ACItems.jzahar_engraved_coin, 3);
        addCoinTrade(list, ACItems.elder_engraved_coin, 24, ACItems.azathoth_engraved_coin, 3);
        addCoinTrade(list, ACItems.elder_engraved_coin, 24, ACItems.nyarlathotep_engraved_coin, 3);
        addCoinTrade(list, ACItems.elder_engraved_coin, 24, ACItems.yog_sothoth_engraved_coin, 3);
        addCoinTrade(list, ACItems.elder_engraved_coin, 24, ACItems.shub_niggurath_engraved_coin, 3);
        break;
      case 6:
        addItemTrade(list, Items.field_151044_h, this.field_70146_Z, adjustProbability(0.7F));
        addItemTrade(list, ACItems.abyssalnite_ingot, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, ACItems.refined_coralium_ingot, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, ACItems.dreadium_ingot, this.field_70146_Z, adjustProbability(0.5F));
        addItemTrade(list, ACItems.ethaxium_ingot, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_sword, this.field_70146_Z, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_axe, this.field_70146_Z, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_pickaxe, this.field_70146_Z, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_shovel, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_hoe, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_boots, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_boots, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_helmet, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_helmet, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_chestplate, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_chestplate, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_leggings, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_leggings, this.field_70146_Z, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_boots, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_helmet, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_chestplate, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_leggings, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.cthulhu_engraving, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.hastur_engraving, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.jzahar_engraving, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.azathoth_engraving, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.nyarlathotep_engraving, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.yog_sothoth_engraving, this.field_70146_Z, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.shub_niggurath_engraving, this.field_70146_Z, adjustProbability(0.1F));
        break;
    } 
    if (list.isEmpty())
      addItemTrade(list, Items.field_151043_k, this.field_70146_Z, 1.0F); 
    Collections.shuffle((List<?>)list);
    if (this.tradingList == null)
      this.tradingList = new MerchantRecipeList(); 
    for (int l = 0; l < par1 && l < list.size(); l++)
      addToListWithCheck(this.tradingList, (MerchantRecipe)list.get(l)); 
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70930_a(MerchantRecipeList var1) {}
  
  public World func_190670_t_() {
    return this.field_70170_p;
  }
  
  public BlockPos func_190671_u_() {
    return new BlockPos((Entity)this);
  }
  
  public void func_70933_a(MerchantRecipe var1) {
    var1.func_77399_f();
    if (var1.func_77397_d().func_77973_b() instanceof net.minecraft.item.ItemTool || var1
      .func_77397_d().func_77973_b() instanceof net.minecraft.item.ItemArmor) {
      var1.func_77399_f();
      var1.func_77399_f();
      var1.func_77399_f();
    } 
    if (var1.func_77394_a().func_77973_b() instanceof ItemNecronomicon || var1
      .func_77394_a().func_77973_b() instanceof com.shinoow.abyssalcraft.common.items.ItemDrainStaff)
      var1.func_82785_h(); 
    this.field_70757_a = -func_70627_aG();
    func_184185_a(ACSounds.remnant_yes, func_70599_aP(), func_70647_i());
    this.timeUntilReset = 40;
    this.needsInitilization = true;
    if (APIUtils.isCoin(var1.func_77394_a()))
      this.wealth += var1.func_77394_a().func_190916_E(); 
    int i = 10 + this.field_70146_Z.nextInt(10);
    if (var1.func_180322_j())
      this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 0.5D, this.field_70161_v, i)); 
  }
  
  private void addToListWithCheck(MerchantRecipeList list, MerchantRecipe recipe) {
    for (int i = 0; i < list.size(); i++) {
      MerchantRecipe merchantrecipe1 = (MerchantRecipe)list.get(i);
      if (hasSameIDsAs(recipe, merchantrecipe1)) {
        if (hasSameItemsAs(recipe, merchantrecipe1))
          list.set(i, recipe); 
        return;
      } 
    } 
    list.add(recipe);
  }
  
  private boolean hasSameIDsAs(MerchantRecipe r1, MerchantRecipe r2) {
    return (r1.func_77394_a().func_77973_b() == r2.func_77394_a().func_77973_b() && r1.func_77397_d().func_77973_b() == r2.func_77397_d().func_77973_b()) ? (((r1
      .func_77396_b().func_190926_b() && r2.func_77396_b().func_190926_b()) || (!r1.func_77396_b().func_190926_b() && !r2.func_77396_b().func_190926_b() && r1
      .func_77396_b().func_77973_b() == r2.func_77396_b().func_77973_b()))) : false;
  }
  
  private boolean hasSameItemsAs(MerchantRecipe r1, MerchantRecipe r2) {
    return (hasSameIDsAs(r1, r2) && (r1.func_77394_a().func_190916_E() < r2.func_77394_a().func_190916_E() || (
      !r1.func_77396_b().func_190926_b() && r1.func_77396_b().func_190916_E() < r2.func_77396_b().func_190916_E())));
  }
  
  public static void addItemTrade(MerchantRecipeList list, Item item, Random rand, float probability) {
    if (rand.nextFloat() < probability)
      list.add(new MerchantRecipe(getItemStackWithQuantity(item, rand), ACItems.elder_engraved_coin)); 
  }
  
  private static ItemStack getItemStackWithQuantity(Item item, Random rand) {
    return new ItemStack(item, getQuantity(item, rand), 0);
  }
  
  private static int getQuantity(Item item, Random rand) {
    Tuple tuple = itemSellingList.get(item);
    return (tuple == null) ? 1 : ((((Integer)tuple.func_76341_a()).intValue() >= ((Integer)tuple.func_76340_b()).intValue()) ? ((Integer)tuple.func_76341_a()).intValue() : (((Integer)tuple.func_76341_a()).intValue() + rand.nextInt(((Integer)tuple.func_76340_b()).intValue() - ((Integer)tuple.func_76341_a()).intValue())));
  }
  
  public static void addCoinTrade(MerchantRecipeList list, Item item, Random rand, float probability) {
    if (rand.nextFloat() < probability) {
      ItemStack itemstack, itemstack1;
      int i = getRarity(item, rand);
      if (i < 0) {
        itemstack = new ItemStack(ACItems.elder_engraved_coin, 1, 0);
        itemstack1 = new ItemStack(item, -i, 0);
      } else {
        itemstack = new ItemStack(ACItems.elder_engraved_coin, i, 0);
        itemstack1 = new ItemStack(item, 1, 0);
      } 
      list.add(new MerchantRecipe(itemstack, itemstack1));
    } 
  }
  
  private static int getRarity(Item par1, Random par2) {
    Tuple tuple = coinSellingList.get(par1);
    return (tuple == null) ? 1 : ((((Integer)tuple.func_76341_a()).intValue() >= ((Integer)tuple.func_76340_b()).intValue()) ? ((Integer)tuple.func_76341_a()).intValue() : (((Integer)tuple.func_76341_a()).intValue() + par2.nextInt(((Integer)tuple.func_76340_b()).intValue() - ((Integer)tuple.func_76341_a()).intValue())));
  }
  
  public void func_110297_a_(ItemStack par1ItemStack) {
    if (!this.field_70170_p.field_72995_K && this.field_70757_a > -func_70627_aG() + 20) {
      this.field_70757_a = -func_70627_aG();
      if (!par1ItemStack.func_190926_b()) {
        func_184185_a(ACSounds.remnant_yes, func_70599_aP(), func_70647_i());
        if (func_70931_l_() != null)
          func_70931_l_().func_145747_a((ITextComponent)new TextComponentString(func_70005_c_() + ": " + I18n.func_74838_a("message.remnanthelpful.trade.success"))); 
      } else {
        func_184185_a(ACSounds.remnant_no, func_70599_aP(), func_70647_i());
        if (func_70931_l_() != null)
          func_70931_l_().func_145747_a((ITextComponent)new TextComponentString(func_70005_c_() + ": " + I18n.func_74838_a("message.remnanthelpful.trade.rejected"))); 
      } 
    } 
  }
  
  public void addCoinTrade(MerchantRecipeList list, Item buy, int q1, Item sell, int q2) {
    addCoinTrade(list, new ItemStack(buy, q1), new ItemStack(sell, q2));
  }
  
  public void addCoinTrade(MerchantRecipeList list, Item buy1, int q1, Item buy2, int q2, Item sell, int q3) {
    addCoinTrade(list, new ItemStack(buy1, q1), new ItemStack(buy2, q2), new ItemStack(sell, q3));
  }
  
  public void addCoinTrade(MerchantRecipeList list, ItemStack buy, ItemStack sell) {
    addCoinTrade(list, buy, ItemStack.field_190927_a, sell);
  }
  
  public void addCoinTrade(MerchantRecipeList list, ItemStack buy1, ItemStack buy2, ItemStack sell) {
    list.add(new MerchantRecipe(buy1, buy2, sell));
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData data) {
    data = super.func_180482_a(difficulty, data);
    applyRandomTrade(this.field_70170_p.field_73012_v);
    if (func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
      Calendar calendar = this.field_70170_p.func_83015_S();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
        func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((this.field_70146_Z.nextFloat() < 0.1F) ? Blocks.field_150428_aP : Blocks.field_150423_aK));
        this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
      } 
    } 
    return data;
  }
  
  static {
    itemSellingList.put(Items.field_151044_h, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    itemSellingList.put(ACItems.abyssalnite_ingot, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    itemSellingList.put(ACItems.refined_coralium_ingot, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
    itemSellingList.put(ACItems.dreadium_ingot, new Tuple(Integer.valueOf(1), Integer.valueOf(2)));
    itemSellingList.put(ACItems.ethaxium_ingot, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    itemSellingList.put(Items.field_151121_aF, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    itemSellingList.put(Items.field_151122_aG, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
    itemSellingList.put(Items.field_151164_bB, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    itemSellingList.put(Items.field_151079_bi, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    itemSellingList.put(Items.field_151061_bv, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    itemSellingList.put(Items.field_151147_al, new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
    itemSellingList.put(Items.field_151082_bd, new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
    itemSellingList.put(Items.field_151076_bf, new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
    itemSellingList.put(Items.field_179566_aV, new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
    itemSellingList.put(Items.field_151014_N, new Tuple(Integer.valueOf(12), Integer.valueOf(16)));
    itemSellingList.put(Items.field_151081_bc, new Tuple(Integer.valueOf(12), Integer.valueOf(16)));
    itemSellingList.put(Items.field_151080_bb, new Tuple(Integer.valueOf(12), Integer.valueOf(16)));
    itemSellingList.put(Items.field_151015_O, new Tuple(Integer.valueOf(8), Integer.valueOf(12)));
    itemSellingList.put(Item.func_150898_a(Blocks.field_150325_L), new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
    itemSellingList.put(Items.field_151078_bh, new Tuple(Integer.valueOf(12), Integer.valueOf(16)));
    itemSellingList.put(ACItems.coralium_plagued_flesh, new Tuple(Integer.valueOf(8), Integer.valueOf(12)));
    itemSellingList.put(ACItems.coralium_plagued_flesh_on_a_bone, new Tuple(Integer.valueOf(8), Integer.valueOf(12)));
    itemSellingList.put(ACItems.dread_fragment, new Tuple(Integer.valueOf(16), Integer.valueOf(28)));
    itemSellingList.put(ACItems.omothol_flesh, new Tuple(Integer.valueOf(8), Integer.valueOf(16)));
    itemSellingList.put(ACItems.rotten_anti_flesh, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
    coinSellingList.put(Items.field_151045_i, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    coinSellingList.put(Items.field_151033_d, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    coinSellingList.put(Items.field_151097_aZ, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    coinSellingList.put(ACItems.ethaxium_sword, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    coinSellingList.put(ACItems.ethaxium_axe, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    coinSellingList.put(ACItems.ethaxium_pickaxe, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    coinSellingList.put(ACItems.ethaxium_shovel, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    coinSellingList.put(ACItems.ethaxium_hoe, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
    coinSellingList.put(ACItems.ethaxium_boots, new Tuple(Integer.valueOf(4), Integer.valueOf(4)));
    coinSellingList.put(ACItems.ethaxium_helmet, new Tuple(Integer.valueOf(5), Integer.valueOf(5)));
    coinSellingList.put(ACItems.ethaxium_chestplate, new Tuple(Integer.valueOf(8), Integer.valueOf(8)));
    coinSellingList.put(ACItems.ethaxium_leggings, new Tuple(Integer.valueOf(7), Integer.valueOf(7)));
    coinSellingList.put(Items.field_151025_P, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
    coinSellingList.put(Items.field_151127_ba, new Tuple(Integer.valueOf(-12), Integer.valueOf(-6)));
    coinSellingList.put(Items.field_151034_e, new Tuple(Integer.valueOf(-12), Integer.valueOf(-6)));
    coinSellingList.put(Items.field_151106_aX, new Tuple(Integer.valueOf(-24), Integer.valueOf(-16)));
    coinSellingList.put(Item.func_150898_a(Blocks.field_150359_w), new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    coinSellingList.put(Item.func_150898_a(Blocks.field_150342_X), new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    coinSellingList.put(ACItems.washcloth, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    coinSellingList.put(ACItems.mre, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
    coinSellingList.put(Items.field_151062_by, new Tuple(Integer.valueOf(-4), Integer.valueOf(-4)));
    coinSellingList.put(Items.field_151137_ax, new Tuple(Integer.valueOf(-27), Integer.valueOf(-9)));
    coinSellingList.put(Items.field_151111_aL, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
    coinSellingList.put(Items.field_151113_aN, new Tuple(Integer.valueOf(2), Integer.valueOf(2)));
    coinSellingList.put(ACItems.necronomicon, new Tuple(Integer.valueOf(1), Integer.valueOf(2)));
    coinSellingList.put(ACItems.abyssal_wasteland_necronomicon, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
    coinSellingList.put(ACItems.dreadlands_necronomicon, new Tuple(Integer.valueOf(4), Integer.valueOf(8)));
    coinSellingList.put(Item.func_150898_a(Blocks.field_150426_aN), new Tuple(Integer.valueOf(-9), Integer.valueOf(-9)));
    coinSellingList.put(ACItems.pork_on_a_plate, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    coinSellingList.put(ACItems.beef_on_a_plate, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    coinSellingList.put(ACItems.chicken_on_a_plate, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    coinSellingList.put(ACItems.fish_on_a_plate, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
    coinSellingList.put(Items.field_151077_bg, new Tuple(Integer.valueOf(-16), Integer.valueOf(-12)));
    coinSellingList.put(Items.field_151061_bv, new Tuple(Integer.valueOf(2), Integer.valueOf(2)));
    coinSellingList.put(Items.field_151032_g, new Tuple(Integer.valueOf(-32), Integer.valueOf(-24)));
    coinSellingList.put(ACItems.plated_coralium_boots, new Tuple(Integer.valueOf(4), Integer.valueOf(4)));
    coinSellingList.put(ACItems.dreadium_samurai_boots, new Tuple(Integer.valueOf(4), Integer.valueOf(4)));
    coinSellingList.put(ACItems.plated_coralium_helmet, new Tuple(Integer.valueOf(5), Integer.valueOf(5)));
    coinSellingList.put(ACItems.dreadium_samurai_helmet, new Tuple(Integer.valueOf(5), Integer.valueOf(5)));
    coinSellingList.put(ACItems.plated_coralium_chestplate, new Tuple(Integer.valueOf(8), Integer.valueOf(8)));
    coinSellingList.put(ACItems.dreadium_samurai_chestplate, new Tuple(Integer.valueOf(8), Integer.valueOf(8)));
    coinSellingList.put(ACItems.plated_coralium_leggings, new Tuple(Integer.valueOf(7), Integer.valueOf(7)));
    coinSellingList.put(ACItems.dreadium_samurai_leggings, new Tuple(Integer.valueOf(7), Integer.valueOf(7)));
    coinSellingList.put(ACItems.staff_of_rending, new Tuple(Integer.valueOf(10), Integer.valueOf(10)));
  }
  
  public void applyRandomTrade(Random rand) {
    int trade = rand.nextInt(7);
    setProfession(trade);
  }
}
