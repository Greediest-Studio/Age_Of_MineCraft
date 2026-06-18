package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityImplosion;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderImplosion extends Render<EntityImplosion> {
  public RenderImplosion(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void doRender(EntityImplosion entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getImplosionTime() > 0) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder vertexbuffer = tessellator.func_178180_c();
      RenderHelper.func_74518_a();
      float f = (entity.getImplosionTime() + partialTicks) / 500.0F;
      float f1 = 0.0F;
      if (f > 1.0F)
        f1 = (f - 1.0F) / 0.2F; 
      Random random = new Random(432L);
      GlStateManager.func_179090_x();
      GlStateManager.func_179103_j(7425);
      GlStateManager.func_179147_l();
      GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
      GlStateManager.func_179118_c();
      GlStateManager.func_179089_o();
      GlStateManager.func_179132_a(false);
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)x, (float)y, (float)z);
      GlStateManager.func_179152_a(f * 2.0F, f * 2.0F, f * 2.0F);
      float ti = (entity.getImplosionTime() + partialTicks - 498.0F) / 2.0F;
      if (entity.getImplosionTime() > 498)
        GlStateManager.func_179152_a(1.0F - ti, 1.0F - ti, 1.0F - ti); 
      GlStateManager.func_179109_b(0.0F, 0.375F, 0.0F);
      GlStateManager.func_179114_b(entity.getImplosionTime() * 3.0F, 1.0F, 1.0F, 1.0F);
      for (int i = 0; i < (f + f * f) / 2.0F * 320.0F; i++) {
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(random.nextFloat() * 720.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
        float f2 = random.nextFloat() * 40.0F + 5.0F + f1 * 10.0F;
        float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
        vertexbuffer.func_181668_a(6, DefaultVertexFormats.field_181706_f);
        vertexbuffer.func_181662_b(0.0D, 0.0D, 0.0D).func_181669_b(255, 255, 255, (int)(255.0F * (1.0F - f1))).func_181675_d();
        vertexbuffer.func_181662_b(-1.0D * f3, f2, (-1.0F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(1.0D * f3, f2, (-1.0F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(0.0D, f2, (1.0F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
        vertexbuffer.func_181662_b(-1.0D * f3, f2, (-1.0F * f3)).func_181669_b(255, 255, 255, 0).func_181675_d();
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
  
  protected ResourceLocation getEntityTexture(EntityImplosion entity) {
    return null;
  }
}
