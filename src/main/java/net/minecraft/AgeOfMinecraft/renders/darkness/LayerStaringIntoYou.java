package net.minecraft.AgeOfMinecraft.renders.darkness;

import net.minecraft.AgeOfMinecraft.entity.untame.tier5.EntityDarkness;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerStaringIntoYou implements LayerRenderer<EntityDarkness> {
  private final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
  
  private final RenderDarkness dragonRenderer;
  
  public LayerStaringIntoYou(RenderDarkness dragonRendererIn) {
    this.dragonRenderer = dragonRendererIn;
  }
  
  public void doRenderLayer(EntityDarkness entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.dragonRenderer.bindTexture(this.TEXTURE);
    GlStateManager.enableBlend();
    GlStateManager.disableAlpha();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    GlStateManager.disableLighting();
    GlStateManager.depthFunc(514);
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 1.572888E7F, 0.0F);
    GlStateManager.enableLighting();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    (Minecraft.getMinecraft()).entityRenderer.setupFogColor(true);
    this.dragonRenderer.getMainModel().render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    (Minecraft.getMinecraft()).entityRenderer.setupFogColor(false);
    this.dragonRenderer.setLightmap(entitylivingbaseIn);
    GlStateManager.disableBlend();
    GlStateManager.enableAlpha();
    GlStateManager.depthFunc(515);
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
