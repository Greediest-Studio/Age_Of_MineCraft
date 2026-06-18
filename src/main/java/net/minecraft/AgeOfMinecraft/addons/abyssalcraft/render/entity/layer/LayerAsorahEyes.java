package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDragonBoss;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerAsorahEyes implements LayerRenderer<EntityDragonBoss> {
  private static final ResourceLocation TEXTURE = new ResourceLocation("abyssalcraft:textures/model/boss/dragonboss_eyes.png");
  
  private final RenderDragonBoss dragonRenderer;
  
  public LayerAsorahEyes(RenderDragonBoss dragonRendererIn) {
    this.dragonRenderer = dragonRendererIn;
  }
  
  public void doRenderLayer(EntityDragonBoss entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.dragonRenderer.bindTexture(TEXTURE);
    GlStateManager.enableBlend();
    GlStateManager.disableAlpha();
    GlStateManager.blendFunc(1, 1);
    GlStateManager.disableLighting();
    GlStateManager.depthFunc(514);
    int i = 61680;
    int j = i % 65536;
    int k = i / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
    GlStateManager.enableLighting();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.dragonRenderer.getMainModel().render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.dragonRenderer.setLightmap(entitylivingbaseIn);
    GlStateManager.disableBlend();
    GlStateManager.enableAlpha();
    GlStateManager.depthFunc(515);
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
