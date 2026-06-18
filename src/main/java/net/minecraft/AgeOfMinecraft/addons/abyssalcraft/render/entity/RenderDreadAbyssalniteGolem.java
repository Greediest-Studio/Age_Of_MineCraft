package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadgolem;
import net.minecraft.AgeOfMinecraft.models.ModelZombie;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.AgeOfMinecraft.renders.LayerMobCape;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDreadAbyssalniteGolem extends RenderBiped<EntityDreadgolem> {
  private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("abyssalcraft:textures/model/dread_warden.png");
  
  public RenderDreadAbyssalniteGolem(RenderManager manager) {
    super(manager, (ModelBiped)new ModelZombie(0.0F, true), 0.5F);
    addLayer((LayerRenderer)new LayerArrowCustomSized((RenderLivingBase)this, 1.0F));
    addLayer((LayerRenderer)new LayerLearningBook((RenderLiving)this));
    addLayer((LayerRenderer)new LayerMobCape((RenderLivingBase)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityDreadgolem entity) {
    return ZOMBIE_TEXTURES;
  }
  
  protected void preRenderCallback(EntityDreadgolem entitylivingbaseIn, float partialTickTime) {
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
}
