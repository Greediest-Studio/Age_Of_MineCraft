package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityMagmaCube;
import net.minecraft.AgeOfMinecraft.models.ModelCMMMagmaCube;
import net.minecraft.AgeOfMinecraft.models.ModelMagmaCube;
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
public class RenderMagmaCube extends RenderLiving<EntityMagmaCube> {
  private static final ResourceLocation cmmMagmaCubeTinyTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/mcube1.png");
  
  private static final ResourceLocation cmmMagmaCubeSmallTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/mcube2.png");
  
  private static final ResourceLocation cmmMagmaCubeLargeTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/mcube4.png");
  
  private static final ResourceLocation magmaCubeTextures = new ResourceLocation("textures/entity/slime/magmacube.png");
  
  private static final ResourceLocation anticmmMagmaCubeTinyTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/mcube1.png");
  
  private static final ResourceLocation anticmmMagmaCubeSmallTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/mcube2.png");
  
  private static final ResourceLocation anticmmMagmaCubeLargeTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/mcube4.png");
  
  private static final ResourceLocation antimagmaCubeTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/magmacube.png");
  
  private static ModelCMMMagmaCube cmmmodel = new ModelCMMMagmaCube();
  
  private static ModelMagmaCube regularmodel = new ModelMagmaCube();
  
  public RenderMagmaCube(RenderManager p_i46159_1_) {
    super(p_i46159_1_, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.25F);
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityMagmaCube entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? ((entity.getSlimeSize() == 1) ? (entity.isAntiMob() ? anticmmMagmaCubeTinyTextures : cmmMagmaCubeTinyTextures) : ((entity.getSlimeSize() == 2 || entity.getSlimeSize() == 3) ? (entity.isAntiMob() ? anticmmMagmaCubeSmallTextures : cmmMagmaCubeSmallTextures) : (entity.isAntiMob() ? anticmmMagmaCubeLargeTextures : cmmMagmaCubeLargeTextures))) : (entity.isAntiMob() ? antimagmaCubeTextures : magmaCubeTextures);
  }
  
  protected void preRenderCallback(EntityMagmaCube entitylivingbaseIn, float partialTickTime) {
    if (EngenderConfig.mobs.useMobTalkerModels) {
      this.field_76989_e = entitylivingbaseIn.getSlimeSize() * 0.125F;
    } else {
      this.field_76989_e = 0.25F * entitylivingbaseIn.getSlimeSize();
    } 
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    float f1 = entitylivingbaseIn.getSlimeSize();
    float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
    float f3 = 1.0F / (f2 + 1.0F);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179152_a(f3, 1.0F / f3, f3);
    } else {
      GlStateManager.func_179152_a(f3 * f1, 1.0F / f3 * f1, f3 * f1);
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
  
  protected void applyRotations(EntityMagmaCube entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
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
  
  public void doRender(EntityMagmaCube entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityMagmaCube entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
