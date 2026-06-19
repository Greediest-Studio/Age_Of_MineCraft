package net.minecraft.AgeOfMinecraft.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class EntityCompat {
  private static Field worldRemoteField;
  private static Field ticksExistedField;
  private static Field isDeadField;
  private static Field posXField;
  private static Field posYField;
  private static Field posZField;
  private static Field playerEntitiesField;
  private static Field loadedEntityListField;

  private EntityCompat() {}

  public static World world(Entity entity) {
    return entity.getEntityWorld();
  }

  public static boolean isRemote(World world) {
    Field field = cachedWorldRemoteField();
    if (field == null)
      throw new RuntimeException("Unable to locate World isRemote field");
    try {
      field.setAccessible(true);
      return field.getBoolean(world);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get World isRemote", e);
    }
  }

  public static int ticksExisted(Entity entity) {
    Field field = cachedTicksExistedField();
    if (field == null)
      throw new RuntimeException("Unable to locate Entity ticksExisted field");
    try {
      field.setAccessible(true);
      return field.getInt(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get ticksExisted from " + entity, e);
    }
  }

  public static boolean isDead(Entity entity) {
    Field field = cachedIsDeadField();
    if (field == null)
      return !entity.isEntityAlive();
    try {
      field.setAccessible(true);
      return field.getBoolean(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get isDead from " + entity, e);
    }
  }

  public static double posX(Entity entity) {
    return getEntityDouble(entity, "posX", "field_70165_t", "p");
  }

  public static double posY(Entity entity) {
    return getEntityDouble(entity, "posY", "field_70163_u", "q");
  }

  public static double posZ(Entity entity) {
    return getEntityDouble(entity, "posZ", "field_70161_v", "r");
  }

  public static void setXpCooldown(EntityPlayer player, int value) {
    Field field = findFieldInHierarchy(EntityPlayer.class, Integer.TYPE, "xpCooldown", "field_71090_bL", "bL");
    if (field == null)
      return;
    try {
      field.setAccessible(true);
      field.setInt(player, value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set xpCooldown on " + player, e);
    }
  }

  public static List<EntityPlayer> playerEntities(World world) {
    Field field = cachedPlayerEntitiesField();
    if (field == null)
      return Collections.emptyList();
    try {
      field.setAccessible(true);
      return (List<EntityPlayer>)field.get(world);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get World playerEntities", e);
    }
  }

  public static List<Entity> loadedEntityList(World world) {
    Field field = cachedLoadedEntityListField();
    if (field == null)
      return Collections.emptyList();
    try {
      field.setAccessible(true);
      return (List<Entity>)field.get(world);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get World loadedEntityList", e);
    }
  }

  private static double getEntityDouble(Entity entity, String mcpName, String srgName, String obfName) {
    Field field = cachedEntityDoubleField(mcpName, srgName, obfName);
    if (field == null)
      throw new RuntimeException("Unable to locate Entity " + mcpName + " field");
    try {
      field.setAccessible(true);
      return field.getDouble(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + mcpName + " from " + entity, e);
    }
  }

  private static Field cachedWorldRemoteField() {
    if (worldRemoteField == null)
      worldRemoteField = findField(World.class, Boolean.TYPE, "isRemote", "field_72995_K", "D");
    return worldRemoteField;
  }

  private static Field cachedTicksExistedField() {
    if (ticksExistedField == null)
      ticksExistedField = findField(Entity.class, Integer.TYPE, "ticksExisted", "field_70173_aa", "aa");
    return ticksExistedField;
  }

  private static Field cachedIsDeadField() {
    if (isDeadField == null)
      isDeadField = findField(Entity.class, Boolean.TYPE, "isDead", "field_70128_L", "L");
    return isDeadField;
  }

  private static Field cachedPlayerEntitiesField() {
    if (playerEntitiesField == null)
      playerEntitiesField = findField(World.class, List.class, "playerEntities", "field_73010_i", "h");
    return playerEntitiesField;
  }

  private static Field cachedLoadedEntityListField() {
    if (loadedEntityListField == null)
      loadedEntityListField = findField(World.class, List.class, "loadedEntityList", "field_72996_f", "f");
    return loadedEntityListField;
  }

  private static Field cachedEntityDoubleField(String mcpName, String srgName, String obfName) {
    if ("posX".equals(mcpName)) {
      if (posXField == null)
        posXField = findField(Entity.class, Double.TYPE, mcpName, srgName, obfName);
      return posXField;
    }
    if ("posY".equals(mcpName)) {
      if (posYField == null)
        posYField = findField(Entity.class, Double.TYPE, mcpName, srgName, obfName);
      return posYField;
    }
    if ("posZ".equals(mcpName)) {
      if (posZField == null)
        posZField = findField(Entity.class, Double.TYPE, mcpName, srgName, obfName);
      return posZField;
    }
    return findField(Entity.class, Double.TYPE, mcpName, srgName, obfName);
  }

  private static Field findField(Class<?> owner, Class<?> expectedType, String... names) {
    for (String name : names) {
      try {
        Field field = owner.getDeclaredField(name);
        if (field.getType() == expectedType || expectedType.isAssignableFrom(field.getType()))
          return field;
      } catch (NoSuchFieldException e) {
      }
    }
    return null;
  }

  private static Field findFieldInHierarchy(Class<?> owner, Class<?> expectedType, String... names) {
    Class<?> type = owner;
    while (type != null) {
      Field field = findField(type, expectedType, names);
      if (field != null)
        return field;
      type = type.getSuperclass();
    }
    return null;
  }
}
