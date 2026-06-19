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
    addLayer(new LayerArrowCustomSized((RenderLivingBase<?>)this, 0.9F));
    addLayer(new LayerSkeletonType((RenderLivingBase<?>)this));
    addLayer(new LayerLearningBook(this));
    addLayer(new LayerMobCape((RenderLivingBase<?>)this));
    this.armor = new LayerCustomArmor((RenderLivingBase)this) {
        protected void initArmor() {
          this.modelLeggings = EngenderConfig.mobs.useMobTalkerModels ? RenderSkeleton.cmmleggings : RenderSkeleton.regularleggings;
          this.modelArmor = EngenderConfig.mobs.useMobTalkerModels ? RenderSkeleton.cmmarmor : RenderSkeleton.regulararmor;
        }
      };
    addLayer((LayerRenderer)this.armor);
    addLayer((LayerRenderer)new LayerElytra((RenderLivingBase)this));
    addLayer((LayerRenderer)new LayerHeldItem((RenderLivingBase)this));
    addLayer(new LayerCustomHeadEngender(regularmodel.bipedHead, cmmmodel.Head));
  }
  
  protected void applyRotations(EntitySkeleton entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (entityLiving.isElytraFlying()) {
      super.applyRotations(entityLiving, p_77043_2_, p_77043_3_, partialTicks);
      float f = entityLiving.getTicksElytraFlying() + partialTicks;
      float f1 = MathHelper.clamp(f * f / 100.0F, 0.0F, 1.0F);
      GlStateManager.rotate(f1 * (-90.0F - entityLiving.rotationPitch), 1.0F, 0.0F, 0.0F);
      Vec3d vec3d = entityLiving.getLook(partialTicks);
      double d0 = entityLiving.motionX * entityLiving.motionX + entityLiving.motionZ * entityLiving.motionZ;
      double d1 = vec3d.x * vec3d.x + vec3d.z * vec3d.z;
      if (d0 > 0.0D && d1 > 0.0D) {
        double d2 = (entityLiving.motionX * vec3d.x + entityLiving.motionZ * vec3d.z) / Math.sqrt(d0) * Math.sqrt(d1);
        double d3 = entityLiving.motionX * vec3d.z - entityLiving.motionZ * vec3d.x;
        GlStateManager.rotate((float)(Math.signum(d3) * Math.acos(d2)) * 180.0F / 3.1415927F, 0.0F, 1.0F, 0.0F);
      } 
    } else {
      GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
      if (entityLiving.deathTime > 0) {
        float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
        f = MathHelper.sqrt(f);
        if (f > 1.0F)
          f = 1.0F; 
        if (EngenderConfig.mobs.useMobTalkerModels) {
          GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 1.0F, 0.0F, 0.0F);
          GlStateManager.translate(0.0F, 0.0F, -f * 0.1F);
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
  }
  
  private void changeModel() {
    this.mainModel = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    this.layerRenderers.remove(this.armor);
    this.armor = new LayerCustomArmor((RenderLivingBase)this) {
        protected void initArmor() {
          this.modelLeggings = EngenderConfig.mobs.useMobTalkerModels ? RenderSkeleton.cmmleggings : RenderSkeleton.regularleggings;
          this.modelArmor = EngenderConfig.mobs.useMobTalkerModels ? RenderSkeleton.cmmarmor : RenderSkeleton.regulararmor;
        }
      };
    addLayer((LayerRenderer)this.armor);
  }
  
  protected void preRenderCallback(EntitySkeleton entitylivingbaseIn, float partialTickTime) {
    changeModel();
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.scale(0.95F, 0.95F, 0.95F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.translate(0.0F, 0.5F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, -0.25F, 0.0F); 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, 0.2F, 0.25F); 
    } else {
      if (entitylivingbaseIn.isSneaking())
        GlStateManager.translate(0.0F, 0.2F, 0.0F); 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild() && !(entitylivingbaseIn.getRidingEntity() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)) {
        GlStateManager.translate(0.0F, -0.625F, 0.0F);
      } else if (entitylivingbaseIn.isRiding() && !entitylivingbaseIn.isChild()) {
        GlStateManager.translate(0.0F, 0.1F, 0.0F);
      } 
    } 
    if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild() && entitylivingbaseIn.getRidingEntity() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider)
      GlStateManager.translate(0.0F, -0.1F, 0.0F); 
    if (entitylivingbaseIn.getSkeletonType() == 1) {
      GlStateManager.scale(1.2F, 1.2F, 1.2F);
      if (EngenderConfig.mobs.useMobTalkerModels)
        GlStateManager.scale(1.05F, 1.05F, 1.05F); 
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.translate(0.0F, 0.1F, 0.0F); 
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
  
  public void transformHeldFull3DItemLayer() {
    GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
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
      float f = handleRotationFloat(entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.doRender(entity, x + (avec3d[i]).x + MathHelper.cos(i + f * 0.5F) * 0.025D, y + (avec3d[i]).y + MathHelper.cos(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).z + MathHelper.cos(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.shadowOpaque = 0.0F;
    } else {
      this.shadowOpaque = 1.0F;
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntitySkeleton entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
