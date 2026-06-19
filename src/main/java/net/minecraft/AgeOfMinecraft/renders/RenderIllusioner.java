package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.AgeOfMinecraft.renders.RenderLayerCompat;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityIllusioner;
import net.minecraft.AgeOfMinecraft.models.ModelIllager;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderIllusioner extends RenderLiving<EntityIllusioner> {
  private static final ResourceLocation ILLUSIONIST = new ResourceLocation("textures/entity/illager/illusionist.png");
  
  private static final ResourceLocation DISGUISE_VILLAGER = new ResourceLocation("textures/entity/villager/farmer.png");
  
  private static final ResourceLocation DISGUISE_VINDICATOR = new ResourceLocation("textures/entity/illager/vindicator.png");
  
  private static final ResourceLocation DISGUISE_EVOKER = new ResourceLocation("textures/entity/illager/evoker.png");
  
  public RenderIllusioner(RenderManager p_i47477_1_) {
    super(p_i47477_1_, new ModelIllager(0.0F, 0.0F, 64, 64), 0.5F);
    RenderLayerCompat.addLayer(this, (LayerRenderer)new LayerHeldItem(this) {
          public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            if (((EntityIllusioner)entitylivingbaseIn).isSpellcasting() || ((EntityIllusioner)entitylivingbaseIn).isArmsRaised())
              super.doRenderLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale); 
          }
          
          protected void translateToHand(EnumHandSide p_191361_1_) {
            ((ModelIllager)RenderLayerCompat.getMainModel(this.livingEntityRenderer)).getArm(p_191361_1_).postRender(0.0625F);
          }
        });
    ((ModelIllager)RenderLayerCompat.getMainModel(this)).hat.showModel = true;
  }
  
  protected ResourceLocation getEntityTexture(EntityIllusioner entity) {
    if (entity.getDisguiseID() > 0 && entity.getDisguiseTime() > 0) {
      switch (entity.getDisguiseID()) {
        case 2:
          return DISGUISE_VINDICATOR;
        case 3:
          return DISGUISE_EVOKER;
      } 
      return DISGUISE_VILLAGER;
    } 
    return ILLUSIONIST;
  }
  
  protected void preRenderCallback(EntityIllusioner entitylivingbaseIn, float partialTickTime) {
    float f = 0.9375F;
    GlStateManager.scale(f, f, f);
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
  
  public void doRender(EntityIllusioner entity, double x, double y, double z, float entityYaw, float partialTicks) {
    if (entity.getGhostTime() > 0) {
      Vec3d[] avec3d = entity.getRenderLocations(partialTicks);
      float f = handleRotationFloat(entity, partialTicks);
      for (int i = 0; i < avec3d.length; i++)
        super.doRender(entity, x + (avec3d[i]).x + MathHelper.cos(i + f * 0.5F) * 0.025D, y + (avec3d[i]).y + MathHelper.cos(i + f * 0.75F) * 0.0125D, z + (avec3d[i]).z + MathHelper.cos(i + f * 0.7F) * 0.025D, entityYaw, partialTicks); 
      RenderLayerCompat.setShadowOpaque(this, 0.0F);
    } else {
      RenderLayerCompat.setShadowOpaque(this, 1.0F);
      super.doRender(entity, x, y, z, entityYaw, partialTicks);
    } 
  }
  
  protected boolean isVisible(EntityIllusioner entity) {
    return (!entity.isInvisible() || RenderLayerCompat.isRenderOutlines(this) || entity.getGhostTime() > 0);
  }
}
