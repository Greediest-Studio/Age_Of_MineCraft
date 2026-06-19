package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelDreadSpawn;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDreadSpawn;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDreadSpawn extends RenderLiving<EntityDreadSpawn> {
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/dread_spawn.png");
  
  public RenderDreadSpawn(RenderManager manager) {
    super(manager, (ModelBase)new ModelDreadSpawn(), 0.5F);
    addLayer((LayerRenderer)new LayerArrowCustomSized((RenderLivingBase)this, 1.0F));
    addLayer((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityDreadSpawn entity) {
    return mobTexture;
  }
  
  protected void preRenderCallback(EntityDreadSpawn entitylivingbaseIn, float partialTickTime) {
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
}
