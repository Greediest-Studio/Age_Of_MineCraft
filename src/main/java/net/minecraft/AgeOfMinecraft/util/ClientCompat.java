package net.minecraft.AgeOfMinecraft.util;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public final class ClientCompat {
  private ClientCompat() {}

  public static EntityPlayerSP player(Minecraft minecraft) {
    Field field = findField(Minecraft.class, EntityPlayerSP.class, "player", "field_71439_g", "h");
    if (field == null)
      return null;
    try {
      field.setAccessible(true);
      return (EntityPlayerSP)field.get(minecraft);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get Minecraft player", e);
    }
  }

  public static WorldClient world(Minecraft minecraft) {
    Field field = findField(Minecraft.class, WorldClient.class, "world", "field_71441_e", "f");
    if (field == null)
      return null;
    try {
      field.setAccessible(true);
      return (WorldClient)field.get(minecraft);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get Minecraft world", e);
    }
  }

  private static Field findField(Class<?> owner, Class<?> expectedType, String... names) {
    for (String name : names) {
      try {
        Field field = owner.getDeclaredField(name);
        if (expectedType.isAssignableFrom(field.getType()))
          return field;
      } catch (NoSuchFieldException e) {
      }
    }

    for (Field field : owner.getDeclaredFields()) {
      if (expectedType.isAssignableFrom(field.getType()))
        return field;
    }

    return null;
  }
}
