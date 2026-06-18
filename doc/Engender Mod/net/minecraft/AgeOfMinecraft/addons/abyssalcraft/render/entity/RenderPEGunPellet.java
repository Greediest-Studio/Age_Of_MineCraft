package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPEGunPellet extends Render<EntityPEGunPellet> {
  private static final ResourceLocation EXPERIENCE_ORB_TEXTURES = new ResourceLocation("textures/entity/experience_orb.png");
  
  public RenderPEGunPellet(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void doRender(EntityPEGunPellet entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (!this.field_188301_f) {
      GlStateManager.func_179094_E();
      GlStateManager.func_179109_b((float)x, (float)y, (float)z);
      func_180548_c((Entity)entity);
      RenderHelper.func_74519_b();
      int i = entity.texture;
      float f = (i % 4 * 16 + 0) / 64.0F;
      float f1 = (i % 4 * 16 + 16) / 64.0F;
      float f2 = (i / 4 * 16 + 0) / 64.0F;
      float f3 = (i / 4 * 16 + 16) / 64.0F;
      int j = entity.func_70070_b();
      int k = j % 65536;
      int l = j / 65536;
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, k, l);
      GlStateManager.func_179131_c(0.5F, 0.25F, 1.0F, 1.0F);
      float f9 = ((entity.field_70173_aa + entity.func_145782_y()) + partialTicks) / 2.0F;
      l = (int)((MathHelper.func_76126_a(f9 + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int j1 = (int)((MathHelper.func_76126_a(f9 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
      GlStateManager.func_179109_b(0.0F, 0.1F, 0.0F);
      GlStateManager.func_179114_b(180.0F - this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(((this.field_76990_c.field_78733_k.field_74320_O == 2) ? -1 : true) * -this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(0.3F, 0.3F, 0.3F);
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder vertexbuffer = tessellator.func_178180_c();
      vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181712_l);
      vertexbuffer.func_181662_b(-0.5D, -0.25D, 0.0D).func_187315_a(f, f3).func_181669_b(l, 255, j1, 128).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      vertexbuffer.func_181662_b(0.5D, -0.25D, 0.0D).func_187315_a(f1, f3).func_181669_b(l, 255, j1, 128).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      vertexbuffer.func_181662_b(0.5D, 0.75D, 0.0D).func_187315_a(f1, f2).func_181669_b(l, 255, j1, 128).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      vertexbuffer.func_181662_b(-0.5D, 0.75D, 0.0D).func_187315_a(f, f2).func_181669_b(l, 255, j1, 128).func_181663_c(0.0F, 1.0F, 0.0F).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179084_k();
      GlStateManager.func_179101_C();
      GlStateManager.func_179121_F();
      super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityPEGunPellet entity) {
    return EXPERIENCE_ORB_TEXTURES;
  }
}
