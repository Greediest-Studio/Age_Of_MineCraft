package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelShadowMonster;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityShadowMonster;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.AgeOfMinecraft.renders.LayerMobCape;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShadowMonster extends RenderLiving<EntityShadowMonster> {
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/shadowmonster.png");
  
  public RenderShadowMonster(RenderManager manager) {
    this(manager, new ModelShadowMonster());
  }
  
  public RenderShadowMonster(RenderManager manager, ModelShadowMonster model) {
    super(manager, (ModelBase)model, 0.0F);
    func_177094_a((LayerRenderer)new LayerCustomHead(model.Head));
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
    func_177094_a((LayerRenderer)new LayerMobCape((RenderLivingBase)this));
  }
  
  protected void renderModel(EntityShadowMonster entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    GlStateManager.func_179147_l();
    GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    super.func_77036_a((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    GlStateManager.func_179084_k();
  }
  
  protected void preRenderCallback(EntityShadowMonster entitylivingbaseIn, float partialTickTime) {
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
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
  
  protected ResourceLocation getEntityTexture(EntityShadowMonster entity) {
    return mobTexture;
  }
}
