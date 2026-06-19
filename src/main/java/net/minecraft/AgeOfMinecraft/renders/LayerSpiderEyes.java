package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSpiderEyes<T extends EntitySpider> implements LayerRenderer<T> {
  private static final ResourceLocation CMM_SPIDER_EYES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/spider_eyes.png");
  
  private static final ResourceLocation CMM_CAVE_SPIDER_EYES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/cspider_eyes.png");
  
  private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
  
  private static final ResourceLocation antiCMM_SPIDER_EYES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/spider_eyes.png");
  
  private static final ResourceLocation antiCMM_CAVE_SPIDER_EYES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/cspider_eyes.png");
  
  private static final ResourceLocation antiSPIDER_EYES = new ResourceLocation("ageofminecraft", "textures/entities/anti/spider_eyes.png");
  
  private final RenderSpider<T> spiderRenderer;
  
  public LayerSpiderEyes(RenderSpider<T> p_i46109_1_) {
    this.spiderRenderer = p_i46109_1_;
  }
  
  public void doRenderLayer(T p_177148_1_, float p_177148_2_, float p_177148_3_, float p_177148_4_, float p_177148_5_, float p_177148_6_, float p_177148_7_, float p_177148_8_) {
    if (!p_177148_1_.isSurvivalTestSkin() && p_177148_1_.isEntityAlive()) {
      this.spiderRenderer.bindTexture(EngenderConfig.mobs.useMobTalkerModels ? ((this.spiderRenderer.getMainModel() instanceof net.minecraft.AgeOfMinecraft.models.ModelCMMCaveSpider) ? (p_177148_1_.isAntiMob() ? antiCMM_CAVE_SPIDER_EYES : CMM_CAVE_SPIDER_EYES) : (p_177148_1_.isAntiMob() ? antiCMM_SPIDER_EYES : CMM_SPIDER_EYES)) : (p_177148_1_.isAntiMob() ? antiSPIDER_EYES : SPIDER_EYES));
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.blendFunc(1, 1);
      if (p_177148_1_.isInvisible()) {
        GlStateManager.depthMask(false);
      } else {
        GlStateManager.depthMask(true);
      } 
      int c0 = (p_177148_1_.getJukeboxToDanceTo() != null) ? 15728880 : 61680;
      int i = c0 % 65536;
      int j = c0 / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.spiderRenderer.getMainModel().render(p_177148_1_, p_177148_2_, p_177148_3_, p_177148_5_, p_177148_6_, p_177148_7_, p_177148_8_);
      int k = 15728880;
      i = k % 65536;
      j = k / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, i / 1.0F, j / 1.0F);
      this.spiderRenderer.setLightmap(p_177148_1_);
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    } 
  }
  
  public boolean shouldCombineTextures() {
    return false;
  }
}
