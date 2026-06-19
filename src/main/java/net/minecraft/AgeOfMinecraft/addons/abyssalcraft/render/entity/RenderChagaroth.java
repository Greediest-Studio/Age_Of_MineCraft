package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelChagaroth;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderChagaroth extends RenderLiving<EntityChagaroth> {
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/chagaroth.png");
  
  public RenderChagaroth(RenderManager manager) {
    super(manager, new ModelChagaroth(), 1.5F);
    addLayer((LayerRenderer)new LayerArrowCustomSized(this, 1.0F));
    addLayer((LayerRenderer)new LayerCustomHead(((ModelChagaroth)this.mainModel).middlehead));
    
  }
  
  protected ResourceLocation getEntityTexture(EntityChagaroth entity) {
    return mobTexture;
  }
  
  protected void preRenderCallback(EntityChagaroth par1EntityLivingBase, float par2) {
    if (par1EntityLivingBase.isRiding())
      GlStateManager.translate(0.0F, -0.25F, -1.0F); 
    float fit = par1EntityLivingBase.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (par1EntityLivingBase.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
  }
}
