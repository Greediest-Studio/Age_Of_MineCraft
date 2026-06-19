package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCreeperGlow implements LayerRenderer<EntityCreeper> {
  private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/creeperp_overlay.png");
  
  private static final ResourceLocation Anti_LIGHTNING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/creeperp_overlay.png");
  
  private final RenderCreeper creeperRenderer;
  
  public LayerCreeperGlow(RenderCreeper creeperRendererIn) {
    this.creeperRenderer = creeperRendererIn;
  }
  
  public void doRenderLayer(EntityCreeper entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (entitylivingbaseIn.getPowered() && EngenderConfig.mobs.useMobTalkerModels && !entitylivingbaseIn.isInvisible()) {
      this.creeperRenderer.bindTexture(entitylivingbaseIn.isAntiMob() ? Anti_LIGHTNING_TEXTURE : LIGHTNING_TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.blendFunc(1, 1);
      GlStateManager.disableLighting();
      boolean flag = entitylivingbaseIn.isInvisible();
      GlStateManager.depthMask(!flag);
      int c0 = 15728880;
      int i = c0 % 65536;
      int j = c0 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
      GlStateManager.enableLighting();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.creeperRenderer.getMainModel().render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.creeperRenderer.setLightmap(entitylivingbaseIn);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return true;
  }
}
