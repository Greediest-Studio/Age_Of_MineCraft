package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.render;

import chumbanotz.mutantbeasts.MutantBeasts;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantEnderman;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.model.ModelMutantEnderman;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMutantEnderman extends RenderLiving<EntityMutantEnderman> {
  private static final ResourceLocation TEXTURE = MutantBeasts.getEntityTexture("mutant_enderman/mutant_enderman");
  
  private static final ResourceLocation Anti_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_enderman.png");
  
  private static final ResourceLocation GLOW_TEXTURE = MutantBeasts.getEntityTexture("endersoul");
  
  private static final ResourceLocation EYES_TEXTURE = MutantBeasts.getEntityTexture("mutant_enderman/eyes");
  
  private static final ResourceLocation Anti_EYES_TEXTURE = new ResourceLocation("ageofminecraft", "textures/entities/mutant/anti/anti_mutant_enderman_eyes.png");
  
  private static final ResourceLocation DEATH_TEXTURE = MutantBeasts.getEntityTexture("mutant_enderman/death");
  
  private static ModelMutantEnderman endermanModel = new ModelMutantEnderman();
  
  private static ModelEnderman cloneModel = new ModelEnderman(0.0F);
  
  private boolean teleportAttack;
  
  public RenderMutantEnderman(RenderManager manager) {
    super(manager, (ModelBase)new ModelMutantEnderman(), 0.8F);
    func_177094_a(new EyesLayer());
    func_177094_a(new GlowLayer());
  }
  
  protected void renderModel(EntityMutantEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
    if (entitylivingbaseIn.field_70725_aQ > 80) {
      float blendFactor = (entitylivingbaseIn.field_70725_aQ - 80) / 200.0F;
      GlStateManager.func_179143_c(515);
      GlStateManager.func_179141_d();
      GlStateManager.func_179092_a(516, blendFactor);
      func_110776_a(DEATH_TEXTURE);
      this.field_77045_g.func_78088_a((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
      GlStateManager.func_179092_a(516, 0.1F);
      GlStateManager.func_179143_c(514);
    } 
    super.func_77036_a((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    GlStateManager.func_179143_c(515);
  }
  
  protected void preRenderCallback(EntityMutantEnderman entitylivingbaseIn, float partialTickTime) {
    float fit = entitylivingbaseIn.getFittness();
    GlStateManager.func_179152_a(fit, fit, fit);
    if (entitylivingbaseIn.isHero())
      GlStateManager.func_179152_a(1.05F, 1.05F, 1.05F); 
    if (entitylivingbaseIn.func_70631_g_())
      GlStateManager.func_179152_a(0.5F, 0.5F, 0.5F); 
    if (!entitylivingbaseIn.field_70122_E)
      GlStateManager.func_179114_b(entitylivingbaseIn.prevRotationPitchFalling + (entitylivingbaseIn.rotationPitchFalling - entitylivingbaseIn.prevRotationPitchFalling) * 2.0F - 1.0F, 1.0F, 0.0F, 0.0F); 
  }
  
  public void doRender(EntityMutantEnderman entity, double x, double y, double z, float entityYaw, float partialTicks) {
    this.field_76989_e = entity.isClone() ? 0.5F : 0.8F;
    this.field_76987_f = entity.isClone() ? 0.5F : 1.0F;
    this.teleportAttack = false;
    double addX = 0.0D;
    double addZ = 0.0D;
    this.field_77045_g = entity.isClone() ? (ModelBase)cloneModel : (ModelBase)endermanModel;
    cloneModel.field_78125_b = entity.isAggressive();
    cloneModel.field_78126_a = (entity.heldBlock[1] != 0);
    boolean forcedLook = (entity.getAttackID() == 6);
    boolean scream = (entity.getAttackID() == 8);
    boolean clone = (entity.isClone() && entity.isAggressive());
    boolean telesmash = (entity.getAttackID() == 10 && entity.getAttackTick() < 18);
    boolean death = (entity.getAttackID() == 11);
    if (forcedLook || scream || clone || telesmash || death) {
      double shake = 0.03D;
      if (entity.getAttackTick() >= 40 && !clone && !death)
        shake *= 0.5D; 
      if (clone)
        shake = 0.02D; 
      if (death)
        shake = (entity.getAttackTick() < 80) ? 0.019999999552965164D : 0.05000000074505806D; 
      addX = entity.func_70681_au().nextGaussian() * shake;
      addZ = entity.func_70681_au().nextGaussian() * shake;
    } 
    super.func_76986_a((EntityLiving)entity, x + addX, y, z + addZ, entityYaw, partialTicks);
  }
  
  protected float getDeathMaxRotation(EntityMutantEnderman entityLivingBaseIn) {
    return 0.0F;
  }
  
  protected ResourceLocation getEntityTexture(EntityMutantEnderman entity) {
    return entity.isClone() ? new ResourceLocation("textures/entity/enderman/enderman_eyes.png") : (entity.isAntiMob() ? Anti_TEXTURE : TEXTURE);
  }
  
  @SideOnly(Side.CLIENT)
  class EyesLayer implements LayerRenderer<EntityMutantEnderman> {
    public void doRenderLayer(EntityMutantEnderman entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      if (!entityIn.isClone() && entityIn.field_70725_aQ <= 80) {
        GlStateManager.func_179140_f();
        RenderMutantEnderman.this.func_110776_a(entityIn.isAntiMob() ? RenderMutantEnderman.Anti_EYES_TEXTURE : RenderMutantEnderman.EYES_TEXTURE);
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, 61680.0F, 0.0F);
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        RenderMutantEnderman.this.field_77045_g.func_78088_a((Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        RenderMutantEnderman.this.func_177105_a((EntityLiving)entityIn);
        GlStateManager.func_179145_e();
      } 
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
  
  @SideOnly(Side.CLIENT)
  class GlowLayer implements LayerRenderer<EntityMutantEnderman> {
    public void doRenderLayer(EntityMutantEnderman entityIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      boolean scream = (entityIn.getAttackID() == 8);
      boolean clone = entityIn.isClone();
      if (scream || clone) {
        float glowScale = 2.0F;
        if (scream)
          if (entityIn.getAttackTick() < 40) {
            glowScale = 1.2F + (entityIn.getAttackTick() + partialTicks) / 40.0F;
          } else if (entityIn.getAttackTick() < 160) {
            glowScale = 2.2F;
          } else {
            glowScale = 2.2F - (entityIn.getAttackTick() + partialTicks) / 10.0F;
          }  
        if (!clone) {
          GlStateManager.func_179094_E();
          GlStateManager.func_179152_a(glowScale, glowScale * 0.8F, glowScale);
        } 
        GlStateManager.func_179140_f();
        RenderMutantEnderman.this.func_110776_a(RenderMutantEnderman.GLOW_TEXTURE);
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179096_D();
        float f = (entityIn.field_70173_aa + partialTicks) * 0.008F;
        GlStateManager.func_179109_b(f, f, 0.0F);
        GlStateManager.func_179128_n(5888);
        GlStateManager.func_179108_z();
        GlStateManager.func_179147_l();
        GlStateManager.func_187401_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int var5 = 15728880;
        int var6 = var5 % 65536;
        int var7 = var5 / 65536;
        OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, var6, var7);
        GlStateManager.func_179131_c(0.9F, 0.3F, 1.0F, getAlpha(entityIn, partialTicks));
        GlStateManager.func_179145_e();
        (Minecraft.func_71410_x()).field_71460_t.func_191514_d(true);
        RenderMutantEnderman.this.func_177087_b().func_78088_a((Entity)entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        (Minecraft.func_71410_x()).field_71460_t.func_191514_d(false);
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179096_D();
        GlStateManager.func_179128_n(5888);
        GlStateManager.func_179084_k();
        if (!clone)
          GlStateManager.func_179121_F(); 
      } 
    }
    
    protected float getAlpha(EntityMutantEnderman entity, float partialTicks) {
      float alpha = 1.0F;
      if (entity.getAttackID() == 7 && entity.getAttackTick() < 10) {
        if (!RenderMutantEnderman.this.teleportAttack && entity.getAttackTick() >= 8)
          alpha -= ((entity.getAttackTick() - 8) + partialTicks) / 2.0F; 
        if (RenderMutantEnderman.this.teleportAttack && entity.getAttackTick() < 2)
          alpha = (entity.getAttackTick() + partialTicks) / 2.0F; 
      } 
      if (entity.getAttackID() == 8)
        if (entity.getAttackTick() < 40) {
          alpha = (entity.getAttackTick() + partialTicks) / 40.0F;
        } else if (entity.getAttackTick() >= 160) {
          alpha = 1.0F - (entity.getAttackTick() + partialTicks) / 40.0F;
        }  
      return alpha;
    }
    
    public boolean func_177142_b() {
      return false;
    }
  }
}
