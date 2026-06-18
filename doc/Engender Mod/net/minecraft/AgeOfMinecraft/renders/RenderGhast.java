package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.models.ModelCMMGhast;
import net.minecraft.AgeOfMinecraft.models.ModelGhast;
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
public class RenderGhast extends RenderLiving<EntityGhast> {
  private static final ResourceLocation cmmGhastTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/ghast1.png");
  
  private static final ResourceLocation cmmGhastShootingTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/ghast2.png");
  
  private static final ResourceLocation ghastTextures = new ResourceLocation("textures/entity/ghast/ghast.png");
  
  private static final ResourceLocation ghastShootingTextures = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");
  
  private static final ResourceLocation anticmmGhastTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/ghast1.png");
  
  private static final ResourceLocation anticmmGhastShootingTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/ghast2.png");
  
  private static final ResourceLocation antighastTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/ghast.png");
  
  private static final ResourceLocation antighastShootingTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/ghast_shooting.png");
  
  private static ModelCMMGhast cmmmodel = new ModelCMMGhast();
  
  private static ModelGhast regularmodel = new ModelGhast();
  
  public RenderGhast(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, EngenderConfig.mobs.useMobTalkerModels ? 0.5F : 3.0F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, EngenderConfig.mobs.useMobTalkerModels ? 1.0F : 0.2F));
    func_177094_a(new LayerGhastEyes(this));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.body, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityGhast entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAttacking() ? (entity.isAntiMob() ? anticmmGhastShootingTextures : cmmGhastShootingTextures) : (entity.isAntiMob() ? anticmmGhastTextures : cmmGhastTextures)) : (entity.isAttacking() ? (entity.isAntiMob() ? antighastShootingTextures : ghastShootingTextures) : (entity.isAntiMob() ? antighastTextures : ghastTextures));
  }
  
  protected void applyRotations(EntityGhast entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179109_b(0.0F, 0.0F, -f * 0.1F);
      } else {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179109_b(f * 2.25F, -f * 2.25F, 0.0F);
      } 
    } else {
      String s = TextFormatting.func_110646_a(entityLiving.func_70005_c_());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.func_179109_b(0.0F, entityLiving.field_70131_O + 0.1F, 0.0F);
        GlStateManager.func_179114_b(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected void preRenderCallback(EntityGhast entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    this.field_76989_e = EngenderConfig.mobs.useMobTalkerModels ? 0.5F : 3.0F;
    if (!EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.func_184207_aI() && entitylivingbaseIn.func_184179_bs() != null && entitylivingbaseIn.func_184179_bs() instanceof net.minecraft.entity.player.EntityPlayer) {
        entitylivingbaseIn.func_82142_c(true);
      } else {
        entitylivingbaseIn.func_82142_c(entitylivingbaseIn.func_82150_aj());
      } 
      float f = 1.0F;
      float f1 = (8.0F + f) / 2.0F;
      float f2 = (8.0F + 1.0F / f) / 2.0F;
      GlStateManager.func_179152_a(f2, f1, f2);
      GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.func_179109_b(0.0F, -0.05F, 0.0F);
      if (entitylivingbaseIn.func_184218_aH())
        GlStateManager.func_179109_b(0.0F, -0.125F, 0.0F); 
      if (entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    } else {
      GlStateManager.func_179152_a(1.01F, 1.01F, 1.01F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, -0.3F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.25F); 
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
  
  public void doRender(EntityGhast entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityGhast entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
