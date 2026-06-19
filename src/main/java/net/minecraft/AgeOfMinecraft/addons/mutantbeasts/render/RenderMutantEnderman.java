package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelMutantEnderman;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMutantEnderman extends RenderLiving<EntityMutantEnderman> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("mutant_enderman/mutant_enderman");
  
  private static final ResourceLocation Anti_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_enderman.png");
  
  private static final ResourceLocation GLOW_TEXTURE = MutantBeasts.getEntityTexture("endersoul");
  
  private static final ResourceLocation EYES_TEXTURE = MutantBeasts.getEntityTexture("mutant_enderman/eyes");
  
  private static final ResourceLocation Anti_EYES_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_enderman_eyes.png");
  
  private static final ResourceLocation DEATH_TEXTURE = MutantBeasts.getEntityTexture("mutant_enderman/death");
  
  private static ModelMutantEnderman endermanModel = new ModelMutantEnderman();
  
  private static ModelEnderman cloneModel = new ModelEnderman(0.0F);
  
  private boolean teleportAttack;
  
  public RenderMutantEnderman(RenderManager manager) {
    super(manager, (ModelBase)new ModelMutantEnderman(), 0.8F);
    addLayer(new EyesLayer());
    addLayer(new GlowLayer());
  }
  
  protected void renderModel(EntityMutantEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (entitylivingbaseIn.deathTime > 80) {
      float blendFactor = (entitylivingbaseIn.deathTime - 80) / 200.0F;
      GlStateManager.depthFunc(515);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, blendFactor);
      bindTexture(DEATH_TEXTURE);
      this.mainModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.depthFunc(514);
    } 
    super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    GlStateManager.depthFunc(515);
  }
  
  protected void preRenderCallback(EntityMutantEnderman entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
    if (!entitylivingbaseIn.onGround)
      GlStateManager.rotate(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
  }
  
  public void doRender(EntityMutantEnderman entity, double x, double y, double z, float entityYaw, float partialTicks) {
    this.shadowSize = entity.isClone() ? 0.5F : 0.8F;
    this.shadowOpaque = entity.isClone() ? 0.5F : 1.0F;
    this.teleportAttack = false;
    double addX = 0.0D;
    double addZ = 0.0D;
    this.mainModel = entity.isClone() ? (ModelBase)cloneModel : (ModelBase)endermanModel;
    cloneModel.isAttacking = entity.isAggressive();
    cloneModel.isCarrying = (entity.heldBlock[1] != 0);
    boolean forcedLook = (entity.getAttackID() == 6);
    boolean scream = (entity.getAttackID() == 8);
    boolean clone = (entity.isClone() && entity.isAggressive());
    boolean telesmash = (entity.getAttackID() == 10 && entity.getAttackTick() < 18);
    boolean death = (entity.getAttackID() == 11);
    if (forcedLook || scream || clone || telesmash || death) {
      double shake = 0.03D;
      if (entity.getAttackTick() >= 40 && !clone && !death)
        shake *= 0.5D; 
      if (clone)
        shake = 0.02D; 
      if (death)
        shake = (entity.getAttackTick() < 80) ? 0.019999999552965164D : 0.05000000074505806D; 
      addX = entity.getRNG().nextGaussian() * shake;
      addZ = entity.getRNG().nextGaussian() * shake;
    } 
    super.doRender(entity, x + addX, y, z + addZ, entityYaw, partialTicks);
  }
  
  protected float getDeathMaxRotation(EntityMutantEnderman entityLivingBaseIn) {
    return 0.0F;
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantEnderman entity) {
    return entity.isClone() ? new ResourceLocation("textures/entity/enderman/enderman_eyes.png") : (entity.isAntiMob() ? Anti_TEXTURE : TEXTURE);
  }
  
  @SideOnly(Side.CLIENT)
  class EyesLayer implements LayerRenderer<EntityMutantEnderman> {
    public void doRenderLayer(EntityMutantEnderman entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (!entityIn.isClone() && entityIn.deathTime <= 80) {
        GlStateManager.disableLighting();
        RenderMutantEnderman.this.bindTexture(entityIn.isAntiMob() ? RenderMutantEnderman.Anti_EYES_TEXTURE : RenderMutantEnderman.EYES_TEXTURE);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        RenderMutantEnderman.this.mainModel.render((Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        RenderMutantEnderman.this.setLightmap(entityIn);
        GlStateManager.enableLighting();
      } 
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
  
  @SideOnly(Side.CLIENT)
  class GlowLayer implements LayerRenderer<EntityMutantEnderman> {
    public void doRenderLayer(EntityMutantEnderman entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      boolean scream = (entityIn.getAttackID() == 8);
      boolean clone = entityIn.isClone();
      if (scream || clone) {
        float glowScale = 2.0F;
        if (scream)
          if (entityIn.getAttackTick() < 40) {
            glowScale = 1.2F + (entityIn.getAttackTick() + partialTicks) / 40.0F;
          } else if (entityIn.getAttackTick() < 160) {
            glowScale = 2.2F;
          } else {
            glowScale = 2.2F - (entityIn.getAttackTick() + partialTicks) / 10.0F;
          }  
        if (!clone) {
          GlStateManager.pushMatrix();
          GlStateManager.scale(glowScale, glowScale * 0.8F, glowScale);
        } 
        GlStateManager.disableLighting();
        RenderMutantEnderman.this.bindTexture(RenderMutantEnderman.GLOW_TEXTURE);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float f = (entityIn.ticksExisted + partialTicks) * 0.008F;
        GlStateManager.translate(f, f, 0.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int var5 = 15728880;
        int var6 = var5 % 65536;
        int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6, var7);
        GlStateManager.color(0.9F, 0.3F, 1.0F, getAlpha(entityIn, partialTicks));
        GlStateManager.enableLighting();
        (Minecraft.getMinecraft()).entityRenderer.setupFogColor(true);
        RenderMutantEnderman.this.getMainModel().render((Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        (Minecraft.getMinecraft()).entityRenderer.setupFogColor(false);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.disableBlend();
        if (!clone)
          GlStateManager.popMatrix(); 
      } 
    }
    
    protected float getAlpha(EntityMutantEnderman entity, float partialTicks) {
      float alpha = 1.0F;
      if (entity.getAttackID() == 7 && entity.getAttackTick() < 10) {
        if (!RenderMutantEnderman.this.teleportAttack && entity.getAttackTick() >= 8)
          alpha -= ((entity.getAttackTick() - 8) + partialTicks) / 2.0F; 
        if (RenderMutantEnderman.this.teleportAttack && entity.getAttackTick() < 2)
          alpha = (entity.getAttackTick() + partialTicks) / 2.0F; 
      } 
      if (entity.getAttackID() == 8)
        if (entity.getAttackTick() < 40) {
          alpha = (entity.getAttackTick() + partialTicks) / 40.0F;
        } else if (entity.getAttackTick() >= 160) {
          alpha = 1.0F - (entity.getAttackTick() + partialTicks) / 40.0F;
        }  
      return alpha;
    }
    
    public boolean shouldCombineTextures() {
      return false;
    }
  }
}
