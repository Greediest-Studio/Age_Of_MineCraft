package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton;
import net.minecraft.AgeOfMinecraft.models.ModelCMMSkeleton;
import net.minecraft.AgeOfMinecraft.models.ModelSkeleton;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
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
public class RenderSkeleton extends RenderLiving<EntitySkeleton> {
  private static final ResourceLocation CMM_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/skeleton.png");
  
  private static final ResourceLocation CMM_WITHER_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/skeletonw.png");
  
  private static final ResourceLocation CMM_STRAY_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/skeletons.png");
  
  private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
  
  private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
  
  private static final ResourceLocation STRAY_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/stray.png");
  
  private static final ResourceLocation antiCMM_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/skeleton.png");
  
  private static final ResourceLocation antiCMM_WITHER_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/skeletonw.png");
  
  private static final ResourceLocation antiCMM_STRAY_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/skeletons.png");
  
  private static final ResourceLocation antiSKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/anti/skeleton.png");
  
  private static final ResourceLocation antiWITHER_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/anti/wither_skeleton.png");
  
  private static final ResourceLocation antiSTRAY_SKELETON_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/anti/stray.png");
  
  private LayerCustomArmor armor = new LayerCustomArmor((RenderLivingBase<?>)this);
  
  private static ModelCMMSkeleton cmmmodel = new ModelCMMSkeleton();
  
  private static ModelSkeleton regularmodel = new ModelSkeleton();
  
  private static ModelCMMSkeleton cmmleggings = new ModelCMMSkeleton(0.5F, true);
  
  private static ModelSkeleton regularleggings = new ModelSkeleton(0.5F, true);
  
  private static ModelCMMSkeleton cmmarmor = new ModelCMMSkeleton(1.0F, true);
  
  private static ModelSkeleton regulararmor = new ModelSkeleton(1.0F, true);
  
  public RenderSkeleton(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 0.9F));
    func_177094_a(new LayerSkeletonType((RenderLivingBase<?>)this));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
    this.armor = new LayerCustomArmor((RenderLivingBase)this) {
        protected void func_177177_a() {
          this.field_177189_c = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderSkeleton.cmmleggings : (ModelBase)RenderSkeleton.regularleggings;
          this.field_177186_d = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderSkeleton.cmmarmor : (ModelBase)RenderSkeleton.regulararmor;
        }
      };
    func_177094_a((LayerRenderer)this.armor);
    func_177094_a((LayerRenderer)new LayerElytra((RenderLivingBase)this));
    func_177094_a((LayerRenderer)new LayerHeldItem((RenderLivingBase)this));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.field_78116_c, cmmmodel.Head));
  }
  
  protected void applyRotations(EntitySkeleton entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (entityLiving.func_184613_cA()) {
      super.func_77043_a((EntityLivingBase)entityLiving, p_77043_2_, p_77043_3_, partialTicks);
      float f = entityLiving.func_184599_cB() + partialTicks;
      float f1 = MathHelper.func_76131_a(f * f / 100.0F, 0.0F, 1.0F);
      GlStateManager.func_179114_b(f1 * (-90.0F - entityLiving.field_70125_A), 1.0F, 0.0F, 0.0F);
      Vec3d vec3d = entityLiving.func_70676_i(partialTicks);
      double d0 = entityLiving.field_70159_w * entityLiving.field_70159_w + entityLiving.field_70179_y * entityLiving.field_70179_y;
      double d1 = vec3d.field_72450_a * vec3d.field_72450_a + vec3d.field_72449_c * vec3d.field_72449_c;
      if (d0 > 0.0D && d1 > 0.0D) {
        double d2 = (entityLiving.field_70159_w * vec3d.field_72450_a + entityLiving.field_70179_y * vec3d.field_72449_c) / Math.sqrt(d0) * Math.sqrt(d1);
        double d3 = entityLiving.field_70159_w * vec3d.field_72449_c - entityLiving.field_70179_y * vec3d.field_72450_a;
        GlStateManager.func_179114_b((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
      } 
    } else {
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
  }
  
  private void changeModel() {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    this.field_177097_h.remove(this.armor);
    this.armor = new LayerCustomArmor((RenderLivingBase)this) {
        protected void func_177177_a() {
          this.field_177189_c = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderSkeleton.cmmleggings : (ModelBase)RenderSkeleton.regularleggings;
          this.field_177186_d = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderSkeleton.cmmarmor : (ModelBase)RenderSkeleton.regulararmor;
        }
      };
    func_177094_a((LayerRenderer)this.armor);
  }
  
  protected void preRenderCallback(EntitySkeleton entitylivingbaseIn, float partialTickTime) {
    changeModel();
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179152_a(0.95F, 0.95F, 0.95F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, 0.5F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, -0.25F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.2F, 0.25F); 
    } else {
      if (entitylivingbaseIn.func_70093_af())
        GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_() && !(entitylivingbaseIn.func_184187_bx() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)) {
        GlStateManager.func_179109_b(0.0F, -0.625F, 0.0F);
      } else if (entitylivingbaseIn.func_184218_aH() && !entitylivingbaseIn.func_70631_g_()) {
        GlStateManager.func_179109_b(0.0F, 0.1F, 0.0F);
      } 
    } 
    if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_() && entitylivingbaseIn.func_184187_bx() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)
      GlStateManager.func_179109_b(0.0F, -0.1F, 0.0F); 
    if (entitylivingbaseIn.getSkeletonType() == 1) {
      GlStateManager.func_179152_a(1.2F, 1.2F, 1.2F);
      if (EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, 0.1F, 0.0F); 
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
  
  public void func_82422_c() {
    GlStateManager.func_179109_b(0.09375F, 0.1875F, 0.0F);
  }
  
  protected ResourceLocation getEntityTexture(EntitySkeleton entity) {
    if (EngenderConfig.mobs.useMobTalkerModels) {
      switch (entity.getSkeletonType()) {
        case 1:
          return entity.isAntiMob() ? antiCMM_WITHER_SKELETON_TEXTURES : CMM_WITHER_SKELETON_TEXTURES;
        case 2:
          return entity.isAntiMob() ? antiCMM_STRAY_SKELETON_TEXTURES : CMM_STRAY_SKELETON_TEXTURES;
      } 
      return entity.isAntiMob() ? antiCMM_SKELETON_TEXTURES : CMM_SKELETON_TEXTURES;
    } 
    switch (entity.getSkeletonType()) {
      case 1:
        return entity.isAntiMob() ? antiWITHER_SKELETON_TEXTURES : WITHER_SKELETON_TEXTURES;
      case 2:
        return entity.isAntiMob() ? antiSTRAY_SKELETON_TEXTURES : STRAY_SKELETON_TEXTURES;
    } 
    return entity.isAntiMob() ? antiSKELETON_TEXTURES : SKELETON_TEXTURES;
  }
  
  public void doRender(EntitySkeleton entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntitySkeleton entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
