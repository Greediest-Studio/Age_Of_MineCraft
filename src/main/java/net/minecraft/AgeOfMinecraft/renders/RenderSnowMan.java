package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntitySnowman;
import net.minecraft.AgeOfMinecraft.models.ModelCMMSnowGolem;
import net.minecraft.AgeOfMinecraft.models.ModelSnowMan;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSnowMan extends RenderLiving<EntitySnowman> {
  private static final ResourceLocation cmmSnowManTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/sgolem.png");
  
  private static final ResourceLocation snowManTextures = new ResourceLocation("textures/entity/snowman.png");
  
  private static final ResourceLocation anticmmSnowManTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/sgolem.png");
  
  private static final ResourceLocation antisnowManTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/snowman.png");
  
  private static ModelCMMSnowGolem cmmmodel = new ModelCMMSnowGolem();
  
  private static ModelSnowMan regularmodel = new ModelSnowMan();
  
  public RenderSnowMan(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    addLayer(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    addLayer(new LayerSnowmanHead(regularmodel.head));
    addLayer(new LayerLearningBook(this));
    addLayer(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected void applyRotations(EntitySnowman entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.deathTime > 0) {
      float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.sqrt(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), -1.0F, 0.0F, 0.0F);
      } else {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(f * 0.25F, 0.0F, 0.0F);
      } 
    } else {
      String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntitySnowman entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? anticmmSnowManTextures : cmmSnowManTextures) : (entity.isAntiMob() ? antisnowManTextures : snowManTextures);
  }
  
  protected void preRenderCallback(EntitySnowman entitylivingbaseIn, float partialTickTime) {
    this.mainModel = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    if (!EngenderConfig.mobs.useMobTalkerModels && !entitylivingbaseIn.getCurrentBook().isEmpty()) {
      regularmodel.rightHand.rotateAngleY = entitylivingbaseIn.bookSpread - 1.0F;
      regularmodel.leftHand.rotateAngleY = -entitylivingbaseIn.bookSpread + 1.0F;
      regularmodel.rightHand.rotateAngleZ = 0.0F;
      regularmodel.leftHand.rotateAngleZ = 0.0F;
      regularmodel.rightHand.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entitylivingbaseIn.ticksExisted * 0.1F) * 0.01F;
      regularmodel.leftHand.rotateAngleX = -1.5F + 0.1F + MathHelper.sin(entitylivingbaseIn.ticksExisted * 0.1F) * 0.01F;
    } 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.scale(0.9F, 0.9F, 0.9F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.translate(0.0F, entitylivingbaseIn.isChild() ? 0.25F : 0.5F, 0.0F); 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, 0.5F, 0.25F); 
    } else {
      if (entitylivingbaseIn.isChild())
        GlStateManager.scale(0.5F, 0.5F, 0.5F); 
      if (entitylivingbaseIn.isRiding())
        GlStateManager.translate(0.0F, -1.0F, 0.0F); 
    } 
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
