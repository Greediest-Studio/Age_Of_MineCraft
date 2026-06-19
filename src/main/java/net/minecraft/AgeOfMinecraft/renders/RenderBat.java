package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityBat;
import net.minecraft.AgeOfMinecraft.models.ModelBat;
import net.minecraft.AgeOfMinecraft.models.ModelBlaze;
import net.minecraft.client.model.ModelBase;
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
public class RenderBat extends RenderLiving<EntityBat> {
  private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");
  
  private static ModelBat regularmodel = new ModelBat();
  
  private static ModelBlaze disguisemodel = new ModelBlaze();
  
  public RenderBat(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelBat(), 0.25F);
    addLayer(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    addLayer(new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityBat entity) {
    return (entity.getIllusionFormTime() > 0) ? new ResourceLocation("textures/entity/blaze.png") : batTextures;
  }
  
  protected void preRenderCallback(EntityBat entitylivingbaseIn, float partialTickTime) {
    this.mainModel = (entitylivingbaseIn.getIllusionFormTime() > 0) ? (ModelBase)disguisemodel : (ModelBase)regularmodel;
    if (entitylivingbaseIn.getIllusionFormTime() <= 0)
      GlStateManager.scale(0.35F, 0.35F, 0.35F); 
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
  
  protected void applyRotations(EntityBat bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (!bat.getIsBatHanging()) {
      GlStateManager.translate(0.0F, MathHelper.cos((bat.isAIDisabled() ? 1.0F : p_77043_2_) * 0.3F) * 0.1F, 0.0F);
    } else {
      GlStateManager.translate(0.0F, -0.1F, 0.0F);
    } 
    super.applyRotations(bat, p_77043_2_, p_77043_3_, partialTicks);
  }
  
  public void doRender(EntityBat entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityBat entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
