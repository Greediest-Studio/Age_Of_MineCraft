package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelChagaroth;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderChagaroth extends RenderLiving<EntityChagaroth> {
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/chagaroth.png");
  
  public RenderChagaroth(RenderManager manager) {
    super(manager, (ModelBase)new ModelChagaroth(), 1.5F);
    func_177094_a((LayerRenderer)new LayerArrowCustomSized((RenderLivingBase)this, 1.0F));
    func_177094_a((LayerRenderer)new LayerCustomHead(((ModelChagaroth)this.field_77045_g).middlehead));
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityChagaroth entity) {
    return mobTexture;
  }
  
  protected void preRenderCallback(EntityChagaroth par1EntityLivingBase, float par2) {
    if (par1EntityLivingBase.func_184218_aH())
      GlStateManager.func_179109_b(0.0F, -0.25F, -1.0F); 
    float fit = par1EntityLivingBase.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (par1EntityLivingBase.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
  }
}
