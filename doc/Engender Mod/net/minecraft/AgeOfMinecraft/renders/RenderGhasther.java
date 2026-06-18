package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityGhasther;
import net.minecraft.AgeOfMinecraft.models.ModelGhasther;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhasther extends RenderLiving<EntityGhasther> {
  private static final ResourceLocation ghastTextures = new ResourceLocation("ageofminecraft", "textures/entities/giant_ghast.png");
  
  private static final ResourceLocation ghastShootingTextures = new ResourceLocation("ageofminecraft", "textures/entities/giant_ghast_shooting.png");
  
  private static ModelGhasther regularmodel = new ModelGhasther();
  
  public RenderGhasther(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)regularmodel, 6.0F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 0.1F));
    func_177094_a(new LayerCustomHeadEngender(regularmodel.body, regularmodel.body));
    func_177094_a(new LayerLearningBook(this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityGhasther entity) {
    return entity.isAttacking() ? ghastShootingTextures : ghastTextures;
  }
  
  protected void preRenderCallback(EntityGhasther entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    float f = 1.0F;
    float f1 = 8.0F + f;
    float f2 = 8.0F + 1.0F / f;
    GlStateManager.func_179152_a(f2, f1, f2);
    GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
    GlStateManager.func_179109_b(0.0F, -0.05F, 0.0F);
    if (entitylivingbaseIn.func_184218_aH())
      GlStateManager.func_179109_b(0.0F, -0.125F, 0.0F); 
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.field_70173_aa <= 81 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 80.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 < -2.0F)
        f5 = -2.0F; 
      GlStateManager.func_179109_b(0.0F, -f5, 0.0F);
      GlStateManager.func_179109_b(0.0F, 1.25F, 0.0F);
    } 
  }
}
