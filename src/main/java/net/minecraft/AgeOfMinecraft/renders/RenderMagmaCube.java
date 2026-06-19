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
    addLayer(new LayerLearningBook(this));
    addLayer(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityMagmaCube entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? ((entity.getSlimeSize() == 1) ? (entity.isAntiMob() ? anticmmMagmaCubeTinyTextures : cmmMagmaCubeTinyTextures) : ((entity.getSlimeSize() == 2 || entity.getSlimeSize() == 3) ? (entity.isAntiMob() ? anticmmMagmaCubeSmallTextures : cmmMagmaCubeSmallTextures) : (entity.isAntiMob() ? anticmmMagmaCubeLargeTextures : cmmMagmaCubeLargeTextures))) : (entity.isAntiMob() ? antimagmaCubeTextures : magmaCubeTextures);
  }
  
  protected void preRenderCallback(EntityMagmaCube entitylivingbaseIn, float partialTickTime) {
    if (EngenderConfig.mobs.useMobTalkerModels) {
      this.shadowSize = entitylivingbaseIn.getSlimeSize() * 0.125F;
    } else {
      this.shadowSize = 0.25F * entitylivingbaseIn.getSlimeSize();
    } 
    this.mainModel = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    float f1 = entitylivingbaseIn.getSlimeSize();
    float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
    float f3 = 1.0F / (f2 + 1.0F);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.scale(f3, 1.0F / f3, f3);
    } else {
      GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    } 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.isSitResting()) {
        GlStateManager.translate(0.0F, 0.75F + f1 * 0.05F, 0.0F);
        if (f1 <= 4.0F)
          GlStateManager.translate(0.0F, -0.45F, 0.0F); 
        if (entitylivingbaseIn.isSmallSlime())
          GlStateManager.translate(0.0F, -0.1F, 0.0F); 
      } 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isSmallSlime())
        GlStateManager.translate(0.0F, 0.6F, 0.25F); 
      GlStateManager.scale(0.95F, 0.95F, 0.95F);
      GlStateManager.scale(f1 * 0.25F, f1 * 0.25F, f1 * 0.25F);
      if (f1 == 2.0F)
        GlStateManager.scale(1.5F, 1.5F, 1.5F); 
      if (f1 <= 1.0F)
        GlStateManager.scale(1.75F, 1.75F, 1.75F); 
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
  
  protected void applyRotations(EntityMagmaCube entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (entityLiving.getJukeboxToDanceTo() != null)
      GlStateManager.scale(1.0F + MathHelper.cos(p_77043_2_ * 1.0F) * 0.1F, 1.0F - MathHelper.cos(p_77043_2_ * 1.0F) * 0.05F, 1.0F + MathHelper.cos(p_77043_2_ * 1.0F) * 0.1F); 
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
      } 
    } else {
      String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  public void doRender(EntityMagmaCube entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityMagmaCube entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
