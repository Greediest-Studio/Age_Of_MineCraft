package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityDepthsGhoul extends EntityTameBase implements IRangedAttackMob, Armored, Undead {
  private static final DataParameter<Integer> TYPE = EntityDataManager.func_187226_a(EntityDepthsGhoul.class, DataSerializers.field_187192_b);
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier ghoulHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);
  
  private static final UUID peteUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A8");
  
  private static final AttributeModifier peteSpeedBoost = new AttributeModifier(peteUUID, "Pete Boost", 0.0699999958276749D, 0);
  
  private static final UUID wilsonUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A8");
  
  private static final AttributeModifier wilsonDamageBoost = new AttributeModifier(wilsonUUID, "Pete Boost", 3.0D, 0);
  
  private int helmetCount = 1;
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 1.0D, 30, 24.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.0D, true);
  
  public EntityDepthsGhoul(World par1World) {
    super(par1World);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    func_70105_a(0.75F, 2.9F);
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityDepthsGhoul(this.field_70170_p);
  }
  
  public boolean passesCoraliumPlague() {
    return true;
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(42.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.23000000417232513D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(60.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(30.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(5.0D);
    } 
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public String func_70005_c_() {
    switch (getGhoulType()) {
      case 0:
        return super.func_70005_c_();
      case 1:
        return "Pete";
      case 2:
        return "Mr. Wilson";
      case 3:
        return "Dr. Orange";
    } 
    return super.func_70005_c_();
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(TYPE, Integer.valueOf(0));
  }
  
  public int getGhoulType() {
    return ((Integer)this.field_70180_af.func_187225_a(TYPE)).intValue();
  }
  
  public void setGhoulType(int par1) {
    IAttributeInstance iattributeinstancepete = func_110148_a(SharedMonsterAttributes.field_111263_d);
    IAttributeInstance iattributeinstancewilson = func_110148_a(SharedMonsterAttributes.field_111264_e);
    iattributeinstancepete.func_111124_b(peteSpeedBoost);
    iattributeinstancewilson.func_111124_b(wilsonDamageBoost);
    if (par1 == 1)
      iattributeinstancepete.func_111121_a(peteSpeedBoost); 
    if (par1 == 2)
      iattributeinstancewilson.func_111121_a(wilsonDamageBoost); 
    this.field_70180_af.func_187227_b(TYPE, Integer.valueOf(par1));
  }
  
  public void func_70636_d() {
    this.isOffensive = true;
    if (getGhoulType() == 3) {
      if (func_70638_az() != null && (func_70068_e((Entity)func_70638_az()) > 36.0D || func_70638_az() instanceof net.minecraft.entity.EntityFlying || (func_70638_az()).field_70163_u > this.field_70163_u + 4.0D)) {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiArrowAttack);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiAttackOnCollide);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      } 
    } else {
      this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiAttackOnCollide);
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
    } 
    if (getGhoulType() != 3)
      this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiAttackOnCollide); 
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
  
  public void func_70645_a(DamageSource par1DamageSource) {
    super.func_70645_a(par1DamageSource);
    if (!this.field_70170_p.field_72995_K)
      if (this.helmetCount > 0)
        func_145779_a((Item)Items.field_151024_Q, this.helmetCount);  
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    func_184609_a(EnumHand.MAIN_HAND);
    func_184609_a(EnumHand.OFF_HAND);
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
    switch (getGhoulType()) {
      case 0:
        return ACSounds.ghoul_normal_ambient;
      case 1:
        return ACSounds.ghoul_pete_ambient;
      case 2:
        return ACSounds.ghoul_wilson_ambient;
      case 3:
        return ACSounds.ghoul_orange_ambient;
    } 
    return ACSounds.ghoul_normal_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return ACSounds.ghoul_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.ghoul_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187939_hm, 0.15F, 1.0F);
  }
  
  protected Item func_146068_u() {
    return ACItems.coralium_plagued_flesh_on_a_bone;
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  protected ResourceLocation func_184647_J() {
    switch (getGhoulType()) {
      case 0:
        return ACLoot.ENTITY_DEPTHS_GHOUL;
      case 1:
        return ACLoot.ENTITY_DEPTHS_GHOUL_PETE;
      case 2:
        return ACLoot.ENTITY_DEPTHS_GHOUL_WILSON;
      case 3:
        return ACLoot.ENTITY_DEPTHS_GHOUL_ORANGE;
    } 
    return null;
  }
  
  public void func_70037_a(NBTTagCompound par1NBTTagCompound) {
    super.func_70037_a(par1NBTTagCompound);
    if (par1NBTTagCompound.func_74764_b("GhoulType")) {
      byte var2 = par1NBTTagCompound.func_74771_c("GhoulType");
      setGhoulType(var2);
    } 
    if (par1NBTTagCompound.func_150297_b("Helmets", 99))
      this.helmetCount = par1NBTTagCompound.func_74762_e("Helmets"); 
  }
  
  public void func_70014_b(NBTTagCompound par1NBTTagCompound) {
    super.func_70014_b(par1NBTTagCompound);
    par1NBTTagCompound.func_74774_a("GhoulType", (byte)getGhoulType());
    par1NBTTagCompound.func_74768_a("Helmets", this.helmetCount);
  }
  
  protected void func_180481_a(DifficultyInstance difficulty) {
    if (this.field_70146_Z.nextFloat() < 0.5F) {
      int i = this.field_70146_Z.nextInt(2);
      float f = (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.75F : 0.5F;
      if (this.field_70146_Z.nextFloat() < 0.25F)
        i++; 
      if (this.field_70146_Z.nextFloat() < 0.125F)
        i++; 
      if (this.field_70146_Z.nextFloat() < 0.0625F)
        i++; 
      boolean flag = true;
      for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values()) {
        if (entityequipmentslot.func_188453_a() == EntityEquipmentSlot.Type.ARMOR) {
          ItemStack itemstack = func_184582_a(entityequipmentslot);
          if (!flag && this.field_70146_Z.nextFloat() < f)
            break; 
          flag = false;
          if (itemstack.func_190926_b()) {
            Item item = func_184636_a(entityequipmentslot, i);
            if (item != null)
              func_184201_a(entityequipmentslot, new ItemStack(item)); 
          } 
        } 
      } 
    } 
    if (this.field_70146_Z.nextFloat() < ((this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.5F : 0.1F)) {
      int i = this.field_70146_Z.nextInt(3);
      if (i == 0) {
        func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.refined_coralium_sword));
        if (func_70681_au().nextInt(2) > 0)
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.refined_coralium_sword)); 
      } else {
        func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.refined_coralium_shovel));
        if (func_70681_au().nextInt(2) > 0)
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.refined_coralium_shovel)); 
      } 
    } 
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    Object data = super.func_180482_a(difficulty, par1EntityLivingData);
    int type = 0;
    if (this.field_70170_p.field_73012_v.nextFloat() < 0.2F) {
      int temp = this.field_70170_p.field_73012_v.nextInt(4);
      if (temp < 4)
        type = temp; 
    } 
    setGhoulType(type);
    float f = difficulty.func_180170_c();
    if (this.field_70170_p.field_73012_v.nextFloat() < ForgeModContainer.zombieBabyChance) {
      setChild(true);
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
    attribute.func_111124_b(ghoulHDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.func_111121_a(ghoulHDamageBoost); 
    return (IEntityLivingData)data;
  }
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    func_184185_a(ACSounds.ghoul_orange_ambient, func_70599_aP(), func_70647_i());
    for (int i = 0; i < (isHero() ? 5 : 1); i++) {
      EntitySquads squads = new EntitySquads(this.field_70170_p, (EntityLivingBase)this);
      double d1 = target.field_70165_t + (isHero() ? (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) : 0.0D) - this.field_70165_t;
      double d2 = target.field_70163_u + (isHero() ? (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) : 0.0D) - 0.5D - this.field_70163_u + 2.25D;
      double d3 = target.field_70161_v + (isHero() ? (this.field_70146_Z.nextDouble() * 2.0D - 1.0D) : 0.0D) - this.field_70161_v;
      float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.2F;
      squads.func_70186_c(d1, d2 + f, d3, 1.6F, 1.0F);
      this.field_70170_p.func_72838_d((Entity)squads);
      float f2 = MathHelper.func_76133_a(d1 * d1 + d2 * d2 + d3 * d3);
      squads.field_70159_w = d1 / f2 * 0.8D * 0.8D + squads.field_70159_w;
      squads.field_70181_x = d2 / f2 * 0.8D * 0.8D + squads.field_70181_x;
      squads.field_70179_y = d3 / f2 * 0.8D * 0.8D + squads.field_70179_y;
    } 
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (func_70658_aO() >= 10) ? ESound.metalHit : ESound.fleshHit;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151024_Q) {
      this.helmetCount++;
      func_184185_a(SoundEvents.field_187728_s, 1.0F, 1.0F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K)
        stack.func_190918_g(1); 
      return true;
    } 
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.MAINHAND).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.MAINHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || stack.func_77973_b() == Items.field_151031_f)) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.OFFHAND).func_190926_b() && (func_184640_d(stack) == EntityEquipmentSlot.OFFHAND || stack.func_77973_b() instanceof net.minecraft.item.ItemSword || stack.func_77973_b() instanceof net.minecraft.item.ItemTool || (stack.func_77973_b() instanceof net.minecraft.item.ItemFood && !(stack.func_77973_b() instanceof net.minecraft.item.ItemAppleGold)) || stack.func_77973_b() == Items.field_185167_i || stack.func_77973_b() == Items.field_185159_cQ)) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.HEAD && stack.func_77973_b() != Items.field_151024_Q) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.CHEST).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.CHEST) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.LEGS).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.LEGS) {
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.FEET).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.FEET) {
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
    if (func_184191_r((Entity)player) && stack.func_190926_b() && player.func_70093_af()) {
      dropEquipmentUndamaged();
      player.func_184609_a(hand);
      return true;
    } 
    return false;
  }
}
