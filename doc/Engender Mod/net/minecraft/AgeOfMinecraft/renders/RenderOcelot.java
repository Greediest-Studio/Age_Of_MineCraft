package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
import net.minecraft.AgeOfMinecraft.models.ModelOcelot;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOcelot extends RenderLiving<EntityOcelot> {
  private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
  
  private static final ResourceLocation antiocelotTextures = new ResourceLocation("ageofminecraft", "textures/entities/anti/ocelot.png");
  
  public RenderOcelot(RenderManager renderManagerIn) {
    super(renderManagerIn, (ModelBase)new ModelOcelot(), 0.4F);
    func_177094_a(new LayerArrowCustomSized((RenderLivingBase<?>)this, 1.0F));
    func_177094_a(new LayerLearningBook(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityOcelot entity) {
    return entity.isAntiMob() ? antiocelotTextures : ocelotTextures;
  }
  
  protected void preRenderCallback(EntityOcelot entitylivingbaseIn, float partialTickTime) {
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
  
  public void doRender(EntityOcelot entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = func_77044_a((EntityLivingBase)entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.field_76987_f = 0.0F;
    } else {
      this.field_76987_f = 1.0F;
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityOcelot entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
