package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStormHead;
import net.minecraft.AgeOfMinecraft.models.ModelWitherStormHead;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherStormHead extends RenderLiving<EntityWitherStormHead> {
  private static final ResourceLocation witherStormTextures = new ResourceLocation("ageofminecraft", "textures/entities/wither_storm_head.png");
  
  public RenderWitherStormHead(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelWitherStormHead(), 4.0F);
    func_177094_a(new LayerWitherStormTractorBeam(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityWitherStormHead entity) {
    return witherStormTextures;
  }
  
  protected void preRenderCallback(EntityWitherStormHead entitylivingbaseIn, float partialTickTime) {
    float f = 2.0F;
    GlStateManager.func_179152_a(f, f, f);
    GlStateManager.func_179109_b(0.0F, -0.25F, 2.25F);
    if (entitylivingbaseIn.field_70173_aa <= 40) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 40.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
    } 
  }
}
