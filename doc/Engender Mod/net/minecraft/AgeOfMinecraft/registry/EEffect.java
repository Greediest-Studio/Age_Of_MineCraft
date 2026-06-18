package net.minecraft.AgeOfMinecraft.registry;

import java.util.UUID;
import net.minecraft.AgeOfMinecraft.effects.PotionBleeding;
import net.minecraft.AgeOfMinecraft.effects.PotionEngender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class EEffect {
  public static final Potion BREAKING = (new PotionEngender("breaking", true, 13015637, 0, 0, -2.0D)).func_111184_a(SharedMonsterAttributes.field_188791_g, UUID.fromString("d8e4ae61-e49d-4931-aa67-0e7662665550").toString(), 0.0D, 0);
  
  public static final Potion BLEEDING = (Potion)new PotionBleeding();
  
  public static final PotionType normal_breaking = (PotionType)(new PotionType("breaking", new PotionEffect[] { new PotionEffect(BREAKING, 3600) })).setRegistryName("breaking");
  
  public static final PotionType long_breaking = (PotionType)(new PotionType("breaking", new PotionEffect[] { new PotionEffect(BREAKING, 9600) })).setRegistryName("long_breaking");
  
  public static final PotionType strong_breaking = (PotionType)(new PotionType("breaking", new PotionEffect[] { new PotionEffect(BREAKING, 1800, 1) })).setRegistryName("strong_breaking");
  
  public static void registerPotions() {
    ForgeRegistries.POTIONS.register((IForgeRegistryEntry)BREAKING);
    ForgeRegistries.POTIONS.register((IForgeRegistryEntry)BLEEDING);
    ForgeRegistries.POTION_TYPES.register((IForgeRegistryEntry)normal_breaking);
    ForgeRegistries.POTION_TYPES.register((IForgeRegistryEntry)long_breaking);
    ForgeRegistries.POTION_TYPES.register((IForgeRegistryEntry)strong_breaking);
    PotionHelper.func_193357_a(PotionTypes.field_185233_e, Items.field_151119_aD, normal_breaking);
    PotionHelper.func_193357_a(normal_breaking, Items.field_151137_ax, long_breaking);
    PotionHelper.func_193357_a(normal_breaking, Items.field_151114_aO, strong_breaking);
  }
}
