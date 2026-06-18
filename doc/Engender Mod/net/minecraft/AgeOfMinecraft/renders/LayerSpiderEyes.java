package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
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
    if (!p_177148_1_.isSurvivalTestSkin() && p_177148_1_.func_70089_S()) {
      this.spiderRenderer.func_110776_a(EngenderConfig.mobs.useMobTalkerModels ? ((this.spiderRenderer.func_177087_b() instanceof net.minecraft.AgeOfMinecraft.models.ModelCMMCaveSpider) ? (p_177148_1_.isAntiMob() ? antiCMM_CAVE_SPIDER_EYES : CMM_CAVE_SPIDER_EYES) : (p_177148_1_.isAntiMob() ? antiCMM_SPIDER_EYES : CMM_SPIDER_EYES)) : (p_177148_1_.isAntiMob() ? antiSPIDER_EYES : SPIDER_EYES));
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179112_b(1, 1);
      if (p_177148_1_.func_82150_aj()) {
        GlStateManager.func_179132_a(false);
      } else {
        GlStateManager.func_179132_a(true);
      } 
      int c0 = (p_177148_1_.getJukeboxToDanceTo() != null) ? 15728880 : 61680;
      int i = c0 % 65536;
      int j = c0 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.spiderRenderer.func_177087_b().func_78088_a((Entity)p_177148_1_, p_177148_2_, p_177148_3_, p_177148_5_, p_177148_6_, p_177148_7_, p_177148_8_);
      int k = 15728880;
      i = k % 65536;
      j = k / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
      this.spiderRenderer.func_177105_a((EntityLiving)p_177148_1_);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
