package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import net.minecraft.AgeOfMinecraft.renders.RenderLayerCompat;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSnowGolem;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelMutantSnowGolem;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMutantSnowGolem extends RenderLiving<EntityMutantSnowGolem> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("mutant_snow_golem/mutant_snow_golem");
  
  private static final ResourceLocation PUMPKIN_TEXTURE = MutantBeasts.getEntityTexture("mutant_snow_golem/pumpkin");
  
  private static final ResourceLocation GLOW_TEXTURE = MutantBeasts.getEntityTexture("mutant_snow_golem/glow");
  
  private static final ResourceLocation Anti_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_snow_golem.png");
  
  public RenderMutantSnowGolem(RenderManager manager) {
    super(manager, new ModelMutantSnowGolem(), 0.7F);
    RenderLayerCompat.addLayer(this, new GlowLayer());
    RenderLayerCompat.addLayer(this, new ThrownBlockLayer());
  }
  
  protected void preRenderCallback(EntityMutantSnowGolem entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
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
  
  public ModelMutantSnowGolem getMutantSnowGolemModel() {
    return (ModelMutantSnowGolem)RenderLayerCompat.getMainModel(this);
  }
  
  protected void renderModel(EntityMutantSnowGolem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    if (!entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isPumpkinEquipped()) {
      bindTexture(PUMPKIN_TEXTURE);
      RenderLayerCompat.getMainModel(this).render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantSnowGolem entity) {
    return entity.isAntiMob() ? Anti_TEXTURE : TEXTURE;
  }
  
  @SideOnly(Side.CLIENT)
  class GlowLayer implements LayerRenderer<EntityMutantSnowGolem> {
    public void doRenderLayer(EntityMutantSnowGolem entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (entityIn.isPumpkinEquipped()) {
        RenderMutantSnowGolem.this.bindTexture(RenderMutantSnowGolem.GLOW_TEXTURE);
        GlStateManager.disableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        float f = entityIn.ticksExisted + partialTicks;
        float f1 = MathHelper.cos(f * 0.1F);
        float f2 = MathHelper.cos(f * 0.15F);
        GlStateManager.color(1.0F, 0.8F + 0.05F * f2, 0.15F + 0.2F * f1, 1.0F);
        (Minecraft.getMinecraft()).entityRenderer.setupFogColor(true);
        RenderLayerCompat.getMainModel(RenderMutantSnowGolem.this).render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        (Minecraft.getMinecraft()).entityRenderer.setupFogColor(false);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderMutantSnowGolem.this.setLightmap(entityIn);
        GlStateManager.enableLighting();
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
  
  @SideOnly(Side.CLIENT)
  class ThrownBlockLayer implements LayerRenderer<EntityMutantSnowGolem> {
    public void doRenderLayer(EntityMutantSnowGolem entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (entityIn.isThrowing() && entityIn.getThrowingTick() < 7) {
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.4F, 0.0F, 0.0F);
        RenderMutantSnowGolem.this.getMutantSnowGolemModel().postRenderArm(0.0625F);
        GlStateManager.translate(0.0F, 0.9F, 0.0F);
        GlStateManager.scale(-0.8F, -0.8F, 0.8F);
        int i = entityIn.getBrightnessForRender();
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderMutantSnowGolem.this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.translate(-0.5F, -0.5F, 0.5F);
        Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness((!entityIn.getHeldItemMainhand().isEmpty() && entityIn.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemBlock) ? Block.getBlockFromItem(entityIn.getHeldItemMainhand().getItem()).getDefaultState() : Blocks.ICE.getDefaultState(), 1.0F);
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
}
