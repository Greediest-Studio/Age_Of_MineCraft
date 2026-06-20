package net.minecraft.AgeOfMinecraft.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public final class EntityCompat {
  private static final Map<String, Field> FIELD_CACHE = new HashMap<>();
  private static Field worldRemoteField;
  private static Field ticksExistedField;
  private static Field isDeadField;
  private static Field entityWorldField;
  private static Field dataManagerField;
  private static Method dataManagerMethod;
  private static Field posXField;
  private static Field posYField;
  private static Field posZField;
  private static Field playerEntitiesField;
  private static Field loadedEntityListField;

  private EntityCompat() {}

  public static World world(Entity entity) {
    World world = entity.getEntityWorld();
    if (world != null)
      return world;
    Field field = cachedEntityWorldField();
    if (field == null)
      return null;
    try {
      field.setAccessible(true);
      return (World)field.get(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get World from " + entity, e);
    }
  }

  public static boolean isRemote(World world) {
    Field field = cachedWorldRemoteField();
    if (field == null)
      return !(world instanceof WorldServer);
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
      return 0;
    try {
      field.setAccessible(true);
      return field.getInt(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get ticksExisted from " + entity, e);
    }
  }

  public static void setTicksExisted(Entity entity, int value) {
    Field field = cachedTicksExistedField();
    if (field == null)
      return;
    try {
      field.setAccessible(true);
      field.setInt(entity, value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set ticksExisted on " + entity, e);
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

  public static EntityDataManager getDataManager(Entity entity) {
    Field field = cachedDataManagerField();
    if (field != null) {
      try {
        field.setAccessible(true);
        return (EntityDataManager)field.get(entity);
      } catch (ReflectiveOperationException | SecurityException e) {
        throw new RuntimeException("Unable to get data manager from " + entity, e);
      }
    }

    Method method = cachedDataManagerMethod();
    if (method != null && method.getDeclaringClass() == entity.getClass()) {
      try {
        method.setAccessible(true);
        return (EntityDataManager)method.invoke(entity);
      } catch (ReflectiveOperationException | SecurityException e) {
        throw new RuntimeException("Unable to invoke data manager getter on " + entity, e);
      }
    }
    return null;
  }

  public static double posX(Entity entity) {
    return getDouble(entity, 0.0D, "entity.posX", "posX", "field_70165_t", "p");
  }

  public static double posY(Entity entity) {
    return getDouble(entity, 0.0D, "entity.posY", "posY", "field_70163_u", "q");
  }

  public static double posZ(Entity entity) {
    return getDouble(entity, 0.0D, "entity.posZ", "posZ", "field_70161_v", "r");
  }

  public static void setPosX(Entity entity, double value) {
    setDouble(entity, value, "entity.posX", "posX", "field_70165_t", "p");
  }

  public static void setPosY(Entity entity, double value) {
    setDouble(entity, value, "entity.posY", "posY", "field_70163_u", "q");
  }

  public static void setPosZ(Entity entity, double value) {
    setDouble(entity, value, "entity.posZ", "posZ", "field_70161_v", "r");
  }

  public static float rotationYaw(Entity entity) {
    return getFloat(entity, 0.0F, "entity.rotationYaw", "rotationYaw", "field_70177_z", "y");
  }

  public static float rotationPitch(Entity entity) {
    return getFloat(entity, 0.0F, "entity.rotationPitch", "rotationPitch", "field_70125_A", "z");
  }

  public static void setRotationYawHead(EntityLivingBase entity, float value) {
    setFloat(entity, value, "entityLivingBase.rotationYawHead", "rotationYawHead", "field_70759_as", "aO");
  }

  public static void setRenderYawOffset(EntityLivingBase entity, float value) {
    setFloat(entity, value, "entityLivingBase.renderYawOffset", "renderYawOffset", "field_70761_aq", "aM");
  }

  public static void copyYawToHeadAndBody(EntityLivingBase entity) {
    float yaw = rotationYaw(entity);
    setRotationYawHead(entity, yaw);
    setRenderYawOffset(entity, yaw);
  }

  public static void setEntityState(Entity entity, byte state) {
    World world = world(entity);
    if (world != null)
      world.setEntityState(entity, state);
  }

  public static void spawnXpOrbAt(EntityLivingBase entity, double yOffset, int value) {
    World world = world(entity);
    if (world != null)
      world.spawnEntity(new EntityXPOrb(world, posX(entity), posY(entity) + yOffset, posZ(entity), value));
  }

  public static double getDouble(Entity entity, double fallback, String key, String... names) {
    Field field = cachedEntityField(entity, key, Double.TYPE, names);
    if (field == null)
      return fallback;
    try {
      field.setAccessible(true);
      return field.getDouble(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + key + " from " + entity.getClass().getName(), e);
    }
  }

  public static void setDouble(Entity entity, double value, String key, String... names) {
    Field field = cachedEntityField(entity, key, Double.TYPE, names);
    if (field == null)
      return;
    try {
      field.setAccessible(true);
      field.setDouble(entity, value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set " + key + " on " + entity.getClass().getName(), e);
    }
  }

  public static float getFloat(Entity entity, float fallback, String key, String... names) {
    Field field = cachedEntityField(entity, key, Float.TYPE, names);
    if (field == null)
      return fallback;
    try {
      field.setAccessible(true);
      return field.getFloat(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + key + " from " + entity.getClass().getName(), e);
    }
  }

  public static void setFloat(Entity entity, float value, String key, String... names) {
    Field field = cachedEntityField(entity, key, Float.TYPE, names);
    if (field == null)
      return;
    try {
      field.setAccessible(true);
      field.setFloat(entity, value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set " + key + " on " + entity.getClass().getName(), e);
    }
  }

  public static int getInt(Entity entity, int fallback, String key, String... names) {
    Field field = cachedEntityField(entity, key, Integer.TYPE, names);
    if (field == null)
      return fallback;
    try {
      field.setAccessible(true);
      return field.getInt(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + key + " from " + entity.getClass().getName(), e);
    }
  }

  public static void setInt(Entity entity, int value, String key, String... names) {
    Field field = cachedEntityField(entity, key, Integer.TYPE, names);
    if (field == null)
      return;
    try {
      field.setAccessible(true);
      field.setInt(entity, value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set " + key + " on " + entity.getClass().getName(), e);
    }
  }

  public static boolean getBoolean(Entity entity, boolean fallback, String key, String... names) {
    Field field = cachedEntityField(entity, key, Boolean.TYPE, names);
    if (field == null)
      return fallback;
    try {
      field.setAccessible(true);
      return field.getBoolean(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + key + " from " + entity.getClass().getName(), e);
    }
  }

  public static void setBoolean(Entity entity, boolean value, String key, String... names) {
    Field field = cachedEntityField(entity, key, Boolean.TYPE, names);
    if (field == null)
      return;
    try {
      field.setAccessible(true);
      field.setBoolean(entity, value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set " + key + " on " + entity.getClass().getName(), e);
    }
  }

  public static float[] getFloatArray(Entity entity, float[] fallback, String key, String... names) {
    Field field = cachedEntityField(entity, key, float[].class, names);
    if (field == null)
      return fallback;
    try {
      field.setAccessible(true);
      return (float[])field.get(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + key + " from " + entity.getClass().getName(), e);
    }
  }

  public static Random getRandom(Entity entity, Random fallback, String key, String... names) {
    Field field = cachedEntityField(entity, key, Random.class, names);
    if (field == null)
      return fallback;
    try {
      field.setAccessible(true);
      return (Random)field.get(entity);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get " + key + " from " + entity.getClass().getName(), e);
    }
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

  private static Field cachedEntityWorldField() {
    if (entityWorldField == null)
      entityWorldField = findField(Entity.class, World.class, "world", "field_70170_p", "p");
    return entityWorldField;
  }

  private static Method cachedDataManagerMethod() {
    if (dataManagerMethod == null)
      dataManagerMethod = findNoArgMethod(Entity.class, "getDataManager", "func_184212_Q", "V");
    return dataManagerMethod;
  }

  private static Field cachedDataManagerField() {
    if (dataManagerField == null)
      dataManagerField = findField(Entity.class, EntityDataManager.class, "dataManager", "field_70180_af", "af");
    return dataManagerField;
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

  private static Field cachedEntityField(Entity entity, String key, Class<?> expectedType, String... names) {
    Class<?> owner = entity == null ? Entity.class : entity.getClass();
    String cacheKey = owner.getName() + ":" + expectedType.getName() + ":" + key;
    if (!FIELD_CACHE.containsKey(cacheKey))
      FIELD_CACHE.put(cacheKey, findVanillaEntityField(owner, expectedType, names));
    return FIELD_CACHE.get(cacheKey);
  }

  private static Field findVanillaEntityField(Class<?> owner, Class<?> expectedType, String... names) {
    Class<?> type = owner;
    while (type != null) {
      if (type.getName().startsWith("net.minecraft.entity.")) {
        Field field = findField(type, expectedType, names);
        if (field != null)
          return field;
      }
      type = type.getSuperclass();
    }
    return findField(Entity.class, expectedType, names);
  }

  private static Field findField(Class<?> owner, Class<?> expectedType, String... names) {
    Class<?> type = owner;
    while (type != null) {
      for (String name : names) {
        try {
          Field field = type.getDeclaredField(name);
          if (field.getType() == expectedType || expectedType.isAssignableFrom(field.getType()))
            return field;
        } catch (NoSuchFieldException e) {
        }
      }
      type = type.getSuperclass();
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
}
