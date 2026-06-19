package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelChagarothSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityLesserDreadbeast;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLesserDreadbeast extends RenderLiving<EntityLesserDreadbeast> {
  private float scale = 3.0F;
  
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/elite/lesser_dreadbeast.png");
  
  public RenderLesserDreadbeast(RenderManager manager) {
    super(manager, (ModelBase)new ModelChagarothSpawn(), 1.5F);
    addLayer((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected void preRenderCallback(EntityLesserDreadbeast entitylivingbaseIn, float partialTickTime) {
    GlStateManager.scale(this.scale, this.scale, this.scale);
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
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
  
  protected ResourceLocation getEntityTexture(EntityLesserDreadbeast entity) {
    return mobTexture;
  }
}
