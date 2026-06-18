package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityBlackHole;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlackHole extends Render<EntityBlackHole> {
  private static final ResourceLocation texture = new ResourceLocation("abyssalcraft", "textures/model/black_hole.png");
  
  int ticks;
  
  public RenderBlackHole(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void doRender(EntityBlackHole entity, double x, double y, double z, float entityYaw, float partialTicks) {
    this.ticks++;
    float wobbleScale = 0.05F;
    float wobble = this.ticks / 10.0F;
    float wobbleX = (float)(Math.sin(wobble) * wobbleScale) + 1.0F;
    float wobbleY = (float)(Math.sin(wobble) * -1.0D * wobbleScale) + 1.0F;
    GlStateManager.func_179094_E();
    GlStateManager.func_179137_b(x + 0.5D, y + 0.5D, z + 0.5D);
    GlStateManager.func_179152_a(wobbleX, wobbleY, 1.0F);
    func_180548_c((Entity)entity);
    float rot = (this.ticks * 2);
    double scale = 2.0D;
    GlStateManager.func_179094_E();
    GlStateManager.func_179114_b(-this.field_76990_c.field_78735_i, 0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(this.field_76990_c.field_78732_j, 1.0F, 0.0F, 0.0F);
    GlStateManager.func_179094_E();
    GlStateManager.func_179114_b(rot, 0.0F, 0.0F, 1.0F);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder tes = tessellator.func_178180_c();
    tes.func_181668_a(7, DefaultVertexFormats.field_181707_g);
    tes.func_181662_b(-scale, -scale, 0.0D).func_187315_a(0.0D, 0.0D).func_181675_d();
    tes.func_181662_b(-scale, scale, 0.0D).func_187315_a(0.0D, 1.0D).func_181675_d();
    tes.func_181662_b(scale, scale, 0.0D).func_187315_a(1.0D, 1.0D).func_181675_d();
    tes.func_181662_b(scale, -scale, 0.0D).func_187315_a(1.0D, 0.0D).func_181675_d();
    tessellator.func_78381_a();
    GlStateManager.func_179121_F();
    GlStateManager.func_179121_F();
    GlStateManager.func_179121_F();
  }
  
  protected ResourceLocation getEntityTexture(EntityBlackHole entity) {
    return texture;
  }
}
