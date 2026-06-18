package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelLesserShoggoth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserShoggoth;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLesserShoggoth extends RenderLiving<EntityLesserShoggoth> {
  private static final ResourceLocation shoggothResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/lessershoggoth.png");
  
  private static final ResourceLocation abyssalResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/abyssalshoggoth.png");
  
  private static final ResourceLocation dreadedResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/dreadedshoggoth.png");
  
  private static final ResourceLocation omotholResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/omotholshoggoth.png");
  
  private static final ResourceLocation darkResource = new ResourceLocation("abyssalcraft:textures/model/shoggoth/shadowshoggoth.png");
  
  public RenderLesserShoggoth(RenderManager manager) {
    super(manager, (ModelBase)new ModelLesserShoggoth(), 2.0F);
    addLayer((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected void renderModel(EntityLesserShoggoth entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (entitylivingbaseIn.getShoggothType() < 4) {
      super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      return;
    } 
    GlStateManager.enableBlend();
    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    GlStateManager.disableBlend();
  }
  
  protected ResourceLocation getEntityTexture(EntityLesserShoggoth par1EntityLiving) {
    switch (par1EntityLiving.getShoggothType()) {
      case 0:
        return shoggothResource;
      case 1:
        return abyssalResource;
      case 2:
        return dreadedResource;
      case 3:
        return omotholResource;
      case 4:
        return darkResource;
    } 
    return shoggothResource;
  }
  
  protected void preRenderCallback(EntityLesserShoggoth entitylivingbaseIn, float partialTickTime) {
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
