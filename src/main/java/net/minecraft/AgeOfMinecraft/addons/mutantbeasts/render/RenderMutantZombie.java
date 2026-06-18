package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantZombie;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelMutantZombie;
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
public class RenderMutantZombie extends RenderLiving<EntityMutantZombie> {
  private static final ResourceLocation MutantZombie = MutantBeasts.getEntityTexture("mutant_zombie");
  
  private static final ResourceLocation MutantHusk = new ResourceLocation("ageofminecraft", "textures/entities/mutant/mutant_husk.png");
  
  private static final ResourceLocation MutantPrisonZombie = new ResourceLocation("ageofminecraft", "textures/entities/mutant/mutant_prison_zombie.png");
  
  private static final ResourceLocation MutantAbyssalZombie = new ResourceLocation("ageofminecraft", "textures/entities/mutant/mutant_abyssal_zombie.png");
  
  private static final ResourceLocation AntiMutantZombie = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_zombie.png");
  
  private static final ResourceLocation AntiMutantHusk = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_husk.png");
  
  private static final ResourceLocation AntiMutantPrisonZombie = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_prison_zombie.png");
  
  private static final ResourceLocation AntiMutantAbyssalZombie = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_abyssal_zombie.png");
  
  public RenderMutantZombie(RenderManager manager) {
    super(manager, (ModelBase)new ModelMutantZombie(), 1.0F);
  }
  
  protected void renderModel(EntityMutantZombie living, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (living.vanishTime > 0) {
      GlStateManager.enableNormalize();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F - (living.vanishTime + ageInTicks) / 100.0F * 0.6F);
      super.renderModel(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GlStateManager.disableBlend();
      GlStateManager.disableNormalize();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    } else {
      super.renderModel(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    } 
  }
  
  protected void preRenderCallback(EntityMutantZombie entitylivingbaseIn, float partialTickTime) {
    GlStateManager.scale(1.3F, 1.3F, 1.3F);
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.scale(fit, fit, fit);
    if (entitylivingbaseIn.getZombieType() == 1)
      GlStateManager.scale(1.0625F, 1.0625F, 1.0625F); 
    if (entitylivingbaseIn.isHero())
      GlStateManager.scale(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.isChild())
      GlStateManager.scale(0.5F, 0.5F, 0.5F); 
    if (!entitylivingbaseIn.onGround)
      GlStateManager.rotate(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
  }
  
  protected void applyRotations(EntityMutantZombie entityLiving, float ageInTicks, float rotationYaw, float partialTicks) {
    if (entityLiving.deathTime > 0) {
      GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
      int pitch = Math.min(20, entityLiving.deathTime);
      boolean reviving = false;
      if (entityLiving.deathTime > 100 && entityLiving.getLives() > 0) {
        pitch = 140 - entityLiving.deathTime;
        reviving = true;
      } 
      if (pitch > 0) {
        float f = (pitch + partialTicks - 1.0F) / 20.0F * 1.6F;
        if (reviving)
          f = (pitch - partialTicks) / 40.0F * 1.6F; 
        f = MathHelper.sqrt(f);
        if (f > 1.0F)
          f = 1.0F; 
        GlStateManager.rotate(f * getDeathMaxRotation(entityLiving), -1.0F, 0.0F, 0.0F);
      } 
    } else {
      super.applyRotations(entityLiving, ageInTicks, rotationYaw, partialTicks);
    } 
  }
  
  protected float getDeathMaxRotation(EntityMutantZombie living) {
    return 80.0F;
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantZombie entity) {
    switch (entity.getZombieType()) {
      case 1:
        return entity.isAntiMob() ? AntiMutantHusk : MutantHusk;
      case 2:
        return entity.isAntiMob() ? AntiMutantPrisonZombie : MutantPrisonZombie;
      case 3:
        return entity.isAntiMob() ? AntiMutantAbyssalZombie : MutantAbyssalZombie;
    } 
    return entity.isAntiMob() ? AntiMutantZombie : MutantZombie;
  }
}
