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
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerSnowmanHead(regularmodel.head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected void applyRotations(EntitySnowman entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), -1.0F, 0.0F, 0.0F);
      } else {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179109_b(f * 0.25F, 0.0F, 0.0F);
      } 
    } else {
      String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.func_179109_b(0.0F, entityLiving.field_70131_O + 0.1F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntitySnowman entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? anticmmSnowManTextures : cmmSnowManTextures) : (entity.isAntiMob() ? antisnowManTextures : snowManTextures);
  }
  
  protected void preRenderCallback(EntitySnowman entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    if (!EngenderConfig.mobs.useMobTalkerModels && !entitylivingbaseIn.getCurrentBook().func_190926_b()) {
      regularmodel.rightHand.field_78796_g = entitylivingbaseIn.bookSpread - 1.0F;
      regularmodel.leftHand.field_78796_g = -entitylivingbaseIn.bookSpread + 1.0F;
      regularmodel.rightHand.field_78808_h = 0.0F;
      regularmodel.leftHand.field_78808_h = 0.0F;
      regularmodel.rightHand.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entitylivingbaseIn.field_70173_aa * 0.1F) * 0.01F;
      regularmodel.leftHand.field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entitylivingbaseIn.field_70173_aa * 0.1F) * 0.01F;
    } 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179152_a(0.9F, 0.9F, 0.9F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, entitylivingbaseIn.func_70631_g_() ? 0.25F : 0.5F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.5F, 0.25F); 
    } else {
      if (entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
      if (entitylivingbaseIn.func_184218_aH())
        GlStateManager.func_179109_b(0.0F, -1.0F, 0.0F); 
    } 
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (!entitylivingbaseIn.field_70122_E)
      GlStateManager.func_179114_b(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
}
