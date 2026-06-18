package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentConviction extends EnchantmentDamage {
  public EnchantmentConviction() {
    super(Enchantment.Rarity.UNCOMMON, 6, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
  }
  
  public int getMinEnchantability(int par1) {
    return 5 + (par1 - 1) * 8;
  }
  
  public int getMaxEnchantability(int par1) {
    return getMinEnchantability(par1) + 20;
  }
  
  public int getMaxLevel() {
    return 5;
  }
  
  public float calcDamageByCreature(int par1, EnumCreatureAttribute par2CreatureAttribute) {
    return (par2CreatureAttribute == EnumCreatureAttribute.ILLAGER) ? (par1 * 3.0F) : 0.0F;
  }
  
  public String getName() {
    return "enchantment.conviction";
  }
  
  public boolean canApplyTogether(Enchantment par1Enchantment) {
    return !(par1Enchantment instanceof EnchantmentDamage);
  }
  
  public boolean canApply(ItemStack par1ItemStack) {
    return (par1ItemStack.getItem() instanceof net.minecraft.item.ItemAxe) ? true : super.canApply(par1ItemStack);
  }
}
