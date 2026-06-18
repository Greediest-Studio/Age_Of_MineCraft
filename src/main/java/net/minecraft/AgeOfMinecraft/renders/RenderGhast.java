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
    addLayer(new LayerArrowCustomSized((RenderLivingBase<?>)this, EngenderConfig.mobs.useMobTalkerModels ? 1.0F : 0.2F));
    addLayer(new LayerGhastEyes(this));
    addLayer(new LayerCustomHeadEngender(regularmodel.body, cmmmodel.Head));
    addLayer(new LayerLearningBook(this));
    addLayer(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityGhast entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAttacking() ? (entity.isAntiMob() ? anticmmGhastShootingTextures : cmmGhastShootingTextures) : (entity.isAntiMob() ? anticmmGhastTextures : cmmGhastTextures)) : (entity.isAttacking() ? (entity.isAntiMob() ? antighastShootingTextures : ghastShootingTextures) : (entity.isAntiMob() ? antighastTextures : ghastTextures));
  }
  
  protected void applyRotations(EntityGhast entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
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
        GlStateManager.translate(f * 2.25F, -f * 2.25F, 0.0F);
      } 
    } else {
      String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
  }
  
  protected void preRenderCallback(EntityGhast entitylivingbaseIn, float partialTickTime) {
    this.mainModel = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    this.shadowSize = EngenderConfig.mobs.useMobTalkerModels ? 0.5F : 3.0F;
    if (!EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.isBeingRidden() && entitylivingbaseIn.getControllingPassenger() != null && entitylivingbaseIn.getControllingPassenger() instanceof net.minecraft.entity.player.EntityPlayer) {
        entitylivingbaseIn.setInvisible(true);
      } else {
        entitylivingbaseIn.setInvisible(entitylivingbaseIn.isInvisible());
      } 
      float f = 1.0F;
      float f1 = (8.0F + f) / 2.0F;
      float f2 = (8.0F + 1.0F / f) / 2.0F;
      GlStateManager.scale(f2, f1, f2);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.translate(0.0F, -0.05F, 0.0F);
      if (entitylivingbaseIn.isRiding())
        GlStateManager.translate(0.0F, -0.125F, 0.0F); 
      if (entitylivingbaseIn.isChild())
        GlStateManager.scale(0.5F, 0.5F, 0.5F); 
    } else {
      GlStateManager.scale(1.01F, 1.01F, 1.01F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.translate(0.0F, 0.6F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, -0.3F, 0.0F); 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, 0.6F, 0.25F); 
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
  
  public void doRender(EntityGhast entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityGhast entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
