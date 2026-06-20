package net.minecraft.AgeOfMinecraft.items;

import java.lang.reflect.Field;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public final class ItemCompat {
  private ItemCompat() {}

  public static void setup(Item item, String name, CreativeTabs tab) {
    setRegistryName(item, name);
    setTranslationKey(item, name);
    setCreativeTab(item, tab);
  }

  public static void setRegistryName(Item item, String name) {
    ResourceLocation registryName = new ResourceLocation("ageofminecraft", name);
    try {
      item.setRegistryName(registryName);
    } catch (NoSuchMethodError e) {
      setField(IForgeRegistryEntry.Impl.class, item, new String[] { "registryName" }, registryName);
    }
  }

  public static void setTranslationKey(Item item, String name) {
    try {
      item.setTranslationKey(name);
    } catch (NoSuchMethodError e) {
      setField(Item.class, item, new String[] { "translationKey", "field_77774_bZ" }, name);
    }
  }

  public static void setCreativeTab(Item item, CreativeTabs tab) {
    try {
      item.setCreativeTab(tab);
    } catch (NoSuchMethodError e) {
      setField(Item.class, item, new String[] { "tabToDisplayOn", "field_77701_a" }, tab);
    }
  }

  public static void setMaxStackSize(Item item, int size) {
    try {
      item.setMaxStackSize(size);
    } catch (NoSuchMethodError e) {
      setField(Item.class, item, new String[] { "maxStackSize", "field_77777_bU" }, Integer.valueOf(size));
    }
  }

  public static void setHasSubtypes(Item item, boolean hasSubtypes) {
    try {
      item.setHasSubtypes(hasSubtypes);
    } catch (NoSuchMethodError e) {
      setField(Item.class, item, new String[] { "hasSubtypes", "field_77787_bX" }, Boolean.valueOf(hasSubtypes));
    }
  }

  public static void setMaxDamage(Item item, int maxDamage) {
    try {
      item.setMaxDamage(maxDamage);
    } catch (NoSuchMethodError e) {
      setField(Item.class, item, new String[] { "maxDamage", "field_77699_b" }, Integer.valueOf(maxDamage));
    }
    if (maxDamage > 0)
      setHasSubtypes(item, false);
  }

  private static void setField(Class<?> owner, Object target, String[] names, Object value) {
    ReflectiveOperationException failure = null;
    for (String name : names) {
      try {
        Field field = owner.getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
        return;
      } catch (ReflectiveOperationException e) {
        failure = e;
      }
    }
    throw new RuntimeException("Unable to set " + owner.getName() + "." + names[0], failure);
  }
}
