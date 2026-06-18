package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentDisruption extends EnchantmentDamage {
  public EnchantmentDisruption() {
    super(Enchantment.Rarity.UNCOMMON, 4, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
  }
  
  public int func_77321_a(int par1) {
    return 5 + (par1 - 1) * 8;
  }
  
  public int func_77317_b(int par1) {
    return func_77321_a(par1) + 20;
  }
  
  public int func_77325_b() {
    return 5;
  }
  
  public float func_152376_a(int par1, EnumCreatureAttribute par2CreatureAttribute) {
    return (par2CreatureAttribute == ESetup.ENDER) ? (par1 * 3.5F) : 0.0F;
  }
  
  public String func_77320_a() {
    return "enchantment.disruption";
  }
  
  public boolean func_77326_a(Enchantment par1Enchantment) {
    return !(par1Enchantment instanceof EnchantmentDamage);
  }
  
  public boolean func_92089_a(ItemStack par1ItemStack) {
    return (par1ItemStack.func_77973_b() instanceof net.minecraft.item.ItemAxe) ? true : super.func_92089_a(par1ItemStack);
  }
}
