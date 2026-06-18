package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityCoraliumSquid;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCoraliumSquid extends RenderLiving<EntityCoraliumSquid> {
  private static final ResourceLocation SQUID_TEXTURES = new ResourceLocation("abyssalcraft", "textures/model/coraliumsquid.png");
  
  public RenderCoraliumSquid(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelSquid(), 0.7F);
    addLayer((LayerRenderer)new LayerArrowCustomSized((RenderLivingBase)this, 1.0F));
    addLayer((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityCoraliumSquid entity) {
    return SQUID_TEXTURES;
  }
  
  protected void applyRotations(EntityCoraliumSquid entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    float f = entityLiving.prevSquidPitch + (entityLiving.squidPitch - entityLiving.prevSquidPitch) * partialTicks;
    float f1 = entityLiving.prevSquidYaw + (entityLiving.squidYaw - entityLiving.prevSquidYaw) * partialTicks;
    GlStateManager.translate(0.0F, 0.5F, 0.0F);
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
    GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
    GlStateManager.translate(0.0F, -1.2F, 0.0F);
    if (entityLiving.isChild())
      GlStateManager.translate(0.0F, 0.6F, 0.0F); 
    GlStateManager.rotate(90.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.deathTime > 0) {
      float f11 = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f11 = MathHelper.sqrt(f11);
      if (f11 > 1.0F)
        f11 = 1.0F; 
      GlStateManager.rotate(f11 * 90.0F, 0.0F, 1.0F, 0.0F);
    } else {
      String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
      if (s != null && (s.equals("Dinnerbone") || s.equals("Grumm"))) {
        GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected float handleRotationFloat(EntityCoraliumSquid livingBase, float partialTicks) {
    return livingBase.lastTentacleAngle + (livingBase.tentacleAngle - livingBase.lastTentacleAngle) * partialTicks;
  }
  
  protected void preRenderCallback(EntityCoraliumSquid entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
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
}
