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
  
  public int func_77321_a(int enchantmentLevel) {
    return enchantmentLevel * 30;
  }
  
  public int func_77317_b(int enchantmentLevel) {
    return func_77321_a(enchantmentLevel) + 50;
  }
  
  public boolean func_185261_e() {
    return true;
  }
  
  public int func_77325_b() {
    return 1;
  }
  
  public String func_77320_a() {
    return "enchantment.neglection";
  }
  
  public String func_77316_c(int level) {
    String s = TextFormatting.GOLD + I18n.func_74838_a(func_77320_a());
    return (level == 1 && func_77325_b() == 1) ? s : (s + " " + I18n.func_74838_a("enchantment.level." + level));
  }
  
  public boolean func_92089_a(ItemStack stack) {
    return (stack.func_77973_b() instanceof net.minecraft.item.ItemTool) ? true : super.func_92089_a(stack);
  }
  
  public void func_151368_a(EntityLivingBase user, Entity target, int level) {
    target.field_70172_ad = 0;
  }
}
