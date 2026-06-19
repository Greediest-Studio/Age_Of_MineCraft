package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentCrusher extends EnchantmentDamage {
  public EnchantmentCrusher() {
    super(Enchantment.Rarity.RARE, 3, EntityEquipmentSlot.MAINHAND);
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
    return (par2CreatureAttribute == ESetup.CONSTRUCT) ? (par1 * 10.0F) : (par1 * 0.5F);
  }
  
  public String getName() {
    return "enchantment.crusher";
  }
  
  public boolean canApplyTogether(Enchantment par1Enchantment) {
    return !(par1Enchantment instanceof EnchantmentDamage);
  }
  
  public boolean canApply(ItemStack par1ItemStack) {
    return (par1ItemStack.getItem() instanceof net.minecraft.item.ItemAxe) ? true : super.canApply(par1ItemStack);
  }
}
