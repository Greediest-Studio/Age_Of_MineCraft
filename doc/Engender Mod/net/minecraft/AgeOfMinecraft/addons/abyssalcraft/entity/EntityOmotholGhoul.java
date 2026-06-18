package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityOmotholGhoul extends EntityTameBase implements Armored, Undead {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);
  
  public EntityOmotholGhoul(World par1World) {
    super(par1World);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 48.0F, 12.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, false));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    func_70105_a(0.9F, 3.9F);
    this.isOffensive = true;
  }
  
  public int getNextLevelRequirement() {
    return 400;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityOmotholGhoul(this.field_70170_p);
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
  
  public float getBonusVSLight() {
    return 1.25F;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.225D);
    if (ACConfig.hardcoreMode) {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(300.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(30.0D);
    } else {
      func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(150.0D);
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(15.0D);
    } 
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public boolean func_70648_aU() {
    return true;
  }
  
  public boolean func_70652_k(Entity par1Entity) {
    func_184609_a(EnumHand.MAIN_HAND);
    func_184609_a(EnumHand.OFF_HAND);
    if (par1Entity instanceof EntityLivingBase && !func_184191_r(par1Entity)) {
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76421_d, 100));
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76440_q, 20));
      ((EntityLivingBase)par1Entity).func_70690_d(new PotionEffect(MobEffects.field_76439_r, 20));
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this).func_76348_h().func_151518_m(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return super.func_70652_k(par1Entity);
  }
  
  protected float func_70647_i() {
    return super.func_70647_i() - 0.3F;
  }
  
  protected SoundEvent func_184639_G() {
    return ACSounds.ghoul_normal_ambient;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource souce) {
    return ACSounds.ghoul_hurt;
  }
  
  protected SoundEvent func_184615_bR() {
    return ACSounds.ghoul_death;
  }
  
  protected void func_180429_a(BlockPos pos, Block par4) {
    func_184185_a(SoundEvents.field_187939_hm, 0.25F, 0.9F);
  }
  
  protected Item func_146068_u() {
    return ACItems.omothol_flesh;
  }
  
  protected ResourceLocation func_184647_J() {
    return ACLoot.ENTITY_OMOTHOL_GHOUL;
  }
  
  public boolean func_70662_br() {
    return true;
  }
  
  public void func_70636_d() {
    this.isOffensive = true;
    super.func_70636_d();
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.func_180482_a(difficulty, par1EntityLivingData);
    float f = difficulty.func_180170_c();
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
    return par1EntityLivingData;
  }
  
  @Nullable
  public static Item getArmorByChance(EntityEquipmentSlot slotIn, int chance) {
    switch (slotIn) {
      case HEAD:
        if (chance == 0)
          return (Item)Items.field_151161_ac; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_helmet; 
        if (chance == 2)
          return ACItems.plated_coralium_helmet; 
        if (chance == 3)
          return ACItems.dreadium_samurai_helmet; 
        if (chance == 4)
          return ACItems.ethaxium_helmet; 
      case CHEST:
        if (chance == 0)
          return (Item)Items.field_151163_ad; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_chestplate; 
        if (chance == 2)
          return ACItems.plated_coralium_chestplate; 
        if (chance == 3)
          return ACItems.dreadium_samurai_chestplate; 
        if (chance == 4)
          return ACItems.ethaxium_chestplate; 
      case LEGS:
        if (chance == 0)
          return (Item)Items.field_151173_ae; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_leggings; 
        if (chance == 2)
          return ACItems.plated_coralium_leggings; 
        if (chance == 3)
          return ACItems.dreadium_samurai_leggings; 
        if (chance == 4)
          return ACItems.ethaxium_leggings; 
      case FEET:
        if (chance == 0)
          return (Item)Items.field_151175_af; 
        if (chance == 1)
          return ACItems.dreaded_abyssalnite_boots; 
        if (chance == 2)
          return ACItems.plated_coralium_boots; 
        if (chance == 3)
          return ACItems.dreadium_samurai_boots; 
        if (chance == 4)
          return ACItems.ethaxium_boots; 
        break;
    } 
    return null;
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
            Item item = getArmorByChance(entityequipmentslot, i);
            if (item != null)
              func_184201_a(entityequipmentslot, new ItemStack(item)); 
          } 
        } 
      } 
    } 
    if (this.field_70146_Z.nextFloat() < ((this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD) ? 0.5F : 0.1F)) {
      int i = this.field_70146_Z.nextInt(3);
      if (i == 0) {
        func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.ethaxium_sword));
        if (func_70681_au().nextInt(2) > 0)
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.ethaxium_sword)); 
      } else {
        func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack(ACItems.ethaxium_shovel));
        if (func_70681_au().nextInt(2) > 0)
          func_184201_a(EntityEquipmentSlot.OFFHAND, new ItemStack(ACItems.ethaxium_shovel)); 
      } 
    } 
  }
  
  protected SoundEvent getRegularHurtSound() {
    return (func_70658_aO() >= 10) ? ESound.metalHit : ESound.fleshHit;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
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
    if (!stack.func_190926_b() && func_184582_a(EntityEquipmentSlot.HEAD).func_190926_b() && func_184640_d(stack) == EntityEquipmentSlot.HEAD) {
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
