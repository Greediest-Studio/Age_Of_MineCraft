package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityCow;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCow extends RenderLiving<EntityCow> {
  private static final ResourceLocation cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
  
  private static final ResourceLocation anticowTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/cow.png");
  
  public RenderCow(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelCow(), 0.7F);
    addLayer(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    addLayer(new LayerCustomHeadEngender(((ModelCow)this.mainModel).head, ((ModelCow)this.mainModel).head));
    addLayer(new LayerLearningBook(this));
  }
  
  protected void preRenderCallback(EntityCow entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (!entitylivingbaseIn.onGround)
      GlStateManager.rotate(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
    if (entitylivingbaseIn.ticksExisted <= 21 && entitylivingbaseIn.ticksExisted > 0) {
      float f5 = (entitylivingbaseIn.ticksExisted + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.scale(f5, f5, f5);
      GlStateManager.rotate(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityCow entity) {
    return entity.isAntiMob() ? anticowTextures : cowTextures;
  }
  
  public void doRender(EntityCow entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = handleRotationFloat(entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.doRender(entity, x + (avec3d[i]).x + MathHelper.cos(i + f * 0.5F) * 0.025D, y + (avec3d[i]).y + MathHelper.cos(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).z + MathHelper.cos(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.shadowOpaque = 0.0F;
    } else {
      this.shadowOpaque = 1.0F;
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityTameBase entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
