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
    GlStateManager.pushMatrix();
    GlStateManager.translate(x + 0.5D, y + 0.5D, z + 0.5D);
    GlStateManager.scale(wobbleX, wobbleY, 1.0F);
    bindEntityTexture(entity);
    float rot = (this.ticks * 2);
    double scale = 2.0D;
    GlStateManager.pushMatrix();
    GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    GlStateManager.pushMatrix();
    GlStateManager.rotate(rot, 0.0F, 0.0F, 1.0F);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder tes = tessellator.getBuffer();
    tes.begin(7, DefaultVertexFormats.POSITION_TEX);
    tes.pos(-scale, -scale, 0.0D).tex(0.0D, 0.0D).endVertex();
    tes.pos(-scale, scale, 0.0D).tex(0.0D, 1.0D).endVertex();
    tes.pos(scale, scale, 0.0D).tex(1.0D, 1.0D).endVertex();
    tes.pos(scale, -scale, 0.0D).tex(1.0D, 0.0D).endVertex();
    tessellator.draw();
    GlStateManager.popMatrix();
    GlStateManager.popMatrix();
    GlStateManager.popMatrix();
  }
  
  protected ResourceLocation getEntityTexture(EntityBlackHole entity) {
    return texture;
  }
}
