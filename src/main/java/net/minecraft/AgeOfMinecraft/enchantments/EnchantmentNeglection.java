package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class EnchantmentNeglection extends Enchantment {
  public EnchantmentNeglection(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
    super(rarityIn, EnumEnchantmentType.WEAPON, slots);
  }
  
  public int getMinEnchantability(int enchantmentLevel) {
    return enchantmentLevel * 30;
  }
  
  public int getMaxEnchantability(int enchantmentLevel) {
    return getMinEnchantability(enchantmentLevel) + 50;
  }
  
  public boolean isTreasureEnchantment() {
    return true;
  }
  
  public int getMaxLevel() {
    return 1;
  }
  
  public String getName() {
    return "enchantment.neglection";
  }
  
  public String getTranslatedName(int level) {
    String s = TextFormatting.GOLD + I18n.translateToLocal(getName());
    return (level == 1 && getMaxLevel() == 1) ? s : (s + " " + I18n.translateToLocal("enchantment.level." + level));
  }
  
  public boolean canApply(ItemStack stack) {
    return (stack.getItem() instanceof net.minecraft.item.ItemTool) ? true : super.canApply(stack);
  }
  
  public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
    target.hurtResistantTime = 0;
  }
}
