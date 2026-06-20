package net.minecraft.AgeOfMinecraft.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public final class AttributeCompat {
  private AttributeCompat() {}

  public static IAttribute ranged(String name, double defaultValue, double minimumValue, double maximumValue, String description, boolean shouldWatch) {
    RangedAttribute attribute = new RangedAttribute(null, name, defaultValue, minimumValue, maximumValue);
    setDescription(attribute, description);
    setShouldWatch(attribute, shouldWatch);
    return attribute;
  }

  public static IAttributeInstance getEntityAttribute(EntityLivingBase entity, IAttribute attribute) {
    Method method = findMethod(EntityLivingBase.class, IAttribute.class, "getEntityAttribute", "func_110148_a", "a");
    if (method != null) {
      try {
        method.setAccessible(true);
        return (IAttributeInstance)method.invoke(entity, attribute);
      } catch (ReflectiveOperationException | RuntimeException e) {
      }
    }

    Object attributeMap = getAttributeMap(entity);
    if (attributeMap == null)
      return null;
    Method getAttributeInstance = findMethod(attributeMap.getClass(), IAttribute.class, "getAttributeInstance", "func_111151_a", "a");
    if (getAttributeInstance == null)
      return null;
    try {
      getAttributeInstance.setAccessible(true);
      return (IAttributeInstance)getAttributeInstance.invoke(attributeMap, attribute);
    } catch (ReflectiveOperationException | RuntimeException e) {
      return null;
    }
  }

  public static double getBaseValue(EntityLivingBase entity, IAttribute attribute, double fallback) {
    IAttributeInstance instance = getEntityAttribute(entity, attribute);
    return instance == null ? fallback : instance.getBaseValue();
  }

  public static void setBaseValue(EntityLivingBase entity, IAttribute attribute, double value) {
    IAttributeInstance instance = getEntityAttribute(entity, attribute);
    if (instance != null)
      instance.setBaseValue(value);
  }

  public static AbstractAttributeMap getAttributeMap(EntityLivingBase entity) {
    Method method = findNoArgMethod(EntityLivingBase.class, "getAttributeMap", "func_110140_aT", "aT");
    if (method == null)
      return null;
    try {
      method.setAccessible(true);
      return (AbstractAttributeMap)method.invoke(entity);
    } catch (ReflectiveOperationException | RuntimeException e) {
      return null;
    }
  }

  private static void setDescription(RangedAttribute attribute, String description) {
    if (invokeStringSetter(attribute, "setDescription", "func_111117_a", "a", description))
      return;
    setField(attribute, String.class, description, "description", "field_111119_c", "c");
  }

  private static void setShouldWatch(RangedAttribute attribute, boolean shouldWatch) {
    if (invokeBooleanSetter(attribute, "setShouldWatch", "func_111112_a", "a", shouldWatch))
      return;
    setField(attribute, Boolean.TYPE, Boolean.valueOf(shouldWatch), "shouldWatch", "field_111114_c", "c");
  }

  private static boolean invokeStringSetter(Object target, String mcpName, String srgName, String obfName, String value) {
    Method method = findMethod(target.getClass(), String.class, mcpName, srgName, obfName);
    if (method == null)
      return false;
    try {
      method.setAccessible(true);
      method.invoke(target, value);
      return true;
    } catch (ReflectiveOperationException | RuntimeException e) {
      return false;
    }
  }

  private static boolean invokeBooleanSetter(Object target, String mcpName, String srgName, String obfName, boolean value) {
    Method method = findMethod(target.getClass(), Boolean.TYPE, mcpName, srgName, obfName);
    if (method == null)
      return false;
    try {
      method.setAccessible(true);
      method.invoke(target, Boolean.valueOf(value));
      return true;
    } catch (ReflectiveOperationException | RuntimeException e) {
      return false;
    }
  }

  private static Method findMethod(Class<?> owner, Class<?> parameterType, String... names) {
    Class<?> type = owner;
    while (type != null) {
      for (String name : names) {
        try {
          return type.getDeclaredMethod(name, parameterType);
        } catch (NoSuchMethodException e) {
        }
      }
      type = type.getSuperclass();
    }
    return null;
  }

  private static Method findNoArgMethod(Class<?> owner, String... names) {
    Class<?> type = owner;
    while (type != null) {
      for (String name : names) {
        try {
          return type.getDeclaredMethod(name);
        } catch (NoSuchMethodException e) {
        }
      }
      type = type.getSuperclass();
    }
    return null;
  }

  private static void setField(Object target, Class<?> expectedType, Object value, String... names) {
    Field field = findField(target.getClass(), expectedType, names);
    if (field == null)
      return;
    try {
      field.setAccessible(true);
      field.set(target, value);
    } catch (ReflectiveOperationException | RuntimeException e) {
    }
  }

  private static Field findField(Class<?> owner, Class<?> expectedType, String... names) {
    Class<?> type = owner;
    while (type != null) {
      for (String name : names) {
        try {
          Field field = type.getDeclaredField(name);
          if (field.getType() == expectedType)
            return field;
        } catch (NoSuchFieldException e) {
        }
      }
      type = type.getSuperclass();
    }

    for (Class<?> candidateType : new Class<?>[] { BaseAttribute.class, RangedAttribute.class }) {
      for (Field field : candidateType.getDeclaredFields()) {
        if (field.getType() == expectedType)
          return field;
      }
    }

    return null;
  }
}
