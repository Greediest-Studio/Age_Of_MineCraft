package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
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
public class LayerCreeperGlow implements LayerRenderer<EntityCreeper> {
  private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/creeperp_overlay.png");
  
  private static final ResourceLocation Anti_LIGHTNING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/creeperp_overlay.png");
  
  private final RenderCreeper creeperRenderer;
  
  public LayerCreeperGlow(RenderCreeper creeperRendererIn) {
    this.creeperRenderer = creeperRendererIn;
  }
  
  public void doRenderLayer(EntityCreeper entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (entitylivingbaseIn.getPowered() && EngenderConfig.mobs.useMobTalkerModels && !entitylivingbaseIn.func_82150_aj()) {
      this.creeperRenderer.func_110776_a(entitylivingbaseIn.isAntiMob() ? Anti_LIGHTNING_TEXTURE : LIGHTNING_TEXTURE);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179112_b(1, 1);
      GlStateManager.func_179140_f();
      boolean flag = entitylivingbaseIn.func_82150_aj();
      GlStateManager.func_179132_a(!flag);
      int c0 = 15728880;
      int i = c0 % 65536;
      int j = c0 / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, i / 1.0F, j / 1.0F);
      GlStateManager.func_179145_e();
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      this.creeperRenderer.func_177087_b().func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.creeperRenderer.func_177105_a((EntityLiving)entitylivingbaseIn);
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
    } 
  }
  
  public boolean func_177142_b() {
    return true;
  }
}
