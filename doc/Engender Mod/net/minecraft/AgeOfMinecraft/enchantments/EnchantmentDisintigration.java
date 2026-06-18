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
  
  public int func_77321_a(int enchantmentLevel) {
    return 14;
  }
  
  public int func_77317_b(int enchantmentLevel) {
    return super.func_77321_a(enchantmentLevel) + 30;
  }
  
  public int func_77325_b() {
    return 1;
  }
  
  public String func_77316_c(int level) {
    String s = TextFormatting.ITALIC + I18n.func_74838_a(func_77320_a());
    return (level == 1 && func_77325_b() == 1) ? s : (s + " " + I18n.func_74838_a("enchantment.level." + level));
  }
  
  public float func_152376_a(int level, EnumCreatureAttribute creatureType) {
    return (creatureType == EnumCreatureAttribute.UNDEAD) ? 6.0F : 1.0F;
  }
  
  public String func_77320_a() {
    return "enchantment.disintigration";
  }
  
  public boolean func_92089_a(ItemStack stack) {
    return (stack.func_77973_b() instanceof net.minecraft.item.ItemAxe) ? true : super.func_92089_a(stack);
  }
  
  public void func_151368_a(EntityLivingBase user, Entity target, int level) {
    if (target instanceof EntityLivingBase) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)target;
      if (!entitylivingbase.field_70170_p.field_72995_K && entitylivingbase.func_184222_aU() && !entitylivingbase.func_70089_S() && entitylivingbase.func_70662_br()) {
        entitylivingbase.func_184185_a(SoundEvents.field_187539_bB, 2.0F, 0.5F);
        entitylivingbase.func_184185_a(SoundEvents.field_187539_bB, 2.0F, 0.75F);
        entitylivingbase.func_184185_a(SoundEvents.field_187539_bB, 2.0F, 1.25F);
        entitylivingbase.func_184185_a(SoundEvents.field_187539_bB, 2.0F, 1.75F);
        entitylivingbase.func_184185_a(SoundEvents.field_187659_cY, 2.0F, 2.0F);
        entitylivingbase.func_184185_a(SoundEvents.field_187659_cY, 2.0F, 1.75F);
        entitylivingbase.func_184185_a(SoundEvents.field_187659_cY, 2.0F, 1.5F);
        entitylivingbase.func_184185_a(SoundEvents.field_187659_cY, 2.0F, 1.25F);
        entitylivingbase.field_70170_p.func_72960_a((Entity)entitylivingbase, (byte)20);
        entitylivingbase.field_70170_p.func_72960_a((Entity)entitylivingbase, (byte)20);
        entitylivingbase.field_70170_p.func_72960_a((Entity)entitylivingbase, (byte)20);
        entitylivingbase.field_70170_p.func_72960_a((Entity)entitylivingbase, (byte)20);
        entitylivingbase.func_70106_y();
      } 
    } 
  }
}
