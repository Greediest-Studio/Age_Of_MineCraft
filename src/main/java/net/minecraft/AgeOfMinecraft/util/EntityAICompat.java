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
    EntityAITasks tasks = getAITasks(living, false);
    if (tasks != null)
      tasks.addTask(priority, task);
  }

  public static void removeTask(EntityLiving living, EntityAIBase task) {
    EntityAITasks tasks = getAITasks(living, false);
    if (tasks != null)
      tasks.removeTask(task);
  }

  public static void addTargetTask(EntityLiving living, int priority, EntityAIBase task) {
    EntityAITasks tasks = getAITasks(living, true);
    if (tasks != null)
      tasks.addTask(priority, task);
  }

  public static boolean hasTasks(EntityLiving living) {
    EntityAITasks tasks = getAITasks(living, false);
    return tasks != null && !tasks.taskEntries.isEmpty();
  }

  public static boolean containsTask(EntityLiving living, EntityAIBase task) {
    EntityAITasks tasks = getAITasks(living, false);
    return tasks != null && tasks.taskEntries.contains(task);
  }

  public static void clearTasks(EntityLiving living) {
    EntityAITasks tasks = getAITasks(living, false);
    if (tasks != null)
      tasks.taskEntries.clear();
  }

  public static void clearTargetTasks(EntityLiving living) {
    EntityAITasks tasks = getAITasks(living, true);
    if (tasks != null)
      tasks.taskEntries.clear();
  }

  private static EntityAITasks getAITasks(EntityLiving living, boolean target) {
    Field field = findAITaskField(target);
    if (field == null)
      return null;
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
    Class<?> type = EntityLiving.class;
    while (type != null) {
      for (Field candidate : type.getDeclaredFields()) {
        if (EntityAITasks.class.isAssignableFrom(candidate.getType())) {
          if (index == targetIndex)
            return candidate;
          index++;
        }
      }
      type = type.getSuperclass();
    }

    return null;
  }

  private static Field findField(Class<?> owner, String... names) {
    Class<?> type = owner;
    while (type != null) {
      for (String name : names) {
        try {
          Field field = type.getDeclaredField(name);
          if (EntityAITasks.class.isAssignableFrom(field.getType()))
            return field;
        } catch (NoSuchFieldException e) {
        }
      }
      type = type.getSuperclass();
    }
    return null;
  }
}
