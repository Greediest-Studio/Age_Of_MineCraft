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
    addLayer(new LayerEndermanEyes(this));
    addLayer(new LayerHeldBlock(this));
    addLayer(new LayerCustomHeadEngender(regularmodel.bipedHead, cmmmodel.Head));
    
    addLayer(new LayerMobCape(this));
  }
  
  protected void preRenderCallback(EntityEnderman entitylivingbaseIn, float partialTickTime) {
    this.mainModel = EngenderConfig.mobs.useMobTalkerModels ? cmmmodel : regularmodel;
    if (entitylivingbaseIn.isSneaking() && !EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.translate(0.0F, -0.2F, -0.75F);
      if (entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, 0.2F, 0.375F); 
    } 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.scale(1.33F, 1.33F, 1.33F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.translate(0.0F, 0.75F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, -0.4F, 0.0F); 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, 0.7F, 0.15F); 
    } else {
      if (entitylivingbaseIn.isRiding())
        GlStateManager.translate(0.0F, 0.25F, 0.0F); 
      if (entitylivingbaseIn.isChild())
        GlStateManager.scale(0.5F, 0.5F, 0.5F); 
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
  
  protected void applyRotations(EntityEnderman entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.deathTime > 0) {
      float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.sqrt(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, f * 0.125F);
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
  
  protected ResourceLocation getEntityTexture(EntityEnderman entity) {
    String s = TextFormatting.getTextWithoutFormattingCodes(entity.getName());
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
      float f = handleRotationFloat(entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.doRender(entity, x + (avec3d[i]).x + MathHelper.cos(i + f * 0.5F) * 0.025D, y + (avec3d[i]).y + MathHelper.cos(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).z + MathHelper.cos(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.shadowOpaque = 0.0F;
    } else {
      this.shadowOpaque = 1.0F;
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityTameBase entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
