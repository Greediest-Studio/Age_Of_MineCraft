package net.minecraft.AgeOfMinecraft.util;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Field;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class PrivateHelper {
  public static <T, E> void set(Class<? super T> classToAccess, T instance, E value, String... fieldNames) {
    try {
      Field field = findField(classToAccess, instance, value, fieldNames);
      field.set(instance, value);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private static <T, E> Field findField(Class<? super T> classToAccess, T instance, E value, String... fieldNames) throws Exception {
      List<String> names = new ArrayList<>(Arrays.asList(fieldNames));
    try {
      String[] remappedNames = ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames);
        names.addAll(Arrays.asList(remappedNames));
    } catch (Throwable ignored) {}
    for (String fieldName : names) {
      try {
        Field field = ReflectionHelper.findField(classToAccess, fieldName);
        field.setAccessible(true);
        return field;
      } catch (Throwable ignored) {}
    } 
    if (value instanceof Double) {
      Field bestField = null;
      double bestValue = -Double.MAX_VALUE;
      for (Field field : classToAccess.getDeclaredFields()) {
        if (field.getType() == Double.TYPE && !Modifier.isStatic(field.getModifiers())) {
          field.setAccessible(true);
          double currentValue = field.getDouble(instance);
          if (bestField == null || currentValue > bestValue) {
            bestField = field;
            bestValue = currentValue;
          } 
        } 
      } 
      if (bestField != null)
        return bestField; 
    } 
    throw new NoSuchFieldException(classToAccess.getName() + "." + names);
  }
}
