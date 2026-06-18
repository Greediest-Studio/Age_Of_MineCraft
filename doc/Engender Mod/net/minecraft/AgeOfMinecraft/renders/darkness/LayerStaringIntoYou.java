package net.minecraft.AgeOfMinecraft.renders.darkness;

import net.minecraft.AgeOfMinecraft.entity.untame.tier5.EntityDarkness;
import net.minecraft.client.Minecraft;
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
public class LayerStaringIntoYou implements LayerRenderer<EntityDarkness> {
  private final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
  
  private final RenderDarkness dragonRenderer;
  
  public LayerStaringIntoYou(RenderDarkness dragonRendererIn) {
    this.dragonRenderer = dragonRendererIn;
  }
  
  public void doRenderLayer(EntityDarkness entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.dragonRenderer.func_110776_a(this.TEXTURE);
    GlStateManager.func_179147_l();
    GlStateManager.func_179118_c();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    GlStateManager.func_179140_f();
    GlStateManager.func_179143_c(514);
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 1.572888E7F, 0.0F);
    GlStateManager.func_179145_e();
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    (Minecraft.func_71410_x()).field_71460_t.func_191514_d(true);
    this.dragonRenderer.func_177087_b().func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    (Minecraft.func_71410_x()).field_71460_t.func_191514_d(false);
    this.dragonRenderer.func_177105_a((EntityLiving)entitylivingbaseIn);
    GlStateManager.func_179084_k();
    GlStateManager.func_179141_d();
    GlStateManager.func_179143_c(515);
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
