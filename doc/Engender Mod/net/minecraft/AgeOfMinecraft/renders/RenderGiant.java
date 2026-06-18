package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGiant;
import net.minecraft.AgeOfMinecraft.models.ModelCMMGiant;
import net.minecraft.AgeOfMinecraft.models.ModelGiant;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
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
public class RenderGiant extends RenderLiving<EntityGiant> {
  private static final ResourceLocation CMM_ZOMBIE_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/zombie.png");
  
  private static final ResourceLocation ZOMBIE_ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
  
  private static final ResourceLocation antiCMM_ZOMBIE_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/zombie.png");
  
  private static final ResourceLocation antiZOMBIE_ZOMBIE_TEXTURES = new ResourceLocation("ageofminecraft", "textures/entities/anti/zombie.png");
  
  private static ModelCMMGiant cmmmodel = new ModelCMMGiant();
  
  private static ModelGiant regularmodel = new ModelGiant();
  
  public RenderGiant(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 3.0F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 0.1675F));
    func_177094_a((LayerRenderer)new LayerHeldItem((RenderLivingBase)this));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.field_78116_c, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
    func_177094_a((LayerRenderer)new LayerBipedArmor((RenderLivingBase)this) {
          protected void func_177177_a() {
            this.field_177189_c = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)new ModelCMMGiant(0.5F, true) : (ModelBase)new ModelGiant(0.5F, true);
            this.field_177186_d = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)new ModelCMMGiant(1.0F, true) : (ModelBase)new ModelGiant(1.0F, true);
          }
        });
  }
  
  public void func_82422_c() {
    GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F);
  }
  
  protected void applyRotations(EntityGiant entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
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
  }
  
  protected void preRenderCallback(EntityGiant entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    GlStateManager.func_179152_a(6.0F, 6.0F, 6.0F);
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, 0.65F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, -0.3F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.55F, 0.1F); 
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
  
  protected ResourceLocation getEntityTexture(EntityGiant entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? antiCMM_ZOMBIE_TEXTURES : CMM_ZOMBIE_TEXTURES) : (entity.isAntiMob() ? antiZOMBIE_ZOMBIE_TEXTURES : ZOMBIE_ZOMBIE_TEXTURES);
  }
  
  public void doRender(EntityGiant entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityGiant entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
