package net.minecraft.AgeOfMinecraft.renders;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.models.ModelCMMEnderman;
import net.minecraft.AgeOfMinecraft.models.ModelEnderman;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEnderman extends RenderLiving<EntityEnderman> {
  private static final ResourceLocation cmmNerrasTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/nerras.png");
  
  private static final ResourceLocation cmmEndermanTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/enderman.png");
  
  private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
  
  private static final ResourceLocation anticmmNerrasTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/nerras.png");
  
  private static final ResourceLocation anticmmEndermanTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/enderman.png");
  
  private static final ResourceLocation antiendermanTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/enderman.png");
  
  private static final ResourceLocation bartTextures = new ResourceLocation("ageofminecraft", "textures/entities/bart.png");
  
  private static ModelCMMEnderman cmmmodel = new ModelCMMEnderman();
  
  private static ModelEnderman regularmodel = new ModelEnderman();
  
  private Random rnd = new Random();
  
  public RenderEnderman(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    func_177094_a(new LayerEndermanEyes(this));
    func_177094_a(new LayerHeldBlock(this));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.field_78116_c, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected void preRenderCallback(EntityEnderman entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    if (entitylivingbaseIn.func_70093_af() && !EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179109_b(0.0F, -0.2F, -0.75F);
      if (entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.2F, 0.375F); 
    } 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179152_a(1.33F, 1.33F, 1.33F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, 0.75F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, -0.4F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.7F, 0.15F); 
    } else {
      if (entitylivingbaseIn.func_184218_aH())
        GlStateManager.func_179109_b(0.0F, 0.25F, 0.0F); 
      if (entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
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
  
  protected void applyRotations(EntityEnderman entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), -1.0F, 0.0F, 0.0F);
        GlStateManager.func_179109_b(0.0F, 0.0F, f * 0.125F);
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
  
  protected ResourceLocation getEntityTexture(EntityEnderman entity) {
    String s = TextFormatting.func_110646_a(entity.func_70005_c_());
    return EngenderConfig.mobs.useMobTalkerModels ? ((s != null && "Nerras".equals(s)) ? (entity.isAntiMob() ? anticmmNerrasTextures : cmmNerrasTextures) : (entity.isAntiMob() ? anticmmEndermanTextures : cmmEndermanTextures)) : (entity.isAntiMob() ? antiendermanTextures : ((s != null && "Bart".equals(s)) ? bartTextures : endermanTextures));
  }
  
  public void doRender(EntityEnderman entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.isArmsRaised()) {
      double d0 = 0.02D;
      x += this.rnd.nextGaussian() * d0;
      z += this.rnd.nextGaussian() * d0;
    } 
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = func_77044_a((EntityLivingBase)entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.field_76987_f = 0.0F;
    } else {
      this.field_76987_f = 1.0F;
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityTameBase entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
