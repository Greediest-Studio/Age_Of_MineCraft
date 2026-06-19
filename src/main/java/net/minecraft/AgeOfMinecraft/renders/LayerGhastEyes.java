package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGhastEyes implements LayerRenderer<EntityGhast> {
  private static final ResourceLocation cmmGhastTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/ghast1_overlay.png");
  
  private static final ResourceLocation cmmGhastShootingTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/ghast2_overlay.png");
  
  private static final ResourceLocation anticmmGhastTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/ghast1_overlay.png");
  
  private static final ResourceLocation anticmmGhastShootingTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/ghast2_overlay.png");
  
  private final RenderGhast creeperRenderer;
  
  public LayerGhastEyes(RenderGhast creeperRendererIn) {
    this.creeperRenderer = creeperRendererIn;
  }
  
  public void doRenderLayer(EntityGhast entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (EngenderConfig.mobs.useMobTalkerModels && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isEntityAlive()) {
      this.creeperRenderer.bindTexture(entitylivingbaseIn.isAttacking() ? (entitylivingbaseIn.isAntiMob() ? anticmmGhastShootingTextures : cmmGhastShootingTextures) : (entitylivingbaseIn.isAntiMob() ? anticmmGhastTextures : cmmGhastTextures));
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.blendFunc(1, 1);
      GlStateManager.disableLighting();
      if (entitylivingbaseIn.isInvisible()) {
        GlStateManager.depthMask(false);
      } else {
        GlStateManager.depthMask(true);
      } 
      char c0 = '\uF0F0';
      int i = c0 % 65536;
      int j = c0 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
      GlStateManager.enableLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      RenderLayerCompat.getMainModel(this.creeperRenderer).render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.creeperRenderer.setLightmap(entitylivingbaseIn);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return true;
  }
}
