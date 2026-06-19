package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.cameos.EntityGasterBlaster;
import net.minecraft.AgeOfMinecraft.models.ModelGasterBlaster;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGasterBlaster extends RenderLivingBase<EntityGasterBlaster> {
  private static final ResourceLocation GB_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/gasterblaster.png");
  
  private static final ResourceLocation BEAM_TEXTURE = new ResourceLocation("textures/entity/beacon_beam.png");
  
  public RenderGasterBlaster(RenderManager renderManagerIn) {
    super(renderManagerIn, new ModelGasterBlaster(), 0.5F);
  }
  
  protected boolean canRenderName(EntityGasterBlaster entity) {
    return false;
  }
  
  protected void preRenderCallback(EntityGasterBlaster entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.ticksExisted <= 6 && entitylivingbaseIn.ticksExisted > 0) {
      float f5 = (entitylivingbaseIn.ticksExisted + partialTickTime - 1.0F) / 5.0F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.scale(f5, f5, f5);
      GlStateManager.rotate(f5 * 90.0F - 90.0F, f5, 0.0F, 0.0F);
    } 
  }
  
  private Vec3d getPositionAway(EntityGasterBlaster entity) {
    Vec3d vec3 = entity.getLook(1.0F);
    double x = entity.posX + vec3.x * (entity.fireLength * 5 - 50);
    double y = entity.posY + vec3.y * (entity.fireLength * 5 - 50);
    double z = entity.posZ + vec3.z * (entity.fireLength * 5 - 50);
    return new Vec3d(x, y, z);
  }
  
  private Vec3d getPosition(EntityGasterBlaster entity) {
    return new Vec3d(entity.posX, entity.posY, entity.posZ);
  }
  
  public void doRender(EntityGasterBlaster entity, double x, double y, double z, float entityYaw, float partialTicks) {
    RenderLayerCompat.setShadowOpaque(this, 1.0F);
    if (entity.fireLength > 10) {
      Tessellator tessellator = Tessellator.getInstance();
      BufferBuilder vertexbuffer = tessellator.getBuffer();
      bindTexture(BEAM_TEXTURE);
      GlStateManager.glTexParameteri(3553, 10242, 10497);
      GlStateManager.glTexParameteri(3553, 10243, 10497);
      GlStateManager.enableBlend();
      GlStateManager.disableAlpha();
      GlStateManager.blendFunc(1, 1);
      GlStateManager.disableLighting();
      GlStateManager.depthMask(true);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)x, (float)y + (entity.isSmall() ? 1.0D : 0.5D), (float)z);
      Vec3d vec3d = getPositionAway(entity);
      Vec3d vec3d1 = getPosition(entity);
      Vec3d vec3d2 = vec3d.subtract(vec3d1);
      double d0 = vec3d2.length() + 1.0D;
      vec3d2 = vec3d2.normalize();
      float f5 = (float)Math.acos(vec3d2.y);
      float f6 = (float)Math.atan2(vec3d2.z, vec3d2.x);
      GlStateManager.rotate((1.5707964F + -f6) * 57.295776F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(f5 * 57.295776F, 1.0F, 0.0F, 0.0F);
      vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      int j = 255;
      int k = 255;
      int l = 255;
      float v = entity.fireLength * 0.1F - 2.0F;
      if (v > (entity.isSmall() ? 0.25F : 0.5F))
        v = entity.isSmall() ? 0.25F : 0.5F; 
      double d4 = 0.0D + Math.cos(2.356194490192345D) * v;
      double d5 = 0.0D + Math.sin(2.356194490192345D) * v;
      double d6 = 0.0D + Math.cos(0.7853981633974483D) * v;
      double d7 = 0.0D + Math.sin(0.7853981633974483D) * v;
      double d8 = 0.0D + Math.cos(3.9269908169872414D) * v;
      double d9 = 0.0D + Math.sin(3.9269908169872414D) * v;
      double d10 = 0.0D + Math.cos(5.497787143782138D) * v;
      double d11 = 0.0D + Math.sin(5.497787143782138D) * v;
      double d12 = 0.0D + Math.cos(Math.PI) * v;
      double d13 = 0.0D + Math.sin(Math.PI) * v;
      double d14 = 0.0D + Math.cos(0.0D) * v;
      double d15 = 0.0D + Math.sin(0.0D) * v;
      double d16 = 0.0D + Math.cos(1.5707963267948966D) * v;
      double d17 = 0.0D + Math.sin(1.5707963267948966D) * v;
      double d18 = 0.0D + Math.cos(4.71238898038469D) * v;
      double d19 = 0.0D + Math.sin(4.71238898038469D) * v;
      vertexbuffer.pos(d12, d0, d13).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d12, 0.0D, d13).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d14, 0.0D, d15).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d14, d0, d15).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d16, d0, d17).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d16, 0.0D, d17).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d18, 0.0D, d19).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d18, d0, d19).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d4, d0, d5).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d6, d0, d7).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d10, d0, d11).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      vertexbuffer.pos(d8, d0, d9).tex(0.0D, 1.0D).color(j, k, l, 255).endVertex();
      tessellator.draw();
      GlStateManager.popMatrix();
      GlStateManager.disableBlend();
      GlStateManager.enableAlpha();
    } 
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityGasterBlaster entity) {
    return GB_TEXTURE;
  }
}
