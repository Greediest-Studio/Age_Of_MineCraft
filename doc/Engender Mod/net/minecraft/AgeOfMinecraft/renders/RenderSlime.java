package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySlime;
import net.minecraft.AgeOfMinecraft.models.ModelCMMSlime;
import net.minecraft.AgeOfMinecraft.models.ModelSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSlime extends RenderLiving<EntitySlime> {
  private static final ResourceLocation cmmSlimeLargeTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/slime4.png");
  
  private static final ResourceLocation cmmSlimeSmallTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/slime2.png");
  
  private static final ResourceLocation cmmSlimeTinyTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/slime1.png");
  
  private static final ResourceLocation slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
  
  private static final ResourceLocation anticmmSlimeLargeTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/slime4.png");
  
  private static final ResourceLocation anticmmSlimeSmallTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/slime2.png");
  
  private static final ResourceLocation anticmmSlimeTinyTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/slime1.png");
  
  private static final ResourceLocation antislimeTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/slime.png");
  
  private static ModelCMMSlime cmmmodel = new ModelCMMSlime();
  
  private static ModelSlime regularmodel = new ModelSlime(16);
  
  public RenderSlime(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.25F);
    func_177094_a(new LayerSlimeGel(this));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.slimeBodies, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
  }
  
  public void doRender(EntitySlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (EngenderConfig.mobs.useMobTalkerModels) {
      this.field_76989_e = entity.getSlimeSize() * 0.125F;
    } else {
      this.field_76989_e = 0.25F * entity.getSlimeSize();
    } 
    super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected void preRenderCallback(EntitySlime entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    float f1 = entitylivingbaseIn.getSlimeSize();
    float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
    float f3 = 1.0F / (f2 + 1.0F);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179152_a(f3, 1.0F / f3, f3);
    } else {
      GlStateManager.func_179152_a(f3 * f1, 1.0F / f3 * f1, f3 * f1);
      if (entitylivingbaseIn.func_70093_af())
        GlStateManager.func_179152_a(1.25F, 0.75F, 1.25F); 
    } 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.isSitResting()) {
        GlStateManager.func_179109_b(0.0F, 0.75F + f1 * 0.05F, 0.0F);
        if (f1 <= 4.0F)
          GlStateManager.func_179109_b(0.0F, -0.45F, 0.0F); 
        if (entitylivingbaseIn.isSmallSlime())
          GlStateManager.func_179109_b(0.0F, -0.1F, 0.0F); 
      } 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.isSmallSlime())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.25F); 
      GlStateManager.func_179152_a(0.95F, 0.95F, 0.95F);
      GlStateManager.func_179152_a(f1 * 0.25F, f1 * 0.25F, f1 * 0.25F);
      if (f1 == 2.0F)
        GlStateManager.func_179152_a(1.5F, 1.5F, 1.5F); 
      if (f1 <= 1.0F)
        GlStateManager.func_179152_a(1.75F, 1.75F, 1.75F); 
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
  
  protected void applyRotations(EntitySlime entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (entityLiving.getJukeboxToDanceTo() != null)
      GlStateManager.func_179152_a(1.0F + MathHelper.func_76134_b(p_77043_2_ * 1.0F) * 0.1F, 1.0F - MathHelper.func_76134_b(p_77043_2_ * 1.0F) * 0.05F, 1.0F + MathHelper.func_76134_b(p_77043_2_ * 1.0F) * 0.1F); 
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
      } 
    } else {
      String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.func_179109_b(0.0F, entityLiving.field_70131_O + 0.1F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected ResourceLocation getEntityTexture(EntitySlime entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? ((entity.getSlimeSize() <= 1) ? (entity.isAntiMob() ? anticmmSlimeTinyTextures : cmmSlimeTinyTextures) : ((entity.getSlimeSize() == 2 || entity.getSlimeSize() == 3) ? (entity.isAntiMob() ? anticmmSlimeSmallTextures : cmmSlimeSmallTextures) : (entity.isAntiMob() ? anticmmSlimeLargeTextures : cmmSlimeLargeTextures))) : (entity.isAntiMob() ? antislimeTextures : slimeTextures);
  }
}
