package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityVex;
import net.minecraft.AgeOfMinecraft.models.ModelVex;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
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
public class RenderVex extends RenderBiped<EntityVex> {
  private static final ResourceLocation VEX_TEXTURE = new ResourceLocation("textures/entity/illager/vex.png");
  
  private static final ResourceLocation VEX_CHARGING_TEXTURE = new ResourceLocation("textures/entity/illager/vex_charging.png");
  
  private static final ResourceLocation antiVEX_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/vex.png");
  
  private static final ResourceLocation antiVEX_CHARGING_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/anti/vex_charging.png");
  
  public RenderVex(RenderManager p_i47190_1_) {
    super(p_i47190_1_, (ModelBiped)new ModelVex(), 0.3F);
    func_177094_a(new LayerCustomHeadEngender(((ModelVex)this.field_77045_g).field_78116_c, ((ModelVex)this.field_77045_g).field_78116_c));
    func_177094_a(new LayerLearningBook((RenderLiving<?>)this));
    func_177094_a(new LayerMobCape((RenderLivingBase<?>)this));
  }
  
  protected ResourceLocation getEntityTexture(EntityVex entity) {
    return entity.isCharging() ? (entity.isAntiMob() ? antiVEX_CHARGING_TEXTURE : VEX_CHARGING_TEXTURE) : (entity.isAntiMob() ? antiVEX_TEXTURE : VEX_TEXTURE);
  }
  
  protected void preRenderCallback(EntityVex entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    GlStateManager.func_179152_a(0.4F, 0.4F, 0.4F);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.field_70173_aa <= 21 && entitylivingbaseIn.field_70173_aa > 0) {
      float f5 = (entitylivingbaseIn.field_70173_aa + partialTickTime - 1.0F) / 20.0F * 1.6F;
      f5 = MathHelper.func_76129_c(f5);
      if (f5 > 1.0F)
        f5 = 1.0F; 
      GlStateManager.func_179152_a(f5, f5, f5);
      GlStateManager.func_179114_b(f5 * 90.0F - 90.0F, f5, f5, f5);
    } 
  }
  
  public void doRender(EntityVex entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = func_77044_a((EntityLivingBase)entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.func_76986_a((EntityLiving)entity, x + (avec3d[i]).field_72450_a + MathHelper.func_76134_b(i + f * 0.5F) * 0.025D, y + (avec3d[i]).field_72448_b + MathHelper.func_76134_b(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).field_72449_c + MathHelper.func_76134_b(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      this.field_76987_f = 0.0F;
    } else if (!entity.func_82150_aj()) {
      this.field_76987_f = 1.0F;
      super.func_76986_a((EntityLiving)entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityVex entity) {
    return (!entity.func_82150_aj() || this.field_188301_f || entity.getGhostTime() > 0);
  }
}
