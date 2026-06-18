package net.minecraft.AgeOfMinecraft.util;

import java.lang.reflect.Field;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class PrivateHelper {
  public static <T, E> void set(Class<? super T> classToAccess, T instance, E value, String... fieldNames) {
    Field field = ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
    try {
      Field modifiersField = Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
      field.set(instance, value);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
}
