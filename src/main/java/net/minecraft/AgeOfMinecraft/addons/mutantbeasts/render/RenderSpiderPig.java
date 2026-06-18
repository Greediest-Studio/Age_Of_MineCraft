package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntitySpiderPig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelSpiderPig;
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
public class RenderSpiderPig extends RenderLiving<EntitySpiderPig> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("spider_pig/spider_pig");
  
  private static final ResourceLocation SADDLE_TEXTURE = MutantBeasts.getEntityTexture("spider_pig/saddle");
  
  public RenderSpiderPig(RenderManager manager) {
    super(manager, (ModelBase)new ModelSpiderPig(0.0F), 0.8F);
    addLayer(new SaddleLayer());
  }
  
  protected float getDeathMaxRotation(EntitySpiderPig entityLivingBaseIn) {
    return 180.0F;
  }
  
  protected void preRenderCallback(EntitySpiderPig entitylivingbaseIn, float partialTickTime) {
    GlStateManager.scale(1.2F, 1.2F, 1.2F);
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
  
  protected ResourceLocation getEntityTexture(EntitySpiderPig entity) {
    return TEXTURE;
  }
  
  @SideOnly(Side.CLIENT)
  class SaddleLayer implements LayerRenderer<EntitySpiderPig> {
    private final ModelSpiderPig spiderPigModel = new ModelSpiderPig(-0.6F);
    
    public void doRenderLayer(EntitySpiderPig entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (!entityIn.isWild()) {
        RenderSpiderPig.this.bindTexture(RenderSpiderPig.SADDLE_TEXTURE);
        RenderSpiderPig.this.mainModel.setModelAttributes((ModelBase)this.spiderPigModel);
        this.spiderPigModel.render((Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
}
