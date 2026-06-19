package net.minecraft.AgeOfMinecraft.util;

import java.lang.reflect.Field;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;

public final class EntityAICompat {
  private EntityAICompat() {}

  public static EntityAITasks tasks(EntityLiving living) {
    return getAITasks(living, false);
  }

  public static EntityAITasks targetTasks(EntityLiving living) {
    return getAITasks(living, true);
  }

  public static void addTask(EntityLiving living, int priority, EntityAIBase task) {
    tasks(living).addTask(priority, task);
  }

  public static void addTargetTask(EntityLiving living, int priority, EntityAIBase task) {
    targetTasks(living).addTask(priority, task);
  }

  private static EntityAITasks getAITasks(EntityLiving living, boolean target) {
    Field field = findAITaskField(target);
    try {
      field.setAccessible(true);
      return (EntityAITasks)field.get(living);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + (target ? "target" : "normal") + " AI tasks from " + living, e);
    }
  }

  private static Field findAITaskField(boolean target) {
    String mcpName = target ? "targetTasks" : "tasks";
    String srgName = target ? "field_70715_bh" : "field_70714_bg";
    String obfName = target ? "bh" : "bg";

    Field field = findField(EntityLiving.class, mcpName, srgName, obfName);
    if (field != null)
      return field;

    int index = 0;
    int targetIndex = target ? 1 : 0;
    for (Field candidate : EntityLiving.class.getDeclaredFields()) {
      if (EntityAITasks.class.isAssignableFrom(candidate.getType())) {
        if (index == targetIndex)
          return candidate;
        index++;
      }
    }

    throw new RuntimeException("Unable to locate EntityLiving " + (target ? "targetTasks" : "tasks") + " field");
  }

  private static Field findField(Class<?> owner, String... names) {
    for (String name : names) {
      try {
        Field field = owner.getDeclaredField(name);
        if (EntityAITasks.class.isAssignableFrom(field.getType()))
          return field;
      } catch (NoSuchFieldException e) {
      }
    }
    return null;
  }
}
