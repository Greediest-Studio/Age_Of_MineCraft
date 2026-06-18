package net.minecraft.AgeOfMinecraft.registry;

import net.minecraft.AgeOfMinecraft.enchantments.EnchantmentConviction;
import net.minecraft.AgeOfMinecraft.enchantments.EnchantmentCrusher;
import net.minecraft.AgeOfMinecraft.enchantments.EnchantmentDisintigration;
import net.minecraft.AgeOfMinecraft.enchantments.EnchantmentDisruption;
import net.minecraft.AgeOfMinecraft.enchantments.EnchantmentNeglection;
import net.minecraft.AgeOfMinecraft.enchantments.EnchantmentObliteration;
import net.minecraft.AgeOfMinecraft.enchantments.EnchantmentWitherStormKiller;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

public class EEnchant {
  public static final Enchantment crusher = (Enchantment)new EnchantmentCrusher();
  
  public static final Enchantment disruption = (Enchantment)new EnchantmentDisruption();
  
  public static final Enchantment conviction = (Enchantment)new EnchantmentConviction();
  
  public static final Enchantment neglection = (Enchantment)new EnchantmentNeglection(Enchantment.Rarity.VERY_RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
  
  public static final Enchantment obliteration = (Enchantment)new EnchantmentObliteration(Enchantment.Rarity.RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
  
  public static final Enchantment disintigration = (Enchantment)new EnchantmentDisintigration(Enchantment.Rarity.RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
  
  public static final Enchantment stormkiller = (Enchantment)new EnchantmentWitherStormKiller(Enchantment.Rarity.VERY_RARE, new EntityEquipmentSlot[] { EntityEquipmentSlot.MAINHAND });
  
  public static void init() {
    GameData.register_impl(crusher.setRegistryName(new ResourceLocation("ageofminecraft", "crusher")));
    GameData.register_impl(disruption.setRegistryName(new ResourceLocation("ageofminecraft", "disruption")));
    GameData.register_impl(conviction.setRegistryName(new ResourceLocation("ageofminecraft", "conviction")));
    GameData.register_impl(disintigration.setRegistryName(new ResourceLocation("ageofminecraft", "disintigration")));
    GameData.register_impl(obliteration.setRegistryName(new ResourceLocation("ageofminecraft", "obliteration")));
    GameData.register_impl(neglection.setRegistryName(new ResourceLocation("ageofminecraft", "neglection")));
    GameData.register_impl(stormkiller.setRegistryName(new ResourceLocation("ageofminecraft", "superweapon")));
  }
}
