package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadedChargeOther;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDreadedCharge extends Render<EntityDreadedChargeOther> {
  private static final ResourceLocation DRAGON_FIREBALL_TEXTURE = new ResourceLocation("abyssalcraft", "textures/model/dreaded_fireball.png");
  
  public RenderDreadedCharge(RenderManager renderManagerIn) {
    super(renderManagerIn);
  }
  
  public void doRender(EntityDreadedChargeOther entity, double x, double y, double z, float entityYaw, float partialTicks) {
    GlStateManager.pushMatrix();
    bindEntityTexture(entity);
    GlStateManager.translate((float)x, (float)y, (float)z);
    GlStateManager.enableRescaleNormal();
    GlStateManager.scale(2.0F, 2.0F, 2.0F);
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder vertexbuffer = tessellator.getBuffer();
    GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1.0F : 1.0F) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    if (this.renderOutlines) {
      GlStateManager.enableColorMaterial();
      GlStateManager.enableOutlineMode(getTeamColor(entity));
    } 
    vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
    vertexbuffer.pos(-0.5D, -0.25D, 0.0D).tex(0.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    vertexbuffer.pos(0.5D, -0.25D, 0.0D).tex(1.0D, 1.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    vertexbuffer.pos(0.5D, 0.75D, 0.0D).tex(1.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    vertexbuffer.pos(-0.5D, 0.75D, 0.0D).tex(0.0D, 0.0D).normal(0.0F, 1.0F, 0.0F).endVertex();
    tessellator.draw();
    if (this.renderOutlines) {
      GlStateManager.disableOutlineMode();
      GlStateManager.disableColorMaterial();
    } 
    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityDreadedChargeOther entity) {
    return DRAGON_FIREBALL_TEXTURE;
  }
}
