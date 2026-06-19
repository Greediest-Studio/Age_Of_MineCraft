package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.renders.RenderLayerCompat;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelJzahar;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer.LayerJzaharDeath;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderJzahar extends RenderLiving<EntityJzahar> {
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/j'zahar.png");
  
  public RenderJzahar(RenderManager manager) {
    super(manager, new ModelJzahar(true), 1.0F);
    RenderLayerCompat.addLayer(this, (LayerRenderer)new LayerJzaharDeath());
    
  }
  
  protected ResourceLocation getEntityTexture(EntityJzahar entity) {
    return mobTexture;
  }
  
  public void preRenderCallback(EntityJzahar entity, float par2) {
    GlStateManager.scale(1.5F, 1.5F, 1.5F);
    GlStateManager.translate(-0.125F, 0.0F, -0.05F);
    float fit = entity.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entity.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (entity.isEntityAlive() && !entity.isBeingRidden() && (entity.isSneaking() || entity.getJukeboxToDanceTo() != null))
      GlStateManager.translate(0.0F, 0.5F + MathHelper.cos(handleRotationFloat(entity, par2) * 0.2F) * 0.1F, 0.0F); 
  }
}
