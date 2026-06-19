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
  private static final DataParameter<Integer> PROFESSION = EntityDataManager.createKey(EntityRemnant.class, DataSerializers.VARINT);
  
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
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(3, new EntityAIFollowLeader(this, 1.2D, 24.0F, 9.0F));
    this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
    this.tasks.addTask(2, new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.tasks.addTask(7, new EntityAIWander(this, 0.8D, 80));
    this.tasks.addTask(8, new EntityAIWatchClosest2(this, EntityJzahar.class, 8.0F, 1.0F));
    this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    this.isOffensive = true;
    setSize(0.5F, 1.95F);
  }
  
  public int getNextLevelRequirement() {
    return 300;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
    } 
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityRemnant(this.world);
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    swingArm(EnumHand.MAIN_HAND);
    swingArm(EnumHand.OFF_HAND);
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return flag;
  }
  
  protected void updateAITasks() {
    if (!isTrading() && this.timeUntilReset > 0) {
      this.timeUntilReset--;
      if (this.timeUntilReset <= 0) {
        if (this.needsInitilization) {
          if (this.tradingList.size() > 1) {
              for (MerchantRecipe merchantrecipe : this.tradingList) {
                  merchantrecipe.increaseMaxTradeUses(2048);
              }
          } 
          addDefaultEquipmentAndRecipies(1);
          this.needsInitilization = false;
        } 
        addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
      } 
    } 
    super.updateAITasks();
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    if (isEntityAlive() && !player.isSneaking())
      if (EntityUtil.hasNecronomicon(player) && ownsTheirBook(player)) {
        if (!isTrading()) {
          if (!this.world.isRemote) {
            setCustomer(player);
            player.displayVillagerTradeGui(this);
            player.sendMessage(new TextComponentString(getName() + ": " + I18n.translateToLocal("message.remnanthelpful.trade")));
            return true;
          } 
        } else if (!this.tradingPlayer.getUniqueID().equals(player.getUniqueID())) {
          player.sendMessage(new TextComponentString(getName() + ": " + I18n.translateToLocal("message.remnanthelpful.busy")));
        } 
      } else {
        advice(player);
        return true;
      }  
    return false;
  }
  
  private boolean ownsTheirBook(EntityPlayer player) {
    for (ItemStack stack : player.inventory.mainInventory) {
      if (stack != null && stack.getItem() instanceof ItemNecronomicon && !((ItemNecronomicon)stack.getItem()).isOwner(player, stack))
        return false; 
    } 
    return true;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(PROFESSION, 0);
  }
  
  private void advice(EntityPlayer player) {
    int insultNum = this.world.rand.nextInt(5);
    String translated = getName() + ": " + String.format(I18n.translateToLocal("message.remnanthelpful.advice." + insultNum), player.getName());
    if (this.world.isRemote) {
      List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, player.getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D));
      if (players != null) {
          for (EntityPlayer player1 : players) {
              player1.sendMessage(new TextComponentString(translated));
          }
      } 
    } 
  }
  
  public void enrage(boolean call, EntityLivingBase enemy) {
    setAttackTarget(enemy);
    enrage(call);
  }
  
  public void enrage(boolean call) {
    if (call) {
      List<EntityRemnant> friends = this.world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D));
      if (friends != null) {
          for (EntityRemnant friend : friends) friend.enrage(false, getAttackTarget());
      } 
      playSound(ACSounds.remnant_scream, 3.0F, 1.0F);
    } 
    setAngry();
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource.getTrueSource() instanceof EntityLivingBase && !false) {
      if (getAttackTarget() != par1DamageSource.getTrueSource()) {
        setAttackTarget((EntityLivingBase)par1DamageSource.getTrueSource());
        enrage(true, getAttackTarget());
      } 
      if (!isAngry()) {
        enrage(true);
      } else {
        enrage((this.rand.nextInt(10) == 0));
      } 
    } 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (isAngry()) {
      this.timer--;
      if (this.timer <= 0)
        this.timer = 0; 
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    par1NBTTagCompound.setInteger("Profession", getProfession());
    par1NBTTagCompound.setInteger("Money", this.wealth);
    par1NBTTagCompound.setInteger("AngerTimer", this.timer);
    if (this.tradingList != null)
      par1NBTTagCompound.setTag("Offers", this.tradingList.getRecipiesAsTags());
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    setProfession(par1NBTTagCompound.getInteger("Profession"));
    this.wealth = par1NBTTagCompound.getInteger("Money");
    this.timer = par1NBTTagCompound.getInteger("AngerTimer");
    if (par1NBTTagCompound.hasKey("Offers", 10)) {
      NBTTagCompound nbttagcompound1 = par1NBTTagCompound.getCompoundTag("Offers");
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
  
  protected Item getDropItem() {
    return ACItems.eldritch_scale;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_REMNANT;
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  protected SoundEvent getAmbientSound() {
    return (getProfession() == 2) ? ACSounds.remnant_priest_chant : null;
  }
  
  protected SoundEvent getHurtSound(DamageSource souce) {
    return ACSounds.remnant_scream;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.shadow_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
  }
  
  public void setProfession(int par1) {
    this.dataManager.set(PROFESSION, par1);
  }
  
  public int getProfession() {
    return this.dataManager.get(PROFESSION);
  }
  
  public void setCustomer(EntityPlayer var1) {
    this.tradingPlayer = var1;
  }
  
  public EntityPlayer getCustomer() {
    return this.tradingPlayer;
  }
  
  public boolean isTrading() {
    return (this.tradingPlayer != null);
  }
  
  public MerchantRecipeList getRecipes(EntityPlayer var1) {
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
      this.field_82191_bN = MathHelper.sqrt(this.tradingList.size()) * 0.2F;
    } else {
      this.field_82191_bN = 0.0F;
    } 
    MerchantRecipeList list = new MerchantRecipeList();
    switch (getProfession()) {
      case 0:
        addItemTrade(list, Items.WHEAT, this.rand, adjustProbability(0.9F));
        addItemTrade(list, Item.getItemFromBlock(Blocks.WOOL), this.rand, adjustProbability(0.5F));
        addItemTrade(list, Items.CHICKEN, this.rand, adjustProbability(0.5F));
        addItemTrade(list, Items.COOKED_FISH, this.rand, adjustProbability(0.4F));
        addCoinTrade(list, Items.BREAD, this.rand, adjustProbability(0.9F));
        addCoinTrade(list, Items.MELON, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, Items.APPLE, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, Items.COOKIE, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, Items.SHEARS, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, Items.FLINT_AND_STEEL, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.fish_on_a_plate, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, Items.ARROW, this.rand, adjustProbability(0.5F));
        if (this.rand.nextFloat() < adjustProbability(0.5F))
          list.add(new MerchantRecipe(new ItemStack(Blocks.GRAVEL, 10), new ItemStack(ACItems.elder_engraved_coin), new ItemStack(Items.FLINT, 4 + this.rand.nextInt(2), 0))); 
        break;
      case 1:
        addItemTrade(list, Items.PAPER, this.rand, adjustProbability(0.8F));
        addItemTrade(list, Items.BOOK, this.rand, adjustProbability(0.8F));
        addItemTrade(list, Items.WRITTEN_BOOK, this.rand, adjustProbability(0.3F));
        addItemTrade(list, Items.ROTTEN_FLESH, this.rand, adjustProbability(0.7F));
        addItemTrade(list, ACItems.coralium_plagued_flesh, this.rand, adjustProbability(0.7F));
        addItemTrade(list, ACItems.dread_fragment, this.rand, adjustProbability(0.7F));
        addItemTrade(list, ACItems.omothol_flesh, this.rand, adjustProbability(0.7F));
        addItemTrade(list, ACItems.rotten_anti_flesh, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, Item.getItemFromBlock(Blocks.BOOKSHELF), this.rand, adjustProbability(0.8F));
        addCoinTrade(list, Item.getItemFromBlock(Blocks.GLASS), this.rand, adjustProbability(0.2F));
        addCoinTrade(list, Items.COMPASS, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, Items.CLOCK, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.necronomicon, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.abyssal_wasteland_necronomicon, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadlands_necronomicon, this.rand, adjustProbability(0.1F));
        break;
      case 2:
        addCoinTrade(list, Items.ENDER_EYE, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, Items.EXPERIENCE_BOTTLE, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, Items.REDSTONE, this.rand, adjustProbability(0.4F));
        addCoinTrade(list, Item.getItemFromBlock(Blocks.GLOWSTONE), this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ritual_charm, this.rand, adjustProbability(0.4F));
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.cthulhu_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.hastur_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.jzahar_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.azathoth_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.nyarlathotep_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.yog_sothoth_charm, 1);
        addCoinTrade(list, ACItems.elder_engraved_coin, 8, ACItems.shub_niggurath_charm, 1);
        addCoinTrade(list, ACItems.staff_of_rending, this.rand, adjustProbability(0.1F));
        aitem = new Item[] { ACItems.ethaxium_sword, ACItems.ethaxium_chestplate, ACItems.ethaxium_axe, ACItems.ethaxium_pickaxe, ACItems.ethaxium_shovel };
        aitem1 = aitem;
        j = aitem.length;
        k = 0;
        while (k < j) {
          Item item = aitem1[k];
          if (this.rand.nextFloat() < adjustProbability(0.05F))
            list.add(new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(ACItems.elder_engraved_coin, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(item, 1, 0), 15 + this.rand.nextInt(15), true))); 
          k++;
        } 
        break;
      case 3:
        addItemTrade(list, Items.COAL, this.rand, adjustProbability(0.7F));
        addItemTrade(list, ACItems.abyssalnite_ingot, this.rand, adjustProbability(0.5F));
        addItemTrade(list, ACItems.refined_coralium_ingot, this.rand, adjustProbability(0.5F));
        addItemTrade(list, ACItems.dreadium_ingot, this.rand, adjustProbability(0.5F));
        addItemTrade(list, ACItems.ethaxium_ingot, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_sword, this.rand, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_axe, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_pickaxe, this.rand, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_shovel, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_hoe, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_boots, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_helmet, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_chestplate, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_leggings, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.blank_engraving, this.rand, adjustProbability(0.2F));
        break;
      case 4:
        addItemTrade(list, Items.COAL, this.rand, adjustProbability(0.7F));
        addItemTrade(list, Items.PORKCHOP, this.rand, adjustProbability(0.5F));
        addItemTrade(list, Items.BEEF, this.rand, adjustProbability(0.5F));
        addItemTrade(list, Items.CHICKEN, this.rand, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.washcloth, this.rand, adjustProbability(0.4F));
        addCoinTrade(list, ACItems.mre, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.pork_on_a_plate, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.beef_on_a_plate, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.chicken_on_a_plate, this.rand, adjustProbability(0.3F));
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
        addItemTrade(list, Items.COAL, this.rand, adjustProbability(0.7F));
        addItemTrade(list, ACItems.abyssalnite_ingot, this.rand, adjustProbability(0.5F));
        addItemTrade(list, ACItems.refined_coralium_ingot, this.rand, adjustProbability(0.5F));
        addItemTrade(list, ACItems.dreadium_ingot, this.rand, adjustProbability(0.5F));
        addItemTrade(list, ACItems.ethaxium_ingot, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_sword, this.rand, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_axe, this.rand, adjustProbability(0.3F));
        addCoinTrade(list, ACItems.ethaxium_pickaxe, this.rand, adjustProbability(0.5F));
        addCoinTrade(list, ACItems.ethaxium_shovel, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_hoe, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_boots, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_boots, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_helmet, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_helmet, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_chestplate, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_chestplate, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.plated_coralium_leggings, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.dreadium_samurai_leggings, this.rand, adjustProbability(0.2F));
        addCoinTrade(list, ACItems.ethaxium_boots, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_helmet, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_chestplate, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.ethaxium_leggings, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.cthulhu_engraving, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.hastur_engraving, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.jzahar_engraving, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.azathoth_engraving, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.nyarlathotep_engraving, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.yog_sothoth_engraving, this.rand, adjustProbability(0.1F));
        addCoinTrade(list, ACItems.shub_niggurath_engraving, this.rand, adjustProbability(0.1F));
        break;
    } 
    if (list.isEmpty())
      addItemTrade(list, Items.GOLD_INGOT, this.rand, 1.0F); 
    Collections.shuffle(list);
    if (this.tradingList == null)
      this.tradingList = new MerchantRecipeList(); 
    for (int l = 0; l < par1 && l < list.size(); l++)
      addToListWithCheck(this.tradingList, list.get(l));
  }
  
  @SideOnly(Side.CLIENT)
  public void setRecipes(MerchantRecipeList var1) {}
  
  public World getWorld() {
    return this.world;
  }
  
  public BlockPos getPos() {
    return new BlockPos(this);
  }
  
  public void useRecipe(MerchantRecipe var1) {
    var1.incrementToolUses();
    if (var1.getItemToSell().getItem() instanceof net.minecraft.item.ItemTool || var1
      .getItemToSell().getItem() instanceof net.minecraft.item.ItemArmor) {
      var1.incrementToolUses();
      var1.incrementToolUses();
      var1.incrementToolUses();
    } 
    if (var1.getItemToBuy().getItem() instanceof ItemNecronomicon || var1
      .getItemToBuy().getItem() instanceof com.shinoow.abyssalcraft.common.items.ItemDrainStaff)
      var1.compensateToolUses(); 
    this.livingSoundTime = -getTalkInterval();
    playSound(ACSounds.remnant_yes, getSoundVolume(), getSoundPitch());
    this.timeUntilReset = 40;
    this.needsInitilization = true;
    if (APIUtils.isCoin(var1.getItemToBuy()))
      this.wealth += var1.getItemToBuy().getCount(); 
    int i = 10 + this.rand.nextInt(10);
    if (var1.getRewardsExp())
      this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
  }
  
  private void addToListWithCheck(MerchantRecipeList list, MerchantRecipe recipe) {
    for (int i = 0; i < list.size(); i++) {
      MerchantRecipe merchantrecipe1 = list.get(i);
      if (hasSameIDsAs(recipe, merchantrecipe1)) {
        if (hasSameItemsAs(recipe, merchantrecipe1))
          list.set(i, recipe); 
        return;
      } 
    } 
    list.add(recipe);
  }
  
  private boolean hasSameIDsAs(MerchantRecipe r1, MerchantRecipe r2) {
    return (r1.getItemToBuy().getItem() == r2.getItemToBuy().getItem() && r1.getItemToSell().getItem() == r2.getItemToSell().getItem()) ? (((r1
      .getSecondItemToBuy().isEmpty() && r2.getSecondItemToBuy().isEmpty()) || (!r1.getSecondItemToBuy().isEmpty() && !r2.getSecondItemToBuy().isEmpty() && r1
      .getSecondItemToBuy().getItem() == r2.getSecondItemToBuy().getItem()))) : false;
  }
  
  private boolean hasSameItemsAs(MerchantRecipe r1, MerchantRecipe r2) {
    return (hasSameIDsAs(r1, r2) && (r1.getItemToBuy().getCount() < r2.getItemToBuy().getCount() || (
      !r1.getSecondItemToBuy().isEmpty() && r1.getSecondItemToBuy().getCount() < r2.getSecondItemToBuy().getCount())));
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
    return (tuple == null) ? 1 : (((Integer) tuple.getFirst() >= (Integer) tuple.getSecond()) ? (Integer) tuple.getFirst() : ((Integer) tuple.getFirst() + rand.nextInt((Integer) tuple.getSecond() - (Integer) tuple.getFirst())));
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
    return (tuple == null) ? 1 : (((Integer) tuple.getFirst() >= (Integer) tuple.getSecond()) ? (Integer) tuple.getFirst() : ((Integer) tuple.getFirst() + par2.nextInt((Integer) tuple.getSecond() - (Integer) tuple.getFirst())));
  }
  
  public void verifySellingItem(ItemStack par1ItemStack) {
    if (!this.world.isRemote && this.livingSoundTime > -getTalkInterval() + 20) {
      this.livingSoundTime = -getTalkInterval();
      if (!par1ItemStack.isEmpty()) {
        playSound(ACSounds.remnant_yes, getSoundVolume(), getSoundPitch());
        if (getCustomer() != null)
          getCustomer().sendMessage(new TextComponentString(getName() + ": " + I18n.translateToLocal("message.remnanthelpful.trade.success")));
      } else {
        playSound(ACSounds.remnant_no, getSoundVolume(), getSoundPitch());
        if (getCustomer() != null)
          getCustomer().sendMessage(new TextComponentString(getName() + ": " + I18n.translateToLocal("message.remnanthelpful.trade.rejected")));
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
    addCoinTrade(list, buy, ItemStack.EMPTY, sell);
  }
  
  public void addCoinTrade(MerchantRecipeList list, ItemStack buy1, ItemStack buy2, ItemStack sell) {
    list.add(new MerchantRecipe(buy1, buy2, sell));
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data) {
    data = super.onInitialSpawn(difficulty, data);
    applyRandomTrade(this.world.rand);
    if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
      Calendar calendar = this.world.getCurrentDate();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    return data;
  }
  
  static {
    itemSellingList.put(Items.COAL, new Tuple(4, 6));
    itemSellingList.put(ACItems.abyssalnite_ingot, new Tuple(4, 6));
    itemSellingList.put(ACItems.refined_coralium_ingot, new Tuple(2, 4));
    itemSellingList.put(ACItems.dreadium_ingot, new Tuple(1, 2));
    itemSellingList.put(ACItems.ethaxium_ingot, new Tuple(1, 1));
    itemSellingList.put(Items.PAPER, new Tuple(6, 8));
    itemSellingList.put(Items.BOOK, new Tuple(2, 4));
    itemSellingList.put(Items.WRITTEN_BOOK, new Tuple(1, 1));
    itemSellingList.put(Items.ENDER_PEARL, new Tuple(1, 1));
    itemSellingList.put(Items.ENDER_EYE, new Tuple(1, 1));
    itemSellingList.put(Items.PORKCHOP, new Tuple(4, 8));
    itemSellingList.put(Items.BEEF, new Tuple(4, 8));
    itemSellingList.put(Items.CHICKEN, new Tuple(4, 8));
    itemSellingList.put(Items.COOKED_FISH, new Tuple(4, 8));
    itemSellingList.put(Items.WHEAT_SEEDS, new Tuple(12, 16));
    itemSellingList.put(Items.MELON_SEEDS, new Tuple(12, 16));
    itemSellingList.put(Items.PUMPKIN_SEEDS, new Tuple(12, 16));
    itemSellingList.put(Items.WHEAT, new Tuple(8, 12));
    itemSellingList.put(Item.getItemFromBlock(Blocks.WOOL), new Tuple(4, 8));
    itemSellingList.put(Items.ROTTEN_FLESH, new Tuple(12, 16));
    itemSellingList.put(ACItems.coralium_plagued_flesh, new Tuple(8, 12));
    itemSellingList.put(ACItems.coralium_plagued_flesh_on_a_bone, new Tuple(8, 12));
    itemSellingList.put(ACItems.dread_fragment, new Tuple(16, 28));
    itemSellingList.put(ACItems.omothol_flesh, new Tuple(8, 16));
    itemSellingList.put(ACItems.rotten_anti_flesh, new Tuple(2, 4));
    coinSellingList.put(Items.DIAMOND, new Tuple(1, 1));
    coinSellingList.put(Items.FLINT_AND_STEEL, new Tuple(1, 1));
    coinSellingList.put(Items.SHEARS, new Tuple(1, 1));
    coinSellingList.put(ACItems.ethaxium_sword, new Tuple(6, 8));
    coinSellingList.put(ACItems.ethaxium_axe, new Tuple(6, 8));
    coinSellingList.put(ACItems.ethaxium_pickaxe, new Tuple(6, 8));
    coinSellingList.put(ACItems.ethaxium_shovel, new Tuple(6, 8));
    coinSellingList.put(ACItems.ethaxium_hoe, new Tuple(6, 8));
    coinSellingList.put(ACItems.ethaxium_boots, new Tuple(4, 4));
    coinSellingList.put(ACItems.ethaxium_helmet, new Tuple(5, 5));
    coinSellingList.put(ACItems.ethaxium_chestplate, new Tuple(8, 8));
    coinSellingList.put(ACItems.ethaxium_leggings, new Tuple(7, 7));
    coinSellingList.put(Items.BREAD, new Tuple(-8, -4));
    coinSellingList.put(Items.MELON, new Tuple(-12, -6));
    coinSellingList.put(Items.APPLE, new Tuple(-12, -6));
    coinSellingList.put(Items.COOKIE, new Tuple(-24, -16));
    coinSellingList.put(Item.getItemFromBlock(Blocks.GLASS), new Tuple(-12, -8));
    coinSellingList.put(Item.getItemFromBlock(Blocks.BOOKSHELF), new Tuple(1, 1));
    coinSellingList.put(ACItems.washcloth, new Tuple(1, 1));
    coinSellingList.put(ACItems.mre, new Tuple(4, 6));
    coinSellingList.put(Items.EXPERIENCE_BOTTLE, new Tuple(-4, -4));
    coinSellingList.put(Items.REDSTONE, new Tuple(-27, -9));
    coinSellingList.put(Items.COMPASS, new Tuple(1, 1));
    coinSellingList.put(Items.CLOCK, new Tuple(2, 2));
    coinSellingList.put(ACItems.necronomicon, new Tuple(1, 2));
    coinSellingList.put(ACItems.abyssal_wasteland_necronomicon, new Tuple(2, 4));
    coinSellingList.put(ACItems.dreadlands_necronomicon, new Tuple(4, 8));
    coinSellingList.put(Item.getItemFromBlock(Blocks.GLOWSTONE), new Tuple(-9, -9));
    coinSellingList.put(ACItems.pork_on_a_plate, new Tuple(-12, -8));
    coinSellingList.put(ACItems.beef_on_a_plate, new Tuple(-12, -8));
    coinSellingList.put(ACItems.chicken_on_a_plate, new Tuple(-12, -8));
    coinSellingList.put(ACItems.fish_on_a_plate, new Tuple(-12, -8));
    coinSellingList.put(Items.COOKED_CHICKEN, new Tuple(-16, -12));
    coinSellingList.put(Items.ENDER_EYE, new Tuple(2, 2));
    coinSellingList.put(Items.ARROW, new Tuple(-32, -24));
    coinSellingList.put(ACItems.plated_coralium_boots, new Tuple(4, 4));
    coinSellingList.put(ACItems.dreadium_samurai_boots, new Tuple(4, 4));
    coinSellingList.put(ACItems.plated_coralium_helmet, new Tuple(5, 5));
    coinSellingList.put(ACItems.dreadium_samurai_helmet, new Tuple(5, 5));
    coinSellingList.put(ACItems.plated_coralium_chestplate, new Tuple(8, 8));
    coinSellingList.put(ACItems.dreadium_samurai_chestplate, new Tuple(8, 8));
    coinSellingList.put(ACItems.plated_coralium_leggings, new Tuple(7, 7));
    coinSellingList.put(ACItems.dreadium_samurai_leggings, new Tuple(7, 7));
    coinSellingList.put(ACItems.staff_of_rending, new Tuple(10, 10));
  }
  
  public void applyRandomTrade(Random rand) {
    int trade = rand.nextInt(7);
    setProfession(trade);
  }
}

