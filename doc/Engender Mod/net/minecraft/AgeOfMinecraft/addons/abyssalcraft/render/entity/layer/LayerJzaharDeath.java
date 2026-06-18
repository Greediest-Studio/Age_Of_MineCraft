package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerJzaharDeath implements LayerRenderer<EntityJzahar> {
  public void doRenderLayer(EntityJzahar entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (entitylivingbaseIn.deathTicks > 400) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder worldrenderer = tessellator.func_178180_c();
      RenderHelper.func_74518_a();
      float f = (entitylivingbaseIn.deathTicks + partialTicks - 400.0F) / 400.0F;
      float f1 = 0.0F;
      if (f > 0.8F)
        f1 = (f - 0.8F) / 0.2F; 
      Random random = new Random(432L);
      GlStateManager.func_179090_x();
      GlStateManager.func_179103_j(7425);
      GlStateManager.func_179147_l();
      GlStateManager.func_179112_b(770, 1);
      GlStateManager.func_179118_c();
      GlStateManager.func_179089_o();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179094_E();
      GlStateManager.func_179139_a(0.25D, 0.25D, 0.25D);
      for (int i = 0; i < (f + f * f) / 2.0F * 30.0F; i++) {
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
        float f2 = random.nextFloat() * 20.0F + 5.0F + f1;
        float f3 = random.nextFloat() * 2.0F + 1.0F + f1;
        worldrenderer.func_181668_a(6, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(255, 255, 255, (int)(255.0F * (1.0F - f1))).func_181675_d();
        worldrenderer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(81, 189, 178, 0).func_181675_d();
        worldrenderer.func_181662_b(0.866D * f3, f2, (-0.5F * f3)).func_181669_b(81, 189, 178, 0).func_181675_d();
        worldrenderer.func_181662_b(0.0D, f2, (1.0F * f3)).func_181669_b(81, 189, 178, 0).func_181675_d();
        worldrenderer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(81, 189, 178, 0).func_181675_d();
        tessellator.func_78381_a();
      } 
      GlStateManager.func_179121_F();
      GlStateManager.func_179132_a(true);
      GlStateManager.func_179129_p();
      GlStateManager.func_179084_k();
      GlStateManager.func_179103_j(7424);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179098_w();
      GlStateManager.func_179141_d();
      RenderHelper.func_74519_b();
    } 
  }
  
  public boolean func_177142_b() {
    return false;
  }
}
