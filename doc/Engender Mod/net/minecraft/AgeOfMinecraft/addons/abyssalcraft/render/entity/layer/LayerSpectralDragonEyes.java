package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.RenderDragonMinion;
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
public class LayerSpectralDragonEyes implements LayerRenderer<EntityDragonMinion> {
  private static final ResourceLocation TEXTURE = new ResourceLocation("abyssalcraft:textures/model/elite/dragonminion_eyes.png");
  
  private final RenderDragonMinion dragonRenderer;
  
  public LayerSpectralDragonEyes(RenderDragonMinion dragonRendererIn) {
    this.dragonRenderer = dragonRendererIn;
  }
  
  public void doRenderLayer(EntityDragonMinion entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.dragonRenderer.func_110776_a(TEXTURE);
    GlStateManager.func_179147_l();
    GlStateManager.func_179118_c();
    GlStateManager.func_179112_b(1, 1);
    GlStateManager.func_179140_f();
    GlStateManager.func_179143_c(514);
    int i = 61680;
    int j = i % 65536;
    int k = i / 65536;
    OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, j / 1.0F, k / 1.0F);
    GlStateManager.func_179145_e();
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    this.dragonRenderer.func_177087_b().func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
    this.dragonRenderer.func_177105_a((EntityLiving)entitylivingbaseIn);
    GlStateManager.func_179084_k();
    GlStateManager.func_179141_d();
    GlStateManager.func_179143_c(515);
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
