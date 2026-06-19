package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.models.ModelVex;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderVex extends RenderBiped<EntityVex> {
  private static final ResourceLocation VEX_TEXTURE = new ResourceLocation("textures/entity/illager/vex.png");
  
  private static final ResourceLocation VEX_CHARGING_TEXTURE = new ResourceLocation("textures/entity/illager/vex_charging.png");
  
  private static final ResourceLocation antiVEX_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/vex.png");
  
  private static final ResourceLocation antiVEX_CHARGING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/vex_charging.png");
  
  public RenderVex(RenderManager p_i47190_1_) {
    super(p_i47190_1_, (ModelBiped)new ModelVex(), 0.3F);
    addLayer(new LayerCustomHeadEngender(((ModelVex)this.mainModel).bipedHead, ((ModelVex)this.mainModel).bipedHead));
    addLayer(new LayerLearningBook((RenderLiving<?>)this));
    addLayer(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityVex entity) {
    return entity.isCharging() ? (entity.isAntiMob() ? antiVEX_CHARGING_TEXTURE : VEX_CHARGING_TEXTURE) : (entity.isAntiMob() ? antiVEX_TEXTURE : VEX_TEXTURE);
  }
  
  protected void preRenderCallback(EntityVex entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    GlStateManager.scale(0.4F, 0.4F, 0.4F);
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.ticksExisted <= 21 && entitylivingbaseIn.ticksExisted > 0) {
      float f5 = (entitylivingbaseIn.ticksExisted + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.sqrt(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.scale(f5, f5, f5);
      GlStateManager.rotate(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  public void doRender(EntityVex entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = handleRotationFloat(entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.doRender(entity, x + (avec3d[i]).x + MathHelper.cos(i + f * 0.5F) * 0.025D, y + (avec3d[i]).y + MathHelper.cos(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).z + MathHelper.cos(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.shadowOpaque = 0.0F;
    } else if (!entity.isInvisible()) {
      this.shadowOpaque = 1.0F;
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityVex entity) {
    return (!entity.isInvisible() || this.renderOutlines || entity.getGhostTime() > 0);
  }
}
