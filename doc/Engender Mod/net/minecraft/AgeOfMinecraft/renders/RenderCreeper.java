package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityCreeper;
import net.minecraft.AgeOfMinecraft.models.ModelCMMCreeper;
import net.minecraft.AgeOfMinecraft.models.ModelCreeper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPig;
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
public class RenderCreeper extends RenderLiving<EntityCreeper> {
  private static final ResourceLocation cmmTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/creeper.png");
  
  private static final ResourceLocation cmmChargedTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/creeperp.png");
  
  private static final ResourceLocation textures = new ResourceLocation("textures/entity/creeper/creeper.png");
  
  private static final ResourceLocation anticmmTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/creeper.png");
  
  private static final ResourceLocation anticmmChargedTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/creeperp.png");
  
  private static final ResourceLocation antiTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/creeper.png");
  
  private static ModelCMMCreeper cmmmodel = new ModelCMMCreeper();
  
  private static ModelCreeper regularmodel = new ModelCreeper();
  
  private static ModelPig disguisemodel = new ModelPig();
  
  public RenderCreeper(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    func_177094_a(new LayerCreeperCharge(this));
    func_177094_a(new LayerCreeperGlow(this));
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.head, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected void preRenderCallback(EntityCreeper entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = (entitylivingbaseIn.getIllusionFormTime() > 0) ? (ModelBase)disguisemodel : (EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel);
    float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
    float f1 = 1.0F + MathHelper.func_76126_a(f * 100.0F) * f * 0.01F;
    f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
    f *= f;
    f *= f;
    float f2 = (1.0F + f * 0.4F) * f1;
    float f3 = (1.0F + f * 0.1F) / f1;
    GlStateManager.func_179152_a(f2, f3, f2);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (!entitylivingbaseIn.getPowered())
        GlStateManager.func_179152_a(0.9F, 0.9F, 0.9F); 
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, -0.3F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.25F); 
    } else {
      if (entitylivingbaseIn.func_184218_aH())
        GlStateManager.func_179109_b(0.0F, -0.5F, 0.0F); 
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
  
  protected int getColorMultiplier(EntityCreeper entitylivingbaseIn, float lightBrightness, float partialTickTime) {
    float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
    if ((int)(f * 10.0F) % 2 == 0)
      return 0; 
    int i = (int)(f * 0.2F * 255.0F);
    i = MathHelper.func_76125_a(i, 0, 255);
    return i << 24 | 0x30FFFFFF;
  }
  
  protected void applyRotations(EntityCreeper entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), -1.0F, 0.0F, 0.0F);
        GlStateManager.func_179109_b(0.0F, 0.0F, f * 0.1F);
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
  
  protected ResourceLocation getEntityTexture(EntityCreeper entity) {
    return (entity.getIllusionFormTime() > 0) ? new ResourceLocation("textures/entity/pig/pig.png") : (EngenderConfig.mobs.useMobTalkerModels ? (entity.getPowered() ? (entity.isAntiMob() ? anticmmChargedTextures : cmmChargedTextures) : (entity.isAntiMob() ? anticmmTextures : cmmTextures)) : (entity.isAntiMob() ? antiTextures : textures));
  }
  
  public void doRender(EntityCreeper entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
    return (entity.getGhostTime() > 0 || func_193115_c((EntityLivingBase)entity));
  }
}
