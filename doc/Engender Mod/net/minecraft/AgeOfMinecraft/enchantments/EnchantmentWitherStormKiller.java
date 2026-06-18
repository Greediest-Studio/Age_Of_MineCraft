package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class EnchantmentWitherStormKiller extends Enchantment {
  public EnchantmentWitherStormKiller(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
    super(rarityIn, EnumEnchantmentType.BREAKABLE, slots);
  }
  
  public int func_77321_a(int enchantmentLevel) {
    return enchantmentLevel * 50;
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
  
  public float func_152376_a(int level, EnumCreatureAttribute creatureType) {
    return (creatureType == ESetup.WITHER_STORM) ? 25000.0F : 50.0F;
  }
  
  public String func_77320_a() {
    return "enchantment.superweapon";
  }
  
  public String func_77316_c(int level) {
    String s = TextFormatting.LIGHT_PURPLE + I18n.func_74838_a(func_77320_a());
    return (level == 1 && func_77325_b() == 1) ? s : (s + " " + I18n.func_74838_a("enchantment.level." + level));
  }
}
