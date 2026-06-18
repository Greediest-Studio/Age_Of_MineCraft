package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityCreeder;
import net.minecraft.AgeOfMinecraft.models.ModelCreeder;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCreeder extends RenderLiving<EntityCreeder> {
  private static final ResourceLocation spiderTextures = new ResourceLocation("ageofminecraft", "textures/entities/creeder.png");
  
  public RenderCreeder(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelCreeder(), 1.0F);
    func_177094_a(new LayerCreederEyes(this));
    func_177094_a(new LayerCreederCharge(this));
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
    func_177094_a(new LayerLearningBook(this));
  }
  
  protected void preRenderCallback(EntityCreeder entitylivingbaseIn, float partialTickTime) {
    float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
    float f1 = 1.0F + MathHelper.func_76126_a(f * 100.0F) * f * 0.01F;
    f = MathHelper.func_76131_a(f, 0.0F, 1.0F);
    f *= f;
    f *= f;
    float f2 = (1.0F + f * 0.4F) * f1;
    float f3 = (1.0F + f * 0.1F) / f1;
    GlStateManager.func_179152_a(f2, f3, f2);
    if (entitylivingbaseIn.func_184218_aH())
      GlStateManager.func_179109_b(0.0F, -0.325F, 0.0F); 
    if (entitylivingbaseIn.func_70093_af())
      GlStateManager.func_179109_b(0.0F, 0.2F, 0.0F); 
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
  
  protected int getColorMultiplier(EntityCreeder entitylivingbaseIn, float lightBrightness, float partialTickTime) {
    float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
    if ((int)(f * 10.0F) % 2 == 0)
      return 0; 
    int i = (int)(f * 0.2F * 255.0F);
    i = MathHelper.func_76125_a(i, 0, 255);
    return i << 24 | 0x30FFFFFF;
  }
  
  protected ResourceLocation getEntityTexture(EntityCreeder entity) {
    return spiderTextures;
  }
  
  public void doRender(EntityCreeder entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityTameBase entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
