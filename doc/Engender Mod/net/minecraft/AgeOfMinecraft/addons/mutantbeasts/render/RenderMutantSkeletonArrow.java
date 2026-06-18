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
    super.func_76986_a((Entity)entity, x, y, z, entityYaw, partialTicks);
    GlStateManager.func_179094_E();
    GlStateManager.func_179108_z();
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    GlStateManager.func_179109_b((float)x, (float)y, (float)z);
    func_180548_c((Entity)entity);
    for (int i = 0; i < entity.getClones(); i++) {
      GlStateManager.func_179094_E();
      float scale = entity.getSpeed() - i * 0.08F;
      double x1 = (entity.getTargetX() - entity.field_70165_t) * (entity.field_70173_aa + partialTicks) * scale;
      double y1 = (entity.getTargetY() - entity.field_70163_u) * (entity.field_70173_aa + partialTicks) * scale;
      double z1 = (entity.getTargetZ() - entity.field_70161_v) * (entity.field_70173_aa + partialTicks) * scale;
      GlStateManager.func_179109_b((float)x1, (float)y1, (float)z1);
      GlStateManager.func_179114_b(entity.field_70177_z, 0.0F, 1.0F, 0.0F);
      GlStateManager.func_179114_b(entity.field_70125_A, 1.0F, 0.0F, 0.0F);
      GlStateManager.func_179152_a(1.2F, 1.2F, 1.2F);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F - i * 0.08F);
      this.arrowModel.func_78088_a((Entity)entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.func_179121_F();
    } 
    GlStateManager.func_179084_k();
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179121_F();
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantSkeletonArrow entity) {
    return TEXTURE;
  }
}
