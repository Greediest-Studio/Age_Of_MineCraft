package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.other.EntityManaOrb;
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
public class RenderManaOrb extends Render<EntityManaOrb> {
  private static final ResourceLocation EXPERIENCE_ORB_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/essence_orb.png");
  
  public RenderManaOrb(RenderManager renderManagerIn) {
    super(renderManagerIn);
    this.shadowSize = 0.15F;
    this.shadowOpaque = 0.75F;
  }
  
  public void doRender(EntityManaOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (!this.renderOutlines) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y, (float)z);
      bindEntityTexture(entity);
      RenderHelper.enableStandardItemLighting();
      float i = entity.getTextureByXP();
      float i1 = entity.getTextureY();
      float f = (i % 4.0F * 16.0F + 8.0F) / 64.0F;
      float f1 = (i % 4.0F * 16.0F + 0.0F) / 64.0F;
      float f2 = (i1 / 4.0F * 16.0F + 0.0F) / 64.0F;
      float f3 = (i1 / 4.0F * 16.0F + 16.0F) / 64.0F;
      int j = entity.getBrightnessForRender();
      int k = j % 65536;
      int l = j / 65536;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k, l);
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      float f9 = (entity.xpColor + partialTicks) / 2.0F;
      l = (int)((MathHelper.sin(f9 + 0.0F) + 1.0F) * 0.5F * 255.0F);
      GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 5.0F, 0.0F);
      GlStateManager.rotate(((this.renderManager.options.thirdPersonView == 2) ? -1.0F : 1.0F) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder bufferbuilder = tessellator.getBuffer();
      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
      if (entity.getEntropy()) {
        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(f, f3).color(255, l, l, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(f1, f3).color(255, l, l, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(f1, f2).color(255, l, l, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(f, f2).color(255, l, l, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
      } else {
        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex(f, f3).color(l, 255, 255, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex(f1, f3).color(l, 255, 255, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex(f1, f2).color(l, 255, 255, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex(f, f2).color(l, 255, 255, 255).normal(0.0F, 1.0F, 0.0F).endVertex();
      } 
      tessellator.draw();
      GlStateManager.disableBlend();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityManaOrb entity) {
    return EXPERIENCE_ORB_TEXTURES;
  }
}
