package net.minecraft.AgeOfMinecraft.renders;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
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
public class LayerEnderDragonDeath implements LayerRenderer<EntityEnderDragon> {
  public void doRenderLayer(EntityEnderDragon entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if (entitylivingbaseIn.deathTicks > 0) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder vertexbuffer = tessellator.func_178180_c();
      RenderHelper.func_74518_a();
      float f = (entitylivingbaseIn.deathTicks + partialTicks) / 200.0F;
      float f1 = 0.0F;
      if (f > 0.8F)
        f1 = (f - 0.8F) / 0.2F; 
      Random random = new Random(432L);
      GlStateManager.func_179090_x();
      GlStateManager.func_179103_j(7425);
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
      GlStateManager.func_179118_c();
      GlStateManager.func_179089_o();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b(0.0F, -1.0F, -2.0F);
      for (int i = 0; i < (f + f * f) / 2.0F * 60.0F; i++) {
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
        float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
        float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
        vertexbuffer.func_181668_a(6, DefaultVertexFormats.field_181706_f);
        vertexbuffer.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(255, 255, 255, (int)(255.0F * (1.0F - f1))).func_181675_d();
        vertexbuffer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(0.0D, f2, (1.0F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 0, 255, 0).func_181675_d();
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
    if (entitylivingbaseIn.getJukeboxToDanceTo() != null) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder vertexbuffer = tessellator.func_178180_c();
      RenderHelper.func_74518_a();
      float f = (entitylivingbaseIn.field_70173_aa + 20.0F + partialTicks) / 60.0F;
      float f1 = 0.0F;
      if (f > 0.8F)
        f1 = (f - 0.8F) / 0.2F; 
      Random random = new Random(1024L);
      GlStateManager.func_179090_x();
      GlStateManager.func_179103_j(7425);
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
      GlStateManager.func_179118_c();
      GlStateManager.func_179089_o();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179094_E();
      GlStateManager.func_179152_a(0.25F, 0.25F, 0.25F);
      GlStateManager.func_179109_b(0.0F, 2.0F, -30.0F);
      for (int i = 0; i < (f + f * f) / 2.0F * 30.0F; i++) {
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
        float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
        float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
        vertexbuffer.func_181668_a(6, DefaultVertexFormats.field_181706_f);
        vertexbuffer.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(random.nextInt(255), random.nextInt(255), random.nextInt(255), (int)(255.0F * (1.0F - f1))).func_181675_d();
        vertexbuffer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(0.0D, f2, (1.0F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(-0.866D * f3, f2, (-0.5F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
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
