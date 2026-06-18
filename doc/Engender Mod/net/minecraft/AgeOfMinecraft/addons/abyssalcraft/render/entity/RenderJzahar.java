package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model.ModelJzahar;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity.layer.LayerJzaharDeath;
import net.minecraft.AgeOfMinecraft.renders.LayerLearningBook;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderJzahar extends RenderLiving<EntityJzahar> {
  private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/j'zahar.png");
  
  public RenderJzahar(RenderManager manager) {
    super(manager, (ModelBase)new ModelJzahar(true), 1.0F);
    func_177094_a((LayerRenderer)new LayerJzaharDeath());
    func_177094_a((LayerRenderer)new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityJzahar entity) {
    return mobTexture;
  }
  
  public void preRenderCallback(EntityJzahar entity, float par2) {
    GlStateManager.func_179152_a(1.5F, 1.5F, 1.5F);
    GlStateManager.func_179109_b(-0.125F, 0.0F, -0.05F);
    float fit = entity.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entity.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entity.func_70089_S() && !entity.func_184207_aI() && (entity.func_70093_af() || entity.getJukeboxToDanceTo() != null))
      GlStateManager.func_179109_b(0.0F, 0.5F + MathHelper.func_76134_b(func_77044_a((EntityLivingBase)entity, par2) * 0.2F) * 0.1F, 0.0F); 
  }
}
