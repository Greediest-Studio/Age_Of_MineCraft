package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIronGolem;
import net.minecraft.AgeOfMinecraft.models.ModelCMMIronGolem;
import net.minecraft.AgeOfMinecraft.models.ModelIronGolem;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
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
public class RenderIronGolem extends RenderLiving<EntityIronGolem> {
  private static final ResourceLocation cmmIronGolemTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/igolem.png");
  
  private static final ResourceLocation ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
  
  private static final ResourceLocation anticmmIronGolemTextures = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/igolem.png");
  
  private static final ResourceLocation antiironGolemTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/iron_golem.png");
  
  private static ModelCMMIronGolem cmmmodel = new ModelCMMIronGolem();
  
  private static ModelIronGolem regularmodel = new ModelIronGolem();
  
  private static ModelVillager disguisemodel = new ModelVillager(0.0F);
  
  public RenderIronGolem(RenderManager p_i46133_1_) {
    super(p_i46133_1_, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.75F);
    addLayer(new LayerIronGolemFlower(this));
    addLayer(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    addLayer(new LayerCustomHeadEngender(regularmodel.ironGolemHead, cmmmodel.Head));
    addLayer(new LayerLearningBook(this));
    addLayer(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityIronGolem entity) {
    return (entity.getIllusionFormTime() > 0) ? new ResourceLocation("textures/entity/villager/butcher.png") : (EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? anticmmIronGolemTextures : cmmIronGolemTextures) : (entity.isAntiMob() ? antiironGolemTextures : ironGolemTextures));
  }
  
  protected void preRenderCallback(EntityIronGolem entitylivingbaseIn, float partialTickTime) {
    this.mainModel = (entitylivingbaseIn.getIllusionFormTime() > 0) ? (ModelBase)disguisemodel : (EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.scale(1.25F, 1.25F, 1.25F);
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.translate(0.0F, 0.75F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, -0.45F, 0.0F); 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, 0.6F, 0.25F); 
    } else {
      if (entitylivingbaseIn.isChild())
        GlStateManager.scale(0.5F, 0.5F, 0.5F); 
      if (entitylivingbaseIn.isRiding())
        GlStateManager.translate(0.0F, -0.825F, 0.0F); 
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
  
  protected void applyRotations(EntityIronGolem entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.deathTime > 0) {
      float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.sqrt(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 1.0F, 0.0F, 0.0F);
      } else {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(f * 0.5F, 0.0F, 0.0F);
      } 
    } else {
      String s = TextFormatting.getTextWithoutFormattingCodes(entityLiving.getName());
      if (s != null && ("Dinnerbone".equals(s) || "Grumm".equals(s))) {
        GlStateManager.translate(0.0F, entityLiving.height + 0.1F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      } 
    } 
    if (entityLiving.limbSwingAmount >= 0.01D) {
      float f3 = 13.0F;
      float f4 = entityLiving.limbSwing - entityLiving.limbSwingAmount * (1.0F - partialTicks) + 6.0F;
      float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / f3 * 0.25F;
      GlStateManager.rotate(7.0F * f5, 0.0F, 0.0F, 1.0F);
    } 
  }
  
  public void doRender(EntityIronGolem entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityIronGolem entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
