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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
    super(renderManagerIn, (ModelBase)new ModelGasterBlaster(), 0.5F);
  }
  
  protected boolean canRenderName(EntityGasterBlaster entity) {
    return false;
  }
  
  protected void preRenderCallback(EntityGasterBlaster entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.field_70173_aa <= 6 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 5.0F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, 0.0F, 0.0F);
    } 
  }
  
  private Vec3d getPositionAway(EntityGasterBlaster entity) {
    Vec3d vec3 = entity.func_70676_i(1.0F);
    double x = entity.field_70165_t + vec3.field_72450_a * (entity.fireLength * 5 - 50);
    double y = entity.field_70163_u + vec3.field_72448_b * (entity.fireLength * 5 - 50);
    double z = entity.field_70161_v + vec3.field_72449_c * (entity.fireLength * 5 - 50);
    return new Vec3d(x, y, z);
  }
  
  private Vec3d getPosition(EntityGasterBlaster entity) {
    return new Vec3d(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
  }
  
  public void doRender(EntityGasterBlaster entity, double x, double y, double z, float entityYaw, float partialTicks) {
    this.field_76987_f = 1.0F;
    if (entity.fireLength > 10) {
      Tessellator tessellator = Tessellator.func_178181_a();
      BufferBuilder vertexbuffer = tessellator.func_178180_c();
      func_110776_a(BEAM_TEXTURE);
      GlStateManager.func_187421_b(3553, 10242, 10497);
      GlStateManager.func_187421_b(3553, 10243, 10497);
      GlStateManager.func_179147_l();
      GlStateManager.func_179118_c();
      GlStateManager.func_179112_b(1, 1);
      GlStateManager.func_179140_f();
      GlStateManager.func_179132_a(true);
      OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 240.0F, 240.0F);
      GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.func_179094_E();
      GlStateManager.func_179137_b((float)x, (float)y + (entity.isSmall() ? 1.0D : 0.5D), (float)z);
      Vec3d vec3d = getPositionAway(entity);
      Vec3d vec3d1 = getPosition(entity);
      Vec3d vec3d2 = vec3d.func_178788_d(vec3d1);
      double d0 = vec3d2.func_72433_c() + 1.0D;
      vec3d2 = vec3d2.func_72432_b();
      float f5 = (float)Math.acos(vec3d2.field_72448_b);
      float f6 = (float)Math.atan2(vec3d2.field_72449_c, vec3d2.field_72450_a);
      GlStateManager.func_179114_b((1.5707964F + -f6) * 57.295776F, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(f5 * 57.295776F, 1.0F, 0.0F, 0.0F);
      vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
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
      vertexbuffer.func_181662_b(d12, d0, d13).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d12, 0.0D, d13).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d14, 0.0D, d15).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d14, d0, d15).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d16, d0, d17).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d16, 0.0D, d17).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d18, 0.0D, d19).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d18, d0, d19).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d4, d0, d5).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d6, d0, d7).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d10, d0, d11).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      vertexbuffer.func_181662_b(d8, d0, d9).func_187315_a(0.0D, 1.0D).func_181669_b(j, k, l, 255).func_181675_d();
      tessellator.func_78381_a();
      GlStateManager.func_179121_F();
      GlStateManager.func_179084_k();
      GlStateManager.func_179141_d();
    } 
    super.func_76986_a((EntityLivingBase)entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityGasterBlaster entity) {
    return GB_TEXTURE;
  }
}
