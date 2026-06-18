package net.minecraft.AgeOfMinecraft.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class EnchantmentObliteration extends Enchantment {
  public EnchantmentObliteration(Enchantment.Rarity rarityIn, EntityEquipmentSlot... slots) {
    super(rarityIn, EnumEnchantmentType.WEAPON, slots);
  }
  
  public int func_77321_a(int enchantmentLevel) {
    return 20;
  }
  
  public String func_77316_c(int level) {
    String s = TextFormatting.ITALIC + I18n.func_74838_a(func_77320_a());
    return TextFormatting.BOLD + ((level == 1 && func_77325_b() == 1) ? s : (s + " " + I18n.func_74838_a("enchantment.level." + level)));
  }
  
  public int func_77317_b(int enchantmentLevel) {
    return super.func_77321_a(enchantmentLevel) + 30;
  }
  
  public int func_77325_b() {
    return 1;
  }
  
  public String func_77320_a() {
    return "enchantment.obliteration";
  }
  
  public boolean func_92089_a(ItemStack stack) {
    return (stack.func_77973_b() instanceof net.minecraft.item.ItemAxe) ? true : super.func_92089_a(stack);
  }
  
  public void func_151368_a(EntityLivingBase user, Entity target, int level) {
    if (target instanceof EntityLivingBase) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)target;
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase.func_184222_aU() && !entitylivingbase.func_70089_S()) {
        entitylivingbase.func_70106_y();
        entitylivingbase.field_70170_p.func_72876_a((Entity)user, target.field_70165_t, target.field_70163_u, target.field_70161_v, entitylivingbase.field_70131_O + entitylivingbase.field_70130_N, false);
        entitylivingbase.field_70170_p.func_72960_a((Entity)entitylivingbase, (byte)20);
      } 
    } 
  }
}
