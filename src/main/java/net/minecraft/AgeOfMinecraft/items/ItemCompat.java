package net.minecraft.AgeOfMinecraft.items;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    if (!invokeAny(item, new String[] { "setRegistryName" }, new Class[] { ResourceLocation.class }, new Object[] { registryName })
        && !invokeAny(item, new String[] { "setRegistryName" }, new Class[] { String.class }, new Object[] { name }))
      setField(IForgeRegistryEntry.Impl.class, item, new String[] { "registryName" }, registryName);
  }

  public static void setTranslationKey(Item item, String name) {
    if (!invokeAny(item, new String[] { "setTranslationKey", "setUnlocalizedName", "func_77655_b" }, new Class[] { String.class }, new Object[] { name }))
      setField(Item.class, item, new String[] { "translationKey", "field_77774_bZ" }, name);
  }

  public static void setCreativeTab(Item item, CreativeTabs tab) {
    if (!invokeAny(item, new String[] { "setCreativeTab", "func_77637_a" }, new Class[] { CreativeTabs.class }, new Object[] { tab }))
      setField(Item.class, item, new String[] { "tabToDisplayOn", "field_77701_a" }, tab);
  }

  public static void setMaxStackSize(Item item, int size) {
    if (!invokeAny(item, new String[] { "setMaxStackSize", "func_77625_d" }, new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(size) }))
      setField(Item.class, item, new String[] { "maxStackSize", "field_77777_bU" }, Integer.valueOf(size));
  }

  public static void setHasSubtypes(Item item, boolean hasSubtypes) {
    if (!invokeAny(item, new String[] { "setHasSubtypes", "func_77627_a" }, new Class[] { Boolean.TYPE }, new Object[] { Boolean.valueOf(hasSubtypes) }))
      setField(Item.class, item, new String[] { "hasSubtypes", "field_77787_bX" }, Boolean.valueOf(hasSubtypes));
  }

  public static void setMaxDamage(Item item, int maxDamage) {
    if (!invokeAny(item, new String[] { "setMaxDamage", "func_77656_e" }, new Class[] { Integer.TYPE }, new Object[] { Integer.valueOf(maxDamage) }))
      setField(Item.class, item, new String[] { "maxDamage", "field_77699_b" }, Integer.valueOf(maxDamage));
    if (maxDamage > 0)
      setHasSubtypes(item, false);
  }

  private static boolean invokeAny(Object target, String[] names, Class<?>[] parameterTypes, Object[] args) {
    for (String name : names)
      if (invoke(target, name, parameterTypes, args))
        return true;
    return false;
  }

  private static boolean invoke(Object target, String name, Class<?>[] parameterTypes, Object[] args) {
    try {
      Method method = target.getClass().getMethod(name, parameterTypes);
      method.setAccessible(true);
      method.invoke(target, args);
      return true;
    } catch (ReflectiveOperationException | SecurityException e) {
      return false;
    }
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
