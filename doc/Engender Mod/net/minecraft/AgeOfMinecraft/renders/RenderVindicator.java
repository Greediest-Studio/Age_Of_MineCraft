package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityVindicator;
import net.minecraft.AgeOfMinecraft.models.ModelCMMVindicator;
import net.minecraft.AgeOfMinecraft.models.ModelIllager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVindicator extends RenderLiving<EntityVindicator> {
  private static final ResourceLocation CMM_VINDICATOR_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/vindicator.png");
  
  private static final ResourceLocation VINDICATOR_TEXTURE = new ResourceLocation("textures/entity/illager/vindicator.png");
  
  private static final ResourceLocation antiCMM_VINDICATOR_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/vindicator.png");
  
  private static final ResourceLocation antiVINDICATOR_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/vindicator.png");
  
  private static ModelCMMVindicator cmmmodel = new ModelCMMVindicator();
  
  private static ModelIllager regularmodel = new ModelIllager(0.0F, 0.0F, 64, 64);
  
  public RenderVindicator(RenderManager p_i47189_1_) {
    super(p_i47189_1_, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    func_177094_a(new LayerCustomHeadEngender(regularmodel.head, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
    func_177094_a((LayerRenderer)new LayerHeldItem((RenderLivingBase)this) {
          public void func_177141_a(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            if (((EntityVindicator)entitylivingbaseIn).isAggressive())
              super.func_177141_a(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale); 
          }
          
          protected void func_191361_a(EnumHandSide p_191361_1_) {
            if (EngenderConfig.mobs.useMobTalkerModels) {
              RenderVindicator.cmmmodel.getArm(p_191361_1_).func_78794_c(0.0625F);
            } else {
              RenderVindicator.regularmodel.getArm(p_191361_1_).func_78794_c(0.0625F);
            } 
          }
        });
  }
  
  protected ResourceLocation getEntityTexture(EntityVindicator entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? antiCMM_VINDICATOR_TEXTURE : CMM_VINDICATOR_TEXTURE) : (entity.isAntiMob() ? antiVINDICATOR_TEXTURE : VINDICATOR_TEXTURE);
  }
  
  protected void applyRotations(EntityVindicator entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
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
  
  protected void preRenderCallback(EntityVindicator entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    float f = 0.9375F;
    if (!EngenderConfig.mobs.useMobTalkerModels) {
      GlStateManager.func_179152_a(f, f, f);
      if (entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    } 
    if (EngenderConfig.mobs.useMobTalkerModels) {
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
  
  public void doRender(EntityVindicator entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = func_77044_a((EntityLivingBase)entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.field_76987_f = 0.0F;
    } else if (!entity.func_82150_aj()) {
      this.field_76987_f = 1.0F;
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityVindicator entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
