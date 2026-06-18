package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class EnchantmentDisintigration extends Enchantment {
  public EnchantmentDisintigration(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
    super(rarityIn, EnumEnchantmentType.WEAPON, slots);
  }
  
  public int getMinEnchantability(int enchantmentLevel) {
    return 14;
  }
  
  public int getMaxEnchantability(int enchantmentLevel) {
    return super.getMinEnchantability(enchantmentLevel) + 30;
  }
  
  public int getMaxLevel() {
    return 1;
  }
  
  public String getTranslatedName(int level) {
    String s = TextFormatting.ITALIC + I18n.translateToLocal(getName());
    return (level == 1 && getMaxLevel() == 1) ? s : (s + " " + I18n.translateToLocal("enchantment.level." + level));
  }
  
  public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
    return (creatureType == EnumCreatureAttribute.UNDEAD) ? 6.0F : 1.0F;
  }
  
  public String getName() {
    return "enchantment.disintigration";
  }
  
  public boolean canApply(ItemStack stack) {
    return (stack.getItem() instanceof net.minecraft.item.ItemAxe) ? true : super.canApply(stack);
  }
  
  public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
    if (target instanceof EntityLivingBase) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)target;
      if (!entitylivingbase.world.isRemote && entitylivingbase.isNonBoss() && !entitylivingbase.isEntityAlive() && entitylivingbase.isEntityUndead()) {
        entitylivingbase.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2.0F, 0.5F);
        entitylivingbase.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2.0F, 0.75F);
        entitylivingbase.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2.0F, 1.25F);
        entitylivingbase.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 2.0F, 1.75F);
        entitylivingbase.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 2.0F, 2.0F);
        entitylivingbase.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 2.0F, 1.75F);
        entitylivingbase.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 2.0F, 1.5F);
        entitylivingbase.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH, 2.0F, 1.25F);
        entitylivingbase.world.setEntityState((Entity)entitylivingbase, (byte)20);
        entitylivingbase.world.setEntityState((Entity)entitylivingbase, (byte)20);
        entitylivingbase.world.setEntityState((Entity)entitylivingbase, (byte)20);
        entitylivingbase.world.setEntityState((Entity)entitylivingbase, (byte)20);
        entitylivingbase.setDead();
      } 
    } 
  }
}
