package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityBat;
import net.minecraft.AgeOfMinecraft.models.ModelBat;
import net.minecraft.AgeOfMinecraft.models.ModelBlaze;
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
public class RenderBat extends RenderLiving<EntityBat> {
  private static final ResourceLocation batTextures = new ResourceLocation("textures/entity/bat.png");
  
  private static ModelBat regularmodel = new ModelBat();
  
  private static ModelBlaze disguisemodel = new ModelBlaze();
  
  public RenderBat(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelBat(), 0.25F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityBat entity) {
    return (entity.getIllusionFormTime() > 0) ? new ResourceLocation("textures/entity/blaze.png") : batTextures;
  }
  
  protected void preRenderCallback(EntityBat entitylivingbaseIn, float partialTickTime) {
    this.field_77045_g = (entitylivingbaseIn.getIllusionFormTime() > 0) ? (ModelBase)disguisemodel : (ModelBase)regularmodel;
    if (entitylivingbaseIn.getIllusionFormTime() <= 0)
      GlStateManager.func_179152_a(0.35F, 0.35F, 0.35F); 
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
  
  protected void applyRotations(EntityBat bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
    if (!bat.getIsBatHanging()) {
      GlStateManager.func_179109_b(0.0F, MathHelper.func_76134_b((bat.func_175446_cd() ? 1.0F : p_77043_2_) * 0.3F) * 0.1F, 0.0F);
    } else {
      GlStateManager.func_179109_b(0.0F, -0.1F, 0.0F);
    } 
    super.func_77043_a((EntityLivingBase)bat, p_77043_2_, p_77043_3_, partialTicks);
  }
  
  public void doRender(EntityBat entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected boolean isVisible(EntityBat entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
