package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.render.entity;

import net.minecraft.AgeOfMinecraft.renders.RenderLayerCompat;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityAbyssalZombie;
import net.minecraft.AgeOfMinecraft.models.ModelZombie;
import net.minecraft.AgeOfMinecraft.renders.LayerArrowCustomSized;
import net.minecraft.AgeOfMinecraft.renders.LayerMobCape;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAbyssalZombie extends RenderBiped<EntityAbyssalZombie> {
  private static final ResourceLocation zombieTexture = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie.png");
  
  private static final ResourceLocation zombieTexture_end = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie_end.png");
  
  private static final ResourceLocation antizombieTexture = new ResourceLocation("abyssalcraft:textures/model/anti/abyssal_zombie.png");
  
  public RenderAbyssalZombie(RenderManager manager) {
    super(manager, new ModelZombie(0.0F, true), 0.5F);
    RenderLayerCompat.addLayer(this, (LayerRenderer)new LayerBipedArmor(this) {
          protected void initArmor() {
            RenderLayerCompat.setArmorLeggings(this, new ModelZombie(0.5F, true));
            RenderLayerCompat.setArmorBody(this, new ModelZombie(1.0F, true));
          }
        });
    RenderLayerCompat.addLayer(this, (LayerRenderer)new LayerArrowCustomSized(this, 1.0F));
    RenderLayerCompat.addLayer(this, (LayerRenderer)new LayerMobCape(this));
  }
  
  protected ResourceLocation getEntityTexture(EntityAbyssalZombie par1EntityLiving) {
    return par1EntityLiving.isAntiMob() ? antizombieTexture : ((par1EntityLiving.getZombieType() == 2) ? zombieTexture_end : zombieTexture);
  }
  
  protected void preRenderCallback(EntityAbyssalZombie entitylivingbaseIn, float partialTickTime) {
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
}
