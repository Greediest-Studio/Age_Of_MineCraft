package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityPigZombie;
import net.minecraft.AgeOfMinecraft.models.ModelCMMPigZombie;
import net.minecraft.AgeOfMinecraft.models.ModelZombie;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
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
public class RenderPigZombie extends RenderLiving<EntityPigZombie> {
  private static final ResourceLocation CMM_ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/pzombie.png");
  
  private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");
  
  private static final ResourceLocation antiCMM_ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/pzombie.png");
  
  private static final ResourceLocation antiZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/zombie_pigman.png");
  
  private LayerBipedArmor armor = new LayerBipedArmor(this);
  
  private static ModelCMMPigZombie cmmmodel = new ModelCMMPigZombie();
  
  private static ModelZombie regularmodel = new ModelZombie();
  
  private static ModelCMMPigZombie cmmleggings = new ModelCMMPigZombie(0.5F, true);
  
  private static ModelZombie regularleggings = new ModelZombie(0.5F, true);
  
  private static ModelCMMPigZombie cmmarmor = new ModelCMMPigZombie(1.0F, true);
  
  private static ModelZombie regulararmor = new ModelZombie(1.0F, true);
  
  public RenderPigZombie(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    addLayer(new LayerArrowCustomSized(this, 1.0F));
    this.armor = new LayerBipedArmor(this) {
        protected void initArmor() {
          this.modelLeggings = EngenderConfig.mobs.useMobTalkerModels ? RenderPigZombie.cmmleggings : RenderPigZombie.regularleggings;
          this.modelArmor = EngenderConfig.mobs.useMobTalkerModels ? RenderPigZombie.cmmarmor : RenderPigZombie.regulararmor;
        }
      };
    addLayer((LayerRenderer)this.armor);
    addLayer((LayerRenderer)new LayerElytra(this));
    addLayer((LayerRenderer)new LayerHeldItem(this));
    addLayer(new LayerCustomHeadEngender(regularmodel.bipedHead, cmmmodel.Head));
    
    addLayer(new LayerMobCape(this));
  }
  
  protected void applyRotations(EntityPigZombie entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.rotate(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.deathTime > 0) {
      float f = (entityLiving.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.sqrt(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(f * 0.25F, 0.0F, 0.0F);
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
  
  public void transformHeldFull3DItemLayer() {
    GlStateManager.translate(0.0F, 0.1875F, 0.0F);
  }
  
  protected ResourceLocation getEntityTexture(EntityPigZombie entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? antiCMM_ZOMBIE_PIGMAN_TEXTURE : CMM_ZOMBIE_PIGMAN_TEXTURE) : (entity.isAntiMob() ? antiZOMBIE_PIGMAN_TEXTURE : ZOMBIE_PIGMAN_TEXTURE);
  }
  
  private void changeModel() {
    this.mainModel = EngenderConfig.mobs.useMobTalkerModels ? cmmmodel : regularmodel;
    this.layerRenderers.remove(this.armor);
    this.armor = new LayerBipedArmor(this) {
        protected void initArmor() {
          this.modelLeggings = EngenderConfig.mobs.useMobTalkerModels ? RenderPigZombie.cmmleggings : RenderPigZombie.regularleggings;
          this.modelArmor = EngenderConfig.mobs.useMobTalkerModels ? RenderPigZombie.cmmarmor : RenderPigZombie.regulararmor;
        }
      };
    addLayer((LayerRenderer)this.armor);
  }
  
  protected void preRenderCallback(EntityPigZombie entitylivingbaseIn, float partialTickTime) {
    changeModel();
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.translate(0.0F, 0.6F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, -0.3F, 0.0F); 
      if (entitylivingbaseIn.isRiding() && entitylivingbaseIn.isChild())
        GlStateManager.translate(0.0F, 0.6F, 0.25F); 
    } else if (entitylivingbaseIn.isSneaking()) {
      GlStateManager.translate(0.0F, 0.2F, 0.0F);
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
  
  public void doRender(EntityPigZombie entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityPigZombie entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
