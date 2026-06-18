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
  
  public int getMinEnchantability(int enchantmentLevel) {
    return 20;
  }
  
  public String getTranslatedName(int level) {
    String s = TextFormatting.ITALIC + I18n.translateToLocal(getName());
    return TextFormatting.BOLD + ((level == 1 && getMaxLevel() == 1) ? s : (s + " " + I18n.translateToLocal("enchantment.level." + level)));
  }
  
  public int getMaxEnchantability(int enchantmentLevel) {
    return super.getMinEnchantability(enchantmentLevel) + 30;
  }
  
  public int getMaxLevel() {
    return 1;
  }
  
  public String getName() {
    return "enchantment.obliteration";
  }
  
  public boolean canApply(ItemStack stack) {
    return (stack.getItem() instanceof net.minecraft.item.ItemAxe) ? true : super.canApply(stack);
  }
  
  public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
    if (target instanceof EntityLivingBase) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)target;
      if (!entitylivingbase.world.isRemote && entitylivingbase.isNonBoss() && !entitylivingbase.isEntityAlive()) {
        entitylivingbase.setDead();
        entitylivingbase.world.createExplosion((Entity)user, target.posX, target.posY, target.posZ, entitylivingbase.height + entitylivingbase.width, false);
        entitylivingbase.world.setEntityState((Entity)entitylivingbase, (byte)20);
      } 
    } 
  }
}
