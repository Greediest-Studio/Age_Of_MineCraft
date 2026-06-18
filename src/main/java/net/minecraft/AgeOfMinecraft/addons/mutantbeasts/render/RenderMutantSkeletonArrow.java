package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import chumbanotz.mutantbeasts.client.model.MutantArrowModel;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeletonArrow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMutantSkeletonArrow extends Render<EntityMutantSkeletonArrow> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("mutant_arrow");
  
  private final MutantArrowModel arrowModel = new MutantArrowModel();
  
  public RenderMutantSkeletonArrow(RenderManager renderManager) {
    super(renderManager);
  }
  
  public boolean shouldRender(EntityMutantSkeletonArrow entity, ICamera camera, double camX, double camY, double camZ) {
    return true;
  }
  
  public void doRender(EntityMutantSkeletonArrow entity, double x, double y, double z, float entityYaw, float partialTicks) {
    super.doRender(entity, x, y, z, entityYaw, partialTicks);
    GlStateManager.pushMatrix();
    GlStateManager.enableNormalize();
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.translate((float)x, (float)y, (float)z);
    bindEntityTexture(entity);
    for (int i = 0; i < entity.getClones(); i++) {
      GlStateManager.pushMatrix();
      float scale = entity.getSpeed() - i * 0.08F;
      double x1 = (entity.getTargetX() - entity.posX) * (entity.ticksExisted + partialTicks) * scale;
      double y1 = (entity.getTargetY() - entity.posY) * (entity.ticksExisted + partialTicks) * scale;
      double z1 = (entity.getTargetZ() - entity.posZ) * (entity.ticksExisted + partialTicks) * scale;
      GlStateManager.translate((float)x1, (float)y1, (float)z1);
      GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(entity.rotationPitch, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(1.2F, 1.2F, 1.2F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F - i * 0.08F);
      this.arrowModel.render((Entity)entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
    } 
    GlStateManager.disableBlend();
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.popMatrix();
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantSkeletonArrow entity) {
    return TEXTURE;
  }
}
