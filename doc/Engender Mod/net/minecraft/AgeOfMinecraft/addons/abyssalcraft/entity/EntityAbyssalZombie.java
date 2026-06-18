package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityChicken;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Loader;

public class EntityAbyssalZombie extends EntityTameBase implements Undead {
  private static final DataParameter<Integer> TYPE = EntityDataManager.func_187226_a(EntityAbyssalZombie.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Byte> CHILD = EntityDataManager.func_187226_a(EntityAbyssalZombie.class, DataSerializers.field_187191_a);
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);
  
  private int helmetCount = 1;
  
  public EntityAbyssalZombie(World par1World) {
    super(par1World);
    func_70105_a(0.5F, 1.95F);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 9.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public boolean isASwarmingMob() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityAbyssalZombie(this.field_70170_p);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(42.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(50.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(12.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(25.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(6.0D);
    } 
  }
  
  public void func_70645_a(DamageSource par1DamageSource) {
    super.func_70645_a(par1DamageSource);
    if (!this.field_70170_p.field_72995_K && getLimitedLife() <= 0)
      for (int i = 0; i < this.helmetCount; i++) {
        func_145779_a((Item)Items.field_151024_Q, 1);
        this.helmetCount--;
      }  
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(CHILD, Byte.valueOf((byte)0));
    this.field_70180_af.func_187214_a(TYPE, Integer.valueOf(0));
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public int getZombieType() {
    return ((Integer)this.field_70180_af.func_187225_a(TYPE)).intValue();
  }
  
  public void setZombieType(int par1) {
    this.field_70180_af.func_187227_b(TYPE, Integer.valueOf(par1));
  }
  
  public void performSpecialAttack() {
    setSpecialAttackTimer(700);
  }
  
  protected boolean func_70610_aX() {
    return (getSpecialAttackTimer() > 600 || super.func_70610_aX());
  }
  
  public void func_70636_d() {
    if (isHero() && getSpecialAttackTimer() > 600) {
      this.field_70159_w = 0.0D;
      this.field_70179_y = 0.0D;
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!func_184191_r((Entity)entity)) {
              entity.field_70172_ad = 0;
              entity.func_70097_a(DamageSource.field_82727_n, 0.05F);
              if (EngenderConfig.general.useMessage && !entity.func_70089_S() && !isWild())
                getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was yelled at to death by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
            }  
        }  
    } 
    if (isHero() && getSpecialAttackTimer() > 600 && getSpecialAttackTimer() < 660)
      this.field_70125_A = -50.0F; 
    if (isHero() && getSpecialAttackTimer() == 600) {
      List<EntityTameBase> list = this.field_70170_p.func_175647_a(EntityTameBase.class, func_174813_aQ().func_186662_g(32.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityTameBase entity = list.get(i1);
          if (entity != null)
            if (func_184191_r((Entity)entity)) {
              this.moralRaisedTimer = 600;
              entity.moralRaisedTimer = 600;
            }  
        }  
    } 
    if (isHero() && getSpecialAttackTimer() == 660)
      if (func_70631_g_()) {
        func_184185_a(ESound.zombieSpecial, 10.0F, 1.25F);
      } else {
        func_184185_a(ESound.zombieSpecial, 10.0F, 0.75F);
      }  
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (this.helmetCount < 0)
      this.helmetCount = 0; 
    if ((func_70631_g_() || isHero()) && this.helmetCount != 0) {
      if (!this.field_70170_p.field_72995_K)
        func_145779_a((Item)Items.field_151024_Q, 1); 
      this.helmetCount--;
    } 
    if (this.field_70170_p.func_72935_r() && !this.field_70170_p.field_72995_K && !func_70631_g_() && !func_70045_F() && !isHero()) {
      float f = func_70013_c();
      if (f > 0.5F && this.field_70173_aa % (!func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() ? 80 : 10) == 0 && this.field_70170_p.func_175678_i(new BlockPos(this.field_70165_t, this.field_70163_u + func_70047_e(), this.field_70161_v))) {
        boolean flag = true;
        ItemStack itemstack = func_184582_a(EntityEquipmentSlot.HEAD);
        if (!itemstack.func_190926_b()) {
          if (itemstack.func_77984_f()) {
            itemstack.func_77964_b(itemstack.func_77952_i() + this.field_70146_Z.nextInt(2));
            if (itemstack.func_77952_i() >= itemstack.func_77958_k()) {
              func_70669_a(itemstack);
              func_184201_a(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
            } 
          } 
          flag = false;
        } 
        if (flag)
          if (this.helmetCount > 0) {
            func_184609_a(EnumHand.MAIN_HAND);
            func_184609_a(EnumHand.OFF_HAND);
            this.helmetCount--;
            func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((Item)Items.field_151024_Q));
            func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
          } else {
            func_70015_d(8);
          }  
      } 
    } 
    super.func_70636_d();
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    boolean flag = super.func_70652_k(par1Entity);
    if (flag) {
      float f = this.field_70170_p.func_175649_E(new BlockPos((Entity)this)).func_180168_b();
      if (func_184582_a(EntityEquipmentSlot.MAINHAND) != null && (func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() == Items.field_151033_d || func_184582_a(EntityEquipmentSlot.MAINHAND).func_77973_b() == Items.field_151059_bz))
        par1Entity.func_70015_d(12); 
      if (func_70027_ad() && this.field_70146_Z.nextFloat() < f * 0.5F)
        par1Entity.func_70015_d(5 * (int)f); 
      if (flag)
        if (par1Entity instanceof EntityLivingBase && 
          !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
          ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));  
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 1.5F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected SoundEvent func_184639_G() {
    return ACSounds.abyssal_zombie_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return ACSounds.abyssal_zombie_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.abyssal_zombie_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187939_hm, 0.15F, 1.0F);
  }
  
  protected Item func_146068_u() {
    return ACItems.coralium_plagued_flesh;
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_ABYSSAL_ZOMBIE;
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    if (par1NBTTagCompound.func_74767_n("IsBaby"))
      setChild(true); 
    if (par1NBTTagCompound.func_74764_b("ZombieType")) {
      byte var2 = par1NBTTagCompound.func_74771_c("ZombieType");
      setZombieType(var2);
    } 
  }
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    if (func_70631_g_())
      par1NBTTagCompound.func_74757_a("IsBaby", true); 
    par1NBTTagCompound.func_74774_a("ZombieType", (byte)getZombieType());
  }
  
  protected void func_175445_a(EntityItem itemEntity) {
    if (!InitHandler.INSTANCE.isItemBlacklisted((Entity)this, itemEntity.func_92059_d()))
      super.func_175445_a(itemEntity); 
  }
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (func_184191_r((Entity)player) && func_70631_g_() && stack.func_190926_b() && player.func_70093_af() && func_184187_bx() == null) {
      List<EntityChicken> list = this.field_70170_p.func_175647_a(EntityChicken.class, func_174813_aQ().func_186662_g(16.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty() && !func_184207_aI())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityChicken entity = list.get(i1);
          if (entity != null && !entity.func_184207_aI() && func_184191_r((Entity)entity) && func_70631_g_() && !this.field_70170_p.field_72995_K) {
            entity.field_70173_aa = 0;
            func_184220_m((Entity)entity);
            func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
            break;
          } 
        }  
      return true;
    } 
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151024_Q) {
      this.helmetCount++;
      func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K)
        stack.func_190918_g(1); 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.HEAD || stack.func_77973_b() == Items.field_151103_aS || stack.func_77973_b() == Item.func_150898_a(Blocks.field_185764_cQ) || stack.func_77973_b() == Items.field_151008_G) && stack.func_77973_b() != Items.field_151024_Q) {
      func_184201_a(EntityEquipmentSlot.HEAD, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.HEAD, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.CHEST).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.CHEST) {
      func_184201_a(EntityEquipmentSlot.CHEST, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.CHEST, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.LEGS).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.LEGS) {
      func_184201_a(EntityEquipmentSlot.LEGS, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.LEGS, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.FEET).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.FEET) {
      func_184201_a(EntityEquipmentSlot.FEET, stack);
      func_184606_a_(stack);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.FEET, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.MAINHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || stack.func_77973_b() == Items.field_151031_f)) {
      func_184185_a(SoundEvents.field_187727_dV, 1.0F, 2.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.MAINHAND, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && !stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.OFFHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || (stack.func_77973_b() instanceof net.minecraft.item.ItemFood && !(stack.func_77973_b() instanceof net.minecraft.item.ItemAppleGold)) || stack.func_77973_b() == Items.field_185167_i || stack.func_77973_b() == Items.field_185159_cQ)) {
      func_184185_a(SoundEvents.field_187727_dV, 1.0F, 2.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        heldItem.func_77982_d(stack.func_77978_p());
        heldItem.func_77964_b(stack.func_77952_i());
        func_184201_a(EntityEquipmentSlot.OFFHAND, heldItem);
        stack.func_190918_g(1);
      } 
      return true;
    } 
    if (func_184191_r((Entity)player) && stack.func_190926_b() && player.func_70093_af()) {
      dropEquipmentUndamaged();
      player.func_184609_a(hand);
      return true;
    } 
    return false;
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    Object data = super.func_180482_a(difficulty, par1EntityLivingData);
    float f = difficulty.func_180170_c();
    if (data == null)
      data = new GroupData((this.field_70170_p.field_73012_v.nextFloat() < ForgeModContainer.zombieBabyChance), null); 
    if (data instanceof GroupData) {
      GroupData groupdata = (GroupData)data;
      if (groupdata.isBaby)
        setGrowingAge(-60000); 
    } 
    func_180481_a(difficulty);
    func_180483_b(difficulty);
    if (func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b()) {
      Calendar calendar1 = this.field_70170_p.func_83015_S();
      if (calendar1.get(2) + 1 == 10 && calendar1.get(5) == 31 && this.field_70146_Z.nextFloat() < 0.25F) {
        func_184201_a(EntityEquipmentSlot.HEAD, new ItemStack((this.field_70146_Z.nextFloat() < 0.1F) ? Blocks.field_150428_aP : Blocks.field_150423_aK));
        this.field_184655_bs[EntityEquipmentSlot.HEAD.func_188454_b()] = 0.0F;
      } 
    } 
    IAttributeInstance attribute = func_110148_a(SharedMonsterAttributes.field_111264_e);
    Calendar calendar = this.field_70170_p.func_83015_S();
    attribute.func_111124_b(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.func_111121_a(attackDamageBoost); 
    return (IEntityLivingData)data;
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts")) {
      EntityMutantZombie mutant = new EntityMutantZombie(this.field_70170_p);
      mutant.setZombieType(3);
      return (EntityTameBase)mutant;
    } 
    return null;
  }
  
  class GroupData implements IEntityLivingData {
    public boolean isBaby;
    
    private GroupData(boolean par2) {
      this.isBaby = false;
      this.isBaby = par2;
    }
    
    GroupData(boolean par2, Object par4EntityZombieINNER1) {
      this(par2);
    }
  }
}
