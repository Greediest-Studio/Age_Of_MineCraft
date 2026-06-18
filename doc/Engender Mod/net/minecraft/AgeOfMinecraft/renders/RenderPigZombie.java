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
public class RenderPigZombie extends RenderLiving<EntityPigZombie> {
  private static final ResourceLocation CMM_ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/pzombie.png");
  
  private static final ResourceLocation ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("textures/entity/zombie_pigman.png");
  
  private static final ResourceLocation antiCMM_ZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/yarrscmm/anti/pzombie.png");
  
  private static final ResourceLocation antiZOMBIE_PIGMAN_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/zombie_pigman.png");
  
  private LayerBipedArmor armor = new LayerBipedArmor((RenderLivingBase)this);
  
  private static ModelCMMPigZombie cmmmodel = new ModelCMMPigZombie();
  
  private static ModelZombie regularmodel = new ModelZombie();
  
  private static ModelCMMPigZombie cmmleggings = new ModelCMMPigZombie(0.5F, true);
  
  private static ModelZombie regularleggings = new ModelZombie(0.5F, true);
  
  private static ModelCMMPigZombie cmmarmor = new ModelCMMPigZombie(1.0F, true);
  
  private static ModelZombie regulararmor = new ModelZombie(1.0F, true);
  
  public RenderPigZombie(RenderManager renderManagerIn) {
    super(renderManagerIn, EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel, 0.5F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    this.armor = new LayerBipedArmor((RenderLivingBase)this) {
        protected void func_177177_a() {
          this.field_177189_c = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderPigZombie.cmmleggings : (ModelBase)RenderPigZombie.regularleggings;
          this.field_177186_d = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderPigZombie.cmmarmor : (ModelBase)RenderPigZombie.regulararmor;
        }
      };
    func_177094_a((LayerRenderer)this.armor);
    func_177094_a((LayerRenderer)new LayerElytra((RenderLivingBase)this));
    func_177094_a((LayerRenderer)new LayerHeldItem((RenderLivingBase)this));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.field_78116_c, cmmmodel.Head));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected void applyRotations(EntityPigZombie entityLiving, float p_77043_2_, float p_77043_3_, float partialTicks) {
    GlStateManager.func_179114_b(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);
    if (entityLiving.field_70725_aQ > 0) {
      float f = (entityLiving.field_70725_aQ + partialTicks - 1.0F) / 20.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 1.0F)
        f = 1.0F; 
      if (EngenderConfig.mobs.useMobTalkerModels) {
        GlStateManager.func_179114_b(f * func_77037_a((EntityLivingBase)entityLiving), 0.0F, 0.0F, 1.0F);
        GlStateManager.func_179109_b(f * 0.25F, 0.0F, 0.0F);
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
  
  public void func_82422_c() {
    GlStateManager.func_179109_b(0.0F, 0.1875F, 0.0F);
  }
  
  protected ResourceLocation getEntityTexture(EntityPigZombie entity) {
    return EngenderConfig.mobs.useMobTalkerModels ? (entity.isAntiMob() ? antiCMM_ZOMBIE_PIGMAN_TEXTURE : CMM_ZOMBIE_PIGMAN_TEXTURE) : (entity.isAntiMob() ? antiZOMBIE_PIGMAN_TEXTURE : ZOMBIE_PIGMAN_TEXTURE);
  }
  
  private void changeModel() {
    this.field_77045_g = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)cmmmodel : (ModelBase)regularmodel;
    this.field_177097_h.remove(this.armor);
    this.armor = new LayerBipedArmor((RenderLivingBase)this) {
        protected void func_177177_a() {
          this.field_177189_c = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderPigZombie.cmmleggings : (ModelBase)RenderPigZombie.regularleggings;
          this.field_177186_d = EngenderConfig.mobs.useMobTalkerModels ? (ModelBase)RenderPigZombie.cmmarmor : (ModelBase)RenderPigZombie.regulararmor;
        }
      };
    func_177094_a((LayerRenderer)this.armor);
  }
  
  protected void preRenderCallback(EntityPigZombie entitylivingbaseIn, float partialTickTime) {
    changeModel();
    if (EngenderConfig.mobs.useMobTalkerModels) {
      if (entitylivingbaseIn.isSitResting())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.0F); 
      if (entitylivingbaseIn.isSitResting() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, -0.3F, 0.0F); 
      if (entitylivingbaseIn.func_184218_aH() && entitylivingbaseIn.func_70631_g_())
        GlStateManager.func_179109_b(0.0F, 0.6F, 0.25F); 
    } else if (entitylivingbaseIn.func_70093_af()) {
      GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F);
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
  
  public void doRender(EntityPigZombie entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityPigZombie entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
