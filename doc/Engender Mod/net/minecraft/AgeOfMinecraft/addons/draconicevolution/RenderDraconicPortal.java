package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import java.util.Random;
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
public class RenderDraconicPortal extends RenderLiving<EntityDraconicPortal> {
  private static final ResourceLocation portalTextures = new ResourceLocation("ageofminecraft", "textures/entities/portals/draconic_portal.png");
  
  private Random rnd = new Random();
  
  public RenderDraconicPortal(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelPortal(), 4.0F);
    func_177094_a(new LayerDraconicPortalGlow(this));
    func_177094_a(new LayerDraconicPortalOverlay(this));
  }
  
  protected void preRenderCallback(EntityDraconicPortal entitylivingbaseIn, float partialTickTime) {
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
  
  public void doRender(EntityDraconicPortal entity, double x, double y, double z, float entityYaw, float partialTicks) {
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
  
  protected void applyRotations(EntityDraconicPortal p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_) {}
  
  protected ResourceLocation getEntityTexture(EntityDraconicPortal entity) {
    return portalTextures;
  }
}
