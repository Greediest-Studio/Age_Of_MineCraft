package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalPortal;
import net.minecraft.AgeOfMinecraft.models.ModelPortal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAbyssalPortal extends RenderLiving<EntityAbyssalPortal> {
  private static final ResourceLocation portalTextures = new ResourceLocation("ageofminecraft", "textures/entities/portals/abyssal_portal.png");
  
  private Random rnd = new Random();
  
  public RenderAbyssalPortal(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelPortal(), 4.0F);
    func_177094_a(new LayerAbyssalPortalGlow(this));
    func_177094_a(new LayerAbyssalPortalOverlay(this));
  }
  
  protected void preRenderCallback(EntityAbyssalPortal entitylivingbaseIn, float partialTickTime) {
    GlStateManager.func_179152_a(4.0F, 4.0F, 4.0F);
    if (entitylivingbaseIn.field_70173_aa < 60) {
      float f = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 60.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f < -1.0F)
        f = -1.0F; 
      GlStateManager.func_179109_b(0.0F, -f, 0.0F);
      GlStateManager.func_179109_b(0.0F, 1.25F, 0.0F);
    } 
    GlStateManager.func_179109_b(0.0F, 0.001F, 0.0F);
    if (entitylivingbaseIn.field_70725_aQ > 0) {
      float f = (entitylivingbaseIn.field_70725_aQ + partialTickTime - 1.0F) / 60.0F * 1.6F;
      f = MathHelper.func_76129_c(f);
      if (f > 4.0F)
        f = 4.0F; 
      GlStateManager.func_179109_b(0.0F, f, 0.0F);
      GlStateManager.func_179114_b(f * 20.0F, 1.0F, 0.0F, 0.0F);
    } 
  }
  
  public void doRender(EntityAbyssalPortal entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.field_70725_aQ > 0) {
      double d0 = 0.1D;
      x += this.rnd.nextGaussian() * d0;
      z += this.rnd.nextGaussian() * d0;
    } 
    if (entity.field_70737_aN > 0) {
      double d0 = entity.field_70737_aN * 0.005D;
      x += this.rnd.nextGaussian() * d0;
      z += this.rnd.nextGaussian() * d0;
    } 
    super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
  }
  
  protected void applyRotations(EntityAbyssalPortal p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_) {}
  
  protected ResourceLocation getEntityTexture(EntityAbyssalPortal entity) {
    return portalTextures;
  }
}
