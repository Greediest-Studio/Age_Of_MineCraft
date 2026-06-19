package net.minecraft.AgeOfMinecraft.renders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public final class RenderLayerCompat {
  private RenderLayerCompat() {}

  public static boolean addLayer(RenderLivingBase renderer, LayerRenderer layer) {
    if (invoke(renderer, "addLayer", layer))
      return true;
    if (invoke(renderer, "func_177094_a", layer))
      return true;
    throw new RuntimeException("Unable to add renderer layer " + layer + " to " + renderer);
  }

  public static boolean removeLayer(RenderLivingBase renderer, Object layer) {
    List layers = getLayerRenderers(renderer);
    return layers.remove(layer);
  }

  public static RenderManager getRenderManager(Render renderer) {
    Field field = findRenderManagerField(renderer.getClass());
    try {
      field.setAccessible(true);
      return (RenderManager)field.get(renderer);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get render manager from " + renderer, e);
    }
  }

  public static boolean isRenderOutlines(Render renderer) {
    Field field = findRenderOutlinesField(renderer.getClass());
    try {
      field.setAccessible(true);
      return field.getBoolean(renderer);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get render outlines from " + renderer, e);
    }
  }

  public static void setShadowSize(Render renderer, float value) {
    setRenderFloat(renderer, findShadowSizeField(renderer.getClass()), value, "shadow size");
  }

  public static void multiplyShadowSize(Render renderer, float value) {
    Field field = findShadowSizeField(renderer.getClass());
    try {
      field.setAccessible(true);
      field.setFloat(renderer, field.getFloat(renderer) * value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to multiply shadow size on " + renderer, e);
    }
  }

  public static void setShadowOpaque(Render renderer, float value) {
    setRenderFloat(renderer, findShadowOpaqueField(renderer.getClass()), value, "shadow opacity");
  }

  public static ModelBase getMainModel(RenderLivingBase renderer) {
    try {
      Method method = findMethod(RenderLivingBase.class, "getMainModel", "func_177087_b");
      if (method != null) {
        method.setAccessible(true);
        return (ModelBase)method.invoke(renderer);
      }
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get main model from " + renderer, e);
    }

    Field field = findMainModelField(renderer.getClass());
    try {
      field.setAccessible(true);
      return (ModelBase)field.get(renderer);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get main model from " + renderer, e);
    }
  }

  public static void setMainModel(RenderLivingBase renderer, ModelBase model) {
    Field field = findMainModelField(renderer.getClass());
    try {
      field.setAccessible(true);
      field.set(renderer, model);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set main model on " + renderer, e);
    }
  }

  public static void setArmorLeggings(LayerArmorBase layer, ModelBase model) {
    setArmorModel(layer, model, true);
  }

  public static void setArmorBody(LayerArmorBase layer, ModelBase model) {
    setArmorModel(layer, model, false);
  }

  private static boolean invoke(RenderLivingBase renderer, String name, LayerRenderer layer) {
    try {
      Method method = RenderLivingBase.class.getMethod(name, new Class[] { LayerRenderer.class });
      method.setAccessible(true);
      method.invoke(renderer, new Object[] { layer });
      return true;
    } catch (ReflectiveOperationException | SecurityException e) {
      return false;
    }
  }

  private static Method findMethod(Class<?> owner, String... names) {
    for (String name : names) {
      try {
        return owner.getMethod(name);
      } catch (NoSuchMethodException e) {
      }
    }
    return null;
  }

  private static List getLayerRenderers(RenderLivingBase renderer) {
    Field field = findLayerRenderersField(renderer.getClass());
    try {
      field.setAccessible(true);
      return (List)field.get(renderer);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to get renderer layers from " + renderer, e);
    }
  }

  private static Field findMainModelField(Class<?> owner) {
    Class<?> type = owner;
    while (type != null) {
      Field field = findField(type, "mainModel", "field_77045_g", "g");
      if (field != null)
        return field;
      type = type.getSuperclass();
    }

    type = owner;
    while (type != null) {
      for (Field field : type.getDeclaredFields()) {
        if (ModelBase.class.isAssignableFrom(field.getType()))
          return field;
      }
      type = type.getSuperclass();
    }

    throw new RuntimeException("Unable to locate RenderLivingBase main model field on " + owner);
  }

  private static Field findLayerRenderersField(Class<?> owner) {
    Class<?> type = owner;
    while (type != null) {
      Field field = findField(type, "layerRenderers", "field_177097_h", "h");
      if (field != null)
        return field;
      type = type.getSuperclass();
    }

    type = owner;
    while (type != null) {
      for (Field field : type.getDeclaredFields()) {
        if (List.class.isAssignableFrom(field.getType()))
          return field;
      }
      type = type.getSuperclass();
    }

    throw new RuntimeException("Unable to locate RenderLivingBase layer renderer list on " + owner);
  }

  private static Field findRenderManagerField(Class<?> owner) {
    Field field = findFieldInHierarchy(owner, RenderManager.class, "renderManager", "field_76990_c", "c");
    if (field != null)
      return field;

    for (Field candidate : Render.class.getDeclaredFields()) {
      if (RenderManager.class.isAssignableFrom(candidate.getType()))
        return candidate;
    }

    throw new RuntimeException("Unable to locate Render render manager field on " + owner);
  }

  private static Field findShadowSizeField(Class<?> owner) {
    Field field = findFieldInHierarchy(owner, Float.TYPE, "shadowSize", "field_76989_e", "e");
    if (field != null)
      return field;
    return findRenderFloatField(owner, 0, "shadow size");
  }

  private static Field findShadowOpaqueField(Class<?> owner) {
    Field field = findFieldInHierarchy(owner, Float.TYPE, "shadowOpaque", "field_188300_g", "field_76987_f", "f", "g");
    if (field != null)
      return field;
    return findRenderFloatField(owner, 1, "shadow opacity");
  }

  private static Field findRenderOutlinesField(Class<?> owner) {
    Field field = findFieldInHierarchy(owner, Boolean.TYPE, "renderOutlines", "field_188301_f", "field_188300_g", "f", "g");
    if (field != null)
      return field;

    for (Field candidate : Render.class.getDeclaredFields()) {
      if (candidate.getType() == Boolean.TYPE)
        return candidate;
    }

    throw new RuntimeException("Unable to locate Render outlines field on " + owner);
  }

  private static Field findRenderFloatField(Class<?> owner, int targetIndex, String description) {
    int index = 0;
    for (Field candidate : Render.class.getDeclaredFields()) {
      if (candidate.getType() == Float.TYPE) {
        if (index == targetIndex)
          return candidate;
        index++;
      }
    }

    throw new RuntimeException("Unable to locate Render " + description + " field on " + owner);
  }

  private static void setRenderFloat(Render renderer, Field field, float value, String description) {
    try {
      field.setAccessible(true);
      field.setFloat(renderer, value);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set " + description + " on " + renderer, e);
    }
  }

  private static void setArmorModel(LayerArmorBase layer, ModelBase model, boolean leggings) {
    Field field = findArmorModelField(layer.getClass(), leggings);
    try {
      field.setAccessible(true);
      field.set(layer, model);
    } catch (ReflectiveOperationException | SecurityException e) {
      throw new RuntimeException("Unable to set armor model on " + layer, e);
    }
  }

  private static Field findArmorModelField(Class<?> owner, boolean leggings) {
    String mcpName = leggings ? "modelLeggings" : "modelArmor";
    String srgName = leggings ? "field_177189_c" : "field_177186_d";
    String obfName = leggings ? "c" : "d";

    Class<?> type = owner;
    while (type != null) {
      Field field = findField(type, mcpName, srgName, obfName);
      if (field != null)
        return field;
      type = type.getSuperclass();
    }

    Field[] fields = LayerArmorBase.class.getDeclaredFields();
    int modelIndex = 0;
    int targetIndex = leggings ? 0 : 1;
    for (Field field : fields) {
      if (ModelBase.class.isAssignableFrom(field.getType())) {
        if (modelIndex == targetIndex)
          return field;
        modelIndex++;
      }
    }

    throw new RuntimeException("Unable to locate LayerArmorBase armor model field on " + owner);
  }

  private static Field findField(Class<?> owner, String... names) {
    for (String name : names) {
      try {
        return owner.getDeclaredField(name);
      } catch (NoSuchFieldException e) {
      }
    }
    return null;
  }

  private static Field findFieldInHierarchy(Class<?> owner, Class<?> expectedType, String... names) {
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
}
