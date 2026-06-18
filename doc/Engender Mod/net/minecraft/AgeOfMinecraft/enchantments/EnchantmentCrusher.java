package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentCrusher extends EnchantmentDamage {
  public EnchantmentCrusher() {
    super(Enchantment.Rarity.RARE, 3, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
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
    return (par2CreatureAttribute == ESetup.CONSTRUCT) ? (par1 * 10.0F) : (par1 * 0.5F);
  }
  
  public String func_77320_a() {
    return "enchantment.crusher";
  }
  
  public boolean func_77326_a(Enchantment par1Enchantment) {
    return !(par1Enchantment instanceof EnchantmentDamage);
  }
  
  public boolean func_92089_a(ItemStack par1ItemStack) {
    return (par1ItemStack.func_77973_b() instanceof net.minecraft.item.ItemAxe) ? true : super.func_92089_a(par1ItemStack);
  }
}
