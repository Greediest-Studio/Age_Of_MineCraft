package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantSkeleton;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelMutantSkeleton;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMutantSkeleton extends RenderLiving<EntityMutantSkeleton> {
  static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("mutant_skeleton");
  
  private static final ResourceLocation Anti_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_skeleton.png");
  
  public RenderMutantSkeleton(RenderManager manager) {
    super(manager, (ModelBase)new ModelMutantSkeleton(), 0.7F);
  }
  
  protected void preRenderCallback(EntityMutantSkeleton entitylivingbaseIn, float partialTickTime) {
    GlStateManager.func_179109_b(0.0F, 0.0F, 0.1F);
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    if (!entitylivingbaseIn.field_70122_E)
      GlStateManager.func_179114_b(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
  }
  
  protected float getDeathMaxRotation(EntityMutantSkeleton entityLivingBaseIn) {
    return 0.0F;
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantSkeleton entity) {
    return entity.isAntiMob() ? Anti_TEXTURE : TEXTURE;
  }
}
