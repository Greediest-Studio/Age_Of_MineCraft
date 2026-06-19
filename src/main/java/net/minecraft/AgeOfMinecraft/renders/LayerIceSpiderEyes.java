package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityIceSpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerIceSpiderEyes<T extends EntityIceSpider> implements LayerRenderer<T> {
  private static final ResourceLocation CMM_SPIDER_EYES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/ispider_eyes.png");
  
  private static final ResourceLocation SPIDER_EYES = new ResourceLocation("ageofminecraft", "textures/entities/ice_spider_eyes.png");
  
  private final RenderIceSpider spiderRenderer;
  
  public LayerIceSpiderEyes(RenderIceSpider p_i46109_1_) {
    this.spiderRenderer = p_i46109_1_;
  }
  
  public void doRenderLayer(T p_177148_1_, float p_177148_2_, float p_177148_3_, float p_177148_4_, float p_177148_5_, float p_177148_6_, float p_177148_7_, float p_177148_8_) {
    this.spiderRenderer.bindTexture(EngenderConfig.mobs.useMobTalkerModels ? CMM_SPIDER_EYES : SPIDER_EYES);
    GlStateManager.enableBlend();
    GlStateManager.disableAlpha();
    GlStateManager.blendFunc(1, 1);
    if (p_177148_1_.isInvisible()) {
      GlStateManager.depthMask(false);
    } else {
      GlStateManager.depthMask(true);
    } 
    char c0 = '\uF0F0';
    int i = c0 % 65536;
    int j = c0 / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    this.spiderRenderer.getMainModel().render((Entity)p_177148_1_, p_177148_2_, p_177148_3_, p_177148_5_, p_177148_6_, p_177148_7_, p_177148_8_);
    int k = p_177148_1_.getBrightnessForRender();
    i = k % 65536;
    j = k / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
    this.spiderRenderer.setLightmap(p_177148_1_);
    GlStateManager.disableBlend();
    GlStateManager.enableAlpha();
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
